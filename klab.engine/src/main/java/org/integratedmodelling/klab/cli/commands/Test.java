package org.integratedmodelling.klab.cli.commands;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;

public class Test implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        IIdentity identity = Klab.INSTANCE.getRootMonitor().getIdentity();
        return identity.toString();
//        ExpressionScope scope = ExpressionScope.empty(session.getMonitor()).withKnownIdentifier("elevation",
//                IKimConcept.Type.QUALITY).withCompilerScope(CompilerScope.Contextual);
//
//        return GroovyProcessor.INSTANCE
//                .compile(StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim(), scope)
//                .eval(null, Parameters.create());

    }

}
