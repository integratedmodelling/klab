package org.integratedmodelling.klab.api.auth;

/**
 * Any identity known to the IM semantic web. Since 0.9.11 identities are arranged in
 * a parent/child hierarchy through exposing their parent token, which is only null
 * in top-level identities, i.e. IServer. Identity objects are passed to the
 * API in lieu of their raw tokens, to give quick access to the identity's metadata
 * and their lineage.
 * 
 * Identities also correspond to roles for Spring security in the kmodeler and klab-community
 * projects.
 * 
 * Identities for now have the following parent/child relationships:
 * 
 * <pre>
 * 	IIdentity (abstract)
 * 		IPartner [top-level: each server is owned by a partner]
 * 			INode [physically a server on the k.LAB network; access point for IUser] 
 * 				IUser (authenticated by IServer, directly or indirectly)
 * 					INetworkSession
 *              		IEngine (has a IUser (automatically promoted to IEngineUser) but can authenticate others as IEngineUser)
 *              			IEngineUser
 * 								ISession
 * 				     				IContext
 * 										ITask
 * </pre>
 * 
 * @author Ferd
 *
 */
public abstract interface IIdentity {
	
	static enum Type {
		/**
		 * Identified by an institutional certificate. Each server has a top-level partner.
		 */
		IM_PARTNER,
		
		/**
		 * Identified by a node token, owned by a partner.
		 */
        NODE,
        
        /**
         * Identified by a user token authenticated by a server.
         */
		IM_USER,
		
		/**
		 * Identified by a network session token owned by a server user.
		 */
		NETWORK_SESSION,
		
		/**
		 * Identified by an engine token using a user certificate
		 */
        ENGINE,
        
        /**
         * Identified by an engine user token released by an engine after
         * authentication. Default engine user is the server user who
         * owns the engine.
         */
        ENGINE_USER,
        
        /**
         * Identified by a session token owned by an engine user.
         */
		MODEL_SESSION,
		
		/**
		 * Identified by a context token owned by a session.
		 */
		CONTEXT,
		
		/**
		 * Identifed by a task token owned by a context.
		 */
		TASK
	}
	
	Type TYPE = null; 
		
	/**
	 * Authorization token retrieved upon authentication. Assumed to expire at
	 * some sensible point in time, if stored it should be validated before use
	 * and refreshed if necessary.
	 * 
	 * @return a token to use as authentication when dealing with the engine.
	 */
	String getSecurityKey();
	
	/**
	 * Return the parent identity. Null only in IM_PARTNER identities.
	 * 
	 * @return
	 */
	IIdentity getParentIdentity();
	
	/**
	 * Get the parent identity of the passed type.
	 * 
	 * @return the desired identity or null.
	 */
	<T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type);
}
