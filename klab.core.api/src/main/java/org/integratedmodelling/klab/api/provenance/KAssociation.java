package org.integratedmodelling.klab.api.provenance;

/**
 * Any link in the provenance graph. Has a type and a timestamp so it can be
 * extracted in chronological order.
 * 
 * @author ferdinando.villa
 *
 */
public interface KAssociation {
	
	enum Type {

		/**
		 * Process wasTriggeredBy Process
		 */
		wasTriggeredBy,
		
		/**
		 * Process wasControlledBy Agent
		 */
		wasControlledBy,
		
		/**
		 * Entity wasDerivedFrom Entity (not by a process)
		 */
		wasDerivedFrom,
		
		/**
		 * Entity wasGeneratedBy Activity
		 */
		wasGeneratedBy,
		
		/**
		 * Process uses Entity 
		 */
		uses,

	}
	
	long getTimestamp();
	
	Type getType();
}
