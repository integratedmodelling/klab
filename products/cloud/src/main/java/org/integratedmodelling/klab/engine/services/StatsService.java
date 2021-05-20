package org.integratedmodelling.klab.engine.services;

import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.http.client.utils.URIBuilder;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.engine.events.GenericUserEvent;
import org.integratedmodelling.klab.engine.events.UserEventContext;
import org.integratedmodelling.klab.engine.events.UserEventHistory;
import org.integratedmodelling.klab.engine.events.UserEventObservation;
import org.integratedmodelling.klab.engine.events.UserEventScale;
import org.integratedmodelling.klab.engine.runtime.ObserveContextTask;
import org.integratedmodelling.klab.engine.runtime.ObserveInContextTask;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.TaskReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
@ConditionalOnProperty(value = "stats.server.url", havingValue = "", matchIfMissing = false)
public class StatsService {

    @Value("${stats.server.url}")
    private String statsServerUrl;


    @Autowired
    RestTemplate template;

    @Async
    @EventListener(condition = "#event.type == T(org.integratedmodelling.klab.engine.events.UserEventType).HISTORY")
    public void handleHistory(GenericUserEvent<HubUserProfile, Session> event) {
        UserEventHistory historyEvent = (UserEventHistory) event;
        SessionActivity activity = historyEvent.getActivity();

        if (activity.getEnd() > activity.getStart()) {
            Logging.INSTANCE.info("activity ended");
            Set<String> matches = event
                    .getSession()
                    .getCompletedTasks()
                    .keySet().stream()
                    .filter(s -> s.startsWith(activity.getActivityId()))
                    .collect(Collectors.toSet());
            
           
            matches.forEach(key -> {
                Future< ? > task = event.getSession().getCompletedTasks().remove(key);
                if (task instanceof ObserveInContextTask) {
                    ObserveInContextTask t = (ObserveInContextTask) task;
                    TaskReference ref = t.getDescriptor();
                    String url = getUrl(ref.getClass().getCanonicalName());
                    if (url != null) {
                        postToServer(url, ref);
                    }
                }
                if (task instanceof ObserveContextTask) {
                    ObserveContextTask t = (ObserveContextTask) task;
                    TaskReference ref = t.getDescriptor();
                    String url = getUrl(ref.getClass().getCanonicalName());
                    if (url != null) {
                        postToServer(url, ref);
                    }
                }
                
            });
        }

        String url = getUrl(activity.getClass().getCanonicalName());
        if (url != null) {
            postToServer(url, activity);
        }
    }

    @Async
    @EventListener(condition = "#event.type == T(org.integratedmodelling.klab.engine.events.UserEventType).SCALE")
    public void handleScale(GenericUserEvent<HubUserProfile, Session> event) {
        UserEventScale scaleEvent = (UserEventScale) event;
        ScaleReference scale = scaleEvent.getScale();
        String url = getUrl(scale.getClass().getCanonicalName());
        if (url != null) {
            postToServer(url, scale);
        }
    }

    @Async
    @EventListener(condition = "#event.type == T(org.integratedmodelling.klab.engine.events.UserEventType).CONTEXT")
    public void handleContext(GenericUserEvent<HubUserProfile, Session> event) throws JsonProcessingException {
        UserEventContext contextEvent = (UserEventContext) event;
        ISubject context = contextEvent.getContext();
        IObservation obs = event.getSession().getObservation(context.getId());
        ObservationReference descriptor = Observations.INSTANCE
                .createArtifactDescriptor(obs/* , getParentArtifactOf(observation) */, obs.getScale().initialization(), 0);
        String url = getUrl(descriptor.getClass().getCanonicalName());
        if (url != null) {
            postToServer(url, descriptor);
        }
    }

    @Async
    @EventListener(condition = "#event.type == T(org.integratedmodelling.klab.engine.events.UserEventType).OBSERVATION")
    public void handleObservation(GenericUserEvent<HubUserProfile, Session> event) {
        UserEventObservation observationEvent = (UserEventObservation) event;
        IObservation observation = observationEvent.getObservation();

        ObservationReference descriptor = Observations.INSTANCE.createArtifactDescriptor(
                observation/* , getParentArtifactOf(observation) */, observation.getScale().initialization(), 0);
        
        ObserveInContextTask task = event.getSession().getTask(descriptor.getTaskId(),  ObserveInContextTask.class);
        if (task != null && !task.isChildTask()) {
            String url = getUrl(descriptor.getClass().getCanonicalName());
            if (url != null) {
                postToServer(url, descriptor);
            }
        }
    }

    private String getUrl(String canonicalName) {
        try {
            URIBuilder urlBuilder;
            urlBuilder = new URIBuilder(statsServerUrl);
            urlBuilder.setPath(API.STATS.STATS_BASE);
            urlBuilder.addParameter(API.STATS.PARAMETERS.TYPE, canonicalName);
            String url = urlBuilder.build().toString();
            return url;
        } catch (URISyntaxException ex) {
            Logging.INSTANCE.warn("Malformed Stat Server URL");
            return null;
        }
    }

    private void postToServer(String url, Object obj) {
        try {
            template.postForLocation(url, obj);
        } catch (RestClientException ex) {
            Logging.INSTANCE.warn("Stat server is not up or url is wrong.");
        }
    }
    
}
