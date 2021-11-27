package org.integratedmodelling.tables.adapter.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.URLUtils;
import org.integratedmodelling.tables.TableInterpreter;
import org.integratedmodelling.tables.adapter.TableAdapter;
import org.integratedmodelling.tables.adapter.TableValidator;

public class XLSInterpreter extends TableInterpreter {

    @Override
    public Type getType(IResource resource, IGeometry geometry) {

        return null;
    }

    @Override
    public void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void buildResource(IParameters<String> userData, org.integratedmodelling.klab.api.data.IResource.Builder ret,
            IMonitor monitor) {

        // TODO Auto-generated method stub
        URL url;
        try {
            url = new URL(userData.get(TableValidator.FILE_URL, String.class));
        } catch (MalformedURLException e1) {
            throw new KlabIOException(e1);
        }

        File file = URLUtils.getFileForURL(url);

        if ("csv".equals(MiscUtilities.getFileExtension(file))) {
            ingestCSV(file, ret, userData, monitor);
        } else {
            ingestXSL(file, ret, userData, monitor);
        }

    }

    private void ingestXSL(File file, IResource.Builder builder, IParameters<String> userData, IMonitor monitor) {
        // TODO Auto-generated method stub

        Boolean hasHeaders = userData.contains("hasHeaders") ? "true".equals(userData.get("hasHeaders")) : null;
        int sheet = userData.contains("sheet") ? Integer.parseInt(userData.get("sheet").toString()) : 0;

        try (InputStream excelFile = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(excelFile)) {

            Sheet datatypeSheet = workbook.getSheetAt(sheet);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while(iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while(cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    // getCellTypeEnum shown as deprecated for version 3.15
                    // getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellType() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }

                }
                System.out.println();

            }
        } catch (Throwable e) {
            builder.addError(e);
        }
    }

    private void ingestCSV(File file, IResource.Builder builder, IParameters<String> userData, IMonitor monitor) {

        // null means we don't know. Will be known only at revalidation after import.
        Boolean hasHeaders = userData.contains("hasHeaders") ? "true".equals(userData.get("hasHeaders")) : null;

        /*
         * for each column, make an attribute; type may be number or string (check lexically), if
         * the second line matches a number and the first doesn't, it's a header.
         */

        GeometryBuilder gbuilder = Geometry.builder();

        // TODO get the charset and format from resource parameters, these below are
        // defaults
        CSVRecord first = null;
        int row = 0;
        int columns = -1;
        int rtot = 0;

        // build columns as we go, skipping any empty ones.
        Map<String, Type> columnTypes = new LinkedHashMap<>();
        boolean checkHeaders = false;

        try (CSVParser parser = CSVTable.getParser(file, userData)) {

            for (CSVRecord record : parser) {

                rtot++;

                if (isEmpty(record)) {
                    continue;
                }

                if (columns < 0) {
                    columns = record.size();
                }

                if (first == null) {
                    first = record;
                    if ((checkHeaders = !isNumeric(record))) {
                        continue;
                    }
                }

                int n = 0;
                for (String value : record) {
                    String header = checkHeaders ? StringUtils.replaceAny("(){}[]|\"'?><:;*^%$#@!/\\", first.get(n).replaceAll("\\s+", "_").toLowerCase(), "_") : ("c" + n);
                    setType(header, value, columnTypes);
                    n++;
                }

                row++;
            }

        } catch (Throwable e) {
            builder.addError(e);
        }

        int n = 0, usable = 0;
        for (String key : columnTypes.keySet()) {
            Type type = columnTypes.get(key);
            if (type == null) {
                type = Type.VOID;
            }
            builder.withAttribute(key, type, false, true);
            builder.withParameter("column." + key + ".index", n);
            builder.withParameter("column." + key + ".mapping", "");
            builder.withParameter("column." + key + ".size", "-1");
            builder.withParameter("column." + key + ".searchable", "false");
            usable++;
            n++;
        }

        builder.withParameter("rows.total", rtot);
        builder.withParameter("rows.data", row);
        builder.withParameter("columns.total", columnTypes.size());
        builder.withParameter("columns.data", usable);
        builder.withParameter("headers.columns", checkHeaders);
        builder.withParameter("headers.rows", false);

        builder.withParameter("format.encoding", "");
        builder.withParameter("format.source", "DEFAULT");
        builder.withParameter("format.nodata", "");
        builder.withParameter("format.lineseparator", "");
        builder.withParameter("format.delimiter", ",");
        builder.withParameter("format.trimspaces", "false");
        builder.withParameter("format.quote", "\"");

        builder.withParameter("time.encoding", "");
        builder.withParameter("space.encoding", "");

        builder.withParameter("resource.type", "csv");
        builder.withParameter("resource.file", MiscUtilities.getFileName(file));
        
        if (checkHeaders) {
            /*
             * add column headers to the list of categorizables
             */
            builder.withCategorizable(TableAdapter.COLUMN_HEADER_CATEGORIZABLE);
        }

        builder.withGeometry(gbuilder.build());

    }

    private void setType(String header, String example, Map<String, Type> columnTypes) {

        if (columnTypes.get(header) == null) {
            if (example != null && !example.trim().isEmpty()) {
                Type type = getType(example);
                columnTypes.put(header, type);
            } else {
                columnTypes.put(header, null);
            }
        }
    }

    private Type getType(String s) {
        if (NumberUtils.encodesDouble(s)) {
            return Type.NUMBER;
        } else if (s.toLowerCase().equals("true") || s.toLowerCase().equals("false")) {
            return Type.BOOLEAN;
        }
        return Type.TEXT;
    }

    // true if ALL the row elements are numbers
    private boolean isNumeric(CSVRecord record) {
        for (String val : record) {
            if (!val.trim().isEmpty() && !NumberUtils.encodesDouble(val)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty(CSVRecord record) {
        for (String val : record) {
            if (!val.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canHandle(URL resource, IParameters<String> parameters) {
        if (resource == null) {
            // TODO check URLs
            return false;
        }
        return XLSAdapter.fileExtensions.contains(MiscUtilities.getFileExtension(resource.toString()));
    }

    @Override
    public ITable<?> getTable(IResource resource, IGeometry geometry, IMonitor monitor) {
        if ("csv".equals(resource.getParameters().get("resource.type"))) {
            return new CSVTable(resource, monitor);
        }
        return null;
    }

}
