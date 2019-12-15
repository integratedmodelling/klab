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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.rasterdata.IMaskeable;
import nl.alterra.shared.rasterdata.RasterData;
import nl.alterra.shared.rasterdata.RasterDataFactory;

/**
 * E.g. a dataset: caption: protected areas filename: c:\data\... E.g another
 * dataset caption: Precipitation for year '2000': filename c:\data\precip for
 * year '2010': filename c:\data\precip-later
 * 
 * @author Peter Verweij, Johnny te Roller
 */
public class SpatialDataset {

	private static final String ERROR_EXPECTED_STABLE_REGIONS = "Cannot cut spatial dataset by region. Regions change over time. Not supported";
	private static final String ERROR_YEAR_ALREADY_INCLUDED = "Cannot add year %d for %s. It is already defined";
	public static final int UNKNOWN_YEAR = -1;
	private String caption;
	private DataKind datakind;

	protected final Map<Integer, RasterData> map; // year, rasterdata

	public SpatialDataset() {
		map = new LinkedHashMap<>();
		datakind = new DataKind();
	}

	public void setDatakind(DataKind datakind) {
		this.datakind = datakind;
	}

	public void add(String filename, int year) {
		RasterData rasterData = RasterDataFactory.createRasterData(filename);
		add(rasterData, year);
	}

	public void add(RasterData rasterData, int year) {
		if (map.containsKey(year))
			throw new RuntimeException(String.format(ERROR_YEAR_ALREADY_INCLUDED, year, getCaption()));
		map.put(year, rasterData);
	}

	public Set<Integer> getYears() {
		return map.keySet();
	}

	public RasterData getRasterData(int year) {
		return map.get(year);
	}

	public RasterData getLastRasterData() {
		int year = getLastYear();
		return getRasterData(year);
	}

	/**
	 * gets the filename given a year. e.g. if the available years are: 2000, 2010,
	 * 2020. A request for year 2009 will return the file for 2000.
	 * 
	 * @param year
	 * @return a filename, or null if the year is below the range of available years
	 */
	public RasterData getMostRecentRasterData(int year) {

		// sort the years
		List<Integer> years = getSortedYears();

		int yearIndex = 0;
		if (year < years.get(yearIndex))
			return null;
		else if (year >= years.get(years.size() - 1))
			yearIndex = years.size() - 1;
		else {
			for (int i = 0; i < years.size() - 2; i++) {
				if (year >= years.get(i) && (year < years.get(i + 1))) {
					yearIndex = i;
					break;
				}
			}
		}

		return getRasterData(years.get(yearIndex));
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public RasterData getRasterData() {
		if (map.size() == 1)
			return map.values().iterator().next();
		else
			return null;
	}

	public void setRasterData(RasterData rasterdata) {
		setRasterData(rasterdata, UNKNOWN_YEAR);
	}

	public void setFilename(String filename) {
		setFilename(filename, UNKNOWN_YEAR);
	}

	public void setRasterData(RasterData rasterdata, int year) {
		map.clear();
		add(rasterdata, year);
	}

	public void setFilename(String filename, int year) {
		map.clear();
		add(filename, year);
	}

	public Integer getYear() {
		if (map.size() == 1)
			return map.keySet().iterator().next();
		else
			return null;
	}

	private List<Integer> getSortedYears() {
		ArrayList<Integer> years = new ArrayList<>();
		years.addAll(getYears());
		Collections.sort(years);
		return years;
	}

	public int getFirstYear() {
		return getSortedYears().get(0);
	}

	public int getLastYear() {
		List<Integer> years = getSortedYears();
		return years.get(years.size() - 1);
	}

	public boolean isYearKnown() {
		if (map.isEmpty())
			return false;
		else if (map.size() > 1)
			return true;
		else
			return UNKNOWN_YEAR != getYear();
	}

	public DataKind getDatakind() {
		return datakind;
	}

	public int getCellCount(Category clz, int year) {

		RasterData rasterData = getRasterData(year);

		int categoryValue = Integer.parseInt(clz.getValueAsString());
		Integer cellCount = rasterData.createValueCountTable().get(categoryValue);

		if (cellCount == null)
			cellCount = 0;

		return cellCount;
	}

	public void removeByYear(int year) {
		map.remove(year);
	}

	/**
	 * perform a shallow clone
	 * 
	 * @return
	 */
	@Override
	public SpatialDataset clone() {
		SpatialDataset result = new SpatialDataset();
		result.caption = this.caption;
		result.datakind = this.datakind; // refer to same datakind instance. Needed as the equals method and hashcode of
											// Clazz instances will be different when using deep clone
		for (Entry<Integer, RasterData> entry : this.map.entrySet())
			result.map.put(entry.getKey(), entry.getValue());
		return result;
	}

	public SpatialDataset cut(SpatialDataset regions, Category regionOfInterest) {
		
		// determine region rasterdata
		Integer regionYear = regions.getYear();
		if (regionYear == null)
			throw new RuntimeException(ERROR_EXPECTED_STABLE_REGIONS);
		RasterData regionData = regions.getRasterData();
		int regionValue = Integer.parseInt(regionOfInterest.getValueAsString());

		SpatialDataset result = null;
		if (this instanceof IMaskeable) {
			
			/*
			 * k.LAB added
			 */
			((IMaskeable) this).setMask(regions, regionOfInterest);
			result = this;

		} else {

			/*
			 * Original CLUE logics, quite wasteful if the context is aware of the
			 * "administrative units" and requiring complex cloning if subclasses of
			 * SpatialDataset are used.
			 */

			result = new SpatialDataset();
			result.caption = this.caption;
			// TODO the stuff below should be obviated by making classes proper comparables.
			result.datakind = this.datakind; // refer to same datakind instance. Needed as the equals method and
												// hashcode of
												// Clazz instances will be different when using deep clone
			for (Entry<Integer, RasterData> entry : this.map.entrySet()) {
				RasterData regionMap = entry.getValue().cut(regionData, regionValue);
				result.map.put(entry.getKey(), regionMap);
			}
		}

		return result;
	}

}
