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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.weather.data.WeatherStation;

/**
 * Station bean returned by get-stations. Provides detailed info about a weather station,
 * optionally including all data stored.
 * 
 * @author Ferd
 */
public class Station {

    double                              lon;
    double                              lat;
    double                              elevation;
    String                              id;
    String                              type;
    String                              source;
    boolean                             error;
    List<String>                        variables = new ArrayList<>();
    Map<String, Map<Integer, double[]>> data;

    public Station(WeatherStation ws, boolean includeData, String[] variables, int[] years) {

        this.lon = ws.getPoint().getX();
        this.lat = ws.getPoint().getY();
        this.elevation = ws.getElevation();
        this.id = ws.getId();
        this.variables.addAll(ws.getProvidedVariables());
        this.type = ws.getType();
        this.source = ws.getSource();

        if (includeData) {
            try {
                this.data = ws.getAllData(variables, years);
            } catch (KlabException e) {
                error = true;
            }
        }
    }

    /*
     * boilerplate setter/getter code from here on.
     */

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Map<String, Map<Integer, double[]>> getData() {
        return data;
    }

    public void setData(Map<String, Map<Integer, double[]>> data) {
        this.data = data;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}

