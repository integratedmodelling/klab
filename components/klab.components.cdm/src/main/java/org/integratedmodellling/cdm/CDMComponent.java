package org.integratedmodellling.cdm;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;

import ucar.ma2.DataType;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.util.net.HTTPAuthScheme;
import ucar.nc2.util.net.HTTPSession;

@Component(id = "org.integratedmodelling.cdm", version = Version.CURRENT)
public class CDMComponent {

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

	public static synchronized NetcdfDataset openAuthenticated(String url) {

		CredentialsProvider credentialProvider = null;
		String host = null;
		AuthScheme scheme;
		
		NetcdfDataset ncd = null;

		try {
			for (String h : Authentication.INSTANCE.getExternalCredentials().keySet()) {
				if (url.contains(h)) {
					host = h;
					credentialProvider = new CredentialsProvider() {
						
						@Override
						public Credentials getCredentials(AuthScheme scheme, String host, int port, boolean proxy)
								throws CredentialsNotAvailableException {
							
							ExternalAuthenticationCredentials credentials = Authentication.INSTANCE.getExternalCredentials().get(h);
							
							switch (credentials.getScheme().toLowerCase()) {
							case "basic":
								break;
							case "ssl":
								break;
//							case "ssl":
//								break;
							}
							
							return null;
						}
					};
					break;
				}
			}
			
			ncd = NetcdfDataset.openDataset(url);

		} catch (IOException e) {
			throw new KlabIOException(e);
		} finally {
			if (credentialProvider != null) {
//				HTTPSession.setGlobalCredentialsProvider(scheme, null);
			}
		}

		return ncd;
	}

}
