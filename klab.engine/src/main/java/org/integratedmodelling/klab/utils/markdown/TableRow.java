package org.integratedmodelling.klab.utils.markdown;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.utils.StringUtils;

public class TableRow<T extends Object> extends MarkdownElement {

    private List<T> columns;

    public TableRow() {
        this.columns = new ArrayList<>();
    }

    public TableRow(List<T> columns) {
        this.columns = columns;
    }

    @Override
    public String serialize() throws MarkdownSerializationException {
        StringBuilder sb = new StringBuilder();
        for (Object item : columns) {
            if (item == null) {
                throw new MarkdownSerializationException("Column is null");
            }
            if (item.toString().contains(Table.SEPARATOR)) {
                throw new MarkdownSerializationException("Column contains seperator char \"" + Table.SEPARATOR + "\"");
            }
            sb.append(Table.SEPARATOR);
            sb.append(StringUtils.surroundValueWith(item.toString(), " "));
            if (columns.indexOf(item) == columns.size() - 1) {
                sb.append(Table.SEPARATOR);
            }
        }
        return sb.toString();
    }

    public List<T> getColumns() {
        return columns;
    }

    public void setColumns(List<T> columns) {
        this.columns = columns;
        invalidateSerialized();
    }

}
