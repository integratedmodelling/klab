package org.integratedmodelling.tables.adapter;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;

public abstract class TableAdapter implements IResourceAdapter {

	protected TableAdapter() {
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
	public IResourceCalculator getCalculator(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

}
