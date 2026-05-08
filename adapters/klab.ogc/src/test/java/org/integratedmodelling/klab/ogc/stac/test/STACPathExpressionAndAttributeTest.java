package org.integratedmodelling.klab.ogc.stac.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hortonmachine.gears.io.stac.HMStacAsset;
import org.integratedmodelling.klab.stac.STACPathExpression;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class STACPathExpressionAndAttributeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static JsonNode testJson() throws Exception {
        String json = """
                "{assets": {
        "peat_thickness": {
          "href": "https://s3.waw4-1.cloudferro.com/ecdc-waw4-1-ekqouvq3otv8hmw0njzuvo0g4dy0ys8r985n7dggjis3erkpn5o/ECDC/Soil/Soil_V2_global_20010101_20211231_peat_thickness.tif",
          "type": "image/tiff; application=geotiff; profile=cloud-optimized",
          "roles": [
            "data"
          ],
          "Method to produce": "Spatial distribution is based on the prediction using the Quantile Random Forest algorithm, informed by land surface data (soil, climate, organisms, and topography), to develop regional models over six regions for peat thickness. Peat thickness models are based on approximately 27,000 data points. Highest accuracy observed in African peatlands.",
          "Link to resource": [
            "https://zenodo.org/records/14183473"
          ],
          "proj:code": "EPSG:4326",
          "proj:geometry": {
            "type": "Polygon",
            "coordinates": [
              [
                [
                  180,
                  -60.007461
                ],
                [
                  180,
                  88.007948
                ],
                [
                  -180,
                  88.007948
                ],
                [
                  -180,
                  -60.007461
                ],
                [
                  180,
                  -60.007461
                ]
              ]
            ]
          },
          "proj:bbox": [
            -180,
            -60.007461,
            180,
            88.007948
          ],
          "proj:shape": [
            16477,
            40076
          ],
          "proj:transform": [
            0.008983152841201715,
            0,
            -180.004416632,
            0,
            -0.008983152841172543,
            88.007948385,
            0,
            0,
            1
          ],
          "eo:bands": [
            {
              "name": "peat_thickness"
            }
          ],
          "raster:bands": [
            {
              "sampling": "area",
              "data_type": "float32",
              "statistics": {
                "minimum": 1.9351852,
                "maximum": 1006.69586
              },
              "unit": "cm",
              "scale": 1,
              "offset": 0
            }
          ]
        }
      }}
                """;

        return MAPPER.readTree(json);
    }

    @Test
    void shouldResolveIndexedArrayElement() throws Exception {
        JsonNode root = testJson();

        STACPathExpression expression =
                STACPathExpression.parse("eo:bands[*]>name");

        List<JsonNode> resolved = expression.resolve(root);

        assertEquals(1, resolved.size());
        assertEquals("something", resolved.get(0).asText());
        assertTrue(expression.matches(root, "peat_thickness"));
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
    
    // Test by attributes
    
    @Test
    void shouldMatchAssetByIdAttribute() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff",
                  "proj:code": "EPSG:32632"
                }
                """);

        HMStacAsset asset = new HMStacAsset("B04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute("id", "B04");

        assertTrue(predicate.test(asset));
    }

    @Test
    void shouldNotMatchAssetByDifferentIdAttribute() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff",
                  "proj:code": "EPSG:32632"
                }
                """);

        HMStacAsset asset = new HMStacAsset("B04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute("id", "B08");

        assertFalse(predicate.test(asset));
    }

    @Test
    void shouldMatchAssetByTitleAttribute() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Surface reflectance band 4",
                  "type": "image/tiff; application=geotiff"
                }
                """);

        HMStacAsset asset = new HMStacAsset("B04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute(
                        "title",
                        "Surface reflectance band 4"
                );

        assertTrue(predicate.test(asset));
    }

    @Test
    void shouldMatchAssetByTypeAttribute() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff"
                }
                """);

        HMStacAsset asset = new HMStacAsset("B04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute(
                        "type",
                        "image/tiff; application=geotiff"
                );

        assertTrue(predicate.test(asset));
    }

    @Test
    void shouldMatchAssetByEpsgAttribute() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff",
                  "proj:code": "EPSG:32632"
                }
                """);

        HMStacAsset asset = new HMStacAsset("B04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute("epsg", "32632");

        assertTrue(predicate.test(asset));
    }

    @Test
    void shouldMatchAssetByEpsgAttributeUsingDecimalEquivalent() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff",
                  "proj:code": "EPSG:32632"
                }
                """);

        HMStacAsset asset = new HMStacAsset("B04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute("epsg", "32632.0");

        assertTrue(predicate.test(asset));
    }

    @Test
    void shouldNotMatchAssetByDifferentEpsgAttribute() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff",
                  "proj:code": "EPSG:32632"
                }
                """);

        HMStacAsset asset = new HMStacAsset("B04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute("epsg", "4326");

        assertFalse(predicate.test(asset));
    }

    @Test
    void shouldMatchAssetByValidAttribute() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff"
                }
                """);

        HMStacAsset asset = new HMStacAsset("B04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute("valid", "true");

        /*
         * One caveat: HMStacAsset computes isValid through HMStacAssetHandlers.getHandler(this).
         * Depending on your test classpath and supported MIME types, asset.isValid()
         *  may be either true or false. That is why the validity test uses:
         *  assertEquals(asset.isValid(), predicate.test(asset));
         */
        assertEquals(asset.isValid(), predicate.test(asset));
    }
    
    // JSON-path-through-asset tests
    @Test
    void shouldMatchAssetUsingJsonPathPredicate() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff",
                  "eo:bands": [
                    {
                      "name": "B01",
                      "common_name": "coastal"
                    },
                    {
                      "name": "B04",
                      "common_name": "red"
                    }
                  ]
                }
                """);

        HMStacAsset asset = new HMStacAsset("asset-b04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromJsonPath(
                        "eo:bands[*]>name",
                        "B04"
                );

        assertTrue(predicate.test(asset));
    }

    @Test
    void shouldNotMatchAssetUsingJsonPathPredicateWhenValueIsMissing() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff",
                  "eo:bands": [
                    {
                      "name": "B01",
                      "common_name": "coastal"
                    },
                    {
                      "name": "B04",
                      "common_name": "red"
                    }
                  ]
                }
                """);

        HMStacAsset asset = new HMStacAsset("asset-b04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromJsonPath(
                        "eo:bands[*]>name",
                        "B08"
                );

        assertFalse(predicate.test(asset));
    }

    @Test
    void shouldMatchAssetUsingDefaultJsonPathPredicateFactory() throws Exception {
        JsonNode assetNode = MAPPER.readTree("""
                {
                  "title": "Band 4",
                  "type": "image/tiff; application=geotiff",
                  "eo:bands": [
                    {
                      "name": "B04"
                    }
                  ]
                }
                """);

        HMStacAsset asset = new HMStacAsset("asset-b04", assetNode);

        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.from(
                        "eo:bands[*]>name",
                        "B04"
                );

        assertTrue(predicate.test(asset));
    }
    
    // negative/null-safety tests:
    @Test
    void shouldReturnFalseWhenAssetIsNullForAttributePredicate() {
        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromAssetAttribute("id", "B04");

        assertFalse(predicate.test(null));
    }

    @Test
    void shouldReturnFalseWhenAssetIsNullForJsonPathPredicate() {
        Predicate<HMStacAsset> predicate =
                STACPathExpression.STACAssetPredicate.fromJsonPath(
                        "eo:bands[*]>name",
                        "B04"
                );

        assertFalse(predicate.test(null));
    }

    @Test
    void shouldRejectUnsupportedAssetAttribute() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.STACAssetPredicate.fromAssetAttribute(
                        "unsupportedAttribute",
                        "value"
                )
        );

        assertTrue(exception.getMessage().contains("Unsupported HMStacAsset attribute"));
    }

    @Test
    void shouldRejectEmptyAssetAttributeName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.STACAssetPredicate.fromAssetAttribute(
                        " ",
                        "value"
                )
        );

        assertTrue(exception.getMessage().contains("Asset attribute name cannot be empty"));
    }
    
    
}