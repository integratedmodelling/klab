package org.integratedmodelling.weather.rest;
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

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.weather.data.WeatherFactory;

/**
 * Capabilities bean returned by /capabilities. Summarizes the status of the database and
 * any other info of general relevance.
 * 
 * @author Ferd
 *
 */
public class Capabilities {

    long    nStations;
    boolean error = false;

    public Capabilities() {
        try {
            this.nStations = WeatherFactory.INSTANCE.getStationsCount();
        } catch (KlabException e) {
            error = true;
        }
    }

    public long getnStations() {
        return nStations;
    }

    public void setnStations(long nStations) {
        this.nStations = nStations;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
