package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IResolutionConstraint;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.model.Model;

/**
 * Resolution constraints can be added through k.Actors or options to affect the resolution. They
 * may include a whitelist of URNs for models or resources or other parameters. For now used only
 * during unit testing driven by k.Actors.
 * 
 * @author Ferd
 *
 */
public class ResolutionConstraint implements IResolutionConstraint {

    public static ResolutionConstraint whitelist(Object... objects) {
        ResolutionConstraint ret = new ResolutionConstraint();
        if (objects != null) {
            for (Object o : objects) {
                if (o instanceof IModel) {
                    ret.modelWhitelist.add((IModel) o);
                } else if (o instanceof IResource) {
                    ret.resourceWhitelist.add((IResource) o);
                } else if (o instanceof INamespace) {
                    ret.namespacesWhitelist.add((INamespace) o);
                } else if (o instanceof String) {
                    if (Urns.INSTANCE.isUrn((String) o)) {
                        IResource res = Resources.INSTANCE.resolveResource((String) o);
                        if (res == null) {
                            ret.resourceWhitelist.add(res);
                        }
                    } else {
                        IKimObject object = Resources.INSTANCE.getModelObject((String) o);
                        if (object == null) {
                            INamespace ns = Namespaces.INSTANCE.getNamespace((String) o);
                            if (ns != null) {
                                ret.namespacesWhitelist.add(ns);
                            }
                        } else if (object instanceof IModel) {
                            ret.modelWhitelist.add((IModel) object);
                        }
                    }
                }
            }
        }
        return ret;
    }

    public static ResolutionConstraint blacklist(Object... objects) {
        ResolutionConstraint ret = new ResolutionConstraint();
        if (objects != null) {
            for (Object o : objects) {
                if (o instanceof IModel) {
                    ret.modelBlacklist.add((IModel) o);
                } else if (o instanceof IResource) {
                    ret.resourceBlacklist.add((IResource) o);
                } else if (o instanceof INamespace) {
                    ret.namespacesBlacklist.add((INamespace) o);
                } else if (o instanceof String) {
                    if (Urns.INSTANCE.isUrn((String) o)) {
                        IResource res = Resources.INSTANCE.resolveResource((String) o);
                        if (res == null) {
                            ret.resourceBlacklist.add(res);
                        }
                    } else {
                        IKimObject object = Resources.INSTANCE.getModelObject((String) o);
                        if (object == null) {
                            INamespace ns = Namespaces.INSTANCE.getNamespace((String) o);
                            if (ns != null) {
                                ret.namespacesBlacklist.add(ns);
                            }
                        } else if (object instanceof IModel) {
                            ret.modelBlacklist.add((IModel) object);
                        }
                    }
                }
            }
        }
        return ret;
    }

    List<IModel> modelWhitelist = new ArrayList<>();
    List<IResource> resourceWhitelist = new ArrayList<>();
    List<INamespace> namespacesWhitelist = new ArrayList<>();
    List<IModel> modelBlacklist = new ArrayList<>();
    List<IResource> resourceBlacklist = new ArrayList<>();
    List<INamespace> namespacesBlacklist = new ArrayList<>();
    Set<IObservedConcept> observables = new HashSet<>();

    private ResolutionConstraint() {
    }

    @Override
    public boolean accepts(IModel model, IObservable observable) {

        boolean ret = accepts(model.getNamespace());

        if (!model.getObservables().contains(observable)) {
            return true;
        }
        
        if (ret) {
            if (!modelBlacklist.isEmpty()) {
                for (IModel m : modelBlacklist) {
                    if (m.getName().equals(model.getName())) {
                        ret = false;
                        break;
                    }
                }
            } else if (!modelWhitelist.isEmpty()) {
                for (IModel m : modelWhitelist) {
                    if (((Model)m).findOutput(observable) == null) {
                        continue;
                    }
                    ret = false;
                    if (m.getName().equals(model.getName())) {
                        ret = true;
                        break;
                    }
                }
            }
        }

        if (ret) {
            for (IContextualizable res : model.getResources()) {
                if (res.getUrn() != null) {
                    IResource resource = Resources.INSTANCE.resolveResource(res.getUrn());
                    if (resource != null) {
                        if (!(ret = accepts(resource))) {
                            break;
                        }
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public boolean accepts(IResource model) {
        boolean ret = true;
        if (!resourceBlacklist.isEmpty()) {
            for (IResource m : resourceBlacklist) {
                if (m.getUrn().equals(model.getUrn())) {
                    ret = false;
                    break;
                }
            }
        } else if (!resourceWhitelist.isEmpty()) {
            ret = false;
            for (IResource m : resourceWhitelist) {
                if (m.getUrn().equals(model.getUrn())) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean accepts(INamespace namespace) {
        boolean ret = true;
        if (!namespacesBlacklist.isEmpty()) {
            for (INamespace m : namespacesBlacklist) {
                if (m.getName().equals(namespace.getName())) {
                    ret = false;
                    break;
                }
            }
        } else if (!namespacesWhitelist.isEmpty()) {
            ret = false;
            for (INamespace m : namespacesWhitelist) {
                if (m.getName().equals(namespace.getName())) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

}
