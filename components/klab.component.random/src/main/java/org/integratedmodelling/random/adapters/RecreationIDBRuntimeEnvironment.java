package org.integratedmodelling.random.adapters;

import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.logging.Logger;

import org.integratedmodelling.klab.exceptions.KlabException;

public class RecreationIDBRuntimeEnvironment {

    public enum HTTPStatusCode {
        OK(200), Unauthorized(401);

        private final int code;

        HTTPStatusCode(int code){
            this.code = code;
        }

        final public int getCode() {
            return this.code;
        }

        static public HTTPStatusCode getStatus(int code){
            HTTPStatusCode status = null;
            switch (code){
                case 200: status = HTTPStatusCode.OK; break;
                case 400: status = HTTPStatusCode.Unauthorized; break;
            }
            return status;
        }
    }

    public enum RecreationIDBRequestType{

        // TODO: think of best ways to include all the requests.
        RecAreas, PermitEntrances, Facilities, Activities;

        static public String getURLSchema(RecreationIDBRequestType requestType) throws KlabException {
            String urlSchema;
            switch (requestType) {
                case RecAreas:
                    urlSchema = "/recareas?";
                    break;
                case PermitEntrances:
                    urlSchema = "/permitentrances?";
                    break;
                case Facilities:
                    urlSchema = "/facilities?";
                    break;
                case Activities:
                    urlSchema = "/activities?";
                    break;
                default:
                    throw new KlabException("Request type does not exist: " + requestType +".");
            }
            return urlSchema;
        }
    }

    private boolean isOnline = true;

    private URI baseURI;



    private static Logger logger = Logger.getLogger(RecreationIDBRuntimeEnvironment.class.getName());

    public RecreationIDBRuntimeEnvironment() throws URISyntaxException {
        this.setBaseURI(new URI("https://ridb.recreation.gov/api/v1"));
    }

    public RecreationIDBRuntimeEnvironment(String baseURI) {
        try {
            this.setBaseURI(new URI(baseURI));
        } catch (URISyntaxException e) {
            isOnline = false;
        }
    }

    public boolean isOnline(){
        return isOnline;
    }

    public URI getBaseURI() {
        return this.baseURI;
    }
    public void setBaseURI(URI baseURI) {
        this.baseURI = baseURI;
    }

    private GetRequest buildRequest(String URL, String input, String apiKey) {
        return Unirest.get(URL)
                .header("accept", "application/json")
                .header("apikey", apiKey);
    }

    private String recreationIDBResponseHandler(HttpResponse<String> response) throws KlabException {

        // Get the response status code:
        int statusCode = response.getStatus();
        HTTPStatusCode status = HTTPStatusCode.getStatus(statusCode);

        // Check the response status code and act accordingly. Note that we don't expect a 201 code
        // as JSON I/O RPC is producing plain 200 with the result in the response body.
        if (status == HTTPStatusCode.OK) {
            logger.info("Request to Recreation Information Data Base was successful.");

            // Get the HTTP entity:
            String body = response.getBody();

            // Check the HTTP entity:
            if (body == null) {
                logger.severe("No content received from the Recreation Information Data Base.");
                throw new KlabException("No content received from the Recreation Information Data Base.");
            }

            return body;

        } else {

            if (Objects.requireNonNull(status) == HTTPStatusCode.Unauthorized) {
                logger.severe(" Unauthorized access. Request failed.");
            } else {
                logger.severe("Request failed with code: " + statusCode);
                throw new KlabException("Request failed with code: " + statusCode);
            }

            String message;
            String body = response.getBody();
            if (body == null) {
                logger.severe("No content received from the Recreation Information Data Base.");
                message = "No content received from the Recreation Information Data Base.";
            } else {
                try {
                    message = body;
                } catch (RuntimeException e) {
                    logger.severe("Cannot read the output from the Recreation Information Data Base response.");
                    throw new KlabException("Cannot read the output from the Recreation Information Data Base response.", e);
                }
            }
            logger.severe("Bad request: " + message);
            throw new KlabException("Bad Request: " + message);
        }
    }

    public String recreationIDBSendRequest(String input, RecreationIDBRequestType requestType, String apiKey) throws KlabException{

        String url = this.getBaseURI().toString() + RecreationIDBRequestType.getURLSchema(requestType);
        GetRequest request = buildRequest(url, input, apiKey);

        logger.info("Sending synchronous request to the Recreation Information Data Base server (" + url + ").");

        HttpResponse<String> response;
        response = request.asString();

        return recreationIDBResponseHandler(response);
    }

}
