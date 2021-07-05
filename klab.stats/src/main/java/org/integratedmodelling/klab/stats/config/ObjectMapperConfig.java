package org.integratedmodelling.klab.stats.config;
  
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ObjectMapperConfig {
  
	@Bean
	public ObjectMapper objectMapper() { 
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		//mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		//mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.  OBJECT_AND_NON_CONCRETE);
		//mapper.registerModules(getModules());
		return mapper;
	}
  
  
  
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
 
}