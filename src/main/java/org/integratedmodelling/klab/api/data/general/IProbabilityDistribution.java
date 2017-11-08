package org.integratedmodelling.klab.api.data.general;

public interface IProbabilityDistribution {

    double[] getData();

    double[] getRanges();

    double getMean();

    double getUncertainty();

}
