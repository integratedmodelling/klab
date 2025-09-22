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
package org.integratedmodelling.klab.rest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Serializable histogram with builder.
 * 
 * @author Ferd
 *
 */
public class Histogram {

	public static class Builder {

		private int[] bins;
		private double max;
		private double min;
		private boolean degenerate = false;

		private Builder(double min, double max, int bins) {
			this.min = min;
			this.max = max;
			this.degenerate = Double.isNaN(min) || Double.isNaN(max);
			this.bins = new int[bins];
		}

		public void add(double d) {
			if (!degenerate) {
			    int size = bins.length - 1; 
		        bins[(int) Math.min(Math.ceil((d - min) / (max - min) * size), size)]++;
			}
		}
		
		public void addToIndex(int d) {
			bins[d]++;
		}

		public Histogram build() {

			Histogram ret = new Histogram();

			ret.bins = bins;
			ret.boundaries = new double[] { min, max };
			ret.degenerate = degenerate;

			return ret;
		}
	}

	public static Builder builder(double min, double max, int bins) {
		return new Builder(min, max, bins);
	}

	int[] bins;
	double[] boundaries;
	boolean degenerate = false;

	public int[] getBins() {
		return bins;
	}

	public void setBins(int[] bins) {
		this.bins = bins;
	}

	public double[] getBoundaries() {
		return boundaries;
	}

	public void setBoundaries(double[] boundaries) {
		this.boundaries = boundaries;
	}

	public boolean isDegenerate() {
		return degenerate;
	}

	public void setDegenerate(boolean degenerate) {
		this.degenerate = degenerate;
	}

	public Image getImage(int w, int h) {

		int divs = bins == null ? 0 : bins.length;

		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		img.createGraphics();
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		if (degenerate) {
			g.setColor(Color.RED);
			g.drawLine(0, 0, w - 1, h - 1);
			g.drawLine(0, h - 1, w - 1, 0);
		} else {
			int max = Arrays.stream(bins).max().getAsInt();
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

}
