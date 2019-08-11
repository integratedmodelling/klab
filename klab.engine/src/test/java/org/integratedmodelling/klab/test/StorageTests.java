/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.util.Random;

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

		// now for the actual test
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

	@Test
	public void singlevalued() {

		/*
		 * Write the same value 1000 times and check we return it and have no slices..
		 */
		Geometry geometry = Geometry.create("T1(10)S2(10,10)");
		MemoryBackedAdaptiveStorage<Integer> storage = new MemoryBackedAdaptiveStorage<Integer>(geometry,
				Integer.class);

		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					GeometryLocator locator = GeometryLocator.create(time + ",(" + spaceX + "," + spaceY + ")",
							geometry);
					int value = 200;
					storage.setValue(value, locator);
				}
			}
		}

		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					GeometryLocator locator = GeometryLocator.create(time + ",(" + spaceX + "," + spaceY + ")",
							geometry);
					int value = storage.getValue(locator);
					assert (value == 200);
				}
			}
		}

		assert (storage.getBackendSliceCount() == 0);

	}

	@Test
	public void repeating() {

		/*
		 * Write the same array 9 times, then change it, and check that there are 2
		 * slices in storage.
		 */
		Random random = new Random();
		int[][] source = new int[10][10];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				source[i][j] = (int) (random.nextDouble() * 9999.0);
			}
		}

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
					int value = time < 9 ? source[spaceX][spaceY] : source[spaceX][spaceY] - 25;
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

		// now for the actual test
		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					GeometryLocator locator = GeometryLocator.create(time + ",(" + spaceX + "," + spaceY + ")",
							geometry);
					int value = time < 9 ? source[spaceX][spaceY] : source[spaceX][spaceY] - 25;
					assert (storage.getValue(locator).equals(value));
				}
			}
		}

		assert (storage.getSliceCount() == 2);

	}

}
