package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.Aggregation;

/**
 * A table transformation is applied on demand to a table that is accompanied by
 * a TableStructure record to turn it into another table. The 'table' command
 * set in the CLI is used to apply transformations to existing tables.
 * Transformations can also be applied using the table.transform verb in
 * k.Actors.
 * 
 * @author Ferd
 *
 */
public class TableTransformation {

	static public enum OperationType {
		COPY, AGGREGATION, SEMANTIC_REDUCTION, TRANSPOSITION, FILTERING, RECALCULATION
	}

	static public enum TargetType {
		ROW, COLUMN
	}

	public static class Operation {

		private OperationType operationType;
		private Aggregation aggregation;
		private TargetType targetType;
		private String target;
		private List<Object> parameters = new ArrayList<>();

		public OperationType getOperationType() {
			return operationType;
		}

		public void setOperationType(OperationType operationType) {
			this.operationType = operationType;
		}

		public Aggregation getAggregation() {
			return aggregation;
		}

		public void setAggregation(Aggregation aggregation) {
			this.aggregation = aggregation;
		}

		public TargetType getTargetType() {
			return targetType;
		}

		public void setTargetType(TargetType targetType) {
			this.targetType = targetType;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		public List<Object> getParameters() {
			return parameters;
		}

		public void setParameters(List<Object> parameters) {
			this.parameters = parameters;
		}

	}

	// optional, substitute $ with the original file name and # with the original
	// filename w/o extension
	private String outputFile;
	private List<Operation> operations = new ArrayList<>();

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

}
