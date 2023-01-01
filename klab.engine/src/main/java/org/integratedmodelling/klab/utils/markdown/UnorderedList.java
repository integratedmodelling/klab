package org.integratedmodelling.klab.utils.markdown;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.utils.StringUtils;

public class UnorderedList<T extends Object> extends MarkdownElement {

    protected List<T> items;
    protected int indentationLevel = 0;

    public UnorderedList() {
        this.items = new ArrayList<>();
    }

    public UnorderedList(List<T> items) {
        this.items = items;
    }

    @Override
    public String serialize() throws MarkdownSerializationException {
        StringBuilder sb = new StringBuilder();
        for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
            T item = items.get(itemIndex);

            if (itemIndex > 0) {
                sb.append(StringUtils.fillUpLeftAligned("", "  ", indentationLevel * 2));
            } else if (indentationLevel > 0) {
                sb.append("  ");
            }

            if (item instanceof UnorderedListItem) {
                sb.append(item);
            } else if (item instanceof UnorderedList) {
                UnorderedList unorderedList = (UnorderedList) item;
                unorderedList.setIndentationLevel(indentationLevel + 1);
                sb.append(unorderedList);
            } else {
                sb.append(new UnorderedListItem(item));
            }

            if (itemIndex < items.size() - 1) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        invalidateSerialized();
    }

    public int getIndentationLevel() {
        return indentationLevel;
    }

    public void setIndentationLevel(int indentationLevel) {
        this.indentationLevel = indentationLevel;
        invalidateSerialized();
    }

    public void incrementIndentationLevel() {
        indentationLevel = indentationLevel + 1;
        invalidateSerialized();
    }

}
