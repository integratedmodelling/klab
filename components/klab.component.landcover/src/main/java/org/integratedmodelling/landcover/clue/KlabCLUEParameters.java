package org.integratedmodelling.landcover.clue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.data.resources.ResourceCalculator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

import nl.wur.iclue.model.demand.DemandFactory.DemandValidationType;
import nl.wur.iclue.parameter.LanduseDistributions;
import nl.wur.iclue.parameter.LanduseDistributions.LanduseDistribution;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.Parameters;
import nl.wur.iclue.parameter.SuitabilityParameters;
import nl.wur.iclue.parameter.conversion.Always;
import nl.wur.iclue.parameter.conversion.Conversion;
import nl.wur.iclue.parameter.conversion.Rule;
import nl.wur.iclue.suitability.SuitabilityCalculationMethod;

/**
 * CLUE parameterization built from k.LAB contextualization scope. Uses the CLUE
 * setup with 1-based years tracking the temporal transition offset instead of
 * the actual year, so that transitions will happen at whatever pace is defined
 * for the k.LAB model.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabCLUEParameters extends Parameters {

	IResourceCalculator<?> suitabilityCalculator = null;
	IResourceCalculator<?> transitionCalculator = null;
	IState lulc;

	public KlabCLUEParameters(IParameters<String> parameters, IRuntimeScope scope, IProcess ret, IState ageState) {

		/*
		 * create a single administrative unit for the entire context. This is woven
		 * deep into the CLUE logics, despite some points where null is accepted, so we
		 * need to make a dummy one here. We use it to mask the region boundaries.
		 */
		setAdministrativeUnits(new KLABContextDataset(scope));

		/*
		 * put an empty placeholders to keep the (modified) validator happy. No point
		 * internalizing the logics for suitability calculation so that it uses the CLUE
		 * logics as long as we can use a suitability model that points back to the
		 * k.LAB environment.
		 */
		setDrivers(new ArrayList<>());

		/*
		 * suitability parameters
		 */
		if (parameters.containsKey("suitability")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("suitability").toString(),
					scope.getModel() == null ? null : scope.getModel().getNamespace().getProject());
			this.suitabilityCalculator = ResourceCalculator.create(suitability, Object.class);
		} else if (parameters.containsKey("change")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("change").toString(),
					scope.getModel() == null ? null : scope.getModel().getNamespace().getProject());
			this.transitionCalculator = ResourceCalculator.create(suitability, Object.class);
		}

		/*
		 * use unimplemented BAYESIAN_STATISTIC for the k.LAB resource extension. Should
		 * be more generic and add a KLAB-specific class or use a generalized function
		 * dictionary thing. For now the function dictionary has the LULC class as a
		 * key, which is not good if using a probability distribution unless we fill in
		 * the first and use the others from a constant (which begs the question of when
		 * to reinitialize the array).
		 */
		SuitabilityParameters suitabilityParameters = new SuitabilityParameters(
				SuitabilityCalculationMethod.BAYESIAN_STATISTICS,
				this.suitabilityCalculator == null ? this.transitionCalculator : this.suitabilityCalculator);

		setSuitabilityParameters(suitabilityParameters);

		IConcept target = scope.getTargetSemantics().getType();
		if (target.is(Type.CHANGE)) {
			target = Observables.INSTANCE.getInherentType(target);
		}

		this.lulc = scope.getArtifact(target, IState.class);

		if (this.lulc == null) {
			throw new KlabResourceNotFoundException("CLUE: cannot find target artifact " + target);
		}

		buildLanduses();

		Map<Landuse, DemandValidationType> vTypes = new HashMap<>();
		Map<Landuse, Integer> dDeviations = new HashMap<>();
		LanduseDistributions demands = new LanduseDistributions();

		Map<?, ?> elasticityMap = null;
		ITable<?> elasticityTable = null;
		Map<?, ?> demandMap = null;
		ITable<?> demandTable = null;
				
		for (Landuse landUse : getLanduses()) {

			LanduseDistribution demand = new LanduseDistribution();

			/*
			 * TODO check specs. If not specified for this landuse, default as below
			 */
			if (elasticityMap != null) {
				
			} else if (elasticityTable != null) {
				
			}

			if (demandMap != null) {
				
			} else if (demandTable != null) {
				
			}
			
			vTypes.put(landUse, DemandValidationType.PERCENTAGE_DEVIATION);
			dDeviations.put(landUse, 100);

			/*
			 * default demand is 0
			 */
			demand.setArea(0);
			demand.setLanduse(landUse);
			demand.setYear(1);
			demands.add(demand);
		}

		setDemands(demands);
		setDemandValidationTypes(vTypes);
		setDemandDeviations(dDeviations);
		setBaseline(new KLABSpatialDataset(this.lulc));
		setTargetTime(1);

		ITable<?> transitionTable = null;
		List<Conversion> conversions = new ArrayList<>();
		
		for (Landuse from : getLanduses()) {
			for (Landuse to : getLanduses()) {
				Rule rule = null;
				if (from == to) {
					rule = new Always();
				} else {
					if (transitionTable != null) {
						/*
						 * TODO find in table. If not, use Always.
						 */
					} else {
						rule = new Always();
					}
				}
				conversions.add(new Conversion(from, to, rule));
			}
		}
		
		setConversions(conversions);
		
	}

	private Landuses buildLanduses() {
		Landuses ret = new Landuses();
		IDataKey dataKey = this.lulc.getDataKey();
		ret.setDataKey(dataKey);
		setLandUses(ret);
		return ret;
	}

}
