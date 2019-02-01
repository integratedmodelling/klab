package org.integratedmodelling.mca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.IStakeholder;

@Component(id = "org.integratedmodelling.mca", version = Version.CURRENT)
public class MCAComponent {

	public static Set<String> criterionAnnotations;

	static {
		criterionAnnotations = new HashSet<>();
		criterionAnnotations.add("cost");
		criterionAnnotations.add("benefit");
	}

	public static enum Method {
		EVAMIX, ELECTRE_I, ELECTRE_Is, ELECTRE_Iv, ELECTRE_II, ELECTRE_III, ELECTRE_IV, ELECTRE_TRI, TOPSIS
	}

	public MCAComponent() {
	}

	/**
	 * Build the value concept for a criterion that will be looked for in each
	 * stakeholder to rank the criterion's importance. It will be a "value of
	 * <i>criterion</i> for assessment of <i>finalTarget</i>".
	 * 
	 * @param criterionObservable
	 * @param targetObservable
	 * @return the finished value concept
	 */
	public static IObservable getCriterionValueObservable(IObservable criterionObservable,
			IObservable targetObservable) {

		return ((Observable) criterionObservable).getBuilder().as(UnarySemanticOperator.VALUE).withGoal(
				((Observable) targetObservable).getBuilder().as(UnarySemanticOperator.ASSESSMENT).buildConcept())
				.buildObservable();

	}

	public static List<IAlternative> rank(List<IAlternative> alternatives, Collection<ICriterion> criteria,
			IStakeholder observer, Method method) {

		// TODO

		return alternatives;
	}

	/**
	 * Extract the elements of a MCA assessment from the passed artifact and put
	 * them in the passed collections. If something goes wrong or the available data
	 * do not support a MCA, warn through the context monitor and return false.
	 * 
	 * @param target
	 * @param alternatives
	 * @param criteria
	 * @param observers
	 * @param method
	 * @param context
	 * @return
	 */
	public static boolean extractAssessment(IArtifact target, List<IAlternative> alternatives,
			Collection<ICriterion> criteria, List<IStakeholder> observers, Method method, IComputationContext context) {
		// TODO Auto-generated method stub
		
		// check out the model for indication of the likely way to go
		boolean outputsHaveCriteria = false;
		boolean dependenciesHaveCriteria = false;
		boolean alternativesHaveCriteria = false;
		
		List<IAnnotation> criterionAnnotations = new ArrayList<>();

		for (int i = 1; i < context.getModel().getObservables().size(); i++) {
			if (getCriterionAnnotation(context.getModel().getObservables().get(i)) != null) {
				outputsHaveCriteria = true;
				criterionAnnotations.add(getCriterionAnnotation(context.getModel().getObservables().get(i)));
			}
		}

		for (IObservable observable :context.getModel().getDependencies()) {
			if (getCriterionAnnotation(observable) != null) {
				dependenciesHaveCriteria = true;
				// can have both but must be different observables
				criterionAnnotations.add(getCriterionAnnotation(observable));
			}
		}
		
		if (!outputsHaveCriteria && !dependenciesHaveCriteria) {
			// must observe those on the alternatives
		}
		
		// scan criteria: lookup in alternatives first
		
		// if alternatives have no states with annotations, alternatives may be in the model's dependencies and 
		// have to be extracted from context states at the alternatives' locations
		
		return false;
	}

	class CriterionDescriptor {
		IObservable observable;
		boolean input;
	}
	
	private static IAnnotation getCriterionAnnotation(IObservable observable) {
		for (IAnnotation annotation : ((Observable)observable).getAnnotations()) {
			if (criterionAnnotations.contains(annotation.getName())) {
				return annotation;
			}
		}
		return null;
	}

}
