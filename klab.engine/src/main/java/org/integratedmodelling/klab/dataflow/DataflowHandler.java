package org.integratedmodelling.klab.dataflow;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * The root contextualization scope ultimately handles a dataflow that can be
 * incrementally defined. This base class contains all dataflow bookkeeping
 * facilities. The runtime contextualization scope derives from this.
 * 
 * Ultimately this will also be in charge of loading dataflow resources.
 * 
 * The derivation from Parameters is not necessary here, but because this serves
 * as base class for AbstractContextualizationScope and Java has no multiple
 * inheritance, we add it here for lack of better options.
 * 
 * @author Ferd
 *
 */
public class DataflowHandler extends Parameters<String> {

	private String id;
	private Dataflow rootDataflow;
	private int dataflowCodeLength = 0;

	/**
	 * These are created and run during resolution, before the root dataflow exists.
	 * Normally used to resolve abstract predicates. They are "orphans" when
	 * created, and the root dataflow will adopt them as soon as it is created.
	 */
	private List<Dataflow> preContextualizationDataflows = new ArrayList<>();
	private Map<ObservedConcept, List<Pair<ICoverage, Dataflow>>> dataflowCache = new HashMap<>();
	List<Dataflow> rootNodes = new ArrayList<>();
	private String rootContextId;

	public DataflowHandler() {
		this.id = NameGenerator.shortUUID();
		this.preContextualizationDataflows = new ArrayList<>();
		this.dataflowCache = new HashMap<>();
		this.rootNodes = new ArrayList<>();
	}

	protected DataflowHandler(DataflowHandler other) {
		copyDataflowInfo(other);
	}

	protected void copyDataflowInfo(DataflowHandler other) {
		this.id = other.id;
		this.preContextualizationDataflows = other.preContextualizationDataflows;
		this.dataflowCache = other.dataflowCache;
		this.rootNodes = other.rootNodes;
		this.dataflowCodeLength = other.dataflowCodeLength;
	}

	public String getKdl() {
		if (rootDataflow == null) {
			return "";
		}
		return rootDataflow.getKdlCode();
	}

	public static String getElkGraph(Dataflow dataflow, IRuntimeScope scope) {
		DataflowHandler strategy = new DataflowHandler();
		strategy.rootDataflow = dataflow;
		return strategy.getElkGraph(scope);
	}

	public void setRootDataflow(Dataflow dataflow, String contextId) {
		this.rootDataflow = dataflow;
		this.rootContextId = contextId;
		dataflow.actuators.addAll(0, preContextualizationDataflows);
	}

	public void addPrecontextualizationDataflow(Dataflow dataflow, Dataflow root) {
		if (root == null) {
			preContextualizationDataflows.add(dataflow);
		} else {
			root.actuators.add(dataflow);
		}
	}

	public void notifyDataflowChanges(IRuntimeScope scope) {

		String code = getKdl();
		if (code.length() > dataflowCodeLength) {
			DataflowReference dataflow = new DataflowReference(rootContextId, code, getElkGraph(scope));
			scope.getSession().getMonitor().send(Message.create(scope.getSession().getId(),
					IMessage.MessageClass.TaskLifecycle, IMessage.Type.DataflowCompiled, dataflow));
			this.dataflowCodeLength = code.length();
			if (Configuration.INSTANCE.isEchoEnabled()) {
				System.out.println(code);
			}
		}
	}

	public void exportDataflow(String baseName, File directory) {

	}

	/**
	 * Return or create the dataflow needed to resolve the passed observable in the
	 * passed mode, scale, scope and context. Cache the result for the observable
	 * and coverage so that no resolution will be required for compatible,
	 * previously computed ones. TODO the caching DOES prevent better specific
	 * dataflows to be resolved after more generic ones, so it should be conditional
	 * to an option.
	 * 
	 * @param observable
	 * @param mode
	 * @param scale
	 * @param context
	 * @return
	 */
	public Dataflow getDataflow(IObservable observable, IResolutionScope.Mode mode, IScale scale,
			IDirectObservation context, Function<IScale, Dataflow> ifMissing) {

		Dataflow ret = null;
		ObservedConcept key = new ObservedConcept((Observable) observable, mode,
				context == null ? null : context.getObservable());
		List<Pair<ICoverage, Dataflow>> pairs = dataflowCache.get(key);

		if (pairs != null) {
			for (Pair<ICoverage, Dataflow> pair : pairs) {
				if (pair.getFirst() == null || pair.getFirst().contains(scale)) {
					ret = pair.getSecond();
					break;
				}
			}
		}

		if (ret == null) {
			ret = ifMissing.apply(scale);
			if (pairs == null) {
				pairs = new ArrayList<>();
				dataflowCache.put(key, pairs);
			}
			if (ret != null) {
				pairs.add(new Pair<>(ret.getCoverage(), ret));
			}
		}

		return ret;
	}

	/**
	 * Return the synthesized actuator structure for a dataflow that was built
	 * incrementally by successive resolutions. Compared to the original dataflow,
	 * this one won't contain sub-dataflows and will report complete coverages. It
	 * is the graph that should be used for serialization and visualization.
	 * 
	 * @return the root node and the full, normalized actuator graph. Because this
	 *         is executed on the root dataflow, the root node is guaranteed unique.
	 */
	public Pair<IActuator, Graph<IActuator, DefaultEdge>> getDataflowStructure() {
		if (rootDataflow == null) {
			return null;
		}
		Pair<List<IActuator>, Graph<IActuator, DefaultEdge>> ret = rootDataflow.getDataflowStructure();
		if (ret.getFirst().size() != 1) {
			throw new KlabInternalErrorException("root dataflow has more than one root node");
		}
		return new Pair<>(ret.getFirst().get(0), ret.getSecond());
	}

	/**
	 * Create the flowchart structure illustrating the dataflow.
	 * 
	 * @param scope
	 * @return
	 */
	public Flowchart getFlowchart(IRuntimeScope scope) {
		Pair<IActuator, Graph<IActuator, DefaultEdge>> structure = getDataflowStructure();
		if (structure == null) {
			return null;
		}
		return Flowchart.create(structure.getFirst(), structure.getSecond(), scope);
	}

	/**
	 * Layout the flowchart into an ELK diagram and return the JSON definition.
	 * 
	 * @param scope
	 * @return
	 */
	public String getElkGraph(IRuntimeScope scope) {
		Flowchart flowchart = getFlowchart(scope);
		if (flowchart != null) {
			return flowchart.getJsonLayout();
		}
		return null;
	}

}
