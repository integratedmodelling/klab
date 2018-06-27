/*******************************************************************************
 *  Copyright (C) 2007, 2014:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.components.geospace.visualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;

/**
 * Serializable histogram with
 * @author Ferd
 *
 */
public class Histogram  {

	static class Builder {
		
		private int[] bins;
		private double max;
		private double min;
		private boolean degenerate = false;

		public Builder(double min, double max, int bins) {
			this.min = min;
			this.max = max;
			this.degenerate = Double.isNaN(min) || Double.isNaN(max);
			this.bins = new int[bins];
		}
		
		public void add(double d) {
            if (!degenerate) { 
            	bins[(int) ((d - min) / (max - min) * (bins.length - 1))] ++;
            }
		}
		
		public Histogram build() {

			if (degenerate) {
				return null;
			}
			
			Histogram ret = new Histogram();
			
			ret.bins = bins;
			ret.boundaries = new double[] {min, max};
			
			return ret;
		}
		
		
		
	}
	
	
    String description;

    int[]    bins;
    double[] boundaries;
    String[] binLegends;

    /**
     * True if there are nodata values. Redundant.
     */
    boolean nodata = false;

    long   nodataCount     = 0;
    double aggregatedMean  = 0;
    double aggregatedTotal = Double.NaN;

    /**
     * When the data are not numeric, this contains the amount of occurrences per
     * category, using the string value of the object counted (which appears also in
     * _binLegends in the same order as the bins).
     */
    HashMap<String, Integer> occurrences;

    /**
     * Count of nodata values in the data seen.
     */
    public long getNoDataCount() {
        return nodataCount;
    }

    public int[] getBins() {
        return bins;
    }

    public boolean isEmpty() {
        return nodata;
    }

    public double[] getNumericBoundaries() {
        return boundaries;
    }

    public String[] getValueDescriptions() {
        return binLegends;
    }

    /**
     * Aggregated mean is aware of the physical nature of what we observe and the nature
     * of the observation (aggregated or not).
     */
    public double getAggregatedMean() {
        return aggregatedMean;
    }

    /**
     * Aggregated total is NaN unless we're dealing with an extensive physical property,
     * in which case it's the total amount over the extents of space and time.
     */
    public double getAggregatedTotal() {
        return aggregatedTotal;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage(int w, int h) {

        int divs = bins == null ? 0 : bins.length;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        if (nodata || divs == 0) {
            g.setColor(Color.RED);
            g.drawLine(0, 0, w - 1, h - 1);
            g.drawLine(0, h - 1, w - 1, 0);
        } else {
            int max = max(bins);
            int dw = w / divs;
            int dx = 0;
            g.setColor(Color.GRAY);
            for (int d : bins) {
                int dh = (int) ((double) h * (double) d / max);
                g.fillRect(dx, h - dh, dw, dh);
                dx += dw;
            }
        }
        return img;
    }

    private int max(int[] a) {
        int ret = a[0];
        for (int i = 1; i < a.length; i++) {
            if (ret < a[i]) {
                ret = a[i];
            }
        }
        return ret;
    }

    /**
     * If the histogram represents occurrences of discrete categories, return the map of
     * each category name in the data to its numerosity. Otherwise, return null.
     * 
     * @return the key
     */
    public Map<String, Integer> getKey() {
        return occurrences;
    }

    public void deserialize(org.integratedmodelling.klab.rest.Histogram bean) {

        this.aggregatedMean = bean.getAggregatedMean();
        this.aggregatedTotal = bean.getAggregatedTotal();
        this.nodata = bean.isNodata();
        this.nodataCount = bean.getNodataCount();
        if (bean.getBinLegends() != null) {
            this.binLegends = bean.getBinLegends().toArray(new String[bean.getBinLegends().size()]);
        }
        if (bean.getBins() != null) {
            this.bins = Ints.toArray(bean.getBins());
        }
        if (bean.getBoundaries() != null) {
            this.boundaries = Doubles.toArray(bean.getBoundaries());
        }
        this.occurrences = bean.getOccurrences();
        this.description = bean.getDescription();
    }

    public org.integratedmodelling.klab.rest.Histogram serialize() {

        org.integratedmodelling.klab.rest.Histogram ret = new org.integratedmodelling.klab.rest.Histogram();

        ret.setAggregatedMean(aggregatedMean);
        ret.setAggregatedTotal(aggregatedTotal);
        ret.setDescription(description);
        ret.setNodata(nodata);
        ret.setNodataCount(nodataCount);
        ret.setOccurrences(occurrences);
        if (binLegends != null) {
            ret.setBinLegends(Arrays.asList(binLegends));
        }
        if (bins != null) {
            ret.setBins(Ints.asList(bins));
        }
        if (boundaries != null) {
            ret.setBoundaries(Doubles.asList(boundaries));
        }

        return ret;
    }
}
