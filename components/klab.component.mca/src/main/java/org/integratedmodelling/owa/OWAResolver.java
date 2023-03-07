package org.integratedmodelling.owa;

import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

public class OWAResolver extends AbstractContextualizer implements IResolver<IState>, IExpression{

    public Type getType(){
        return Type.NUMBER;
    }

    public IState resolve(IState target, IContextualizationScope context) throws KlabException {

        IState dem = context.getArtifact("owa.layer", IState.class);

        GridCoverage2D coverage = GeotoolsUtils.INSTANCE.stateToCoverage(dem, context.getScale(),DataBuffer.TYPE_FLOAT,floatNovaluue, false);

        RandomIter inputIter = CoverageUtilities.getRandomIterator(coverage);

        double nv = HMConstants.getNovalue(coverage);

        RegionMap regionMap = CoverageUtilities.getRegionParamsFromGridCoverage(coverage);
        int rows = regionMap.getRows();
        int cols = regionMap.getCols();

        WritableRaster outWR = CoverageUtilities.createWritableRaster(cols,rows,null,null,nv);
        WritableRandomIter outIter = CoverageUtilities.getWritableRandomIterator(outWR);

        try {

            for(int r=0; r<rows; r++) {
                for(int c=0; c<cols; c++){
                    // Diego: I guess the third argument in getSampleDouble is to access the value of a given layer
                    // then we either need to iterate over layers or to retrieve all as an array.
                    double value = inputIter.getSampleDouble(c,r,layerID);
                    if (!HMConstants.isNovalue(value,nv)){
                        // DO THE OWA FOR THIS PIXEL HERE
                    }
                }
                // Diego: again I think the 3rd arg correpsonds to the layer, the ouput is only one layer so 0 is good.
                outIter.setSample(c,r,0,value);
            }
        } finally {
            outIter.done();
            inputIter.done();
        }

        if (!context.getMonitor().isInterrupted()) {
            GridCoverage2D outCoverage = CoverageUtilities.buildCoverageWithNoValue("raster",outWR, regionMap, coverage.getCoordinateReferenceSystem(), nv);
            GeotoolsUtils.INSTANCE.coverageToState(outCoverage, target, context.getScale(), null, null)
        }

    }

    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws  KlabException{
        OWAResolver res = new OWAResolver();
        res.ordinalWeights = parameters.get("ordinal_weights", HashMap<Int, Double>.class);
        // Diego: alternatively and as discussed during the meeting a risk profile can be passed
        // instead of the explicit weights:
        res.riskProfile = parameters.get("risk_profile", Double.class)
        res.relevanceWeights = parameters.get("relevance_weights", HashMap<Int, Double>.class);
    }

}