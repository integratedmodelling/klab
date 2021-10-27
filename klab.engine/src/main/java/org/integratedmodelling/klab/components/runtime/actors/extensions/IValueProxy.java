package org.integratedmodelling.klab.components.runtime.actors.extensions;

/**
 * If an object constructed through a k.Actors constructor extends this
 * interface, the getValue() return value will be used instead of the object
 * after construction.
 * 
 * @author Ferd
 *
 */
public interface IValueProxy {

	Object getValue();

}
