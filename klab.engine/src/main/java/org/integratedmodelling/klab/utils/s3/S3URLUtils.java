package org.integratedmodelling.klab.utils.s3;

import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;

public class S3URLUtils {
    final public static String AWS_ENDPOINT = "s3.amazonaws.com";

    /**
     * Checks if the given url is a valid S3 endpoint
     * @param url to be analyzed
     * @return true if it is a valid S3 endpoint
     */
    public static boolean isS3Endpoint(String url) {
        return url.startsWith("s3://");
    }

    /**
     * Translates an S3 URL into to HTTP. It does not take the region into account.  
     * Example: "s3://bucket/object" -> "https://bucket.s3.amazonaws.com/object"
     * @param s3url URL for S3 protocol
     * @return The equivalent URL for an HTTP endpoint
     */
    public static String convertS3URLToHTTP(String s3url) {
        if (!isS3Endpoint(s3url)) {
            throw new KlabIllegalArgumentException(String.format("The URL %s does not utilize the S3 protocol", s3url));
        }
        String[] bucketAndObject = s3url.split("://")[1].split("/", 2);
        return "https://" + bucketAndObject[0] + ".s3.amazonaws.com/" + bucketAndObject[1];
    }
}