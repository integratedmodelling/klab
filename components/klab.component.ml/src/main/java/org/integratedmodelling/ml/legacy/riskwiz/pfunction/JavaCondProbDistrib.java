package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.List;




/**
 * Abstract implementation of the CondProbDistrib interface. It implements the
 * getLogProb method.
 */
public abstract class JavaCondProbDistrib extends JavaFunction implements ICondProbDistrib {
    @Override
	public double getLogProb(List args, Object childValue)  {
        return Math.log(getProb(args, childValue));
    }
	
    @Override
	public Object getValue(List args)  {
		 
        return getProb(args.subList(1, args.size()), args.get(0));
    }

}
