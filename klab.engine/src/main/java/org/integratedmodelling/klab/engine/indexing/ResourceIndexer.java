package org.integratedmodelling.klab.engine.indexing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public enum ResourceIndexer {

	INSTANCE;

	private Directory index;
	private IndexWriter writer;
	private StandardAnalyzer analyzer;
	private ReferenceManager<IndexSearcher> searcherManager;
	private ControlledRealTimeReopenThread<IndexSearcher> nrtReopenThread;
	
	
	
	public static final int MAX_RESULT_COUNT = 9;

	private ResourceIndexer() {
		try {
			this.index = new RAMDirectory(); // new
												// MMapDirectory(Configuration.INSTANCE.getDataPath("index").toPath());
			this.analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
			this.writer = new IndexWriter(index, config);

			this.searcherManager = new SearcherManager(writer, true, true, null);
			/**
			 * Thread supporting near-realtime index background refresh
			 */
			nrtReopenThread = new ControlledRealTimeReopenThread<IndexSearcher>(writer, searcherManager, 1.0, 0.1);
			nrtReopenThread.setName("NRT Reopen Thread");
			nrtReopenThread.setPriority(Math.min(Thread.currentThread().getPriority() + 2, Thread.MAX_PRIORITY));
			nrtReopenThread.setDaemon(true);
			nrtReopenThread.start();

		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	public void index(IResource resource) {

		try {

			Document document = new Document();

			Urn urn = new Urn(resource.getUrn());

			document.add(new StringField("id", urn.getUrn(), Store.YES));
			document.add(new StringField("namespace", urn.getNamespace(), Store.YES));
			document.add(new StringField("catalog", urn.getCatalog(), Store.YES));
			document.add(new TextField("name", urn.getUrn(), Store.YES));
			for (String key : resource.getMetadata().keySet()) {
				if (IMetadata.DC_DESCRIPTION.equals(key)) {
					document.add(new TextField("description", resource.getMetadata().get(key).toString(), Store.YES));
				} else {
					document.add(new TextField(key, resource.getMetadata().get(key).toString(), Store.YES));
				}
			}

			this.writer.addDocument(document);
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	public void commitChanges() {
		try {
			this.writer.commit();
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	public boolean ensureClosed() {
		nrtReopenThread.close();
		if (this.writer.isOpen()) {
			try {
				this.writer.close();
			} catch (IOException e) {
				Logging.INSTANCE.error(e);
				return false;
			}
		}
		return true;
	}

	public List<Match> query(String query) {
		return query(query, MAX_RESULT_COUNT);
	}

	private Query buildQuery(String currentTerm) {
		QueryParser parser = new QueryParser("name", analyzer);
		// parser.setAllowLeadingWildcard(true);
		try {
			// hai voglia
			return parser.parse(currentTerm + "*");
		} catch (ParseException e) {
			throw new KlabValidationException(e);
		}
	}

	public List<Match> query(String query, int maxResults) {

		List<Match> ret = new ArrayList<>();

		Set<String> ids = new HashSet<>();

		IndexSearcher searcher;
		try {
			searcher = searcherManager.acquire();
		} catch (IOException e) {
			// adorable exception management
			throw new KlabIOException(e);
		}

		try {
			TopDocs docs = searcher.search(buildQuery(query), maxResults);
			ScoreDoc[] hits = docs.scoreDocs;

			for (ScoreDoc hit : hits) {

				Document document = searcher.doc(hit.doc);

				if (!ids.contains(document.get("id"))) {

					SearchMatch match = new SearchMatch();
					match.setId(document.get("id"));
					match.setName(document.get("name"));
					match.setDescription(document.get("description"));
					match.setScore(hit.score);
					match.setMatchType(Match.Type.RESOURCE);

					ret.add(match);
					ids.add(document.get("id"));
				}
			}

		} catch (Exception e) {
			throw new KlabIOException(e);
		} finally {
			try {
				searcherManager.release(searcher);
			} catch (IOException e) {
				// unbelievable, they want it in finally and make it throw a checked
				// exception
				throw new KlabIOException(e);
			}
		}

		return ret;
	}
}
