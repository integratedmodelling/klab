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
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.CastUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Parameters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceDeserializer.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class ResourceDeserializer extends StdDeserializer<Resource> {

  private static final long serialVersionUID = -1395932709465412782L;

//  ObjectMapper mapper = new ObjectMapper();
  
  /**
   * Instantiates a new resource deserializer.
   */
  public ResourceDeserializer() {
    super(Resource.class);
//    SimpleModule module = new SimpleModule();
//    module.addDeserializer(Resource.class, new ResourceDeserializer());
//    mapper.registerModule(module);
  }

  /** {@inheritDoc} */
  @Override
  public Resource deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    Resource ret = new Resource();
    JsonNode node = jp.getCodec().readTree(jp);

    ret.urn = node.get("urn").asText();
    ret.resourceTimestamp = node.get("resourceTimestamp").asLong();
    ret.adapterType = node.get("adapterType").asText();
    ret.localPath = node.get("localPath").asText();
    ret.geometry = Geometry.create(node.get("geometry").asText());
    ret.version = Version.create(node.get("version").asText());
    ret.parameters = new Parameters(JsonUtils.asMap(node.get("parameters")));
    ret.metadata = new Metadata(JsonUtils.asMap(node.get("metadata")));
    // FOCK converts to maps, not resources
    ret.history = new CastUtils<Resource, IResource>()
        .cast(JsonUtils.asList(node.get("history"), Resource.class));
    ret.notifications = new CastUtils<KimNotification, INotification>()
        .cast(JsonUtils.asList(node.get("notifications"), KimNotification.class));
    ret.localPaths = new CastUtils<String, String>()
            .cast(JsonUtils.asList(node.get("localPaths"), String.class));

    return ret;
  }


}
