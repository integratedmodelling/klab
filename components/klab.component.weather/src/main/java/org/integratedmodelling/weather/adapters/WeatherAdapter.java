package org.integratedmodelling.weather.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.weather.data.WeatherEvents;
import org.integratedmodelling.weather.data.WeatherFactory;

/**
 * Urns:
 * 
 * klab:weather:data:<all|catalog> (handles primary output as parameter, e.g.
 * #precipitation, and others as additional attributes)
 * klab:weather:stations:<all|catalog> klab:weather:storms:<all|catalog>
 * 
 * @author Ferd
 *
 */
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
		switch (Services.valueOf(urn.getNamespace())) {
		case data:
		case stations:
			return WeatherFactory.INSTANCE.isOnline();
		case storms:
			return WeatherEvents.INSTANCE.isOnline();
		}
		return false;
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
	public Collection<String> getResourceUrns() {
		List<String> ret = new ArrayList<>();
		// TODO
		return ret;
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
			// S2
			break;
		case stations:
			// #T0S0(nstations - according to catalog)
			break;
		case storms:
			// #T0S2
			break;
		default:
			break;
		}

		throw new IllegalArgumentException(
				"weather service: URN namespace " + urn.getNamespace() + " cannot be understood");
	}

	@Override
	public String getDescription() {
		return "Weather stations and their data, reconstructed storm events and on-demand interpolation of weather records.";
	}

}
