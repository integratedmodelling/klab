package org.integratedmodelling.klab.data.resources;

import java.util.Map;
import java.util.Optional;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.raw.IRawObject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class UrnResource extends AbstractResource {

  public final static String URN_FUNCTION_ID = "klab.resolve.urn";
  
  public UrnResource(String urn) {
    super(urn);
  }

  private static final long serialVersionUID = -4553572352529276681L;

  @Override
  public IGeometry getGeometry() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<IResourceAdapter>  getAdapter() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IRawObject get(IScale scale, IMonitor monitor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Object> getParameters() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IServiceCall getComputation() {
    return new KimServiceCall(URN_FUNCTION_ID, "urn", urn);
  }

}
