package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kdl.kdl.Model;
import org.integratedmodelling.kdl.model.Kdl;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.services.IDataflowService;
import org.integratedmodelling.klab.components.runtime.AbstractRuntimeScope;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.DataflowCompiler;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
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
		Services.INSTANCE.registerService(this, IDataflowService.class);
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

	public Dataflow compile(String name, IResolutionScope scope, IActuator parentDataflow) throws KlabException {

		DataflowCompiler compiler = new DataflowCompiler(name, scope, (Actuator)parentDataflow);

		if (((ResolutionScope) scope).getObserver() != null) {
			compiler = compiler.withResolvable(((ResolutionScope) scope).getObserver());
		}

		for (Link link : ((ResolutionScope) scope).getLinks()) {
			compiler = compiler.withResolution(link);
		}
		
		for (ResolutionScope occurrent : ((ResolutionScope)scope).getOccurrentResolutions()) {
			for (Link link : occurrent.getLinks()) {
				compiler = compiler.withResolution(link);
			}
		}
		
		Dataflow ret = compiler.compile(scope.getMonitor());
		
		return ret;
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
	public List<Trigger> getActionTriggers(boolean initialization) {
		List<Trigger> ret = new ArrayList<>();
		// TODO!
		if (initialization) {
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
