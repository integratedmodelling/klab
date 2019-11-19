package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class ContextualizationEvent {

	public static class Task {
		private long utcStart;
		private long utcEnd;
		private boolean succeeded;
		private long size;
	}
	
	private String uid;
	private String eid;
	private String version;
	private String build;
	private String branch;
	private List<Task> tasks = new ArrayList<>();
	
}
