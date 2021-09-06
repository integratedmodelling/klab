package org.integratedmodelling.klab.hub.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.security.RolesAllowed;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.config.LoggingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.EvictingQueue;

import net.minidev.json.JSONObject;

@RestController
@RolesAllowed({"ROLE_SYSTEM" })
public class SystemController {
	
	@Autowired
	LoggingConfig loggingConfig;
	

	@GetMapping(value=API.HUB.LOGS, params = {"lines"})
	public ResponseEntity<?> getHubLogResponse(@RequestParam("lines") int lines) throws IOException {
		Path path = Paths.get(loggingConfig.getLOGGING_FILE());
		File file = path.toFile();
		FileInputStream input = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		EvictingQueue<Object> fifoLog;
		if (lines == -1) {
			int lineCount = (int) Files.lines(path).count();
			fifoLog = EvictingQueue.create(lineCount);
		} else {
			fifoLog = EvictingQueue.create(lines);
		}
		
		for(String tmp; (tmp = reader.readLine()) != null;)
			fifoLog.add(tmp);
		reader.close();
		JSONObject resp = new JSONObject();
		resp.appendField("Log", fifoLog.toArray());
		return new ResponseEntity<JSONObject>(resp, HttpStatus.OK);
	}
	
}
