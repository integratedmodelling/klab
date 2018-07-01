package org.integratedmodelling.klab.engine.indexing;

import org.apache.lucene.store.RAMDirectory;

public enum Indexer {

	INSTANCE;
	
	// TODO switch to MMapDirectory if this gets too big
	RAMDirectory index = new RAMDirectory();
}
