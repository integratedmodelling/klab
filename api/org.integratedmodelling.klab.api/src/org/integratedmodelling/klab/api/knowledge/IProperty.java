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
package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The interface for a Property. Most of the interface is meant to check the
 * subtype of property we're dealing with. Some of the available subtypes are
 * standard in OWL, others (such as classification) are k.LAB-specific and are
 * normally figured out by checking whether the property inherits by specific
 * upper ontology ones. Also, for now we just ignore functionality, symmetry,
 * transitivity etc, given that the reasoner operates on the underlying OWL
 * model.
 * <p>
 * Properties are not part of observations, but only of the underlying data
 * model. Only RDF-compatible {@link IIndividual}s instantiate properties. The
 * semantics of observed relationships is modeled using observable IConcepts and
 * has nothing to do with IProperties.
 * 
 * @author Ferdinando Villa, Ecoinformatics Collaboratory, UVM
 * @version $Id: $Id
 */
public interface IProperty extends IKnowledge {

	/**
	 * The IMA provides ways around the fact that OWL-DL does not allow properties
	 * to have a class in their range, but only an instance. A property can be made
	 * a classification property by making it a subproperty of a generic
	 * "classification property" that is known to the KM. Instances of such
	 * properties will automatically look for (and create if necessary) a special
	 * unique instance of the class. The target of the relationship will appear as a
	 * class in the API.
	 *
	 * @return true if relationship is a classification relationship.
	 */
	boolean isClassification();

	/**
	 * An equivalent of Datatype property in OWL, but extended to handle the
	 * extended literals (Reified literals) that the IMA supports. Such literals can
	 * be defined in OWL as instances of a class that derives from the configured
	 * ReifiedLiteral class in the base ontology, and they have a text property that
	 * links to the literal's definition. The API will create validated Values and
	 * not instances for these instances, using the validator configured for the
	 * type, and I/O to ontologies will handle them transparently.
	 *
	 * @return true if literal property: either DatatypeProperty or linking to a
	 *         ReifiedLiteral
	 */
	boolean isLiteralProperty();

	/**
	 * Check if this property links to an instance (object).
	 *
	 * @return true if property links to objects.
	 */
	boolean isObjectProperty();

	/**
	 * <p>
	 * isAnnotation.
	 * </p>
	 *
	 * @return true if annotation property
	 */
	boolean isAnnotation();

	/**
	 * Returns the inverse of a IProperty. Null if there is no inverse.
	 *
	 * @return the inverse IProperty
	 */
	IProperty getInverseProperty();

	/**
	 * <p>
	 * getRange.
	 * </p>
	 *
	 * @return the range
	 */
	Collection<IConcept> getRange();

	/**
	 * TODO domain may not be unique. It would be wonderful to ignore that for
	 * simplicity. I don't think multi-domain properties are good design.
	 *
	 * @return the domain
	 */
	Collection<IConcept> getPropertyDomain();

	/**
	 * Return the (only) parent property, or throw an exception if there's more than
	 * one parent.
	 *
	 * @return the parent
	 * @throws org.integratedmodelling.klab.exceptions.KlabException
	 */
	IProperty getParent() throws KlabException;

	/**
	 * <p>
	 * getParents.
	 * </p>
	 *
	 * @return all direct parents
	 */
	Collection<IProperty> getParents();

	/**
	 * <p>
	 * getAllParents.
	 * </p>
	 *
	 * @return the parent closure
	 */
	Collection<IProperty> getAllParents();

	/**
	 * <p>
	 * getChildren.
	 * </p>
	 *
	 * @return the direct children
	 */
	Collection<IProperty> getChildren();

	/**
	 * <p>
	 * getAllChildren.
	 * </p>
	 *
	 * @return the children closure
	 */
	Collection<IProperty> getAllChildren();

	/**
	 * <p>
	 * isFunctional.
	 * </p>
	 *
	 * @return true if functional
	 */
	boolean isFunctional();

}
