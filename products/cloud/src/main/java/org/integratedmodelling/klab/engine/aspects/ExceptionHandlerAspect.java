package org.integratedmodelling.klab.engine.aspects;

import java.io.File;
import java.security.AccessController;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;


/**
 * This is a simple aspect that will wait to see if the get coverage throws any exception.
 * If an exception is thrown in this method, the method needs to check how much free space is left
 * on disk in the temporary folder.  When the disk is full we will exit.  This really is temporary
 * fix for the remote engines running out of disk.
 * 
 * @author steve
 *
 */
@Configuration
@Aspect
public class ExceptionHandlerAspect {
	
	@AfterThrowing(value="execution(* org.integratedmodelling.klab.raster.wcs.WcsEncoder.getCoverage(..))",throwing="ex")  
	public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex)  {
		File tmpdir = new File(System.getProperty("java.io.tmpdir"));
		long freeSpace = tmpdir.getFreeSpace();
		//10Mb
		if (freeSpace < 10000000) {
			System.exit(0);
		}
	}
}
