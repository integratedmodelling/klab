package org.integratedmodelling.klab.api.observations;

import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IIndividual;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.services.IOntologyService;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface ISubject extends IDirectObservation {

    /**
     * 
     * @return
     */
    Collection<IEvent> getEvents();

    /**
     * 
     * @return
     */
    Collection<IProcess> getProcesses();

    /**
     * 
     * @return
     */
    Collection<ISubject> getSubjects();

    /**
     * 
     * @return
     */
    Collection<IRelationship> getRelationships();

    /**
     * 
     * @param subject
     * @return
     */
    Collection<IRelationship> getIncomingRelationships(ISubject subject);

    /**
     * 
     * @param subject
     * @return
     */
    Collection<IRelationship> getOutgoingRelationships(ISubject subject);

    /**
     * 
     * @return
     */
    Map<IConcept, IConfiguration> getConfigurations();

    /**
     * Call this on the root observation to create the logical peers of an observation tree in the passed
     * ontology.
     * 
     * @param ontology
     *            an ontology, usually empty (may be created with {@link IOntologyService#require(String)}).
     * @return the individual corresponding to this subject.
     */
    IIndividual instantiate(IOntology ontology);

}
