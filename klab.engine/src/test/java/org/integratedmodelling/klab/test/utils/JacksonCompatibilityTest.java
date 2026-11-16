package org.integratedmodelling.klab.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geotools.data.geojson.GeoJSONReader;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.engine.rest.SchemaExtractor;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.junit.Test;
import org.locationtech.jts.geom.Point;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class JacksonCompatibilityTest {

    @Test
    public void engineStartupSchemasCanBeGenerated() {
        Map<Class<?>, JsonNode> schemas =
                SchemaExtractor.extractResourceSchema(
                        IConfigurationService.REST_RESOURCES_PACKAGE_ID);

        assertTrue(schemas.containsKey(ResourceReference.class));
        assertEquals("object", schemas.get(ResourceReference.class)
                .path("type").asText());
        assertTrue(schemas.get(ResourceReference.class)
                .path("properties").has("urn"));
    }

    @Test
    public void applicationMapperWorksWithSpringConverter() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);

        assertSame(mapper, converter.getObjectMapper());
        assertEquals(42, mapper.readTree("{\"value\":42}").get("value").asInt());
    }

    @Test
    public void geotoolsReadsGeoJsonAlongsideApplicationJackson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String geometry = "{\"type\":\"Point\",\"coordinates\":[12.5,43.5]}";

        assertEquals("Point", mapper.readTree(geometry).get("type").asText());
        Point point = (Point) GeoJSONReader.parseGeometry(geometry);
        assertEquals(12.5, point.getX(), 0.0);
        assertEquals(43.5, point.getY(), 0.0);
    }
}
