package org.integratedmodelling.klab.api.provenance;

/**
 * Actions are the edges of the provenance graph. Action type determines the role of
 * their vertices and can be translated into OPM relationships, which typically
 * represent the inverse action.
 * 
 * @author Ferd
 */
public interface Action {

    /**
     * If the action was caused by another action, return the action that caused it.
     * 
     * @return
     */
    Action getCause();

    /**
     * Actions are made by agents. We keep them with the actions and out of the graph.
     * 
     * @return
     */
    Agent getAgent();

}