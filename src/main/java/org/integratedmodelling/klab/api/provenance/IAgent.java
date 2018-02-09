package org.integratedmodelling.klab.api.provenance;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.provenance.IProvenance.Node;

/**
 * An agent in k.LAB is anything that makes observations.
 * 
 * @author Ferd
 */
public interface IAgent extends Node {
  
  IIdentity getIdentity();

}