package org.integratedmodelling.klab.provenance;

import java.util.Optional;

import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IProvenance;

/**
 * One of the concrete activities (Description) in the ODO observation ontology: Computation,
 * Detection or Acknowledgement. We only represent the first two, as artifacts that have no activity
 * generating them are by default acknowledged by the identity that owns the graph.
 * <p>
 * The agent for a computation is a dataflow, which follows the Plan specified by a Model. All these
 * are reported in their serialized form.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public class Activity implements IActivity {


  public Activity() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public long getStart() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long getEnd() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Optional<IActivity> getCause() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IAgent getAgent() {
    // TODO Auto-generated method stub
    return null;
  }

@Override
public long getTimestamp() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public IProvenance getProvenance() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isEmpty() {
	// TODO Auto-generated method stub
	return false;
}

}
