package org.integratedmodelling.ecology;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;

/**
 * Do-nothing example of a component declaration. Use the
 * {@link org.integratedmodelling.api.plugin.Component} annotation to declare
 * it. It is mandatory to provide the class with at least the component ID and
 * the version, although it doesn't need to have any methods.
 *
 * This class does not provide any services: all the component API is
 * implemented using contextualizers tagged with the appropriate @Prototype
 * annotation. These should be in the same package or a subpackage of the
 * component's. Each prototype will turn into a function in the client, calling
 * the local code if local or a remote service if made available from a
 * networked node.
 *
 * @author ferdinando.villa
 *
 */
@Component(id = "im.ecology", version = Version.CURRENT)
public class EcologyComponent {

}
