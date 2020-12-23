//package org.integratedmodelling.klab.stats.config;
//  
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.bson.codecs.pojo.ClassModel;
//import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;
//import org.reflections.Reflections;
//import org.reflections.scanners.ResourcesScanner;
//import org.reflections.scanners.SubTypesScanner;
//import org.reflections.util.ClasspathHelper;
//import org.reflections.util.ConfigurationBuilder;
//import org.reflections.util.FilterBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.TreeNode;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//
//@Configuration public class ObjectMapperConfig {
//  
//	@Bean
//	public ObjectMapper objectMapper() { 
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
//		//mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.  OBJECT_AND_NON_CONCRETE);
//		mapper.registerModules(getModules());
//		return mapper;
//	}
//  
//  
//  
//  public SimpleModule[] getModules() {
//		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
//		classLoadersList.add(ClasspathHelper.contextClassLoader());
//		classLoadersList.add(ClasspathHelper.staticClassLoader());
//
//		Reflections reflections = new Reflections(new ConfigurationBuilder()
//		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
//		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
//		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.integratedmodelling.klab.rest"))));
//		
//		List<SimpleModule> sm = new ArrayList<SimpleModule>();
//		
//		reflections.getAllTypes().forEach(cls -> {
//			try {
//				Class<?> cl = Class.forName(cls);
//				sm.add(getCustomDeserializer(cl));
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		SimpleModule[] array = new SimpleModule[sm.size()];
//		sm.toArray(array);
//		return array;
//  }
//  
//  private SimpleModule getCustomDeserializer(Class<?> cls) {
//	  SimpleModule module = new SimpleModule(cls.toGenericString());
//	  //module.addDeserializer(cls, )
//	  return module;
//  }
//  
//  private class CustomDeserializer<M> extends StdDeserializer<StatsInsertRequest<M>> {
//	  
//	  private static final long serialVersionUID = -2002762415236171568L;
//	  private Class<M> type;
//
//	  protected CustomDeserializer(Class<?> vc) {
//		  super(vc);
//	  }
//	  
//	  protected CustomDeserializer(Class<?> vc, Class<M> cls) {
//		  super(vc);
//		  this.type = cls;
//	  }
//	  
//	  @Override
//	  public StatsInsertRequest<M> deserialize(JsonParser p, DeserializationContext ctxt)
//			  throws IOException, JsonProcessingException {
//		  JsonNode node = p.readValueAsTree();
//		  M mapper = objectMapper().readValue(node.get("m").get(0).asText(), M);
//		  StatsInsertRequest<M> resp = new StatsInsertRequest<>(type);
//		  resp.setModel(mapper);
//	  }
//	  
//  }
// 
//}