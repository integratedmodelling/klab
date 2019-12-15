package nl.alterra.shared.rasterdata;

import nl.alterra.shared.datakind.Category;
import nl.wur.iclue.parameter.SpatialDataset;

/**
 * FV added: if a dataset is a IMaskeable, the cut() operation won't create a new dataset but just
 * add the mask and let the dataset deal with it.
 * 
 * @author Ferd
 *
 */
public interface IMaskeable {

	void setMask(SpatialDataset regions, Category regionOfInterest);
	
}
