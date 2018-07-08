/**
 * 
 */
/**
 * This package provides a thin layer of abstraction over OWLAPI so that
 * ontologies compliant with k.LAB's API can be easily read, created, managed
 * and reasoned upon. The main k.LAB API classes implemented are
 * {@link org.integratedmodelling.klab.api.knowledge.IConcept},
 * {@link org.integratedmodelling.klab.api.knowledge.IOntology} and
 * {@link org.integratedmodelling.klab.api.knowledge.IProperty}. Because
 * relationships are modeled with concepts in k.LAB, the IProperty class is used
 * much less than in OWL.
 * 
 * Knowledge-aware services such as
 * {@link org.integratedmodelling.klab.api.services.IOntologyService},
 * {@link org.integratedmodelling.klab.api.services.IConceptService},
 * {@link org.integratedmodelling.klab.api.services.IReasonerService},
 * {@link org.integratedmodelling.klab.api.services.IRoleService},
 * {@link org.integratedmodelling.klab.api.services.ITraitService},
 * {@link org.integratedmodelling.klab.api.services.IObservableService} use
 * functionalities defined here to perform their duties.
 * 
 * Each OWL2 ontology under the control of this subsystem, including the core
 * ontologies that are read from the classpath at startup, correspond to a
 * {@link INamespace}, which in turn can
 * return the correspondent {@link IOntology}.
 * The {@link org.integratedmodelling.klab.api.services.INamespaceService} is
 * the endorsed k.LAB way to access knowledge, and direct use of classes from
 * this package should not be necessary for the regular k.LAB user or developer.
 * 
 * @author Ferdinando Villa
 * @author Ioannis Athanasiadis
 * 
 */
package org.integratedmodelling.klab.owl;
