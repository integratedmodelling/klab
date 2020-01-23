package org.integratedmodelling.klab.hub.license;

public class ArmoredKeyPair {
    private final String privateKey;
    private final String publicKey;

    private ArmoredKeyPair(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String privateKey() {
        return privateKey;
    }

    public String publicKey() {
        return publicKey;
    }

    public static ArmoredKeyPair of(String privateKey, String publicKey) {
        return new ArmoredKeyPair(privateKey, publicKey);
    }
}
