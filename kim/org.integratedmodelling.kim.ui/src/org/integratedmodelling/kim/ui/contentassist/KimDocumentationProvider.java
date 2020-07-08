package org.integratedmodelling.kim.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.kim.Annotation;
import org.integratedmodelling.kim.kim.Concept;
import org.integratedmodelling.kim.kim.ConceptReference;
import org.integratedmodelling.kim.kim.Function;
import org.integratedmodelling.kim.kim.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;
import org.integratedmodelling.kim.model.Kim.Validator;
import org.integratedmodelling.klab.api.documentation.IDocumentation;

import com.google.inject.Inject;


public class KimDocumentationProvider implements IEObjectDocumentationProvider {

	@Inject
	private ILabelProvider labelProvider;
	
	@SuppressWarnings("unchecked")
	<T extends EObject> T getEcoreParent(EObject o, Class<T> cls) {
		if (cls.isAssignableFrom(o.getClass())) {
			return (T)o;
		}
		return o.eContainer() == null ? null : getEcoreParent(o.eContainer(), cls);
	}
	
	public String getObservableDocumentation(ObservableSemantics observable) {
		
		String ret = "";
		Validator validator = Kim.INSTANCE.getValidator();
		if (validator != null) {
			IKimObservable obs = Kim.INSTANCE.declareObservable(observable);
			if (obs != null) {
				ret = "Part of observable <b>" + obs.getDefinition() + "</b>";
				String doc = validator.getObservableInformation(obs, true);
				if (doc != null) {
					ret += "\n\n" + doc;
				}
			}
		}
		return ret;
	}
	
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
				String ret = cd.getDocumentation();
				ObservableSemantics observable = getEcoreParent(o, ObservableSemantics.class);
				if (observable != null) {
					String doc = getObservableDocumentation(observable);
					if (doc != null && !doc.isEmpty()) {
						ret += "<p>\n\n" + doc;
					}
				}
				return ret;
			}
		} else if (o instanceof ObservableSemantics) {

			return getObservableDocumentation((ObservableSemantics)o);
			
		} else if (o instanceof Function) {
			Kim.Validator validator = Kim.INSTANCE.getValidator();
			if (validator != null) {
				IPrototype prototype = validator.getFunctionPrototype(((Function)o).getName());
				if (prototype != null) {
					return prototype.getSynopsis(IDocumentation.DOC_HTMLTAGS);
				}
			}
		} else if (o instanceof Annotation) {
            Kim.Validator validator = Kim.INSTANCE.getValidator();
            if (validator != null) {
                IPrototype prototype = validator.getAnnotationPrototype(((Annotation)o).getName().substring(1));
                if (prototype != null) {
                    return prototype.getSynopsis(IDocumentation.DOC_HTMLTAGS);
                }
            }
        }
		return null;
	}

}
