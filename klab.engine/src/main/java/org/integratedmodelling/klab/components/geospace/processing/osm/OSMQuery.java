package org.integratedmodelling.klab.components.geospace.processing.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.processing.osm.OSMSubjectInstantiator.SpatialBoundaries;
import org.integratedmodelling.klab.utils.Pair;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

/*
 * simple query builder optimized for k.LAB's needs.
 * 
 * Automatically quotes arguments; filter can have string or list value (in OR);
 *  
 */
public class OSMQuery {

	static public final int NODE = 0x01;
	static public final int WAY = 0x02;
	static public final int RELATION = 0x04;

	List<Pair<Object, Object>> equal = new ArrayList<>();
	List<Pair<Object, Object>> nequal = new ArrayList<>();
	List<Pair<Object, Object>> match = new ArrayList<>();
	List<Pair<Object, Object>> nmatch = new ArrayList<>();

	List<String> preQueries = new ArrayList<>();

	int targets = 0;
	private int timeout = 300;
	private int maxresults = 0;
	String output = "xml";
	IShape shape;
	SpatialBoundaries spatialBoundaries;

	/**
	 * Call with an OR of all the intended return types, e.g. OSMQuery(WAY |
	 * RELATION);
	 * 
	 * @param targets
	 */
	public OSMQuery(IShape where, String... what) {
		this.shape = where;
		if (what == null || what.length == 0) {
			targets |= NODE;
			targets |= WAY;
			targets |= RELATION;
		} else {
			for (String s : what) {
				if (s.equals("node") || s.equals("point")) {
					targets |= NODE;
				} else if (s.equals("line") || s.equals("way")) {
					targets |= WAY;
				} else if (s.equals("relation") || s.equals("rel")) {
					targets |= RELATION;
				} else if (s.equals("area") || s.equals("polygon")) {
					targets |= WAY;
					targets |= RELATION;
				} else {
					preQueries.add(s);
				}
			}
		}
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setMaxResult(int maxresults) {
		this.maxresults = maxresults;
	}

	public void filterEqual(Object... strings) {
		for (int i = 0; i < strings.length; i++) {
			Object key = strings[i];
			Object value = strings[++i];
			if (value instanceof List) {
				match.add(new Pair<>(key, value));
			} else {
				equal.add(new Pair<>(key, value));
			}
		}
	}

	public void filterMatch(Object... strings) {
		for (int i = 0; i < strings.length; i++) {
			match.add(new Pair<>(strings[i], strings[++i]));
		}
	}

	public void filterNotEqual(Object... strings) {
		for (int i = 0; i < strings.length; i++) {
			nequal.add(new Pair<>(strings[i], strings[++i]));
		}
	}

	public void filterNotMatch(Object... strings) {
		for (int i = 0; i < strings.length; i++) {
			nmatch.add(new Pair<>(strings[i], strings[++i]));
		}
	}

	@Override
	public String toString() {

		String ret = "[out:" + output + "][timeout:" + timeout + "];\n";

		List<String> qs = getQueries();
		if (qs.size() > 1) {
			ret += "(\n";
			for (String q : qs) {
				ret += "  " + q + "\n";
			}
			ret += ");\n";
		} else {
			ret += qs.get(0) + "\n";
		}

		ret += "out meta qt";

		if (maxresults > 0) {
			ret += " " + maxresults;
		}

		ret += ";";

		return ret;
	}

    private String getBboxQuery(String what) {
        String q = what + getFilters() + getBoundingBox() + ";";
        if (what.equals("way") || what.equals("rel")) {
            // the result set plus all the composing ways and nodes
            q += "\n(._; >;);";
        }
        return q;
    }

    private String getPolygonQuery(String what, String poly) {
        String q = what + getFilters() + "(poly:" + poly + ");";
        if (what.equals("way") || what.equals("rel")) {
            // the result set plus all the composing ways and nodes
            q += "\n(._; >;);";
        }
        return q;
    }

	private List<String> getQueries() {

		List<String> ret = new ArrayList<>();

		if (preQueries.size() > 0) {
			for (String q : preQueries) {
				String qq = q;
				if (q.endsWith(";")) {
					qq = qq.substring(qq.length() - 2);
				}
				qq += getBoundingBox() + ";";
				ret.add(qq);
			}
		}

        for(String what : getTypes()) {
            if (spatialBoundaries == SpatialBoundaries.bbox) {
                ret.add(getBboxQuery(what));
                continue;
            }
            for(String poly : getPolygons()) {
                ret.add(getPolygonQuery(what, poly));
            }
        }

		return ret;
	}

	private String getFilters() {
		String ret = "";
		if (equal.size() > 0) {
			for (String f : getFilter(equal, "=")) {
				ret += f;
			}
		}
		if (nequal.size() > 0) {
			for (String f : getFilter(nequal, "!=")) {
				ret += f;
			}
		}
		if (match.size() > 0) {
			for (String f : getFilter(match, "~")) {
				ret += f;
			}
		}
		if (nmatch.size() > 0) {
			for (String f : getFilter(nmatch, "!~")) {
				ret += f;
			}
		}
		return ret;
	}

	private Collection<String> getFilter(List<Pair<Object, Object>> conditions, String operator) {
		List<String> ret = new ArrayList<>();
		for (Pair<Object, Object> c : conditions) {

			String first = getArg(c.getFirst());
			String second = getArg(c.getSecond());

			if (c.getSecond().equals("*") && operator.equals("=")) {
				ret.add("[" + quote(first) + "]");
			} else {
				ret.add("[" + quote(first) + operator + quote(second) + "]");
			}
		}
		return ret;
	}

	private String getArg(Object first) {

		if (first instanceof List) {
			return StringUtils.join(((List<?>) first).toArray(), '|');
		}
		return first.toString();
	}

	private String quote(String first) {
		boolean q = false;
		for (int i = 0; i < first.length(); i++) {
			if (!Character.isLetter(first.charAt(i))) {
				q = true;
				break;
			}
		}
		return q ? ("\"" + first + "\"") : first;
	}

    private List<String> getPolygons() {
        List<String> polygons = new Vector<>();
        Geometry fullGeometry = ((Shape)shape).getStandardizedGeometry();
        int numberOfPolygons = fullGeometry.getNumGeometries();
        for (int i = 0; i < numberOfPolygons; i++) {
            String formattedString = "\"";
            Geometry polygon = fullGeometry.getGeometryN(i);
            Coordinate[] coordinates = polygon.getCoordinates();
            for (Coordinate coordinate : coordinates) {
                formattedString += coordinate.getY() + " " + coordinate.getX() + " ";
            }
            formattedString = formattedString.substring(0, formattedString.length() - 1) + "\"";
            polygons.add(formattedString);
        }
        return polygons;
    }

	private String getBoundingBox() {

		IEnvelope envelope = shape.getEnvelope().transform(Projection.getDefault(), true);
		return "(" + envelope.getMinY() + ", " + envelope.getMinX() + ", " + envelope.getMaxY() + ", "
				+ envelope.getMaxX() + ")";
	}

	private Collection<String> getTypes() {
		Set<String> ret = new HashSet<>();
		if ((targets & WAY) != 0) {
			ret.add("way");
		}
		if ((targets & RELATION) != 0) {
			ret.add("rel");
		}
		if ((targets & NODE) != 0) {
			ret.add("node");
		}
		return ret;
	}

    public void setSpatialBoundaries(SpatialBoundaries spatialBoundaries) {
        this.spatialBoundaries = spatialBoundaries;
    }

}
