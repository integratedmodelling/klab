//package org.integratedmodelling.klab.components.runtime.contextualizers.mergers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.integratedmodelling.kim.api.IParameters;
//import org.integratedmodelling.kim.api.IServiceCall;
//import org.integratedmodelling.kim.model.KimServiceCall;
//import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
//import org.integratedmodelling.klab.api.data.general.IExpression;
//import org.integratedmodelling.klab.api.knowledge.IObservable;
//import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
//import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
//import org.integratedmodelling.klab.api.provenance.IArtifact;
//import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
//import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
//import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
//import org.integratedmodelling.klab.exceptions.KlabException;
//
//public class MergedUrnInstantiator implements IExpression, IInstantiator {
//
//	public final static String FUNCTION_ID = "klab.runtime.timechooser";
//
//	private List<String> urns;
//	private Type type;
//
//	// don't remove - only used as expression
//	public MergedUrnInstantiator() {
//	}
//
//	public MergedUrnInstantiator(List<String> urns, IContextualizationScope context) {
//		this.urns = urns;
//		this.type = context.getTargetSemantics().getArtifactType();
//	}
//
//	public static IServiceCall getServiceCall(List<String> mergedUrns) {
//		return KimServiceCall.create(FUNCTION_ID, "urns", mergedUrns);
//	}
//
//	@Override
//	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {
//		
//		List<IObjectArtifact> ret = new ArrayList<>();
//		
//		ComputableSchedule schedule = new ComputableSchedule(context.getTargetSemantics(), (IRuntimeScope) context);
//		schedule.add(urns);
//		
//		for (IContextualizer contextualizer : schedule.getComputation(context.getScale().getTime(), context)) {
//			// run it
//		}
//		
//		return ret;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
//		return new MergedUrnInstantiator(parameters.get("urns", List.class), context);
//	}
//
//	@Override
//	public IArtifact.Type getType() {
//		return type;
//	}
//
//}
