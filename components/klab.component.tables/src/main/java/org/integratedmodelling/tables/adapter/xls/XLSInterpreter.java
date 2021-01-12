package org.integratedmodelling.tables.adapter.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.URLUtils;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.tables.AbstractTable;
import org.integratedmodelling.tables.TableInterpreter;
import org.integratedmodelling.tables.adapter.TableValidator;

public class XLSInterpreter extends TableInterpreter {

	@Override
	public Type getType(IResource resource, IGeometry geometry) {

		return null;
	}

	@Override
	public void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
			IContextualizationScope context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildResource(IParameters<String> userData, org.integratedmodelling.klab.api.data.IResource.Builder ret,
			IMonitor monitor) {

		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL(userData.get(TableValidator.FILE_URL, String.class));
		} catch (MalformedURLException e1) {
			throw new KlabIOException(e1);
		}

		File file = URLUtils.getFileForURL(url);

		if ("csv".equals(MiscUtilities.getFileExtension(file))) {
			ingestCSV(file, ret, userData, monitor);
		} else {
			ingestXSL(file, ret, userData, monitor);
		}

	}

	private void ingestXSL(File file, IResource.Builder builder, IParameters<String> userData, IMonitor monitor) {
		// TODO Auto-generated method stub

		Boolean hasHeaders = userData.contains("hasHeaders") ? "true".equals(userData.get("hasHeaders")) : null;
		int sheet = userData.contains("sheet") ? Integer.parseInt(userData.get("sheet").toString()) : 0;

		try (InputStream excelFile = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(excelFile)) {

			Sheet datatypeSheet = workbook.getSheetAt(sheet);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					// getCellTypeEnum shown as deprecated for version 3.15
					// getCellTypeEnum ill be renamed to getCellType starting from version 4.0
					if (currentCell.getCellType() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + "--");
					} else if (currentCell.getCellType() == CellType.NUMERIC) {
						System.out.print(currentCell.getNumericCellValue() + "--");
					}

				}
				System.out.println();

			}
		} catch (Throwable e) {
			builder.addError(e);
		}
	}

	private void ingestCSV(File file, IResource.Builder builder, IParameters<String> userData, IMonitor monitor) {

		// null means we don't know. Will be known only at revalidation after import.
		Boolean hasHeaders = userData.contains("hasHeaders") ? "true".equals(userData.get("hasHeaders")) : null;

		/*
		 * for each column, make an attribute; type may be number or string (check
		 * lexically), if the second line matches a number and the first doesn't, it's a
		 * header.
		 */

		GeometryBuilder gbuilder = Geometry.builder();

		// TODO get the charset and format from resource parameters, these below are
		// defaults
		CSVRecord first = null;
		int row = 0;
		int columns = -1;
		int rtot = 0;

		// build columns as we go, skipping any empty ones.
		Map<String, Type> columnTypes = new LinkedHashMap<>();
		boolean checkHeaders = false;

		try (CSVParser parser = CSVTable.getParser(file, userData)) {

			for (CSVRecord record : parser) {

				rtot++;

				if (isEmpty(record)) {
					continue;
				}

				if (columns < 0) {
					columns = record.size();
				}

				if (first == null) {
					first = record;
					if ((checkHeaders = !isNumeric(record))) {
						continue;
					}
				}

				int n = 0;
				for (String value : record) {
					String header = checkHeaders ? first.get(n).replaceAll("\\s+", "_").toLowerCase() : ("c" + n);
					setType(header, value, columnTypes);
					n++;
				}

				row++;
			}

		} catch (Throwable e) {
			builder.addError(e);
		}

		int n = 0, usable = 0;
		for (String key : columnTypes.keySet()) {
			Type type = columnTypes.get(key);
			if (type != null) {
				builder.withAttribute(key, type, false, true);
				builder.withParameter("column." + key + ".index", n);
				builder.withParameter("column." + key + ".mapping", "");
				usable++;
			}
			n++;
		}

		builder.withParameter("rows.total", rtot);
		builder.withParameter("rows.data", row);
		builder.withParameter("columns.total", columnTypes.size());
		builder.withParameter("columns.data", usable);
		builder.withParameter("headers.columns", checkHeaders);
		builder.withParameter("headers.rows", false);

		builder.withParameter("format.encoding", "");
		builder.withParameter("format.source", "DEFAULT");
		builder.withParameter("format.nodata", "");
		builder.withParameter("format.lineseparator", "");
		builder.withParameter("format.delimiter", ",");
		builder.withParameter("format.trimspaces", "false");
		builder.withParameter("format.quote", "\"");

		builder.withParameter("time.encoding", "");
		builder.withParameter("space.encoding", "");

		builder.withParameter("resource.type", "csv");
		builder.withParameter("resource.file", MiscUtilities.getFileName(file));

		builder.withGeometry(gbuilder.build());

	}

	private void setType(String header, String example, Map<String, Type> columnTypes) {

		if (columnTypes.get(header) == null) {
			if (example != null && !example.trim().isEmpty()) {
				Type type = getType(example);
				columnTypes.put(header, type);
			} else {
				columnTypes.put(header, null);
			}
		}
	}

	private Type getType(String s) {
		if (NumberUtils.encodesDouble(s)) {
			return Type.NUMBER;
		} else if (s.toLowerCase().equals("true") || s.toLowerCase().equals("false")) {
			return Type.BOOLEAN;
		}
		return Type.TEXT;
	}

	// true if ALL the row elements are numbers
	private boolean isNumeric(CSVRecord record) {
		for (String val : record) {
			if (!val.trim().isEmpty() && !NumberUtils.encodesDouble(val)) {
				return false;
			}
		}
		return true;
	}

	private boolean isEmpty(CSVRecord record) {
		for (String val : record) {
			if (!val.trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean canHandle(URL resource, IParameters<String> parameters) {
		if (resource == null) {
			// TODO check URLs
			return false;
		}
		return XLSAdapter.fileExtensions.contains(MiscUtilities.getFileExtension(resource.toString()));
	}

	@Override
	public ITable<?> getTable(IResource resource, IGeometry geometry) {
		if ("csv".equals(resource.getParameters().get("resource.type"))) {
			return new CSVTable(resource);
		}
		return null;
	}

}

class CSVTable extends AbstractTable<Object> {

	CSVParser parser_;
	private boolean skipHeader;
	private File file;

	// TODO use mapDB
	List<List<Object>> table_ = null;

	public static CSVParser getParser(File file, Map<String, Object> resourceParameters) {

		String encoding = "UTF-8";
		if (resourceParameters.containsKey("format.encoding")
				&& !resourceParameters.get("format.encoding").toString().isEmpty()) {
			encoding = resourceParameters.get("format.encoding").toString();
		}
		// TODO add nodata, source, separators, trim (boolean) - see CSVFormat
		// asciidocs.
		if (resourceParameters.containsKey("format.source")
				&& !resourceParameters.get("format.source").toString().isEmpty()) {
			// TODO
		}
		if (resourceParameters.containsKey("format.nodata")
				&& !resourceParameters.get("format.nodata").toString().isEmpty()) {
			// TODO
		}
		if (resourceParameters.containsKey("format.lineseparator")
				&& !resourceParameters.get("format.lineseparator").toString().isEmpty()) {
			// TODO
		}
		if (resourceParameters.containsKey("format.delimiter")
				&& !resourceParameters.get("format.delimiter").toString().isEmpty()) {
			// TODO
		}
		if (resourceParameters.containsKey("format.trimspaces")
				&& !resourceParameters.get("format.trimspaces").toString().isEmpty()) {
			// TODO
		}
		if (resourceParameters.containsKey("format.quote")
				&& !resourceParameters.get("format.quote").toString().isEmpty()) {
			// TODO
		}

		try {
			return CSVParser.parse(file, Charset.forName(encoding), CSVFormat.DEFAULT);
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	private List<List<Object>> getTable() {
		if (table_ == null) {
			table_ = new ArrayList<>();
			for (CSVRecord row : getParser(this.file, this.resource.getParameters())) {
				List<Object> items = new ArrayList<>();
				for (int i = 0; i < row.size(); i++) {
					items.add(row.get(i));
				}
				table_.add(items);
			}
		}
		return table_;
	}

	public CSVTable(IResource resource) {
		super(resource, Object.class);
		this.skipHeader = "true".equals(resource.getParameters().get("headers.columns").toString());
		this.file = ((Resource) resource).getLocalFile("resource.file");
	}

	private CSVTable(CSVTable table) {
		super(table);
		this.skipHeader = table.skipHeader;
		this.file = table.file;
	}

//	public CSVParser getParser() {
//		if (parser_ == null) {
//			parser_ = getParser(this.file, this.resource.getParameters());
//		} 
//		return parser_;
//	}

	@Override
	protected AbstractTable<Object> copy() {
		return new CSVTable(this);
	}

	@Override
	public List<Object> getRowItems(Object rowLocator) {

		List<Object> ret = new ArrayList<>();
		if (rowLocator instanceof Integer) {
			int rown = (Integer) rowLocator;
			if (this.skipHeader) {
				rown++;
			}
			int line = 0;
			for (List<Object> row : getTable()) {
				if (line == rown) {
					for (int col = 0; col < row.size(); col++) {
						Attribute attr = getColumnDescriptor(col);
						Object value = row.get(col);
						if (value == null || value.toString().trim().isEmpty()) {
							value = null;
						}
						ret.add(Utils.asType(value, Utils.getClassForType(attr.getType())));
					}
					break;
				}
				line++;
			}
		}
		return ret;
	}

	@Override
	public List<Object> getColumnItems(Object columnLocator) {

		List<Object> ret = new ArrayList<>();
		Attribute attr = null;
		int column = columnLocator instanceof Integer ? (Integer) columnLocator : -1;
		if (column >= 0) {
			attr = getColumnDescriptor(columnLocator.toString());
			if (attr != null) {
				column = attr.getIndex();
			}
			if (column >= 0) {
				if (attr == null) {
					attr = getColumnDescriptor(column);
					for (List<Object> row : getTable()) {
						Object value = row.get(column);
						if (value == null || value.toString().trim().isEmpty()) {
							value = null;
						}
						ret.add(Utils.asType(value, Utils.getClassForType(attr.getType())));
					}
				}
			}
		}
		return ret;
	}

	@Override
	public Object getItem(Object rowLocator, Object columnLocator) {

		int columnIndex = columnLocator instanceof Integer ? (Integer) columnLocator : -1;
		if (columnIndex < 0) {
			Attribute attr = this.getColumnDescriptor(columnLocator.toString());
			if (attr != null) {
				columnIndex = attr.getIndex();
			}
		}
		if (columnIndex >= 0) {
			List<Object> row = getRowItems(rowLocator);
			return row == null || row.isEmpty() ? null : row.get(columnIndex);
		}

		return null;

	}

}
