package org.integratedmodelling.mca.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.mca.MCAComponent;
import org.integratedmodelling.mca.MCAComponent.Method;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.IStakeholder;

public class RankingInstantiator extends AbstractContextualizer implements IInstantiator, IProcessor, IExpression {

	String targetArtifact = null;
	Method method = Method.EVAMIX;
	boolean normalize = true;

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		RankingInstantiator ret = new RankingInstantiator();
		// TODO method
		ret.normalize = Parameters.create(parameters).get("normalize", Boolean.TRUE);
		return ret;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {

		IArtifact target = null;
		if (targetArtifact == null) {
			target = context.getTargetArtifact();
		} else {
			target = context.getArtifact(targetArtifact);
		}

		// must be a group
		if (!(target instanceof IObservationGroup)) {
			context.getMonitor()
					.warn("MCA ranking cannot be performed on an artifact that is not a group of objects. Exiting.");
			return null;
		}

		// Set up the MCA assessment
		List<IAlternative> alternatives = new ArrayList<>();
		List<ICriterion> criteria = new ArrayList<>();
		List<IStakeholder> observers = new ArrayList<>();

		// find criteria and possibly stakeholders
		if (!MCAComponent.extractAssessment(target, alternatives, criteria, observers, method, context)) {
			context.getMonitor().warn(
					"MCA ranking cannot be performed due to inconsistent alternatives, criteria or stakeholders collected from context. Exiting.");
			return null;
		}

		// run MCA
		for (IStakeholder observer : observers) {
			
			 /**
             * Stakeholder switching logics. TODO: this will not affect the criteria
             * if they are also subjective.
             */
            if (observers.size() > 1) {
//                // if state is not an observed set, wrap it into one
//                if (!(target instanceof ISubjectiveObservation)) {
//                    target = ((ObservationGroup)target).reinterpret(/* TODO */ null);
//                }
                // set the observer in the stakeholder in the set so that all further assignments reflect
                // its perspective
                ((ObservationGroup) target).setObserver(observer.getSubject());
            }

			// save scores
			Map<String, Double> scores = new HashMap<>();
			for (IAlternative a : MCAComponent.rank(alternatives, criteria, observer, method, normalize, context.getMonitor())) {
				scores.put(a.getId(), a.getScore());
			}
			
			// set in metadata
			for (IArtifact artifact : target) {
				artifact.getMetadata().put(MCAComponent.SCORE_METADATA_PROPERTY, scores.get(artifact.getId()));
			}
			
			// TODO if @concordance was given and method has concordance, observe state in artifact
			
			// build and insert comparator in artifact, descending order
			((ObservationGroup)target).setComparator(new Comparator<IArtifact>() {
				
				@Override
				public int compare(IArtifact arg0, IArtifact arg1) {
					double s0 = arg0.getMetadata().get(MCAComponent.SCORE_METADATA_PROPERTY, Double.NaN);
					double s1 = arg1.getMetadata().get(MCAComponent.SCORE_METADATA_PROPERTY, Double.NaN);
					return Double.isNaN(s0) || Double.isNaN(s1) ? 0 : Double.compare(s1, s0);
				}
			});
		}


		// we have no output of our own, so return null
		return null;
	}

}
