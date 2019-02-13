///**
// * ClojureIntrpreter.java
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
//import clojure.lang.Compiler;
//import clojure.lang.Namespace;
//import clojure.lang.RT;
//import clojure.lang.Symbol;
//
//
///**
// * @author Sergey Krivov
// *
// */
//public class ClojureInterpreter implements IInterpreter {
//	
//    private static ClojureInterpreter interpreter = new ClojureInterpreter();
//    private Namespace ns;
//    private Namespace cashns;
//    private String dir;
//
//    /**
//     * 
//     */
//    private ClojureInterpreter() {// TODO Auto-generated constructor stub
//    }
//	
//    public static ClojureInterpreter get() {
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
//	public void addVariable(String name, double val) throws Exception {
//        String exp = "(def " + name + " (ref " + val + " ))";
//
//        Compiler.eval(RT.readString(exp));
//
//    }
//	
//    @Override
//	public void setVarValue(String name, double val) throws Exception {
//        String exp = "(dosync (ref-set " + name + " " + val + " ))";
//
//        Compiler.eval(RT.readString(exp));
//
//    }
//
//    /* (non-Javadoc)
//     * @see org.integratedmodelling.riskwiz.pf.IInterpreter#eval(java.lang.Object)
//     */
//    @Override
//	public double eval(Object exp) {
//        // TODO Auto-generated method stub
//        try {
//            return Double.parseDouble(Compiler.eval(exp).toString());
//        } catch (NumberFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return Double.NaN;
//    }
//
//    /* (non-Javadoc)
//     * @see org.integratedmodelling.riskwiz.pf.IInterpreter#parse(java.lang.String)
//     */
//    @Override
//	public Object parse(String exp) {		 
//        return RT.readString(exp);
//    }
//	
//    @Override
//	public Object parse(double val) {
//        return RT.readString(String.valueOf(val));
//    }
//
//    /* (non-Javadoc)
//     * @see org.integratedmodelling.riskwiz.pf.IInterpreter#toString(java.lang.Object)
//     */
//    @Override
//	public String toString(Object exp) {
//		
//        return exp.toString();
//    }
//
//    public Object invoke(String op, Object exp1, Object exp2) {
// 
//        return RT.readString("(" + op + " " + exp1 + " " + exp2 + ")");
//    }
//
//    @Override
//	public Object add(Object o1, Object o2) {
//        return  invoke("+", o1, o2);
//    }
//	
//    @Override
//	public void start() throws Exception {
//        ns = Namespace.findOrCreate(Symbol.intern("rwiz"));
//        Compiler.eval(RT.readString("(use   'rwiz)"));
//		
//    }
//	
//    public void start(String dir) throws Exception {
//        start();
//        this.dir = dir;
//        ns = Namespace.findOrCreate(Symbol.intern("rwiz"));
//        Compiler.eval(RT.readString("(use   'rwiz" + dir + ")"));
//		
//    }
//	
//    @Override
//	public void end() {// TODO Auto-generated method stub
//    }
//
//}
