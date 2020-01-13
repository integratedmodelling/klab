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
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;

/**
 * Handles "klab:random:...." URNs. Possible selectors in third field of URN:
 * <p>
 * <dl>
 * <dt>data</dt>
 * <dd>produces numbers for states with the distribution set in the fourth
 * field. Range, distribution and sequence vary according to parameters min,
 * max, mean, std, variance, alpha, beta, seed; defaults to normalized
 * distributions.</dd>
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
		return true;
	}

	@Override
	public void getEncodedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		switch (urn.getNamespace()) {
		case "data":
			makeData(urn, builder, geometry, context);
			break;
		}

	}

	private void makeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		Object distribution = getDistribution(urn);
		Builder stateBuilder = builder.startState("result");
		for (ILocator locator : geometry) {
			if (distribution instanceof RealDistribution) {
				stateBuilder.add(((RealDistribution)distribution).sample());
			} else if (distribution instanceof IntegerDistribution) {
				stateBuilder.add(((IntegerDistribution)distribution).sample());
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
		}
		throw new KlabUnimplementedException("random: can't handle URN " + urn);
	}

	@Override
	public IGeometry getGeometry(Urn urn) {
		// TODO Auto-generated method stub
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

}
