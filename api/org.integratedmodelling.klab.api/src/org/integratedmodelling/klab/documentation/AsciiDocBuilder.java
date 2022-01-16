package org.integratedmodelling.klab.documentation;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple, non-sequential AsciiDoc document builder. Just a stub for now.
 * 
 * @author Ferd
 *
 */
public class AsciiDocBuilder {

    String title = null;
    Section rootSection;
    Section currentSection;
    
    class Section {
        Section parent;
        List<String> paragraphs = new ArrayList<>();
        List<Section> children = new ArrayList<>();
    }
    
    public AsciiDocBuilder() {
        rootSection = currentSection = new Section();
    }
    
    public void documentTitle(String string) {
        this.title = string;
    }

    public void paragraph(String string) {
        currentSection.paragraphs.add(string);
    }

    public void writeToFile(Path path, Charset forName) {
        // TODO Auto-generated method stub
        
    }

    public void sectionTitleLevel(int i, String string) {
        // TODO Auto-generated method stub
        
    }

    public void listingBlock(String sourceCode, String language) {
//        currentSection.paragraphs.add(")
    }

}
