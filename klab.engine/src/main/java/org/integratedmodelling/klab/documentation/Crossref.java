package org.integratedmodelling.klab.documentation;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.DocumentationNode.Reference;
import org.integratedmodelling.klab.rest.DocumentationNode.Reference.Author;
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
            DOI = "https://data.crossref.org/" + DOI;
        }

        try {
            HttpResponse<JsonNode> response = Unirest.get(DOI).accept("application/vnd.citationstyles.csl+json")
                    .connectTimeout(30000).socketTimeout(60000).asJson();
            if (response.isSuccess()) {
                JsonNode data = response.getBody();
                return data.getObject().toMap();
            }
        } catch (Throwable t) {
            // screw it and return null
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
            ret.setIssue(result.containsKey("issue") ? Integer.parseInt(result.get("issue").toString()) : -1);
            ret.setContainer(result.containsKey("container-title") ? result.get("container-title").toString() : null);
            ret.setContainerShort(
                    result.containsKey("container-title-short") ? result.get("container-title-short").toString() : null);
            ret.setLink(result.containsKey("URL") ? result.get("URL").toString() : null);
            ret.setDoi(DOI);
            ret.setPageRange(result.containsKey("page") ? result.get("page").toString() : null);
            ret.setIssn(result.containsKey("ISSN") ? result.get("ISSN").toString() : null);
            ret.setVolume(result.containsKey("volume") ? Integer.parseInt(result.get("volume").toString()) : -1);
            ret.setCitedBy(result.get("is-referenced-by-count") != null
                    ? Integer.parseInt(result.get("is-referenced-by-count").toString())
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
            if (result.get("indexed") instanceof Map) {
                Object timestamp = ((Map<?, ?>) result.get("indexed")).get("timestamp");
                if (timestamp instanceof Number) {
                    ret.setDate(new Date(((Number) timestamp).longValue()));
                }
            }

            ret.getCitations().put("default", makeCitation(ret, "default"));

            cache.put(DOI, JsonUtils.asString(ret));
            db.commit();
            return ret;
        }

        return null;
    }

    private String makeCitation(Reference reference, String style) {

        StringBuffer ret = new StringBuffer(1024);
        for (Author a : reference.getAuthors()) {
            ret.append((ret.length() == 0 ? "" : "; ") + a.getName());
        }
        ret.append(". " + (reference.getDate().getYear() + 1900) + ". ");
        ret.append(reference.getTitle() + (reference.getTitle().endsWith(".") ? "" : "."));
        ret.append(" " + reference.getContainer());
        if (reference.getVolume() >= 0) {
            ret.append(" " + reference.getVolume());
        }
        if (reference.getIssue() >= 0) {
            ret.append("(" + reference.getIssue() + ")");
        }
        if (reference.getPageRange() != null) {
            ret.append(": " + reference.getPageRange());
        }
        if (reference.getPublisher() != null) {
            ret.append(". " + reference.getPublisher());
        }
        ret.append(".");
        return ret.toString();
    }

    public static void main(String[] args) {
        Reference ref = INSTANCE.resolve("10.1371/journal.pone.0091001");
        if (ref != null) {
            System.out.println(JsonUtils.printAsJson(ref));
        }
    }

}
