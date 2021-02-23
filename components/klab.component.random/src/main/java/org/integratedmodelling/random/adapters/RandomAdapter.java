package org.integratedmodelling.random.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.GeometricDistribution;
import org.apache.commons.math3.distribution.HypergeometricDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.LaplaceDistribution;
import org.apache.commons.math3.distribution.LevyDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.LogisticDistribution;
import org.apache.commons.math3.distribution.NakagamiDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.commons.math3.distribution.PascalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.random.RandomShapes;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Utils;

/**
 * Handles "klab:random:...." URNs. Produces various types of random data,
 * objects, or events. The namespace (third field of the URN) selects the type
 * of object:
 * <p>
 * <dl>
 * <dt>data</dt>
 * <dd>produces numbers for states with the distribution set in the fourth field
 * (resource ID). Range, distribution and sequence vary according to parameters
 * <code>min</code>, <code>max</code>, <code>mean</code>, <code>std</code>,
 * <code>variance</code>, <code>alpha</code>, <code>beta</code>,
 * <code>seed</code>; defaults to normalized distributions.</dd>
 * <dt>events</dt>
 * <dd>produces random events with the shape and duration defined in parameters.
 * The fourth field (resource ID) can be <code>polygons</code>,
 * <code>points</code> or <code>lines</code>. By default, will produce
 * non-overlapping shapes occupying a 10x10 grid in the context and with a
 * probability of 10% at each tick and a duration between 0.1 and 2x the event
 * frequency. Non-spatial events will be supported later.</dd>
 * <dt>objects</dt>
 * <dd>produces random objects with the shape defined in parameters. The fourth
 * field (resource ID) can be <code>polygons</code>, <code>points</code> or
 * <code>lines</code>. By default, will produce non-overlapping shapes in a
 * 10x10 grid with a 50% probability per cell. Non-spatial objects will be
 * supported later.</dd> *
 * </dl>
 * 
 * @author Ferd
 *
 */
@UrnAdapter(type = "random", version = Version.CURRENT)
public class RandomAdapter implements IUrnAdapter {

	private static final String POISSON = "poisson";
	private static final String PASCAL = "pascal";
	private static final String HYPERGEOMETRIC = "hypergeometric";
	private static final String GEOMETRIC = "geometric";
	private static final String BINOMIAL = "binomial";
	private static final String LAPLACE = "laplace";
	private static final String EXPONENTIAL = "exponential";
	private static final String F = "f";
	private static final String T = "t";
	private static final String NAKAGAMI = "nakagami";
	private static final String BETA = "beta";
	private static final String CAUCHY = "cauchy";
	private static final String TRIANGULAR = "triangular";
	private static final String GAMMA = "gamma";
	private static final String PARETO = "pareto";
	private static final String WEIBULL = "weibull";
	private static final String LEVY = "levy";
	private static final String GAUSSIAN = "gaussian";
	private static final String LOGNORMAL = "lognormal";
	private static final String LOGISTIC = "logistic";
	private static final String UNIFORM = "uniform";

	private static final String FRACTION = "fraction";
	private static final String YDIVS = "ydivs";
	private static final String XDIVS = "xdivs";
	private static final String STD = "std";
	private static final String VERTICES = "vertices";
	private static final String GRID = "grid";
	private static final String DURATION = "duration";
	private static final String START = "start";

	public static final String POLYGONS = "polygons";
	public static final String LINES = "lines";
	public static final String POINTS = "points";

	public static final String OBJECTS = "objects";
	public static final String EVENTS = "events";
	public static final String DATA = "data";
	public static final String NAME = "random";

	public static final String P0 = "p0";
	public static final String P1 = "p1";
	public static final String P2 = "p2";
	public static final String P3 = "p3";

	public static String[] namespace_ids = new String[] { DATA, EVENTS, OBJECTS };
	public static String[] shape_ids = new String[] { LINES, POINTS, POLYGONS };
	public static String[] distribution_ids = new String[] { POISSON, PASCAL, HYPERGEOMETRIC, GEOMETRIC, BINOMIAL,
			LAPLACE, EXPONENTIAL, F, T, NAKAGAMI, BETA, CAUCHY, TRIANGULAR, GAMMA, PARETO, WEIBULL, LEVY, GAUSSIAN,
			LOGNORMAL, LOGISTIC, UNIFORM };
	public static String[] object_attribute_ids = new String[] { FRACTION, XDIVS, YDIVS, VERTICES, STD, GRID, P0, P1,
			P2, P3, DURATION, START };

	private Map<String, Object> distributions = Collections.synchronizedMap(new HashMap<>());

	public RandomAdapter() {
		Arrays.sort(namespace_ids);
		Arrays.sort(shape_ids);
		Arrays.sort(distribution_ids);
		Arrays.sort(object_attribute_ids);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isOnline(Urn urn) {

		switch (urn.getNamespace()) {
		case DATA:
			return Arrays.binarySearch(distribution_ids, urn.getResourceId()) >= 0;
		case OBJECTS:
		case EVENTS:
			return Arrays.binarySearch(shape_ids, urn.getResourceId()) >= 0;
		default:
			break;
		}
		return false;
	}

	@Override
	public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		switch (urn.getNamespace()) {
		case DATA:
			makeData(urn, builder, geometry, context);
			break;
		case EVENTS:
		case OBJECTS:
			makeObjects(urn, builder, geometry, context);
			break;
		}
//		return builder.build();
	}

	private void makeObjects(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {

		// TODO use parameters
		int vertices = urn.getParameters().containsKey(VERTICES) ? Integer.parseInt(urn.getParameters().get(VERTICES))
				: 5;
		int xdivs = urn.getParameters().containsKey(XDIVS) ? Integer.parseInt(urn.getParameters().get(XDIVS)) : 10;
		int ydivs = urn.getParameters().containsKey(YDIVS) ? Integer.parseInt(urn.getParameters().get(YDIVS)) : 10;
		double probability = urn.getParameters().containsKey(FRACTION)
				? Double.parseDouble(urn.getParameters().get(FRACTION))
				: 0.5;
		String artifactName = urn.getResourceId().substring(0, urn.getResourceId().length() - 1);

		IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);

		if (scale.getSpace() != null) {

			IEnvelope envelope = scale.getSpace().getEnvelope();

			Collection<IShape> shapes = null;
			switch (urn.getResourceId()) {
			case POINTS:
				shapes = RandomShapes.INSTANCE.create(envelope, xdivs, ydivs, probability, 1);
				break;
			case LINES:
				shapes = RandomShapes.INSTANCE.create(envelope, xdivs, ydivs, probability, 2);
				break;
			case POLYGONS:
				shapes = RandomShapes.INSTANCE.create(envelope, xdivs, ydivs, probability, vertices);
				break;
			}

			int n = 1;
			for (IShape shape : shapes) {

				/*
				 * TODO honor any filters on the shapes - area, width, length, whatever
				 */

				Builder obuilder = builder.startObject(context.getTargetName(), artifactName + "_" + (n++),
						makeScale(urn, shape, context));

				/*
				 * If states are requested, add them to the returned builder. Could be encoded
				 * in any parameters not understood, e.g. "precipitation=normal(2.3, 1.2)" or
				 * "temperature=12.3". Of course these would need to be returned in the
				 * attributes for the resource too.
				 */
				for (String attribute : urn.getParameters().keySet()) {
					if (Arrays.binarySearch(object_attribute_ids, attribute) < 0) {
						Object value = getAttributeValue(urn.getParameters().get(attribute));
						if (value != null) {
							obuilder.withMetadata(attribute, value);
						}
					}
				}
				obuilder.finishObject();
			}
		}

	}

	private synchronized Object getAttributeValue(String string) {
		if (NumberUtils.encodesDouble(string) || NumberUtils.encodesLong(string)) {
			return Double.parseDouble(string);
		}
		String[] tokens = Utils.parseAsFunctionCall(string);
		if (tokens != null && Arrays.binarySearch(distribution_ids, tokens[0]) >= 0) {
			return sampleDistribution(tokens);
		}

		return null;
	}

	private Double sampleDistribution(String[] tokens) {
		Object distribution = getDistribution(tokens);
		if (distribution instanceof RealDistribution) {
			return ((RealDistribution) distribution).sample();
		} else if (distribution instanceof IntegerDistribution) {
			return (double) ((IntegerDistribution) distribution).sample();
		}
		return null;
	}

	private IGeometry makeScale(Urn urn, IShape shape, IContextualizationScope scope) {

		List<IExtent> extents = new ArrayList<>();
		if (EVENTS.equals(urn.getNamespace())) {

			/*
			 * TODO tie to parameters
			 */
			String[] tspecs = new String[3];
			// this is the time of the extent of computation
			ITime time = scope.getScale().getTime();

			long start = time.getStart().getMilliseconds();
			long duration = time.getEnd().getMilliseconds() - start;

			if (urn.getParameters().containsKey(START)) {
				// same - use ref = 0-1 and refer to length of timestep in ms
				Object d = getAttributeValue(START);
				if (d instanceof Number && ((Number) d).doubleValue() > 0) {
					if (((Number) d).doubleValue() >= duration) {
						d = duration;
					}
					start += (long) ((double) duration * ((Number) d).doubleValue());
				}
			}

			if (urn.getParameters().containsKey(DURATION)) {
				// expected to produce a number of intervals
				Object d = getAttributeValue(DURATION);
				if (d instanceof Number && ((Number) d).doubleValue() > 0) {
					duration = (long) ((double) duration * ((Number) d).doubleValue());
				}
			}

			extents.add(Time.create(start, start + duration));

		} else {
			// full extent for a continuant
			extents.add(scope.getContextSubject().getScale().getTime());
		}

		// TODO if a grid is requested, modify the shape
		if (urn.getParameters().containsKey(GRID)) {
			// TODO
		}

		extents.add(shape);

		return Scale.create(extents).asGeometry();
	}

	private void makeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		Object distribution = getDistribution(urn);
		Builder stateBuilder = builder.startState("result");
		for (ILocator locator : geometry) {
			if (distribution instanceof RealDistribution) {
				stateBuilder.add(((RealDistribution) distribution).sample());
			} else if (distribution instanceof IntegerDistribution) {
				stateBuilder.add(((IntegerDistribution) distribution).sample());
			}
		}
		stateBuilder.finishState();
	}

	private Object getDistribution(Urn urn) {

		List<String> tokens = new ArrayList<>();
		tokens.add(urn.getResourceId());
		for (int i = 0;; i++) {
			if (!urn.getParameters().containsKey("p" + i)) {
				break;
			}
			tokens.add(urn.getParameters().get("p" + i));
		}

		return getDistribution(tokens.toArray(new String[tokens.size()]));
	}

	private synchronized Object getDistribution(String[] tokens) {

		String signature = Arrays.toString(tokens);
		if (distributions.containsKey(signature)) {
			return distributions.get(signature);
		}

		List<Double> params = new ArrayList<>();
		for (int i = 1; i < tokens.length; i++) {
			try {
				params.add(Double.parseDouble(tokens[i]));
			} catch (NumberFormatException e) {
				return null;
			}
		}

		Object ret = null;

		switch (tokens[0]) {
		case UNIFORM:
			if (params.size() == 0) {
				ret = new UniformRealDistribution();
			} else if (params.size() == 2) {
				ret = new UniformRealDistribution(params.get(0), params.get(1));
			}
			break;
		case LOGISTIC:
			if (params.size() == 2) {
				ret = new LogisticDistribution(params.get(0), params.get(1));
			}
			break;
		case LOGNORMAL:
			if (params.size() == 0) {
				ret = new LogNormalDistribution();
			} else if (params.size() == 2) {
				ret = new LogNormalDistribution(params.get(0), params.get(1));
			}
			break;
		case GAUSSIAN:
			if (params.size() == 0) {
				ret = new NormalDistribution();
			} else if (params.size() == 2) {
				ret = new NormalDistribution(params.get(0), params.get(1));
			}
			break;
		case LEVY:
			if (params.size() == 2) {
				ret = new LevyDistribution(params.get(0), params.get(1));
			}
			break;
		case WEIBULL:
			if (params.size() == 2) {
				ret = new WeibullDistribution(params.get(0), params.get(1));
			} else if (params.size() == 3) {
				ret = new WeibullDistribution(params.get(0), params.get(1), params.get(2));
			}
			break;
		case PARETO:
			if (params.size() == 0) {
				ret = new ParetoDistribution();
			} else if (params.size() == 2) {
				ret = new ParetoDistribution(params.get(0), params.get(1));
			} else if (params.size() == 3) {
				ret = new ParetoDistribution(params.get(0), params.get(1), params.get(2));
			}
			break;
		case GAMMA:
			if (params.size() == 0) {
				ret = new ParetoDistribution();
			} else if (params.size() == 2) {
				ret = new ParetoDistribution(params.get(0), params.get(1));
			} else if (params.size() == 3) {
				ret = new ParetoDistribution(params.get(0), params.get(1), params.get(2));
			}
			break;
		case TRIANGULAR:
			if (params.size() == 3) {
				ret = new TriangularDistribution(params.get(0), params.get(1), params.get(2));
			}
			break;
		case CAUCHY:
			if (params.size() == 0) {
				ret = new CauchyDistribution();
			} else if (params.size() == 2) {
				ret = new CauchyDistribution(params.get(0), params.get(1));
			} else if (params.size() == 3) {
				ret = new CauchyDistribution(params.get(0), params.get(1), params.get(2));
			}
			break;
		case BETA:
			if (params.size() == 2) {
				ret = new BetaDistribution(params.get(0), params.get(1));
			} else if (params.size() == 3) {
				ret = new BetaDistribution(params.get(0), params.get(1), params.get(2));
			}
			break;
		case NAKAGAMI:
			if (params.size() == 2) {
				ret = new NakagamiDistribution(params.get(0), params.get(1));
			} else if (params.size() == 3) {
				ret = new NakagamiDistribution(params.get(0), params.get(1), params.get(2));
			}
			break;
		case T:
			if (params.size() == 1) {
				ret = new TDistribution(params.get(0));
			} else if (params.size() == 2) {
				ret = new TDistribution(params.get(0), params.get(1));
			}
			break;
		case F:
			if (params.size() == 2) {
				ret = new FDistribution(params.get(0), params.get(1));
			} else if (params.size() == 3) {
				ret = new FDistribution(params.get(0), params.get(1), params.get(2));
			}
			break;
		case EXPONENTIAL:
			if (params.size() == 1) {
				ret = new ExponentialDistribution(params.get(0));
			} else if (params.size() == 2) {
				ret = new ExponentialDistribution(params.get(0), params.get(1));
			}
			break;
		case LAPLACE:
			if (params.size() == 2) {
				ret = new LaplaceDistribution(params.get(0), params.get(1));
			}
			break;
		case BINOMIAL:
			if (params.size() == 2) {
				ret = new BinomialDistribution(params.get(0).intValue(), params.get(1));
			}
			break;
		case GEOMETRIC:
			if (params.size() == 1) {
				ret = new GeometricDistribution(params.get(0));
			}
			break;
		case HYPERGEOMETRIC:
			if (params.size() == 3) {
				ret = new HypergeometricDistribution(params.get(0).intValue(), params.get(1).intValue(),
						params.get(2).intValue());
			}
			break;
		case PASCAL:
			if (params.size() == 2) {
				ret = new PascalDistribution(params.get(0).intValue(), params.get(1));
			}
			break;
		case POISSON:
			if (params.size() == 1) {
				ret = new PoissonDistribution(params.get(0));
			} else if (params.size() == 2) {
				ret = new PoissonDistribution(params.get(0), params.get(1));
			}
			break;
		}

		if (ret == null) {
			throw new IllegalArgumentException(
					"random adapter: distribution " + tokens[0] + " called with wrong parameters or unknown");
		}

		distributions.put(signature, ret);

		return ret;
	}

	@Override
	public Type getType(Urn urn) {
		switch (urn.getNamespace()) {
		case DATA:
			return Type.NUMBER;
		case EVENTS:
			return Type.EVENT;
		case OBJECTS:
			return Type.OBJECT;
		}
		throw new KlabUnimplementedException("random: can't handle URN " + urn);
	}

	@Override
	public IGeometry getGeometry(Urn urn) {
		switch (urn.getNamespace()) {
		case DATA:
			// TODO use parameters
			return Geometry.create("S2");
		case EVENTS:
			// TODO use parameters
			return Geometry.create("#T1s2");
		case OBJECTS:
			// TODO use parameters
			return Geometry.create("#S2");
		}
		return null;
	}

	@Override
	public String getDescription() {
		return "Random data and objects for testing and stochastic simulations.";
	}

	@Override
	public Collection<String> getResourceUrns() {
		List<String> ret = new ArrayList<>();
		// TODO
		return ret;
	}

	@Override
	public IResource getResource(String urn) {

		Urn kurn = new Urn(urn);
		ResourceReference ref = new ResourceReference();
		ref.setUrn(urn.toString());
		ref.setAdapterType(getName());
		ref.setLocalName(kurn.getResourceId());

		switch (kurn.getNamespace()) {
		case DATA:
			ref.setGeometry("*");
			break;
		case EVENTS:
			// TODO use parameters; space should be 0,1,2 according to resource ID
			ref.setGeometry("#T1s2");
			break;
		case OBJECTS:
			// TODO use parameters
			ref.setGeometry("#S2");
			break;
		}

		/**
		 * any parameters not understood become attributes
		 */
		if (!DATA.equals(kurn.getNamespace())) {
			for (String attribute : kurn.getParameters().keySet()) {
				if (Arrays.binarySearch(object_attribute_ids, attribute) < 0) {
					Object value = getAttributeValue(kurn.getParameters().get(attribute));
					if (value != null) {
						Type type = Utils.getArtifactType(value.getClass());
						AttributeReference aref = new AttributeReference();
						aref.setName(attribute);
						aref.setExampleValue(value.toString());
						aref.setType(type);
						ref.getAttributes().add(aref);
					}
				}
			}
		}

		ref.setVersion(Version.CURRENT);
		ref.setType(getType(kurn));
		return new Resource(ref);
	}
}
