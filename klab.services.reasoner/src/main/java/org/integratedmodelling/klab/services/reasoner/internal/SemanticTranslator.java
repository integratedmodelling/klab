package org.integratedmodelling.klab.services.reasoner.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.codehaus.groovy.transform.trait.Traits;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.engine.IEngineService.Reasoner;
import org.integratedmodelling.klab.api.exceptions.KValidationException;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement;
import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement.ParentConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement.ApplicableConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;
import org.integratedmodelling.klab.api.lang.kim.KKimScope;
import org.integratedmodelling.klab.api.lang.kim.impl.KimConceptStatement;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.runtime.KChannel;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.services.reasoner.internal.CoreOntology.NS;
import org.integratedmodelling.klab.services.reasoner.owl.Axiom;
import org.integratedmodelling.klab.services.reasoner.owl.Concept;
import org.integratedmodelling.klab.services.reasoner.owl.OWL;
import org.integratedmodelling.klab.services.reasoner.owl.Ontology;
import org.integratedmodelling.klab.services.reasoner.owl.Property;
import org.integratedmodelling.klab.services.reasoner.owl.adapter.ErrorNotifyingMonitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.stereotype.Service;

@Service
public class SemanticTranslator {

    /*
     * the back-end implementation of each concept, linked to the ID in the concept class.
     */
    private Map<Long, OWLClass> owlClasses = new HashMap<>();

    public KConcept defineConcept(KKimConcept parsed) {
        // TODO Auto-generated method stub
        return null;
    }

    public KObservable defineObservable(KKimObservable parsed) {
        // TODO Auto-generated method stub
        return null;
    }

    public KConcept defineConcept(KKimConceptStatement statement) {
        // TODO
        return null;
    }

    public KConcept build(KKimConceptStatement concept, Ontology ontology, KKimConceptStatement kimObject, KChannel monitor) {

        if (concept.isMacro()) {
            return null;
        }

        try {

            if (concept.isAlias()) {

                /*
                 * can only have 'is' X or 'equals' X
                 */
                KConcept parent = null;
                if (concept.getUpperConceptDefined() != null) {
                    parent = OWL.INSTANCE.getConcept(concept.getUpperConceptDefined());
                    if (parent == null) {
                        monitor.error("Core concept " + concept.getUpperConceptDefined() + " is unknown", concept);
                    } else {
                        ((Concept) parent).getTypeSet().addAll(concept.getType());
                    }
                } else {

                    List<KConcept> concepts = new ArrayList<>();
                    int i = 0;
                    for (ParentConcept p : concept.getParents()) {

                        if (i > 0) {
                            monitor.error("concepts defining aliases with 'equals' cannot have more than one parent", p);
                        }

                        for (KKimConcept pdecl : p.getConcepts()) {
                            KConcept declared = declare(pdecl, ontology, monitor);
                            if (declared == null) {
                                monitor.error("parent declaration " + pdecl + " does not identify known concepts", pdecl);
                                return null;
                            }
                            concepts.add(declared);
                        }
                        i++;
                    }

                    if (concepts.size() == 1) {
                        parent = concepts.get(0);
                    }
                }

                if (parent != null) {
                    ontology.addDelegateConcept(concept.getName(), ontology.getName(), (Concept) parent);
                }

                return null;
            }

            KConcept ret = buildInternal(concept, ontology, kimObject, monitor);
            KConcept upperConceptDefined = null;
            if (concept.getParents().isEmpty()) {
                KConcept parent = null;
                if (concept.getUpperConceptDefined() != null) {
                    upperConceptDefined = parent = Concepts.INSTANCE.getConcept(concept.getUpperConceptDefined());
                    if (parent == null) {
                        monitor.error("Core concept " + concept.getUpperConceptDefined() + " is unknown", concept);
                    }
                } else {
                    parent = Resources.INSTANCE.getUpperOntology().getCoreType(concept.getType());
                    if (coreConceptPeers.containsKey(ret.toString())) {
                        // ensure that any non-trivial core inheritance is dealt with appropriately
                        parent = Resources.INSTANCE.getUpperOntology().alignCoreInheritance(ret);
                    }
                }

                if (parent != null) {
                    ontology.add(Axiom.SubClass(parent.getUrn(), ret.getName()));
                }
            }

            if (ret != null) {
                ontology.addAxiom(Axiom.AnnotationAssertion(ret.getName(), NS.BASE_DECLARATION, "true"));
                createProperties(ret, ontology);
                ontology.define();

                if (coreConceptPeers.containsKey(ret.toString()) && upperConceptDefined != null
                        && "true".equals(upperConceptDefined.getMetadata().get(NS.IS_CORE_KIM_TYPE, "false"))) {
                    Resources.INSTANCE.getUpperOntology().setAsCoreType(ret);
                }

            }

            return ret;

        } catch (Throwable e) {
            monitor.error(e, concept);
        }
        return null;
    }

    private Concept buildInternal(final KKimConceptStatement concept, final Ontology ontology, KKimConceptStatement kimObject,
            final KChannel monitor) {

        Concept main = null;
        String mainId = concept.getName();

        ontology.add(Axiom.ClassAssertion(mainId,
                concept.getType().stream().map((c) -> SemanticType.valueOf(c.name())).collect(Collectors.toSet())));

        // set the k.IM definition
        ontology.add(
                Axiom.AnnotationAssertion(mainId, NS.CONCEPT_DEFINITION_PROPERTY, ontology.getName() + ":" + concept.getName()));

        // and the reference name
        ontology.add(Axiom.AnnotationAssertion(mainId, NS.REFERENCE_NAME_PROPERTY,
                getCleanFullId(ontology.getName(), concept.getName())));

        /*
         * basic attributes subjective deniable internal uni/bidirectional (relationship)
         */
        if (concept.isAbstract()) {
            ontology.add(Axiom.AnnotationAssertion(mainId, CoreOntology.NS.IS_ABSTRACT, "true"));
        }

        ontology.define();
        main = ontology.getConcept(mainId);

        for (ParentConcept parent : concept.getParents()) {

            List<KConcept> concepts = new ArrayList<>();
            for (KKimConcept pdecl : parent.getConcepts()) {
                KConcept declared = declare(pdecl, ontology, monitor);
                if (declared == null) {
                    monitor.error("parent declaration " + pdecl + " does not identify known concepts", pdecl);
                    return null;
                }
                concepts.add(declared);
            }

            if (concepts.size() == 1) {
                ontology.add(Axiom.SubClass(concepts.get(0).getUrn(), mainId));
            } else {
                KConcept expr = null;
                switch(parent.getConnector()) {
                case INTERSECTION:
                    expr = OWL.INSTANCE.getIntersection(concepts, ontology, ((Concept) concepts.get(0)).getTypeSet());
                    break;
                case UNION:
                    expr = OWL.INSTANCE.getUnion(concepts, ontology, ((Concept) concepts.get(0)).getTypeSet());
                    break;
                case FOLLOWS:
                    expr = OWL.INSTANCE.getConsequentialityEvent(concepts, ontology);
                    break;
                default:
                    // won't happen
                    break;
                }
                if (concept.isAlias()) {
                    ontology.addDelegateConcept(mainId, ontology, (Concept) expr);
                } else {
                    ontology.add(Axiom.SubClass(expr.getUrn(), mainId));
                }
            }
            ontology.define();
        }

        for (KKimScope child : concept.getChildren()) {
            if (child instanceof KKimConceptStatement) {
                try {
                    KimConceptStatement chobj = kimObject == null ? null : new KimConceptStatement((IKimConceptStatement) child);
                    KConcept childConcept = buildInternal((IKimConceptStatement) child, ontology, chobj,
                            monitor instanceof ErrorNotifyingMonitor
                                    ? ((ErrorNotifyingMonitor) monitor).contextualize(child)
                                    : monitor);
                    ontology.add(Axiom.SubClass(mainId, childConcept.getName()));
                    ontology.define();
                    kimObject.getChildren().add(chobj);
                } catch (Throwable e) {
                    monitor.error(e, child);
                }
            }
        }

        for (KKimConcept inherited : concept.getTraitsInherited()) {
            KConcept trait = declare(inherited, ontology, monitor);
            if (trait == null) {
                monitor.error("inherited " + inherited.getName() + " does not identify known concepts", inherited);
                return null;
            }
            try {
                Traits.INSTANCE.addTrait(main, trait, ontology);
            } catch (KlabValidationException e) {
                monitor.error(e, inherited);
            }
        }

        // TODO all the rest: creates, ....
        for (KKimConcept affected : concept.getQualitiesAffected()) {
            KConcept quality = declare(affected, ontology, monitor);
            if (quality == null) {
                monitor.error("affected " + affected.getName() + " does not identify known concepts", affected);
                return null;
            }
            OWL.INSTANCE.restrictSome(main, Concepts.p(NS.AFFECTS_PROPERTY), quality, namespace.getOntology());
        }

        for (KKimConcept required : concept.getRequiredIdentities()) {
            KConcept quality = declare(required, ontology, monitor);
            if (quality == null) {
                monitor.error("required " + required.getName() + " does not identify known concepts", required);
                return null;
            }
            OWL.INSTANCE.restrictSome(main, Concepts.p(NS.REQUIRES_IDENTITY_PROPERTY), quality, ontology);
        }

        for (KKimConcept affected : concept.getObservablesCreated()) {
            KConcept quality = declare(affected, ontology, monitor);
            if (quality == null) {
                monitor.error("created " + affected.getName() + " does not identify known concepts", affected);
                return null;
            }
            OWL.INSTANCE.restrictSome(main, Concepts.p(NS.CREATES_PROPERTY), quality, namespace.getOntology());
        }

        for (ApplicableConcept link : concept.getSubjectsLinked()) {
            if (link.getOriginalObservable() == null && link.getSource() != null) {
                // relationship source->target
                Observables.INSTANCE.defineRelationship(main, declare(link.getSource(), ontology, monitor),
                        declare(link.getTarget(), ontology, monitor), ontology);
            } else {
                // TODO
            }
        }

        if (!concept.getEmergenceTriggers().isEmpty()) {
            List<KConcept> triggers = new ArrayList<>();
            for (KKimConcept trigger : concept.getEmergenceTriggers()) {
                triggers.add(declare(trigger, ontology, monitor));
            }
            Reasoner.INSTANCE.registerEmergent(main, triggers);
        }

        if (kimObject != null) {
            kimObject.set(main);
        }

        return main;
    }

    private KConcept declare(KKimConcept affected, Ontology ontology, KChannel monitor) {
        // TODO Auto-generated method stub
        return null;
    }


    public void restrict(KConcept target, Property property, LogicalConnector how, Collection<KConcept> fillers, Ontology ontology)
            throws KValidationException {

        /*
         * divide up in bins according to base trait; take property from annotation;
         * restrict each group.
         */
        Map<KConcept, List<KConcept>> pairs = new HashMap<>();
        for (KConcept t : fillers) {
            KConcept base = getBaseParentTrait(t);
            if (!pairs.containsKey(base)) {
                pairs.put(base, new ArrayList<>());
            }
            pairs.get(base).add(t);
        }

        for (KConcept base : pairs.keySet()) {

            String prop = base.getMetadata().get(NS.TRAIT_RESTRICTING_PROPERTY, String.class);
            if (prop == null || Concepts.INSTANCE.getProperty(prop) == null) {
                if (base.is(SemanticType.SUBJECTIVE)) {
                    /*
                     * we can assign any subjective traits to anything
                     */
                    prop = NS.HAS_SUBJECTIVE_TRAIT_PROPERTY;
                } else {
                    throw new KlabValidationException("cannot find property to restrict for trait " + base);
                }
            }
            // System.out.println("TRAIT " + pairs.get(base) + " for " + target + " with " +
            // prop);
            OWL.INSTANCE.restrictSome(target, Concepts.p(prop), how, pairs.get(base), (Ontology) ontology);
        }
    }
    

    @Override
    public KConcept getBaseParentTrait(KConcept trait) {

        String orig = trait.getMetadata().get(NS.ORIGINAL_TRAIT, String.class);
        if (orig != null) {
            trait = Concepts.c(orig);
        }

        /*
         * there should only be one of these or none.
         */
        if (trait.getMetadata().get(NS.BASE_DECLARATION) != null) {
            return trait;
        }

        for (KConcept c : trait.getParents()) {
            KConcept r = getBaseParentTrait(c);
            if (r != null) {
                return r;
            }
        }
        return null;
    }
    
}
