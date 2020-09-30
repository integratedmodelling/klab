package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.OutputStream;

import org.integratedmodelling.klab.api.documentation.views.ITableView;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorksheetDocument;

public class ExcelView implements ITableView {

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
		
	}

	@Override
	public int header(int table) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int body(int table) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int footer(int table) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int newRow(int section) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void write(int cell, Object content, Object... options) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int sheet(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int table(String caption, int sheet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int newCell(int row) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int newCell(int row, int colSpan, int rowSpan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int newHeaderCell(int row, boolean rowScope) {
		// TODO Auto-generated method stub
		return 0;
	}


}
