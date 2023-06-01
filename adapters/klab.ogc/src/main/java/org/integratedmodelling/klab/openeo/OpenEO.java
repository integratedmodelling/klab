package org.integratedmodelling.klab.openeo;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.auth.Authorization;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabRemoteException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

/**
 * Helper methods for OpenEO communication.
 * 
 * @author Ferd
 *
 */
public class OpenEO {

	private long POLLING_INTERVAL_MS = 1000;

	private Authorization authorization;
	private String endpoint;
	String plan; // TODO expose, link to /me
	int budget; // TODO expose, update

	class Job {
		String jobId;
		Consumer<Map<String, Object>> resultConsumer;
		BiConsumer<String, String> errorConsumer;
		IMonitor monitor;
	}

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private Set<Job> jobs = Collections.synchronizedSet(new LinkedHashSet<>());

	public static class ProcessNode {

		private String process_id;
		private String namespace;
		private String description;
		private boolean result;
		private Map<String, Object> arguments;

		public String getProcess_id() {
			return process_id;
		}

		public void setProcess_id(String process_id) {
			this.process_id = process_id;
		}

		public String getNamespace() {
			return namespace;
		}

		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public Map<String, Object> getArguments() {
			return arguments;
		}

		public void setArguments(Map<String, Object> arguments) {
			this.arguments = arguments;
		}
	}

	public static class Schema {

		private String type = "number";
		private String subtype;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getSubtype() {
			return subtype;
		}

		public void setSubtype(String subtype) {
			this.subtype = subtype;
		}

	}

	public static class Parameter {

		private String name;
		private String description;
		private Object schema;
		private boolean optional;
		private boolean deprecated;
		private boolean experimental;
		private boolean isDefault;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Object getSchema() {
			return schema;
		}

		public void setSchema(Object schema) {
			this.schema = schema;
		}

		public boolean isOptional() {
			return optional;
		}

		public void setOptional(boolean optional) {
			this.optional = optional;
		}

		public boolean isDeprecated() {
			return deprecated;
		}

		public void setDeprecated(boolean deprecated) {
			this.deprecated = deprecated;
		}

		public boolean isExperimental() {
			return experimental;
		}

		public void setExperimental(boolean experimental) {
			this.experimental = experimental;
		}

		@JsonProperty("default")
		public boolean isDefault() {
			return isDefault;
		}

		public void setDefault(boolean isDefault) {
			this.isDefault = isDefault;
		}

		public List<Schema> schemata() {
			List<Schema> ret = new ArrayList<>();
			if (schema instanceof Map) {
				ret.add(JsonUtils.convertMap((Map<?, ?>) schema, Schema.class));
			} else if (schema instanceof Collection) {
				for (Object s : (Collection<?>) schema) {
					ret.add(JsonUtils.convertMap((Map<?, ?>) s, Schema.class));
				}
			}
			return ret;
		}

	}

	public static class ReturnValue {

		private String description;
		private Schema schema;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Schema getSchema() {
			return schema;
		}

		public void setSchema(Schema schema) {
			this.schema = schema;
		}

	}

	public static class Link {

		private String rel;
		private String href;
		private String type;
		private String title;

		public String getRel() {
			return rel;
		}

		public void setRel(String rel) {
			this.rel = rel;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

	/*
	 * A user defined OpenEO process. Can be used as a "namespace" when running a
	 * job.
	 */
	public static class Process {

		private String id;
		private String summary;
		private String description;
		private List<Parameter> parameters = new ArrayList<>();
		private Map<String, ProcessNode> process_graph = new LinkedHashMap<>();
		private ReturnValue returns;
		private List<Link> links = new ArrayList<>();

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<Parameter> getParameters() {
			return parameters;
		}

		public void setParameters(List<Parameter> parameters) {
			this.parameters = parameters;
		}

		public Map<String, ProcessNode> getProcess_graph() {
			return process_graph;
		}

		public void setProcess_graph(Map<String, ProcessNode> process_graph) {
			this.process_graph = process_graph;
		}

		public ReturnValue getReturns() {
			return returns;
		}

		public void setReturns(ReturnValue returns) {
			this.returns = returns;
		}

		public List<Link> getLinks() {
			return links;
		}

		public void setLinks(List<Link> links) {
			this.links = links;
		}

		/**
		 * Set the "self" link into the process with the passed URL, substituting the
		 * URL if already existing
		 * 
		 * @param string
		 */
		public void encodeSelf(String url) {
			boolean done = false;
			for (Link link : links) {
				if ("self".equals(link.getRel())) {
					link.setHref(url);
					done = true;
					break;
				}
			}
			if (!done) {
				Link link = new Link();
				link.setHref(url);
				link.setRel("self");
				link.setTitle("Network location of process");
				links.add(link);
			}
		}

	}

	public OpenEO(String endpoint) {
		this.endpoint = endpoint;
		ExternalAuthenticationCredentials credentials = Authentication.INSTANCE.getCredentials(endpoint);
		if (credentials != null) {
			this.authorization = new Authorization(credentials);
		}
		this.executor.scheduleAtFixedRate(() -> {
			Set<Job> finished = new HashSet<>();
			for (Job job : jobs) {
				if (checkFinish(job)) {
					finished.add(job);
				}
			}
			jobs.removeAll(finished);
		}, 0, POLLING_INTERVAL_MS, TimeUnit.MILLISECONDS);
	}

	/**
	 * Read a UDP from a network-available JSON specification. Will insert the URL
	 * into the "self" link of the spec, so that the process can be validated at
	 * client side but the link remains available for use at server side.
	 * 
	 * @param url
	 * @return
	 */
	public static Process readUdp(String url) {
		try {

			Process ret = JsonUtils.load(new URL(url), Process.class);

			Link self = new Link();
			self.setRel("self");
			self.setHref(url);
			ret.links.add(self);

			return ret;

		} catch (KlabIOException | MalformedURLException e) {
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private boolean checkFinish(Job job) {

		System.out.println("CHECKING JOB " + job.jobId);

		Map<String, Object> metadata = getJobMetadata(job.jobId);
		if (metadata.containsKey("status")) {

			System.out.println("   STATUS: " + metadata.get("status"));

			switch (metadata.get("status").toString()) {
			case "created":
			case "queued":
			case "running":
				return false;
			case "canceled":
				job.resultConsumer.accept(Collections.emptyMap());
				return true;
			case "finished":
				Map<String, Object> results = getJobResults(job);
				job.resultConsumer.accept((Map<String, Object>) results.get("assets"));
				return true;
			case "error":
				for (Notification notification : getJobLogs(job)) {
					if ("error".equals(notification.getLevel())) {
						job.monitor.error(notification.getMessage());
						job.errorConsumer.accept("ServerError", notification.getMessage());
					} else if ("warning".equals(notification.getLevel())) {
						job.monitor.warn(notification.getMessage());
					}
				}
				return true;
			}
		}

		job.errorConsumer.accept("InternalError", "Job " + job.jobId + " terminated atypically");
		return true;
	}

	/**
	 * Retrieve the results of a job, which must be finished (the metadata returned
	 * by {@link #getJobMetadata(String)} must contain "status" with values
	 * "canceled", "error" or "finished").
	 */
	public Map<String, Object> getJobResults(Job job) {

		HttpResponse<JsonNode> response = Unirest.get(endpoint + "/jobs/" + job.jobId + "/results")
				.header("Authorization", authorization.getAuthorization()).asJson();

		if (response.isSuccess()) {
			for (String cost : response.getHeaders().get("OpenEO-Costs")) {
				// TODO adjust budget if so configured
				job.monitor.info("job " + job.jobId + " costs " + cost + " to download");
			}
			return response.getBody().getObject().toMap();
		}

		return Collections.emptyMap();

	}

	/**
	 * Create a job and return its ID. If a non-null string is returned, it can be
	 * passed to other job-related functions. The job is NOT started, just created
	 * on the back end.
	 * 
	 * @param process
	 * @param title
	 * @param description
	 * @param plan
	 * @param budget
	 * @return
	 */
	public String createRemoteJob(String processId, Parameters<String> parameters, Process... processes) {

		Map<String, Object> request = new LinkedHashMap<>();

		request.put("process", createJobDefinition(processId, parameters, processes));
		request.put("plan", plan);
		request.put("budget", budget <= 0 ? null : budget);

		System.out.println(JsonUtils.printAsJson(request));

		HttpResponse<String> response = Unirest.post(endpoint + "/jobs").contentType("application/json")
				.header("Authorization", authorization.getAuthorization()).body(request).asString();

		if (response.isSuccess() && response.getStatus() == 201) {
			for (String header : response.getHeaders().get("OpenEO-Identifier")) {
				return header;
			}
		}

		return null;
	}

	/**
	 * <em>Synchronous</em> job executor that blocks until the job is finished. Only
	 * to be used with jobs that are known to be small. Returns the results as a
	 * POD, which will need to be interpreted at the user end. The result may
	 * contain errors from the OpenEO service. Throws an exception only in case of
	 * remote server error.
	 * 
	 * @param process   the process to be sent
	 * @param namespace any processes that need to be added to the namespace. For
	 *                  each process, if there is a link with ref == "self", the URL
	 *                  is used in the namespace. Otherwise the process is stored at
	 *                  server side before the run and removed afterwards.
	 */
	@SuppressWarnings("unchecked")
	public <T> T runJob(String processId, Parameters<String> parameters, IMonitor monitor, Class<T> resultClass,
			Process... namespace) {

		Map<String, Object> request = new LinkedHashMap<>();

		request.put("process", createJobDefinition(processId, parameters, namespace));
		request.put("plan", plan);
		request.put("budget", budget <= 0 ? null : budget);

		HttpResponse<String> response = Unirest.post(endpoint + "/result").contentType("application/json")
				.header("Authorization", authorization.getAuthorization()).body(request).asString();

		if (response.isSuccess()) {
			return (T) Utils.convertValue(response.getBody().trim(), Utils.getArtifactType(resultClass));
		}

		throw new KlabRemoteException("OpenEO runJob returned error code " + response.getStatus());
	}

	/**
	 * <em>Synchronous</em> job executor that blocks until the job is finished. Only
	 * to be used with jobs that are known to be small. Passes the result
	 * inputstream to a consumer. Throws an exception only in case of remote server
	 * error.
	 * 
	 * @param process   the process to be sent
	 * @param budget    put 0 if no budget should be sent
	 * @param plan      null is accepted
	 * @param namespace any processes that need to be added to the namespace. For
	 *                  each process, if there is a link with ref == "self", the URL
	 *                  is used in the namespace. Otherwise the process is stored at
	 *                  server side before the run and removed afterwards.
	 */
	public void runJob(String processId, Parameters<String> parameters, IMonitor monitor,
			Consumer<InputStream> resultConsumer, Process... namespace) {

		Map<String, Object> request = new LinkedHashMap<>();

		request.put("process", createJobDefinition(processId, parameters, namespace));
		request.put("plan", plan);
		request.put("budget", budget <= 0 ? null : budget);

		Unirest.post(endpoint + "/result").contentType("application/json")
				.header("Authorization", authorization.getAuthorization()).body(request).thenConsume((rawr) -> {
					boolean error = false;
					if (rawr.getStatus() - 400 >= 0) {
						monitor.error(new KlabRemoteException("Server returned error code " + rawr.getStatus()));
					} else if (rawr.getHeaders().get("Link") != null) {
						/*
						 * TODO get logs and check for errors; if error, call monitor and throw
						 * exception
						 */
						for (String link : rawr.getHeaders().get("Link")) {
							System.out.println("DIO TAFANO: " + link);
						}
					}
					resultConsumer.accept(rawr.getContent());
				});

	}

	private String getNamespaceUrl(Process udp) {
		for (Link link : udp.getLinks()) {
			if ("self".equals(link.getRel())) {
				return link.getHref();
			}
		}
		return null;
	}

	/**
	 * Create the definition of the job named processId with the passed parameters.
	 * If process UDP objects are added, they must be merged and provided as
	 * namespace for the job, according to configuration (as a public URL or stored
	 * at server side before execution).
	 * 
	 * @param processId
	 * @param parameters
	 * @param namespace  any processes that need to be added to the namespace. For
	 *                   each process, if there is a link with ref = "self", the URL
	 *                   is used in the namespace. Otherwise the process is stored
	 *                   at server side before the run and removed afterwards.
	 * @return
	 */
	private Map<String, Object> createJobDefinition(String processId, Parameters<String> parameters,
			Process... namespace) {

		Map<String, Object> processGraph = new LinkedHashMap<>();
		Map<String, Object> processCall = new LinkedHashMap<>();

		processCall.put("process_id", processId);
		processCall.put("arguments", parameters == null ? new HashMap<>() : parameters.getData());
		processCall.put("result", true);

		if (namespace != null) {
			for (Process udp : namespace) {
				String namespaceUrl = getNamespaceUrl(udp);
				if (namespaceUrl != null) {
					// substitute any previous one. The schema only accepts one URL.
					// TODO clarify the schema and if needed, throw exception if there's 2+
					// namespace URLs in the args.
					processCall.put("namespace", namespaceUrl);
				} else {
					// TODO must "publish" the process(es) into the OpenEO engine to make them
					// available in namespace backend
				}
			}
		}

		processGraph.put("p" + NameGenerator.shortUUID(), processCall);

		Map<String, Object> ret = new LinkedHashMap<>();

		ret.put("id", "p" + NameGenerator.shortUUID());
		ret.put("description", "Generated by k.LAB on " + new Date());
		ret.put("process_graph", processGraph);

		System.out.println(JsonUtils.printAsJson(ret));

		return ret;
	}

	/**
	 * <em>Asynchronous</em> job executor. Must be passed a valid job ID from a
	 * previous call to {@link #createJob(Process)}, which guarantees validation.
	 * Returns immediately and calls the error handler or the result handler
	 * depending on the outcome when the process has finished. Polls the server for
	 * status at the established polling interval.
	 * 
	 * @param jobId
	 * @param resultHandler
	 * @param errorHandler
	 */
	public void startJob(String jobId, IMonitor monitor, Consumer<Map<String, Object>> resultHandler,
			BiConsumer<String, String> errorHandler) {

		HttpResponse<?> response = Unirest.post(endpoint + "/jobs/" + jobId + "/results")
				.header("Authorization", authorization.getAuthorization()).asEmpty();

		if (response.isSuccess() && response.getStatus() == 202) {

			System.out.println("STARTED JOB: " + jobId);

			// create the job at server side
			Job job = new Job();

			job.monitor = monitor;
			job.jobId = jobId;
			job.errorConsumer = errorHandler;
			job.resultConsumer = resultHandler;

			// insert in jobs set for polling thread to process
			jobs.add(job);

		} else {
			errorHandler.accept("ServerError", "Job " + jobId + ": submission unsuccessful");
		}
	}

	/**
	 * Validate a process graph and submit it if valid, passing control to
	 * callbacks. Return false if process validation fails. Validation is on the
	 * back-end and synchronous, execution is asynchronous.
	 * 
	 * @param processId
	 * @param parameters
	 * @param resultHandler
	 * @param errorHandler
	 * @param namespace     any processes that need to be added to the namespace.
	 *                      For each process, if there is a link with ref = "self",
	 *                      the URL is used in the namespace. Otherwise the process
	 *                      is stored at server side before the run and removed
	 *                      afterwards.
	 * @return
	 */
	public boolean submit(String processId, Parameters<String> parameters, IMonitor monitor,
			Consumer<Map<String, Object>> resultHandler, BiConsumer<String, String> errorHandler,
			Process... namespace) {

		String jobId = createRemoteJob(processId, parameters, namespace);
		if (jobId == null) {
			errorHandler.accept("InternalError", "Process " + processId + " was rejected by server");
			return false;
		}
		startJob(jobId, monitor, resultHandler, errorHandler);
		return true;
	}

	/**
	 * Asynchronous submission returning a future and throwing remote exceptions in
	 * case of error.
	 * 
	 * @param processId
	 * @param parameters
	 * @param namespace
	 * @return
	 */
	public OpenEOFuture submit(String processId, Parameters<String> parameters, IMonitor monitor,
			Process... namespace) {
		return new OpenEOFuture(processId, parameters, monitor, namespace);
	}

	public boolean validateProcess(Process process, Parameters<String> parameters,
			BiConsumer<String, String> errorHandler) {

		HttpResponse<JsonNode> response = Unirest.post(endpoint + "/validation").contentType("application/json")
				.header("Authorization", authorization.getAuthorization()).body(process).asJson();

		if (response.isSuccess()) {
			if (response.getBody().getObject().has("errors")) {
				JSONArray errors = response.getBody().getObject().getJSONArray("errors");
				if (errorHandler != null) {
					for (Object error : errors) {
						errorHandler.accept(((JSONObject) error).getString("code"),
								((JSONObject) error).getString("message"));
					}
				}
				return errors.length() == 0;
			}
		}

		return false;
	}

	public boolean deleteJob(String jobId) {
		HttpResponse<?> response = Unirest.delete(endpoint + "/jobs/" + jobId)
				.header("Authorization", authorization.getAuthorization()).asEmpty();

		if (response.isSuccess()) {
			return response.getStatus() == 204;
		}

		return false;
	}

	public boolean cancelJob(String jobId) {

		HttpResponse<?> response = Unirest.delete(endpoint + "/jobs/" + jobId + "/results")
				.header("Authorization", authorization.getAuthorization()).asEmpty();

		if (response.isSuccess()) {
			return response.getStatus() == 204;
		}

		return false;
	}

	/**
	 * Get logs from a job in the form of k.LAB Notification objects. Error levels
	 * are "error", "info", "warning" and "debug".
	 * 
	 * @param job
	 * @return
	 */
	public Collection<Notification> getJobLogs(Job job) {

		List<Notification> ret = new ArrayList<>();
		HttpResponse<JsonNode> logs = Unirest.get(endpoint + "/jobs/" + job.jobId + "/logs")
				.header("Authorization", authorization.getAuthorization()).asJson();
		if (logs.isSuccess()) {
			JSONObject map = logs.getBody().getObject();
			if (map.has("logs")) {
				for (Object l : map.getJSONArray("logs")) {
					Map<?, ?> log = ((JSONObject) l).toMap();
					if ("debug".equals(log.get("level")) || "info".equals(log.get("level"))) {
						continue;
					}
					if (log.containsKey("message") && log.containsKey("level")) {
						ret.add(new Notification(
								(log.containsKey("code") ? (log.get("code") + ": ") : "") + log.get("message"),
								log.get("level").toString()));
					}
				}
			}
		}
		return ret;
	}

	public Map<String, Object> getJobMetadata(String jobId) {

		HttpResponse<JsonNode> response = Unirest.get(endpoint + "/jobs/" + jobId)
				.header("Authorization", authorization.getAuthorization()).asJson();

		if (response.isSuccess()) {

			System.out.println("  metadata: status = " + response.getBody().getObject().getString("status"));

			return response.getBody().getObject().toMap();
		}

		return Collections.emptyMap();
	}

	public boolean isOnline() {
		return authorization != null && authorization.isOnline();
	}

	/**
	 * Budget should be automatically maintained (TODO).
	 * 
	 * @return
	 */
	public int getBudget() {
		return this.budget;
	}

	public class OpenEOFuture implements Future<Map<String, Object>> {

		String processId;
		AtomicReference<Map<String, Object>> result = new AtomicReference<>(null);
		AtomicBoolean done = new AtomicBoolean(false);
		AtomicBoolean canceled = new AtomicBoolean(false);
		String error;

		public OpenEOFuture(String processId, Parameters<String> parameters, IMonitor monitor, Process... namespace) {
			this.processId = processId;
			String jobId = createRemoteJob(processId, parameters, namespace);
			if (jobId == null) {
				done.set(true);
				error = "Server could not create job";
			} else {

				startJob(jobId, monitor, (result) -> {
					OpenEOFuture.this.result.set(result);
					done.set(true);
				}, (code, error) -> {
					// as implemented the error message contains the server-side error code already
					OpenEOFuture.this.error = error;
					done.set(true);
				});
			}
		}

		/**
		 * Call only after job is done
		 * 
		 * @return
		 */
		public String getError() {
			return error;
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			canceled.set(true);
			if (mayInterruptIfRunning) {
				return cancelJob(processId);
			}
			return false;
		}

		@Override
		public boolean isCancelled() {
			return canceled.get();
		}

		@Override
		public boolean isDone() {
			return done.get();
		}

		@Override
		public Map<String, Object> get() throws InterruptedException, ExecutionException {
			while (!done.get()) {
				if (result.get() != null) {
					break;
				}
				Thread.sleep(POLLING_INTERVAL_MS);
			}
			return result.get();
		}

		@Override
		public Map<String, Object> get(long timeout, TimeUnit unit)
				throws InterruptedException, ExecutionException, TimeoutException {
			long time = 0;
			long tout = unit.convert(timeout, TimeUnit.MILLISECONDS);
			while (!done.get()) {
				if (time > tout) {
					throw new TimeoutException("OpenEO: timeout reached on job " + processId);
				}
				if (result.get() != null) {
					break;
				}
				Thread.sleep(POLLING_INTERVAL_MS);
				time += POLLING_INTERVAL_MS;

			}
			return result.get();
		}

	}

}