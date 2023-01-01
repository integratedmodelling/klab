package org.integratedmodelling.klab.utils.markdown;

public class InsertedText extends Text {

    public InsertedText(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "++";
    }

}