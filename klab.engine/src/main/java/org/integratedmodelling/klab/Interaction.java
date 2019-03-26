package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IComputableResource.InteractiveParameter;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IInteractionService;
import org.integratedmodelling.klab.rest.UserInputRequest;
import org.integratedmodelling.klab.rest.UserInputResponse;
import org.integratedmodelling.klab.utils.Pair;

public enum Interaction implements IInteractionService {

    INSTANCE;

    /**
     * Describe the passed parameter for the passed service call.
     * 
     * @param call
     * @param parameter
     * @return the interactive parameter descriptor.
     */
    public InteractiveParameter getParameterDescriptor(IServiceCall call, String parameter) {
        InteractiveParameter p = null;
        IPrototype prototype = Extensions.INSTANCE.getPrototype(call.getName());
        if (prototype != null) {
            p = new InteractiveParameter();
            p.setId(parameter);
            p.setDescription(prototype.getArgument(parameter).getDescription());
            p.setType(prototype.getArgument(parameter).getType());
            if (call.getParameters().contains(parameter)) {
                p.setInitialValue(call.getParameters().get(parameter).toString());
            }
        }
        return p;
    }

    @Override
    public Collection<InteractiveParameter> getInteractiveParameters(IComputableResource computable) {
        List<InteractiveParameter> ret = new ArrayList<>();
        if (computable.getServiceCall() != null) {
            for (String id : computable.getServiceCall().getInteractiveParameters()) {
                InteractiveParameter descriptor = getParameterDescriptor(computable.getServiceCall(), id);
                if (descriptor != null) {
                    ret.add(descriptor);
                }
            }
        }
        return ret;
    }

    /**
     * Submit the passed parameters, wait for a user's response which will directly modify the
     * the passed resources.
     * 
     * @param resources
     * @param fields
     */
    public void submitParameters(List<Pair<IComputableResource, List<String>>> resources, List<InteractiveParameter> fields, ISession session) {
        // TODO Auto-generated method stub
        UserInputRequest request = new UserInputRequest();
        try {
            request.setDescription("The following parameters admit user input in interactive mode. Please submit the desired values.");
            request.getFields().addAll(fields);
            IMessage resp = session.getMonitor()
                    .ask(IMessage.MessageClass.UserInterface, IMessage.Type.UserInputRequested, request)
                    .get();

            UserInputResponse response = resp.getPayload(UserInputResponse.class);

            // TODO

        } catch (Throwable e) {
            session.getMonitor().error(e);
        }
    }

}
