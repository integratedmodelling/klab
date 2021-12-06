package org.integratedmodelling.klab.scale;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.integratedmodelling.klab.utils.Pair;

public class EnumeratedExtent extends Extent {

    @Override
    public IExtent merge(IExtent extent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExtent adopt(IExtent extent, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExtent at(Object... locators) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getScaleRank() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IExtent collapse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExtent getBoundingExtent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getDimensionSize(IUnit unit) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Pair<Double, IUnit> getStandardizedDimension(ILocator locator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IScaleMediator getMediator(IExtent extent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGeometry getGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends ILocator> T as(Class<T> cls) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean contains(IExtent o) throws KlabException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean overlaps(IExtent o) throws KlabException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean intersects(IExtent o) throws KlabException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isGeneric() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isRegular() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getDimensionality() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getOffset(long... offsets) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long[] shape() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IParameters<String> getParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExtentDimension getExtentDimension() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConsistent() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long[] getDimensionSizes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCovered(long stateIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IExtent getExtent(long stateIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getCoveredExtent() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String encode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExtent getExtent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExtent copy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IServiceCall getKimSpecification() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected IExtent contextualizeTo(IExtent other, IAnnotation constraint) {
        // TODO Auto-generated method stub
        return null;
    }

}
