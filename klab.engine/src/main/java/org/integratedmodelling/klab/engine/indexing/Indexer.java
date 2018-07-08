package org.integratedmodelling.klab.engine.indexing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public enum Indexer {

	INSTANCE;

	private Map<String, Directory> index = new HashMap<>();
	private Map<String, Lookup> suggesters = new HashMap<>();

	private Indexer() {
		createIndexes(indexOperators());
	}

	public void createIndexes(Map<String, MatchIterator> indexed) {
		for (String key : indexed.keySet()) {
			try {
				Directory directory = new MMapDirectory(Configuration.INSTANCE.getDataPath("index/" + key).toPath());
				index.put(key, directory);
				// TODO build suggester
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}
	}

	public Map<String, MatchIterator> indexOperators() {
		Map<String, MatchIterator> ret = new HashMap<>();
		return ret;
	}

	public Map<String, MatchIterator> indexWorldview() {
		
		Map<String, MatchIterator> ret = new HashMap<>();
		for (IProject project : Resources.INSTANCE.getWorldview().getProjects()) {
			for (INamespace namespace : project.getNamespaces()) {
				for (IKimObject object : namespace.getAllObjects()) {
					// index it
				}
			}
		}
		return ret;
	}

	public Map<String, MatchIterator> indexWorkspace() {
		Map<String, MatchIterator> ret = new HashMap<>();
		for (IProject project : Resources.INSTANCE.getLocalWorkspace().getProjects()) {
			for (INamespace namespace : project.getNamespaces()) {
				for (IKimObject object : namespace.getAllObjects()) {
					// index it
				}
			}
		}
		return ret;
	}

	public Map<String, MatchIterator> indexNetwork() {
		Map<String, MatchIterator> ret = new HashMap<>();
		return ret;
	}

	public Match index(IKimStatement object) {
		return null;
	}

	public Iterator<Match> query(String currentTerm, SearchContext context) {
		// TODO Auto-generated method stub
		return null;
	}
}
