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
public class CloudApplication {
	
		private ConfigurableApplicationContext context;
		
//		@Bean
//		public ServletWebServerFactory servletContainer() {
//			TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//				@Override
//				protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
////					try {
////						Context context = tomcat.addWebapp("/kgit", Node.gitWarPath.getAbsolutePath());
////						WebappLoader loader = new WebappLoader(Thread.currentThread().getContextClassLoader());
////						context.setLoader(loader);
////					} catch (ServletException e) {
////						throw new IllegalStateException("could not deploy the Git server from the embedded war");
////					}
//					return super.getTomcatWebServer(tomcat);
//				}
//			};
//			return tomcat;
//		}
		
		public void run(String[] args) {
			context = new SpringApplicationBuilder(CloudApplication.class)
		            .listeners(new Cloud()).run();
		}
		

		@PreDestroy
		public void shutdown() {
		}
		
		
		
		public static void main(String args[]) {
			new CloudApplication().run(args);
		}
		


}
