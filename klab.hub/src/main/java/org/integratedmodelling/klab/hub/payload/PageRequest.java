package org.integratedmodelling.klab.hub.payload;

public class PageRequest {
    
    private int page;
    private int size;
    private String filterOr;
    private String filterAnd;
    private String orders;
    
    
    public PageRequest() {
        super();
    }
    
    public PageRequest(int page, int size, String filterOr, String filterAnd, String orders) {
        super();
        this.page = page;
        this.size = size;
        this.filterOr = filterOr;
        this.filterAnd = filterAnd;
        this.orders = orders;
    }
    
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getFilterOr() {
        return filterOr;
    }
    public void setFilterOr(String filterOr) {
        this.filterOr = filterOr;
    }
    public String getFilterAnd() {
        return filterAnd;
    }
    public void setFilterAnd(String filterAnd) {
        this.filterAnd = filterAnd;
    }
    public String getOrders() {
        return orders;
    }
    public void setOrders(String orders) {
        this.orders = orders;
    }
    
    

}
