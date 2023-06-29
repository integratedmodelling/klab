package org.integratedmodelling.klab.ogc;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.integratedmodelling.klab.DOIReader;
import org.junit.Test;

public class DOIReaderTest {


    @Test
    public void getAuthorInfoCrossref() {
        String exampleDOI = "10.1093/mnras/stac3799";
        String authorName = "Expósito-Márquez,J";

        Set<String> authors = DOIReader.readAuthors(exampleDOI);

        assertTrue(!authors.isEmpty());
        assertTrue(authors.contains(authorName));
    }

    @Test
    public void getAuthorInfoDatacite() {
        String exampleDOI = "10.14454/fxws-0523";
        String authorName = "DataCite Metadata Working Group";

        Set<String> authors = DOIReader.readAuthors(exampleDOI);

        assertTrue(!authors.isEmpty());
        assertTrue(authors.contains(authorName));
    }

}
