package org.integratedmodelling.klab.data.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Wrapper for the result of a resource contextualization operation, used during one and only one
 * contextualizer resolve() or instantiate() operation. In simple cases it's nothing but a single
 * resource. In more complex cases it may contain multiple time-dependent resources that must be
 * aggregated, or multiple spatial slices (the latter case is not happening at the moment, as the
 * spatial merging is done upstream at the model level by the dataflow compiler, but it is assumed
 * that the spatial coverage of multiple-resource models is the same, which may change).
 * 
 * {@link IResourceService#contextualizeResource(IResource, Map, IScale, IArtifact, IContextualizationScope)}
 * returns one of these mandatorily, so that contextualizers can organize to handle this type and
 * nothing else.
 * 
 * @author Ferd
 *
 */
public class ContextualizedResource extends Resource {

    List<Pair<IResource, ICoverage>> resourceStructure = new ArrayList<>();
    boolean empty = false;
    
    /**
     * Simple copy of all resource data
     * 
     * @param resource
     */
    public ContextualizedResource(Resource resource) {
        super(resource.getReference());
        resourceStructure.add(new Pair<>(resource, null));
        runtimeData.putAll(resource.runtimeData);
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }
    
    /**
     * Resource holds the result of contextualizing a merging resource.
     * 
     * @param ret
     */
    public ContextualizedResource(MergedResource source, List<Pair<IResource, Map<String, String>>> ret) {
        if (ret.size() == 0) {
            empty = true;
//            copyContents(source);
        } else if (ret.size() == 1) {
            IResource resource = ((Resource) ret.get(0).getFirst());
            copy(((Resource) resource).getReference());
            resourceStructure.add(new Pair<>(resource, null));
            runtimeData.putAll(((Resource)resource).runtimeData);
        } else {
            throw new KlabUnimplementedException("multiple resources per contextualization are still unsupported");
        }
    }

    public ContextualizedResource(ResourceReference reference) {
        super(reference);
        resourceStructure.add(new Pair<>(this, null));
    }

    /**
     * The resource structure for recording into provenance. The trivial case will simply report one
     * pair with the coverage at null, i.e. the entire scale of contextualization. In more complex
     * cases, the coverage is only for provenance recording so it will only contain the boundary
     * extents for merging.
     * 
     * @return
     */
    public List<Pair<IResource, ICoverage>> getAtomicResources() {
        return resourceStructure;
    }

}
