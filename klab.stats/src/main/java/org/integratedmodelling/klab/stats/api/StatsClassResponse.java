package org.integratedmodelling.klab.stats.api;

import java.util.HashMap;

@Deprecated
public class StatsClassResponse {
	
	HashMap<String, Integer> classCounts;
	
	public StatsClassResponse() {}
	
	public HashMap<String, Integer> getClassCounts() {
		return classCounts;
	}

	public void setClassCounts(HashMap<String, Integer> classCounts) {
		this.classCounts = classCounts;
	}
	
	
}
