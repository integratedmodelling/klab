package org.integratedmodelling.klab.stats.api.models;

import java.io.Serializable;

import org.joda.time.DateTime;


abstract class BaseEntity implements Serializable {

	public BaseEntity() {
		super();
		setCreated();
	}

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 2976615962294386224L;
	

    private String id;
    
    private DateTime created;
    private DateTime updated;


    public String getId() {
        return id;
    }

	public DateTime getCreated() {
		return created;
	}

	public void setCreated() {
		this.created = DateTime.now();
	}

	public DateTime getUpdated() {
		return updated;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}
    
    
}
