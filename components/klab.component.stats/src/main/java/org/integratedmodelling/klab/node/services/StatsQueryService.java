package org.integratedmodelling.klab.node.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
        switch(request.getQuery_type()) {
        case "asset":
            query = "SELECT * FROM assets;";
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
                    + "FROM assets a INNER JOIN queries q ON a.context_id=q.context_id and a.query_id = q.id "
                    + "INNER JOIN contexts c on c.id=q.context_id ";
            String outcome = request.getOutcome();
            if(Objects.equals(outcome,"Success") ||  Objects.equals(outcome,"Error") ||  Objects.equals(outcome,"Exception") ) {
                query = query + "WHERE a.outcome='" + outcome +"'";
            }
            query = query + " ORDER BY c.context_name, q.id, count DESC;";  
            break;
        case "context_name_count":
            query = "SELECT DISTINCT COUNT(context_name) OVER (PARTITION BY context_name) AS count, "
                    + "context_name FROM contexts  ORDER BY count DESC LIMIT " + request.getTop() + ";";
            break;
        case "time_range":
            if(request.getFrom() == null) {
                request.setFrom(LocalDate.of(2000, Month.JANUARY, 1));
            }
            if(request.getTo() == null) {
                request.setTo(LocalDate.of(2099, Month.JANUARY, 1));
            }
            LocalDateTime dateStartTime = request.getFrom().atStartOfDay();
            LocalDateTime dateEndTime = request.getTo().atStartOfDay();
            
            long milliSecondsDateStart = Timestamp.valueOf(dateStartTime).getTime();          
            long milliSecondsDateEnd = Timestamp.valueOf(dateEndTime).getTime();

            query = "SELECT * FROM queries WHERE start_time > " + milliSecondsDateStart + 
                    " AND start_time < " + milliSecondsDateEnd + ";";
            break;
            default:
            query = "SELECT * FROM assets;";
        }
        long n = stats.getDatabase().scan(query, (result) -> {
            StatsQuery queryStats = new StatsQuery(result, request.getQuery_type());
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
