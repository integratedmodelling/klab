/**
 * 
 */
package org.integratedmodelling.klab.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Authenticate and try resolving resources from k.LAB 1.0 into legacy
 * resources.
 * 
 * @author ferdinando.villa
 *
 */
public class ResourceTranslationTests {

	static IUserIdentity user;

	static String KLAB10_URN = "im.resources-main:staging:im.data.global:impact.observatory.annual.landuse";

	@BeforeClass
	public static void setUp() throws Exception {

		File certFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + "im.cert");
		var certificate = certFile.exists() ? KlabCertificate.createFromFile(certFile)
				: KlabCertificate.createDefault();

		user = Authentication.INSTANCE.authenticate(certificate);

	}

	@AfterClass
	public static void tearDown() throws Exception {
	}

	@Test
	public void retrieveResource() throws Exception {
		var resource = Resources.INSTANCE.resolveResource(KLAB10_URN);
		assertNotNull(resource);
	}

}
