package org.integratedmodelling.klab.stats.api.models;

@Deprecated
public class StatsInsertRequest<M>  extends StatsBaseRequest<M>{
	
    public StatsInsertRequest() {
    }
    
    public StatsInsertRequest(Class<M> type) {
		this.type = type;
    }
    
    public StatsInsertRequest(Class<M> type, Object m) {
		this.type = type;
		this.setModel(type.cast(m));
    }
    
    public Class<M> getType() {
        return this.type;
    }
	
	private M m;


	public M getM() {
		return m;
	}

	public void setModel(M m) {
		this.m = m;
	}
	
	public void setType(Class<M> type) {
		this.type = type;
	}

}
