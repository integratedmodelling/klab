package org.integratedmodelling.klab.ogc.stac.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.engine.resources.Project;
import org.integratedmodelling.klab.stac.STACValidator;
import org.integratedmodelling.klab.utils.Parameters;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

public class STACValidatorTest {
    @Test
    public void validate_passCreateValidBuilder() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();
        userData.put("collection", "https://planetarycomputer.microsoft.com/api/stac/v1/collections/io-lulc-9-class");
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

    /**
     * Disclaimer: It is known that testing the implementation itself is not a recommended practice.
     * However, to check the success of the `validate` method, the build process has to be finished.
     * The primary challenge in this approach lies in the implementation's attempt to read projects using a singleton,
     * necessitating the need to mock several elements that may not be relevant to our tests.
     */
    private void mockResourcesSingleton() {
        File file =  Mockito.mock(File.class);
        Mockito.when(file.getParentFile()).thenReturn(Paths.get("src/test/resources/temp").toAbsolutePath().toFile());
        Project project = Mockito.mock(Project.class);
        Mockito.when(project.getRoot()).thenReturn(file);
        Resources resourcesInstance = Mockito.mock(Resources.class);
        Whitebox.setInternalState(Resources.class, "INSTANCE", resourcesInstance);
        Mockito.when(resourcesInstance.getProject("TEST")).thenReturn(project);
    }

    @Test
    public void validate_passBuilderHasCodelist() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();
        // Add a resource with codelist: https://planetarycomputer.microsoft.com/api/stac/v1/collections/io-lulc-9-class
        userData.put("collection", "https://planetarycomputer.microsoft.com/api/stac/v1/collections/io-lulc-9-class");
        userData.put("collectionId", "io-lulc-9-class");
        userData.put("asset", "data");
        mockResourcesSingleton();

        Builder ret =  validator.validate("any:urn", null, userData, null);

        // The resource has to be build to retrieve the data
        ret.withLocalPath("./").withProjectName("TEST");
        IResource resource = ret.build();
        assertTrue(resource.getCodelists().get(0).equals("DATA"));
    }

    @Test
    public void validate_passBuilderHasNoCodelist() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();
        // Add a resource with no codelist: https://earth-search.aws.element84.com/v1/collections/sentinel-2-l2a
        userData.put("collection", "https://earth-search.aws.element84.com/v1/collections/sentinel-2-l2a");
        userData.put("collectionId", "sentinel-2-l2a");
        userData.put("asset", "blue");
        mockResourcesSingleton();

        Builder ret =  validator.validate("any:urn", null, userData, null);

        // The resource has to be build to retrieve the data
        ret.withLocalPath("./").withProjectName("TEST");
        IResource resource = ret.build();
        assertTrue(resource.getCodelists().isEmpty());
    }

    @Test
    public void canHandle_passValidUserData() {
        STACValidator validator = new STACValidator();
        IParameters<String> userData = new Parameters<>();
        userData.put("collection", "https://planetarycomputer.microsoft.com/api/stac/v1/collections/io-lulc-9-class");
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
        userData.put("collection", "https://planetarycomputer.microsoft.com/api/stac/v1/collections/io-lulc-9-class");
        // Missing "collectionId" and "asset"

        boolean ret = validator.canHandle(null, userData);

        assertFalse(ret);
    }

    @AfterAll
    public static void afterAll() throws IOException {
        // Clean the codelist file
        Files.deleteIfExists(Paths.get("src/test/resources/temp/code_DATA.json").toAbsolutePath());
    }
}
