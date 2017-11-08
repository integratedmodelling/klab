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
package org.integratedmodelling.klab.api.data.general;

/**
 * A table column in the unified table interface.
 * 
 * @author Ferd
 *
 */
public interface IColumn {

    /**
     * Use this constant to refer to the 0-based row index when asking a table for a mapping.
     */
    public static final String INDEX = "__index__";

    /**
     * @return column name
     */
    String getName();

    /**
     * @return number of rows with values in column.
     */
    int getValueCount();

    /**
     * @return all values.
     */
    Iterable<Object> getValues();

    /**
     * @param index
     * @return get a value corresponding to an index object.
     */
    Object getValue(Object index);

}
