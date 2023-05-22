package org.integratedmodelling.klab.openeo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.auth.Authorization;
import org.integratedmodelling.klab.exceptions.KlabRemoteException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

/**
 * Helper methods for OpenEO communication.
 * 
 * @author Ferd
 *
 */
public class OpenEO {

    private Authorization authorization;
    private String endpoint;

    public static class ProcessNode {

        private String process_id;
        private String namespace;
        private String description;
        private boolean result;
        private Map<String, Object> arguments;

        public String getProcess_id() {
            return process_id;
        }

        public void setProcess_id(String process_id) {
            this.process_id = process_id;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public Map<String, Object> getArguments() {
            return arguments;
        }

        public void setArguments(Map<String, Object> arguments) {
            this.arguments = arguments;
        }
    }

    public static class Schema {

        public enum Type {
            number;
        }

        private Type type = Type.number;

        public Type getType() {
            return type;
        }
        public void setType(Type type) {
            this.type = type;
        }

    }

    public static class Parameter {

        private String name;
        private String description;
        private Schema schema;
        private boolean optional;
        private boolean deprecated;
        private boolean experimental;
        private boolean isDefault;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public Schema getSchema() {
            return schema;
        }
        public void setSchema(Schema schema) {
            this.schema = schema;
        }
        public boolean isOptional() {
            return optional;
        }
        public void setOptional(boolean optional) {
            this.optional = optional;
        }
        public boolean isDeprecated() {
            return deprecated;
        }
        public void setDeprecated(boolean deprecated) {
            this.deprecated = deprecated;
        }
        public boolean isExperimental() {
            return experimental;
        }
        public void setExperimental(boolean experimental) {
            this.experimental = experimental;
        }

        @JsonProperty("default")
        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

    }

    public static class ReturnValue {

        private String description;
        private Schema schema;

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public Schema getSchema() {
            return schema;
        }
        public void setSchema(Schema schema) {
            this.schema = schema;
        }

    }

    public static class Builder {

        String id = NameGenerator.shortUUID();
        String description;
        String summary;
        Map<String, ProcessNode> nodes = new LinkedHashMap<>();

        public static class NodeBuilder {

            ProcessNode node = new ProcessNode();

        }

        public NodeBuilder node(String nodeName) {

            NodeBuilder ret = new NodeBuilder();

            return ret;
        }

        public Process build() {
            Process ret = new Process();
            // TODO
            return ret;
        }
    }

    /*
     * In addition to processing steps, it should have the fields for parameters, title, etc.
     */
    public static class Process {

        private String id;
        private String summary;
        private String description;
        private List<Parameter> parameters = new ArrayList<>();
        private Map<String, ProcessNode> process_graph = new LinkedHashMap<>();
        private ReturnValue returns;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getSummary() {
            return summary;
        }
        public void setSummary(String summary) {
            this.summary = summary;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public List<Parameter> getParameters() {
            return parameters;
        }
        public void setParameters(List<Parameter> parameters) {
            this.parameters = parameters;
        }
        public Map<String, ProcessNode> getProcess_graph() {
            return process_graph;
        }
        public void setProcess_graph(Map<String, ProcessNode> process_graph) {
            this.process_graph = process_graph;
        }
        public ReturnValue getReturns() {
            return returns;
        }
        public void setReturns(ReturnValue returns) {
            this.returns = returns;
        }

        public static Builder builder() {
            return new Builder();
        }

    }

    public OpenEO(String endpoint) {
        this.endpoint = endpoint;
        ExternalAuthenticationCredentials credentials = Authentication.INSTANCE.getCredentials(endpoint);
        if (credentials != null) {
            this.authorization = new Authorization(credentials);
        }
    }

    /**
     * Create a job and return its ID. If a non-null string is returned, it can be passed to other
     * job-related functions. The job is NOT started, just created on the back end.
     * 
     * @param process
     * @param title
     * @param description
     * @param plan
     * @param budget
     * @return
     */
    public String createJob(Process process) {
        return null;
    }

    /**
     * <em>Synchronous</em> job executor that blocks until the job is finished. Only to be used with
     * jobs that are known to be small. Returns the results as a JSON map, which will need to be
     * interpreted at the user end. The result may contain errors from the OpenEO service. Throws an
     * exception only in case of remote server error.
     * 
     * @param process the process to be sent
     * @param budget put 0 if no budget should be sent
     * @param plan null is accepted
     */
    @SuppressWarnings("unchecked")
    public <T> T runJob(Process process, int budget, String plan, Class<T> resultClass) {

        Map<String, Object> request = new LinkedHashMap<>();
        
        request.put("process", process);
        request.put("plan", plan);
        request.put("budget", budget <= 0 ? null : budget);
        
        String dioboia = JsonUtils.printAsJson(request);
        
        HttpResponse<String> response = Unirest.post(endpoint + "/result").contentType("application/json")
                .header("Authorization", authorization.getAuthorization()).body(request).asString();

        if (response.isSuccess()) {
            return (T)Utils.convertValue(response.getBody().trim(), Utils.getArtifactType(resultClass));
        }
        
        throw new KlabRemoteException("OpenEO runJob returned error code " + response.getStatus());
    }

    /**
     * <em>Asynchronous</em> job executor. Must be passed a valid job ID from a previous call to
     * {@link #createJob(Process)}, which guarantees validation. Returns immediately and calls the
     * error handler or the result handler depending on the outcome when the process has finished.
     * Polls the server for status at the established polling interval.
     * 
     * @param jobId
     * @param resultHandler
     * @param errorHandler
     */
    public void startJob(String jobId, Consumer<Map<String, Object>> resultHandler, Consumer<String> errorHandler) {

    }

    /**
     * Validate a process and submit it if valid, passing control to callbacks. Return false if
     * process validation fails. Validation is on the back-end and synchronous, execution is
     * asynchronous.
     * 
     * @param process
     * @param resultHandler
     * @param errorHandler
     * @return
     */
    public boolean submit(Process process, Consumer<Map<String, Object>> resultHandler, Consumer<String> errorHandler) {

        /*
         * for the time being this is synchronous
         */
        if (!validateProcess(process, null)) {
            return false;
        }

        return false;
    }

    public boolean validateProcess(Process process, Consumer<String> errorHandler) {

        HttpResponse<JsonNode> response = Unirest.post(endpoint + "/validation").contentType("application/json")
                .header("Authorization", authorization.getAuthorization()).body(JsonUtils.printAsJson(process)).asJson();

        if (response.isSuccess()) {
            if (response.getBody().getObject().has("errors")) {
                JSONArray errors = response.getBody().getObject().getJSONArray("errors");
                if (errorHandler != null) {
                    for (Object error : errors) {
                        errorHandler.accept(((JSONObject) error).getString("message"));
                    }
                }
                return errors.length() == 0;
            }
        }

        return false;
    }

}
