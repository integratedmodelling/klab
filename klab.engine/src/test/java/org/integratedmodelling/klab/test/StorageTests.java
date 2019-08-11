/**
 * 
 */
package org.integratedmodelling.klab.test;

import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryLocator;
import org.integratedmodelling.klab.test.storage.MemoryBackedAdaptiveStorage;
import org.junit.Test;

/**
 * Runs various scale subsetting and indexing tests using regular and irregular
 * space and time extents.
 * 
 * @author ferdinando.villa
 *
 */
public class StorageTests {

	@Test
	public void readwrite() {

		// 10x10x10
		Geometry geometry = Geometry.create("T1(10)S2(10,10)");
		MemoryBackedAdaptiveStorage<Integer> storage = new MemoryBackedAdaptiveStorage<Integer>(geometry,
				Integer.class);

		System.out.println("WRITING:");
		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				System.out.print(spaceX == 0 ? "\nT" + time + ": " : "\n    ");
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					GeometryLocator locator = GeometryLocator.create(time + ",(" + spaceX + "," + spaceY + ")",
							geometry);
					int value = time * ((spaceX * 100) + spaceY);
					// first slice should be all 0s, then predictable values
					storage.setValue(value, locator);
					System.out.print(String.format("%04d", value) + " ");
				}
			}
			System.out.println();
		}

		System.out.println("\nREADING:");
		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				System.out.print(spaceX == 0 ? "\nT" + time + ": " : "\n    ");
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					GeometryLocator locator = GeometryLocator.create(time + ",(" + spaceX + "," + spaceY + ")",
							geometry);
					int value = storage.getValue(locator);
					System.out.print(String.format("%04d", value) + " ");
				}
			}
			System.out.println();
		}

		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					GeometryLocator locator = GeometryLocator.create(time + ",(" + spaceX + "," + spaceY + ")",
							geometry);
					assert (storage.getValue(locator).equals((time * ((spaceX * 100) + spaceY))));
				}
			}
		}

	}

}
