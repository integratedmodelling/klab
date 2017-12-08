package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.SemanticOperator;
import org.integratedmodelling.kim.model.SemanticType;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.services.IObservableService.Builder;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ObservableBuilder implements Builder {

    private IOntology                     ontology;

    private IConcept                      main;
    private IConcept                      parent;
    private String                        mainId;
    private Set<Type>                     type;
    private boolean                       negated;
    private IConcept                      inherent;
    private IConcept                      context;
    private IConcept                      compresent;
    private IConcept                      causant;
    private IConcept                      caused;
    private IConcept                      goal;
    private IConcept                      classifier;
    private IConcept                      downTo;

    private List<IConcept>                traits    = new ArrayList<>();
    private List<IConcept>                roles     = new ArrayList<>();
    private List<KlabValidationException> errors    = new ArrayList<>();

    private boolean                       isTrivial = true;

    public ObservableBuilder(IConcept main, IOntology ontology) {
        this.main = main;
        this.ontology = ontology;
        this.type = ((Concept) main).type;
    }

    public ObservableBuilder(String main, IConcept parent, IOntology ontology) {
        this.mainId = main;
        this.ontology = ontology;
        this.parent = parent;
        this.type = ((Concept) parent).type;
    }

    public ObservableBuilder(String main, Set<Type> parent, IOntology ontology) {
        this.mainId = main;
        this.ontology = ontology;
        this.parent = Workspaces.INSTANCE.getUpperOntology().getCoreType(parent);
        this.type = parent;
    }

    @Override
    public Builder of(IConcept concept) {
        this.inherent = concept;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder within(IConcept concept) {
        this.context = concept;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder to(IConcept concept) {
        this.caused = concept;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder from(IConcept concept) {
        this.causant = concept;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder negated() {
        negated = true;
        return this;
    }

    @Override
    public Builder with(IConcept concept) {
        this.compresent = concept;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder as(IConcept concept) {
        if (!concept.is(Type.ROLE)) {
            errors.add(new KlabValidationException("cannot use concept " + concept + " as a role"));
        }
        this.roles.add(concept);
        isTrivial = false;
        return this;
    }

    @Override
    public Builder downTo(IConcept concept) {
        this.downTo = concept;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder by(IConcept concept) {
        // must be a trait and must be unique
        this.classifier = concept;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder withGoal(IConcept goal) {
        this.goal = goal;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder contextualizedTo(IConcept context) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Builder as(SemanticOperator type, IConcept... participants) {

        if (resolveMain()) {

            switch (type) {
            case ASSESSMENT:
                this.main = makeAssessment(this.main);
                break;
            case COUNT:
                break;
            case DISTANCE:
                break;
            case OCCURRENCE:
                break;
            case PRESENCE:
                break;
            case PROBABILITY:
                break;
            case PROPORTION:
                break;
            case RATIO:
                break;
            case UNCERTAINTY:
                break;
            case VALUE:
                break;
            default:
                break;
            }
        }

        return this;
    }

    @Override
    public Collection<KlabValidationException> getErrors() {
        return errors;
    }

    @Override
    public Builder without(Collection<IConcept> concepts) {
        return without(concepts.toArray(new IConcept[concepts.size()]));
    }

    @Override
    public Builder without(IConcept... concepts) {
        if (resolveMain()) {
            // TODO perform the extraction right here
        }
        return this;
    }

    @Override
    public Builder withTrait(IConcept... concepts) {
        for (IConcept concept : concepts) {
            if (!concept.is(Type.TRAIT)) {
                errors.add(new KlabValidationException("cannot use concept " + concept + " as a trait"));
            } else {
                traits.add(concept);
            }
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder withTrait(Collection<IConcept> concepts) {
        return withTrait(concepts.toArray(new IConcept[concepts.size()]));
    }

    private static IConcept makeAssessment(IConcept concept) {

        String cName = cleanInternalId(concept.getLocalName()) + "Assessment";

        if (!concept.is(Type.QUALITY)) {
            return null;
        }

        /*
         * TODO make new type - remove all qualities and make it a process
         */
        EnumSet<Type> newType = EnumSet.copyOf(((Concept) concept).type);

        /*
         * make a ConceptAssessment if not there, and ensure it's a continuously
         * quantifiable quality. Must be in same ontology as the original concept.
         */
        IConcept ret = concept.getOntology().getConcept(cName);

        if (ret == null) {

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(cName, newType));
            ax.add(Axiom.SubClass(NS.CORE_ASSESSMENT, cName));
            // ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY, Qualities.ASSESSMENT
            // .name().toLowerCase() + " " + ((Concept) concept).getAssertedDefinition()));
            ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
            concept.getOntology().define(ax);
            ret = concept.getOntology().getConcept(cName);
            OWL.INSTANCE.restrictSome(ret, Concepts.p(CoreOntology.NS.OBSERVES_PROPERTY), concept);
        }

        return ret;

    }

    private boolean resolveMain() {

        if (main != null) {
            return true;
        }

        if (ontology == null) {
            if (mainId != null) {
                if (mainId.contains(":")) {
                    SemanticType st = new SemanticType(mainId);
                    ontology = OWL.INSTANCE.getOntology(st.getNamespace());
                    mainId = st.getName();
                    if ((main = ontology.getConcept(mainId)) != null) {
                        mainId = null;
                    }
                }
                if (ontology == null) {
                    errors.add(new KlabValidationException("cannot create a new concept from an ID if the ontology is not specified"));
                }
            }
        }

        return main != null;
    }

    // just remove the starting 'i' if any.
    private static String cleanInternalId(String s) {
        return s.startsWith("i") ? s.substring(1) : s;
    }

    @Override
    public IConcept build() throws KlabValidationException {

        if (errors.size() > 0) {
            String message = "";
            for (KlabValidationException error : errors) {
                message += (message.isEmpty() ? "" : "\n") + error.getLocalizedMessage();
            }
            throw new KlabValidationException(message);
        }

        resolveMain();

        /*
        * correctly support trival case so we can use this without checking.
        */
        if (isTrivial()) {
            return main;
        }

        Set<IConcept> identities = new HashSet<>();
        Set<IConcept> attributes = new HashSet<>();
        Set<IConcept> realms = new HashSet<>();

        /*
        * to ensure traits are not conflicting
        */
        Set<IConcept> baseTraits = new HashSet<>();

        /*
        * to ensure we know if we concretized any abstract traits so we can properly
        * compute our abstract status.
        */
        Set<IConcept> abstractTraitBases = new HashSet<>();

        IConcept ret = main;
        ArrayList<String> tids = new ArrayList<>();
        ArrayList<IConcept> keep = new ArrayList<IConcept>();

        /*
        * preload any base traits we already have. If any of them is abstract, take
        * notice so we can see if they are all concretized later.
        */
        for (IConcept c : Traits.INSTANCE.getTraits(main)) {
            IConcept base = Traits.INSTANCE.getBaseParentTrait(c);
            baseTraits.add(base);
            if (c.isAbstract()) {
                abstractTraitBases.add(base);
            }
        }

        /*
         * name and display label for the finished concept
         */
        String cId = "";
        String cDs = "";

        /*
         * we also add a non-by, non-down-to concept (untrasformed) if absent, so that we
         * can reconstruct an observable without transformations but with all traits and
         * roles if required. This is returned by getUntransformedObservable().
         * 
         * TODO this is related to the observable not being the same as the declared - see
         * what's the best way to handle this. Better to build the concept using only the
         * declaration, then create the other using the same axioms + by/downto if needed.
         * No need for a special uId - just do it after.
         */
        String uId = "";

        if (traits != null && traits.size() > 0) {

            for (IConcept t : traits) {

                if (t.equals(main)) {
                    continue;
                }

                if (Traits.INSTANCE.getTraits(main).contains(t)) {
                    throw new KlabValidationException("concept " + Concepts.INSTANCE.getDisplayName(main)
                            + " already adopts trait " + Concepts.INSTANCE.getDisplayName(t));
                }

                if (t.is(Type.IDENTITY)) {
                    identities.add(t);
                } else if (t.is(Type.REALM)) {
                    realms.add(t);
                } else if (!t.is(Type.SUBJECTIVE)) {
                    attributes.add(t);
                }

                IConcept base = Traits.INSTANCE.getBaseParentTrait(t);

                if (base == null) {
                    throw new KlabValidationException("trait " + t
                            + " cannot be understood as coming from a known declaration");
                }

                if (!baseTraits.add(base)) {
                    throw new KlabValidationException("cannot add trait "
                            + Concepts.INSTANCE.getDisplayName(t)
                            + " to concept " + main
                            + " as it already adopts a trait of type "
                            + Concepts.INSTANCE.getDisplayName(base));
                }

                if (t.isAbstract()) {
                    abstractTraitBases.add(base);
                } else {
                    abstractTraitBases.remove(base);
                }

                tids.add(Concepts.INSTANCE.getDisplayName(t));

            }
        }

        /*
        * FIXME using the display name to build an ID is wrong and forces us to use
        * display names that are legal for concept names. The two should work
        * independently.
        */
        if (tids.size() > 0) {
            Collections.sort(tids);
            for (String s : tids) {
                cId += cleanInternalId(s);
                cDs += cleanInternalId(s);
                uId += cleanInternalId(s);
            }
        }

        /*
        * add the main identity to the ID after all traits and before any context
        */
        cId += cleanInternalId(main.getLocalName());
        cDs += cleanInternalId(main.getLocalName());
        uId += cleanInternalId(main.getLocalName());

        /*
        * handle context and inherency
        */
        if (inherent != null) {
            IConcept other = Observables.INSTANCE.getInherentType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(inherent, other)) {
                throw new KlabValidationException("cannot add inherent type "
                        + Concepts.INSTANCE.getDisplayName(inherent)
                        + " to concept " + Concepts.INSTANCE.getDisplayName(main)
                        + " as it already has an incompatible inherency: "
                        + Concepts.INSTANCE.getDisplayName(other));
            }
            cId += "Of" + cleanInternalId(inherent.getLocalName());
            cDs += "Of" + cleanInternalId(inherent.getLocalName());
            uId += "Of" + cleanInternalId(inherent.getLocalName());
        }

        if (context != null) {
            IConcept other = Observables.INSTANCE.getContextType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(context, other)) {
                throw new KlabValidationException("cannot add context "
                        + Concepts.INSTANCE.getDisplayName(context)
                        + " to concept " + Concepts.INSTANCE.getDisplayName(main)
                        + " as it already has an incompatible context: "
                        + Concepts.INSTANCE.getDisplayName(other));
            }
            cId += "In" + cleanInternalId(context.getLocalName());
            cDs += "In" + cleanInternalId(context.getLocalName());
            uId += "In" + cleanInternalId(context.getLocalName());
        }

        //

        // String roleDefinition = "";
        // String roleIds = "";
        // List<String> rids = new ArrayList<>();
        // Set<IConcept> acceptedRoles = new HashSet<>();
        //
        // if (roles != null && roles.size() > 0) {
        // for (IConcept role : roles) {
        // if (Roles.getRoles(main).contains(role)) {
        // throw new KlabValidationException("concept " + NS.getDisplayName(main)
        // + " already has role " + NS.getDisplayName(role));
        // }
        // roleDefinition += (roleDefinition.isEmpty() ? "" : ",") + role.getDefinition();
        // rids.add(NS.getDisplayName(role));
        // acceptedRoles.add(role);
        // if (role.isAbstract()) {
        // makeAbstract = true;
        // }
        // }
        // }
        //
        // if (rids.size() > 0) {
        // Collections.sort(rids);
        // for (String s : rids) {
        // roleIds += s;
        // }
        // }
        //
        // /*
        // * add the main identity to the ID after all traits and before any context
        // */
        // if (!roleIds.isEmpty()) {
        // cId += "As" + roleIds;
        // // only add role names to user description if roles are not from the
        // // root of the worldview
        // if (!rolesAreFundamental(roles)) {
        // cDs = roleIds + NS.getDisplayName(main);
        // }
        // }
        //
        // /*
        // * Add a lowercase prefix to the ID to ensure no conflict can exist with a
        // * situation like "TraitConcept is Trait Concept". The name without prefix will be
        // * in the display label annotation.
        // */
        // cId = "i" + cId;
        //
        // ret = ontology.getConcept(cId);
        // boolean needUntransformed = byTrait != null || downTo != null;
        // if (needUntransformed) {
        // needUntransformed = !uId.equals(cleanInternalId(main.getLocalName()))
        // && ontology.getConcept("i" + uId) == null;
        // }
        //
        // IConcept uret = null;
        // String untransformedDefinition = mainDefinition;
        //
        // if (ret == null) {
        //
        List<IAxiom> axioms = new ArrayList<>();
        axioms.add(Axiom.ClassAssertion(cId, type));
        axioms.add(Axiom.AnnotationAssertion(cId, NS.DISPLAY_LABEL_PROPERTY, cDs));

        /*
         * add the core observable concept ID using NS.CORE_OBSERVABLE_PROPERTY
         */
        axioms.add(Axiom.AnnotationAssertion(cId, NS.CORE_OBSERVABLE_PROPERTY, main.toString()));

        //
        // if (needUntransformed) {
        // axioms.add(Axiom.ClassAssertion("i" + uId));
        // }
        //
        // /*
        // * if there is a 'by', this is the child of the class that exposes it, not the
        // * original concept's.
        // */
        // axioms.add(Axiom
        // .SubClass((byTrait == null ? main.toString() : makeTypeFor(byTrait).toString()), cId));
        //
        // if (needUntransformed) {
        // axioms.add(Axiom.SubClass(main.toString(), uId));
        // }
        //
        if (type.contains(Type.ABSTRACT)) {
            axioms.add(Axiom.AnnotationAssertion(cId, NS.IS_ABSTRACT, "true"));
        }
        //
        // boolean isDerived = false;
        // if (!traitDefinition.isEmpty()) {
        // mainDefinition += "+T(" + traitDefinition + ")";
        // if (needUntransformed) {
        // untransformedDefinition += "+T(" + untransformedDefinition + ")";
        // }
        // isDerived = true;
        // }
        // if (!inherentDefinition.isEmpty()) {
        // mainDefinition += (traitDefinition.isEmpty() ? "+" : ",") + "I(" + inherentDefinition + ")";
        // if (needUntransformed) {
        // untransformedDefinition += (traitDefinition.isEmpty() ? "+" : ",") + "I("
        // + inherentDefinition
        // + ")";
        // }
        // isDerived = true;
        // }
        // if (!contextDefinition.isEmpty()) {
        // mainDefinition += ((traitDefinition.isEmpty() && inherentDefinition.isEmpty()) ? "+" : ",")
        // + "C("
        // + contextDefinition + ")";
        // if (needUntransformed) {
        // untransformedDefinition += ((traitDefinition.isEmpty() && inherentDefinition.isEmpty())
        // ? "+" : ",")
        // + "C(" + contextDefinition + ")";
        // }
        // isDerived = true;
        // }
        // if (!roleDefinition.isEmpty()) {
        // mainDefinition += ((traitDefinition.isEmpty() && inherentDefinition.isEmpty()
        // && contextDefinition.isEmpty()) ? "+" : ",") + "R(" + roleDefinition + ")";
        // isDerived = true;
        // }
        // if (!byDefinition.isEmpty()) {
        // mainDefinition += ((traitDefinition.isEmpty() && inherentDefinition.isEmpty()
        // && contextDefinition.isEmpty() && roleDefinition.isEmpty()) ? "+" : ",") + "B("
        // + byDefinition
        // + ")";
        // isDerived = true;
        // }
        // if (!downToDefinition.isEmpty()) {
        // mainDefinition += ((traitDefinition.isEmpty() && inherentDefinition.isEmpty()
        // && contextDefinition.isEmpty() && roleDefinition.isEmpty() && byDefinition.isEmpty())
        // ? "+"
        // : ",")
        // + "D(" + downToDefinition + ")";
        // isDerived = true;
        // }
        //

        //
        
        ontology.define(axioms);
        ret = ontology.getConcept(cId);
        
        /*
         * restrictions
         */
        
        // if (identities.size() > 0) {
        // Traits.restrict(ret, KLAB.p(NS.HAS_IDENTITY_PROPERTY), LogicalConnector.INTERSECTION, identities);
        // if (needUntransformed) {
        // Traits.restrict(uret, KLAB
        // .p(NS.HAS_IDENTITY_PROPERTY), LogicalConnector.INTERSECTION, identities);
        // }
        // }
        // if (realms.size() > 0) {
        // Traits.restrict(ret, KLAB.p(NS.HAS_REALM_PROPERTY), LogicalConnector.INTERSECTION, realms);
        // if (needUntransformed) {
        // Traits.restrict(uret, KLAB.p(NS.HAS_REALM_PROPERTY), LogicalConnector.INTERSECTION, realms);
        // }
        // }
        // if (attributes.size() > 0) {
        // Traits.restrict(ret, KLAB
        // .p(NS.HAS_ATTRIBUTE_PROPERTY), LogicalConnector.INTERSECTION, attributes);
        // if (needUntransformed) {
        // Traits.restrict(uret, KLAB
        // .p(NS.HAS_ATTRIBUTE_PROPERTY), LogicalConnector.INTERSECTION, attributes);
        // }
        // }
        // if (acceptedRoles.size() > 0) {
        // OWL.restrictSome(ret, KLAB.p(NS.HAS_ROLE_PROPERTY), LogicalConnector.INTERSECTION, acceptedRoles);
        // }
        // if (inherent != null) {
        // OWL.restrictSome(ret, KLAB.p(NS.IS_INHERENT_TO_PROPERTY), inherent);
        // if (needUntransformed) {
        // OWL.restrictSome(uret, KLAB.p(NS.IS_INHERENT_TO_PROPERTY), inherent);
        // }
        // }
        // if (context != null) {
        // OWL.restrictSome(ret, KLAB.p(NS.HAS_CONTEXT_PROPERTY), context);
        // if (needUntransformed) {
        // OWL.restrictSome(uret, KLAB.p(NS.HAS_CONTEXT_PROPERTY), context);
        // }
        // }
        // if (byTrait != null) {
        // OWL.restrictSome(ret, KLAB.p(NS.REPRESENTED_BY_PROPERTY), byTrait);
        // }
        // if (downTo != null) {
        // OWL.restrictSome(ret, KLAB.p(NS.LIMITED_BY_PROPERTY), LogicalConnector.UNION, allowedDetail);
        // }
        
        // TODO here - if by/downto are there, add them to axioms now and redefine ret, adding the
        // untransformed one in the metadata.
        
        // String byDefinition = "";
        // String downToDefinition = "";
        // Set<IConcept> allowedDetail = new HashSet<>();
        //
        // if (byTrait != null) {
        //
        // if (!NS.isTrait(byTrait)) {
        // throw new KlabValidationException("the concept in a 'by' clause must be a base abstract trait");
        // }
        //
        // /*
        // * TODO trait must be a base trait and abstract.
        // */
        // if (!NS.isBaseDeclaration(byTrait) || !byTrait.isAbstract()) {
        // throw new KlabValidationException("traits used in a 'by' clause must be abstract and declared at
        // root level");
        // }
        // cId += "By" + cleanInternalId(byTrait.getLocalName());
        // cDs += "By" + cleanInternalId(byTrait.getLocalName());
        // byDefinition = byTrait.getDefinition();
        // makeAbstract = true;
        // }
        //
        // if (downTo != null) {
        // IConcept trait = byTrait == null ? main : byTrait;
        // if (!NS.isTrait(trait)) {
        // throw new KlabValidationException("cannot use 'down to' on non-trait observables");
        // }
        // allowedDetail.addAll(Types.getChildrenAtLevel(trait, Types.getDetailLevel(trait, downTo)));
        // cId += "DownTo" + cleanInternalId(downTo.getLocalName());
        // // display label stays the same
        // downToDefinition = downTo.getDefinition();
        // }
        //
        // if (needUntransformed) {
        // axioms.add(Axiom.AnnotationAssertion("i"
        // + uId, NS.CONCEPT_DEFINITION_PROPERTY, untransformedDefinition));
        // axioms.add(Axiom
        // .AnnotationAssertion("i" + cId, NS.UNTRANSFORMED_CONCEPT_PROPERTY, "i" + uId));
        // } else if (byTrait != null || downTo != null) {
        // // untransformed is the main concept
        // axioms.add(Axiom
        // .AnnotationAssertion(cId, NS.UNTRANSFORMED_CONCEPT_PROPERTY, main.toString()));
        // }
        
        // if (needUntransformed) {
        // uret = ontology.getConcept("i" + uId);
        // }
        // }
        //
        
        

        //

        if (negated) {
            // TODO
        }

        return ret;
    }

    private boolean isTrivial() {
        return isTrivial;
    }
}
