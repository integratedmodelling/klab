package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.collections.KLiteral;
import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.knowledge.KArtifact.Type;
import org.integratedmodelling.klab.api.lang.KContextualizable;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimModelStatement;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;

public class KimModelStatement extends KimActiveStatement implements KKimModelStatement {

    private static final long serialVersionUID = -6068429551009652469L;
    private KKimConcept reinterpretingRole;
    private List<KKimObservable> dependencies = new ArrayList<>();
    private List<KKimObservable> observables = new ArrayList<>();
    private Type type;
    private List<String> resourceUrns = new ArrayList();
    private boolean learningModel;
    private boolean interpreter;
    private boolean instantiator;
    private String name;
    private KLiteral inlineValue;
    private List<KContextualizable> contextualization = new ArrayList<>();
    private String docstring;
    private boolean semantic;

    @Override
    public KKimConcept getReinterpretingRole() {
        return this.reinterpretingRole;
    }

    @Override
    public List<KKimObservable> getDependencies() {
        return this.dependencies;
    }

    @Override
    public List<KKimObservable> getObservables() {
        return this.observables;
    }

    @Override
    public KArtifact.Type getType() {
        return this.type;
    }

    @Override
    public List<String> getResourceUrns() {
        return this.resourceUrns;
    }

    @Override
    public boolean isLearningModel() {
        return this.learningModel;
    }

    @Override
    public boolean isInterpreter() {
        return this.interpreter;
    }

    @Override
    public boolean isInstantiator() {
        return this.instantiator;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public KLiteral getInlineValue() {
        return this.inlineValue;
    }

    @Override
    public List<KContextualizable> getContextualization() {
        return this.contextualization;
    }

    @Override
    public String getDocstring() {
        return this.docstring;
    }

    @Override
    public boolean isSemantic() {
        return this.semantic;
    }

    public void setReinterpretingRole(KKimConcept reinterpretingRole) {
        this.reinterpretingRole = reinterpretingRole;
    }

    public void setDependencies(List<KKimObservable> dependencies) {
        this.dependencies = dependencies;
    }

    public void setObservables(List<KKimObservable> observables) {
        this.observables = observables;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setResourceUrns(List<String> resourceUrns) {
        this.resourceUrns = resourceUrns;
    }

    public void setLearningModel(boolean learningModel) {
        this.learningModel = learningModel;
    }

    public void setInterpreter(boolean interpreter) {
        this.interpreter = interpreter;
    }

    public void setInstantiator(boolean instantiator) {
        this.instantiator = instantiator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInlineValue(KLiteral inlineValue) {
        this.inlineValue = inlineValue;
    }

    public void setContextualization(List<KContextualizable> contextualization) {
        this.contextualization = contextualization;
    }

    public void setDocstring(String docstring) {
        this.docstring = docstring;
    }

    public void setSemantic(boolean semantic) {
        this.semantic = semantic;
    }

}
