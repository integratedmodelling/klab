package org.integratedmodelling.landcover.clue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.resources.ResourceCalculator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

import nl.alterra.shared.rasterdata.RasterData;
import nl.wur.iclue.model.demand.DemandFactory.DemandValidationType;
import nl.wur.iclue.parameter.EaseOfChange;
import nl.wur.iclue.parameter.LanduseDistributions;
import nl.wur.iclue.parameter.LanduseDistributions.LanduseDistribution;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.Parameters;
import nl.wur.iclue.parameter.SpatialDataset;
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
	private SpatialDataset ageData;
	private IRuntimeScope scope;
	private IProcess process;
	private IGrid grid;

	public KlabCLUEParameters(IParameters<String> parameters, IRuntimeScope scope, IProcess ret, IState ageState) {

		this.scope = scope;
		this.process = ret;

		ISpace space = ret.getScale().getSpace();

		if (!(space instanceof Space && ((Space) space).getGrid() != null)) {
			throw new KlabValidationException("The CLUE model requires a gridded space to run");
		}

		this.grid = ((Space) space).getGrid();

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

		double defaultDeviation = parameters.get("deviation", 0.1);

		for (Landuse landUse : getLanduses()) {

			double deviationValue = defaultDeviation;
			boolean isDeviationArea = false;
			double elasticity = 0;
			List<Pair<Long, Integer>> demandspecs = new ArrayList<>();

			/*
			 * check specs. If not specified for this landuse, use sensible defaults.
			 */
			if (parameters.get("elasticities") != null) {
				for (Object[] row : findMatching(landUse.getConcept(), parameters.get("elasticities"))) {
					if (row[0] instanceof Number) {
						elasticity = ((Number) row[0]).doubleValue();
					}
				}
			}

			if (parameters.get("demand") != null) {

				for (Object[] row : findMatching(landUse.getConcept(), parameters.get("demand"))) {

					long time = 0;
					int cells = 0;

					if (row.length == 1) {

						/*
						 * no year; can be a proportion or an area measurement to conver into cells
						 */
						cells = quantityToCells(row[0]);

					} else if (row.length > 1) {

						/*
						 * should be date/time to convert into time index and a proportion or an area
						 * measurement to conver into cells
						 */
						time = quantityToTime(row[1]);
						cells = quantityToCells(row[2]);
					}

					demandspecs.add(new Pair<Long, Integer>(time, cells));

				}
			}

			if (parameters.get("deviations") != null) {

				for (Object[] row : findMatching(landUse.getConcept(), parameters.get("deviations"))) {

					int dvalue = 10;

					if (row.length == 1 && row[0] instanceof Number) {

						vTypes.put(landUse, DemandValidationType.PERCENTAGE_DEVIATION);

					} else if (row.length == 1 && row[0] instanceof IKimQuantity) {

						// TODO make it cells
						vTypes.put(landUse, DemandValidationType.ABSOLUTE_DEVIATION);

					} else {
						throw new KlabValidationException(
								"Wrong deviation for " + landUse + ": should be a proportion or an area with units");
					}

					dDeviations.put(landUse, dvalue);
				}

			}

			if (demandspecs.isEmpty()) {

				LanduseDistribution demand = new LanduseDistribution();
				demand.setAdministrativeUnit(getAdministrativeUnits().getDatakind().getClasses().iterator().next());
				demand.setArea(0);
				demand.setYear(1);
				demand.setLanduse(landUse);
				demands.add(demand);

			} else {
				for (Pair<Long, Integer> p : demandspecs) {

					long timeslice = 0;
					if (p.getFirst() > 0) {
						ITimeInstant it = new TimeInstant(p.getFirst());
						timeslice = ((Time) ((Time) ret.getScale().getTime()).at(it)).getLocatedOffset();
					}

					LanduseDistribution demand = new LanduseDistribution();
					demand.setAdministrativeUnit(getAdministrativeUnits().getDatakind().getClasses().iterator().next());
					demand.setArea(p.getSecond());
					demand.setYear((int) timeslice + 1);
					demand.setLanduse(landUse);
					demands.add(demand);
				}
			}

			dDeviations.put(landUse, (int) (100.0 * deviationValue));
			vTypes.put(landUse, isDeviationArea ? DemandValidationType.ABSOLUTE_DEVIATION
					: DemandValidationType.PERCENTAGE_DEVIATION);
			landUse.setEaseOfChange(EaseOfChange.findByProbability(elasticity));

		}

		setDemands(demands);
		setDemandValidationTypes(vTypes);
		setDemandDeviations(dDeviations);
		setBaseline(new KLABSpatialDataset(this.lulc));
		setTargetTime(1);

		IKimTable transitionTable = parameters.get("transitions", IKimTable.class);
		List<Conversion> conversions = new ArrayList<>();

		Map<IConcept, Integer> order = new LinkedHashMap<>();

		if (transitionTable != null) {

			// must be square besides the first row
			if (transitionTable.getColumnCount() != transitionTable.getRowCount() + 1) {

			}

			// build order and validate table

		}

		for (IConcept c : order.keySet()) {
			// TODO ensure the datakey contains all mentioned concepts, even if not present
			// in data.
		}

		for (Landuse from : getLanduses()) {

			int fromIndex = findClosest(from.getConcept(), order);

			for (Landuse to : getLanduses()) {

				int toIndex = findClosest(to.getConcept(), order);

				Rule rule = null;
				if (from == to || fromIndex < 0 || toIndex < 0) {
					rule = new Always();
				} else {
					// determine rule from table
				}
				conversions.add(new Conversion(from, to, rule));
			}
		}

		setConversions(conversions);

	}

	private int findClosest(IConcept concept, Map<IConcept, Integer> order) {
		if (order.isEmpty()) {
			return -1;
		}
		return -1;
	}

	private long quantityToTime(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int quantityToCells(Object object) {

		if (object instanceof Number) {
			if (!Range.create(0, 1, true).contains(((Number) object).doubleValue())) {
				throw new KlabValidationException("invalid proportion of area: " + object);
			}
			// TODO must use the total accessible cells, not the grid size
			return (int) ((double) grid.getCellCount() * ((Number) object).doubleValue());
		} else if (object instanceof IKimQuantity) {
			Quantity quantity = Quantity.create(((IKimQuantity) object).getValue(),
					Unit.create(((IKimQuantity) object).getUnit()));
			int ret = (int) (quantity.in(Units.INSTANCE.SQUARE_METERS) / grid.getCell(0).getStandardizedArea());
			return ret == 0 ? 1 : ret;
		}
		return 0;
	}

	/**
	 * Find the matching table entries or map entry for a passed concept. Do a first
	 * pass where the concept is looked for literally; if nothing is found, do a
	 * second pass where the inheritance is used. The object may be a map or a
	 * table; if a map, the concept is matched to the key, otherwise it's matched to
	 * the first column. The returned object(s) are either the map value or the
	 * (full) rows of the table.
	 * 
	 * @param concept the LUC concept to match
	 * @param object  the entry/entries matching the concept
	 * 
	 * @return
	 */
	public List<Object[]> findMatching(IConcept concept, Object object) {

		List<Object[]> ret = new ArrayList<>();
		if (object instanceof Map) {

			for (int i = 0; i < 2; i++) {
				if (i == 0) {
					for (Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
						if (entry.getKey() instanceof IKimConcept) {
							IConcept con = Concepts.c(((IKimConcept) entry.getKey()).toString());
							if (con != null && con.equals(concept)) {
								ret.add(new Object[] { entry.getValue() });
							}
						}
					}
				} else if (i > 0) {
					for (Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
						IConcept con = Concepts.c(((IKimConcept) entry.getKey()).toString());
						if (con != null && concept.is(con)) {
							ret.add(new Object[] { entry.getValue() });
						}
					}
				}

				if (!ret.isEmpty()) {
					return ret;
				}
			}
		} else if (object instanceof IKimTable) {
			for (int i = 0; i < 2; i++) {

				if (i == 0) {
					for (IKimClassifier[] row : ((IKimTable) object).getRows()) {
						if (row[0].getConceptMatch() != null
								&& concept.equals(Concepts.c(row[0].getConceptMatch().toString()))) {
							ret.add(row);
						}
					}
				} else if (i > 0) {
					for (IKimClassifier[] row : ((IKimTable) object).getRows()) {
						if (row[0].getConceptMatch() != null
								&& concept.is(Concepts.c(row[0].getConceptMatch().toString()))) {
							ret.add(row);
						}
					}
				}

				if (!ret.isEmpty()) {
					return ret;
				}
			}
		}
		return ret;
	}

	private Landuses buildLanduses() {
		Landuses ret = new Landuses();
		IDataKey dataKey = this.lulc.getDataKey();
		ret.setDataKey(dataKey);
		setLandUses(ret);
		return ret;
	}

	public SpatialDataset getAgeDataset() {

		if (this.ageData == null) {

			this.ageData = new SpatialDataset();
			this.ageData.setCaption("Age");

			RasterData ageMap = null;

			if (false) {

				/*
				 * TODO if an output or an input is tagged with @age, use that. If it's an
				 * output, set the values to 0 wherever the mask is.
				 */

			} else {

				scope.getMonitor().info("No age state in input or output: initializing age to 0 for all categories");

				IScale ascale = process.getScale().initialization();
				@SuppressWarnings("unchecked")
				IStorage<Number> adata = (IStorage<Number>) Klab.INSTANCE.getStorageProvider()
						.createStorage(IArtifact.Type.NUMBER, ascale, this.scope);

				for (ILocator locator : ascale) {
					adata.put(0, locator);
				}

				ageMap = new KLABRasterData(adata);

			}

			this.ageData.add(ageMap, 0);
		}
		return this.ageData;
	}

}
