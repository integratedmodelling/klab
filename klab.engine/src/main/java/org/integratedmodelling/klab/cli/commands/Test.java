package org.integratedmodelling.klab.cli.commands;

import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerScope;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.code.ExpressionScope;
import org.integratedmodelling.klab.engine.runtime.expressions.GroovyProcessor;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtils;

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
