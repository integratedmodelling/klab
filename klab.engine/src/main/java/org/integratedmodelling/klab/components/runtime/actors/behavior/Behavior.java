package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.model.KActorsBehavior;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.Range;

public class Behavior implements IBehavior {

	/**
	 * Pre-processed match value optimized for matching.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Match {

		KActorsValue value;

		public Match(IKActorsValue ikActorsValue) {
			this.value = (KActorsValue) ikActorsValue;
		}

		private boolean notMatch(Object value) {
			return value == null || value instanceof Throwable || (value instanceof Boolean && !((Boolean)value));
		}
		
		public boolean matches(Object value, Scope scope) {
			switch (this.value.getType()) {
			case ANYTHING:
				return true;
			case ANYVALUE:
				return value != null && !(value instanceof Throwable);
			case ANYTRUE:
				return value != null && !(value instanceof Throwable) && !(value instanceof Boolean && !((Boolean)value));
			case BOOLEAN:
				return value instanceof Boolean && value.equals(this.value.getValue());
			case CLASS:
				break;
			case DATE:
				break;
			case EXPRESSION:
				System.out.println("ACH AN EXPRESSION");
				break;
			case IDENTIFIER:
				if (!notMatch(value)) {
					scope.symbolTable.put(this.value.getValue().toString(), value);
					return true;
				}
				break;
			case LIST:
				// TODO differentiate between multi-identifier and OR match for values in list
				break;
			case MAP:
				break;
			case NODATA:
				return value == null || value instanceof Number && Double.isNaN(((Number)value).doubleValue());
			case NUMBER:
				return value instanceof Number && value.equals(this.value.getValue());
			case NUMBERED_PATTERN:
				break;
			case OBSERVABLE:
				break;
			case QUANTITY:
				break;
			case RANGE:
				return value instanceof Number
						&& ((Range) (this.value.getValue())).contains(((Number) value).doubleValue());
			case REGEXP:
				break;
			case STRING:
				return value instanceof String && value.equals(this.value.getValue());
			case TABLE:
				break;
			case TYPE:
				break;
			case URN:
				break;
			default:
				break;

			}
			return false;
		}
	}

	IKActorsBehavior statement;
	Map<String, BehaviorAction> actions = new LinkedHashMap<>();
	IMetadata metadata = new Metadata();
	List<IAnnotation> annotations = new ArrayList<>();

	public Behavior(IKActorsBehavior statement) {
		this.statement = statement;
		for (IKActorsAction a : statement.getActions()) {
			BehaviorAction action = new BehaviorAction(a, this);
			actions.put(action.getId(), action);
		}
	}

	private Behavior() {
		// empty behavior
		this.statement = new KActorsBehavior();
	}
	
	@Override
	public String getId() {
		return this.statement.getName();
	}

	@Override
	public String getName() {
		return statement.getName();
	}

	@Override
	public IKActorsBehavior getStatement() {
		return this.statement;
	}

	@Override
	public List<IKimObject> getChildren() {
		List<IKimObject> ret = new ArrayList<>();
		for (String id : actions.keySet()) {
			ret.add(actions.get(id));
		}
		return ret;
	}

	@Override
	public List<IAnnotation> getAnnotations() {
		return annotations;
	}

	@Override
	public boolean isDeprecated() {
		return statement.isDeprecated();
	}

	@Override
	public boolean isErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMetadata getMetadata() {
		return metadata;
	}

	@Override
	public Type getDestination() {
		return statement.getType();
	}

	@Override
	public List<Action> getActions(String... match) {
		List<Action> ret = new ArrayList<>();
		for (Action action : actions.values()) {
			if (match == null || match.length == 0) {
				ret.add(action);
				continue;
			}
			boolean ok = false;
			for (String m : match) {
				if (!ok && m.startsWith("@")) {
					ok = Annotations.INSTANCE.hasAnnotation(action, m.substring(1));
				} else if (!ok) {
					ok = m.equals(action.getId()) || m.equals(action.getName());
				}
				if (ok) {
					break;
				}
			}
			if (ok) {
				ret.add(action);
			}
		}
		return ret;
	}

	@Override
	public Action getAction(String actionId) {
		return actions.get(actionId);
	}

	public static IBehavior empty() {
		return new Behavior();
	}

}
