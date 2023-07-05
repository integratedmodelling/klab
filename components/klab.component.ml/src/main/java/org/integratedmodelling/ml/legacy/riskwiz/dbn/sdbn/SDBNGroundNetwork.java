/**
 * SDBNGroundNetwork.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Jul 29, 2009
 *
 * ----------------------------------------------------------------------------------
 * This file is part of riskwiz-cvars.
 * 
 * riskwiz-cvars is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * riskwiz-cvars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * ----------------------------------------------------------------------------------
 * 
 * @copyright 2009 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Jul 29, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.dbn.sdbn;


import org.integratedmodelling.ml.legacy.riskwiz.dbn.DBNGroundNetwork;


/**
 * @author Sergey Krivov
 *
 */
public class SDBNGroundNetwork extends DBNGroundNetwork {

    /**
     * 
     */
    public SDBNGroundNetwork() {
        super();
        // TODO Auto-generated constructor stub
    }

//    /**
//     * @param edgeClass
//     */
//    public SDBNGroundNetwork(Class<? extends BNEdge> edgeClass) {
//        super(edgeClass);
//        // TODO Auto-generated constructor stub
//    }

    /**
     * @param ef
     * @param allowMultipleEdges
     * @param allowLoops
     */
    public SDBNGroundNetwork(boolean allowMultipleEdges, boolean allowLoops) {
        super(allowMultipleEdges, allowLoops);
        // TODO Auto-generated constructor stub
    }

//    /**
//     * @param ef
//     */
//    public SDBNGroundNetwork(EdgeFactory<BNNode, BNEdge> ef) {
//        super(ef);
//        // TODO Auto-generated constructor stub
//    }

    /**
     * @param name
     */
    public SDBNGroundNetwork(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

}
