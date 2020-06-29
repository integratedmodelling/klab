package org.integratedmodelling.random.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.scale.Scale;

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

	@Override
	public String getName() {
		return "random";
	}

	@Override
	public boolean isOnline(Urn urn) {
		// TODO validate namespace, resource ID and parameters
		return true;
	}

	@Override
	public void getEncodedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		switch (urn.getNamespace()) {
		case "data":
			makeData(urn, builder, geometry, context);
			break;
		case "events":
		case "objects":
			makeObjects(urn, builder, geometry, context);
			break;
		}

	}

	private void makeObjects(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {

		// TODO use parameters
		int vertices = urn.getParameters().containsKey("vertices") ? Integer.parseInt(urn.getParameters().get("vertices")) : 5;
		int xdivs = urn.getParameters().containsKey("xdivs") ? Integer.parseInt(urn.getParameters().get("xdivs")) : 10;
		int ydivs = urn.getParameters().containsKey("ydivs") ? Integer.parseInt(urn.getParameters().get("ydivs")) : 10;
		double probability = urn.getParameters().containsKey("fraction") ? Double.parseDouble(urn.getParameters().get("fraction")) : 0.5;
		String artifactName = urn.getResourceId().substring(0, urn.getResourceId().length() - 1);

		IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);

		if (scale.getSpace() != null) {

			IEnvelope envelope = scale.getSpace().getEnvelope();

			Collection<IShape> shapes = null;
			switch (urn.getResourceId()) {
			case "points":
				shapes = RandomShapes.INSTANCE.create(envelope, xdivs, ydivs, probability, 1);
				break;
			case "lines":
				shapes = RandomShapes.INSTANCE.create(envelope, xdivs, ydivs, probability, 2);
				break;
			case "polygons":
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
				 * TODO if states are requested, add them to the returned builder. Could be
				 * encoded in any parameters not understood, e.g.
				 * "precipitation=normal(2.3, 1.2)" or "temperature=12.3". Of course these would
				 * need to be returned in the attributes for the resource too.
				 */
				obuilder.finishObject();
			}
		}

	}

	private IGeometry makeScale(Urn urn, IShape shape, IContextualizationScope scope) {

		List<IExtent> extents = new ArrayList<>();
		if ("events".equals(urn.getNamespace())) {
			// this is the time of the extent of computation
			ITime time = scope.getScale().getTime();
			// TODO modify as needed by specs
			extents.add(time);
		} else {
			// full extent for a continuant
			extents.add(scope.getContextSubject().getScale().getTime());
		}

		// TODO if a grid is requested, modify the shape

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
		// TODO Auto-generated method stub
		switch (urn.getResourceId()) {
		case "uniform":
			return new UniformRealDistribution();
		case "logistic":
			break;
		case "lognormal":
			return new LogNormalDistribution();
		case "gaussian":
			return new NormalDistribution();
		case "levy":
			break;
		case "weibull":
			break;
		case "pareto":
			break;
		case "gamma":
			break;
		case "triangular":
			break;
		case "cauchy":
			break;
		case "beta":
			break;
		case "nakagami":
			break;
		case "t":
			break;
		case "f":
			break;
		case "exponential":
			break;
		case "laplace":
			break;
		case "binomial":
			break;
		case "geometric":
			break;
		case "hypergeometric":
			break;
		case "pascal":
			break;
		case "poisson":
			break;
		}
		return null;
	}

	@Override
	public Type getType(Urn urn) {
		switch (urn.getNamespace()) {
		case "data":
			return Type.NUMBER;
		case "events":
			return Type.EVENT;
		case "objects":
			return Type.OBJECT;
		}
		throw new KlabUnimplementedException("random: can't handle URN " + urn);
	}

	@Override
	public IGeometry getGeometry(Urn urn) {
		switch (urn.getNamespace()) {
		case "data":
			// TODO use parameters
			return Geometry.create("S2");
		case "events":
			// TODO use parameters
			return Geometry.create("#T1s2");
		case "objects":
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
		case "data":
			ref.setGeometry("*");
			break;
		case "events":
			// TODO use parameters; space should be 0,1,2 according to resource ID
			ref.setGeometry("#T1s2");
			break;
		case "objects":
			// TODO use parameters
			ref.setGeometry("#S2");
			break;
		}

		/**
		 * TODO any parameters not understood should become outputs
		 */
		
		ref.setVersion(Version.CURRENT);
		ref.setType(getType(kurn)); // for now
		return new Resource(ref);
	}
}
