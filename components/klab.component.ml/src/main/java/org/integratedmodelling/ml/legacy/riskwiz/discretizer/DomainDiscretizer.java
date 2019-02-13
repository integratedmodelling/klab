/**
 * DomainDiscretizer.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Jul 13, 2009
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
 * @date      Jul 13, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.discretizer;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.ContinuousDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;


/**
 * @author Sergey Krivov
 * 
 */
public class DomainDiscretizer {

    /**
     * 
     */
    public DomainDiscretizer() {// TODO Auto-generated constructor stub
    }

    public static BeliefNetwork discretize(BeliefNetwork bn) {

        Set<BNNode> nodes = bn.vertexSet();

        for (BNNode node : nodes) {
            createDiscreteDomain(node);
        }		 

        return bn;

    }

    public static void createDiscreteDomain(BNNode node) {
        if (node.getDomain() instanceof ContinuousDomain) {

            IntervalDomain ddom = discretizeDomain(
                    (ContinuousDomain) node.getDomain());

            node.setDiscretizedDomain(ddom);
        } else {
            node.setDiscretizedDomain((DiscreteDomain) node.getDomain());
        }
        node.setWeight(node.getDiscretizedDomain().getOrder());

    }

    public static IntervalDomain discretizeDomain(ContinuousDomain dom) {
        return new IntervalDomain(dom.getName(), dom.getMin(), dom.getMax(),
                dom.getDiscretizationOrder());
    }

}
