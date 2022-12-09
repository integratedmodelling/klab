package org.integratedmodelling.klab.rest;

public class ObservationAssetStatistics {

    public enum Type {
        ResolvedObservable,
        Model,
        Resource,
        Export
    }
    
    private Type type;
}
