package org.integratedmodelling.stats.api;

public class StatsQueryRequest {

        private int top = 10;
        private String queryType = "asset";
        private String outcome;
        
        private int resolutionTimeMin = -1;
        private int resolutionTimeMax = -1;
        
        private long from = 0;  //in milliseconds
        private long to = 0;    //in milliseconds
        
        private String db;
        private String where;      
        private String select;

        private String groupBy = "month";
        
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
        public String getQueryType() {
            return queryType;
        }
        public void setQueryType(String queryType) {
            this.queryType = queryType;
        }
        public String getOutcome() {
            return outcome;
        }
        public void setOutcome(String outcome) {
            this.outcome = outcome;
        }
        public int getResolutionTimeMin() {
            return resolutionTimeMin;
        }
        public void setResolutionTimeMin(int resolutionTimeMin) {
            this.resolutionTimeMin = resolutionTimeMin;
        }
        public int getResolutionTimeMax() {
            return resolutionTimeMax;
        }
        public void setResolutionTimeMax(int resolutionTimeMax) {
            this.resolutionTimeMax = resolutionTimeMax;
        }
        public long getFrom() {
            return from;
        }
        public void setFrom(long from) {
            this.from = from;
        }
        public long getTo() {
            return to;
        }
        public void setTo(long to) {
            this.to = to;
        }

        public String getGroupBy() {
			return groupBy;
		}
		public void setGroupBy(String groupBy) {
			this.groupBy = groupBy;
		}
		public StatsQueryRequest() {}
        @Override
        public String toString() {
            return "StatsQueryRequest [top=" + top + ", queryType=" + queryType + ", outcome=" + outcome + ", resolutionTimeMin="
                    + resolutionTimeMin + ", resolutionTimeMax=" + resolutionTimeMax + ", from=" + from + ", to=" + to + ", db="
                    + db + ", where=" + where + ", select=" + select + "]";
        }
        
        
        
}
