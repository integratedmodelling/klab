package org.integratedmodelling.klab.rest;

import java.util.List;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.utils.Triple;

public class ObservationChange {

	/**
	 * Type of change
	 * 
	 * @author Ferd
	 *
	 */
	public enum Type {

		/**
		 * Spatial context has changed location
		 */
		SpatialTranslation,

		/**
		 * Spatial context has changed shape
		 */
		SpatialChange,

		/**
		 * Observation has been terminated and is no longer in the context
		 */
		Termination,

		/**
		 * Number of children has changed: newSize contains the new number
		 */
		StructureChange,

		/**
		 * Name of object has changed
		 */
		NameChange,

		/**
		 * Attributes linked to an object or a folder have been added
		 */
		AttributeChange,

		/**
		 * Observable semantics has changed (so far by removing attributes)
		 */
		SemanticsChange,

		/**
		 * Values of a state have changed
		 */
		ValueChange,

		/**
		 * Observation becomes "main"
		 */
		BringForward,
		
		/**
		 * End of resolution for object
		 */
		ContextualizationCompleted
	}

	private String id;
	private String contextId;
	private long timestamp;

	// each new attribute gets Semantics, Label and Description
	private List<Triple<String, String, String>> newAttributes;
	private ScaleReference newScale;
	private String newName;
	private String newSemantics;
	private int newSize;
	private Type type;

	public List<Triple<String, String, String>> getNewAttributes() {
		return newAttributes;
	}

	public void setNewAttributes(List<Triple<String, String, String>> newAttributes) {
		this.newAttributes = newAttributes;
	}

	public ScaleReference getNewScale() {
		return newScale;
	}

	public void setNewScale(ScaleReference newScale) {
		this.newScale = newScale;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewSemantics() {
		return newSemantics;
	}

	public void setNewSemantics(String newSemantics) {
		this.newSemantics = newSemantics;
	}

	public int getNewSize() {
		return newSize;
	}

	public void setNewSize(int newSize) {
		this.newSize = newSize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public static ObservationChange main(IObservation observation, IContextualizationScope scope) {
		ObservationChange ret = new ObservationChange();
		ret.setTimestamp(-1);
		ret.setType(Type.BringForward);
		ret.setContextId(scope.getRootSubject().getId());
		ret.setId(observation.getId());
		return ret;
	}

	@Override
	public String toString() {
		return "ObservationChange [id=" + id + ", contextId=" + contextId + ", newAttributes=" + newAttributes
				+ ", newScale=" + newScale + ", newSize=" + newSize + ", type=" + type + "]";
	}

}
