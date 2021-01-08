package org.integratedmodelling.tables;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.tables.AbstractTable.FilterDescriptor;

/**
 * This matches a dimension in the table (according to specified encodings) to
 * the corresponding index on a table dimension or to the geometric extents
 * corresponding to it. These are computed by TableInterpreter and may be
 * cached.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class DimensionScanner<T> {

	public enum Dimension {
		COLUMN, ROW
	};

	// if not null, this matches a row/column header (according to dimension) to
	// extract a string to match to the extent.
	private Pattern dimensionHeaderMatch;

	// scan this dimension
	private Dimension dimension = Dimension.ROW;
	// we encode this extent
	private Class<?> extent;

	private String columnName;
	private String rowName;

	// cache for computed extents and correspondent dimension indices
	private List<Integer> indices = null;
	private List<T> extents = null;
	private ITable<?> table;

	/*
	 * extents matched through a table dimension will come from a column and be
	 * matched backwards to the column, after going through the mappings.
	 */
	private IResource auxiliaryResource = null;
	/*
	 * mappings are listed in the order they come in the specifications, and will be
	 * matched backwards from the resource to obtain the values to use for
	 * filtering.
	 */
	private List<CodeMapping> mappings = new ArrayList<>();

	/*
	 * for temporal indexing
	 */
	NavigableMap<Long, Integer> temporalDimensions = null;
	IDataArtifact spatialContextualizer = null;

	// result of encoding of the geometry, stored by the TableInterpreter.
	public String encodedDimension = null;

	private String auxiliaryResourceUrn;

	public DimensionScanner(IResource resource, String[] definition, Class<T> cls) {

		this.extent = cls;
		String[] ss = definition[0].split(":");

		switch (ss[0]) {
		case "COLUMN_HEADER":
			this.dimension = Dimension.COLUMN;
			this.dimensionHeaderMatch = Pattern.compile(ss[1]);
			break;
		case "ROW_HEADER":
			this.dimension = Dimension.COLUMN;
			this.dimensionHeaderMatch = Pattern.compile(ss[1]);
			break;
		case "ROW":
			this.dimension = Dimension.ROW;
			this.rowName = ss[1];
			break;
		case "COLUMN":
			this.dimension = Dimension.COLUMN;
			this.columnName = ss[1];
			break;
		default:
			// column
			this.dimension = Dimension.COLUMN;
			this.columnName = ss[0];
		}

		for (int i = 1; i < definition.length; i++) {
			if (Urns.INSTANCE.isUrn(definition[i])) {
				if (this.auxiliaryResource != null) {
					throw new KlabValidationException(
							"cannot have more than one auxiliary resource in a table encoding");
				}
				this.auxiliaryResource = Resources.INSTANCE.resolveResource(definition[i]);
				this.auxiliaryResourceUrn = definition[i];
				if (this.auxiliaryResource == null || !Resources.INSTANCE.isResourceOnline(this.auxiliaryResource)) {
					throw new KlabValidationException("auxiliary resource in table encoding is unknown or offline");
				}
			} else {
				/*
				 * must be a code mapping
				 */
				File mapfile = new File(
						((Resource) resource).getPath() + File.separator + "code_" + definition[i] + ".properties");
				if (mapfile == null || !mapfile.exists()) {
					throw new KlabValidationException(
							"code mapping " + definition[i] + " cannot be matched to a definition");

				}
				mappings.add(new CodeMapping(mapfile));
			}
		}

	}

	public Iterable<T> scanExtents(ITable<?> table) {
		if (this.extents == null || this.table != table) {
			computeMatches(table);
		}
		return this.extents;
	}

	public Iterable<Integer> scanIndices(ITable<?> table) {
		if (this.indices == null || this.table != table) {
			computeMatches(table);
		}
		return this.indices;
	}

	/*
	 * table should come pre-filtered according to any other parameters, we add the
	 * column header filter to produce extent locators
	 */
	private void computeMatches(ITable<?> table) {

		this.table = table;
		this.extents = new ArrayList<T>();
		this.indices = new ArrayList<>();

		if (dimensionHeaderMatch != null) {

			int n = 0;
			for (Object o : table.resetFilters().filter(Filter.Type.COLUMN_HEADER, dimensionHeaderMatch)
					.collectIndices(this.indices)
					.asList(dimension == Dimension.ROW ? -1 : 0, dimension == Dimension.ROW ? 0 : -1)) {
				T extent = extractExtent(o);
				if (extent != null) {
					this.extents.add(extent);
				}
				n++;
			}

		} else if (this.columnName != null) {

		} else if (this.rowName != null) {

		}

	}

	@SuppressWarnings("unchecked")
	private T extractExtent(Object o) {
		if (o != null) {
			if (NumberUtils.encodesInteger(o.toString()) && ITime.class.isAssignableFrom(this.extent)) {
				int year = Integer.parseInt(o.toString());
				return (T) Time.create(year);
			}
		}
		return null;
	}

	public ITable<?> contextualize(ITable<?> table, T extent, IContextualizationScope scope) {

		if (ITime.class.isAssignableFrom(this.extent) && extent instanceof ITime) {

			if (temporalDimensions == null) {
				scanExtents(table);
				this.temporalDimensions = new TreeMap<>();
				for (int i = 0; i < this.extents.size(); i++) {
					ITime text = (ITime) this.extents.get(i);
					int index = this.indices.get(i);
					this.temporalDimensions.put(text.getStart().getMilliseconds(), index);
				}
			}

			/*
			 * find the column(s) or row(s) covering the passed time and add a filter to the
			 * table for that.
			 */
			Entry<Long, Integer> index = this.temporalDimensions
					.floorEntry(((ITime) extent).getStart().getMilliseconds());

			if (index == null) {

				// no way
				table = table.filter(Filter.Type.NO_RESULTS);

			} else {

				/*
				 * all columns with compatible values get in the filter.
				 */
				List<Integer> columns = new ArrayList<>();
				columns.add(index.getValue());
				for (Long other : this.temporalDimensions
						.subMap(index.getKey(), false, ((ITime) extent).getEnd().getMilliseconds(), false).keySet()) {
					columns.add(this.temporalDimensions.get(other));
				}
			}

		} else if (ISpace.class.isAssignableFrom(this.extent) && extent instanceof ISpace) {

			/*
			 * contextualize the resource in space and establish the mappings: column ->
			 * classification -> resource means resource -> inverse classification -> column
			 * filter (to add to any other in the table).
			 */

			if (auxiliaryResource != null) {

				VisitingDataBuilder builder = new VisitingDataBuilder().keepStates(scope.getScale());
				IKlabData data = Resources.INSTANCE.getResourceData(auxiliaryResourceUrn, builder, scope.getScale(),
						scope.getMonitor());
				this.spatialContextualizer = (IDataArtifact) data.getArtifact();
			}
		}

		return table;
	}

	/**
	 * Return the filter that will select dimensions corresponding to the passed
	 * locator. Null means all dimensions OK.
	 * 
	 * @param table
	 * @param locator
	 * @return
	 */
	public Filter locate(ITable<?> table, ILocator locator) {

		if (this.spatialContextualizer != null) {
			Object value = this.spatialContextualizer.get(locator);
			if (value == null) {
				// return no-values filter
				return FilterDescriptor.stop();
			} else if (this.columnName != null) {
				for (int i = mappings.size() - 1; i >= 0; i--) {
					value = mappings.get(i).reverseMap(value);
				}
				return new FilterDescriptor(Filter.Type.COLUMN_MATCH, new Object[] { this.columnName, value });
			}
		} else if (ITime.class.isAssignableFrom(this.extent)) {
			// TODO
		}

		return null;
	}

}
