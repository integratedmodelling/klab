package app.freelancer.syafiqq.madm.topsis.core.factory;

/*
 * This <topsis> created by : 
 * Name         : syafiq
 * Date / Time  : 13 April 2017, 8:11 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class Criterion {
	public abstract void collect(Accumulator accumulator);

	public abstract void calculate(Accumulator accumulator);

	public abstract void normalize(Weight weight);

	public abstract void searchProfit(Profit profit);

	public abstract void searchLoss(Profit profit);

	public abstract void profitDistance(Profit profit);

	public abstract void lossDistance(Profit profit);
}
