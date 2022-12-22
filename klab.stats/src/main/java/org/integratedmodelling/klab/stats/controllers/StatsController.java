package org.integratedmodelling.klab.stats.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.api.StatsClassResponse;
import org.integratedmodelling.klab.stats.api.models.StatsFindPageRequest;
import org.integratedmodelling.klab.stats.api.models.StatsFindPageResponse;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;
import org.integratedmodelling.klab.stats.services.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@Deprecated
@RestController
public class StatsController {
    
    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);
	
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
            String paramValue = request.getParameter(paramName);
            logger.info("Stats POST -> " + paramName + ": " + (paramValue != null ? paramValue.substring(paramValue.lastIndexOf(".") + 1) : "nothing"));
            map.put(paramName, paramValue);
        }

		InputStream body = request.getInputStream();
	    String text = new BufferedReader(
	    	      new InputStreamReader(body, StandardCharsets.UTF_8))
	    	        .lines()
	    	        .collect(Collectors.joining("\n"));
	    
	    Map<String, Object> model = mapper.readValue(text, Map.class);	    
		Map<String, Object> cleanBoy = replaceKeysWithDot(model);
		
		map.put("m", cleanBoy);
		
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
	    
	    logger.info("Stats GET -> " + typeC.getTypeName());
	    return service.findRequest(mapper.readValue(mapper.writeValueAsString(map) , typeC));
	    
	}
	
	@GetMapping(API.STATS.STATS_CLASSES)
	StatsClassResponse handleClassRequest() {
		return service.findClasses();
	}

	
	private Map<String, Object> replaceKeysWithDot(Map<String, Object> model) {
	    HashMap<String, Object> ret = new HashMap<>();
	    
	    for(Iterator<String> iterator = model.keySet().iterator(); iterator.hasNext();) {
	        
	        String key = iterator.next();
	        String replacement = key.replace(".", "__");
	        
	        if( (!key.contains(".")) && !(model.get(key) instanceof LinkedHashMap)) {         
	            ret.put(key, model.get(key));
	        } else {
	            
	            if(model.get(key) instanceof LinkedHashMap) {
	                Object next = model.get(key);
	                Map<String, Object> nextCleaned = replaceKeysWithDot((Map<String, Object>) next);
	                if(key.contains(".")) {
	                    ret.put(replacement, nextCleaned);
	                } else {
	                    ret.put(key, nextCleaned);
	                }
	            } else {
	                ret.put(replacement, model.get(key));
	            }
	        }
	    }
	    return ret;
	}
}
