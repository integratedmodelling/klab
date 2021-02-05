package org.integratedmodelling.klab.stats.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.api.models.StatsFindPageRequest;
import org.integratedmodelling.klab.stats.api.models.StatsFindPageResponse;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;
import org.integratedmodelling.klab.stats.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	
	TypeFactory factory = TypeFactory.defaultInstance();
	
	@PostMapping(API.STATS.STATS_BASE)
	StatsInstertResponse<?> handleRequest(HttpServletRequest request) throws IOException {

	    Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> map = new HashMap<>();
        while(parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            map.put(paramName, request.getParameter(paramName));
        }

		InputStream body = request.getInputStream();
	    String text = new BufferedReader(
	    	      new InputStreamReader(body, StandardCharsets.UTF_8))
	    	        .lines()
	    	        .collect(Collectors.joining("\n"));
	    
	    Map<String, Object> model = mapper.readValue(text, Map.class);
	    
	    map.put("m", model);
		
		JavaType typeC = mapper
				.getTypeFactory()
				.constructParametricType(StatsInsertRequest.class, factory.constructFromCanonical(map.get(API.STATS.PARAMETERS.TYPE).toString()));
		
		mapper.writeValueAsString(map);
		
		return service.insertRequest(mapper.readValue(mapper.writeValueAsString(map), typeC));
		
	}
	
	
	@GetMapping(API.STATS.STATS_BASE)
	StatsFindPageResponse<?> handleFindRequest(HttpServletRequest request) throws IOException {
	    
	    Enumeration<String> parameterNames = request.getParameterNames();
	    Map<String, String> map = new HashMap<>();
	    while(parameterNames.hasMoreElements()) {
	        String paramName = parameterNames.nextElement();
	        map.put(paramName, request.getParameter(paramName));
	    }

	    JavaType typeC = mapper
	            .getTypeFactory()
	            .constructParametricType(StatsFindPageRequest.class, factory.constructFromCanonical(map.get(API.STATS.PARAMETERS.TYPE)));
	    
	    
	    return service.findRequest(mapper.readValue(mapper.writeValueAsString(map) , typeC));
	    
	}
	
}
