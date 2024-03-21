package org.integratedmodelling.klab.test.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.utils.s3.S3URLUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class S3UtilsTest {
    @Test
    public void convertS3URLToHTTP_ValidInput() {
        String input = "s3://bucket/path/to/object";

        String ret = S3URLUtils.convertS3URLToHTTP(input);
        
        assertEquals("https://bucket.s3.amazonaws.com/path/to/object", ret);
    }

    @Test
    public void convertS3URLToHTTP_InvalidInput() {
        String input = "kfc://bucket/object";

        Assertions.assertThrows(KlabIllegalArgumentException.class, () -> {
            S3URLUtils.convertS3URLToHTTP(input);
        });
    }

}
