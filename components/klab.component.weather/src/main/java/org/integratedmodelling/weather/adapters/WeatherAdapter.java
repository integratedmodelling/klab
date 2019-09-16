package org.integratedmodelling.weather.adapters;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

public class WeatherAdapter implements IUrnAdapter {

	public enum Services {
		/**
		 * Return weather stations with their data for the requested spatio/temporal ctx
		 */
		stations,
		/**
		 * Return interpolated weather data for the requested variables and context
		 */
		data,
		/**
		 * Return individual storm events for the context
		 */
		storms
	}

	@Override
	public String getName() {
		return "weather";
	}

	@Override
	public boolean isOnline(Urn urn) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void getEncodedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {

		// TODO Auto-generated method stub
		switch (Services.valueOf(urn.getNamespace())) {
		case data:
			break;
		case stations:
			break;
		case storms:
			break;
		default:
			break;
		}

		throw new IllegalArgumentException(
				"weather service: URN namespace " + urn.getNamespace() + " cannot be understood");

		
	}

	@Override
	public Type getType(Urn urn) {

		switch (Services.valueOf(urn.getNamespace())) {
		case data:
			break;
		case stations:
			break;
		case storms:
			break;
		default:
			break;
		}

		throw new IllegalArgumentException(
				"weather service: URN namespace " + urn.getNamespace() + " cannot be understood");
	}

	@Override
	public IGeometry getGeometry(Urn urn) {

		switch (Services.valueOf(urn.getNamespace())) {
		case data:
			break;
		case stations:
			break;
		case storms:
			break;
		default:
			break;
		}

		throw new IllegalArgumentException(
				"weather service: URN namespace " + urn.getNamespace() + " cannot be understood");
	}

}
