package org.integratedmodelling.klab.utils.markdown;

public class Text extends MarkdownElement implements MarkdownCascadable {

    protected Object value;

    public Text(Object value) {
        this.value = value;
    }

    @Override
    public String serialize() throws MarkdownSerializationException {
        if (value == null) {
            throw new MarkdownSerializationException("Value is null");
        }
        return getPredecessor() + value.toString() + getSuccessor();
    }

    @Override
    public String getPredecessor() {
        return "";
    }

    @Override
    public String getSuccessor() {
        return getPredecessor();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        invalidateSerialized();
    }

}
