package org.integratedmodelling.klab.rest;

/**
 * Sent B->F after a request for details on a dataflow element.
 * 
 * @author ferdinando.villa
 *
 */
public class DataflowDetail {

	private String dataflowId;
	private String htmlDescription;
	private boolean rateable;
	private int rating;
	private int averageRating;

	public DataflowDetail() {}
	
	public DataflowDetail(String dataflowId, String htmlDescription, boolean rateable) {
		this.dataflowId = dataflowId;
		this.htmlDescription = htmlDescription;
		this.rateable = rateable;
	}
	
	public String getDataflowId() {
		return dataflowId;
	}

	public void setDataflowId(String dataflowId) {
		this.dataflowId = dataflowId;
	}

	public String getHtmlDescription() {
		return htmlDescription;
	}

	public void setHtmlDescription(String htmlDescription) {
		this.htmlDescription = htmlDescription;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isRateable() {
		return rateable;
	}

	public void setRateable(boolean rateable) {
		this.rateable = rateable;
	}

	public int getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}

}
