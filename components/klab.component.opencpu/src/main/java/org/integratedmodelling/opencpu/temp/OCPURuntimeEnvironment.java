package org.integratedmodelling.opencpu.temp;

import org.apache.commons.text.StringSubstitutor;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.logging.Logger;

public class OCPURuntimeEnvironment {

    public enum HTTPMessage {
        POST, GET
    }

    /**
     * Defines an HTTP status code enumeration class.
     */
    public enum HTTPStatusCode {
        // Define HTTP status codes:
        OK(200), CREATED(201), FOUND(302), BAD_REQUEST(400), BAD_GATEWAY(502), SERVICE_UNAVAILABLE(503);

        /**
         * Defines the numeric code of the HTTP status code.
         */
        private int code;

        /**
         * Constructor consuming the numeric HTTP status code.
         * 
         * @param code
         */
        private HTTPStatusCode(int code) {
            this.code = code;
        }

        /**
         * Returns the numeric HTTP Status code.
         * 
         * @return
         */
        public int getCode() {
            return this.code;
        }
    }

    // Define HTTP Endpoint URL schemas:
    private static final String GlobalPackagePath = "/library/${package}";
    private static final String UserPackagePath = "/user/${user}/library/${package}";
    private static final String GithubPackagePath = "/github/${user}/${repo}";
    private static final String SessionOutputPath = "/tmp/${key}";
    // private static final String PackageInfoPath = "/info";
    private static final String PackageObjectPath = "/R";
    private static final String PackageDataPath = "/data";

    private boolean isOnline = true;
    
    /**
     * Defines the base URI of the OpenCPU service.
     */
    private URI baseURI;

    /**
     * Defines the class logger.
     */
    private static Logger logger = Logger.getLogger(OCPURuntimeEnvironment.class.getName());

    /**
     * Default constructor for the {@link OCPURuntimeEnvironment}.
     *
     * @throws URISyntaxException URISyntaxException thrown.
     */
    public OCPURuntimeEnvironment() throws URISyntaxException {
        this.setBaseURI(new URI("http", null, "localhost", 9999, "/ocpu", null, null));
    }

    public OCPURuntimeEnvironment(String scheme, String host, int port, String rootPath) throws URISyntaxException {
        this.setBaseURI(new URI(scheme, null, host, port, rootPath, null, null));
    }

    public OCPURuntimeEnvironment(String scheme, String userInfo, String host, int port, String rootPath)
            throws URISyntaxException {
        this.setBaseURI(new URI(scheme, userInfo, host, port, rootPath, null, null));
    }

    public OCPURuntimeEnvironment(URI baseURI) {
        this.setBaseURI(baseURI);
    }

    public OCPURuntimeEnvironment(String baseURI) {
        try {
            this.setBaseURI(new URI(baseURI));
        } catch (URISyntaxException e) {
            isOnline = false;
        }
    }

    /**
     * Returns the base URI of the OpenCPU service.
     *
     * @return The base URI of the OpenCPU service.
     */
    public URI getBaseURI() {
        return this.baseURI;
    }

    /**
     * Sets the base URI of the OpenCPU service.
     *
     * @param baseURI The base URI of the OpenCPU service.
     */
    public void setBaseURI(URI baseURI) {
        this.baseURI = baseURI;
    }

    enum InputType {
        R, JSON
    }

    private HttpRequest buildJSONPOSTRequest(String URL, String input) {
        return HttpRequest.newBuilder().uri(URI.create(URL)).header("Content-type", "application/json")
                .POST(BodyPublishers.ofString(input)).build();
    }

    private HttpRequest buildPOSTRequest(String URL, String input) {
        return HttpRequest.newBuilder().uri(URI.create(URL)).header("Content-type", "application/x-www-form-urlencoded")
                .POST(BodyPublishers.ofString(input)).build();
    }

    private HttpRequest buildGETRequest(String URL) {
        return HttpRequest.newBuilder().uri(URI.create(URL)).header("Content-type", "application/json").GET().build();
    }

    private String ocpuResponseHandler(HttpResponse<String> response, HTTPMessage msg) throws OCPUException {
        
        // Get the response status code:
        int statusCode = response.statusCode();
        int successCode;
        if (msg == HTTPMessage.GET) {
            successCode = HTTPStatusCode.OK.getCode();
        } else if (msg == HTTPMessage.POST) {
            successCode = HTTPStatusCode.CREATED.getCode();
        } else {
            logger.info("Type of message does not exist.");
            throw new OCPUException("Type of message does not exist.");
        }

        // Check the response status code and act accordingly. Note that we don't expect a 201 code
        // as JSON I/O RPC is producing plain 200 with the result in the response body.
        if (statusCode == successCode) {
            logger.info("Response received successfully.");

            // Get the HTTP entity:
            String body = response.body();

            // Check the HTTP entity:
            if (body == null) {
                logger.severe("No content received from OpenCPU Server.");
                throw new OCPUException("No content received from OpenCPU Server.");
            }

            // Return the key of the session storing the results:
            return body;

        } else if (statusCode == HTTPStatusCode.FOUND.getCode()) {
            logger.severe("Response is redirected.");
            throw new OCPUException("Response is redirected.");
        } else if (statusCode == HTTPStatusCode.BAD_REQUEST.getCode()) {
            // Declare the error message:
            String message = "";
            // Get the HTTP entity:
            String body = response.body();

            // Check the HTTP entity:
            if (body == null) {
                logger.severe("No content received from OpenCPU Server.");
                message = "No content received from OpenCPU Server.";
            } else {
                // Return the results:
                try {
                    message = body;
                } catch (RuntimeException e) {
                    logger.severe("Cannot read the output from OpenCPU server response.");
                    throw new OCPUException("Cannot read the output from OpenCPU server response.", e);
                }
            }
            logger.severe("Bad request: " + message);
            throw new OCPUException("Bad Request: " + message);
        } else if (statusCode == HTTPStatusCode.BAD_GATEWAY.getCode()) {
            logger.severe("OpenCPU Server is not responsive (" + statusCode + ").");
            throw new OCPUException("OpenCPU Server is not responsive (" + statusCode + ").");
        } else if (statusCode == HTTPStatusCode.SERVICE_UNAVAILABLE.getCode()) {
            logger.severe("OpenCPU Server is not responsive (" + statusCode + ").");
            throw new OCPUException("OpenCPU Server is not responsive (" + statusCode + ").");
        }

        // TODO: Change the structure of this code as it reads like a VB code.
        logger.severe("Unrecognized response from the OpenCPU Server: " + statusCode);
        throw new OCPUException("Unrecognized response from the OpenCPU Server: " + statusCode);

    }

    /**
     * Calls a remote procedure (function) from the R Global Package which consumes and produces
     * JSON objects.
     *
     * @param rpackage The name of the package of the function to be called.
     * @param function The name of the function to be called.
     * @param input The input as a JSON string.
     * @return The output as a JSON string.
     * @throws OCPUException if problems with the HTTP response
     */
    public String rGlobalPackageFunctionCall(String rpackage, String function, String input, InputType it) throws OCPUException {
        // Construct the path for the HTTP POST request
        String strURL = this.getBaseURI().toString() + new StringSubstitutor(new HashMap<String, String>(){
            {
                put("package", rpackage);
                put("function", function);
            }
        }).replace(GlobalPackagePath + PackageObjectPath + "/${function}/");
        // Build the HTTP POST request
        HttpRequest request;
        if (it == InputType.R) {
            request = buildPOSTRequest(strURL, input);
        } else if (it == InputType.JSON) {
            request = buildJSONPOSTRequest(strURL, input);
        } else {
            throw new OCPUException("Input type incorrect.");
        }

        // Send the HTTP POST request
        logger.info("Sending synchronous request to OpenCPU server (" + strURL + ").");
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException ie) {
            throw new OCPUException(ie);
        }
        // Return the response body or handle exceptions
        return ocpuResponseHandler(response, HTTPMessage.POST);
    }

    /**
     * Calls a remote procedure (function) from a package hosted in GitHub which consumes and
     * produces JSON objects.
     *
     * @param user The GitHub user that hosts the package.
     * @param repo The name of the package of the function to be called.
     * @param function The name of the function to be called.
     * @param input The input as a JSON string.
     * @return The output as a JSON string.
     * @throws OCPUException if problems with the HTTP response
     */
    public String githubPackageFunctionCall(String user, String repo, String function, String input) throws OCPUException {
        // Construct the path for the HTTP POST request
        String strURL = this.getBaseURI().toString() + new StringSubstitutor(new HashMap<String, String>(){
            {
                put("user", user);
                put("repo", repo);
                put("function", function);
            }
        }).replace(GithubPackagePath + PackageObjectPath + "/${function}/json?digits=6");
        // Build the HTTP POST request
        HttpRequest request = buildPOSTRequest(strURL, input);
        // Send the HTTP POST request
        logger.info("Sending synchronous request to OpenCPU server (" + strURL + ").");
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException ie) {
            throw new OCPUException(ie);
        }
        // Return the response body or handle exceptions
        return ocpuResponseHandler(response, HTTPMessage.POST);
    }

    /**
     * Calls a remote procedure (function) from a local package which consumes and produces JSON
     * objects.
     *
     * @param user The name of the local user.
     * @param rpackage The name of the package of the function to be called.
     * @param function The name of the function to be called.
     * @param input The input as a JSON string.
     * @return The output as a JSON string.
     * @throws OCPUException if problems with the HTTP response
     */
    public String localPackageFunctionCall(String user, String rpackage, String function, String input, InputType it)
            throws OCPUException {
        // Construct the path for the HTTP POST request
        String strURL = this.getBaseURI().toString() + new StringSubstitutor(new HashMap<String, String>(){
            {
                put("user", user);
                put("package", rpackage);
                put("function", function);
            }
        }).replace(UserPackagePath + PackageObjectPath + "/${function}/json?digits=6");
        // Build the HTTP POST request
        // Build the HTTP POST request
        HttpRequest request;
        if (it == InputType.R) {
            request = buildPOSTRequest(strURL, input);
        } else if (it == InputType.JSON) {
            request = buildJSONPOSTRequest(strURL, input);
        } else {
            throw new OCPUException("Input type incorrect.");
        }
        // Send the HTTP POST request
        logger.info("Sending synchronous request to OpenCPU server (" + strURL + ").");
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException ie) {
            throw new OCPUException(ie);
        }
        // Return the response body or handle exceptions
        return ocpuResponseHandler(response, HTTPMessage.POST);
    }

    /**
     * Supposed to return a json string with the R object stored in the specified session
     */
    public String getRObjectFromSession(String body) throws OCPUException {
        String key = getSessionKey(body);
        String strURL = this.getBaseURI().toString() + new StringSubstitutor(new HashMap<String, String>(){
            {
                put("key", key);
            }
        }).replace(SessionOutputPath + PackageObjectPath + "/.val/print");
        // Build the HTTP POST request
        HttpRequest request = buildGETRequest(strURL);
        logger.info("Attempting to retrieve object in (" + strURL + ").");
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException ie) {
            throw new OCPUException(ie);
        }
        // Return the response body or handle exceptions
        return ocpuResponseHandler(response, HTTPMessage.GET);
    }

    public String getSessionKey(String body) {
        return body.substring(body.indexOf("tmp/") + 4, body.indexOf("/R/.val"));
    }

    /*
     * Takes a JSON string input and directly returns the JSON object in the R session without
     * additional GET request CAUTION: openCPU documentation claims that a POST message post-fixed
     * with /json returns status 200 if successful, but it actually returns status 201 as a regular
     * POST message
     */
    public String jsonIORPC(String rpackage, String function, String input) throws OCPUException {
        // Construct the path for the HTTP POST request
        String strURL = this.getBaseURI().toString() + new StringSubstitutor(new HashMap<String, String>(){
            {
                put("package", rpackage);
                put("function", function);
            }
        }).replace(GlobalPackagePath + PackageObjectPath + "/${function}/json");
        // Build the HTTP POST request
        HttpRequest request = buildJSONPOSTRequest(strURL, input);
        // Send the HTTP POST request
        logger.info("Sending synchronous request to OpenCPU server (" + strURL + ").");
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException ie) {
            throw new OCPUException(ie);
        }
        // Return the response body or handle exceptions
        return ocpuResponseHandler(response, HTTPMessage.POST);
    }
}
