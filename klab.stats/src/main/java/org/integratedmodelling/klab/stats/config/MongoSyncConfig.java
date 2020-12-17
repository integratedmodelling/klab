package org.integratedmodelling.klab.stats.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.ServerVersion;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.Conventions;

@Configuration
public class MongoSyncConfig {

  @Bean(destroyMethod="shutdown")
  public MongoServer mongoServer() {
	  
	  MemoryBackend backend = (MemoryBackend) new MemoryBackend().version(ServerVersion.MONGO_3_6);
  	
	  MongoServer mongoServer = new MongoServer(backend);
    
	  mongoServer.bind("localhost", 27017);
    
	  return mongoServer;
  }
  
	@Bean(destroyMethod="close")
	public MongoClient mongoClient(MongoServer mongoServer) {
	    ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
	    CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().conventions(asList(Conventions.ANNOTATION_CONVENTION)).register(getClassModels()).automatic(true).build());
	    //CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().conventions(asList(Conventions.ANNOTATION_CONVENTION)).register("org.integratedmodelling.klab.rest").automatic(true).build());
	    CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
	    MongoClientSettings clientSettings = MongoClientSettings.builder()
	                                                            .applyConnectionString(connectionString)
	                                                            .codecRegistry(codecRegistry)
	                                                            .build();
	    MongoClient mongoClient = MongoClients.create(clientSettings);
	    return mongoClient;
	}

	private List<Convention> asList(Convention annotationConvention) {
		List<Convention> convention = new ArrayList<>();
		convention.add(annotationConvention);
		return convention;
	}
	
	public ClassModel<?>[] getClassModels(){
		//https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection magic?
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.integratedmodelling.klab.rest"))));
		
		reflections.getAllTypes();
		
		List<ClassModel<?>> cm = new ArrayList<ClassModel<?>>();
		
		reflections.getAllTypes().forEach(cls -> {
			try {
				Class<?> cl = Class.forName(cls);
				cm.add(ClassModel.builder(cl).enableDiscriminator(true).build());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		;
		//ClassModel.builder(SessionReference.class).enableDiscriminator(true).build();
		ClassModel<?>[] array = new ClassModel<?>[cm.size()];
		cm.toArray(array);
		return array;
	}
}
