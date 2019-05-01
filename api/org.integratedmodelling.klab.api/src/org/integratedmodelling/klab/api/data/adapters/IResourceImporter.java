package org.integratedmodelling.klab.api.data.adapters;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.Triple;

/**
 * Handles import and export for a resource.
 * 
 * @author ferdinando.villa
 *
 */
public interface IResourceImporter {

    /**
     * Import all the resources that can be resolved through the passed import
     * location string. If there are no usable resources or the location is
     * unrecognized, an empty collection should be returned without error.
     * 
     * @param importLocation
     * @param userData
     * @param monitor 
     * @return builders for all found resources, possibly with errors.
     */
    Collection<IResource.Builder> importResources(String importLocation, IParameters<String> userData, IMonitor monitor);

    /**
     * Import the resources from the passed location into an existing resource.
     * 
     * @param importLocation
     * @param target
     * @param monitor
     * @return
     */
    IResource importIntoResource(String importLocation, IResource target, IMonitor monitor);
    
    /**
     * Check if the passed location (file, URL or whatever) can be handled. In this
     * case 'unknown' is a possible response, in which case we can return true as
     * long as {@link #importResources(String, IParameters)} behaves gracefully.
     * 
     * @param importLocation
     * @param userData
     * @return true if we recognize this URL or we don't know and want to try
     *         importing.
     */
    boolean canHandle(String importLocation, IParameters<String> userData);

    /**
     * Check if the resource can handle the import of the passed string.
     * 
     * @param importLocation
     * @return
     */
    boolean resourceCanHandle(IResource resource, String importLocation);
    
    /**
     * Return a list of triples ID/Description/FileExtension for all the formats of file export that
     * this adapter supports for the passed observation.
     * 
     * @param observation
     * @return
     */
    Collection<Triple<String, String, String>> getExportCapabilities(IObservation observation);

    /**
     * Export the passed observation to the passed file using the passed format. The
     * format will always be one of those returned by {@link #getExportCapabilities(IObservation)} for
     * the same observation.
     * 
     * @param file
     * @param observation
     * @param locator TODO
     * @param format
     * @param monitor TODO
     * @return the file if successful, null otherwise. The file should be reassigned as some output
     * formats may require to output an archive or another file different from the requested. 
     * Should only throw exceptions when the file can't be written or any parameters are null or invalid.
     */
    File exportObservation(File file, IObservation observation, ILocator locator, String format, IMonitor monitor);

    /**
     * Return a map of ID/Description for all the formats of file export that
     * this adapter supports for the passed resource. Pass a null resource to
     * obtain export formats that are valid for any (viable) resource for this
     * adapter.
     * 
     * @param observation or null. If null, this method must return all universal export formats.
     * @return
     */
    Map<String, String> getExportCapabilities(IResource resource);

    /**
     * Export the passed observation to the passed file using the passed format. The
     * format will always be one of those returned by {@link #getExportCapabilities(IObservation)} for
     * the same observation.
     * 
     * @param file
     * @param observation
     * @param format
     * @return true if successful. Should only throw exceptions when the file can't be written or any
     * parameters are null or invalid.
     */
    boolean exportResource(File file, IResource resource, String format);

}
