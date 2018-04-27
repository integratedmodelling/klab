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
package org.integratedmodelling.klab.visualization;

import org.integratedmodelling.kim.utils.Pair;

public class Viewport  {

    int x, y;

    public Viewport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return x;
    }

    public int getHeight() {
        return y;
    }

    public int[] getSizeFor(double imageWidth, double imageHeight) {
        Pair<Integer, Integer> ret = getViewportSize(x, y, imageWidth, imageHeight);
        return new int[] { ret.getFirst(), ret.getSecond() };
    }

    /**
     * Define the plot size for the given map dimensions that ensures
     * that a map drawing of the area fits maximally within a viewport.
     * 
     * @param viewportWidth
     * @param viewportHeight
     * @param mapWidth
     * @param mapHeight
     * @return <width, height> of the largest map that fits in the viewport without distorsion.
     */
    public static Pair<Integer, Integer> getViewportSize(int viewportWidth, int viewportHeight,
            Number mapWidth, Number mapHeight) {

        int x = viewportWidth, y = viewportHeight;
        double image_aspect_ratio = mapWidth.doubleValue() / mapHeight.doubleValue();

        // largest side of image must fit within corresponding side of viewport
        if (mapWidth.doubleValue() > mapHeight.doubleValue()) {
            x = viewportWidth;
            y = (int) (((double) x) / image_aspect_ratio);
            if (y > viewportHeight) {
                // reduce further
                double fc = (double) viewportHeight / (double) y;
                x = (int) ((double) x * fc);
                y = (int) ((double) y * fc);
            }
        } else {
            y = viewportHeight;
            x = (int) (((double) y) * image_aspect_ratio);
            if (x > viewportWidth) {
                // reduce further
                double fc = (double) viewportWidth / (double) x;
                x = (int) ((double) x * fc);
                y = (int) ((double) y * fc);
            }
        }

        return new Pair<Integer, Integer>(x, y);
    }

    public void setSize(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
