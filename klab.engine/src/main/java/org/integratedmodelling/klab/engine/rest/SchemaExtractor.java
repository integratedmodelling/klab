package org.integratedmodelling.klab.engine.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Path;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

/**
 * Helper class that extracts the JSON schema from all the beans in this package
 * and subpackages.
 * 
 * @author ferdinando.villa
 *
 */
public class SchemaExtractor {

	private static ObjectMapper mapper = new ObjectMapper();
	private static JsonSchemaGenerator schemaGen;
	private static Map<String, Map<Class<?>, JsonNode>> schemata = new HashMap<>();

	static {
		// avoid all f'ing transient fields.
		mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
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
		Map<Class<?>, JsonNode> schema = extractResourceSchema(packageId);
		// Graph<Class<?>, DefaultEdge> graph = new
		// DefaultDirectedGraph<>(DefaultEdge.class);
		// for (Class<?> cls : schema.keySet()) {
		//
		// try {
		// graph.addVertex(cls);
		// for (Field field : cls.getDeclaredFields()) {
		// Type type = field.getGenericType();
		// Class<?> fclass = null;
		// if (type instanceof ParameterizedType) {
		// if (Map.class.isAssignableFrom(field.getType())) {
		// Type dtype = ((ParameterizedType) type).getActualTypeArguments()[1];
		// if (dtype instanceof WildcardType) {
		// dtype = ((WildcardType) dtype).getUpperBounds()[0];
		// }
		// fclass = (Class<?>) dtype;
		// } else {
		// Type dtype = ((ParameterizedType) type).getActualTypeArguments()[0];
		// if (dtype instanceof WildcardType) {
		// dtype = ((WildcardType) dtype).getUpperBounds()[0];
		// }
		// fclass = (Class<?>) dtype;
		// }
		// } else {
		// fclass = (Class<?>) type;
		// }
		// if (fclass.getCanonicalName().startsWith(packageId)) {
		// graph.addVertex(fclass);
		// graph.addEdge(fclass, cls);
		// }
		// }
		// } catch (Exception e) {
		// throw new KlabInternalErrorException(e);
		// }
		// }
		//
		// CycleDetector<Class<?>, DefaultEdge> cycleDetector = new
		// CycleDetector<>(graph);
		//
		// if (cycleDetector.detectCycles()) {
		// Iterator<Class<?>> iterator;
		// Set<Class<?>> cycleVertices;
		// Set<Class<?>> subCycle;
		// Class<?> cycle;
		//
		// // Get all vertices involved in cycles.
		// cycleVertices = cycleDetector.findCycles();
		//
		// // Loop through vertices trying to find disjoint cycles.
		// while (!cycleVertices.isEmpty()) {
		//// System.out.println("Cycle:");
		//
		// // Get a vertex involved in a cycle.
		// iterator = cycleVertices.iterator();
		// cycle = iterator.next();
		//
		// // Get all vertices involved with this vertex.
		// subCycle = cycleDetector.findCyclesContainingVertex(cycle);
		// for (Class<?> sub : subCycle) {
		//// System.out.println(" " + sub);
		// // Remove vertex so that this cycle is not encountered again
		// cycleVertices.remove(sub);
		// }
		// }
		// }
		//
		// TopologicalOrderIterator<Class<?>, DefaultEdge> iterator = new
		// TopologicalOrderIterator<>(graph);
		// for (; iterator.hasNext();) {
		// ret.add(iterator.next());
		// }

		ret.addAll(schema.keySet());

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
			Map<Class<?>, JsonNode> schema = extractResourceSchema(packageId);
			List<Class<?>> classes = getSortedClasses(packageId);
			Map<Class<?>, JsonNode> retained = new HashMap<>();
			for (Class<?> cls : classes) {
				if (schema.get(cls) != null) {
					retained.put(cls, schema.get(cls));
				}
			}
			int count = retained.size();
			int n = 0;
			for (Class<?> cls : retained.keySet()) {
				ret += "   \"" + Path.getLast(cls.getCanonicalName(), '.') + "\" : "
						+ mapper.writeValueAsString(retained.get(cls)) + (n < (count - 1) ? "," : "") + "\n";
				n++;
			}
		} catch (Throwable e) {
			throw new KlabInternalErrorException(e);
		}
		return ret + "\n}";
	}

	public static Map<Class<?>, JsonNode> extractResourceSchema(String packageId) {

		if (schemata.get(packageId) == null) {
			Map<Class<?>, JsonNode> ret = new HashMap<>();
			for (Class<?> cls : scanPackage(packageId)) {
				if (!cls.getCanonicalName().endsWith("package-info")) {
					try {
						ret.put(cls, schemaGen.generateJsonSchema(cls));
					} catch (Exception e) {
						// it seems that generateJsonSchema throw a generic java.lang.Exception
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
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(Object.class));
		Set<BeanDefinition> beans = provider.findCandidateComponents(packageId);
		try {
			for (BeanDefinition bd : beans) {
				ret.add(Class.forName(bd.getBeanClassName()));
			}
		} catch (Throwable e) {
			throw new KlabInternalErrorException(e);
		}
		//
		//
		//
		// try {
		// for (ClassInfo cls : ClassPath.from(SchemaExtractor.class.getClassLoader())
		// .getTopLevelClassesRecursive(packageId)) {
		// ret.add(Class.forName(cls.getName()));
		// }
		// } catch (Throwable e) {
		// throw new KlabInternalErrorException(e);
		// }
		return ret;
	}

	public static void main(String[] args) {
		System.out.println(getSchemata(IConfigurationService.REST_RESOURCES_PACKAGE_ID));
	}

	public static String getSchema(String restResourcesPackageId, String resourceId) {
		if ("all".equals(resourceId)) {
			return getSchemaIds(restResourcesPackageId);
		}
		try {
			Map<Class<?>, JsonNode> schemata = extractResourceSchema(restResourcesPackageId);
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
