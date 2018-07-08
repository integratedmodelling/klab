/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.scale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.ObservationType;
import org.integratedmodelling.klab.data.Aggregators.Max;
import org.integratedmodelling.klab.data.Aggregators.Mean;
import org.integratedmodelling.klab.data.Aggregators.Min;
import org.integratedmodelling.klab.data.Aggregators.Sum;

/**
 * Holds aggregation methods for all mediators to use.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class AbstractMediator implements Scale.Mediator {

    /**
     * These keys MAY be available after each mediation in the state's metadata. Their meaning may
     * differ according to the observer.
     * 
     * @author ferdinando.villa
     *
     */
    public final static String SPACE_MIN_VALUE = "Mediator.SPACE_MIN_VALUE";
    public final static String SPACE_MAX_VALUE = "Mediator.SPACE_MAX_VALUE";
    public final static String SPACE_VALUE_SUM = "Mediator.SPACE_VALUE_SUM";
    public final static String SPACE_VALUE_DISTRIBUTION = "Mediator.SPACE_VALUE_DISTRIBUTION";
    public final static String SPACE_TOTAL_VALUES = "Mediator.SPACE_TOTAL_VALUES";
    public final static String SPACE_CONFIDENCE = "Mediator.SPACE_CONFIDENCE";
    public final static String SPACE_ERROR = "Mediator.SPACE_ERROR";
    public final static String TIME_MIN_VALUE = "Mediator.TIME_MIN_VALUE";
    public final static String TIME_MAX_VALUE = "Mediator.TIME_MAX_VALUE";
    public final static String TIME_VALUE_SUM = "Mediator.TIME_VALUE_SUM";
    public final static String TIME_VALUE_DISTRIBUTION = "Mediator.TIME_VALUE_DISTRIBUTION";
    public final static String TIME_TOTAL_VALUES = "Mediator.TIME_TOTAL_VALUES";
    public final static String TIME_CONFIDENCE = "Mediator.TIME_CONFIDENCE";
    public final static String TIME_ERROR = "Mediator.TIME_ERROR";

    private IObservable observable;
    private Aggregation aggregation;

    protected AbstractMediator(IObservable observable, Aggregation aggregation) {
        this.observable = observable;
        this.aggregation = aggregation;
    }

    @Override
    public Object reduce(Collection<IPair<Object, Double>> toReduce, IMetadata metadata) {

        if (observable.getObservationType() == ObservationType.QUANTIFICATION) {
            return reduceNumbers(toReduce, metadata);
        } else if (observable.getObservationType() == ObservationType.CLASSIFICATION) {
            return reduceConcepts(toReduce, metadata);
        } else if (observable.getObservationType() == ObservationType.VERIFICATION) {
            return reduceBooleans(toReduce, metadata);
        }

        return null;
    }

    /*
     * CHECK - TODO: this mediator takes as true anything that mediates at least one true value with a
     * weight > 0. We should enable configuration with truth values too.
     */
    private Object reduceBooleans(Collection<IPair<Object, Double>> toReduce, IMetadata metadata) {

        double truth = Double.NaN;
        int total = 0, tottrue = 0;
        boolean ret = false;
        for (IPair<Object, Double> p : toReduce) {
            if (p.getFirst() instanceof Boolean && ((Boolean) p.getFirst()) && p.getSecond() > 0) {
                ret = true;
                tottrue++;
            }
            total++;
        }

        metadata.put(SPACE_TOTAL_VALUES, total);
        metadata.put(SPACE_VALUE_SUM, tottrue);

        return ret;
        // return (truth / total) > .5;
    }

    /*
     * TODO this one should produce a distribution, although I'm not sure the state upstream is
     * prepared to handle it.
     */
    private Object reduceConcepts(Collection<IPair<Object, Double>> toReduce, IMetadata metadata) {

        Map<IConcept, Integer> counts = new HashMap<>();

        return null;
    }

    private Object reduceNumbers(Collection<IPair<Object, Double>> toReduce, IMetadata metadata) {

        boolean extensive = aggregation != null && aggregation == Aggregation.SUM;

        ArrayList<Object> vals = new ArrayList<>();
        for (IPair<Object, Double> zio : toReduce) {
            if (zio.getFirst() instanceof Number) {
                double d = ((Number) zio.getFirst()).doubleValue();
                if (!Double.isNaN(d)) {
                    vals.add(extensive ? d * zio.getSecond() : d);
                }
            }
        }
        if (aggregation != null) {
            switch (aggregation) {
            case SUM:
                return new Sum().aggregate(vals);
            case AVERAGE:
                return new Mean().aggregate(vals);
            case MIN:
                return new Min().aggregate(vals);
            case MAX:
                return new Max().aggregate(vals);
            default:
                break;
            }
        }
        return null;
    }
}
