package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.pt.CPT;


public interface IJoinTreeDecision<V extends JTVertex> {
    public abstract void initializeStructiure();

    // StrongJoinTree map structure has to be built before calling initialize()
    public abstract void initialize();
	 
    public abstract void propagateEvidence();
	 
    public abstract void initializeLikelihoods();
    public abstract BeliefNetwork getBeliefNetwork();	
    public abstract Set<V> vertexSet();
    public abstract CPT getPolicy(DiscreteDomain node);
    public V getRoot();
	 	
}
