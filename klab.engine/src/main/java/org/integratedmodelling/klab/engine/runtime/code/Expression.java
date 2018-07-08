package org.integratedmodelling.klab.engine.runtime.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

public abstract class Expression implements IExpression {

    // A dummy context to use when we don't have a context to pass
    private static class Context extends Parameters<String> implements IComputationContext {

        private IMonitor monitor;
        private INamespace namespace;

        Context(IMonitor monitor) {
            this.monitor = monitor;
        }

        Context(IMonitor monitor, INamespace namespace) {
            this.monitor = monitor;
            this.namespace = namespace;
        }

        @Override
        public INamespace getNamespace() {
            return this.namespace;
        }

        @Override
        public IProvenance getProvenance() {
            return null;
        }

        @Override
        public IEventBus getEventBus() {
            return null;
        }

        @Override
        public Collection<IRelationship> getOutgoingRelationships(ISubject observation) {
            return new ArrayList<>();
        }

        @Override
        public Collection<IRelationship> getIncomingRelationships(ISubject observation) {
            return new ArrayList<>();
        }

        @Override
        public IArtifact getArtifact(String localName) {
            return null;
        }

        @Override
        public IMonitor getMonitor() {
            return monitor;
        }

        @Override
        public IObjectArtifact newObservation(IObservable observable, String name, IScale scale) {
            return null;
        }

        @Override
        public IObjectArtifact newRelationship(IObservable observable, String name, IScale scale,
                IObjectArtifact source, IObjectArtifact target) {
            return null;
        }

        @Override
        public Type getArtifactType() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type) {
            return new ArrayList<>();
        }

        @Override
        public IScale getScale() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Collection<String> getInputs() {
            return new HashSet<>();
        }

        @Override
        public Collection<String> getOutputs() {
            return new HashSet<>();
        }

        @Override
        public IObservable getSemantics(String identifier) {
            return null;
        }

        @Override
        public IArtifact getTargetArtifact() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T extends IArtifact> T getArtifact(String localName, Class<T> cls) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IObservable getTargetSemantics() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getTargetName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public ISubject getSourceSubject(IRelationship relationship) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public ISubject getTargetSubject(IRelationship relationship) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IDirectObservation getContextObservation() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IDirectObservation getParentOf(IObservation observation) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Collection<IObservation> getChildrenOf(IObservation observation) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IScheduler getScheduler() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    public static IComputationContext emptyContext(IMonitor monitor) {
        return new Context(monitor);
    }

    public static IComputationContext emptyContext(IMonitor monitor, INamespace namespace) {
        return new Context(monitor, namespace);
    }
}
