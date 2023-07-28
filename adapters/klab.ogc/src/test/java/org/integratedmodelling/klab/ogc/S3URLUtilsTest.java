package org.integratedmodelling.klab.ogc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Optional;

import org.integratedmodelling.klab.S3ConnectionManager;
import org.integratedmodelling.klab.S3URLUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class S3URLUtilsTest {
    String minioEndpoint = "https://play.min.io";

    @Test
    public void makeATestConnection() {
        S3ConnectionManager s3connection = new S3ConnectionManager();
        s3connection.connect(minioEndpoint, Optional.empty());
    }


    @Nested
    @DisplayName("AWS Tests")
    public class AWSTests {
        @Test
        public void makeATestConnection() {
            S3ConnectionManager s3connection = new S3ConnectionManager();
            s3connection.connect(S3URLUtils.AWS_ENDPOINT, Optional.empty());
        }

        @Test
        public void downloadFile() {
            // TODO see how to manage the file
            String fileDestination = "./scene_list.gz";
            String testResourceURL = "s3://landsat-pds/scene_list.gz";
            String bucketRegion = "us-west-2";
            S3ConnectionManager s3connection = new S3ConnectionManager();
            s3connection.connect(S3URLUtils.AWS_ENDPOINT, Optional.of(bucketRegion));

            File file = s3connection.getFileFromS3URL(testResourceURL);

            assertTrue(file.exists());
        }
    }
}
