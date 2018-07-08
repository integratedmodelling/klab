package deleteme;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

public class SuggestProducts {

    // Get suggestions given a prefix and a region.
    private static void lookup(AnalyzingInfixSuggester suggester, String name, String region) {
        try {
            List<Lookup.LookupResult> results;
            HashSet<BytesRef> contexts = new HashSet<BytesRef>();
            contexts.add(new BytesRef(region.getBytes("UTF8")));
            // Do the actual lookup.  We ask for the top 2 results.
            results = suggester.lookup(name, contexts, 2, true, false);
            System.out.println("-- \"" + name + "\" (" + region + "):");
            for (Lookup.LookupResult result : results) {
                System.out.println(result.key);
                Product p = getProduct(result);
                if (p != null) {
                    System.out.println("  image: " + p.image);
                    System.out.println("  # sold: " + p.numberSold);
                }
            }
        } catch (IOException e) {
            System.err.println("Error");
        }
    }

    // Deserialize a Product from a LookupResult payload.
    private static Product getProduct(Lookup.LookupResult result) {
        try {
            BytesRef payload = result.payload;
            if (payload != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(payload.bytes);
                ObjectInputStream in = new ObjectInputStream(bis);
                Product p = (Product) in.readObject();
                return p;
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Error("Could not decode payload :(");
        }
    }

    public static void main(String[] args) {
        try {
            RAMDirectory index_dir = new RAMDirectory();
            StandardAnalyzer analyzer = new StandardAnalyzer();
            AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(index_dir, analyzer);

            // Create our list of products.
            ArrayList<Product> products = new ArrayList<Product>();
            products.add(new Product("Electric Guitar", "http://images.example/electric-guitar.jpg",
                    new String[] { "US", "CA" }, 100));
            products.add(
                    new Product("Electric Train", "http://images.example/train.jpg", new String[] { "US", "CA" }, 100));
            products.add(new Product("Acoustic Guitar", "http://images.example/acoustic-guitar.jpg",
                    new String[] { "US", "ZA" }, 80));
            products.add(
                    new Product("Guarana Soda", "http://images.example/soda.jpg", new String[] { "ZA", "IE" }, 130));

            // Index the products with the suggester.
            suggester.build(new ProductIterator(products.iterator()));

            // Do some example lookups.
            lookup(suggester, "Gu", "US");
            lookup(suggester, "Gu", "ZA");
            lookup(suggester, "Gui", "CA");
            lookup(suggester, "Electric guit", "US");
        } catch (IOException e) {
            System.err.println("Error!");
        }
    }
}
