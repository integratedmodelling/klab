package org.integratedmodelling.klab.components.runtime.actors;

import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Actors.PanelLocation;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.base.Optional;

/**
 * Scope used during view extraction as the actor executes view-bound actions.
 * One of these is part of the KlabActor's scope used during k.Actors
 * interpretation.
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

		ViewComponent parent = this.currentComponent;
		ViewComponent ret = new ViewComponent();
		ret.setIdentity(identityId);
		ret.setApplicationId(applicationId);
		ret.setActorPath(actorPath);
		boolean isActive = group.getGroupMetadata().containsKey("inputgroup");
		ret.setType(isActive ? ViewComponent.Type.InputGroup : ViewComponent.Type.Group);
		if (group.getGroupMetadata().containsKey("name")) {
			ret.setName(group.getGroupMetadata().get("name").getValue().toString());
		}
		String id = null;
		if (group.getGroupMetadata().containsKey("id")) {
			id = group.getGroupMetadata().get("id").getValue().toString();
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
				if (Actors.INSTANCE.getLayoutMetadata().contains(key)) {
					Object param = parameters.get(key);
					component.getAttributes().put(key,
							param instanceof KActorsValue ? ((KActorsValue) param).getValue().toString()
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
	 * Pass an action; if the action has a view associated, create the correspondent
	 * panel (and, if needed, the layout) in the view scope passed with the actor
	 * scope. The returned panel becomes the current view component for the calls in
	 * the action to populate.
	 * 
	 * @param action
	 * @param parent
	 * @return
	 */
	public ViewPanel createPanel(Action action) {

		ViewPanel panel = null;
		boolean hasView = action.getBehavior().getDestination() == Type.COMPONENT && "main".equals(action.getName());
		if (!hasView) {
			// scan annotations
			for (IAnnotation annotation : action.getAnnotations()) {

				PanelLocation panelLocation = Utils.valueOf(StringUtils.capitalize(annotation.getName()),
						PanelLocation.class);

				if (panelLocation != null) {

					panel = new ViewPanel(
							annotation.containsKey("id") ? annotation.get("id", String.class) : action.getId(),
							annotation.get("style", String.class));
					panel.getAttributes().putAll(ViewBehavior.getMetadata(annotation, null));

					if (this.layout == null) {
						this.layout = createLayout(action.getBehavior());
					}

					switch (panelLocation) {
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

	private Layout createLayout(IBehavior behavior) {

		Layout ret = new Layout(behavior.getName(), this.applicationId);
		ret.setStyle(behavior.getStatement().getStyle());
		ret.setDestination(behavior.getDestination());
		ret.setLabel(behavior.getStatement().getLabel());
		ret.setDescription(StringUtils.pack(behavior.getStatement().getDescription()));
		ret.setPlatform(behavior.getPlatform());
		ret.setLogo(behavior.getStatement().getLogo());
		ret.setProjectId(behavior.getProject());

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
	public ViewScope getChild(Action action) {

		// this creates the layout if needed.
		ViewPanel panel = createPanel(action);
		if (panel == null) {
			return this;
		}

		ViewScope ret = new ViewScope(this);
		ret.currentComponent = panel;
		return ret;
	}

}