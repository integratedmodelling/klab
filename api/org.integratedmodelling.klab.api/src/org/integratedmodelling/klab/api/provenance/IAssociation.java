package org.integratedmodelling.klab.api.provenance;

/**
 * Any link in the provenance graph. Has a type and a timestamp so it can be
 * extracted in chronological order.
 * 
 * @author ferdinando.villa
 *
 */
public interface IAssociation {
	
	enum Type {
		/**
		 * Activity wasAssociatedWith Agent;
		 *
		 * Entity wasGeneratedBy Activity wasAssociatedWith Agent [if Agent = model,
		 * then Activity = {resolution|instantiation}
		 */
		wasAssociatedWith,
		/**
		 * Entity wasAttributedTo Agent (implicit Activity, for remote provenance)
		 */
		wasAttributedTo,
		/**
		 * Entity wasGeneratedBy Activity
		 */
		wasGeneratedBy,

	}
	
	long getTimestamp();
	
	Type getType();
}
