package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent by the back end as a response to a {@link SearchRequest}. The response
 * is asynchronous and may come after requests with higher
 * {@link #getRequestId() IDs} have been sent.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public class SearchResponse {

	private String contextId;
	private long requestId;
	private List<SearchMatch> matches = new ArrayList<>();
	private boolean end;
	private boolean error;
	private boolean last;
	private long elapsedTimeMs = System.currentTimeMillis();
	private String errorMessage;
	private int parenthesisDepth;
	
	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public List<SearchMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<SearchMatch> matches) {
		this.matches = matches;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public long getElapsedTimeMs() {
		return elapsedTimeMs;
	}

	public void setElapsedTimeMs(long elapsedTimeMs) {
		this.elapsedTimeMs = elapsedTimeMs;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	/**
	 * Call at end of search to set the elapsed time.
	 * 
	 * @return
	 */
	public SearchResponse signalEndTime() {
		long current = System.currentTimeMillis();
		this.elapsedTimeMs = current - this.elapsedTimeMs;
		return this;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getParenthesisDepth() {
		return parenthesisDepth;
	}

	public void setParenthesisDepth(int parenthesisDepth) {
		this.parenthesisDepth = parenthesisDepth;
	}

}
