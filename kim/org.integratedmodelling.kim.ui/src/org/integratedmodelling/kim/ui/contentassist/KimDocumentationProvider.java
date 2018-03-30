package org.integratedmodelling.kim.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.integratedmodelling.kim.kdecl.Concept;
import org.integratedmodelling.kim.kdecl.ConceptReference;
import org.integratedmodelling.kim.kdecl.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;


public class KimDocumentationProvider implements IEObjectDocumentationProvider {

	@Override
	public String getDocumentation(EObject o) {
		
	    String name = null;
	    if (o instanceof ConceptReference && ((ConceptReference) o).getName() != null) {
	        name = ((ConceptReference) o).getName();
	    } else if (o instanceof Concept && ((Concept) o).getName() != null && ((Concept) o).getName().getName() != null) {
	        name =  ((Concept) o).getName().getName();
	    }
		
		if (name != null) {
			ConceptDescriptor cd = Kim.INSTANCE.getConceptDescriptor(name);
			if (cd != null) {
				return cd.getDocumentation();
			}
		} else if (o instanceof ObservableSemantics/* && o.eContainer() instanceof ModelBodyStatement*/) {
//		    return "ZIO CAROTA ";
		}
		return null;
	}

}
