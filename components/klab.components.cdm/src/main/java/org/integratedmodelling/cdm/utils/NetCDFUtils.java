package org.integratedmodelling.cdm.utils;

import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.jai.RasterFactory;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.data.DataUtilities;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffWriteParams;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.operation.projection.MapProjection;
import org.geotools.util.NumberRange;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.data.storage.BasicFileMappedStorage;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.beust.jcommander.internal.Lists;

/*
 * GeoTools - The Open Source Java GIS Toolkit http://geotools.org
 *
 * (C) 2007-2018, Open Source Geospatial Foundation (OSGeo)
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; version 2.1 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

import it.geosolutions.imageio.stream.AccessibleStream;
import it.geosolutions.imageio.stream.input.URIImageInputStream;
import it.geosolutions.imageio.utilities.ImageIOUtilities;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.Group;
import ucar.nc2.Variable;
import ucar.nc2.constants.AxisType;
import ucar.nc2.dataset.CoordinateAxis1D;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import ucar.unidata.geoloc.ProjectionRect;

/**
 * Set of NetCDF utility methods.
 * <p>
 * Adapted for netcdf4 v5.0 and decoupled from Geotools.
 * 
 * @author Alessio Fabiani, GeoSolutions SAS
 * @author Daniele Romagnoli, GeoSolutions SAS
 * @author Simone Giannecchini, GeoSolutions SAS
 */
public class NetCDFUtils {

	static GridCoverageFactory rasterFactory = new GridCoverageFactory();

	public static final boolean CHECK_COORDINATE_PLUGINS;

	public static final String CHECK_COORDINATE_PLUGINS_KEY = "netcdf.coordinates.enablePlugins";

	public static final String NETCDF4_MIMETYPE = "application/x-netcdf4";

	public static final String NETCDF3_MIMETYPE = "application/x-netcdf";

	public static final String NETCDF = "NetCDF";

	public static final String NETCDF4 = "NetCDF4";

	public static final String NETCDF_4C = "NetCDF-4C";

	public static final String NETCDF_3 = "NetCDF-3";

	public static final String STANDARD_PARALLEL_1 = MapProjection.AbstractProvider.STANDARD_PARALLEL_1.getName()
			.getCode();

	public static final String STANDARD_PARALLEL_2 = MapProjection.AbstractProvider.STANDARD_PARALLEL_2.getName()
			.getCode();

	public static final String CENTRAL_MERIDIAN = MapProjection.AbstractProvider.CENTRAL_MERIDIAN.getName().getCode();

	public static final String LATITUDE_OF_ORIGIN = MapProjection.AbstractProvider.LATITUDE_OF_ORIGIN.getName()
			.getCode();

	public static final String SCALE_FACTOR = MapProjection.AbstractProvider.SCALE_FACTOR.getName().getCode();

	public static final String FALSE_EASTING = MapProjection.AbstractProvider.FALSE_EASTING.getName().getCode();

	public static final String FALSE_NORTHING = MapProjection.AbstractProvider.FALSE_NORTHING.getName().getCode();

	public static final String SEMI_MINOR = MapProjection.AbstractProvider.SEMI_MINOR.getName().getCode();

	public static final String SEMI_MAJOR = MapProjection.AbstractProvider.SEMI_MAJOR.getName().getCode();

	public static final String INVERSE_FLATTENING = "inverse_flattening";

	public static final String UNKNOWN = "unknown";

	public static final double DEFAULT_EARTH_RADIUS = 6371229.0d;
	/**
	 * When true, the stack trace that created a reader that wasn't closed is
	 * recorded and then printed out when warning the user about this.
	 */
	public static final Boolean TRACE_ENABLED = "true".equalsIgnoreCase(System.getProperty("gt2.netcdf.trace"));

	private NetCDFUtils() {
	}

	/** The LOGGER for this class. */
	private static final Logger LOGGER = Logger.getLogger(NetCDFUtils.class.toString());

	/**
	 * Set containing all the definition of the UNSUPPORTED DIMENSIONS to set as
	 * vertical ones
	 */
	private static final Set<String> UNSUPPORTED_DIMENSIONS;

	/** Set containing all the dimensions to be ignored */
	private static final Set<String> IGNORED_DIMENSIONS = new HashSet<>();

	/** Boolean indicating if GRIB library is available */
	private static boolean IS_GRIB_AVAILABLE;

	/** Boolean indicating if NC4 C Library is available */
	private static boolean IS_NC4_LIBRARY_AVAILABLE;

	public static final String EXTERNAL_DATA_DIR;

	public static final String NETCDF_DATA_DIR = "NETCDF_DATA_DIR";

	public static final String FILL_VALUE = "_FillValue";

	public static final String MISSING_VALUE = "missing_value";

	public static final String ACTUAL_RANGE = "actual_range";

	public static final String VALID_RANGE = "valid_range";

	public static final String VALID_MIN = "valid_min";

	public static final String VALID_MAX = "valid_max";

	public static final String LOWER_LEFT_LONGITUDE = "lower_left_longitude";

	public static final String LOWER_LEFT_LATITUDE = "lower_left_latitude";

	public static final String UPPER_RIGHT_LONGITUDE = "upper_right_longitude";

	public static final String UPPER_RIGHT_LATITUDE = "upper_right_latitude";

	public static final String COORDSYS = "latLonCoordSys";

	public static final String Y = "y";

	public static final String Y_COORD_PROJ = "y coordinate of projection";

	public static final String Y_PROJ_COORD = "projection_y_coordinate";

	public static final String X = "x";

	public static final String X_COORD_PROJ = "x coordinate of projection";

	public static final String X_PROJ_COORD = "projection_x_coordinate";

	public static final String LATITUDE = "latitude";

	public static final String LAT = "lat";

	public static final String LONGITUDE = "longitude";

	public static final String LON = "lon";

	public static final String GRID_LATITUDE = "grid_latitude";

	public static final String RLAT = "rlat";

	public static final String GRID_LONGITUDE = "grid_longitude";

	public static final String RLON = "rlon";

	public static final String DEPTH = "depth";

	public static final String ZETA = "z";

	public static final String BOUNDS = "bounds";

	private static final String BNDS = "bnds";

	public static final String HEIGHT = "height";

	public static final String TIME = "time";

	public static final String POSITIVE = "positive";

	public static final String UNITS = "units";

	public static final String NAME = "name";

	public static final String LONG_NAME = "long_name";

	// public static final String ELEVATION_DIM =
	// ImageMosaicFormat.ELEVATION.getName().toString();
	//
	// public static final String TIME_DIM =
	// ImageMosaicFormat.TIME.getName().toString();

	public static final String STANDARD_NAME = "standard_name";

	public static final String DESCRIPTION = "description";

	public static final String M = "m";

	public static final String BOUNDS_SUFFIX = "_bnds";

	public static final String LON_UNITS = "degrees_east";

	public static final String LAT_UNITS = "degrees_north";

	public static final String RLATLON_UNITS = "degrees";

	public static final String NO_COORDS = "NoCoords";

	public static final String TIME_ORIGIN = "seconds since 1970-01-01 00:00:00 UTC";

	public static final long START_TIME;

	public static final String BOUNDARY_DIMENSION = "nv";

	public static final TimeZone UTC;

	public static final String GRID_MAPPING = "grid_mapping";

	public static final String GRID_MAPPING_NAME = "grid_mapping_name";

	public static final String COORDINATE_AXIS_TYPE = "_CoordinateAxisType";

	public static final String CONVENTIONS = "Conventions";

	public static final String COORD_SYS_BUILDER = "_CoordSysBuilder";

	public static final String COORD_SYS_BUILDER_CONVENTION = "ucar.nc2.dataset.conv.CF1Convention";

	public static final String COORDINATE_TRANSFORM_TYPE = "_CoordinateTransformType";

	public static final String COORDINATES = "coordinates";

	// They are recognized from GDAL
	public static final String SPATIAL_REF = "spatial_ref";

	public static final String GEO_TRANSFORM = "GeoTransform";

	// They are recognized from CERP NetCDF Metadata convention
	// https://www.jem.gov/downloads/CERP%20NetCDF%20standard/CERP_NetCDF_Metadata_Conventions_1.2.pdf
	public static final String CERP_ESRI_PE_STRING = "esri_pe_string";

	public static final String UNIQUE_TIME_ATTRIBUTE = "uniqueTimeAttribute";

	static final Set<String> EXCLUDED_ATTRIBUTES = new HashSet<String>();

	public static final String ENHANCE_COORD_SYSTEMS = "org.geotools.coverage.io.netcdf.enhance.CoordSystems";

	public static final String ENHANCE_SCALE_MISSING = "org.geotools.coverage.io.netcdf.enhance.ScaleMissing";

	public static final String ENHANCE_CONVERT_ENUMS = "org.geotools.coverage.io.netcdf.enhance.ConvertEnums";

	public static final String ENHANCE_SCALE_MISSING_DEFER = "org.geotools.coverage.io.netcdf.enhance.ScaleMissingDefer";

	public static boolean ENHANCE_SCALE_OFFSET = false;

	public static final String STORE_NAME = "StoreName";

	/**
	 * Number of bytes at the start of a file to search for a GRIB signature. Some
	 * GRIB files have WMO headers prepended by a telecommunications gateway.
	 * NetCDF-Java Grib{1,2}RecordScanner look for the header in this many bytes.
	 */
	private static final int GRIB_SEARCH_BYTES = 16000;

	static {
		// TODO remove this block when enhance mode can be set some other way, possibly
		// via read
		// params

		// Default used to be to just enhance coord systems
		EnumSet<NetcdfDataset.Enhance> defaultEnhanceMode = EnumSet.of(NetcdfDataset.Enhance.CoordSystems);

		if (System.getProperty(ENHANCE_COORD_SYSTEMS) != null && !Boolean.getBoolean(ENHANCE_COORD_SYSTEMS)) {
			defaultEnhanceMode.remove(NetcdfDataset.Enhance.CoordSystems);
		}

		// if (Boolean.getBoolean(ENHANCE_SCALE_MISSING)) {
		// defaultEnhanceMode.add(NetcdfDataset.Enhance.ScaleMissing);
		// ENHANCE_SCALE_OFFSET = true;
		// }

		if (Boolean.getBoolean(ENHANCE_CONVERT_ENUMS)) {
			defaultEnhanceMode.add(NetcdfDataset.Enhance.ConvertEnums);
		}

		// if (Boolean.getBoolean(ENHANCE_SCALE_MISSING_DEFER)) {
		// defaultEnhanceMode.add(NetcdfDataset.Enhance.ScaleMissingDefer);
		// }

		NetcdfDataset.setDefaultEnhanceMode(defaultEnhanceMode);
	}

	/**
	 * Global attribute for coordinate coverageDescriptorsCache.
	 *
	 * @author Simone Giannecchini, GeoSolutions S.A.S.
	 */
	public static enum Axis {
		X, Y, Z, T;
	}

	public static enum CheckType {
		NONE, UNSET, NOSCALARS, ONLYGEOGRIDS
	}

	public static enum FileFormat {
		NONE, CDF, HDF5, GRIB, NCML, FC
	}

	/**
	 * The dimension <strong>relative to the rank</strong> in {@link #variable} to
	 * use as image width. The actual dimension is
	 * {@code variable.getRank() - X_DIMENSION}. Is hard-coded because the loop in
	 * the {@code read} method expects this order.
	 */
	public static final int X_DIMENSION = 1;

	/**
	 * The dimension <strong>relative to the rank</strong> in {@link #variable} to
	 * use as image height. The actual dimension is
	 * {@code variable.getRank() - Y_DIMENSION}. Is hard-coded because the loop in
	 * the {@code read} method expects this order.
	 */
	public static final int Y_DIMENSION = 2;

	/**
	 * The default dimension <strong>relative to the rank</strong> in
	 * {@link #variable} to use as Z dimension. The actual dimension is
	 * {@code variable.getRank() - Z_DIMENSION}.
	 *
	 * <p>
	 */
	public static final int Z_DIMENSION = 3;

	/**
	 * The data type to accept in images. Used for automatic detection of which
	 * coverageDescriptorsCache to assign to images.
	 */
	public static final Set<DataType> VALID_TYPES = new HashSet<DataType>(12);

	public static final String NC4_ERROR_MESSAGE = "Native NetCDF C library is not available. "
			+ "Unable to handle NetCDF4 files on input/output."
			+ "\nPlease make sure to add the paht of the Native NetCDF C libraries to the "
			+ "PATH environment variable\n if you want to support NetCDF4-Classic files";

	static {
		String property = System.getProperty(CHECK_COORDINATE_PLUGINS_KEY);
		CHECK_COORDINATE_PLUGINS = Boolean.getBoolean(CHECK_COORDINATE_PLUGINS_KEY);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Value of Check Coordinate Plugins:" + property);
			LOGGER.info("Should check for coordinate handler plugins:" + CHECK_COORDINATE_PLUGINS);
		}

		// IGNORED_DIMENSIONS = initializeIgnoreSet();

		// Setting the LINUX Epoch as start time
		final GregorianCalendar calendar = new GregorianCalendar(1970, 00, 01, 00, 00, 00);
		UTC = TimeZone.getTimeZone("UTC");
		calendar.setTimeZone(UTC);
		START_TIME = calendar.getTimeInMillis();

		EXCLUDED_ATTRIBUTES.add(UNITS);
		EXCLUDED_ATTRIBUTES.add(LONG_NAME);
		EXCLUDED_ATTRIBUTES.add(DESCRIPTION);
		EXCLUDED_ATTRIBUTES.add(STANDARD_NAME);

		HashSet<String> unsupportedSet = new HashSet<String>();
		unsupportedSet.add("OSEQD");
		UNSUPPORTED_DIMENSIONS = Collections.unmodifiableSet(unsupportedSet);

		VALID_TYPES.add(DataType.BOOLEAN);
		VALID_TYPES.add(DataType.BYTE);
		VALID_TYPES.add(DataType.SHORT);
		VALID_TYPES.add(DataType.INT);
		VALID_TYPES.add(DataType.LONG);
		VALID_TYPES.add(DataType.FLOAT);
		VALID_TYPES.add(DataType.DOUBLE);

		// Didn't extracted to a separate method
		// since we can't initialize the static fields
		final Object externalDir = System.getProperty(NETCDF_DATA_DIR);
		String finalDir = null;
		if (externalDir != null) {
			String dir = (String) externalDir;
			final File file = new File(dir);
			if (isValidDir(file)) {
				finalDir = dir;
			}
		}
		EXTERNAL_DATA_DIR = finalDir;

		try {
			Class.forName("ucar.nc2.grib.collection.GribIosp");
			Class.forName("org.geotools.coverage.io.grib.GribUtilities");
			IS_GRIB_AVAILABLE = true;
		} catch (ClassNotFoundException cnf) {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.fine("No Grib library found on classpath. GRIB will not be supported");
			}
			IS_GRIB_AVAILABLE = false;
		}

		// IS_NC4_LIBRARY_AVAILABLE = Nc4Iosp.isClibraryPresent();
		// if (!IS_NC4_LIBRARY_AVAILABLE && LOGGER.isLoggable(Level.FINE)) {
		// LOGGER.fine(NC4_ERROR_MESSAGE);
		// }
	}

	static boolean isLatLon(String bandName) {
		return bandName.equalsIgnoreCase(LON) || bandName.equalsIgnoreCase(LAT);
	}

	// private static Set<String> initializeIgnoreSet() {
	// Set<CoordinateHandlerSpi> handlers =
	// CoordinateHandlerFinder.getAvailableHandlers();
	// Iterator<CoordinateHandlerSpi> iterator = handlers.iterator();
	// Set<String> ignoredSet = new HashSet<String>();
	// while (iterator.hasNext()) {
	// CoordinateHandlerSpi handler = iterator.next();
	// Set<String> ignored = handler.getIgnoreSet();
	// if (ignored != null && !ignored.isEmpty()) {
	// ignoredSet.addAll(ignored);
	// }
	// }
	// if (!ignoredSet.isEmpty()) {
	// return ignoredSet;
	// }
	// return new HashSet<>();
	// }

	public static boolean isValidDir(File file) {
		String dir = file.getAbsolutePath();
		if (!file.exists()) {
			if (LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.warning("The specified " + NETCDF_DATA_DIR + " property doesn't refer "
						+ "to an existing folder. Please check the path: " + dir);
			}
			return false;
		} else if (!file.isDirectory()) {
			if (LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.warning("The specified " + NETCDF_DATA_DIR + " property doesn't refer "
						+ "to a directory. Please check the path: " + dir);
			}
			return false;
		} else if (!file.canWrite()) {
			if (LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.warning("The specified " + NETCDF_DATA_DIR + " property refers to "
						+ "a directory which can't be written. Please check the path and" + " the permissions for: "
						+ dir);
			}
			return false;
		}
		return true;
	}

	/**
	 * Get Z Dimension Lenght for standard CF variables
	 *
	 * @param var
	 * @return
	 */
	public static int getZDimensionLength(Variable var) {
		final int rank = var.getRank();
		if (rank > 2) {
			return var.getDimension(rank - Z_DIMENSION).getLength();
		}
		// TODO: Should I avoid use this method in case of 2D Variables?
		return 0;
	}

	public static int getDimensionLength(Variable var, final int dimensionIndex) {
		return var.getDimension(dimensionIndex).getLength();
	}

	/**
	 * Transcode a NetCDF data type into a java2D DataBuffer type.
	 *
	 * @param type     the {@link DataType} to transcode.
	 * @param unsigned if the original data is unsigned or not
	 * @return an int representing the correct DataBuffer type.
	 */
	public static int transcodeNetCDFDataType(final DataType type, final boolean unsigned) {
		if (DataType.BOOLEAN.equals(type) || DataType.BYTE.equals(type)) {
			return DataBuffer.TYPE_BYTE;
		}
		if (DataType.CHAR.equals(type)) {
			return DataBuffer.TYPE_USHORT;
		}
		if (DataType.SHORT.equals(type)) {
			return unsigned ? DataBuffer.TYPE_USHORT : DataBuffer.TYPE_SHORT;
		}
		if (DataType.INT.equals(type)) {
			return DataBuffer.TYPE_INT;
		}
		if (DataType.FLOAT.equals(type)) {
			return DataBuffer.TYPE_FLOAT;
		}
		if (DataType.LONG.equals(type) || DataType.DOUBLE.equals(type)) {
			return DataBuffer.TYPE_DOUBLE;
		}
		return DataBuffer.TYPE_UNDEFINED;
	}

	/**
	 * NetCDF files may contains a wide set of coverageDescriptorsCache. Some of
	 * them are unuseful for our purposes. The method returns {@code true} if the
	 * specified variable is accepted.
	 */
	public static boolean isVariableAccepted(final Variable var, final CheckType checkType) {
		return isVariableAccepted(var, checkType, null);
	}

	/**
	 * NetCDF files may contains a wide set of coverageDescriptorsCache. Some of
	 * them are unuseful for our purposes. The method returns {@code true} if the
	 * specified variable is accepted.
	 */
	public static boolean isVariableAccepted(final Variable var, final CheckType checkType,
			final NetcdfDataset dataset) {
		if (var instanceof CoordinateAxis1D) {
			return false;
		} else if (checkType == CheckType.NOSCALARS) {
			List<Dimension> dimensions = var.getDimensions();
			if (dimensions.size() < 2) {
				return false;
			}
			DataType dataType = var.getDataType();
			if (dataType == DataType.CHAR) {
				return false;
			}
			return isVariableAccepted(var.getFullName(), CheckType.NONE);
		} else if (checkType == CheckType.ONLYGEOGRIDS) {
			List<Dimension> dimensions = var.getDimensions();
			if (dimensions.size() < 2) {
				return false;
			}
			int twoDimensionalCoordinates = 0;
			for (Dimension dimension : dimensions) {
				String dimName = dimension.getFullName();
				// check the dimension to be defined
				Group group = dimension.getGroup();
				// Simple check if the group is not present. In that case false is returned.
				// This situation could happen with anonymous dimensions inside variables which
				// indicates the bounds of another variable. These kind of variable are not
				// useful
				// for displaying the final raster.
				if (group == null) {
					return false;
				}
				if (IGNORED_DIMENSIONS.contains(dimName)) {
					continue;
				}
				Variable dimVariable = group.findVariable(dimName);
				if (dimVariable == null && dataset != null) {
					// fallback on coordinates attribute for auxiliary coordinates.
					dimVariable = getAuxiliaryCoordinate(dataset, group, var, dimName);
				}
				if (dimVariable instanceof CoordinateAxis1D) {
					CoordinateAxis1D axis = (CoordinateAxis1D) dimVariable;
					AxisType axisType = axis.getAxisType();
					if (axisType == null) {
						return false;
					}
					switch (axisType) {
					case GeoX:
					case GeoY:
					case Lat:
					case Lon:
						twoDimensionalCoordinates++;
						break;
					default:
						break;
					}
				}
			}
			if (twoDimensionalCoordinates < 2) {
				// 2D Grid is missing
				return false;
			}

			DataType dataType = var.getDataType();
			if (dataType == DataType.CHAR) {
				return false;
			}
			return isVariableAccepted(var.getFullName(), CheckType.NONE);
		} else {
			return isVariableAccepted(var.getFullName(), checkType);
		}
	}

	private static Variable getAuxiliaryCoordinate(NetcdfDataset dataset, Group group, Variable var, String dimName) {
		Variable coordinateVariable = null;
		Attribute attribute = var.findAttribute(NetCDFUtils.COORDINATES);
		if (attribute != null) {
			String coordinates = attribute.getStringValue();
			String[] coords = coordinates.split(" ");
			for (String coord : coords) {
				Variable coordVar = dataset.findVariable(group, coord);
				List<Dimension> varDimensions = coordVar.getDimensions();
				if (varDimensions != null && varDimensions.size() == 1
						&& varDimensions.get(0).getFullName().equalsIgnoreCase(dimName)) {
					coordinateVariable = coordVar;
					break;
				}
			}
		}
		return coordinateVariable;
	}

	/**
	 * NetCDF files may contain a wide set of coverageDescriptorsCache. Some of them
	 * are unuseful for our purposes. The method returns {@code true} if the
	 * specified variable is accepted.
	 */
	public static boolean isVariableAccepted(final String name, final CheckType checkType) {
		if (checkType == CheckType.NONE) {
			return true;
		} else if (name.equalsIgnoreCase(LATITUDE) || name.equalsIgnoreCase(LONGITUDE) || name.equalsIgnoreCase(LON)
				|| name.equalsIgnoreCase(LAT) || name.equalsIgnoreCase(TIME) || name.equalsIgnoreCase(DEPTH)
				|| name.equalsIgnoreCase(ZETA) || name.equalsIgnoreCase(HEIGHT)
				|| name.toLowerCase().contains(COORDSYS.toLowerCase()) || name.endsWith(BOUNDS) || name.endsWith(BNDS)
				|| UNSUPPORTED_DIMENSIONS.contains(name)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks if the input is file based, and if yes, returns the file.
	 *
	 * @param input the input to check.
	 * @return the file or <code>null</code> if it is not file based.
	 * @throws IOException
	 */
	public static File getFile(Object input) throws IOException {
		File guessedFile = null;
		if (input instanceof File) {
			guessedFile = (File) input;
		} else if (input instanceof String) {
			guessedFile = new File((String) input);
		} else if (input instanceof URL) {
			final URL tempURL = (URL) input;
			String protocol = tempURL.getProtocol();
			if (protocol.equalsIgnoreCase("file")) {
				guessedFile = ImageIOUtilities.urlToFile(tempURL);
			}
		} else if (input instanceof URIImageInputStream) {
			final URIImageInputStream uriInStream = (URIImageInputStream) input;
			String uri = uriInStream.getUri().toString();
			guessedFile = new File(uri);
		} else if (input instanceof AccessibleStream) {
			final AccessibleStream<?> stream = (AccessibleStream<?>) input;
			if (stream.getBinding().isAssignableFrom(File.class)) {
				guessedFile = ((AccessibleStream<File>) input).getTarget();
			}
		}
		// check
		if (guessedFile != null && guessedFile.exists() && !guessedFile.isDirectory()) {
			return guessedFile;
		}
		return null;
	}

	/**
	 * Returns a format to use for parsing values along the specified axis type.
	 * This method is invoked when parsing the date part of axis units like
	 * "<cite>days since 1990-01-01 00:00:00</cite>". Subclasses should override
	 * this method if the date part is formatted in a different way. The default
	 * implementation returns the following formats:
	 *
	 * <p>
	 *
	 * <ul>
	 * <li>For {@linkplain AxisType#Time time axis}, a {@link DateFormat} using the
	 * {@code
	 *       "yyyy-MM-dd HH:mm:ss"} pattern in UTC {@linkplain TimeZone timezone}.
	 * <li>For all other kind of axis, a {@link NumberFormat}.
	 * </ul>
	 *
	 * <p>
	 * The {@linkplain Locale#CANADA Canada locale} is used by default for most
	 * formats because it is relatively close to ISO (for example regarding days and
	 * months order in dates) while using the English symbols.
	 *
	 * @param type      The type of the axis.
	 * @param prototype An example of the values to be parsed. Implementations may
	 *                  parse this prototype when the axis type alone is not
	 *                  sufficient. For example the {@linkplain AxisType#Time time
	 *                  axis type} should uses the {@code "yyyy-MM-dd"} date
	 *                  pattern, but some files do not follow this convention and
	 *                  use the default local instead.
	 * @return The format for parsing values along the axis.
	 */
	public static Format getAxisFormat(final AxisType type, final String prototype) {
		if (!type.equals(AxisType.Time) && !(type.equals(AxisType.RunTime))) {
			return NumberFormat.getNumberInstance(Locale.CANADA);
		}
		char dateSeparator = '-'; // The separator used in ISO format.
		boolean twoDigitYear = false; // Year is two digits
		boolean yearLast = false; // Year is first in ISO pattern.
		boolean namedMonth = false; // Months are numbers in the ISO pattern.
		boolean monthFirst = false; // Month first (assumes yearLast AND namedMonth true as well)
		boolean addT = false;
		boolean appendZ = false;
		int dateLength = 0;
		if (prototype != null) {
			/*
			 * Performs a quick check on the prototype content. If the prototype seems to
			 * use a different date separator than the ISO one, we will adjust the pattern
			 * accordingly. Also checks if the year seems to appears last rather than first,
			 * and if the month seems to be written using letters rather than digits.
			 */
			int field = 1;
			int digitCount = 0;

			final int length = prototype.length();
			for (int i = 0; i < length; i++) {
				final char c = prototype.charAt(i);
				if (Character.isWhitespace(c)) {
					if (monthFirst && field == 1) {
						dateLength++; // move to next field
					} else {
						break; // Checks only the dates, ignore the hours.
					}
				} else if (Character.isDigit(c)) {
					digitCount++;
					dateLength++;
					continue; // Digits are legal in all cases.
				} else if (Character.isLetter(c) && field <= 2) {
					if (field == 1) {
						yearLast = true;
						monthFirst = true;
					}
					namedMonth = true;
					dateLength++;
					continue; // Letters are legal for month only.
				} else if (field == 1 || monthFirst && field == 2) {
					dateSeparator = c;
					dateLength++;
				} else if (c == dateSeparator) {
					dateLength++;
				} else if (c == 'T') {
					addT = true;
				} else if (c == 'Z' && i == length - 1) {
					appendZ = true;
				}

				if ((field == 1 || yearLast && field == 3) && digitCount <= 2) {
					twoDigitYear = true;
				}

				digitCount = 0;
				field++;
			}
			if (digitCount >= 4) {
				yearLast = true;
				twoDigitYear = false;
			}
		}
		String pattern = null;
		if (yearLast) {
			pattern = (monthFirst ? "MMM dd-" : "dd-" + (namedMonth ? "MMM-" : "MM-")) + (twoDigitYear ? "yy" : "yyyy");
		} else {
			pattern = (twoDigitYear ? "yy-" : "yyyy-") + (namedMonth ? "MMM-" : "MM-") + "dd";
			if (dateLength < pattern.length()) {
				// case of truncated date
				pattern = pattern.substring(0, dateLength);
			}
		}
		pattern = pattern.replace('-', dateSeparator);
		int lastColon = prototype != null ? prototype.lastIndexOf(":") : -1; // $NON-NLS-1$
		if (lastColon != -1) {
			pattern += addT ? "'T'" : " ";
			pattern += prototype != null && lastColon >= 16 ? "HH:mm:ss" : "HH:mm";
		}
		// TODO: Improve me:
		// Handle timeZone
		pattern += appendZ ? "'Z'" : "";
		final DateFormat format = new SimpleDateFormat(pattern, Locale.CANADA);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format;
	}

	/**
	 * Depending on the type of model/netcdf file, we will check for the presence of
	 * some coverageDescriptorsCache rather than some others. The method returns the
	 * type of check on which we need to leverage to restrict the set of interesting
	 * coverageDescriptorsCache. The method will check for some KEY/FLAGS/ATTRIBUTES
	 * within the input dataset in order to define the proper check type to be
	 * performed.
	 *
	 * @param dataset the input dataset.
	 * @return the proper {@link CheckType} to be performed on the specified
	 *         dataset.
	 */
	public static CheckType getCheckType(NetcdfDataset dataset) {
		CheckType ct = CheckType.UNSET;
		if (dataset != null) {
			ct = CheckType.ONLYGEOGRIDS;
		}
		return ct;
	}

	/**
	 * @param schemaDef
	 * @param crs
	 * @return
	 */
	public static SimpleFeatureType createFeatureType(String schemaName, String schemaDef,
			CoordinateReferenceSystem crs) {
		SimpleFeatureType indexSchema = null;
		if (schemaDef == null) {
			throw new IllegalArgumentException("Unable to create feature type from null definition!");
		}
		schemaDef = schemaDef.trim();
		// get the schema
		try {
			indexSchema = DataUtilities.createType(schemaName, schemaDef);
			indexSchema = DataUtilities.createSubType(indexSchema, DataUtilities.attributeNames(indexSchema), crs);
		} catch (Throwable e) {
			if (LOGGER.isLoggable(Level.FINE))
				LOGGER.log(Level.FINE, e.getLocalizedMessage(), e);
			indexSchema = null;
		}
		return indexSchema;
	}

	/** @return true if the GRIB library is available */
	public static boolean isGribAvailable() {
		return IS_GRIB_AVAILABLE;
	}

	/** @return true if the C Native NetCDF 4 library is available */
	public static boolean isNC4CAvailable() {
		return IS_NC4_LIBRARY_AVAILABLE;
	}

	public static boolean isCheckCoordinatePlugins() {
		return CHECK_COORDINATE_PLUGINS;
	}

	/** @return An unmodifiable Set of Unsupported Dimension names */
	public static Set<String> getUnsupportedDimensions() {
		return UNSUPPORTED_DIMENSIONS;
	}

	/**
	 * @return an unmodifiable Set of the Dimensions to be ignored by the Coordinate
	 *         parsing machinery
	 */
	public static Set<String> getIgnoredDimensions() {
		return Collections.unmodifiableSet(IGNORED_DIMENSIONS);
	}

	/** Adds a dimension to the ignored dimensions set. */
	public static void addIgnoredDimension(String dimensionName) {
		IGNORED_DIMENSIONS.add(dimensionName);
	}

	/**
	 * Utility method for getting NoData from an input {@link Variable}
	 *
	 * @param var Variable instance
	 * @return a Number representing NoData
	 */
	public static Number getNodata(Variable var) {
		if (var != null) {
			// Getting all the Variable attributes
			List<Attribute> attributes = var.getAttributes();
			String fullName;
			// Searching for FILL_VALUE or MISSING_VALUE attributes
			for (Attribute attribute : attributes) {
				fullName = attribute.getFullName();
				if (fullName.equalsIgnoreCase(FILL_VALUE) || fullName.equalsIgnoreCase(MISSING_VALUE)) {
					return attribute.getNumericValue();
				}
			}
		}
		return null;
	}

	/**
	 * Utility method for getting Range from an input {@link Variable}
	 *
	 * @param var Variable instance
	 * @return a Range representing actual_range or valid_min/valid_max range or
	 *         valid_range
	 */
	public static NumberRange getRange(Variable var) {
		if (var != null) {
			double min = Double.NaN;
			double max = Double.NaN;
			DataType dataType = null;
			Attribute rangeAttribute = var.findAttribute(ACTUAL_RANGE);
			if (rangeAttribute == null) {
				rangeAttribute = var.findAttribute(VALID_RANGE);
			}
			if (rangeAttribute != null) {
				dataType = rangeAttribute.getDataType();
				min = rangeAttribute.getNumericValue(0).doubleValue();
				max = rangeAttribute.getNumericValue(1).doubleValue();
			} else {
				Attribute minAttribute = var.findAttribute(VALID_MIN);
				Attribute maxAttribute = var.findAttribute(VALID_MAX);
				if (minAttribute != null && maxAttribute != null) {
					dataType = minAttribute.getDataType();
					min = minAttribute.getNumericValue().doubleValue();
					max = maxAttribute.getNumericValue().doubleValue();
				}
			}
			if (!Double.isNaN(min) && !Double.isNaN(max)) {
				// Quoting from:
				// http://www.unidata.ucar.edu/software/netcdf/docs/attribute_conventions.html
				// For integer types, there should be a difference of 1 between the _FillValue
				// and
				// this valid minimum or maximum.
				// Force this constraints to also avoid overlapping categories
				if (dataType == DataType.BYTE || dataType == DataType.INT || dataType == DataType.SHORT
						|| dataType == DataType.LONG) {
					Number noData = getNodata(var);
					if (noData != null && (noData.intValue() == min)) {
						min++;
					}
				}
				return NumberRange.create(min, max);
			}
		}

		return null;
	}

	/**
	 * Return the propery NetCDF dataType for the input datatype class
	 *
	 * @param classDataType
	 * @return
	 */
	public static DataType getNetCDFDataType(String classDataType) {
		if (isATime(classDataType)) {
			return DataType.DOUBLE;
		} else if (classDataType.endsWith("Integer")) {
			return DataType.INT;
		} else if (classDataType.endsWith("Double")) {
			return DataType.DOUBLE;
		} else if (classDataType.endsWith("String")) {
			return DataType.STRING;
		}
		return DataType.STRING;
	}

	/**
	 * Transcode a DataBuffer type into a NetCDF DataType .
	 *
	 * @param dataType the beam {@link ProductData} type to transcode.
	 * @return an NetCDF DataType type.
	 */
	public static DataType transcodeImageDataType(final int dataType) {
		switch (dataType) {
		case DataBuffer.TYPE_BYTE:
			return DataType.BYTE;
		case DataBuffer.TYPE_SHORT:
			return DataType.SHORT;
		case DataBuffer.TYPE_INT:
			return DataType.INT;
		case DataBuffer.TYPE_DOUBLE:
			return DataType.DOUBLE;
		case DataBuffer.TYPE_FLOAT:
			return DataType.FLOAT;
		case DataBuffer.TYPE_UNDEFINED:
		default:
			throw new IllegalArgumentException("Invalid input data type:" + dataType);
		}
	}

	/**
	 * Return true in case that dataType refers to something which need to be
	 * handled as a Time (TimeStamp, Date)
	 *
	 * @param classDataType
	 * @return
	 */
	public static final boolean isATime(String classDataType) {
		return (classDataType.endsWith("Timestamp") || classDataType.endsWith("Date"));
	}

	/**
	 * Transcode a NetCDF Number into a proper Number instance.
	 *
	 * @param type the {@link DataType} to transcode.
	 * @return the proper number instance
	 */
	public static Number transcodeNumber(final DataType type, Number value) {
		if (DataType.DOUBLE.equals(type)) {
			return Double.valueOf(value.doubleValue());
		} else if (DataType.FLOAT.equals(type)) {
			return Float.valueOf(value.floatValue());
		} else if (DataType.LONG.equals(type)) {
			return Long.valueOf(value.longValue());
		} else if (DataType.INT.equals(type)) {
			return Integer.valueOf(value.intValue());
		} else if (DataType.SHORT.equals(type)) {
			return Short.valueOf(value.shortValue());
		} else if (DataType.BYTE.equals(type)) {
			return Byte.valueOf(value.byteValue());
		}
		throw new IllegalArgumentException(
				"Unsupported type or value: type = " + type.toString() + " value = " + value);
	}

	/**
	 * Default parameter behavior properties TODO: better way of handling
	 * configuration settings, such as read parameters.
	 */
	public static final String PARAMS_MAX_KEY = "org.geotools.coverage.io.netcdf.param.max";

	public static final String PARAMS_MIN_KEY = "org.geotools.coverage.io.netcdf.param.min";

	private static Set<String> PARAMS_MAX;

	private static Set<String> PARAMS_MIN;

	static {
		refreshParameterBehaviors();
	}

	public static void refreshParameterBehaviors() {

		PARAMS_MAX = new HashSet<String>();
		String maxProperty = System.getProperty(PARAMS_MAX_KEY);
		if (maxProperty != null) {
			for (String param : maxProperty.split(",")) {
				PARAMS_MAX.add(param.trim().toUpperCase());
			}
		}

		String minProperty = System.getProperty(PARAMS_MIN_KEY);
		PARAMS_MIN = new HashSet<String>();
		if (minProperty != null) {
			for (String param : minProperty.split(",")) {
				PARAMS_MIN.add(param.trim().toUpperCase());
			}
		}
	}

	public enum ParameterBehaviour {
		DO_NOTHING, MAX, MIN
	}

	public static ParameterBehaviour getParameterBehaviour(String parameter) {
		if (PARAMS_MAX.contains(parameter.toUpperCase())) {
			return ParameterBehaviour.MAX;
		} else if (PARAMS_MIN.contains(parameter.toUpperCase())) {
			return ParameterBehaviour.MIN;
		} else {
			return ParameterBehaviour.DO_NOTHING;
		}
	}


	public static void main(String[] dio) {

		String fileDir = System.getProperty("user.home") + File.separator + ".klab" + File.separator + "copernicus"
				+ File.separator + "sis-agrometeorological-indicators";

		String source_1 = fileDir + File.separator + "liquid_precipitation_volume_160" + File.separator
				+ "Precipitation-Flux_C3S-glob-agric_AgERA5_20190101_final-v1.0.nc";
		String source_2 = fileDir + File.separator + "liquid_precipitation_volume_160" + File.separator
				+ "Precipitation-Flux_C3S-glob-agric_AgERA5_20190102_final-v1.0.nc";
		String aggregated_1 = fileDir + File.separator + "aggregated" + File.separator + "test_1.tiff";
		String aggregated_2 = fileDir + File.separator + "aggregated" + File.separator + "test_2.tiff";

//
//        List<File> f2016 = new ArrayList<>();
//        f2016.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_148\\Precipitation-Flux_C3S-glob-agric_AgERA5_20160101_final-v1.0.nc"));
//        f2016.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_148\\Precipitation-Flux_C3S-glob-agric_AgERA5_20160102_final-v1.0.nc"));
//        f2016.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_148\\Precipitation-Flux_C3S-glob-agric_AgERA5_20160103_final-v1.0.nc"));
//        f2016.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_148\\Precipitation-Flux_C3S-glob-agric_AgERA5_20160104_final-v1.0.nc"));
//        f2016.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_148\\Precipitation-Flux_C3S-glob-agric_AgERA5_20160105_final-v1.0.nc"));
//        f2016.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_148\\Precipitation-Flux_C3S-glob-agric_AgERA5_20160106_final-v1.0.nc"));
//
//        List<File> f2019 = new ArrayList<>();
//        f2019.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_160\\Precipitation-Flux_C3S-glob-agric_AgERA5_20190101_final-v1.0.nc"));
//        f2019.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_160\\Precipitation-Flux_C3S-glob-agric_AgERA5_20190102_final-v1.0.nc"));
//        f2019.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_160\\Precipitation-Flux_C3S-glob-agric_AgERA5_20190103_final-v1.0.nc"));
//        f2019.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_160\\Precipitation-Flux_C3S-glob-agric_AgERA5_20190104_final-v1.0.nc"));
//        f2019.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_160\\Precipitation-Flux_C3S-glob-agric_AgERA5_20190105_final-v1.0.nc"));
//        f2019.add(new File(
//                "C:\\Users\\Ferd\\.klab\\copernicus\\sis-agrometeorological-indicators\\liquid_precipitation_volume_160\\Precipitation-Flux_C3S-glob-agric_AgERA5_20190106_final-v1.0.nc"));

//        aggregateGrid(Collections.singletonList(new File(source_1)), new File(aggregated_1), null, Aggregation.SUM, -9999.0);
		aggregateGrid(Lists.newArrayList(new File(source_1), new File(source_2)), new File(aggregated_2), null,
				Aggregation.MEAN, -9999.0);
//        aggregateGrid(f2016, new File(tocock), null, Aggregation.SUM, -9999.0);

	}

	public static boolean aggregateGrid(List<File> toAggregate, File destinationFile, Resolution resolution,
			Aggregation aggregation, double noDataValue) {
		return aggregateGrid(toAggregate, destinationFile, resolution, aggregation, noDataValue, null);
	}

	/**
	 * Same as {@link #aggregateFiles(List, File, Resolution, Aggregation, double)}
	 * but limited to one grid and producing a GeoTIFF instead of a netcdf. Created
	 * out of frustration with NetCDF constantly changing and never working write
	 * implementations, plus complete unreliability due to the need of native
	 * support that only works if a machine has been blessed by the right priest.
	 * <p>
	 * NOTE: this assumes that all input files have either data or nodata in the
	 * same grid cell. Otherwise the only alternative is to keep a separate state
	 * with the counts of nodata values.
	 * 
	 * @param toAggregate
	 * @param destinationFile
	 * @param resolution
	 * @param aggregation
	 * 
	 * @return the name of the last non-dimension variable aggregated.
	 */
	public static boolean aggregateGrid(List<File> toAggregate, File destinationFile, Resolution resolution,
			Aggregation aggregation, double noDataValue, Function<Double, Double> transformation) {

		int times = 1;
		int rows = 0;
		int cols = 0;
		int rowDim = 0;
		int colDim = 0;
		String nativeName = null;
		Map<String, BasicFileMappedStorage<Double>> storage = new HashMap<>();
		ReferencedEnvelope envelope = null;
		int nd = 0;

		try {

			boolean isNew = true;
			boolean first = true;

			int nfile = 1;
			for (File infile : toAggregate) {

				System.out.println("Reading " + infile + "...");

				try (GridDataset dataset = GridDataset.open(infile.toString())) {

					for (GridDatatype grid : dataset.getGrids()) {

						if (isNew) {
							/*
							 * extract grid parameters
							 */
							isNew = false;
							nativeName = grid.getName();
							GridCoordSystem crs = grid.getCoordinateSystem();
							if (!crs.isLatLon()) {
								throw new KlabUnsupportedFeatureException(
										"NetCDF grids are only supported in lat/lon for the time being");
							}
							ProjectionRect bbox = crs.getBoundingBox();
							envelope = new ReferencedEnvelope(bbox.getMinX(), bbox.getMaxX(), bbox.getMinY(),
									bbox.getMaxY(), Projection.getLatLon().getCRS());
						}

						Variable v = dataset.getNetcdfFile().findVariable(grid.getFullName());

						/*
						 * each fucking variable and each fucking file can have a different fucking
						 * chunk size; reading anything else than one fucking chunk at a time slows
						 * things down to a fucking crawl.
						 */
						int[] chunkSizes = new int[v.getShape().length];
						for (Attribute a : v.attributes()) {
							if ("_ChunkSizes".equals(a.getName())) {
								for (int i = 0; i < a.getValues().getSize(); i++) {
									chunkSizes[i] = ((Number) a.getValue(i)).intValue();
								}
								break;
							}
						}

						if (rows == 0 && cols == 0) {

							nd = v.getShape().length;
							times = nd > 2 ? v.getShape()[0] : 1;
							rowDim = nd > 2 ? 1 : 0;
							colDim = nd > 2 ? 2 : 1;
							rows = v.getShape()[rowDim];
							cols = v.getShape()[colDim];

							/*
							 * Create storage for aggregation and open the writable file
							 */
							storage.put(v.getFullName(), new BasicFileMappedStorage<Double>(Double.class, cols, rows));

						} else if (rows != v.getShape()[1] || cols != v.getShape()[2]) {
							throw new KlabIllegalArgumentException(
									"NetCDF files to aggregate have different shapes: " + infile);
						}

						/*
						 * Read the variable
						 */
						if (!storage.containsKey(v.getFullName())) {
							throw new KlabIllegalArgumentException(
									"NetCDF files to aggregate have different variables: " + v.getFullName());
						}

						/*
						 * Read up one chunk at a time
						 */
						int[] readOrigin = new int[nd];
						int[] readShape = new int[nd];
						BasicFileMappedStorage<Double> store = storage.get(v.getFullName());

						if (chunkSizes.length == 3 && chunkSizes[0] != 1) {
							Logging.INSTANCE.warn("NetCDF reader: time chunk size != 1: proceed at your own risk");
						}

						/*
						 * assumes we're facing a 1 for time chunksize, or no time; we have sent a
						 * fucking warning if not
						 */
						for (int startRow = 0; startRow < rows; startRow += chunkSizes[rowDim]) {
							for (int startCol = 0; startCol < cols; startCol += chunkSizes[colDim]) {

								if (nd > 2) {
									readOrigin[0] = 0;
									readShape[0] = 1;
								}

								readOrigin[rowDim] = startRow;
								readOrigin[colDim] = startCol;
								readShape[rowDim] = chunkSizes[rowDim];
								readShape[colDim] = chunkSizes[colDim];

								/*
								 * they apparently like to have chunks[dim] starting at last chunk[dim] + 1,
								 * just to complicate things.
								 */
								while (readOrigin[rowDim] + readShape[rowDim] > rows) {
									readShape[rowDim]--;
								}
								while (readOrigin[colDim] + readShape[colDim] > cols) {
									readShape[colDim]--;
								}

								// this is the fucking chunk and its fucking index
								Array array = v.read(readOrigin, readShape);
								Index index = array.getIndex();

								for (int col = 0; col < readShape[colDim]; col++) {
									for (int row = 0; row < readShape[rowDim]; row++) {

										if (nd > 2) {
											index.set(0, row, col);
										} else {
											index.set(row, col);
										}

										Double sample = array.getDouble(index);

										if (transformation != null && Observations.INSTANCE.isData(sample)) {
											sample = transformation.apply(sample);
										}
										
										if (first) {
											store.set(sample, col + startCol, row + startRow);
										} else {
											Double d = store.get(col + startCol, row + startRow);
											if (!NumberUtils.equal(d, noDataValue)) {
												d = d + sample;
											} else {
												d = sample;
											}
											store.set(d, col + startCol, row + startRow);
										}
									}
								}
							}

						}
					}

				} catch (Throwable e) {
					Logging.INSTANCE.error(e);
				}

				first = false;

				System.out.println("Done " + (nfile++) + "/" + toAggregate.size() + ": " + infile);

			}

			/**
			 * Create the output coverage (one band per variable).
			 */
			WritableRaster raster = RasterFactory.createBandedRaster(DataBuffer.TYPE_FLOAT, cols, rows, storage.size(),
					null);

			int band = 0;
			for (String var : storage.keySet()) {
				BasicFileMappedStorage<Double> data = storage.get(var);
				for (int x = 0; x < cols; x++) {
					for (int y = 0; y < rows; y++) {
						Double value = data.get(x, y);
						if (NumberUtils.equal(value, noDataValue)) {
							value = Double.NaN;
						}
						if (aggregation == Aggregation.MEAN && Observations.INSTANCE.isData(value)) {
							/**
							 * ACHTUNG this assumes that all input files have either data or nodata in the
							 * same grid cell. Otherwise the only alternative is to keep a separate state
							 * with the counts of nodata values.
							 */
							value /= (double) toAggregate.size();
						}
						raster.setSample(x, y, band, value);
					}
				}
				band++;
			}

			GridCoverage2D coverage = rasterFactory.create(nativeName, raster, envelope);

			GeoTiffWriteParams wp = new GeoTiffWriteParams();
			ParameterValueGroup params = new GeoTiffFormat().getWriteParameters();
			params.parameter(AbstractGridFormat.GEOTOOLS_WRITE_PARAMS.getName().toString()).setValue(wp);
			try (OutputStream out = new FileOutputStream(destinationFile)) {
				new GeoTiffWriter(out).write(coverage,
						(GeneralParameterValue[]) params.values().toArray(new GeneralParameterValue[1]));
				System.out.println("Wrote output file " + destinationFile);
			} catch (Throwable diocan) {
				Logging.INSTANCE.error(diocan);
				return false;
			}

		} catch (

		Throwable e1) {

			Logging.INSTANCE.error(e1);
			return false;

		} finally {

			/*
			 * close the storage
			 */
			for (BasicFileMappedStorage<?> store : storage.values()) {
				try {
					store.close();
				} catch (Exception e) {
					Logging.INSTANCE.error(e);
				}
			}
		}

		return true;
	}

}