/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.data.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.NotificationUtils;

/**
 * The Class ResourceBuilder.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class ResourceBuilder implements IResource.Builder {

  private Metadata            metadata          = new Metadata();
  private Parameters          parameters        = new Parameters();
  private IGeometry           geometry;
  private List<INotification> history           = new ArrayList<>();
  private List<INotification> notifications     = new ArrayList<>();
  private long                resourceTimestamp = System.currentTimeMillis();
  private Version             resourceVersion;
  private boolean             error             = false;
  private String              adapterType;

  /** {@inheritDoc} */
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
    ret.version = this.resourceVersion;
    ret.adapterType = this.adapterType;
    return ret;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder setMetadata(String key, Object value) {
    metadata.put(key, value);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder setParameter(String key, Object value) {
    parameters.put(key, value);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder addError(Object... o) {
    notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.SEVERE));
    error = true;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder addWarning(Object... o) {
    notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.WARNING));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder addInfo(Object... o) {
    notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.INFO));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder setResourceVersion(Version v) {
    this.resourceVersion = v;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder setResourceTimestamp(long timestamp) {
    this.resourceTimestamp = timestamp;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder addHistory(INotification notification) {
    this.history.add(notification);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public ResourceBuilder setGeometry(IGeometry s) {
    this.geometry = s;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public boolean hasErrors() {
    return error;
  }

  /** {@inheritDoc} */
  @Override
  public void setAdapterType(String string) {
    this.adapterType = string;
  }

}
