package org.integratedmodelling.klab.utils.s3;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class S3RegionResolver {

    public static String resolveBucketRegion(String bucketName, String objectKey, IMonitor monitor) {
        /*
        // Step 1: Attempt to dynamically resolve the bucket's region
        try (Client s3 = S3Client.builder()
                .httpClientBuilder(ApacheHttpClient.builder())
                .region(Region.US_EAST_1) // Use the default region
                .credentialsProvider(AnonymousCredentialsProvider.create()) // Anonymous credentials
                .build()) {

            GetBucketLocationRequest request = GetBucketLocationRequest.builder()
                    .bucket(bucketName)
                    .build();

            GetBucketLocationResponse response = s3.getBucketLocation(request);
            String location = response.locationConstraintAsString();

            // Handle "null" or "global" regions
            if (location == null || location.equalsIgnoreCase("null")) {
                return Region.US_EAST_1;
            }

            Region resolvedRegion = Region.of(location);
            monitor.debug("Bucket " + bucketName + " exists in the default region.");
            return resolvedRegion;
        } catch (S3Exception e) {
            ;// Nothing to do here. It is expected to fail if the bucket is not in the default region. Try on step 2.
        } catch (Exception e) {
            monitor.debug("Unexpected exception trying to get to the S3 default region: " + e.getMessage());
        }
        */
        // Step 2: Iterate through all regions and test lightweight requests
        return resolveRegionByTesting(bucketName, objectKey, monitor);
    }

    /*
    private static List<Region> getAwsRegions() {
        // List of regions to exclude (e.g., isolated regions or restricted access regions)
        List<Region> excludedRegions = List.of(
                Region.US_ISO_EAST_1, // Restricted to isolated networks
                Region.US_ISO_WEST_1, // Example of another restricted region
                Region.CN_NORTH_1,    // China regions may require special accounts
                Region.CN_NORTHWEST_1
        );

        // Get the list of all AWS regions, excluding problematic ones
        return Region.regions().stream()
                .filter(region -> !excludedRegions.contains(region))
                .collect(Collectors.toList());
    }
    */

    private static String resolveRegionByTesting(String bucketName, String objectKey, IMonitor monitor) {
        /*
        // Get the list of all AWS regions
        List<Region> regions = getAwsRegions();

        // Iterate through regions to perform a lightweight test
        for (Region region : regions) {
            try (S3Client s3 = S3Client.builder()
                    .httpClientBuilder(ApacheHttpClient.builder())
                    .region(region)
                    .credentialsProvider(AnonymousCredentialsProvider.create())
                    .build()) {

                // Perform a lightweight HEAD request to check if the object exists
                HeadObjectRequest request = HeadObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build();

                s3.headObject(request); // If no exception is thrown, the region is correct
                monitor.debug("Bucket " + bucketName + " exists in the region " + region.id() + ".");
                return region;
            } catch (S3Exception e) {
                // Continue testing other regions if the bucket is not found
                continue;
            } catch (Exception e) {
                monitor.debug("Unexpected exception trying to get to the S3 region " + region.id() + ": " + e.getMessage());
                continue;
            }
        }

        throw new KlabResourceAccessException("Unable to resolve region for bucket: " + bucketName);
        */
        return "ap-southeast-2";
    }
}