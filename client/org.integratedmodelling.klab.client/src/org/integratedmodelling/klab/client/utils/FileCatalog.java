/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.integratedmodelling.klab.exceptions.KlabIOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// TODO: Auto-generated Javadoc
/**
 * A catalog (simply a map String -> T) read from a JSON file and capable of
 * resynchronizing intelligently on request.
 * <p>
 * Any put/remove operations won't sync the contents to the backing file. To
 * synchronize the file write() must be called.
 * 
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T>
 *            the type of the resource in the catalog
 */
public class FileCatalog<T> extends LinkedHashMap<String, T> {

	private static final long serialVersionUID = 8664294752013639030L;

	Class<? extends T> cls;
	File file;
	long timestamp;
	boolean error;

	/**
	 * Creates the.
	 *
	 * @param <T>
	 *            the main type for the collection
	 * @param url
	 *            the URL containing the JSON data catalog
	 * @param interfaceClass
	 *            the type of the interface returned (or the implementation type
	 *            itself)
	 * @param implementationClass
	 *            the class implementing the interface
	 * @return a new URL-based catalog
	 */
	public static <T> FileCatalog<T> create(URL url, Class<T> interfaceClass, Class<? extends T> implementationClass) {
		return new FileCatalog<T>(url, interfaceClass, implementationClass);
	}

	/**
	 * Creates the.
	 *
	 * @param <T>
	 *            the interface type
	 * @param file
	 *            the containing the JSON data catalog
	 * @param interfaceClass
	 *            the type of the interface returned (or the implementation type
	 *            itself)
	 * @param implementationClass
	 *            the class implementing the interface
	 * @return a new file-based catalog
	 */
	public static <T> FileCatalog<T> create(File file, Class<T> interfaceClass,
			Class<? extends T> implementationClass) {
		return new FileCatalog<T>(file, interfaceClass, implementationClass);
	}

	private FileCatalog(URL url, Class<T> type, Class<? extends T> cls) {
		this.cls = cls;
		try (InputStream input = url.openStream()) {
			this.error = !synchronize(input);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Instantiates a new file catalog.
	 *
	 * @param file
	 *            the file
	 * @param type
	 *            the type
	 * @param cls
	 *            the cls
	 */
	public FileCatalog(File file, Class<? extends T> type, Class<? extends T> cls) {

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}

		this.file = file;
		this.cls = cls;
		try (InputStream input = new FileInputStream(file)) {
			this.error = !synchronize(input);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Checks for errors.
	 *
	 * @return a boolean.
	 */
	public boolean hasErrors() {
		return this.error;
	}

	/**
	 * Synchronize, reading the file if necessary.
	 *
	 * @param stream
	 *            the stream
	 * @return true if no errors. Non-existing file is not an error.
	 * @throws java.lang.ClassCastException
	 *             if the data read are not of the type configured
	 */
	public boolean synchronize(InputStream stream) {

		boolean ret = true;

		if (this.file == null || (this.file.exists() && this.timestamp < this.file.lastModified())) {

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
					.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

			try {
				JavaType type = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, cls);
				Map<Object, T> data = objectMapper.reader(type).readValue(stream);
				clear();
				for (Object key : data.keySet()) {
					put(key.toString(), (T) data.get(key));
				}
				this.timestamp = this.file == null ? System.currentTimeMillis() : this.file.lastModified();
			} catch (IOException e) {
				ret = false;
			}
		}

		return ret;

	}

	/**
	 * Write the map to the backing file. Call after making changes to the
	 * underlying map.
	 * 
	 * @throws IllegalStateException
	 *             if the catalog was read from a URL.
	 */
	public void write() {
		if (this.file != null && this.file.exists()) {

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
			objectMapper.enable(SerializationFeature.WRITE_NULL_MAP_VALUES); // pretty print
			objectMapper.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED); // pretty print
			objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
					.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			Map<String, T> data = new HashMap<>();
			data.putAll(this);
			try {
				objectMapper.writer().writeValue(this.file, data);
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
			this.timestamp = this.file.lastModified();
		}
	}

	public File getFile() {
		return file;
	}

}
