package org.integratedmodelling.klab.auth;

import java.util.Collection;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.utils.StringUtil;

public class AnonymousEngineCertificate implements ICertificate {

	private String worldview = KlabCertificate.DEFAULT_WORLDVIEW;
	private Collection<String> worldview_repositories = StringUtil
			.splitOnCommas(KlabCertificate.DEFAULT_WORLDVIEW_REPOSITORIES);

	public AnonymousEngineCertificate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getWorldview() {
		return worldview;
	}
	
	@Override
	public Collection<String> getWorldviewRepositories() {
		return worldview_repositories;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public String getInvalidityCause() {
		return null;
	}

	@Override
	public String getProperty(String property) {
		switch (property) {
		case KEY_PARTNER_HUB:
			return "http://127.0.0.1:8284/klab";
		case KEY_CERTIFICATE_LEVEL:
			return ICertificate.Level.ANONYMOUS.name();
		case KEY_USERNAME:
			return "anonymous";
		}
		return null;
	}

	@Override
	public Type getType() {
		return Type.ENGINE;
	}

	@Override
	public Level getLevel() {
		return Level.ANONYMOUS;
	}

}
