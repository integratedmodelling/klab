package org.integratedmodelling.klab.documentation;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * Simple AsciiDoc document builder with delayed action placeholders. Very hacky.
 * 
 * @author Ferd
 *
 */
public class AsciiDocBuilder {

    private static final String SECTION_MARKER = "_#_#_#_#_#_#_#_#_#_#";
    private static final String ACTION_MARKER = "_A_A_A_A_A_A_A_A_A_A";

    Section rootSection;
    Set<Option> options = EnumSet.noneOf(Option.class);

    public enum Option {
        NUMBER_SECTIONS, COLLAPSIBLE
    }

    public static class Table {

        public static class Span {
            int[] span;
            public Span(int... span) {
                this.span = span;
            }
        }

        class Row {
            int[] spans; // null unless non-standard, otherwise length == object.length
            Object[] objects; // if null, empty row with span == ncols
        }

        int ncols;
        int[] colspans;
        String[] headers;
        List<Row> rows = new ArrayList<>();
        String title;

        public Table(String... headers) {
            this.headers = headers;
            this.ncols = headers.length;
        }

        public Table(int columns) {
            this.ncols = columns;
        }

        public Table title(String title) {
            this.title = title;
            return this;
        }

        public Table spans(int... spans) {
            this.colspans = spans;
            return this;
        }

        /**
         * Add objects for the cells and optionally a Span object. Empty args == empty row with span
         * == columns
         * 
         * @param objects
         */
        public void addRow(Object... objects) {
            if (objects == null || objects.length == 0) {
                rows.add(new Row());
                return;
            }
            Row row = new Row();
            List<Object> data = new ArrayList<>();
            for (Object o : objects) {
                if (o instanceof Span) {
                    row.spans = ((Span) o).span;
                } else {
                    data.add(o);
                }
            }
            row.objects = data.toArray();
            rows.add(row);
        }

        public String toString() {

            StringBuffer ret = new StringBuffer();

            if (title != null) {
                ret.append("." + title + "\n");
            }
            
            ret.append("[cols=\"");
            for (int i = 0; i < ncols; i++) {
                ret.append((i == 0 ? "" : ",") + (colspans == null ? "1" : ("" + colspans[i])));
            }
            ret.append("\"]\n|===\n");
            if (headers != null) {
                for (Object header : headers) {
                    ret.append("|" + asString(header));
                }
                ret.append("\n\n");
            }

            for (Row row : rows) {
                int i = 0;
                for (Object o : row.objects) {
                    if (row.spans != null && row.spans[i] > 1) {
                        ret.append(row.spans[i] + "+");
                    }
                    String ob = asString(o);
                    String mod = "";
                    if (ob.startsWith(">") || ob.startsWith("<") && !(ob.startsWith(">>") || ob.startsWith("<<"))) {
                        mod = ob.substring(0, 1);
                        ob = ob.substring(1);
                    }
                    ret.append(mod + "|" + ob + "\n");
                    i++;
                }
            }

            ret.append("|===\n\n");

            return ret.toString();
        }

        private String asString(Object object) {
            // TODO number formatting and the like
            return object.toString();
        }

    }

    public class Section {

        String id = NameGenerator.newName();
        Section parent;
        String title;
        int level = 1;
        List<String> paragraphs = new ArrayList<>();
        List<Section> children = new ArrayList<>();
        List<Supplier<String>> actions = new ArrayList<>();
        public String subtitle;

        public Section(String title) {
            this.title = title;
        }

        public void paragraph(String string) {
            paragraphs.add(string);
        }

        public Section getChild(String title) {
            Section section = new Section(title);
            section.level = this.level + 1;
            section.parent = this;
            this.children.add(section);
            paragraphs.add(SECTION_MARKER + section.id);
            return section;
        }

        public void action(Supplier<String> action) {
            actions.add(action);
            paragraphs.add(ACTION_MARKER + (actions.size() - 1));
        }
    }

    public AsciiDocBuilder(String title, String subtitle, Option... options) {
        rootSection = new Section(title);
        rootSection.subtitle = subtitle;
        if (options != null) {
            for (Option option : options) {
                this.options.add(option);
            }
        }
    }

    public AsciiDocBuilder() {
        rootSection = new Section(null);
    }

    public void writeToFile(Path path, Charset charset) {
        try (OutputStream output = new FileOutputStream(path.toFile())) {
            PrintWriter writer = new PrintWriter(output);
            write(rootSection, writer, true);
            writer.flush();
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    private void write(Section section, PrintWriter writer, boolean isRoot) {

        if (section.title != null) {
            writer.println(StringUtil.repeat('#', section.level) + " " + section.title);
            if (isRoot) {
                for (Option option : options) {
                    switch(option) {
                    case NUMBER_SECTIONS:
                        writer.println(":sectnums:");
                        break;
                    default:
                        break;
                    }
                }
                if (section.subtitle != null) {
                    writer.println(section.subtitle);
                }
            }
            writer.println();
            for (String paragraph : section.paragraphs) {
                if (paragraph.startsWith(SECTION_MARKER)) {
                    for (Section child : section.children) {
                        if (paragraph.equals(SECTION_MARKER + child.id)) {
                            writer.println();
                            write(child, writer, false);
                            break;
                        }
                    }
                } else if (paragraph.startsWith(ACTION_MARKER)) {
                    int index = Integer.parseInt(paragraph.substring(ACTION_MARKER.length()));
                    writer.println(section.actions.get(index).get());
                } else {
                    writer.println(paragraph + "\n");
                }
            }
        }

    }

    public static String listingBlock(String sourceCode, String language, Option... options) {

        String preamble = "";
        String post = "";
        if (options != null) {
            for (Option option : options) {
                switch(option) {
                case COLLAPSIBLE:
                    preamble += "[%collapsible]\n";
                    break;
                default:
                    break;

                }
            }
            preamble += "====\n";
            post = "====\n";
        }
        return preamble + "[source" + (language == null ? "]" : ("," + language)) + "]\n----\n" + sourceCode
                + (sourceCode.endsWith("\n") ? "" : "\n") + "----\n" + post;
    }

    public Section getRootSection() {
        return rootSection;
    }

}
