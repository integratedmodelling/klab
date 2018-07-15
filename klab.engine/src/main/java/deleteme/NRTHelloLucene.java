package deleteme;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;

public class NRTHelloLucene {

    public static void main(String[] args) throws IOException, InterruptedException {

        //=========================================================
        // Setup as per usual
        //=========================================================
        StandardAnalyzer analyzer = new StandardAnalyzer();
        RAMDirectory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        final IndexWriter indexWriter = new IndexWriter(index, config);

        //=========================================================
        // This bit is specific to NRT
        //=========================================================
        final ReferenceManager<IndexSearcher> searcherManager = new SearcherManager(indexWriter, true, true, null);

        //=========================================================
        // This thread handles the actual reader reopening.
        //=========================================================
        ControlledRealTimeReopenThread<IndexSearcher> nrtReopenThread = new ControlledRealTimeReopenThread<IndexSearcher>(
                indexWriter, searcherManager, 1.0, 0.1);
        nrtReopenThread.setName("NRT Reopen Thread");
        nrtReopenThread.setPriority(Math.min(Thread.currentThread().getPriority() + 2, Thread.MAX_PRIORITY));
        nrtReopenThread.setDaemon(true);
        nrtReopenThread.start();

        //=========================================================
        // Start a new writer thread
        //=========================================================
        Thread writerThread = new Thread() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100000; ++i) {
                        Document doc = new Document();
                        doc.add(new LongPoint("time", System.currentTimeMillis()));
                        doc.add(new StringField("counter", "" + i, Field.Store.YES));
                        indexWriter.addDocument(doc);
                        searcherManager.maybeRefresh();
                        Thread.sleep(100);
                    }
                    indexWriter.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        writerThread.start();

        // now wait a couple seconds
        Thread.sleep(5000);

        //=========================================================
        // then do a search. we should have some results
        // even though no commit has been called
        //=========================================================
        IndexSearcher searcher = searcherManager.acquire();
        Query q = new TermQuery(new Term("counter", "1"));
        TopDocs docs = searcher.search(q, 10);
        System.out.println("Found " + docs.totalHits + " docs for counter=1");

        searcherManager.release(searcher);
    }
}
