package org.integratedmodelling.klab;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.IOntologyService;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Ontology;

public enum Ontologies implements IOntologyService {
	
    INSTANCE;

    private Ontologies() {
        Services.INSTANCE.registerService(this, IOntologyService.class);
    }

    @Override
    public Ontology require(String name) {
        return OWL.INSTANCE.requireOntology(name, OWL.INTERNAL_ONTOLOGY_PREFIX);
    }

    @Override
    public void release(IOntology ontology) {
        OWL.INSTANCE.releaseOntology(ontology);
    }

    /**
     * Given a collection of namespace-specified knowledge and/or collections
     * thereof, return the ontology of a namespace that sits on top of all others in
     * the asserted dependency chain and imports all concepts. If that is not
     * possible, return the "fallback" ontology which must already import all the
     * concepts (typically the one where the targets are used in a declaration). 
     * Used to establish where to put derived concepts and restrictions so as to 
     * avoid circular dependencies in the underlying OWL model while minimizing
     * redundant concepts.
     * 
     * @param targets
     * @return
     */
    public Ontology getTargetOntology(Ontology fallback, Object... targets) {

        Set<String> graph = new HashSet<>();
        if (targets != null) {
            for (Object o : targets) {
                if (o instanceof IKnowledge) {
                    String nsid = ((IKnowledge) o).getNamespace();
                    if (Namespaces.INSTANCE.getNamespace(nsid) != null) {
                        graph.add(nsid);
                    }
                } else if (o instanceof Iterable) {
                    for (Object u : (Iterable<?>) o) {
                        if (u instanceof IKnowledge) {
                            String nsid = ((IKnowledge) u).getNamespace();
                            if (Namespaces.INSTANCE.getNamespace(nsid) != null) {
                                graph.add(nsid);
                            }
                        }
                    }
                }
            }
        }

        String namespace = null;
        Set<String> os = new HashSet<>(graph);
        for (String a : graph) {
            if (namespace == null) {
                namespace = a;
            }
            for (String b : os) {
                Namespace ns = Namespaces.INSTANCE.getNamespace(b);
                if (!b.equals(a) && ns.getStatement().getImportedIds().contains(a)) {
                    namespace = b;
                }
            }
        }

        /*
         * candidate namespace must already import all the others. If not, choose the
         * fallback ontology which must be guaranteed to contain all the imports already.
         */
        boolean transitive = true;
        INamespace ns = Namespaces.INSTANCE.getNamespace(namespace);
        for (String s : graph) {
            if (!s.equals(ns.getName()) && !ns.getStatement().getImportedIds().contains(s)) {
                transitive = false;
                break;
            }
        }
        return (Ontology) (transitive && ns != null ? ns.getOntology() : fallback);
    }
}
