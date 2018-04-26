package org.integratedmodelling.klab.client.utils.codegen;

/**
 * Copied from https://github.com/medallia/javaone2016/tree/master/src/main/java/com/medallia/codegen
 * See above for copyright and license
 * @author Juan Cruz Nores https://github.com/juancn
 *
 */
/** Classloader that can be garbage collected after it's only class is unloaded */
public class DiscardableClassLoader {

    /**
     * This method loads a single class in its own class loader.
     * The classloader will be a child of the baseClass' classloader.
     */
    public static <T> Class<? extends T> classFromBytes(final Class<T> baseClass, final String name,
            final byte[] bytecode) {
        return new ClassLoader(baseClass.getClassLoader()) {

            Class<? extends T> c = defineClass(name, bytecode, 0, bytecode.length).asSubclass(baseClass);
        }.c;
    }
}
