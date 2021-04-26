package org.integratedmodelling.klab.components.runtime.actors;

import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Actors.PanelLocation;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.base.Optional;

/**
 * Scope used during view extraction as the actor executes view-bound actions. One of these is part
 * of the KlabActor's scope used during k.Actors interpretation.
 * 
 * @author Ferd
 *
 */
class ViewScope {

    String identityId;
    IIdentity identity;
    String applicationId;
    String actorPath = null;
    Layout layout = null;
    Optional<Boolean> notEmpty;
    ViewComponent currentComponent;

    private Integer groupCounter = new Integer(0);

    public ViewScope(Scope actorScope) {
        this.applicationId = actorScope.appId;
        this.identity = actorScope.identity;
        this.identityId = actorScope.identity.getId();
    }

    public ViewScope getChild(ConcurrentGroup group) {

        if (this.currentComponent == null) {
            // not an app with a view
            return null;
        }

        ViewComponent parent = this.currentComponent;
        ViewComponent ret = new ViewComponent();
        ret.setIdentity(identityId);
        ret.setApplicationId(applicationId);
        ret.setActorPath(actorPath);
        boolean isActive = group.getGroupMetadata().containsKey("inputgroup");
        ret.setType(isActive ? ViewComponent.Type.InputGroup : ViewComponent.Type.Group);
        if (group.getGroupMetadata().containsKey("name")) {
            String name = group.getGroupMetadata().get("name").getStatedValue().toString();
            ret.setName(name);
        }
        String id = null;
        if (group.getGroupMetadata().containsKey("id")) {
            id = group.getGroupMetadata().get("id").getStatedValue().toString();
        } else {
            id = "g" + (groupCounter++);
        }

        setViewMetadata(ret, group.getGroupMetadata());
        ret.setId(parent.getId() + "/" + id);
        parent.getComponents().add(ret);

        ViewScope child = new ViewScope(this);
        child.currentComponent = ret;

        return child;
    }

    void setViewMetadata(ViewComponent component, Map<String, ?> parameters) {
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                if (!component.getAttributes().containsKey(key) && Actors.INSTANCE.getLayoutMetadata().contains(key)) {
                    Object param = parameters.get(key);
                    component.getAttributes().put(key,
                            param instanceof KActorsValue
                                    ? ((KActorsValue) param).getStatedValue().toString()
                                    : param.toString());
                }
            }
        }
    }

    public ViewScope(IIdentity identity, Layout layout, String applicationId, String actorPath) {
        this.identity = identity;
        this.layout = layout;
        this.identityId = identity == null ? null : identity.getId();
        this.applicationId = applicationId;
        this.actorPath = actorPath;
        this.notEmpty = Optional.of(Boolean.FALSE);
    }

    public ViewScope(ViewScope scope) {
        this.layout = scope.layout;
        this.applicationId = scope.applicationId;
        this.identityId = scope.identityId;
        this.groupCounter = scope.groupCounter;
        this.actorPath = scope.actorPath;
        this.notEmpty = scope.notEmpty;
    }

    /**
     * Pass an action; if the action has a view associated, create the correspondent panel (and, if
     * needed, the layout) in the view scope passed with the actor scope. The returned panel becomes
     * the current view component for the calls in the action to populate.
     * 
     * @param action
     * @param identity2 
     * @param appId 
     * @param parent
     * @return
     */
    public ViewPanel createPanel(Action action, String appId, IActorIdentity<?> identity) {

        ViewPanel panel = null;
        boolean hasView = action.getBehavior().getDestination() == Type.COMPONENT && "main".equals(action.getName());
        if (!hasView) {
            // scan annotations
            for (IAnnotation annotation : action.getAnnotations()) {

                PanelLocation panelLocation = Utils.valueOf(StringUtils.capitalize(annotation.getName()), PanelLocation.class);

                if (panelLocation != null) {

                    panel = new ViewPanel(annotation.containsKey("id") ? annotation.get("id", String.class) : action.getId(),
                            annotation.get("style", String.class));
                    panel.getAttributes().putAll(ViewBehavior.getMetadata(annotation, null));

                    if (this.layout == null) {
                        this.layout = createLayout(action.getBehavior());
                    }

                    switch(panelLocation) {
                    case Footer:
                        this.layout.setFooter(panel);
                        break;
                    case Header:
                        this.layout.setFooter(panel);
                        break;
                    case Left:
                        this.layout.getLeftPanels().add(panel);
                        break;
                    case Panel:
                        this.layout.getPanels().add(panel);
                        break;
                    case Right:
                        this.layout.getRightPanels().add(panel);
                        break;
                    case Window:
                    case Modal:
                        // TODO
                        break;
                    }
                }
            }

        } else {

            /*
             * must have component in scope; panel enters as new component
             */

            panel = new ViewPanel(action.getBehavior().getId(), action.getBehavior().getStatement().getStyle());
            for (IAnnotation annotation : action.getAnnotations()) {
                panel.getAttributes().putAll(ViewBehavior.getMetadata(annotation, null));
            }
        }

        return panel;
    }

    ViewScope createLayout(IAnnotation annotation, String actionId) {

        ViewScope ret = new ViewScope(this);

        ret.layout = new Layout(actionId, this.applicationId);
        ret.layout.setStyle(this.layout.getStyle());
        ret.layout.setDestination(this.layout.getDestination());
        ret.layout.setLabel(annotation.get("title", ""));
        ret.layout.setDescription(StringUtils.pack(annotation.get("subtitle", "")));
        ret.layout.setPlatform(this.layout.getPlatform());
        ret.layout.setLogo(annotation.get("logo", (String) null));
        ret.layout.setProjectId(this.layout.getProjectId());
        ret.layout.setApplicationId(applicationId);
        ret.layout.setId(actionId);
        ret.layout.setType("modal".equals(annotation.getName()) ? ViewComponent.Type.ModalWindow : ViewComponent.Type.Window);
        ViewPanel panel = new ViewPanel(annotation.containsKey("id") ? annotation.get("id", String.class) : actionId,
                annotation.get("style", String.class));
        panel.getAttributes().putAll(ViewBehavior.getMetadata(annotation, null));
        ret.layout.getPanels().add(panel);
        ret.currentComponent = panel;
        return ret;
    }

    private Layout createLayout(IBehavior behavior) {

        Layout ret = new Layout(behavior.getName(), this.applicationId);
        ret.setIdentity(this.identityId);
        ret.setVersionString(behavior.getStatement().getVersionString());
        ret.setStyle(behavior.getStatement().getStyle());
        ret.setDestination(behavior.getDestination());
        ret.setLabel(behavior.getStatement().getLabel());
        ret.setDescription(StringUtils.pack(behavior.getStatement().getDescription()));
        ret.setPlatform(behavior.getPlatform());
        ret.setLogo(behavior.getStatement().getLogo());
        ret.setProjectId(behavior.getProject());

        for (Action action : behavior.getActions()) {
            IAnnotation menu = Annotations.INSTANCE.getAnnotation(action, "menu");
            if (menu != null) {
                Layout.MenuItem menuItem = new Layout.MenuItem();
                menuItem.setId("menu." + action.getId());
                menuItem.setText(menu.containsKey("title") ? menu.get("title").toString() : "Unnamed menu");
                ret.getMenu().add(menuItem);
            }
        }

        if (behavior.getStatement().getStyleSpecs() != null) {
            ret.setStyleSpecs(JsonUtils.printAsJson(behavior.getStatement().getStyleSpecs()));
        }
        return ret;
    }

    /**
     * Get the view scope for a panel, linked to an action
     * 
     * @param action
     * @return
     */
    public ViewScope getChild(Action action, String appId, IActorIdentity<?> identity) {

        // this creates the layout if needed.
        this.applicationId = appId;
        ViewPanel panel = createPanel(action, appId, identity);
        if (panel == null) {
            return this;
        }

        ViewScope ret = new ViewScope(this);
        ret.currentComponent = panel;
        return ret;
    }

}