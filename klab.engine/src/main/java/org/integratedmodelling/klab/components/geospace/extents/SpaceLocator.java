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
package org.integratedmodelling.klab.components.geospace.extents;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

/**
 * Simple serializable object to use in requests for partial states. Can specify a given
 * timeslice, all of them, or the latest. Only real utility for this class is that it can 
 * be passed to IScale.locate() and recognized.
 * 
 * @author ferdinando.villa
 *
 */
public class SpaceLocator /* extends AbstractLocator */ {

    public int x = -1;
    public int y = -1;
    public double lon = 0;
    public double lat = 0;

    public SpaceLocator(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SpaceLocator(double lon, double lat) {
        this.x = -3;
        this.y = -3;
        this.lon = lon;
        this.lat = lat;
    }

    public SpaceLocator(String s) {
        if (!s.startsWith("S")) {
            throw new KlabValidationException("error parsing space locator: " + s);
        }
        String[] ss = s.substring(2).split(",");
        if (s.charAt(1) == 'l') {
            x = y = -3;
            lon = Double.parseDouble(ss[0]);
            lat = Double.parseDouble(ss[1]);
        } else {
            x = Integer.parseInt(ss[0]);
            y = Integer.parseInt(ss[1]);
        }
    }

    @Override
    public String toString() {
        if (isLatLon()) {
            return "Sl" + lon + "," + lat;
        }
        return "Si" + x + "," + y;
    }

    /**
     * Create a locator for a geographical position identified by world coordinates. Use x on horizontal
     * coordinates.
     * 
     * @param lon
     * @param lat
     * @return a new space locator
     */
    public static SpaceLocator get(double lon, double lat) {
        return new SpaceLocator(lon, lat);
    }

    /**
     * Create a locator for a geographical position identified by world coordinates. Use x on horizontal
     * coordinates.
     * 
     * @param x
     * @param y
     * @return a new space locator
     */
    public static SpaceLocator get(int x, int y) {
        return new SpaceLocator(x, y);
    }

    public static SpaceLocator all() {
        return new SpaceLocator(-1, -1);
    }

    //    @Override
    public boolean isAll() {
        return x == -1 && y == -1;
    }

    public boolean isLatLon() {
        return x == -3;
    }

    //    @Override
    public int getDimensionCount() {
        return 2;
    }

    //    @Override
    public IConcept getExtent() {
        return Concepts.c(NS.SPACE_DOMAIN);
    }

}
