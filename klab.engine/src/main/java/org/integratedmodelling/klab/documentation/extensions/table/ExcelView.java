package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public class ExcelView extends TableView {

    // TODO how to insert SVG
    // /* Create the drawing container */
    // XSSFDrawing drawing = my_sheet.createDrawingPatriarch();
    // /* Create an anchor point */
    // ClientAnchor my_anchor = new XSSFClientAnchor();
    // /* Define top left corner, and we can resize picture suitable from there */
    // my_anchor.setCol1(4);
    // my_anchor.setRow1(5);
    // /* Invoke createPicture and pass the anchor point and ID */
    // XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
    // /* Call resize method, which resizes the image */
    // my_picture.resize();

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

        XSSFSheet sht = workbook.createSheet(sheet.title == null || sheet.title.isEmpty() ? ("Sheet " + sheetNo) : sheet.title);

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
                        XSSFCell xcell = rw.createCell(cel++, cell.header ? CellType.STRING : CellType.NUMERIC);
                        if (cell.header || cell.value == null) {
                            xcell.setCellValue(cell.contents == null ? "" : cell.contents);
                        } else if (cell.value != null){
                            xcell.setCellValue(cell.value);
                        }
                    }
                }
                // skip a row between tables
                startRow++;
            }
        }
        return startRow;
    }

}
