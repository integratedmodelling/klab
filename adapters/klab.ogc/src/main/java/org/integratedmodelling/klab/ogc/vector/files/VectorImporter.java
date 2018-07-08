package org.integratedmodelling.klab.ogc.vector.files;

import java.io.File;
import java.net.MalformedURLException;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.adapters.AbstractFilesetImporter;
import org.integratedmodelling.klab.ogc.VectorAdapter;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class VectorImporter extends AbstractFilesetImporter {

    VectorValidator validator = new VectorValidator();

    public VectorImporter() {
        super(VectorAdapter.fileExtensions.toArray(new String[VectorAdapter.fileExtensions.size()]));
    }

    @Override
    protected Builder importFile(File file, IParameters<String> userData, IMonitor monitor) {
        try {

            Builder builder = validator.validate(file.toURI().toURL(), userData, monitor);

            if (builder != null) {
                builder.setResourceId(MiscUtilities.getFileBaseName(file).toLowerCase());
                for (File f : validator.getAllFilesForResource(file)) {
                    builder.addImportedFile(f);
                }
            }

            return builder;

        } catch (MalformedURLException e) {
            Logging.INSTANCE.error(e);
            return null;
        }
    }

}
