package org.integratedmodelling.klab.k;

import javax.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**
 * A Cloud-driven k.LAB Engine.
 * 
 * @author steven.wohl
 *
 */
@EnableAutoConfiguration
@ComponentScan
public class EngineApplication {
	
		private ConfigurableApplicationContext context;
		
		
		public void run(String[] args) {
			context = new SpringApplicationBuilder(EngineApplication.class)
		            .listeners(new EngineRunner()).run();
		}
		

		@PreDestroy
		public void shutdown() {
		}
		
		
		
		public static void main(String args[]) {
			new EngineApplication().run(args);
		}
		


}
