package org.integratedmodelling.klab.stats.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.ClassModelBuilder;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.ServerVersion;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

@Configuration
@Profile("development")
public class MongoSyncConfigDev {

    @Bean(destroyMethod = "shutdown")
    public MongoServer mongoServer() {

        MemoryBackend backend = (MemoryBackend) new MemoryBackend().version(ServerVersion.MONGO_3_6);
        MongoServer mongoServer = new MongoServer(backend);
        mongoServer.bind("localhost", 27017);
        return mongoServer;

    }

    @Bean(destroyMethod = "close")
    public MongoClient mongoClient(MongoServer mongoServer) {
        ConnectionString connectionString = new ConnectionString("mongodb://192.168.250.210:27017");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().register(getClassModels()).automatic(true).build());
        // CodecRegistry pojoCodecRegistry =
        // fromProviders(PojoCodecProvider.builder().conventions(asList(Conventions.ANNOTATION_CONVENTION)).register("org.integratedmodelling.klab.rest").automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();
        MongoClient mongoClient = MongoClients.create(clientSettings);
        return mongoClient;
    }

    private List<Convention> asList(Convention annotationConvention) {
        List<Convention> convention = new ArrayList<>();
        convention.add(annotationConvention);
		convention.add(idConnvention());
        return convention;
    }

    public ClassModel< ? >[] getClassModels() {
        // https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
        // magic?
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());
        ConfigurationBuilder config = new ConfigurationBuilder()
        		.setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
        		.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
        		.filterInputsBy(new FilterBuilder().includePackage("org.integratedmodelling.klab.rest"));
        
        Reflections reflections = new Reflections(config);

        List<ClassModel< ? >> cm = new ArrayList<ClassModel< ? >>();

        reflections.getAllTypes().forEach(cls -> {
            try {
                Class< ? > cl = Class.forName(cls);
                ClassModelBuilder< ? > m = ClassModel.builder(cl).conventions(asList(Conventions.USE_GETTERS_FOR_SETTERS));
                cm.add(m.build());
            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });;
        // ClassModel.builder(SessionReference.class).enableDiscriminator(true).build();
        ClassModel< ? >[] array = new ClassModel< ? >[cm.size()];
        cm.toArray(array);
        return array;
    }

    private Convention idConnvention() {
        return new Convention() {
            @Override
            public void apply(final ClassModelBuilder<?> classModelBuilder) {
                
                if (classModelBuilder.getDiscriminatorKey() == null) {
                    classModelBuilder.discriminatorKey("_t");
                }
                if (classModelBuilder.getDiscriminator() == null && classModelBuilder.getType() != null) {
                    classModelBuilder.discriminator(classModelBuilder.getType().getName());
                }
                
                classModelBuilder.enableDiscriminator(true);
            }
        };
    }

}
