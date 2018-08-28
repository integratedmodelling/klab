package org.integratedmodelling.klab.rest;

/**
 * One of these may accompany any individual observation to specify a
 * non-default style. Applies to lines, polygons, and
 * 
 * @author ferdinando.villa
 *
 */
public class Style {

	private String lineColor;
	private String lineStyle;
	private String dashArray;
	private String fillColor;
	private String svgShape;
	private int maxPixelSize;

	public String getLineColor() {
		return lineColor;
	}

	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	public String getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(String lineStyle) {
		this.lineStyle = lineStyle;
	}

	public String getDashArray() {
		return dashArray;
	}

	public void setDashArray(String dashArray) {
		this.dashArray = dashArray;
	}

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public String getSvgShape() {
		return svgShape;
	}

	public void setSvgShape(String svgShape) {
		this.svgShape = svgShape;
	}

	public int getMaxPixelSize() {
		return maxPixelSize;
	}

	public void setMaxPixelSize(int maxPixelSize) {
		this.maxPixelSize = maxPixelSize;
	}

}
