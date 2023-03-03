package org.integratedmodelling.klab.data.encoding;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.integratedmodelling.klab.api.collections.Annotation;
import org.integratedmodelling.klab.api.collections.KLiteral;
import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.collections.Metadata;
import org.integratedmodelling.klab.api.collections.impl.Parameters;
import org.integratedmodelling.klab.api.data.KMetadata;
import org.integratedmodelling.klab.api.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.api.lang.KAnnotation;
import org.integratedmodelling.klab.logging.Logging;

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

    static class LiteralSerializer extends JsonSerializer<KLiteral> {

        @Override
        public void serialize(KLiteral value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            // TODO Auto-generated method stub
            
        }
        
    }
    
    static class LiteralDeserializer extends JsonDeserializer<KLiteral> {

        @Override
        public KLiteral deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
    
    @SuppressWarnings("rawtypes")
    static class ParameterSerializer<T extends KParameters> extends JsonSerializer<T> {

        @Override
        public void serialize(KParameters value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            String type = getTypeName(value);
            gen.writeObjectField("@type", getTypeName(value));
            for (Object key : value.keySet()) {
                gen.writeObjectField(key.toString(), value.get(key));
            }
            gen.writeEndObject();
        }

        private String getTypeName(KParameters<?> value) {
            if (value instanceof KMetadata) {
                return "KMetadata";
            } else if (value instanceof KAnnotation) {
                return "KAnnotation";
            }
            return "KParameters";
        }
    }

    static class ParameterDeserializer<T extends KParameters<?>> extends JsonDeserializer<T> {
        
        @SuppressWarnings("unchecked")
        @Override
        public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            return (T)readParameters(node, p, node.get("@type").asText());
        }

        @SuppressWarnings("unchecked")
        private KParameters<?> readParameters(JsonNode node, JsonParser p, String type) {

            try {
                KParameters<?> ret = null;

                switch(type) {
                case "KParameters":
                    ret = (KParameters<?>)Parameters.create();
                    break;
                case "KAnnotation":
                    ret = new Annotation();
                    break;
                case "KMetadata":
                    ret = new Metadata();
                    break;
                }
                Iterator<String> fields = node.fieldNames();
                while(fields.hasNext()) {
                    String field = fields.next();
                    JsonNode value = node.get(field);
                    Object val = null;
                    if (field.startsWith("@")) {
                        continue;
                    } else if (value.has("@type")) {
                        val = readParameters(value, p, node.get("@type").asText());
                    } else {
                        try {
                            val = p.getCodec().treeToValue(value, Object.class);
                        } catch (JsonProcessingException e) {
                            // screw that
                            Logging.INSTANCE.error(e);
                        }
                    }
                    ((Map<String, Object>) ret).put(field, val);
                }
                return ret;
            } catch (Throwable t) {
                throw new KlabInternalErrorException(t);
            }
        }

    }

    @SuppressWarnings({"unchecked"})
    public static void configureObjectMapperForKlabTypes(ObjectMapper mapper) {
        @SuppressWarnings("rawtypes")
        SimpleModule module = new SimpleModule()
                .addSerializer(KAnnotation.class, new ParameterSerializer())
                .addSerializer(KMetadata.class, new ParameterSerializer())
                .addSerializer(KParameters.class, new ParameterSerializer())
                .addSerializer(KLiteral.class, new LiteralSerializer())
                .addDeserializer(KMetadata.class, new ParameterDeserializer())
                .addDeserializer(KAnnotation.class, new ParameterDeserializer())
                .addDeserializer(KParameters.class, new ParameterDeserializer())
                .addDeserializer(KLiteral.class, new LiteralDeserializer())
                ;
        mapper.registerModule(module);
    }

}
