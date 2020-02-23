package org.integratedmodelling.weather.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.weather.WeatherComponent;
import org.integratedmodelling.weather.data.Weather;
import org.integratedmodelling.weather.data.WeatherEvent;
import org.integratedmodelling.weather.data.WeatherEvents;
import org.integratedmodelling.weather.data.WeatherFactory;
import org.integratedmodelling.weather.data.WeatherStation;

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

		Scale scale = Scale.create(geometry);
		String[] originalVariables = urn.getSplitParameter(Urn.SINGLE_PARAMETER_KEY);
		String[] variables = WeatherComponent.normalizeVariableNames(originalVariables);
		Weather weather = WeatherComponent.getWeather(scale.getSpace(), scale.getTime(), "ALL", variables);

		for (Map<String, Object> sd : weather.getStationData()) {

			Scale stationScale = Scale.create(scale.getTime(),
					Shape.create((Double) sd.get("lon"), (Double) sd.get("lat"), Projection.getLatLon()));

			Builder ob = builder.startObject("result", sd.get("id").toString(), stationScale.asGeometry());

			for (int i = 0; i < originalVariables.length; i++) {

				if (sd.containsKey(variables[i])) {
					double[] data = (double[]) sd.get(variables[i]);
					Builder sb = ob.startState(originalVariables[i]);
					for (double d : data) {
						sb.add(d);
					}
					sb.finishState();
				}
			}

			ob.finishObject();
		}
	}

	private void getStorms(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {

		double minPrecipitation = 0;

		if (urn.getParameters().containsKey("minprec")) {
			minPrecipitation = Double.parseDouble(urn.getParameters().get("minprec").toString());
		}

		for (WeatherEvent event : WeatherEvents.INSTANCE.getEvents(Scale.create(geometry), minPrecipitation)) {

			Builder ob = builder.startObject("result", "storm_" + event.asData().get(WeatherEvent.ID),
					(IGeometry) event.asData().get(WeatherEvent.BOUNDING_BOX));

			// TODO use urn parameters, set attributes
			
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
			return Type.NUMBER;
		case storms:
			return Type.EVENT;
		case stations:
			return Type.OBJECT;
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
			return Geometry.create("\u03c41\u03c32");
		case stations:
			return Geometry.create("#\u03c41\u03c30");
		case storms:
			return Geometry.create("#\u03c41\u03c32");
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

		Urn kurn = new Urn(urn);
		ResourceReference ref = new ResourceReference();
		ref.setUrn(kurn.getUrn());
		ref.setAdapterType(getName());
		ref.setLocalName(kurn.getResourceId());
		ref.setGeometry(getGeometry(kurn).encode());
		ref.setVersion(Version.CURRENT);
		ref.setType(getType(kurn));
		return new Resource(ref);
	}

}
