package org.integratedmodelling.klab.components.network.services;

import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;

public class RandomInstantiator implements IExpression, IInstantiator {

    // From Unai for directed network:
    //	# %% random network directed
    //
    //	# %% parameters
    //	# n is the number of nodes
    //	# k is the average out degree of the network
    //
    //	# %% result
    //	# a is a list of lists containing the output connections of each node
    //
    //	# %% observation
    //	# no self-connections allowed
    //
    //	a=[]
    //	for i in range(n):
    //	    a.append([])
    //	    for j in range(n):
    //	        if int(random.random()+k/(n-1))==1 and i!=j:
    //	            a[i].append(j)

    public RandomInstantiator() {
        /* to instantiate as expression - do not remove (or use) */}

    public RandomInstantiator(IParameters<String> parameters, IComputationContext context) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public IGeometry getGeometry() {
        // TODO ensure this reflects the spatial and temporal character of the
        // relationships based on the context and choice of parameters.
        return Geometry.create("#");
    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
        return new RandomInstantiator(parameters, context);
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

}
