package org.integratedmodelling.klab.hub.licenses.dto;

public class ArmoredKeyPair {
    private final byte[] privateKey;
    private final byte[] publicKey;

    private ArmoredKeyPair(byte[] privateKey, byte[] publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public byte[] privateKey() {
        return privateKey;
    }

    public byte[] publicKey() {
        return publicKey;
    }

    public static ArmoredKeyPair of(byte[] privateKey, byte[] publicKey) {
        return new ArmoredKeyPair(privateKey, publicKey);
    }
}
