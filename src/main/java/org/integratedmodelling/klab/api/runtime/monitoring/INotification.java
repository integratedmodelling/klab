package org.integratedmodelling.klab.api.runtime.monitoring;

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
    
    long getTimestamp();
    
    String getUser();
    
    String getNotes();

    Type getType();
    
}
