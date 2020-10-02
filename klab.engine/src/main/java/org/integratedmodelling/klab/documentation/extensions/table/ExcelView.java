package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public class ExcelView extends TableView {

	;

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
		XSSFWorkbook ret = new XSSFWorkbook();
		int n = 0;
		for (int s : this.sheets) {
			compile(ret, containers.get(s), 0, n++);
		}
		try {
			ret.write(output);
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	protected int compile(XSSFWorkbook workbook, Container sheet, int startRow, int sheetNo) {

		XSSFSheet sht = workbook
				.createSheet(sheet.title == null || sheet.title.isEmpty() ? ("Sheet " + sheetNo) : sheet.title);

		for (int t : sheet.children) {

			Container table = containers.get(t);
			if (table.title != null) {
				XSSFRow row = sht.createRow(startRow++);
				row.createCell(0).setCellValue(table.title);
				// skip a row after the title
				startRow++;
			}
			for (int s : table.children) {
				Container section = containers.get(s);
				// TODO use specific style for headers and footers
				for (int r : section.children) {
					Container row = containers.get(r);
					XSSFRow rw = sht.createRow(startRow++);
					int cel = 0;
					for (int c : row.children) {
						Cell cell = cells.get(c);
						// TODO types, style, everything
						rw.createCell(cel++).setCellValue(cell.contents);
					}
				}
				// skip a row between tables
				startRow++;
			}
		}
		return startRow;
	}

}
