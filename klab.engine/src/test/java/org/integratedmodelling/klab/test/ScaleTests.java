/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Runs various scale subsetting and indexing tests using regular and irregular space and time
 * extents.
 * 
 * @author ferdinando.villa
 *
 */
public class ScaleTests {

    static String tzShape = "EPSG:4326 POLYGON((33.796 -7.086, 35.946 -7.086, 35.946 -9.41, 33.796 -9.41, 33.796 -7.086))";
    static String tzShapeTop = "EPSG:4326 POLYGON((33.796 -7.086, 35.946 -7.086, 35.946 -8.00, 33.796 -8.00, 33.796 -7.086))";
    static String tzShapeBottom = "EPSG:4326 POLYGON((33.796 -8.00, 35.946 -8.00, 35.946 -9.41, 33.796 -9.41, 33.796 -8.00))";

    IServiceCall tzcall = KimServiceCall.create("space", "grid", "1 km", "shape", tzShape);
    IServiceCall tztopcall = KimServiceCall.create("space", "grid", "1 km", "shape", tzShapeTop);
    IServiceCall tzbotcall = KimServiceCall.create("space", "grid", "1 km", "shape", tzShapeBottom);

    static Engine engine;
    List<Scale> scales = new ArrayList<>();

    @BeforeClass
    public static void setUp() throws Exception {
        engine = Engine.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        engine.stop();
    }

    @Test
    public void testGeometryExtraction() {

        Scale tzScale = Scale
                .create((IExtent) Extensions.INSTANCE.callFunction(tzcall, Klab.INSTANCE.getRootMonitor()));

        // check encoding to JSON completes w/o error
        System.out.println(JsonUtils.printAsJson(tzScale.asGeometry()));
        // check encoding as string
        String gdef = tzScale.asGeometry().encode();
        System.out.println(gdef);
        Geometry gcopy = Geometry.create(gdef);
        // check equality
        assert (gcopy.equals(tzScale.asGeometry()));
    }

    /**
     * Basic test of merge operations on coverages.
     * 
     * @throws Exception
     */
    @Test
    public void testCoverageOperations() throws Exception {

        Scale tzScale = Scale
                .create((IExtent) Extensions.INSTANCE.callFunction(tzcall, Klab.INSTANCE.getRootMonitor()));
        Scale tzScaleBot = Scale
                .create((IExtent) Extensions.INSTANCE.callFunction(tzbotcall, Klab.INSTANCE.getRootMonitor()));
        Scale tzScaleTop = Scale
                .create((IExtent) Extensions.INSTANCE.callFunction(tztopcall, Klab.INSTANCE.getRootMonitor()));

        ICoverage total = Coverage.empty(tzScale);
        ICoverage bottom = Coverage.full(tzScaleBot);
        ICoverage top = Coverage.full(tzScaleTop);

        // uncomment to check operands visually 
        //    SpatialDisplay display = new SpatialDisplay(tzScale.getSpace());
        //    display.add(tzScaleBot.getSpace(), "bottom");
        //    display.add(tzScaleTop.getSpace(), "top");
        //    display.add((ISpace)tzScaleTop.getSpace().merge(tzScaleBot.getSpace(), LogicalConnector.UNION), "both");
        //    display.show();

        // OR every piece into empty coverage
        assert (total.getCoverage() == 0);
        // add bottom piece, should become > 0.6
        total = total.merge(bottom, LogicalConnector.UNION);
        assert (total.getCoverage() > 0.6 && total.getCoverage() < 0.7);
        // add top piece into result, should bring coverage back to 1
        total = total.merge(top, LogicalConnector.UNION);
        assert (total.getCoverage() == 1);

        // take full coverage and AND it with bottom, resulting in bottom
        total = total.merge(bottom, LogicalConnector.INTERSECTION);
        assert (total.getCoverage() < 0.7 && total.getCoverage() > 0.6);
        // take resulting bottom and AND with top, resulting in empty
        total = total.merge(top, LogicalConnector.INTERSECTION);
        assert (total.getCoverage() == 0);

        // TODO test with multiple dimensions
    }

}
