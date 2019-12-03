package org.integratedmodelling.klab.data.storage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.DataType;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.components.localstorage.LocalStorageComponent;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Utils;

public class FileMappedStorage<T> extends AbstractAdaptiveStorage<T> implements AutoCloseable {

	RandomAccessFile storage = null;
	private Class<?> valueClass;
	boolean open = false;
	private long highestSliceIndex = -1;
	private DataType type;
	private MappedByteBuffer page0;
	private MappedByteBuffer page1;
	private long pageIndex0 = -1;
	private long pageIndex1 = -1;
	private File file;

	/*
	 * stuff below gets reused at each read or write. As we use linear, scalar
	 * addressing, the extent of each read or write is always (1,1).
	 */

	@Override
	public void close() {
		if (open) {
			try {
				storage.close();
				page0 = null;
				page1 = null;
				open = false;
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
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
				throw new IllegalStateException(
						"NetCDF-backed storage cannot use type " + valueClass.getCanonicalName());
			}
		}

		return type;
	}

	private Object getNodataValue() {
		switch (getDatatype()) {
		case BYTE:
			return Byte.MIN_VALUE;
		case DOUBLE:
			return Double.NaN;
		case FLOAT:
			return Float.NaN;
		case INT:
			return Integer.MIN_VALUE;
		case LONG:
			return Long.MIN_VALUE;
		case SHORT:
			return Short.MIN_VALUE;
		default:
			break;
		}
		throw new KlabInternalErrorException("NetCDF-backed storage: unexpected type");
	}

	private boolean isNodata(Object value) {
		switch (type) {
		case BYTE:
			return value instanceof Number && ((Number) value).byteValue() == Byte.MIN_VALUE;
		case DOUBLE:
			return value instanceof Number && Double.isNaN(((Number) value).doubleValue());
		case FLOAT:
			return value instanceof Number && Double.isNaN(((Number) value).floatValue());
		case INT:
			return value instanceof Number && ((Number) value).intValue() == Integer.MIN_VALUE;
		case LONG:
			return value instanceof Number && ((Number) value).longValue() == Long.MIN_VALUE;
		case SHORT:
			return value instanceof Number && ((Number) value).shortValue() == Short.MIN_VALUE;
		default:
			break;
		}
		throw new KlabInternalErrorException("NetCDF-backed storage: unexpected type");
	}

	public FileMappedStorage(IGeometry geometry, Class<?> valueClass) {
		super(geometry);
		this.valueClass = valueClass;
	}

	@Override
	protected void createBackendStorage(long pageIndex, T initialValue) {

		if (storage == null) {
			try {
				this.file = new File(Configuration.INSTANCE.getTemporaryDataDirectory() + File.separator + LocalStorageComponent.FILE_PREFIX
						+ NameGenerator.shortUUID() + ".dat");
				this.file.deleteOnExit();
				this.storage = new RandomAccessFile(this.file, "rw");
				this.page1 = this.storage.getChannel().map(FileChannel.MapMode.READ_WRITE, 0,
						getSliceSize() * getDatatype().size);
				this.pageIndex1 = pageIndex;
			} catch (Exception e) {
				throw new KlabIOException(e);
			}
		}

		Object val = initialValue;
		if (val == null) {
			val = getNodataValue();
		}

		for (long i = 0; i < getSliceSize(); i++) {
			put(val, page1, -1);
		}

		if (highestSliceIndex < pageIndex) {
			highestSliceIndex = pageIndex;
		}
	}

	private void put(Object val, MappedByteBuffer page, long offset) {

		if (val == null) {
			val = getNodataValue();
		}
		
		if (offset >= 0) {
			page.position((int) offset * type.size);
		}

		if (val instanceof Boolean) {
			val = Byte.valueOf((byte) (((Boolean) val) ? 1 : 0));
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

	/**
	 * Always return null for nodata, no matter what.
	 * 
	 * @param page
	 * @param offset
	 * @return
	 */
	private Object get(MappedByteBuffer page, long offset) {

		if (offset >= 0) {
			page.position((int) offset * type.size);
		}

		switch (type) {
		case BYTE:
			byte b = page.get();
			return b == Byte.MIN_VALUE ? null : (b == 1);
		case DOUBLE:
			double d = page.getDouble();
			return Double.isNaN(d) ? null : d;
		case FLOAT:
			float f = page.getFloat();
			return Float.isNaN(f) ? null : f;
		case INT:
			int i = page.getInt();
			return i == Integer.MIN_VALUE ? null : i;
		case LONG:
			long l = page.getLong();
			return l == Long.MIN_VALUE ? null : l;
		case SHORT:
			short s = page.getShort();
			return s == Short.MIN_VALUE ? null : s;
		default:
			break;
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	protected T getValueFromBackend(long offsetInSlice, long backendTimeSlice) {

		Object result = null;
		if (backendTimeSlice >= 0) {
			if (backendTimeSlice == this.pageIndex0) {
				result = get(page0, offsetInSlice);
			} else if (backendTimeSlice == this.pageIndex1) {
				result = get(page1, offsetInSlice);
			} else {
				// read directly from file
				if (backendTimeSlice > this.pageIndex1) {
					throw new IllegalStateException("file mapped storage: trying to read an unassigned value");
				}
				result = getDirect(offsetInSlice, backendTimeSlice);
			}
		}
		return result == null ? null : (isNodata(result) ? null : (T) result);
	}

	private Object getDirect(long offsetInSlice, long backendTimeSlice) {
		try {
			storage.seek((backendTimeSlice * getSliceSize() * type.size) + (offsetInSlice * type.size));
			switch (type) {
			case BYTE:
				return storage.readByte();
			case DOUBLE:
				return storage.readDouble();
			case FLOAT:
				return storage.readFloat();
			case INT:
				return storage.readInt();
			case LONG:
				return storage.readLong();
			case SHORT:
				return storage.readShort();
			default:
				break;
			}
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
		return null;
	}

	private void putDirect(Object value, long offsetInSlice, long backendTimeSlice) {
		
		if (value == null) {
			value = getNodataValue();
		}
		
		try {
			storage.seek((backendTimeSlice * getSliceSize() * type.size) + (offsetInSlice * type.size));
			switch (type) {
			case BYTE:
				storage.writeByte(((Byte) value).byteValue());
				break;
			case DOUBLE:
				storage.writeDouble(((Double) value).doubleValue());
				break;
			case FLOAT:
				storage.writeFloat(((Float) value).floatValue());
				break;
			case INT:
				storage.writeInt(((Integer) value).intValue());
				break;
			case LONG:
				storage.writeLong(((Long) value).longValue());
				break;
			case SHORT:
				storage.writeShort(((Short) value).shortValue());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	protected void setValueIntoBackend(T value, long offsetInSlice, long backendTimeSlice) {

		Object val = value == null ? getNodataValue() : value;

		if (backendTimeSlice == this.pageIndex1) {
			put(val, this.page1, offsetInSlice);
		} else if (backendTimeSlice == this.pageIndex0) {
			put(val, this.page0, offsetInSlice);
		} else {
			if (backendTimeSlice > this.pageIndex1) {
				throw new IllegalStateException("file mapped storage: trying to read an unassigned value");
			}
			putDirect(val, offsetInSlice, backendTimeSlice);
		}
	}

	@Override
	protected void duplicateBackendSlice(long sliceToCopy, long newSliceIndex) {

		try {
			if (this.page1 != null) {
				this.page1.force();
				MappedByteBuffer newPage = this.storage.getChannel().map(FileChannel.MapMode.READ_WRITE,
						newSliceIndex * getSliceSize() * getDatatype().size, getSliceSize() * getDatatype().size);
				this.page1.rewind();
				newPage.put(this.page1);
				this.pageIndex0 = sliceToCopy;
				this.pageIndex1 = newSliceIndex;
				this.page0 = this.page1;
				this.page1 = newPage;
			}

		} catch (IOException e) {
			throw new KlabIOException(e);
		}

		if (highestSliceIndex < newSliceIndex) {
			highestSliceIndex = newSliceIndex;
		}
	}

	public int getBackendSliceCount() {
		return (int) highestSliceIndex + 1;
	}

	public int getSliceCount() {
		return sliceCount();
	}

	@Override
	public Type getType() {
		return Utils.getArtifactType(valueClass);
	}

}
