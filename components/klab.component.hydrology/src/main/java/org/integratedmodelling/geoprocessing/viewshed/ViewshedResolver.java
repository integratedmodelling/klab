package org.integratedmodelling.geoprocessing.viewshed;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.hortonmachine.gears.utils.features.FeatureUtilities;
import org.hortonmachine.hmachine.modules.geomorphology.viewshed.OmsViewshed;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class ViewshedResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

    Double height = 100.0;

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... params) throws KlabException {
        Parameters<String> parameters = Parameters.create(params);
        ViewshedResolver ret = new ViewshedResolver();
        if (parameters.containsKey("height")) {
            ret.height = parameters.get("height", Double.class);
        }
        return ret;
    }

    @Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {

        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Viewshed");

        OmsViewshed algorithm = new OmsViewshed();

        if (context.getArtifact("artifact") instanceof IObjectArtifact) {
            IObjectArtifact artifacts = (IObjectArtifact) context.getArtifact("artifact");
            List<Geometry> viewPoints = new ArrayList<>();
            CoordinateReferenceSystem crs = null;
            for(IArtifact artifact : artifacts) {
                ISpace space = ((IObservation) artifact).getSpace();
                if (space == null) {
                    continue;
                }
                Point point = ((Shape) space.getShape()).getJTSGeometry().getCentroid();
                point.setUserData(height);
                viewPoints.add(point);
                if (crs == null) {
                    crs = ((Space) space).getShape().getJTSEnvelope().getCoordinateReferenceSystem();
                }
            }
            SimpleFeatureCollection viewPointsCollection = FeatureUtilities.featureCollectionFromGeometry(crs,
                    viewPoints.toArray(new Geometry[0]));
            algorithm.inViewPoints = viewPointsCollection;
            algorithm.pField = "userdata";
            algorithm.pm = taskMonitor;
        } else {
            throw new KlabValidationException("The viewshed algorithm needs at least one input view point.");
        }

        IState dem = context.getArtifact("elevation", IState.class);
        if (dem != null) {
            algorithm.inRaster = GeotoolsUtils.INSTANCE.stateToCoverage(dem, context.getScale(), DataBuffer.TYPE_FLOAT,
                    floatNovalue, false);
        } else {
            throw new KlabValidationException("The viewshed algorithm needs an input elevation grid.");
        }

        try {
            algorithm.process();
        } catch (Exception e) {
            throw new KlabException(e);
        }
        if (!context.getMonitor().isInterrupted()) {
            GeotoolsUtils.INSTANCE.coverageToState(algorithm.outViewshed, target, context.getScale(), null);
        }

        return target;
    }

}
