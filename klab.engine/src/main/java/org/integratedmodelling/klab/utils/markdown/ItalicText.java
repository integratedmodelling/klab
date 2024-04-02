package org.integratedmodelling.klab.utils.markdown;

public class ItalicText extends Text {

    public ItalicText(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "_";
    }

}
