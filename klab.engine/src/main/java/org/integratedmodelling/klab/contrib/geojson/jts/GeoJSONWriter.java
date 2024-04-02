package org.integratedmodelling.klab.contrib.geojson.jts;

import java.util.List;

import org.integratedmodelling.klab.contrib.geojson.Feature;
import org.locationtech.jts.geom.*;

public class GeoJSONWriter {

    final static GeoJSONReader reader = new GeoJSONReader();

    public org.integratedmodelling.klab.contrib.geojson.Geometry write(Geometry geometry) {
        Class<? extends Geometry> c = geometry.getClass();
        if (c.equals(Point.class))
            return convert((Point) geometry);
        else if (c.equals(LineString.class))
            return convert((LineString) geometry);
        else if (c.equals(LinearRing.class))
            return convert((LinearRing) geometry);
        else if (c.equals(Polygon.class))
            return convert((Polygon) geometry);
        else if (c.equals(MultiPoint.class))
            return convert((MultiPoint) geometry);
        else if (c.equals(MultiLineString.class))
            return convert((MultiLineString) geometry);
        else if (c.equals(MultiPolygon.class))
            return convert((MultiPolygon) geometry);
        else if (c.equals(GeometryCollection.class))
            return convert((GeometryCollection) geometry);
        else
            throw new UnsupportedOperationException();
    }

    public org.integratedmodelling.klab.contrib.geojson.FeatureCollection write(List<Feature> features) {
        int size = features.size();
        org.integratedmodelling.klab.contrib.geojson.Feature[] featuresJson = new org.integratedmodelling.klab.contrib.geojson.Feature[size];
        for (int i = 0; i < size; i++)
            featuresJson[i] = features.get(i);
        return new org.integratedmodelling.klab.contrib.geojson.FeatureCollection(featuresJson);
    }

    org.integratedmodelling.klab.contrib.geojson.Point convert(Point point) {
        return new org.integratedmodelling.klab.contrib.geojson.Point(convert(point.getCoordinate()));
    }

    org.integratedmodelling.klab.contrib.geojson.MultiPoint convert(MultiPoint multiPoint) {
        return new org.integratedmodelling.klab.contrib.geojson.MultiPoint(
            convert(multiPoint.getCoordinates()));
    }

    org.integratedmodelling.klab.contrib.geojson.LineString convert(LineString lineString) {
        return new org.integratedmodelling.klab.contrib.geojson.LineString(
            convert(lineString.getCoordinates()));
    }

    org.integratedmodelling.klab.contrib.geojson.LineString convert(LinearRing ringString) {
        return new org.integratedmodelling.klab.contrib.geojson.LineString(
            convert(ringString.getCoordinates()));
    }

    org.integratedmodelling.klab.contrib.geojson.MultiLineString convert(MultiLineString multiLineString) {
        int size = multiLineString.getNumGeometries();
        double[][][] lineStrings = new double[size][][];
        for (int i = 0; i < size; i++)
            lineStrings[i] = convert(multiLineString.getGeometryN(i).getCoordinates());
        return new org.integratedmodelling.klab.contrib.geojson.MultiLineString(lineStrings);
    }

    org.integratedmodelling.klab.contrib.geojson.Polygon convert(Polygon polygon) {
        int size = polygon.getNumInteriorRing() + 1;
        double[][][] rings = new double[size][][];
        rings[0] = convert(polygon.getExteriorRing().getCoordinates());
        for (int i = 0; i < size - 1; i++)
            rings[i + 1] = convert(polygon.getInteriorRingN(i).getCoordinates());
        return new org.integratedmodelling.klab.contrib.geojson.Polygon(rings);
    }

    org.integratedmodelling.klab.contrib.geojson.MultiPolygon convert(MultiPolygon multiPolygon) {
        int size = multiPolygon.getNumGeometries();
        double[][][][] polygons = new double[size][][][];
        for (int i = 0; i < size; i++)
            polygons[i] = convert((Polygon) multiPolygon.getGeometryN(i)).getCoordinates();
        return new org.integratedmodelling.klab.contrib.geojson.MultiPolygon(polygons);
    }

    org.integratedmodelling.klab.contrib.geojson.GeometryCollection convert(GeometryCollection gc) {
        int size = gc.getNumGeometries();
        org.integratedmodelling.klab.contrib.geojson.Geometry[] geometries = new org.integratedmodelling.klab.contrib.geojson.Geometry[size];
        for (int i = 0; i < size; i++)
            geometries[i] = write((Geometry) gc.getGeometryN(i));
        return new org.integratedmodelling.klab.contrib.geojson.GeometryCollection(geometries);
    }

    double[] convert(Coordinate coordinate) {
        if (Double.isNaN( coordinate.getZ() ))
            return new double[] { coordinate.x, coordinate.y };
        else
            return new double[] { coordinate.x, coordinate.y, coordinate.getZ() };
    }

    double[][] convert(Coordinate[] coordinates) {
        double[][] array = new double[coordinates.length][];
        for (int i = 0; i < coordinates.length; i++)
            array[i] = convert(coordinates[i]);
        return array;
    }
}
