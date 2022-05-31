package org.integratedmodelling.klab.api.observations;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.documentation.views.IDocumentationView;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;

/**
 * The compiled artifact created by a IViewModel when contextualized.
 * 
 * @author Ferd
 *
 */
public interface IKnowledgeView extends IArtifact {

	enum AggregationType {
		Aggregator, Differentiator, Comparator, Counter
	}

	enum TargetType {
		AREA, DURATION, QUALITY, NUMEROSITY
	}

	enum Style {
		RIGHT, LEFT, CENTER, BOLD, ITALIC, BG_HIGHLIGHT, FG_HIGHLIGHT,
		/** make 0 columns empty */
		EMPTY,
		/** make empty columns zeros */
		ZERO
		/* TODO borders, font size etc */
	}

	/**
	 * Attribute roles for rows or columns being created, to be followed by their
	 * argument if needed.
	 * 
	 * @author Ferd
	 *
	 */
	enum Attribute {
		HEADER, HEADER_0, HEADER_1, HEADER_2, HEADER_3
	}

	enum ComputationType {

		/**
		 * Sum
		 */
		Sum(Aggregation.SUM),
		/**
		 * Sum
		 */
		Average(Aggregation.MEAN),
		/**
		 * Sum
		 */
		Std(Aggregation.STD),
		/**
		 * Sum
		 */
		Variance(Aggregation.VARIANCE),
		/**
		 * Sum
		 */
		Min(Aggregation.MIN),
		/**
		 * Sum
		 */
		Max(Aggregation.MAX),
		/**
		 * Compute a specific expression in the expression field, to be evaluated at
		 * each pass and whose result is aggregated.
		 */
		Expression(null),
		/**
		 * Compute an expression at the end of everything and only once, based on
		 * results in other cells.
		 */
		Summarize(null);

		Aggregation aggregation;

		ComputationType(Aggregation aggregation) {
			this.aggregation = aggregation;
		}

		public Aggregation getAggregation() {
			return this.aggregation;
		}

		public boolean isAggregation() {
			return this.aggregation != null || this == Summarize;
		}
	}

	/**
	 * A builder for a view, used for now only in custom builders where the
	 * structure is created from the data or other specs, so not a full builder.
	 * 
	 * @author Ferd
	 *
	 */
	interface Builder {

		/**
		 * Create or retrieve a column for a given classifier.
		 * 
		 * @param options classifier, sorting, style, header - any of the enums above.
		 *                If a floating value is passed that does not correspond to a
		 *                parameter for a preceding operator, it's interpreted as the
		 *                classifier, which may be null. If there is no classifier,
		 *                there should be header options.
		 * @return the ID for the column
		 */
		String getColumn(Object... options);

		/**
		 * Create or retrieve a row for a given classifier.
		 * 
		 * @param options classifier, sorting, style, header - any of the enums above.
		 *                If a floating value is passed that does not correspond to a
		 *                parameter for a preceding operator, it's interpreted as the
		 *                classifier, which may be null. If there is no classifier,
		 *                there should be header options.
		 * @return the ID for the row
		 */
		String getRow(Object... options);

		/**
		 * 
		 * @param rowId
		 * @param colId
		 * @param value
		 * @param options
		 * @return
		 */
		void setCell(String rowId, String colId, Object value, Object... options);

		/**
		 * Return the view artifact.
		 * 
		 * @return
		 */
		IKnowledgeView build();

		/**
		 * Define the appearance of empty and nodata cells
		 * 
		 * @param emptyValue
		 * @param noDataValue
		 */
		void setEmptyCells(String emptyValue, String noDataValue);

		/**
		 * Get the IDs of all columns in order of definition
		 * 
		 * @return
		 */
        List<String> getColumnIds();
        
        /**
         * Get the IDs of all rows in order of definition
         * 
         * @return
         */
        List<String> getRowIds();
        
        /**
         * Get the value of the passed cell, or null if the cell isn't set.
         * 
         * @param rowId
         * @param colId
         * @return
         */
        Object getCellValue(String rowId, String colId);

	}

	/**
	 * 
	 * @return
	 */
	String getViewClass();

	/**
	 * 
	 * @return
	 */
	String getName();

	/**
	 * A possibly long caption for the artifact we represent.
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * If a label is specified, return it for user-level referencing and indexing.
	 * 
	 * @return
	 */
	String getLabel();

	/**
	 * Export formats; if empty, no export is possible
	 * 
	 * @return
	 */
	Collection<ExportFormat> getExportFormats();

	/**
	 * Return the compiled view in exportable form as a {@link IDocumentationView}.
	 * 
	 * @param mediaType
	 * @return
	 */
	IDocumentationView getCompiledView(String mediaType);

	/**
	 * 
	 * @param file
	 * @param mediaType
	 * @return
	 */
	boolean export(File file, String mediaType);

	/**
	 * Return a suitable bean for transferring the view to clients. Class is usually
	 * mandatory in each implementation, added here for fluency in calls to avoid
	 * painful typing at the class level.
	 * 
	 * @param <T>
	 * @param cls
	 * @return
	 */
	<T> T getBean(Class<T> cls);

	/**
	 * A view can be given an additional identifier (tied to the "name" parameter in
	 * specifications) that can be the same across groups of related tables and is
	 * used to retrieve a view in documentation, regardless of which specific model
	 * has computed it. This is never null as it defaults to the model name if left
	 * unspecified.
	 * 
	 * @return
	 */
	String getIdentifier();

}
