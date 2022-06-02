//package org.integratedmodelling.klab.components.runtime.contextualizers;
//
//import java.util.Collections;
//import java.util.List;
//
//import org.integratedmodelling.kim.api.IContextualizable;
//import org.integratedmodelling.kim.api.IKimConcept;
//import org.integratedmodelling.kim.api.IParameters;
//import org.integratedmodelling.kim.api.IServiceCall;
//import org.integratedmodelling.kim.model.KimServiceCall;
//import org.integratedmodelling.klab.Observables;
//import org.integratedmodelling.klab.Resources;
//import org.integratedmodelling.klab.api.data.IResource;
//import org.integratedmodelling.klab.api.data.adapters.IKlabData;
//import org.integratedmodelling.klab.api.data.general.IExpression;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.model.contextualization.IResolver;
//import org.integratedmodelling.klab.api.observations.IDirectObservation;
//import org.integratedmodelling.klab.api.provenance.IArtifact;
//import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
//import org.integratedmodelling.klab.engine.resources.MergedResource;
//import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabValidationException;
//
//@Deprecated
//public class ChangingResourceResolver extends AbstractContextualizer implements IResolver<IArtifact>, IExpression {
//
//    static final public String FUNCTION_ID = "klab.runtime.resourcechange";
//
//    private MergedResource mergedResource;
//    private IResource resource;
//
//    // don't remove - only used as expression
//    public ChangingResourceResolver() {
//    }
//
//    public ChangingResourceResolver(IConcept changeProcess, MergedResource resource) {
//        this.mergedResource = resource;
//    }
//
//    public static IServiceCall getServiceCall(IConcept change, MergedResource resource) throws KlabValidationException {
//        return KimServiceCall.create(FUNCTION_ID, "change", change.getDefinition(), "resources", resource.getUrns());
//    }
//
//    @Override
//    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
//        IConcept change = Observables.INSTANCE.declare(parameters.get("change", String.class)).getType();
//        @SuppressWarnings("unchecked")
//        List<String> urns = parameters.get("resources", List.class);
//        return new ChangingResourceResolver(change, new MergedResource(urns, (IRuntimeScope) context));
//    }
//
//    @Override
//    public IArtifact.Type getType() {
//        return this.mergedResource.getType();
//    }
//
//    @Override
//    public void notifyContextualizedResource(IContextualizable resource, IArtifact target, IContextualizationScope scope) {
//        this.resource = resource.getResource();
//    }
//
//    /*
//     * TODO this is identical to UrnResolver, it should be derived from it - only difference is that
//     * it's initialized with a list of URNs, which must stay in order to remove the MergedResource
//     * logics from the dataflow. In fact the initialization with the list should become a standard
//     * feature of UrnResolver, which would create a merged resource if initialized that way.
//     */
//    @Override
//    public IArtifact resolve(IArtifact ret, IContextualizationScope scope) throws KlabException {
//
//        if (this.resource.getAvailability() != null) {
//            switch(this.resource.getAvailability().getAvailability()) {
//            case DELAYED:
//                scope.getMonitor().addWait(this.resource.getAvailability().getRetryTimeSeconds());
//                return ret;
//            case NONE:
//                scope.getMonitor().error("resource " + resource.getUrn() + " has no data available in this context");
//                return ret;
//            case PARTIAL:
//                scope.getMonitor().warn("resource " + resource.getUrn() + " reports partial availability in this context");
//                break;
//            case COMPLETE:
//                break;
//            }
//        }
//
//        IKlabData data = Resources.INSTANCE.getResourceData(this.resource, mergedResource.getParameters(this.resource.getUrn()),
//                scope.getScale(), scope, ret);
//        if (data != null) {
//            IConcept concept = data.getSemantics();
//            if (concept != null) {
//                IConcept toResolve = scope.getTargetSemantics().getType();
//                List<IConcept> traits = concept.is(IKimConcept.Type.INTERSECTION)
//                        ? Collections.singletonList(concept)
//                        : concept.getOperands();
//                if (ret instanceof IDirectObservation) {
//                    for (IConcept predicate : traits) {
//                        ((IRuntimeScope) scope).newPredicate((IDirectObservation) ret, predicate);
//                    }
//                } else {
//                    ((IRuntimeScope) scope).setConcreteIdentities(toResolve, traits);
//                }
//            }
//        }
//        return ret;
//
//        // List<Pair<IResource, Map<String, String>>> resources = ((MergedResource)
//        // this.resource).contextualize(scope.getScale(),
//        // ret, scope);
//        //
//        // if (resources.isEmpty()) {
//        // // this can happen when the resource can't add anything to the artifact.
//        // return ret;
//        // }
//        //
//        // for (Pair<IResource, Map<String, String>> pr : resources) {
//        // ((Report) scope.getReport()).addContextualizedResource(this.resource.getUrn(),
//        // pr.getFirst());
//        // }
//        //
//        // // TODO must contextualize the LIST, not just the first resource. For now it can
//        // // only happen with
//        // // multiple spatial extents, but it could happen also with multiple temporal
//        // // slices.
//        // if (resources.size() > 1) {
//        // scope.getMonitor()
//        // .warn("Warning: unimplemented use of multiple resources for one timestep. Choosing only
//        // the first.");
//        // }
//        //
//        // IResource res = resources.get(0).getFirst();
//        //
//        // if (Configuration.INSTANCE.isEchoEnabled()) {
//        // System.err.println("GETTING DATA FROM " + res.getUrn());
//        // }
//        // IKlabData data = Resources.INSTANCE.getResourceData(res, resources.get(0).getSecond(),
//        // scope.getScale(), scope, ret);
//        // if (Configuration.INSTANCE.isEchoEnabled()) {
//        // System.err.println("DONE " + res.getUrn());
//        // }
//        //
//        // if (data == null) {
//        // scope.getMonitor().error("Cannot extract data from resource " + resource.getUrn());
//        // }
//        //
//        // return data == null ? ret : data.getArtifact();
//    }
//}
