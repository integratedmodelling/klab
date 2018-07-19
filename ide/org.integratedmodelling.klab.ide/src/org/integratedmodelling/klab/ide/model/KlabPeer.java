package org.integratedmodelling.klab.ide.model;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Consumer;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class KlabPeer {

	static EventAdmin eventAdmin = null;

	public enum Sender {
		ANY, SESSION, TASK, ENGINE, EXPLORER
	}

	private Sender sender;
	private ServiceRegistration<EventHandler> registration;

	protected KlabPeer(Sender type) {
		this.sender = type;
	}

	/**
	 * Pass a handler for messages of the type requested.
	 * 
	 * @param type
	 *            a type to subscribe to, or ANY for all message receivers.
	 * @param messageHandler
	 */
	public KlabPeer(final Sender type, final Consumer<IMessage> messageHandler) {
		this(type);
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put(EventConstants.EVENT_TOPIC, type == Sender.ANY ? "*" : (type.name() + "/*"));
		BundleContext ctx = FrameworkUtil.getBundle(Activator.class).getBundleContext();
		this.registration = ctx.registerService(EventHandler.class, new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				if (event.getProperty("KlabMessage") != null) {
					messageHandler.accept(((IMessage) event.getProperty("KlabMessage")));
				}
			}
		}, properties);
	}

	public void send(IMessage message) {
		if (eventAdmin == null) {
			BundleContext ctx = FrameworkUtil.getBundle(Activator.class).getBundleContext();
			ServiceReference<EventAdmin> ref = ctx.getServiceReference(EventAdmin.class);
			eventAdmin = ctx.getService(ref);
		}
		eventAdmin.sendEvent(
				new Event(sender + "/" + message.getType(), Collections.singletonMap("KlabMessage", message)));
	}

	/**
	 * Call to remove the event registration when finished using.
	 */
	public void dispose() {
		if (this.registration != null) {
			this.registration.unregister();
		}
	}

}
