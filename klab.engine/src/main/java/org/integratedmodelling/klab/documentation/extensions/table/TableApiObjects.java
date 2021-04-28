package org.integratedmodelling.klab.documentation.extensions.table;

import java.util.Map;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Dimension;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Filter;

import groovy.lang.GroovyObjectSupport;

/**
 * Collection of beans for the table Groovy API, included if mentioned to access columns,
 * classifiers and the like from computed expressions.
 * 
 * @author Ferd
 *
 */
public class TableApiObjects {

    public static class TableCell extends GroovyObjectSupport {

        private ILocator locator;
        private Object target;

        public TableCell(TableArtifact table, Object target, Dimension column, Dimension row, ILocator locator) {
            this.locator = locator;
            this.target = target;
        }

        /**
         * True if classifier of passed dimension matches current target
         * 
         * @param what
         * @return
         */
        public boolean match(Object what) {
            IConcept target = ((TableDimension) what).getConcept();
            return (this.target == null && target == null) || (this.target != null && this.target.equals(target));
        }

        /**
         * True if the current target is the one passed and the target at the passed time was
         * different.
         * 
         * @param what
         * @param locateFrom
         * @return
         */
        public boolean changedInto(Object what, Object locateFrom) {

            IConcept target = ((TableDimension) what).getConcept();
            if (target == null) {
                return false;
            }
            Object comparison = ((TableDimension) what).getState(((IScale) this.locator).at((ILocator) locateFrom));
            if (comparison != null && !(comparison instanceof IConcept)) {
                return false;
            }

            return target.equals(this.target) && (comparison == null || !comparison.equals(target));
        }

        /**
         * True if the current target is not the one passed but it was it at the passed time.
         * 
         * @param what
         * @param locateFrom
         * @return
         */
        public boolean changedFrom(Object what, Object locateFrom) {

            IConcept target = ((TableDimension) what).getConcept();
            Object comparison = ((TableDimension) what).getState(((IScale) this.locator).at((ILocator) locateFrom));
            if (!(comparison instanceof IConcept)) {
                return false;
            }

            // also works if the current target is nodata
            return !target.equals(this.target) && comparison.equals(target);
        }

        /**
         * Return true if the target of the "from" dimension is the previous state at the locator
         * and the current state is the target of the "to" dimension.
         * <p>
         * If two equal states are passed, the answer is yes if the value stayed the same.
         * 
         * @param from
         * @param to
         * @param locateFrom
         * @return
         */
        public boolean changed(Object from, Object to, Object locateFrom) {

            IConcept cTo = ((TableDimension) to).getConcept();
            IConcept cFrom = ((TableDimension) from).getConcept();

            if (!cTo.equals(this.target)) {
                return false;
            }

            Object oFrom = ((TableDimension) from).getState(((IScale) this.locator).at((ILocator) locateFrom));
            return oFrom != null && oFrom.equals(cFrom);
        }

        public Object getProperty(String state) {
            switch(state) {
            case "target":
                return target;
            }
            return null;
        }
    }

    public static class TableDimension {

        private Dimension dimension;
        private IConcept concept;
        private IObservation observation;
        private boolean checked;
        private Map<ObservedConcept, IObservation> catalog;

        public TableDimension(Dimension dimension, Map<ObservedConcept, IObservation> catalog, ILocator locator) {
            this.dimension = dimension;
            this.catalog = catalog;
        }

        public Object getState(ILocator locator) {
            if (observation instanceof IState) {
                return ((IState) observation).get(locator);
            }
            return null;
        }

        public IConcept getConcept() {
            if (!checked) {
                checked = true;
                if (dimension.filters != null) {
                    for (Filter filter : dimension.filters) {
                        if (filter.classifier != null && filter.classifier.isConcept()) {
                            this.concept = filter.classifier.getConcept();
                            ObservedConcept target = filter.target;
                            if (target == null) {
                                target = dimension.target;
                            }
                            if (target != null) {
                                observation = catalog.get(target);
                            }
                            break;
                        }
                    }
                }
                if (concept == null && dimension.classifiers != null) {
                    for (Filter filter : dimension.classifiers) {
                        if (filter.classifier != null && filter.classifier.isConcept()) {
                            this.concept = filter.classifier.getConcept();
                            ObservedConcept target = filter.target;
                            if (target == null) {
                                target = dimension.target;
                            }
                            if (target != null) {
                                observation = catalog.get(target);
                            }
                            break;
                        }
                    }
                }
            }
            return concept;
        }

        public Object getProperty(String state) {
            return null;
        }

    }

}
