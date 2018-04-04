package org.integratedmodelling.klab;

/**
 * An attempt to systematize the API - probably overkill but I'll give it a try.
 * 
 * TODO to be restructured
 * 
 * @author ferdinando.villa
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
