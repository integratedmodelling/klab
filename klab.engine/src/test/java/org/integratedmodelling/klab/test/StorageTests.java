/**
 * 
 */
package org.integratedmodelling.klab.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.storage.FileMappedStorage;
import org.integratedmodelling.klab.scale.Scale;
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
	public void sumIncludesCarriedForwardCellsAndReflectsOverwrites() throws IOException {
		Time time = Time.create(ITime.Type.GRID, ITime.Resolution.Type.YEAR, 1.0,
				TimeInstant.create(2020), TimeInstant.create(2022), null);
		Geometry grid = Geometry.create("S2(3,1){bbox=[0 3 0 1],proj=EPSG:4326}");
		Scale scale = Scale.create(time, Space.create(grid.getDimensions().get(0), null));
		try (MemoryBackedAdaptiveStorage<Double> storage = new MemoryBackedAdaptiveStorage<>(scale, Double.class)) {
			IScale firstStep = scale.at(time.getExtent(1));
			storage.put(1.0, firstStep.at(0));
			storage.put(1.0, firstStep.at(1));
			assertEquals(2.0, storage.sumAt(firstStep), 0.0);

			IScale secondStep = scale.at(time.getExtent(2));
			storage.put(1.0, secondStep.at(0));
			storage.put(null, secondStep.at(1));
			storage.put(3.0, secondStep.at(2));
			assertEquals(4.0, storage.sumAt(secondStep), 0.0);

			storage.put(5.0, secondStep.at(2));
			assertEquals(6.0, storage.sumAt(secondStep), 0.0);
			assertEquals(2.0, storage.sumAt(firstStep), 0.0);
		}
	}

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
					Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
					int value = time * ((spaceX * 100) + spaceY);
					// first slice should be all 0s, then predictable values
					storage.put(value, locator);
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
					Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
					int value = storage.get(locator);
					System.out.print(String.format("%04d", value) + " ");
				}
			}
			System.out.println();
		}

		// now for the actual test
		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
					assert (storage.get(locator).equals((time * ((spaceX * 100) + spaceY))));
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
					Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
					int value = 200;
					storage.put(value, locator);
				}
			}
		}

		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
					int value = storage.get(locator);
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
					Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
					int value = time < 9 ? source[spaceX][spaceY] : source[spaceX][spaceY] - 25;
					// first slice should be all 0s, then predictable values
					storage.put(value, locator);
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
					Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
					int value = storage.get(locator);
					System.out.print(String.format("%04d", value) + " ");
				}
			}
			System.out.println();
		}

		// now for the actual test
		for (int time = 0; time < 10; time++) {
			for (int spaceX = 0; spaceX < 10; spaceX++) {
				for (int spaceY = 0; spaceY < 10; spaceY++) {
					Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
					int value = time < 9 ? source[spaceX][spaceY] : source[spaceX][spaceY] - 25;
					assert (storage.get(locator).equals(value));
				}
			}
		}

		assert (storage.getSliceCount() == 2);

	}

	@Test
	public void readwrite_mapped() {

		// 10x10x10
		Geometry geometry = Geometry.create("T1(10)S2(10,10)");
		try (FileMappedStorage<Integer> storage = new FileMappedStorage<Integer>(geometry, Integer.class)) {

			System.out.println("WRITING:");
			for (int time = 0; time < 10; time++) {
				for (int spaceX = 0; spaceX < 10; spaceX++) {
					System.out.print(spaceX == 0 ? "\nT" + time + ": " : "\n    ");
					for (int spaceY = 0; spaceY < 10; spaceY++) {
						Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
						int value = time * ((spaceX * 100) + spaceY);
						// first slice should be all 0s, then predictable values
						storage.put(value, locator);
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
						Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
						int value = storage.get(locator);
						System.out.print(String.format("%04d", value) + " ");
					}
				}
				System.out.println();
			}

			// now for the actual test
			for (int time = 0; time < 10; time++) {
				for (int spaceX = 0; spaceX < 10; spaceX++) {
					for (int spaceY = 0; spaceY < 10; spaceY++) {
						Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
						assert (storage.get(locator).equals((time * ((spaceX * 100) + spaceY))));
					}
				}
			}
		}
	}

	@Test
	public void singlevalued_mapped() {

		/*
		 * Write the same value 1000 times and check we return it and have no slices..
		 */
		Geometry geometry = Geometry.create("T1(10)S2(10,10)");
		try (FileMappedStorage<Integer> storage = new FileMappedStorage<Integer>(geometry, Integer.class)) {

			for (int time = 0; time < 10; time++) {
				for (int spaceX = 0; spaceX < 10; spaceX++) {
					for (int spaceY = 0; spaceY < 10; spaceY++) {
						Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
						int value = 200;
						storage.put(value, locator);
					}
				}
			}

			for (int time = 0; time < 10; time++) {
				for (int spaceX = 0; spaceX < 10; spaceX++) {
					for (int spaceY = 0; spaceY < 10; spaceY++) {
						Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
						int value = storage.get(locator);
						assert (value == 200);
					}
				}
			}

			assert (storage.getBackendSliceCount() == 1);

		}

	}

	@Test
	public void repeating_mapped() {

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

		try (FileMappedStorage<Integer> storage = new FileMappedStorage<Integer>(geometry, Integer.class)) {

			System.out.println("WRITING:");
			for (int time = 0; time < 10; time++) {
				for (int spaceX = 0; spaceX < 10; spaceX++) {
					System.out.print(spaceX == 0 ? "\nT" + time + ": " : "\n    ");
					for (int spaceY = 0; spaceY < 10; spaceY++) {
						Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
						int value = time < 9 ? source[spaceX][spaceY] : source[spaceX][spaceY] - 25;
						// first slice should be all 0s, then predictable values
						storage.put(value, locator);
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
						Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
						int value = storage.get(locator);
						System.out.print(String.format("%04d", value) + " ");
					}
				}
				System.out.println();
			}

			// now for the actual test
			for (int time = 0; time < 10; time++) {
				for (int spaceX = 0; spaceX < 10; spaceX++) {
					for (int spaceY = 0; spaceY < 10; spaceY++) {
						Offset locator = Offset.create(time + ",(" + spaceX + "," + spaceY + ")", geometry);
						int value = time < 9 ? source[spaceX][spaceY] : source[spaceX][spaceY] - 25;
						assert (storage.get(locator).equals(value));
					}
				}
			}

			assert (storage.getSliceCount() == 2);

		}

	}

}
