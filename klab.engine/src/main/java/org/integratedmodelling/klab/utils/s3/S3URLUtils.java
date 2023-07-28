package org.integratedmodelling.klab.utils.s3;

public class S3URLUtils {
    final public static String AWS_ENDPOINT = "https://s3.amazonaws.com";

    public static boolean isS3Endpoint(String url) {
        return url.startsWith("s3:");
    }
}