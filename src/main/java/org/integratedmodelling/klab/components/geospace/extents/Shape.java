package org.integratedmodelling.klab.components.geospace.extents;

import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;

import com.vividsolutions.jts.geom.Geometry;

public class Shape implements IShape {

    private Geometry   geometry;
    private IProjection projection;

    public static Shape fromWKT(String wkt) {
        return null;
    }

    private Shape() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public IProjection getProjection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getArea(IUnit unit) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public org.integratedmodelling.klab.api.observations.scale.space.IShape transform(IProjection projection) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IEnvelope getEnvelope() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public org.integratedmodelling.klab.api.observations.scale.space.IShape intersection(org.integratedmodelling.klab.api.observations.scale.space.IShape other) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public org.integratedmodelling.klab.api.observations.scale.space.IShape union(org.integratedmodelling.klab.api.observations.scale.space.IShape other) {
        // TODO Auto-generated method stub
        return null;
    }

}
