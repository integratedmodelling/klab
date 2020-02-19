package org.integratedmodelling.weather.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.weather.WeatherComponent;
import org.integratedmodelling.weather.data.Weather;
import org.integratedmodelling.weather.data.WeatherEvent;
import org.integratedmodelling.weather.data.WeatherEvents;
import org.integratedmodelling.weather.data.WeatherFactory;

/**
 * Handles URNs:
 * 
 * <ul>
 * <li>klab:weather:data:{all|catalog} (handles primary output as parameter,
 * e.g. #precipitation, and others as additional attributes)</li>
 * <li>klab:weather:stations:{all|catalog}</li>
 * <li>klab:weather:storms:{all|catalog}</li>
 * </ul>
 * <p>
 * "Catalog" may depend on the setup, should be ghdnc (or noaa), ....
 * 
 * @author Ferd
 *
 */
@UrnAdapter(type = "weather", version = Version.CURRENT)
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
			// TODO check catalog and args before saying OK!
			return WeatherFactory.INSTANCE.isOnline();
		case storms:
			// TODO check catalog and args before saying OK!
			return WeatherEvents.INSTANCE.isOnline();
		}
		return false;
	}

	@Override
	public void getEncodedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		// TODO Auto-generated method stub
		switch (Services.valueOf(urn.getNamespace())) {
		case data:
			getInterpolatedData(urn, builder, geometry, context);
			return;
		case stations:
			getStations(urn, builder, geometry, context);
			return;
		case storms:
			getStorms(urn, builder, geometry, context);
			return;
		default:
			break;
		}

		throw new IllegalArgumentException(
				"weather service: URN namespace " + urn.getNamespace() + " cannot be understood");

	}

	private void getStations(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		// TODO Auto-generated method stub
		Scale scale = Scale.create(geometry);
		Weather weather = WeatherComponent.getWeather(scale.getSpace(), scale.getTime(), "ALL",
				urn.getSplitParameter(Urn.SINGLE_PARAMETER_KEY));
		
		System.out.println("ZIOPOP");
	}

	private void getStorms(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {

		double minPrecipitation = 0;

		if (urn.getParameters().containsKey("minprec")) {
			minPrecipitation = Double.parseDouble(urn.getParameters().get("minprec").toString());
		}

		for (WeatherEvent event : WeatherEvents.INSTANCE.getEvents(Scale.create(geometry), minPrecipitation)) {

			Builder ob = builder.startObject("result", "storm_" + event.asData().get(WeatherEvent.ID),
					(IGeometry) event.asData().get(WeatherEvent.BOUNDING_BOX));

			Builder sb = ob.startState("precipitation");
			sb.add(event.asData().get(WeatherEvent.PRECIPITATION_MM));
			sb.finishState();

			Builder db = ob.startState("duration");
			db.add(event.asData().get(WeatherEvent.DURATION_HOURS));
			db.finishState();

			ob.finishObject();
		}

	}

	private void getInterpolatedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		// TODO Auto-generated method stub

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

	@Override
	public IResource getResource(String urn) {
		// TODO Auto-generated method stub
		Urn kurn = new Urn(urn);
		ResourceReference ref = new ResourceReference();
		ref.setUrn(kurn.getUrn());
		ref.setAdapterType(getName());
		ref.setLocalName(kurn.getResourceId());
		ref.setGeometry("#"); // getGeometry(urn)
		ref.setVersion(Version.CURRENT);
		ref.setType(Type.VALUE); // getType(urn)
		return new Resource(ref);
	}

}
