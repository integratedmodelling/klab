package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior;

public interface IActorsService {

	/**
	 * Return the behavior identified by the passed string.
	 * 
	 * @param id
	 * @return a behavior or null
	 */
	IBehavior getBehavior(String id);
	
	/**
	 * Read a k.Actor specification from a URL.
	 * 
	 * @param url
	 * @return the actor specification
	 */
	IKActorsBehavior declare(URL url);

	/**
	 * Read a k.Actor specification from a file.
	 * 
	 * @param file
	 * @return the actor specification
	 */
	IKActorsBehavior declare(File file);

	/**
	 * Read a k.Actor specification from a stream.
	 * 
	 * @param file
	 * @return the actor specification
	 */
	IKActorsBehavior declare(InputStream file);

}