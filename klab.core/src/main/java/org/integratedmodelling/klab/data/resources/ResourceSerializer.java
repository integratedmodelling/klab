package org.integratedmodelling.klab.data.resources;

import java.io.IOException;
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
    gen.writeStringField("geometry", resource.geometry.toString());
    // TODO
    gen.writeEndObject();
  }


}
