package org.integratedmodelling.klab.data.resources;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.data.Metadata;

public class ResourceBuilder implements IResource.Builder {

  Metadata   metadata   = new Metadata();
  Parameters parameters = new Parameters();

  @Override
  public IResource build(String urn) {
    Resource ret = new Resource();
    ret.urn = urn;
    ret.parameters = this.parameters;
    ret.metadata = this.metadata;
    return ret;
  }

  @Override
  public void setMetadata(String key, Object value) {
    metadata.put(key, value);
  }

  @Override
  public void setParameter(String key, Object value) {
    parameters.put(key, value);
  }

  @Override
  public void addError(Object... o) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addWarning(Object... o) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addInfo(Object... o) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setVersion(Version v) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setTimestamp(long timestamp) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addHistory(INotification notification) {
    // TODO Auto-generated method stub

  }

}
