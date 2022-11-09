package org.integratedmodelling.klab;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;

import org.integratedmodelling.klab.api.services.IStorageService;

public enum Storage implements IStorageService {

    INSTANCE;
    
    private long STORAGE_CLEANER_INTERVAL_SECONDS = 100000l;
    
    private Timer timer = new Timer("temporary storage cleaning");

    private Storage() {

        Services.INSTANCE.registerService(this, IStorageService.class);
        

        // schedule the reaper queue
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cleanup();
            }
        }, STORAGE_CLEANER_INTERVAL_SECONDS * 1000, STORAGE_CLEANER_INTERVAL_SECONDS * 1000);
    }
    
    @Override
    public File requestFile(String prefix, String extensionNoDot) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public RandomAccessFile requestRandomAccessFile(String prefix, String extensionNoDot) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void release(File file) {
        // TODO Auto-generated method stub
        
    }
    
    private void cleanup() {
        
    }
    
}
