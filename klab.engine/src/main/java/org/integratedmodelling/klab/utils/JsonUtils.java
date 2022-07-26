/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.exceptions.KlabIOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Obvious JSON utilities.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class JsonUtils {

    static ObjectMapper defaultMapper;

    static {
        defaultMapper = new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).enable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        defaultMapper.getSerializerProvider().setNullKeySerializer(new NullKeySerializer());
    }

    static class NullKeySerializer extends StdSerializer<Object> {

        private static final long serialVersionUID = 7120301608140961908L;

        public NullKeySerializer() {
            this(null);
        }

        public NullKeySerializer(Class<Object> t) {
            super(t);
        }

        @Override
        public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused)
                throws IOException, JsonProcessingException {
            jsonGenerator.writeFieldName("");
        }
    }

    /**
     * Default conversion for a map object.
     *
     * @param node the node
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> asMap(JsonNode node) {
        return defaultMapper.convertValue(node, Map.class);
    }

    /**
     * Default conversion, use within custom deserializers to "normally" deserialize an object.
     *
     * @param <T> the generic type
     * @param node the node
     * @param cls the cls
     * @return the t
     */
    public static <T> T as(JsonNode node, Class<T> cls) {
        return defaultMapper.convertValue(node, cls);
    }

    /**
     * Convert node to list of type T.
     *
     * @param <T> the generic type
     * @param node the node
     * @param cls the cls
     * @return the list
     */
    public static <T> List<T> asList(JsonNode node, Class<T> cls) {
        return defaultMapper.convertValue(node, new TypeReference<List<T>>(){
        });
    }

    public static <T> List<T> asList(JsonNode node, Class<T> cls, ObjectMapper mapper) {
        return mapper.convertValue(node, new TypeReference<List<T>>(){
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String text, Class<T> cls) {
        try {
            return (T) defaultMapper.readerFor(cls).readValue(text);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Convert node to list of type T.
     *
     * @param <T> the generic type
     * @param node the node
     * @param cls the cls
     * @return the sets the
     */
    public static <T> Set<T> asSet(JsonNode node, Class<T> cls) {
        return defaultMapper.convertValue(node, new TypeReference<Set<T>>(){
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T cloneObject(T object) {
        return (T) parseObject(printAsJson(object), object.getClass());
    }
    
    /**
     * Load an object from an input stream.
     * 
     * @param is the input stream
     * @param cls the class
     * @return the object
     * @throws KlabIOException
     */
    public static <T> T load(InputStream url, Class<T> cls) throws KlabIOException {
        try {
            return defaultMapper.readValue(url, cls);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    /**
     * Load an object from a file.
     * 
     * @param file
     * @param cls
     * @return the object
     * @throws KlabIOException
     */
    public static <T> T load(File file, Class<T> cls) throws KlabIOException {
        try {
            return defaultMapper.readValue(file, cls);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    /**
     * Load an object from a URL.
     * 
     * @param url
     * @param cls
     * @return the object
     * @throws KlabIOException
     */
    public static <T> T load(URL url, Class<T> cls) throws KlabIOException {
        try {
            return defaultMapper.readValue(url, cls);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    /**
     * Serialize an object to a file.
     * 
     * @param object
     * @param outFile
     * @throws KlabIOException
     */
    public static void save(Object object, File outFile) throws KlabIOException {
        try {
            defaultMapper.writeValue(outFile, object);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    public static String asString(Object object) {
        try {
            return defaultMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("serialization failed: " + e.getMessage());
        }
    }

    /**
     * Serialize the passed object as JSON and pretty-print the resulting code.
     *
     * @param object the object
     * @return the string
     */
    public static String printAsJson(Object object) {

        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
        om.enable(SerializationFeature.WRITE_NULL_MAP_VALUES); // pretty print
        om.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED); // pretty print
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("serialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Serialize the passed object as JSON and pretty-print the resulting code.
     *
     * @param object the object
     * @param file 
     * @return the string
     */
    public static void printAsJson(Object object, File file) {

        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
        om.enable(SerializationFeature.WRITE_NULL_MAP_VALUES); // pretty print
        om.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED); // pretty print
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        try {
        	om.writeValue(file, object);
        } catch (Exception e) {
            throw new IllegalArgumentException("serialization failed: " + e.getMessage());
        }
    }

    /**
     * Convert a map resulting from parsing generic JSON (or any other source) to the passed type.
     * 
     * @param payload
     * @param cls
     * @return the converted object
     */
    public static <T> T convertMap(Map<?, ?> payload, Class<T> cls) {
        return defaultMapper.convertValue(payload, cls);
    }

}
