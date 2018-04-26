package org.integratedmodelling.klab.client.utils.codegen;


import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Copied from https://github.com/medallia/javaone2016/tree/master/src/main/java/com/medallia/codegen
 * See above for copyright and license
 * @author Juan Cruz Nores https://github.com/juancn
 *
 */
/** Simple wrapper over javac to compile and class-load a single class */
public class SimpleJavaCompiler {

    private static final String CLASSPATH = System.getProperty("java.class.path");

    /** A javac instance */
    private final JavaCompiler compiler;
    /** The Standard file manager for the compiler */
    private final StandardJavaFileManager standardJavaFileManager;

    /** We keep an initialized copy for each thread. */
    private static final ThreadLocal<SimpleJavaCompiler> BY_THREAD = new ThreadLocal<SimpleJavaCompiler>() {
        @Override
        protected SimpleJavaCompiler initialValue() {
            return new SimpleJavaCompiler();
        }
    };

    /** Create a new compiler */
    private SimpleJavaCompiler() {
        compiler = ToolProvider.getSystemJavaCompiler();
        standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
    }

    /**
     * Compiles and class-loads a single class from the specified Java source code.
     *
     * To amortize the cost of initializing a JavaCompiler, underlying instances are cached
     * on a per-thread basis. It is recommended that callers of this method reuse threads
     * for increased performance and reduced memory consumption.
     *
     * @param source a String containing Java source code to generate the class
     * @return the compiled and class-loaded class
     */
    public static <T> Class<? extends T> compile(Class<T> superClass, String source) {
        return BY_THREAD.get().compile0(superClass, source);
    }


    /**
     * Compiles and class-loads a single class from the specified Java source code.
     *
     * To amortize the cost of initializing a JavaCompiler, underlying instances are cached
     * on a per-thread basis. It is recommended that callers of this method reuse threads
     * for increased performance and reduced memory consumption.
     *
     * @param sourceGen a String containing Java source code to generate the class
     * @return the compiled and class-loaded class
     */
    public static <T> Class<? extends T> compile(Class<T> superClass, Consumer<JavaCodeGenerator> sourceGen) {
        final JavaCodeGenerator cg = new JavaCodeGenerator();
        sourceGen.accept(cg);

        final StringWriter sw = new StringWriter();
        cg.generate(sw);

        final Class<? extends T> compiledClass = compile(superClass, sw.toString());
        dump(sw, compiledClass);
        return compiledClass;
    }

    public static volatile boolean dump = Boolean.getBoolean("compiler.java.dump");

    public static volatile String dumpDir = System.getProperty("compiler.java.dump.dir");

    private static void dump(StringWriter sw, Class<?> compiledClass) {
        if (dump) {
            System.out.println(sw);
        }
        if (dumpDir != null) {
            try {
                Files.write(Paths.get(dumpDir, compiledClass.getCanonicalName().replace('.', File.separatorChar) + ".java"), sw.toString().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("unable to dump source file", e);
            }
        }
    }


    /**
     * Compiles and class-loads a single class from the specified Java source code.
     * @param source a String containing Java source code to generate the class
     * @return the compiled and class-loaded class
     */
    private <T> Class<? extends T> compile0(Class<T> superClass, String source) {
        final List<JavaFileObject> compilationUnits = Collections.singletonList(new SourceFile(toUri("Generated"), source));
        final FileManager fileManager = new FileManager(standardJavaFileManager);
        final StringWriter output = new StringWriter();
        final CompilationTask task = compiler.getTask(output, fileManager, null, Arrays.asList("-g", "-proc:none", "-classpath", CLASSPATH), null, compilationUnits);

        if (!task.call()) {
            throw new IllegalArgumentException("Compilation failed:\n" + output + "\n Source code: \n" + source);
        }

        int classCount = fileManager.output.size();
        // 2nd condition to work around the case where additional "GuardedBy" generated in TA Spark tagging
        if (classCount == 1 || (classCount > 0 && "CompiledStage"
                .equals(fileManager.output.get(0).getName()))) {
            return DiscardableClassLoader.classFromBytes(superClass, null, fileManager.output.get(0).outputStream.toByteArray());
        }
        throw new IllegalArgumentException("Compilation yielded an unexpected number of classes: " + classCount);
    }

    /** Represents a source file whose contents is loaded from a String */
    private static class SourceFile extends SimpleJavaFileObject {
        private final String contents;

        private SourceFile(URI uri, String contents) {
            super(uri, Kind.SOURCE);
            this.contents = contents;
        }

        @Override
        public String getName() { return uri.getRawSchemeSpecificPart(); }

        /** Ignore the file name for public classes */
        @Override
        public boolean isNameCompatible(String simpleName, Kind kind) { return true; }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return contents;
        }
    }

    /** A compiled class file. It's content is stored in a ByteArrayOutputStream. */
    private static class ClassFile extends SimpleJavaFileObject {
        private ByteArrayOutputStream outputStream;

        private ClassFile(URI uri) {
            super(uri, Kind.CLASS);
        }

        @Override
        public String getName() { return uri.getRawSchemeSpecificPart(); }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return outputStream = new ByteArrayOutputStream();
        }
    }

    /** A simple file manager that collects written files in memory */
    private static class FileManager
            extends ForwardingJavaFileManager<StandardJavaFileManager>
    {

        private List<ClassFile> output = new ArrayList<>();

        FileManager(StandardJavaFileManager target) {
            super(target);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) {
            final ClassFile file = new ClassFile(toUri(className));
            output.add(file);
            return file;
        }
    }

    private static URI toUri(String path)  {
        try {
            return new URI(null, null, path, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException("exception parsing uri", e);
        }
    }
}
