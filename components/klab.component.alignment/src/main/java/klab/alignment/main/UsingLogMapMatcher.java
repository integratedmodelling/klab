package klab.alignment.main;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.ox.krr.logmap2.LogMap2_Matcher;
import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;

public class UsingLogMapMatcher {

	OWLOntology onto1;
	OWLOntology onto2;

	OWLOntologyManager onto_manager;

	public UsingLogMapMatcher() {

		try {

			String onto1_iri = "http://purl.obolibrary.org/obo/envo.owl";
			String onto2_iri = "http://www.integratedmodelling.org/downloads/sweet.owl";

			onto_manager = OWLManager.createOWLOntologyManager();

			onto1 = onto_manager.loadOntology(IRI.create(onto1_iri));
			onto2 = onto_manager.loadOntology(IRI.create(onto2_iri));

			LogMap2_Matcher logmap2 = new LogMap2_Matcher(onto1, onto2);
			// Optionally LogMap also accepts the IRI strings as input
			// LogMap2_Matcher logmap2 = new LogMap2_Matcher(onto1_iri, onto2_iri);

			// Set of mappings computed my LogMap
			Set<MappingObjectStr> logmap2_mappings = logmap2.getLogmap2_Mappings();

			System.out.println("Number of mappings computed by LogMap: " + logmap2_mappings.size());

//			PrintWriter OutAlign = new PrintWriter(new FileWriter("C:\\ont\\OutAlign.txt"));

			for (MappingObjectStr mapping : logmap2_mappings) {
				System.out.println("Mapping: ");
				System.out.println("\t" + mapping.getIRIStrEnt1());
				System.out.println("\t" + mapping.getIRIStrEnt2());
				System.out.println("\t" + mapping.getConfidence());

				// MappingObjectStr.EQ or MappingObjectStr.SUB or MappingObjectStr.SUP
				System.out.println("\t" + mapping.getMappingDirection()); // Utilities.EQ;

				// MappingObjectStr.CLASSES or MappingObjectStr.OBJECTPROPERTIES or
				// MappingObjectStr.DATAPROPERTIES or MappingObjectStr.INSTANCES
				System.out.println("\t" + mapping.getTypeOfMapping());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new UsingLogMapMatcher();

	}

}
