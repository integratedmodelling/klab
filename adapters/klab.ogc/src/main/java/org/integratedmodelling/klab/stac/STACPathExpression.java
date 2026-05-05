package org.integratedmodelling.klab.stac;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.hortonmachine.gears.io.stac.HMStacAsset;

public final class STACPathExpression {

	private final List<PathPart> path;

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

        for (JsonNode node : resolvedNodes) {
            if (jsonValueEquals(node, expectedValue)) {
                return true;
            }
        }

        return false;
    }

    public List<JsonNode> resolve(JsonNode root) {
        List<JsonNode> currentNodes = new ArrayList<>();
        currentNodes.add(root);

        for (PathPart part : path) {
            List<JsonNode> nextNodes = new ArrayList<>();

            for (JsonNode current : currentNodes) {
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
                        for (JsonNode arrayElement : fieldNode) {
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

        for (String token : tokens) {
            String trimmed = token.trim();

            if (trimmed.isEmpty()) {
                throw new IllegalArgumentException("Invalid empty path element");
            }

            parts.add(PathPart.parse(trimmed));
        }

        return parts;
    }

    private static boolean jsonValueEquals(JsonNode actualValue, String expectedValue) {
        if (actualValue == null || actualValue.isNull() || actualValue.isMissingNode()) {
            return expectedValue == null;
        }

        if (expectedValue == null) {
            return false;
        }

        if (actualValue.isNumber()) {
            return numberEquals(actualValue, expectedValue);
        }

        if (actualValue.isBoolean()) {
            return Boolean.toString(actualValue.booleanValue())
                    .equalsIgnoreCase(expectedValue);
        }

        if (actualValue.isTextual()) {
            return Objects.equals(actualValue.asText(), expectedValue);
        }

        return Objects.equals(actualValue.asText(), expectedValue);
    }

    private static boolean numberEquals(JsonNode actualValue, String expectedValue) {
        try {
            BigDecimal actual = actualValue.decimalValue();
            BigDecimal expected = new BigDecimal(expectedValue);

            return actual.compareTo(expected) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public record PathPart(
            String fieldName,
            ArrayMode arrayMode,
            Integer arrayIndex
    ) {

        public static PathPart parse(String token) {
            int bracketStart = token.indexOf('[');

            if (bracketStart < 0) {
                return new PathPart(token, ArrayMode.NONE, null);
            }

            int bracketEnd = token.indexOf(']', bracketStart);

            if (bracketEnd < 0) {
                throw new IllegalArgumentException(
                        "Invalid array syntax in path element: " + token
                );
            }

            if (bracketEnd != token.length() - 1) {
                throw new IllegalArgumentException(
                        "Unexpected characters after array syntax in path element: " + token
                );
            }

            String fieldName = token.substring(0, bracketStart).trim();
            String indexText = token.substring(bracketStart + 1, bracketEnd).trim();

            if (fieldName.isEmpty()) {
                throw new IllegalArgumentException(
                        "Field name cannot be empty in path element: " + token
                );
            }

            if ("*".equals(indexText)) {
                return new PathPart(fieldName, ArrayMode.WILDCARD, null);
            }

            int index;

            try {
                index = Integer.parseInt(indexText);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Invalid array index in path element: " + token,
                        e
                );
            }

            if (index < 0) {
                throw new IllegalArgumentException(
                        "Array index cannot be negative in path element: " + token
                );
            }

            return new PathPart(fieldName, ArrayMode.INDEX, index);
        }
    }
    
    public enum ArrayMode {
        NONE,
        INDEX,
        WILDCARD
    }
    
    public static final String PREDICATE_EO_BANDS_NAME = "eo:bands[*]>name";
    
    public final class STACAssetPredicate {

        private STACAssetPredicate() {
        }

        public static Predicate<HMStacAsset> from(String jsonPath, String expectedValue) {
            STACPathExpression expression = STACPathExpression.parse(jsonPath);

            return asset -> expression.matches(asset.getAssetNode(), expectedValue);
        }
    }
    
    
   
}