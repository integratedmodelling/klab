package org.integratedmodelling.klab.api.runtime.rest;

import java.io.File;

/**
 * Bare-bones http client API, exposed by network identities for ease of
 * interaction. The calls accept full URLs or just the API endpoint without the
 * URL.
 * 
 * @author Ferd
 *
 */
public interface IClient {

	/**
	 * Send a GET url and return the parsed response. Throw exceptions on error.
	 * 
	 * @param <T>
	 * @param url
	 * @param cls
	 * @return
	 */
	<T> T get(String url, Class<? extends T> cls);

	/**
	 * Send a payload through a POST instruction and return the parsed response.
	 * Throw exceptions on error.
	 * 
	 * @param <T>
	 * @param url
	 * @param data
	 * @param cls
	 * @return
	 */
	<T> T post(String url, Object data, Class<? extends T> cls);

	/**
	 * Upload a multipart file through a POST instruction and return the parsed
	 * response. Throw exceptions on error.
	 * 
	 * @param <T>
	 * @param url
	 * @param data
	 * @param cls
	 * @return
	 */
	<T> T postFile(String url, Object data, Class<? extends T> cls);

	/**
	 * Download a file through a GET call into the file path of choice. Throw
	 * exceptions on error.
	 * 
	 * @param url
	 * @param output
	 * @return
	 */
	boolean getDownload(String url, File output);

}
