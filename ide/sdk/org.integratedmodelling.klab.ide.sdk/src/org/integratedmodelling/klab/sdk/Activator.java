package org.integratedmodelling.klab.sdk;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

    @Override
    public void start(BundleContext context) throws Exception {
        
        Dictionary<String,String> props = new Hashtable<>();
        props.put("service.exported.interfaces", "*");
        props.put("service.exported.configs","ecf.generic.server");
        context.registerService(IKlabService.class, new ClientKlabService(), props);
        super.start(context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // TODO Auto-generated method stub
        super.stop(context);
    }

}
