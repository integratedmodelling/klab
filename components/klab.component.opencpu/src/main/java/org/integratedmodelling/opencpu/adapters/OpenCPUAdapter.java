package org.integratedmodelling.opencpu.adapters;

import java.util.Collection;
import java.util.Collections;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.kim.Prototype;

/**
 * URNs using this adapter must specify
 * <i>node</i>:<i>functionality</i>:<i>method</i>:<i>operation</i> where:
 * 
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
 * @deprecated should use the klab: version but offer local customization through resource content
 */
//@ResourceAdapter(version = Version.CURRENT, canCreateEmpty = true, handlesFiles = false, type = OpenCPUAdapter.ID)
public class OpenCPUAdapter implements IResourceAdapter {

    public static final String ID = "opencpu";

    /**
     * Operations for I/O or other "primitives" that are handled specially by k.LAB, usually to
     * interface complex input data types to R or back to k.LAB.
     * 
     * @author Ferd
     *
     */
    public static enum InternalOperation {
        /**
         * Import a raster state from k.LAB input into the specified variable in R. If >1 input
         * (comma-separated variable name list) or the input is specified through an annotation
         * (variable name starts with @), a raster stack is created.
         */
        RasterImport,
        /**
         * Export a R raster into an output state for k.LAB
         */
        RasterExport,
        /**
         * k.LAB -> R simple features. If input is a raster grid, treat each cell as a feature.
         */
        FeatureImport,
        /**
         * R simple features -> k.LAB
         */
        FeatureExport,
        /**
         * k.LAB tabular -> R data frame
         */
        TableImport,
        /**
         * R data frame -> k.LAB tabular
         */
        TableExport,
        /**
         * Output a scalar to k.LAB, automatically promoting to the state geometry required.
         */
        ScalarExport,
        /**
         * Output scalars to object metadata for k.LAB.
         */
        MetadataExport,
        /**
         * Evaluate the constraint and if false, stop contextualization
         */
        Assertion
    }

    @Override
    public String getName() {
        return ID;
    }

    @Override
    public IResourceValidator getValidator() {
        return new OpenCPUValidator();
    }

    @Override
    public IResourcePublisher getPublisher() {
        return new OpenCPUPublisher();
    }

    @Override
    public IResourceEncoder getEncoder() {
        return new OpenCPUEncoder();
    }

    @Override
    public IResourceImporter getImporter() {
        return new OpenCPUImporter();
    }

    @Override
    public IResourceCalculator getCalculator(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPrototype> getResourceConfiguration() {
        return Collections.singleton(new Prototype(
                Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("opencpu/prototypes/opencpu.kdl"))
                        .getActuators().iterator().next(),
                null));
    }

}
