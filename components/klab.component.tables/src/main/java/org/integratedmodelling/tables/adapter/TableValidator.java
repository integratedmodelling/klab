package org.integratedmodelling.tables.adapter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.MiscUtilities;

import com.google.common.collect.Lists;

public class TableValidator implements IResourceValidator {

    @Override
    public Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Operation> getAllowedOperations(IResource resource) {
        List<Operation> ret = new ArrayList<>();
        return ret;
    }

    @Override
    public IResource performOperation(IResource resource, String operationName, IResourceCatalog catalog, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canHandle(File resource, IParameters<String> parameters) {
        if (resource == null) {
            return false;
        }
        return TableAdapter.fileExtensions.contains(MiscUtilities.getFileExtension(resource));
    }

    @Override
    public Collection<File> getAllFilesForResource(File file) {
        return Lists.newArrayList(file);
    }

}
