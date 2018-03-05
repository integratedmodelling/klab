package org.integratedmodelling.klab.data.encoding;

import javax.annotation.Nullable;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.knowledge.IConcept;

public enum Decoder {

    INSTANCE;

    /**
     * Decode a data response into the corresponding raw object. Pass an expected geometry for validation (or
     * possibly rescaling) and optionally a concept for semantic validation if the data come with semantic
     * constraints.
     * 
     * @param data
     * @return
     */
    IObjectData decode(IKlabData data, IGeometry geometry, @Nullable IConcept semantics) {
        return null;
    }

}
