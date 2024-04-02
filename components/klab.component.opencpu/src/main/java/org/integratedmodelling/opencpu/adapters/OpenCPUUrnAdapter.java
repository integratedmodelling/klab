package org.integratedmodelling.opencpu.adapters;

import java.util.Collection;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IConfigurableUrnAdapter;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.opencpu.runtime.OpenCPU;

/**
 * URNs using this adapter must specify method and operation through the klab: URN
 * <i>klab</i>:<i>opencpu</i>:<i>method</i>:<i>operation</i> where:
 * 
 * 
 * For more complex parameterizations, use the ConfigurableURN 
 * <i>node</i>:<i>functionality</i>:<i>method</i>:<i>operation</i> where:
 * <ul>
 * <li><b>functionality</b> is an arbitrary name identifying the kind of methodology, e.g.
 * "optimization" - can be multiple. e.g. optimization.mips;</li>
 * <li><b>method</b> is a class of methods corresponding to a specific R package or function, e.g.
 * "prioritizr" or "raster". This must correspond to a <i>method</i>.json file available in the
 * config area of the adapter, defining the various submethods and their input/output contract;</li>
 * <li><b>operation</b> specifies an atomic operation for the method, defined in the
 * <i>method</i>.json adapter configuration, and ultimately selects the R algorithm that will be
 * run.</li>
 * </ul>
 * 
 * The <i>operation</i> specification defines:
 * <ul>
 * <li>how to map inputs, resource parameters and URN parameters to the R side;</li>
 * <li>what R computations to execute once a session is opened and the mapping has been done;</li>
 * <li>how to remap the result of the last R computation to the k.LAB side.</li>
 * </ul>
 * 
 * @author Ferd
 *
 */
@UrnAdapter( type = OpenCPUUrnAdapter.ID, version = Version.CURRENT)
public class OpenCPUUrnAdapter implements IConfigurableUrnAdapter {

    public static final String ID = "opencpu";
    
    OpenCPU openCpu = null;
    
    public OpenCPUUrnAdapter() {
        
    }
    
    @Override
    public String getName() {
        return ID;
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
    public boolean isOnline(Urn urn) {
        // TODO Auto-generated method stub
        return openCpu != null && openCpu.isOnline();
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        
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

    @Override
    public void encodeData(IResource resource, Builder builder, IGeometry geometry, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        
    }

}
