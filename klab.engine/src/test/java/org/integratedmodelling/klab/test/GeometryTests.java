package org.integratedmodelling.klab.test;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Offset;
import org.junit.Test;

public class GeometryTests {

	@Test
	public void basic_iteration() {

		Geometry geometry = Geometry.create("T1(2)S2(3,3)");

		System.out.println("Full geometry:");
		int n = 0;
		for (ILocator locator : geometry) {
			System.out.println("   " + locator);
			n++;
		}
		assert (n == 18);

		System.out.println("Only T=1:");
		n = 0;
		for (ILocator locator : geometry.at(ITime.class, 1)) {
			assert (locator.as(Offset.class).pos[0] == 1);
			System.out.println("   " + locator);
			n++;
		}
		assert (n == 9);
		
		System.out.println("Only S=4:");
		n = 0;
		for (ILocator locator : geometry.at(ISpace.class, 4)) {
			assert (locator.as(Offset.class).pos[1] == 4);
			System.out.println("   " + locator);
			n++;
		}
		assert (n == 2);

		System.out.println("Only T=1, S(1,2):");
		for (ILocator locator : geometry.at(ITime.class, 1, ISpace.class, 1, 2)) {
			System.out.println("   " + locator);
			assert (locator.equals(Offset.create("1,(1,2)", geometry)));
		}

	}

}
