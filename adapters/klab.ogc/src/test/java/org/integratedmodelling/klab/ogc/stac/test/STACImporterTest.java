package org.integratedmodelling.klab.ogc.stac.test;

import static org.junit.Assert.assertFalse;

import java.util.Collection;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.stac.STACImporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings({"unchecked", "unused"})
public class STACImporterTest {

    final static String IMPORT_LOCATION = "https://planetarycomputer.microsoft.com/api/stac/v1/collections/io-lulc-9-class";

    @Test
    public void importResource_correctlyImportFromCollection() {
        STACImporter importer = new STACImporter();
        IProject project = Mockito.mock(IProject.class);
        Mockito.when(project.getName()).thenReturn("test");
        IParameters<String> params = Mockito.mock(IParameters.class);
        IMonitor monitor = Mockito.mock(IMonitor.class);
        IUserIdentity identity = Mockito.mock(IUserIdentity.class);
        Mockito.when(identity.getUsername()).thenReturn("hares");
        Mockito.when(identity.getId()).thenReturn("hares");
        Authentication.INSTANCE.registerIdentity(identity); // Sadly, I don't know how to mock an enum singleton

        Collection<Builder> ret = importer.importResources(IMPORT_LOCATION, project, params, monitor);

        assertFalse(ret.isEmpty());
    }

    @Test
    public void importResource_failCannotImportCatalog() {
        String importLocation = "https://planetarycomputer.microsoft.com/api/stac/v1";
        STACImporter importer = new STACImporter();
        IProject project = Mockito.mock(IProject.class);
        Mockito.when(project.getName()).thenReturn("test");
        IParameters<String> params = Mockito.mock(IParameters.class);
        IMonitor monitor = Mockito.mock(IMonitor.class);
        IUserIdentity identity = Mockito.mock(IUserIdentity.class);
        Mockito.when(identity.getUsername()).thenReturn("hares");
        Mockito.when(identity.getId()).thenReturn("hares");
        Authentication.INSTANCE.registerIdentity(identity);

        Assertions.assertThrows(KlabUnsupportedFeatureException.class, () -> {
            Collection<Builder> ret = importer.importResources(importLocation, project, params, monitor);
        });
    }

    @Test
    public void importResource_failCannotImportNonExistingCollection() {
        String importLocation = "https://planetarycomputer.microsoft.com/api/stac/v1/collections/nothing";
        STACImporter importer = new STACImporter();
        IProject project = Mockito.mock(IProject.class);
        Mockito.when(project.getName()).thenReturn("test");
        IParameters<String> params = Mockito.mock(IParameters.class);
        IMonitor monitor = Mockito.mock(IMonitor.class);
        IUserIdentity identity = Mockito.mock(IUserIdentity.class);
        Mockito.when(identity.getUsername()).thenReturn("hares");
        Mockito.when(identity.getId()).thenReturn("hares");
        Authentication.INSTANCE.registerIdentity(identity);

        Assertions.assertThrows(KlabIOException.class, () -> {
            Collection<Builder> ret = importer.importResources(importLocation, project, params, monitor);
        });
    }

}