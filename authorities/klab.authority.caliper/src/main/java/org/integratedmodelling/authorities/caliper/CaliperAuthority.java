package org.integratedmodelling.authorities.caliper;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.rest.AuthorityReference;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import unirest.shaded.com.google.gson.JsonObject;

@Authority(id = CaliperAuthority.ID, description = CaliperAuthority.DESCRIPTION, catalogs = {"ISIC", "ICC10",
        "ICC"/*
              * , "WCACROPS"
              */, "M49"/* , "FPCD" */, "SDGEO", "FOODEX2", "CPC20", "CPC21"/* , "CPC21AG" */, "CPC21FERT", "FCL",
        "HS"/*
             * , "WRB"
             */}, version = Version.CURRENT)
public class CaliperAuthority implements IAuthority {

    public static final String ID = "CALIPER";
    public static final String DESCRIPTION = "";
    private static final String SCHEME = "{SCHEME}";
    private static final String QUERY_STRING = "{QUERY_STRING}";

    private static final String DESCRIPTION_QUERY = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
            + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\r\n" + "SELECT ?code ?label_en ?concept WHERE {\r\n"
            + "  ?concept rdf:type skos:Concept . \r\n" + "  ?concept skos:inScheme <{SCHEME}> .\r\n"
            + "  ?concept skos:prefLabel ?label_en . FILTER(contains(lcase(str(?label_en)), '{QUERY_STRING}')) .\r\n"
            + "  ?concept skos:notation ?code .\r\n" + "} order by ?code";

    private static final String SPARQL_ENDPOINT = "https://stats-class.fao.uniroma2.it/AllVoc_Sparql/";
    private static final Map<String, String> CALIPER_SCHEMES = new HashMap<>();

    static {
        CALIPER_SCHEMES.put("ISIC", "http://stats-class.fao.uniroma2.it/ISIC/rev4/scheme");
        CALIPER_SCHEMES.put("ICC10", "http://stats-class.fao.uniroma2.it/ICC/v1.0/scheme");
        CALIPER_SCHEMES.put("ICC", "http://stats-class.fao.uniroma2.it/ICC/v1.1/scheme");
        // CALIPER_SCHEMES.put("WCACROPS", "http://stats-class.fao.uniroma2.it/ICC/v1.0/scheme");
        CALIPER_SCHEMES.put("M49", "http://stats-class.fao.uniroma2.it/geo/M49");
        // CALIPER_SCHEMES.put("FPCD", "http://stats-class.fao.uniroma2.it/ICC/v1.0/scheme");
        CALIPER_SCHEMES.put("SDGEO", "http://stats-class.fao.uniroma2.it/geo/M49/SDG-groups");
        CALIPER_SCHEMES.put("FOODEX2", "http://stats-class.fao.uniroma2.it/foodex2/all");
        CALIPER_SCHEMES.put("CPC20", "http://stats-class.fao.uniroma2.it/CPC/v2.0/scheme");
        CALIPER_SCHEMES.put("CPC21", "http://stats-class.fao.uniroma2.it/CPC/v2.1/core");
        // CALIPER_SCHEMES.put("CPC21AG", "http://stats-class.fao.uniroma2.it/ICC/v1.0/scheme");
        CALIPER_SCHEMES.put("CPC21FERT", "http://stats-class.fao.uniroma2.it/CPC/v2.1/fert");
        CALIPER_SCHEMES.put("FCL", "http://stats-class.fao.uniroma2.it/FCL/v2019/scheme");
        CALIPER_SCHEMES.put("HS", "http://stats-class.fao.uniroma2.it/HS/fao_mapping_targets/scheme");

    }

    @Override
    public Identity getIdentity(String identityId, String catalog) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Capabilities getCapabilities() {
        AuthorityReference ret = new AuthorityReference();
        ret.setSearchable(true);
        ret.setFuzzy(true);
        return ret;
    }
    @Override
    public void document(String identityId, String mediaType, OutputStream destination) {
        // TODO Auto-generated method stub
    }

    @Override
    public List<Identity> search(String query, String catalog) {

        String q = DESCRIPTION_QUERY.replace(SCHEME, CALIPER_SCHEMES.get(catalog)).replace(QUERY_STRING, query);
        HttpResponse<JsonNode> response = Unirest.post(SPARQL_ENDPOINT).accept("application/sparql-results+json")
                .contentType("application/sparql-query").body(q).asJson();

        if (response.isSuccess()) {
            try {
                JSONObject result = response.getBody().getObject();
                for (Object zoz : result.getJSONObject("results").getJSONArray("bindings")) {

                    // TODO make an identity
                    
                    JSONObject res = (JSONObject) zoz;
                    String code = res.getJSONObject("code").getString("value");
                    String name = res.getJSONObject("label_en").getString("value");
                    String uri = res.getJSONObject("concept").getString("value");

                    // TODO internal try/catch, add error message to identity
                    
                }
            } catch (Throwable t) {
                // TODO monitor the error, return nothing
                throw new KlabInternalErrorException(t);
            }
        }

        return null;
    }

    @Override
    public boolean setup(Map<String, String> options) {
        // TODO Auto-generated method stub
        return true;
    }

    public static void main(String[] args) {

        String query = DESCRIPTION_QUERY.replace(SCHEME, CALIPER_SCHEMES.get("ICC")).replace(QUERY_STRING, "rice");
        HttpResponse<JsonNode> response = Unirest.post(SPARQL_ENDPOINT).accept("application/sparql-results+json")
                .contentType("application/sparql-query").body(query).asJson();

        if (response.isSuccess()) {
            try {
                JSONObject result = response.getBody().getObject();
                for (Object zoz : result.getJSONObject("results").getJSONArray("bindings")) {
                    JSONObject res = (JSONObject) zoz;
                    String code = res.getJSONObject("code").getString("value");
                    String name = res.getJSONObject("label_en").getString("value");
                    String uri = res.getJSONObject("concept").getString("value");

                    System.out.println("Got " + code + ", " + name + ", " + uri);
                }
            } catch (Throwable t) {
                throw new KlabInternalErrorException(t);
            }
        }

    }

}
