/**
 * JTInferencePN.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 9, 2008
 *
 * ----------------------------------------------------------------------------------
 * This file is part of RiskWiz.
 * 
 * RiskWiz is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RiskWiz is distributed in the hope that it will be useful,
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
 * @copyright 2008 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      May 9, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.influence.IPolicyNetworkInference;


/**
 * @author Sergey Krivov
 * 
 */
public class JTInferencePN extends JTInference<IJoinTreePN> implements
        IPolicyNetworkInference {

    @Override
	public String getAlgorithmName() {
        return "Strong Join Tree  Inference on Policy Network";
    }

    /**
     * 
     */
    public JTInferencePN(IJoinTreePN jtree) {
        this.jTree = jtree;
        this.bn = jtree.getBeliefNetwork();
        this.treeIsConsistent = false;
        this.retraction = true;
        this.updatedNode = null;

    }

    @Override
	public void setDecision(String nodeName, int valueIndex) {
        BNNode node = bn.getBeliefNode(nodeName);

        setDecision(node, valueIndex);
    }

    @Override
	public void setDecision(BNNode node, int valueIndex) {
        getJoinTree().setDecision(node, valueIndex);
        retraction = true;
    }

    @Override
	public void setDecision(String nodeName, String value) {
        BNNode node = bn.getBeliefNode(nodeName);

        setDecision(node, value);
    }

    @Override
	public void setDecision(BNNode node, String value) {
        getJoinTree().setDecision(node, value);
        retraction = true;
    }

    @Override
	public void setOptimalPolicy(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);

        setOptimalPolicy(node);

    }

    @Override
	public void setOptimalPolicy(BNNode node) {
        getJoinTree().setOptimalPolicy(node);
        retraction = true;

    }

}
