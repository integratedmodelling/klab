package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.OutputStream;

import org.integratedmodelling.klab.utils.Escape;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorksheetDocument;

public class ExcelView extends TableView {

	WorksheetDocument document;

	@Override
	public boolean isText() {
		return false;
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public void write(OutputStream output) {
		// TODO Auto-generated method stub
		super.write(output);
	}

	@Override
	public void write(int cell, Object content, Object... options) {
		// TODO Auto-generated method stub
		super.write(cell, content, options);
	}

	protected String compile(Container sheet) {
		// TODO redo
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


}
