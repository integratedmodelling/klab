package org.integratedmodelling.klab.documentation;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.dataflow.Flowchart;

public enum DataflowDocumentation {
	
	INSTANCE;
	
	Map<String, String> templates = new HashMap<>();
	
	public String getDocumentation(Flowchart.Element element) {
		return null;
	}

	public void addTemplate(URL url) {
		// TODO Auto-generated method stub
		
	}
	
}
