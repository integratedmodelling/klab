package klab.stats;

import static org.junit.Assert.assertEquals;

import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.stats.Application;
import org.integratedmodelling.klab.stats.services.HelloService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class MongoTestIntegrationTest {

	
	
    @Autowired
    HelloService helloService;
    
    @Autowired
    MongoClient mongo;
    

    
    @DisplayName("Test You are Sane")
    @Test
    public void testGet() {
        assertEquals("Hello JUnit 5", helloService.get());
    }


    
//    @DisplayName("given object to save"
//            + " when save object using MongoDB template"
//            + " then object is saved")
//    @Test
//    public void test() throws InstantiationException, IllegalAccessException {
//    	KlabRestEntity objectToSave = new KlabRestEntity(new SessionReference());
//    	objectToSave.setModel(new SessionReference());
//        // when
//        mongoTemplate.save(objectToSave, objectToSave.getMyType());
//        KlabRestEntity objectToSave2 = new KlabRestEntity(new SessionActivity());
//        mongoTemplate.save(objectToSave2, objectToSave.getMyType());
//        
//        // then
//        Query query = new Query();
//        List<KlabRestEntity> test = mongoTemplate.find(query, KlabRestEntity.class, SessionReference.class.getSimpleName());
//
//        Query query2= new Query(Criteria.where("model._class").is(SessionReference.class.getName()));
//        test = mongoTemplate.find(query2, KlabRestEntity.class, SessionReference.class.getSimpleName());
//        Object x = test.get(0).getModel();
//        if(x instanceof SessionReference) {
//        	SessionReference y = (SessionReference) x;
//        	y.getAppUrns();
//        }
//        
//    }
    
    
    @Test
    public void test1() throws InstantiationException, IllegalAccessException {
    	MongoDatabase db = mongo.getDatabase("stats");
    	MongoCollection<SessionReference> sessions = db.getCollection("sessions", SessionReference.class);
    	
    	SessionReference ref = new SessionReference();
    	ref.setId("34ru8524452390549023");
    	InsertOneResult res = sessions.insertOne(ref);
    	FindIterable<SessionReference> y = sessions.find();
    	y.forEach(action -> {
    		System.out.println(action.getId());
    	});
    	
    }
    
    

}