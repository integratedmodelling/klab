package org.integratedmodelling.mca.contrib.topsis;

/*
 * This <topsis> created by : 
 * Name         : syafiq
 * Date / Time  : 13 April 2017, 7:57 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class Alternative implements Comparable<Alternative> {
	public abstract void collectData(AccumulatorContainer container);

	public abstract void calculateDecisionMatrix(AccumulatorContainer container);

	public abstract void calculateWeightedDecisionMatrix(WeightContainer container);

	public abstract ProfitContainer adaptWeightedDecisionMatrix();

	public abstract void getProfit(ProfitContainer container);

	public abstract void getLoss(ProfitContainer container);

	public abstract void calculateProfitDistance(ProfitContainer container);

	public abstract void calculateLossDistance(ProfitContainer container);

	public abstract void calculatePreferences();
}
