package org.integratedmodelling.klab.stats.api.models;


public class KlabRestEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4563787726086214514L;
	
	public KlabRestEntity(Object model) {
		this.model = model;
		this.type = model.getClass().getSimpleName();
		
	}
	
	private Object model;
	
	private final String type;

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
	
    public String getMyType() {
        return this.type;
    }
	
}
