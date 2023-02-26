/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.services.reasoner.owl;

import java.util.HashMap;

import org.integratedmodelling.klab.api.data.KMetadata;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.services.reasoner.internal.CoreOntology;
import org.integratedmodelling.klab.services.reasoner.internal.CoreOntology.NS;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Implements metadata for ontologies, concepts and properties by providing an interface to
 * annotation properties of an OWL entity and exposing them with their fully qualified property IDs
 * as keys.
 * 
 * @author Ferd
 */
public class OWLMetadata extends Metadata implements KMetadata {

    // OWLEntity _owl;
    // OWLOntology _ontology;

    static HashMap<String, String> _metadataVocabulary = new HashMap<>();

    static {
        _metadataVocabulary.put(OWLRDFVocabulary.RDFS_LABEL.getIRI().toString(), Vocabulary.DC_LABEL);
        _metadataVocabulary.put(OWLRDFVocabulary.RDFS_COMMENT.getIRI().toString(), Vocabulary.DC_COMMENT);
        _metadataVocabulary.put("http://protege.stanford.edu/plugins/owl/dc/protege-dc.owl#label", Vocabulary.DC_LABEL);
        _metadataVocabulary.put("http://protege.stanford.edu/plugins/owl/dc/protege-dc.owl#comment", Vocabulary.DC_COMMENT);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#baseDeclaration", CoreOntology.NS.BASE_DECLARATION);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#isAbstract", NS.IS_ABSTRACT);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#unit", NS.SI_UNIT_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#isDeniable", NS.DENIABILITY_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#orderingRank", NS.ORDER_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#conceptDefinition", NS.CONCEPT_DEFINITION_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#coreObservable", NS.CORE_OBSERVABLE_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#displayLabel", NS.DISPLAY_LABEL_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#originalTrait", NS.ORIGINAL_TRAIT);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#localAlias", NS.LOCAL_ALIAS_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#referenceName", NS.REFERENCE_NAME_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#authorityId", NS.AUTHORITY_ID_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#restrictingProperty", NS.TRAIT_RESTRICTING_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#isTypeDelegate", NS.IS_TYPE_DELEGATE);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/klab.owl#untransformedConceptId",
                NS.UNTRANSFORMED_CONCEPT_PROPERTY);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#isSubjectiveTrait", NS.IS_SUBJECTIVE);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#inherencyIsDistributed",
                NS.INHERENCY_IS_DISTRIBUTED);
        _metadataVocabulary.put("http://integratedmodelling.org/ks/observation.owl#isCoreKimType", NS.IS_CORE_KIM_TYPE);
    }

    public OWLMetadata(OWLEntity owl, OWLOntology ontology) {
        for (OWLAnnotation zio : owl.getAnnotations(ontology)) {
            OWLAnnotationValue val = zio.getValue();
            String piri = zio.getProperty().getIRI().toString();
            String prop = _metadataVocabulary.get(piri);
            if (prop != null) {
                this.put(prop, literal2obj(val));
            }
        }
    }

    /*
     * mah
     */
    public static Object literal2obj(OWLAnnotationValue l) {

        if (l instanceof OWLLiteral) {
            if (((OWLLiteral) l).isBoolean())
                return ((OWLLiteral) l).parseBoolean();
            else if (((OWLLiteral) l).isInteger())
                return ((OWLLiteral) l).parseInteger();
            else if (((OWLLiteral) l).isFloat())
                return ((OWLLiteral) l).parseFloat();
            else if (((OWLLiteral) l).isDouble())
                return ((OWLLiteral) l).parseDouble();
            else {
                return ((OWLLiteral) l).getLiteral();
            }
        } else if (l instanceof IRI) {
            return ((IRI) l).toURI().toString();
        }
        return l.toString();
    }

}
