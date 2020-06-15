package org.integratedmodelling.klab.hub.groups.services;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import net.minidev.json.JSONObject;

import org.integratedmodelling.klab.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Component
public class GroupServiceAdvice {
	
	@Autowired
	UserGroupEntryService groupEntryService;

	//Add advice for CRUD groups
	@AfterReturning(
			pointcut = "execution(* org.integratedmodelling.klab.hub.groups.services.GroupService.*(org.integratedmodelling.klab.hub.api.MongoGroup))", 
			returning = "group" )
	public void afterDeleteGroup(JoinPoint jp, MongoGroup group) {
		if(group != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			JSONObject entry = new JSONObject();
			entry.appendField("command", jp.getSignature().toShortString());
			entry.appendField("group", group);
			entry.appendField("user", username);
			entry.appendField("timestamp", DateTime.now());
			Logging.INSTANCE.info(entry.toJSONString());
		}
	}
	
	@Before("execution(* org.integratedmodelling.klab.hub.groups.services.GroupService.delete(..))") 
	public void beforeDeleteGroup(JoinPoint jp) {
		if(jp.getArgs() != null) {
			MongoGroup group = (MongoGroup) jp.getArgs()[0];
			groupEntryService.removeGroupFromUsers(group);
		}
	}

}
