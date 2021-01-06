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
import org.integratedmodelling.tables.adapter.TableValidator;

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

	/**
	 * Build the first version of a resource from user input, using the userData as
	 * a guide. If a file was dropped, the userData will be empty except for the
	 * field {@link TableValidator.FILE_URL}.
	 * 
	 * @param userData
	 * @param ret
	 * @param monitor
	 */
	void buildResource(IParameters<String> userData, IResource.Builder ret, IMonitor monitor);

	/**
	 * When dropping a file, the first interpreter that responds true will be used
	 * to define the interpreter and the adapter, so do this right (also mind the
	 * sequence in TableComponent if ambiguities need to be resolved).
	 * 
	 * @param resource
	 * @param parameters
	 * @return
	 */
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

	/**
	 * Called when parameters include the {space|time}.encoding fields after user
	 * editing of the resource data. Results should be cached if practical and
	 * expensive to compute.
	 * 
	 * @param resource
	 * @param parameters
	 * @return
	 */
	IGeometry recomputeGeometry(IResource resource, Map<String, String> parameters);
}
