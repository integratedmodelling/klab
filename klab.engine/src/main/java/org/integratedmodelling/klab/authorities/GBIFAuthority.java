package org.integratedmodelling.klab.authorities;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.Escape;
import org.springframework.web.client.RestTemplate;

//
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.integratedmodelling.api.metadata.ISearchResult;
//import org.integratedmodelling.collections.Path;
//import org.integratedmodelling.common.beans.authority.Authority;
//import org.integratedmodelling.common.beans.authority.AuthorityConcept;
//import org.integratedmodelling.common.beans.authority.AuthorityQueryResponse;
//import org.integratedmodelling.common.data.lists.Escape;
//import org.integratedmodelling.common.vocabulary.NS;
//import org.integratedmodelling.common.vocabulary.authority.BaseAuthority;
//
//import us.monoid.json.JSONArray;
//import us.monoid.json.JSONException;
//import us.monoid.json.JSONObject;
//import us.monoid.web.JSONResource;
//import us.monoid.web.Resty;
//
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
	Map<String, IConcept> cache = new HashMap<>();

	private int rankIndex = -1;

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
	}

	@Override
	public IConcept getIdentity(String identityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSearchable() {
		return true;
	}

	@Override
	public List<IMetadata> search(String query) {
		List<IMetadata> ret = new ArrayList<>();
		Object[] results = client.getForObject(getSearchURL(query, 0), Object[].class);
		for (Object o : results) {
			if (o instanceof Map<?, ?> && ((Map<?, ?>) o).containsKey("key")) {
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
			}
		}
		return ret;
	}
	//
	// static Map<String, String> rankKeywords = new HashMap<>();
	//
	// static final int pageSize = 100;
	//
	// static {
	// rankKeywords.put("GBIF.SPECIES", "SPECIES");
	// rankKeywords.put("GBIF.CLASS", "CLASS");
	// rankKeywords.put("GBIF.PHYLUM", "PHYLUM");
	// rankKeywords.put("GBIF.GENUS", "GENUS");
	// rankKeywords.put("GBIF.ORDER", "ORDER");
	// rankKeywords.put("GBIF.FAMILY", "FAMILY");
	// rankKeywords.put("GBIF.CULTIVAR", "CULTIVAR");
	// rankKeywords.put("GBIF.KINGDOM", "KINGDOM");
	// }
	//
	// public GBIFAuthority(Authority definition) {
	// super(definition);
	// }
	//
	// @Override
	// public ISearchResult<AuthorityConcept> search(String query, String
	// authorityId) {
	//
	// AuthorityQueryResponse ret = new AuthorityQueryResponse();
	//
	// try {
	// // TODO support paged services
	// JSONResource res = new Resty().json(getSearchURL(query, authorityId, -1));
	// JSONArray ares = res.array();
	// for (int i = 0; i < ares.length(); i++) {
	// JSONObject ores = (JSONObject) ares.get(i);
	// if (ores != null) {
	// ret.getMatches().add(parseGBIFResponse(ores, authorityId));
	// }
	// }
	// } catch (Exception e) {
	// ret.setError(e.getMessage());
	// }
	//
	// // TODO support paged services
	// ret.setMoreMatches(false);
	// ret.setPage(-1);
	//
	// return ret;
	// }
	//
	// public static GBIFAuthority newInstance() {
	//
	// Authority ret = new Authority();
	//
	// ret.setName("GBIF");
	// ret.setOverallDescription("<b>Global Biodiversity Information Facility
	// (GBIF)</b>\n\n"
	// + "GBIF provides uniform codes for taxonomic entities such as species. This
	// authority provides "
	// + "access to GBIF's <b>suggest</b> service for the listed categories.\n\n"
	// + "For more details, head over to http://gbif.org");
	//
	// ret.getAuthorities().add("GBIF.SPECIES");
	// ret.getAuthorityDescriptions().add("Provides access to GBIF's species
	// catalog");
	// ret.getAuthorities().add("GBIF.CLASS");
	// ret.getAuthorityDescriptions().add("Provides access to GBIF's taxonomic class
	// catalog");
	// ret.getAuthorities().add("GBIF.PHYLUM");
	// ret.getAuthorityDescriptions().add("Provides access to GBIF's taxonomic
	// phylum catalog");
	// ret.getAuthorities().add("GBIF.GENUS");
	// ret.getAuthorityDescriptions().add("Provides access to GBIF's taxonomic genus
	// catalog");
	// ret.getAuthorities().add("GBIF.ORDER");
	// ret.getAuthorityDescriptions().add("Provides access to GBIF's taxonomic order
	// catalog");
	// ret.getAuthorities().add("GBIF.FAMILY");
	// ret.getAuthorityDescriptions().add("Provides access to GBIF's taxonomic
	// family catalog");
	// ret.getAuthorities().add("GBIF.CULTIVAR");
	// ret.getAuthorityDescriptions().add("Provides access to GBIF's cultivar
	// catalog");
	// ret.getAuthorities().add("GBIF.KINGDOM");
	// ret.getAuthorityDescriptions().add("Provides access to GBIF's kingdom
	// catalog");
	//
	// ret.setOntologyId("gbif");
	// ret.setSearchable(true);
	// ret.setWorldview("im");
	// ret.setVersion("v1");
	//
	// /*
	// * link to the worldview in k.IM
	// */
	// ret.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + ",Species");
	// ret.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + ",Family");
	// ret.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + ",Class");
	// ret.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + ",Phylum");
	// ret.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + ",Genus");
	// ret.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + ",Order");
	// ret.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + ",Cultivar");
	// ret.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + ",Kingdom");
	//
	// /*
	// * annotation properties for resulting concepts
	// */
	// ret.getInitialProperties().add("@familyKey");
	// ret.getInitialProperties().add("@genusKey");
	// ret.getInitialProperties().add("@orderKey");
	// ret.getInitialProperties().add("@classKey");
	// ret.getInitialProperties().add("@phylumKey");
	// ret.getInitialProperties().add("@parentKey");
	// ret.getInitialProperties().add("@kingdomKey");
	// ret.getInitialProperties().add("@family");
	// ret.getInitialProperties().add("@genus");
	// ret.getInitialProperties().add("@order");
	// ret.getInitialProperties().add("@class");
	// ret.getInitialProperties().add("@phylum");
	// ret.getInitialProperties().add("@parent");
	// ret.getInitialProperties().add("@kingdom");
	//
	// /*
	// * base concepts for all authorities
	// */
	// ret.getCoreConcepts().put("GBIF.SPECIES", "Species");
	// ret.getCoreConcepts().put("GBIF.CLASS", "Class");
	// ret.getCoreConcepts().put("GBIF.PHYLUM", "Phylum");
	// ret.getCoreConcepts().put("GBIF.GENUS", "Genus");
	// ret.getCoreConcepts().put("GBIF.ORDER", "Order");
	// ret.getCoreConcepts().put("GBIF.FAMILY", "Family");
	// ret.getCoreConcepts().put("GBIF.CULTIVAR", "Cultivar");
	// ret.getCoreConcepts().put("GBIF.KINGDOM", "Kingdom");
	//
	// return new GBIFAuthority(ret);
	// }
	//
	// @Override
	// public AuthorityConcept getConcept(String authority, String id) {
	//
	// JSONResource res;
	// try {
	// res = new Resty().json(getDescribeURL(id));
	// return parseGBIFResponse(res.object(), authority);
	// } catch (Exception e) {
	// // just return null
	// }
	// return null;
	// }
	//
	// private AuthorityConcept parseGBIFResponse(JSONObject object, String
	// authorityId) {
	//
	// AuthorityConcept ret = new AuthorityConcept();
	//
	// try {
	//
	// String key = object.getString("key");
	// String kingdom = object.optString("kingdom");
	// String phylum = object.optString("phylum");
	// String parent = object.optString("parent");
	// String order = object.optString("order");
	// String family = object.optString("family");
	// String clss = object.optString("class");
	// String kingdomKey = object.optString("kingdomKey");
	// String classKey = object.optString("classKey");
	// String phylumKey = object.optString("phylumKey");
	// String parentKey = object.optString("parentKey");
	// String orderKey = object.optString("orderKey");
	// String familyKey = object.optString("familyKey");
	// String scientificName = object.getString("scientificName");
	// String canonicalName = object.getString("canonicalName");
	//
	// /*
	// * rank must match the authority
	// */
	// String rank = object.getString("rank");
	// if (!rank.equals(Path.getLast(authorityId, '.'))) {
	// ret.setError("authority " + authorityId + " cannot be used with identifier of
	// rank " + rank);
	// }
	//
	// String concept = getBaseIdentity(authorityId) + ",g" + key;
	//
	// if (phylum != null && !phylum.isEmpty()) {
	// concept += ",phylum=" + phylum + ",phylumKey=" + phylumKey;
	// }
	// if (kingdom != null && !kingdom.isEmpty()) {
	// concept += ",kingdom=" + kingdom + ",kingdomKey=" + kingdomKey;
	// }
	// if (order != null && !order.isEmpty()) {
	// concept += ",order=" + order + ",orderKey=" + orderKey;
	// }
	// if (family != null && !family.isEmpty()) {
	// concept += ",family=" + family + ",familyKey=" + familyKey;
	// }
	// if (clss != null && !clss.isEmpty()) {
	// concept += ",class=" + clss + ",classKey=" + classKey;
	// }
	// if (parent != null && !parent.isEmpty()) {
	// concept += ",parent=" + parent + ",parentKey=" + parentKey;
	// }
	//
	// ret.setId("g" + key);
	// ret.setDefinition(key);
	// ret.setLabel(canonicalName);
	// ret.setDescription(scientificName);
	// ret.getConceptDefinition().add(concept);
	//
	// } catch (JSONException e) {
	// return null;
	// }
	// return ret;
	// }

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