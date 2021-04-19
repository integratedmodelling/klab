package org.integratedmodelling.klab.documentation;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.DocumentationNode.Reference;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

/**
 * Interface to Crossref.org citation service and resolution of DOI
 * 
 * @author Ferd
 *
 */
public enum Crossref {

    INSTANCE;

    static DB db;
    static Map<String, String> cache;

    public Map<?, ?> resolveDOI(String DOI) {
        if (!DOI.startsWith("http")) {
            DOI = "https://doi.org/" + DOI;
        }
        HttpResponse<JsonNode> response = Unirest.get(DOI).header("Accept", "application/json").asJson();
        if (response.isSuccess()) {
            JsonNode data = response.getBody();
            return data.getObject().toMap();
        }
        return null;
    }

    public DocumentationNode.Reference resolve(String DOI) {

        if (db == null) {
            File dpath = Configuration.INSTANCE.getDataPath("doi");
            dpath.mkdirs();
            db = DBMaker.fileDB(new File(dpath + File.separator + "refs.dat")).closeOnJvmShutdown().transactionEnable().make();
            cache = db.treeMap("references", Serializer.STRING, Serializer.STRING).createOrOpen();
        }

        String ref = cache.get(DOI);
        if (ref != null) {
            return JsonUtils.parseObject(ref, DocumentationNode.Reference.class);
        }

        Map<?, ?> result = resolveDOI(DOI);
        
        if (result != null) {
            DocumentationNode.Reference ret = new DocumentationNode.Reference();
            ret.setPublisher(result.containsKey("publisher") ? result.get("publisher").toString() : null);
            ret.setTitle(result.containsKey("title") ? result.get("title").toString() : null);
            ret.setIssue(result.containsKey("issue") ? ((Number) result.get("publisher")).intValue() : -1);
            ret.setContainer(result.containsKey("container") ? result.get("container").toString() : null);
            ret.setContainerShort(result.containsKey("container-title-short") ? result.get("container-title-short").toString() : null);
            ret.setLink(result.containsKey("URL") ? result.get("URL").toString() : null);
            ret.setDoi(DOI);
            ret.setPageRange(result.containsKey("page") ? result.get("page").toString() : null);
            ret.setIssn(result.containsKey("ISSN") ? result.get("ISSN").toString() : null);
            ret.setVolume(result.get("volume") instanceof Number ? ((Number) result.get("volume")).intValue() : -1);
            ret.setCitedBy(result.get("is-referenced-by-count") instanceof Number
                    ? ((Number) result.get("is-referenced-by-count")).intValue()
                    : -1);
            ret.setLanguage(result.containsKey("language") ? result.get("language").toString() : null);
            ret.setSource(result.containsKey("source") ? result.get("source").toString() : null);
            ret.setType(result.containsKey("type") ? result.get("type").toString() : null);
            if (result.get("author") instanceof Collection) {
                for (Object author : ((Collection<?>) result.get("author"))) {
                    if (author instanceof Map) {
                        // TODO use the ridiculous textual author order field?
                        Map<?, ?> auth = (Map<?, ?>) author;
                        String name = auth.containsKey("family") ? auth.get("family").toString() : "";
                        if (auth.containsKey("given")) {
                            name += (auth.isEmpty() ? "" : ", ") + auth.get("given").toString();
                        }
                        String affiliation = null;
                        if (auth.containsKey("affiliation")) {
                            // TODO
                        }
                        if (name != null) {
                            DocumentationNode.Reference.Author a = new DocumentationNode.Reference.Author();
                            a.setName(name);
                            a.setAffiliation(affiliation);
                            ret.getAuthors().add(a);
                        }
                    }
                }
            }
            if (result.get("published-print") instanceof Map) {
                Object parts = ((Map<?,?>)result.get("published-print")).get("date-parts");
                System.out.println("" + parts);
            }

            ret.getCitations().put("default", makeCitation(ret, "default"));
            
            cache.put(DOI, JsonUtils.asString(ret));
            db.commit();
            return ret;
        }

        return null;
    }

    private String makeCitation(Reference reference, String style) {
        // TODO Auto-generated method stub
        StringBuffer ret = new StringBuffer(1024);
        return ret.toString();
    }

    public static void main(String[] args) {
        Reference ref = INSTANCE.resolve("10.1126/science.169.3946.635");
        if (ref != null) {
            System.out.println(JsonUtils.printAsJson(ref));
        }
    }

}
