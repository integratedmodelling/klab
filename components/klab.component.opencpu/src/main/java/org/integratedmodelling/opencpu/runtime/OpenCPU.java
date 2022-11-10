package org.integratedmodelling.opencpu.runtime;

/**
 * The Java peer for the OpenCPU runtime installed in the machine running the adapter.
 * 
 * @author Ferd
 *
 */
public class OpenCPU {

    private boolean isOnline = false;
    
    public OpenCPU(String url) {
        
    }
    
    boolean isOnline() {
        return isOnline;
    }
    
}
