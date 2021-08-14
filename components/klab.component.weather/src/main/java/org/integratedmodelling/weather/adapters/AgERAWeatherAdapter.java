package org.integratedmodelling.weather.adapters;

import java.util.ArrayList;

import org.integratedmodelling.adapter.datacube.GenericDatacubeAdapter;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.weather.adapters.agera.AgERA5Repository;
import org.integratedmodelling.weather.adapters.agera.AgERA5Repository.VariableConfiguration;

public class AgERAWeatherAdapter extends GenericDatacubeAdapter {

	protected AgERAWeatherAdapter() {
		super("agera5", new AgERA5Repository());
	}
	
    @Override
    public IResource getResource(String urn) {

        Urn kurn = new Urn(urn);
        ResourceReference ref = new ResourceReference();
        ref.setUrn(kurn.getUrn());
        ref.setAdapterType(getName());
        ref.setLocalName(kurn.getResourceId());
        ref.setGeometry(getGeometry(kurn).encode());
        ref.setVersion(Version.CURRENT);
        ref.setType(getType(kurn));
        
        int i = 0;
        ref.setOutputs(new ArrayList<>());
        for (VariableConfiguration v : ((AgERA5Repository)datacube).getVariable(kurn.getResourceId())) {
            try {
                AttributeReference attr = new AttributeReference();
                attr.setIndex(i++);
                attr.setType(Type.NUMBER);
                attr.setName(v.variable.codename);
                ref.getOutputs().add(attr);
            } catch (Throwable t) {
                return null;
            }
        }

        return new Resource(ref);
    }

	
}
