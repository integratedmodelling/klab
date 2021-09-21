package klab.component.stats;

import java.net.MalformedURLException;
import java.net.URISyntaxException;


import org.apache.http.client.utils.URIBuilder;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.stats.api.StatsFindPageResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class StatsRestTest {
	
    @Autowired
    RestTemplate restTemplate = new RestTemplate();
    
	@Test
	public void test_1() {
		ParameterizedTypeReference<StatsFindPageResponse<SessionActivity>> sessions =
				new ParameterizedTypeReference<StatsFindPageResponse<SessionActivity>>() {};
		
		String url = null;
	    try {
			URIBuilder b = new URIBuilder("http://192.168.250.210:8380" + API.STATS.STATS_BASE);
			b.addParameter(API.STATS.PARAMETERS.TYPE, SessionReference.class.getCanonicalName());
			b.addParameter(API.STATS.PARAMETERS.PAGE, "1");
			b.addParameter(API.STATS.PARAMETERS.LIMIT, "500");
			url = b.build().toURL().toString();
		} catch (URISyntaxException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    		   
        ResponseEntity<StatsFindPageResponse<SessionActivity>> response = restTemplate
                .exchange(url,HttpMethod.GET, null, sessions);
	}

}
