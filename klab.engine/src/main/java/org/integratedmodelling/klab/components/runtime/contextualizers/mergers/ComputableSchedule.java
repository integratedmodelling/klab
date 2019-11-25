package org.integratedmodelling.klab.components.runtime.contextualizers.mergers;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

public class ComputableSchedule {

	IObservable observable;
	IRuntimeScope scope;

	// hostia
	List<Pair<Range, List<Pair<IServiceCall, IContextualizer>>>> schedule = new ArrayList<>();

	public ComputableSchedule(IObservable observable, IRuntimeScope scope) {
		this.observable = observable;
		this.scope = scope;
	}

	public void add(List<String> urns) {

		ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
		IRuntimeProvider runtime = Klab.INSTANCE.getRuntimeProvider();

		for (String urn : urns) {

			Range tindex = new Range();
			List<Pair<IServiceCall, IContextualizer>> computation = new ArrayList<>();

			if (urn.contains(":")) {

				IResource resource = Resources.INSTANCE.resolveResource(urn);
				IGeometry.Dimension time = resource.getGeometry().getDimension(Dimension.Type.TIME);

				if (time != null) {
			
					// TODO set range
					
					computation.add(new Pair<IServiceCall, IContextualizer>(runtime.getServiceCall(
							new ComputableResource(urn,
									observable.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION),
							observable, session), null));
				}
				
			} else {

				/*
				 * must be a model
				 */
				IKimObject model = Resources.INSTANCE.getModelObject(urn);
				if (model instanceof IModel) {

					IGeometry.Dimension time = ((IModel) model).getGeometry().getDimension(Dimension.Type.TIME);

					// TODO set range
					
					if (time != null) {

						for (IContextualizable c : ((IModel) model).getComputation()) {
							computation.add(new Pair<IServiceCall, IContextualizer>(
									runtime.getServiceCall(c, observable, session), null));
						}
					}
				}
			}

			schedule.add(new Pair<>(tindex, computation));
		}
	}

	public List<IContextualizer> getComputation(ITime time, IContextualizationScope scope) {

		List<IContextualizer> ret = new ArrayList<>();

		/*
		 * choose the closest schedule that's not after time
		 */
		Range chosenRange = null;
		List<Pair<IServiceCall, IContextualizer>> chosenStrategy = null;
		
		for (Pair<Range, List<Pair<IServiceCall, IContextualizer>>> strategy : schedule) {
			// TODO strategy is: OK if range covers/intersects; if another is there already, trumps if it starts later.
		}

		if (chosenStrategy == null) {
			return ret;
		}

		/*
		 * scan it and create the contextualizers if necessary, then add them to the
		 * return list.
		 */
		for (Pair<IServiceCall, IContextualizer> cct : chosenStrategy) {
			if (cct.getSecond() == null) {
				cct.setSecond((IContextualizer) Extensions.INSTANCE.callFunction(cct.getFirst(), scope));
			}
			ret.add(cct.getSecond());
		}

		return ret;
	}
}
