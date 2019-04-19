package org.integratedmodelling.klab.components.network.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.utils.Triple;

import com.google.common.collect.Lists;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.GraphMLWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.Edge;
import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.Attribute;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.dynamic.Spell;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.SpellImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;

public class Network implements INetwork {

    /*
     * export functions use the adapter interface; we redirect to the network
     * passing this name in REST calls.
     */
    public static final String                                  ADAPTER_ID = "NETWORK_ADAPTER";

    DirectedSparseMultigraph<IDirectObservation, IRelationship> network    = new DirectedSparseMultigraph<>();
    IConfiguration                                              configuration;

    /**
     * True if the passed configuration is a network, based on the presence of
     * relationships.
     * 
     * @param configuration
     * @return
     */
    public static boolean isNetwork(IConfiguration configuration) {
        boolean ret = true;
        for (IObservation obs : configuration.getTargetObservations()) {
            if (obs.getObservable().is(Type.RELATIONSHIP)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    public Network(IConfiguration configuration) {

        this.configuration = configuration;

        /*
         * Build network
         */
        for (IObservation observation : configuration.getTargetObservations()) {
            IRuntimeContext context = ((Observation) observation).getRuntimeContext();
            for (IArtifact artifact : observation) {
                if (artifact instanceof IRelationship) {
                    IDirectObservation source = context.getSourceSubject((IRelationship) artifact);
                    IDirectObservation target = context.getTargetSubject((IRelationship) artifact);
                    network.addEdge((IRelationship) artifact, Lists
                            .newArrayList(source, target), ((IRelationship) artifact).getObservable()
                                    .is(Type.UNIDIRECTIONAL) ? EdgeType.DIRECTED
                                            : EdgeType.UNDIRECTED);
                }
            }
        }

        ((Artifact) configuration).addPeer(this, INetwork.class);
    }

    @Override
    public void export(String format, OutputStream output) {

        Writer writer = new OutputStreamWriter(output);

        switch (format) {
        case "json":
            exportJson(writer);
            break;
        case "gexf":
            exportGefx(writer);
            break;
        case "graphml":
            GraphMLWriter<IDirectObservation, IRelationship> graphml = new GraphMLWriter<>();
            // TODO set handlers for labels etc
            try {
                graphml.save(network, writer);
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
            break;
        }

    }

    private void exportJson(Writer output) {

        for (IDirectObservation o : network.getVertices()) {

        }

        for (IRelationship r : network.getEdges()) {

        }

    }

    private void exportGefx(Writer output) {

        Gexf gexf = new GexfImpl();
        Graph graph = gexf.getGraph();
        AttributeList nodeAttributes = new AttributeListImpl(AttributeClass.NODE);
        graph.getAttributeLists().add(nodeAttributes);
        Attribute latitude = null;
        Attribute longitude = null;

        gexf.getMetadata()
                .setLastModified(new Date())
                .setCreator("k.LAB " + Version.CURRENT)
                .setDescription(configuration.getMetadata().get(IMetadata.DC_COMMENT, "GEXF network export"));

        boolean graphSet = false;

        Map<String, Node> nodes = new HashMap<>();

        for (IDirectObservation o : network.getVertices()) {

            Node node = graph.createNode(o.getId());
            node.setLabel(o.getName());

            /*
             * preserve spatial arrangement as attributes if it makes sense to do so.
             */
            if (o.getSpace() != null) {

                if (!graphSet) {
                    latitude = nodeAttributes.createAttribute("latitude", AttributeType.FLOAT, "latitude");
                    longitude = nodeAttributes.createAttribute("longitude", AttributeType.FLOAT, "longitude");
                    graphSet = true;
                }

                double[] xy = o.getSpace().getShape().getCenter(true);
                node.getAttributeValues().addValue(longitude, "" + xy[0]).addValue(latitude, "" + xy[1]);
            }

            /*
             * temporal attributes
             */
            if (configuration.isTemporallyDistributed()) {
                Spell spell = new SpellImpl();
                spell.setStartValue(new Date(o.getTimestamp()));
                if (o.getExitTime() > 0) {
                    spell.setEndValue(new Date(o.getExitTime()));
                }
                node.getSpells().add(spell);
            }

            /*
             * TODO attributes
             * AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
            graph.getAttributeLists().add(attrList);
            
            Attribute attUrl = attrList.createAttribute("0", AttributeType.STRING, "url");
            Attribute attIndegree = attrList.createAttribute("1", AttributeType.FLOAT, "indegree");
            Attribute attFrog = attrList.createAttribute("2", AttributeType.BOOLEAN, "frog")
            .setDefaultValue("true");
            
            // for each node:
            gephi.getAttributeValues()
                .addValue(attUrl, "http://gephi.org")
                .addValue(attIndegree, "1");
             */

            nodes.put(o.getId(), node);

        }

        for (IRelationship r : network.getEdges()) {

            Node source = nodes.get(r.getSource().getId());
            Node target = nodes.get(r.getTarget().getId());
            Edge edge = source.connectTo(target);

            /*
             * temporal attributes
             */
            if (configuration.isTemporallyDistributed()) {
                Spell spell = new SpellImpl();
                spell.setStartValue(new Date(r.getCreationTime()));
                if (r.getExitTime() > 0) {
                    spell.setEndValue(new Date(r.getExitTime()));
                }
                edge.getSpells().add(spell);
            }

            /*
             * TODO edge attributes
             */
            edge.setLabel(r.getName());

        }

        StaxGraphWriter graphWriter = new StaxGraphWriter();
        try {
            graphWriter.writeToStream(gexf, output, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Collection<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
        List<Triple<String, String, String>> ret = new ArrayList<>();
        // TODO others, more for code-compatible formats and possibly spatial networks
        ret.add(new Triple<>("gexf", "GEXF 1.2 network (dynamic)", "gexf"));
        ret.add(new Triple<>("graphml", "GraphML 1.0 graph (static)", "gml"));
        return ret;
    }

}
