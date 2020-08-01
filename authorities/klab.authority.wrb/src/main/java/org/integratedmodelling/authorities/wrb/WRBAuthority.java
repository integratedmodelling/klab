package org.integratedmodelling.authorities.wrb;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.authorities.wrb.model.Vocabulary;
import org.integratedmodelling.authorities.wrb.parser.Parser;
import org.integratedmodelling.authorities.wrb.utils.WRBUtils;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.AuthorityReference;

// TODO add catalogs for the different versions
@Authority(id = WRBAuthority.ID, description = "", catalogs = {}, version = Version.CURRENT)
public class WRBAuthority implements IAuthority {

	public static final String ID = "WRB";
	public static final String VOCABULARY_2014 = "2014";
	public static final String VOCABULARY_2006 = "2006";
	public static final String VOCABULARY_1998 = "1998";

	public Map<String, Vocabulary> vocabularies = new HashMap<>();

	public WRBAuthority() {
		WRBVocabulary.useClasspathResources();
		vocabularies.put(VOCABULARY_2014, WRBUtils.read2014());
	}

	@Override
	public Identity getIdentity(String identityId, String vocabulary) {

		if (vocabulary == null) {
			vocabulary = VOCABULARY_2014;
		}

		Vocabulary voc = vocabularies.get(vocabulary);
		if (voc == null) {
			throw new KlabValidationException("WRB authority: cannot find vocabulary '" + vocabulary + "'");
		}

		Parser parser = new Parser(voc);
		return parser.parse(identityId).getAuthorityIdentity();

//		/*
//		 * TODO catalog
//		 */
//		WRBIdentity parsed = WRBParser.parse(identityId);
//		if (parsed != null) {
//			return parsed.getConceptDefinition();
//		}
	}

	@Override
	public void document(String identityId, String mediaType, OutputStream destination) {
		// no doc formats
	}

	@Override
	public List<Identity> search(String query, String catalog) {
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
