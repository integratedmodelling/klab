package org.integratedmodelling.klab.ogc.stac.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Predicate;

import org.hortonmachine.gears.io.stac.HMStacAsset;
import org.integratedmodelling.klab.stac.STACPathExpression;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class STACPathExpressionTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static JsonNode stacItemNode() throws Exception {
        String json = """
                {
                  "assets": {
                    "CHELSA_bio12_1981-2010_V.2.1": {
                      "href": "s3://bucket/CHELSA_bio12_1981-2010_V.2.1.tif",
                      "title": "Climate Annual Precipitation (CHELSA bio12)",
                      "description": "Annual precipitation data from the CHELSA Climate dataset for the period 1981-2010.",
                      "type": "image/tiff",
                      "proj:code": "EPSG:4326",
                      "proj:bbox": [
                        -180.0,
                        -90.0,
                        180.0,
                        90.0
                      ],
                      "roles": [
                        "data"
                      ],
                      "eo:bands": [
                        {
                          "name": "bio12",
                          "description": "Annual precipitation"
                        }
                      ],
                      "file:size": 2608605060,
                      "raster:bands": [
                        {
                          "nodata": -99999,
                          "data_type": "int32"
                        }
                      ]
                    },
                    "CHELSA_gdd5_1981-2010_V.2.1": {
                      "href": "s3://bucket/CHELSA_gdd5_1981-2010_V.2.1.tif",
                      "title": "Climate Growing Degree Days > 5°C (CHELSA gdd5)",
                      "description": "Growing degree days (T > 5°C) from the CHELSA Climate dataset for the period 1981-2010",
                      "type": "image/tiff",
                      "proj:code": "EPSG:4326",
                      "roles": [
                        "data"
                      ],
                      "eo:bands": [
                        {
                          "name": "gdd5",
                          "description": "Growing degree days with temperature > 5°C"
                        }
                      ],
                      "file:size": 3520234749,
                      "raster:bands": [
                        {
                          "nodata": 2147483647,
                          "data_type": "int32"
                        }
                      ]
                    },
                    "CHELSA_gsp_1981-2010_V.2.1": {
                      "href": "s3://bucket/CHELSA_gsp_1981-2010_V.2.1.tif",
                      "title": "Climate Precipitation in Growing Season (CHELSA gsp)",
                      "description": "Precipitation during the growing season from the CHELSA Climate dataset for the period 1981-2010.",
                      "type": "image/tiff",
                      "proj:code": "EPSG:3857",
                      "roles": [
                        "data"
                      ],
                      "eo:bands": [
                        {
                          "name": "gsp",
                          "description": "Precipitation in growing season"
                        }
                      ],
                      "file:size": 2447532198,
                      "raster:bands": [
                        {
                          "nodata": 2147483648,
                          "data_type": "float32"
                        }
                      ]
                    },
                    "CHELSA_gst_1981-2010_V.2.1": {
                      "href": "s3://bucket/CHELSA_gst_1981-2010_V.2.1.tif",
                      "title": "Climate Mean Temperature in Growing Season (CHELSA gst)",
                      "description": "Mean temperature during the growing season from the CHELSA Climate dataset for the period 1981-2010.",
                      "type": "image/tiff",
                      "proj:code": "EPSG:4326",
                      "roles": [
                        "data"
                      ],
                      "eo:bands": [
                        {
                          "name": "gst",
                          "description": "Mean temperature in growing season"
                        }
                      ],
                      "file:size": 308275230,
                      "raster:bands": [
                        {
                          "nodata": 65535,
                          "data_type": "int32"
                        }
                      ]
                    }
                  }
                }
                """;

        return OBJECT_MAPPER.readTree(json);
    }

    private static JsonNode assetNode(String assetId) throws Exception {
        return stacItemNode().get("assets").get(assetId);
    }

    private static HMStacAsset bio12Asset() throws Exception {
        return new HMStacAsset(
                "CHELSA_bio12_1981-2010_V.2.1",
                assetNode("CHELSA_bio12_1981-2010_V.2.1")
        );
    }

    private static HMStacAsset gstAsset() throws Exception {
        return new HMStacAsset(
                "CHELSA_gst_1981-2010_V.2.1",
                assetNode("CHELSA_gst_1981-2010_V.2.1")
        );
    }

    @Test
    void stacPathExpressionMatchesAssetByVirtualIdFromAssetsObjectMap() throws Exception {
        JsonNode root = stacItemNode();

        STACPathExpression expression = STACPathExpression.parse("assets.id");

        assertTrue(expression.matches(root, "CHELSA_gst_1981-2010_V.2.1"));
        assertTrue(expression.matches(root, "CHELSA_bio12_1981-2010_V.2.1"));
        assertFalse(expression.matches(root, "CHELSA_unknown"));
    }

    @Test
    void stacPathExpressionResolvesAllAssetIdsFromAssetsObjectMapKeys() throws Exception {
        JsonNode root = stacItemNode();

        List<JsonNode> resolved = STACPathExpression.parse("assets.id").resolve(root);

        assertEquals(4, resolved.size());
        assertTrue(resolved.stream().anyMatch(node -> node.asText().equals("CHELSA_bio12_1981-2010_V.2.1")));
        assertTrue(resolved.stream().anyMatch(node -> node.asText().equals("CHELSA_gdd5_1981-2010_V.2.1")));
        assertTrue(resolved.stream().anyMatch(node -> node.asText().equals("CHELSA_gsp_1981-2010_V.2.1")));
        assertTrue(resolved.stream().anyMatch(node -> node.asText().equals("CHELSA_gst_1981-2010_V.2.1")));
    }

    @Test
    void stacPathExpressionMatchesEoBandNameUsingImplicitArrayExpansion() throws Exception {
        JsonNode root = stacItemNode();

        STACPathExpression expression = STACPathExpression.parse("assets.eo:bands.name");

        assertTrue(expression.matches(root, "bio12"));
        assertTrue(expression.matches(root, "gdd5"));
        assertTrue(expression.matches(root, "gsp"));
        assertTrue(expression.matches(root, "gst"));
        assertFalse(expression.matches(root, "unknown-band"));
    }

    @Test
    void stacPathExpressionMatchesEoBandNameUsingExplicitArrayIndex() throws Exception {
        JsonNode root = stacItemNode();

        STACPathExpression expression = STACPathExpression.parse("assets.eo:bands[0].name");

        assertTrue(expression.matches(root, "bio12"));
        assertTrue(expression.matches(root, "gst"));
        assertFalse(expression.matches(root, "unknown-band"));
    }

    @Test
    void stacPathExpressionMatchesAssetMediaTypeFromAssetsObjectMap() throws Exception {
        JsonNode root = stacItemNode();

        STACPathExpression expression = STACPathExpression.parse("assets.type");

        assertTrue(expression.matches(root, "image/tiff"));
        assertFalse(expression.matches(root, "application/json"));
    }

    @Test
    void stacPathExpressionMatchesRasterBandDataTypeUsingArrayIndex() throws Exception {
        JsonNode root = stacItemNode();

        STACPathExpression expression = STACPathExpression.parse("assets.raster:bands[0].data_type");

        assertTrue(expression.matches(root, "int32"));
        assertTrue(expression.matches(root, "float32"));
        assertFalse(expression.matches(root, "uint8"));
    }

    @Test
    void stacPathExpressionMatchesNumericValuesUsingNumericComparison() throws Exception {
        JsonNode root = stacItemNode();

        STACPathExpression expression = STACPathExpression.parse("assets.file:size");

        assertTrue(expression.matches(root, "2608605060"));
        assertTrue(expression.matches(root, "308275230.0"));
        assertFalse(expression.matches(root, "123"));
    }

    @Test
    void hmStacAssetPredicateMatchesJsonExpressionAgainstAssetNode() throws Exception {
        HMStacAsset asset = bio12Asset();

        Predicate<HMStacAsset> bandPredicate =
                STACPathExpression.STACAssetPredicate.fromHMStacAsset("eo:bands.name", "bio12");

        Predicate<HMStacAsset> indexedBandPredicate =
                STACPathExpression.STACAssetPredicate.fromHMStacAsset("eo:bands[0].name", "bio12");

        Predicate<HMStacAsset> typePredicate =
                STACPathExpression.STACAssetPredicate.fromHMStacAsset("type", "image/tiff");

        Predicate<HMStacAsset> rasterDataTypePredicate =
                STACPathExpression.STACAssetPredicate.fromHMStacAsset("raster:bands[0].data_type", "int32");

        assertTrue(bandPredicate.test(asset));
        assertTrue(indexedBandPredicate.test(asset));
        assertTrue(typePredicate.test(asset));
        assertTrue(rasterDataTypePredicate.test(asset));

        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAsset("eo:bands.name", "gst")
                .test(asset));

        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAsset("type", "application/json")
                .test(asset));
    }

    @Test
    void hmStacAssetPredicateMatchesJavaAssetAttributes() throws Exception {
        HMStacAsset asset = bio12Asset();

        assertTrue(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetId("CHELSA_bio12_1981-2010_V.2.1")
                .test(asset));

        assertTrue(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("id", "CHELSA_bio12_1981-2010_V.2.1")
                .test(asset));

        assertTrue(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("title", "Climate Annual Precipitation (CHELSA bio12)")
                .test(asset));

        assertTrue(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("type", "image/tiff")
                .test(asset));

        assertTrue(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("epsg", "4326")
                .test(asset));

        assertTrue(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("epsg", "4326.0")
                .test(asset));

        assertTrue(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("valid", Boolean.toString(asset.isValid()))
                .test(asset));

        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetId("CHELSA_gst_1981-2010_V.2.1")
                .test(asset));

        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("title", "Wrong title")
                .test(asset));

        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("type", "application/json")
                .test(asset));

        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("epsg", "3857")
                .test(asset));
    }

    @Test
    void hmStacAssetAttributePredicateDistinguishesDifferentAssets() throws Exception {
        HMStacAsset bio12 = bio12Asset();
        HMStacAsset gst = gstAsset();

        Predicate<HMStacAsset> bio12IdPredicate =
                STACPathExpression.STACAssetPredicate.fromHMStacAssetId("CHELSA_bio12_1981-2010_V.2.1");

        Predicate<HMStacAsset> gstIdPredicate =
                STACPathExpression.STACAssetPredicate.fromHMStacAssetId("CHELSA_gst_1981-2010_V.2.1");

        Predicate<HMStacAsset> bio12BandPredicate =
                STACPathExpression.STACAssetPredicate.fromHMStacAsset("eo:bands.name", "bio12");

        Predicate<HMStacAsset> gstBandPredicate =
                STACPathExpression.STACAssetPredicate.fromHMStacAsset("eo:bands.name", "gst");

        assertTrue(bio12IdPredicate.test(bio12));
        assertFalse(bio12IdPredicate.test(gst));

        assertTrue(gstIdPredicate.test(gst));
        assertFalse(gstIdPredicate.test(bio12));

        assertTrue(bio12BandPredicate.test(bio12));
        assertFalse(bio12BandPredicate.test(gst));

        assertTrue(gstBandPredicate.test(gst));
        assertFalse(gstBandPredicate.test(bio12));
    }

    @Test
    void predicatesReturnFalseForNullHMStacAsset() {
        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAsset("eo:bands.name", "bio12")
                .test(null));

        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetAttribute("id", "CHELSA_bio12_1981-2010_V.2.1")
                .test(null));

        assertFalse(STACPathExpression.STACAssetPredicate
                .fromHMStacAssetId("CHELSA_bio12_1981-2010_V.2.1")
                .test(null));
    }

    @Test
    void stacPathExpressionReturnsEmptyResultForMissingPath() throws Exception {
        JsonNode root = stacItemNode();

        List<JsonNode> resolved = STACPathExpression.parse("assets.missing.attribute").resolve(root);

        assertTrue(resolved.isEmpty());
    }

    @Test
    void parseRejectsOldWildcardArraySyntax() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.parse("assets.eo:bands[*].name")
        );

        assertTrue(exception.getMessage().contains("Invalid array index"));
    }

    @Test
    void oldGreaterThanSeparatorDoesNotMatchBecauseDotIsNowTheSeparator() throws Exception {
        JsonNode root = stacItemNode();

        STACPathExpression expression = STACPathExpression.parse("assets>eo:bands>name");

        assertFalse(expression.matches(root, "bio12"));
    }

    @Test
    void parseRejectsEmptyPath() {
        assertThrows(IllegalArgumentException.class, () -> STACPathExpression.parse(null));
        assertThrows(IllegalArgumentException.class, () -> STACPathExpression.parse(""));
        assertThrows(IllegalArgumentException.class, () -> STACPathExpression.parse("   "));
    }

    @Test
    void parseRejectsNegativeArrayIndex() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.parse("assets.eo:bands[-1].name")
        );

        assertTrue(exception.getMessage().contains("Array index cannot be negative"));
    }

    @Test
    void parseRejectsUnsupportedHMStacAssetAttribute() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> STACPathExpression.STACAssetPredicate.fromHMStacAssetAttribute("unsupported", "value")
        );

        assertTrue(exception.getMessage().contains("Unsupported HMStacAsset attribute"));
    }
}