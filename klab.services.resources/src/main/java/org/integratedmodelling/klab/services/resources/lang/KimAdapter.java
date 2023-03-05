package org.integratedmodelling.klab.services.resources.lang;

import java.util.stream.Collectors;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimLoader.NamespaceDescriptor;
import org.integratedmodelling.klab.api.collections.KLiteral;
import org.integratedmodelling.klab.api.collections.impl.Literal;
import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.collections.impl.Parameters;
import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.mediation.KValueMediator;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.exceptions.KIllegalArgumentException;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.impl.Geometry;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.knowledge.KResource;
import org.integratedmodelling.klab.api.knowledge.SemanticRole;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.KContextualizable;
import org.integratedmodelling.klab.api.lang.KServiceCall;
import org.integratedmodelling.klab.api.lang.UnarySemanticOperator;
import org.integratedmodelling.klab.api.lang.ValueOperator;
import org.integratedmodelling.klab.api.lang.impl.Contextualizable;
import org.integratedmodelling.klab.api.lang.kim.KKimClassification;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept.Expression;
import org.integratedmodelling.klab.api.lang.kim.KKimExpression;
import org.integratedmodelling.klab.api.lang.kim.KKimLookupTable;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable.ResolutionException;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement.Scope;
import org.integratedmodelling.klab.api.lang.kim.impl.KimAcknowledgement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimConcept;
import org.integratedmodelling.klab.api.lang.kim.impl.KimConceptStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimModelStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimNamespace;
import org.integratedmodelling.klab.api.lang.kim.impl.KimObservable;
import org.integratedmodelling.klab.api.lang.kim.impl.KimStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimSymbolDefinition;
import org.integratedmodelling.klab.utils.Utils;

public class KimAdapter {

    public static KimNamespace adaptKimNamespace(NamespaceDescriptor ns) {

        IKimNamespace original = ns.getNamespace();
        KimNamespace ret = new KimNamespace();
        Utils.Kim.copyStatementData(original, ret);

        ret.setName(original.getName());
        for (ICompileNotification notification : ns.getIssues()) {
            // TODO
        }

        ret.setMetadata(Utils.Kim.makeMetadata(ns.getNamespace().getMetadata()));

        switch(ns.getNamespace().getScope()) {
        case NAMESPACE:
            ret.setScope(Scope.PRIVATE);
            break;
        case PROJECT:
            ret.setScope(Scope.PROJECT_PRIVATE);
            break;
        default:
            ret.setScope(Scope.PUBLIC);
            break;
        }

        for (IKimScope statement : original.getChildren()) {
            ret.getStatements().add(makeStatement(statement));
        }

        return ret;
    }

    public static KimStatement makeStatement(IKimScope statement) {

        if (statement instanceof IKimConceptStatement) {
            return adaptConceptStatement((IKimConceptStatement) statement);
        } else if (statement instanceof IKimModel) {
            return adaptModelStatement((IKimModel) statement);
        } else if (statement instanceof IKimSymbolDefinition) {
            return adaptSymbolDefinition((IKimSymbolDefinition) statement);
        }  else if (statement instanceof IKimAcknowledgement) {
            return adaptAcknowledgementStatement((IKimAcknowledgement) statement);
        } 
        throw new KIllegalArgumentException("statement " + statement + " cannot be understood");
    }

    private static KimStatement adaptAcknowledgementStatement(IKimAcknowledgement statement) {
        
        KimAcknowledgement ret = new KimAcknowledgement();
        Utils.Kim.copyStatementData(statement, ret);
        
        ret.setDocstring(statement.getDocstring());
        ret.setName(statement.getName());
        ret.setObservable(adaptKimObservable(statement.getObservable()));
        for (IKimObservable state : statement.getStates()) {
            ret.getStates().add(adaptKimObservable(state));
        }
        ret.setUrn(statement.getUrn());
        
        ret.setUri(ret.getNamespace() + ":" + ret.getName());
        
        return ret;
    }

    public static KimObservable adaptKimObservable(IKimObservable parsed) {

        KimObservable ret = new KimObservable();
        Utils.Kim.copyStatementData(parsed, ret);

        ret.setAttributeIdentifier(parsed.hasAttributeIdentifier() ? parsed.getValue().toString() : null);
        ret.setValue(parsed.hasAttributeIdentifier()
                ? null
                : (parsed.getValue() == null ? null : Literal.of(adapt(parsed.getValue()))));
        ret.setCodeName(parsed.getCodeName());
        ret.setCurrency(parsed.getCurrency());
        ret.setDefaultValue(parsed.getDefaultValue() == null ? null : Literal.of(adapt(parsed.getDefaultValue())));
        ret.setDefinition(parsed.getDefinition());
        ret.setExclusive(parsed.isExclusive());
        ret.setFormalName(parsed.getFormalName());
        ret.setGeneric(parsed.isGeneric());
        ret.setGlobal(parsed.isGlobal());
        ret.setMain(adaptKimConcept(parsed.getMain()));
        ret.setModelReference(parsed.getModelReference());
        ret.setNonSemanticType(
                parsed.getNonSemanticType() == null ? null : KArtifact.Type.valueOf(parsed.getNonSemanticType().name()));
        ret.setOptional(parsed.isOptional());
        ret.setRange(parsed.getRange() == null
                ? null
                : new Range(parsed.getRange().getLowerBound(), parsed.getRange().getUpperBound(),
                        parsed.getRange().isLeftBounded(), parsed.getRange().isRightBounded()));
        ret.getResolutionExceptions().addAll(parsed.getResolutionExceptions().stream()
                .map((t) -> ResolutionException.valueOf(t.name())).collect(Collectors.toSet()));
        ret.setUnit(parsed.getUnit());
        for (org.integratedmodelling.klab.utils.Pair<org.integratedmodelling.kim.api.ValueOperator, Object> vop : parsed
                .getValueOperators()) {
            ret.getValueOperators().add(new Pair<ValueOperator, KLiteral>(ValueOperator.valueOf(vop.getFirst().name()),
                    Literal.of(adapt(vop.getSecond()))));
        }
        
        ret.setUri(ret.getDefinition());

        return ret;
    }

    private static Object adapt(Object object) {
        if (object instanceof IKimScope) {
            if (object instanceof IKimConcept) {
                object = adaptKimConcept((IKimConcept) object);
            } else if (object instanceof IKimObservable) {
                object = adaptKimObservable((IKimObservable) object);
            } // TODO continue for all possible literals
        }
        return object;
    }

    public static KimConcept adaptKimConcept(IKimConcept original) {

        KimConcept ret = new KimConcept();
        Utils.Kim.copyStatementData(original, ret);

        ret.setObservable(original.getObservable() == null ? null : adaptKimConcept(original.getObservable()));

        ret.setFundamentalType(
                original.getFundamentalType() == null ? null : SemanticType.valueOf(original.getFundamentalType().name()));
        ret.getType().addAll(original.getType().stream().map((t) -> SemanticType.valueOf(t.name())).collect(Collectors.toSet()));
        ret.setAuthority(original.getAuthority());
        ret.setAuthorityTerm(original.getAuthorityTerm());
        ret.setCodeName(original.getCodeName());
        ret.setDefinition(original.getDefinition());
        ret.setName(original.getName());
        ret.setNegated(original.isNegated());
        ret.setSemanticModifier(original.getSemanticModifier() == null
                ? null
                : UnarySemanticOperator.valueOf(original.getSemanticModifier().name()));
        ret.setSemanticRole(original.getDistributedInherent() == null
                ? null
                : SemanticRole.valueOf(original.getDistributedInherent().name()));
        ret.setTemplate(original.isTemplate());
        ret.setTraitObservable(original.isTraitObservable());
        ret.setExpressionType(Expression.valueOf(original.getExpressionType().name()));

        ret.getRoles().addAll(original.getRoles().stream().map((t) -> adaptKimConcept(t)).collect(Collectors.toList()));
        ret.getTraits().addAll(original.getTraits().stream().map((t) -> adaptKimConcept(t)).collect(Collectors.toList()));

        if (ret.getExpressionType() != Expression.SINGLETON) {
            ret.getOperands().addAll(original.getOperands().stream().map((t) -> adaptKimConcept(t)).collect(Collectors.toList()));
        }

        ret.setAdjacent(original.getAdjacent() == null ? null : adaptKimConcept(original.getAdjacent()));
        ret.setComparisonConcept(
                original.getComparisonConcept() == null ? null : adaptKimConcept(original.getComparisonConcept()));
        ret.setCompresent(original.getCompresent() == null ? null : adaptKimConcept(original.getCompresent()));
        ret.setContext(original.getContext() == null ? null : adaptKimConcept(original.getContext()));
        ret.setCooccurrent(original.getCooccurrent() == null ? null : adaptKimConcept(original.getCooccurrent()));
        ret.setCausant(original.getCausant() == null ? null : adaptKimConcept(original.getCausant()));
        ret.setCaused(original.getCaused() == null ? null : adaptKimConcept(original.getCaused()));
        ret.setInherent(original.getInherent() == null ? null : adaptKimConcept(original.getInherent()));
        ret.setMotivation(original.getMotivation() == null ? null : adaptKimConcept(original.getMotivation()));
        ret.setRelationshipSource(
                original.getRelationshipSource() == null ? null : adaptKimConcept(original.getRelationshipSource()));
        ret.setRelationshipTarget(
                original.getRelationshipTarget() == null ? null : adaptKimConcept(original.getRelationshipTarget()));
        ret.setTemporalInherent(original.getTemporalInherent() == null ? null : adaptKimConcept(original.getTemporalInherent()));

        ret.setUri(ret.getDefinition());
        
        return ret;
    }

    public static KimSymbolDefinition adaptSymbolDefinition(IKimSymbolDefinition statement) {

        KimSymbolDefinition ret = new KimSymbolDefinition();
        Utils.Kim.copyStatementData(statement, ret);

        ret.setDefineClass(statement.getDefineClass());
        ret.setName(statement.getName());
        ret.setValue(Literal.of(adapt(statement.getValue())));

        return ret;
    }

    public static KimModelStatement adaptModelStatement(IKimModel statement) {

        KimModelStatement ret = new KimModelStatement();
        Utils.Kim.copyStatementData(statement, ret);

        for (IContextualizable contextualizable : statement.getContextualization()) {
            ret.getContextualization().add(adaptContextualization(contextualizable));
        }

        for (IKimObservable dep : statement.getObservables()) {
            ret.getObservables().add(adaptKimObservable(dep));
        }
        for (IKimObservable dep : statement.getDependencies()) {
            ret.getDependencies().add(adaptKimObservable(dep));
        }

        ret.setDocstring(statement.getDocstring());
        ret.setInlineValue(statement.getInlineValue() == null ? null : Literal.of(statement.getInlineValue()));
        ret.setInstantiator(statement.isInstantiator());
        ret.setInterpreter(statement.isInterpreter());
        ret.setLearningModel(statement.isLearningModel());
        ret.setName(statement.getName());
        ret.setReinterpretingRole(
                statement.getReinterpretingRole().isEmpty() ? null : adaptKimConcept(statement.getReinterpretingRole().get()));
        if (statement.getResourceUrns() != null) {
            ret.getResourceUrns().addAll(statement.getResourceUrns());
        }
        ret.setSemantic(statement.isSemantic());
        ret.setType(statement.isInactive() ? KArtifact.Type.VOID : KArtifact.Type.valueOf(statement.getType().name()));
        ret.setUri(ret.getNamespace() + ":" + ret.getName());
        
        return ret;
    }

    private static KContextualizable adaptContextualization(IContextualizable contextualizable) {

        Contextualizable ret = new Contextualizable();
        Utils.Kim.copyStatementData(contextualizable, ret);

        ret.setAccordingTo(contextualizable.getAccordingTo());
        ret.setClassification(
                contextualizable.getClassification() == null ? null : adaptClassification(contextualizable.getClassification()));
        ret.setCondition(
                contextualizable.getCondition() == null ? null : adaptContextualization(contextualizable.getCondition()));
        ret.setConversion(contextualizable.getConversion() == null
                ? null
                : new Pair<KValueMediator, KValueMediator>(adaptMediator(contextualizable.getConversion().getFirst()),
                        adaptMediator(contextualizable.getConversion().getSecond())));
        ret.setEmpty(contextualizable.isEmpty());
        ret.setExpression(contextualizable.getExpression() == null ? null : adaptKimExpression(contextualizable.getExpression()));
        ret.setFinal(contextualizable.isFinal());
        ret.setGeometry(contextualizable.getGeometry() == null ? null : adaptGeometry(contextualizable.getGeometry()));
        ret.setInputs(contextualizable.getInputs().stream()
                .map((c) -> new Pair<>(c.getFirst(), KArtifact.Type.valueOf(c.getSecond().name()))).collect(Collectors.toList()));
        ret.getInteractiveParameters().addAll(contextualizable.getInteractiveParameters());
        ret.setLanguage(contextualizable.getLanguage());
        ret.setLiteral(contextualizable.getLiteral() == null ? null : Literal.of(contextualizable.getLiteral()));
        ret.setLookupTable(
                contextualizable.getLookupTable() == null ? null : adaptLookupTable(contextualizable.getLookupTable()));
        ret.setMediation(contextualizable.isMediation());
        ret.setMediationTargetId(contextualizable.getMediationTargetId());
        ret.setNegated(contextualizable.isNegated());
        ret.setParameters(
                contextualizable.getParameters() == null ? null : new Parameters<String>(contextualizable.getParameters()));
        ret.setResource(contextualizable.getResource() == null ? null : adaptResource(contextualizable.getResource()));
        ret.setServiceCall(
                contextualizable.getServiceCall() == null ? null : adaptServiceCall(contextualizable.getServiceCall()));
        ret.setTarget(contextualizable.getTarget() == null ? null : adaptObservable(contextualizable.getTarget()));
        ret.setTargetId(contextualizable.getTargetId());
        ret.setType(KContextualizable.Type.valueOf(contextualizable.getType().name()));
        ret.setUrn(contextualizable.getUrn());
        ret.setVariable(contextualizable.isVariable());
        return ret;
    }

    private static KValueMediator adaptMediator(IValueMediator first) {
        // TODO Auto-generated method stub
        return null;
    }

    private static KKimExpression adaptKimExpression(IKimExpression expression) {
        // TODO Auto-generated method stub
        return null;
    }

    private static KKimObservable adaptObservable(IObservable target) {
        IKimObservable parsed = Kim.INSTANCE.declare(target.getDefinition());
        return parsed == null ? null : adaptKimObservable(parsed);
    }

    private static KServiceCall adaptServiceCall(IServiceCall serviceCall) {
        // TODO Auto-generated method stub
        return null;
    }

    private static KResource adaptResource(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    private static KKimLookupTable adaptLookupTable(IKimLookupTable lookupTable) {
        // TODO Auto-generated method stub
        return null;
    }

    private static KGeometry adaptGeometry(IGeometry geometry) {
        return Geometry.create(geometry.encode());
    }

    private static KKimClassification adaptClassification(IKimClassification classification) {
        // TODO Auto-generated method stub
        return null;
    }

    private static KimConceptStatement adaptConceptStatement(IKimConceptStatement statement) {
        KimConceptStatement ret = new KimConceptStatement();
        Utils.Kim.copyStatementData(statement, ret);

        // setAbstract(boolean)
        // setAlias(boolean)
        // setAppliesTo(List<ApplicableConcept>)
        // setAuthorityDefined(String)
        // setAuthorityRequired(String)
        // setDocstring(String)
        // setEmergenceTriggers(List<KKimConcept>)
        // setMacro(boolean)
        // setName(String)
        // setObservablesCreated(List<KKimConcept>)
        // setObservablesDescribed(List<Pair<KKimConcept, DescriptionType>>)
        // setQualitiesAffected(List<KKimConcept>)
        // setRequiredAttributes(List<KKimConcept>)
        // setRequiredExtents(List<KKimConcept>)
        // setRequiredIdentities(List<KKimConcept>)
        // setRequiredRealms(List<KKimConcept>)
        // setRestrictions(List<KKimRestriction>)
        // setSubjectsLinked(List<ApplicableConcept>)
        // setTraitsConferred(List<KKimConcept>)
        // setTraitsInherited(List<KKimConcept>)
        // setType(Set<SemanticType>)
        // setUpperConceptDefined(String)

        return ret;
    }

}
