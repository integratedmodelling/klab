package org.integratedmodelling.klab.engine.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.engine.services.ConsulDnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Aspect
@Profile("consul")
public class ConsulServiceRegistryAspect {
	
	@Autowired
	ConsulDnsService service;
	
	@After("execution(* org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry.register(..))")
    public void setIntialServiceDnsWeight(JoinPoint joinPoint) 
    {
        service.setIntialServiceWeight();
    }
	
	@Before("execution(* org.integratedmodelling.klab.engine.runtime.Session.Listener.onClose(..))")
    public void createdSessiont(JoinPoint joinPoint) 
    {
        Logging.INSTANCE.debug("Reaper is comming");
    }
	
	@Pointcut("execution(* org.integratedmodelling.klab.engine.RemoteEngine.closeExpiredSessions(..))")
    public void reclaimServiceDnsWeight() 
    {
        Logging.INSTANCE.debug("Session closed, so I should do something abour my weight.");
    }

}
