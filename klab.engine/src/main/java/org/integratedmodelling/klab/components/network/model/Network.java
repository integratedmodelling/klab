package org.integratedmodelling.klab.components.network.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.Triple;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.csv.CSVExporter;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.gexf.GEXFExporter;
import org.jgrapht.nio.gml.GmlExporter;
import org.jgrapht.nio.graphml.GraphMLExporter;
import org.jgrapht.nio.json.JSONExporter;
import org.jgrapht.nio.lemon.LemonExporter;

public class Network extends Pattern implements INetwork {

	/*
	 * export functions use the adapter interface; we redirect to the network
	 * passing this name in REST calls.
	 */
	public static final String ADAPTER_ID = "NETWORK_ADAPTER";

	Graph<IDirectObservation, IRelationship> network = new DefaultDirectedGraph<>(IRelationship.class);

	public Network(Collection<IObservation> observations, IRuntimeScope scope) {

		super(observations, scope);
		IDirectObservation source=null;
		IDirectObservation target=null;
		for (IObservation observation : observations) {
			for (IArtifact artifact : observation) {
				if (artifact instanceof IRelationship) {
					source = scope.getSourceSubject((IRelationship) artifact);
					target = scope.getTargetSubject((IRelationship) artifact);
					network.addVertex(source); network.addVertex(target);
					network.addEdge(source, target, (IRelationship) artifact);
				}
			}
		}
		
		String namePattern = scope.getPattern().getName();
		String nameSource = source.getName();
		String nameTarget = target.getName();
		export("json","~/.klab/export/network_pattern_"+namePattern+"_source_"+nameSource+"_target_"+nameTarget+".json");
	}
	

	@Override
	public void export(String format, OutputStream output) {

		// Define vertex and edges attribute providers. 
		Function<IDirectObservation, Map<String, Attribute>> vertexAttributeProvider = v -> {
		    Map<String, Attribute> map = new LinkedHashMap<>();
		    double[] xy = v.getSpace().getShape().getCenter(true);
		    map.put("latitude", DefaultAttribute.createAttribute(xy[1]));
		    map.put("longitude", DefaultAttribute.createAttribute(xy[0]));
		    return map;
		};
		
		Function<IRelationship, Map<String, Attribute>> edgeAttributeProvider = e -> {
		    Map<String, Attribute> map = new LinkedHashMap<>();
		    String time = e.getScale().getTime().getStart().toRFC3339String();
		    map.put("time", DefaultAttribute.createAttribute(time));
		    return map;
		};
		
		Writer writer = new OutputStreamWriter(output);
		
		switch (format) {
		case "json":
			JSONExporter<IDirectObservation, IRelationship> json = new JSONExporter<>();
			json.setVertexAttributeProvider(vertexAttributeProvider);
			json.setEdgeAttributeProvider(edgeAttributeProvider);
			json.exportGraph(network, writer);
			break;
		case "gexf":
			GEXFExporter<IDirectObservation, IRelationship> gexf = new GEXFExporter<>();
			gexf.setCreator("k.LAB " + Version.CURRENT);
			gexf.setDescription(emergentObservation.getMetadata().get(IMetadata.DC_COMMENT, "GEXF network export"));
			gexf.setVertexAttributeProvider(vertexAttributeProvider);
			gexf.setEdgeAttributeProvider(edgeAttributeProvider);
			gexf.exportGraph(network, writer);
			break;
		case "graphml":
			GraphMLExporter<IDirectObservation, IRelationship> graphml = new GraphMLExporter<>();
			graphml.setVertexAttributeProvider(vertexAttributeProvider);
			graphml.setEdgeAttributeProvider(edgeAttributeProvider);
			graphml.exportGraph(network, writer);
			break;
		case "csv":
			CSVExporter<IDirectObservation, IRelationship> csv = new CSVExporter<>();
			csv.setVertexAttributeProvider(vertexAttributeProvider);
			csv.setEdgeAttributeProvider(edgeAttributeProvider);
			csv.exportGraph(network, writer);
			break;
		case "dot":
			DOTExporter<IDirectObservation, IRelationship> dot = new DOTExporter<>();
			dot.setVertexAttributeProvider(vertexAttributeProvider);
			dot.setEdgeAttributeProvider(edgeAttributeProvider);
			dot.exportGraph(network, writer);
			break;
		case "gml":
			GmlExporter<IDirectObservation, IRelationship> gml = new GmlExporter<>();
			gml.setVertexAttributeProvider(vertexAttributeProvider);
			gml.setEdgeAttributeProvider(edgeAttributeProvider);
			gml.exportGraph(network, writer);
			break;
		case "lemon":
			LemonExporter<IDirectObservation, IRelationship> lemon = new LemonExporter<>();
			lemon.setVertexAttributeProvider(vertexAttributeProvider);
			lemon.setEdgeAttributeProvider(edgeAttributeProvider);
			lemon.exportGraph(network, writer);
			break;
		default:
			JSONExporter<IDirectObservation, IRelationship> def = new JSONExporter<>();
			def.setVertexAttributeProvider(vertexAttributeProvider);
			def.setEdgeAttributeProvider(edgeAttributeProvider);
			def.exportGraph(network, writer);
			throw new KlabIOException("Solicited graph export format is not supported or does not exist. Exporting in JSON format instead.");
		}
	}

	
	public void export(String format, String filename){ 
		try { 
			OutputStream out = new FileOutputStream( new File(filename) ); 
			export(format, out); 
		} catch (FileNotFoundException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
			 
	} 
	
	@Override
	public Collection<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
		List<Triple<String, String, String>> ret = new ArrayList<>();
		// TODO others, more for code-compatible formats and possibly spatial networks
		ret.add(new Triple<>("gexf", "GEXF 1.2 network (dynamic)", "gexf"));
		ret.add(new Triple<>("graphml", "GraphML 1.0 graph (static)", "gml"));
		ret.add(new Triple<>("json", "JSON (dynamic)", "json"));
		return ret;
	}

	@Override
	public void update(IObservation trigger) {
		// NAH if the emergent is a subject, this can't work, we need to rebuild the
		// connected components
	}

}
