package org.integratedmodelling.klab.data.storage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.DataType;
import org.integratedmodelling.klab.components.localstorage.LocalStorageComponent;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Utils;

/**
 * Just a chunk of memory that uses a backing memory-mapped file and is addressed linearly.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class BasicFileMappedStorage<T> implements AutoCloseable {

    File file;
    RandomAccessFile storage = null;
    DataType type;
    private Class<?> valueClass;
    private MappedByteBuffer page;

    public BasicFileMappedStorage(long size, Class<? extends T> cls) {
        this.valueClass = cls;
        try {
            this.file = new File(Configuration.INSTANCE.getTemporaryDataDirectory() + File.separator
                    + LocalStorageComponent.FILE_PREFIX + NameGenerator.shortUUID() + ".dat");
            this.storage = new RandomAccessFile(this.file, "rw");
            FileUtils.forceDeleteOnExit(this.file);
            this.page = this.storage.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, size * getDatatype().size);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            storage.close();
            page = null;
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    private DataType getDatatype() {

        if (type == null) {

            if (valueClass == Double.class) {
                // Double.NaN for nodata
                type = DataType.DOUBLE;
            } else if (valueClass == Short.class) {
                // Short.MIN_VALUE for nodata
                type = DataType.SHORT;
            } else if (valueClass == Integer.class) {
                // Integer.MIN_VALUE for nodata
                type = DataType.INT;
            } else if (valueClass == Long.class) {
                // Long.MIN_VALUE for nodata
                type = DataType.LONG;
            } else if (valueClass == Float.class) {
                // Float.NaN for nodata
                type = DataType.FLOAT;
            } else if (valueClass == Boolean.class) {
                // 0 for false, 1 for true, Byte.MIN_VALUE for nodata
                type = DataType.BYTE;
            } else {
                throw new IllegalStateException("file-backed storage cannot use type " + valueClass.getCanonicalName());
            }
        }

        return type;
    }
    
    @SuppressWarnings("unchecked")
    public void set(T val, long offset) {

        if (offset >= 0) {
            page.position((int) offset * type.size);
        }

        if (val instanceof Boolean) {
            val = (T)Byte.valueOf((byte) (((Boolean) val) ? 1 : 0));
        }

        switch (type) {
        case BYTE:
            page.put(((Byte) val).byteValue());
            break;
        case DOUBLE:
            page.putDouble(((Number) val).doubleValue());
            break;
        case FLOAT:
            page.putFloat(((Number) val).floatValue());
            break;
        case INT:
            page.putInt(((Number) val).intValue());
            break;
        case LONG:
            page.putLong(((Number) val).longValue());
            break;
        case SHORT:
            page.putShort(((Number) val).shortValue());
            break;
        default:
            break;
        }
    }
    
    public T get(long offset) {
        
        if (offset >= 0) {
            page.position((int) offset * type.size);
        }

        switch (type) {
        case BYTE:
            byte b = page.get();
            return Utils.asType(b, valueClass);
        case DOUBLE:
            double d = page.getDouble();
            return Utils.asType(d, valueClass);
        case FLOAT:
            float f = page.getFloat();
            return Utils.asType(f, valueClass);
        case INT:
            int i = page.getInt();
            return Utils.asType(i, valueClass);
        case LONG:
            long l = page.getLong();
            return Utils.asType(l, valueClass);
        case SHORT:
            short s = page.getShort();
            return Utils.asType(s, valueClass);
        default:
            break;
        }
        return null;
    }

}
