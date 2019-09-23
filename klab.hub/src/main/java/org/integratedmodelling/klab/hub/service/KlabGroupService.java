package org.integratedmodelling.klab.hub.service;

import java.util.Collection;
import java.util.Optional;

import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.rest.Group;

public interface KlabGroupService {
   public abstract void createGroup(String id, KlabGroup group);
   public abstract void updateGroup(String id, KlabGroup group);
   public abstract void deleteGroup(String id);
   public abstract Collection<KlabGroup> getGroups();
   public abstract Optional<KlabGroup> getGroup(String id);
   public abstract Collection<String> getGroupNames();
   public abstract Collection<? extends Group> getGroupsList();
}
