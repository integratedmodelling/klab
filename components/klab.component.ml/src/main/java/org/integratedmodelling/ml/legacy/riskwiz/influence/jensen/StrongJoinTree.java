/**
 * StrongJoinTree.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 19, 2008
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
 * @date      Feb 19, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.influence.jensen;


import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.influence.JTPotential;
import org.integratedmodelling.ml.legacy.riskwiz.influence.jensen.SJTVertex.EliminationOrder;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.IJoinTreeDecision;
import org.integratedmodelling.ml.legacy.riskwiz.pt.CPT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.DomainMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FMarginalizationMap;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FastMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.SubtableFastMap2;
import org.jgrapht.traverse.BreadthFirstIterator;


/**
 * @author Sergey Krivov
 * 
 */

/**
 * @author Sergey Krivov
 * 
 */
public class StrongJoinTree extends RiskUndirectedGraph<SJTVertex, SJTEdge>
        implements IJoinTreeDecision<SJTVertex> {

    protected BeliefNetwork bn;

    protected SJTVertex root;

    protected Hashtable<BNNode, ClusterBundle> clusterHash;

    protected Hashtable<DiscreteDomain, CPT> policyHash;
	
    protected Vector<Object> rootMmaps;

    public StrongJoinTree(BeliefNetwork bn) {
        super(SJTEdge.class);
        this.bn = bn;
    }

    public StrongJoinTree() {
        super(SJTEdge.class);

    }

    public void setVaciousPotentials() {
        Set<SJTVertex> verttset = this.vertexSet();

        // initialize clusters
        for (SJTVertex vertex : verttset) {
            vertex.setVacious();
        }
        // initialize spesets
        Set<SJTEdge> edgeset = this.edgeSet();

        for (SJTEdge edge : edgeset) {
            edge.setVacious();
        }
    }

    // builds map for fast operation and initializes tree
    // unless evidences changed no need to call initialize() after this
    @Override
	public void initializeStructiure() {
        setVaciousPotentials();

        // create correspondence between Belif nodes and clusters (vertexes of
        // StrongJoinTree)
        clusterHash = new Hashtable<BNNode, ClusterBundle>();

        Set<BNNode> bNNodes = bn.vertexSet();

        // debug
		 
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
            }

        }

        // compile maps for fast operations
        Set<SJTVertex> verttset = this.vertexSet();

        for (SJTVertex vertex : verttset) {
            Set<SJTEdge> edgesOfvertex = this.edgesOf(vertex);

            for (SJTEdge jtedge : edgesOfvertex) {
                vertex.createFastMaps(jtedge);
            }
        }
		
        createRootMarginalizationFastMap();

    }

    // StrongJoinTree map structure has to be built before calling
    // initializeDecision()
    @Override
	public void initialize() {
        setVaciousPotentials();

        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                ClusterBundle bundle = clusterHash.get(node);
                SJTVertex parentCluster = bundle.jtcluster;
                JTPotential clusterPT = parentCluster.getPotential();

                clusterPT.multiplyByProbabilitySubtable(node.getDiscreteCPT(),
                        bundle.fopmap);
            } else if (node.isUtility()) {
                ClusterBundle bundle = clusterHash.get(node);
                SJTVertex parentCluster = bundle.jtcluster;
                JTPotential clusterPT = parentCluster.getPotential();

                clusterPT.addUtilitySubtable(node.getDiscreteCPT(),
                        bundle.fopmap);

            }

        }

    }

    @Override
	public void initializeLikelihoods() {
        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isProbabilistic()) {
                initializeLikelihood(node);
            }
        }

    }

    public void initializeLikelihood(BNNode node) {
        ClusterBundle bundle = clusterHash.get(node);
        SJTVertex parentCluster = bundle.jtcluster;
        JTPotential clusterPT = parentCluster.getPotential();

        if (node.hasEvidence()) {

            clusterPT.multiplyByProbabilitySubtable(node.getEvidence(),
                    bundle.liklihoodfmap);

        }
    }

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
	public void propagateEvidence() {
        policyHash = new Hashtable<DiscreteDomain, CPT>();
        unmarkAll();
		 
        collectEvidence(root);
        JTPotential.marginalizeDomainsSequence(root.getPotential(), rootMmaps,
                policyHash);
		
    }

    public void passMessage(SJTVertex source, SJTVertex target) {
        // boolean pp=false;
        SJTEdge jtedge = this.getEdge(source, target);
        JTPotential sepsetPT = jtedge.getPotential();

        JTPotential sourcePT = source.getPotential();
 
        Vector<Object> mmaps = source.getMarginalizationFastMap(jtedge);

        // System.out.println("\nmapsize: "+ mmaps.size()+ " \n" );
        // for (Object object : mmaps) {
        // if (object instanceof MarginalizationFastMap) {
        // System.out.println( "marg," );
        // } else if (object instanceof DiscreteDomain) {
        //
        // System.out.println( "maxMarg," );
        // }
        // }
        sepsetPT = JTPotential.marginalizeDomainsSequence(sourcePT, mmaps,
                policyHash);
        jtedge.setPotential(sepsetPT);

        JTPotential targetPT = target.getPotential();

        SubtableFastMap2 fmap2 = target.getSubtableOpFastMap(jtedge);

        targetPT.multiplyBySubtableFast(sepsetPT, fmap2);

    }

    public void unmarkAll() {
        Set<SJTVertex> vertexSet = this.vertexSet();

        for (SJTVertex vertex : vertexSet) {
            vertex.isMarked = false;
        }
    }

    protected SJTVertex assignParentCluster(BNNode node) {
        if (node.isDecision()) {
            return assignParentClusterDecision(node);
        }
        Set<BNNode> family = new HashSet<BNNode>();

        if (!node.isUtility()) {
            family.add(node);
        }
        family.addAll(bn.getParents(node));
        SJTVertex v = null;
        Set<SJTVertex> verttset = this.vertexSet();

        for (SJTVertex vertex : verttset) {
            if (vertex.containsAll(family)) {
                v = vertex;
                break;
            }
        }
        return v;
    }

    protected SJTVertex assignParentClusterDecision(BNNode node) {

        for (BreadthFirstIterator<SJTVertex, SJTEdge> it = new BreadthFirstIterator<SJTVertex, SJTEdge>(
                this, this.getRoot()); it.hasNext();) {
            SJTVertex vertex = it.next();

            if (vertex.contains(node)) {
                return vertex;
            }
        }
        return null;
    }

    protected class ClusterBundle {
        protected SJTVertex jtcluster;

        protected DomainMap2 fopmap;

        protected FastMap2 liklihoodfmap;

        protected FMarginalizationMap mfmap;

        public ClusterBundle(SJTVertex jtcluster, DomainMap2 fmap,
                FMarginalizationMap mfmap, FastMap2 liklihoodfmap) {
            this.fopmap = fmap;
            this.jtcluster = jtcluster;
            this.mfmap = mfmap;
            this.liklihoodfmap = liklihoodfmap;
        }

    }

    @Override
	public SJTVertex getRoot() {
        return root;
    }

    public void setRoot(SJTVertex root) {
        this.root = root;
    }
	
    // vacious methods, TODO need an adapter

    @Override
	public BeliefNetwork getBeliefNetwork() {
		 
        return bn;
    }

    @Override
	public CPT getPolicy(DiscreteDomain nodeDom) {
        // TODO Auto-generated method stub
        return policyHash.get(nodeDom);
    }
	
    public void createRootMarginalizationFastMap() {
        rootMmaps = new Vector<Object>();
        SortedSet<BNNode> marginals = new TreeSet<BNNode>(new EliminationOrder());

        marginals.addAll(root.getClique());
		
        Vector<DiscreteDomain> currentDomainProduct = new Vector<DiscreteDomain>();

        currentDomainProduct.addAll(root.getPotential().getDomainProduct());
		
        while (!marginals.isEmpty()) {
            Vector<DiscreteDomain> domsSet = new Vector<DiscreteDomain>();

            while (!marginals.isEmpty() && marginals.first().isNature()) {
                BNNode first = marginals.first();

                domsSet.add(first.getDiscretizedDomain());
                marginals.remove(first);
            }
            if (!domsSet.isEmpty()) {
				 
                FMarginalizationMap mmap = new FMarginalizationMap(
                        currentDomainProduct, domsSet);

                rootMmaps.add(mmap);
                currentDomainProduct.removeAll(domsSet);
            }
            while (!marginals.isEmpty() && marginals.first().isDecision()) {
				  
                BNNode first = marginals.first();
				
                rootMmaps.add(first.getDomain());
                currentDomainProduct.remove(first.getDomain());
                marginals.remove(first);
            }
        }	
		  
    }
	
    // debugging functions

	 
    public void report(SJTVertex v) {
        System.out.println(" clique:");
        Set<BNNode> cliq = v.getClique();

        for (BNNode node : cliq) {
            System.out.println(node.getName());
        }				 
        System.out.println();
    }
	 
    public void reportAll() {
        Set<SJTVertex> verttset = this.vertexSet();

        for (SJTVertex vertex : verttset) {
            report(vertex);
        }

    }

}
