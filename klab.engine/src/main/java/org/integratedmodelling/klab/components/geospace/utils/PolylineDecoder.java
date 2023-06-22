package org.integratedmodelling.klab.components.geospace.utils;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;

public class PolylineDecoder {
	
	private static final double DEFAULT_PRECISION = 1E5;
	
	public static Shape decode(String encoded) {
		return decode(encoded, DEFAULT_PRECISION);
	}

	/**
	 * Precision should be something like 1E5 or 1E6. For OSRM routes found
	 * precision was 1E6, not the original default 1E5.
	 * 
	 * FIXME precision different from 1E5 changes the magnitude!
	 *
	 * @param encoded
	 * @param precision
	 * @return
	 */
	private static Shape decode(String encoded, double precision) {
		
		List<Coordinate> track = new ArrayList<>();
		int index = 0;
		int lat = 0, lng = 0;

		while (index < encoded.length()) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			track.add(new Coordinate((double) lat / precision, (double) lng / precision));
			
		}
		
		LineString linestring = Geospace.gFactory.createLineString(track.toArray(new Coordinate[track.size()]));
		
		return Shape.create(linestring, Projection.getLatLon());
	}
	
	public static void main(String[] args) {
		System.out.println(decode("yjq~FprhvO`qDbIfuEogJ"));
	}

}