/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.model;

import org.integratedmodelling.klab.api.knowledge.IConcept;

/**
 * k.IM object corresponding to the definition of a concept (the statement that creates it, as opposed to the
 * declaration by composing one or more existing concepts). Contains the concept defined as well as the
 * syntactic elements that generated it.
 * 
 * The {@link #getChildren()} method of an IConceptDefinition only returns other IConceptDefinitions.
 * 
 * @author fvilla
 *
 */
public interface IConceptDefinition extends IKimObject, INamespaceQualified {

    /**
     * Get the top-level declared concept.
     * 
     * @return the concept resulting from the definition
     */
    IConcept getConcept();

}
