package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.debugger.IJTCompilerDebugger;


public interface IJoinTreeCompiler<V extends JTVertex> {

    public abstract IJoinTree<V> execute(BeliefNetwork beliefNetwork) throws Exception;
    public abstract IJoinTree<V> execute(BeliefNetwork beliefNetwork, IJTCompilerDebugger deb) throws Exception;

}
