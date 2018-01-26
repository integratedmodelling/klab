package org.integratedmodelling.klab.documentation;

import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.data.Metadata;

public class Documentation extends Metadata implements IDocumentation {

    public Documentation(IKimMetadata data) {
        super(data);
    }

}
