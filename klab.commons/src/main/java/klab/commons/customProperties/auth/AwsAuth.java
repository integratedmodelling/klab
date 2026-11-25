package klab.commons.customProperties.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Authentication of the data on AWS S3 and S3 compatible services, with requests signed with AWS Signature V4.
 *
 * <pre>
 * "s3://my-bucket": {
 *   "type": "aws",
 *   "accessKeyId": "AKIA...",                                  (alias: username)
 *   "secretAccessKeySecret": "/secret/data/klab/hub/S3_KEY",   (alias: passwordSecret)
 *   "sessionTokenSecret": "/secret/...",                       (optional, temporary credentials)
 *   "region": "us-west-2",                                     (needed on AWS if the urls don't contain it)
 *   "endpoint": "https://eodata.dataspace.copernicus.eu"       (optional, S3 compatible services)
 * }
 * </pre>
 *
 * The fields with the secrets hold Vault paths, which the Hub replaces with the values. Without access key the
 * entry describes a public bucket, read without credentials in the given region.
 */
public class AwsAuth implements AuthType {

    @JsonAlias("username")
    private String accessKeyId;
    @JsonAlias("passwordSecret")
    private String secretAccessKeySecret;
    private String sessionTokenSecret;
    private String region;
    private String endpoint;

    public AwsAuth() {}

    public AwsAuth(String accessKeyId, String secretAccessKeySecret, String region) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKeySecret = secretAccessKeySecret;
        this.region = region;
    }

    @Override
    public String getType() {
        return "aws";
    }

    /**
     * @return <code>true</code> if the entry has no credentials, as for public buckets.
     */
    @JsonIgnore
    public boolean isAnonymous() {
        return accessKeyId == null || accessKeyId.isBlank() || secretAccessKeySecret == null
                || secretAccessKeySecret.isBlank();
    }

    public String getAccessKeyId() { return accessKeyId; }
    public void setAccessKeyId(String accessKeyId) { this.accessKeyId = accessKeyId; }

    public String getSecretAccessKeySecret() { return secretAccessKeySecret; }
    public void setSecretAccessKeySecret(String secretAccessKeySecret) { this.secretAccessKeySecret = secretAccessKeySecret; }

    public String getSessionTokenSecret() { return sessionTokenSecret; }
    public void setSessionTokenSecret(String sessionTokenSecret) { this.sessionTokenSecret = sessionTokenSecret; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
}
