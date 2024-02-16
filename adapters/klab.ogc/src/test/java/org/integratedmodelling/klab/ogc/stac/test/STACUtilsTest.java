package org.integratedmodelling.klab.ogc.stac.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.integratedmodelling.klab.stac.STACUtils;
import org.junit.jupiter.api.Test;

public class STACUtilsTest {

    @Test
    public void getExtensionName_getAnExtensionName() {
        String extensionId = "https://stac-extensions.github.io/file/v2.1.0/schema.json";

        String ret = STACUtils.getExtensionName(extensionId);

        assertThat("file", equalTo(ret));
    }
}
