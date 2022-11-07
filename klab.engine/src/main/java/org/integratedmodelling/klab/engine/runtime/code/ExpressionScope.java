package org.integratedmodelling.klab.engine.runtime.code;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerScope;
import org.integratedmodelling.klab.api.data.general.IExpression.Forcing;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

public class ExpressionScope implements IExpression.Scope {

    private Type returnType = Type.VALUE;
    private INamespace namespace;
    private Set<String> identifiers = new HashSet<>();
    private Set<String> stateIdentifiers = new HashSet<>();
    // FIXME switch to ARTIFACT TYPES!
    private Map<String, Type> identifierTypes = new HashMap<>();
    private IScale scale;
    private IMonitor monitor;
    private CompilerScope compilerScope;
    private boolean forcedScalar;
    private IRuntimeScope runtimeScope;

    public static ExpressionScope create(IRuntimeScope context) {

        ExpressionScope ret = new ExpressionScope();

        ret.scale = context.getScale();
        ret.monitor = context.getMonitor();
        ret.returnType = context.getArtifactType();
        ret.compilerScope = context.getTargetArtifact() instanceof IState ? CompilerScope.Scalar : CompilerScope.Contextual;
        ret.runtimeScope = context;

        /*
         * use the catalog
         */
        for (Entry<String, IObservation> artifact : context.getLocalCatalog(IObservation.class).entrySet()) {

            String name = artifact.getKey();
            String fname = artifact.getValue().getObservable().getName();
            ret.identifiers.add(name);
            ret.identifiers.add(fname);
            if (artifact.getValue() instanceof IState) {
                ret.stateIdentifiers.add(name);
                ret.stateIdentifiers.add(fname);
            }
            ret.identifierTypes.put(name, Observables.INSTANCE.getObservableType(artifact.getValue().getObservable(), true));
            ret.identifierTypes.put(fname, Observables.INSTANCE.getObservableType(artifact.getValue().getObservable(), true));
        }

        return ret;
    }

    public static ExpressionScope create(IRuntimeScope context, IObservable targetObservable) {

        ExpressionScope ret = create(context);
        if (targetObservable == null) {
            targetObservable = context.getTargetSemantics();
        }
        IObservation targetArtifact = (IObservation) context.getTargetArtifact();
        if (targetObservable != null && (targetArtifact == null || !targetObservable.is(targetArtifact.getObservable()))) {
            targetArtifact = context.getObservation(targetObservable);
        }
        if (ret.returnType == Type.PROCESS && targetArtifact instanceof IState) {
            ret.returnType = Type.QUALITY;
        }
        return ret;
    }

    private ExpressionScope() {
    }

    public ExpressionScope(ExpressionScope expressionScope) {
        this.returnType = expressionScope.returnType;
        this.namespace = expressionScope.namespace;
        this.identifiers.addAll(expressionScope.identifiers);
        this.stateIdentifiers.addAll(expressionScope.stateIdentifiers);
        this.identifierTypes.putAll(expressionScope.identifierTypes);
        this.compilerScope = expressionScope.compilerScope;
        this.scale = expressionScope.scale;
        this.monitor = expressionScope.monitor;
        this.runtimeScope = expressionScope.runtimeScope;
    }

    public static ExpressionScope empty(IMonitor monitor) {
        ExpressionScope ret = new ExpressionScope();
        ret.monitor = monitor;
        ret.compilerScope = CompilerScope.Scalar;
        return ret;
    }

    @Override
    public Type getReturnType() {
        return this.returnType;
    }

    @Override
    public INamespace getNamespace() {
        return this.namespace;
    }

    @Override
    public Collection<String> getIdentifiers() {
        return this.identifiers;
    }

    @Override
    public Collection<String> getStateIdentifiers() {
        return this.stateIdentifiers;
    }

    @Override
    public Type getIdentifierType(String identifier) {
        return this.identifierTypes.get(identifier);
    }

    @Override
    public IScale getScale() {
        return scale;
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    @Override
    public void addKnownIdentifier(String id, IKimConcept.Type type) {
        this.identifiers.add(id);
        this.identifierTypes.put(id, type);
    }

//    public ExpressionScope withKnownIdentifier(String id, IKimConcept.Type type) {
//        this.identifiers.add(id);
//        this.identifierTypes.put(id, type);
//        return this;
//    }

    public ExpressionScope withCompilerScope(CompilerScope scope) {
        this.compilerScope = scope;
        return this;
    }

    @Override
    public CompilerScope getCompilerScope() {
        return compilerScope;
    }

    @Override
    public ExpressionScope scalar(Forcing forcing) {
        ExpressionScope ret = new ExpressionScope(this);
        ret.compilerScope = CompilerScope.Scalar;
        if (forcing == Forcing.Always) {
            ret.forcedScalar = true;
        }
        return ret;
    }

    @Override
    public boolean isForcedScalar() {
        return forcedScalar;
    }

    @Override
    public IRuntimeScope getRuntimeScope() {
        return runtimeScope;
    }

}
