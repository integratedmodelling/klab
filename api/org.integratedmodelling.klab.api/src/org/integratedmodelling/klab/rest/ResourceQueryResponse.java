package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * Brief summary of a resource search request, with a short record per resource
 * plus query statistics.
 * 
 * @author ferdinando.villa
 *
 */
public class ResourceQueryResponse {

	public static class ResourceSummary {
		
		private String urn;
		private Set<String> nodes = new HashSet<>();
		private String description;
		private IArtifact.Type type;
		private double matchScore;

		public String getUrn() {
			return urn;
		}

		public void setUrn(String urn) {
			this.urn = urn;
		}

		public Set<String> getNodes() {
			return nodes;
		}

		public void setNodes(Set<String> nodes) {
			this.nodes = nodes;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public IArtifact.Type getType() {
			return type;
		}

		public void setType(IArtifact.Type type) {
			this.type = type;
		}

		public double getMatchScore() {
			return matchScore;
		}

		public void setMatchScore(double matchScore) {
			this.matchScore = matchScore;
		}

	}

	private List<ResourceSummary> result = new ArrayList<>();
}
