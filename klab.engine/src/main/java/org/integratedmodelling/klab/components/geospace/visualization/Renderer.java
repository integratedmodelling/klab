package org.integratedmodelling.klab.components.geospace.visualization;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.geotools.factory.CommonFactoryFinder;
import org.geotools.styling.ColorMap;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.observations.IState;

/**
 * Rendering functions for raster coverages and possibly more. Uses Geotools'
 * SLD support and manages SLD definitions created from classpath, files, URLs
 * and annotations.
 * 
 * @author ferdinando.villa
 *
 */
public enum Renderer {

	INSTANCE;

	private Map<String, Style> styles = new HashMap<>();
	private StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();

	Renderer() {
		// TODO lookup and load any pre-defined styles in the classpath and local
		// workspace
	}

	public void renderToImage(IState state, File imageFile, int[] viewport) {
		
		// https://github.com/geotools/geotools/blob/master/modules/library/render/src/test/java/org/geotools/renderer/lite/GridCoverageRendererTest.java

//        CoordinateReferenceSystem googleMercator = CRS.decode("EPSG:3857");
//        ReferencedEnvelope mapExtent =
//                new ReferencedEnvelope(
//                        -20037508.34, 20037508.34, -20037508.34, 20037508.34, googleMercator);
//        Rectangle screenSize =
//                new Rectangle(200, (int) (mapExtent.getHeight() / mapExtent.getWidth() * 200));
//        AffineTransform w2s = RendererUtilities.worldToScreenTransform(mapExtent, screenSize);
//        GridCoverageRenderer renderer =
//                new GridCoverageRenderer(googleMercator, mapExtent, screenSize, w2s);
//        RasterSymbolizer rasterSymbolizer = new StyleBuilder().createRasterSymbolizer();
//        RenderedImage image =
//                renderer.renderImage(
//                        worldPaletteReader,
//                        null,
//                        rasterSymbolizer,
//                        Interpolation.getInstance(Interpolation.INTERP_BICUBIC),
//                        null,
//                        256,
//                        256);

//		public static BufferedImage convertRenderedImage(RenderedImage img) {
//			if (img instanceof BufferedImage) {
//				return (BufferedImage) img;
//			}
//			ColorModel cm = img.getColorModel();
//			int width = img.getWidth();
//			int height = img.getHeight();
//			WritableRaster raster = cm
//					.createCompatibleWritableRaster(width, height);
//			boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
//			Hashtable properties = new Hashtable();
//			String[] keys = img.getPropertyNames();
//			if (keys != null) {
//				for (int i = 0; i < keys.length; i++) {
//					properties.put(keys[i], img.getProperty(keys[i]));
//				}
//			}
//			BufferedImage result = new BufferedImage(cm, raster,
//					isAlphaPremultiplied, properties);
//			img.copyData(raster);
//			return result;
//		}
		
		
//		File outputfile = new File("image.jpg");
//		ImageIO.write(bufferedImage, "jpg", outputfile);
	}

	private RasterSymbolizer getRasterSymbolizer(IState state) {

		if (!state.getMetadata().contains("colormap")) {
			return styleFactory.getDefaultRasterSymbolizer();
		}

		// create style
		// TODO use colormaps and do it right, then export to GeotoolsUtils for map
		// generation also as
		// Image
		StyleBuilder sb = new StyleBuilder();
		ColorMap colorMap = sb.createColorMap(new String[] { "poco", "meglio", "buono", "ostia" }, // labels
				new double[] { 100, 400, 2000, 3000 }, // values that begin a category, or break points in a
														// ramp, or isolated values, according to the type of
														// color map specified by Type
				new Color[] { new Color(0, 100, 0), new Color(150, 150, 50), new Color(200, 200, 50), Color.WHITE },
				ColorMap.TYPE_RAMP);

		return sb.createRasterSymbolizer(colorMap, 1.0 /* opacity */);
	}

	/**
	 * Get a style with fixed values from a known resource or pre-loaded ID.
	 * 
	 * @param id
	 * @return
	 */
	public Style getStyle(String id) {

		Style ret = styles.get(id);
		if (ret == null) {
			// ID may be classpath file, predefined ID, URL, local file
		}
		return null;
	}

	/**
	 * Get a style from a colorbrewer template, customized to the values in the
	 * passed state.
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public Style getStyle(String id, IState state, IParameters parameters) {

		return null;
	}
}
