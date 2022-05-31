package org.integratedmodelling.klab.api.extensions;

import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * These are loadable extensions with a k.DL prototype that can be use to
 * provide custom strategy to compile a table. To do that, use 'using' and the
 * function that creates it in a table definition.
 * 
 * @author Ferd
 *
 */
public interface ITableCompiler {

	/**
	 * Initialize with the parameters from the call, also passing the table
	 * definition and the scope. Note that this is called on reading (scope = null)
	 * AND at the moment of use on a new object (scope = the scope of
	 * contextualization).
	 * 
	 * @param parameters
	 * @param tableDefinition
	 * @param scope
	 */
	void initialize(IParameters<String> parameters, Map<?,?> tableDefinition, IContextualizationScope scope);
	
	/**
	 * Build the artifact using the passed builder.
	 * 
	 * @param builder
	 */
	void compile(IKnowledgeView.Builder builder);

}
