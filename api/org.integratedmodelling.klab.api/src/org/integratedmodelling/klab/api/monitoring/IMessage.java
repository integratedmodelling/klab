package org.integratedmodelling.klab.api.monitoring;

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
		 * 
		 */
		UserContextChange,
		/**
		 * 
		 */
		EngineLifecycle,

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
		RegionOfInterest,

		/**
		 * F->B.
		 */
		PeriodOfInterest,

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
		 * F->B: notification when files are changed, added or deleted
		 */
		ProjectFileAdded, ProjectFileModified, ProjectFileDeleted,

		/*
		 * --- Notification-class types ---
		 */
		Debug, Info, Warning, Error,

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

		/*
		 * --- Task lifecycle ---
		 */
		ScriptStarted, TaskStarted, TaskFinished, TaskAborted, DataflowCompiled,

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
		ImportResource, DeleteResource, UpdateResource, ValidateResource, PreviewResource,

		/*
		 * --- ResourceLifecycle-class types, B->F
		 */
		ResourceImported, ResourceDeleted, ResourceUpdated, ResourceValidated
	}

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
	 * Get the payload of the message, ensuring it is of type T.
	 * 
	 * @param cls
	 * @return the payload
	 */
	<T> T getPayload(Class<? extends T> cls);
}
