package org.integratedmodelling.random.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.scale.Scale;

@UrnAdapter(type = "random", version = Version.CURRENT)
public class RecreationIDBAdapter implements IUrnAdapter {

	public static final String NAME = "ridb";
	
	public static final String LIMIT = "limit";
	public static final String OFFSET = "offset";
	public static final String STATE = "state";
	public static final String ACTIVITY = "activity";
	public static final String RADIUS = "radius";
	public static final String APIKEY = "apikey"; 
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public boolean isOnline(Urn urn) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {
		// Based on the encodeData method of the RandomAdapter, assuming that we only create objects:
		// instances of observables.		
		
		Map<String,String> parameters = new HashMap<>();
		if (urn.getParameters().containsKey(LIMIT)){
			parameters.put(LIMIT,urn.getParameters().get(LIMIT));
		} else {
			parameters.put(LIMIT,"20");
		}
		if (urn.getParameters().containsKey(OFFSET)) {
			parameters.put(OFFSET, urn.getParameters().get(OFFSET));
		} else {
			parameters.put(OFFSET,"0");
		}
		if (urn.getParameters().containsKey(STATE)) {
			parameters.put(STATE,urn.getParameters().get(STATE));
		} else {
			parameters.put(STATE,"CO");
		}
		if (urn.getParameters().containsKey(ACTIVITY)) {
			parameters.put(ACTIVITY,urn.getParameters().get(ACTIVITY)) ;
		} else {
			parameters.put(ACTIVITY,"1");
		}
		if (urn.getParameters().containsKey(RADIUS) ) {
			parameters.put(RADIUS,urn.getParameters().get(RADIUS));
		} else {
			parameters.put(RADIUS,"10.0");
		}
		
		String apiKey = urn.getParameters().containsKey(APIKEY) 
				? urn.getParameters().get(APIKEY) 
				: "82b00cad-58b5-40e8-9d77-77caba299473";
		
		String artifactName = urn.getResourceId().substring(0, urn.getResourceId().length() - 1);

        IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);

        int n = 1;
        if (scale.getSpace() != null) {
        	
        	RecreationIDB ridb = new RecreationIDB();
            String input = buildRecreationIDBInput(parameters);
            RecreationIDBOutputDeserializer.RecreationAreas recreationAreas = ridb.recreationAreas(input,apiKey);
            List<Map<String, Object>> data = recreationAreas.getData();
            
            IShape shape;
            
            for (Map<String,Object> area : data) {
            	
            	double lat = (double) area.get("lat");
            	double lon = (double) area.get("lon");
            	
            	// Create the point
            	shape = Shape.create(lon,lat,(Projection) scope.getScale().getSpace().getProjection());
            	
            	Builder obuilder = builder.startObject(scope.getTargetName(), artifactName + "_" + (n++),
                        makeScale(urn, shape, scope));
            
            	// Add attributes to each recreation area like the name and id.
            	for (Map.Entry<String, Object> entry : area.entrySet()) {
            		if (entry.getKey()!="lat" || entry.getKey()!="lon") {
            			obuilder.withMetadata(entry.getKey(), (String) entry.getValue());
            		}
            	}
            
            }
            
        }
	
	}
	
	private String buildRecreationIDBInput(Map<String,String> parameters) {
		ArrayList<String> query = new ArrayList<>();
		for(Map.Entry<String, String> entry : parameters.entrySet()) {
			query.add(entry.getKey()+"="+entry.getValue());
		}
		return String.join("&", query);	
	}

	
	private IGeometry makeScale(Urn urn, IShape shape, IContextualizationScope scope) {

        List<IExtent> extents = new ArrayList<>();
        extents.add(scope.getContextSubject().getScale().getTime());
        extents.add(shape);
        return Scale.create(extents).asGeometry();
    }
	
	@Override
	public IResource getResource(String urn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	@Override
	public Type getType(Urn urn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGeometry getGeometry(Urn urn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getResourceUrns() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}