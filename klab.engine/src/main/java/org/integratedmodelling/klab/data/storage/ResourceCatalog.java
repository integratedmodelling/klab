package org.integratedmodelling.klab.data.storage;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Pair;

/**
 * The Nitrite database storing resource data. Resource data are automatically
 * replicated to a local directory if that is passed in the constructor.
 * 
 * @author ferdinando.villa
 *
 */
public class ResourceCatalog implements IResourceCatalog {

    Nitrite                             db;
    ObjectRepository<ResourceReference> resources;

    /**
     * Create a new resource catalog.
     * 
     * @param name
     * @param localPath
     */
    public ResourceCatalog(String name) {
        this.db = Nitrite.builder()// .compressed() TODO may reintegrate in production
                .filePath(Configuration.INSTANCE.getDataPath() + File.separator + name + ".db")
                .openOrCreate("user", "password");
        this.resources = db.getRepository(ResourceReference.class);
    }

    @Override
    public int size() {
        return (int) resources.size();
    }

    @Override
    public boolean isEmpty() {
        return resources.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return resources.find(eq("urn", key)).hasMore();
    }

    @Override
    public boolean containsValue(Object value) {
        return value instanceof IResource && containsKey(((IResource) value).getUrn());
    }

    @Override
    public IResource get(Object key) {
        ResourceReference ref = resources.find(eq("urn", key)).firstOrDefault();
        return ref == null ? null : new Resource(ref);
    }

    /**
     * Return the resource's valid directory path in the filesystem, creating
     * it if necessary. If this returns null, there must be an error in the resource.
     *  
     * @param resource
     * @return
     */
    private File getResourcePath(IResource resource) {

        File ret = null;

        if (resource.getLocalPath() != null) {
            String resPath = resource.getLocalPath();
            // ACHTUNG never call Resources.INSTANCE.getProject() here - it will recursively
            // sync resources, resulting in stack overflow.
            IKimProject project = Kim.INSTANCE.getProject(resource.getLocalProjectName());
            if (project == null) {
                throw new KlabIOException("resource belongs to invalid project "
                        + resource.getLocalProjectName());
            }
            resPath = resPath.substring(resource.getLocalProjectName().length() + 1);
            ret = new File(project.getRoot() + File.separator + resPath);
            ret.mkdir();
        }

        return ret;
    }

    /**
     * Return the new path and new resource data for moving the passed resource to another project.
     *  
     * @param value
     * @return
     */
    private Pair<File, ResourceReference> getResourcePath(IResource value, IKimProject destinationProject) {

        if (value.getLocalPath() != null) {

            File ret = null;
            String resPath = value.getLocalPath();
            resPath = resPath.substring(value.getLocalProjectName().length() + 1);
            ret = new File(destinationProject.getRoot() + File.separator + resPath);
            ret.mkdir();
            ResourceReference ref = ((Resource) value).getReference();
            ref.setLocalPath(destinationProject.getName() + File.separator + resPath);
            List<String> localfiles = new ArrayList<>();
            for (String localfile : ref.getLocalPaths()) {
                if (localfile.startsWith(value.getLocalProjectName())) {
                    localfile = localfile.substring(value.getLocalProjectName().length() + 1);
                    localfiles.add(destinationProject.getName() + File.separator + localfile);
                } else {
                    localfiles.add(localfile);
                }
            }
            ref.setLocalPaths(localfiles);
            ref.setProjectName(destinationProject.getName());
            return new Pair<>(ret, ref);
        }

        return null;
    }

    @Override
    public IResource put(String key, IResource value) {

        ((Resource) value).validate(Resources.INSTANCE);

        IResource ret = get(value.getUrn());
        if (ret != null) {
            removeDefinition(value.getUrn());
        }

        ResourceReference ref = ((Resource) value).getReference();
        resources.insert(ref);
        File resourcePath = getResourcePath(value);
        if (resourcePath != null) {
            try {
                File resFile = new File(resourcePath + File.separator + "resource.json");
                if (!resFile.exists() || resFile.lastModified() < ref.getResourceTimestamp()) {
                    FileUtils.writeStringToFile(resFile, JsonUtils.printAsJson(ref)/*, TODO REINTEGRATE - somehow can't find the method  StandardCharsets.UTF_8*/);
                }
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
        }

        db.commit();

        return ret;
    }

    public IResource removeDefinition(Object key) {
        IResource ret = get(key);
        resources.remove(eq("urn", key));
        db.commit();
        return ret;
    }

    @Override
    public IResource remove(Object key) {
        IResource ret = get(key);
        resources.remove(eq("urn", key));
        File resourcePath = getResourcePath(ret);
        if (resourcePath != null) {
            try {
                FileUtils.deleteDirectory(resourcePath);
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
        }
        db.commit();
        return ret;
    }

    @Override
    public void putAll(Map<? extends String, ? extends IResource> m) {
        for (String key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    @Override
    public void clear() {
        for (IResource resource : values()) {
            File resourcePath = getResourcePath(resource);
            if (resourcePath.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(resourcePath);
                } catch (IOException e) {
                    throw new KlabIOException(e);
                }
            }
        }
        resources.remove((ObjectFilter) null);
        db.commit();
    }

    @Override
    public Set<String> keySet() {
        Set<String> ret = new HashSet<>();
        for (Iterator<ResourceReference> r = resources.find().iterator(); r.hasNext();) {
            ret.add(r.next().getUrn());
        }
        return ret;
    }

    @Override
    public Collection<IResource> values() {
        List<IResource> ret = new ArrayList<>();
        for (Iterator<ResourceReference> r = resources.find().iterator(); r.hasNext();) {
            ret.add(new Resource(r.next()));
        }
        return ret;
    }

    @Override
    public Set<Entry<String, IResource>> entrySet() {
        Set<Entry<String, IResource>> ret = new HashSet<>();
        for (Iterator<ResourceReference> r = resources.find().iterator(); r.hasNext();) {
            final ResourceReference rr = r.next();
            ret.add(new Entry<String, IResource>() {
                @Override
                public String getKey() {
                    return rr.getUrn();
                }

                @Override
                public IResource getValue() {
                    return new Resource(rr);
                }

                @Override
                public IResource setValue(IResource value) {
                    put(value.getUrn(), value);
                    return new Resource(rr);
                }
            });
        }
        return ret;
    }

    @Override
    public void clearOnly(Object... objects) {
        if (objects != null) {
            for (Object object : objects) {
                if (object instanceof IProject) {
                    resources.remove(eq("projectName", ((IProject) object).getName()));
                } else if (object instanceof IResource) {
                    resources.remove(eq("urn", ((IResource) object).getUrn()));
                } else if (object instanceof String) {
                    resources.remove(eq("urn", (String) object));
                } else {
                    throw new IllegalArgumentException("cannot remove resources corresponding to selector "
                            + object);
                }
            }
        }

        db.commit();
    }

    @Override
    public IResource move(IResource resource, IProject destinationProject) {

        File previousDir = getResourcePath(resource);
        Pair<File, ResourceReference> newData = getResourcePath(resource, Kim.INSTANCE
                .getProject(destinationProject.getName()));

        if (previousDir != null && newData != null) {
            try {
                FileUtils.copyDirectory(previousDir, newData.getFirst());
                FileUtils.writeStringToFile(new File(newData.getFirst() + File.separator
                        + "resource.json"), JsonUtils
                                .printAsJson(newData.getSecond())/* TODO REINTEGRATE - somehow can't find the method , StandardCharsets.UTF_8*/);
                FileUtils.deleteDirectory(previousDir);
                resources.remove(eq("urn", resource.getUrn()));
                resources.insert(newData.getSecond());
                db.commit();
            } catch (IOException e) {
                throw new KlabIOException(e);
            }

        }
        return get(resource.getUrn());
    }

    @Override
    public IResource copy(IResource resource, IProject destinationProject) {
        // define new name with _c and progressive number
        // copy resource files
        // overwrite resource.json with new project name
        // add new to catalog
        return null;
    }

    @Override
    public IResource rename(IResource resource, String newUrn) {
        // TODO
        return null;
    }
    
    public IResource update(ResourceReference reference) {
        // TODO
        // ENSURE THE RESOURCE TIMESTAMP IS NEW
        return null;
    }


}
