package org.integratedmodelling.klab.hub.commands;

import java.util.List;

import org.integratedmodelling.klab.hub.api.Agreement;

public interface AgreementCommand {
    public List<Agreement> execute();

}
