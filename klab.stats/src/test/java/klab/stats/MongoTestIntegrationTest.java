package klab.stats;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.Application;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;
import org.integratedmodelling.klab.stats.services.HelloService;
import org.integratedmodelling.klab.stats.services.MongoStatServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

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
        assertThat("Hello JUnit 5", is(helloService.get()));
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
    	assertThat(request.getType().toGenericString(), is(response.getMyType().toGenericString()));
    }
    
    
    @DisplayName("Test generic insert via post")
    @Test
    public void test_3() throws URISyntaxException {
    	SessionReference ref = new SessionReference();
    	ref.setId("hereissomeid2");
    	//StatsInsertRequest<SessionReference> request = new StatsInsertRequest<>(SessionReference.class);
    	//request.setModel(ref);
    	
    	URIBuilder urlBuilder = new URIBuilder("http://localhost:" + port);
    	urlBuilder.setPath(API.STATS.STATS_BASE);
    	urlBuilder.addParameter(API.STATS.PARAMETERS.TYPE, ref.getClass().getCanonicalName());
    	
    	String url = urlBuilder.build().toString();
    	
    	ResponseEntity<String> response = restTemplate.postForEntity(url, ref, String.class);
    	response.toString();
    }
    
    @DisplayName("Test get with type")
    @Test
    public void test_4() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("http://localhost:" + port + API.STATS.STATS_BASE + "?type=" + SessionActivity.class.getCanonicalName(), String.class);
        response.getBody().toString();
        System.out.println(response.getBody().toString());
    }

    @DisplayName("Test to get types with count")
    @Test
    public void test_5() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("http://localhost:" + port + API.STATS.STATS_CLASSES, String.class);
        response.getBody().toString();
        System.out.println(response.getBody().toString());
    }
    

}