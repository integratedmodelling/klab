package org.integratedmodelling.klab.utils.s3;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.WorkInProgress;
import org.integratedmodelling.klab.utils.WorkInProgress.Status;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
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
    @WorkInProgress(reason = "This method should determine the region of the bucket instead of making multiple calls to the bucket to guess it or extracting it from an error response.", status = Status.ON_HOLD)
    public InputStream getInputStreamFromS3URL(String url) {
        Pair<String, String> bucketAndKey = extractBucketAndKey(url);
        String bucket = bucketAndKey.getFirst();
        String object = bucketAndKey.getSecond();

        // Until a better way is implemented, we use the default region for the first try.
        @WorkInProgress(reason = "Currently, there is no way to determine the region of the bucket.", status = Status.ON_HOLD)
        String region = "eu-west-1";

        try {
            return getObject(bucket, object, region);
        } catch (InvalidKeyException | InsufficientDataException | InternalException | InvalidResponseException
                | NoSuchAlgorithmException | ServerException | XmlParserException | IOException e) {
            throw new KlabResourceAccessException(String.format("Cannot get stream from S3 object at %s. ", url) + e);
        } catch (ErrorResponseException e) {
            // Try to read the region from the error message and extract the region.
            // If not possible, just throw an exception
            region = e.response().networkResponse().header("x-amz-bucket-region");
            if (region == null) {
                throw new KlabResourceAccessException(String.format("Error receiving stream from S3 object at %s. ", url) + e);
            }
            try {
                return getObject(bucket, object, region);
            } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                    | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                    | IOException f) {
                throw new KlabResourceAccessException(String.format("Cannot get stream from %s. ", url) + f);
            }
        }
    }

    private GetObjectResponse getObject(String bucket, String object, String region)
            throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException,
            InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .region(region)
                .build());
    }
}
