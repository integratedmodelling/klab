package org.integratedmodelling.klab.services.engine.reasoner;

import java.io.Serializable;

import org.integratedmodelling.klab.api.engine.IEngineService;

import io.reacted.core.reactorsystem.ReActorRef;

public class ReasonerDefaultService implements IEngineService.Reasoner, Serializable {

    private static final long serialVersionUID = -4710516592907623356L;

    // ?
    ReActorRef service;
    
}
