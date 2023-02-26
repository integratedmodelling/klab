package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;

import org.integratedmodelling.klab.api.lang.kim.KKimScope;

public class KimScope implements KKimScope {

    private static final long serialVersionUID = 6072620934600537545L;
    
    private List<KKimScope> children;
    private String locationDescriptor;
    private String URI;

    @Override
    public List<KKimScope> getChildren() {
        return this.children;
    }

    @Override
    public String getLocationDescriptor() {
        return this.locationDescriptor;
    }

    @Override
    public String getURI() {
        return this.URI;
    }

    public void setChildren(List<KKimScope> children) {
        this.children = children;
    }

    public void setLocationDescriptor(String locationDescriptor) {
        this.locationDescriptor = locationDescriptor;
    }

    public void setURI(String uRI) {
        URI = uRI;
    }

    @Override
    public void visit(Visitor visitor) {
        // TODO Auto-generated method stub

    }

}
