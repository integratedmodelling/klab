package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.utils.Parameters;

public class KimMetadata extends KimStatement implements IParameters<String> {

    private static final long serialVersionUID = 3885078963867253246L;

    /*
     * It's a multimap: the value can be a list and if so, the API must be capable of dealing with
     * it.
     */
    protected Parameters<String> data;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public KimMetadata(org.integratedmodelling.kim.kim.Map statement, IKimStatement parent) {
        super(statement, parent);
        this.data = new Parameters(Kim.INSTANCE.parseMap(statement,
                parent instanceof IKimNamespace
                        ? (IKimNamespace) parent
                        : Kim.INSTANCE.getNamespace(KimValidator.getNamespace(statement))));
    }

    public KimMetadata() {
        this.data = new Parameters<>();
    }

    public IParameters<String> getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public Object put(String key, Object value) {

        if (value == null) {
            return null;
        }

//        if (data.containsKey(key)) {
//            if (data.get(key) instanceof List) {
//                ((List<Object>) data.get(key)).add(value);
//            } else {
//                Object o = data.get(key);
//                data.put(key, new ArrayList<Object>());
//                ((List<Object>) data.get(key)).add(o);
//                ((List<Object>) data.get(key)).add(value);
//            }
//        } else {
//            data.put(key, new ArrayList<Object>());
//            ((List<Object>) data.get(key)).add(value);
//        }

        return data.put(key, value);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return data.get(key);
    }

    @Override
    public Object remove(Object key) {
        return data.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        data.putAll(m);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @Override
    public Collection<Object> values() {
        return data.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return data.entrySet();
    }

    @Override
    public <K> K get(String name, Class<? extends K> cls) {
        return data.get(name, cls);
    }

    @Override
    public <K> K getNotNull(String name, Class<? extends K> cls) {
        return data.getNotNull(name, cls);
    }

    @Override
    public <K> K get(String name, K defaultValue) {
        return data.get(name, defaultValue);
    }

    @Override
    public List<String> getUnnamedKeys() {
        return data.getUnnamedKeys();
    }

    @Override
    public List<String> getNamedKeys() {
        return data.getNamedKeys();
    }

    @Override
    public boolean contains(String key) {
        return data.contains(key);
    }

    @Override
    public boolean contains(String key, Class<?> cls) {
        return data.contains(key, cls);
    }

    @Override
    public boolean containsAnyKey(String... keys) {
        return data.containsAnyKey(keys);
    }

    @Override
    public boolean containsAny(Object... objects) {
        return data.containsAny(objects);
    }

    @Override
    public <K> K getAny(String... keys) {
        return data.getAny(keys);
    }

    @Override
    public Map<String, Object> getLike(String string) {
        return data.getLike(string);
    }

    @Override
    public List<Object> getUnnamedArguments() {
        return data.getUnnamedArguments();
    }

}
