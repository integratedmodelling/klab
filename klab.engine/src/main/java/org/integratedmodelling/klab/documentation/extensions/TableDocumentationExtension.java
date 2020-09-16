package org.integratedmodelling.klab.documentation.extensions;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

public class TableDocumentationExtension {

	private IAnnotation definition;
	private IRuntimeScope scope;
	private IObservable target;
	private List<Column> columns;
	private List<Row> rows;
	private List<Split> splits;

	enum SplitType {
		TOTAL, PERIOD, RANGE, SEMANTICS, EXPRESSION, REMAINDER
	};

	/**
	 * Descriptor for one (or more if distributed) column
	 * 
	 * @author Ferd
	 *
	 */
	class Column {
		
		boolean repeats = false;

	}

	/**
	 * Descriptor for one (or more if distributed) row
	 * 
	 * @author Ferd
	 *
	 */
	class Row {
		
		boolean repeats = false;

	}

	/**
	 * Description of a table, potentially on a subset of the data. If TOTAL it
	 * means all of the data, so there is at least one of those.
	 * 
	 * @author Ferd
	 *
	 */
	class Split {

		SplitType type = SplitType.TOTAL;

	}

	public TableDocumentationExtension(IAnnotation definition, IObservable target, IRuntimeScope scope) {
		
		this.definition = definition;
		this.scope = scope;
		this.target = target;
		this.columns = parseColumns(definition.get("columns"));
		this.rows = parseRows(definition.get("rows"));
		this.splits = parseSplits(definition.get("splits"));

	}

	private List<Row> parseRows(Object object) {
		List<Row> ret = new ArrayList<>();
		return ret;
	}

	private List<Column> parseColumns(Object object) {
		List<Column> ret = new ArrayList<>();
		return ret;
	}

	private List<Split> parseSplits(Object object) {
		List<Split> ret = new ArrayList<>();
		if (object == null) {
			ret.add(new Split());
		}
		return ret;
	}

	public String compile() {

		StringBuffer buffer = new StringBuffer(2048);
		
		for (Split split : splits) {
			buffer.append("\n<p>");
			buffer.append(compile(split));
			buffer.append("</p>");
		}

		return buffer.toString();
	}

	private String compile(Split split) {
		// TODO Auto-generated method stub
		return "<b>UNIMPLEMENTED TABLE EXTENSION</b>";
	}

}
