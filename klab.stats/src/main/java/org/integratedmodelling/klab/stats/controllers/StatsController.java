package org.integratedmodelling.klab.stats.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;
import org.integratedmodelling.klab.stats.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@RestController
public class StatsController {
	
	@Autowired
	StatsService service;
	
	@Autowired
	private ObjectMapper mapper;
	
	@PostMapping(API.STATS.STATS_BASE)
	StatsInstertResponse<?> handleRequest(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		InputStream body = request.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		
	    String text = new BufferedReader(
	    	      new InputStreamReader(body, StandardCharsets.UTF_8))
	    	        .lines()
	    	        .collect(Collectors.joining("\n"));
	    
	    Map<String, Object> jsonMap = mapper.readValue(text, Map.class);
	    	        
		String type = (String) jsonMap.get("type");
		TypeFactory factory = TypeFactory.defaultInstance();
		
		JavaType typeC = mapper
				.getTypeFactory()
				.constructParametricType(StatsInsertRequest.class, factory.constructFromCanonical(type));
		
		return service.insertRequest(mapper.readValue(text, typeC));
	}
}
