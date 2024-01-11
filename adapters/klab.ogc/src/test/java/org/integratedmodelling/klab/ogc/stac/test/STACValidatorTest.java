package org.integratedmodelling.klab.ogc.stac.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.stac.STACValidator;
import org.integratedmodelling.klab.utils.Parameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class STACValidatorTest {
    @Test
    public void validate_passCreateValidBuilder() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();
        userData.put("catalogUrl", "https://planetarycomputer.microsoft.com/api/stac/v1");
        userData.put("collectionId", "io-lulc-9-class");
        userData.put("asset", "data");

        Builder ret =  validator.validate("any:urn", null, userData, null);

        assertFalse(ret.hasErrors());
    }

    @Test
    public void validate_failIncompleteUserData() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();
        userData.put("catalogUrl", "https://planetarycomputer.microsoft.com/api/stac/v1");
        userData.put("collectionId", "io-lulc-9-class");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            validator.validate("any:urn", null, userData, null);
        });
    }

    @Test
    public void canHandle_passValidUserData() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();
        userData.put("catalogUrl", "https://planetarycomputer.microsoft.com/api/stac/v1");
        userData.put("collectionId", "io-lulc-9-class");
        userData.put("asset", "data");

        boolean ret = validator.canHandle(null, userData);

        assertTrue(ret);
    }

    @Test
    public void canHandle_failEmptyUserData() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();

        boolean ret = validator.canHandle(null, userData);

        assertFalse(ret);
    }

    @Test
    public void canHandle_failIncompleteUserData() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();
        userData.put("catalogUrl", "https://planetarycomputer.microsoft.com/api/stac/v1");
        // Missing "collectionId" and "asset"

        boolean ret = validator.canHandle(null, userData);

        assertFalse(ret);
    }
}
