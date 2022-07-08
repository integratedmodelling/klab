package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IReducible;
import org.integratedmodelling.klab.api.documentation.views.IDocumentationView;
import org.integratedmodelling.klab.api.documentation.views.ITableView;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.provenance.IArtifact.ValuePresentation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.documentation.extensions.table.ExcelView;
import org.integratedmodelling.klab.documentation.extensions.table.TableArtifact;
import org.integratedmodelling.klab.documentation.extensions.table.TableView;
import org.integratedmodelling.klab.documentation.style.StyleDefinition;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
import org.integratedmodelling.klab.utils.Utils;

/**
 * This is the table that gets into states when a IArtifact.Type.TABLE is the
 * representation type in a state. The table is in fact a multiple-key hash with
 * one and only one value column, and is optimized for size and speed of
 * comparison. Like anything that is stored in a state, it can be reduced to a
 * single atomic value (IReducible interface).
 * <p>
 * The TableProcessor inheritance gives it the ability of complex table stying
 * and transformation when compiled, if a style has been associated to the data,
 * normally through a {@code @style} annotation on the observing model.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class TableValue extends TableProcessor implements IReducible {

	Map<String, Metadata> metadata = new HashMap<>();
	MultiKeyMap<String, Object> data;
	Map<String, ICodelist> codelists = new HashMap<>();
	Object scalar;
	private StyleDefinition style;

	// compiled views indexed by media type
	private Map<String, ITableView> compiledViews = new HashMap<>();

	private TableValue() {
		super(null, null, null, null);
	}

	/**
	 * Just a few parameters
	 * 
	 * @param data       the data, either a list of values or a list of lists, each
	 *                   a row indexed by allFields
	 * @param allFields  may be more than keyFields + valueField; those that aren't
	 *                   in there must be aggregated when keys are same at reduce()
	 * @param keyFields  fields whose identity we need to preserve
	 * @param valueField the key holding the value to report at reduce()
	 * @param codelists  any codelists for the key fields
	 * @param aggregator an aggregator to use when the keys match for multiple
	 *                   values (or there are no keys)
	 * @param style      any style definition associated to the generating model
	 * @param scope      the scope so that we can access the session state for
	 *                   template substitution etc.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public TableValue(List<Object> data, List<String> allFields, Set<String> keyFields, String valueField,
			Map<String, ICodelist> codelists, Aggregator aggregator, StyleDefinition style,
			IContextualizationScope scope, ILocator locator) {

		super(style, valueField, aggregator, scope);

		List<String> keyList = new ArrayList<>();

		this.codelists = codelists;

		List<Object> scalarData = null;
		boolean first = true;

		if (this.style != null && this.style.get("keep") instanceof Collection) {
			for (Object o : (Collection<?>) this.style.get("keep")) {
				keyFields.add(o.toString());
			}
		}

		for (Object o : data) {

			if (keyFields != null && !keyFields.isEmpty() && o instanceof List && valueField != null) {
				if (this.data == null) {
					this.data = new MultiKeyMap<>();
				}

				List<?> row = (List<?>) o;
				int i = 0;
				Object value = null;
				List<String> keys = new ArrayList<>();
				for (String field : allFields) {
					if (keyFields.contains(field)) {
						if (first) {
							keyList.add(field);
						}
						keys.add(row.get(i) == null ? "null" : row.get(i).toString());
					}
					if (valueField.equals(field)) {
						value = row.get(i);
					}
					i++;
				}

				MultiKey<String> key = new MultiKey<>(keys.toArray(new String[keys.size()]));
				if (this.data.containsKey(key)) {
					Object current = this.data.get(key);
					if (current instanceof List) {
						((List<Object>) current).add(value);
					} else {
						List<Object> crn = new ArrayList<>();
						crn.add(current);
						crn.add(value);
						this.data.put(key, crn);
					}
				} else {
					this.data.put(key, value);
				}

			} else {
				if (scalarData == null) {
					scalarData = new ArrayList<>();
				}
				scalarData.add(o);
			}

			first = false;
		}

		if (scalarData != null) {
			this.scalar = scalarData;
		}

		if (aggregator != null) {
			if (scalar != null) {
				if (scalar instanceof Collection) {
					scalar = aggregator.aggregate((Collection<?>) scalar, locator);
				}
			} else if (this.data != null) {
				for (Entry<MultiKey<? extends String>, Object> entry : this.data.entrySet()) {
					if (entry.getValue() instanceof Collection) {
						entry.setValue(aggregator.aggregate((Collection<?>) entry.getValue(), locator));
					}
				}
			}

			super.setKeyFields(keyList);
		}
	}

	@Override
	public boolean isEmpty() {
		return scalar == null && (data == null || data.isEmpty());
	}

	public int[] size() {
		return null;
	}

	public ICodelist getCodelist(String columnId) {
		return this.codelists == null ? null : this.codelists.get(columnId);
	}

	public IMetadata getColumnMetadata(String columnId) {
		return metadata.get(columnId);
	}

	@Override
	public Object reduce(Class<?> cls, boolean forceReduction, ILocator locator) {

		if (isEmpty()) {
			return null;
		}

		if (scalar != null) {
			return Utils.asType((scalar instanceof Collection && forceReduction && aggregator != null
					? aggregator.aggregate((Collection<?>) scalar, locator)
					: scalar), cls);
		} else if (this.data != null && this.data.size() == 1) {
			return this.data.get(this.data.keySet().iterator().next());
		} else if (this.data != null && forceReduction && aggregator != null) {
			return aggregator.aggregate(this.data.values(), locator);
		}

		return this;

	}

	public static TableValue empty() {
		return new TableValue();
	}

	@Override
	public ValuePresentation getValuePresentation() {
		return ValuePresentation.TABLE;
	}

	public IDocumentationView getCompiledView(String mediaType) {

		ITableView ret = this.compiledViews.get(mediaType);
		if (ret == null) {

			if (TableArtifact.HTML_MEDIA_TYPE.equals(mediaType)) {
				ret = new TableView();
			} else if (TableArtifact.EXCEL_MEDIA_TYPE.equals(mediaType)) {
				ret = new ExcelView();
			}

			if (ret == null) {
				throw new KlabValidationException("table view: media type " + mediaType + " is not supported");
			}

			int sheetId = ret.sheet(this.getId());
			int hTable = ret.table(getTitle(), sheetId);
			int hHeader = ret.header(hTable);
			int hRow = ret.newRow(hHeader);
			for (String key : getKeyFields()) {
				int cell = ret.newHeaderCell(hRow, 1, false);
				ret.write(cell, key, Double.NaN, IKnowledgeView.Style.BOLD);
			}
			int cell = ret.newHeaderCell(hRow, 1, false);
			ret.write(cell, valueField, Double.NaN, IKnowledgeView.Style.BOLD);

			int hBody = ret.body(hTable);
			for (Map<String, Object> rDesc : getRows()) {
				hRow = ret.newRow(hBody);
				for (String key : getKeyFields()) {
					ret.write(ret.newCell(hRow), rDesc.get(key), Double.NaN, getStyle(key));
				}
				ret.write(ret.newCell(hRow), rDesc.get(valueField), Double.NaN, getStyle(valueField));
			}

			this.compiledViews.put(mediaType, ret);
		}
		return ret;
	}

	public String getId() {
		// TODO FIXME this will be the same if the artifact contains multiple table
		// values. We should add an ID linked to the locator used for extraction, or
		// (better) to the signature of the filters that have generated the value.
		return scope.getTargetArtifact().getId();
	}

	// TODO use style
	private Set<IKnowledgeView.Style> getStyle(String key) {
		Set<IKnowledgeView.Style> style = new HashSet<>();
		// TODO
		return style;
	}

	public List<Map<String, Object>> getRows() {
		List<Map<String, Object>> ret = new ArrayList<>();
		if (scalar != null) {
			if (scalar instanceof Collection) {
				for (Object value : (Collection<?>) scalar) {
					Map<String, Object> row = new HashMap<>();
					row.put(valueField, value);
					ret.add(row);
				}
			} else {
				Map<String, Object> row = new HashMap<>();
				row.put(valueField, scalar);
				ret.add(row);
			}
		}
		for (Entry<MultiKey<? extends String>, Object> row : data.entrySet()) {
			Map<String, Object> rowData = new HashMap<>();
			int index = 0;
			for (String key : getKeyFields()) {
				rowData.put(key, row.getKey().getKey(index++));
			}
			rowData.put(valueField, row.getValue());
			ret.add(rowData);
		}
		return ret;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, scalar);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableValue other = (TableValue) obj;
		return Objects.equals(data, other.data) && Objects.equals(scalar, other.scalar);
	}

	public Collection<ExportFormat> getExportFormats() {
		List<ExportFormat> ret = new ArrayList<>();
		ret.add(new ExportFormat("Excel worksheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"xlsx", "xlsx"));
		return ret;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> cls) {

		if (Table.class.isAssignableFrom(cls)) {
		    Table ret = new Table();
			compile(getRows(), ret);
			return (T) ret;
		}

		return null;
	}

	@Override
	protected String getValueLabel(String targetField, Object value) {
		ICodelist codelist = getCodelist(targetField);
		if (codelist != null) {
			String ret = codelist.getDescription(value);
			if (ret != null) {
				return ret;
			}
		}
		return super.getValueLabel(targetField, value);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return super.getLabel();
	}

	
	
}
