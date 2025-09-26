package org.integratedmodelling.klab.hub.vault;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vault")
public class VaultController {

    private final VaultService vaultService;

    // Constructor to inject the service
    public VaultController(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    @PostMapping("/get-secret")
    public String registerUser(@RequestParam String path) {
        // Get the API key dynamically from Vault
        String secret = vaultService.getSecret(path);
        if (secret == null) {
            return "Error: Secret " +secret+ " not found";
        }

        // Use the dynamic API key to register the user
        return secret;
    }
}

