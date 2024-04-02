package org.integratedmodelling.klab.utils.markdown;

public class SubScriptText extends Text {

    public SubScriptText(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "~";
    }

}