package org.integratedmodelling.klab.engine.runtime;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.rest.DataflowState.Status;
import org.integratedmodelling.klab.rest.ObservationAssetStatistics;
import org.integratedmodelling.klab.rest.ObservationAssetStatistics.Type;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.integratedmodelling.klab.rest.ScaleStatistics;
import org.integratedmodelling.klab.utils.StringUtils;

public class ActivityBuilder {

	enum TargetIdentity {
		Agent, Dataflow, Actuator, Contextualizer, Resource, Download
	}

	/**
	 * Check if this has been accounted for (sent to a stat server). If accounted,
	 * some of our children may still be unaccounted for.
	 */
	private boolean accounted = false;

	private String targetId;
	private TargetIdentity type;
	private String observable;
	private long startTime = System.currentTimeMillis();
	private long endTime;
	private double totalTime;
	private long totalContextTime;
	private List<ActivityBuilder> children = new ArrayList<>();
	private Map<ObservedConcept, ActivityBuilder> actuators = new HashMap<>();
	private Map<String, ActivityBuilder> contextualizers = new HashMap<>();
	private Map<String, ActivityBuilder> resources = new HashMap<>();
	private Status status = Status.WAITING;
	private String contextId;
	private String contextName;
	private int scheduledSteps;
	private String contextCreated;
	private int passes;
	private long byteSize;
	private String engineName;
	private boolean instantiation;
	private Collection<String> scenarios;
	private ScaleStatistics scaleStatistics;

	public static ActivityBuilder root(ResolutionScope resolutionScope) {
		Session session = resolutionScope.getMonitor().getIdentity().getParentIdentity(Session.class);
		ActivityBuilder ret = new ActivityBuilder(session.getId(), TargetIdentity.Agent,
				session.getParentIdentity(IEngineIdentity.class).getName());
		ret.scenarios = resolutionScope.getScenarios();
		ret.contextName = resolutionScope.getObserver().getName();
		ret.collectGeometryStatistics(resolutionScope.getScale());
		return ret;
	}

	private ActivityBuilder(String targetId, TargetIdentity targetIdentity, String engineName) {
		this.targetId = targetId;
		this.type = targetIdentity;
		this.engineName = engineName;
	}

	public String getTargetId() {
		return targetId;
	}

	public TargetIdentity getType() {
		return type;
	}

	public String getObservable() {
		return observable;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public long getTotalContextTime() {
		return totalContextTime;
	}

	public List<ActivityBuilder> getChildren() {
		return children;
	}

	public String getContextId() {
		return contextId;
	}

	public String getContextName() {
		return contextName;
	}

	public int getScheduledSteps() {
		return scheduledSteps;
	}

	/**
	 * Return a child builder for the passed target, which can be an observer agent
	 * (or session representing the "god" agent), a dataflow, an actuator, a
	 * contextualizer or a resource. Each scope should expose a builder made for the
	 * current target.
	 * 
	 * @param target
	 * @return
	 */
	public ActivityBuilder forTarget(Object... targets) {

		ActivityBuilder ret = null;
		IGeometry geometry = null;
		IObservable observable = null;
		IDirectObservation context = null;
		String obsname = null;

		Object target = targets[0];
		for (int i = 1; i < targets.length; i++) {
			if (targets[i] instanceof IGeometry) {
				geometry = (IGeometry) targets[i];
			} else if (targets[i] instanceof IObservable) {
				observable = (IObservable) targets[i];
			} else if (targets[i] instanceof IDirectObservation) {
				context = (IDirectObservation) targets[i];
			} else if (targets[i] instanceof String) {
				obsname = (String)targets[i];
			}
		}

		if (target instanceof IActorIdentity) {
			ret = new ActivityBuilder(((IActorIdentity<?>) target).getId(), TargetIdentity.Agent, engineName);
		} else if (target instanceof IDataflow) {

			String name = observable == null ? ((IDataflow<?>) target).getId() : observable.getDefinition();
			ret = new ActivityBuilder(name, TargetIdentity.Dataflow, engineName);

		} else if (target instanceof IActuator) {

			ObservedConcept obs = new ObservedConcept(((Actuator) target).getObservable(),
					((Actuator) target).getMode());
			if (this.actuators.containsKey(obs)) {
				ret = this.actuators.get(obs);
				if (ret.endTime > 0) {
					ret.totalTime += (ret.endTime - ret.startTime);
					ret.passes++;
				}
				ret.startTime = System.currentTimeMillis();
				ret.endTime = 0;
				return ret;
			}
			ret = new ActivityBuilder(((Actuator) target).getModel().getName(), TargetIdentity.Actuator, engineName);
			ret.observable = ((Actuator) target).getObservable().getDefinition();
			ret.instantiation = ((Actuator) target).getMode() == Mode.INSTANTIATION;
			if (((Actuator)target).getCoverage() != null) {
				// specialized scale
				geometry = ((Actuator)target).getCoverage();
			}
			this.actuators.put(obs, ret);

		} else if (target instanceof AbstractContextualizer) {

			String ctxId = ((AbstractContextualizer) target).getPrototype().getName();
			if (this.contextualizers.containsKey(ctxId)) {
				ret = this.contextualizers.get(ctxId);
				if (ret.endTime > 0) {
					ret.totalTime += (ret.endTime - ret.startTime);
					ret.passes++;
				}
				ret.startTime = System.currentTimeMillis();
				ret.endTime = 0;
				return ret;
			}
			ret = new ActivityBuilder(ctxId, TargetIdentity.Contextualizer, engineName);
			this.contextualizers.put(ctxId, ret);

		} else if (target instanceof IResource) {

			String ctxId = ((IResource) target).getUrn();
			if (this.resources.containsKey(ctxId)) {
				ret = this.resources.get(ctxId);
				if (ret.endTime > 0) {
					ret.totalTime += (ret.endTime - ret.startTime);
					ret.passes++;
				}
				ret.startTime = System.currentTimeMillis();
				ret.endTime = 0;
				return ret;
			}
			ret = new ActivityBuilder(((IResource) target).getUrn(), TargetIdentity.Resource, engineName);
			this.resources.put(ctxId, ret);
		} else if (target instanceof File) {
			ret =  new ActivityBuilder(obsname, TargetIdentity.Download, engineName);
			ret.byteSize = ((File)target).length();
			return ret;
		}

		if (ret == null) {
			throw new KlabIllegalArgumentException("internal: cannot create activity for target " + target);
		}

		if (observable != null) {
			ret.observable = observable.getDefinition();
		}
		if (geometry != null) {
			collectGeometryStatistics(geometry);
		}
		if (context != null) {
			ret.contextId = context.getId();
			ret.contextName = context.getName();
		} else {
			ret.contextId = this.contextId;
		}

		this.children.add(ret);

		return ret;
	}

	private void collectGeometryStatistics(IGeometry geometry) {

		if (geometry instanceof IScale) {

			this.scaleStatistics = new ScaleStatistics();
			
			// space statistics + bounding box
			if (((IScale)geometry).getSpace() != null) {
				
				ISpace space = ((IScale)geometry).getSpace();
				
				// complexity of spatial shape = number of coordinates
				this.scaleStatistics.setSpaceComplexity(space.getShape().getComplexity());
				this.scaleStatistics.setSpaceSize(space.size());
				this.scaleStatistics.setBboxWkt(((Shape)space.getShape()).getStandardizedEnvelopeWKT());

			}

			// time statistics
			if (((IScale)geometry).getTime() != null) {
				ITime time = ((IScale)geometry).getTime();
				this.scaleStatistics.setTimeResolution(time.getResolution().toString());
				this.scaleStatistics.setTimeSize(time.size());
				this.scaleStatistics.setTimeStart(time.getStart().getMilliseconds());
				this.scaleStatistics.setTimeEnd(time.getEnd().getMilliseconds());
			}

		}
	}

	/**
	 * If this isn't called, start should be set to the time the builder is created.
	 * 
	 * @return
	 */
	ActivityBuilder start() {
		this.startTime = System.currentTimeMillis();
		this.status = Status.STARTED;
		return this;
	}

	/**
	 * Successful end. After this no more activity should be possible.
	 * 
	 * @return
	 */
	public ActivityBuilder success() {
		this.endTime = System.currentTimeMillis();
		this.status = Status.FINISHED;
		return this;
	}

	public Status getStatus() {
		return status;
	}

	/**
	 * Intentional interruption. After this no more activity should be possible.
	 * 
	 * @return
	 */
	public ActivityBuilder interrupt() {
		this.endTime = System.currentTimeMillis();
		this.status = Status.INTERRUPTED;
		return this;
	}

	/**
	 * Unintentional interruption for controlled causes (no exception). After this
	 * no more activity should be possible.
	 * 
	 * @return
	 */
	public ActivityBuilder error() {
		this.endTime = System.currentTimeMillis();
		this.status = Status.ABORTED;
		return this;
	}

	/**
	 * Intentional interruption. After this no more activity should be possible.
	 * 
	 * @return
	 */
	public ActivityBuilder exception(Throwable e) {
		this.endTime = System.currentTimeMillis();
		return this;
	}

	public ActivityBuilder withGeometry(IGeometry geometry) {
		analyzeGeometry();
		return this;
	}

	private void analyzeGeometry() {
		// TODO Auto-generated method stub

	}

	double getTotalTimeSeconds() {
		return endTime == 0 ? Double.NaN : ((double) (endTime - startTime) + totalTime) / 1000.0;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer(512);
		dump(ret, 0);
		return ret.toString();
	}

	private void dump(StringBuffer buffer, int offset) {
		buffer.append(StringUtils.spaces(offset) + type + ": " + targetId);
		if (endTime > 0) {
			buffer.append(" (" + NumberFormat.getInstance().format(getTotalTimeSeconds()) + " s)");
		}
		buffer.append(" " + status + "\n");
		for (ActivityBuilder child : children) {
			child.dump(buffer, offset + 2);
		}
	}

	public boolean isAccounted() {
		return accounted;
	}

	public ActivityBuilder schedulerStep() {
		scheduledSteps++;
		return this;
	}

	/**
	 * Further specify the target for logging; called on root dataflow stats only.
	 * 
	 * @param observer
	 */
	public void defineTarget(Object... target) {
		for (Object o : target) {
			if (o instanceof IAcknowledgement) {
				this.targetId = ((IAcknowledgement) o).getName() + " ("
						+ ((IAcknowledgement) o).getObservable().getDefinition() + ")";
			} else if (o instanceof String) {
				this.targetId = (String) o;
			} else if (o instanceof IDirectObservation) {
				this.contextId = ((IDirectObservation) o).getId();
			}
		}
	}

	public void notifyContextCreated(ISubject ret) {
		this.contextCreated = ret.getId();
	}

	public ObservationResultStatistics encode() {

		ObservationResultStatistics ret = new ObservationResultStatistics();
		boolean first = true;
		boolean success = true;
		String contextId = this.contextId;
		
		for (ActivityBuilder child : unwrapChildren()) {

			if (first) {
				ret.setEngineName(engineName);
				ret.setEngineVersion(Version.CURRENT);
				ret.setStartTime(startTime);
				ret.setEndTime(endTime);
				ret.setDurationSeconds(getTotalTimeSeconds());
				ret.setObservable(getObservable());
				ret.setObservationName(contextName);
				ret.setScaleStatistics(getScaleStatistics(child));
				if (contextId == null) {
					ret.setRoot(true);
					contextId = child.contextCreated;
				}
			}

			ret.setContextId(contextId);
			ret.setScaleStatistics(scaleStatistics);
						
			switch (child.getType()) {
			case Dataflow:
			case Agent:
				if (child.observable != null && ret.getObservable() == null) {
					ret.setObservable(child.observable);
				}
				break;
			case Actuator:
				ret.getAssets().add(encodeResolvedObservable(child));
			case Contextualizer:
			case Resource:
			case Download:
				ObservationAssetStatistics asset = encodeAsset(child);
				success = success && asset.getStatus() == Status.FINISHED;
				ret.getAssets().add(asset);
				break;
			}

		}
		
		if (!success && ret.getStatus() == Status.FINISHED) {
			ret.setStatus(Status.ABORTED);
		}

		return ret;

	}

	private ObservationAssetStatistics encodeResolvedObservable(ActivityBuilder child) {
		ObservationAssetStatistics ret = encodeAsset(child);
		ret.setType(Type.ResolvedObservable);
		ret.setName(child.getObservable());
		ret.setInstantiation(child.instantiation);
		return null;
	}

	private ScaleStatistics getScaleStatistics(ActivityBuilder child) {
		return scaleStatistics;
	}

	private ObservationAssetStatistics encodeAsset(ActivityBuilder child) {

		ObservationAssetStatistics ret = new ObservationAssetStatistics();
		
		ret.setComputationTime(child.getTotalTimeSeconds());
		ret.setName(child.getTargetId());
		ret.setStatus(child.getStatus());
		ret.setComputations(child.getPasses());
		ret.setSchedules(child.getScheduledSteps());
		
		switch (child.getType()) {
		case Actuator:
			ret.setType(Type.Model);
			break;
		case Contextualizer:
			ret.setType(Type.Operation);
			break;
		case Resource:
			ret.setType(Type.Resource);
			break;
		case Download:
			ret.setType(Type.Export);
			ret.setSize(byteSize);
			break;
		default:
			break;
		}
		return ret ;
	}

	List<ActivityBuilder> unwrapChildren() {
		List<ActivityBuilder> ret = new ArrayList<>();
		_unwrapChildren(this, ret);
		return ret;
	}

	private void _unwrapChildren(ActivityBuilder activityBuilder, List<ActivityBuilder> ret) {
		ret.add(activityBuilder);
		for (ActivityBuilder child : activityBuilder.children) {
			_unwrapChildren(child, ret);
		}
	}

	public String getContextCreated() {
		return contextCreated;
	}

	public int getPasses() {
		return passes;
	}

	public boolean isInstantiation() {
		return instantiation;
	}

	public Collection<String> getScenarios() {
		return scenarios;
	}

}
