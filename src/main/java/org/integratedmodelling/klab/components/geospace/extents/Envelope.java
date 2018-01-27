package org.integratedmodelling.klab.components.geospace.extents;

import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;

public class Envelope implements IEnvelope {

    private com.vividsolutions.jts.geom.Envelope envelope;
    private IProjection                           projection;

    public Envelope() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public IProjection getProjection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getMinX() {
        return envelope.getMinX();
    }

    @Override
    public double getMaxX() {
        return envelope.getMaxX();
    }

    @Override
    public double getMinY() {
        return envelope.getMinY();
    }

    @Override
    public double getMaxY() {
        return envelope.getMaxY();
    }

    @Override
    public IShape asShape() {
        // TODO Auto-generated method stub
        return null;
    }

}
