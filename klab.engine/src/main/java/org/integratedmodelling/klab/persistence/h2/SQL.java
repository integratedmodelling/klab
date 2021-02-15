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
package org.integratedmodelling.klab.persistence.h2;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.DataType;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.utils.Escape;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Simple utility interfaces for cleaner code.
 * 
 * @author ferdinando.villa
 *
 */
public class SQL {

    public static Map<DataType, String> sqlTypes = Collections.synchronizedMap(new HashMap<>());
    static {
        sqlTypes.put(DataType.FLOAT, "FLOAT");
        sqlTypes.put(DataType.DOUBLE, "DOUBLE");
        sqlTypes.put(DataType.LONG, "LONG");
        sqlTypes.put(DataType.SHAPE, "GEOMETRY");
        sqlTypes.put(DataType.TEXT, "VARCHAR");
    }

    public static String getType(IArtifact.Type type) {

        switch(type) {
        case BOOLEAN:
            return "BOOLEAN";
        case NUMBER:
            return "DOUBLE";
        default:
            break;
        }

        return "VARCHAR";
    }

    public static String wrapPOD(Object o) {

        if (o instanceof ISpace) {
            o = ((Shape) ((ISpace) o).getShape()).getStandardizedGeometry();
        }

        if (o instanceof Geometry) {
            return "'" + o + "'";
        }

        return o instanceof String ? ("'" + o + "'") : (o == null ? "NULL" : o.toString());
    }

    /**
     * Passed to some SQL kboxes' query() to ease handling of statements and connections.
     * 
     * @author ferdinando.villa
     *
     */
    public static interface ResultHandler {

        void onRow(ResultSet rs);

        /**
         * Passed the number of result AFTER all rows (if any) have been processed with onRow.
         * 
         * @param nres
         */
        void nResults(int nres);

    }

    /**
     * For code tightness when we don't want nResults.
     * 
     * @author Ferd
     *
     */
    public abstract static class SimpleResultHandler implements ResultHandler {

        @Override
        public void nResults(int nres) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * Wrap the obvious values. Empty string, NaN or any true nodata (null, Double.NaN) are nodata.
     * 
     * @param value
     * @param type
     * @return
     */
    public static String wrapPOD(Object value, Type type) {
        if (Observations.INSTANCE.isNodata(value) || value.toString().trim().isEmpty() || "NaN".equals(value.toString().trim())) {
            return "NULL";
        }
        switch(type) {
        case BOOLEAN:
            try {
                return Boolean.parseBoolean(value.toString()) ? "TRUE" : "FALSE";
            } catch (Throwable t) {
                return "1".equals(value.toString()) ? "TRUE" : "FALSE";
            }
        case NUMBER:
            return value.toString();
        default:
            break;
        }
        return "'" + Escape.forSQL(value.toString()) + "'";
    }

}
