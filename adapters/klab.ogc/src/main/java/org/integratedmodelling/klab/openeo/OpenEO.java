package org.integratedmodelling.klab.openeo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.auth.Authorization;
import org.integratedmodelling.klab.utils.JsonUtils;

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

    public static class Process extends LinkedHashMap<String, ProcessNode> {
        private static final long serialVersionUID = -7734440696956959927L;
    }

    public OpenEO(String endpoint, Authorization authorization) {
        this.endpoint = endpoint;
        this.authorization = authorization;
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
    public String createJob(Process process, String title, String description, String plan, int budget) {
        return null;
    }

    public void startJob(String jobId, Consumer<Map<String, Object>> resultHandler, Consumer<String> errorHandler) {

    }

    /**
     * Validate a process and submit it if valid. Return false if not.
     * 
     * @param process
     * @param resultHandler
     * @param errorHandler
     * @return
     */
    public boolean submit(Process process, Consumer<Map<String, Object>> resultHandler, Consumer<String> errorHandler) {
        return false;
    }

    public boolean validateProcess(Process process) {
        return false;
    }

    public static void main(String[] args) {

        Authorization authorization = new Authorization(Authentication.INSTANCE.getCredentials("https://openeo.vito.be"));
        
        OpenEO openEO = new OpenEO("https://openeo.vito.be", authorization);
        String processDefinition = "{\r\n" + "    \"zumba\": {\r\n" + "        \"process_id\": \"add\",\r\n"
                + "        \"arguments\": {\"x\": 3, \"y\": 5},\r\n" + "        \"result\": true\r\n" + "    }\r\n" + "}";
        Process process = JsonUtils.parseObject(processDefinition, Process.class);
        
        if (openEO.validateProcess(process)) {
            String job = openEO.createJob(process, "Putanada", "Niente descrizione", "no plan", 0);
            if (job != null) {
                openEO.startJob(job, (result) -> {
                    System.out.println(result);
                }, (error) -> {
                    System.err.println(error);
                });
            }
        }
        System.out.println("Hola");
    }

}
