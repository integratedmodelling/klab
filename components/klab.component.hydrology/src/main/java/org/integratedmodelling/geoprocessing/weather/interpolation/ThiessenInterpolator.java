package org.integratedmodelling.geoprocessing.weather.interpolation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.integratedmodelling.geoprocessing.weather.IWeatherInterpolator;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpatial;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.ThiessenLocator;
import org.integratedmodelling.klab.scale.Scale;

public class ThiessenInterpolator implements IWeatherInterpolator {

	private IScale scale;
	private List<Station> stations;
	private IState elevation;
	private ThiessenLocator<Station> thiessen;

	class Station implements ISpatial {

		private IArtifact data;
		private IShape shape;

		public Station(IArtifact s) {
			this.data = s;
			if (data.getGeometry() instanceof IScale) {
				this.shape = ((IScale)data.getGeometry()).getSpace().getShape();
			} else if (data.getGeometry() instanceof IGeometry) {
				this.shape = Scale.create(data.getGeometry()).getSpace().getShape();
			}
		}

		@Override
		public IShape getShape() {
			return this.shape;
		}
		
	}
	
	public ThiessenInterpolator(IState elevation, IObjectArtifact weatherStations) {

		this.scale = elevation.getScale();
		this.stations = wrap(weatherStations);
		this.elevation = elevation;
		this.thiessen = new ThiessenLocator<>(scale, stations);
	}

	private List<Station> wrap(IObjectArtifact weatherStations) {
		List<Station> ret = new ArrayList<>();
		for (IArtifact s : weatherStations) {
			ret.add(new Station(s));
		}
		return ret;
	}

	@Override
	public void computeState(IState target, String artifactName, long timeOffset,
			Function<Double, Double> transformation) {
		// TODO Auto-generated method stub
		Grid grid = Space.extractGrid(elevation.getScale());

	}

}
