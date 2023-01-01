package org.integratedmodelling.klab.utils.markdown;

public class Code extends Text {

    public Code(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "`";
    }

}
