package klab.stats;

import static org.junit.Assert.assertEquals;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.Application;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;
import org.integratedmodelling.klab.stats.services.HelloService;
import org.integratedmodelling.klab.stats.services.MongoStatServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.ResponseEntity;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class MongoTestIntegrationTest {

	
	
    @Autowired
    HelloService helloService;
    
    @Autowired
    MongoStatServiceImpl service;
    
    @Autowired
    MongoClient mongo;
    
    @Autowired
    TestRestTemplate restTemplate;
    
    @LocalServerPort
    protected int port;

    
    @DisplayName("Test You are Sane")
    @Test
    public void testGet() {
        assertEquals("Hello JUnit 5", helloService.get());
    }
    
    
    @DisplayName("Test That mongo codecs are correct")
    @Test
    public void test_1() throws InstantiationException, IllegalAccessException {
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
    
    
    @DisplayName("Test generic insert request")
    @Test
    public void test_2() throws InstantiationException, IllegalAccessException {
    	SessionReference ref = new SessionReference();
    	ref.setId("hereissomeid");
    	StatsInsertRequest<SessionReference> request = new StatsInsertRequest<>(SessionReference.class);
    	request.setModel(ref);
    	StatsInstertResponse<?> response = service.insertRequest(request);
    	assertEquals(request.getType(), response.getMyType());
    }
    
    
    @DisplayName("Test generic insert via post")
    @Test
    public void test_3() {
    	SessionReference ref = new SessionReference();
    	ref.setId("hereissomeid2");
    	StatsInsertRequest<SessionReference> request = new StatsInsertRequest<>(SessionReference.class);
    	request.setModel(ref);
    	ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + API.STATS.STATS_BASE, request, String.class);
    	response.toString();
    }
    
    

}