package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;


public interface IJoinTreeDecisionCompiler <V extends JTVertex> {
    public abstract IJoinTreeDecision<V> execute(BeliefNetwork beliefNetwork) throws Exception;
}
