package org.integratedmodelling.tables;

import java.net.URL;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public interface ITableInterpreter {

	IArtifact.Type getType(IResource resource, IGeometry geometry);

	/**
	 * Return the resource in the form of a table, in whatever form makes it quicker
	 * to scan and use it. The geometry may be null, which returns the entire table.
	 * 
	 * @param resource
	 * @param geometry
	 * @return
	 */
	ITable<?> getTable(IResource resource, IGeometry geometry);

	void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
			IContextualizationScope context);

	void buildResource(IParameters<String> userData, IResource.Builder ret, IMonitor monitor);

	boolean canHandle(URL resource, IParameters<String> parameters);

	/**
	 * Create a property file with a standard, empty categorization for a dimension,
	 * to be filled in by users. Category files start with "code_" and have
	 * extension .properties. They contain properties named category.XXX for each
	 * unique code admitted in the dimension, initialized at the empty string.
	 * 
	 * @param resource
	 * @param parameters
	 */
	void categorize(IResource resource, IParameters<String> parameters);
}
