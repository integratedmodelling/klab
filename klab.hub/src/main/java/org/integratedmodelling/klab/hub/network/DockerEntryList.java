package org.integratedmodelling.klab.hub.network;

import java.util.List;

public class DockerEntryList {
	
   public List<String> getFromConfig(DockerConfiguration config) {
	      if(config.getClass() == DockerNode.class){
	    	  return new DockerNodeEntry().getFromPorperties(config.getProperties());
	      }
	      return null;
	   }

}
