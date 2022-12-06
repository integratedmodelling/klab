package org.integratedmodelling.klab.stats.api.models;

@Deprecated
public class StatsFindPageRequest<M> extends StatsBaseRequest<M>{

	public StatsFindPageRequest() {
    }
    
    public StatsFindPageRequest(Class<M> type) {
		this.type = type;
    }
    
    public StatsFindPageRequest(Class<M> type, Integer page) {
		this.type = type;
		this.setPage(page);
    }
    
    public Class<M> getType() {
        return this.type;
    }
	
	public void setType(Class<M> type) {
		this.type = type;
	}
	
    private int page;
    private int limit;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
	
	
	
}
