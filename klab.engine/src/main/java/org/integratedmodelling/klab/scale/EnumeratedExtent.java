package org.integratedmodelling.klab.scale;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.impl.factory.Sets;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IGeometry.Encoding;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IEnumeratedExtent;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.integratedmodelling.klab.utils.Pair;

public class EnumeratedExtent extends Extent implements IEnumeratedExtent {

    private IAuthority authority;
    private IConcept baseIdentity;
    protected Set<IConcept> concepts = new LinkedHashSet<>();
    protected String originalDeclaration;

    protected EnumeratedExtent(IConcept concept) {
        this.originalDeclaration = concept.getDefinition();
        if (concept.is(IKimConcept.Type.UNION)) {
            concepts.addAll(concept.getOperands());
        } else {
            concepts.add(concept);
        }

        if (!concept.is(IKimConcept.Type.IDENTITY)) {
            throw new KlabIllegalArgumentException(
                    "enumerated space must use an identity for its concept space");
        }
        
        for (IConcept c : concepts) {
        	if (this.authority == null) {
        		this.authority = Authorities.INSTANCE.getAuthority(c);
        		this.baseIdentity = Traits.INSTANCE.getBaseParentTrait(c);
        	} else if (this.authority != Authorities.INSTANCE.getAuthority(c)) {
        		throw new KlabIllegalStateException("cannot merge concepts from different authorities");
        	}
        }
    }

    private EnumeratedExtent(EnumeratedExtent original, Collection<IConcept> concepts) {
        this.concepts.addAll(concepts);
        boolean isOr = false;
        for (IConcept c : concepts) {
            if (originalDeclaration == null) {
                originalDeclaration = c.getDefinition();
            } else {
                isOr = true;
                originalDeclaration += " or " + c.getDefinition();
            }
        }
        if (isOr) {
            originalDeclaration = "(" + originalDeclaration + ")";
        }
    }

    public EnumeratedExtent(IAuthority authority, IConcept baseConcept) {
        this.authority = authority;
        this.baseIdentity = baseConcept;
    }

    @Override
    public IExtent mergeContext(IExtent extent) {
        if (!(extent instanceof EnumeratedExtent)) {
            throw new KlabIllegalArgumentException(
                    "cannot merge an enumerated extent with a non-enumerated one");
        }
        Set<IConcept> newx = Sets.intersect(concepts, ((EnumeratedExtent) extent).concepts);
        return new EnumeratedExtent(this, newx);
    }

    private IExtent adopt(IExtent extent, IMonitor monitor) {
        if (!(extent instanceof EnumeratedExtent)) {
            throw new KlabIllegalArgumentException(
                    "cannot merge an enumerated extent with a non-enumerated one");
        }
        Set<IConcept> newx = Sets.union(concepts, ((EnumeratedExtent) extent).concepts);
        return new EnumeratedExtent(this, newx);
    }

    @Override
    public IAuthority getAuthority() {
        return authority;
    }

    @Override
    public IConcept getBaseIdentity() {
        return baseIdentity;
    }

    @Override
    public IExtent at(Object... locators) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public int getScaleRank() {
        return 0;
    }

    @Override
    public IExtent collapse() {
        return this;
    }

    @Override
    public IExtent getBoundingExtent() {
        return this;
    }

    @Override
    public double getDimensionSize(IUnit unit) {
        if (!unit.isUnitless()) {
            throw new KlabIllegalArgumentException(
                    "cannot use unit " + unit + " to measure the size of an enumerated extent");
        }
        return concepts.size();
    }

    @Override
    public Pair<Double, IUnit> getStandardizedDimension(ILocator locator) {
        return new Pair<Double, IUnit>((double) concepts.size(), Units.INSTANCE.COUNTER);
    }

    @Override
    public IScaleMediator getMediator(IExtent extent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {

        switch(how.value) {
        case LogicalConnector._UNION:
            return adopt((IExtent) other, Klab.INSTANCE.getRootMonitor());
        case LogicalConnector._INTERSECTION:
            return mergeContext((IExtent) other);
        default:
            break;
        }

        throw new KlabIllegalStateException("cannot merge enumerated extent with " + how);
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
        return concepts.size();
    }

    @Override
    public boolean contains(IExtent o) throws KlabException {
        return o instanceof EnumeratedExtent && concepts.containsAll(((EnumeratedExtent) o).concepts);
    }

    @Override
    public boolean overlaps(IExtent o) throws KlabException {
        return o instanceof EnumeratedExtent
                && Sets.intersect(concepts, ((EnumeratedExtent) o).concepts).size() > 0;
    }

    @Override
    public boolean intersects(IExtent o) throws KlabException {
        return overlaps(o);
    }

    @Override
    public boolean isGeneric() {
        return concepts.size() == 0;
    }

    @Override
    public Type getType() {
        return Type.NUMEROSITY;
    }

    @Override
    public boolean isRegular() {
        return true;
    }

    @Override
    public int getDimensionality() {
        return 1;
    }

    @Override
    public long getOffset(long... offsets) {
        return offsets[0];
    }

    @Override
    public long[] shape() {
        return new long[]{size()};
    }

    @Override
    public IParameters<String> getParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExtentDimension getExtentDimension() {
        return ExtentDimension.CONCEPTUAL;
    }

    @Override
    public boolean isConsistent() {
        return concepts.size() > 0;
    }

    @Override
    public boolean isEmpty() {
        return concepts.isEmpty();
    }

    @Override
    public long[] getDimensionSizes() {
        return shape();
    }

    @Override
    public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
        // TODO
        return null;
    }

    @Override
    public boolean isCovered(long stateIndex) {
        return stateIndex >= 0 && stateIndex < size();
    }

    @Override
    public IExtent getExtent(long stateIndex) {
        int i = 0;
        IConcept ret = null;
        for (IConcept c : concepts) {
            if (i == stateIndex) {
                ret = c;
                break;
            }
            i++;
        }
        return ret == null ? null : new EnumeratedExtent(this, Collections.singleton(ret));
    }

    @Override
    public double getCoveredExtent() {
        return size();
    }

    @Override
    public String encode(Encoding...options) {
        String ret = (isGeneric() ? "\u03b4" : "D") + "1(" + size() + "){";
        if (isConsistent()) {
            ret += "declaration=" + originalDeclaration;
        } else if (getAuthority() != null) {
            ret += "authority=" + getAuthority().getName();
        } else if (getBaseIdentity() != null) {
            ret += "baseIdentity=" + getBaseIdentity().getDefinition();
        }
        return ret + "}";
    }

    @Override
    public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {
        return merge(other, connector);
    }

    @Override
    public IExtent getExtent() {
        return this;
    }

    @Override
    public AbstractExtent copy() {
        return new EnumeratedExtent(this, concepts);
    }

    @Override
    public IServiceCall getKimSpecification() {
        return null;
    }

    @Override
    protected IExtent contextualizeTo(IExtent other, IAnnotation constraint) {
        return this;
    }

    /**
     * This is used in the database to search for compatible models (with the same domain).
     * 
     * @return
     */
    public Pair<String, String> getExtentDescriptors() {
        String domain = authority == null ? baseIdentity.getDefinition() : authority.getName();
        String value = originalDeclaration;
        return new Pair<>(domain, value);
    }

    @Override
    public Collection<IConcept> getExtension() {
        return concepts;
    }

    @Override
    public boolean isDistributed() {
        return size() > 1;
    }

}
