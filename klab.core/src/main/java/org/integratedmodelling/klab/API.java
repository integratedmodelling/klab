package org.integratedmodelling.klab;

/**
 * An attempt to systematize the API - probably overkill but I'll give it a try.
 * 
 * Each subset of the API is in a separate sub-interface;
 * Parameters in URLs are in the same sub-interface prefixed by P_;
 * Each constant ID with parameters contains the same names given after P_, in order, separated by underscores.
 * Comments on each constant must specify the protocols and the 
 * @author Ferd
 *
 */
public interface API {

	public interface RESOURCE {

		/*
		 * parameter: the URN being resolved
		 */
		public static final String P_URN = "{urn}";
		
		/**
		 * 
		 */
		public static final String GET_URN = "/resource/get/" + P_URN;

		/**
		 * 
		 */
		public static final String RESOLVE_URN = "/resource/resolve/" + P_URN;
	}

	public interface ENGINE {
	}
	
}
