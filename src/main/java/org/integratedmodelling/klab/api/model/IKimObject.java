package org.integratedmodelling.klab.api.model;

import java.io.Serializable;
import java.util.List;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimStatement;

/**
 * A k.IM object is anything that was stated in k.IM. As a result, it can produce the k.IM statement that
 * corresponds to it. Some concrete subclasses of IKimObject (at the moment {@link IConceptDefinition} and
 * {@link IObserver} an have children of the same type. The {@link INamespace} will list the top-level objects
 * through {@link INamespace#getObjects()} or the flattened tree of objects through
 * {@link INamespace#getAllObjects()}.
 * 
 * @author Ferd
 *
 */
public interface IKimObject extends Serializable {

    /**
     * The object's ID is its unique name within the namespace. The fully qualified name is returned by
     * {@link #getName()}.
     * 
     * @return the simple name for the object.
     */
    String getId();

    /**
     * Each k.IM object has a simple name, returned by {@link #getId()}. In any object except namespaces, the
     * fully qualified name is a path starting with the namespace.
     * 
     * @return the object's fully qualified name.
     */
    String getName();

    /**
     * The statement that originated the object in k.IM. Will return null in generated object that do not
     * start from source.
     * 
     * @return the k.IM statement that originated this object, or null.
     */
    IKimStatement getStatement();

    /**
     * If the object has child objects of the same kind, these will be returned here. For now only
     * {@link IConceptDefinition} and {@link IObserver} have children.
     * 
     * @return the list of children in order of declaration. Never null.
     */
    List<IKimObject> getChildren();

    /**
     * Return all the annotations attributed to the object in the originating k.IM code.
     * 
     * @return a list of annotations in order of declaration, or null.
     */
    List<IKimAnnotation> getAnnotations();

}
