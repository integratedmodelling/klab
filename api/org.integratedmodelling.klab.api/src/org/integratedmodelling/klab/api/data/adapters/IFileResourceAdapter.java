package org.integratedmodelling.klab.api.data.adapters;

/**
 * This interface tags those resource adapters that handle physical files
 * exclusively. It is important to use this as the UI for resource creation will
 * either require file upload or not show the adapter when creating resources by
 * hand.
 * 
 * @author ferdinando.villa
 *
 */
public interface IFileResourceAdapter extends IResourceAdapter {

}
