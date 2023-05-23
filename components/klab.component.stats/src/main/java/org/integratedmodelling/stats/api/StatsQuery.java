/**
 * 
 */
package org.integratedmodelling.stats.api;

import org.integratedmodelling.klab.utils.Parameters;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import java.text.SimpleDateFormat;

/**
 * @author aris
 *
 */

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class StatsQuery {
    
    private int id;
    private String contextId;
    private int queryId;
    private String contextName;
    private String observations;
    private String observable;
    private String outcome;
    private String groupBy;
    private long count;
    private double resolutionTime;
    private boolean successful;
    private long startTime;
    private int dataflowComplexity;
    private long successCount;
    private long exceptionCount;
    private long errorCount;
    private double resolutionTimeTotal;
    private String assetName;
    private String assetType;
    private String startDate;
    private String queryResult;
    
    public String getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(String queryResult) {
        this.queryResult = queryResult;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public String getObservable() {
        return observable;
    }

    public void setObservable(String observable) {
        this.observable = observable;
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public long getExceptionCount() {
        return exceptionCount;
    }

    public void setExceptionCount(long exceptionCount) {
        this.exceptionCount = exceptionCount;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getDataflowComplexity() {
        return dataflowComplexity;
    }

    public void setDataflowComplexity(int dataflowComplexity) {
        this.dataflowComplexity = dataflowComplexity;
    }
    
    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public double getResolutionTime() {
        return resolutionTime;
    }
    
    public void setResolutionTime(double resolutionTime) {
        this.resolutionTime = resolutionTime;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getObservations() {
        return observations;
    }
    
    public void setObservations(String observations) {
        this.observations = observations;
    }
    
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getGroupBy() {
		return groupBy;
	}

	public void setGroup_by(String groupBy) {
		this.groupBy = groupBy;
	}

	public double getResolutionTimeTotal() {
        return resolutionTimeTotal;
    }

    public void setResolutionTimeTotal(double resolutionTimeTotal) {
        this.resolutionTimeTotal = resolutionTimeTotal;
    }
    
    public StatsQuery() {
       
    }
    
    public StatsQuery(Parameters<?> queryParam, String operation) {
        
        switch(operation) {
        case "asset":
            this.contextId = (String) queryParam.get("context_id");
            this.assetName = (String) queryParam.get("name");
            this.resolutionTime = (double) queryParam.get("total_time_sec");
            this.outcome = (String) queryParam.get("outcome");  
            break;
        case "outcome_group_count":
            this.outcome = (String) queryParam.get("outcome");
            this.count = (long) queryParam.get("instances");
            break;
        case "asset_name_group_count":
            this.assetName = (String) queryParam.get("name");
            this.count = (long) queryParam.get("count");
            if(queryParam.get("time_sum") == null) {
                this.resolutionTimeTotal = 0.0;
            }else this.resolutionTimeTotal = (double) queryParam.get("time_sum");      
            break;
        case "outcome_aggregate":
            this.contextName = (String) queryParam.get("context_name");
            this.queryId = (int) queryParam.get("id");
            this.observable = (String) queryParam.get("observable");
            this.count = (long) queryParam.get("count");
            this.outcome = (String) queryParam.get("outcome");
            this.assetType = (String) queryParam.get("asset_type");
            break;
        case "context_name_count":
            this.contextName = (String) queryParam.get("context_name");
            this.count = (long) queryParam.get("count");
            break;
        case "time_range":
            this.contextId = (String) queryParam.get("context_id");
            this.observable = (String) queryParam.get("observable");
            this.resolutionTime = (double) queryParam.get("total_time_sec");
            this.outcome = (String) queryParam.get("outcome");
            SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z");
            this.startDate = DateFormat.format((long) queryParam.get("start_time"));
            break;
        case "queries_per_day":
        	this.count = (long) queryParam.get("count");
            DateFormat = new SimpleDateFormat("dd.MM.yyyy");
        	this.startDate = DateFormat.format(queryParam.get("date"));
        	break;
        case "queries_per_month":
        	this.count = (long) queryParam.get("count");
            DateFormat = new SimpleDateFormat("MM.yyyy");
        	this.startDate = DateFormat.format(queryParam.get("date"));
        	break;
        case "queries_per_year":
        	this.count = (long) queryParam.get("count");
            DateFormat = new SimpleDateFormat("yyyy");
        	this.startDate = DateFormat.format(queryParam.get("date"));
        	break;
        /* default same as "asset" */
        default:
            this.id = (int) queryParam.get("query_id");
            this.contextId = (String) queryParam.get("context_id");
            this.assetName = (String) queryParam.get("name");
            this.resolutionTime = (double) queryParam.get("total_time_sec");
            
            if(Objects.equals((String) queryParam.get("outcome"),"Success")) {
                this.successful = true;
            }else this.successful = false;  
            break;
        }
    }
    

}
