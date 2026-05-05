package org.integratedmodelling.klab.ogc.stac.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.integratedmodelling.klab.stac.STACPathExpression;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class STACPathExpressionTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static JsonNode testJson() throws Exception {
        String json = """
                {
                  "node1": {
                    "node2": [
                      {
                        "name": "something",
                        "count": 10,
                        "active": true
                      },
                      {
                        "name": "other",
                        "count": 20,
                        "active": false
                      }
                    ]
                  },
                  "eo:bands": [
                    {
                      "name": "B01",
                      "common_name": "coastal"
                    },
                    {
                      "name": "B04",
                      "common_name": "red"
                    },
                    {
                      "name": "B08",
                      "common_name": "nir"
                    }
                  ],
                  "properties": {
                    "cloud_cover": 12.5,
                    "enabled": true
                  }
                }
                """;

        return MAPPER.readTree(json);
    }

    @Test
    void shouldResolveIndexedArrayElement() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("node1>node2[0]>name");

        List<JsonNode> resolved = expression.resolve(root);

        assertEquals(1, resolved.size());
        assertEquals("something", resolved.get(0).asText());
        assertTrue(expression.matches(root, "something"));
        assertFalse(expression.matches(root, "other"));
    }

    @Test
    void shouldResolveSecondIndexedArrayElement() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("node1>node2[1]>name");

        List<JsonNode> resolved = expression.resolve(root);

        assertEquals(1, resolved.size());
        assertEquals("other", resolved.get(0).asText());
        assertTrue(expression.matches(root, "other"));
        assertFalse(expression.matches(root, "something"));
    }

    @Test
    void shouldResolveWildcardArrayElements() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("node1>node2[*]>name");

        List<JsonNode> resolved = expression.resolve(root);

        assertEquals(2, resolved.size());
        assertEquals("something", resolved.get(0).asText());
        assertEquals("other", resolved.get(1).asText());

        assertTrue(expression.matches(root, "something"));
        assertTrue(expression.matches(root, "other"));
        assertFalse(expression.matches(root, "missing"));
    }

    @Test
    void shouldMatchEoBandsUsingWildcard() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("eo:bands[*]>name");

        List<JsonNode> resolved = expression.resolve(root);

        assertEquals(3, resolved.size());
        assertEquals("B01", resolved.get(0).asText());
        assertEquals("B04", resolved.get(1).asText());
        assertEquals("B08", resolved.get(2).asText());

        assertTrue(expression.matches(root, "B01"));
        assertTrue(expression.matches(root, "B04"));
        assertTrue(expression.matches(root, "B08"));
        assertFalse(expression.matches(root, "B99"));
    }

    @Test
    void shouldMatchEoBandCommonNameUsingWildcard() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("eo:bands[*]>common_name");

        assertTrue(expression.matches(root, "coastal"));
        assertTrue(expression.matches(root, "red"));
        assertTrue(expression.matches(root, "nir"));
        assertFalse(expression.matches(root, "green"));
    }

    @Test
    void shouldMatchNumbers() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("node1>node2[0]>count");

        assertTrue(expression.matches(root, "10"));
        assertTrue(expression.matches(root, "10.0"));
        assertFalse(expression.matches(root, "20"));
        assertFalse(expression.matches(root, "not-a-number"));
    }

    @Test
    void shouldMatchBooleans() throws Exception {
        JsonNode root = testJson();

        STACPathExpression firstExpression =
                STACPathExpression.parse("node1>node2[0]>active");

        STACPathExpression secondExpression =
                STACPathExpression.parse("node1>node2[1]>active");

        assertTrue(firstExpression.matches(root, "true"));
        assertTrue(firstExpression.matches(root, "TRUE"));
        assertFalse(firstExpression.matches(root, "false"));

        assertTrue(secondExpression.matches(root, "false"));
        assertTrue(secondExpression.matches(root, "FALSE"));
        assertFalse(secondExpression.matches(root, "true"));
    }

    @Test
    void shouldMatchTopLevelProperties() throws Exception {
        JsonNode root = testJson();

        STACPathExpression cloudCoverExpression =
                STACPathExpression.parse("properties>cloud_cover");

        STACPathExpression enabledExpression =
                STACPathExpression.parse("properties>enabled");

        assertTrue(cloudCoverExpression.matches(root, "12.5"));
        assertTrue(cloudCoverExpression.matches(root, "12.50"));
        assertFalse(cloudCoverExpression.matches(root, "13"));

        assertTrue(enabledExpression.matches(root, "true"));
        assertFalse(enabledExpression.matches(root, "false"));
    }

    @Test
    void shouldReturnEmptyListForMissingPath() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("node1>node2[*]>missing");

        List<JsonNode> resolved = expression.resolve(root);

        assertTrue(resolved.isEmpty());
        assertFalse(expression.matches(root, "anything"));
    }

    @Test
    void shouldReturnEmptyListWhenWildcardIsAppliedToNonArray() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("properties[*]>cloud_cover");

        List<JsonNode> resolved = expression.resolve(root);

        assertTrue(resolved.isEmpty());
        assertFalse(expression.matches(root, "12.5"));
    }

    @Test
    void shouldRejectInvalidWildcardSyntax() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.parse("node1>node2[abc]>name")
        );

        assertTrue(exception.getMessage().contains("Invalid array index"));
    }

    @Test
    void shouldRejectNegativeArrayIndex() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.parse("node1>node2[-1]>name")
        );

        assertTrue(exception.getMessage().contains("Array index cannot be negative"));
    }

    @Test
    void shouldRejectUnexpectedCharactersAfterArraySyntax() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.parse("node1>node2[0]extra>name")
        );

        assertTrue(exception.getMessage().contains("Unexpected characters"));
    }

    @Test
    void shouldRejectEmptyPathElement() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.parse("node1>>name")
        );

        assertTrue(exception.getMessage().contains("Invalid empty path element"));
    }
}