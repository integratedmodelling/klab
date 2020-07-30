package org.integratedmodelling.authorities.iupac;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.JsonUtils;
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
		this.db = DBMaker
				.fileDB(Configuration.INSTANCE.getDataPath("authorities") + File.separator + "iupac.db")
				.make();
		this.cache = db.treeMap("collectionName", Serializer.STRING, Serializer.STRING).createOrOpen();
	}
	
	@Override
	public Identity getIdentity(String identityId, String catalog) {
		
		String value = this.cache.get(identityId);
		if (value != null) {
			return JsonUtils.parseObject(value, AuthorityIdentity.class);
		}
		
		String original = identityId;
		AuthorityIdentity ret = new AuthorityIdentity();
		if (!isStdKey(identityId)) {
			identityId = getIdentity(identityId);
		}
		String label = getIUPACName(identityId);
		if (label == null) {
			ret.setError("Identity " + identityId + " is unknown to authority " + ID);
		}
		
		ret.setConceptName(identityId.toLowerCase().replace('-', '_'));
		ret.setDescription(label + " (" + getFormula(identityId) + ")");
		ret.setLabel(label);
		ret.setId(identityId);
		
		/*
		 * cache also the errors
		 */
		this.cache.put(original, JsonUtils.asString(ret));
		
		return ret;
	}

	/**
	 * Check for the official ID in XXXXXXXXXXXXXX-YYYYYYYYYY-Z format, with 
	 * 14, 12 and 1 uppercase characters.
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
		ref.getDocumentationFormats().add("text/plain");
		ref.getDocumentationFormats().add("image/png");
		ref.setName(ID);
		return ref;
	}

	public List<String> getNames(String identity) {
		List<String> ret = new ArrayList<>();
		HttpResponse<String> response = Unirest.get(RESOLVER_URL + "/" + identity + "/" + "names").asString();
		if (response.isSuccess()) {
			for (String ss : response.getBody().split("\\r?\\n")) {
				ret.add(ss);
			}
		}
		return ret;
	}

	public String getFormula(String identity) {
		HttpResponse<String> response = Unirest.get(RESOLVER_URL + "/" + Escape.forURL(identity) + "/" + "formula")
				.asString();
		if (response.isSuccess()) {
			return response.getBody();
		}
		return null;
	}
	
	public String getInChl(String identity) {
		HttpResponse<String> response = Unirest.get(RESOLVER_URL + "/" + Escape.forURL(identity) + "/" + "stdinchi")
				.asString();
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
		HttpResponse<String> response = Unirest.get(RESOLVER_URL + "/" + Escape.forURL(identity) + "/" + "iupac_name")
				.asString();
		if (response.isSuccess()) {
			return response.getBody();
		}
		return null;
	}

	public String getIdentity(String query) {

		String ret = null;
		HttpResponse<String> response = Unirest.get(RESOLVER_URL + "/" + Escape.forURL(query) + "/" + "stdinchikey")
				.asString();
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
		for (String c : new String[] {"XLYOFNOQVPJJNP-UHFFFAOYSA-N", "Water", "Aspirin",  "Cyanometaacrylate", "Polyacrylamide" }) {
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

}
