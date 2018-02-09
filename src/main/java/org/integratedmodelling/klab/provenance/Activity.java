package org.integratedmodelling.klab.provenance;

import java.util.Optional;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.jgrapht.graph.DefaultEdge;

public class Activity extends DefaultEdge implements IActivity {

  private static final long serialVersionUID = 2322736753319019967L;

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

}
