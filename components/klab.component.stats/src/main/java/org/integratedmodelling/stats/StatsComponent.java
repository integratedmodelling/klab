package org.integratedmodelling.stats;

import org.integratedmodelling.klab.api.extensions.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.integratedmodelling.klab.Version;

@Component(id = "org.integratedmodelling.statistics", version = Version.CURRENT)
public class StatsComponent {

    @PutMapping
    public void addActivity() {
        
    }
    
}
