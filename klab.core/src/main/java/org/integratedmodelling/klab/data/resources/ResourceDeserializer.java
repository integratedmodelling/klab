package org.integratedmodelling.klab.data.resources;

import java.io.IOException;
import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.CastUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ResourceDeserializer extends StdDeserializer<Resource> {

  private static final long serialVersionUID = -1395932709465412782L;

  public ResourceDeserializer() {
    super(Resource.class);
  }

  @Override
  public Resource deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    Resource ret = new Resource();
    JsonNode node = jp.getCodec().readTree(jp);

    ret.urn = node.get("urn").asText();
    ret.resourceTimestamp = node.get("resourceTimestamp").asLong();
    ret.adapterType = node.get("adapterType").asText();
    ret.geometry = Geometry.create(node.get("geometry").asText());
    ret.version = Version.create(node.get("version").asText());
    ret.parameters = new Parameters(JsonUtils.asMap(node.get("parameters")));
    ret.metadata = new Metadata(JsonUtils.asMap(node.get("metadata")));
    ret.history = new CastUtils<KimNotification, INotification>()
        .cast(JsonUtils.asList(node.get("history"), KimNotification.class));
    ret.notifications = new CastUtils<KimNotification, INotification>()
        .cast(JsonUtils.asList(node.get("notifications"), KimNotification.class));

    return ret;
  }


}
