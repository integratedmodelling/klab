package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.integratedmodelling.kactors.api.IKActorsBehavior;

public interface IActorsService {

	/**
	 * Read a k.Actor specification from a URL.
	 * 
	 * @param url
	 * @return the actor
	 */
	IKActorsBehavior declare(URL url);

	/**
	 * Read a k.Actor specification from a file.
	 * 
	 * @param file
	 * @return the actor
	 */
	IKActorsBehavior declare(File file);

	/**
	 * Read a k.Actor specification from a stream.
	 * 
	 * @param file
	 * @return the actor
	 */
	IKActorsBehavior declare(InputStream file);

}