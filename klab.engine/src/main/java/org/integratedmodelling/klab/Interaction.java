package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IComputableResource.InteractiveParameter;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IInteractionService;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.rest.UserInputRequest;
import org.integratedmodelling.klab.rest.UserInputResponse;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.TypeUtils;
import org.integratedmodelling.klab.utils.Utils;

public enum Interaction implements IInteractionService {

    INSTANCE;

    /**
     * Describe the passed parameter for the passed service call.
     * 
     * @param call
     * @param parameter
     * @return the interactive parameter descriptor.
     */
    public InteractiveParameter getParameterDescriptor(String id, IServiceCall call, String parameter) {
        InteractiveParameter p = null;
        IPrototype prototype = Extensions.INSTANCE.getPrototype(call.getName());
        if (prototype != null) {
            p = new InteractiveParameter();
            p.setFunctionId(id + "/" + prototype.getName());
            p.setId(parameter);
            p.setDescription(prototype.getArgument(parameter).getDescription());
            p.setType(prototype.getArgument(parameter).getType());
            if (call.getParameters().contains(parameter)) {
                p.setInitialValue(call.getParameters().get(parameter).toString());
            }
        }
        return p;
    }

    private InteractiveParameter getParameterDescriptor(String id, IAnnotation annotation) {
        InteractiveParameter p = new InteractiveParameter();
        p.setFunctionId(id + "/EXTERNAL");
        p.setId(annotation.get("name", String.class));
        p.setDescription(annotation.get("description", String.class));
        p.setType(Utils.getArtifactType(annotation.get("default", Object.class).getClass()));
        p.setInitialValue(annotation.get("default", Object.class).toString());
        return p;
    }

    @Override
    public Collection<InteractiveParameter> getInteractiveParameters(IComputableResource computable) {
        List<InteractiveParameter> ret = new ArrayList<>();
        if (computable.getServiceCall() != null) {
            for (String id : computable.getServiceCall().getInteractiveParameters()) {
                InteractiveParameter descriptor = getParameterDescriptor(((ComputableResource) computable)
                        .getId(), computable.getServiceCall(), id);
                if (descriptor != null) {
                    ret.add(descriptor);
                }
            }
        }
        if (((ComputableResource) computable).getExternalParameters() != null) {
            for (IAnnotation parameter : ((ComputableResource) computable).getExternalParameters()) {
                ret.add(getParameterDescriptor(((ComputableResource) computable)
                        .getId(), parameter));
            }
        }
        return ret;
    }

    /**
     * Submit the passed parameters, wait for a user's response which will directly
     * modify the the passed resources.
     * 
     * @param resources
     * @param fields
     */
    public void submitParameters(List<Pair<IComputableResource, List<String>>> resources, List<InteractiveParameter> fields, ISession session) {

        UserInputRequest request = new UserInputRequest();

        /*
         * Send the session ID as request ID so that multiple requests can be compounded
         * at the client side for a single response.
         */
        request.setRequestId(session.getId());

        try {
            request.setDescription("The following parameters admit user input in interactive mode. Please submit the desired values.");
            request.getFields().addAll(fields);
            IMessage resp = session.getMonitor()
                    .ask(IMessage.MessageClass.UserInterface, IMessage.Type.UserInputRequested, request)
                    .get();
            Object payload = resp.getPayload(Object.class);
            if (payload instanceof Map) {
                UserInputResponse response = JsonUtils
                        .convertMap((Map<?, ?>) payload, UserInputResponse.class);
                for (String value : response.getValues().keySet()) {
                    String keys[] = value.split("/");
                    for (Pair<IComputableResource, List<String>> resource : resources) {
                        if (((ComputableResource) resource.getFirst()).getId().equals(keys[0])) {
                            ((ComputableResource) resource.getFirst())
                                    .setInteractiveParameter(keys[2], parseValue(response.getValues()
                                            .get(value), keys[2], resource.getFirst()));
                        }
                    }
                }
            }

        } catch (Throwable e) {
            session.getMonitor().error(e);
        }
    }

    private Object parseValue(Object value, String key, IComputableResource resource) {
        boolean found = false;
        if (resource.getServiceCall() != null) {
            Prototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
            Argument argument = prototype == null ? null : prototype.getArgument(key);
            if (argument != null) {
                found = true;
                value = TypeUtils.convert(value, Utils.getClassForType(argument.getType()));
            }
        }
        if (!found) {
            for (IAnnotation annotation : ((ComputableResource)resource).getExternalParameters()) {
                if (annotation.get("name", String.class).equals(key)) {
                    value = TypeUtils.convert(value, annotation.get("default").getClass());
                    break;
                }
            }
        }
        return value;
    }

}
