package org.integratedmodelling.authorities.iupac;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.UrlEscape;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * TODO use online service from https://cactus.nci.nih.gov/chemical/structure
 * (see https://cactus.nci.nih.gov/chemical/structure_documentation) and
 * implement caching.
 * 
 * @author Ferd
 *
 */
@Authority(id = IUPACAuthority.ID, description = "", version = Version.CURRENT)
public class IUPACAuthority implements IAuthority {

	public static final String ID = "IUPAC";

	private static final String KEY_PATTERN = "[A-Z]{14}-[A-Z]{10}-[A-Z]";
	private static final String RESOLVER_URL = "https://cactus.nci.nih.gov/chemical/structure";

	private DB db = null;
	private ConcurrentNavigableMap<String, String> cache = null;
	private Pattern pattern;

	public IUPACAuthority() {
		this.db = DBMaker.fileDB(Configuration.INSTANCE.getDataPath("authorities") + File.separator + "iupac_ids.db")
				.closeOnJvmShutdown().transactionEnable().make();
		this.cache = db.treeMap("collectionName", Serializer.STRING, Serializer.STRING).createOrOpen();
		Unirest.config().verifySsl(false);
	}

	@Override
	public Identity getIdentity(String identityId, String catalog) {

		String value = this.cache.get(identityId);
		if (value != null) {
			return JsonUtils.parseObject(value, AuthorityIdentity.class);
		}

		String original = identityId;
		String standardKey = null;
		String standardName = null;
	
		AuthorityIdentity ret = new AuthorityIdentity();
		if (!isStdKey(identityId)) {
			standardKey = getIdentity(identityId);
			if (standardKey == null) {
				ret.getNotifications().add(new Notification("Identity " + identityId + " is unknown to authority " + ID,
						Level.SEVERE.getName()));
			}
		}
		String officialName = null;
		if (standardKey != null) {
			standardName = getInChl(identityId);
			officialName = getIUPACName(identityId);
			if (officialName == null) {
				ret.getNotifications().add(new Notification("Identity " + identityId + " has no common name in " + ID,
						Level.INFO.getName()));
				officialName = standardName;
			}
			if (standardName == null) {
				ret.getNotifications().add(new Notification("Identity " + identityId + " has has no official IUPAC name",
						Level.SEVERE.getName()));
			}
		}

		ret.setAuthorityName(ID);

		if (standardKey != null && standardName != null) {
			ret.setConceptName(standardKey.toLowerCase().replace('-', '_'));
			ret.setDescription(officialName + " (" + getFormula(identityId) + ")");
			ret.setLabel(original);
			ret.setId(identityId);
		}
		boolean ws = StringUtils.containsWhitespace(original);
		ret.setLocator(ID + (ws ? ":'" : ":") + original + (ws ? "':" : ":"));

		/*
		 * cache also the errors
		 */
		this.cache.put(original, JsonUtils.asString(ret));
		this.db.commit();

		return ret;
	}

	/**
	 * Check for the official ID in XXXXXXXXXXXXXX-YYYYYYYYYY-Z format, with 14, 12
	 * and 1 uppercase characters.
	 * 
	 * @param identityId
	 * @return
	 */
	private boolean isStdKey(String identityId) {
		if (this.pattern == null) {
			this.pattern = Pattern.compile(KEY_PATTERN);
		}
		Matcher matcher = pattern.matcher(identityId);
		return matcher.matches();
	}

	@Override
	public void document(String identityId, String mediaType, OutputStream destination) {
		switch (mediaType) {
		case "text/plain":
			break;
		case "image/png":
			break;
		}
	}

	@Override
	public List<Identity> search(String query, String catalog) {
		return Collections.singletonList(getIdentity(query, null));
	}

	@Override
	public Capabilities getCapabilities() {
		AuthorityReference ref = new AuthorityReference();
		ref.setSearchable(true);
		ref.setFuzzy(true);
		ref.getDocumentationFormats().add("text/plain");
		ref.getDocumentationFormats().add("image/png");
		ref.setName(ID);
		return ref;
	}

	public List<String> getNames(String identity) {
		List<String> ret = new ArrayList<>();
		HttpResponse<String> response = Unirest.get(RESOLVER_URL + "/" + UrlEscape.escapeurl(identity) + "/" + "names")
				.asString();
		if (response.isSuccess()) {
			for (String ss : response.getBody().split("\\r?\\n")) {
				ret.add(ss);
			}
		}
		return ret;
	}

	public String getFormula(String identity) {
		HttpResponse<String> response = Unirest
				.get(RESOLVER_URL + "/" + UrlEscape.escapeurl(identity) + "/" + "formula").asString();
		if (response.isSuccess()) {
			return response.getBody();
		}
		return null;
	}

	public String getInChl(String identity) {
		HttpResponse<String> response = Unirest
				.get(RESOLVER_URL + "/" + UrlEscape.escapeurl(identity) + "/" + "stdinchi").asString();
		if (response.isSuccess()) {
			String ret = response.getBody();
			if (ret.contains("=")) {
				int idx = ret.indexOf('=');
				ret = ret.substring(idx + 1);
			}
			return ret;
		}
		return null;
	}

	public String getIUPACName(String identity) {
		HttpResponse<String> response = Unirest
				.get(RESOLVER_URL + "/" + UrlEscape.escapeurl(identity) + "/" + "iupac_name").asString();
		if (response.isSuccess()) {
			return response.getBody();
		}
		return null;
	}

	public String getIdentity(String query) {

		String ret = null;
		String url = RESOLVER_URL + "/" + UrlEscape.escapeurl(query) + "/" + "stdinchikey";
		HttpResponse<String> response = Unirest.get(url).asString();
		if (response.isSuccess()) {
			ret = response.getBody();
			if (ret.contains("=")) {
				int idx = ret.indexOf('=');
				ret = ret.substring(idx + 1);
			}
		}

		return ret;
	}

	public static void main(String[] args) {
		IUPACAuthority auth = new IUPACAuthority();
		for (String c : new String[] { "XLYOFNOQVPJJNP-UHFFFAOYSA-N", "Water", "Aspirin", "Cyanometaacrylate",
				"Polyacrylamide" }) {
			System.out.println("Looking up " + c);
			System.out.println("  Standard key: " + (auth.isStdKey(c) ? "YES" : "NO"));
			String identity = auth.getIdentity(c);
			if (identity == null) {
				System.out.println("  Identity: UNKNOWN");
			} else {
				System.out.println("  Identity: " + identity);
				System.out.println("  IUPAC name: " + auth.getIUPACName(identity));
				System.out.println("  InChl: " + auth.getInChl(identity));
				System.out.println("  Brute formula: " + auth.getFormula(identity));
				System.out.println("  Names:");
				for (String s : auth.getNames(identity)) {
					System.out.println("    " + s);
				}
			}
		}
	}

	@Override
	public boolean setup(Map<String, String> options) {
		if ("true".equals(options.get("clearcache"))) {
			try {
				cache.clear();
				db.commit();
			} catch (Throwable t) {
				return false;
			}
		}
		return true;
	}

}
