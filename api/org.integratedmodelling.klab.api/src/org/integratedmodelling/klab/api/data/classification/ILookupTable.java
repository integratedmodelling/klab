package org.integratedmodelling.klab.api.data.classification;

import java.util.List;

import org.integratedmodelling.klab.api.data.general.ITable;

/**
 * A lookup table matches a table to a lookup strategy and exposes 
 * search methods.
 * 
 * TODO
 * 
 * @author Ferd
 *
 */
public interface ILookupTable extends IDataKey {

	/**
	 * The table we use for lookup. Classifiers are the most general
	 * content type for it.
	 * 
	 * @return
	 */
	ITable<IClassifier> getTable();
	
	
	/**
	 * The variables we look up.
	 * 
	 * @return vars
	 */
	List<String> getArguments();

//	/**
//	 * Lookup values in columnIndex based on matching the other values to the other
//	 * columns. If other values are expressions, run them with the value of each
//	 * column ID (or "$n" if columns are unnamed) in the current row as parameters.
//	 * Values that are not expressions are matched to columns in left to right
//	 * order, skipping the requested result column.
//	 *
//	 * @param columnId
//	 *            the index of the column we want returned
//	 * @param values
//	 *            values or expressions to be matched to the other columns, left to
//	 *            right.
//	 * @return all the objects matching the values.
//	 */
//	List<Object> lookup(int columnId, Object... values);
//
//	/**
//	 * Lookup values in columnIndex based on matching the other values. If other
//	 * values are expressions, run them with the value of each column ID in the
//	 * current row as parameters. Values that are not expressions are matched to
//	 * columns in left to right order, skipping the requested result column.
//	 *
//	 * @param columnId
//	 *            the ID of the column we want returned
//	 * @param match
//	 *            a
//	 *            {@link org.integratedmodelling.klab.api.data.general.IExpression}
//	 *            object.
//	 * @param parameters
//	 *            additional parameters for the expression evaluation.
//	 * @param monitor
//	 *            a
//	 *            {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor}
//	 *            object.
//	 * @return all objects matching the expression once it's run with the passed
//	 *         parameters and row values.
//	 * @throws org.integratedmodelling.klab.exceptions.KlabException
//	 */
//	List<Object> lookup(String columnId, IExpression match, IParameters<String> parameters, IMonitor monitor)
//			throws KlabException;
//
//	/**
//	 * Return all the rows that match IExpression, which may use any of the column
//	 * header IDs.
//	 *
//	 * @param expression
//	 *            a
//	 *            {@link org.integratedmodelling.klab.api.data.general.IExpression}
//	 *            object.
//	 * @return all matching rows
//	 */
//	List<Map<String, Object>> lookup(IExpression expression);
	
}
