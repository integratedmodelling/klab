/*
 * Copyright 2014 Alterra, Wageningen UR
 * 
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package nl.wur.iclue.parameter;

import nl.wur.iclue.parameter.conversion.Conversion;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import nl.wur.iclue.model.demand.DemandFactory.DemandValidationType;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.alterra.shared.datakind.Clazz;

/**
 *
 * @author Johnny te Roller, Peter Verweij
 */
public class Parameters {

	public static final int UNDEFINED = -1;

	private Landuses landuses = null;
	private SpatialDataset baseline = null;
	private SpatialDataset administrativeUnits = null;
	private List<SpatialDataset> drivers = null;
	private LanduseDistributions demands = null;
	private List<Conversion> conversions = null;
	private Long targetTime = SpatialDataset.UNKNOWN_YEAR;
	private Map<Landuse, DemandValidationType> demandValidationTypes = null;
	private Map<Landuse, Integer> demandDeviations = null;
	private Map<Landuse, FocalFilter> focalFilters = null;
	private SuitabilityParameters suitabilityParams = null;

	private final ParameterStatus status;

	public Parameters() {
		super();
		status = new ParameterStatus(this);
	}

	public void setLandUses(Landuses landuses) {
		this.landuses = landuses;
		synchronizeBaselineDataKindFromLanduses();
	}

	public Landuses getLanduses() {
		return this.landuses;
	}

	public void setBaseline(SpatialDataset dataset) {
		this.baseline = dataset;
		synchronizeBaselineDataKindFromLanduses();
	}

	public SpatialDataset getBaseline() {
		return this.baseline;
	}

	private void synchronizeBaselineDataKindFromLanduses() {
		if ((baseline != null) && (landuses != null))
			baseline.setDatakind(landuses.getDataKind());
	}

	public void setAdministrativeUnits(SpatialDataset dataset) {
		this.administrativeUnits = dataset;
	}

	public SpatialDataset getAdministrativeUnits() {
		return administrativeUnits;
	}

	public void setDemands(LanduseDistributions demands) {
		this.demands = demands;
	}

	public LanduseDistributions getDemands() {
		return this.demands;
	}

	public void setSuitabilityParameters(SuitabilityParameters suitabilityParameters) {
		this.suitabilityParams = suitabilityParameters;
	}

	public SuitabilityParameters getSuitabilityParameters() {
		return this.suitabilityParams;
	}

	/**
	 * 
	 * @return list of demands PLUS baseline distribution
	 */
	public LanduseDistributions getLanduseDistributions() {
		LanduseDistributions lud = new LanduseDistributions();
		lud.addAll(createBaselineLanduseDistributionPerAdministrativeUnit());
		lud.addAll(demands);
		return lud;
	}

	private LanduseDistributions createBaselineLanduseDistributionPerAdministrativeUnit() {

		LanduseDistributions result = new LanduseDistributions();

		Map<Integer, Map<Integer, Integer>> tabulateAreas = getBaseline().getRasterData()
				.tabulateCellCount(getAdministrativeUnits().getRasterData());
		for (Entry<Integer, Map<Integer, Integer>> adminUnitEntry : tabulateAreas.entrySet()) {
			Integer adminUnitValue = adminUnitEntry.getKey();
			Clazz adminUnit = getAdministrativeUnits().getDatakind().findByValue(adminUnitValue);

			for (Entry<Integer, Integer> landuseEntry : adminUnitEntry.getValue().entrySet()) {
				Integer landuseValue = landuseEntry.getKey();
				int area = landuseEntry.getValue();
				Landuse landuse = getLanduses().findByValue(landuseValue);

				LanduseDistributions.LanduseDistribution dist = new LanduseDistributions.LanduseDistribution();
				dist.setYear(baseline.getYear());
				dist.setLanduse(landuse);
				dist.setAdministrativeUnit(adminUnit);
				dist.setArea(area);

				result.add(dist);
			}
		}

		return result;
	}

	public List<SpatialDataset> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<SpatialDataset> drivers) {
		this.drivers = drivers;
	}

	public List<Conversion> getConversions() {
		return conversions;
	}

	public void setConversions(List<Conversion> conversions) {
		this.conversions = conversions;
	}

	public long getTargetTime() {
		return targetTime;
	}

	/**
	 * set baseline with its year before setting the target time
	 * 
	 * @param targetTime
	 */
	public void setTargetTime(Long targetTime) {
		if ((baseline != null) && (baseline.isYearKnown()) && (targetTime != null) && (baseline.getYear() < targetTime))
			this.targetTime = targetTime;
	}

	public boolean isValidTargetTime() {
		return (getTargetTime() != SpatialDataset.UNKNOWN_YEAR) && (getTargetTime() > getBaseline().getYear());
	}

	public DemandValidationType getDemandDeviationType(Landuse landuse) {
		if (demandValidationTypes != null) {
			return demandValidationTypes.get(landuse);
		}
		return null;
	}

	public void setDemandValidationTypes(Map<Landuse, DemandValidationType> validationTypes) {
		this.demandValidationTypes = validationTypes;
	}

	public Integer getDemandDeviation(Landuse landuse) {
		if (demandDeviations != null) {
			return demandDeviations.get(landuse);
		}
		return null;
	}

	public void setDemandDeviations(Map<Landuse, Integer> demandDeviations) {
		this.demandDeviations = demandDeviations;
	}

	public Map<Landuse, FocalFilter> getFocalFilters() {
		return focalFilters;
	}

	public void setFocalFilters(Map<Landuse, FocalFilter> filters) {
		this.focalFilters = filters;
	}

	public ParameterStatus getCompletenessStatus() {
		return status;
	}

}
