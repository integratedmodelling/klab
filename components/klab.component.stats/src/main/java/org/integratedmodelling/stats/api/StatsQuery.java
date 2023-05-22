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
    private String context_id;
    private int query_id;
    private String context_name;
    private String observations;
    private String observable;
    private String outcome;
    private long count;
    private long start_time;
    private double resolution_time;
    private boolean successful;
    private long startTime;
    private int dataflow_complexity;
    private long success_count;
    private long exception_count;
    private long error_count;
    private double resolution_time_total;
    private String asset_name;
    private String asset_type;
    private String start_date;
    private String query_result;
    
    public String getQuery_result() {
        return query_result;
    }

    public void setQuery_result(String query_result) {
        this.query_result = query_result;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }
    
    public String getObservable() {
        return observable;
    }

    public void setObservable(String observable) {
        this.observable = observable;
    }

    public int getQuery_id() {
        return query_id;
    }

    public void setQuery_id(int query_id) {
        this.query_id = query_id;
    }

    public String getContext_name() {
        return context_name;
    }

    public void setContext_name(String context_name) {
        this.context_name = context_name;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public long getSuccess_count() {
        return success_count;
    }

    public void setSuccess_count(long success_count) {
        this.success_count = success_count;
    }

    public long getException_count() {
        return exception_count;
    }

    public void setException_count(long exception_count) {
        this.exception_count = exception_count;
    }

    public long getError_count() {
        return error_count;
    }

    public void setError_count(long error_count) {
        this.error_count = error_count;
    }

    public String getContext_id() {
        return context_id;
    }

    public void setContext_id(String context_id) {
        this.context_id = context_id;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getDataflowComplexity() {
        return dataflow_complexity;
    }

    public void setDataflowComplexity(int dataflowComplexity) {
        this.dataflow_complexity = dataflowComplexity;
    }
    
    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public double getResolution_time() {
        return resolution_time;
    }
    
    public void setResolution_time(double resolution_time) {
        this.resolution_time = resolution_time;
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

    public double getResolution_time_total() {
        return resolution_time_total;
    }

    public void setResolution_time_total(double resolution_time_total) {
        this.resolution_time_total = resolution_time_total;
    }
    
    public StatsQuery() {
       
    }
    
    public StatsQuery(Parameters<?> queryParam, String operation) {
        
        switch(operation) {
        case "asset":
            this.context_id = (String) queryParam.get("context_id");
            this.asset_name = (String) queryParam.get("name");
            this.resolution_time = (double) queryParam.get("total_time_sec");
            this.outcome = (String) queryParam.get("outcome");  
            break;
        case "outcome_group_count":
            this.outcome = (String) queryParam.get("outcome");
            this.count = (long) queryParam.get("instances");
            break;
        case "asset_name_group_count":
            this.asset_name = (String) queryParam.get("name");
            this.count = (long) queryParam.get("count");
            if(queryParam.get("time_sum") == null) {
                this.resolution_time_total = 0.0;
            }else this.resolution_time_total = (double) queryParam.get("time_sum");      
            break;
        case "outcome_aggregate":
            this.context_name = (String) queryParam.get("context_name");
            this.query_id = (int) queryParam.get("id");
            this.observable = (String) queryParam.get("observable");
            this.count = (long) queryParam.get("count");
            this.outcome = (String) queryParam.get("outcome");
            this.asset_type = (String) queryParam.get("asset_type");
            break;
        case "context_name_count":
            this.context_name = (String) queryParam.get("context_name");
            this.count = (long) queryParam.get("count");
            break;
        case "time_range":
            this.context_id = (String) queryParam.get("context_id");
            this.observable = (String) queryParam.get("observable");
            this.resolution_time = (double) queryParam.get("total_time_sec");
            this.outcome = (String) queryParam.get("outcome");
            SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z");
            this.start_date = DateFormat.format((long) queryParam.get("start_time"));
            break;
        /* default same as "asset" */
        default:
            this.id = (int) queryParam.get("query_id");
            this.context_id = (String) queryParam.get("context_id");
            this.asset_name = (String) queryParam.get("name");
            this.resolution_time = (double) queryParam.get("total_time_sec");
            
            if(Objects.equals((String) queryParam.get("outcome"),"Success")) {
                this.successful = true;
            }else this.successful = false;  
            break;
        }
    }
    

}
