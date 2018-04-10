package org.integratedmodelling.klab.data.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.NotificationUtils;

public class ResourceBuilder implements IResource.Builder {

  private Metadata            metadata          = new Metadata();
  private Parameters          parameters        = new Parameters();
  private IGeometry           geometry;
  private List<INotification> history           = new ArrayList<>();
  private List<INotification> notifications     = new ArrayList<>();
  private long                resourceTimestamp = System.currentTimeMillis();
  private Version             resourceVersion;
  private boolean             error = false;

  @Override
  public IResource build(String urn) {

    Resource ret = new Resource();
    ret.urn = urn;
    ret.parameters = this.parameters;
    ret.metadata = this.metadata;
    ret.geometry = this.geometry;
    ret.notifications.addAll(this.notifications);
    ret.history.addAll(this.history);
    ret.resourceTimestamp = this.resourceTimestamp;
    ret.version = resourceVersion;

    return ret;
  }

  @Override
  public ResourceBuilder setMetadata(String key, Object value) {
    metadata.put(key, value);
    return this;
  }

  @Override
  public ResourceBuilder setParameter(String key, Object value) {
    parameters.put(key, value);
    return this;
  }

  @Override
  public ResourceBuilder addError(Object... o) {
    notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.SEVERE));
    error = true;
    return this;
  }

  @Override
  public ResourceBuilder addWarning(Object... o) {
    notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.WARNING));
    return this;
  }

  @Override
  public ResourceBuilder addInfo(Object... o) {
    notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.INFO));
    return this;
  }

  @Override
  public ResourceBuilder setResourceVersion(Version v) {
    this.resourceVersion = v;
    return this;
  }

  @Override
  public ResourceBuilder setResourceTimestamp(long timestamp) {
    this.resourceTimestamp = timestamp;
    return this;
  }

  @Override
  public ResourceBuilder addHistory(INotification notification) {
    this.history.add(notification);
    return this;
  }

  @Override
  public ResourceBuilder setGeometry(IGeometry s) {
    this.geometry = s;
    return this;
  }

  @Override
  public boolean hasErrors() {
    return error;
  }

}
