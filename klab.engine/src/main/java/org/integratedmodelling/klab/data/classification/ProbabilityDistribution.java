package org.integratedmodelling.klab.data.classification;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IReducible;
import org.integratedmodelling.klab.api.provenance.IArtifact.ValuePresentation;

/**
 * Still unused for now.
 * 
 * @author Ferd
 *
 */
public class ProbabilityDistribution extends EnumeratedRealDistribution implements IReducible {

	private static final long serialVersionUID = 7172087375008996078L;

	public ProbabilityDistribution(double[] singletons, double[] probabilities) {
		super(singletons, probabilities);
	}

	@Override
	public ValuePresentation getValuePresentation() {
		return ValuePresentation.PROBABILITY_DISTRIBUTION;
	}

	@Override
	public Object reduce(Class<?> cls, boolean forceReduction, ILocator locator) {
		// TODO
		return getNumericalMean();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
