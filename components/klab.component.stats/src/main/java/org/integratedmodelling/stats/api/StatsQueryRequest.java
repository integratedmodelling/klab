package org.integratedmodelling.stats.api;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class StatsQueryRequest {

        private int top = 10;
        private String query_type = "asset";
        private String outcome;
        
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate from;
        
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate to;
        
        private String db;
        private String where;      
        private String select;

        public String getDb() {
            return db;
        }
        public void setDb(String db) {
            this.db = db;
        }
        public String getSelect() {
            return select;
        }
        public void setSelect(String select) {
            this.select = select;
        }
        public String getWhere() {
            return where;
        }
        public void setWhere(String where) {
            this.where = where;
        }
        
        
        public int getTop() {
            return top;
        }
        public void setTop(int top) {
            this.top = top;
        }
        public String getQuery_type() {
            return query_type;
        }
        public void setQuery_type(String query_type) {
            this.query_type = query_type;
        }
        public String getOutcome() {
            return outcome;
        }
        public void setOutcome(String outcome) {
            this.outcome = outcome;
        }
        public LocalDate getFrom() {
            return from;
        }
        public void setFrom(LocalDate from) {
            this.from = from;
        }
        public LocalDate getTo() {
            return to;
        }
        public void setTo(LocalDate to) {
            this.to = to;
        }

        public StatsQueryRequest() {}
        @Override
        public String toString() {
            return "StatsQueryRequest [top=" + top + ", query_type=" + query_type + ", outcome=" + outcome + ", from=" + from
                    + ", to=" + to + "]";
        }
        
        
        
}
