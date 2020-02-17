package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.integratedmodelling.kactors.api.IKActorBehavior;

public interface IActorsService {

	/**
	 * Read a k.Actor specification from a URL.
	 * 
	 * @param url
	 * @return the actor
	 */
	IKActorBehavior declare(URL url);

	/**
	 * Read a k.Actor specification from a file.
	 * 
	 * @param file
	 * @return the actor
	 */
	IKActorBehavior declare(File file);

	/**
	 * Read a k.Actor specification from a stream.
	 * 
	 * @param file
	 * @return the actor
	 */
	IKActorBehavior declare(InputStream file);

}