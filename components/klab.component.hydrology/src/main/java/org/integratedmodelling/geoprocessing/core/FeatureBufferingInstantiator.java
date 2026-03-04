package org.integratedmodelling.geoprocessing.core;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;

public class FeatureBufferingInstantiator extends AbstractContextualizer implements IInstantiator, IExpression {

    private double distance;
    private String artifact;
    private boolean subtract;
    private double simplify = 0;

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... params) throws KlabException {
        Parameters<String> parameters = Parameters.create(params);
        FeatureBufferingInstantiator ret = new FeatureBufferingInstantiator();
        ret.distance = parameters.get("radius", Double.class);
        ret.artifact = parameters.get("artifact", String.class);
        ret.subtract = parameters.get("subtract", Boolean.FALSE);
        ret.simplify = parameters.get("simplify", 0);
        return ret;
    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context)
            throws KlabException {

        IArtifact source = artifact == null ? context.getTargetArtifact() : context.getArtifact(artifact);
        boolean transform = artifact == null;

        if (!(source instanceof IObjectArtifact)) {
            throw new IllegalArgumentException(
                    "buffer instantiator: source artifact does not exist or is not an object artifact");
        }

        double bdistance = context.getScale().getSpace().getEnvelope().metersToDistance(this.distance);

        List<IObjectArtifact> ret = new ArrayList<>();
        context.getMonitor().info("starting spatial buffer operation");

        int tot = 0;
        int spc = 0;

        for (IArtifact obj : source) {
            ISpace space = ((IObservation) obj).getSpace();
            if (space == null) {
                tot++;
                continue;
            }

            IShape originalShape = space.getShape();
            IShape workingShape = originalShape;

            // Optional simplification before buffering
            if (simplify > 0) {
                workingShape = ((Shape) workingShape).getSimplified(simplify);
            }

            // Apply buffer (positive = external, negative = internal)
            IShape newShape = workingShape.buffer(bdistance);

            // If buffer collapses, skip
            if (newShape == null || newShape.isEmpty()) {
                tot++;
                continue;
            }

            // Optional simplification after buffering
            if (simplify > 0) {
                ((Shape) newShape).simplify(simplify);
            }

            // Subtract only makes sense for external buffers
            if (subtract && distance > 0 &&
                originalShape.getGeometryType() != IShape.Type.POINT &&
                originalShape.getGeometryType() != IShape.Type.MULTIPOINT) {

                newShape = newShape.difference(originalShape);

                if (newShape == null || newShape.isEmpty()) {
                    tot++;
                    continue;
                }
            }

            IScale newScale = Scale.substituteExtent(((IObservation) obj).getScale(), newShape);

            if (transform) {
                ((ObservedArtifact) obj).setGeometry(newScale);
            } else {
                ret.add(context.newObservation(
                        semantics,
                        ((IDirectObservation) obj).getName() + "_buffered",
                        newScale,
                        null
                ));
            }

            spc++;
            tot++;
        }

        context.getMonitor().info("buffer operation " + (transform ? "modified" : "created") + " " + spc
                + (tot > spc ? (" (" + (tot - spc) + " skipped)") : ""));

        return ret;
    }
}
// REWRITTEN WITH COPILOT TO ALLOW NEGATIVE BUFFER