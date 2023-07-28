package org.integratedmodelling.klab.ogc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.integratedmodelling.klab.S3URLUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

public class S3URLUtilsTest {
    @Nested
    @DisplayName("Minio Exploratory Tests")
    public class MinioExploratoryTests {
        String minioEndpoint = "https://play.min.io";

        @Test
        public void makeATestConnection() {
            S3URLUtils.connect(minioEndpoint, Optional.empty());
        }

        @Test
        @Disabled("We are in the process of rethinking the Minio utilities")
        public void getBuckets() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
            S3URLUtils.connect(minioEndpoint, Optional.empty());

//            List<Bucket> buckets = minioClient.listBuckets();
//
//            assertFalse(buckets.isEmpty());
        }

        @Test
        @Disabled("We are in the process of rethinking the Minio utilities")
        public void knownBucketExists() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
            String existingBucketName = "grocery";
            S3URLUtils.connect(minioEndpoint, Optional.empty());

//            List<Bucket> buckets = minioClient.listBuckets();
//            Optional<Bucket> bucket = buckets.stream().filter(b -> b.name().equals(existingBucketName)).findFirst();
//
//            assertTrue(bucket.isPresent());
        }
    }

    @Nested
    @DisplayName("AWS Exploratory Tests")
    public class AWSExploratoryTests {
        @Test
        public void makeATestConnection() {
            S3URLUtils.connect(S3URLUtils.AWS_ENDPOINT, Optional.empty());
        }

        @Test
        public void downloadFile() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
            // TODO see how to manage the file
            String fileDestination = "./scene_list.gz";
            String testResourceURL = "s3://landsat-pds/scene_list.gz";
            String bucketRegion = "us-west-2";
            S3URLUtils.connect(S3URLUtils.AWS_ENDPOINT, Optional.of(bucketRegion));

            File file = S3URLUtils.getFileForURL(testResourceURL, bucketRegion);
            
            assertTrue(file.exists());
        }
    }
}
