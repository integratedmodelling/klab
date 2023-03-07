//package org.integratedmodelling.klab.services.reasoner.owl.adapter;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.codehaus.groovy.transform.trait.Traits;
//import org.integratedmodelling.klab.api.collections.Annotation;
//import org.integratedmodelling.klab.api.knowledge.KConcept;
//import org.integratedmodelling.klab.api.knowledge.SemanticType;
//import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
//import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement;
//import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement.ApplicableConcept;
//import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement.ParentConcept;
//import org.integratedmodelling.klab.api.lang.kim.KKimObservable;
//import org.integratedmodelling.klab.api.lang.kim.KKimScope;
//import org.integratedmodelling.klab.api.lang.kim.impl.KimConceptStatement;
//import org.integratedmodelling.klab.api.services.runtime.KChannel;
//import org.integratedmodelling.klab.configuration.Services;
//import org.integratedmodelling.klab.exceptions.KlabValidationException;
//import org.integratedmodelling.klab.knowledge.Observable;
//import org.integratedmodelling.klab.services.reasoner.internal.CoreOntology;
//import org.integratedmodelling.klab.services.reasoner.internal.CoreOntology.NS;
//import org.integratedmodelling.klab.services.reasoner.owl.Axiom;
//import org.integratedmodelling.klab.services.reasoner.owl.Concept;
//import org.integratedmodelling.klab.services.reasoner.owl.OWL;
//import org.integratedmodelling.klab.services.reasoner.owl.Ontology;
//import org.integratedmodelling.klab.utils.CamelCase;
//import org.integratedmodelling.klab.utils.Pair;
//
///**
// * A singleton that handles translation of k.IM knowledge statements to internal OWL-based
// * knowledge. The actual semantic work is done by {@link ObservableBuilder}.
// * 
// * @author Ferd
// *
// */
//public enum KimKnowledgeProcessor {
//
//    INSTANCE;
//
//    private Map<String, String> coreConceptPeers = new HashMap<>();
//
//    
//    /*
//     * Record correspondence of core concept peers to worldview concepts. Called by KimValidator for
//     * later use at namespace construction.
//     */
//    public void setWorldviewPeer(String coreConcept, String worldviewConcept) {
//        coreConceptPeers.put(worldviewConcept, coreConcept);
//    }
//
//    public Concept build(final KKimConceptStatement concept, Ontology ontology, KKimConceptStatement kimObject,
//            final KChannel monitor) {
//
//        if (concept.isMacro()) {
//            return null;
//        }
//
//        try {
//
//            if (concept.isAlias()) {
//
//                /*
//                 * can only have 'is' X or 'equals' X
//                 */
//                KConcept parent = null;
//                if (concept.getUpperConceptDefined() != null) {
//                    parent = OWL.INSTANCE.getConcept(concept.getUpperConceptDefined());
//                    if (parent == null) {
//                        monitor.error("Core concept " + concept.getUpperConceptDefined() + " is unknown", concept);
//                    } else {
//                        parent.getType().addAll(concept.getType());
//                    }
//                } else {
//
//                    List<KConcept> concepts = new ArrayList<>();
//                    int i = 0;
//                    for (ParentConcept p : concept.getParents()) {
//
//                        if (i > 0) {
//                            monitor.error("concepts defining aliases with 'equals' cannot have more than one parent", p);
//                        }
//
//                        for (KKimConcept pdecl : p.getConcepts()) {
//                            KConcept declared = declare(pdecl, ontology, monitor);
//                            if (declared == null) {
//                                monitor.error("parent declaration " + pdecl + " does not identify known concepts", pdecl);
//                                return null;
//                            }
//                            concepts.add(declared);
//                        }
//                        i++;
//                    }
//
//                    if (concepts.size() == 1) {
//                        parent = concepts.get(0);
//                    }
//                }
//
//                if (parent != null) {
//                    ontology.addDelegateConcept(concept.getName(), ontology.getName(), (Concept) parent);
//                }
//
//                return null;
//            }
//
//            Concept ret = buildInternal(concept, ontology, kimObject, monitor);
//            KConcept upperConceptDefined = null;
//            if (concept.getParents().isEmpty()) {
//                KConcept parent = null;
//                if (concept.getUpperConceptDefined() != null) {
//                    upperConceptDefined = parent = OWL.INSTANCE.getConcept(concept.getUpperConceptDefined());
//                    if (parent == null) {
//                        monitor.error("Core concept " + concept.getUpperConceptDefined() + " is unknown", concept);
//                    }
//                } else {
//                    parent = OWL.INSTANCE.getCoreOntology().getCoreType(concept.getType());
//                    if (coreConceptPeers.containsKey(ret.toString())) {
//                        // ensure that any non-trivial core inheritance is dealt with appropriately
//                        parent = OWL.INSTANCE.getCoreOntology().alignCoreInheritance(ret);
//                    }
//                }
//
//                if (parent != null) {
//                    ontology.add(Axiom.SubClass(parent.getUrn(), ret.getName()));
//                }
//            }
//
//            if (ret != null) {
//                ontology.add(Axiom.AnnotationAssertion(ret.getName(), NS.BASE_DECLARATION, "true"));
//                createProperties(ret, ontology);
//                ontology.define();
//
//                if (coreConceptPeers.containsKey(ret.toString()) && upperConceptDefined != null
//                        && "true".equals(upperConceptDefined.getMetadata().get(NS.IS_CORE_KIM_TYPE, "false"))) {
//                    OWL.INSTANCE.getCoreOntology().setAsCoreType(ret);
//                }
//
//            }
//
//            return ret;
//
//        } catch (Throwable e) {
//            monitor.error(e, concept);
//        }
//        return null;
//    }
//
//    private Concept buildInternal(KKimConceptStatement concept, Ontology ontology, KKimConceptStatement kimObject,
//            KChannel monitor) {
//
//        Concept main = null;
//        String mainId = concept.getName();
//
//        ontology.add(Axiom.ClassAssertion(mainId, concept.getType()));
//
//        // set the k.IM definition
//        ontology.add(
//                Axiom.AnnotationAssertion(mainId, NS.CONCEPT_DEFINITION_PROPERTY, ontology.getName() + ":" + concept.getName()));
//
//        // and the reference name
//        ontology.add(Axiom.AnnotationAssertion(mainId, NS.REFERENCE_NAME_PROPERTY,
//                getCleanFullId(ontology.getName(), concept.getName())));
//
//        /*
//         * basic attributes subjective deniable internal uni/bidirectional (relationship)
//         */
//        if (concept.isAbstract()) {
//            ontology.add(Axiom.AnnotationAssertion(mainId, CoreOntology.NS.IS_ABSTRACT, "true"));
//        }
//
//        ontology.define();
//        main = ontology.getConcept(mainId);
//
//        for (ParentConcept parent : concept.getParents()) {
//
//            List<KConcept> concepts = new ArrayList<>();
//            for (KKimConcept pdecl : parent.getConcepts()) {
//                KConcept declared = declare(pdecl, ontology, monitor);
//                if (declared == null) {
//                    monitor.error("parent declaration " + pdecl + " does not identify known concepts", pdecl);
//                    return null;
//                }
//                concepts.add(declared);
//            }
//
//            if (concepts.size() == 1) {
//                ontology.add(Axiom.SubClass(concepts.get(0).getUrn(), mainId));
//            } else {
//                KConcept expr = null;
//                switch(parent.getConnector()) {
//                case INTERSECTION:
//                    expr = OWL.INSTANCE.getIntersection(concepts, ontology, ((Concept) concepts.get(0)).getType());
//                    break;
//                case UNION:
//                    expr = OWL.INSTANCE.getUnion(concepts, ontology, ((Concept) concepts.get(0)).getType());
//                    break;
//                case FOLLOWS:
//                    expr = OWL.INSTANCE.getConsequentialityEvent(concepts, ontology);
//                    break;
//                default:
//                    // won't happen
//                    break;
//                }
//                if (concept.isAlias()) {
//                    ontology.addDelegateConcept(mainId, ontology.getName(), (Concept) expr);
//                } else {
//                    ontology.add(Axiom.SubClass(expr.getUrn(), mainId));
//                }
//            }
//            ontology.define();
//        }
//
//        for (KKimScope child : concept.getChildren()) {
//            if (child instanceof KKimConceptStatement) {
//                try {
//                    KimConceptStatement chobj = kimObject == null ? null : new KimConceptStatement((KKimConceptStatement) child);
//                    KConcept childConcept = buildInternal((KKimConceptStatement) child, ontology, chobj,
//                            monitor instanceof ErrorNotifyingMonitor
//                                    ? ((ErrorNotifyingMonitor) monitor).contextualize(child)
//                                    : monitor);
//                    ontology.add(Axiom.SubClass(mainId, childConcept.getName()));
//                    ontology.define();
//                    kimObject.getChildren().add(chobj);
//                } catch (Throwable e) {
//                    monitor.error(e, child);
//                }
//            }
//        }
//
//        for (KKimConcept inherited : concept.getTraitsInherited()) {
//            KConcept trait = declare(inherited, ontology, monitor);
//            if (trait == null) {
//                monitor.error("inherited " + inherited.getName() + " does not identify known concepts", inherited);
//                return null;
//            }
//            try {
//                OWL.INSTANCE.addTrait(main, trait, ontology);
//            } catch (KlabValidationException e) {
//                monitor.error(e, inherited);
//            }
//        }
//
//        // TODO all the rest: creates, ....
//        for (KKimConcept affected : concept.getQualitiesAffected()) {
//            KConcept quality = declare(affected, ontology, monitor);
//            if (quality == null) {
//                monitor.error("affected " + affected.getName() + " does not identify known concepts", affected);
//                return null;
//            }
//            OWL.INSTANCE.restrictSome(main, OWL.INSTANCE.getProperty(NS.AFFECTS_PROPERTY), quality, ontology);
//        }
//
//        for (KKimConcept required : concept.getRequiredIdentities()) {
//            KConcept quality = declare(required, ontology, monitor);
//            if (quality == null) {
//                monitor.error("required " + required.getName() + " does not identify known concepts", required);
//                return null;
//            }
//            OWL.INSTANCE.restrictSome(main, OWL.INSTANCE.getProperty(NS.REQUIRES_IDENTITY_PROPERTY), quality, ontology);
//        }
//
//        for (KKimConcept affected : concept.getObservablesCreated()) {
//            KConcept quality = declare(affected, ontology, monitor);
//            if (quality == null) {
//                monitor.error("created " + affected.getName() + " does not identify known concepts", affected);
//                return null;
//            }
//            OWL.INSTANCE.restrictSome(main, OWL.INSTANCE.getProperty(NS.CREATES_PROPERTY), quality, ontology);
//        }
//
//        for (ApplicableConcept link : concept.getSubjectsLinked()) {
//            if (link.getOriginalObservable() == null && link.getSource() != null) {
//                // relationship source->target
//                OWL.INSTANCE.defineRelationship(main, declare(link.getSource(), ontology, monitor),
//                        declare(link.getTarget(), ontology, monitor), ontology);
//            } else {
//                // TODO
//            }
//        }
//
//        if (!concept.getEmergenceTriggers().isEmpty()) {
//            List<KConcept> triggers = new ArrayList<>();
//            for (KKimConcept trigger : concept.getEmergenceTriggers()) {
//                triggers.add(declare(trigger, ontology, monitor));
//            }
//            Reasoner.INSTANCE.registerEmergent(main, triggers);
//        }
//
//        if (kimObject != null) {
//            kimObject.set(main);
//        }
//
//        return main;
//    }
//    
//
//    public synchronized Observable declare(final KKimObservable concept, Ontology declarationOntology, final KChannel monitor) {
//
//        if (concept.getNonSemanticType() != null) {
//            Concept nsmain = OWL.INSTANCE.getNonsemanticPeer(concept.getModelReference(), concept.getNonSemanticType());
//            Observable observable = new Observable(nsmain);
//            observable.setModelReference(concept.getModelReference());
//            observable.setName(concept.getFormalName());
//            observable.setStatedName(concept.getFormalName());
//            observable.setReferenceName(concept.getFormalName());
//            return observable;
//        }
//
//        Concept main = declareInternal(concept.getMain(), (Ontology) declarationOntology, monitor);
//
//        if (main == null) {
//            return null;
//        }
//
//        Concept observable = main;
//
//        Observable ret = new Observable(observable);
//
//        IObservable.Builder builder = ObservableBuilder.getBuilder(main, monitor);
//
//        // ret.setUrl(concept.getURI());
//        builder.withUrl(concept.getURI());
//
//        boolean unitsSet = false;
//
//        if (concept.getUnit() != null) {
//            unitsSet = true;
//            builder = builder.withUnit(concept.getUnit());
//        }
//
//        if (concept.getCurrency() != null) {
//            unitsSet = true;
//            builder = builder.withCurrency(concept.getCurrency());
//        }
//
//        if (concept.getValue() != null) {
//            Object value = concept.getValue();
//            if (value instanceof KKimConcept) {
//                value = Concepts.INSTANCE.declare((KKimConcept) value);
//            }
//            builder = builder.withInlineValue(value);
//            // ret.setValue(concept.getValue());
//        }
//
//        if (concept.getDefaultValue() != null) {
//            Object value = concept.getValue();
//            if (value instanceof KKimConcept) {
//                value = Concepts.INSTANCE.declare((KKimConcept) value);
//            }
//            builder = builder.withDefaultValue(value);
//            /*
//             * ret.setDefaultValue(concept.getValue());
//             */
//        }
//
//        for (ResolutionException exc : concept.getResolutionExceptions()) {
//            builder = builder.withResolutionException(exc);
//        }
//
//        if (concept.getRange() != null) {
//            builder = builder.withRange(concept.getRange());
//            // ret.setRange(concept.getRange());
//        }
//
//        builder = builder.optional(concept.isOptional()).generic(concept.isGeneric()).global(concept.isGlobal())
//                .named(concept.getFormalName());
//
//        if (concept.isExclusive()) {
//            builder = builder.withResolution(Resolution.Only);
//        } else if (concept.isGlobal()) {
//            builder = builder.withResolution(Resolution.All);
//        } else if (concept.isGeneric()) {
//            builder = builder.withResolution(Resolution.Any);
//        }
//
//        for (Pair<ValueOperator, Object> operator : concept.getValueOperators()) {
//            builder = builder.withValueOperator(operator.getFirst(), operator.getSecond());
//        }
//
//        if (Units.INSTANCE.needsUnits(ret) && !unitsSet) {
//            builder = builder.fluidUnits(true);
//        }
//
//        for (IKimAnnotation annotation : concept.getAnnotations()) {
//            builder = builder.withAnnotation(new Annotation(annotation));
//        }
//
//        return (Observable) builder.buildObservable();
//    }
//
//    /**
//     * 
//     * @param concept
//     * @param declarationNamespace the namespace where derived concepts will be put if declaring
//     *        them in the original ontologies causes loss of referential integrity.
//     * @param monitor
//     * @return
//     */
//    public KConcept declare(final KKimConcept concept, Ontology declarationOntology, final KChannel monitor) {
//        return declareInternal(concept, (Ontology) declarationOntology, monitor);
//    }
//
//    private synchronized Concept declareInternal(final KKimConcept concept, Ontology ontology, final KChannel monitor) {
//
//        Concept main = null;
//
//        if (concept.getObservable() != null) {
//            main = declareInternal(concept.getObservable(), ontology, monitor);
//        } else if (concept.getName() != null) {
//            main = Concepts.INSTANCE.getConcept(concept.getName());
//        }
//
//        if (main == null) {
//            return null;
//        }
//
//        Builder builder = new ObservableBuilder(main, ontology, monitor).withDeclaration(concept, monitor);
//
//        if (concept.getDistributedInherent() != null) {
//            builder.withDistributedInherency(true);
//        }
//
//        /*
//         * transformations first
//         */
//
//        if (concept.getInherent() != null) {
//            KConcept c = declareInternal(concept.getInherent(), ontology, monitor);
//            if (c != null) {
//                builder.of(c);
//            }
//        }
//        if (concept.getContext() != null) {
//            KConcept c = declareInternal(concept.getContext(), ontology, monitor);
//            if (c != null) {
//                if (ObservableRole.CONTEXT.equals(concept.getDistributedInherent())) {
//                    builder.of(c);
//                } else {
//                    builder.within(c);
//                }
//            }
//        }
//        if (concept.getCompresent() != null) {
//            KConcept c = declareInternal(concept.getCompresent(), ontology, monitor);
//            if (c != null) {
//                builder.with(c);
//            }
//        }
//        if (concept.getCausant() != null) {
//            KConcept c = declareInternal(concept.getCausant(), ontology, monitor);
//            if (c != null) {
//                builder.from(c);
//            }
//        }
//        if (concept.getCaused() != null) {
//            KConcept c = declareInternal(concept.getCaused(), ontology, monitor);
//            if (c != null) {
//                builder.to(c);
//            }
//        }
//        if (concept.getMotivation() != null) {
//            KConcept c = declareInternal(concept.getMotivation(), ontology, monitor);
//            if (c != null) {
//                if (ObservableRole.GOAL.equals(concept.getDistributedInherent())) {
//                    builder.of(c);
//                } else {
//                    builder.withGoal(c);
//                }
//            }
//        }
//        if (concept.getCooccurrent() != null) {
//            KConcept c = declareInternal(concept.getCooccurrent(), ontology, monitor);
//            if (c != null) {
//                builder.withCooccurrent(c);
//            }
//        }
//        if (concept.getAdjacent() != null) {
//            KConcept c = declareInternal(concept.getAdjacent(), ontology, monitor);
//            if (c != null) {
//                builder.withAdjacent(c);
//            }
//        }
//        if (concept.getRelationshipSource() != null) {
//            KConcept source = declareInternal(concept.getRelationshipSource(), ontology, monitor);
//            KConcept target = declareInternal(concept.getRelationshipTarget(), ontology, monitor);
//            if (source != null && target != null) {
//                builder.linking(source, target);
//            }
//
//        }
//
//        for (KKimConcept c : concept.getTraits()) {
//            KConcept trait = declareInternal(c, ontology, monitor);
//            if (trait != null) {
//                builder.withTrait(trait);
//            }
//        }
//
//        for (KKimConcept c : concept.getRoles()) {
//            KConcept role = declareInternal(c, ontology, monitor);
//            if (role != null) {
//                builder.withRole(role);
//            }
//        }
//
//        if (concept.getSemanticModifier() != null) {
//            KConcept other = null;
//            if (concept.getComparisonConcept() != null) {
//                other = declareInternal(concept.getComparisonConcept(), ontology, monitor);
//            }
//            try {
//                builder.as(concept.getSemanticModifier(), other == null ? (KConcept[]) null : new KConcept[]{other});
//            } catch (KlabValidationException e) {
//                monitor.error(e, concept);
//            }
//        }
//
//        Concept ret = null;
//        try {
//
//            ret = (Concept) builder.buildConcept();
//
//            /*
//             * handle unions and intersections
//             */
//            if (concept.getOperands().size() > 0) {
//                List<KConcept> concepts = new ArrayList<>();
//                concepts.add(ret);
//                for (KKimConcept op : concept.getOperands()) {
//                    concepts.add(declareInternal(op, ontology, monitor));
//                }
//                ret = concept.getExpressionType() == KKimConcept.Expression.INTERSECTION
//                        ? OWL.INSTANCE.getIntersection(concepts, ontology, concept.getOperands().get(0).getType())
//                        : OWL.INSTANCE.getUnion(concepts, ontology, concept.getOperands().get(0).getType());
//            }
//
//            // set the k.IM definition in the concept.This must only happen if the
//            // concept wasn't there - within build() and repeat if mods are made
//            if (builder.axiomsAdded()) {
//
//                ret.getOntology().define(Collections.singletonList(
//                        Axiom.AnnotationAssertion(ret.getName(), NS.CONCEPT_DEFINITION_PROPERTY, concept.getDefinition())));
//
//                // consistency check
//                if (!Services.INSTANCE.getReasoner().satisfiable(ret)) {
//                    ret.getType().add(SemanticType.NOTHING);
//                    monitor.error("the definition of this concept has logical errors and is inconsistent", concept);
//                }
//            }
//
//        } catch (Throwable e) {
//            monitor.error(e, concept);
//        }
//
//        if (concept.isNegated()) {
//            ret = (Concept) Traits.INSTANCE.makeNegation(ret, ontology);
//        }
//
//        return ret;
//    }
//
//    /**
//     * Source of truth for identifier-friendly reference names
//     * 
//     * @param main
//     * @return
//     */
//    public static String getCleanFullId(String namespace, String name) {
//        return namespace.replaceAll("\\.", "_") + "__" + CamelCase.toLowerCase(name, '_');
//    }
//
//
//}
