package org.integratedmodelling.klab.api.provenance;

import java.util.Optional;

/**
 * Actions are the edges of the provenance graph. In k.LAB, these are represented by the internal
 * type IActuator. Action type determines the role of their vertices and can be translated into OPM
 * relationships, which typically represent the inverse action. Actions may be linked to each other
 * in a causal graph that is independent from the primary graph. Actions that are not caused by
 * another action are called "primary" and can be obtained in chronological order from the
 * provenance graph.
 * 
 * @author Ferd
 */
public interface IActivity {

  long getStart();

  long getEnd();

  /**
   * If the action was caused by another action, return the action that caused it.
   * 
   * @return
   */
  Optional<IActivity> getCause();

  /**
   * Actions are made by agents. We keep them with the actions and out of the graph.
   * 
   * @return
   */
  IAgent getAgent();

}
