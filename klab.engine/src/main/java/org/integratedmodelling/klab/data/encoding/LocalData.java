package org.integratedmodelling.klab.data.encoding;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;

public class LocalData implements IKlabData {

    IState state;
    IDirectObservation object;
    List<INotification> notifications = new ArrayList<>();
    boolean error = false;

    public LocalData(LocalDataBuilder builder) {
        if (builder.state != null) {
            this.state = builder.state;
        }
        if (builder.observation != null) {
            this.object = builder.observation;
        }
        for (INotification notification : builder.notifications) {
            if (notification.getLevel().equals(Level.SEVERE)) {
                error = true;
            }
            notifications.add(notification);
        }
    }

    @Override
    public List<INotification> getNotifications() {
        return notifications;
    }

    @Override
    public IArtifact getArtifact() {
        return state == null ? object : state;
    }

    @Override
    public boolean hasErrors() {
        return error;
    }

}
