package org.integratedmodelling.stats;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.integratedmodelling.stats.database.StatsDatabase;

@Component(id = StatsComponent.ID, version = Version.CURRENT)
public class StatsComponent {

	public static final String ID = "org.integratedmodelling.statistics";
	
    StatsDatabase database;
    
    public StatsComponent() {
    }
    
    @Initialize
    public void initialize() {
    	database = new StatsDatabase();
    }

	public void submit(ObservationResultStatistics obs) {
		// TODO Auto-generated method stub
		System.out.println("DIOCAN " + obs);
	}
    
}
