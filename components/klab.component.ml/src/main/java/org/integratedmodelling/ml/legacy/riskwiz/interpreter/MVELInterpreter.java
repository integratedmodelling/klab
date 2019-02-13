/**
 * MVELInterpreter.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Apr 9, 2009
 *
 * ----------------------------------------------------------------------------------
 * This file is part of riskwiz-cvars.
 * 
 * riskwiz-cvars is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * riskwiz-cvars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * ----------------------------------------------------------------------------------
 * 
 * @copyright 2009 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Apr 9, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.interpreter;

 
import java.util.HashMap;
import java.util.Map;

import org.mvel.MVEL;


/**
 * @author Sergey Krivov
 *
 */
public class MVELInterpreter implements IInterpreter {
	
    private static MVELInterpreter interpreter = new MVELInterpreter();
    private Map vars;

    /**
     * 
     */
    private MVELInterpreter() {
        vars = new HashMap();
    }
	
    public static MVELInterpreter get() {
        return interpreter;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#add(java.lang.Object, java.lang.Object)
     */
    @Override
	public Object add(Object o1, Object o2) {
        String exp = "(" + o1.toString() + " + " + o2.toString() + ")";
		 
        return exp; 
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#addVariable(java.lang.String, double)
     */
    @Override
	public void addVariable(String name, double val) throws Exception {
        vars.put(name, val);

    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#end()
     */
    @Override
	public void end() {// TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#eval(java.lang.Object)
     */
    @Override
	public double eval(Object exp) {
        // Object output= MVEL.executeExpression(exp, vars);
        // if(output instanceof Integer ){
        // return new Double((Integer)output );
        // } else if(output instanceof Double ) {
        // return (Double)output;
        // } else {
        // return Double.NaN;
        // }
		
        Object output = MVEL.eval((String) exp, vars);

        if (output instanceof Integer) {
            return new Double((Integer) output);
        } else if (output instanceof Double) {
            return (Double) output;
        } else {
            return Double.NaN;
        }
		
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#parse(java.lang.String)
     */
    @Override
	public Object parse(String expression)  {
        // Serializable compiled = MVEL.compileExpression(expression);
        // return compiled;
        // ParserContext context = new ParserContext();
        // CompiledExpression compiledExpression = new ExpressionCompiler(expression)
        // .compile(context);
        // return compiledExpression;
        return expression;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#parse(double)
     */
    @Override
	public Object parse(double val) {
        // Serializable compiled = MVEL.compileExpression(String.valueOf(val));
        //
        // return compiled;
        // ParserContext context = new ParserContext();
        // CompiledExpression compiledExpression = new ExpressionCompiler(String.valueOf(val))
        // .compile(context);
        // return compiledExpression;
		
        return String.valueOf(val);
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#setVarValue(java.lang.String, double)
     */
    @Override
	public void setVarValue(String name, double val) throws Exception {
        vars.put(name, val);

    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#start()
     */
    @Override
	public void start() throws Exception {// TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.interpreter.IInterpreter#toString(java.lang.Object)
     */
    @Override
	public String toString(Object exp) {
		 
        // return ((CompiledExpression)exp).getSourceName();
        return String.valueOf(exp);
    }

}
