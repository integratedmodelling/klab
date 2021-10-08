package org.integratedmodelling.klab.data.resources;

import java.util.Collection;
import java.util.Properties;

import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.rest.CodelistReference;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class Codelist<K, T> implements ICodelist<K, T> {

    CodelistReference reference;

    Multimap<K, T> direct = LinkedListMultimap.create();
    Multimap<K, T> reverse = LinkedListMultimap.create();

    public Codelist(CodelistReference reference) {
        this.reference = reference;
        // TODO populate the mappings
    }

    public Codelist(Properties properties) {
        this.reference = new CodelistReference();
        // TODO build the mappings, save as .json in same place so we override the properties with
        // the new form
    }

    public CodelistReference getReference() {
        return this.reference;
    }

    @Override
    public T value(K key) {
        return null;
    }

    @Override
    public K key(T value) {
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

    @Override
    public Collection<T> values(K key) {
        // TODO Auto-generated method stub
        return null;
    }
}
