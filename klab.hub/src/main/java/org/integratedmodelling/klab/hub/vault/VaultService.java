package org.integratedmodelling.klab.hub.vault;

import java.util.Map;

import org.integratedmodelling.klab.hub.exception.HubHttpException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
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
    public String getSecret(String fullPath) {
        
        String secret = fullPath.substring(fullPath.lastIndexOf("/") + 1);
        String path = fullPath.substring(0, fullPath.lastIndexOf("/"));

        // Reading the secret from Vault        
        VaultResponse response = vaultTemplate.read(path);
        
        if (response == null || response.getData() == null) {
            throw new HubHttpException(
                    "vault.no_data_found", 
                    "No data found at the requested path. " + path, 
                    HttpStatus.NOT_FOUND
                );
        }

        // Return the key 
        Map data = (Map) response.getData().get("data");
        if (data.isEmpty()) {
            throw new HubHttpException(
                    "vault.mapa_is_empty", 
                    "The map is empty. No data found at the requested path. " + path, 
                    HttpStatus.NOT_FOUND
                );
        }
        
        JSONObject json = new JSONObject((Map) data);
        
     // Check if the key 'exists in the JSON object
        if (json.has(secret)) {
            return json.get(secret).toString();  // Get the value if the key exists
        } else {
            // Handle the case when 'secret' doesn't exist in the JSON
            throw new HubHttpException(
                    "vault.secret_not_found", "Secret not found", HttpStatus.NOT_FOUND);
        }
    }
}

