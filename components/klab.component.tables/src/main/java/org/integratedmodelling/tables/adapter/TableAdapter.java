package org.integratedmodelling.tables.adapter;

import java.util.Set;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;

import com.google.common.collect.Sets;

@ResourceAdapter(type = "table", version = Version.CURRENT)
public class TableAdapter implements IResourceAdapter {

    /**
     * All recognized primary file extensions - CSV and the Excel formats.
     */
    public static Set<String> fileExtensions = Sets.newHashSet("csv", "xls", "xlsx");
    
    @Override
    public String getName() {
        return "table";
    }

    @Override
    public IResourceValidator getValidator() {
        return new TableValidator();
    }

    @Override
    public IResourcePublisher getPublisher() {
        return new TablePublisher();
    }

    @Override
    public IResourceEncoder getEncoder() {
        return new TableEncoder();
    }

    @Override
    public IResourceImporter getImporter() {
        return new TableImporter();
    }

    @Override
    public IPrototype getResourceConfiguration() {
        return new Prototype(Dataflows.INSTANCE
                .declare(getClass().getClassLoader()
                        .getResource("components/org.integratedmodelling.table/adapter/table.kdl"))
                .getActuators().iterator().next(), null);
    }

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

}
