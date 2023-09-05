package org.integratedmodelling.klab.hub.customProperties.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.RecordedCustomProperty;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;
import org.integratedmodelling.klab.hub.customProperties.payload.RecordedCustomPropertiyRequest;
import org.integratedmodelling.klab.hub.customProperties.services.RecordedCustomPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordedCustomPropertyController {

    private RecordedCustomPropertyService customPropertyService;

    @Autowired
    public RecordedCustomPropertyController(RecordedCustomPropertyService customPropertiesServices) {
        super();
        this.customPropertyService = customPropertiesServices;
    }

    @GetMapping(API.HUB.CUSTOM_PROPERTIES)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getRecordedCustomProperties(@RequestParam(required = false) String type) {
        List<RecordedCustomProperty> customProperties;
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
    public ResponseEntity< ? > createRecordedCustomProperty(@RequestBody RecordedCustomPropertiyRequest request) {
        try {
            customPropertyService.createNewCustomProperties(request.getCustomPropertiesType(), request.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
