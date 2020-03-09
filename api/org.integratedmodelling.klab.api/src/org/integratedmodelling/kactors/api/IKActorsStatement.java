package org.integratedmodelling.kactors.api;

import java.util.List;

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
		CONCURRENT_GROUP, SEQUENCE
	}

	public interface If extends IKActorsStatement {

		IKActorsValue getCondition();

		IKActorsStatement getThen();

		List<Pair<IKActorsValue, IKActorsStatement>> getElseIfs();

		IKActorsStatement getElse();

	}

	public interface ConcurrentGroup extends IKActorsStatement {

		public List<IKActorsStatement> getStatements();

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

		String getVariable();

		IKActorsValue getValue();
	}

	public interface FireValue extends IKActorsStatement {

		IKActorsValue getValue();

	}

	public interface TextBlock extends IKActorsStatement {

		String getText();

	}

	public interface Call extends IKActorsStatement {

		/**
		 * Null means self
		 * 
		 * @return
		 */
		String getRecipient();

		/**
		 * The message ID
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
