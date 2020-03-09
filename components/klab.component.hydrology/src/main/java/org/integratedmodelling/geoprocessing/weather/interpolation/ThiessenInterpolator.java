package org.integratedmodelling.geoprocessing.weather.interpolation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.integratedmodelling.geoprocessing.weather.IWeatherInterpolator;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpatial;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.utils.ThiessenLocator;
import org.integratedmodelling.klab.components.runtime.artifacts.DataArtifact;
import org.integratedmodelling.klab.components.runtime.artifacts.ObjectArtifact;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CollectionWrapper;

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
				this.shape = ((IScale) data.getGeometry()).getSpace().getShape();
			} else if (data.getGeometry() instanceof IGeometry) {
				this.shape = Scale.create(data.getGeometry()).getSpace().getShape();
			}
		}

		@Override
		public IShape getShape() {
			return this.shape;
		}

		public CollectionWrapper getData(String variable) {
			if (data instanceof ObjectArtifact && !((ObjectArtifact) data).getChildren(IArtifact.class).isEmpty()) {
				for (IArtifact artifact : ((ObjectArtifact) data).getChildren(IArtifact.class)) {
					if (artifact.getId().equals(variable) && artifact instanceof DataArtifact) {
						return ((DataArtifact) artifact).getData();
					}
				}
			}
			return null;
		}

	}

	public ThiessenInterpolator(IState elevation, IObjectArtifact weatherStations) {

		this.scale = elevation.getScale();
		this.stations = wrap(weatherStations);
		this.elevation = elevation;
		this.thiessen = new ThiessenLocator<>(scale, this.stations);
	}

	private List<Station> wrap(IObjectArtifact weatherStations) {
		List<Station> ret = new ArrayList<>();
		for (IArtifact s : weatherStations) {
			ret.add(new Station(s));
		}
		return ret;
	}

	@Override
	public void computeState(IState target, String artifactName, long timeOffset, IScale scale,
			Function<Double, Double> transformation) {

		for (ILocator loc : scale) {

			Offset offset = loc.as(Offset.class);

			Station representativeStation = thiessen.get(offset.getOffset(Type.SPACE));
			double data = Double.NaN;

			if (representativeStation != null) {

				CollectionWrapper storage = representativeStation.getData(artifactName);
				if (storage != null) {
					
					Object d = storage.get(timeOffset, Number.class);
					if (d instanceof Number) {
						data = ((Number)d).doubleValue();
					}
					
					// if (elevation != null
//					&& (var.equals(Weather.MAX_TEMPERATURE_C) || var.equals(Weather.MIN_TEMPERATURE_C))) {
//
//				double refvalue = elevation.get(loc, Double.class);
//				if (!Double.isNaN(data) && !Double.isNaN(refvalue)) {
//					if (refvalue < 0) {
//						/*
//						 * happens, although it shouldn't - maybe we should warn as typically it's a
//						 * missing nodata spec.
//						 */
//						refvalue = 0;
//					}
//					data += (representativeStation.getElevation() - refvalue) * 6.4 / 1000.0;
//				}
//			} else if (representativeStation.refData.containsKey(var)) {
//
//				IState refstate = statesForVar.get(var);
//				double refvalue = refstate.get(loc, Double.class);
//				double refdata = representativeStation.refData.get(var);
//				if (!Double.isNaN(refvalue) && !Double.isNaN(refdata)) {
//					data = (data * refvalue) / (refdata <= 0 ? 1 : refdata);
//				}
//			}

				}
//
			}

			target.set(loc, data);
		}

	}

}
