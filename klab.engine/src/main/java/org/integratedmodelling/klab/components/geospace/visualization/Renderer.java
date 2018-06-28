package org.integratedmodelling.klab.components.geospace.visualization;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.media.jai.Interpolation;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.renderer.lite.RendererUtilities;
import org.geotools.renderer.lite.gridcoverage2d.GridCoverageRenderer;
import org.geotools.styling.ColorMap;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.rest.StateSummary;

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
	private StyleBuilder styleBuilder = new StyleBuilder();

	Renderer() {
		// TODO lookup and load any pre-defined styles in the classpath and local
		// workspace
	}

	/**
	 * Render a given state to an image within a specified viewport using settings
	 * from annotations or defaults.
	 * 
	 * @param state
	 * @param locator
	 *            must specify a full spatial slice
	 * @param imageFile
	 * @param viewport
	 */
	public BufferedImage render(IState state, ILocator locator, int[] viewport) {

		if (state.getSpace() == null
				|| !(state.getSpace() instanceof Space && ((Space) state.getSpace()).getGrid() != null)) {
			throw new IllegalArgumentException("cannot render a state as a map unless its space is gridded");
		}

		ISpace space = state.getSpace();

		// https://github.com/geotools/geotools/blob/master/modules/library/render/src/test/java/org/geotools/renderer/lite/GridCoverageRendererTest.java
		try {

			GridCoverage2D coverage = GeotoolsUtils.INSTANCE.stateToCoverage(state, locator);
			IEnvelope envelope = space.getEnvelope();
			IProjection projection = space.getProjection();
			Rectangle screenSize = new Rectangle(viewport[0], viewport[1]);
			AffineTransform w2s = RendererUtilities.worldToScreenTransform(((Envelope) envelope).getJTSEnvelope(),
					screenSize);
			GridCoverageRenderer renderer = new GridCoverageRenderer(
					((Projection) projection).getCoordinateReferenceSystem(), ((Envelope) envelope).getJTSEnvelope(),
					screenSize, w2s);
			RasterSymbolizer rasterSymbolizer = getRasterSymbolizer(state, locator);
			RenderedImage image = renderer.renderImage(coverage, rasterSymbolizer,
					Interpolation.getInstance(Interpolation.INTERP_BICUBIC), new Color(0f, 0f, 0f, 0f), viewport[0],
					viewport[1]);

			return convertRenderedImage(image);

		} catch (Exception e) {
			throw new KlabInternalErrorException(e);
		}
	}

	public static BufferedImage convertRenderedImage(RenderedImage img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		ColorModel cm = img.getColorModel();
		int width = img.getWidth();
		int height = img.getHeight();
		WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		Hashtable<String, Object> properties = new Hashtable<>();
		String[] keys = img.getPropertyNames();
		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				properties.put(keys[i], img.getProperty(keys[i]));
			}
		}
		BufferedImage result = new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
		img.copyData(raster);
		return result;
	}

	private RasterSymbolizer getRasterSymbolizer(IState state, ILocator locator) {

		StateSummary summary = Observations.INSTANCE.getStateSummary(state, locator);
		String defaultMap = state.getDataKey() == null ? "jet" : "random";
		double opacity = 1.0;

		for (IAnnotation annotation : state.getAnnotations()) {
			if (annotation.getName().equals("colormap")) {

			} else if (annotation.getName().equals("color")) {

			}
		}

		if (defaultMap.equals("jet")) {

		} else if (defaultMap.equals("random")) {

		} else {

		}

		// create style
		// TODO use colormaps and do it right, then export to GeotoolsUtils for map
		// generation also as
		// Image
		ColorMap colorMap = styleBuilder.createColorMap(new String[] { "poco", "meglio", "buono", "ostia" }, // labels
				new double[] { 100, 400, 2000, 3000 }, // values that begin a category, or break points in a
														// ramp, or isolated values, according to the type of
														// color map specified by Type
				new Color[] { new Color(0, 100, 0), new Color(150, 150, 50), new Color(200, 200, 50), Color.WHITE },
				ColorMap.TYPE_RAMP);

		return styleBuilder.createRasterSymbolizer(colorMap, opacity);
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
	public Style getStyle(String id, IState state, IParameters<String> parameters) {

		return null;
	}
}
