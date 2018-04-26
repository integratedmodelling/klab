package org.integratedmodelling.klab.engine.rest;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

/**
 * Helper class that extracts the JSON schema from all the beans in this package and subpackages.
 * 
 * @author ferdinando.villa
 *
 */
public class SchemaExtractor {

    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonSchemaGenerator schemaGen;
    private static Map<String, Map<Class<?>, JsonSchema>> schemata = new HashMap<>();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        schemaGen = new JsonSchemaGenerator(mapper);
    }

    /**
     * Return the list of POJO classes in dependency order.
     * 
     * @param packageId
     * @return sorted POJO classes in package
     */
    public static List<Class<?>> getSortedClasses(String packageId) {

        List<Class<?>> ret = new ArrayList<>();
        Map<Class<?>, JsonSchema> schema = extractResourceSchema(packageId);
        Graph<Class<?>, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        for (Class<?> cls : schema.keySet()) {

            try {
                graph.addVertex(cls);
                for (Field field : cls.getDeclaredFields()) {
                    Type type = field.getGenericType();
                    Class<?> fclass = null;
                    if (type instanceof ParameterizedType) {
                        if (Map.class.isAssignableFrom(field.getType())) {
                            Type dtype = ((ParameterizedType) type).getActualTypeArguments()[1];
                            if (dtype instanceof WildcardType) {
                                dtype = ((WildcardType)dtype).getUpperBounds()[0];
                            }
                            fclass = (Class<?>)dtype;
                        } else {
                            Type dtype = ((ParameterizedType) type).getActualTypeArguments()[0];
                            if (dtype instanceof WildcardType) {
                                dtype = ((WildcardType)dtype).getUpperBounds()[0];
                            }
                            fclass = (Class<?>)dtype;
                        }
                    } else {
                        fclass = (Class<?>) type;
                    }
                    if (fclass.getCanonicalName().startsWith(packageId)) {
                        graph.addVertex(fclass);
                        graph.addEdge(fclass, cls);
                    }
                }
            } catch (Exception e) {
                throw new KlabInternalErrorException(e);
            }
        }

        TopologicalOrderIterator<Class<?>, DefaultEdge> iterator = new TopologicalOrderIterator<>(graph);
        for (; iterator.hasNext();) {
            ret.add(iterator.next());
        }

        return ret;
    }

    public static String getSchemaIds(String packageId) {

        String ret = "{\n   \"schemata\": [";
        boolean first = true;
        for (Class<?> key : getSortedClasses(packageId)) {
            ret += (first ? "" : ",") + "\n      " + "\"" + Path.getLast(key.getCanonicalName(), '.') + "\"";
            first = false;
        }
        return ret + "\n   ],\n   \"package\": \"" + packageId + "\"\n}";
    }

    public static String getSchemata(String packageId) {
        String ret = "{\n";
        try {
            Map<Class<?>, JsonSchema> schema = extractResourceSchema(packageId);
            int count = schema.size();
            int n = 0;
            for (Class<?> cls : getSortedClasses(packageId)) {
                ret += "   \"" + Path.getLast(cls.getCanonicalName(), '.') + "\" : "
                        + mapper.writeValueAsString(schema.get(cls)) + (n < (count - 1) ? "," : "") + "\n";
                n++;
            }
        } catch (Throwable e) {
            throw new KlabInternalErrorException(e);
        }
        return ret + "\n}";
    }

    public static Map<Class<?>, JsonSchema> extractResourceSchema(String packageId) {

        if (schemata.get(packageId) == null) {
            Map<Class<?>, JsonSchema> ret = new HashMap<>();
            for (Class<?> cls : scanPackage(packageId)) {
                if (!cls.getCanonicalName().endsWith("package-info")) {
                    try {
                        ret.put(cls, schemaGen.generateSchema(cls));
                    } catch (JsonMappingException e) {
                        throw new KlabInternalErrorException(e);
                    }
                }
            }
            schemata.put(packageId, ret);
        }
        return schemata.get(packageId);
    }

    private static List<Class<?>> scanPackage(String packageId) {

        List<Class<?>> ret = new ArrayList<>();
        try {
            for (ClassInfo cls : ClassPath.from(SchemaExtractor.class.getClassLoader())
                    .getTopLevelClassesRecursive(packageId)) {
                ret.add(Class.forName(cls.getName()));
            }
        } catch (Throwable e) {
            throw new KlabInternalErrorException(e);
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(getSchemata(Klab.REST_RESOURCES_PACKAGE_ID));
    }

    public static String getSchema(String restResourcesPackageId, String resourceId) {
        if ("all".equals(resourceId)) {
            return getSchemaIds(restResourcesPackageId);
        }
        try {
            Map<Class<?>, JsonSchema> schemata = extractResourceSchema(restResourcesPackageId);
            Class<?> key = null;
            for (Class<?> k : schemata.keySet()) {
                if (k.getCanonicalName().endsWith("." + resourceId)) {
                    key = k;
                    break;
                }
            }
            if (key == null) {
                throw new IllegalArgumentException("JSON schema for class " + resourceId + " not found");
            }
            return mapper.writeValueAsString(schemata.get(key));
        } catch (Throwable e) {
            throw new KlabInternalErrorException(e);
        }
    }

}
