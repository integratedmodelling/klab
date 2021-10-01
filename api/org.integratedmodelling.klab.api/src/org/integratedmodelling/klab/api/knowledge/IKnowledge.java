/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.utils.SemanticType;

/**
 * IKnowledge defines the methods that are common to both IConcept and IProperty, i.e. all
 * assertions made in both OWL and k.IM namespaces.
 *
 * @author Ferdinando Villa
 * @version $Id: $Id
 */
public interface IKnowledge extends ISemantic {

    /**
     * The URN for a concept is the fully qualified name of the concept, in the form supported by
     * {@link SemanticType}. It can be converted to a proper URN format by prefixing it with the
     * standard k.LAB format for knowledge URNs, explained in TODO.
     *
     * @return the fully qualified name, e.g. 'geography:Elevation'.
     */
    String getUrn();

    /**
     * All knowledge has a URI.
     *
     * @return the URI
     */
    String getURI();

    /**
     * All IKnowledge objects have a simple local name
     *
     * @return local name within namespace
     */
    String getName();

    /**
     * A name of reference that is uniquely associated with the semantics (not necessarily with the
     * OWL class) but is suitable as a language identifier. In simple concepts, identical to
     * {@link #getName()}.
     * 
     * @return
     */
    String getReferenceName();

    /**
     * The name of the namespace or ontology that this knowledge comes from.
     *
     * @return the ontology's simple ID
     */
    String getNamespace();

    /**
     * True if this is subsumed by the passed resource.
     *
     * @param concept a {@link org.integratedmodelling.klab.api.knowledge.ISemantic} object.
     * @return true if this knowledge is the passed knowledge
     */
    boolean is(ISemantic concept);

    /**
     * The notion of an abstract concept is extremely important in k.LAB: abstract knowledge
     * represents questions, where concrete knowledge represents answers (observations).
     *
     * @return true if concept is abstract (no instances can be created).
     */
    boolean isAbstract();

    /**
     * Get the semantic closure in the current environment (may be using a reasoner or not).
     *
     * @return the set of things we are.
     */
    Collection<IConcept> getSemanticClosure();

    /**
     * Return the domain of the owning namespace, or null if the knowledge is not part of any
     * domain.
     *
     * @return the concept describing our domain.
     */
    IConcept getDomain();

    /**
     * All knowledge comes from an ontology.
     *
     * @return the actual ontology, contained in the namespace.
     */
    IOntology getOntology();

    /**
     * All knowledge may have metadata, which may be empty. The metadata will contain the value of
     * all annotation properties recognized by k.LAB, indexed by the properties' k.LAB qualified
     * name. This also applies to concepts from OWL ontologies that did not originate in a k.LAB
     * namespace.
     *
     * @return metadata. Never null.
     */
    IMetadata getMetadata();

}
