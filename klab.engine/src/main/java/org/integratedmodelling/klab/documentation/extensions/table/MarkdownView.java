package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.documentation.views.ITableView;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.MarkdownUtils;

/**
 * Compile to HTML. Unused in newer releases.
 */
public class MarkdownView implements ITableView {

    Map<Integer, Container> containers = new HashMap<>();
    Map<Integer, Cell> cells = new HashMap<>();
    List<Integer> sheets = new ArrayList<>();
    boolean render;

    public MarkdownView(boolean render) {
        this.render = render;
    }

    class Container {
        public Container(String title) {
            this.title = title;
        }

        String title;
        List<Integer> children = new ArrayList<>();
    }

    // just a string for now, but we need an object-oriented API w/o COW pattern.
    class Cell {

        boolean header;
        boolean rowScope;
        Double value;
        String contents;
        Set<IKnowledgeView.Style> style;
        int span = 1;

        public Cell(boolean b, boolean rowScope) {
            this.header = b;
            this.rowScope = rowScope;
        }

        public Cell(boolean b, boolean rowScope, int span) {
            this.header = b;
            this.rowScope = rowScope;
            this.span = span;
        }

        public String getHtmlContents() {
            return contents == null ? " " : contents.toString();
        }

    }

    @Override
    public boolean isText() {
        return true;
    }

    @Override
    public String getText() {
        StringBuffer ret = new StringBuffer();
        for (int sheet : sheets) {
            ret.append(compile(containers.get(sheet)));
        }
        return ret.toString();
    }

    protected String compile(Container sheet) {
        StringBuffer ret = new StringBuffer(2048);
        for (int t : sheet.children) {
            ret.append("\n\n");
            Container table = containers.get(t);
            for (int s : table.children) {
                Container section = containers.get(s);
                for (int r : section.children) {
                    Container row = containers.get(r);
                    boolean header = false;
                    for (int c : row.children) {
                        Cell cell = cells.get(c);
                        if (cell.header) {
                            header = true;
                        }
                        ret.append("| " + cell.getHtmlContents() + " ");
                    }
                    ret.append("|\n");
                    if (header) {
                        for (int c : row.children) {
                            ret.append("|----");
                        }
                        ret.append("|\n");
                    }
                }
            }
        }

        return this.render ? MarkdownUtils.INSTANCE.format(ret.toString()) : ret.toString();
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public void write(OutputStream output) {
        try {
            Writer writer = new OutputStreamWriter(output, "UTF-8");
            for (int sheet : sheets) {
                writer.write(compile(containers.get(sheet)));
            }
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    public int sheet(String title) {
        int id = containers.size() + 1;
        containers.put(id, new Container(title));
        sheets.add(id);
        return id;
    }

    @Override
    public int table(String caption, int sheet) {
        Container sht = containers.get(sheet);
        int id = containers.size() + 1;
        containers.put(id, new Container(caption));
        sht.children.add(id);
        return id;
    }

    @Override
    public int header(int table) {
        Container tbl = containers.get(table);
        int id = containers.size() + 1;
        containers.put(id, new Container("thead>"));
        tbl.children.add(id);
        return id;
    }

    @Override
    public int body(int table) {
        Container tbl = containers.get(table);
        int id = containers.size() + 1;
        containers.put(id, new Container("tbody>"));
        tbl.children.add(id);
        return id;
    }

    @Override
    public int footer(int table) {
        Container tbl = containers.get(table);
        int id = containers.size() + 1;
        containers.put(id, new Container("tfoot>"));
        tbl.children.add(id);
        return id;
    }

    @Override
    public int newRow(int section) {
        Container sec = containers.get(section);
        int id = containers.size() + 1;
        Container row = new Container("__row__");
        containers.put(id, row);
        sec.children.add(id);
        return id;
    }

    @Override
    public int newCell(int row) {
        int id = cells.size() + 1;
        Container rw = containers.get(row);
        this.cells.put(id, new Cell(false, false));
        rw.children.add(id);
        return id;
    }

    @Override
    public int newCell(int row, int colSpan, int rowSpan) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void write(int cell, Object content, double value, Object... options) {
        Cell cll = cells.get(cell);
        if (options != null) {
            for (Object option : options) {
                if (option instanceof Set) {
                    cll.style = (Set<IKnowledgeView.Style>) option;
                }
                // TODO other parameters
            }
        }
        // TODO if there is formatting in the options, add it
        cll.contents = content == null ? "" : content.toString();
        if (!Double.isNaN(value)) {
            cll.value = value;
        }
    }

    @Override
    public int newHeaderCell(int row, boolean rowScope) {
        int id = cells.size() + 1;
        Container rw = containers.get(row);
        this.cells.put(id, new Cell(true, rowScope));
        rw.children.add(id);
        return id;
    }

    @Override
    public int newHeaderCell(int row, int colSpan, boolean rowScope) {
        int id = cells.size() + 1;
        Container rw = containers.get(row);
        this.cells.put(id, new Cell(true, rowScope, colSpan));
        rw.children.add(id);
        return id;
    }
}
