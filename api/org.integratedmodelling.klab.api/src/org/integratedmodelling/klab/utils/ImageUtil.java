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

package org.integratedmodelling.klab.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.integratedmodelling.klab.exceptions.KlabIOException;

/**
 * @author Sergey Krivov
 * 
 */
public class ImageUtil {

    /**
     * Draw the passed collection of values as a vertical histogram of specified 
     * width and height.
     * 
     * @param values
     * @param w
     * @param h
     * @return
     */
    public static Image drawHistogram(Collection<Double> values, int w, int h) {

        int divs = values == null ? 0 : values.size();

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        if (divs == 0) {
            g.setColor(Color.RED);
            g.drawLine(0, 0, w - 1, h - 1);
            g.drawLine(0, h - 1, w - 1, 0);
        } else {
            double max = max(values);
            int dw = w / divs;
            int dx = 0;
            g.setColor(Color.GRAY);
            for (Double d : values) {
                int dh = (int) ((double) h * (double) d / max);
                g.fillRect(dx, h - dh, dw, dh);
                dx += dw;
            }
        }
        return img;
    }
    
    private static double max(Collection<Double> values) {
        double ret = Double.NEGATIVE_INFINITY;
        for (double d : values) {
            if (d > ret) {
                ret = d;
            }
        }
        return ret;
    }

    /**
     * Make a clone of a buffered image
     * 
     * @param image
     * @return cloned image
     */
    public static BufferedImage clone(BufferedImage image) {

        String[] pnames = image.getPropertyNames();
        Hashtable<String, Object> cproperties = new Hashtable<String, Object>();
        if (pnames != null) {
            for (int i = 0; i < pnames.length; i++) {
                cproperties.put(pnames[i], image.getProperty(pnames[i]));
            }
        }
        WritableRaster wr = image.getRaster();
        WritableRaster cwr = wr.createCompatibleWritableRaster();
        cwr.setRect(wr);
        BufferedImage cimage = new BufferedImage(image.getColorModel(), // should
                                                                        // be
                                                                        // immutable
        cwr, image.isAlphaPremultiplied(), cproperties);

        return cimage;
    }

    public static int[] upsideDown(int[] pixels, int rowWidth) {
        int[] pixelsud = new int[pixels.length];
        int cols = pixels.length / rowWidth;
        int k = 0;
        for (int i = cols - 1; i >= 0; i--) {
            for (int j = 0; j < rowWidth; j++) {
                pixelsud[k] = pixels[rowWidth * i + j];
                k++;
            }
        }

        return pixelsud;
    }

    public static void saveImage(BufferedImage bimg, String fileName) throws KlabIOException {

        try {
            File outputfile = new File(fileName);
            String ext = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());

            ImageIO.write(bimg, ext, outputfile);
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    // The rest of the class is pretty much is inside the besball

    // This method returns a buffered image with the contents of an image
    private static BufferedImage toBufferedImage(Image image, int w, int h) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent
        // Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the
        // screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(w, h, transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(w, h, type); // image.getWidth(null),
                                                    // image.getHeight(null),
                                                    // type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, w, h, null); // /??? scaling
        g.dispose();

        return bimage;
    }

    // This method returns true if the specified image has transparent pixels
    private static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm == null ? false : cm.hasAlpha();
    }

    public static void show(File image, String title) {
        try {
            show(ImageIO.read(image), title);
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    public static void show(File image) {
        show(image, "Image display");
    }

    public static void show(BufferedImage image) {
        show(image, "Image display");
    }
    
    public static void show(BufferedImage image, String title) {
    	
    	Icon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);

        final JFrame f = new JFrame(title);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.getContentPane().add(label);
        f.pack();
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }

}
