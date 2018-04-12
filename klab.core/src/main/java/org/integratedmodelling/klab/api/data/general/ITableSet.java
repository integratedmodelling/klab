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

import java.util.Collection;

/**
 * For those resources that contain more than one table, like Excel spreadsheets and Access dbs. Simply
 * a named collection of named tables.
 * 
 * @author Ferd
 *
 */
public interface ITableSet {

    /**
     * 
     * @return the tableset name
     */
    String getName();

    /**
     * 
     * @return all tables in the tableset
     */
    Collection<ITable> getTables();

    /**
     * 
     * @param tableName
     * @return a specific table in the tableset, or null.
     */
    ITable getTable(String tableName);

}
