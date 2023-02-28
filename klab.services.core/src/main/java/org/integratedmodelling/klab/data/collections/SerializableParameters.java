//package org.integratedmodelling.klab.data.collections;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.integratedmodelling.kim.api.IParameters;
//import org.integratedmodelling.klab.utils.Parameters;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
//import com.fasterxml.jackson.databind.ser.std.MapSerializer;
//
///**
// * Map with custom serialization methods so that Jackson sends it around with correct types.
// * 
// * @author Ferd
// *
// */
//@JsonSerialize(contentUsing = MapSerializer.class)
//@JsonDeserialize(contentUsing = MapDeserializer.class)
//public class SerializableParameters extends LinkedHashMap<String, Object> implements IParameters<String> {
//
//    private static final long serialVersionUID = -4772716274342410304L;
//    private List<String> unnamedKeys = new ArrayList<>();
//
//    @Override
//    public boolean contains(T key, Class<?> cls) {
//        Object obj = this.get(key);
//        return obj != null && cls.isAssignableFrom(obj.getClass());
//    }
//
//    @Override
//    public List<String> getUnnamedKeys() {
//        return unnamedKeys;
//    }
//
//    @Override
//    public List<String> getNamedKeys() {
//        List<String> ret = new ArrayList<>();
//        for (String key : this.keySet()) {
//            if (!unnamedKeys.contains(key)) {
//                ret.add(key);
//            }
//        }
//        return ret;
//    }
//
//    @Override
//    public boolean containsAnyKey(String... keys) {
//        if (keys != null) {
//            for (String key : keys) {
//                if (this.containsKey(key)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean containsAny(Object... objects) {
//        if (objects != null) {
//            for (Object key : objects) {
//                if (this.containsValue(key)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public <K> K getAny(String... keys) {
//        if (keys != null) {
//            for (String key : keys) {
//                K ret = (K) get(key);
//                if (ret != null) {
//                    return (K) ret;
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> getLike(String string) {
//        Map<String, Object> ret = new LinkedHashMap<>();
//        for (String key : keySet()) {
//            if (key.toString().startsWith(string)) {
//                ret.put(key, get(key));
//            }
//        }
//        return ret;
//    }
//
//    @Override
//    public List<Object> getUnnamedArguments() {
//        List<Object> ret = new ArrayList<>();
//        for (T key : getUnnamedKeys()) {
//            ret.add(get(key));
//        }
//        return ret;
//    }
//
//    public IParameters<String> with(IParameters<String> state) {
//        if (state != null && !state.isEmpty()) {
//            SerializableParameters<String> ret = new SerializableParameters(this.this, this.unnamedKeys);
//            ret.templateVariables = state;
//            return ret;
//        }
//        return (IParameters<String>) this;
//    }
//
//    @Override
//    public IParameters<String> getTemplateVariables() {
//        return this.templateVariables;
//    }
//
//}
