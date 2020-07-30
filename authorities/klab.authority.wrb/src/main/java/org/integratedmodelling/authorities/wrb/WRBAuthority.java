package org.integratedmodelling.authorities.wrb;

import java.io.OutputStream;
import java.util.List;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.AuthorityReference;

// TODO add catalogs for the different versions
@Authority(id = WRBAuthority.ID, description = "", catalogs = { "2006" }, version = Version.CURRENT)
public class WRBAuthority implements IAuthority {

	public static final String ID = "WRB";

	public WRBAuthority() {
		WRBVocabulary.useClasspathResources();
	}

	@Override
	public Identity getIdentity(String identityId, String catalog) {
		/*
		 * TODO catalog
		 */
		WRBIdentity parsed = WRBParser.parse(identityId);
		if (parsed != null) {
			return parsed.getConceptDefinition();
		}
		throw new KlabValidationException("WRB authority could not interpret definition '" + identityId + "'");
	}

	@Override
	public void document(String identityId, String mediaType, OutputStream destination) {
		// no doc formats
	}

	@Override
	public List<Identity> search(String query) {
		// not searchable
		return null;
	}

	@Override
	public Capabilities getCapabilities() {
		AuthorityReference ref = new AuthorityReference();
		ref.setSearchable(false);
		ref.setName(ID);
		return ref;
	}

}
