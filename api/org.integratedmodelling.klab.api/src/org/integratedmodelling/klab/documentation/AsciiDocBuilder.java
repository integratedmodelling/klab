package org.integratedmodelling.klab.documentation;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * Simple, non-sequential AsciiDoc document builder. Just a stub for now.
 * 
 * @author Ferd
 *
 */
public class AsciiDocBuilder {

    private static final String SECTION_MARKER = "_#_#_#_#_#_#_#_#_#_#";
    
    Section rootSection;
    
    class Section {
        
        String id = NameGenerator.newName();
        Section parent;
        String title;
        List<String> paragraphs = new ArrayList<>();
        List<Section> children = new ArrayList<>();

        public Section(String title) {
            this.title = title;
        }

        public void paragraph(String string) {
            paragraphs.add(string);
        }
        
        public Section getChild(String title) {
            Section section = new Section(title);
            paragraphs.add(SECTION_MARKER + section.id);
            return section;
        }

    }
    
    public AsciiDocBuilder(String title) {
        rootSection = new Section(title);
    }
    
    public AsciiDocBuilder() {
        rootSection = new Section(null);
    }
    
    public void writeToFile(Path path, Charset forName) {
        // TODO Auto-generated method stub
    }

    public void listingBlock(String sourceCode, String language) {
//        currentSection.paragraphs.add(")
    }

}
