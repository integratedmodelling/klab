package org.integratedmodelling.aligment;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.owl.Ontology;

import com.google.common.collect.Sets;

import uk.ac.ox.krr.logmap2.LogMap2_Matcher;
import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;

/**
 * Provisional API: align functions should return a k.LAB-centric view of the alignment set, while
 * describeAlignments should produce a k.LAB-informative description of it.
 * 
 * @param ontologies
 */
@Component(id = "alignment", version = Version.CURRENT)
public class AlignmentComponent {

    public void align(Collection<IOntology> ontologies) {
        for (Set<IOntology> pair : Sets.combinations(new HashSet<IOntology>(ontologies), 2)) {
            Iterator<IOntology> pit = pair.iterator();
            align(pit.next(), pit.next());
        }
    }

    public void describeAlignments(Collection<IOntology> ontologies, Writer writer) {
        for (Set<IOntology> pair : Sets.combinations(new HashSet<IOntology>(ontologies), 2)) {
            Iterator<IOntology> pit = pair.iterator();
            describeAlignments(pit.next(), pit.next(), writer);
        }
    }

    public void align(IOntology onto1, IOntology onto2) {
    }

    /**
     * Provisional API: should return a k.LAB-centric view of the mapping set
     * 
     * @param ontologies
     */
    public void describeAlignments(IOntology onto1, IOntology onto2, Writer writer) {

        LogMap2_Matcher logmap2 = new LogMap2_Matcher(((Ontology) onto1).getOWLOntology(),
                ((Ontology) onto2).getOWLOntology());

        // Set of mappings computed my LogMap
        Set<MappingObjectStr> logmap2_mappings = logmap2.getLogmap2_Mappings();

        try {
            writer.append("Number of mappings computed by LogMap: " + logmap2_mappings.size() + "\n");

            //      PrintWriter OutAlign = new PrintWriter(new FileWriter("C:\\ont\\OutAlign.txt"));

            for (MappingObjectStr mapping : logmap2_mappings) {

                writer.append("Mapping: " + "\n");
                writer.append("\t" + mapping.getIRIStrEnt1() + "\n");
                writer.append("\t" + mapping.getIRIStrEnt2() + "\n");
                writer.append("\t" + mapping.getConfidence() + "\n");

                // MappingObjectStr.EQ or MappingObjectStr.SUB or MappingObjectStr.SUP
                writer.append("\t" + mapping.getMappingDirection() + "\n"); // Utilities.EQ;

                // MappingObjectStr.CLASSES or MappingObjectStr.OBJECTPROPERTIES or
                // MappingObjectStr.DATAPROPERTIES or MappingObjectStr.INSTANCES
                writer.append("\t" + mapping.getTypeOfMapping() + "\n");

            }
        } catch (IOException e) {
            throw new KlabIOException(e);
        }

    }

}
