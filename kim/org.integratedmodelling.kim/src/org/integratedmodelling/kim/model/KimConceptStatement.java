package org.integratedmodelling.kim.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.eclipse.xtext.util.Pair;
import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConcept.Visitor;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.kim.ConceptStatementBody;

/**
 * Concept definition
 * 
 * @author ferdinando.villa
 *
 */
public class KimConceptStatement extends KimStatement implements IKimConceptStatement {

    private static final long  serialVersionUID = -118216699151744808L;

    public static final String ROOT_DOMAIN_NAME = "Domain";

    // describes an 'applies to' statement, possibly to
    // restricted target relationships
    public static class ApplicableConcept {
        public KimConcept concept;
        public KimConcept from;
        public KimConcept to;

        void visit(Visitor visitor) {
            if (concept != null) {
                concept.visit(visitor);
            }
            if (from != null) {
                from.visit(visitor);
            }
            if (to != null) {
                to.visit(visitor);
            }
        }
    }

    public static class ParentConcept {
        private List<KimConcept> concepts  = new ArrayList<>();
        private BinarySemanticOperator        connector = BinarySemanticOperator.NONE;

        public List<KimConcept> getConcepts() {
            return concepts;
        }

        public void setConcepts(List<KimConcept> concepts) {
            this.concepts = concepts;
        }

        public BinarySemanticOperator getConnector() {
            return connector;
        }

        public void setConnector(BinarySemanticOperator connector) {
            this.connector = connector;
        }
    }

    /**
     * Descriptor for a role. After KLAB-76: roles are scoped and not based on
     * restrictions, to allow their use within scenarios.
     * 
     * @author Ferd
     */
    public static class RoleDescriptor implements Serializable {

        private static final long serialVersionUID = 24722961549070111L;

        IKimConcept               within;

        public IKimConcept getWithin() {
            return within;
        }

        public void setWithin(IKimConcept within) {
            this.within = within;
        }

        public IKimConcept getTarget() {
            return target;
        }

        public void setTarget(IKimConcept target) {
            this.target = target;
        }

        public IKimConcept getRole() {
            return role;
        }

        public void setRole(IKimConcept role) {
            this.role = role;
        }

        IKimConcept target;
        IKimConcept role;

    }

    private EnumSet<Type>                           type                      = EnumSet.noneOf(Type.class);

    private String                                  namespace;
    private String                                  name;
    private String                                  upperConceptDefined;
    private String                                  authorityDefined;
    private String                                  authorityRequired;
    private boolean                                 Abstract;
    private boolean                                 alias;
    private boolean                                 macro;
    private String                                  authority;
    private String                                  authorityTerm;

    /*
     * each parent is a list: if more than one, the parent is the union of the
     * parents (using +) or the intersection (*) according to the contents of the
     * connector list. Mixed chains of +* or parenthesized expressions are not
     * allowed.
     */
    private List<ParentConcept>                     parents                   = new ArrayList<>();
    private List<KimRestriction>                    restrictions              = new ArrayList<>();
    private List<KimConcept>                        exposedTraits             = new ArrayList<>();
    private List<KimConcept>                        requiredIdentities        = new ArrayList<>();
    private List<KimConcept>                        requiredAttributes        = new ArrayList<>();
    private List<KimConcept>                        requiredRealms            = new ArrayList<>();
    private List<KimConcept>                        requiredExtents           = new ArrayList<>();
    private List<Pair<KimConcept, DescriptionType>> observablesDescribed      = new ArrayList<>();
    private List<KimConcept>                        traitsInherited           = new ArrayList<>();
    private List<KimConcept>                        traitsConferred           = new ArrayList<>();
    private List<KimConcept>                        partParticipants          = new ArrayList<>();
    private List<KimConcept>                        configurationParticipants = new ArrayList<>();
    private List<KimConcept>                        constituentParticipants   = new ArrayList<>();
    private List<KimConcept>                        countablesCreated         = new ArrayList<>();
    private List<ApplicableConcept>                 appliesTo                 = new ArrayList<>();
    private List<ApplicableConcept>                 subjectsLinked            = new ArrayList<>();
    private List<KimConcept>                        qualitiesAffected         = new ArrayList<>();
    private List<RoleDescriptor>                    roles                     = new ArrayList<>();

    /**
     * Classes expose traits with their observable semantics
     */
    private List<IKimObservable>                    traitsExposed             = new ArrayList<>();

    /**
     * If there are exposed traits, this defines whether the class is exposing (abstract) or adopting
     * (concrete)
     */
    private boolean                                 definingExposedTraits;

    @Override
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isMacro() {
        return macro;
    }

    public void setMacro(boolean template) {
        this.macro = template;
    }

    @Override
    public List<Pair<KimConcept, DescriptionType>> getObservablesDescribed() {
        return observablesDescribed;
    }

    public void setObservablesDescribed(List<Pair<KimConcept, DescriptionType>> observablesDescribed) {
        this.observablesDescribed = observablesDescribed;
    }

    @Override
    public List<ApplicableConcept> getAppliesTo() {
        return appliesTo;
    }

    public void setAppliesTo(List<ApplicableConcept> appliesTo) {
        this.appliesTo = appliesTo;
    }

    public KimConceptStatement(ConceptStatementBody statement) {
        super(statement);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected String getStringRepresentation(int offset) {
        String ret = offset(offset) + "[concept " + name + "]";
        for (IKimScope child : children) {
            ret += "\n" + ((KimScope) child).getStringRepresentation(offset + 3);
        }
        return ret;
    }

    public EnumSet<Type> getType() {
        return type;
    }

    public void setType(EnumSet<Type> type) {
        this.type = type;
    }

    @Override
    public String getUpperConceptDefined() {
        return upperConceptDefined;
    }

    public void setUpperConceptDefined(String upperConceptDefined) {
        this.upperConceptDefined = upperConceptDefined;
    }

    @Override
    public String getAuthorityDefined() {
        return authorityDefined;
    }

    public void setAuthorityDefined(String authorityDefined) {
        this.authorityDefined = authorityDefined;
    }

    @Override
    public String getAuthorityRequired() {
        return authorityRequired;
    }

    public void setAuthorityRequired(String authorityRequired) {
        this.authorityRequired = authorityRequired;
    }

    @Override
    public boolean isAbstract() {
        return Abstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.Abstract = isAbstract;
    }

    @Override
    public boolean isAlias() {
        return alias;
    }

    public void setAlias(boolean isAlias) {
        this.alias = isAlias;
    }

    @Override
    public List<ParentConcept> getParents() {
        return parents;
    }

    public void setParents(List<ParentConcept> parents) {
        this.parents = parents;
    }

    @Override
    public List<KimRestriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<KimRestriction> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public List<KimConcept> getExposedTraits() {
        return exposedTraits;
    }

    public void setExposedTraits(List<KimConcept> exposedTraits) {
        this.exposedTraits = exposedTraits;
    }

    @Override
    public List<KimConcept> getRequiredIdentities() {
        return requiredIdentities;
    }

    public void setRequiredIdentities(List<KimConcept> requiredIdentities) {
        this.requiredIdentities = requiredIdentities;
    }

    @Override
    public List<KimConcept> getRequiredAttributes() {
        return requiredAttributes;
    }

    public void setRequiredAttributes(List<KimConcept> requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
    }

    @Override
    public List<KimConcept> getRequiredRealms() {
        return requiredRealms;
    }

    public void setRequiredRealms(List<KimConcept> requiredRealms) {
        this.requiredRealms = requiredRealms;
    }

    @Override
    public List<KimConcept> getRequiredExtents() {
        return requiredExtents;
    }

    public void setRequiredExtents(List<KimConcept> requiredExtents) {
        this.requiredExtents = requiredExtents;
    }

    @Override
    public List<KimConcept> getTraitsInherited() {
        return traitsInherited;
    }

    public void setTraitsInherited(List<KimConcept> traitsInherited) {
        this.traitsInherited = traitsInherited;
    }

    @Override
    public List<KimConcept> getTraitsConferred() {
        return traitsConferred;
    }

    public void setTraitsConferred(List<KimConcept> traitsConferred) {
        this.traitsConferred = traitsConferred;
    }

    @Override
    public List<KimConcept> getPartParticipants() {
        return partParticipants;
    }

    @Override
    public List<KimConcept> getConfigurationParticipants() {
        return configurationParticipants;
    }

    public void setPartParticipants(List<KimConcept> partParticipants) {
        this.partParticipants = partParticipants;
    }

    @Override
    public List<KimConcept> getConstituentParticipants() {
        return constituentParticipants;
    }

    public void setConstituentParticipants(List<KimConcept> constituentParticipants) {
        this.constituentParticipants = constituentParticipants;
    }

    @Override
    public List<KimConcept> getCountablesCreated() {
        return countablesCreated;
    }

    public void setCountablesCreated(List<KimConcept> countablesCreated) {
        this.countablesCreated = countablesCreated;
    }

    @Override
    public List<ApplicableConcept> getSubjectsLinked() {
        return subjectsLinked;
    }

    public void setSubjectsLinked(List<ApplicableConcept> subjectsLinked) {
        this.subjectsLinked = subjectsLinked;
    }

    @Override
    public List<KimConcept> getQualitiesAffected() {
        return qualitiesAffected;
    }

    public void setQualitiesAffected(List<KimConcept> qualitiesAffected) {
        this.qualitiesAffected = qualitiesAffected;
    }

    @Override
    public void visitDeclarations(Visitor visitor) {

        for (ApplicableConcept applicable : appliesTo) {
            applicable.visit(visitor);
        }

        for (KimConcept c : constituentParticipants) {
            c.visit(visitor);
        }
        for (KimConcept c : countablesCreated) {
            c.visit(visitor);
        }
        for (KimConcept c : exposedTraits) {
            c.visit(visitor);
        }
        for (Pair<KimConcept, DescriptionType> c : observablesDescribed) {
            c.getFirst().visit(visitor);
        }
        for (ParentConcept parent : parents) {
            for (IKimConcept concept : parent.concepts) {
                concept.visit(visitor);
            }
        }
        for (KimConcept c : partParticipants) {
            c.visit(visitor);
        }
        for (KimConcept c : qualitiesAffected) {
            c.visit(visitor);
        }
        for (KimConcept c : requiredAttributes) {
            c.visit(visitor);
        }
        for (KimConcept c : requiredExtents) {
            c.visit(visitor);
        }
        for (KimConcept c : requiredIdentities) {
            c.visit(visitor);
        }
        for (KimConcept c : requiredRealms) {
            c.visit(visitor);
        }
        for (KimRestriction c : restrictions) {
            c.visit(visitor);
        }
        for (ApplicableConcept a : subjectsLinked) {
            a.visit(visitor);
        }
        for (KimConcept c : traitsConferred) {
            c.visit(visitor);
        }
        for (KimConcept c : traitsInherited) {
            c.visit(visitor);
        }

    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String getAuthorityTerm() {
        return authorityTerm;
    }

    public void setAuthority(String string) {
        this.authority = string;
    }

    public void setAuthorityTerm(String string) {
        this.authorityTerm = string;
    }

    @Override
    public List<IKimObservable> getTraitsExposed() {
        return traitsExposed;
    }

    public void setTraitsExposed(List<IKimObservable> traitsExposed) {
        this.traitsExposed = traitsExposed;
    }

    @Override
    public boolean isDefiningExposedTraits() {
        return definingExposedTraits;
    }

    public void setDefiningExposedTraits(boolean definingExposedTraits) {
        this.definingExposedTraits = definingExposedTraits;
    }

    public void addRole(IKimConcept role, IKimConcept target, IKimConcept restricted) {
        RoleDescriptor rd = new RoleDescriptor();
        rd.role = role;
        rd.target = target;
        rd.within = restricted;
        roles.add(rd);
    }

    public List<RoleDescriptor> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDescriptor> roles) {
        this.roles = roles;
    }

}
