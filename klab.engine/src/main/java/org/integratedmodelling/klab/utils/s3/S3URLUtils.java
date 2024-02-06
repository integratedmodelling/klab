package org.integratedmodelling.klab.utils.s3;

public class S3URLUtils {
    final public static String AWS_ENDPOINT = "https://s3.amazonaws.com";

    /**
     * Checks if the given url is a valid S3 endpoint
     * @param url to be analyzed
     * @return true if it is a valid S3 endpoint
     */
    public static boolean isS3Endpoint(String url) {
        return url.startsWith("s3:");
    }
}