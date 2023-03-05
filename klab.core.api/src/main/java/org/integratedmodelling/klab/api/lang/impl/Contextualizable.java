package org.integratedmodelling.klab.api.lang.impl;

import java.util.Collection;

import org.integratedmodelling.klab.api.collections.KLiteral;
import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.data.mediation.KValueMediator;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.knowledge.KResource;
import org.integratedmodelling.klab.api.lang.KContextualizable;
import org.integratedmodelling.klab.api.lang.KServiceCall;
import org.integratedmodelling.klab.api.lang.kim.KKimClassification;
import org.integratedmodelling.klab.api.lang.kim.KKimExpression;
import org.integratedmodelling.klab.api.lang.kim.KKimLookupTable;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;
import org.integratedmodelling.klab.api.lang.kim.impl.KimStatement;
import org.integratedmodelling.klab.api.services.runtime.KNotification.Mode;

public class Contextualizable extends KimStatement implements KContextualizable {

    private static final long serialVersionUID = -1700963983184974464L;

    private Type type;
    private String targetId;
    private KResource resource;
    private KKimObservable target;
    private String mediationTargetId;
    private String language;
    private KLiteral literal;
    private KServiceCall serviceCall;
    private KKimExpression expression;
    private KKimClassification classification;
    private KKimLookupTable lookupTable;
    private String accordingTo;
    private String urn;
    private Collection<Pair<String, org.integratedmodelling.klab.api.knowledge.KArtifact.Type>> inputs;
    private KParameters<String> parameters;
    private Collection<String> interactiveParameters;
    private KContextualizable condition;
    private Pair<KValueMediator, KValueMediator> conversion;
    private boolean negated;
    private boolean mediation;
    private KGeometry geometry;
    private boolean variable;
    private boolean isFinal;
    private boolean empty;

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public String getTargetId() {
        return this.targetId;
    }

    @Override
    public KResource getResource() {
        return this.resource;
    }

    @Override
    public KKimObservable getTarget() {
        return this.target;
    }

    @Override
    public String getMediationTargetId() {
        return this.mediationTargetId;
    }

    @Override
    public String getLanguage() {
        return this.language;
    }

    @Override
    public KLiteral getLiteral() {
        return this.literal;
    }

    @Override
    public KServiceCall getServiceCall() {
        return this.serviceCall;
    }

    @Override
    public KKimExpression getExpression() {
        return this.expression;
    }

    @Override
    public KKimClassification getClassification() {
        return this.classification;
    }

    @Override
    public KKimLookupTable getLookupTable() {
        return this.lookupTable;
    }

    @Override
    public String getAccordingTo() {
        return this.accordingTo;
    }

    @Override
    public String getUrn() {
        return this.urn;
    }

    @Override
    public Collection<Pair<String, KArtifact.Type>> getInputs() {
        return this.inputs;
    }

    @Override
    public KParameters<String> getParameters() {
        return this.parameters;
    }

    @Override
    public Collection<String> getInteractiveParameters() {
        return this.interactiveParameters;
    }

    @Override
    public KContextualizable getCondition() {
        return this.condition;
    }

    @Override
    public Pair<KValueMediator, KValueMediator> getConversion() {
        return this.conversion;
    }

    @Override
    public boolean isNegated() {
        return this.negated;
    }

    @Override
    public boolean isMediation() {
        return this.mediation;
    }

    @Override
    public KGeometry getGeometry() {
        return this.geometry;
    }

    @Override
    public boolean isVariable() {
        return this.variable;
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    @Override
    public boolean isEmpty() {
        return this.empty;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public void setResource(KResource resource) {
        this.resource = resource;
    }

    public void setTarget(KKimObservable target) {
        this.target = target;
    }

    public void setMediationTargetId(String mediationTargetId) {
        this.mediationTargetId = mediationTargetId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLiteral(KLiteral literal) {
        this.literal = literal;
    }

    public void setServiceCall(KServiceCall serviceCall) {
        this.serviceCall = serviceCall;
    }

    public void setExpression(KKimExpression expression) {
        this.expression = expression;
    }

    public void setClassification(KKimClassification classification) {
        this.classification = classification;
    }

    public void setLookupTable(KKimLookupTable lookupTable) {
        this.lookupTable = lookupTable;
    }

    public void setAccordingTo(String accordingTo) {
        this.accordingTo = accordingTo;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public void setInputs(Collection<Pair<String, org.integratedmodelling.klab.api.knowledge.KArtifact.Type>> inputs) {
        this.inputs = inputs;
    }

    public void setParameters(KParameters<String> parameters) {
        this.parameters = parameters;
    }

    public void setInteractiveParameters(Collection<String> interactiveParameters) {
        this.interactiveParameters = interactiveParameters;
    }

    public void setCondition(KContextualizable condition) {
        this.condition = condition;
    }

    public void setConversion(Pair<KValueMediator, KValueMediator> conversion) {
        this.conversion = conversion;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public void setMediation(boolean mediation) {
        this.mediation = mediation;
    }

    public void setGeometry(KGeometry geometry) {
        this.geometry = geometry;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

}
