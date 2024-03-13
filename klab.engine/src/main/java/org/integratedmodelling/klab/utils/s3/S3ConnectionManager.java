package org.integratedmodelling.klab.utils.s3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabMissingCredentialsException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.utils.Pair;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.MinioClient.Builder;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

public class S3ConnectionManager {
    String s3AccessKey;
    String s3SecretKey;
    MinioClient minioClient;

    /**
     * Creates a connection to the S3 endpoint 
     * @param endpoint S3 endpoint
     * @param region Region of the bucket (Optional)
     */
    public void connect(String endpoint, Optional<String> region) {
        boolean hasCredentials = readS3Credentials(endpoint);
        Builder builder = MinioClient.builder()
                .endpoint(endpoint);
        if (hasCredentials) {
            builder.credentials(s3AccessKey, s3SecretKey);
        }
        if (region.isPresent()) {
            builder.region(region.get());
        }
        minioClient = builder.build();
    }

    /**
     * Downloads a file from the S3 endpoint
     * @param url of the object
     * @param filename where the file is going to be stored
     * @return the File where the object has been downloaded
     */
    public File downloadFileFromS3URL(String url, String filename) {
        if (!isConnected()) {
            throw new KlabIllegalStateException("There is not an open S3 connection.");
        }

        if (!S3URLUtils.isS3Endpoint(url)) {
            throw new KlabIllegalArgumentException("Tried to download the resource at " + url + " using an S3 connection.");
        }
        Pair<String, String> bucketAndKey = extractBucketAndKey(url);
        String bucket = bucketAndKey.getFirst();
        String object = bucketAndKey.getSecond();

        try {
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(bucket)
                    .filename(filename)
                    .object(object)
                    .build());
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            throw new KlabResourceAccessException("Cannot download the resource at " + url + ". Error " + e);
        }

        return new File(filename);
    }

    private boolean readS3Credentials(String endpoint) {
        ExternalAuthenticationCredentials credentials = Authentication.INSTANCE.getCredentials(endpoint);
        if (credentials == null) {
            return false;
        }

        s3AccessKey = credentials.getCredentials().get(0);
        s3SecretKey = credentials.getCredentials().get(1);
        return true;
    }

    private static Pair<String, String> extractBucketAndKey(String s3Uri) {
        String[] uriParts = s3Uri.replaceFirst("s3://", "").split("/", 2);
        return new Pair<>(uriParts[0], uriParts[1]);
    }

    /**
     * Checks if there is an existing connection to a S3 endpoint
     * @return true if connected
     */
    public boolean isConnected() {
        return minioClient != null;
    }

    /**
     * Gets the input stream of an object
     * @param url of the object
     * @return the object as an InputStream
     */
    public InputStream getInputStreamFromS3URL(String url) {
        Pair<String, String> bucketAndKey = extractBucketAndKey(url);
        String bucket = bucketAndKey.getFirst();
        String object = bucketAndKey.getSecond();

        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .build());
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            throw new KlabResourceAccessException("Cannot get stream from the resource at " + url + ". Error " + e);
        }
    }
}
