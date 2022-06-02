package org.integratedmodelling.klab.provenance;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IAssociation;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.jgrapht.graph.DefaultEdge;

public class ProvenanceEdge extends DefaultEdge implements IAssociation {

	private static final long serialVersionUID = 1013844289682153561L;

	private long timestamp = System.currentTimeMillis();
	private Type type;
	private ITime period;
	private ISpace shape;
	private boolean initialized = false;
	private boolean simplified = false;

	public ProvenanceEdge(IScale scale, Type association, Provenance provenance, boolean simplified) {
		if (scale != null) {
			if ((scale.getTime() == null || !scale.getTime().is(ITime.Type.INITIALIZATION))) {
				this.period = scale.getTime() == null ? null : scale.getTime().getBoundingExtent();
			} else {
				initialized = true;
			}
			this.shape = scale.getSpace() == null ? null : scale.getSpace().getBoundingExtent();
		} else {
			initialized = true;
		}
		this.type = association;
		this.simplified = simplified;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public Type getType() {
		return type;
	}

	public void merge(IScale scale) {

		if (scale == null || scale.getTime() == null || scale.getTime().is(ITime.Type.INITIALIZATION)) {
			initialized = true;
			return;
		}

		if (scale != null) {
			if (scale.getSpace() != null) {
				this.shape = this.shape == null ? scale.getSpace().getBoundingExtent()
						: this.shape.merge(scale.getSpace().getBoundingExtent(), LogicalConnector.UNION);
			}
			if (scale.getTime() != null) {
				this.period = this.period == null ? scale.getTime().getBoundingExtent()
						: this.period.merge(scale.getTime().getBoundingExtent(), LogicalConnector.UNION);
			}
		}
	}

	public String toString() {
		String timeDescription = describeTime();
		String spaceDescription = describeSpace();
		return (simplified ? "" : type.name())
				+ (timeDescription != null ? ((simplified ? "" : "\n") + timeDescription) : "")
				+ (spaceDescription != null ? ((simplified ? "" : "\n") + spaceDescription) : "");
	}

	private String describeSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	private String describeTime() {
		if (this.period == null) {
			return "Initialization";
		}
		return (initialized ? "Init, " : "") + ((Time) this.period).describe();
	}
}
