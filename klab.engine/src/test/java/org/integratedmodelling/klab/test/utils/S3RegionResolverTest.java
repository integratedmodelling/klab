package org.integratedmodelling.klab.test.utils;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.utils.s3.S3RegionResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class S3RegionResolverTest {
    @Test
    @Disabled
    public void resolveBucketRegion_resolutionSuccessful() {
        String bucket = "deafrica-input-datasets";
        String objectKey = "rainfall_chirps_monthly/chirps-v2.0_2024.03.tif";
        IMonitor monitor = Mockito.mock(IMonitor.class);

        String ret = S3RegionResolver.resolveBucketRegion(bucket, objectKey, monitor);

        Assertions.assertEquals("WIP", ret);
    }

    @Test
    @Disabled
    // Warning: a relatively costly test (sometimes over 30 seconds)
    public void resolveBucketRegion_resolutionUnsuccessful() {
        String bucket = "fake-bucket";
        String objectKey = "fake-object.tif";
        IMonitor monitor = Mockito.mock(IMonitor.class);

        Assertions.assertThrows(KlabResourceAccessException.class, () -> {
            S3RegionResolver.resolveBucketRegion(bucket, objectKey, monitor);
        });
    }

}
