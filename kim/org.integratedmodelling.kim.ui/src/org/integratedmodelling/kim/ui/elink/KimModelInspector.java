package org.integratedmodelling.kim.ui.elink;

import org.eclipse.emf.ecore.EObject;

/**
 * Utility class to inspect the context of an element so that actions such
 * as suggestions can be defined appropriately.
 * 
 * @author ferdinando.villa
 *
 */
public class KimModelInspector {

    public static class Context {
        public String namespace;
        public boolean withinModel;
        public boolean withinObserver;
        public boolean instantiatorModel;
    }
    
    public static Context getContextFor(EObject object) {
        return null;
    }
    
}
