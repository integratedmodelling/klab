package org.integratedmodelling.klab.test.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.integratedmodelling.klab.utils.s3.S3ConnectionManager;
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

}
