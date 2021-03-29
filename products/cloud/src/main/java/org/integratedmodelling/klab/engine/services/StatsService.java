package org.integratedmodelling.klab.engine.services;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.engine.events.GenericUserEvent;
import org.integratedmodelling.klab.engine.events.UserEventHistory;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
@ConditionalOnProperty(
        value="stats.server.url", 
        havingValue = "", 
        matchIfMissing = false)
public class StatsService {
    
  @Value( "${stats.server.url}" )
  private String statsServerUrl;
  
  @Autowired
  RestTemplate template;
  
  @Async
  @EventListener(condition = "#event.type == T(org.integratedmodelling.klab.engine.events.UserEventType).HISTORY")
  public void handleLogin(GenericUserEvent<HubUserProfile, Session> event) {
      String url = null;
      UserEventHistory history = (UserEventHistory) event;
      SessionActivity activity = history.getActivity();
      try {
          URIBuilder urlBuilder;
          urlBuilder = new URIBuilder(statsServerUrl);
          urlBuilder.setPath(API.STATS.STATS_BASE);
          urlBuilder.addParameter(API.STATS.PARAMETERS.TYPE, activity.getClass().getCanonicalName());
          url = urlBuilder.build().toString();
          } catch (URISyntaxException e) {
              // TODO Auto-generated catch block
             Logging.INSTANCE.warn("Stat server URl malformed!");
      }
      
      try {
          template.postForLocation(url, activity);
      } catch(RestClientException ex) {
          Logging.INSTANCE.warn("Stat server is not up or url is wrong.");
      }
  }
}

