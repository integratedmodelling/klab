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
package org.integratedmodelling.klab.communication.client;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.rest.IClient;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.integratedmodelling.klab.utils.Escape;
import org.springframework.core.io.FileSystemResource;
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
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * Helper to avoid having to write 10 lines every time I need to do a GET with
 * headers. It can be given authorization objects using the with(...) idiom:
 * 
 * <code> RestTemplateHelper template = new RestTemplateHelper(); ...
 * template.with(session).post(....) ... </code>
 * 
 * with(..) can be called with any {@link IIdentity} according to the type of
 * authentication required by the receiver. Automatically sets the necessary
 * tokens and parameters.
 *
 * The template also configures an objectmapper for optimal use in k.LAB,
 * manages errors and inserts user agent headers.
 *
 * 
 * @author ferdinando.villa
 *
 */
public class Client extends RestTemplate implements IClient {

	public static final String KLAB_VERSION_HEADER = "KlabVersion";
	public static final String KLAB_CONNECTION_TIMEOUT = "klab.connection.timeout";

	ObjectMapper objectMapper;
	String authToken;

	RestTemplate basicTemplate = new RestTemplate();
	private Set<String> endpoints = new HashSet<>();

	private static ClientHttpRequestFactory factory;

	public static Client createCustomTimeoutClient(int timeout) {
		HttpComponentsClientHttpRequestFactory custom = new HttpComponentsClientHttpRequestFactory();
		custom.setReadTimeout(timeout);
		custom.setConnectTimeout(timeout);
		return new Client(custom);
	}

	public static Client create() {

		if (factory == null) {
			factory = new HttpComponentsClientHttpRequestFactory();
			if (Configuration.INSTANCE.getProperties().containsKey(KLAB_CONNECTION_TIMEOUT)) {
				int connectTimeout = 1000
						* Integer.parseInt(Configuration.INSTANCE.getProperties().getProperty(KLAB_CONNECTION_TIMEOUT));
				((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout(connectTimeout);
				((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout(connectTimeout);
			}
		}

		return new Client(factory);
	}

	/**
	 * This one will return a client that interprets anything as JSON independent of
	 * content type. Required for misbehaving services that return JSON with other
	 * content types.
	 * 
	 * @return
	 */
	public static Client createUniversalJSON() {

		if (factory == null) {
			factory = new HttpComponentsClientHttpRequestFactory();
			if (Configuration.INSTANCE.getProperties().containsKey(KLAB_CONNECTION_TIMEOUT)) {
				int connectTimeout = 1000
						* Integer.parseInt(Configuration.INSTANCE.getProperties().getProperty(KLAB_CONNECTION_TIMEOUT));
				((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout(connectTimeout);
				((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout(connectTimeout);
			}
		}

		return new UniversalClient(factory);
	}

	public static class UniversalClient extends Client {
		UniversalClient(ClientHttpRequestFactory factory) {
			super(factory);
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			/*
			 * Make this converter process any kind of response, not only application/*json,
			 * which is the default behaviour
			 */
			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
			messageConverters.add(converter);
			this.setMessageConverters(messageConverters);
		}
	}

	public static class NodeClient extends Client {

		public NodeClient(INodeIdentity node) {

			super(factory);

			objectMapper = new ObjectMapper();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
			StringHttpMessageConverter utf8 = new StringHttpMessageConverter(Charset.forName("UTF-8"));
			FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
			ProtobufHttpMessageConverter protobufConverter = new ProtobufHttpMessageConverter();
			// ByteArrayHttpMessageConverter byteConverter = new
			// ByteArrayHttpMessageConverter();
			jsonMessageConverter.setObjectMapper(objectMapper);

			setErrorHandler(new JSONResponseErrorHandler());
			messageConverters.add(jsonMessageConverter);
			messageConverters.add(utf8);
			messageConverters.add(formHttpMessageConverter);
			messageConverters.add(protobufConverter);
			// messageConverters.add(byteConverter);
			setMessageConverters(messageConverters);
			this.setInterceptors(Collections.singletonList(new AuthorizationInterceptor()));
			this.authToken = node.getId();
		}
	}

	/**
	 * Send an authentication request to a hub for an engine.
	 * 
	 * @param url
	 * @param request
	 * @return the response. If not authenticated, throw a
	 *         KlabAuthorizationException. If timeout, return null.
	 */
	public EngineAuthenticationResponse authenticateEngine(String url, EngineAuthenticationRequest request) {
		return post(url + API.HUB.AUTHENTICATE_ENGINE, request, EngineAuthenticationResponse.class);
	}

	/**
	 * Send an authentication request to a hub for a node.
	 * 
	 * @param url
	 * @param request
	 * @return the response. If not authenticated, throw a
	 *         KlabAuthorizationException. If timeout, return null.
	 */
	public NodeAuthenticationResponse authenticateNode(String url, NodeAuthenticationRequest request) {
		return post(url + API.HUB.AUTHENTICATE_NODE, request, NodeAuthenticationResponse.class);
	}
	
	/**
	 * Check an engine's heartbeat.
	 * 
	 * @param url base engine/node URL
	 * @return true if alive
	 */
	public boolean ping(String url) {
		try {
			ResponseEntity<Object> response = basicTemplate.exchange(url + "/actuator/health", HttpMethod.GET,
					new HttpEntity<Object>(null, null), Object.class);
			return response.getStatusCodeValue() == 200;
		} catch (Throwable e) {
			return false;
		}
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
	 * Interceptor to add user agents and ensure that the authorization token gets
	 * in.
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
			headers.set("X-User-Agent", "k.LAB " + Version.CURRENT);
			headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
			if (authToken != null) {
				headers.set(HttpHeaders.AUTHORIZATION, authToken);
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

	private Client(ClientHttpRequestFactory factory) {
		super(factory);
		setup();
	}

	private Client() {
		super(factory);
	}

	/**
	 * Return a client with authorization set to the passed object.
	 * 
	 * @param authorizer any identity.
	 * @return a new
	 */
	public Client with(IIdentity authorizer) {

		Client ret = new Client();
		ret.objectMapper = this.objectMapper;
		ret.authToken = authorizer.getId();
		return ret;
	}

	public Client with(String authorization) {

		Client ret = new Client();
		ret.objectMapper = this.objectMapper;
		ret.authToken = authorization;
		return ret;
	}

	@Override
	public <T extends Object> T post(String url, Object data, Class<? extends T> cls) {

		url = checkEndpoint(url);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		if (data != null) {
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}

		HttpEntity<Object> entity = new HttpEntity<>(data, headers);

		try {

			ResponseEntity<Object> response = exchange(url, HttpMethod.POST, entity, Object.class);

			switch (response.getStatusCodeValue()) {
			case 302:
			case 403:
				throw new KlabAuthorizationException("unauthorized request " + url);
			case 404:
				throw new KlabInternalErrorException("internal: request " + url + " was not accepted");
			case 500:
				throw new KlabResourceAccessException("internal: request " + url + " caused a remote server error");
			}

			if (response.getBody() == null) {
				return null;
			}
			if (response.getBody() instanceof Map && ((Map<?, ?>) response.getBody()).containsKey("exception")
					&& ((Map<?, ?>) response.getBody()).get("exception") != null) {

				Map<?, ?> map = (Map<?, ?>) response.getBody();
				Object exception = map.get("exception");
				// Object path = map.get("path");
				Object message = map.get("message");
				// Object error = map.get("error");
				
	            dumpRequest(url, headers, data);
				throw new KlabIOException("remote exception: " + (message == null ? exception : message));
			}

			if (cls.isAssignableFrom(response.getBody().getClass())) {
				return (T) response.getBody();
			}

			try {

			    return objectMapper.convertValue(response.getBody(), cls);
			} catch (Throwable t) {
				System.out.println("Unrecognized response: " + response.getBody());
				throw t;
			}
		} catch (RestClientException e) {
		    
		    System.out.println("REST  exception: " + e.getMessage());
		    dumpRequest(url, headers, data);
			throw new KlabIOException(e);
		}
	}

	private void dumpRequest(String url, HttpHeaders headers, Object data) {

        System.out.println("Endpoint: " + url);
        System.out.println("Headers:");
        for (String header : headers.keySet()) {
            System.out.println("  " + header + " = " + headers.get(header));
        }
        System.out.println("Data:\n" + printAsJson(data));

    }

    public void setUrl(String... url) {
		if (url == null || url.length == 0) {
			this.endpoints.clear();
		} else {
			for (String u : url) {
				this.endpoints.add(u);
			}
		}
	}

	private String pickEndpoint() {
		// TODO periodically check URLs and choose the first that responds (or the one
		// with the smallest load)
		return this.endpoints.isEmpty() ? null : this.endpoints.iterator().next();
	}

	@Override
	public boolean getDownload(String url, File output) {

		url = checkEndpoint(url);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		// HttpHeaders headers = new HttpHeaders();
		// headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		// HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

		switch (response.getStatusCodeValue()) {
		case 302:
		case 403:
			throw new KlabAuthorizationException("unauthorized request " + url);
		case 404:
			throw new KlabInternalErrorException("internal: request " + url + " was not accepted");
		}

		if (response.getBody() == null) {
			return false;
		}
		if (response.getStatusCode() == HttpStatus.OK) {
			try {
				Files.write(output.toPath(), response.getBody());
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}

		return true;
	}

	private String checkEndpoint(String url) {
		if (!url.toLowerCase().startsWith("http")) {
			String ep = pickEndpoint();
			if (ep != null) {
				url = ep + (ep.endsWith("/") || url.startsWith("/") ? "" : "/") + url;
			}
		}
		return url;
	}

	/**
	 * Issue a DELETE request. Called remove to avoid conflict with super.
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 */
	public Object remove(String url, Object... parameters) {

		url = checkEndpoint(url);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}
		HttpEntity<String> entity = new HttpEntity<>(headers);

		if (parameters != null) {
			String params = "";
			for (int i = 0; i < parameters.length; i++) {
				String key = parameters[i].toString();
				String nakedKey = key;
				String val = parameters[++i].toString();
				if (!(key.startsWith("{") && key.endsWith("}"))) {
					key = "{" + key + "}";
				} else {
					nakedKey = key.substring(1, key.length() - 1);
				}
				if (url.contains(key)) {
					url = url.replace(key, val);
				} else {
					params += (params.isEmpty() ? "" : "&") + nakedKey + "=" + Escape.forURL(val);
				}
			}
			if (!params.isEmpty()) {
				url += "?" + params;
			}
		}

		ResponseEntity<?> response = exchange(url, HttpMethod.DELETE, entity, Object.class);

		switch (response.getStatusCodeValue()) {
		case 302:
		case 403:
			throw new KlabAuthorizationException("unauthorized request " + url);
		case 404:
			throw new KlabInternalErrorException("internal: request " + url + " was not recognized");
		case 503:
			throw new KlabInternalErrorException("internal: request " + url + " caused a server error");
		}

		return response.getBody();
	}

	/**
	 * Instrumented for header communication and error parsing
	 * 
	 * @param url
	 * @param cls
	 * @return the deserialized result
	 */
	@Override
	@SuppressWarnings({ "unchecked" })
	public <T> T get(String url, Class<? extends T> cls, Object... parameters) {

		url = checkEndpoint(url);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", cls.equals(String.class) ? "text/plain" : "application/json");
		headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}
		HttpEntity<String> entity = new HttpEntity<>(headers);

		if (parameters != null) {
			String params = "";
			for (int i = 0; i < parameters.length; i++) {
				String key = parameters[i].toString();
				String nakedKey = key;
				String val = parameters[++i].toString();
				if (!(key.startsWith("{") && key.endsWith("}"))) {
					key = "{" + key + "}";
				} else {
					nakedKey = key.substring(1, key.length() - 1);
				}
				if (url.contains(key)) {
					url = url.replace(key, val);
				} else {
					params += (params.isEmpty() ? "" : "&") + nakedKey + "=" + Escape.forURL(val);
				}
			}
			if (!params.isEmpty()) {
				url += "?" + params;
			}
		}

		ResponseEntity<?> response = null;
		if (cls.isArray()) {
			response = exchange(url, HttpMethod.GET, entity, Object.class);
		} else if (String.class.equals(cls)) {
			response = basicTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} else /* if (Map.class.isAssignableFrom(cls)) */ {
			response = exchange(url, HttpMethod.GET, entity, Map.class);
		} 

		switch (response.getStatusCodeValue()) {
		case 302:
		case 403:
			throw new KlabAuthorizationException("unauthorized request " + url);
		case 404:
			throw new KlabInternalErrorException("internal: request " + url + " was not recognized");
		case 406:
		case 503:
			throw new KlabInternalErrorException("internal: request " + url + " caused a server error");
		}

		if (response.getBody() instanceof Map) {

			Object exception = ((Map<?, ?>) response.getBody()).get("exception");
			Object error = ((Map<?, ?>) response.getBody()).get("error");

			if (exception != null || error != null) {
				Object message = ((Map<?, ?>) response.getBody()).get("message");
				// Object error = response.getBody().get("error");
				throw new KlabIOException("remote exception: " + (message == null ? (exception == null ? error : exception) : message));
			}

			return objectMapper.convertValue(response.getBody(), cls);

		} else if (response.getBody() instanceof List && cls.isArray()) {

			List<?> list = (List<?>) response.getBody();
			Object ret = Array.newInstance(cls.getComponentType(), (((List<?>) response.getBody()).size()));
			for (int i = 0; i < list.size(); i++) {
				Object object = list.get(i);
				if (object instanceof Map) {
					object = objectMapper.convertValue(object, cls.getComponentType());
				}
				Array.set(ret, i, object);
			}

			return (T) ret;

		} else if (response.getBody() != null && cls.isAssignableFrom(response.getBody().getClass())) {
			return (T) response.getBody();
		}

		return null;
	}

	@Override
	public <T> T postFile(String url, File file, Class<? extends T> cls) {

		url = checkEndpoint(url);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", new FileSystemResource(file));
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

		try {

			final RestTemplate restTemplate = new RestTemplate();
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			// without this, large files will eat up the heap
			requestFactory.setBufferRequestBody(false);
			restTemplate.setRequestFactory(requestFactory);
			ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

			switch (response.getStatusCodeValue()) {
			case 302:
			case 403:
				throw new KlabAuthorizationException("unauthorized request " + url);
			case 404:
				throw new KlabInternalErrorException("internal: request " + url + " was not accepted");
			}

			if (response.getBody().containsKey("exception") && response.getBody().get("exception") != null) {
				Object exception = response.getBody().get("exception");
				// Object path = response.getBody().get("path");
				Object message = response.getBody().get("message");
				// Object error = response.getBody().get("error");
				throw new KlabIOException("remote exception: " + (message == null ? exception : message));
			}

			return objectMapper.convertValue(response.getBody(), cls);

		} catch (RestClientException e) {
			throw new KlabIOException(e);
		}

	}

    public static String printAsJson(Object object) {

        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
        om.enable(SerializationFeature.WRITE_NULL_MAP_VALUES); // pretty print
        om.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED); // pretty print
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        try {
            return om.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalArgumentException("serialization failed: " + e.getMessage());
        }
    }

    @Override
    public boolean put(String url, Object data) {
        // TODO Auto-generated method stub
        return false;
    }
	
}
