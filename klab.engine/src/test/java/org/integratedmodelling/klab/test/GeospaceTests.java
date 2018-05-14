/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.extents.mediators.Subgrid;
import org.integratedmodelling.klab.components.geospace.utils.SpatialDisplay;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Runs various scale subsetting and indexing tests using regular and irregular
 * space and time extents.
 * 
 * @author ferdinando.villa
 *
 */
public class GeospaceTests {

	static String tzShape = "EPSG:4326 POLYGON((33.796 -7.086, 35.946 -7.086, 35.946 -9.41, 33.796 -9.41, 33.796 -7.086))";
	static String tzShapeTop = "EPSG:4326 POLYGON((33.796 -7.086, 35.946 -7.086, 35.946 -8.00, 33.796 -8.00, 33.796 -7.086))";
	static String tzShapeBottom = "EPSG:4326 POLYGON((33.796 -8.00, 35.946 -8.00, 35.946 -9.41, 33.796 -9.41, 33.796 -8.00))";

	IServiceCall tzcall = KimServiceCall.create("space", "grid", "10 km", "shape", tzShape);
	IServiceCall tztopcall = KimServiceCall.create("space", "grid", "10 km", "shape", tzShapeTop);
	IServiceCall tzbotcall = KimServiceCall.create("space", "grid", "10 km", "shape", tzShapeBottom);

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
	public void testSubgrids() throws IOException {

		Scale tzScale = Scale
				.create((IExtent) Extensions.INSTANCE.callFunction(tzcall, Klab.INSTANCE.getRootMonitor()));
		Shape tzShapeUp = Shape.create(tzShapeTop);
		Shape tzShapeDown = Shape.create(tzShapeBottom);

		Grid grid = (Grid) ((Space) tzScale.getSpace()).getGrid().get();
		IGrid gridUp = Subgrid.create(grid, tzShapeUp);
		IGrid gridDown = Subgrid.create(grid, tzShapeDown);

		if (System.getProperty("visualize", "false").equals("true")) {
			
			SpatialDisplay display = new SpatialDisplay(tzScale.getSpace());

			display.add(((Space) tzScale.getSpace()).getGrid().get(), "original");
			display.add(gridUp, "up");
			display.add(gridDown, "down");

			display.show();
			// block to see the display
			System.out.print("Press a key to continue...");
			System.in.read();
		}
		
		assert(NumberUtils.equal(gridUp.getCellWidth(), grid.getCellWidth()));
		assert(NumberUtils.equal(gridDown.getCellWidth(), grid.getCellWidth()));
		assert(NumberUtils.equal(gridUp.getCellHeight(), grid.getCellHeight()));
		assert(NumberUtils.equal(gridDown.getCellHeight(), grid.getCellHeight()));
		assert(gridUp.getXCells() == grid.getXCells());
		// allow 1-cell overlap on the Y axis
		assert(gridUp.getYCells() + gridDown.getYCells() - grid.getYCells() <= 1);
	}

}
