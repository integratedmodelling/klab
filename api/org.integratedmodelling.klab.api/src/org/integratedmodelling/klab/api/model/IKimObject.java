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

import java.util.List;

import org.integratedmodelling.klab.api.IStatement;

/**
 * A k.IM object is anything that was stated in k.IM. As a result, it can
 * produce the k.IM statement that corresponds to it. Some concrete subclasses
 * of IKimObject (at the moment
 * {@link org.integratedmodelling.klab.api.model.IConceptDefinition} and
 * {@link org.integratedmodelling.klab.api.model.IAcknowledgement} an have children of
 * the same type. The {@link org.integratedmodelling.klab.api.model.INamespace}
 * will list the top-level objects through
 * {@link org.integratedmodelling.klab.api.model.INamespace#getObjects()} or the
 * flattened tree of objects through
 * {@link org.integratedmodelling.klab.api.model.INamespace#getAllObjects()}.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IKimObject {

	
	/**
	 * The object's ID is its unique name within the namespace. The fully qualified
	 * name is returned by {@link #getName()}.
	 *
	 * @return the simple name for the object.
	 */
	String getId();

	/**
	 * Each k.IM object has a simple name, returned by {@link #getId()}. In any
	 * object except namespaces, the fully qualified name is a path starting with
	 * the namespace.
	 *
	 * @return the object's fully qualified name.
	 */
	String getName();

	/**
	 * The statement that originated the object in k.IM. Will return null in
	 * generated object that do not start from source.
	 *
	 * @return the k.IM statement that originated this object, or null.
	 */
	IStatement getStatement();

	/**
	 * If the object has child objects of the same kind, these will be returned
	 * here. For now only
	 * {@link org.integratedmodelling.klab.api.model.IConceptDefinition} and
	 * {@link org.integratedmodelling.klab.api.model.IAcknowledgement} have children.
	 *
	 * @return the list of children in order of declaration. Never null.
	 */
	List<IKimObject> getChildren();

	/**
	 * Return all the annotations attributed to the object in the originating k.IM
	 * code.
	 *
	 * @return a list of annotations in order of declaration, or null.
	 */
	List<IAnnotation> getAnnotations();

	/**
	 * True if the object is declared deprecated. If so, the annotations will likely
	 * contain a 'deprecated' annotation with more information.
	 *
	 * @return true if deprecated
	 */
	boolean isDeprecated();

	/**
	 * True if any errors were detected either in the parsing stage or the engine
	 * internalization phase.
	 * 
	 * Ungrammatical, but still idiomatic.
	 * 
	 * @return true if the object shouldn't be used because of errors.
	 */
	boolean isErrors();

}
