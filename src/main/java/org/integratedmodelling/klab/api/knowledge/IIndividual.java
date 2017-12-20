package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.api.runtime.IContext;
import org.integratedmodelling.klab.api.services.IReasonerService;

/**
 * The individual (instance). In k.LAB, individuals do not need to be generated unless the runtime context
 * allows inconsistent observations to be produces, in which case they should be requested through
 * {@link IContext#instantiate(IOntology)} and validated using the {@link IReasonerService}. They can also be
 * requested for RDF export of contextualization results.
 * 
 * @author Ferd
 *
 */
public interface IIndividual extends ISemantic {

    Collection<IIndividual> getIndividuals(IProperty property);

    Collection<Object> getData(IProperty property);

    Collection<IProperty> getObjectRelationships();

    Collection<IProperty> getDataRelationships();

    boolean is(ISemantic type);

    IMetadata getMetadata();
}
