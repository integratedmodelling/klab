package org.integratedmodelling.klab.components.geospace.routing.services;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.routing.Valhalla;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaException;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.GeometryCollapser;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.IsochroneType;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.TransportType;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRemoteException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;
import org.locationtech.jts.geom.Geometry;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class IsochroneInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {
    private String source = null;
    private TransportType transportType = TransportType.Auto;
    private GeometryCollapser geometryCollapser = GeometryCollapser.Centroid;
    private IsochroneType isochroneType;
    private String server;
    private Valhalla valhalla;

    private double range;

    public IsochroneInstantiator() {
    }

    // TODO merge with the one in RoutingRelationshipInstantiator
    private boolean isValhallaServerOnline(String server) {
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get(server + "/status").asJson();
        } catch (Exception e) {
            throw new KlabRemoteException("Cannot access Valhalla server. Reason: " + e.getMessage());
        }
        if (response.getStatus() != 200) {
            return false;
        }
        return true;
    }

    public IsochroneInstantiator(Parameters<Object> parameters, IContextualizationScope scope) {
        this.server = parameters.get("server", String.class);
        this.source = parameters.get("source", String.class);
        if (isValhallaServerOnline(server)) {
            this.valhalla = new Valhalla(server);
        } else {
            throw new KlabRemoteException("The server " + server + " is offline or not a valid Valhalla instance.");
        }
        if (parameters.containsKey("transport")) {
            this.transportType = TransportType.fromValue(Utils.removePrefix(parameters.get("transport", String.class)));
        }
        if (parameters.containsKey("collapse_geometry")) {
            this.geometryCollapser = GeometryCollapser
                    .fromValue(Utils.removePrefix(parameters.get("collapse_geometry", String.class)));
        }
        this.range = 10.0;
        this.isochroneType = IsochroneType.Time;
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {
        List<IObjectArtifact> ret = new ArrayList<>();
        IArtifact sources = context.getArtifact(this.source);
        int isochroneCount = 0;
        for(IArtifact source : sources) {
            double[] coordinates = Valhalla.getCoordinates((IDirectObservation) source, geometryCollapser);
            //TODO avoid the false part and pass it as a parameter
            String valhallaInput = Valhalla.buildValhallaIsochroneInput(coordinates, transportType.getType(), isochroneType.getType(), range, false);
            try {
                Geometry geometry = valhalla.isochrone(valhallaInput);
                ret.add(context.newObservation(semantics, Observables.INSTANCE.getDisplayName(semantics) + "_" + isochroneCount++,
                        Scale.substituteExtent(context.getScale(), Shape.create(geometry.toText(), Projection.getDefault())),
                        /* TODO send useful metadata */null));
            } catch (ValhallaException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) {
        return new IsochroneInstantiator(Parameters.create(parameters), context);
    }

}
