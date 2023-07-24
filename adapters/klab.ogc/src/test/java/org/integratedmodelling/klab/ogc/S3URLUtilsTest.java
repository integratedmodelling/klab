package org.integratedmodelling.klab.ogc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.integratedmodelling.klab.S3URLUtils;
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

public class S3URLUtilsTest {
    String existingBucketName = "grocery";
    // These endpoint and keys come from the MinIO documentation
    String minioEndpoint = "https://play.min.io";
    String accessKey = "Q3AM3UQ867SPQQA43P2F";
    String secretKey = "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG";

    @Test
    @Disabled("Not finished yet")
    public void getAFile() {
        // We are using this URL because it is a known open S3 resource
        String S3_URL = "s3://landsat-pds/scene_list.gz";
        
        File file = S3URLUtils.getFileForURL(S3_URL, "us-west-2");

        assertEquals(true, file.exists());
    }

    @Test
    public void makeATestConnection() {
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

        assertEquals(false, buckets.isEmpty());
    }
}
