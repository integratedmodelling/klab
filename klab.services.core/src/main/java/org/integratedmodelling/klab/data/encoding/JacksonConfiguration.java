package org.integratedmodelling.klab.data.encoding;

import java.io.IOException;
import java.util.Iterator;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.collections.impl.Parameters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonConfiguration {

    @SuppressWarnings("rawtypes")
    static class ParameterSerializer extends JsonSerializer<KParameters> {

        @SuppressWarnings("rawtypes")
        @Override
        public void serialize(KParameters value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("@type", "KParameters");
            for (Object key : value.keySet()) {
                gen.writeObjectField(key.toString(), value.get(key));
            }
            gen.writeEndObject();
        }

    }

    static class ParameterDeserializer extends JsonDeserializer<KParameters<?>> {
        @Override
        public KParameters<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            return readParameters(node, p);
        }

        private KParameters<?> readParameters(JsonNode node, JsonParser p) {

            Parameters<Object> ret = Parameters.create();
            Iterator<String> fields = node.fieldNames();
            while(fields.hasNext()) {
                String field = fields.next();
                JsonNode value = node.get(field);
                Object val = null;
                if (value.has("@type")) {
                    val = parseType(value, node.get("@type").asText(), p);
                } else {
                    // ???
                }
                ret.put(field, val);
            }
            return ret;

        }

        private Object parseType(JsonNode value, String type, JsonParser p) {
            Object ret = null;
            switch(type) {
            case "KParameters":
                ret = readParameters(value, p);
                break;
            }
            return ret;
        }
    }

    public static void configureObjectMapper(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule().addDeserializer(KParameters.class, new ParameterDeserializer())
                .addSerializer(KParameters.class, new ParameterSerializer());
        mapper.registerModule(module);
    }

}
