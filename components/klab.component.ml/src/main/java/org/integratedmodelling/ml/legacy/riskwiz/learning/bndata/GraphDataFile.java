/**
 * GraphDataFile.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 21, 2008
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
 * @date      May 21, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.learning.bndata;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.learning.data.Instances;
import org.integratedmodelling.ml.legacy.riskwiz.learning.data.loader.ArffLoader;
import org.integratedmodelling.ml.legacy.riskwiz.learning.data.loader.CSVLoader;
import org.integratedmodelling.ml.legacy.riskwiz.learning.data.loader.Loader;
import org.integratedmodelling.ml.legacy.riskwiz.learning.data.loader.XRFFLoader;
import org.integratedmodelling.ml.legacy.riskwiz.learning.data.loader.ConverterUtils.DataSource;


/**
 * @author Sergey Krivov
 * 
 */
public class GraphDataFile extends GraphDataAdapter implements IGraphTable {

    GraphDataWEKA gdata;

    /**
     * 
     */
    public GraphDataFile() {// TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.riskwiz.learning.bndata.GraphDataAdapter#getScheme()
     */
    @Override
    public Vector<String> getScheme() {
        if (gdata != null) {
            return gdata.getScheme();
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.riskwiz.learning.bndata.GraphDataAdapter#getValues()
     */
	 
    @Override
	public Vector<Vector<String>> getValues() {
        if (gdata != null) {
            return gdata.getValues();
        } else {
            return null;
        }
    }
	
    public void readArff(String location) throws Exception {
        read(location, new ArffLoader());
    }
	
    public void readCSV(String location) throws Exception {
        read(location, new CSVLoader());
    }
	
    public void readXRFF(String location) throws Exception {
        read(location, new XRFFLoader());
    }
	
    public void read(String location, Loader lod) throws Exception {
		 
        DataSource source = new DataSource(location, lod);
        Instances insts = source.getDataSet();

        if (insts != null) {
            gdata = new GraphDataWEKA(insts);
        }
		 
    }
	
}
