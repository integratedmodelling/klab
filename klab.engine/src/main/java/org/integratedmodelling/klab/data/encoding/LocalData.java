package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.runtime.artifacts.DataArtifact;
import org.integratedmodelling.klab.components.runtime.artifacts.ObjectArtifact;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Utils;

/**
 * A flexible data container that can be initialized and built both in a semantic scope and in a
 * data-only, artifact-only scope.
 * 
 * @author Ferd
 *
 */
public class LocalData implements IKlabData {

    IDataArtifact state;
    IArtifact object;
    List<INotification> notifications = new ArrayList<>();
    boolean error = false;
    IConcept semantics = null;
    IUnit originalUnit = null;
    IUnit targetUnit = null;

    static Set<String> reservedFields = null;

    static {
        reservedFields = new HashSet<>();
        reservedFields.add("objects");
        reservedFields.add("states");
        reservedFields.add("geometry");
        reservedFields.add("name");
    }

    public LocalData(LocalDataBuilder builder) {
        if (builder.state != null) {
            this.state = builder.state;
        }
        if (builder.observation != null) {
            this.object = builder.observation;
        }
        for (INotification notification : builder.notifications) {
            if (notification.getLevel().equals(Level.SEVERE.getName())) {
                error = true;
            }
            notifications.add(notification);
        }
        if (builder.semantics != null) {
            this.semantics = builder.semantics;
        }
    }

    /**
     * Dumb version that produces artifacts directly and does not use a scope to incarnate them in
     * an observation structure. Monitor gets the notifications unless null.
     * 
     * @param data
     */
    public LocalData(Map<?, ?> data, IMonitor monitor) {

        if (data.containsKey("states")) {
            for (Object s : (Iterable<?>) data.get("states")) {

                Map<?, ?> state = (Map<?, ?>) s;
                IScale scale = Scale.create(Geometry.create(state.get("geometry").toString()));
                String name = state.get("name").toString();

                IDataArtifact art = new DataArtifact(name, getDataType(state), scale, getData(state));
                if (this.state == null) {
                    this.state = art;
                } else {
                    ((Artifact) this.state).chain(art);
                }

                /*
                 * OK but I don't think this gets ever called (only outside of regular
                 * contextualizations as it gets used if the scope is not a IRuntimeScope). This
                 * sets the builder-wide unit but I don't think it's ever accessed after this.
                 */
                if (state.containsKey("metadata")) {
                    /*
                     * TODO check out units
                     */
                    Map<?, ?> metadata = (Map<?, ?>) state.get("metadata");
                    if (metadata != null && metadata.containsKey("originalUnit")) {
                        this.originalUnit = Unit.create(metadata.get("originalUnit").toString());
                    }
                }

            }
        } else if (data.containsKey("objects")) {

            for (Object object : ((List<?>) data.get("objects"))) {

                Map<?, ?> obj = (Map<?, ?>) object;

                IScale scale = Scale.create(Geometry.create(obj.get("geometry").toString()));

                IObjectArtifact output = new ObjectArtifact(obj.get("name").toString(), scale);

                /*
                 * add any states or other artifacts. TODO not sure how this works with object
                 * structures, or even whether it should at all.
                 */
                if (obj.containsKey("states")) {
                    for (Object state : (List<?>) obj.get("states")) {

                        Map<?, ?> sob = (Map<?, ?>) state;
                        String stateName = sob.get("name").toString();
                        IDataArtifact st = new DataArtifact(stateName, getDataType(sob), scale, getData(sob));
                        ((ObjectArtifact) output).addChild(st);
                    }
                }

                if (this.object == null) {
                    this.object = output;
                } else {
                    ((Artifact) this.object).chain(output);
                }

            }

        }

        if (data.containsKey("notifications")) {
            // TODO send them over to the monitor
            for (Object o : (List<?>) data.get("notification")) {
                // System.out.println("GOT NOTIFICATION " + o);
            }
        }

    }

    private IArtifact.Type getDataType(Map<?, ?> data) {
        if (data.containsKey("doubledata") || data.containsKey("intdata")) {
            return IArtifact.Type.NUMBER;
        } else if (data.containsKey("booleandata")) {
            return IArtifact.Type.BOOLEAN;
        }
        // TODO categories
        return IArtifact.Type.VALUE;
    }

    /**
     * Special constructor used after a remote resource/contextualize call. Passes the JSON map
     * equivalent in structure to the protobuf message from a node.
     * 
     * @param data
     * @param context
     */
    public LocalData(Map<?, ?> data, IRuntimeScope context) {

        if (data.containsKey("error")) {
            String errorMessage = data.get("error") + ": " + data.get("message");
            // context.getMonitor().error(errorMessage);
            throw new KlabResourceAccessException(errorMessage);
        }

        if (data.containsKey("states")) {
            for (Object s : (Iterable<?>) data.get("states")) {

                Map<?, ?> state = (Map<?, ?>) s;
                IState target = null;
                if ("result".equals(state.get("name"))) {
                    target = context.getTargetArtifact() instanceof IState ? (IState) context.getTargetArtifact() : null;
                } else {
                    IArtifact artifact = context.getArtifact(state.get("name").toString());
                    if (artifact instanceof IState) {
                        target = (IState) artifact;
                    }
                }

                /*
                 * FIXME this is necessary if names come from mandatorily named deps needed to match
                 * with resources and they've been named differently in dependencies. The name is
                 * disconnected when derived models are used (e.g. change) but there is no chance of
                 * error. Should find a way to use the name nevertheless (the observable is the
                 * changing one so no memory of the original name is kept).
                 */
                if (target == null && context.getTargetArtifact() instanceof IState
                        && ((Collection<?>) data.get("states")).size() == 1) {
                    target = (IState) context.getTargetArtifact();
                }

                if (target == null) {
                    throw new KlabIllegalStateException("cannot establish state target for node resource");
                }

                if (this.state == null) {
                    this.state = target;
                }

                IUnit originalUnit = null;
                IUnit targetUnit = target.getObservable().getUnit();
                if (state.containsKey("metadata")) {
                    /*
                     * check out units (TODO currencies and ranges)
                     */
                    Map<?, ?> metadata = (Map<?, ?>) state.get("metadata");
                    if (metadata != null && metadata.containsKey("originalUnit")) {
                        originalUnit = Unit.create(metadata.get("originalUnit").toString());
                    }
                }
                Iterator<?> doubles = state.containsKey("doubledata") ? ((Iterable<?>) state.get("doubledata")).iterator() : null;

                IUnit scaledUnit = null;
                boolean scaled = false;
                boolean needsScaling = Units.INSTANCE.needsUnitScaling(target.getObservable());
                Object o = null;
                long offset = 0;
                while(doubles.hasNext()) {

                    o = doubles.next();
                    // yes, they do this, mixed in with actual doubles.
                    if ("NaN".equals(o)) {
                        o = null;
                    }

                    /*
                     * freaking complex, but should be OK now
                     */
                    IScale locator = context.getScale().at(offset++);
                    if (targetUnit != null && originalUnit != null && o instanceof Number) {
                        if (scaledUnit == null && needsScaling) {
                            scaledUnit = targetUnit;
                            if (!targetUnit.equals(originalUnit)) {
                                scaledUnit = scaledUnit.contextualize(
                                        new Observable(((Observable) target.getObservable())).withUnit(originalUnit), locator);
                                scaled = true;
                            }
                        }
                        o = scaled ? scaledUnit.convert((Number) o, locator) : targetUnit.convert((Number) o, originalUnit);
                    }

                    target.set(locator, o);
                }

            }
        } else if (data.containsKey("objects")) {

            for (Object object : ((List<?>) data.get("objects"))) {

                Map<?, ?> obj = (Map<?, ?>) object;

                IScale scale = Scale.create(Geometry.create(obj.get("geometry").toString()));

                IObjectArtifact output = null;

                if (context.getTargetSemantics().is(Type.RELATIONSHIP)) {

                    IDirectObservation source = null; // TODO
                    IDirectObservation target = null; // TODO
                    output = context.newRelationship(context.getTargetSemantics(), obj.get("name").toString(), scale, source,
                            target, extractMetadata(obj));
                } else {

                    output = context.newObservation(context.getTargetSemantics(), obj.get("name").toString(), scale,
                            extractMetadata(obj));
                }

                /*
                 * add any states or other artifacts. TODO not sure how this works with object
                 * structures, or even whether it should at all.
                 */
                if (output != null && obj.containsKey("states") && context.getModel() != null) {
                    for (Object state : (List<?>) obj.get("states")) {

                        Map<?, ?> sob = (Map<?, ?>) state;
                        String stateName = sob.get("name").toString();
                        IObservable observable = context.getModel().getAttributeObservables().get(stateName);
                        if (observable != null) {
                            context.addState((IDirectObservation) output, observable, getData(sob));
                        }

                    }
                }

                if (this.object == null) {
                    this.object = (IObservation) output;
                } else {
                    if (!(this.object instanceof IObservationGroup)) {
                        IObservation obs = (IObservation) this.object;
                        this.object = new ObservationGroup((Observable) context.getTargetSemantics(), (Scale) context.getScale(),
                                context, context.getTargetSemantics().getArtifactType());
                        ((ObservationGroup) this.object).chain(obs);
                    }
                    ((Artifact) this.object).chain(output);
                }

            }

        }

        if (data.containsKey("notifications")) {
            for (Object o : (List<?>) data.get("notifications")) {
                if (o instanceof Map && ((Map<?, ?>) o).containsKey("severity")) {
                    // System.out.println("DIO TORTA " + o);
                    switch(((Map<?, ?>) o).get("severity").toString()) {
                    case "ERROR":
                        context.getMonitor().error("remote service reported: " + ((Map<?, ?>) o).get("text"));
                        break;
                    case "WARNING":
                    case "WARN":
                        context.getMonitor().warn("remote service reported: " + ((Map<?, ?>) o).get("text"));
                        break;
                    case "INFO":
                        context.getMonitor().info("remote service reported: " + ((Map<?, ?>) o).get("text"));
                        break;
                    case "DEBUG":
                        context.getMonitor().debug("remote service reported: " + ((Map<?, ?>) o).get("text"));
                        break;
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Object getData(Map<?, ?> data) {

        Iterator<?> doubles = data.containsKey("doubledata") ? ((Iterable<?>) data.get("doubledata")).iterator() : null;
        // TODO tabledata + lookup table
        Iterator<?> floats = data.containsKey("intdata") ? ((Iterable<?>) data.get("intdata")).iterator() : null;
        Iterator<?> booleans = data.containsKey("booleandata") ? ((Iterable<?>) data.get("booleandata")).iterator() : null;

        List<Object> ret = new ArrayList<>();
        for (Iterator<?> it = Utils.chooseNotNull(doubles, floats, booleans); it.hasNext();) {
            Object o = it.next();
            ret.add("NaN".equals(o) ? null : o);
        }
        return ret.size() == 1 ? ret.get(0) : ret;
    }

    private IMetadata extractMetadata(Map<?, ?> obj) {
        Metadata ret = new Metadata();
        for (Object k : obj.keySet()) {
            if (!reservedFields.contains(k.toString())) {
                ret.put(k.toString(), obj.get(k));
            }
        }
        return ret;
    }

    @Override
    public List<INotification> getNotifications() {
        return notifications;
    }

    @Override
    public IArtifact getArtifact() {
        return state == null ? object : state;
    }

    @Override
    public boolean hasErrors() {
        return error;
    }

    @Override
    public IArtifact.Type getArtifactType() {
        return object == null ? (state == null ? IArtifact.Type.VOID : state.getType()) : object.getType();
    }

    @Override
    public int getObjectCount() {
        return object == null ? 0 : object.groupSize();
    }

    @Override
    public int getStateCount() {
        return state == null ? 0 : 1;
    }

    @Override
    public IScale getObjectScale(int i) {
        if (object != null) {
            ObjectArtifact member = (ObjectArtifact) ((Artifact) object).getGroupMember(i);
            if (member != null) {
                IGeometry geometry = member.getGeometry();
                if (geometry != null) {
                    return geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);
                }
            }
        }
        return null;
    }

    @Override
    public String getObjectName(int i) {
        if (object != null) {
            ObjectArtifact member = (ObjectArtifact) ((Artifact) object).getGroupMember(i);
            if (member != null) {
                return member.getName();
            }
        }
        return null;
    }

    @Override
    public IMetadata getObjectMetadata(int i) {
        if (object != null) {
            ObjectArtifact member = (ObjectArtifact) ((Artifact) object).getGroupMember(i);
            if (member != null) {
                return member.getMetadata();
            }
        }
        return null;
    }

    @Override
    public IConcept getSemantics() {
        return semantics;
    }

}
