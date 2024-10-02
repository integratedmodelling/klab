package org.integratedmodelling.klab.components.geospace.routing.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.routing.Valhalla;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaException;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.GeometryCollapser;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.IsochroneType;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.TransportType;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Geometry;

public class IsochroneInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {
    private String sourceArtifact = null;
    private TransportType transportType = TransportType.Auto;
    private GeometryCollapser geometryCollapser = GeometryCollapser.Centroid;
    private IsochroneType isochroneType;
    private String server;
    private Valhalla valhalla;

    private double range;

    public IsochroneInstantiator(Parameters<Object> create, IContextualizationScope context) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope scope) throws KlabException {
        IConcept sourceConcept = Observables.INSTANCE.getRelationshipSource(semantics.getType());

        List<IObservation> sources = new ArrayList<>();
        if (sourceArtifact == null) {
            sources.addAll(scope.getObservations(sourceConcept));
        } else {
            IArtifact src = scope.getArtifact(sourceArtifact);
            if (src instanceof IObservationGroup) {
                for (IArtifact a : src) {
                    sources.add((IObservation) a);
                }
            }
        }

        boolean sourcesAreObjectArtifact = sources.parallelStream().allMatch(element -> element instanceof IObjectArtifact);
        if (!sourcesAreObjectArtifact) {
            throw new IllegalArgumentException(
                    "klab.networks.routing: at least one source does not exist or is not an object artifact");
        }

        List<Geometry> isochrones = new Vector();
        for(IObservation source : sources) {
            double[] sourceCoordinates = Valhalla.getCoordinates((IDirectObservation) source, geometryCollapser);

            String valhallaInput = Valhalla.buildValhallaIsochroneInput(sourceCoordinates, transportType.getType(), isochroneType.getType(), range);
            try {
                Geometry geometry = valhalla.isochrone(valhallaInput);
                isochrones.add(geometry);
            } catch (ValhallaException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // TODO return the value
        return null;
    }

    @Override
    public Object eval(IContextualizationScope scope, Object... parameters) {
        return new IsochroneInstantiator(Parameters.create(parameters), scope);
    }

}
