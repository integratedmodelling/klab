package org.integratedmodelling.klab.engine.indexing;

import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public enum Indexer {

	INSTANCE;
	
	Directory index = null;

	private Indexer() {
		try {
			index = MMapDirectory.open(Configuration.INSTANCE.getDataPath("index").toPath());
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}
	
	public void index(IKimStatement object) {
		
	}
	
	public Iterator<Match> query(String currentTerm, SearchContext context) {
		// TODO Auto-generated method stub
		return null;
	}
}
