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

import java.io.IOException;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.Parameters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * The Class ResourceSerializer.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class ResourceSerializer extends StdSerializer<Resource> {

  private static final long serialVersionUID = 7503848628165833367L;

  /**
   * <p>Constructor for ResourceSerializer.</p>
   */
  protected ResourceSerializer() {
    super(Resource.class);
  }

  /** {@inheritDoc} */
  @Override
  public void serialize(final Resource resource, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    gen.writeStartObject();

    gen.writeNumberField("resourceTimestamp", resource.resourceTimestamp);
    if (resource.urn != null) {
      gen.writeStringField("urn", resource.urn);
    }
    if (resource.geometry != null) {
      gen.writeStringField("geometry", resource.geometry.toString());
    }
    if (resource.version != null) {
      gen.writeStringField("version", resource.version.toString());
    }
    if (resource.adapterType != null) {
      gen.writeStringField("adapterType", resource.adapterType);
    }

    gen.writeObjectField("parameters", ((Parameters) resource.parameters).getData());;
    gen.writeObjectField("metadata", ((Metadata) resource.metadata).getData());;

    gen.writeArrayFieldStart("history");
    for (Object h : resource.history) {
      gen.writeObject(h);
    }
    gen.writeEndArray();

    gen.writeArrayFieldStart("notifications");
    for (INotification h : resource.notifications) {
      gen.writeObject(h);
    }
    gen.writeEndArray();
    
    gen.writeArrayFieldStart("localPaths");
    for (String h : resource.localPaths) {
      gen.writeString(h);
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }


}
