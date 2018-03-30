package org.integratedmodelling.kdl.model;

import org.integratedmodelling.kdl.api.IKdlContextualizer;
import org.integratedmodelling.kdl.kdl.Function;

public class KdlContextualizer extends KdlStatement implements IKdlContextualizer {

    private static final long serialVersionUID = 692420005135910303L;
    
    KdlContextualizer(Function o) {
        super(o);
    }
}
