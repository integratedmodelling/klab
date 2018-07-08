package org.integratedmodelling.klab.auth;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.engine.resources.AbstractWorkspace;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.utils.StringUtils;

public class AnonymousEngineCertificate implements ICertificate {

	private String worldview = KlabCertificate.DEFAULT_WORLDVIEW;
	private Collection<String> worldview_repositories = StringUtils
			.splitOnCommas(KlabCertificate.DEFAULT_WORLDVIEW_REPOSITORIES);

	public AnonymousEngineCertificate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IWorldview getWorldview() {
		return new Worldview(worldview, Configuration.INSTANCE.getDataPath("worldview"), worldview_repositories,
				((AbstractWorkspace) Resources.INSTANCE.getLocalWorkspace()).getProjectLocations()
						.toArray(new File[((AbstractWorkspace) Resources.INSTANCE.getLocalWorkspace())
								.getProjectLocations().size()]));
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
		case KEY_SERVER:
			return "http://127.0.0.1:8284/klab";
		case KEY_CERTIFICATE_LEVEL:
			return ICertificate.Level.ANONYMOUS.name();
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
