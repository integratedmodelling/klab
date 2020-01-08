package org.integratedmodelling.klab.api.monitoring;

import org.integratedmodelling.klab.rest.DropData;
import org.integratedmodelling.klab.rest.DropPermission;
import org.integratedmodelling.klab.rest.DropRequest;
import org.integratedmodelling.klab.rest.GlobalActionRequest;
import org.integratedmodelling.klab.rest.RuntimeDocumentation;
import org.integratedmodelling.klab.rest.SettingChangeRequest;
import org.integratedmodelling.klab.rest.UserInputRequest;
import org.integratedmodelling.klab.rest.UserInputResponse;

/**
 * Messages exchanged between the engine and its web UI.
 * 
 * @author ferdinando.villa
 *
 */
public interface IMessage {

	public static IMessage NO_RESPONSE = null;

	/**
	 * Message class. Ugly type name makes life easier.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	enum MessageClass {

		/**
		 * Only for no-op defaults in the message handler annotation
		 */
		Void,

		/**
		 * Used within a UI for communicating things to react to and between F/B to
		 * gather user input.
		 */
		UserInterface,

		/**
		 * F->B when user selects context
		 */
		UserContextChange,

		/**
		 * B->F after UserContextChange was received, containing the remaining
		 * definition set by the engine
		 */
		UserContextDefinition,

		/**
		 * 
		 */
		EngineLifecycle,

		KimLifecycle,

		/**
		 * 
		 */
		ResourceLifecycle,

		/**
		 * 
		 */
		ProjectLifecycle,
		/**
		 * 
		 */
		Authorization,
		/**
		 * 
		 */
		TaskLifecycle,
		/**
		 * 
		 */
		ObservationLifecycle,
		/**
		 * 
		 */
		SessionLifecycle,
		/**
		 * 
		 */
		Notification,
		/**
		 * Search-class messages are sent by the front end to initiate or continue
		 * incremental knowledge searches.
		 */
		Search,
		/**
		 * Query messages are sent by the back end upon receiving Search-class messages.
		 */
		Query,

		/**
		 * Run-class messages start scripts and tests.
		 */
		Run,
	}

	/**
	 * Message type within its class.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	enum Type {

		/**
		 * Only used as a default for the MessageClass annotation.
		 */
		Void,

		/*
		 * UserContextChange-class types.
		 */
		/**
		 * F->B.
		 */
		RegionOfInterest, FeatureAdded,

		/**
		 * F->B
		 */
		PeriodOfInterest,

		/**
		 * B->F sent whenever a user message affecting the context is processed
		 */
		ScaleDefined,

		/**
		 * F<->B
		 */
		ResetContext,

		/**
		 * F->B whenever the user wants to (re)contextualize to either a URN specifying
		 * one or more contexts, a query (both may require users to choose one), or a
		 * specified observation ID. The last use case can happen with a parent context
		 * set, when users want to select a sub-context of the current, or without when
		 * the user wants to revert back to the original root context.
		 * 
		 * Class: ObservationLifecycle
		 */
		Recontextualize,

		/*
		 * Messages with class UserInterface, some local to the UI and not marshalled
		 * across websockets, others initiated on either side when user input is
		 * provided or requested.
		 */
		HistoryChanged, FocusChanged, Notification,

		/**
		 * B->F: notification for projects in user workspace when they are opened.UIs
		 * may not be aware of them and want to offer to import them. The backend does
		 * not modify or delete projects.
		 * <p>
		 * F->B: notification for projects in IDE workspace that are opened and the
		 * engine may not be aware of.
		 */
		UserProjectOpened, UserProjectModified, UserProjectDeleted,

		/**
		 * Class UserInterface: User input requests and responses: request is B->F,
		 * response is F->B. Use beans {@link UserInputRequest} and
		 * {@link UserInputResponse} respectively.
		 */
		UserInputRequested, UserInputProvided,

		/**
		 * Class UserInterface: B->F when a new documentation item becomes available for
		 * display at context level or at the dataflow actuator level. Uses bean
		 * {@link RuntimeDocumentation}.
		 */
		RuntimeDocumentation, DataflowDocumentation, TicketRequest, TicketResponse,

		/**
		 * Class UserInterface: request addition of action to either context menu or
		 * global menu. Use bean {@link GlobalActionRequest}.
		 */
		AddGlobalAction,

		/**
		 * Class UserInterface: handling of drop events in UI
		 * 
		 * {@link #DropInitiated}: F->B communicate content type, name and size (bean
		 * {@link DropRequest} {@link #DropPermission}: B->F accept/reject drop (bean
		 * {@link DropPermission} {@link #DropData}: F->B execute drop upload and
		 * communicate on finish (bean {@link DropData}
		 */
		DropInitiated, DropPermission, DropData,

		/**
		 * Class UserInterface: request change in setting communicating through bean
		 * {@link SettingChangeRequest}. F->B
		 */
		ChangeSetting,

		/*
		 * F->B: ask engine to modify or delete projects or project assets
		 */
		CreateNamespace, CreateScenario, DeleteNamespace, DeleteLocalResource, CreateProject, DeleteProject,
		CreateScript, DeleteScript, CreateTestCase, DeleteTestCase,
		
		/*
		 * F->B: publish or update a local or public resource
		 */
		PublishLocalResource, UpdatePublicResource, 
		
		/*
		 * B->F: respond to a request to publish a resource (just submit asynchronously).
		 */
		ResourceSubmitted,

		/**
		 * F->B: notification when files are explicitly changed, added or deleted;
		 * notify projects to load and respond to project lifecycle requests
		 */
		ProjectFileAdded, ProjectFileModified, ProjectFileDeleted, NotifyProjects, DocumentationModified,

		/*
		 * --- Notification-class types ---
		 */
		Debug, Info, Warning, Error,

		/*
		 * --- KimLifecycle: one-off compile notifications at the namespace or project
		 * level
		 */
		NamespaceCompilationIssues,

		/*
		 * --- Observation lifecycle ---
		 */
		/**
		 * Request the observation of a URN or logical expression. F->B. If the URN has
		 * resulted from a search, send the ID of the search so it can be disposed of.
		 */
		RequestObservation,

		/**
		 * A new observation is available. Back->Front.
		 */
		NewObservation,

		/**
		 * A previously reported observation had its contents modified. Back->Front.
		 */
		ModifiedObservation,

		/**
		 * F->B: user has selected an action among those supplied by the engine with
		 * each observation.
		 */
		ExecuteObservationAction,

		/**
		 * F->B Authorization class - inquiries about permitted operations and network
		 * status
		 */
		NetworkStatus,

		/**
		 * --- Task lifecycle --- B -> F
		 */
		ScriptStarted, TaskStarted, TaskFinished, TaskAborted, DataflowCompiled, DataflowStateChanged,

		/**
		 * Task lifecycle F -> B
		 */
		TaskInterrupted, DataflowNodeDetail, DataflowNodeRating,

		/**
		 * Scheduler lifecycle F->B
		 */
		SchedulingStarted, SchedulingFinished, SchedulerReset,

		/*
		 * --- Search-class types ---
		 */
		SubmitSearch, MatchAction,

		/*
		 * --- Query-class types ---
		 */
		QueryResult,

		/*
		 * --- EngineLifecycle ---
		 */
		EngineUp, EngineDown,

		/*
		 * --- Run-class types
		 */
		RunScript, RunTest, DebugScript, DebugTest,

		/*
		 * --- ResourceLifecycle-class types, F->B
		 */
		ImportResource, DeleteResource, UpdateResource, ValidateResource, PreviewResource, CopyResource, MoveResource,
		CreateResource, ImportIntoResource, ResourceOperation,

		/*
		 * --- ResourceLifecycle-class types, B->F
		 */
		ResourceImported, ResourceDeleted, ResourceUpdated, ResourceValidated, ResourceCreated
	}

	/**
	 * Unique ID for each message.
	 * 
	 * @return
	 */
	String getId();

	/**
	 * The message exposes the identity that created it through a token, which may
	 * or may not be parseable at the receiving end but will be consistently linked
	 * to the message type. For example, task messages will have the identity of the
	 * task that generated them so they can be correctly distributed among tasks.
	 * 
	 * @return the sender's identity. Never null.
	 */
	String getIdentity();

	/**
	 * 
	 * @return the message class
	 */
	MessageClass getMessageClass();

	/**
	 * 
	 * @return the message type
	 */
	Type getType();

	/**
	 * Timestamp (milliseconds since epoch) of message at sender side. Meant to
	 * enforce sequentiality rather than reliable date/time attribution.
	 * 
	 * @return
	 */
	long getTimestamp();

	/**
	 * Get the payload of the message, whatever it is.
	 * 
	 * @return the payload or null.
	 */
	Object getPayload();

	/**
	 * Get the payload of the message, ensuring it is of type T.
	 * 
	 * @param cls
	 * @return the payload
	 */
	<T> T getPayload(Class<? extends T> cls);
}
