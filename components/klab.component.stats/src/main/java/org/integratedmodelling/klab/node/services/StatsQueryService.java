package org.integratedmodelling.klab.node.services;

import java.util.ArrayList;
import java.util.Objects;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.stats.StatsComponent;
import org.integratedmodelling.stats.api.StatsQuery;
import org.integratedmodelling.stats.api.StatsQueryRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class StatsQueryService {

    public ResponseEntity<?> getQuery(StatsQueryRequest request) throws JsonProcessingException {
        Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
        StatsComponent stats = stc.getImplementation(StatsComponent.class);
        ArrayList<StatsQuery> ret = new ArrayList<StatsQuery>();
        String query = null;
        switch(request.getQueryType()) {
        case "asset":
        	if(request.getFrom() == 0) {
                request.setFrom(946684800000L);
            }
            if(request.getTo() == 0) {
                request.setTo(4070908800000L);
            }
            
            query = "SELECT * FROM assets";
            if(request.getResolutionTimeMin() != -1 && request.getResolutionTimeMax() != -1) {
                query += " WHERE total_time_sec > " + request.getResolutionTimeMin()
                + " AND total_time_sec < " + request.getResolutionTimeMax(); 
            }else if(request.getResolutionTimeMin() != -1) {
                query += " WHERE total_time_sec > " + request.getResolutionTimeMin();
            }else if(request.getResolutionTimeMax() != -1) {
                query += " WHERE total_time_sec < " + request.getResolutionTimeMax();
            }
            String outcome = request.getOutcome();
            if(Objects.equals(outcome,"Success") ||  Objects.equals(outcome,"Error") ||  Objects.equals(outcome,"Exception") ) {
                if(request.getResolutionTimeMin() == -1 && request.getResolutionTimeMax() == -1)
                    query += " WHERE ";
                else
                    query +=  " AND ";
                query += "outcome='" + outcome +"'";
            query +=  "AND start_time >  " + request.getFrom() + " AND start_time < " + request.getTo() +";";
            }
            
            query += ";";
            break;
        case "asset_name_group_count":
            query = "SELECT DISTINCT name, COUNT(name) OVER (PARTITION BY name) AS count, "
                    + "SUM(nullif(total_time_sec, 'NaN')) OVER (PARTITION BY name) AS time_sum "
                    + "FROM assets ORDER BY count DESC, time_sum DESC LIMIT " + request.getTop() + ";";
            break;
        case "outcome_group_count":
            query = "SELECT DISTINCT outcome, COUNT(outcome) "
                    + "OVER (PARTITION BY outcome) AS instances FROM assets;";
            break;
        case "outcome_aggregate":
            query = "SELECT DISTINCT c.context_name,q.id, q.observable, COUNT(a.outcome) "
                    + "OVER (PARTITION BY q.context_id,q.id,a.outcome,a.asset_type,c.context_name)"
                    + ",a.outcome,a.asset_type "
                    + "FROM assets a INNER JOIN queries q ON a.context_id=q.context_id AND a.query_id = q.id "
                    + "INNER JOIN contexts c on c.id=q.context_id ";
            outcome = request.getOutcome();
            if(Objects.equals(outcome,"Success") ||  Objects.equals(outcome,"Error") ||  Objects.equals(outcome,"Exception") ) {
                query += "WHERE a.outcome='" + outcome +"'";
            }
            query += " ORDER BY c.context_name, q.id, count DESC;";  
            break;
        case "context_name_count":
            query = "SELECT DISTINCT COUNT(context_name) OVER (PARTITION BY context_name) AS count, "
                    + "context_name FROM contexts  ORDER BY count DESC LIMIT " + request.getTop() + ";";
            break;
        case "requests_per_user":
        	if(request.getFrom() == 0) {
                request.setFrom(946684800000L);
            }
            if(request.getTo() == 0) {
                request.setTo(4070908800000L);
            }
            
        	query = "SELECT DISTINCT principal,"
        			+ " COUNT(principal) OVER (PARTITION BY principal) AS count"
        			+ " FROM contexts WHERE created > " + request.getFrom()
        			+ " AND created < " + request.getTo()
        			+ " ORDER BY count DESC LIMIT " + request.getTop() + ";";
        	break;
        case "time_range":
            if(request.getFrom() == 0) {
                request.setFrom(946684800000L);
            }
            if(request.getTo() == 0) {
                request.setTo(4070908800000L);
            }
//            LocalDateTime dateStartTime = request.getFrom().atStartOfDay();
//            LocalDateTime dateEndTime = request.getTo().atStartOfDay();
//            
//            long milliSecondsDateStart = Timestamp.valueOf(dateStartTime).getTime();          
//            long milliSecondsDateEnd = Timestamp.valueOf(dateEndTime).getTime();

            query = "SELECT * FROM queries WHERE start_time > " + request.getFrom() + 
                    " AND start_time < " + request.getTo() + ";";
            break;
        case "queries_per":
        	if(!request.getGroupBy().equals("day") && !request.getGroupBy().equals("month") && !request.getGroupBy().equals("year")) {
        		request.setGroupBy("month");
        		}
        	/* if we want to receive number of contexts instead of queries */
//        	query = "SELECT DISTINCT date_trunc('"+ request.getGroupBy() + "', to_timestamp(created/1000)) AS date, "
//        			+ "COUNT(id) OVER (PARTITION BY date_trunc('" + request.getGroupBy() + "', to_timestamp(created/1000))) AS count "
//        			+ "FROM contexts ORDER BY date;";
        	query = "SELECT DISTINCT date_trunc('"+ request.getGroupBy() + "', to_timestamp(start_time/1000)) AS date, "
        			+ "COUNT(context_id) OVER (PARTITION BY date_trunc('" + request.getGroupBy() + "', to_timestamp(start_time/1000))) AS count "
        			+ "FROM queries ORDER BY date;";
            /* change queryType to contain the {day,month,year} which is needed when resolving the query later */
        	request.setQueryType(request.getQueryType()+"_"+request.getGroupBy());
        	break;
        /* default same as "outcome_group_count" */
        default:
        	query = "SELECT DISTINCT outcome, COUNT(outcome) "
                    + "OVER (PARTITION BY outcome) AS instances FROM assets;";
        }
        stats.getDatabase().scan(query, (result) -> {
            StatsQuery queryStats = new StatsQuery(result, request.getQueryType());
            ret.add(queryStats);
        }); 
        
        String _json;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        _json = ow.writeValueAsString(ret);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(_json);
    }

}
