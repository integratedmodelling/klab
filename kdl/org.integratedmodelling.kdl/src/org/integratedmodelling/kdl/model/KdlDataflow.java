package org.integratedmodelling.kdl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlContextualizer;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kdl.kdl.ActorDefinition;
import org.integratedmodelling.kdl.kdl.Function;
import org.integratedmodelling.kdl.kdl.Model;

public class KdlDataflow extends KdlStatement implements IKdlDataflow {

    private static final long serialVersionUID = -5880984598966851266L;

    List<IKdlContextualizer>  scale            = new ArrayList<>();
    List<IKdlActuator>        actors           = new ArrayList<>();

    String                    worldview;
    String                    klabVersion;
    String                    version;
    String                    endpoint;
    String                    packageName;
    String                    geometry;

    public KdlDataflow(Model o) {
        super(o);
        this.version = o.getVersion();
        this.klabVersion = o.getKlabVersion();
        this.geometry = o.getGeometry();
        this.endpoint = o.getEndpoint();
        this.packageName = o.getPackage();

        for (Function ctx : o.getScale()) {
            scale.add(new KdlContextualizer(ctx));
        }

        for (ActorDefinition actor : o.getActors()) {
            actors.add(new KdlActuator(actor));
        }
    }

    @Override
    public Collection<IKdlActuator> getActuators() {
        return actors;
    }

    @Override
    public List<IKdlContextualizer> getScale() {
        return scale;
    }

    public void setScale(List<IKdlContextualizer> scale) {
        this.scale = scale;
    }

    @Override
    public String getWorldview() {
        return worldview;
    }

    public void setWorldview(String worldview) {
        this.worldview = worldview;
    }

    @Override
    public String getKlabVersion() {
        return klabVersion;
    }

    public void setKlabVersion(String klabVersion) {
        this.klabVersion = klabVersion;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        String ret = "<dataflow [";
        int i = 0;
        for (IKdlActuator actor : actors) {
            ret += (i == 0 ? "" : ", ") + actor;
            i++;
        }
        return ret + "]>"; 
    }
    
}
