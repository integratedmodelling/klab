package org.integratedmodelling.klab.hub.tests;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.hub.IHubStartupOptions;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.HubApplication;
import org.integratedmodelling.klab.hub.HubStartupOptions;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {HubApplication.class})
@TestPropertySource(locations="classpath:application.yml")
@ActiveProfiles(profiles = "development")
public abstract class ApplicationCheck {
	
	@LocalServerPort
	int port;
	
	public static GreenMail greenMail;
	
	@BeforeClass public static void setup() {
		IHubStartupOptions options = new HubStartupOptions();
		ICertificate certFile = KlabCertificate.createFromFile(options.getCertificateFile());
		certFile.isValid();
		HubAuthenticationManager.INSTANCE.authenticate(options, certFile);
		
		greenMail = new GreenMail(ServerSetupTest.ALL);
		greenMail.setUser("system", "password").create();
		greenMail.setUser("new.user@email.com", "new.user@email.com", "password").create();
		greenMail.setUser("hades@integratedmodelling.org", "hades@integratedmodelling.org", "password").create();
		greenMail.start();
		
	}
	
    
    
}
    