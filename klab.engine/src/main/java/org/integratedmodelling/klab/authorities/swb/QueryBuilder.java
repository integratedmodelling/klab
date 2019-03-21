package org.integratedmodelling.klab.authorities.swb;

/**
 * Contains all queries and endpoint info to fill the vocabulary at initialization.
 * 
 * @author Ferd
 *
 */
public class QueryBuilder {

    /**
     * AllegroGraph endpoint to submit the queries below. Use POST, send Accept =
     * application/json header and put query in 'query' field. Will return something like
     * 
     * <pre>
     * 
     * { "names": [ "uri", "literalForm" ], "values": [ [
     * "<http://soilmaps.entecra.it/kos/c_801>", "\"Bathi\"@en" ], [
     * "<http://soilmaps.entecra.it/kos/c_802>", "\"Cumuli\"@en" ] ... ] } </pre>
     */
    public final static String sparqlEndpoint   = "http://202.45.139.84:10035/catalogs/fao/repositories/agINFRA";

    /**
     * SPARQL to get all RSG terms (first-level taxonomy)
     */
    public static final String QUERY_RSG        = "SELECT ?uri ?literalForm\n" +
            "WHERE { ?uri  <http://www.w3.org/2004/02/skos/core#broader> <http://soilmaps.entecra.it/kos/c_755> .\n"
            +
            "	?uri <http://www.w3.org/2008/05/skos-xl#prefLabel> ?xlabel .\n" +
            "      ?xlabel <http://www.w3.org/2008/05/skos-xl#literalForm> ?literalForm .\n" +
            "  }" +
            "";

    /**
     * SPARQL to get all second-level qualifiers
     */
    public static final String QUERY_QUALIFIERS = "SELECT ?uri ?literalForm\n" +
            "WHERE { ?uri  <http://www.w3.org/2004/02/skos/core#broader> <http://soilmaps.entecra.it/kos/c_506> .\n"
            +
            " ?uri <http://www.w3.org/2008/05/skos-xl#prefLabel> ?xlabel .\n" +
            "      ?xlabel <http://www.w3.org/2008/05/skos-xl#literalForm> ?literalForm .       \n" +
            "      FILTER (lang(?literalForm ) =\"en\") .\n" +
            "  }";

    /**
     * SPARQL to get all specifiers
     */
    public static final String QUERY_SPECIFIERS = "SELECT ?uri ?literalForm\n" +
            "WHERE { ?uri  <http://www.w3.org/2004/02/skos/core#broader> <http://soilmaps.entecra.it/kos/c_799> .\n"
            +
            "	?uri <http://www.w3.org/2008/05/skos-xl#prefLabel> ?xlabel .\n" +
            "      ?xlabel <http://www.w3.org/2008/05/skos-xl#literalForm> ?literalForm .	      \n" +
            "      FILTER (lang(?literalForm ) =\"en\") .\n" +
            "  }";

}
