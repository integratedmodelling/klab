package org.integratedmodelling.kactors.api;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Statements have a type according to which each can be casted to the
 * corresponding sub-interface.
 * 
 * @author Ferd
 *
 */
public interface IKActorsStatement extends IKActorsCodeStatement {

	public enum Type {
		ACTION_CALL, IF_STATEMENT, FOR_STATEMENT, DO_STATEMENT, WHILE_STATEMENT, TEXT_BLOCK, FIRE_VALUE, ASSIGNMENT,
		CONCURRENT_GROUP, SEQUENCE, INSTANTIATION
	}

	public interface If extends IKActorsStatement {

		IKActorsValue getCondition();

		IKActorsStatement getThen();

		List<Pair<IKActorsValue, IKActorsStatement>> getElseIfs();

		IKActorsStatement getElse();

	}

	public interface ConcurrentGroup extends IKActorsStatement {

		public List<IKActorsStatement> getStatements();

		Map<String, IKActorsValue> getGroupMetadata();

		/**
		 * Actions with the corresponding pattern to match to fired values. If the value
		 * is null, any fired values matches.
		 * 
		 * @return
		 */
		List<Pair<IKActorsValue, IKActorsStatement>> getGroupActions();

	}

	public interface Sequence extends IKActorsStatement {

		public List<IKActorsStatement> getStatements();

	}

	public interface While extends IKActorsStatement {

		IKActorsValue getCondition();

		IKActorsStatement getBody();

	}

	public interface Do extends IKActorsStatement {

		IKActorsValue getCondition();

		IKActorsStatement getBody();

	}

	public interface For extends IKActorsStatement {

		// TODO

		IKActorsStatement getBody();

	}

	public interface Assignment extends IKActorsStatement {

		/**
		 * Recipient is the part before the dot if set x.y value is issued. It may be
		 * null (local variable in the internal actor's symbols), refer to the state of
		 * another actor, or be 'self' which means the value is published to the state
		 * of the k.LAB identity connected to the actor, not to the internal actor's
		 * state. It's not possible to touch the state of an identity connected to
		 * another actor.
		 * 
		 * @return
		 */
		String getRecipient();

		/**
		 * Variable, which may or may not be prefixed with a recipient.
		 * 
		 * @return
		 */
		String getVariable();

		/**
		 * The value to set the variable to, which will be evaluated in the scope of
		 * the recipient executing the set statement.
		 * 
		 * @return
		 */
		IKActorsValue getValue();
	}

	public interface FireValue extends IKActorsStatement {

		IKActorsValue getValue();

	}

	public interface TextBlock extends IKActorsStatement {

		String getText();

	}

	public interface Instantiation extends IKActorsStatement {

		/**
		 * The behavior for the new actor
		 * 
		 * @return
		 */
		String getBehavior();

		/**
		 * Arguments, possibly empty, for the main action. Should include a tag if the
		 * actor must be referenced.
		 * 
		 * @return
		 */
		IParameters<String> getArguments();

		/**
		 * Actions with the corresponding pattern to match values fired by the child
		 * actor.
		 * 
		 * @return
		 */
		List<Pair<IKActorsValue, IKActorsStatement>> getActions();

		/**
		 * Each instantiation action needs a name to reference the actor, so that the
		 * parent actors can dispatch messages to children appropriately when the actors
		 * create external controllers such as view components. If the instantiation is
		 * called more than once, the path will have a 1-based index appended after an
		 * underscore, so that any actors created in a loop can be differentiated. This
		 * gets renamed to the tag if the parameters contain one.
		 * 
		 * @return the base name - either the tag assigned in the parameters or an
		 *         automatically generated one.
		 */
		String getActorBaseName();
		
	}

	public interface Call extends IKActorsStatement {

		/**
		 * If group != null, name will be null and the actions will refer to any of the
		 * messages in the group.
		 * 
		 * @return
		 */
		ConcurrentGroup getGroup();

		/**
		 * Parsed after checking with the loaded behavior manifest unless there is an
		 * explicit recipient. If the message is unrecognized this will be null and the
		 * engine will have to match it.
		 * 
		 * @return
		 */
		String getRecipient();

		/**
		 * The message ID. Must contain the recipient when understood through behavior
		 * manifest.
		 * 
		 * @return
		 */
		String getMessage();

		/**
		 * Arguments, possibly empty.
		 * 
		 * @return
		 */
		IParameters<String> getArguments();

		/**
		 * Actions with the corresponding pattern to match to fired values. If the value
		 * is null, any fired values matches.
		 * 
		 * @return
		 */
		List<Pair<IKActorsValue, IKActorsStatement>> getActions();

	}

	/**
	 * According to type, this statement can be cast to one of the above interfaces.
	 * 
	 * @return
	 */
	Type getType();

}
