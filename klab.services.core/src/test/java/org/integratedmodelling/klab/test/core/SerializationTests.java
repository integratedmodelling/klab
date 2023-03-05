package org.integratedmodelling.klab.test.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.integratedmodelling.klab.api.collections.Annotation;
import org.integratedmodelling.klab.api.collections.KLiteral;
import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.collections.Metadata;
import org.integratedmodelling.klab.api.collections.impl.Literal;
import org.integratedmodelling.klab.api.collections.impl.Parameters;
import org.integratedmodelling.klab.api.data.KMetadata;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.impl.Geometry;
import org.integratedmodelling.klab.api.lang.KAnnotation;
import org.integratedmodelling.klab.utils.Utils;
import org.junit.jupiter.api.Test;

class SerializationTests {

    static String centralColombia = "τ0(1){ttype=LOGICAL,period=[1609459200000 1640995200000],tscope=1.0,tunit=YEAR}S2(934,631){bbox=[-75.2281407807369 -72.67107290964314 3.5641500380320963 5.302943221927137],shape=00000000030000000100000005C0522AF2DBCA0987400C8361185B1480C052CE99DBCA0987400C8361185B1480C052CE99DBCA098740153636BF7AE340C0522AF2DBCA098740153636BF7AE340C0522AF2DBCA0987400C8361185B1480,proj=EPSG:4326}";

    @Test
    void parameters() {
        KParameters<Object> object = Parameters.create("one", 1, "oneString", "one", "params", Parameters.create("one", 1));
        String serialized = Utils.Json.asString(object);
        KParameters<?> deserialized = Utils.Json.parseObject(serialized, KParameters.class);
        System.out.println(serialized);
        System.out.println(deserialized.getClass());
        System.out.println(deserialized.get("params").getClass());
        assert (deserialized instanceof KParameters);
        assert (deserialized.get("params") instanceof KParameters);
    }

    @Test
    void metadata() {
        KMetadata object = Metadata.create("one", 1, "oneString", "one", "params", Metadata.create("one", 1));
        String serialized = Utils.Json.asString(object);
        KMetadata deserialized = Utils.Json.parseObject(serialized, KMetadata.class);
        System.out.println(serialized);
        System.out.println(deserialized.getClass());
        System.out.println(deserialized.get("params").getClass());
        assert (deserialized instanceof KMetadata);
        assert (deserialized.get("params") instanceof KMetadata);
    }

    @Test
    void annotation() {
        KAnnotation object = Annotation.create("belaCagada", "one", 1, "oneString", "one", "params",
                Annotation.create("cazzarola", "one", 1));
        String serialized = Utils.Json.asString(object);
        KAnnotation deserialized = Utils.Json.parseObject(serialized, KAnnotation.class);
        System.out.println(serialized);
        System.out.println(deserialized.getClass());
        System.out.println(deserialized.get("params").getClass());
        assert (deserialized instanceof KAnnotation);
        assert (deserialized.get("params") instanceof KAnnotation);
    }

    @Test
    void geometry() {
        KGeometry geometry = Geometry.create(centralColombia);
        String serialized = Utils.Json.asString(geometry);
        KGeometry deserialized = Utils.Json.parseObject(serialized, KGeometry.class);
        System.out.println(serialized);
        assert (deserialized instanceof KGeometry);
        String before = geometry.encode();
        String after = deserialized.encode();
        assertEquals(before, after);
    }

    private Object serializeAndDeserializeLiteral(Object o) {
        Literal literal = Literal.of(o);
        String serialized = Utils.Json.asString(literal);
        System.out.println(serialized);
        return Utils.Json.parseObject(serialized, KLiteral.class).get(Object.class);
    }

    @Test
    void literals() {
        // TODO use the above with all kinds of things
        assert (serializeAndDeserializeLiteral(10) instanceof Integer);
        assert (serializeAndDeserializeLiteral("Zorba") instanceof String);

    }
}
