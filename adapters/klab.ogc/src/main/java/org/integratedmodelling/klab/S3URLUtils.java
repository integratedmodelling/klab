package org.integratedmodelling.klab;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.CheckReturnValue;

import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.URLUtils;
import org.junit.Test;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3URLUtils {
	@CheckReturnValue
	public static File getFileForURL(String url) throws KlabIOException {
		if (url.toString().startsWith("s3:")) {
			Pair<String, String> bucketAndKey = extractBucketAndKey(url);
			String bucketName = bucketAndKey.getFirst();
			String key = bucketAndKey.getSecond();
			try {
				AmazonS3 s3client = AmazonS3ClientBuilder.standard()
					.withRegion(Regions.US_WEST_2) // TODO detect the Region beforehand
					.withCredentials(new ProfileCredentialsProvider())
					.build();
				
				S3Object s3object = s3client.getObject(bucketName, key);
				S3ObjectInputStream inputStream = s3object.getObjectContent();
				File temp = File.createTempFile("url", "url");
				FileUtils.copyInputStreamToFile(inputStream, temp);
				return temp;
			} catch (SdkClientException e) {
				throw new KlabResourceAccessException(e);
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		} else {
			try {
				return URLUtils.getFileForURL(new URL(url));
			} catch (KlabIOException | MalformedURLException e) {
				throw new KlabIOException(e);
			}
		}
	}

	private static Pair<String, String> extractBucketAndKey(String s3Uri) {
        String[] uriParts = s3Uri.replaceFirst("s3://", "").split("/", 2);
        return new Pair<>(uriParts[0], uriParts[1]);
	}

	@Test
	public void testerino() throws KlabIOException, IOException {
		// We are using this URL because it is a known open S3 resource
		String S3_URL = "s3://landsat-pds/scene_list.gz";
		
		File file = S3URLUtils.getFileForURL(S3_URL);
		
		assertTrue(file.exists());
	}
}