/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/

package org.integratedmodelling.klab.common.mediation;

import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IValueMediator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.joda.time.DateTime;

public class Currency implements ICurrency {

    private String       code;
    private String       currency;
    private DateTime     date;
    private IConcept     concept;
    private NumericRange scale;

    private boolean ok = true;

    public Currency() {
    }

    public void setMonetary(String currency, int year) {
        // TODO check it's known; error if not;
        // TODO check we have data for PPP conversion; warn if not;
        this.currency = currency;
        this.date = new DateTime(year, 1, 1, 0, 0);
    }

    public void setConcept(IConcept concept, double from, double to) {
        this.concept = concept;
        this.scale = new NumericRange(from, to, false, false);
    }

    public boolean isOK() {
        return ok;
    }

    public void parse(String code) {
        this.code = code;
        if (code.contains("@")) {
            String[] ss = code.split("@");
            currency = ss[0];
            date = new DateTime(Integer.parseInt(ss[1]), 1, 1, 0, 0);
        } /*else if (code.contains(":")) {
            concept = Concepts.c(code);
        }*/
    }

    public Currency(String code) {
        parse(code);
    }

    public Currency(IConcept concept) {
        this.concept = concept;
    }  

    @Override
    public boolean isMonetary() {
        return currency != null && date != null;
    }

    @Override
    public Number convert(Number d, IValueMediator scale) {
        if (!(scale instanceof ICurrency)) {
            throw new KlabRuntimeException("invalid conversion: " + scale + " to " + this);
        }
        if (((Currency)scale).scale != null && scale != null) {
            return this.scale.convert(d, ((Currency)scale).scale);
        }
        // TODO        
        return d;
    }

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
            return (currency2.currency != null && currency2.currency.equals(currency) &&
                    currency2.date != null && currency2.date.equals(date));
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

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
    
    

}
