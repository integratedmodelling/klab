package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.GraphReference;
import org.integratedmodelling.klab.rest.DocumentationNode.Type;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * Build a document graph by parsing the structure to build a tree of sections according to Markdown
 * conventions and attributing nodes to the section that contains them. Done by incrementally adding
 * first-level sections with additional nodes, each with a starting offset to locate it properly in
 * the structure.
 * 
 * @author Ferd
 *
 */
public class Document {

    class Section {
        int level = 0;
        int lines = 0;
        String text;
        int offset = 0;
        String title;
        List<Section> children = new ArrayList<>();
    }

    Map<SectionRole, List<Section>> chapters = new HashMap<>();

    /**
     * Add the string definition of a section to a first-level section, and all objects that pertain
     * to it, with offsets relative to the start of the section
     * 
     * @param section
     * @param locatedElements
     */
    public void add(SectionRole mainSection, String section, List<DocumentationNode> childElements) {

        /*
         * build a tree of subsections and remember the highest section level in it, which is 1 or
         * the number of # signs in the highest-level section mentioned minus 1. mainSection is
         * level 0.
         */
        List<Section> chapter = chapters.get(mainSection);
        if (chapter == null) {
            chapter = new ArrayList<>();
            chapters.put(mainSection, chapter);
        }
        chapter.add(readSection(section.split("\\r?\\n"), 0, 0));
    }

    Section readSection(String[] lines, int level, int offset) {
        Section section = new Section();
        section.text = "";
        section.level = level;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith("#") && lines[i].length() > level) {
                if (lines[i].charAt(level) == '#') {
                    // child
                    section.children.add(readSection(Arrays.copyOfRange(lines, i, lines.length), level + 1, offset));
                    section.title = lines[i];
                } else {
                    return section;
                }
            } else {
                section.text += lines[i];
                offset += lines[i].length();
                section.lines++;
            }
        }
        section.offset = offset;
        return section;
    }

    public GraphReference<DocumentationNode> getGraph() {
        GraphReference<DocumentationNode> ret = new GraphReference<>();
        for (SectionRole role : SectionRole.values()) {
            List<Section> chapter = chapters.get(role);
            if (chapter != null) {
                DocumentationNode main = new DocumentationNode();
                main.setTitle(StringUtil.capitalize(role.name()));
                main.setId(NameGenerator.shortUUID());
                main.setType(Type.Section);
                for (Section section : chapter) {
                    addSection(section, main, ret);
                }
                ret.getObjects().put(main.getId(), main);
                ret.getRootObjectIds().add(main.getId());
            }
        }
        return ret;
    }

    private DocumentationNode addSection(Section section, DocumentationNode parent, GraphReference<DocumentationNode> graph) {
        DocumentationNode ret = new DocumentationNode();
        ret.setId(NameGenerator.shortUUID());
        ret.setTitle(section.title);
        ret.setType(Type.Section);
        DocumentationNode.Section s = new DocumentationNode.Section();
        s.setText(section.text);
        s.setLevel(section.level);
        ret.setSection(s);
        graph.getObjects().put(ret.getId(), ret);
        graph.link(ret.getId(), parent.getId());
        return ret;
    }

}
