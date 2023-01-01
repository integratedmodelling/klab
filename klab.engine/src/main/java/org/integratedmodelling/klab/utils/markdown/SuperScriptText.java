package org.integratedmodelling.klab.utils.markdown;

public class SuperScriptText extends Text {

    public SuperScriptText(Object value) {
        super(value);
    }

    @Override
    public String getPredecessor() {
        return "^";
    }

}