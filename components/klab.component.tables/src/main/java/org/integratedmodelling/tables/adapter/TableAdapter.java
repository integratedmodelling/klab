package org.integratedmodelling.tables.adapter;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;

public abstract class TableAdapter implements IResourceAdapter {

	private String subtype;

	protected TableAdapter(String subtype) {
		this.subtype = subtype;
	}
	
	@Override
	public IResourceValidator getValidator() {
		return new TableValidator(subtype);
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new TablePublisher(subtype);
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new TableEncoder(subtype);
	}

	@Override
	public IResourceImporter getImporter() {
		return new TableImporter(subtype);
	}

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

}
