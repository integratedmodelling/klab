package org.integratedmodelling.random.adapters;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.scale.Scale;

@UrnAdapter(type = "recreationidb", version = Version.CURRENT)
public class RecreationIDBAdapter implements IUrnAdapter {

	public static final String NAME = "ridb";
	
	public static final String RECAREAS = "recreation.areas";
	
	public static final String SITES = "sites";
	
	public static final String LIMIT = "limit";
	public static final String OFFSET = "offset";
	public static final String STATE = "state";
	public static final String ACTIVITY = "activity";
	public static final String RADIUS = "radius";
	
	public static final String APIKEY = "apikey"; 
	
	// TODO: complete other possible namespaces.
	public static String[] namespace_ids = new String[] { RECAREAS };
	public static String[] area_attribute_ids = new String[] { LIMIT, OFFSET, STATE, ACTIVITY, RADIUS};
	
	public RecreationIDBAdapter() {		
		Arrays.sort(area_attribute_ids);
		Arrays.sort(namespace_ids);
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public boolean isOnline(Urn urn) {
		//TODO
		return true;
	}
	
	@Override
	public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {	
		
		Map<String,String> parameters = new HashMap<>();
		if (urn.getParameters().containsKey(LIMIT)){
			parameters.put(LIMIT,urn.getParameters().get(LIMIT));
		} 
		if (urn.getParameters().containsKey(OFFSET)) {
			parameters.put(OFFSET, urn.getParameters().get(OFFSET));
		} 
		if (urn.getParameters().containsKey(STATE)) {
			parameters.put(STATE,urn.getParameters().get(STATE));
		} 
		if (urn.getParameters().containsKey(ACTIVITY)) {
			parameters.put(ACTIVITY,urn.getParameters().get(ACTIVITY)) ;
		} 
		if (urn.getParameters().containsKey(RADIUS) ) {
			parameters.put(RADIUS,urn.getParameters().get(RADIUS));
		} else {
			parameters.put(RADIUS,"0.0");
		}
		
		String apiKey = urn.getParameters().containsKey(APIKEY) 
				? urn.getParameters().get(APIKEY) 
				: "82b00cad-58b5-40e8-9d77-77caba299473";
		
        IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);

        if (scale.getSpace() != null) {
        	
        	RecreationIDB ridb = new RecreationIDB();
            String input = buildRecreationIDBInput(parameters);
            RecreationIDBOutputDeserializer.RecreationAreas recreationAreas = ridb.recreationAreas(input,apiKey);
            List<Map<String, Object>> data = recreationAreas.getData();
                        
            IShape shape;
            
            for (Map<String,Object> area : data) {
            	
            	double lat = (double) area.get("lat");
            	double lon = (double) area.get("lon");
            	String siteName = (String) area.get("name");
            	            	
            	// Create the point. 
            	// TODO: in the general case recreation areas should be polygons while entrances to the areas are points.
            	shape = Shape.create(lon,lat,(Projection) scope.getScale().getSpace().getProjection());
            	
            	Builder obuilder = builder.startObject(scope.getTargetName(), siteName, makeScale(urn, shape, scope));
            
            	// Add attributes to each recreation area like the name and id.
            	for (Map.Entry<String, Object> entry : area.entrySet()) {
            		if (entry.getKey()!="lat" || entry.getKey()!="lon" || entry.getKey()!="name") {
            			obuilder.withMetadata(entry.getKey(), entry.getValue());
            		}
            	}
            	obuilder.finishObject();
            
            }
            
        }
	
	}
	
    // TODO: There's a limit of 1000 entries hardcoded in the API. To make sure that we retrieve everything and with the ad hoc knowledge that 
    // there's always less than 1000 recreation sites per US state, when there are N states passed as arguments create N different GET calls to 
    // the API one per each state and assemble later all the responses. In the case no state parameter is specified default is retrieving from 
    // the entire US territory, in that case have a list of all the states hardcoded and create one GET message per state. Eventually it would 
    // be ideal to identify automatically the states involved and also reject responses out of the geographical scope of the context.
    private final String USA_STATES = "AL,AK,AZ,AR,CA,CO,CT,DE,FL,GA,HI,ID,IL,IN,IA,KS,KY,LA,ME,MD,MA,MI,MN,MS,MO,MT,NE,NV,NH,NJ,NM,NY,NC,ND,OH,OK,OR,PA,RI,SC,SD,TN,TX,UT,VT,VA,WA,WV,WI,WY";
    private String buildRecreationIDBInput(Map<String,String> parameters) {
        ArrayList<String> query = new ArrayList<>();
        if (!parameters.containsKey(STATE)) {
            query.add(STATE + "-" + USA_STATES);
        }
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
		Urn kurn = new Urn(urn);
        ResourceReference ref = new ResourceReference();
        ref.setUrn(urn.toString());
        ref.setAdapterType(getName());
        ref.setLocalName(kurn.getResourceId());
        ref.setGeometry("#S2");
	    ref.setVersion(Version.CURRENT);
	    ref.setType(getType(kurn));
	    return new Resource(ref);
	}

	@Override
	public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {
		return resource;
	}

	
	@Override
	public Type getType(Urn urn) {
		// Instances of recreation areas are all bounded objects: polygons or points. 
		return Type.OBJECT;
	}

	@Override
	public IGeometry getGeometry(Urn urn) {
		// Object geometry.
		return Geometry.create("#S2");
	}

	@Override
	public String getDescription() {
		return "Outdoor recreation areas in USA federal lands.";
	}

	@Override
	public Collection<String> getResourceUrns() {
		List<String> ret = new ArrayList<>();
        // TODO
        return ret;
	}
	
	
}