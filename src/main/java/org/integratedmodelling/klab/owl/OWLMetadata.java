/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.owl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Implements metadata for ontologies, concepts and properties by providing an interface
 * to annotation properties of an OWL entity and exposing them with their fully qualified
 * property IDs as keys.
 * 
 * @author Ferd
 */
public class OWLMetadata implements IMetadata {

    private static final long serialVersionUID = 8596779276530421122L;

    OWLEntity                      _owl;
    OWLOntology                    _ontology;
    HashMap<String, Object>        __data;

    static HashMap<String, String> _metadataVocabulary = new HashMap<>();

    static {
        _metadataVocabulary
                .put(OWLRDFVocabulary.RDFS_LABEL.getIRI().toString(), IMetadata.DC_LABEL);
        _metadataVocabulary.put(OWLRDFVocabulary.RDFS_COMMENT.getIRI()
                .toString(), IMetadata.DC_COMMENT);
        _metadataVocabulary
                .put("http://protege.stanford.edu/plugins/owl/dc/protege-dc.owl#label", IMetadata.DC_LABEL);
        _metadataVocabulary
                .put("http://protege.stanford.edu/plugins/owl/dc/protege-dc.owl#comment", IMetadata.DC_COMMENT);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/observation.owl#baseDeclaration", NS.BASE_DECLARATION);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/observation.owl#isAbstract", NS.IS_ABSTRACT);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/observation.owl#unit", NS.SI_UNIT_PROPERTY);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/observation.owl#isDeniable", NS.DENIABILITY_PROPERTY);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/observation.owl#orderingRank",  NS.ORDER_PROPERTY);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/klab.owl#conceptDefinition",  NS.CONCEPT_DEFINITION_PROPERTY);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/klab.owl#displayLabel",  NS.DISPLAY_LABEL_PROPERTY);
        _metadataVocabulary
            .put("http://integratedmodelling.org/ks/klab.owl#originalTrait", NS.ORIGINAL_TRAIT);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/klab.owl#localAlias", NS.LOCAL_ALIAS_PROPERTY);
        _metadataVocabulary
            .put("http://integratedmodelling.org/ks/klab.owl#authorityId", NS.AUTHORITY_ID_PROPERTY);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/klab.owl#restrictingProperty", NS.TRAIT_RESTRICTING_PROPERTY);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/observation.owl#isTypeDelegate", NS.IS_TYPE_DELEGATE);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/klab.owl#untransformedConceptId", NS.UNTRANSFORMED_CONCEPT_PROPERTY);
        _metadataVocabulary
                .put("http://integratedmodelling.org/ks/observation.owl#isSubjectiveTrait", NS.IS_SUBJECTIVE);    }

    public OWLMetadata(OWLEntity owl, OWLOntology ontology) {
        _owl = owl;
        _ontology = ontology;
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
                return ((OWLLiteral)l).getLiteral();
            }
        } else if (l instanceof IRI) {
            return ((IRI)l).toURI().toString();
        } 
        return l.toString();
    }

    private Map<String, Object> getData() {

        if (__data == null) {
            __data = new HashMap<>();

            for (OWLAnnotation zio : _owl.getAnnotations(_ontology)) {
                OWLAnnotationValue val = zio.getValue();
                String piri = zio.getProperty().getIRI().toString();
                String prop = _metadataVocabulary.get(piri);
                if (prop != null) {
                    __data.put(prop, literal2obj(val));
                }
            }
        }
        return __data;
    }

    @Override
    public Object get(String string) {
        return getData().get(string);
    }

    @Override
    public Collection<String> getKeys() {
        return getData().keySet();
    }

    @Override
    public void merge(IMetadata md, boolean overwriteExisting) {
    }

    @Override
    public String getString(String field) {
        Object o = get(field);
        return o != null ? o.toString() : null;
    }

    @Override
    public Integer getInt(String field) {
        Object o = get(field);
        if (o instanceof String) {
            try {
                o = Integer.parseInt(o.toString());
            } catch (Throwable e) {
                return null;
            }
        }
        return o != null && o instanceof Number ? ((Number) o).intValue() : null;
    }

    @Override
    public Long getLong(String field) {
        Object o = get(field);
        return o != null && o instanceof Number ? ((Number) o).longValue() : null;
    }

    @Override
    public Double getDouble(String field) {
        Object o = get(field);
        return o != null && o instanceof Number ? ((Number) o).doubleValue() : null;
    }

    @Override
    public Float getFloat(String field) {
        Object o = get(field);
        return o != null && o instanceof Number ? ((Number) o).floatValue() : null;
    }

    @Override
    public Boolean getBoolean(String field) {
        Object o = get(field);
        return o != null && o instanceof Boolean ? (Boolean) o : null;
    }

    @Override
    public IConcept getConcept(String field) {
        Object o = get(field);
        return o != null && o instanceof IConcept ? (IConcept) o : null;
    }

    @Override
    public String getString(String field, String def) {
        Object o = get(field);
        return o != null ? o.toString() : def;
    }

    @Override
    public int getInt(String field, int def) {
        Object o = get(field);
        if (o instanceof String) {
            try {
                o = Integer.parseInt(o.toString());
            } catch (Throwable e) {
                return def;
            }
        }
        return o != null && o instanceof Number ? ((Number) o).intValue() : def;
    }

    @Override
    public long getLong(String field, long def) {
        Object o = get(field);
        return o != null && o instanceof Number ? ((Number) o).longValue() : def;
    }

    @Override
    public double getDouble(String field, double def) {
        Object o = get(field);
        return o != null && o instanceof Number ? ((Number) o).doubleValue() : def;
    }

    @Override
    public float getFloat(String field, float def) {
        Object o = get(field);
        return o != null && o instanceof Number ? ((Number) o).floatValue() : def;
    }

    @Override
    public boolean getBoolean(String field, boolean def) {
        Object o = get(field);
        return o != null && o instanceof Boolean ? (Boolean) o : def;
    }

    @Override
    public IConcept getConcept(String field, IConcept def) {
        Object o = get(field);
        return o != null && o instanceof IConcept ? (IConcept) o : def;
    }

    @Override
    public Collection<Object> getValues() {
        return getData().values();
    }

    @Override
    public void put(String key, Object value) {
        __data.put(key, value);
    }

    @Override
    public void remove(String key) {
        __data.remove(key);
    }

    @Override
    public Map<? extends String, ? extends Object> getDataAsMap() {
        return __data;
    }

    @Override
    public boolean contains(String key) {
        return __data == null ? false : __data.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return __data == null || __data.isEmpty();
    }

}
