package org.integratedmodelling.mca.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.mca.MCAComponent;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.IStakeholder;

/**
 * Builds the MCA objects from a k.LAB context and model and sets back MCA
 * results into the context.
 * 
 * @author ferdinando.villa
 *
 */
public class MCAContext {
	
	private List<IAlternative> alternatives = new ArrayList<>();
	private List<IStakeholder> stakeholders = new ArrayList<>();
	private List<ICriterion> criteria = new ArrayList<>();
	
	public MCAContext(IObservable concordanceObservable, IRuntimeContext context) {
		
		// extract from annotations
		for (IObservable observable : context.getModel().getDependencies()) {
			IAnnotation annotation = getMCAAnnotation(observable);
			if (annotation != null) {
				Object value = annotation.get(IKimAnnotation.DEFAULT_PARAMETER_NAME);
				if (value instanceof Number) {
					// context is stakeholder, absolute single concordance
				} else if (value instanceof Map) {
					// build stakeholders from concepts and weights in map, subjective concordances
				} else {
					// nothing: just tag as criterion and look for stakeholders, make observations
				}
			}
		}
		
		// make missing observations
		
		// if needed, classify map to build alternatives or build them from an artifact
		
	}

	private IAnnotation getMCAAnnotation(IObservable observable) {
		for (IAnnotation annotation : ((Observable)observable).getAnnotations()) {
			if (MCAComponent.criterionAnnotations.contains(annotation.getName())) {
				return annotation;
			}
		}
		return null;
	}

	public List<IAlternative> getAlternatives() {
		return alternatives;
	}

	public List<IStakeholder> getStakeholders() {
		return stakeholders;
	}

	public List<ICriterion> getCriteria() {
		return criteria;
	}
	
	
	
}
