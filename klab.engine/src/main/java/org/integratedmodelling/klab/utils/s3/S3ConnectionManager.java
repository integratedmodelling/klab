package org.integratedmodelling.klab.utils.s3;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.MinioClient.Builder;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

public class S3ConnectionManager {
    String url;
    String s3AccessKey;
    String s3SecretKey;
    MinioClient minioClient;

    /**
     * Creates a connection to the S3 endpoint 
     * @param endpoint S3 endpoint
     */
    public void connect(String endpoint) {
        boolean hasCredentials = readS3Credentials(endpoint);
        Builder builder = MinioClient.builder()
                .endpoint(endpoint);
        if (hasCredentials) {
            builder.credentials(s3AccessKey, s3SecretKey);
        }
        minioClient = builder.build();
    }

    /**
     * Checks if a bucket is present in the existing connection
     * @param bucketName
     * @return true if the bucket exists
     */
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            throw new KlabResourceAccessException(e);
        }
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

    /**
     * Checks if there is an existing connection to a S3 endpoint
     * @return true if connected
     */
    public boolean isConnected() {
        return minioClient != null;
    }

}
