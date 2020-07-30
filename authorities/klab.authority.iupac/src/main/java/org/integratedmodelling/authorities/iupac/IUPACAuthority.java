package org.integratedmodelling.authorities.iupac;

import java.io.OutputStream;
import java.util.List;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;

/**
 * TODO use online service from
 * https://cactus.nci.nih.gov/chemical/structure_documentation and implement
 * caching.
 * 
 * @author Ferd
 *
 */
@Authority(id = "IUPAC", description = "", version = Version.CURRENT)
public class IUPACAuthority implements IAuthority {

	@Override
	public Identity getIdentity(String identityId, String catalog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void document(String identityId, String mediaType, OutputStream destination) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Identity> search(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Capabilities getCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

}
