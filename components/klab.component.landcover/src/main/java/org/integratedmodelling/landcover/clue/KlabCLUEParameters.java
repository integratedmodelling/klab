package org.integratedmodelling.landcover.clue;

import java.awt.Color;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.resources.ResourceCalculator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Pair;

import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
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

	public KlabCLUEParameters(IParameters<String> parameters, IContextualizationScope context) {

		if (parameters.containsKey("suitability")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("suitability").toString(),
					context.getModel() == null ? null : context.getModel().getNamespace().getProject());
			this.suitabilityCalculator = ResourceCalculator.create(suitability, EnumeratedRealDistribution.class);
		} else if (parameters.containsKey("change")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("change").toString(),
					context.getModel() == null ? null : context.getModel().getNamespace().getProject());
			this.transitionCalculator = ResourceCalculator.create(suitability, EnumeratedRealDistribution.class);
		} 

		buildLanduses((IRuntimeScope)context);
	}

	private void buildLanduses(IRuntimeScope scope) {
		
		Landuses ret = new Landuses();
		
		if (scope.getTargetSemantics().getType().is(Type.CHANGE)) {
			IConcept inherent = Observables.INSTANCE.getInherentType(scope.getTargetSemantics().getType());
			this.lulc = scope.getArtifact(inherent, IState.class);
			
			if (this.lulc != null) {
				IDataKey dataKey = this.lulc.getDataKey();
				ret.setDataKey(dataKey);
			}
		}
		
		setLandUses(ret);
	}

}
