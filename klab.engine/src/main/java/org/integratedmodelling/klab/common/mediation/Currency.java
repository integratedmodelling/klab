/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */

package org.integratedmodelling.klab.common.mediation;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.utils.Range;
import org.joda.time.DateTime;

// TODO: Auto-generated Javadoc
/**
 * The Class Currency.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Currency extends AbstractMediator implements ICurrency {

    private String code;
    private String currency;
    private DateTime date;
    private IConcept concept;
    private Range scale;
    private IUnit unit = null;

    private boolean ok = true;

    /**
     * Instantiates a new currency.
     */
    public Currency() {
    }

    public static Currency create(String s) {
        return new Currency(s);
    }

    /**
     * Sets the monetary.
     *
     * @param currency the currency
     * @param year the year
     */
    public void setMonetary(String currency, int year) {
        // TODO check it's known; error if not;
        // TODO check we have data for PPP conversion; warn if not;
        this.currency = currency;
        this.date = new DateTime(year, 1, 1, 0, 0);
    }

    /**
     * Sets the concept.
     *
     * @param concept the concept
     * @param from the from
     * @param to the to
     */
    public void setConcept(IConcept concept, double from, double to) {
        this.concept = concept;
        this.scale = new Range(from, to, false, false);
    }

    /**
     * Checks if is ok.
     *
     * @return a boolean.
     */
    public boolean isOK() {
        return ok;
    }

    /**
     * Parses the.
     *
     * @param code the code
     */
    public void parse(String code) {
        this.code = code;
        this.unit = Unit.unitless();
        if (code.contains("/")) {
            int sp = code.indexOf('/');
            String dist = code.substring(sp + 1);
            code = code.substring(0, sp);
            this.unit = this.unit.divide(Unit.create(dist));
        }
        if (code.contains("@")) {
            String[] ss = code.split("@");
            currency = ss[0];
            date = new DateTime(Integer.parseInt(ss[1]), 1, 1, 0, 0);
        }
    }

    /**
     * Instantiates a new currency.
     *
     * @param code the code
     */
    public Currency(String code) {
        parse(code);
    }

    /**
     * Instantiates a new currency.
     *
     * @param concept the concept
     */
    public Currency(IConcept concept) {
        this.concept = concept;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isMonetary() {
        return currency != null && date != null;
    }

    /** {@inheritDoc} */
    @Override
    public Number convert(Number d, IValueMediator scale) {

        if (Observations.INSTANCE.isNodata(d)) {
            return d;
        }

        if (!(scale instanceof ICurrency)) {
            throw new IllegalArgumentException("invalid conversion: " + scale + " to " + this);
        }
        if (((Currency) scale).scale != null && scale != null) {
            return this.scale.convert(d, ((Currency) scale).scale);
        }
        // TODO
        return d;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        if (code != null) {
            return code;
        }
        String ret = "";
        if (concept != null) {
            ret += concept;
        }
        if (scale != null) {
            ret += (ret.isEmpty() ? "" : " ") + scale;
        }
        return ret;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCompatible(IValueMediator other) {
        return other instanceof Currency && ((Currency) other).canMediate(this);
    }

    private boolean canMediate(Currency currency2) {
        if (concept != null && (currency2.concept == null || !concept.equals(currency2.concept))) {
            return false;
        }
        if (scale != null && (currency2.scale == null || !scale.isCompatible(currency2.scale))) {
            return false;
        }

        /*
         * TODO check actual PPP
         */
        if (currency != null && date != null) {
            return (currency2.currency != null && currency2.currency.equals(currency) && currency2.date != null
                    && currency2.date.equals(date));
        }

        return true;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Currency other = (Currency) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public IUnit getUnit() {
        return unit;
    }
    //
    // @Override
    // public Number convert(Number d, IObservable observable, IValueMediator from, IScale scale) {
    // // TODO Auto-generated method stub
    // return convert(d, from);
    // }
    //
    // @Override
    // public double getContextualizationFactor(IObservable observable, IValueMediator from, IScale
    // scale) {
    // // TODO Auto-generated method stub
    // return 1.0;
    // }

    @Override
    public IValueMediator contextualize(IObservable observable, IGeometry scale) {
        return null;
    }

}
