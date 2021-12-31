package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.IReducible;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter.Type;
import org.integratedmodelling.klab.api.documentation.views.IDocumentationView;
import org.integratedmodelling.klab.api.documentation.views.ITableView;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.ValuePresentation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.documentation.extensions.table.ExcelView;
import org.integratedmodelling.klab.documentation.extensions.table.TableArtifact;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Style;
import org.integratedmodelling.klab.documentation.extensions.table.TableView;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Table.Column;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Utils;
import org.springframework.util.StringUtils;

/**
 * This is the table that gets into states when a IArtifact.Type.TABLE is the
 * representation type in a state. The table is in fact a multiple-key hash with
 * one and only one value column, and is optimized for size and speed of
 * comparison. The keys can be of any type, while T describes the type of the
 * value. Like anything that is in a state, it can be reduced to a single atomic
 * value for display or conventional export. (IReducible interface TBI).
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class TableValue implements ITable<Object>, IReducible {

	Map<String, Metadata> metadata = new HashMap<>();
	MultiKeyMap<String, Object> data;
	Map<String, ICodelist> codelists = new HashMap<>();
	Object scalar;
	private Aggregator aggregator;

	// compiled views indexed by media type
	private Map<String, ITableView> compiledViews = new HashMap<>();
	private List<String> keyFields = new ArrayList<>();
	private String valueField;
	private IArtifact.Type valueType = IArtifact.Type.NUMBER;

	private TableValue() {
	}

	/**
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
	 */
	public TableValue(List<Object> data, List<String> allFields, Set<String> keyFields, String valueField,
			Map<String, ICodelist> codelists, Aggregator aggregator) {

		this.codelists = codelists;
		this.aggregator = aggregator;
		this.valueField = valueField;

		List<Object> scalarData = null;
		boolean first = true;
		
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
							this.keyFields.add(field);
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
					scalar = aggregator.aggregate((Collection<?>) scalar);
				}
			} else if (this.data != null) {
				for (Entry<MultiKey<? extends String>, Object> entry : this.data.entrySet()) {
					if (entry.getValue() instanceof Collection) {
						entry.setValue(aggregator.aggregate((Collection<?>) entry.getValue()));
					}
				}
			}
		}
	}

	@Override
	public Iterator<Iterable<?>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getDimensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> E get(Class<E> cls, IContextualizationScope scope, Object... locators) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> asList(Object... locators) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<Object> filter(Type target, Object... locators) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<Object> filter(Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getRowItems(Object... rowLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getColumnItems(Object... columnLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getItem(Object rowLocator, Object columnLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<Object> collectIndices(List<Integer> indices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute getColumnDescriptor(String columnName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute getColumnDescriptor(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<?> resetFilters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return scalar == null && (data == null || data.isEmpty());
	}

	@Override
	public List<Filter> getFilters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<Object> contextualize(IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] size() {
		return null;
	}

	@Override
	public ICodelist getCodelist(String columnId) {
		return this.codelists == null ? null : this.codelists.get(columnId);
	}

	public IMetadata getColumnMetadata(String columnId) {
		return metadata.get(columnId);
	}

	@Override
	public Object reduce(Class<?> cls, boolean forceReduction) {

		if (isEmpty()) {
			return null;
		}

		if (scalar != null) {
			return Utils.asType((scalar instanceof Collection && forceReduction && aggregator != null
					? aggregator.aggregate((Collection<?>) scalar)
					: scalar), cls);
		} else if (this.data != null && this.data.size() == 1) {
			return this.data.get(this.data.keySet().iterator().next());
		} else if (this.data != null && forceReduction && aggregator != null) {
			return aggregator.aggregate(this.data.values());
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
	        for (String key : keyFields) {
                int cell = ret.newHeaderCell(hRow, 1, false);
                ret.write(cell, key, Double.NaN, Style.BOLD);
	        }
            int cell = ret.newHeaderCell(hRow, 1, false);
            ret.write(cell, valueField, Double.NaN, Style.BOLD);

            int hBody = ret.body(hTable);
			for (Map<String, Object> rDesc : getRows()) {
	            hRow = ret.newRow(hBody);
	            for (String key : this.keyFields) {
	                ret.write(ret.newCell(hRow), rDesc.get(key), Double.NaN, getStyle(key));
	            }
                ret.write(ret.newCell(hRow), rDesc.get(valueField), Double.NaN, getStyle(valueField));
	        }

			this.compiledViews.put(mediaType, ret);
		}
		return ret;
	}

	// TODO use style 
    private Set<TableCompiler.Style> getStyle(String key) {
        Set<TableCompiler.Style> style = new HashSet<>();
        // TODO
        return style;
    }

    // TODO use style for sorting and filtering
	public List<Map<String, Object>> getRows() {
		List<Map<String, Object>> ret = new ArrayList<>();
		if (scalar != null) {
			if (scalar instanceof Collection) {
				for (Object value : (Collection<?>)scalar) {
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
			for (String key : keyFields) {
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

	public String getTitle() {
		// TODO Link to table decorations. Use template substitution {space} {time}
		return "SEEA-CF: Fossil fuel energy assets, Belgium 2010";
	}

	public String getId() {
		// TODO Link to table decorations. Use template substitution {space} {time}
		return "ID";
	}

	public Collection<ExportFormat> getExportFormats() {
		List<ExportFormat> ret = new ArrayList<>();
		ret.add(new ExportFormat("Excel worksheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"xlsx", "xlsx"));
		return ret;
	}

	public String getLabel() {
		// TODO Auto-generated method stub
		return "Energy assets table";
	}

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> cls) {

        if (Table.class.isAssignableFrom(cls)) {
            Table ret = new Table();
            compile(ret);
            return (T) ret;
        }

        return null;
    }

    /**
     * Compile to bean. FIXME the logics when cells are not computed should be in one place only -
     * at worst, just identify the places where that is called and call compileView (text) to ensure
     * it's done.
     * 
     * @param ret
     */
    private void compile(Table ret) {

        ret.setNumberFormat(getNumberFormat() == null ? "%.2f" : getNumberFormat());
//        ret.setDocumentationIdentifier(getDocumentationIdentifier());

        for (String key : keyFields) {
            Column column = new Column();
            column.setId(key);
            column.setTitle(StringUtils.capitalize(key) /* TODO use codelist */);
            column.setType(IArtifact.Type.TEXT);
            ret.getColumns().add(column);
        }

        Column column = new Column();
        column.setId(valueField);
        column.setTitle(StringUtils.capitalize(valueField) /* TODO use codelist */);
        column.setType(valueType);
        ret.getColumns().add(column);
        
		for (Map<String, Object> rDesc : getRows()) {
	        Map<String, String> rowData = new HashMap<>();
            for (String key : this.keyFields) {
            	rowData.put(key, rDesc.get(key) == null ? "" : rDesc.get(key).toString());
            }
            // TODO number format
            rowData.put(valueField, rDesc.get(valueField) == null ? "" : rDesc.get(valueField).toString());
            ret.getRows().add(rowData);
        }

		if (Configuration.INSTANCE.isEchoEnabled()) {
            System.out.println(JsonUtils.printAsJson(ret));
        }
    }

//    private String getDocumentationIdentifier() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private String getNumberFormat() {
		// TODO Auto-generated method stub
		return null;
	}
}
