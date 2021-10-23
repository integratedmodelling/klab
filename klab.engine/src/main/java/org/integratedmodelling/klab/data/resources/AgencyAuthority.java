package org.integratedmodelling.klab.data.resources;

import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * A meta-authority representing an agency responsible for a number of
 * classifications. Holds as many resource authorities as there are published
 * codelists, and switches to them as needed.
 * 
 * @author Ferd
 *
 */
public class AgencyAuthority implements IAuthority {

	String agency;
	Map<String, IAuthority> authorities = Collections.synchronizedMap(new HashMap<>());

	public AgencyAuthority(String agency) {
		this.agency = agency;
	}

	public void addAuthority(String id, IAuthority authority) {
		this.authorities.put(id, authority);
	}

	@Override
	public Identity getIdentity(String identityId, String catalog) {
		return authorities.get(catalog).getIdentity(identityId, null);
	}

	@Override
	public Capabilities getCapabilities() {
		AuthorityReference ret = new AuthorityReference(this.agency);
		ret.setSearchable(true);
		ret.setFuzzy(true);
		ret.setDescription(getDescription());
		for (String authority : authorities.keySet()) {
			ret.getSubAuthorities()
					.add(new Pair<>(authority, authorities.get(authority).getCapabilities().getDescription()));
		}
		return ret;
	}

	public String getDescription() {
		String ret = "Authority collecting classifications published by the " + agency + " agency.";
		if (!authorities.isEmpty()) {
			ret += " Includes " + StringUtil.join(authorities.keySet(), ", ") + ".";
		}
		return ret;
	}

	@Override
	public void document(String identityId, String mediaType, OutputStream destination) {
	}

	@Override
	public List<Identity> search(String query, String catalog) {
		return authorities.get(catalog).search(query, null);
	}

	@Override
	public boolean setup(Map<String, String> options) {
		return true;
	}

}
