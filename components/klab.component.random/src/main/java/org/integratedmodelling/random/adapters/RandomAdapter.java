package org.integratedmodelling.random.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
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
 * min, max, mean, std, variance, alpha, beta, seed; defaults to normalized
 * distributions.</dd>
 * <dt>events</dt>
 * <dd>produces random events with the shape and duration defined in parameters.
 * The fourth field (resource ID) can be polygons, points or lines. By default,
 * will produce non-overlapping shapes occupying a 10x10 grid in the context and
 * with a probability of 10% at each tick and a duration between 0.1 and 2x the
 * event frequency. Non-spatial events will be supported later.</dd>
 * <dt>objects</dt>
 * <dd>produces random objects with the shape defined in parameters. The fourth
 * field (resource ID) can be polygons, points or lines. By default, will
 * produce non-overlapping shapes in a 10x10 grid with a 50% probability per
 * cell. Non-spatial objects will be supported later.</dd> *
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

	public static final String POLYGONS = "polygons";
	public static final String LINES = "lines";
	public static final String POINTS = "points";

	public static final String OBJECTS = "objects";
	public static final String EVENTS = "events";
	public static final String DATA = "data";
	public static final String NAME = "random";

	public static String[] namespace_ids = new String[] { DATA, EVENTS, OBJECTS };
	public static String[] shape_ids = new String[] { LINES, POINTS, POLYGONS };
	public static String[] distribution_ids = new String[] { POISSON, PASCAL, HYPERGEOMETRIC, GEOMETRIC, BINOMIAL,
			LAPLACE, EXPONENTIAL, F, T, NAKAGAMI, BETA, CAUCHY, TRIANGULAR, GAMMA, PARETO, WEIBULL, LEVY, GAUSSIAN,
			LOGNORMAL, LOGISTIC, UNIFORM };
	public static String[] object_attribute_ids = new String[] { FRACTION, XDIVS, YDIVS, VERTICES, STD, GRID };

	private Map<String, Object> distributions = Collections.synchronizedMap(new HashMap<>());
	
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
	public void getEncodedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		switch (urn.getNamespace()) {
		case DATA:
			makeData(urn, builder, geometry, context);
			break;
		case EVENTS:
		case OBJECTS:
			makeObjects(urn, builder, geometry, context);
			break;
		}

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
							Builder sbuilder = obuilder.startState(attribute);
							sbuilder.add(value);
							sbuilder.finishState();
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
			return ((RealDistribution)distribution).sample();
		}
		
		// TODO other distributions
		
		return null;
	}

	private IGeometry makeScale(Urn urn, IShape shape, IContextualizationScope scope) {

		List<IExtent> extents = new ArrayList<>();
		if (EVENTS.equals(urn.getNamespace())) {
			// this is the time of the extent of computation
			ITime time = scope.getScale().getTime();
			// TODO modify as needed by specs
			extents.add(time);
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
		for (int i = 0; ; i++) {
			if (!urn.getParameters().containsKey("p"+i)) {
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
			ret = new UniformRealDistribution();
		case LOGISTIC:
			break;
		case LOGNORMAL:
			ret = new LogNormalDistribution();
		case GAUSSIAN:
			ret = new NormalDistribution();
		case LEVY:
			break;
		case WEIBULL:
			break;
		case PARETO:
			break;
		case GAMMA:
			break;
		case TRIANGULAR:
			break;
		case CAUCHY:
			break;
		case BETA:
			break;
		case NAKAGAMI:
			break;
		case T:
			break;
		case F:
			break;
		case EXPONENTIAL:
			break;
		case LAPLACE:
			break;
		case BINOMIAL:
			break;
		case GEOMETRIC:
			break;
		case HYPERGEOMETRIC:
			break;
		case PASCAL:
			break;
		case POISSON:
			break;
		}
		
		if (ret != null) {
			distributions.put(signature, ret);
		}
		
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
