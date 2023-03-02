package org.integratedmodelling.klab.data.collections;

import org.integratedmodelling.klab.api.lang.KAnnotation;

public class SerializableAnnotation extends SerializableParameters<String> implements KAnnotation {

    private static final long serialVersionUID = -1135154127033892164L;
    private String name;
    private String contentClass;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getContentClass() {
        return contentClass;
    }

    public void setContentClass(String contentClass) {
        this.contentClass = contentClass;
    }

}
