package org.integratedmodelling.amp;

public interface API extends org.integratedmodelling.klab.api.API {

	public interface AMP {

		static final String AMP_BASE = PUBLIC.PUBLIC_BASE + "/amp";

		/**
		 * Post the JSON-LD annotation instance to start the ingestion process and
		 * obtain a ticket to track it.
		 */
		public static final String SUBMIT_ANNOTATION = AMP_BASE + "/submit";
	}

}
