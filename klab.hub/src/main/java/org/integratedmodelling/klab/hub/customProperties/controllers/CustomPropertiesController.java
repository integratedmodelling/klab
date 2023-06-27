package org.integratedmodelling.klab.hub.customProperties.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.CustomProperties;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertiesType;
import org.integratedmodelling.klab.hub.customProperties.payload.CustomPropertiesRequest;
import org.integratedmodelling.klab.hub.customProperties.services.CustomPropertiesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomPropertiesController {

    private CustomPropertiesServices customPropertiesServices;

    @Autowired
    public CustomPropertiesController(CustomPropertiesServices customPropertiesServices) {
        super();
        this.customPropertiesServices = customPropertiesServices;
    }

    @GetMapping(API.HUB.CUSTOM_PROPERTIES)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getCustomProperties(@RequestParam(required = false) String type) {
        List<CustomProperties> customProperties;
        try {
            if (type == null) {
                customProperties = customPropertiesServices.getAllCustomProperties();
            } else {
                customProperties = customPropertiesServices.getCustomPropertiesByType(CustomPropertiesType.valueOf(type));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(customProperties, HttpStatus.OK);
    }

    @PostMapping(API.HUB.CUSTOM_PROPERTIES)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > createNewCustomProperties(@RequestBody CustomPropertiesRequest customPropertiesRequest) {
        try {
            customPropertiesServices.createNewCustomProperties(customPropertiesRequest.getCustomPropertiesType(), customPropertiesRequest.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
