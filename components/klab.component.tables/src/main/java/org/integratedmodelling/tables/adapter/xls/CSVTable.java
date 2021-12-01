package org.integratedmodelling.tables.adapter.xls;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.tables.AbstractTable;

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

	public CSVTable(IResource resource, IMonitor monitor) {
		super(resource, resource.getUrn().replaceAll(":", "_"), Object.class, monitor);
		this.skipHeader = "true".equals(resource.getParameters().get("headers.columns").toString());
		this.file = ((Resource) resource).getLocalFile("resource.file");
	}

	@Override
	protected boolean isOutdated(IResource resource) {
	    String property = resource.getUrn().replaceAll(":", "_") + ".timestamp";
		Long timestamp = Long.parseLong(Resources.INSTANCE.getProperty(property, "0"));
		if (this.file.lastModified() > timestamp) {
			Logging.INSTANCE.info("Rebuilding resource cache from " + this.file);
			Resources.INSTANCE.setProperty(property, "" + this.file.lastModified());
			Resources.INSTANCE.persistProperties();
			((Resource)resource).touch();
			table_ = null;
			getTable();
			Resources.INSTANCE.getCatalog(resource).update(resource, "Rebuild data cache after source file modification");
			return true;
		}
		return false;
	}

	private CSVTable(CSVTable table) {
		super(table);
		this.skipHeader = table.skipHeader;
		this.file = table.file;
	}

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
						ret.add(getValue(value, attr));
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
						ret.add(getValue(value, attr));
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

	@Override
	public Iterator<Iterable<?>> iterator() {

		return new Iterator<Iterable<?>>() {

			CSVParser parser_ = getParser(file, resource.getParameters());
			Iterator<CSVRecord> delegate = null;
			boolean skipped = false;

			@Override
			public boolean hasNext() {
				if (!skipped) {
					skipped = true;
					delegate = parser_.iterator();
					if (skipHeader && delegate.hasNext()) {
						delegate.next();
					}
				}
				boolean ret = delegate.hasNext();

				if (!ret) {
					try {
						parser_.close();
					} catch (IOException e) {
						throw new KlabIOException(e);
					}
				}

				return ret;
			}

			@Override
			public Iterable<?> next() {
				if (!skipped) {
					skipped = true;
					delegate = parser_.iterator();
					if (skipHeader && delegate.hasNext()) {
						delegate.next();
					}
				}
				return delegate.next();
			}
		};
	}

}