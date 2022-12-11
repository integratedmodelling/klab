package org.integratedmodelling.klab.api.runtime.rest;

import java.io.File;

/**
 * Bare-bones http client API, exposed by network identities for ease of interaction. The calls
 * accept full URLs or just the API endpoint without the URL.
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
    <T> T get(String url, Class<? extends T> cls, Object... query);

    /**
     * Send a payload through a POST instruction and return the parsed response. Throw exceptions on
     * error.
     * 
     * @param <T>
     * @param url
     * @param data
     * @param cls
     * @return
     */
    <T> T post(String url, Object data, Class<? extends T> cls);

    /**
     * Send a payload through a PUT instruction and return a success/failure boolean.
     * 
     * @param <T>
     * @param url
     * @param data
     * @param cls
     * @return
     */
    boolean put(String url, Object data);

    /**
     * Upload a multipart file through a POST instruction and return the parsed response. Throw
     * exceptions on error.
     * 
     * @param <T>
     * @param url
     * @param data
     * @param cls
     * @return
     */
    <T> T postFile(String url, File data, Class<? extends T> cls);

    /**
     * DELETE request with optional return value. Can't call it delete for implementation reasons.
     * 
     * @param url
     * @param query
     * @return
     */
    Object remove(String url, Object... query);

    /**
     * Download a file through a GET call into the file path of choice. Throw exceptions on error.
     * 
     * @param url
     * @param output
     * @return
     */
    boolean getDownload(String url, File output);

}
