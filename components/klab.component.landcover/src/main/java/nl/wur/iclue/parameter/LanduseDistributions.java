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
 */package nl.wur.iclue.parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.alterra.shared.datakind.Clazz;
import nl.wur.iclue.parameter.LanduseDistributions.LanduseDistribution;
import nl.wur.iclue.parameter.Landuses.Landuse;

/**
 *
 * @author Johnny te Roller, Peter Verweij
 */

public class LanduseDistributions extends ArrayList<LanduseDistribution> {

	private static final long serialVersionUID = -4718770523141687944L;

	private final String ERROR_YEAR_OUT_OF_RANGE = "Year %d out of range {%d..%d}";
	private final String ERROR_INTERPOLATE_AREA = "Cannot interpolate '%s' demand for %d from previous demand/baseline %d (%d) and next demand %d (%d)";
	private final long INVALID_YEAR = -1;

	public List<Long> getSortedYears(Clazz administrativeUnit) {
		List<Long> years = new ArrayList<>();
		for (LanduseDistribution dist : this) {
			if ((administrativeUnit.equals(dist.getAdministrativeUnit())) && (!years.contains(dist.getYear())))
				years.add(dist.getYear());
		}
		Collections.sort(years);
		return years;
	}

	/**
	 * 
	 * @param administrativeUnit
	 * @param year
	 * @return map of <landuse, areaAmount>
	 */
	public Map<Landuse, Integer> getAreaAmounts(Clazz administrativeUnit, long year) {
		List<Long> years = getSortedYears(administrativeUnit);
		if (years.contains(year)) {
			return getDefinedAreaAmounts(administrativeUnit, year);
		} else {
			return getInterpolatedAreaAmounts(administrativeUnit, getPreviousDefinedYear(years, year), year,
					getNextDefinedYear(years, year));
		}
	}

	public boolean setAreaAmount(Clazz administrativeUnit, int year, Landuse landuse, int area) {
		for (LanduseDistribution distribution : this) {
			if ((administrativeUnit.equals(distribution.getAdministrativeUnit())) && (year == distribution.getYear())
					&& (landuse.equals(distribution.getLanduse()))) {
				distribution.setArea(area);
				return true;
			}
		}
		return false;
	}

	public boolean removeAreaAmounts(Clazz administrativeUnit, long year) {
		List<Long> sortedYears = getSortedYears(administrativeUnit);
		if (!sortedYears.contains(year))
			return false;

		List<LanduseDistribution> distributionsToBeRemoved = new ArrayList<>();
		for (LanduseDistribution distribution : this) {
			if ((administrativeUnit.equals(distribution.getAdministrativeUnit())) && (year == distribution.getYear()))
				distributionsToBeRemoved.add(distribution);
		}

		if (distributionsToBeRemoved.size() <= 0)
			return false;

		for (LanduseDistribution dist : distributionsToBeRemoved)
			remove(dist);
		return true;
	}

	private long getPreviousDefinedYear(List<Long> definedYears, long requestedYear) {
		throwExceptionIfOutsideRange(definedYears, requestedYear);

		int index = 0;
		long result = definedYears.get(index);
		while (definedYears.get(index) < requestedYear) {
			result = definedYears.get(index++);
		}
		return result;
	}

	private long getNextDefinedYear(List<Long> definedYears, long requestedYear) {
		throwExceptionIfOutsideRange(definedYears, requestedYear);

		int index = definedYears.size() - 1;
		long result = definedYears.get(index);
		while (definedYears.get(index) > requestedYear) {
			result = definedYears.get(index--);
		}
		return result;
	}

	private void throwExceptionIfOutsideRange(List<Long> definedYears, long requestedYear) {
		long minYear = definedYears.get(0);
		long maxYear = definedYears.get(definedYears.size() - 1);
		if ((requestedYear < minYear) || (requestedYear > maxYear))
			throw new RuntimeException(String.format(ERROR_YEAR_OUT_OF_RANGE, requestedYear, minYear, maxYear));
	}

	private Map<Landuse, Integer> getDefinedAreaAmounts(Clazz administrativeUnit, long year) {
		Map<Landuse, Integer> result = new HashMap<>();
		for (LanduseDistribution distribution : this) {
			if ((administrativeUnit.equals(distribution.getAdministrativeUnit())) && (year == distribution.getYear()))
				result.put(distribution.getLanduse(), distribution.getArea());
		}

		return result;
	}

	private Map<Landuse, Integer> getInterpolatedAreaAmounts(Clazz administrativeUnit, long previousDefinedyear,
			long year, long nextDefinedYear) {
		// Linear interpolation is implemented here (might use enum with various
		// interpolation methods)
		Map<Landuse, Integer> result = new HashMap<>();
		Map<Landuse, Integer> differenceAreaAmount = new HashMap<>();
		double fraction = (double) (year - previousDefinedyear) / (nextDefinedYear - previousDefinedyear);
		Map<Landuse, Integer> previous = getDefinedAreaAmounts(administrativeUnit, previousDefinedyear);
		Map<Landuse, Integer> next = getDefinedAreaAmounts(administrativeUnit, nextDefinedYear);
		for (Entry<Landuse, Integer> previousEntry : previous.entrySet()) {
			Landuse lu = previousEntry.getKey();
			Integer previousArea = previousEntry.getValue();
			Integer nextArea = next.get(previousEntry.getKey());
			if ((previousArea == null) || (nextArea == null))
				throw new RuntimeException(String.format(ERROR_INTERPOLATE_AREA, lu.getCaption(), year, previousArea,
						previousDefinedyear, nextArea, nextDefinedYear));
			int differenceArea = nextArea - previousArea;
			differenceAreaAmount.put(previousEntry.getKey(), differenceArea);
			int area = (int) Math.round(previousArea + (differenceArea) * fraction);
			result.put(previousEntry.getKey(), area);
		}

		// do we have round errors? -> correct them!
		int totalArea = getTotalArea(next);
		int totalAreaAfterInterpolation = getTotalArea(result);
		if (totalArea != totalAreaAfterInterpolation)
			redistributeRoundErrors(result, totalArea - totalAreaAfterInterpolation, differenceAreaAmount);

		return result;
	}

	private void redistributeRoundErrors(Map<Landuse, Integer> result, int amountToRedistribute,
			Map<Landuse, Integer> differenceAreaAmount) {
		// TODO: really redistribute instead of putting into the landuse which changes
		// the most area
		Landuse lu = null;
		int difArea = 0;
		for (Landuse landuse : differenceAreaAmount.keySet()) {
			if (differenceAreaAmount.get(landuse) > difArea) {
				lu = landuse;
				difArea = differenceAreaAmount.get(lu);
			}
		}
		result.put(lu, result.get(lu) + amountToRedistribute);
	}

	private int getTotalArea(Map<Landuse, Integer> areaAmounts) {
		int totalArea = 0;
		for (Integer areaOfSomeLanduse : areaAmounts.values())
			totalArea += areaOfSomeLanduse;
		return totalArea;
	}

	public int getTotalArea(Clazz administrativeUnit, long year) {
		return getTotalArea(getAreaAmounts(administrativeUnit, year));
	}

	public static class LanduseDistribution {
		private long year;
		private int area;
		private Landuse landuse;
		private Clazz administrativeUnit;

		public long getYear() {
			return year;
		}

		public LanduseDistribution setYear(long year) {
			this.year = year;
			return this;
		}

		public int getArea() {
			return area;
		}

		public LanduseDistribution setArea(int area) {
			this.area = area;
			return this;
		}

		public Landuse getLanduse() {
			return landuse;
		}

		public LanduseDistribution setLanduse(Landuse landuse) {
			this.landuse = landuse;
			return this;
		}

		public Clazz getAdministrativeUnit() {
			return administrativeUnit;
		}

		public LanduseDistribution setAdministrativeUnit(Clazz administrativeUnit) {
			this.administrativeUnit = administrativeUnit;
			return this;
		}

		@Override
		public String toString() {
			return getAdministrativeUnit().getCaption() + ", " + getYear() + " -> " + getLanduse().getCaption() + " of "
					+ getArea() + " cells";
		}

	}

}
