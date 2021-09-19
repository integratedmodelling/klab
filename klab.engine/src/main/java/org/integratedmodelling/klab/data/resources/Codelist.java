package org.integratedmodelling.klab.data.resources;

import java.util.Properties;

import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.rest.CodelistReference;

public class Codelist<K, T> implements ICodelist<K, T> {

    CodelistReference reference;
    
    public Codelist(CodelistReference reference) {
        this.reference = reference;
    }

    public Codelist(Properties properties) {
        this.reference = new CodelistReference();
    }
    
    public CodelistReference getReference() {
        return this.reference;
    }
    
    @Override
    public T map(K key) {
        return null;
    }

    @Override
    public K reverseMap(T value) {
        return null;
    }
    
    @Override
    public String getWorldview() {
        return reference.getWorldview();
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return reference.getType();
    }

    @Override
    public String getName() {
        return reference.getName();
    }

    @Override
    public String getDescription() {
        return reference.getDescription();
    }

    @Override
    public String getAuthorityId() {
        return reference.getAuthorityId();
    }

    @Override
    public boolean isAuthority() {
        return reference.isAuthority();
    }

}
