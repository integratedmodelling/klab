/**
 * IOUtil.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Mar 6, 2008
 *
 * ----------------------------------------------------------------------------------
 * This file is part of RiskWiz.
 * 
 * RiskWiz is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RiskWiz is distributed in the hope that it will be useful,
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
 * @copyright 2008 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Mar 6, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.io;


import java.util.StringTokenizer;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode.DomainType;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.interpreter.RT;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.NoisyT;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularDetF;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;


/**
 * @author Sergey Krivov
 * 
 */
public class IOUtil {

    /**
     * 
     */
    public IOUtil() {// TODO Auto-generated constructor stub
    }

    /**
     * Escape the string s to oblige to the XML rules (e.g.: "'" becomes &apos;,
     * "&" becomes &amp;, and so on).
     * 
     * @param s
     *            The raw string
     * @return String The formatted string
     */
    public static String mangleXMLString(String s) {
        StringBuffer buf = new StringBuffer();
        
        int max = s.length();

        for (int i = 0; i < max; i++) {
            char c = s.charAt(i);

            switch (c) {
            case '\'':
                buf.append("&apos;");
                break; // $NON-NLS-1$

            case '&':
                buf.append("&amp;");
                break; // $NON-NLS-1$

            case '<':
                buf.append("&lt;");
                break; // $NON-NLS-1$

            case '>':
                buf.append("&gt;");
                break; // $NON-NLS-1$

            case '\"':
                buf.append("&quot;");
                break; // $NON-NLS-1$

            default:
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * Converting XMLBif table to flat CPT
     * 
     * @throws ParseException
     * 
     */
    public static void parseTableString(String cptstring, TabularFunction funct)
         {
        StringTokenizer tokenizer = new StringTokenizer(cptstring);

        Vector<String> bifArray = new Vector<String>();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            // Double dbl = new Double(token);
            bifArray.add(token);

        }
        int domainOrder = ((DiscreteDomain) funct.getDomain()).getOrder();

        for (int i = 0; i < bifArray.size(); i++) {
            int z = i % domainOrder;
            int z2 = i / domainOrder;
            int z3 = bifArray.size() / domainOrder;

            funct.setValue(z * z3 + z2, Double.valueOf(bifArray.elementAt(i)));
        }

    }

    // public static void parseDetTableString(String cptstring, CPF cpf) {
    // StringTokenizer tokenizer = new StringTokenizer(cptstring);
    //
    // Vector<String> valArray = new Vector<String>();
    // while (tokenizer.hasMoreTokens()) {
    // String token = tokenizer.nextToken().trim();
    // valArray.add(token);
    //
    // }
    //
    // Vector<Double> bifArray = new Vector<Double>();
    // for (String value : valArray) {
    // Vector<Double> probabilities = toProbabilities(value, cpf
    // .getDomain());
    // bifArray.addAll(probabilities);
    // }
    //
    // int domainOrder = cpf.getDomain().getOrder();
    // for (int i = 0; i < bifArray.size(); i++) {
    // int z = i % domainOrder;
    // int z2 = i / domainOrder;
    // int z3 = bifArray.size() / domainOrder;
    //
    // cpf.setValue(z * z3 + z2, bifArray.elementAt(i));
    // }
    //
    // }

    public static void parseDetTableString(String cptstring, TabularDetF func) {
        StringTokenizer tokenizer = new StringTokenizer(cptstring);

        Vector<String> bifArray = new Vector<String>();

        // DiscreteDomain dom = (DiscreteDomain)func.getDomain();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            bifArray.add(token);

        }
        // process as value table by se4tting fake domain order 1
        int domainOrder = 1;
		 
        for (int i = 0; i < bifArray.size(); i++) {
            int z = i % domainOrder;
            int z2 = i / domainOrder;
            int z3 = bifArray.size() / domainOrder;
			
            func.setValue(z * z3 + z2, bifArray.elementAt(i));
        } 
		
    }

    public static void parseDetFTableString(String cptstring, BNNode node) {

        String[] expressions = cptstring.split(";");

        Vector<String> bifArray = new Vector<String>();
        TabularDetF cpf = (TabularDetF) node.getFunction();

        if (node.getDomType() == DomainType.labels
                || node.getDomType() == DomainType.intervals) {
            DiscreteDomain dom = (DiscreteDomain) cpf.getDomain();

            for (int i = 0; i < expressions.length; i++) {

                String token = expressions[i].trim();

                bifArray.add(token);
                // int k = dom.findState(token);
                // bifArray.add(String.valueOf(k));

            }

            cpf.setValues(bifArray);

        } else if (node.getDomType() == DomainType.continuous) {

            if (expressions.length == 1) {
                cpf.setAll(expressions[0]);

            } else {

                for (int i = 0; i < expressions.length; i++) {

                    String token = expressions[i].trim();

                    cpf.setValue(i, token);
                }
            }

        }

    }

    public static String saveTable(TabularFunction cpf) {
        String cptString = new String();
        int max = cpf.size();
        int tab = ((DiscreteDomain) cpf.getDomain()).getOrder();

        for (int j = 0; j < max / tab; j++) {
            for (int k = 0; k < tab; k++) {

                cptString += RT.toString(cpf.getValue(k * max / tab + j));
                cptString += " ";

            }
        }
        return cptString;
    }

    // public static String saveDeterministicTable(CPF cpf) {
    // String cptString = new String();
    // int max = cpf.size();
    // int tab = cpf.getDomain().getOrder();
    // try {
    // for (int j = 0; j < max / tab; j++) {
    // for (int k = 0; k < tab; k++) {
    // double v = Double.parseDouble(PF.jep.evaluate(
    // cpf.getValue(k * max / tab + j)).toString());
    // if (v != 0.0) {
    // cptString += cpf.getDomain().getState(k);
    // }
    //
    // }
    // cptString += " ";
    // }
    // } catch (NumberFormatException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (ParseException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return cptString;
    // }

    public static String saveDeterministicTable(TabularDetF detfunc) {
		
        String cptString = new String();        
        int max = detfunc.size();
        // set fake tab
        int tab = 1;  

        for (int j = 0; j < max / tab; j++) {
            for (int k = 0; k < tab; k++) {
                cptString += detfunc.getValue(k * max / tab + j);
                cptString += " ";
				 
            }
        }
        return cptString;
		
    }

    public static String saveDeterministicFTable(BNNode node) {
        String cpfString = new String();
        TabularDetF cpf = (TabularDetF) node.getFunction();

        if (node.getDomType() == DomainType.labels
                || node.getDomType() == DomainType.intervals) {

            for (int i = 0; i < cpf.size(); i++) {
                // int istate = (int) RT.eval(cpf.getValue(i));
                String sstate = (String) cpf.getValue(i);
				 
                cpfString += sstate; // ((DiscreteDomain)cpf.getDomain()).getState(istate);
                cpfString += "; ";
            }

        } else if (node.getDomType() == DomainType.continuous) {

            for (int i = 0; i < cpf.size(); i++) {
                cpfString += RT.toString((cpf.getValue(i)));
                cpfString += "; ";

            }

        }

        return cpfString;
    }

    // utilities for RiskWiz IO

    public static void parseCPFString(String cptstring, TabularFunction function)
         {
        String[] expressions = cptstring.split(";");

        Vector<Object> bifArray = new Vector<Object>();

        for (int i = 0; i < expressions.length; i++) {

            String token = expressions[i].trim();

            Object node = RT.parse(token);

            bifArray.add(node);

        }

        int domainOrder = ((DiscreteDomain) function.getDomain()).getOrder();

        // if(cpf.getDomain() instanceof ExpressionDomain){
        // System.out.println( "order="+ cpf.getDomain().getOrder());
        //
        // }
        if (bifArray.size() == 1) {
            function.setAll(bifArray.elementAt(0));

        } else {

            for (int i = 0; i < bifArray.size(); i++) {
                int z = i % domainOrder;
                int z2 = i / domainOrder;
                int z3 = bifArray.size() / domainOrder;

                function.setValue(z * z3 + z2, bifArray.elementAt(i));
            }
        }

    }

    public static String saveCPF(TabularFunction  cpf) {
        String cpfString = new String();
        int max = cpf.size();
        int tab = ((DiscreteDomain) cpf.getDomain()).getOrder();

        for (int j = 0; j < max / tab; j++) {
            for (int k = 0; k < tab; k++) {
                cpfString += RT.toString((cpf.getValue(k * max / tab + j)));
                cpfString += "; ";

            }
        }

        return cpfString;
    }

    public static void parseNoisyParams(String ParamString, NoisyT noisyT) {
        StringTokenizer tokenizer = new StringTokenizer(ParamString);

        Vector<Double> paramArray = new Vector<Double>();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            Double dbl = new Double(token);

            paramArray.add(dbl);

        }

        noisyT.setValues(paramArray);

    }

    public static Vector<Double> parseStatesString(String statesStr) {
        Vector<Double> states = new Vector<Double>();
        StringTokenizer tokenizer = new StringTokenizer(statesStr);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            Double dbl = new Double(token);

            states.add(dbl);

        }
        return states;
    }

    public static String statesString(Vector<Double> states) {
        String str = "";

        for (Double state : states) {
            str += state;
            str += " ";
        }
        return str;
    }
	
    private static Vector<Double> toProbabilities(String value,
            DiscreteDomain dom) {
        int k = dom.getStates().indexOf(value);
        Vector<Double> probabilities = new Vector<Double>();

        for (int i = 0; i < dom.getStates().size(); i++) {
            if (i == k) {
                probabilities.add((double) 1);
            } else {
                probabilities.add((double) 0);
            }
        }
        return probabilities;
    }

}
