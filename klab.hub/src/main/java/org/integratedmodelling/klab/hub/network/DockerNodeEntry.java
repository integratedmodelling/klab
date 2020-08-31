package org.integratedmodelling.klab.hub.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DockerNodeEntry {
	private List<String> entry = new ArrayList<>();

	public List<String> getFromPorperties(Properties properties) {
		entry.add(properties.getProperty(DockerProperties.JAVA_PATH));
		entry.add(properties.getProperty(DockerProperties.JAVA_OPT));
		entry.add(properties.getProperty(DockerProperties.NODE_JAR));
		entry.add(properties.getProperty(DockerProperties.JAVA_VM_MAX));	
		properties.remove(DockerProperties.JAVA_PATH);
		properties.remove(DockerProperties.JAVA_OPT);
		properties.remove(DockerProperties.NODE_JAR);
		properties.remove(DockerProperties.JAVA_VM_MAX);
		properties.forEach((v,k) -> entry.add(v.toString()+"="+k.toString()));
		return entry;
	}

}
