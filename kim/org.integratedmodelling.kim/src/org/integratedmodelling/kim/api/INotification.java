package org.integratedmodelling.kim.api;

import java.util.logging.Level;

public interface INotification {
    
    // NAAAH replace with action (see provenance) and separate from type
    public enum Type {
        created,
        deletedFile,
        error,
        finalizedUpload,
        importNotes,
        movedFile,
        prunedBrokenLink,
        uploaded,
        updatedMetadata,
    }
    
    Level getLevel();
    
    long getTimestamp();
    
//    String getUser();
//    
//    String getNotes();

    Type getType();
    
}
