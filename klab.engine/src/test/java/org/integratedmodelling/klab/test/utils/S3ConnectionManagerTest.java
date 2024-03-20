package org.integratedmodelling.klab.test.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.s3.S3ConnectionManager;
import org.integratedmodelling.klab.utils.s3.S3URLUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class S3ConnectionManagerTest {
    String minioEndpoint = "https://play.min.io";

    // Note: These tests might require that you have added your MinIO or AWS credentials.

    @Test
    public void makeATestConnection() {
        S3ConnectionManager s3connection = new S3ConnectionManager();
        s3connection.connect(minioEndpoint);
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
        s3connection.connect(minioEndpoint);

        boolean isConnected = s3connection.isConnected();

        assertTrue(isConnected);
    }

    @Nested
    @Disabled("There are no known resources that will remain unmodified forever.")
    @DisplayName("Tests usign AWS")
    public class AWSTests {
        @Test
        public void makeATestConnection() {
            S3ConnectionManager s3connection = new S3ConnectionManager();
            s3connection.connect(S3URLUtils.AWS_ENDPOINT);
        }

        @Test
        public void getStream() throws IOException {
            String testResourceURL = "s3://landsat-pds/scene_list.gz";
            S3ConnectionManager s3connection = new S3ConnectionManager();
            s3connection.connect(S3URLUtils.AWS_ENDPOINT);

            InputStream input = s3connection.getInputStreamFromS3URL(testResourceURL);

            File file = File.createTempFile("test", ".gz");
            FileUtils.copyInputStreamToFile(input, file);
            assertTrue(file.exists());
        }
    }
}
