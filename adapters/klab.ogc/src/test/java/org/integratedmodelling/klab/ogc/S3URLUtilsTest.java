package org.integratedmodelling.klab.ogc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.S3URLUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;

public class S3URLUtilsTest {
    // These endpoint and keys come from the MinIO documentation
    String minioEndpoint = "https://play.min.io";
    String accessKey = "Q3AM3UQ867SPQQA43P2F";
    String secretKey = "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG";

    @Nested
    @DisplayName("Minio Exploratory Tests")
    public class MinioExploratoryTests {
        @Test
        public void makeATestConnection() {
            @SuppressWarnings("unused")
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .credentials(accessKey, secretKey)
                    .build();
        }

        @Test
        public void getBuckets() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            List<Bucket> buckets = minioClient.listBuckets();
        
            assertFalse(buckets.isEmpty());
        }

        @Test
        public void knownBucketExists() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
            String existingBucketName = "grocery";
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            List<Bucket> buckets = minioClient.listBuckets();
            Optional<Bucket> bucket = buckets.stream().filter(b -> b.name().equals(existingBucketName)).findFirst();

            assertTrue(bucket.isPresent());
        }
    }

    @Nested
    @DisplayName("AWS Exploratory Tests")
    public class AWSExploratoryTests {
        String bucketRegion = "us-west-2";
        String awsEndpoint = "https://s3.amazonaws.com";
        String testEndpoint = "s3://landsat-pds/scene_list.gz";
        // TODO Do not hardcode the credentials
        String awsAccessKey = "XXX";
        String awsSecretKey = "XXX";

        @Test
        public void makeATestConnection() {
            @SuppressWarnings("unused")
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .credentials(accessKey, secretKey)
                    .build();
        }

        @Test
        public void downloadFile() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
            String fileDestination = "./scene_list.gz";
            String bucket = "landsat-pds";
            String object = "scene_list.gz";
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(awsEndpoint)
                    .credentials(awsAccessKey, awsSecretKey)
                    .region(bucketRegion)
                    .build();

            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(bucket)
                    .filename(fileDestination)
                    .object(object)
                    .build());

            Path path = Paths.get(fileDestination);
            assertTrue(Files.exists(path));
        }
    }
}
