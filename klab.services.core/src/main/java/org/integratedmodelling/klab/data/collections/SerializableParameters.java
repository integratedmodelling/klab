package org.integratedmodelling.klab.data.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.lang.kim.impl.KimConcept;
import org.integratedmodelling.klab.api.lang.kim.impl.KimLookupTable;
// import org.integratedmodelling.klab.api.data.TemplateValue;
import org.integratedmodelling.klab.api.utils.Utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Parameters with the JSON annotations that make it serializable (TODO) without hassle.
 * 
 * @author ferdinando.villa
 *
 */
public class SerializableParameters<T> implements KParameters<T> {

    private static final long serialVersionUID = 9005569559325849053L;

    
    private Map<T, Object> delegate;
    private List<T> unnamedKeys = new ArrayList<>();
    private KParameters<String> templateVariables = null;

    public SerializableParameters(Map<T, Object> delegate) {
        this.delegate = delegate == null ? new LinkedHashMap<>() : delegate;
        if (delegate instanceof SerializableParameters) {
            this.unnamedKeys.addAll(((SerializableParameters<T>) delegate).unnamedKeys);
        }
    }

    public static final String IGNORED_PARAMETER = "__IGNORED__";

    /**
     * Create a parameters object from a list of key/value pairs, optionally including also other
     * (non-paired) map objects whose values are added as is. A null in first position of a pair is
     * ignored, as well as anything whose key is {@link #IGNORED_PARAMETER}.
     * 
     * @param o
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> SerializableParameters<T> create(Object... o) {
        Map<T, Object> inp = new LinkedHashMap<T, Object>();
        if (o != null) {
            for (int i = 0; i < o.length; i++) {
                if (o[i] instanceof Map) {
                    inp.putAll((Map) o[i]);
                } else if (o[i] != null) {
                    if (!IGNORED_PARAMETER.equals(o[i])) {
                        inp.put((T) o[i], o[i + 1]);
                    }
                    i++;
                }
            }
        }
        return new SerializableParameters(inp);
    }

    /**
     * Like the other create() but also ignores null values for non-null keys.
     * 
     * @param <T>
     * @param o
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> SerializableParameters<T> createNotNull(Object... o) {
        Map<T, Object> inp = new LinkedHashMap<T, Object>();
        if (o != null) {
            for (int i = 0; i < o.length; i++) {
                if (o[i] instanceof Map) {
                    inp.putAll((Map) o[i]);
                } else if (o[i] != null) {
                    if (o[1 + 1] != null && !IGNORED_PARAMETER.equals(o[i])) {
                        inp.put((T) o[i], o[i + 1]);
                    }
                    i++;
                }
            }
        }
        return new SerializableParameters(inp);
    }

    /**
     * Create a parameters object from a list of key/value pairs, optionally including also other
     * (non-paired) map objects whose values are added as is. A null in first position of a pair is
     * ignored, as well as anything whose key is {@link #IGNORED_PARAMETER}.
     * 
     * @param o
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> SerializableParameters<T> createSynchronized(Object... o) {
        Map<T, Object> inp = Collections.synchronizedMap(new LinkedHashMap<T, Object>());
        if (o != null) {
            for (int i = 0; i < o.length; i++) {
                if (o[i] instanceof Map) {
                    inp.putAll((Map) o[i]);
                } else if (o[i] != null) {
                    if (!IGNORED_PARAMETER.equals(o[i])) {
                        inp.put((T) o[i], o[i + 1]);
                    }
                    i++;
                }
            }
        }
        return new SerializableParameters(inp);
    }

    /**
     * Wrap an existing map and enjoy.
     * 
     * @param <T>
     * @param map
     * @return
     */
    public static <T> SerializableParameters<T> wrap(Map<T, Object> map) {
        return new SerializableParameters<T>(map);
    }

    /**
     * Only used when the object must be serialized through reflection.
     * 
     * @return a map with all data
     */
    public Map<T, Object> getData() {
        return delegate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K> K get(T name, K defaultValue) {
        Object ret = get(name);
        if (ret == null) {
            return defaultValue;
        }
        return defaultValue == null ? (K) ret : Utils.Data.asType(ret, defaultValue.getClass());
    }

    @Override
    public <K> K get(T name, Class<? extends K> cls) {
        Object ret = get(name);
        if (ret == null) {
            return null;
        }
        return Utils.Data.asType(ret, cls);
    }

    @Override
    public <K> K getNotNull(T name, Class<? extends K> cls) {
        Object ret = get(name);
        if (ret == null) {
            return Utils.Data.notNull(cls);
        }
        return Utils.Data.asType(ret, cls);
    }

    public SerializableParameters() {
        this.delegate = new LinkedHashMap<>();
    }

    public SerializableParameters(Map<T, Object> delegate, List<T> unnamedKeys) {
        this.delegate = delegate;
        this.unnamedKeys = unnamedKeys;
    }

    public int size() {
        return delegate.size();
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object get(Object key) {
        Object ret = delegate.get(key);
        // if (this.templateVariables != null && ret instanceof TemplateValue) {
        // ret = ((TemplateValue) ret).getValue(this.templateVariables);
        // }
        if (ret instanceof Map) {
            ret = new SerializableParameters((Map<?, ?>) ret);
            ((SerializableParameters<?>) ret).templateVariables = this.templateVariables;
        }
        return ret;
    }

    public Object put(T key, Object value) {
        return delegate.put(key, value);
    }

    public Object remove(Object key) {
        return delegate.remove(key);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map<? extends T, ? extends Object> m) {
        delegate.putAll(m);
        if (m instanceof SerializableParameters) {
            this.unnamedKeys.addAll(((SerializableParameters<T>) m).unnamedKeys);
        }
    }

    public Map<String, String> asStringMap() {
        Map<String, String> ret = new LinkedHashMap<>();
        for (T object : keySet()) {
            ret.put(object.toString(), ret.get(object) == null ? null : ret.get(object).toString());
        }
        return ret;
    }

    public void clear() {
        delegate.clear();
    }

    public Set<T> keySet() {
        return delegate.keySet();
    }

    public Collection<Object> values() {
        return delegate.values();
    }

    public Set<Entry<T, Object>> entrySet() {
        return delegate.entrySet();
    }

    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    public int hashCode() {
        return delegate.hashCode();
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        return delegate.getOrDefault(key, defaultValue);
    }

    public void forEach(BiConsumer<? super T, ? super Object> action) {
        delegate.forEach(action);
    }

    public void replaceAll(BiFunction<? super T, ? super Object, ? extends Object> function) {
        delegate.replaceAll(function);
    }

    public Object putIfAbsent(T key, Object value) {
        return delegate.putIfAbsent(key, value);
    }

    @SuppressWarnings("unchecked")
    public Object putUnnamed(Object value) {
        String name = "_p" + (unnamedKeys.size() + 1);
        unnamedKeys.add((T) name);
        return put((T) name, value);
    }

    public boolean remove(Object key, Object value) {
        return delegate.remove(key, value);
    }

    public boolean replace(T key, Object oldValue, Object newValue) {
        return delegate.replace(key, oldValue, newValue);
    }

    public Object replace(T key, Object value) {
        return delegate.replace(key, value);
    }

    public Object computeIfAbsent(T key, Function<? super T, ? extends Object> mappingFunction) {
        return delegate.computeIfAbsent(key, mappingFunction);
    }

    public Object computeIfPresent(T key, BiFunction<? super T, ? super Object, ? extends Object> remappingFunction) {
        return delegate.computeIfPresent(key, remappingFunction);
    }

    public Object compute(T key, BiFunction<? super T, ? super Object, ? extends Object> remappingFunction) {
        return delegate.compute(key, remappingFunction);
    }

    public Object merge(T key, Object value, BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        return delegate.merge(key, value, remappingFunction);
    }

    @Override
    public boolean contains(T key) {
        return delegate.get(key) != null;
    }

    @Override
    public boolean contains(T key, Class<?> cls) {
        Object obj = delegate.get(key);
        return obj != null && cls.isAssignableFrom(obj.getClass());
    }

    @Override
    public List<T> getUnnamedKeys() {
        return unnamedKeys;
    }

    @Override
    public List<T> getNamedKeys() {
        List<T> ret = new ArrayList<>();
        for (T key : delegate.keySet()) {
            if (!unnamedKeys.contains(key)) {
                ret.add(key);
            }
        }
        return ret;
    }

    @Override
    public boolean containsAnyKey(@SuppressWarnings("unchecked") T... keys) {
        if (keys != null) {
            for (T key : keys) {
                if (this.containsKey(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAny(Object... objects) {
        if (objects != null) {
            for (Object key : objects) {
                if (this.containsValue(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K> K getAny(T... keys) {
        if (keys != null) {
            for (T key : keys) {
                K ret = (K) get(key);
                if (ret != null) {
                    return (K) ret;
                }
            }
        }
        return null;
    }

    @Override
    public Map<T, Object> getLike(String string) {
        Map<T, Object> ret = new LinkedHashMap<>();
        for (T key : keySet()) {
            if (key.toString().startsWith(string)) {
                ret.put(key, get(key));
            }
        }
        return ret;
    }

    @Override
    public List<Object> getUnnamedArguments() {
        List<Object> ret = new ArrayList<>();
        for (T key : getUnnamedKeys()) {
            ret.add(get(key));
        }
        return ret;
    }

    @SuppressWarnings("rawtypes")
    public KParameters<T> with(KParameters<String> state) {
        if (state != null && !state.isEmpty()) {
            @SuppressWarnings("unchecked")
            SerializableParameters<T> ret = new SerializableParameters(this.delegate, this.unnamedKeys);
            ret.templateVariables = state;
            return ret;
        }
        return (KParameters<T>) this;
    }

    @Override
    public KParameters<String> getTemplateVariables() {
        return this.templateVariables;
    }

}
