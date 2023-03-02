package org.integratedmodelling.klab.test.core;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.data.collections.SerializableParameters;
import org.integratedmodelling.klab.utils.Utils;
import org.junit.jupiter.api.Test;

class SerializationTests {

    @Test
    void parameters() {
        SerializableParameters<Object> object = SerializableParameters.create("one", 1, "oneString", "one", "params",
                SerializableParameters.create("one", 1));
        String serialized = Utils.Json.asString(object);
        System.out.println(serialized);
        Object deserialized = Utils.Json.parseObject(serialized, KParameters.class);
        System.out.println(deserialized);
    }

}
