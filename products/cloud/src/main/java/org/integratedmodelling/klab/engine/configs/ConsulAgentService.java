package org.integratedmodelling.klab.engine.configs;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = false)
@Profile("consul")
public class ConsulAgentService {
	
	@JsonProperty("ID")
	private String id;
	
	@JsonProperty("Service")
	@JsonInclude(Include.NON_NULL)
	private String service;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("Tags")
	private List<Object> tags;
	
	@JsonProperty("Meta")
	private Map<String, String> meta;
	
	@JsonProperty("Port")
	private int port;
	
	@JsonProperty("Address")
	private JsonNode address;
	
	@JsonProperty("TaggedAddresses")
	private JsonNode taggedAddresses;
	
	@JsonProperty("EnableTagOverride")
	private boolean enableTagOverride;
	
	@JsonProperty("Weights")
	private Weights weights;
	
	public class Weights {
		
		@JsonProperty("Passing")
		private int passing;
		
		@JsonProperty("Warning")
		private int warning;

		public int getPassing() {
			return passing;
		}

		public void setPassing(int passing) {
			this.passing = passing;
		}

		public int getWarning() {
			return warning;
		}

		public void setWarning(int warning) {
			this.warning = warning;
		}
		
	}

	public Weights getWeights() {
		return weights;
	}

	public void setRegister() {
		this.name = this.service;
		this.service = null;
	}

	public List<Object> getTags() {
		return tags;
	}

	public void setTags(List<Object> tags) {
		this.tags = tags;
	}

	public Map<String, String> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}
	
	
}
