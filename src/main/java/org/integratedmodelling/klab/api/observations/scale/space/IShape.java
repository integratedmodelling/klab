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
package org.integratedmodelling.klab.api.observations.scale.space;

/**
 * Basically a tag interface because we want no dependencies in the API - but it should have
 * the getGeometry() and getStandardizedGeometry() methods that only appear in IGeometricShape
 * from the common package.
 * 
 * TODO we can add an enum for empty, point, line, polygon (and maybe volume) and a getShapeType method.
 * 
 * @author Ferd
 *
 */
public interface IShape {

    public enum Type {
        EMPTY,
        POINT,
        LINESTRING,
        POLYGON,
        MULTIPOINT,
        MULTILINESTRING,
        MULTIPOLYGON
    }

    /**
     * Geometry type
     * 
     * @return
     */
    Type getGeometryType();
    
    /**
     * Return whatever class implements the geometry.
     * 
     * @return
     */
    Class<?> getGeometryClass();

    /**
     * Return a suitable measure of area. Units not guaranteed - only comparability between
     * conformant shapes.
     * 
     * @return area in stable unit
     */
    double getArea();

    /**
     * Shapes may be empty or inconsistent.
     * 
     * @return true if not really a shape
     */
    boolean isEmpty();

}
