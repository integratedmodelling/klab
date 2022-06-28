package org.integratedmodelling.amp.annotation;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.rest.TicketResponse.Ticket;
import org.springframework.stereotype.Component;

import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;

@Component
public class AnnotationManager {
	
	public Ticket process(Object jsonObject) {
		Map<String, Object> context = new HashMap<>();
		JsonLdOptions options = new JsonLdOptions();
		Map<String, Object> result = JsonLdProcessor.compact(jsonObject, context, options);
		// TODO Auto-generated method stub
		return null;
	}

}
