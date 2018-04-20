package org.integratedmodelling.klab.engine.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public static final String JSON_$SCHEMA_DRAFT4_VALUE = "http://json-schema.org/draft-04/schema#";
    public static final String JSON_$SCHEMA_ELEMENT = "$schema";

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        schemaGen = new JsonSchemaGenerator(mapper);
    }

    public static String getSchemata(String packageId) {
        String ret = "{\n";
        try {
            Map<Class<?>, JsonSchema> schema = extractResourceSchema(packageId);
            int count = schema.size();
            int n = 0;
            for (Class<?> cls : schema.keySet()) {
                ret += "   \"" + cls.getCanonicalName() + "\" : " + mapper.writeValueAsString(schema.get(cls))
                        + (n < (count - 1) ? "," : "") + "\n";
                n++;
            }
        } catch (JsonMappingException e) {
            Logging.INSTANCE.error(e);
        } catch (JsonProcessingException e) {
            Logging.INSTANCE.error(e);
        }
        return ret + "\n}";
    }

    public static Map<Class<?>, JsonSchema> extractResourceSchema(String packageId) throws JsonMappingException {

        Map<Class<?>, JsonSchema> ret = new HashMap<>();
        for (Class<?> cls : scanPackage(packageId)) {
            if (!cls.getCanonicalName().endsWith("package-info")) {
                ret.put(cls, schemaGen.generateSchema(cls));
            }
        }

        return ret;
    }

    private static List<Class<?>> scanPackage(String packageId) {

        List<Class<?>> ret = new ArrayList<>();
        try {
            for (ClassInfo cls : ClassPath.from(SchemaExtractor.class.getClassLoader())
                    .getTopLevelClassesRecursive(packageId)) {
                ret.add(Class.forName(cls.getName()));
            }
        } catch (Exception e) {
            Logging.INSTANCE.error(e);
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(getSchemata(Klab.REST_RESOURCES_PACKAGE_ID));
    }

}
