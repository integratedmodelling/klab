package org.integratedmodelling.landcover;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;

import nl.alterra.shared.rasterdata.RasterData;
import nl.alterra.shared.rasterdata.RasterDataStack;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.Parameters;

/**
 * Port of iCLUE to k.LAB, trying to be the least invasive possible.
 * 
 * Sketch of modifications:
 * <p>
 * <ul>
 * <li>port {@link Landuse} and related classes from concepts</li>
 * <li>port Demand and related classes from dependencies</li>
 * <li>port transitions and generalize the conditional case to using generic
 * expression rather than "in ....", including CA-like syntax.</li>
 * <li>build {@link SpatialDataset}, {@link RasterData} and
 * {@link RasterDataStack} from (located) IState</li>
 * <li>SuitabilityCalculator will wrap a resource tied to the predictors, using
 * the code in ML at the beginning (a better API for computed resources should
 * be developed). CLUE's own suitability calculators can be removed.</li>
 * </ul>
 * <li>Extend {@link Parameters} to create all this based on a k.LAB
 * contextualization scope.</li>
 * 
 * @author ferdinando.villa
 *
 */
@Component(id = "org.integratedmodelling.landcover", version = Version.CURRENT)
public class LandCoverChangeComponent {

}
