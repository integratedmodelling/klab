/**
 * RT.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Apr 30, 2009
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
 * @date      Apr 30, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.interpreter;



/**
 * @author Sergey Krivov
 *
 */
public class RT {

    private  static IInterpreter rt = MVELInterpreter.get();
	
    public static void start() throws Exception {
        rt.start();
    }

    public static void end() {
        rt.end();
    }

    public static Object parse(String str)  {
        return rt.parse(str);
    }
	
    public static Object parse(double val)  {
        return rt.parse(val);
    }

    public static double eval(Object exp) {
        return rt.eval(exp);
    }
	
    public static String toString(Object exp) {
        return rt.toString(exp);
    }

    public static void addVariable(String name, double val) throws Exception {
        rt.addVariable(name, val);
    }

    public static void setVarValue(String name, double val) throws Exception {
        rt.setVarValue(name, val);
    }
	
    public static Object add(Object o1, Object o2) {
        return rt.add(o1, o2);
    }

}
