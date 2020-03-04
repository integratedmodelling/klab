package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;

/**
 * Asynchronous group of call sequences (set semantics).
 * 
 * @author Ferd
 *
 */
public class CallGroup {
	private List<KlabMessage> calls = new ArrayList<>();
}
