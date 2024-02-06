package org.integratedmodelling.klab.test.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.integratedmodelling.klab.exceptions.KlabMissingCredentialsException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.s3.S3ConnectionManager;
import org.integratedmodelling.klab.utils.s3.S3URLUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class S3ConnectionManagerTest {
    String minioEndpoint = "https://play.min.io";

    // Note: These tests might require that you have added your MinIO or AWS credentials.

    @Test
    public void makeATestConnection() {
        S3ConnectionManager s3connection = new S3ConnectionManager();
        s3connection.connect(minioEndpoint, Optional.empty());
    }

    @Test
    public void isNotConnectedBeforeConnection() {
        S3ConnectionManager s3connection = new S3ConnectionManager();

        boolean isConnected = s3connection.isConnected();

        assertFalse(isConnected);
    }

    @Test
    public void isConnectedAfterConnection() {
        S3ConnectionManager s3connection = new S3ConnectionManager();
        s3connection.connect(minioEndpoint, Optional.empty());

        boolean isConnected = s3connection.isConnected();

        assertTrue(isConnected);
    }

    @Test
    public void failToConnectDueToMissingCredentials() {
        S3ConnectionManager s3connection = new S3ConnectionManager();

        Assertions.assertThrows(KlabMissingCredentialsException.class, () -> {
            s3connection.connect("https://unregistered.eus", Optional.empty());
        });
    }

    @Nested
    @DisplayName("Tests usign AWS")
    public class AWSTests {
        @Test
        public void makeATestConnection() {
            S3ConnectionManager s3connection = new S3ConnectionManager();
            s3connection.connect(S3URLUtils.AWS_ENDPOINT, Optional.empty());
        }

        @Test
        public void downloadFile() throws IOException {
            String testResourceURL = "s3://landsat-pds/scene_list.gz";
            String bucketRegion = "us-west-2";
            String filePath = "test.gz";
            S3ConnectionManager s3connection = new S3ConnectionManager();
            s3connection.connect(S3URLUtils.AWS_ENDPOINT, Optional.of(bucketRegion));

            File file = s3connection.downloadFileFromS3URL(testResourceURL, filePath);

            assertTrue(file.exists());
            file.delete();
        }

        @Test
        public void getStream() throws IOException {
            String testResourceURL = "s3://landsat-pds/scene_list.gz";
            String bucketRegion = "us-west-2";
            S3ConnectionManager s3connection = new S3ConnectionManager();
            s3connection.connect(S3URLUtils.AWS_ENDPOINT, Optional.of(bucketRegion));

            InputStream input = s3connection.getInputStreamFromS3URL(testResourceURL);

            File file = File.createTempFile("test", ".gz");
            FileUtils.copyInputStreamToFile(input, file);
            assertTrue(file.exists());
        }
    }
}
