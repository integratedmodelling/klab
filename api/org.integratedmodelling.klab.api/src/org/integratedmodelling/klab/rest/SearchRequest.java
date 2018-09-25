package org.integratedmodelling.klab.rest;

import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.services.IIndexingService;

/**
 * Sent by the front end to execute a query with a partial match.
 * <p>
 * Each request should come with a requestId that is unique within a session and
 * increases at each new request within the same search. First time sent, leave
 * contextId blank. The response with the passed requestId will contain the
 * contextId which should be used for each later request. If establishing a
 * context is wished before any search proceeds (for example when search
 * response can be slow), establish the context passing an empty query and block
 * until the response arrives.
 * <p>
 * Semantic types and match types should only be set to constrain the query at
 * the first request. After that it is better to leave it to the indexing
 * service, as any type not fitting the current query context will cause empty
 * responses.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public class SearchRequest {

	private String queryString;
	private String contextId;
	private long requestId;
	private boolean cancelSearch;
	private boolean defaultResults;
	private int maxResults = 9;
	private Set<IKimConcept.Type> semanticTypes = EnumSet.noneOf(IKimConcept.Type.class);
	private Set<IIndexingService.Match.Type> matchTypes = EnumSet.noneOf(IIndexingService.Match.Type.class);

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

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

	public boolean isCancelSearch() {
		return cancelSearch;
	}

	public void setCancelSearch(boolean cancelSearch) {
		this.cancelSearch = cancelSearch;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public Set<IKimConcept.Type> getSemanticTypes() {
		return semanticTypes;
	}

	public void setSemanticTypes(Set<IKimConcept.Type> semanticTypes) {
		this.semanticTypes = semanticTypes;
	}

	public Set<IIndexingService.Match.Type> getMatchTypes() {
		return matchTypes;
	}

	public void setMatchTypes(Set<IIndexingService.Match.Type> matchTypes) {
		this.matchTypes = matchTypes;
	}

	@Override
	public String toString() {
		return "SearchRequest [queryString=" + queryString + ", contextId=" + contextId + ", requestId=" + requestId
				+ ", cancelSearch=" + cancelSearch + ", maxResults=" + maxResults + ", semanticTypes=" + semanticTypes
				+ ", matchTypes=" + matchTypes + "]";
	}

	/**
	 * A search with this body corresponds to an empty search string and retrieves the
	 * user's most likely matches of interest, based on group and search history.
	 * 
	 * @return
	 */
	public boolean isDefaultResults() {
		return defaultResults;
	}

	public void setDefaultResults(boolean defaultResults) {
		this.defaultResults = defaultResults;
	}

}
