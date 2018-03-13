package org.integratedmodelling.klab.utils.collections;

import java.io.Closeable;

/* For Copyright and License see LICENSE.txt and COPYING.txt in the root directory */
/**
 * 
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import sun.misc.Unsafe;

//import com.nerdscentral.sython.SFPL_RuntimeException;

/**
 * @author a1t
 * 
 */
public class SFData implements Serializable, Closeable
{

    private static Unsafe getUnsafe()
    {
        try
        {
            Field f = Unsafe.class.getDeclaredField("theUnsafe"); //$NON-NLS-1$
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static final Unsafe unsafe = getUnsafe();

    private static class ByteBufferWrapper
    {
        public long       address;
        @SuppressWarnings("unused")
        // buffer is used to hold a reference to the buffer
        // but its data is read via unsafe
        public ByteBuffer buffer;

        public ByteBufferWrapper(ByteBuffer b) throws NoSuchMethodException, SecurityException, IllegalAccessException,
                        IllegalArgumentException, InvocationTargetException
        {
            Method addM = b.getClass().getMethod("address"); //$NON-NLS-1$
            addM.setAccessible(true);
            address = (long) addM.invoke(b);
            buffer = b;
        }
    }

    public static class NotCollectedException extends Exception
    {
        private static final long serialVersionUID = 1L;
    }

    // Directory to send swap files to
    private static final String                             SONIC_FIELD_TEMP = "sonicFieldTemp";             //$NON-NLS-1$
    private static final long                               CHUNK_LEN        = 1024 * 1024;
    private static final long                               CHUNK_SHIFT      = 20;
    private static final long                               CHUNK_MASK       = CHUNK_LEN - 1;
    private static final long                               serialVersionUID = 1L;
    private final int                                       length;
    private volatile boolean                                killed           = false;
    private static File[]                                   coreFile;
    private static RandomAccessFile[]                       coreFileAccessor;
    private ByteBufferWrapper[]                             chunks;
    private static FileChannel[]                            channelMapper;
    private static int                                      fileRoundRobbin  = 0;
    private final NotCollectedException                     javaCreated;
//    private final String                                    pythonCreated;
    private final long                                      chunkIndex;
    // shadows chunkIndex for memory management as chunkIndex must
    // be final to enable optimisation
    private long                                            allocked;
    private static ConcurrentLinkedDeque<ByteBufferWrapper> freeChunks       = new ConcurrentLinkedDeque<>();

    private static class ResTracker
    {
        public ResTracker(NotCollectedException javaCreated, String pythonCreated)
        {
            j = javaCreated;
            p = pythonCreated;
        }

        NotCollectedException j;
        String                p;
    }

    private static ConcurrentHashMap<SFData, ResTracker> resourceTracker = new ConcurrentHashMap<>();

    static
    {
        File tempDir[];
        String tempEnv = System.getProperty(SONIC_FIELD_TEMP);
        String[] tdNames = null;
        if (tempEnv == null)
        {
            tdNames = new String[] { System.getProperty("java.io.tmpdir") }; //$NON-NLS-1$
        }
        else
        {
            tdNames = tempEnv.split(","); //$NON-NLS-1$
        }
        int nTemps = tdNames.length;
        tempDir = new File[nTemps];
        coreFile = new File[nTemps];
        coreFileAccessor = new RandomAccessFile[nTemps];
        channelMapper = new FileChannel[nTemps];
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int index = 0;
        for (String tdName : tdNames)
        {
            tempDir[index] = new File(tdName);
            try
            {
                coreFile[index] = File.createTempFile("SonicFieldSwap" + pid, ".mem", tempDir[index]);  //$NON-NLS-1$//$NON-NLS-2$            
                coreFile[index].deleteOnExit();
                // Now create the actual file
                coreFileAccessor[index] = new RandomAccessFile(coreFile[index], "rw"); //$NON-NLS-1$
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            channelMapper[index] = coreFileAccessor[index].getChannel();
            ++index;
        }
    }

    public static void dumpNotCollected()
    {
        if (resourceTracker.size() != 0)
        {
            System.out.println("SFData Resources Not Properly Closed"); //$NON-NLS-1$
            for (Entry<SFData, ResTracker> x : resourceTracker.entrySet())
            {
                printResourceError("Allocation positions ", x.getValue()); //$NON-NLS-1$
            }
        }
    }

    private void makeMap(long size) throws IOException, SecurityException, IllegalArgumentException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException
    {
        long countDown = size;
        int chunkCount = 0;
        while (countDown > 0)
        {
            ++chunkCount;
            countDown -= CHUNK_LEN;
        }

        countDown = size;
        chunks = new ByteBufferWrapper[chunkCount];
        chunkCount = 0;
        while (countDown > 0)
        {
            ByteBufferWrapper chunk = freeChunks.poll();
            if (chunk == null) break;
            chunks[chunkCount] = chunk;
            ++chunkCount;
            countDown -= CHUNK_LEN;
        }
        if (countDown > 0)
        {
            synchronized (coreFile)
            {
                while (countDown > 0)
                {
                    long from = coreFile[fileRoundRobbin].length();
                    ByteBuffer chunk = channelMapper[fileRoundRobbin].map(MapMode.READ_WRITE, from, CHUNK_LEN);
                    chunk.order(ByteOrder.nativeOrder());
                    chunks[chunkCount] = new ByteBufferWrapper(chunk);
                    ++chunkCount;
                    from += CHUNK_LEN;
                    countDown -= CHUNK_LEN;
                    if (++fileRoundRobbin >= coreFile.length) fileRoundRobbin = 0;
                }
            }
        }
        // now we have the chunks we get the address of the underlying memory
        // of each and place that in the off heap lookup so we no longer reference
        // them via objects but purely as raw memory
        long offSet = 0;
        for (ByteBufferWrapper chunk : chunks)
        {
            unsafe.putAddress(chunkIndex + offSet, chunk.address);
            offSet += 8;
        }
    }

//    @Override
    public void clear()
    {
        for (long i = 0; i < chunks.length; ++i)
        {
            long address = unsafe.getAddress(chunkIndex + (i << 3l));
            unsafe.setMemory(address, CHUNK_LEN << 3l, (byte) 0);
        }
    }

//    @Override
    public void release()
    {
        for (ByteBufferWrapper chunk : chunks)
        {
            freeChunks.push(chunk);
        }
        chunks = null;
        resourceTracker.remove(this);
        synchronized (unsafe)
        {
            if (allocked != 0) unsafe.freeMemory(chunkIndex);
            allocked = 0;
        }
    }

    private SFData(int l)
    {
        allocked = 0;
        try
        {
            try
            {
                // Allocate the memory for the index - final so do it here
                long size = (1 + ((l << 3) >> CHUNK_SHIFT)) << 3;
                allocked = chunkIndex = unsafe.allocateMemory(size);
                if (allocked == 0)
                {
                    throw new RuntimeException("Out of memory allocating " + size); //$NON-NLS-1$
                }
                makeMap(l << 3l);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            this.length = l;
            NotCollectedException nc = new NotCollectedException();
            nc.fillInStackTrace();
//            pythonCreated = getPythonStack();
            resourceTracker.put(this, new ResTracker(nc, "no"));
            javaCreated = nc;
        }
        catch (Throwable t)
        {
            if (allocked != 0)
            {
                unsafe.freeMemory(allocked);
                allocked = 0;
            }
            throw t;
        }
    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#isKilled()
     */
//    @Override
    public boolean isKilled()
    {
        return killed;
    }

    public final static SFData build(int l)
    {
        return new SFData(l);
    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#replicate()
     */
//    @Override
    public final SFData replicate()
    {
        System.out.println("Replicate"); //$NON-NLS-1$
        SFData data1 = new SFData(this.getLength());
        for (int i = 0; i < this.getLength(); ++i)
        {
            data1.setSample(i, this.getSample(i));
        }
        return data1;
    }

    private long getAddress(long index)
    {
        long bytePos = index << 3; 
        long pos = bytePos & CHUNK_MASK;
        long bufPos = (bytePos - pos) >> CHUNK_SHIFT;
        long address = chunkIndex + (bufPos << 3);
        return unsafe.getAddress(address) + pos;
    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#getSample(int)
     */
//    @Override
    public final double getSample(long index)
    {
        return unsafe.getDouble(getAddress(index));
    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#setSample(int, double)
     */
//    @Override
    public final double setSample(long index, double value)
    {
        unsafe.putDouble(getAddress(index), value);
        return value;
    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#getLength()
     */
//    @Override
    public final int getLength()
    {
        return this.length;
    }

    public static final SFData build(float[] input)
    {
      SFData data = SFData.build(input.length);
        for (int i = 0; i < input.length; ++i)
        {
            data.setSample(i, input[i]);
        }
        return data;
    }

    public static SFData build(double[] input)
    {
      SFData data = SFData.build(input.length);
        for (int i = 0; i < input.length; ++i)
        {
            data.setSample(i, input[i]);
        }
        return data;
    }

    public static final SFData build(double[] input, int j)
    {
        SFData data = SFData.build(j);
        for (int i = 0; i < j; ++i)
        {
            data.setSample(i, input[i]);
        }
        return data;
    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#setAt(int, com.nerdscentral.audio.SFData)
     */
//    @Override
    public void setAt(int pos, SFData data2) 
    {
        int pos2 = pos;
        if (pos2 + data2.getLength() > length)
        {
            System.out.println("Vals " + pos2 + "SFData.10" + data2.getLength() + "SFData.11" + length); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            throw new KlabRuntimeException("SFData.0"); //$NON-NLS-1$
        }
        long end = pos2 + data2.getLength();
        for (int index = pos2; index < end; ++index)
        {
            setSample(index, data2.getSample(index - pos2));
        }
    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#setFrom(int, com.nerdscentral.audio.SFData)
     */
//    @Override
    public void setFrom(int pos, SFData data2) 
    {
        int pos2 = pos;
        if (pos2 + length > data2.getLength()) throw new KlabRuntimeException("SFData.1"); //$NON-NLS-1$
        for (int index = 0; index < length; ++index)
        {
            setSample(index, data2.getSample(index - pos2));
        }
    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#toString()
     */

//    @Override
    public String toString()
    {
        return "SFData.2" + length + "SFData.3"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public static SFData realiseSwap(SFData in)
    {
        if (in instanceof SFData) return (SFData) in;
        int len = in.getLength();
        SFData output = SFData.build(len);
        for (int i = 0; i < len; ++i)
        {
            output.setSample(i, in.getSample(i));
        }
        return output;
    }

    public static SFData realise(SFData in)
    {
        if (in instanceof SFData) return (SFData) in;
        int len = in.getLength();
        SFData output = SFData.build(len);
        for (int i = 0; i < len; ++i)
        {
            output.setSample(i, in.getSample(i));
        }
        return output;
    }

//    @Override
//    public void finalize()
//    {
//        if (referenceCount.get() != 0)
//        {
//            System.err.println(Messages.getString("SFData.13")); //$NON-NLS-1$
//            System.err.println(pythonCreated);
//            javaCreated.printStackTrace();
//        }
//    }

    /* (non-Javadoc)
     * @see com.nerdscentral.audio.SFSignal#close()
     */
//    @Override
//    public void close() throws RuntimeException
//    {
//        try
//        {
//            super.close();
//        }
//        catch (RuntimeException e)
//        {
//            printResourceError(Messages.getString("SFData.16"), new ResTracker(this.javaCreated, this.pythonCreated)); //$NON-NLS-1$
//            throw e;
//        }
//    }

    private static void printResourceError(String message, ResTracker data)
    {
        System.out.println(message);
        System.out.println("SFData.17"); //$NON-NLS-1$
        for (StackTraceElement x : data.j.getStackTrace())
        {
            if (x.getClassName().contains(".reflect.")) break; //$NON-NLS-1$
            System.out.println(x.getClassName() + "!" + x.getFileName() + ":" + x.getLineNumber()); //$NON-NLS-1$ //$NON-NLS-2$
        }
        System.out.println("SFData.18"); //$NON-NLS-1$
        System.out.println(data.p);
    }

//    @Override
    public double[] getDataInternalOnly()
    {
        long length = getLength();
        if (length > Integer.MAX_VALUE)
        {
            throw new RuntimeException("SFData.5" + length); //$NON-NLS-1$
        }
        int len = (int) length;
        double[] ret = new double[len];
        for (int index = 0; index < len; ++len)
        {
            ret[index] = getSample(index);
        }
        return ret;
    }

    @Override
    public void close() throws IOException {
      release();
    }
}