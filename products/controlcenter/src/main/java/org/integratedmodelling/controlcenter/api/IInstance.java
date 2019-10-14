package org.integratedmodelling.controlcenter.api;

import java.util.List;
import java.util.function.Consumer;

import org.integratedmodelling.controlcenter.product.Distribution;
import org.integratedmodelling.controlcenter.runtime.EngineInstance.EngineInfo;

/**
 * The local instance of a product.
 * 
 * @author Ferd
 *
 */
public interface IInstance {

	enum Status {
		UNKNOWN, RUNNING, STOPPED, WAITING, ERROR
	}

	/**
	 * The product this is an instance of.
	 * 
	 * @return
	 */
	IProduct getProduct();
	
	/**
	 * The current status of the instance.
	 * 
	 * @return
	 */
	Status getStatus();

	/**
	 * Get all the builds available on the local machine. The list may
	 * differ from those available from the network.
	 * 
	 * @return
	 */
	List<Integer> getInstalledBuilds();

	/**
	 * Download if necessary, using the previous build to pre-populate to minimize
	 * the download size. If a listener is passed, use it to notify all events.
	 * 
	 * @param build
	 * @param listener
	 * @return
	 */
	Distribution download(int build, Distribution.SyncListener listener);
	
	/**
	 * Start the instance, returning immediately. A true return value means that the
	 * instance has been started correctly and is either in WAITING, RUNNING or
	 * ERROR state; it does not mean that it is running. A false return value means
	 * that the instance could not be started, because of a corrupted product or
	 * some other issue.
	 * 
	 * @param listener 
	 * 
	 * @return
	 */
	boolean start(int build);

	/**
	 * Stop the instance, returning immediately. The instance after this is called
	 * can be in any status. A false return value means the instance could not be
	 * stopped for any reason - corruption, already stopped etc. True means that
	 * shutdown has correctly begun.
	 * 
	 * @return
	 */
	boolean stop();
	
	/**
	 * Start listening for changes in status. Can be called at any time. For now 
	 * there is no corresponding stop() method.
	 * 
	 * @param listener
	 */
	void pollStatus(Consumer<Status> listener);

}
