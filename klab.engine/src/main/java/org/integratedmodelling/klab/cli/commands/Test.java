package org.integratedmodelling.klab.cli.commands;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.expressions.GroovyProcessor;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtils;

public class Test implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        return GroovyProcessor.INSTANCE
                .compile(StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim(), null)
                .eval(Parameters.create(), null);

    }

}
