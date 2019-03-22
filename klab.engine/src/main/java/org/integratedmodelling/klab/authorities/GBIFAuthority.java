package org.integratedmodelling.klab.authorities;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.web.client.RestTemplate;

public class GBIFAuthority implements IAuthority {

	static final int pageSize = 100;

	public static final String KINGDOM_RANK = "kingdom";
	public static final String PHYLUM_RANK = "phlyum";
	public static final String CLASS_RANK = "class";
	public static final String ORDER_RANK = "order";
	public static final String FAMILY_RANK = "family";
	public static final String GENUS_RANK = "genus";
	public static final String SPECIES_RANK = "species";

	protected String rank;
	private RestTemplate client = new RestTemplate();
	private int rankIndex = -1;

	DB db = null;
	ConcurrentNavigableMap<String, String> cache = null;

	static protected List<String> ranks = null;

	static {
		ranks = new ArrayList<>();
		ranks.add(KINGDOM_RANK);
		ranks.add(PHYLUM_RANK);
		ranks.add(CLASS_RANK);
		ranks.add(ORDER_RANK);
		ranks.add(FAMILY_RANK);
		ranks.add(GENUS_RANK);
		ranks.add(SPECIES_RANK);
	}

	static public void register() {
		for (String rank : ranks) {
			Authorities.INSTANCE.registerAuthority(new GBIFAuthority(rank));
		}
	}

	private IOntology getOntology() {
		return OWL.INSTANCE.requireOntology("gbif_" + rank, OWL.INTERNAL_ONTOLOGY_PREFIX + "/authority/");
	}

	protected GBIFAuthority(String rank) {
		this.rank = rank;
		for (int i = 0; i < ranks.size(); i++) {
			if (rank.equals(ranks.get(i))) {
				this.rankIndex = i;
				break;
			}
		}
		if (rankIndex < 0) {
			throw new IllegalStateException("GBIF authority initialized with invalid rank");
		}
		this.db = DBMaker
				.fileDB(Configuration.INSTANCE.getDataPath("authorities") + File.separator + "gbif" + rank + ".db")
				.make();
		this.cache = db.treeMap("collectionName", Serializer.STRING, Serializer.STRING).createOrOpen();
	}

	@Override
	public IConcept getIdentity(String identityId) {
		
		IConcept ret = getOntology().getConcept("GBIF" + identityId);
		if (ret == null) {
			IMetadata source = null;
			// search cache first
			String cached = cache.get(identityId);
			if (cached != null) {
				source = parseResult(JsonUtils.parseObject(cached, Map.class));
			} else {
				// if not in there, use network
				source = parseResult(client.getForObject(getDescribeURL(identityId), Map.class));
				cache.put(identityId, JsonUtils.asString(source));
				db.commit();
			}
			
			if (source != null) {
				ret = makeIdentity(source);
			}
		}
		return ret;
	}

	private IConcept makeIdentity(IMetadata source) {
		// TODO Auto-generated method stub
		String conceptId = "GBIF" + source.get(IMetadata.IM_KEY, String.class);
		// TODO call getIdentity() on all the upper levels using the proper authorities and 
		// link them as parents.
		return null;
	}

	@Override
	public boolean isSearchable() {
		return true;
	}

	public IMetadata parseResult(Map<?,?> o) {

		if (o == null) {
			return null;
		}
		
		Metadata result = new Metadata();

		String key = getString(o, "key");
		String kingdom = getString(o, "kingdom");
		String phylum = getString(o, "phylum");
		String parent = getString(o, "parent");
		String order = getString(o, "order");
		String family = getString(o, "family");
		String clss = getString(o, "class");
		String kingdomKey = getString(o, "kingdomKey");
		String classKey = getString(o, "classKey");
		String phylumKey = getString(o, "phylumKey");
		String parentKey = getString(o, "parentKey");
		String orderKey = getString(o, "orderKey");
		String familyKey = getString(o, "familyKey");
		String scientificName = getString(o, "scientificName");
		String canonicalName = getString(o, "canonicalName");

		result.put(IMetadata.IM_KEY, key);
		result.put(IMetadata.DC_LABEL, canonicalName);
		result.put(IMetadata.DC_COMMENT, scientificName);
	
		return result;
	}
	
	@Override
	public List<IMetadata> search(String query) {
		List<IMetadata> ret = new ArrayList<>();
		Object[] results = client.getForObject(getSearchURL(query, 0), Object[].class);
		for (Object o : results) {
			if (o instanceof Map<?, ?> && ((Map<?, ?>) o).containsKey("key")) {
				IMetadata r = parseResult((Map<?,?>)o);
				if (r != null) {
					ret.add(r);
				}
			}
		}
		return ret;
	}

	private String getString(Object o, String string) {
		return ((Map<?, ?>) o).containsKey(string) ? ((Map<?, ?>) o).get(string).toString() : null;
	}

	private URI getSearchURL(String query, int page) {
		String ret = "http://api.gbif.org/v1/species/suggest?q=" + Escape.forURL(query) + "&limit=100&rank=" + rank;
		if (page > 0) {
			ret += "&offset=" + (page * pageSize);
		}
		try {
			return new URI(ret);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	//
	private URI getDescribeURL(String id) {
		try {
			return new URI("http://api.gbif.org/v1/species/" + id);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String getName() {
		return "GBIF." + rank.toUpperCase();
	}

	@Override
	public String getDescription() {
		return "<b>Global Biodiversity Information Facility (GBIF)</b>\n\n"
				+ "GBIF provides stable identities for taxonomic entities. The " + getName()
				+ " authority provides k.LAB identities at the " + rank + " taxonomic rank.\n\n"
				+ "For more details, see the GBIF project at http://gbif.org";
	}
}