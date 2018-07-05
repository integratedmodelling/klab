package org.integratedmodelling.klab.components.geospace.visualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.geotools.brewer.color.BrewerPalette;
import org.geotools.brewer.color.ColorBrewer;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.renderer.lite.RendererUtilities;
import org.geotools.renderer.lite.gridcoverage2d.GridCoverageRenderer;
import org.geotools.styling.ColorMap;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.ColorUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

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
	ColorBrewer colorBrewer = new ColorBrewer();

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
				|| !(state.getSpace() instanceof Space && ((Space) state.getSpace()).getGrid().isPresent())) {
			throw new IllegalArgumentException("cannot render a state as a map unless its space is gridded");
		}

		ISpace space = state.getSpace();
		IGrid grid = ((Space) state.getSpace()).getGrid().get();

		// https://github.com/geotools/geotools/blob/master/modules/library/render/src/test/java/org/geotools/renderer/lite/GridCoverageRendererTest.java
		try {

			Viewport vport = new Viewport(viewport[0], viewport.length == 1 ? viewport[0] : viewport[1]);
			GridCoverage2D coverage = GeotoolsUtils.INSTANCE.stateToCoverage(state, locator);
			IEnvelope envelope = space.getEnvelope();
			IProjection projection = space.getProjection();
			int[] imagesize = vport.getSize(grid.getXCells(), grid.getYCells());
			Rectangle screenSize = new Rectangle(imagesize[0], imagesize[1]);
			AffineTransform w2s = RendererUtilities.worldToScreenTransform(((Envelope) envelope).getJTSEnvelope(),
					screenSize);
			GridCoverageRenderer renderer = new GridCoverageRenderer(
					((Projection) projection).getCoordinateReferenceSystem(), ((Envelope) envelope).getJTSEnvelope(),
					screenSize, w2s);
			RasterSymbolizer rasterSymbolizer = getRasterSymbolizer(state, locator);

			Rectangle imageBounds = new Rectangle(viewport[0], viewport[1]);
			BufferedImage image = new BufferedImage(viewport[0], viewport[1], BufferedImage.TYPE_INT_ARGB);
		    Graphics2D gr = image.createGraphics();
		    gr.setPaint(new Color(0f, 0f, 0f, 0f));
		    gr.fill(imageBounds);
		    
		    renderer.paint(gr, coverage, rasterSymbolizer);
		    
		    return image;
//			
//			RenderedImage image = renderer.renderImage(coverage, rasterSymbolizer,
//					Interpolation.getInstance(Interpolation.INTERP_BICUBIC), , viewport[0],
//					viewport[1]);
//
//			return convertRenderedImage(image);

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

	public RasterSymbolizer getRasterSymbolizer(IState state, ILocator locator) {

		StateSummary summary = Observations.INSTANCE.getStateSummary(state, locator);

		float opacity = 1f;
		Color[] colors = null;
		double[] values = null;
		double midpoint = Double.NaN;
		String[] labels = null;
		int colormapType = state.getDataKey() == null ? ColorMap.TYPE_RAMP
				: (state.getDataKey().isOrdered() ? ColorMap.TYPE_INTERVALS : ColorMap.TYPE_VALUES);

		// TODO parameters for shaded relief and contrast enhancement
		
		for (IAnnotation annotation : state.getAnnotations()) {
			if (annotation.getName().equals("colormap")) {

				// check if we have just a name
				String name = annotation.get("value", String.class);
				if (name == null) {
					name = annotation.get("name", String.class);
				}

				if (name != null) {
					colors = getColormap(name, opacity);
					if (colors == null && colorBrewer.hasPalette(name)) {
						BrewerPalette palette = colorBrewer.getPalette(name);
						colors = palette.getColors(state.getDataKey() == null ? 256 : state.getDataKey().size());
						if (palette.getType() == ColorBrewer.DIVERGING) {
							midpoint = 0.0;
						} else if (palette.getType() == ColorBrewer.QUALITATIVE) {
							if (colormapType != ColorMap.TYPE_VALUES) {
								throw new IllegalArgumentException("qualitative colormap chosen for quantitative data");
							}
						}
					}
				} else if (annotation.containsKey("values")) {

					Map<?, ?> vals = annotation.get("values", Map.class);
					List<Pair<Object, Color>> svals = new ArrayList<>();
					Class<?> type = null;
					for (Object o : vals.keySet()) {
						if (type == null) {
							type = o.getClass();
						} else if (!type.equals(o.getClass())) {
							throw new IllegalArgumentException(
									"color keys must be of the same type in a colormap specification");
						}
						svals.add(new Pair<>(o, parseColor(vals.get(o))));
					}

					if (IConcept.class.isAssignableFrom(type)) {
						colormapType = ColorMap.TYPE_VALUES;
					} else if (Range.class.isAssignableFrom(type)) {
						colormapType = ColorMap.TYPE_INTERVALS;
					} else if (Number.class.isAssignableFrom(type)) {
						colormapType = annotation.get("continuous", Boolean.TRUE) ? ColorMap.TYPE_RAMP : ColorMap.TYPE_INTERVALS;
					} else if (Boolean.class.isAssignableFrom(type)) {
						colormapType = ColorMap.TYPE_VALUES;
						for (Pair<Object, Color> pair : svals) {
							if ((Boolean)pair.getFirst()) {
								pair.setFirst(1.0);
							} else {
								pair.setFirst(0.0);
							}
						}
					} else {
						throw new IllegalArgumentException(
								"invalid color keys: must be number, boolean, concept or range");
					}
					
					labels = new String[svals.size()];
					values = new double[svals.size()];
					colors = new Color[svals.size()];
					
					int i = 0;
					for (Pair<Object, Color> pair : svals) {
						
						// we assume the user has used proper sorting.
						if (pair.getFirst() instanceof IConcept) {
							labels[i] = Concepts.INSTANCE.getDisplayName((IConcept)pair.getFirst());
							values[i] = state.getDataKey().reverseLookup((IConcept)pair.getFirst());
							colors[i] = pair.getSecond();
						}

						i++;
					}

				} else if (annotation.containsKey("colors")) {

					List<Color> clrs = new ArrayList<>();
					for (Object o : annotation.get("colors", List.class)) {
						clrs.add(parseColor(o));
					}
					colors = clrs.toArray(new Color[clrs.size()]);
				}

				if (annotation.containsKey("opacity")) {
					opacity = annotation.get("opacity", Number.class).floatValue();
				}

				if (annotation.containsKey("midpoint")) {
					midpoint = annotation.get("midpoint", Number.class).floatValue();
				}

				// check for modifiers

			} else if (annotation.getName().equals("color")) {

				// must be boolean

			}
		}

		if (colors == null) {
			if (state.getDataKey() == null) {
				colors = jet(1.0f);
			} else {

				// establish if we have an ordering
				if (state.getDataKey().isOrdered()) {
					// TODO intervals
				} else {
					if (state.getDataKey().size() <= random20.length) {
						colors = Arrays.copyOf(random20, state.getDataKey().size());
					} else if (state.getDataKey().size() <= excel.length) {
						colors = Arrays.copyOf(excel, state.getDataKey().size());
					} else {
						// TODO warn and use a scale
					}
				}
			}
		}

		if (colormapType == ColorMap.TYPE_RAMP || colormapType == ColorMap.TYPE_INTERVALS) {
			values = new double[colors.length];
			labels = new String[colors.length];
			for (int i = 0; i < colors.length; i++) {
				// TODO handle midpoint
				values[i] = summary.getRange().get(0) + ((summary.getRange().get(1) - summary.getRange().get(0))
						* ((double) (i + 1) / (double) colors.length));
				labels[i] = "" + values[i];
			}

		} else {
			// build value array
		}

		ColorMap colorMap = styleBuilder.createColorMap(labels, values, colors, colormapType);
		return styleBuilder.createRasterSymbolizer(colorMap, opacity);
	}

	private Color parseColor(Object o) {

		Color ret = null;

		if (o instanceof String) {
			if (o.equals("none")) {
				return new Color(0f, 0f, 0f, 0f);
			}
			int[] color = ColorUtils.getRGB(o.toString());
			if (color == null) {
				throw new IllegalArgumentException("identifier " + o + " does not specify a known CSS color");
			}
			ret = new Color(color[0], color[1], color[2]);
		} else if (o instanceof List) {
			// TODO more error handling
			ret = new Color(((Number) ((List<?>) o).get(0)).intValue(), ((Number) ((List<?>) o).get(1)).intValue(),
					((Number) ((List<?>) o).get(2)).intValue());
		}

		if (ret == null) {
			throw new IllegalArgumentException("bad color specification: need color name or RGB list");
		}

		return ret;
	}

	public Color[] getColormap(String name, float alpha) {

		switch (name) {
		case "jet":
			return jet(alpha);
		case "heat":
			return heat();
		case "rainbow":
			return rainbow();
		case "redgreen":
			return redgreen();
		case "redblackgreen":
			return redblackgreen();
		case "wave":
			return wave();
		case "excel":
			return excel;
		case "random":
			return random20;
		}
		return null;
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

	/*
	 * -----------------------------------------------------------------------------
	 * ------ Well-known colormaps
	 * -----------------------------------------------------------------------------
	 * ------
	 */
	public Color[] jet(float alpha) {

		Color[] ret = new Color[256];

		float r = 0;
		float g = 0;
		float b = 0;

		int n = 256 / 4;
		for (int i = 0; i < 256; ++i) {
			if (i < n / 2.) {
				r = 0;
				g = 0;
				b = 0.5f + (float) i / n;
			} else if (i >= n / 2. && i < 3. * n / 2.) {
				r = 0;
				g = (float) i / n - 0.5f;
				b = 1.f;
			} else if (i >= 3. * n / 2. && i < 5. * n / 2.) {
				r = (float) i / n - 1.5f;
				g = 1.f;
				b = 1.f - (float) i / n + 1.5f;
			} else if (i >= 5. * n / 2. && i < 7. * n / 2.) {
				r = 1.f;
				g = 1.f - (float) i / n + 2.5f;
				b = 0;
			} else if (i >= 7. * n / 2.) {
				r = 1.f - (float) i / n + 3.5f;
				g = 0;
				b = 0;
			}

			ret[i] = new Color(r, g, b, alpha);
		}
		return ret;
	}

	public Color[] heat() {

		Color[] ret = new Color[256];

		int n = (int) (3. / 8. * 256);
		for (int i = 0; i < 256; ++i) {
			double r = (1. / n) * (i + 1);
			double g = 0.;
			double b = 0.;

			if (i >= n) {
				r = 1.;
				g = (1. / n) * (i + 1 - n);
				b = 0.;
			}
			if (i >= 2 * n) {
				r = 1.;
				g = 1.;
				b = 1. / (256 - 2 * n) * (i + 1 - 2 * n);
			}

			ret[i] = new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
		}

		return ret;
	}

	public Color[] grayscale() {
		Color[] colors = new Color[256];
		for (int i = 0; i < colors.length; ++i)
			colors[i] = new Color(i, i, i);
		return colors;
	}

	public Color[] redgreen() {
		Color[] colors = new Color[256];

		double half = colors.length / 2.;
		for (int i = 0; i <= half; ++i)
			colors[i] = new Color(255, (int) ((i / half) * 255), (int) ((i / half) * 255));
		for (int i = (int) half + 1; i < colors.length; ++i)
			colors[i] = new Color(255 - (int) (((i - half) / half) * 255), 255,
					255 - (int) (((i - half) / half) * 255));
		return colors;
	}

	public Color[] redblackgreen() {

		Color[] colors = new Color[256];

		double half = colors.length / 2.;
		for (int i = 0; i <= half; ++i)
			colors[i] = new Color(255 - (int) (((i) / half) * 255), 0, 0);
		for (int i = (int) half + 1; i < colors.length; ++i)
			colors[i] = new Color(0, (int) (((i - half) / half) * 255), 0);
		return colors;
	}

	public Color[] rainbow() {

		Color[] colors = new Color[256];

		for (int i = 0; i < colors.length; ++i) {
			if (i <= 29)
				colors[i] = new Color((int) (129.36 - i * 4.36), 0, 255);
			else if (i <= 86)
				colors[i] = new Color(0, (int) (-133.54 + i * 4.52), 255);
			else if (i <= 141)
				colors[i] = new Color(0, 255, (int) (665.83 - i * 4.72));
			else if (i <= 199)
				colors[i] = new Color((int) (-635.26 + i * 4.47), 255, 0);
			else
				colors[i] = new Color(255, (int) (1166.81 - i * 4.57), 0);
		}
		return colors;
	}

	public Color[] wave() {

		Color[] colors = new Color[256];
		for (int i = 0; i < colors.length; ++i) {
			colors[i] = new Color((int) ((Math.sin(((double) i / 40 - 3.2)) + 1) * 128),
					(int) ((1 - Math.sin((i / 2.55 - 3.1))) * 70 + 30),
					(int) ((1 - Math.sin(((double) i / 40 - 3.1))) * 128));
		}
		return colors;
	}

	public BufferedImage createImage(Color[] colors, int width, int height) {

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();

		double step = height / (double) colors.length;
		for (int c = 0; c < height; ++c) {
			g.setColor(colors[(int) Math.floor(c / step)]);
			g.drawLine(0, height - (c + 1), width, height - (c + 1));
		}

		return img;
	}

	/**
	 * Default different/random selected by Sasha Trubetskoy (see
	 * https://sashat.me/2017/01/11/list-of-20-simple-distinct-colors)
	 */
	static Color[] random20 = new Color[] { new Color(230, 25, 75), new Color(60, 180, 75), new Color(255, 225, 25),
			new Color(0, 130, 200), new Color(245, 130, 48), new Color(145, 30, 180), new Color(70, 240, 240),
			new Color(240, 50, 230), new Color(210, 245, 60), new Color(250, 190, 190), new Color(0, 128, 128),
			new Color(230, 190, 255), new Color(170, 110, 40), new Color(255, 250, 200), new Color(128, 0, 0),
			new Color(170, 255, 195), new Color(128, 128, 0), new Color(255, 215, 180), new Color(0, 0, 128),
			new Color(128, 128, 128), new Color(255, 255, 255), new Color(0, 0, 0) };

	// http://dmcritchie.mvps.org/excel/colors.htm
	static Color[] excel = new Color[] { new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255),
			new Color(255, 255, 0), new Color(255, 0, 255), new Color(0, 255, 255), new Color(128, 0, 0),
			new Color(0, 128, 0), new Color(0, 0, 128), new Color(128, 128, 0), new Color(128, 0, 128),
			new Color(0, 128, 128), new Color(192, 192, 192), new Color(128, 128, 128), new Color(153, 153, 255),
			new Color(153, 51, 102), new Color(255, 255, 204), new Color(204, 255, 255), new Color(102, 0, 102),
			new Color(255, 128, 128), new Color(0, 102, 204), new Color(204, 204, 255), new Color(0, 0, 128),
			new Color(255, 0, 255), new Color(255, 255, 0), new Color(0, 255, 255), new Color(128, 0, 128),
			new Color(128, 0, 0), new Color(0, 128, 128), new Color(0, 0, 255), new Color(0, 204, 255),
			new Color(204, 255, 255), new Color(204, 255, 204), new Color(255, 255, 153), new Color(153, 204, 255),
			new Color(255, 153, 204), new Color(204, 153, 255), new Color(255, 204, 153), new Color(51, 102, 255),
			new Color(51, 204, 204), new Color(153, 204, 0), new Color(255, 204, 0), new Color(255, 153, 0),
			new Color(255, 102, 0), new Color(102, 102, 153), new Color(150, 150, 150), new Color(0, 51, 102),
			new Color(51, 153, 102), new Color(0, 51, 0), new Color(51, 51, 0), new Color(153, 51, 0),
			new Color(153, 51, 102), new Color(51, 51, 153), new Color(51, 51, 51) };

	public static void main(String[] args) throws IOException {
		ImageIO.write(INSTANCE.createImage(INSTANCE.wave(), 20, 256), "png", new java.io.File("legend.png"));
	}

	// elevation: check out
	// http://cartographicperspectives.org/index.php/journal/article/download/20/70
}
