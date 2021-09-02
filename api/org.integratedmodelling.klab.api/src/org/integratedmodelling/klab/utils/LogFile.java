package org.integratedmodelling.klab.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * File appender that opens and closes the file at every write. Use for simplicity when performance
 * is not an issue but the execution flow is complex.
 * 
 * @author Ferd
 *
 */
public class LogFile {

    private File fname;

    public LogFile(File file) {
        this.fname = file;
        if (file.exists()) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                // ma va a cagher
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(fname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LogFile other = (LogFile) obj;
        return Objects.equals(fname, other.fname);
    }

    public void print(String s) {
        try {
            FileWriter writer = new FileWriter(fname, true);
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new KlabException(e);
        }
    }

    public void println(String s) {
        print(s +  System.getProperty("line.separator"));
    }

    public File getFile() {
        return fname;
    }

}