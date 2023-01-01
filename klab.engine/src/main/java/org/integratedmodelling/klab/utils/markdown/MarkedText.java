package org.integratedmodelling.klab.utils.markdown;

public class MarkedText extends Text {

    public MarkedText(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "==";
    }

}