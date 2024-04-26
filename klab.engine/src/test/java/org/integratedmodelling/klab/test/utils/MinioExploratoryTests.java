package org.integratedmodelling.klab.test.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;

public class MinioExploratoryTests {
    // These endpoint and credentials were taken from the official MinIO documentation
    String minioEndpoint = "https://play.min.io";
    String minioAccessKey = "Q3AM3UQ867SPQQA43P2F";
    String minioSecretKey = "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG";

    @Test
    public void makeASuccessfulConnection() {
        @SuppressWarnings("unused")
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
    }

    @Test
    public void getBuckets() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
            InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(minioAccessKey, minioSecretKey)
                .build();

        List<Bucket> buckets = minioClient.listBuckets();

        assertFalse(buckets.isEmpty());
    }

    @Test
    @Disabled("There are no known public buckets that will remain unmodified forever.")
    public void knownBucketExists()
            throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
            InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        String existingBucketName = "grocery";
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(minioAccessKey, minioSecretKey)
                .build();

        List<Bucket> buckets = minioClient.listBuckets();
        Optional<Bucket> bucket = buckets.stream().filter(b -> b.name().equals(existingBucketName)).findFirst();

        assertTrue(bucket.isPresent());
    }

}
