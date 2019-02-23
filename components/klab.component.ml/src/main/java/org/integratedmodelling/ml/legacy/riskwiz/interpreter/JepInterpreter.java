///**
// * JepInterpreter.java
// * ----------------------------------------------------------------------------------
// * 
// * Copyright (C) 2009 www.integratedmodelling.org
// * Created: Mar 20, 2009
// *
// * ----------------------------------------------------------------------------------
// * This file is part of riskwiz-cvars.
// * 
// * riskwiz-cvars is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 3 of the License, or
// * (at your option) any later version.
// * 
// * riskwiz-cvars is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// * 
// * You should have received a copy of the GNU General Public License
// * along with the software; if not, write to the Free Software
// * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
// * 
// * ----------------------------------------------------------------------------------
// * 
// * @copyright 2009 www.integratedmodelling.org
// * @author    Sergey Krivov
// * @date      Mar 20, 2009
// * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
// * @link      http://www.integratedmodelling.org
// **/
//
//package org.integratedmodelling.ml.legacy.riskwiz.interpreter;
//
//
//import org.lsmp.djep.xjep.NodeFactory;
//import org.lsmp.djep.xjep.XJep;
//import org.nfunk.jep.Node;
//import org.nfunk.jep.Operator;
//import org.nfunk.jep.OperatorSet;
//import org.nfunk.jep.ParseException;
//
//
///**
// * @author Sergey Krivov
// *
// */
//public class JepInterpreter implements IInterpreter {
//	
//    public static XJep jep = new XJep();
//    private static JepInterpreter interpreter = new JepInterpreter();
//
//    /**
//     * 
//     */
//    private JepInterpreter() {}
//	
//    public static JepInterpreter get() {
//        return interpreter;
//    }
//	
//    @Override
//	public Object clone() throws CloneNotSupportedException {
//        throw new CloneNotSupportedException();
//    }
//
//    /* (non-Javadoc)
//     * @see org.integratedmodelling.riskwiz.pf.IInterpreter#addVariable(java.lang.String, double)
//     */
//    @Override
//	public void addVariable(String name, double val) {
//        jep.addVariable(name, val);
//    }
//	
//    @Override
//	public void setVarValue(String name, double val) {
//        jep.addVariable(name, val);
//		
//    }
//
//    /* (non-Javadoc)
//     * @see org.integratedmodelling.riskwiz.pf.IInterpreter#eval(java.lang.Object)
//     */
//    @Override
//	public double eval(Object exp) {
//		 
//        double val = Double.NaN;
//
//        try {
//            val = Double.parseDouble(jep.evaluate((Node) exp).toString());
//        } catch (NumberFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return val;
//		 
//    }
//	
//    public Object evaluate(Object exp) throws ParseException {
//        return jep.evaluate((Node) exp);
//    }
//
//    /* (non-Javadoc)
//     * @see org.integratedmodelling.riskwiz.pf.IInterpreter#parse(java.lang.String)
//     */
//    @Override
//	public Object parse(String str) throws ParseException {		 
//        return jep.parse(str);
//    }
//	
//    @Override
//	public Object parse(double val) throws ParseException {
//        NodeFactory nf = getNodeFactory();
//
//        return nf.buildConstantNode(new Double(val));
//    }
//
//    /* (non-Javadoc)
//     * @see org.integratedmodelling.riskwiz.pf.IInterpreter#toString(java.lang.Object)
//     */
//    @Override
//	public String toString(Object exp) {		 
//        return jep.toString((Node) exp);
//    }
//	
//    public NodeFactory getNodeFactory() {
//        return jep.getNodeFactory();
//    }
//	
//    public OperatorSet getOperatorSet() {
//        return jep.getOperatorSet();
//    }
//	
//    public Node simplify(Node exp) throws ParseException {
//        return jep.simplify(exp);
//    }
//
//    @Override
//	public void end() {// TODO Auto-generated method stub
//    }
//
//    @Override
//	public void start() {// TODO Auto-generated method stub
//    }
//
//    public Node invoke(Operator op, Node n1, Node n2) {
//        try {
//            return this.simplify(
//                    this.getNodeFactory().buildOperatorNode(op, n1, n2));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//	public Object add(Object o1, Object o2) {
//        return  invoke(this.getOperatorSet().getAdd(), (Node) o1, (Node) o2);
//    }
//
//}
