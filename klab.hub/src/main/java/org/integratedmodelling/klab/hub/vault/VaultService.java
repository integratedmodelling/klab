package org.integratedmodelling.klab.hub.vault;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

@Service
public class VaultService {

    private final VaultTemplate vaultTemplate;

    // Injecting VaultTemplate
    public VaultService(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    // Method to get the API key from Vault
    public String getSecret(String path, String secret, String pathList) {

        // Reading the secret from Vault        
        VaultResponse response = vaultTemplate.read(path);
        
        if (response == null || response.getData() == null) {
            return null;
        }

        // Return the key 
        JSONObject json = new JSONObject(response.getData().get("data"));
        return (json.get(secret).toString());
    }
}

