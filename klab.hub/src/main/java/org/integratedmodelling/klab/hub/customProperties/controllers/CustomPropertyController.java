package org.integratedmodelling.klab.hub.customProperties.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;
import org.integratedmodelling.klab.hub.customProperties.payloads.CustomPropertyRequest;
import org.integratedmodelling.klab.hub.customProperties.services.CustomPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomPropertyController {

    private CustomPropertyService customPropertyService;

    @Autowired
    public CustomPropertyController(CustomPropertyService customPropertiesServices) {
        super();
        this.customPropertyService = customPropertiesServices;
    }

    @GetMapping(API.HUB.CUSTOM_PROPERTIES)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getCustomProperties(@RequestParam(required = false) String type) {
        List<CustomProperty> customProperties;
        try {
            if (type == null) {
                customProperties = customPropertyService.getAllCustomProperties();
            } else {
                customProperties = customPropertyService.getCustomPropertiesByType(CustomPropertyType.valueOf(type));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(customProperties, HttpStatus.OK);
    }

    @PostMapping(API.HUB.CUSTOM_PROPERTIES)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > createCustomProperty(@RequestBody CustomPropertyRequest request) {
        try {
            customPropertyService.createNewCustomProperties(request.getCustomPropertiesType(), request.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
