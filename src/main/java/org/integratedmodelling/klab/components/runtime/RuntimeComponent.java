package org.integratedmodelling.klab.components.runtime;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;

/**
 * This component provides the default dataflow execution runtime and the 
 * associated services.
 * 
 * @author Ferd
 *
 */
@Component(id = "runtime", version = Version.CURRENT)
public class RuntimeComponent implements IRuntimeProvider {

    
    // TODO run dataflows
}
