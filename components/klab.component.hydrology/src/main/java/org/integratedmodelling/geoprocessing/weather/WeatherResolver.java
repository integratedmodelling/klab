package org.integratedmodelling.geoprocessing.weather;

import org.integratedmodelling.geoprocessing.weather.interpolation.ThiessenInterpolator;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.scale.Scale;

/**
 * Resolve any of the observables provided by the weather component into
 * interpolated temporal data surfaces using weather stations. Use the universal
 * URN for the service from the node that the network assigns to us.
 * <p>
 * Requires elevation data and global (annual) temperature data for adjustment.
 * 
 * @author Ferd
 *
 */
public class WeatherResolver implements IResolver<IProcess>, IExpression {

	/**
	 * The station artifacts contextualized to the full distributed spatio/temporal
	 * context.
	 */
	private IArtifact weatherStations;
	// we store this to get our weather stations at the first resolution
	private IScale resolutionScale;
	private String source = "all";
	private IWeatherInterpolator interpolator = null;

	public WeatherResolver() {
	}

	public WeatherResolver(IParameters<String> parameters, IContextualizationScope context) {
		// put this away: we get the resources in one shot with all the data for
		// efficiency
		resolutionScale = context.getDataflow().getResolutionScale();
		if (parameters.containsKey("source")) {
			this.source = parameters.get("source", String.class);
		}
	}

	@Override
	public Type getType() {
		return Type.PROCESS;
	}

	@Override
	public IProcess resolve(IProcess target, IContextualizationScope context) throws KlabException {

		if (weatherStations == null) {

			/*
			 * the entire scale with the current resolution
			 */
			IScale wscale = Scale.create(Time.create(resolutionScale.getTime().getStart(),
					resolutionScale.getTime().getEnd(), context.getScale().getTime().getResolution()),
					resolutionScale.getSpace());

			String urn = "klab:weather:stations:" + source;
			String fragment = "";

			// add all outputs according to our model's observables.
			for (String obs : context.getModel().getAttributeObservables().keySet()) {
				fragment += (fragment.isEmpty() ? "" : "&") + obs;
			}
			urn += "#" + fragment;

			this.weatherStations = Resources.INSTANCE.getResourceData(urn, wscale, context.getMonitor()).getArtifact();

			if (this.weatherStations == null) {
				throw new KlabResourceNotFoundException("the weather service is not available on the k.LAB network");
			}

			/**
			 * TODO provide a choice of interpolators. For now this is the one we use.
			 */
			this.interpolator = new ThiessenInterpolator(context.getArtifact("elevation", IState.class),
					(IObjectArtifact) weatherStations);

		}

		Offset offset = context.getScale().as(Offset.class);

		/**
		 * Interpolate the station data for all requested variables.
		 */
		for (String obs : context.getModel().getAttributeObservables().keySet()) {
			this.interpolator.computeState(context.getArtifact(obs, IState.class), obs,
					offset.getOffset(Dimension.Type.TIME) - 1, context.getScale(),
					null /* TODO whatever is needed to turn into required units */);
		}

		return target;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new WeatherResolver(parameters, context);
	}
}
