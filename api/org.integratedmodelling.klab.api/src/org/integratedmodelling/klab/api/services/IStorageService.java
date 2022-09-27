package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Handles temporary storage and provides a release mechanism and a reaper queue to handle temporary
 * storage more intelligently.
 * 
 * @author Ferd
 *
 */
public interface IStorageService {

    /**
     * Request a temporary file name. Record the file so that we can release when no longer needed.
     * If release isn't called on the result file, the file will be deleted at the closing of the
     * JVM. No file is actually created.
     * 
     * @param prefix
     * @param extensionNoDot
     * @return
     */
    File requestFile(String prefix, String extensionNoDot);

    /**
     * Request a temporary file name. Record the file so that we can release when no longer needed.
     * If release isn't called on the result file, the file will be deleted at the closing of the
     * JVM. No file is actually created.
     * 
     * @param prefix
     * @param extensionNoDot
     * @return
     */
    RandomAccessFile requestRandomAccessFile(String prefix, String extensionNoDot);

    void release(File file);

}
