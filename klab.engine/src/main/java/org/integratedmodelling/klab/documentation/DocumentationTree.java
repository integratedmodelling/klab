package org.integratedmodelling.klab.documentation;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Path;

/**
 * The structured version of the report, to substitute the simpler report based on a document view.
 * Uses the beans from the rest package directly. Each report holds a doctree with info on
 * everything that has gone through it. The tree is serializable to JSON. Eventually this may
 * substitute the Report object in its entirety.
 * 
 * @author Ferd
 *
 */
public class DocumentationTree {

    Map<String, DocumentationItem> items = new HashMap<>();
    
    public DocumentationTree() {
        // 
    }
    
    public DocumentationTree(IRuntimeScope context, ISession identity) {
        // TODO Auto-generated constructor stub
    }

    public void add(Object o) {
        System.out.println("Got shitteron " + Path.getLast(o.getClass().getCanonicalName(), '.') + ": " + o);
    }
    
}
