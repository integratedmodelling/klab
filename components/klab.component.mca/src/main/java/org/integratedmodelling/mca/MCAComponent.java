package org.integratedmodelling.mca;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.owl.Observable;

@Component(id = "org.integratedmodelling.mca", version = Version.CURRENT)
public class MCAComponent {

	public static Set<String> criterionAnnotations;
	static {
		criterionAnnotations = new HashSet<>();
		criterionAnnotations.add("cost");
		criterionAnnotations.add("benefit");
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

}
