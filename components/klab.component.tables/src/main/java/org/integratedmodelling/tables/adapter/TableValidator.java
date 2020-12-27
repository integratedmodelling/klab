package org.integratedmodelling.tables.adapter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler;
import org.integratedmodelling.tables.TablesComponent;
import org.integratedmodelling.tables.adapter.xls.XLSAdapter;
import org.integratedmodelling.tables.adapter.xls.XLSInterpreter;

import com.google.common.collect.Lists;

public class TableValidator implements IResourceValidator {

	static public final String FILE_URL = "__source_url";

	public TableValidator() {
	}

	@Override
    public Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();
		
		if (url != null) {
			userData.put(FILE_URL, url);
		}

        for (String adapter : TablesComponent.adapters) {
        	if (TablesComponent.getTableInterpreter(adapter).canHandle(url, userData)) {
        		TablesComponent.getTableInterpreter(adapter).buildResource(userData, ret, monitor);
        		break;
        	}
        }
        return ret;
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
        
        for (String adapter : TablesComponent.adapters) {
        	try {
				if (TablesComponent.getTableInterpreter(adapter).canHandle(resource.toURI().toURL(), parameters)) {
					return true;
				}
			} catch (MalformedURLException e) {
				// move on
			}
        }
        
        return false; // TODO TableAdapter.fileExtensions.contains(MiscUtilities.getFileExtension(resource));
    }

    @Override
    public Collection<File> getAllFilesForResource(File file) {
        return Lists.newArrayList(file);
    }

	@Override
	public Map<String, Object> describeResource(IResource resource) {
		Map<String, Object> ret = new LinkedHashMap<>();
		// TODO
		return ret;
	}
}
