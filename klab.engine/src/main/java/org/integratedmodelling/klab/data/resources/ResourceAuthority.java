package org.integratedmodelling.klab.data.resources;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.engine.indexing.GenericRAMIndexer;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.utils.CastUtils;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * Actuator for any authority based on a resource's codelist. Resources that
 * publish one or more codelists are intercepted on startup and one of these is
 * exposes automatically.
 * 
 * @author Ferd
 *
 */
public class ResourceAuthority implements IAuthority {

	ICodelist codelist;
	IResource resource;
	IConcept rootIdentity;
	GenericRAMIndexer<AuthorityIdentity> index = new GenericRAMIndexer<AuthorityIdentity>(AuthorityIdentity.class) {

		@Override
		protected IMetadata getResourceMetadata(AuthorityIdentity resource) {
			return null;
		}

		@Override
		protected String getResourceDescription(AuthorityIdentity resource) {
			return resource.getDescription();
		}

		@Override
		protected String getResourceLabel(AuthorityIdentity resource) {
			return resource.getLabel();
		}

		@Override
		protected String getResourceId(AuthorityIdentity resource) {
			return resource.getId();
		}
	};

	public ResourceAuthority(ICodelist codelist, IResource resource) {
		this.codelist = codelist;
		this.resource = resource;
		indexCodelist();
	}

	private void indexCodelist() {

		String conceptPrefix = "";
		String[] cl = codelist.getAuthorityId().split("\\.");
		String authId = codelist.getAuthorityId();
		String subAuthId = "";
		if (cl.length > 1) {
			authId = cl[0];
			conceptPrefix = StringUtils.join(Arrays.copyOfRange(cl, 1, cl.length), "_");
			subAuthId = conceptPrefix;
		}
		if (!conceptPrefix.isEmpty()) {
			conceptPrefix += "__";
		}
		for (String desc : ((Codelist) codelist).getReference().getCodeDescriptions().keySet()) {

			String description = ((Codelist) codelist).getReference().getCodeDescriptions().get(desc);
			AuthorityIdentity identity = new AuthorityIdentity();
			identity.setId(desc);
			identity.setLabel(description);
			identity.setDescription(description);
			identity.setAuthorityName(authId);
			identity.setConceptName(sanitize(conceptPrefix + desc));
			identity.setLocator(authId + (subAuthId.isEmpty() ? "" : ("." + subAuthId)) + ":" + desc);

			index.index(identity);
		}
	}

	private String sanitize(String id2) {
		return codelist.getAuthorityId() + "_" + id2.replace('.', '_').replace('-', '_');
	}

	@Override
	public Identity getIdentity(String identityId, String catalog) {
		return index.getResource(identityId);
	}

	@Override
	public Capabilities getCapabilities() {

		AuthorityReference ret = new AuthorityReference(codelist.getAuthorityId());
		ret.setSearchable(true);
		ret.setFuzzy(true);
		ret.setWorldview(codelist.getWorldview());
		ret.setDescription(codelist.getDescription());

		return ret;
	}

	@Override
	public void document(String identityId, String mediaType, OutputStream destination) {
	}

	@Override
	public List<Identity> search(String query, String catalog) {
		return new CastUtils<AuthorityIdentity, Identity>().cast(index.query(query));
	}

	@Override
	public boolean setup(Map<String, String> options) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getName() {
		return codelist.getAuthorityId();
	}

	@Override
	public ICodelist getCodelist() {
		return codelist;
	}

}
