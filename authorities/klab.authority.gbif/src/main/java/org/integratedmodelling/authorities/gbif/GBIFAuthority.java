package org.integratedmodelling.authorities.gbif;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.StringUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.web.client.RestTemplate;

@Authority(id = GBIFAuthority.ID, description = GBIFAuthority.DESCRIPTION, catalogs = { "KINGDOM", "PHYLUM", "CLASS",
		"ORDER", "FAMILY", "GENUS", "SPECIES" }, version = Version.CURRENT)
public class GBIFAuthority implements IAuthority {

	static final int pageSize = 100;
	static final public String ID = "GBIF";
	static final public String DESCRIPTION = "Global Biodiversity Information Facility (GBIF)\n\n"
			+ "GBIF provides stable identities for taxonomic entities. The available catalogs "
			+ " authority provides k.LAB identities at different taxonomic ranks.\n\n"
			+ "For more details, see the GBIF project at http://gbif.org";

	public static final String KINGDOM_RANK = "kingdom";
	public static final String PHYLUM_RANK = "phlyum";
	public static final String CLASS_RANK = "class";
	public static final String ORDER_RANK = "order";
	public static final String FAMILY_RANK = "family";
	public static final String GENUS_RANK = "genus";
	public static final String SPECIES_RANK = "species";

//	protected String rank;
	private RestTemplate client = new RestTemplate();

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

	public GBIFAuthority() {
		this.db = DBMaker.fileDB(Configuration.INSTANCE.getDataPath("authorities") + File.separator + "gbif_ids.db")
				.transactionEnable().closeOnJvmShutdown().make();
		this.cache = db.treeMap("collectionName", Serializer.STRING, Serializer.STRING).createOrOpen();
	}

	@Override
	public Identity getIdentity(String identityId, String rank) {

		Identity source = null;
		// search cache first
		String cached = cache.get(identityId);
		if (cached != null) {
			source = JsonUtils.parseObject(cached, AuthorityIdentity.class);
		} else {

			if (rank != null) {
				rank = rank.toLowerCase();
				int rankIndex = -1;
				for (int i = 0; i < ranks.size(); i++) {
					if (rank.equals(ranks.get(i))) {
						rankIndex = i;
						break;
					}
				}
				if (rankIndex < 0) {
					throw new KlabValidationException("GBIF authority: invalid catalog " + ranks);
				}
			}

			// if not in there, use network
			try {
				source = parseResult(client.getForObject(getDescribeURL(identityId), Map.class));
				// TODO check that the catalog is what we expect
				cache.put(identityId, JsonUtils.asString(source));
				db.commit();
			} catch (Throwable t) {
				// just return null
			}
		}

		if (rank != null) {
			/*
			 * TODO verify that the catalog is what we passed.
			 */
		}

		return source;
	}

	public Identity parseResult(Map<?, ?> o) {

		if (o == null) {
			return null;
		}

		AuthorityIdentity result = new AuthorityIdentity();

		Map<String, String> desc = new HashMap<>();

		String key = getString(o, "key");
		desc.put(KINGDOM_RANK, getString(o, "kingdom"));
		desc.put(PHYLUM_RANK, getString(o, "phylum"));
		desc.put(CLASS_RANK, getString(o, "class"));
		desc.put(ORDER_RANK, getString(o, "order"));
		desc.put(FAMILY_RANK, getString(o, "family"));

		String parent = getString(o, "parent");
		String parentKey = getString(o, "parentKey");
//		String kingdomKey = getString(o, "kingdomKey");
//		String classKey = getString(o, "classKey");
//		String phylumKey = getString(o, "phylumKey");
//		String orderKey = getString(o, "orderKey");
//		String familyKey = getString(o, "familyKey");
		String authorship = getString(o, "authorship");
		String canonicalName = getString(o, "canonicalName");

		String parents = null;
		String rank = null;
		if (parent != null) {
			for (int i = ranks.size() - 1; i >= 0; i--) {
				if (parent.equals(desc.get(ranks.get(i)))) {
					rank = ranks.get(i + 1);
					parents = desc.get(ranks.get(i));
				} else if (parents != null && desc.get(ranks.get(i)) != null) {
					parents += ", " + desc.get(ranks.get(i));
				}
			}
		}

		result.setAuthorityName(ID);
		result.setId(key);
		result.setLabel(canonicalName);
		result.setDescription((rank == null ? "" : (StringUtils.capitalize(rank) + ": ")) + canonicalName
				+ ((authorship == null || authorship.isEmpty()) ? "" : (" (" + authorship + ")"))
				+ (parents == null ? "" : (". " + parents + ".")));
		result.setConceptName("gbif" + key);
		result.setLocator(ID + ":" + key);
		if (parentKey != null) {
			result.setParentIds(Collections.singletonList(parentKey));
		}
		return result;
	}

	@Override
	public List<Identity> search(String query, String catalog) {
		List<Identity> ret = new ArrayList<>();
		Object[] results = client.getForObject(getSearchURL(query, catalog, 0), Object[].class);
		for (Object o : results) {
			if (o instanceof Map<?, ?> && ((Map<?, ?>) o).containsKey("key")) {
				Identity r = parseResult((Map<?, ?>) o);
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

	private URI getSearchURL(String query, String catalog, int page) {
		String ret = "http://api.gbif.org/v1/species/suggest?q=" + Escape.forURL(query) + "&limit=100&rank=" + catalog;
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
	public void document(String identityId, String mediaType, OutputStream destination) {
		// TODO Auto-generated method stub

	}

	@Override
	public Capabilities getCapabilities() {
		AuthorityReference ref = new AuthorityReference();
		ref.setSearchable(true);
		ref.setDescription(DESCRIPTION);
		ref.getDocumentationFormats().add("text/plain");
		ref.getSubAuthorities().add(new Pair<>("", "Any rank"));
		for (String rank : ranks) {
			ref.getSubAuthorities().add(new Pair<>(rank.toUpperCase(), StringUtil.capitalize(rank) + " rank"));
		}
		ref.setName(ID);
		return ref;
	}

	@Override
	public boolean setup(Map<String, String> options) {
		if ("true".equals(options.get("clearcache"))) {
			try {
				cache.clear();
				db.commit();
			} catch (Throwable t) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getName() {
		return ID;
	}

	@Override
	public ICodelist getCodelist() {
		return null;
	}
}