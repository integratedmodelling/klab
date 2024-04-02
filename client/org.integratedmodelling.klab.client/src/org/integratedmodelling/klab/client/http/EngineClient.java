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
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.AuthorizeSessionResponse;
import org.integratedmodelling.klab.rest.PingResponse;
import org.integratedmodelling.klab.utils.Escape;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import org.springframework.util.LinkedMultiValueMap;
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
 * with(..) can be called with any of IUser, INode, ITask, IContext and ISession
 * according to the type of authentication required by the receiver.
 * Automatically sets the necessary tokens and parameters.
 *
 * The template also configures an objectmapper for optimal use in k.LAB,
 * manages errors and inserts user agent headers.
 *
 * 
 * @author ferdinando.villa
 *
 */
public class EngineClient extends RestTemplate {

	public static final String ENGINE_DEFAULT_URL = "http://127.0.0.1:8283/modeler";

	public static final String KLAB_VERSION_HEADER = "KlabVersion";
	public static final String KLAB_CONNECTION_TIMEOUT = "klab.connection.timeout";

	ObjectMapper objectMapper;
	String authToken;
	String url;
	IMonitor monitor;
	String mediaType = "application/json";

	private static ClientHttpRequestFactory factory;

	public static EngineClient create(String url) {

		if (factory == null) {
			factory = new HttpComponentsClientHttpRequestFactory();
		}

		return new EngineClient(factory, url);
	}

	public String getUrl() {
		return url;
	}

	/**
	 * Quickly check the engine's heartbeat.
	 * 
	 * @return milliseconds since boot if alive, -1 otherwise
	 */
	public long ping() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "text/plain");
			ResponseEntity<PingResponse> response = exchange(url + API.PING, HttpMethod.GET,
					new HttpEntity<PingResponse>(headers), PingResponse.class);
			return response.getStatusCodeValue() == 200 ? response.getBody().getUptime() : -1;
		} catch (Throwable e) {
			return -1;
		}
	}

	/**
	 * Open a session with the engine.
	 * 
	 * @param rejoinSession
	 * @param relayId       the ID of a relay object to receive Explorer messages
	 * @return a new session ID (possibly same as passed one to rejoin, meaning the
	 *         rejoin was successful)
	 */
	public String openSession(@Nullable String rejoinSession, @Nullable String relayId) {

		AuthorizeSessionResponse response = get(
				API.ENGINE.SESSION.AUTHORIZE + (rejoinSession == null ? "" : ("?join=" + rejoinSession))
						+ (relayId == null ? "" : ((rejoinSession == null ? "?relay=" : "&relay=") + relayId)),
				AuthorizeSessionResponse.class);

		if (monitor != null) {
			if (response.getInfo() != null) {
				monitor.warn(response.getInfo());
			} else if (rejoinSession != null) {
				// TODO send info about session rejoined.
			}
		}

		return response.getSessionId();
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
			headers.set(HttpHeaders.ACCEPT, "application/json");
			// headers.set("X-User-Agent", "k.LAB " + Version.CURRENT);
			// headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
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
		jsonMessageConverter.setObjectMapper(objectMapper);

		setErrorHandler(new JSONResponseErrorHandler());
		messageConverters.add(jsonMessageConverter);
		messageConverters.add(utf8);
		messageConverters.add(formHttpMessageConverter);
		// messageConverters.add(byteConverter);
		setMessageConverters(messageConverters);

		this.setInterceptors(Collections.singletonList(new AuthorizationInterceptor()));
	}

	private EngineClient(ClientHttpRequestFactory factory, String url) {
		super(factory);
		this.url = url;
		setup();
	}

	private EngineClient() {
		super(factory);
		this.url = ENGINE_DEFAULT_URL;
	}

	/**
	 * Return a client with authorization set to the passed object.
	 * 
	 * @param authorization any identity.
	 * @return a new
	 */
	public EngineClient with(String authorization) {

		/*
		 * TODO handle the chain of authorization properly. Tokens are only issued for
		 * engines, network sessions and sessions. The rest should set parameters for
		 * calls.
		 */

		EngineClient ret = new EngineClient();
		ret.objectMapper = this.objectMapper;
		ret.authToken = authorization;
		ret.url = url;
		return ret;
	}

	/**
	 * Return a client with every other argument that will also substitute the
	 * Accept: header in regular POST and GET calls with the passed media type
	 * instead of application/json
	 * 
	 * @param authorization any identity.
	 * @return a new
	 */
	public EngineClient accept(String mediaType) {

		EngineClient ret = new EngineClient();
		ret.objectMapper = this.objectMapper;
		ret.authToken = this.authToken;
		ret.url = url;
		ret.mediaType = mediaType;
		return ret;
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends Object> T post(String endpoint, Object data, Class<? extends T> cls) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", mediaType);
		// headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}

		HttpEntity<Object> entity = new HttpEntity<>(data, headers);

		try {

			ResponseEntity<Map> response = exchange(url + endpoint, HttpMethod.POST, entity, Map.class);

			switch (response.getStatusCodeValue()) {
			case 302:
			case 403:
				throw new KlabAuthorizationException("unauthorized request " + url + endpoint);
			case 404:
				throw new IllegalStateException("internal: request " + url + endpoint + " was not accepted");
			}

			if (response.getBody() == null) {
				return null;
			}
			if (response.getBody().containsKey("exception") && response.getBody().get("exception") != null) {
				Object exception = response.getBody().get("exception");
				// Object path = response.getBody().get("path");
				Object message = response.getBody().get("message");
				// Object error = response.getBody().get("error");
				throw new RuntimeException("remote exception: " + (message == null ? exception : message));
			}

			return objectMapper.convertValue(response.getBody(), cls);

		} catch (RestClientException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean download(String endpoint, File output) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		// headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.WWW_AUTHENTICATE, authToken);
		}

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		// HttpHeaders headers = new HttpHeaders();
		// headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		// HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(url + endpoint, HttpMethod.GET, entity, byte[].class);

		switch (response.getStatusCodeValue()) {
		case 302:
		case 403:
			throw new KlabAuthorizationException("unauthorized request " + url + endpoint);
		case 404:
		case 406:
			throw new IllegalStateException("internal: request " + url + endpoint + " was not accepted");
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
	 * Upload contents from a URL/file and return the response after upload is
	 * complete. Blocking, so meant for small payloads or use in a thread.
	 * 
	 * @param url
	 * @param method       POST or PUT
	 * @param contents
	 * @param responseType
	 * @return
	 */
	public <T> T upload(String endpoint, File contents, Class<T> responseType) {

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("file", new FileSystemResource(contents));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
				map, headers);
		ResponseEntity<T> result = exchange(url + endpoint, HttpMethod.POST, requestEntity, responseType);
		return result.getBody();
	}

	/**
	 * Upload contents from a URL/file expecting a void response. Still blocking.
	 *
	 * @param endpoint
	 * @param method
	 * @param contents
	 */
	public boolean upload(String endpoint, File contents) {

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("file", new FileSystemResource(contents));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
				map, headers);
		try {
			exchange(url + endpoint, HttpMethod.POST, requestEntity, Object.class);
			return true;
		} catch (Throwable e) {
			//
		}
		return false;
	}

	/**
	 * Upload contents from a URL/file with asynchronous treatment of the upload.
	 * Will periodically recheck the upload status, invoking the passed consumer
	 * with the response after the upload is complete. Meant to work with services
	 * that return 201 and 203 and task endpoints. Should be used with compliant
	 * remote endpoints when the payload is not small.
	 * 
	 * @param url
	 * @param contents
	 * @param responseType
	 * @param responseConsumer
	 * @param errorConsumer
	 * @return
	 */
	public <T> void uploadAsynchronous(String endpoint, HttpMethod method, File contents, Class<T> responseType,
			Consumer<T> responseConsumer, @Nullable Consumer<String> errorConsumer) {
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("file", new FileSystemResource(contents));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
				map, headers);
		ResponseEntity<T> response = exchange(url + endpoint, method, requestEntity, responseType);
		switch (response.getStatusCodeValue()) {
		case 200:
			responseConsumer.accept(response.getBody());
			break;
		case 201:
			break;
		case 203:
			break;
		case 302:
		case 403:
			throw new KlabAuthorizationException("unauthorized request " + url + endpoint);
		case 404:
			throw new IllegalStateException("internal: request " + url + endpoint + " was not accepted");
		}
	}

	/**
	 * Instrumented for header communication and error parsing
	 * 
	 * @param url
	 * @param cls
	 * @return the deserialized result
	 */
	public <T> T get(String endpoint, Class<T> cls) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", mediaType);
		headers.set("User-Agent", "k.LAB/" + Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<T> response = exchange(url + endpoint, HttpMethod.GET, entity, cls);

		switch (response.getStatusCodeValue()) {
		case 302:
		case 403:
			throw new KlabAuthorizationException("unauthorized request " + url + endpoint);
		case 404:
			throw new RuntimeException("internal: request " + url + endpoint + " was not accepted");
		}

		if (response.getBody() == null) {
			return null;
		}

		if (response.getBody() instanceof Map) {

			Map<?, ?> map = (Map<?, ?>) response.getBody();

			if (map.containsKey("exception") && map.get("exception") != null) {
				Object exception = map.get("exception");
				// Object path = response.getBody().get("path");
				Object message = map.get("message");
				// Object error = response.getBody().get("error");
				throw new RuntimeException("remote exception: " + (message == null ? exception : message));
			}
			return objectMapper.convertValue(response.getBody(), cls);
		}

		return response.getBody();
	}

	/**
	 * Returns an inputstream directly, for byte resources.
	 * 
	 * @param url
	 * @param cls
	 * @return the deserialized result
	 */
	public InputStream get(String endpoint) {

		HttpHeaders headers = new HttpHeaders();
//		headers.set("Accept", "application/json");
		// headers.set(KLAB_VERSION_HEADER, Version.CURRENT);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, authToken);
		}
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<Resource> response = exchange(url + endpoint, HttpMethod.GET, entity, Resource.class);

		switch (response.getStatusCodeValue()) {
		case 302:
		case 403:
			throw new KlabAuthorizationException("unauthorized request " + url + endpoint);
		case 404:
			throw new RuntimeException("internal: request " + url + endpoint + " was not accepted");
		}

		if (response.getBody() == null) {
			return null;
		}

		try {
			return response.getBody().getInputStream();
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	/**
	 * Instrumented for header communication and error parsing
	 * 
	 * @param url
	 * @param cls
	 * @param urlVariables
	 * @return the deserialized result
	 */
	<T> T get(String endpoint, Class<T> cls, Map<String, ?> urlVariables) {
		return get(addParameters(endpoint, urlVariables), cls);
	}

	/**
	 * Create a GET URL from a base url and a set of parameters. Yes, I know I can
	 * use URIComponentsBuilder etc.
	 * 
	 * @param url
	 * @param parameters
	 * @return the finished url
	 */
	public static String addParameters(String endpoint, Map<String, ?> parameters) {
		String ret = endpoint;
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				if (ret.length() == endpoint.length()) {
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
