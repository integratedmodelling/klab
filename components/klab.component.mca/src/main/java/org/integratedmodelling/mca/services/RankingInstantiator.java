package org.integratedmodelling.mca.services;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.mca.MCAComponent;
import org.integratedmodelling.mca.MCAComponent.Method;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.IStakeholder;

public class RankingInstantiator implements IInstantiator, IExpression {

	String targetArtifact = null;
	Method method = Method.ELECTRE_III;

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("#");
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		// TODO parameters
		return new RankingInstantiator();
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		IArtifact target = null;
		if (targetArtifact == null) {
			target = context.getTargetArtifact();
		} else {
			target = context.getArtifact(targetArtifact);
		}

		// must be a group
		if (!(target instanceof ObservationGroup)) {
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
			List<IAlternative> ranked = MCAComponent.rank(alternatives, criteria, observer, method);
			// build and insert comparator in artifact
		}


		// we have no output of our own, so return null
		return null;
	}

}
