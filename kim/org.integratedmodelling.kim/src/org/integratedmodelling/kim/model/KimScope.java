package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimScope;

public abstract class KimScope implements IKimScope {

    private static final long serialVersionUID = -369366891002303085L;

    protected List<IKimScope> children = new ArrayList<>();
	protected org.eclipse.emf.common.util.URI uri;

	@Override
	public List<IKimScope> getChildren() {
		return children;
	}

	public java.net.URI getURI() {
		return null; //uri;
	}
	
    public void dump() {
        System.out.println(getStringRepresentation(0));
    }

    protected abstract String getStringRepresentation(int offset);

    protected static String offset(int n) {
        String ret = "";
        for (int i = 0; i < n; i++) {
            ret += " ";
        }
        return ret;
    }

    public void visit(Visitor visitor) {
    	
    }
    
}
