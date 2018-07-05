package org.integratedmodelling.klab.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.engine.resources.AbstractWorkspace;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.exceptions.KlabIllegalStatusException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.rest.AuthenticationResponse;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Wraps an IM certificate and checks for encryption and validity.
 * 
 * @author Ferd
 *
 */
public class KlabCertificate implements ICertificate {


	// just for info
	public static final String KEY_EXPIRATION = "klab.validuntil";

	private static final String LEGACY_AUTHENTICATION_ENDPOINT = "https://integratedmodelling.org/collaboration/authentication/cert-file";

	// TODO just store a URL and handle uniformly
	private File file = null;
	private String resource = null;

	private Properties properties = null;
	private String cause = null;
	private DateTime expiry;
	private String worldview = DEFAULT_WORLDVIEW;
	private Collection<String> worldview_repositories = StringUtils.splitOnCommas(DEFAULT_WORLDVIEW_REPOSITORIES);
	private AuthenticationResponse authentication = null;
//	private IIdentity identity = null;
	private String certificateType = "UNKNOWN";

	/**
	 * Property key for username
	 */
	public static final String USER_KEY = "user";
	/**
	 * Property key for primary node.
	 */
	public static final String PRIMARY_NODE_KEY = "primary.server";

	static final String DEFAULT_WORLDVIEW = "im";

	static final String DEFAULT_WORLDVIEW_REPOSITORIES = "https://bitbucket.org/ariesteam/im.git#feature/noobservers";

	/**
	 * Create a new certificate from a file. Check isValid() on the resulting
	 * certificate.
	 * 
	 * @param file
	 *            the certificate file
	 * @return a certificate read from the passed file
	 */
	public static ICertificate createFromFile(File file) {
		if (file == null) {
			return createDefault();
		}
		return new KlabCertificate(file);
	}

	public static ICertificate createFromClasspath(String resource) {
		return new KlabCertificate(resource);
	}

	/**
	 * Get the file from its configured locations and open it. If there is no
	 * certificate and the configuration allows anonymous users, return an anonymous
	 * certificate. Check {@link #isValid()} after construction.
	 * 
	 * @return the default certificate
	 */
	public static ICertificate createDefault() {

		File certfile = getCertificateFile();
		if (certfile.exists()) {
			return new KlabCertificate(certfile);
		}
		if (Configuration.INSTANCE.allowAnonymousUsage()) {
			return new AnonymousCertificate();
		}
		throw new KlabIllegalStatusException("certificate file not found and anonymous usage not allowed");
	}

	/**
	 * Get the file for the certificate according to configuration. Does not check
	 * that the file exists.
	 * 
	 * @return the configured certificate file location
	 */
	public static File getCertificateFile() {
		if (System.getProperty(Configuration.CERTFILE_PROPERTY) != null) {
			return new File(System.getProperty(Configuration.CERTFILE_PROPERTY));
		}
		return new File(Configuration.INSTANCE.getDataPath() + File.separator + DEFAULT_CERTIFICATE_FILENAME);
	}

	/**
	 * Create from a certificate file, downloading the public keyring from the net
	 * if not already installed. Check {@link #isValid()} after construction.
	 * 
	 * @param file
	 */
	private KlabCertificate(File file) {
		this.file = file;
	}

	private KlabCertificate(String resource) {
		this.resource = resource;
	}

	public boolean isValid() {

		if (cause != null) {
			return false;
		}

		if (properties == null) {

			if (file != null && (!file.exists() || !file.isFile() || !file.canRead())) {
				cause = "certificate file cannot be read";
				return false;
			}

			if (file != null && upgradeCertificate(file)) {
				return true;
			}

			properties = new Properties();
			try (InputStream inp = (resource == null ? new FileInputStream(file)
					: getClass().getClassLoader().getResource(resource).openStream())) {
				properties.load(inp);
			} catch (IOException e) {
				cause = e.getMessage();
				return false;
			}

			this.certificateType = properties.getProperty(KEY_CERTIFICATE_TYPE, "UNKNOWN");

			return true;
		}

		return false;
	}

	/**
	 * Check if the certificate is an old version and try to upgrade it by
	 * authenticating to the collaboration server and writing a new one for a
	 * generic hub. If successful, finish authentication and return true to signal
	 * that everything is done.
	 * 
	 * @param file
	 * @return true if
	 */
	private boolean upgradeCertificate(File file2) {
		try {
			String fileContent = FileUtils.readFileToString(file2);
			if (fileContent.startsWith("-----BEGIN PGP MESSAGE-----")) {

				// reauthenticate and produce a new format certificate
				RestTemplate restTemplate = new RestTemplate();
				HttpEntity<String> request = new HttpEntity<String>(fileContent);
				Map<?,?> response = restTemplate.postForObject(LEGACY_AUTHENTICATION_ENDPOINT, request, Map.class);
				if (response != null) {
					
					Map<?,?> profile = (Map<?, ?>) response.get("profile");
					
					Logging.INSTANCE.info("upgrading pre-0.10 certificate to new format: new certificate will be saved as klab.cert");
					
					this.properties = new Properties();
					this.properties.setProperty(KEY_CERTIFICATE, fileContent);
					this.properties.setProperty(KEY_EXPIRATION, response.get("expiration").toString());
					this.properties.setProperty(KEY_CERTIFICATE_TYPE, this.certificateType = "USER");
					this.properties.setProperty(KEY_USERNAME, response.get("username").toString());
					this.properties.setProperty(KEY_EMAIL, profile.get("email").toString());
					this.properties.setProperty(KEY_SIGNATURE, "legacy certificate");
					this.properties.setProperty(KEY_PARTNER_NAME, "integratedmodelling.org");
					this.properties.setProperty(KEY_PARTNER_EMAIL, "info@integratedmodelling.org");
					this.properties.setProperty(KEY_NODENAME, "im");

					// TODO add these back afterwards. Not finding them will cause a local server to be used.
					// this.properties.setProperty(KEY_SERVER, "https://integratedmodelling.org/klab");
					// this.properties.setProperty(KEY_URL, "https://integratedmodelling.org/klab");
					
					File out = new File(Configuration.INSTANCE.getDataPath() + File.separator + "klab.cert");
					try (FileOutputStream o = new FileOutputStream(out)) {
						this.properties.store(o, "Automatically upgraded on " + new Date());
					}

					this.expiry = DateTime.parse(response.get("expiration").toString());
					if (expiry == null) {
						cause = "certificate has no expiration date. Please obtain a new certificate.";
						return false;
					} else if (expiry.isBeforeNow()) {
						cause = "certificate expired on " + expiry + ". Please obtain a new certificate.";
						return false;
					}
					
					return true;
					
				} else {
					Logging.INSTANCE.error("legacy certificate could not be authenticated");
				}
			}
		} catch (IOException e) {
			// just return false
		}
		return false;
	}

	public DateTime getExpiryDate() {
		return expiry;
	}

	/**
	 * If {@link #isValid()} returns false, return the reason why.
	 * 
	 * @return cause of invalidity
	 */
	@Override
	public String getInvalidityCause() {
		return cause;
	}

	/**
	 * Return the file we've been read from.
	 * 
	 * @return the certificate file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Descriptive string with user name, email, and expiry.
	 * 
	 * @return printable description.
	 */
	public String getUserDescription() {
		if (properties != null && properties.containsKey(KEY_USERNAME)) {
			return properties.getProperty(KEY_USERNAME) + " (" + properties.getProperty(KEY_USERNAME)
					+ "), valid until " + DateTimeFormat.forPattern("MM/dd/YYYY").print(expiry);
		}
		return "anonymous user";
	}

	@Override
	public IWorldview getWorldview() {
		return new Worldview(worldview, Configuration.INSTANCE.getDataPath("worldview"), worldview_repositories,
				((AbstractWorkspace) Resources.INSTANCE.getLocalWorkspace()).getProjectLocations()
						.toArray(new File[((AbstractWorkspace) Resources.INSTANCE.getLocalWorkspace())
								.getProjectLocations().size()]));
	}

	@Override
	public String getProperty(String property) {
		return properties == null ? null : properties.getProperty(property);
	}
}
