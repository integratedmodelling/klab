package org.integratedmodelling.klab.engine.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.engine.configs.ConsulConfig;
import org.integratedmodelling.klab.engine.services.ConsulDnsService;
import org.integratedmodelling.klab.engine.services.HubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class ConsulServiceRegistryAspect {
	
	@Autowired
	ConsulDnsService service;
	
	
	@After("execution(* org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry.register(..))")
    public void setIntialServiceDnsWeight(JoinPoint joinPoint) 
    {
        service.setIntialServiceWeight();
    }
	
	@Before("execution(* org.integratedmodelling.klab.engine.Engine.closeExpiredSessions(..))")
    public void createdSessiont(JoinPoint joinPoint) 
    {
        Logging.INSTANCE.debug("Reaper is comming");
    }
	
	@After("execution(* org.integratedmodelling.klab.engine.runtime.Session.close(..))")
    public void reclaimServiceDnsWeight(JoinPoint joinPoint) 
    {
        Logging.INSTANCE.debug("Session closed, so I should do something abour my weight.");
    }

}
