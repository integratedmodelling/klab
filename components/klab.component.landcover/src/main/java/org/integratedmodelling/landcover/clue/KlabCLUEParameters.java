package org.integratedmodelling.landcover.clue;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.data.resources.ResourceCalculator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Parameters;

/**
 * CLUE parameterization built from k.LAB contextualization scope.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabCLUEParameters extends Parameters {

	IResourceCalculator<EnumeratedRealDistribution> suitabilityCalculator = null;
	IResourceCalculator<EnumeratedRealDistribution> transitionCalculator = null;
	IState lulc;

	public KlabCLUEParameters(IParameters<String> parameters, IRuntimeScope scope, IProcess ret, IState ageState) {

		/*
		 * put an empty placeholders to keep the (modified) validator happy. No point
		 * internalizing the logics for suitability calculation so that it uses the 
		 * CLUE logics as long as we can use a suitability model that points back to
		 * the k.LAB environment.
		 */
		setDrivers(new ArrayList<>());

		IConcept target = scope.getTargetSemantics().getType();
		if (target.is(Type.CHANGE)) {
			target = Observables.INSTANCE.getInherentType(target);
		}

		this.lulc = scope.getArtifact(target, IState.class);

		if (this.lulc == null) {
			throw new KlabResourceNotFoundException("CLUE: cannot find target artifact " + target);
		}

		buildLanduses();
		
		/*
		 * TODO read demand and call setDemandValidationType() and demandDeviation() for ALL
		 * landuses; those not mentioned get 0
		 */

		setBaseline(new KLABSpatialDataset(this.lulc));
		setTargetTime((int) ret.getScale().getTime().size());

		if (parameters.containsKey("suitability")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("suitability").toString(),
					scope.getModel() == null ? null : scope.getModel().getNamespace().getProject());
			this.suitabilityCalculator = ResourceCalculator.create(suitability, EnumeratedRealDistribution.class);
		} else if (parameters.containsKey("change")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("change").toString(),
					scope.getModel() == null ? null : scope.getModel().getNamespace().getProject());
			this.transitionCalculator = ResourceCalculator.create(suitability, EnumeratedRealDistribution.class);
		}

	}

	private Landuses buildLanduses() {
		Landuses ret = new Landuses();
		IDataKey dataKey = this.lulc.getDataKey();
		ret.setDataKey(dataKey);
		setLandUses(ret);
		return ret;
	}

}
