package org.integratedmodelling.cdm;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

import ucar.httpservices.HTTPException;
import ucar.httpservices.HTTPSession;
import ucar.ma2.DataType;
import ucar.nc2.dods.DODSNetcdfFile;
import ucar.nc2.grib.collection.GribCdmIndex;

@Component(id = "org.integratedmodelling.cdm", version = Version.CURRENT)
public class CDMComponent {

	@Initialize
	public boolean initialize() {

		// other settings should go into init as well.
		DODSNetcdfFile.setAllowCompression(true);
		DODSNetcdfFile.setAllowSessions(true);
		// caching
//		RandomAccessFile.enableDefaultGlobalFileCache();
		GribCdmIndex.initDefaultCollectionCache(100, 200, -1);

		try {
			
			/**
			 * Set up authentication through the centralized k.LAB directory. FIXME use a
			 * factory and remove the deprecation (no docs, must use source code to
			 * understand).
			 */
			HTTPSession.setGlobalCredentialsProvider(Authentication.INSTANCE.getCredentialProvider());
			HTTPSession.setGlobalUserAgent("k.LAB v" + Version.CURRENT);
			
		} catch (HTTPException e) {
			Klab.INSTANCE.getRootMonitor().error(e);
			return false;
		}

		return true;
	}

	public static DataType getDatatype(Class<?> valueClass) {

		DataType type = null;

		if (valueClass == Double.class) {
			// Double.NaN for nodata
			type = DataType.DOUBLE;
		} else if (valueClass == Short.class) {
			// Short.MIN_VALUE for nodata
			type = DataType.SHORT;
		} else if (valueClass == Integer.class) {
			// Integer.MIN_VALUE for nodata
			type = DataType.INT;
		} else if (valueClass == Long.class) {
			// Long.MIN_VALUE for nodata
			type = DataType.LONG;
		} else if (valueClass == Float.class) {
			// Float.NaN for nodata
			type = DataType.FLOAT;
		} else if (valueClass == Boolean.class) {
			// 0 for nodata, 1 for false, 2 for true
			type = DataType.BYTE;
		} else {
			throw new IllegalStateException("NetCDF-backed storage cannot use type " + valueClass.getCanonicalName());
		}

		return type;
	}

	public static Object getNodataValue(DataType type) {
		switch (type) {
		case BYTE:
			return Byte.valueOf((byte) 0);
		case DOUBLE:
			return Double.NaN;
		case FLOAT:
			return Float.NaN;
		case INT:
			return Integer.MIN_VALUE;
		case LONG:
			return Long.MIN_VALUE;
		case SHORT:
			return Short.MIN_VALUE;
		default:
			break;
		}

		throw new KlabInternalErrorException("NetCDF-backed storage: unexpected type");
	}

	public static boolean isNodata(Object value, DataType type) {
		switch (type) {
		case BYTE:
			return value instanceof Number && ((Number) value).byteValue() == 0;
		case DOUBLE:
			return value instanceof Number && Double.isNaN(((Number) value).doubleValue());
		case FLOAT:
			return value instanceof Number && Double.isNaN(((Number) value).floatValue());
		case INT:
			return value instanceof Number && ((Number) value).intValue() == Integer.MIN_VALUE;
		case LONG:
			return value instanceof Number && ((Number) value).longValue() == Long.MIN_VALUE;
		case SHORT:
			return value instanceof Number && ((Number) value).shortValue() == Short.MIN_VALUE;
		default:
			break;
		}
		throw new KlabInternalErrorException("NetCDF-backed storage: unexpected type");
	}

}
