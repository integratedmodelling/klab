package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.lang.kim.KKimScope;

public abstract class KimScope implements KKimScope {

    private static final long serialVersionUID = 6072620934600537545L;
    
    private List<KKimScope> children = new ArrayList<>();
    private String locationDescriptor;
    private String uri;

    @Override
    public List<KKimScope> getChildren() {
        return this.children;
    }

    @Override
    public String getLocationDescriptor() {
        return this.locationDescriptor;
    }

    @Override
    public String getUri() {
        return this.uri;
    }

    public void setChildren(List<KKimScope> children) {
        this.children = children;
    }

    public void setLocationDescriptor(String locationDescriptor) {
        this.locationDescriptor = locationDescriptor;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public void visit(Visitor visitor) {
        // TODO Auto-generated method stub

    }

}
