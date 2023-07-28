package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabMissingCredentialsException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.URLUtils;

import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

public class S3URLUtils {
    final public static String AWS_ENDPOINT = "https://s3.amazonaws.com";
    static String awsAccessKey;
    static String awsSecretKey;
    static MinioClient minioClient;

    public static void connect(String endpoint, Optional<String> region) {
        readS3Credentials(endpoint);
        if (region.isPresent()) {
            minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(awsAccessKey, awsSecretKey)
                    .region(region.get())
                    .build();
        } else {
            // TODO check if the region is always required 
            minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(awsAccessKey, awsSecretKey)
                    .build();
        }
    }

    public static File getFileForURL(String url, String region) throws ExceptionInInitializerError, InvalidKeyException,
            ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException,
            NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
        if (url.toString().startsWith("s3:")) {
            Pair<String, String> bucketAndKey = extractBucketAndKey(url);
            String bucket = bucketAndKey.getFirst();
            String object = bucketAndKey.getSecond();
            // TODO see how to manage the file
            String filename = "./temp-" + object;

            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(bucket)
                    .filename(filename)
                    .object(object)
                    .build());

            return new File(filename);
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