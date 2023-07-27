package org.integratedmodelling.klab;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabMissingCredentialsException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.URLUtils;

import io.minio.MinioClient;

public class S3URLUtils {
    final public static String AWS_ENDPOINT = "https://s3.amazonaws.com";
    static String awsAccessKey;
    static String awsSecretKey;


    public static MinioClient connect(String endpoint, Optional<String> region) {
        readS3Credentials(endpoint);
        if (region.isPresent()) {
            return MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(awsAccessKey, awsSecretKey)
                    .region(region.get())
                    .build();
        } else {
            // TODO check if the region is always required 
            return MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(awsAccessKey, awsSecretKey)
                    .build();
        }

    }
    
    public static File getFileForURL(String url, String region) throws ExceptionInInitializerError {
        if (url.toString().startsWith("s3:")) {
            Pair<String, String> bucketAndKey = extractBucketAndKey(url);
            String bucketName = bucketAndKey.getFirst();
            String objectName = bucketAndKey.getSecond();

            // TODO retrieve the file

            return null;
        } else {
            try {
                return URLUtils.getFileForURL(new URL(url));
            } catch (KlabIOException | MalformedURLException e) {
                throw new KlabIOException(e);
            }
        }
    }

    private static Pair<String, String> extractBucketAndKey(String s3Uri) {
        String[] uriParts = s3Uri.replaceFirst("s3://", "").split("/", 2);
        return new Pair<>(uriParts[0], uriParts[1]);
    }

    private static void readS3Credentials(String endpoint) {
        ExternalAuthenticationCredentials credentials = Authentication.INSTANCE.getCredentials(endpoint);
        if (credentials == null) {
            throw new KlabMissingCredentialsException("No credentials for the S3 endpoint " + endpoint);
        }

        awsAccessKey = credentials.getCredentials().get(0);
        awsSecretKey = credentials.getCredentials().get(1);
    }
}