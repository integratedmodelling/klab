package org.integratedmodelling.klab.data.adapters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.MiscUtilities;

public abstract class AbstractFilesetImporter implements IResourceImporter {

    private String[] recognizedExtensions;

    /**
     * Try to import the passed file, which matches one of the {@link #recognizedExtensions}. If successful,
     * return a builder for the correspondent resource. Otherwise return null.
     * 
     * @param file
     * @param userData
     * @param monitor
     * @return a builder for the resource or null.
     */
    protected abstract Builder importFile(File file, IParameters<String> userData, IMonitor monitor);

    protected AbstractFilesetImporter(String[] recognizedExtensions) {
        this.recognizedExtensions = recognizedExtensions;
    }

    @Override
    public Collection<Builder> importResources(String importLocation, IParameters<String> userData, IMonitor monitor) {
        List<Builder> ret = new ArrayList<>();
        File file = getFile(importLocation);
        if (file != null && file.isDirectory()) {
            scanDirectory(file, userData, ret, monitor);
        }
        return ret;
    }

    private void scanDirectory(File directory, IParameters<String> userData, List<Builder> ret, IMonitor monitor) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, userData, ret, monitor);
            } else if (file.canRead()) {
                for (String s : recognizedExtensions) {
                    if (MiscUtilities.getFileExtension(file).equals(s)) {
                        Builder builder = importFile(file, userData, monitor);
                        if (builder != null) {
                            ret.add(builder);
                        }
                    }
                }
            }
        }
    }

    public File getFile(String importLocation) {

        File file = null;
        if (importLocation.startsWith("file:")) {
            try {
                file = new File(new URL(importLocation).getFile());
            } catch (MalformedURLException e) {
                return null;
            }
        } else {
            file = Klab.INSTANCE.resolveFile(importLocation);
            if (file == null || !file.exists()) {
                return null;
            }
            if (file.exists()) {
                if (file.isDirectory()) {
                    return file;
                } else {
                    // TODO check for archive file
                }
            }
        }
        return file;
    }

    @Override
    public boolean canHandle(String importLocation, IParameters<String> userData) {
        return getFile(importLocation) != null;
    }

}
