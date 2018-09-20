package org.integratedmodelling.klab.dataflow;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * The semantically aware implementation of {@link IDataflow}, built by the
 * k.LAB runtime as a result of a semantic resolution. Its
 * {@link #run(IScale, IMonitor)} produces {@link IObservation observations}
 * unless the dataflow is {@link #isEmpty() empty}.
 * <p>
 * A matching implementation may be provided to run non-semantic workflows in
 * semantically unaware computation engines, or a translator could be used to
 * provide commodity semantics to use this one so that k.LAB servers can serve
 * indirectAdapters through URNs.
 * <p>
 * 
 * @author Ferd
 *
 */
public class Dataflow extends Actuator implements IDataflow<IArtifact> {
	
	private String description;
	private DirectObservation context;
	private ResolutionScope scope;
	private boolean primary = true;
	
	/*
	 * TODO this should be removed and an actual layout should be created
	 */
	private static String demoLayout = null;
	
	@Override
	public IArtifact run(IScale scale, IMonitor monitor) throws KlabException {

		if (actuators.size() == 0) {
			if (scope.getResolvedArtifact() != null) {
				return scope.getResolvedArtifact().getArtifact();
			}
			return Observation.empty();
		}

		/*
		 * Children at the dataflow level run in parallel, so have the runtime start
		 * futures for each child and chain the results when they come.
		 */
		IArtifact ret = null;
		for (IActuator actuator : actuators) {
			try {

				IArtifact data = Klab.INSTANCE.getRuntimeProvider().compute(actuator, scale, scope, context, monitor)
						.get();

				if (ret == null) {
					ret = data;
				} else {
					((ObservedArtifact) ret).chain(data);
				}
			} catch (InterruptedException e) {
				return null;
			} catch (ExecutionException e) {
				throw new KlabContextualizationException(e);
			}
		}

		return ret;
	}

	@Override
	protected String encode(int offset) {

		String ret = "";

		if (offset == 0) {
			ret += "@klab " + Version.CURRENT + "\n";
			ret += "@dataflow " + getName() + "\n";
			ret += "@author 'k.LAB resolver " + creationTime + "'" + "\n";
			if (getContext() != null) {
				ret += "@context " + getContext().getUrn() + "\n";
			}
			if (coverage != null && coverage.getExtentCount() > 0) {
				List<IServiceCall> scaleSpecs = ((Scale) coverage).getKimSpecification();
				if (!scaleSpecs.isEmpty()) {
					ret += "@coverage";
					for (int i = 0; i < scaleSpecs.size(); i++) {
						ret += " " + scaleSpecs.get(i).getSourceCode()
								+ ((i < scaleSpecs.size() - 1) ? (",\n" + "   ") : "");
					}
					ret += "\n";
				}
			}
			ret += "\n";
		}

		for (IActuator actuator : actuators) {
			ret += ((Actuator) actuator).encode(offset) + "\n";
		}

		return ret;
	}

	/**
	 * Return the source code of the dataflow.
	 * 
	 * @return the source code as a string.
	 */
	@Override
	public String getKdlCode() {
		return encode(0);
	}

	/**
	 * Called by tasks
	 * 
	 * @param name
	 * @param description
	 */
	public void setName(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public ICoverage getCoverage() {
		return scope.getCoverage();
	}

	public DirectObservation getContext() {
		return context;
	}

	public void setContext(DirectObservation context) {
		this.context = context;
	}

	public void setResolutionScope(ResolutionScope scope) {
		this.scope = scope;
	}

	public static IDataflow<IArtifact> empty() {
		return new Dataflow();
	}

	@Override
	public boolean isEmpty() {
		return actuators.size() == 0;
	}

	/**
	 * True if the dataflow is handling an API observation request. False if the request
	 * is to resolve an object instantiated by another dataflow.
	 * 
	 * @return
	 */
	public boolean isPrimary() {
		return primary;
	}
	
	public Dataflow setPrimary(boolean b) {
		this.primary = b;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public String getElkJsonLayout() {
		if (demoLayout == null) {
			URL url = Resources.getResource("stubs/dataflow_sample.json");
			try {
				demoLayout = Resources.toString(url, Charsets.UTF_8);
			} catch (IOException e) {
				// hostia!
			}
		}
		return demoLayout;
	}
	
	

}
