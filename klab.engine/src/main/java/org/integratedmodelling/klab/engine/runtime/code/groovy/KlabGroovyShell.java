//package org.integratedmodelling.klab.engine.runtime.code.groovy;
//
//import java.lang.reflect.Constructor;
//import java.security.AccessController;
//import java.security.PrivilegedAction;
//
//import org.codehaus.groovy.control.CompilationFailedException;
//import org.codehaus.groovy.control.CompilerConfiguration;
//import org.codehaus.groovy.control.customizers.ImportCustomizer;
//import org.integratedmodelling.klab.Extensions;
//import org.integratedmodelling.klab.utils.Path;
//
//import groovy.lang.Binding;
//import groovy.lang.GroovyCodeSource;
//import groovy.lang.GroovyShell;
//import groovy.lang.Script;
//
//public class KlabGroovyShell extends GroovyShell {
//
//	private static final String BASE_ACTION_CLASS = "org.integratedmodelling.klab.extensions.groovy.ActionBase";
////	private static final Object[] EMPTY_MAIN_ARGS = new Object[] { new String[0] };
//
//	private static CompilerConfiguration getConfiguration() {
//		CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
//		compilerConfiguration.setScriptBaseClass(BASE_ACTION_CLASS);
//		ImportCustomizer customizer = new ImportCustomizer();
//		for (Class<?> cls : Extensions.INSTANCE.getKimImports()) {
//			customizer.addImport(Path.getLast(cls.getCanonicalName(), '.'), cls.getCanonicalName());
//		}
//		compilerConfiguration.addCompilationCustomizers(customizer);
//		return compilerConfiguration;
//	}
//
//	public KlabGroovyShell() {
//		super(KlabGroovyShell.class.getClassLoader(), getConfiguration());
//	}
//
//	public Class<?> parseToClass(final String scriptText) throws CompilationFailedException {
//		GroovyCodeSource gcs = AccessController.doPrivileged(new PrivilegedAction<GroovyCodeSource>() {
//			public GroovyCodeSource run() {
//				return new GroovyCodeSource(scriptText, generateScriptName(), DEFAULT_CODE_BASE);
//			}
//		});
//		return getClassLoader().parseClass(gcs);
//	}
//
//	public Script createFromClass(Class<?> script, Binding context) throws Exception {
//		Script runnable = null;
//		try {
//			Constructor<?> constructor = script.getConstructor(Binding.class);
//			runnable = (Script) constructor.newInstance(context);
//		} catch (NoSuchMethodException e) {
//			// Fallback for non-standard "Script" classes.
//			runnable = (Script) script.getConstructor().newInstance();
//			runnable.setBinding(context);
//		}
//		return runnable;
//	}
//	
//	public Object runClass(Class<?> script, Binding context) throws Exception {
//		Script runnable = null;
//		try {
//			Constructor<?> constructor = script.getConstructor(Binding.class);
//			runnable = (Script) constructor.newInstance(context);
//		} catch (NoSuchMethodException e) {
//			// Fallback for non-standard "Script" classes.
//			runnable = (Script) script.getConstructor().newInstance();
//			runnable.setBinding(context);
//		}
//		return runnable.run();
//	}
//}
