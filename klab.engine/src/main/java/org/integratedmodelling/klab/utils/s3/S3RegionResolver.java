package org.integratedmodelling.klab.utils.s3;

import java.util.List;
import java.util.stream.Collectors;

import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetBucketLocationRequest;
import software.amazon.awssdk.services.s3.model.GetBucketLocationResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3RegionResolver {

    public static Region resolveBucketRegion(String bucketName, String objectKey) {
        // Default region to start with for GetBucketLocation
        Region defaultRegion = Region.US_EAST_1;

        // Step 1: Attempt to dynamically resolve the bucket's region
        try (S3Client s3 = S3Client.builder()
                .httpClientBuilder(ApacheHttpClient.builder())
                .region(defaultRegion) // Use the default region
                .credentialsProvider(AnonymousCredentialsProvider.create()) // Anonymous credentials
                .build()) {

            System.out.println("Attempting to resolve region dynamically for bucket: " + bucketName);

            GetBucketLocationRequest request = GetBucketLocationRequest.builder()
                    .bucket(bucketName)
                    .build();

            GetBucketLocationResponse response = s3.getBucketLocation(request);
            String location = response.locationConstraintAsString();

            // Handle "null" or "global" regions
            if (location == null || location.equalsIgnoreCase("null")) {
                System.out.println("Bucket region resolved dynamically to: us-east-1");
                return Region.US_EAST_1;
            }

            Region resolvedRegion = Region.of(location);
            System.out.println("Bucket region resolved dynamically to: " + resolvedRegion);
            return resolvedRegion;

        } catch (Exception e) {
            System.err.println("Failed to resolve region dynamically for bucket: " + bucketName + ". Error: " + e.getMessage());
        }


        // List of regions to exclude (e.g., isolated regions or restricted access regions)
        List<Region> excludedRegions = List.of(
                Region.US_ISO_EAST_1, // Restricted to isolated networks
                Region.US_ISO_WEST_1, // Example of another restricted region
                Region.CN_NORTH_1,    // China regions may require special accounts
                Region.CN_NORTHWEST_1
        );

        // Get the list of all AWS regions, excluding problematic ones
        List<Region> regions = Region.regions().stream()
                .filter(region -> !excludedRegions.contains(region))
                .collect(Collectors.toList());

        System.out.println("Falling back to region testing for bucket: " + bucketName);


        // Step 2: Iterate through all regions and test lightweight requests
        return resolveRegionByTesting(bucketName, objectKey);
    }

    private static Region resolveRegionByTesting(String bucketName, String objectKey) {
        // Get the list of all AWS regions
        List<Region> regions = Region.regions();

        System.out.println("Falling back to region testing for bucket: " + bucketName);

        // Iterate through regions to perform a lightweight test
        for (Region region : regions) {
            try (S3Client s3 = S3Client.builder()
                    .region(region)
                    .credentialsProvider(AnonymousCredentialsProvider.create())
                    .build()) {

                System.out.println("Testing region: " + region);

                // Perform a lightweight HEAD request to check if the object exists
                HeadObjectRequest request = HeadObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build();

                s3.headObject(request); // If no exception is thrown, the region is correct
                System.out.println("Region confirmed by testing: " + region);
                return region;

            } catch (S3Exception e) {
                // Continue testing other regions if the bucket is not found
                if (e.statusCode() != 403 && e.statusCode() != 404) {
                    System.err.println("Error testing region " + region + ": " + e.awsErrorDetails().errorMessage());
                }
            } catch (Exception e) {
                System.err.println("Exception testing region " + region + ": " + e.getMessage());
            }
        }

        throw new RuntimeException("Unable to resolve region for bucket: " + bucketName);
    }
}