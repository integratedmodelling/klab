package org.integratedmodelling.controlcenter.api;

/**
 * The local instance of a product.
 * 
 * @author Ferd
 *
 */
public interface IInstance {

	enum Status {
		RUNNING, STOPPED, WAITING, ERROR
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

	boolean isRunning();

	/**
	 * Start the instance, returning immediately. A true return value means that the
	 * instance has been started correctly and is either in WAITING, RUNNING or
	 * ERROR state; it does not mean that it is running. A false return value means
	 * that the instance could not be started, because of a corrupted product or
	 * some other issue.
	 * 
	 * @return
	 */
	boolean start();

	/**
	 * Stop the instance, returning immediately. The instance after this is called
	 * can be in any status. A false return value means the instance could not be
	 * stopped for any reason - corruption, already stopped etc. True means that
	 * shutdown has correctly begun.
	 * 
	 * @return
	 */
	boolean stop();

}
