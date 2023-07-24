package org.integratedmodelling.klab;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.URLUtils;

public class S3URLUtils {
    public static File getFileForURL(String url, String region) throws ExceptionInInitializerError {
        if (url.toString().startsWith("s3:")) {
            Pair<String, String> bucketAndKey = extractBucketAndKey(url);
            String bucketName = bucketAndKey.getFirst();
            String objectName = bucketAndKey.getSecond();

            // TODO retrieve the file

            return null;
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
}