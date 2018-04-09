package org.integratedmodelling.klab.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

  static ObjectMapper defaultMapper = new ObjectMapper();

  /**
   * Default conversion for a map object.
   * 
   * @param node
   * @return
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> asMap(JsonNode node) {
    return defaultMapper.convertValue(node, Map.class);
  }

  /**
   * Default conversion, use within custom deserializers to "normally" deserialize an object.
   * 
   * @param node
   * @param cls
   * @return
   */
  public static <T> T as(JsonNode node, Class<T> cls) {
    return defaultMapper.convertValue(node, cls);
  }

  /**
   * Convert node to list of type T
   * 
   * @param node
   * @param cls
   * @return
   */
  public static <T> List<T> asList(JsonNode node, Class<T> cls) {
    return defaultMapper.convertValue(node, new TypeReference<List<T>>() {});
  }
  
  /**
   * Convert node to list of type T
   * 
   * @param node
   * @param cls
   * @return
   */
  public static <T> Set<T> asSet(JsonNode node, Class<T> cls) {
    return defaultMapper.convertValue(node, new TypeReference<Set<T>>() {});
  }

}
