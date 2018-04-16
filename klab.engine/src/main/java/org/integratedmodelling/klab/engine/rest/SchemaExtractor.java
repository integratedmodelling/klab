package org.integratedmodelling.klab.engine.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

/**
 * Helper class that extracts the JSON schema from all the beans in this package and subpackages.
 * 
 * @author ferdinando.villa
 *
 */
public class SchemaExtractor {

  private static ObjectMapper mapper                    = new ObjectMapper();
  public static final String  JSON_$SCHEMA_DRAFT4_VALUE = "http://json-schema.org/draft-04/schema#";
  public static final String  JSON_$SCHEMA_ELEMENT      = "$schema";

  static {
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public static String getSchemata(String packageId) {
    String ret = "{\n";
    Map<Class<?>, JsonNode> schema = extractResourceSchema(packageId);
    for (Class<?> cls : schema.keySet()) {
      try {
        ret +=
            "   \"" + cls.getCanonicalName() + "\" : " + mapper.writeValueAsString(schema) + "\n";
      } catch (JsonProcessingException e) {
        Logging.INSTANCE.error(e);
      }
    }
    return ret;
  }

  public static Map<Class<?>, JsonNode> extractResourceSchema(String packageId) {

    Map<Class<?>, JsonNode> ret = new HashMap<>();
    JsonSchemaFactory v4generator = new JsonSchemaV4Factory();
    v4generator.setAutoPutDollarSchema(true);
    
    for (Class<?> cls : scanPackage(packageId)) {
      ret.put(cls,  v4generator.createSchema(cls));
    }

    return ret;
  }

  public static void prettyPrintSchema(JsonNode schema) {
    try {
      System.out.println(mapper.writeValueAsString(schema));
    } catch (JsonProcessingException e) {
      throw new KlabValidationException(e);
    }
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
