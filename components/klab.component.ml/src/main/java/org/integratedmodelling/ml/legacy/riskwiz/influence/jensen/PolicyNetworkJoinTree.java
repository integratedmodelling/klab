/**
 * PolicyNetworkJoinTree.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 2, 2008
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
 * @date      May 2, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.influence.jensen;


import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;
import org.integratedmodelling.ml.legacy.riskwiz.influence.JTPotential;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.IJoinTreePN;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.TableFactory;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FMarginalizationMap;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FastMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.SubtableFastMap2;
import org.jgrapht.Graphs;


/**
 * @author Sergey Krivov
 * 
 */
public class PolicyNetworkJoinTree extends StrongJoinTree implements
        IJoinTreePN<SJTVertex> {

    protected Hashtable<DiscreteDomain, PT> decisionPotentialHash;

    /**
     * @param bn
     */
    public PolicyNetworkJoinTree(BeliefNetwork bn) {
        super(bn);
        // TODO Auto-generated constructor stub
    }

    public PolicyNetworkJoinTree(StrongJoinTree  sjt) {
        super();
        this.bn = sjt.getBeliefNetwork();
        this.root = sjt.getRoot();
        this.policyHash = sjt.policyHash;
        this.clusterHash = sjt.clusterHash;

        Graphs.addGraph(this, sjt);

    }

    @Override
	public void setDecision(BNNode node, int valueIndex) {
        decisionPotentialHash.put(node.getDiscretizedDomain(),
                TableFactory.createObservation(node.getDiscretizedDomain(),
                valueIndex));
    }

    @Override
	public void setDecision(BNNode node, String value) {
        decisionPotentialHash.put(node.getDiscretizedDomain(),
                TableFactory.createObservation(node.getDiscretizedDomain(),
                value));
    }

    @Override
	public void setOptimalPolicy(BNNode node) {
        decisionPotentialHash.remove(node.getDomain());
        decisionPotentialHash.put(node.getDiscretizedDomain(),
                policyHash.get(node.getDomain()));
    }

    // builds map for fast operation and initializes tree
    // unless evidences changed no need to call initialize() after this
    @Override
	public void initializeStructiure() {
        setVaciousProbabilityPotentials();

        // create correspondence between Belif nodes and clusters (vertexes of
        // StrongJoinTree)
        clusterHash = new Hashtable<BNNode, ClusterBundle>();

        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {

            SJTVertex parentCluster = assignParentCluster(node);

            if (node.isNature()) {

                JTPotential clusterPT = parentCluster.getPotential();
                PT nodePT = node.getDiscreteCPT();
                FastMap2 fmap = clusterPT.createSubtableFastMap(nodePT);
                FMarginalizationMap mfmap = clusterPT.createFMarginalizationMap(
                        node.getDiscretizedDomain());
                FastMap2 liklihoodfmap = clusterPT.createSubtableFastMap(
                        DomainFactory.createDomainProduct(
                                node.getDiscretizedDomain()));

                clusterHash.put(node,
                        new ClusterBundle(parentCluster, fmap, mfmap,
                        liklihoodfmap));
            } else if (node.isUtility()) {
                JTPotential clusterPT = parentCluster.getPotential();
                Vector<DiscreteDomain> parentDomains = node.getDiscreteCPT().getParentsDomains();
                FastMap2 fmap = clusterPT.createSubtableFastMap(parentDomains);
                FMarginalizationMap mfmap = clusterPT.createFMarginalizationMap(
                        node.getDiscreteCPT().getParentsDomains());

                clusterHash.put(node,
                        new ClusterBundle(parentCluster, fmap, mfmap, null));
            } else if (node.isDecision()) {

                JTPotential clusterPT = parentCluster.getPotential();
                // PT nodePT = decisionPotentialHash.get(node);
                // FastMap2 fmap = clusterPT.createSubtableFastMap(nodePT);
                FMarginalizationMap mfmap = clusterPT.createFMarginalizationMap(
                        node.getDiscretizedDomain());
                FastMap2 liklihoodfmap = clusterPT.createSubtableFastMap(
                        DomainFactory.createDomainProduct(
                                node.getDiscretizedDomain()));

                clusterHash.put(node,
                        new ClusterBundle(parentCluster, null, mfmap,
                        liklihoodfmap));
            }

        }

        // compile maps for fast operations
        Set<SJTVertex> verttset = this.vertexSet();

        for (SJTVertex vertex : verttset) {
            Set<SJTEdge> edgesOfvertex = this.edgesOf(vertex);

            for (SJTEdge jtedge : edgesOfvertex) {
                vertex.createFastMapsPN(jtedge);
            }
        }

    }

    // StrongJoinTree map structure has to be built before calling and decision
    // problem has to be solved
    // initializePolicy()
    @Override
	public void initialize() {
        // boolean init=false;
        setVaciousProbabilityPotentials();

        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                ClusterBundle bundle = clusterHash.get(node);
                SJTVertex parentCluster = bundle.jtcluster;
                JTPotential clusterPT = parentCluster.getPotential();

                clusterPT.multiplyByProbabilitySubtable(node.getDiscreteCPT(),
                        bundle.fopmap);
            } /*
             * else if (node.isUtility()) { ClusterBundle bundle =
             * clusterHash.get(node); SJTVertex parentCluster =
             * bundle.jtcluster; JTPotential clusterPT =
             * parentCluster.getPotential();
             * clusterPT.addUtilitySubtable(node.getTable(), bundle.fopmap); }
             */ else if (node.isDecision()) {
                ClusterBundle bundle = clusterHash.get(node);
                SJTVertex parentCluster = bundle.jtcluster;
                JTPotential clusterPT = parentCluster.getPotential();
                FastMap2 fmap = clusterPT.createSubtableFastMap(
                        decisionPotentialHash.get(node));

                clusterPT.multiplyByProbabilitySubtable(
                        decisionPotentialHash.get(node), fmap);
            }

        }

    }

    @Override
	public BeliefNetwork getBeliefNetwork() {
        // TODO Auto-generated method stub
        return bn;
    }

    @Override
	public void propagateEvidence(SJTVertex X) {
        unmarkAll();
        collectEvidence(X);
        unmarkAll();
        distributeEvidence(X);
    }

    @Override
	public void propagateEvidence(BNNode node) {
        ClusterBundle bundle = clusterHash.get(node);
        SJTVertex parentCluster = bundle.jtcluster;

        JTPotential clusterPT = parentCluster.getPotential();

        clusterPT.multiplyByProbabilitySubtable(node.getEvidence(),
                bundle.liklihoodfmap);
        propagateEvidence(parentCluster);
    }

    @Override
	public void collectEvidence(SJTVertex X) {
        X.isMarked = true;
        Set<SJTVertex> neighbours = this.getNeighbors(X);

        for (SJTVertex neighbor : neighbours) {
            if (!neighbor.isMarked) {
                collectEvidence(neighbor);
                passMessage(neighbor, X);
            }
        }

    }

    @Override
	public void distributeEvidence(SJTVertex X) {
        X.isMarked = true;
        Set<SJTVertex> neighbours = this.getNeighbors(X);

        for (SJTVertex neighbor : neighbours) {
            if (!neighbor.isMarked) {
                passMessage(X, neighbor);
                distributeEvidence(neighbor);
            }
        }
    }

    @Override
	public void passMessage(SJTVertex source, SJTVertex target) {
        // boolean pp=false;
        SJTEdge jtedge = this.getEdge(source, target);
        PT sepsetPT = jtedge.getPotential().getProbabilityPotential();

        PT oldSepsetPT = sepsetPT.clone();
        PT sourcePT = source.getPotential().getProbabilityPotential();

        FMarginalizationMap mmap = source.getFMarginalizationMapPN(jtedge);

        PT.marginalizeDomainsFast(sepsetPT, sourcePT, mmap);

        PT targetPT = target.getPotential().getProbabilityPotential();

        SubtableFastMap2 fmap2 = target.getSubtableOpFastMap(jtedge);

        targetPT.multiplyBySubtableFast(sepsetPT, fmap2);
        targetPT.divideBySubtableFast(oldSepsetPT, fmap2);

    }

    public void setNodeMarginals() {
        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature() || node.isDecision()) {
                ClusterBundle cbundle = clusterHash.get(node);
                SJTVertex jtcluster = cbundle.jtcluster;
                FMarginalizationMap mfmap = cbundle.mfmap;
                PT marginal = new PT(mfmap.getProjectionDomainProduct());

                PT.marginalizeDomainsFast(marginal,
                        jtcluster.getPotential().getProbabilityPotential(),
                        mfmap);
                node.setMarginal(marginal);
            } else if (node.isUtility()) {
                ClusterBundle cbundle = clusterHash.get(node);
                SJTVertex jtcluster = cbundle.jtcluster;
                PT utility = jtcluster.getPotential().getProbabilityPotential().clone();

                utility.multiplyBySubtable(node.getDiscreteCPT(), cbundle.fopmap);
                node.setMarginalUtility(utility.sum());
            }

        }
    }

    @Override
	public void setNodeConditionalMarginals() {
        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature() || node.isDecision()) {
                ClusterBundle cbundle = clusterHash.get(node);
                SJTVertex jtcluster = cbundle.jtcluster;
                FMarginalizationMap mfmap = cbundle.mfmap;
                PT likelihood = new PT(mfmap.getProjectionDomainProduct());

                PT.marginalizeDomainsFast(likelihood,
                        jtcluster.getPotential().getProbabilityPotential(),
                        mfmap);
                likelihood.normalize();
                node.setMarginal(likelihood);
            } else if (node.isUtility()) {
                ClusterBundle cbundle = clusterHash.get(node);
                SJTVertex jtcluster = cbundle.jtcluster;
                PT utility = jtcluster.getPotential().getProbabilityPotential().clone();

                utility.normalize();
                utility.multiplyBySubtable(node.getDiscreteCPT(), cbundle.fopmap);
                node.setMarginalUtility(utility.sum());

            }

        }
    }

    public void setVaciousProbabilityPotentials() {
        Set<SJTVertex> verttset = this.vertexSet();

        // initialize clusters
        for (SJTVertex vertex : verttset) {
            vertex.getPotential().setVaciousProb();
        }
        // initialize spesets
        Set<SJTEdge> edgeset = this.edgeSet();

        for (SJTEdge edge : edgeset) {
            edge.getPotential().setVaciousProb();
        }
    }

}
