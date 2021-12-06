package org.integratedmodelling.klab.components.geospace.extents;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.scale.EnumeratedExtent;

public class EnumeratedSpace extends EnumeratedExtent implements ISpace {

    public EnumeratedSpace(IConcept c) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public IShape getShape() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IEnvelope getEnvelope() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IProjection getProjection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SpatialExtent getExtentDescriptor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getStandardizedVolume() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getStandardizedArea() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getStandardizedWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double[] getStandardizedCentroid() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getStandardizedHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getStandardizedDepth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getStandardizedLength() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getStandardizedDistance(ISpace extent) {
        // TODO Auto-generated method stub
        return 0;
    }

}
