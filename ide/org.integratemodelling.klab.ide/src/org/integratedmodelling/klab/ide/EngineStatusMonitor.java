package org.integratedmodelling.klab.ide;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.integratedmodelling.klab.client.Client;

/**
 * Status monitor that checks the engine status at regular intervals, notifying of either
 * down or up events.
 * 
 * @author ferdinando.villa
 *
 */
public class EngineStatusMonitor {

    public static final String ENGINE_DEFAULT_URL = "http://127.0.0.1:8283/modeler";
    
    String engineUrl = ENGINE_DEFAULT_URL;
    String engineKey;
    protected long recheckSecondsWhenOnline = 60;
    protected long recheckSecondsWhenOffline = 10;
    long uptime = -1;
    Client client = Client.create();

    private Runnable onEngineUp;

    private Runnable onEngineDown;
    
    public EngineStatusMonitor(String url, Runnable onEngineUp, Runnable onEngineDown) {
        engineUrl = url;
        this.onEngineUp = onEngineUp;
        this.onEngineDown = onEngineDown;
        new RepeatingJob().schedule();
    }
    
    public class RepeatingJob extends Job {
        
        private boolean running = true;
        
        public RepeatingJob() {
           super("Engine status monitor");
        }
        
        protected IStatus run(IProgressMonitor monitor) {
            
            long delay = recheckSecondsWhenOffline;
            long up = client.ping(engineUrl);
            if (uptime < 0 && up > 0) {
                onEngineUp.run();
                delay = recheckSecondsWhenOnline;
            } else if (up < 0 && uptime > 0) {
                onEngineDown.run();
            }
            
            uptime = up;
            schedule(delay * 1000);
            
            return Status.OK_STATUS;
        }
        
        public boolean shouldSchedule() {
           return running;
        }
        
        public void stop() {
           running = false;
        }
     }
    
}
