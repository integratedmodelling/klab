package org.integratedmodelling.klab.data.encoding;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.integratedmodelling.klab.api.collections.Annotation;
import org.integratedmodelling.klab.api.collections.KLiteral;
import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.collections.Metadata;
import org.integratedmodelling.klab.api.collections.impl.Literal;
import org.integratedmodelling.klab.api.collections.impl.Parameters;
import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.data.KMetadata;
import org.integratedmodelling.klab.api.data.ValueType;
import org.integratedmodelling.klab.api.exceptions.KInternalErrorException;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.lang.KAnnotation;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement;
import org.integratedmodelling.klab.logging.Logging;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class JacksonConfiguration {

    static class KimStatementResolverBuilder extends DefaultTypeResolverBuilder {
        private static final long serialVersionUID = -8873215972141029473L;

        public KimStatementResolverBuilder() {
            super(DefaultTyping.NON_FINAL, LaissezFaireSubTypeValidator.instance);
        }

        @Override
        public boolean useForType(JavaType t) {
            if (KKimStatement.class.isAssignableFrom(t.getRawClass()) || KGeometry.class.isAssignableFrom(t.getRawClass())) {
                return true;
            }

            return false;
        }
    }

    static class LiteralDeserializer extends JsonDeserializer<KLiteral> {

        @Override
        public KLiteral deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Literal ret = new Literal();
            JsonNode node = p.getCodec().readTree(p);
            ret.setValueType(p.getCodec().treeToValue(node.get("valueType"), ValueType.class));
            switch(ret.getValueType()) {
            case ANNOTATION:
                break;
            case ANYTHING:
                break;
            case ANYTRUE:
                break;
            case ANYVALUE:
                break;
            case BOOLEAN:
                ret.setValue(node.get("valueType").asBoolean());
                break;
            case CALLCHAIN:
                break;
            case COMPONENT:
                break;
            case DATE:
                break;
            case EMPTY:
                break;
            case ERROR:
                break;
            case EXPRESSION:
                break;
            case LIST:
                break;
            case CONSTANT:
            case STRING:
            case CLASS:
            case IDENTIFIER:
            case LOCALIZED_KEY:
            case REGEXP:
            case NUMBERED_PATTERN:
            case URN:
                ret.setValue(node.get("valueType").asText());
                break;
            case MAP:
                break;
            case NODATA:
                break;
            case NUMBER:
                ret.setValue(node.get("valueType").asDouble());
                break;
            case OBJECT:
                break;
            case OBSERVABLE:
                break;
            case OBSERVATION:
                break;
            case QUANTITY:
                break;
            case RANGE:
                ret.setValue(p.getCodec().treeToValue(node.get("valueType"), Range.class));
                break;
            case SET:
                break;
            case TABLE:
                break;
            case TREE:
                break;
            case TYPE:
                break;
            case CONCEPT:
                break;
            case DOUBLE:
                ret.setValue(node.get("valueType").asDouble());
                break;
            case INTEGER:
                ret.setValue(node.get("valueType").asInt());
                break;
            default:
                break;
            }
            return ret;
        }

    }

    @SuppressWarnings("rawtypes")
    static class ParameterSerializer<T extends KParameters> extends JsonSerializer<T> {

        @Override
        public void serialize(KParameters value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
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
            return (T) readParameters(node, p, node.get("@type").asText());
        }

        @SuppressWarnings("unchecked")
        private KParameters<?> readParameters(JsonNode node, JsonParser p, String type) {

            try {
                KParameters<?> ret = null;

                switch(type) {
                case "KParameters":
                    ret = (KParameters<?>) Parameters.create();
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
                throw new KInternalErrorException(t);
            }
        }
    }

    @SuppressWarnings({"unchecked"})
    public static void configureObjectMapperForKlabTypes(ObjectMapper mapper) {
        @SuppressWarnings("rawtypes")
        SimpleModule module = new SimpleModule().addSerializer(KAnnotation.class, new ParameterSerializer())
                .addSerializer(KMetadata.class, new ParameterSerializer())
                .addSerializer(KParameters.class, new ParameterSerializer())
                .addDeserializer(KMetadata.class, new ParameterDeserializer())
                .addDeserializer(KAnnotation.class, new ParameterDeserializer())
                .addDeserializer(KParameters.class, new ParameterDeserializer())
                .addDeserializer(KLiteral.class, new LiteralDeserializer());
        mapper.registerModule(module);
        mapper.registerModule(new ParameterNamesModule());

        TypeResolverBuilder<?> typeResolver = new KimStatementResolverBuilder();
        typeResolver.init(JsonTypeInfo.Id.CLASS, null);
        typeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
        typeResolver.typeProperty("@CLASS");
        mapper.setDefaultTyping(typeResolver);
    }

}
