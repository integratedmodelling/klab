package org.integratedmodelling.kim.api;

import java.util.List;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public interface IKimLookupTable extends IKimStatement {

	/**
	 * Return the type of the result column, which must be uniform.
	 * 
	 * @return
	 */
	IArtifact.Type getLookupType();

	/**
	 * The variables used for lookup. TODO these should be here, the rest in a
	 * lower-level IKimTable like in the engine peer class.
	 * 
	 * @return
	 */
	List<String> getArguments();

	IKimTable getTable();

	/**
	 * Return the numeric index of the result column in the table, or -1 if none was
	 * specified (which should not happen).
	 * 
	 * @return
	 */
	int getLookupColumnIndex();

}
