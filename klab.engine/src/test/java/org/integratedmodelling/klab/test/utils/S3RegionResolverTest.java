package org.integratedmodelling.klab.test.utils;

import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.utils.s3.S3RegionResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import software.amazon.awssdk.regions.Region;

public class S3RegionResolverTest {

    @Test
    public void resolveBucketRegion_resolutionSuccessful() {
        String bucket = "deafrica-input-datasets";
        String objectKey = "rainfall_chirps_monthly/chirps-v2.0_2024.03.tif";

        Region ret = S3RegionResolver.resolveBucketRegion(bucket, objectKey);

        Assertions.assertEquals(Region.AF_SOUTH_1, ret);
    }

    @Test
    // Warning: a relatively costly test (sometimes over 30 seconds)
    public void resolveBucketRegion_resolutionUnsuccessful() {
        String bucket = "fake-bucket";
        String objectKey = "fake-object.tif";

        Assertions.assertThrows(KlabResourceAccessException.class, () -> {
            S3RegionResolver.resolveBucketRegion(bucket, objectKey);
        });
    }

}
