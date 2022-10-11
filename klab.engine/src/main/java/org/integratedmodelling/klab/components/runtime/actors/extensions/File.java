package org.integratedmodelling.klab.components.runtime.actors.extensions;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.exceptions.KlabActorException;

/**
 * Filestream proxy for k.Actors.
 * 
 * @author Ferd
 *
 */
public class File {

    java.io.File file = null;
    String filename = null;

    public File(String filename) {
        this.filename = filename;
        file = new java.io.File(filename);
    }

    public File(String filename, Map<?, ?> options) {
        this.filename = filename;
        if (options.get("export") instanceof Boolean && ((Boolean) options.get("export"))) {
            this.file = Configuration.INSTANCE.getExportFile(filename);
        } else if (options.get("tmp") instanceof Boolean && ((Boolean) options.get("tmp"))) {
            try {
                this.file = Files.createTempFile((options.containsKey("prefix") ? options.get("prefix").toString() : "klab"),
                        "." + filename).toFile();
            } catch (IOException e) {
                throw new KlabActorException(e);
            }
        } else {
            file = new java.io.File(filename);
        }
    }

    public boolean exists() {
        return this.file.exists();
    }

    public void setProperty(String key, Object value) {
        switch(key) {
        case "export":
            this.file = Configuration.INSTANCE.getExportFile(this.filename);
            break;
        case "tmp":
            try {
                this.file = Files.createTempFile("klab", "." + this.filename).toFile();
            } catch (IOException e) {
                throw new KlabActorException(e);
            }
            break;
        }
    }
}
