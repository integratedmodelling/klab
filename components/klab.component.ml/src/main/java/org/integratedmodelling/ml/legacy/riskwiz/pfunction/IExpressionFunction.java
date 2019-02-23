package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.Vector;


public interface IExpressionFunction extends IFunction {
    abstract  public String getExpression();
	 
    abstract public Vector<String> getArguments();
	
}
