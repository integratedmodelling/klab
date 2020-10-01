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
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Style;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.Escape;

/**
 */
public class TableView implements ITableView {

	Map<Integer, Container> containers = new HashMap<>();
	Map<Integer, Cell> cells = new HashMap<>();
	List<Integer> sheets = new ArrayList<>();

	class Container {
		public Container(String title) {
			this.title = title;
		}

		String title;
		List<Integer> children = new ArrayList<>();
	}

	// just a string for now, but we need an object-oriented API w/o COW pattern.
	class Cell {
		public Cell(boolean b, boolean rowScope) {
			this.header = b;
			this.rowScope = rowScope;
		}

		boolean header;
		boolean rowScope;
		String contents;
		Set<Style> style;

		public String getHtmlContents() {
			if (contents == null) {
				return (header ? "<th" + (rowScope ? " scope=\"row\"" : "") + "></th>" : "<td></td>");
			}
			return (header ? ("<th" + (rowScope ? " scope=\"ro\"" : "")) : "<td") + getStyle(style) + ">"
					+ Escape.forHTML(contents.toString()) + (header ? "</th>" : "</td>");
		}

		private String getStyle(Set<Style> style) {

			if (style != null && !style.isEmpty()) {
				String ret = "\"";
				for (Style s : style) {
					switch (s) {
					case BOLD:
						ret += (ret.length() == 1 ? "" : " ") + "kv-bold";
						break;
					case CENTER:
						ret += (ret.length() == 1 ? "" : " ") + "kv-align-center";
						break;
					case ITALIC:
						ret += (ret.length() == 1 ? "" : " ") + "kv-italic";
						break;
					case LEFT:
						ret += (ret.length() == 1 ? "" : " ") + "kv-align-left";
						break;
					case RIGHT:
						ret += (ret.length() == 1 ? "" : " ") + "kv-align-right";
						break;
					case BG_HIGHLIGHT:
						ret += (ret.length() == 1 ? "" : " ") + "kv-bg-highlight";
						break;
					case FG_HIGHLIGHT:
						ret += (ret.length() == 1 ? "" : " ") + "kv-fg-highlight";
						break;
					default:
						break;

					}
				}

				return " class=" + ret + "\"";
			}
			return "";
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
		ret.append("<div>\n");
		for (int t : sheet.children) {
			ret.append("<table>\n");
			Container table = containers.get(t);
			if (table.title != null) {
				ret.append("  <caption>");
				ret.append(Escape.forHTML(table.title));
				ret.append("  </caption>\n");
			}
			for (int s : table.children) {
				Container section = containers.get(s);
				ret.append("  <" + section.title + "\n");
				for (int r : section.children) {
					ret.append("    <tr>\n");
					Container row = containers.get(r);
					for (int c : row.children) {
						Cell cell = cells.get(c);
						ret.append("      " + cell.getHtmlContents() + "\n");
					}
					ret.append("    </tr>\n");
				}
				ret.append("  </" + section.title + "\n");
			}
			ret.append("</table>\n");
		}
		ret.append("</div>");
		return ret.toString();
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
	public void write(int cell, Object content, Object... options) {
		Cell cll = cells.get(cell);
		for (Object option : options) {
			if (option instanceof Set) {
				cll.style = (Set<Style>) option;
			}
			// TODO other parameters
		}
		// TODO if there is formatting in the options, add it
		cll.contents = content.toString();

	}

	@Override
	public int newHeaderCell(int row, boolean rowScope) {
		int id = cells.size() + 1;
		Container rw = containers.get(row);
		this.cells.put(id, new Cell(true, rowScope));
		rw.children.add(id);
		return id;
	}

}
