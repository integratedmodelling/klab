package org.integratedmodelling.klab.data.resources;

import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.data.encoding.LocalDataBuilder;
import org.integratedmodelling.klab.engine.runtime.SimpleRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

public class ResourceCalculator<T> implements IResourceCalculator<T> {

	private IResource resource;
	private Class<?> cls;
	IRuntimeScope scope = null;
	IResourceEncoder encoder = null;
	private Map<String, String> resparams;
	private Scale scalar = Scale.create();

	private ResourceCalculator(Class<? extends T> cls) {
		this.cls = cls;
	}

	public static <T> ResourceCalculator<T> create(IResource resource, Class<T> cls) {

		ResourceCalculator<T> ret = new ResourceCalculator<>(cls);
		Pair<String, Map<String, String>> split = Urns.INSTANCE.resolveParameters(resource.getUrn());
		ret.resparams = split.getSecond();
		ret.resource = resource;
		return ret;
	}

	@Override
	public T eval(IParameters<String> arguments, IMonitor monitor) {

		if (Urns.INSTANCE.isLocal(resource.getUrn())) {

			if (scope == null) {
				scope = new SimpleRuntimeScope(
						Observable
								.promote(OWL.INSTANCE.getNonsemanticPeer(resource.getLocalName(), resource.getType())),
						scalar, monitor);
			}
			if (encoder == null) {
				encoder = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType()).getEncoder();
			}

			IKlabData.Builder builder = new LocalDataBuilder(scope);
			try {
				encoder.getEncodedData(resource, resparams, scalar, builder, scope);
				Object ret = builder.build();
				
			} catch (Throwable e) {
				// just return null later
			}
		} else {

			/*
			 * TODO send REST request to any node that owns this resource - start with the
			 * named owner if we have it; if unsuccessful, try using resolution service on
			 * all nodes. Then use the get endpoint.
			 */
		}
		return null;
	}

	@Override
	public T eval(IContextualizationScope scope, ILocator locator, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource getResource() {
		return resource;
	}

}
