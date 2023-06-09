package org.integratedmodelling.klab.dataflow;

import org.integratedmodelling.klab.api.data.IResource;

public interface FlowchartProvider {
	
	void createFlowchart(IResource resource, Flowchart.Element element, Flowchart flowchart);
	
}
