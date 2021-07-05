package org.integratedmodelling.klab.cli.commands.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.rest.ModelReference;
import org.integratedmodelling.klab.utils.Parameters;

public class List implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        String ret = "";
        Parameters<String> parameters = call.getParameters();
        boolean verbose = parameters.get("verbose", false);
        String pattern = parameters.get("modelpattern", "");

        java.util.List<ModelReference> params = null;
        if (pattern.isEmpty()) {
            // if no pattern is supplied, allow the argument to still be a model name
            // in which case the verbose mode for that model should be enabled
            if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
                params = new ArrayList<>();
                for(Object o : call.getParameters().get("arguments", java.util.List.class)) {
                    String string = o.toString();
                    params.add(Models.INSTANCE.getModelReference(string));
                }
                verbose = true;
            }
        }
        java.util.List<ModelReference> modelsList = Models.INSTANCE.listModels(true);
        if (!pattern.isEmpty()) {
            modelsList = modelsList.stream()
                    .filter(modelReference -> modelReference.getName().toLowerCase().contains(pattern.toLowerCase()))
                    .collect(Collectors.toList());
            ret += "Filter enabled with pattern: " + pattern;
        }
        for(ModelReference model : params == null ? modelsList : params) {
            ret += (ret.isEmpty() ? "" : "\n") + describe(model, verbose);
        }
        ret += "\nKnown definitions:\n";
        for(String definition : Models.INSTANCE.getKbox().getKnownDefinitions()) {
            if (pattern.isEmpty() || definition.toLowerCase().contains(pattern.toLowerCase())) {
                ret += "   " + definition + "\n";
            }
        }

        return ret;
    }

    private String describe(ModelReference urn, boolean verbose) {
        String ret = "URN: " +  urn.getUrn();
        if (verbose) {
            ret += "\n  Observable:  " + urn.getObservable();
            ret += "\n  Shape:  " + (urn.getShape() == null ? "-nv-" : urn.getShape());
            ret += "\n  Observation Type:  " + (urn.getObservationType() == null ? "-nv-" : urn.getObservationType());
        }
        return ret;
    }

}
