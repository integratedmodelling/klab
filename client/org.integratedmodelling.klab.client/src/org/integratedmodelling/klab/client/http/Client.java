/*******************************************************************************
 * Copyright (C) 2007, 2016:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other authors listed
 * in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular, collaborative,
 * integrated development of interoperable data and model components. For details, see
 * http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the Affero
 * General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without even the
 * implied warranty of merchantability or fitness for a particular purpose. See the Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if not, write
 * to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. The license
 * is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.client.http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.utils.Escape;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Helper to avoid having to write 10 lines every time I need to do a GET with
 * headers. It can be given authorization objects using the with(...) idiom:
 * 
 * <code> RestTemplateHelper template = new RestTemplateHelper(); ...
 * template.with(session).post(....) ... </code>
 * 
 * with(..) can be called with any of IUser, INode, ITask, IContext and ISession according
 * to the type of authentication required by the receiver. Automatically sets the necessary
 * tokens and parameters.
 *
 * The template also configures an objectmapper for optimal use in k.LAB, manages errors
 * and inserts user agent headers.
 *
 * 
 * @author ferdinando.villa
 *
 */
public class Client extends RestTemplate {

    public static final String ENGINE_DEFAULT_URL = "http://127.0.0.1:8283/modeler";

    public static final String KLAB_VERSION_HEADER = "KlabVersion";
    public static final String KLAB_CONNECTION_TIMEOUT = "klab.connection.timeout";

    public static final String API_PING = "/ping";
    public static final String API_SCHEMA_GET = "/schema?get=";

    ObjectMapper objectMapper;
    String authToken;
    String url;

    private static ClientHttpRequestFactory factory;
    
    public class BeanDescriptor {
        String packageName;
        List<String> classes;
    }

    public static Client create(String url) {

        if (factory == null) {
            factory = new HttpComponentsClientHttpRequestFactory();
        }

        return new Client(factory, url);
    }

    public String getUrl() {
        return url;
    }

    //
    //    /**
    //     * Send an authentication request. 
    //     * @param url
    //     * @param request
    //     * @return the response. If not authenticated, throw a KlabAuthorizationException. If timeout, return null.
    //     */
    //    public AuthenticationResponse authenticate(String url, AuthenticationRequest request) {
    //        return post(url + API.AUTHENTICATE, request, AuthenticationResponse.class);
    //    }
    //
    /**
     * Check the engine's heartbeat.
    
     * @return ms since boot if alive, -1 otherwise
     */
    public long ping() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "text/plain");
            ResponseEntity<String> response = exchange(url + API_PING, HttpMethod.GET, new HttpEntity<String>(headers),
                    String.class);
            return response.getStatusCodeValue() == 200 ? Long.parseLong(response.getBody()) : -1;
        } catch (Throwable e) {
            return -1;
        }
    }

    public BeanDescriptor getPOJOClasses() {
        
        List<String> classes = new ArrayList<>();
        Map<?, ?> map = get(url + API_SCHEMA_GET + "all", Map.class);
        String packageName = map.get("package").toString();
        for (Object o : (List<?>) map.get("schemata")) {
            classes.add(o.toString());
        }
        BeanDescriptor ret = new BeanDescriptor();
        ret.packageName = packageName;
        ret.classes = classes;
        return ret;
    }

    /**
     * Open a session with the engine.
     * 
     * @param rejoinSession
     * @return a new session ID (possibly same as passed one to rejoin, meaning the rejoin was successful)
     */
    public String openSession(@Nullable String rejoinSession) {
        return null;
    }

    private class JSONResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            HttpStatus status = response.getStatusCode();
            HttpStatus.Series series = status.series();
            return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
        }
    }

    /**
     * Interceptor to add user agents and ensure that the authorization token gets in.
     * 
     * @author ferdinando.villa
     *
     */
    public class AuthorizationInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {

            HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
            HttpHeaders headers = requestWrapper.getHeaders();
            headers.set("Accept", "application/json");
            //            headers.set("X-User-Agent", "k.LAB " + Version.CURRENT);
            //            headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
            if (authToken != null) {
                headers.set(HttpHeaders.WWW_AUTHENTICATE, authToken);
            }
            return execution.execute(requestWrapper, body);
        }
    }

    private void setup() {

        objectMapper = new ObjectMapper();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        StringHttpMessageConverter utf8 = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        // ByteArrayHttpMessageConverter byteConverter = new
        // ByteArrayHttpMessageConverter();
        jsonMessageConverter.setObjectMapper(objectMapper);

        setErrorHandler(new JSONResponseErrorHandler());
        messageConverters.add(jsonMessageConverter);
        messageConverters.add(utf8);
        messageConverters.add(formHttpMessageConverter);
        // messageConverters.add(byteConverter);
        setMessageConverters(messageConverters);
        this.setInterceptors(Collections.singletonList(new AuthorizationInterceptor()));
    }

    private Client(ClientHttpRequestFactory factory, String url) {
        super(factory);
        this.url = url;
        setup();
    }

    private Client() {
        super(factory);
        this.url = ENGINE_DEFAULT_URL;
    }

    /**
     * Return a client with authorization set to the passed object.
     * 
     * @param authorization any identity. 
     * @return a new 
     */
    public Client with(String authorization) {

        /*
         * TODO handle the chain of authorization properly. Tokens are only issued
         * for engines, network sessions and sessions. The rest should set parameters
         * for calls.
         */

        Client ret = new Client();
        ret.objectMapper = this.objectMapper;
        ret.authToken = authorization;
        ret.url = url;
        return ret;
    }

    @SuppressWarnings({ "rawtypes" })
    public <T extends Object> T post(String url, Object data, Class<? extends T> cls) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        //        headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
        if (authToken != null) {
            headers.set(HttpHeaders.WWW_AUTHENTICATE, authToken);
        }

        HttpEntity<Object> entity = new HttpEntity<>(data, headers);

        try {

            ResponseEntity<Map> response = exchange(url, HttpMethod.POST, entity, Map.class);

            switch (response.getStatusCodeValue()) {
            case 302:
            case 403:
                throw new RuntimeException("unauthorized request " + url);
            case 404:
                throw new RuntimeException("internal: request " + url + " was not accepted");
            }

            if (response.getBody() == null) {
                return null;
            }
            if (response.getBody().containsKey("exception") && response.getBody().get("exception") != null) {
                Object exception = response.getBody().get("exception");
                //                Object path = response.getBody().get("path");
                Object message = response.getBody().get("message");
                //                Object error = response.getBody().get("error");
                throw new RuntimeException("remote exception: " + (message == null ? exception : message));
            }

            return objectMapper.convertValue(response.getBody(), cls);

        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getDownload(String url, File output) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        //        headers.set(KLAB_VERSION_HEADER, Version.CURRENT);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // HttpHeaders headers = new HttpHeaders();
        // headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        // HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        switch (response.getStatusCodeValue()) {
        case 302:
        case 403:
            throw new RuntimeException("unauthorized request " + url);
        case 404:
            throw new RuntimeException("internal: request " + url + " was not accepted");
        }

        if (response.getBody() == null) {
            return false;
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                Files.write(output.toPath(), response.getBody());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    /**
     * Instrumented for header communication and error parsing
     * 
     * @param url
     * @param cls
     * @return the deserialized result
     */
    @SuppressWarnings({ "rawtypes" })
    public <T> T get(String url, Class<T> cls) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        //        headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
        if (authToken != null) {
            headers.set(HttpHeaders.WWW_AUTHENTICATE, authToken);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = exchange(url, HttpMethod.GET, entity, Map.class);

        switch (response.getStatusCodeValue()) {
        case 302:
        case 403:
            throw new RuntimeException("unauthorized request " + url);
        case 404:
            throw new RuntimeException("internal: request " + url + " was not accepted");
        }

        if (response.getBody() == null) {
            return null;
        }
        if (response.getBody().containsKey("exception") && response.getBody().get("exception") != null) {
            Object exception = response.getBody().get("exception");
            //            Object path = response.getBody().get("path");
            Object message = response.getBody().get("message");
            //            Object error = response.getBody().get("error");
            throw new RuntimeException("remote exception: " + (message == null ? exception : message));
        }

        return objectMapper.convertValue(response.getBody(), cls);
    }

    /**
     * Instrumented for header communication and error parsing
     * 
     * @param url
     * @param cls
     * @param urlVariables
     * @return the deserialized result
     */
    <T> T get(String url, Class<T> cls, Map<String, ?> urlVariables) {
        return get(addParameters(url, urlVariables), cls);
    }

    /**
     * Create a GET URL from a base url and a set of parameters. Yes I know I can use
     * URIComponentsBuilder etc.
     * 
     * @param url
     * @param parameters
     * @return the finished url
     */
    public static String addParameters(String url, Map<String, ?> parameters) {
        String ret = url;
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                if (ret.length() == url.length()) {
                    ret += "?";
                } else {
                    ret += "&";
                }
                ret += key + "=" + Escape.forURL(parameters.get(key).toString());
            }
        }
        return ret;
    }

}
