package org.integratedmodelling.klab.authorities.swb;
//package org.integratedmodelling.klab.authorities.swb.vocabulary;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.integratedmodelling.klab.Configuration;
//import org.integratedmodelling.klab.api.knowledge.IMetadata;
//import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
//import org.integratedmodelling.klab.utils.ClassUtils;
//import org.integratedmodelling.klab.utils.Pair;
//import org.integratedmodelling.klab.utils.StringUtils;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.FormHttpMessageConverter;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
///**
// * Vocabulary for all terms; we don't hardcode anything but fill the hashes in at
// * synchronization using the queries and SPARQL endpoint in {@link QueryBuilder}.
// * 
// * If k.LAB is initialized at synchronization, will maintain the wrb ontology where all
// * the concepts are created. Call {@link #setCoreTypes(String, String, String))} before
// * creating the object to establish the base types for qualifiers, specifiers and
// * reference soil groups. If not called, the base attributes and identities will be used.
// * 
// * JSON outputs are cached for offline use after the first sync.
// * 
// */
//public class WRBVocabulary {
//
//    private static WRBVocabulary      _this;
//
//    // private IOntology ontology;
//
//    public Map<String, URI>           RSGTerms                 = new HashMap<>();
//    public Map<String, URI>           SpecifierTerms           = new HashMap<>();
//    public Map<String, URI>           QualifierTerms           = new HashMap<>();
//
//    private String                    invariantRSGPrefix;
//    private String                    invariantSpecifierPrefix;
//    private String                    invariantQualifierPrefix;
//
//    static ObjectMapper               objectMapper             = new ObjectMapper();
//
//    public static String              RSG_BASE_IDENTITY        = "ReferenceSoilGroup";
//    public static String              QUALIFIER_BASE_ATTRIBUTE = "WRBPrefixQualifier";
//    public static String              SPECIFIER_BASE_ATTRIBUTE = "WRBSpecifier";
//
//    /**
//     * anything that needs to change compared to the "official" id can be added here with
//     * its corrected form.
//     */
//    public static Map<String, String> corrections              = new HashMap<>();
//
//    // if these are defined before the first use, read the vocabulary from the classpath.
//    private static boolean            useClasspathResources;
//
//    static {
//        // BS currently in allegrograph - eliminate when we control the vocabulary
//        corrections.put("Eutrisilic", "Eutrosilic");
//        corrections.put("Hydragic", "Hydragric");
//        corrections.put("Puf?c", "Puffic");
//    }
//
//    /**
//     * Call before first use to load vocabulary from classpath
//     */
//    public static void useClasspathResources() {
//        useClasspathResources = true;
//    }
//
//    private WRBVocabulary() {
//        synchronize();
//    }
//
//    /**
//     * Create descriptors
//     * @param definition
//     */
//    public void defineOntology(Authority definition) {
//
//        definition.getInitialConcepts().add(NS.CORE_IDENTITY_TRAIT + "," + RSG_BASE_IDENTITY);
//        definition.getInitialConcepts().add(NS.ATTRIBUTE_TRAIT + "," + QUALIFIER_BASE_ATTRIBUTE);
//        definition.getInitialConcepts().add(NS.ATTRIBUTE_TRAIT + "," + SPECIFIER_BASE_ATTRIBUTE);
//
//        for (String s : RSGTerms.keySet()) {
//            definition.getInitialConcepts()
//                    .add(RSG_BASE_IDENTITY + "," + s + "," + IMetadata.DC_SOURCE + "=" + RSGTerms.get(s));
//        }
//        for (String s : SpecifierTerms.keySet()) {
//            definition.getInitialConcepts().add(SPECIFIER_BASE_ATTRIBUTE + "," + s + "," + IMetadata.DC_SOURCE
//                    + "=" + SpecifierTerms.get(s));
//        }
//        for (String s : QualifierTerms.keySet()) {
//            definition.getInitialConcepts().add(QUALIFIER_BASE_ATTRIBUTE + "," + s + "," + IMetadata.DC_SOURCE
//                    + "=" + QualifierTerms.get(s));
//        }
//    }
//
//    public void setCoreTypes(String baseRSG, String baseQualifier, String baseSpecifier) {
//        RSG_BASE_IDENTITY = baseRSG;
//        QUALIFIER_BASE_ATTRIBUTE = baseQualifier;
//        SPECIFIER_BASE_ATTRIBUTE = baseSpecifier;
//    }
//
//    public static WRBVocabulary get() {
//        if (_this == null) {
//            _this = new WRBVocabulary();
//        }
//        return _this;
//    }
//
//    /**
//     * Send a SPARQL query and parse the JSON results according to AllegroGraph's REST
//     * API. Cache any successful query and use the cached result, if present, to handle
//     * any http error before throwing an exception.
//     * 
//     * @param query the SPARQL query
//     * @param id an ID to use as reference in building the cache
//     * @return
//     */
//    public static List<Pair<String, URI>> query(String query, String queryId) {
//
//        List<Pair<String, URI>> ret = new ArrayList<>();
//
//        RestTemplate template = new RestTemplate();
//        template.setMessageConverters(Arrays.asList(new HttpMessageConverter[] {
//                new MappingJackson2HttpMessageConverter(),
//                new FormHttpMessageConverter(),
//                new StringHttpMessageConverter() }));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("query", query);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
//        Object values = null;
//        File cached = new File(System.getProperty("user.home") + File.separator
//                + Configuration.KLAB_RELATIVE_WORK_PATH + File.separator
//                + "wrb");
//
//        cached.mkdirs();
//        cached = new File(cached + File.separator + queryId + ".json");
//
//        try {
//
//            Map<?, ?> response = template.postForObject(QueryBuilder.sparqlEndpoint, request, Map.class);
//
//            values = response.get("values");
//
//            if (values instanceof List) {
//                try {
//                    objectMapper.writeValue(cached, response);
//                } catch (Throwable ex) {
//                    KLAB.error("error writing cached response for " + queryId);
//                }
//            }
//
//        } catch (HttpClientErrorException e) {
//
//            if (cached.exists()) {
//
//                KLAB.warn("REST service not available: using cached vocabulary");
//                try {
//                    Map<?, ?> response = objectMapper.readValue(cached, Map.class);
//                    values = response.get("values");
//                } catch (Throwable ex) {
//                    throw new KlabRuntimeException("error reading cached data for " + queryId);
//                }
//            } else {
//                throw new KlabRuntimeException(e.getResponseBodyAsString());
//            }
//        }
//
//        if (values instanceof List) {
//
//            for (Object o : ((List<?>) values)) {
//                if (o instanceof List && ((List<?>) o).size() > 1) {
//
//                    String uri = ((List<?>) o).get(0).toString();
//                    String id = ((List<?>) o).get(1).toString();
//
//                    try {
//                        URI u = new URI(cleanUri(uri));
//                        ret.add(new Pair<>(cleanId(id), u));
//                    } catch (URISyntaxException e) {
//                        throw new KlabRuntimeException("invalid URI from vocabulary: " + uri);
//                    }
//                }
//            }
//        }
//
//        return ret;
//    }
//
//    private List<Pair<String, URI>> readFromCache(String resource) {
//
//        List<Pair<String, URI>> ret = new ArrayList<>();
//
//        try (InputStream input = new FileInputStream(new File(KLAB.CONFIG.getDataPath("wrb") + File.separator
//                + resource))) {
//
//            Map<?, ?> response = objectMapper.readValue(input, Map.class);
//            Object values = response.get("values");
//
//            if (values instanceof List) {
//
//                for (Object o : ((List<?>) values)) {
//                    if (o instanceof List && ((List<?>) o).size() > 1) {
//
//                        String uri = ((List<?>) o).get(0).toString();
//                        String id = ((List<?>) o).get(1).toString();
//
//                        try {
//                            URI u = new URI(cleanUri(uri));
//                            ret.add(new Pair<>(cleanId(id), u));
//                        } catch (URISyntaxException e) {
//                            throw new KlabRuntimeException("invalid URI from vocabulary: " + uri);
//                        }
//                    }
//                }
//            }
//
//        } catch (Throwable ex) {
//            throw new KlabRuntimeException("error reading cached data for " + resource);
//        }
//
//        return ret;
//    }
//
//    private static String cleanId(String id) {
//        int atpos = id.indexOf('@');
//        if (atpos >= 0) {
//            id = id.substring(0, atpos);
//        }
//        if (id.startsWith("\"")) {
//            id = id.substring(1);
//        }
//        if (id.endsWith("\"")) {
//            id = StringUtils.chomp(id, "\"");
//        }
//        return id;
//    }
//
//    private static String cleanUri(String uri) {
//        if (uri.startsWith("<")) {
//            uri = uri.substring(1);
//        }
//        if (uri.endsWith(">")) {
//            uri = StringUtils.chomp(uri, ">");
//        }
//        return uri;
//    }
//
//    private void synchronize() {
//
//        if (useClasspathResources) {
//            try {
//                ClassUtils.extract("wrb", KLAB.CONFIG.getDataPath("wrb"));
//            } catch (Exception e) {
//                throw new KlabRuntimeException(e);
//            }
//        }
//
//        List<Pair<String, URI>> qualifiers = useClasspathResources ? readFromCache("qualifiers.json")
//                : query(QueryBuilder.QUERY_QUALIFIERS, "qualifiers");
//        List<Pair<String, URI>> specifiers = useClasspathResources ? readFromCache("specifiers.json")
//                : query(QueryBuilder.QUERY_SPECIFIERS, "specifiers");
//        List<Pair<String, URI>> rsg = useClasspathResources ? readFromCache("rsg.json")
//                : query(QueryBuilder.QUERY_RSG, "rsg");
//
//        
//        for (Pair<String, URI> p : rsg) {
//            RSGTerms.put(correct(StringUtils.capitalize(p.getFirst().toLowerCase())), p.getSecond());
//        }
//        for (Pair<String, URI> p : qualifiers) {
//            QualifierTerms.put(correct(StringUtils.capitalize(p.getFirst().toLowerCase())), p.getSecond());
//        }
//        for (Pair<String, URI> p : specifiers) {
//            SpecifierTerms.put(correct(StringUtils.capitalize(p.getFirst().toLowerCase())), p.getSecond());
//        }
//
//        /*
//         * compute the invariant part of the URIs to subtract from the IDs when building
//         * short and stable identifiers.
//         */
//        this.invariantRSGPrefix = StringUtils.computeInvariantPrefix(RSGTerms.values());
//        this.invariantSpecifierPrefix = StringUtils.computeInvariantPrefix(SpecifierTerms.values());
//        this.invariantQualifierPrefix = StringUtils.computeInvariantPrefix(QualifierTerms.values());
//
//    }
//
//    private String correct(String string) {
//        return corrections.containsKey(string) ? corrections.get(string) : string;
//    }
//
//    public String getQualifierShortId(String specifier) {
//        String uri = QualifierTerms.get(StringUtils.capitalize(specifier.toLowerCase())).toString();
//        if (uri != null) {
//            return "q" + uri.substring(invariantQualifierPrefix.length());
//        }
//        return null;
//    }
//
//    public String getSpecifierShortId(String specifier) {
//        String uri = SpecifierTerms.get(StringUtils.capitalize(specifier.toLowerCase())).toString();
//        if (uri != null) {
//            return "s" + uri.substring(invariantSpecifierPrefix.length());
//        }
//        return null;
//    }
//
//    public String getRSGShortId(String specifier) {
//        String uri = RSGTerms.get(StringUtils.capitalize(specifier.toLowerCase())).toString();
//        if (uri != null) {
//            return "r" + uri.substring(invariantRSGPrefix.length());
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//
//        System.out.println("RSG\n");
//        for (String s : WRBVocabulary.get().RSGTerms.keySet()) {
//            System.out.println("   " + s + " [" + WRBVocabulary.get().RSGTerms.get(s) + "]\t"
//                    + WRBVocabulary.get().getRSGShortId(s));
//        }
//        System.out.println("\nQUALIFIERS\n");
//        for (String s : WRBVocabulary.get().QualifierTerms.keySet()) {
//            System.out.println("   " + s + " [" + WRBVocabulary.get().QualifierTerms.get(s) + "]\t"
//                    + WRBVocabulary.get().getQualifierShortId(s));
//        }
//        System.out.println("\nSPECIFIERS\n");
//        for (String s : WRBVocabulary.get().SpecifierTerms.keySet()) {
//            System.out.println("   " + s + " [" + WRBVocabulary.get().SpecifierTerms.get(s) + "]\t"
//                    + WRBVocabulary.get().getSpecifierShortId(s));
//        }
//
//        System.out.println("\nRSG common prefix: " + WRBVocabulary.get().invariantRSGPrefix);
//        System.out.println("Qualifier common prefix: " + WRBVocabulary.get().invariantQualifierPrefix);
//        System.out.println("Specifier common prefix: " + WRBVocabulary.get().invariantSpecifierPrefix);
//    }
//
//    public boolean isQualifier(String string) {
//        return QualifierTerms.containsKey(StringUtils.capitalize(string.toLowerCase()));
//    }
//
//    public boolean isSpecifier(String string) {
//        return SpecifierTerms.containsKey(StringUtils.capitalize(string.toLowerCase()));
//    }
//
//    public boolean isRSG(String string) {
//        return RSGTerms.containsKey(StringUtils.capitalize(string.toLowerCase()));
//    }
//
//}
