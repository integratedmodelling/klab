package org.integratedmodelling.klab.api.lang.kim;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.KArtifact;

public interface KKimLookupTable extends KKimStatement {

    public static class Argument {

        public enum Dimension {
            ROW, COLUMN
        }

        public String id;
        public KKimConcept concept;
        // if passed, we're looking at a two-way table where the arguments are matched to a row or a
        // column header.
        public Dimension dimension;
    }

    /**
     * Return the type of the result column, which must be uniform.
     * 
     * @return
     */
    KArtifact.Type getLookupType();

    /**
     * The variables used for lookup. TODO these should be here, the rest in a lower-level IKimTable
     * like in the engine peer class.
     * 
     * @return
     */
    List<Argument> getArguments();

    /**
     * The table itself.
     * 
     * @return
     */
    KKimTable getTable();

    /**
     * If true, the table is a two-way table with classifiers for both rows and columns, and the
     * match must be done by matching them instead of the contents of the table.
     */
    boolean isTwoWay();

    /**
     * If {@link #isTwoWay()} returns true, this will return the classifiers to match the rows.
     * Its return value is undefined (likely null) if the table is not 2-way.
     * 
     * @return
     */
    List<KKimClassifier> getRowClassifiers();

    /**
     * If {@link #isTwoWay()} returns true, this will return the classifiers to match the columns.
     * Its return value is undefined (likely null) if the table is not 2-way.
     * 
     * @return
     */
    List<KKimClassifier> getColumnClassifiers();

    /**
     * Return the numeric index of the result column in the table, or -1 if none was specified
     * (which should not happen unless the table is 2-way).
     * 
     * @return
     */
    int getLookupColumnIndex();

}
