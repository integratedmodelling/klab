package org.integratedmodelling.klab.cli.commands;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.owl.ObservableComposer;

public class Test implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {
        // simplest
        ObservableComposer composer = ObservableComposer.create();
        composer.concept("im:Height");
        composer.of().concept("ecology:Tree").concept("im:High");
        composer.withUnit("m");
        System.out.println(composer.buildObservable().getDefinition());
		return null;
	}

}
