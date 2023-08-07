package org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm;

import org.jgrapht.Graph;

// we may not need this class

public class Algorithm<V, E> {

	protected Class<? extends E> edgeClass;
	
	public Algorithm(Class<? extends E> cls) {// TODO Auto-generated constructor stub
		this.edgeClass = cls;
	}

	protected Graph<V, E> graph = null;
	protected String _Name;

	public Graph<V, E> getGraph() {
		return graph;
	}

	public void setGraph(Graph<V, E> graph) {
		this.graph = graph;
	}

	public String getName() {
		return _Name;
	}

	public void setName(String name) {
		_Name = name;
	}

	// /*! Execute the algorithm
	// */
	// public abstract Graph<V,E> execute();
	// /*! execute the algorithm with beliefNetwork G
	// * \param[in] G
	// */
	// public Graph<V,E> execute(Graph<V,E> g)
	// {
	// setGraph(g);
	// return execute();
	// }

}
