package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;


public interface IJoinTree<V extends JTVertex> {
	
    // builds map for fast operation and initializes tree
    // unless evidences changed no need to call initialize() after this
    public abstract void initializeStructiure();

    // StrongJoinTree map structure has to be built before calling initialize()
    public abstract void initialize();
    public abstract void collectEvidence(V X);
    public abstract void propagateEvidence();
    public abstract void passMessage(V source, V target);	
    public abstract void initializeLikelihoods();
    public abstract BeliefNetwork getBeliefNetwork();	
    public abstract Set<V> vertexSet();
	
    public abstract void setNodeConditionalMarginals();
    public abstract void propagateEvidence(V X);
    public abstract void propagateEvidence(BNNode node);
    public abstract void distributeEvidence(V X);

}
