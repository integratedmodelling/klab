package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kdl.kdl.Model;
import org.integratedmodelling.kdl.model.Kdl;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.services.IDataflowService;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.DataflowGraph;
import org.integratedmodelling.klab.dataflow.DataflowGraph;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.resolution.DataflowCompiler;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.ResolutionScope.Link;
import org.integratedmodelling.klab.utils.xtext.DataflowInjectorProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Dataflows implements IDataflowService {

	INSTANCE;

	@Inject
	ParseHelper<Model> dataflowParser;

	private Dataflows() {
		IInjectorProvider injectorProvider = new DataflowInjectorProvider();
		Injector injector = injectorProvider.getInjector();
		if (injector != null) {
			injector.injectMembers(this);
		}
	}

	@Override
	public IKdlDataflow declare(URL url) throws KlabException {
		try (InputStream stream = url.openStream()) {
			return declare(stream);
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public IKdlDataflow declare(File file) throws KlabException {
		try (InputStream stream = new FileInputStream(file)) {
			return declare(stream);
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public IKdlDataflow declare(InputStream file) throws KlabValidationException {
		IKdlDataflow ret = null;
		try {
			String definition = IOUtils.toString(file);
			Model model = dataflowParser.parse(definition);
			ret = Kdl.INSTANCE.declare(model);
		} catch (Exception e) {
			throw new KlabValidationException(e);
		}
		return ret;
	}

	@Override
	public Dataflow compile(IKdlDataflow specification) {
		return null;
	}

	/**
	 * Produce an ELK graph from a dataflow for subsequent display. For now we have
	 * no backwards path (which would be needed for a workflow editor).
	 * 
	 * @param dataflow
	 * @return
	 */
	public ElkNode extractGraph(Dataflow dataflow) {
		return new DataflowGraph(dataflow).getRootNode();
	}

	@Override
	public Dataflow compile(String name, IResolutionScope scope) throws KlabException {

		DataflowCompiler builder = new DataflowCompiler(name, scope);

		if (((ResolutionScope) scope).getObserver() != null) {
			builder = builder.withResolvable(((ResolutionScope) scope).getObserver());
		}

		for (Link link : ((ResolutionScope) scope).getLinks()) {
			builder = builder.withResolution(link.getTarget().getResolvable(), link.getSource().getResolvable(),
					link.getTarget().getCoverage(), link.getComputation());
		}

		return builder.compile(scope.getMonitor());
	}

	/**
	 * Given a transition, return all the action triggers that pertain to it. If
	 * more than one trigger is returned, any actions corresponding to the first
	 * will be applied before, and the second after, the transition event: e.g.
	 * definition vs. resolution or (last) transition vs. termination.
	 *
	 * @param transition
	 *            a {@link org.integratedmodelling.klab.api.data.ILocator} object.
	 * @return all pertaining triggers. Possibly empty, never null.
	 */
	public List<Trigger> getActionTriggersFor(ILocator transition) {
		List<Trigger> ret = new ArrayList<>();
		// TODO!
		if (transition.equals(ITime.INITIALIZATION)) {
			ret.add(Trigger.DEFINITION);
			ret.add(Trigger.RESOLUTION);
			ret.add(Trigger.INSTANTIATION);
		} else {
			ret.add(Trigger.TRANSITION);
			// FIXME add back
			// if (transition.isLast()) {
			// ret.add(Trigger.TERMINATION);
			// }
		}
		return ret;
	}

}
