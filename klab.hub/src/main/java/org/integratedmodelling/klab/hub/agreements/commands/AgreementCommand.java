package org.integratedmodelling.klab.hub.agreements.commands;

import java.util.List;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;

public interface AgreementCommand {
    public List<Agreement> execute();

}
