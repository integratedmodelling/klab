package org.integratedmodelling.klab.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.integratedmodelling.klab.Logging;

public class ClassUtils {
	/**
	 * Return all subclasses of given class in given package. Uses file structure in classpath as
	 * seen by passed classloader. Loads ALL classes in package in the process. Use with caution -
	 * it's sort of dirty, but it's the only way to obtain the class structure without preloading
	 * classes.
	 *
	 * @param mainClass the main class
	 * @param pckgname the pckgname
	 * @param cloader the cloader
	 * @return subclasses
	 */
	public static Collection<Class<?>> findSubclasses(Class<?> mainClass, String pckgname, ClassLoader cloader) {
		return findSubclasses(null, mainClass, pckgname, cloader);
	}

	private static Collection<Class<?>> findSubclasses(ArrayList<Class<?>> ret, Class<?> mainClass, String pckgname,
			ClassLoader cloader) {

		if (ret == null)
			ret = new ArrayList<>();

		// Translate the package name into an absolute path
		String name = new String(pckgname).replace('.', '/');

		// Get a File object for the package
		URL url = cloader.getResource(name);

		if (url == null)
			return ret;

		File directory = new File(Escape.fromURL(url.getFile()));

		if (directory.exists()) {

			// Get the list of the files contained in the package
			String[] files = directory.list();

			for (int i = 0; i < files.length; i++) {

				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					String classname = files[i].substring(0, files[i].length() - 6);
					try {
						Class<?> clls = Class.forName(pckgname + "." + classname, true, cloader);
						if (mainClass.isAssignableFrom(clls)) {
							ret.add(clls);
						}
					} catch (ClassNotFoundException e) {
					  Logging.INSTANCE.warn("task class " + pckgname + "." + classname + " could not be created: "
								+ e.getMessage());
					}
				} else {

					File ff = new File(Escape.fromURL(url.getFile()) + "/" + files[i]);

					if (ff.isDirectory()) {
						String ppk = pckgname + "." + files[i];
						findSubclasses(ret, mainClass, ppk, cloader);
					}
				}
			}
		}

		return ret;
	}
}
