package org.integratedmodelling.klab.test.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.integratedmodelling.klab.utils.DOIReader;
import org.junit.jupiter.api.Test;

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
