package org.integratedmodelling.klab.utils.markdown;

public class StrikeThroughText extends Text {

    public StrikeThroughText(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "~~";
    }

}
