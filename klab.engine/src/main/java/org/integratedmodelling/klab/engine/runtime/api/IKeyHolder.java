package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.data.classification.IDataKey;

/**
 * Convenience interface to tag any state that can be given a {@link IDataKey}.
 * 
 * @author Ferd
 *
 */
public interface IKeyHolder {
	void setDataKey(IDataKey key);
}
