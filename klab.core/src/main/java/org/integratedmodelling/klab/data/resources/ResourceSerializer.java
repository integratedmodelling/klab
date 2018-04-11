package org.integratedmodelling.klab.data.resources;

import java.io.IOException;
import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.data.Metadata;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ResourceSerializer extends StdSerializer<Resource> {

  private static final long serialVersionUID = 7503848628165833367L;

  protected ResourceSerializer() {
    super(Resource.class);
  }

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
    for (INotification h : resource.history) {
      gen.writeObject(h);
    }
    gen.writeEndArray();

    gen.writeArrayFieldStart("notifications");
    for (INotification h : resource.notifications) {
      gen.writeObject(h);
    }
    gen.writeEndArray();

    gen.writeEndObject();
  }


}
