package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.documentation.views.IDocumentationView;
import org.integratedmodelling.klab.api.documentation.views.ITableView;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.provenance.Artifact;

public abstract class AbstractTableArtifact extends Artifact implements IKnowledgeView {

	private Map<String, ITableView> compiledViews = new HashMap<>();
	
	public static final String EXCEL_MEDIA_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String HTML_MEDIA_TYPE = "text/html";
    public static final String TEXT_PLAIN_MEDIA_TYPE = "text/plain";
    public static final String MARKDOWN_MEDIA_TYPE = "text/markdown";
    
	protected abstract IDocumentationView getCompiledView(ITableView view, int sheetId);
	
	/**
	 * TODO turn into a private function that takes a table view and a sheet handle,
	 * so it can be generalized to >1 sheets.
	 */
	@Override
	public IDocumentationView getCompiledView(String mediaType) {

		ITableView ret = this.compiledViews.get(mediaType);
		if (ret == null) {

			if (HTML_MEDIA_TYPE.equals(mediaType)) {
				ret = new TableView();
			} else if (EXCEL_MEDIA_TYPE.equals(mediaType)) {
				ret = new ExcelView();
			} else if (MARKDOWN_MEDIA_TYPE.equals(mediaType)) {
                ret = new MarkdownView(false);
			} else if (TEXT_PLAIN_MEDIA_TYPE.equals(mediaType)) {
                ret = new MarkdownView(true);
            }

			if (ret == null) {
				throw new KlabValidationException("table view: media type " + mediaType + " is not supported");
			}

			getCompiledView(ret, ret.sheet(getLabel()));
			this.compiledViews.put(mediaType, ret);
		}
		return ret;
	}
	/**
	 * Export multiple tables as one XLS file with multiple sheets.
	 * 
	 * @param tables
	 * @param file   if null, a temp file is created (erased after shutdown)
	 * @return
	 */
	public static File exportMultiple(Collection<AbstractTableArtifact> tables, File file) {

		if (file == null) {
			try {
				file = File.createTempFile("tables", ".xlsx");
				file.deleteOnExit();
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}

		ITableView ret = new ExcelView();
		Map<String, Integer> sheetNames = new HashMap<String, Integer>();
		for (AbstractTableArtifact table : tables) {
			StringBuffer sheetName = new StringBuffer(table.getLabel());
			if (sheetNames.containsKey(table.getLabel())) {
				int index = sheetNames.get(table.getLabel()) + 1;
				sheetName.append(" (").append(index).append(")");
				sheetNames.put(table.getLabel(), index);
			} else {
				sheetNames.put(table.getLabel(), 0);
			}
			table.getCompiledView(ret, ret.sheet(sheetName.toString()));
		}

		try (OutputStream out = new FileOutputStream(file)) {
			ret.write(out);
		} catch (Throwable e) {
			throw new KlabIOException(e);
		}

		return file;
	}
}
