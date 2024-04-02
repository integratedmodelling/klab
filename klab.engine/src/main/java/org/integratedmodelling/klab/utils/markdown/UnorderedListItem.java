package org.integratedmodelling.klab.utils.markdown;

public class UnorderedListItem extends Text {

    public UnorderedListItem(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "- ";
    }

    @Override
    public String getSuccessor() {
        return "";
    }

}
