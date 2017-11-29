/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The interface for a Property.  Most of the interface is meant to check the subtype of property we're dealing with.
 * Some of the available subtypes are standard in OWL, others (such as classification) are k.LAB-specific and are
 * normally figured out by checking whether the property inherits by specific upper ontology ones.  Also, for now we
 * just ignore functionality, symmetry, transitivity etc, given that the reasoner  operates on the underlying OWL
 * model. We'll see if they're needed.
 * 
 * @author  Ferdinando Villa, Ecoinformatics Collaboratory, UVM
 */
public interface IProperty extends IKnowledge {

    /**
     * The IMA provides ways around the fact that OWL-DL does not allow properties to have a class in their
     * range, but only an instance. A property can be made a classification property by making it a subproperty
     * of a generic "classification property" that is known to the KM. Instances of such properties will automatically
     * look for (and create if necessary) a special unique instance of the class. The target of the relationship will
     * appear as a class in the API.
     * @return true if relationship is a classification relationship.
     */
    boolean isClassification();

    /**
     * An equivalent of Datatype property in OWL, but extended to handle the extended literals (Reified literals)
     * that the IMA supports. Such literals can be defined in OWL as instances of a class that derives from the
     * configured ReifiedLiteral class in the base ontology, and they have a text property that links to the
     * literal's definition. The API will create validated Values and not instances for these instances, using the
     * validator configured for the type, and I/O to ontologies will handle them transparently.
     * @return true if literal property: either DatatypeProperty or linking to a ReifiedLiteral
     */
    boolean isLiteralProperty();

    /**
     * Check if this property links to an instance (object).
     * @return true if property links to objects.
     */
    boolean isObjectProperty();

    /**
     * 
     * @return true if annotation property
     */
    boolean isAnnotation();

    /**
     * Returns the inverse of a IProperty. Null if there is no inverse.
     * @return   the inverse IProperty
     */
    IProperty getInverseProperty();

    /**
     * 
     * @return the range 
     */
    Collection<IConcept> getRange();

    /**
     * TODO domain may not be unique. It would be wonderful to ignore that for simplicity. I don't think
     * multi-domain properties are good design.
     * @return the domain
     */
    Collection<IConcept> getPropertyDomain();

    /**
     * Return the (only) parent property, or throw an exception if there's more than one parent.
     * @return the parent
     * @throws KlabException 
     */
    IProperty getParent() throws KlabException;

    /**
     * @return all direct parents
     */
    Collection<IProperty> getParents();

    /**
     * @return the parent closure
     */
    Collection<IProperty> getAllParents();

    /**
     * @return the direct children
     */
    Collection<IProperty> getChildren();

    /**
     * @return the children closure
     */
    Collection<IProperty> getAllChildren();

    /**
     * @return true if functional
     */
    boolean isFunctional();

}
