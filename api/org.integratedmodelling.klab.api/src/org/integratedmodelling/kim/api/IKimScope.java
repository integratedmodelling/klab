package org.integratedmodelling.kim.api;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

public interface IKimScope extends Serializable {

    List<IKimScope> getChildren();

    /**
     * Return a parseable string that describes the location of this code scope.
     * 
     * @return the location
     */
    String getLocationDescriptor();

	URI getURI();
	
}
