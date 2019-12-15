package nl.alterra.shared.rasterdata;

/**
 * TODO implement on top of k.LAB observations.
 * 
 * @author ferdinando.villa
 *
 */
public class RasterDataFactory {

	public static RasterDataStack createStack(Object...objects) {
		RasterDataStack ret = new RasterDataStack();
		// TODO 
		if (objects != null) {
			System.out.println("HOSTIA do something with the objects");
		}
		return ret;
	}

	public static RasterData createRasterData(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

}
