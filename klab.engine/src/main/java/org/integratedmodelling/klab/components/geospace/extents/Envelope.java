package org.integratedmodelling.klab.components.geospace.extents;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

public class Envelope implements IEnvelope {

    /**
     * Default minimum resolution in meters when a ROI is created from a user interacting with a
     * map.
     */
    public static final int DEFAULT_MIN_RESOLUTION = 5;

    ReferencedEnvelope envelope;
    Projection projection;
    Integer scaleRank = null;
    private IMetadata metadata;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((envelope == null) ? 0 : envelope.hashCode());
        result = prime * result + ((projection == null) ? 0 : projection.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Envelope other = (Envelope) obj;
        if (envelope == null) {
            if (other.envelope != null)
                return false;
        } else if (!envelope.equals(other.envelope))
            return false;
        if (projection == null) {
            if (other.projection != null)
                return false;
        } else if (!projection.equals(other.projection))
            return false;
        return true;
    }

    public static Envelope create(org.locationtech.jts.geom.Envelope envelope, Projection projection) {
        Envelope ret = new Envelope();
        ret.envelope = new ReferencedEnvelope(envelope, projection.getCoordinateReferenceSystem());
        ret.projection = projection;
        return ret;
    }

    public static Envelope create(org.opengis.geometry.Envelope envelope, Projection projection) {
        Envelope ret = new Envelope();
        ret.envelope = new ReferencedEnvelope(envelope);
        ret.projection = projection;
        return ret;
    }

    public static Envelope create(ReferencedEnvelope envelope) {
        Envelope ret = new Envelope();
        ret.envelope = envelope;
        ret.projection = Projection.create(envelope.getCoordinateReferenceSystem());
        return ret;
    }

    public static Envelope create(double minX, double maxX, double minY, double maxY, IProjection projection) {
        return create(new ReferencedEnvelope(minX, maxX, minY, maxY, ((Projection) projection).getCoordinateReferenceSystem()));
    }

    public Envelope copy() {
        return create(new ReferencedEnvelope(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY(),
                envelope.getCoordinateReferenceSystem()));
    }

    public static Envelope create(ReferencedEnvelope envelope, boolean swapXY) {
        Envelope ret = new Envelope();
        ret.envelope = swapXY
                ? new ReferencedEnvelope(envelope.getMinY(), envelope.getMaxY(), envelope.getMinX(), envelope.getMaxX(),
                        envelope.getCoordinateReferenceSystem())
                : new ReferencedEnvelope(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY(),
                        envelope.getCoordinateReferenceSystem());
        ret.projection = Projection.create(envelope.getCoordinateReferenceSystem());
        return ret;
    }

    public String toString() {
        return envelope.toString();
    }

    @Override
    public double metersToDistance(double metersDistance) {
        if (getProjection().isMeters()) {
            return metersDistance;
        }
        double cMeters = (getMaxX() - getMinX()) / asShape().getStandardizedWidth();
        return metersDistance * cMeters;
    }

    @Override
    public double distanceToMeters(double originalDistance) {
        if (getProjection().isMeters()) {
            return originalDistance;
        }
        double cMeters = (getMaxX() - getMinX()) / asShape().getStandardizedWidth();
        return originalDistance / cMeters;
    }

    private Envelope() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Projection getProjection() {
        return projection;
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
    public Shape asShape() {
        return Shape.create(this);
    }

    public ISpace asGrid(double resolution, String unit) {
        return (unit == null || resolution <= 0)
                ? Shape.create(this)
                : Space.create(Shape.create(this), Units.INSTANCE.METERS.convert(resolution, Unit.create(unit)).doubleValue());
    }

    public Geometry asJTSGeometry() {
        return Shape.makeCell(this.envelope.getMinX(), this.envelope.getMinY(), this.envelope.getMaxX(), this.envelope.getMaxY());
    }

    public static Envelope create(double minx, double maxx, double miny, double maxy, Projection crs) {
        return create(new ReferencedEnvelope(minx, maxx, miny, maxy, crs.getCoordinateReferenceSystem()));
    }

    @Override
    public double getWidth() {
        return envelope.getWidth();
    }

    @Override
    public double getHeight() {
        return envelope.getHeight();
    }

    @Override
    public Envelope transform(IProjection projection, boolean lenient) {

        if (projection.equals(this.projection)) {
            return this;
        }

        Envelope ret = new Envelope();
        try {
            ret.envelope = this.envelope.transform(((Projection) projection).crs, lenient);
        } catch (TransformException | FactoryException e) {
            throw new KlabValidationException(e);
        }
        ret.projection = (Projection) projection;
        return ret;
    }

    /**
     * Same as {@link #getResolutionForZoomLevel(int, double) using a default of 5 meters and the
     * default multiplier of 4.
     * 
     * @param roundTo meters to round to. Also the minimum resolution if we can't get enough screen
     *        pixels.
     * @return resolution and the correspondent unit string.
     */
    public Pair<Integer, String> getResolutionForZoomLevel() {
        return getResolutionForZoomLevel(DEFAULT_MIN_RESOLUTION, 4.0);
    }

    /**
     * Same as {@link #getResolutionForZoomLevel(int, double) using a default multiplier of 4.
     * 
     * @param roundTo meters to round to. Also the minimum resolution if we can't get enough screen
     *        pixels.
     * @return resolution and the correspondent unit string.
     */
    public Pair<Integer, String> getResolutionForZoomLevel(int roundTo) {
        return getResolutionForZoomLevel(roundTo, 4.0);
    }

    /**
     * Return a "good" grid resolution by zoom level including both the actual meters per pixel and
     * the readable unit string for it. For defaults and used when observing raw datasets.
     * Reasoning: give the envelope enough resolution to show enough square pixels to occupy half
     * the screen by computing the grid size against the m/pixel for the zoom level.
     * <p>
     * Meters per pixel at zoom level z are equal to 156412/(2^z).
     * 
     * @param roundTo meters to round to. Also the minimum resolution if we can't get enough screen
     *        pixels.
     * @param multiplier multiplier for the literal resolution. A good value for modern screens is
     *        around 4.
     * 
     * @return resolution in meters and the natural unit to use in displaying it.
     */
    public Pair<Integer, String> getResolutionForZoomLevel(int roundTo, double multiplier) {

        int zoomLevel = getScaleRank();
        int metersPerPixel = 156412 / (int) (Math.pow(2., (double) zoomLevel) * multiplier);
        int gridRounded = ((metersPerPixel + (roundTo / 2)) / roundTo) * roundTo;

        String unit = "m";
        if (gridRounded > 2000) {
            gridRounded = (((gridRounded) + 500) / 1000);
            unit = "km";
            // gridRounded *= 1000;
        } else if (gridRounded < roundTo) {
            gridRounded = roundTo;
            unit = "m";
        }

        return new Pair<>(gridRounded, unit);
    }

    @Override
    public int getScaleRank() {

        if (this.scaleRank == null) {
            Envelope envelope = transform(Projection.getLatLon(), true);

            int zoomLevel;
            double latDiff = envelope.getHeight();
            double lngDiff = envelope.getWidth();

            double maxDiff = (lngDiff > latDiff) ? lngDiff : latDiff;
            if (maxDiff < 360 / Math.pow(2, 20)) {
                zoomLevel = 21;
            } else {
                zoomLevel = (int) (-1 * ((Math.log(maxDiff) / Math.log(2)) - (Math.log(360) / Math.log(2))));
                if (zoomLevel < 1) {
                    zoomLevel = 1;
                }
            }
            this.scaleRank = zoomLevel;
        }

        return this.scaleRank;
    }

    public ReferencedEnvelope getJTSEnvelope() {
        return envelope;
    }

    public boolean intersects(IEnvelope envelope) {
        return this.envelope.intersects((org.locationtech.jts.geom.Envelope) ((Envelope) envelope).envelope);
    }

    @Override
    public double[] getCenterCoordinates() {
        return new double[]{envelope.getMedian(0), envelope.getMedian(1)};
    }

    /**
     * Encoding suitable for geometry specs
     * 
     * @return the boundary specifications
     */
    public String encode() {
        return "bbox=[" + getMinX() + " " + getMaxX() + " " + getMinY() + " " + getMaxY() + "]";
    }

    @Override
    public IEnvelope standard() {
        return transform(Projection.getDefault(), true);
    }

    @Override
    public IMetadata getMetadata() {
        if (this.metadata == null) {
            this.metadata = new Metadata();
        }
        return this.metadata;
    }

    @Override
    public IEnvelope grow(double factor) {
        if (factor != 1) {
            double xgrow = ((getWidth() * factor) - getWidth()) / 2.0;
            double ygrow = ((getHeight() * factor) - getHeight()) / 2.0;
            return create(getMinX() - xgrow, getMaxX() + xgrow, getMinY() - ygrow, getMaxY() + ygrow, getProjection());
        }
        return this;
    }

    @Override
    public boolean overlaps(IEnvelope other) {
        try {
            return this.envelope.intersects(((Envelope)other).getJTSEnvelope().toBounds(this.projection.getCRS()));
        } catch (TransformException e) {
            return false;
        }
    }

}
