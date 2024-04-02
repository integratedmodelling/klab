package org.integratedmodelling.klab.utils.markdown;

public class BoldText extends Text {

    public BoldText(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "**";
    }

}
