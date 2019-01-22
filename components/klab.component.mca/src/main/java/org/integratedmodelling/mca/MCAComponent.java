package org.integratedmodelling.mca;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;

@Component(id="org.integratedmodelling.mca", version=Version.CURRENT)
public class MCAComponent {

    public static Set<String> criterionAnnotations;
    static {
    	criterionAnnotations = new HashSet<>();
    	criterionAnnotations.add("cost");
    	criterionAnnotations.add("benefit");
    }

	public MCAComponent() {
        // TODO Auto-generated constructor stub
    }
}
