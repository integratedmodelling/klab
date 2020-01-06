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
import org.integratedmodelling.klab.api.runtime.rest.IClient;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.integratedmodelling.klab.utils.Escape;
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
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	@SuppressWarnings({ "rawtypes" })
	public <T extends Object> T post(String url, Object data, Class<? extends T> cls) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}

		HttpEntity<Object> entity = new HttpEntity<>(data, headers);

		try {

			ResponseEntity<Map> response = exchange(url, HttpMethod.POST, entity, Map.class);

			switch (response.getStatusCodeValue()) {
			case 302:
			case 403:
				throw new KlabAuthorizationException("unauthorized request " + url);
			case 404:
				throw new KlabInternalErrorException("internal: request " + url + " was not accepted");
			}

			if (response.getBody() == null) {
				return null;
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
	 * Instrumented for header communication and error parsing
	 * 
	 * @param url
	 * @param cls
	 * @return the deserialized result
	 */
	@Override
	@SuppressWarnings({ "rawtypes" })
	public <T> T get(String url, Class<? extends T> cls) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<Map> response = exchange(url, HttpMethod.GET, entity, Map.class);

		switch (response.getStatusCodeValue()) {
		case 302:
		case 403:
			throw new KlabAuthorizationException("unauthorized request " + url);
		case 404:
			throw new KlabInternalErrorException("internal: request " + url + " was not accepted");
		}

		if (response.getBody() == null) {
			return null;
		}
		if (response.getBody().containsKey("exception") && response.getBody().get("exception") != null) {
			Object exception = response.getBody().get("exception");
			// Object path = response.getBody().get("path");
			Object message = response.getBody().get("message");
			// Object error = response.getBody().get("error");
			throw new KlabIOException("remote exception: " + (message == null ? exception : message));
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
	public <T> T get(String url, Class<T> cls, Map<String, ?> urlVariables) {
		return get(addParameters(url, urlVariables), cls);
	}

	/**
	 * Create a GET URL from a base url and a set of parameters. Yes I know I can
	 * use URIComponentsBuilder etc.
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

	@Override
	public <T> T postFile(String url, Object data, Class<? extends T> cls) {
		throw new KlabUnimplementedException("POST file unimplemented!");
	}

}
