package org.integratedmodelling.klab.rest;

/**
 * Sent by the front-end when one of the matches for the passed context ID was
 * accepted by the user. It will modify the context pointed to by the Id to
 * reflect the new search context. Passing -1 as the match index means remove
 * the last accepted match.
 * 
 * @author ferdinando.villa
 *
 */
public class SearchMatchAction {

	private String contextId;
	private String matchId;
	private int matchIndex;
	private boolean added;

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchIndex) {
		this.matchId = matchIndex;
	}

	/**
	 * If true, it's a new match, otherwise it's the previous being deleted.
	 */
	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}

	public int getMatchIndex() {
		return matchIndex;
	}

	public void setMatchIndex(int matchIndex) {
		this.matchIndex = matchIndex;
	}

}
