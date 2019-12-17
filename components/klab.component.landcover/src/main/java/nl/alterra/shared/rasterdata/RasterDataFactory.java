package nl.alterra.shared.rasterdata;

import nl.wur.iclue.model.CLUEModel;

/**
 * TODO implement on top of k.LAB observations.
 * 
 * @author ferdinando.villa
 *
 */
public class RasterDataFactory {

	public static RasterDataStack createStack(CLUEModel model, Object...objects) {
		
		
		RasterDataStack ret = new RasterDataStack(model);
		// TODO 
		if (objects != null) {
//			System.out.println("HOSTIA do something with the objects");
		}
		return ret;
	}

	public static RasterData createRasterData(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

}
