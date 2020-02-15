package org.integratedmodelling.kactors.api;

public interface IKaction extends IKactorsStatement {

	IKmatch getMatch();

	IKactor getAction();
}
