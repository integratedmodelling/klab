/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.data.general;

// TODO: Auto-generated Javadoc
/**
 * A table column in the unified table interface.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IColumn {

    /**
     * Use this constant to refer to the 0-based row index when asking a table for a mapping.
     */
    public static final String INDEX = "__index__";

    /**
     * <p>getName.</p>
     *
     * @return column name
     */
    String getName();

    /**
     * <p>getValueCount.</p>
     *
     * @return number of rows with values in column.
     */
    int getValueCount();

    /**
     * <p>getValues.</p>
     *
     * @return all values.
     */
    Iterable<Object> getValues();

    /**
     * <p>getValue.</p>
     *
     * @param index a {@link java.lang.Object} object.
     * @return get a value corresponding to an index object.
     */
    Object getValue(Object index);

}
