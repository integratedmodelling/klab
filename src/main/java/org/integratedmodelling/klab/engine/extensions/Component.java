package org.integratedmodelling.klab.engine.extensions;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Setup;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Component implements IComponent {

    String                  name;
    boolean                 active;
    boolean                 initialized;
    Version                 version;
    Map<String, IPrototype> services            = new HashMap<>();

    private String          initMethod          = null;
    private String          setupMethod         = null;
    private boolean         isSetupAsynchronous = false;
    private boolean         binaryAssetsLoaded;
    private Class<?>        implementation;

    public Component() {
        // TODO Auto-generated constructor stub
    }

    public Component(org.integratedmodelling.klab.api.extensions.Component annotation,
            Class<?> implementation) {

        this.name = annotation.id();
        this.version = Version.parse(annotation.version());
        this.implementation = implementation;

        try {
            // TODO scan methods and exec/store setup and initialization
            for (Method method : implementation.getMethods()) {
                if (method.isAnnotationPresent(Initialize.class)) {
                    this.initMethod = method.getName();
                }
                if (method.isAnnotationPresent(Setup.class)) {
                    this.setupMethod = method.getName();
                    Setup setup = method.getAnnotation(Setup.class);
                    this.isSetupAsynchronous = setup.asynchronous();
                }
            }
        } catch (Exception e) {
            // don't break for now, just deactivate, log the error and move
            // on.
            Klab.INSTANCE.error(e);
            this.active = false;
        }
    }

    public void initialize(IMonitor monitor) throws KlabException {

        loadBinaryAssets();

        if (!active) {
            return;
        }

        String missing = "";
        // if (importedKnowledge != null) {
        // for (String imp : importedKnowledge) {
        // if (KLAB.MMANAGER.getExportedKnowledge(imp) == null) {
        // missing += (missing.isEmpty() ? "" : ", ") + imp;
        // }
        // }
        // }

        if (!missing.isEmpty()) {
            throw new KlabValidationException("component " + name
                    + " is missing the following exported knowledge IDs: " + missing);
        }

        if (initialized) {
            return;
        }
        if (implementation == null) {
            active = false;
            return;
        }

        if (initMethod == null) {
            active = true;
            initialized = true;
            return;
        }

        File toolkitPath = new File(getRoot() + File.separator + "toolkits");
        if (toolkitPath.exists() && toolkitPath.isDirectory()) {
            // PaletteManager.get().readPalettes(toolkitPath);
        }

        Klab.INSTANCE.info("initializing component " + name);

        Object executor = null;
        try {
            executor = implementation.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new KlabInternalErrorException("cannot instantiate component executor for " + name);
        }

        if (executor == null) {
            throw new KlabInternalErrorException("component " + name + " has not been configured properly");
        }

        /*
         * find executing method and call it
         */
        Method method = null;
        try {
            method = executor.getClass().getMethod(initMethod);
        } catch (NoSuchMethodException | SecurityException e) {
            return;
        }

        try {
            active = (Boolean) method.invoke(executor);
        } catch (Exception e) {
            throw (e instanceof KlabException ? (KlabException) e
                    : new KlabInternalErrorException("error calling component initializer for " + name + ": "
                            + ExceptionUtils.getRootCauseMessage(e)));
        }

        initialized = true;

        Klab.INSTANCE.info("component " + name + " initialized successfully and ready for operation");
    }

    public void setup() throws KlabException {

        if (implementation == null) {
            return;
        }
        if (setupMethod == null) {
            return;
        }
        Object executor = null;
        try {
            executor = implementation.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new KlabInternalErrorException("cannot instantiate component executor for " + name);
        }

        if (executor == null) {
            throw new KlabInternalErrorException("component " + name + " has not been configured properly");
        }

        /*
         * find executing method and call it
         */
        Method method = null;

        try {
            method = executor.getClass().getMethod(setupMethod);
        } catch (NoSuchMethodException | SecurityException e) {
            return;
        }

        try {
            /*
             * TODO do the task thing if isSetupAsynchronous
             */
            active = (Boolean) method.invoke(executor);
        } catch (Exception e) {
            throw (e instanceof KlabException ? (KlabException) e
                    : new KlabInternalErrorException("error calling component initializer for " + name + ": "
                            + ExceptionUtils.getRootCauseMessage(e)));
        }
    }

    public void loadBinaryAssets() {

        if (!binaryAssetsLoaded) {

            binaryAssetsLoaded = true;

            /*
             * load binary assets in /lib - at the very least, that will contain
             * our own code. Validation through signing should have prevented
             * getting here if the code is suspicious. This doesn't get hit in
             * development components.
             */
            // if (getLibDirectory().exists() && getLibDirectory().isDirectory()) {
            // for (Iterator<File> it = FileUtils.iterateFiles(getLibDirectory(), new String[] { "jar" },
            // false); it
            // .hasNext();) {
            // File jarFile = it.next();
            // ClassLoaderUtil.addFileToClassPath(jarFile, this.getClass().getClassLoader());
            // }
            // }

            /*
             * binary assets - usually from target/classes in development
             * components.
             */
            // if (getBinDirectory().exists() && getBinDirectory().isDirectory()) {
            // ClassLoaderUtil.addFileToClassPath(getBinDirectory(), this.getClass().getClassLoader());
            // }

            // try {
            // for (Pair<Annotation, Class<?>> decl : KLAB.ENGINE
            // .scanPackage(getProperties().getProperty(COMPONENT_PACKAGE_PROPERTY))) {
            // if (decl.getFirst() instanceof org.integratedmodelling.api.components.Component) {
            // this.implementation = decl.getSecond();
            // this.domain = ((org.integratedmodelling.api.components.Component) decl.getFirst()).worldview();
            // this.version = Version
            // .parse(((org.integratedmodelling.api.components.Component) decl.getFirst()).version()
            // .toString());
            //
            // for (Method method : implementation.getMethods()) {
            // if (method.isAnnotationPresent(Initialize.class)) {
            // initMethod = method.getName();
            // }
            // if (method.isAnnotationPresent(Setup.class)) {
            // setupMethod = method.getName();
            // Setup setup = method.getAnnotation(Setup.class);
            // isSetupAsynchronous = setup.asynchronous();
            // }
            // }
            //
            // } else if (decl.getFirst() instanceof
            // org.integratedmodelling.api.services.annotations.Prototype) {
            // IPrototype prototype = KLAB.ENGINE.getFunctionPrototype(
            // ((org.integratedmodelling.api.services.annotations.Prototype) decl.getFirst()).id());
            // ((Prototype) prototype).setComponentId(id);
            // this.services.add(prototype);
            // }
            //
            // }
            // } catch (KlabException e) {
            // // don't break for now, just deactivate, log the error and move
            // // on.
            // Klab.INSTANCE.error(e);
            // active = false;
            // }

            Klab.INSTANCE
                    .info("component " + name + " loaded  (" + this.services.size() + " services provided)");
        }
    }

    @Override
    public Collection<File> getBinaryAssets() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPrototype> getAPI() {
        return services.values();
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void addService(IPrototype prototype) {
        services.put(prototype.getName(), prototype);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public File getRoot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<INamespace> getNamespaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IProject> getPrerequisites() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version getVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public INamespace getUserKnowledge() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCanonical() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRemote() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getOriginatingNodeId() {
        // TODO Auto-generated method stub
        return null;
    }

}
