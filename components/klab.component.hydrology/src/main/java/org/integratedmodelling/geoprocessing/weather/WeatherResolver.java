package org.integratedmodelling.geoprocessing.weather;

import java.util.ArrayList;
import java.util.List;

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
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

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
public class WeatherResolver extends AbstractContextualizer implements IResolver<IProcess>, IExpression {

	/**
	 * The station artifacts contextualized to the full distributed spatio/temporal
	 * context.
	 */
	private IArtifact weatherStations;
	// we store this to get our weather stations at the first resolution
	private IScale resolutionScale;
	private String source = "all";
	private IWeatherInterpolator interpolator = null;
	private List<Pair<String, IState>> states = new ArrayList<>();

	public WeatherResolver() {
	}

	public WeatherResolver(IParameters<String> parameters, IContextualizationScope scope) {
		// put this away: we get the resources in one shot with all the data for
		// efficiency
		resolutionScale = scope.getResolutionScale();
		if (parameters.containsKey("source")) {
			this.source = parameters.get("source", String.class);
		}
	}

	@Override
	public Type getType() {
		return Type.PROCESS;
	}

	@Override
	public IProcess resolve(IProcess target, IContextualizationScope scope) throws KlabException {

		if (weatherStations == null) {

			/*
			 * the entire scale with the current resolution
			 */
			IScale wscale = Scale.create(Time.create(resolutionScale.getTime().getStart(),
					resolutionScale.getTime().getEnd(), scope.getScale().getTime().getResolution()),
					resolutionScale.getSpace().getBoundingExtent());

			String urn = "klab:weather:stations:" + source;
			String fragment = "";

			// add all outputs according to our model's observables.
			for (int i = 1; i < scope.getModel().getObservables().size(); i++) {
				String variable = scope.getModel().getObservables().get(i).getName();
				fragment += (fragment.isEmpty() ? "" : "&") + variable;
				IState state = scope.getArtifact(variable, IState.class);
				if (state == null) {
					scope.getMonitor().warn("weather: cannot find state for " + variable);
				} else {
					states.add(new Pair<>(variable, state));
				}
			}
			urn += "#" + fragment + "&expand=true";

			this.weatherStations = Resources.INSTANCE.getResourceData(urn, wscale, scope.getMonitor()).getArtifact();

			if (this.weatherStations == null) {
				throw new KlabResourceNotFoundException("the weather service is not available on the k.LAB network");
			}

			/**
			 * TODO provide a choice of interpolators. For now this is the one we use.
			 */
			this.interpolator = new ThiessenInterpolator(scope.getArtifact("elevation", IState.class),
					(IObjectArtifact) weatherStations);

		}

		Offset offset = scope.getScale().as(Offset.class);

		/**
		 * Interpolate the station data for all requested variables.
		 */
		for (Pair<String, IState> ps : states) {
			this.interpolator.computeState(ps.getSecond(), ps.getFirst(), offset.getOffset(Dimension.Type.TIME) - 1,
					scope.getScale(), null /*
												 * TODO whatever is needed to turn into required units, potentially
												 * including recontextualization - must interoperate with the resource
												 */);
		}

		return target;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new WeatherResolver(parameters, context);
	}
}
