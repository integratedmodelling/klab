package org.integratedmodelling.klab.stac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hortonmachine.gears.io.stac.HMStacAsset;

public final class STACPathExpression {

    private final List<PathPart> path;

    /*
     * This works: eo:bands[*]>name
     * This probably does not behave as a user might expect: eo:bands>name
     * because eo:bands resolves to an array node, and then the next path part tries to read field name directly from an array.
     * That returns nothing. That is acceptable, but it should be documented: arrays require either [n] or [*].
     */
    public static final String PREDICATE_EO_BANDS_NAME = "eo:bands[*]>name";

    private STACPathExpression(List<PathPart> path) {
        this.path = path;
    }

    public static STACPathExpression parse(String jsonPath) {
        if (jsonPath == null || jsonPath.isBlank()) {
            throw new IllegalArgumentException("JSON path cannot be empty");
        }
        return new STACPathExpression(parsePath(jsonPath));
    }

    public boolean matches(JsonNode root, String expectedValue) {
        List<JsonNode> resolvedNodes = resolve(root);

        for(JsonNode node : resolvedNodes) {
            if (jsonValueEquals(node, expectedValue)) {
                return true;
            }
        }

        return false;
    }

    public List<JsonNode> resolve(JsonNode root) {
        List<JsonNode> currentNodes = new ArrayList<>();
        currentNodes.add(root);

        for(PathPart part : path) {
            List<JsonNode> nextNodes = new ArrayList<>();

            for(JsonNode current : currentNodes) {
                if (current == null || current.isNull() || current.isMissingNode()) {
                    continue;
                }

                JsonNode fieldNode = current.get(part.fieldName());

                if (fieldNode == null || fieldNode.isNull() || fieldNode.isMissingNode()) {
                    continue;
                }

                if (part.arrayMode() == ArrayMode.NONE) {
                    nextNodes.add(fieldNode);
                } else if (part.arrayMode() == ArrayMode.INDEX) {
                    if (fieldNode.isArray()) {
                        JsonNode indexedNode = fieldNode.get(part.arrayIndex());

                        if (indexedNode != null && !indexedNode.isNull() && !indexedNode.isMissingNode()) {
                            nextNodes.add(indexedNode);
                        }
                    }
                } else if (part.arrayMode() == ArrayMode.WILDCARD) {
                    if (fieldNode.isArray()) {
                        for(JsonNode arrayElement : fieldNode) {
                            if (arrayElement != null && !arrayElement.isNull() && !arrayElement.isMissingNode()) {
                                nextNodes.add(arrayElement);
                            }
                        }
                    }
                }
            }

            currentNodes = nextNodes;

            if (currentNodes.isEmpty()) {
                break;
            }
        }

        return currentNodes;
    }

    private static List<PathPart> parsePath(String jsonPath) {
        String[] tokens = jsonPath.split(">");

        List<PathPart> parts = new ArrayList<>();

        for(String token : tokens) {
            String trimmed = token.trim();

            if (trimmed.isEmpty()) {
                throw new IllegalArgumentException("Invalid empty path element");
            }

            parts.add(PathPart.parse(trimmed));
        }

        return parts;
    }

    private static boolean valueEquals(Object actualValue, String expectedValue) {
        if (actualValue == null) {
            return expectedValue == null;
        }

        if (expectedValue == null) {
            return false;
        }

        if (actualValue instanceof Number number) {
            return numberEquals(number, expectedValue);
        }

        if (actualValue instanceof Boolean bool) {
            return Boolean.toString(bool).equalsIgnoreCase(expectedValue);
        }

        return Objects.equals(String.valueOf(actualValue), expectedValue);
    }

    private static boolean jsonValueEquals(JsonNode actualValue, String expectedValue) {
        if (actualValue == null || actualValue.isNull() || actualValue.isMissingNode()) {
            return expectedValue == null;
        }

        if (expectedValue == null || !actualValue.isValueNode()) {
            return false;
        }

        if (actualValue.isNumber()) {
            return numberEquals(actualValue.decimalValue(), expectedValue);
        }

        if (actualValue.isBoolean()) {
            return Boolean.toString(actualValue.booleanValue())
                    .equalsIgnoreCase(expectedValue);
        }

        if (actualValue.isTextual()) {
            return Objects.equals(actualValue.asText(), expectedValue);
        }

        return false;
    }

    private static boolean numberEquals(Number actualValue, String expectedValue) {
        try {
            BigDecimal actual = new BigDecimal(actualValue.toString());
            BigDecimal expected = new BigDecimal(expectedValue);

            return actual.compareTo(expected) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public record PathPart(String fieldName, ArrayMode arrayMode, Integer arrayIndex) {

        public static PathPart parse(String token) {
            int bracketStart = token.indexOf('[');

            if (bracketStart < 0) {
                return new PathPart(token, ArrayMode.NONE, null);
            }

            int bracketEnd = token.indexOf(']', bracketStart);

            if (bracketEnd < 0) {
                throw new IllegalArgumentException("Invalid array syntax in path element: " + token);
            }

            if (bracketEnd != token.length() - 1) {
                throw new IllegalArgumentException("Unexpected characters after array syntax in path element: " + token);
            }

            String fieldName = token.substring(0, bracketStart).trim();
            String indexText = token.substring(bracketStart + 1, bracketEnd).trim();

            if (fieldName.isEmpty()) {
                throw new IllegalArgumentException("Field name cannot be empty in path element: " + token);
            }

            if ("*".equals(indexText)) {
                return new PathPart(fieldName, ArrayMode.WILDCARD, null);
            }

            int index;

            try {
                index = Integer.parseInt(indexText);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid array index in path element: " + token, e);
            }

            if (index < 0) {
                throw new IllegalArgumentException("Array index cannot be negative in path element: " + token);
            }

            return new PathPart(fieldName, ArrayMode.INDEX, index);
        }
    }

    public enum ArrayMode {
        NONE, INDEX, WILDCARD
    }

    public static final class STACAssetPredicate {

        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        private STACAssetPredicate() {
        }

        public static <T> Predicate<T> fromJsonPath(String jsonPath, String expectedValue,
                Function<T, JsonNode> jsonNodeExtractor) {
            STACPathExpression expression = STACPathExpression.parse(jsonPath);

            return object -> {
                if (object == null) {
                    return false;
                }

                JsonNode node = jsonNodeExtractor.apply(object);

                if (node == null || node.isNull() || node.isMissingNode()) {
                    return false;
                }

                return expression.matches(node, expectedValue);
            };
        }

        public static Predicate<HMStacAsset> fromHMStacAsset(String jsonPath, String expectedValue) {
            return fromJsonPath(jsonPath, expectedValue, asset -> asset == null ? null : asset.getAssetNode());
        }

        public static Predicate<JsonNode> fromJsonNode(String jsonPath, String expectedValue) {
            return fromJsonPath(jsonPath, expectedValue, node -> node);
        }

        public static Predicate<JSONObject> fromKongJsonObject(String jsonPath, String expectedValue) {
            return fromJsonPath(jsonPath, expectedValue, STACAssetPredicate::toJsonNode);
        }

        private static JsonNode toJsonNode(JSONObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }

            try {
                return OBJECT_MAPPER.readTree(jsonObject.toString());
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Cannot convert JSONObject to JsonNode", e);
            }
        }

        public static Predicate<HMStacAsset> fromHMStacAssetAttribute(String attributeName, String expectedValue) {
            AssetAttribute attribute = AssetAttribute.fromName(attributeName);

            return asset -> {
                if (asset == null) {
                    return false;
                }

                Object actualValue = attribute.read(asset);

                return valueEquals(actualValue, expectedValue);
            };
        }
        
        public static Predicate<HMStacAsset> fromHMStacAssetId(String expectedValue) {
            return fromHMStacAssetAttribute("id", expectedValue);
        }
    }

    public enum AssetAttribute {

        ID("id") {
            @Override
            Object read(HMStacAsset asset) {
                return asset.getId();
            }
        },

        TITLE("title") {
            @Override
            Object read(HMStacAsset asset) {
                return asset.getTitle();
            }
        },

        TYPE("type") {
            @Override
            Object read(HMStacAsset asset) {
                return asset.getType();
            }
        },

        VALID("valid") {
            @Override
            Object read(HMStacAsset asset) {
                return asset.isValid();
            }
        },

        EPSG("epsg") {
            @Override
            Object read(HMStacAsset asset) {
                return asset.getEpsg();
            }
        },

        NON_VALID_REASON("nonValidReason") {
            @Override
            Object read(HMStacAsset asset) {
                return asset.getNonValidReason();
            }
        };

        private final String name;

        AssetAttribute(String name) {
            this.name = name;
        }

        abstract Object read(HMStacAsset asset);

        public static AssetAttribute fromName(String name) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Asset attribute name cannot be empty");
            }

            for(AssetAttribute attribute : values()) {
                if (attribute.name.equalsIgnoreCase(name.trim())) {
                    return attribute;
                }
            }

            throw new IllegalArgumentException("Unsupported HMStacAsset attribute: " + name
                    + ". Supported attributes are: id, title, type, valid, epsg, nonValidReason");
        }
    }

}