package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kactors.model.KActors.Notifier;
import org.integratedmodelling.kactors.model.KActors.ValueTranslator;
import org.integratedmodelling.kactors.model.KActorsQuantity;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimExpression;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IActorsService;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.runtime.actors.KlabActionExecutor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage.Semaphore;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.components.runtime.actors.ViewBehavior.KlabWidgetActionExecutor;
import org.integratedmodelling.klab.components.runtime.actors.extensions.Artifact;
import org.integratedmodelling.klab.components.runtime.artifacts.ObjectArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.BehaviorReference;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.xtext.KactorsInjectorProvider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;
import com.google.inject.Injector;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public enum Actors implements IActorsService {

    INSTANCE;

    public static final String JAVA_EXTENSION_NAMESPACE = "org.integratedmodelling.klab.components.runtime.actors.extensions";

    /**
     * The possible destinations of a view panel in a layout. The lowercased name of these
     * corresponds to the annotations that attribute view roles to actions in applications.
     * 
     * @author Ferd
     */
    public enum PanelLocation {
        Left, Right, Panel, Header, Footer, Window
    }

    @Inject
    ParseHelper<Model> kActorsParser;

    private ActorSystem<Void> supervisor;
    private Map<String, IBehavior> behaviors = Collections.synchronizedMap(new HashMap<>());
    private Map<String, BehaviorReference> behaviorDescriptors = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Pair<String, Class<? extends KlabActionExecutor>>> actionClasses = Collections
            .synchronizedMap(new HashMap<>());
    private Map<String, Pair<String, Class<? extends KlabWidgetActionExecutor>>> viewActionClasses = Collections
            .synchronizedMap(new HashMap<>());
    AtomicLong semaphoreId = new AtomicLong(1L);
    Map<Semaphore.Type, Set<Long>> semaphores = Collections.synchronizedMap(new HashMap<>());
    static Set<String> layoutMetadata = null;

    /**
     * Metadata for layout control
     * <p>
     * No argument:
     * <ul>
     * <li>:right, :left, :top, :bottom</li>
     * <li>:hfill, :vfill, :fill</li>
     * <li>:disabled {!disabled for completeness}</li>
     * <li>:hidden {!hidden}</li>
     * <li>:hbox :vbox :pager :shelf :tabs [:table is the default] to specify the type of
     * arrangement in a group</li>
     * </ul>
     * <p>
     * With argument:
     * <ul>
     * <li>:cspan, :rspan (columns and rows spanned in grid)</li>
     * <li>:fg, :bg (color name for now?)</li>
     * <li>:bstyle {?HTML solid dotted}</li>
     * <li>:bwidth <n> border width (always solid for now)</li>
     * <li>:fstyle {bold|italic|strike|normal}</li>
     * <li>:fsize <n></li>
     * <li>:symbol {font awesome char code}</li>
     * <li>:class (CSS class)</li>
     * <li>:wmin, :hmin (minimum height and width)</li>
     * <li>:cols, :equal for panel grids</li>
     * </ul>
     */
    static {
        layoutMetadata = new HashSet<>();
        layoutMetadata.add("right");
        layoutMetadata.add("left");
        layoutMetadata.add("top");
        layoutMetadata.add("bottom");
        layoutMetadata.add("hfill");
        layoutMetadata.add("vfill");
        layoutMetadata.add("fill");
        layoutMetadata.add("icon");
        layoutMetadata.add("iconname");
        layoutMetadata.add("iconsize");
        layoutMetadata.add("toggle");
        layoutMetadata.add("info");
        layoutMetadata.add("disabled");
        layoutMetadata.add("hidden");
        layoutMetadata.add("hbox");
        layoutMetadata.add("vbox");
        layoutMetadata.add("inputgroup");
        layoutMetadata.add("pager");
        layoutMetadata.add("shelf");
        layoutMetadata.add("tabs");
        layoutMetadata.add("cspan");
        layoutMetadata.add("rspan");
        layoutMetadata.add("fg");
        layoutMetadata.add("bg");
        layoutMetadata.add("bwidth");
        layoutMetadata.add("checked");
        layoutMetadata.add("waiting");
        layoutMetadata.add("computing");
        layoutMetadata.add("error");
        layoutMetadata.add("done");
        layoutMetadata.add("bstyle");
        layoutMetadata.add("fstyle");
        layoutMetadata.add("fsize");
        layoutMetadata.add("symbol");
        layoutMetadata.add("class");
        layoutMetadata.add("wmin");
        layoutMetadata.add("wmax");
        layoutMetadata.add("hmin");
        layoutMetadata.add("hmax");
        layoutMetadata.add("height");
        layoutMetadata.add("width");
        layoutMetadata.add("cols");
        layoutMetadata.add("equal");
        layoutMetadata.add("collapse");
        layoutMetadata.add("remove");
        layoutMetadata.add("altfg");
        layoutMetadata.add("altbg");
        layoutMetadata.add("tooltip");
        layoutMetadata.add("ellipsis");
        layoutMetadata.add("multiple");
        layoutMetadata.add("selected");
        layoutMetadata.add("type");
    }

    @Override
    public IBehavior getBehavior(String behaviorId) {
        return behaviors.get(behaviorId);
    }

    /**
     * The actor system entry point at /user and available as getSupervisor(). It will be the
     * (direct for now) father of all session actors. We create this to have a visible top-level
     * supervisor reference at the first call to Actors, which tells us that actors will have a role
     * in the engine.
     * 
     * @author Ferd
     */
    public static class KlabSupervisor extends AbstractBehavior<Void> {

        public static Behavior<Void> create() {
            return Behaviors.setup(KlabSupervisor::new);
        }

        private KlabSupervisor(ActorContext<Void> context) {
            super(context);
            Logging.INSTANCE.info("k.LAB actor system initialized");
        }

        @Override
        public Receive<Void> createReceive() {
            return newReceiveBuilder()
                    // for now just stop
                    .onSignal(PostStop.class, signal -> onPostStop()).build();
        }

        private KlabSupervisor onPostStop() {
            getContext().getLog().info("k.LAB actor system stopped");
            return this;
        }
    }

    private Actors() {

        IInjectorProvider injectorProvider = new KactorsInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            injector.injectMembers(this);
        }
        this.supervisor = ActorSystem.create(KlabSupervisor.create(), "klab-system");
        Services.INSTANCE.registerService(this, IActorsService.class);
        KActors.INSTANCE.setValueTranslator(new ValueTranslator(){
            @Override
            public Object translate(KActorsValue container, Object value) {

                switch(container.getType()) {
                case ANYTHING:
                    break;
                case ANYTRUE:
                    break;
                case OBJECT:
                    return createJavaObject(container.getConstructor(), null, null);
                case ANYVALUE:
                    break;
                case BOOLEAN:
                    break;
                case CLASS:
                    break;
                case DATE:
                    break;
                case ERROR:
                    break;
                case EXPRESSION:
                    value = new KimExpression(value.toString() + " ", Extensions.DEFAULT_EXPRESSION_LANGUAGE);
                    break;
                case IDENTIFIER:
                    break;
                case SET:
                    break;
                case TREE:
                    break;
                case LIST:
                    break;
                case MAP:
                    break;
                case NODATA:
                    break;
                case NUMBER:
                    break;
                case NUMBERED_PATTERN:
                    break;
                case OBSERVABLE:
                    value = Observables.INSTANCE.declare(value.toString());
                    break;
                case QUANTITY:
                    // NO - leave it as is, implementing the syntactic peer.
//                    if (value instanceof KActorsQuantity) {
//                        if (((KActorsQuantity) value).getUnit() != null) {
//                            value = Quantity.create(((KActorsQuantity) value).getValue(),
//                                    Unit.create(((KActorsQuantity) value).getUnit()));
//                        } else if (((KActorsQuantity) value).getCurrency() != null) {
//                            value = Quantity.create(((KActorsQuantity) value).getValue(),
//                                    Currency.create(((KActorsQuantity) value).getCurrency()));
//                        }
//                    }
                    break;
                case RANGE:
                    break;
                case REGEXP:
                    break;
                case STRING:
                    break;
                case TABLE:
                    break;
                case TYPE:
                    break;
                case URN:
                    break;
                case OBSERVATION:
                    break;
                case CONSTANT:
                    break;
                case EMPTY:
                    break;
                default:
                    break;
                }

                return value;
            }
        });
    }

    @Override
    public IKActorsBehavior declare(URL url) throws KlabException {
        try (InputStream stream = url.openStream()) {
            return declare(stream);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    @Override
    public IKActorsBehavior declare(File file) throws KlabException {
        try (InputStream stream = new FileInputStream(file)) {
            return declare(stream);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    /**
     * Install listeners to build behaviors on read and organize them by project.
     */
    public void setup() {
        KActors.INSTANCE.addNotifier(new Notifier(){
            @Override
            public void notify(IKActorsBehavior behavior) {
                behaviors.put(behavior.getName(),
                        new org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior(behavior));
            }
        });
    }

    @Override
    public IKActorsBehavior declare(InputStream file) throws KlabValidationException {
        IKActorsBehavior ret = null;
        try {
            String definition = IOUtils.toString(file);
            Model model = kActorsParser.parse(definition);
            ret = KActors.INSTANCE.declare(model);
        } catch (Exception e) {
            throw new KlabValidationException(e);
        }
        return ret;
    }

    /**
     * The supervisor actor.
     * 
     * @return
     */
    public ActorSystem<Void> getSupervisor() {
        return this.supervisor;
    }

    /**
     * Create a direct child of the supervisor with the specified identity. Implementations should
     * only call this for top-level actors and have the others created through messages to them. The
     * top-level identities for now are user actors.
     * 
     * @param <T>
     * @param create
     * @param identity
     * @return
     */
    public <T> ActorRef<T> createActor(Behavior<T> create, IIdentity identity) {
        return ActorSystem.create(Behaviors.supervise(create).onFailure(SupervisorStrategy.resume().withLoggingEnabled(true)),
                identity instanceof IUserIdentity ? sanitize(((IUserIdentity) identity).getUsername()) : identity.getId());
    }

    private String sanitize(String username) {
        // should be enough
        return username.replace('.', '_');
    }

    /**
     * Called when a class annotated as a behavior is found.
     * 
     * @param annotation
     * @param cls
     */
    @SuppressWarnings("unchecked")
    public void registerBehavior(org.integratedmodelling.klab.api.extensions.actors.Behavior annotation, Class<?> cls) {

        BehaviorReference descriptor = behaviorDescriptors.get(annotation.id());
        if (descriptor == null) {
            descriptor = new BehaviorReference();
            behaviorDescriptors.put(annotation.id(), descriptor);
            descriptor.setName(annotation.id());
        }

        if (!annotation.description().isEmpty()) {
            descriptor.setDescription(annotation.description());
        }
        if (!annotation.color().isEmpty()) {
            descriptor.setColor(annotation.color());
        }

        for (Class<?> cl : cls.getDeclaredClasses()) {
            Action message = cl.getAnnotation(Action.class);
            if (message != null) {
                BehaviorReference.Action ad = new BehaviorReference.Action();
                ad.setName(message.id());
                ad.setDescription(message.description());
                descriptor.getActions().add(ad);
                this.actionClasses.put(message.id(), new Pair<>(annotation.id(), (Class<? extends KlabActionExecutor>) cl));
                if (KlabActionExecutor.class.isAssignableFrom(cl)) {
                    this.viewActionClasses.put(message.id(),
                            new Pair<>(annotation.id(), (Class<? extends KlabWidgetActionExecutor>) cl));
                }
            }
        }
    }

    public void exportBehaviors(File file) {

        /*
         * Fill this in so we can read actor files with the actual knowledge of the installed
         * behaviors. This is called at the right time, after loading and before reading behaviors.
         */
        KActors.INSTANCE.getBehaviorManifest().putAll(behaviorDescriptors);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
            mapper.setSerializationInclusion(Include.NON_NULL);
            JavaType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, BehaviorReference.class);
            mapper.writerFor(type).writeValue(file, this.behaviorDescriptors);
        } catch (IOException e) {
            Logging.INSTANCE.error(e);
        }
    }

    /**
     * Create and return an action from one of the system behaviors.
     * 
     * @param behavior
     * @param id
     * @param sender
     * @param arguments
     * @param messageId
     * @param scope
     * @return
     */
    public KlabActionExecutor getSystemAction(String id, IActorIdentity<KlabMessage> identity, IParameters<String> arguments,
            KlabActor.Scope scope, ActorRef<KlabMessage> sender, String callId) {

        Pair<String, Class<? extends KlabActionExecutor>> cls = actionClasses.get(id);
        if (cls != null) {
            try {
                Constructor<? extends KlabActionExecutor> constructor = cls.getSecond().getConstructor(IActorIdentity.class,
                        IParameters.class, KlabActor.Scope.class, ActorRef.class, String.class);
                return constructor.newInstance(identity, arguments, scope, sender, callId);
            } catch (Throwable e) {
                scope.getMonitor().error("Error while creating action " + id + ": " + e.getMessage());
            }
        }
        return null;
    }

    public Class<? extends KlabActionExecutor> getActionClass(String id) {
        Pair<String, Class<? extends KlabActionExecutor>> ret = actionClasses.get(id);
        return ret == null ? null : ret.getSecond();
    }

    public Class<? extends KlabWidgetActionExecutor> getViewActionClass(String id) {
        Pair<String, Class<? extends KlabWidgetActionExecutor>> ret = viewActionClasses.get(id);
        return ret == null ? null : ret.getSecond();
    }

    
    @Override
    public Collection<String> getBehaviorIds() {
        return behaviors.keySet();
    }

    @Override
    public Collection<String> getBehaviorIds(IKActorsBehavior.Type type) {
        List<String> ret = new ArrayList<>();
        for (String key : behaviors.keySet()) {
            if (behaviors.get(key).getDestination() == type) {
                ret.add(key);
            }
        }
        return ret;
    }

    @Override
    public Collection<String> getPublicApps() {
        List<String> ret = new ArrayList<>();
        for (String key : behaviors.keySet()) {
            if (behaviors.get(key).getDestination() == Type.APP && behaviors.get(key).getStatement().isPublic()) {
                ret.add(key);
            }
        }
        return ret;
    }

    public void instrument(List<IAnnotation> annotations, Observation observation) {
        instrument(annotations, observation, observation.getScope());
    }

    public void instrument(List<IAnnotation> annotations, Observation observation, IRuntimeScope scope) {

        /*
         * find any bindings made at runtime
         */
        Pair<String, IKimExpression> rb = observation.getScope().getBehaviorBindings().get(observation.getObservable().getType());
        if (rb != null) {
            IBehavior b = getBehavior(rb.getFirst());
            if (b != null) {
                if (rb.getSecond() != null) {
                    // TODO filter
                }
                observation.getScope().scheduleActions(observation, b);
                ((Observation) observation).load(b, scope);
            }
        }

        for (IAnnotation annotation : annotations) {
            if (annotation.getName().equals("bind")) {
                String behavior = annotation.containsKey("behavior")
                        ? annotation.get("behavior", String.class)
                        : annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME, String.class);
                if (behavior != null) {
                    IBehavior b = getBehavior(behavior);
                    if (b != null) {

                        if (annotation.contains("filter")) {
                            // TODO build/cache and run filter, skip if false
                        }

                        /*
                         * observation will load behavior, schedule all the temporal actions it
                         * contains before main is run
                         */
                        observation.getScope().scheduleActions(observation, b);

                        /*
                         * load the behavior, running any main actions right away
                         */
                        ((Observation) observation).load(b, scope);
                    }
                }
            }
        }
    }

    /**
     * Find the first unnamed parameter that matches the passed class, resolving actor values into
     * their correspondent value.
     * 
     * @param <T>
     * @param arguments
     * @param class1
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getArgument(IParameters<String> arguments, Class<T> cls) {
        for (String key : arguments.getUnnamedKeys()) {
            Object ret = arguments.get(key);
            if (ret instanceof KActorsValue) {
                ret = ((KActorsValue) ret).getValue();
            }
            if (ret != null && cls.isAssignableFrom(ret.getClass())) {
                return (T) ret;
            }
        }
        return null;
    }

    public List<ViewPanel> getPanels(Layout view) {
        List<ViewPanel> panels = new ArrayList<>();
        panels.addAll(view.getLeftPanels());
        panels.addAll(view.getRightPanels());
        panels.addAll(view.getPanels());
        if (view.getHeader() != null) {
            panels.add(view.getHeader());
        }
        if (view.getFooter() != null) {
            panels.add(view.getFooter());
        }
        return panels;
    }

    public String dumpView(Layout view) {
        StringBuffer ret = new StringBuffer(2048);
        dumpView(view, ret, 0);
        return ret.toString();
    }

    public void dumpView(Layout view, StringBuffer ret, int offset) {

        String spacer = StringUtil.spaces(offset);

        ret.append(spacer + "View " + view.getName() + "\n");

        if (view.getHeader() != null) {
            dumpPanel(view.getHeader(), "Header", ret, offset + 3);
        }

        for (ViewPanel panel : view.getLeftPanels()) {
            dumpPanel(panel, "Left", ret, offset + 3);
        }

        for (ViewPanel panel : view.getPanels()) {
            dumpPanel(panel, "Center", ret, offset + 3);
        }

        for (ViewPanel panel : view.getRightPanels()) {
            dumpPanel(panel, "Right", ret, offset + 3);
        }

        if (view.getFooter() != null) {
            dumpPanel(view.getFooter(), "Footer", ret, offset + 3);
        }

    }

    private void dumpPanel(ViewPanel panel, String title, StringBuffer ret, int offset) {

        String spacer = StringUtil.spaces(offset);

        ret.append(spacer + title + ": " + panel.getName() + " [" + panel.getId() + "]\n");

        for (ViewComponent component : panel.getComponents()) {
            dumpComponent(component, ret, offset + 3);
        }

    }

    private void dumpComponent(ViewComponent component, StringBuffer ret, int offset) {
        if (component instanceof Layout) {
            dumpView((Layout) component, ret, offset);
        }
        if (component instanceof ViewPanel) {
            dumpPanel((ViewPanel) component, "Panel", ret, offset);
        } else {
            String spacer = StringUtil.spaces(offset);
            String name = component.getName() == null ? component.getTitle() : component.getName();
            ret.append(spacer + component.getType() + " " + (name == null ? "<unnamed>" : name) + " [" + component.getId() + "]"
                    + dumpAttributes(component) + (component.getActorPath() == null ? "" : (" -> " + component.getActorPath()))
                    + "\n");
            for (ViewComponent c : component.getComponents()) {
                dumpComponent(c, ret, offset + 3);
            }
        }
    }

    private String dumpAttributes(ViewComponent component) {
        String ret = "";
        if (!component.getAttributes().isEmpty()) {
            String attrs = "";
            for (String key : component.getAttributes().keySet()) {
                attrs += (attrs.isEmpty() ? "" : ", ") + key + ": " + component.getAttributes().get(key);
            }
            ret = " {" + attrs + "}";
        }
        return ret;
    }

    /**
     * Load all the behaviors in <klab>/user. These should be managed through the IDE and there
     * should always be a default one.
     */
    public void loadUserBehaviors() {
        File bspace = Configuration.INSTANCE.getDataPath("user");
        for (File bfile : bspace.listFiles()) {
            if (bfile.toString().endsWith(".kactor")) {
                KActors.INSTANCE.add(bfile);
            }
        }
    }

    public ViewPanel findPanel(Layout view, String id) {
        ViewPanel ret = null;
        for (Collection<?> panels : new Collection[]{view.getPanels(), view.getLeftPanels(), view.getRightPanels(),
                Collections.singleton(view.getFooter()), Collections.singleton(view.getHeader())}) {
            for (Object panel : panels) {
                ret = findPanel((ViewPanel) panel, id);
                if (ret != null) {
                    return ret;
                }
            }
        }
        return null;
    }

    private ViewPanel findPanel(ViewComponent panel, String id) {
        if (panel instanceof ViewPanel && id.equals(panel.getId())) {
            return (ViewPanel) panel;
        }
        for (ViewComponent component : panel.getComponents()) {
            ViewPanel ret = findPanel(component, id);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

    public Set<String> getLayoutMetadata() {
        return layoutMetadata;
    }

    class SemaphoreImpl implements Semaphore {

        long id;
        Type type;

        SemaphoreImpl(Type type, long id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public Type getType() {
            return type;
        }

        public long getId() {
            return id;
        }
        
        @Override
        public String toString() {
            return type + "-" + id;
        }

    }

    /**
     * Create and keep a semaphore for different actors to share and check. Used to implement serial
     * behavior when needed to serialize chains of fired messages or load operations.
     * 
     * @param type
     * @return
     */
    public Semaphore createSemaphore(final Semaphore.Type type) {

        Set<Long> ids = this.semaphores.get(type);
        if (ids == null) {
            ids = Collections.synchronizedSet(new HashSet<>());
            this.semaphores.put(type, ids);
        }

        final long id = semaphoreId.incrementAndGet();
        ids.add(id);
        return new SemaphoreImpl(type, id);
    }

    /**
     * Make the passed semaphore expire. TODO eventually this will need to work across network
     * boundaries.
     * 
     * @param semaphor
     */
    public void expire(Semaphore semaphore) {
        Set<Long> ids = this.semaphores.get(semaphore.getType());
        if (ids != null) {
            ids.remove(((SemaphoreImpl) semaphore).getId());
        }
    }

    /**
     * Check if the passed semaphore has expired.
     * 
     * @param semaphor
     * @return
     */
    public boolean expired(Semaphore semaphore) {
        Set<Long> ids = this.semaphores.get(semaphore.getType());
        return ids == null || !ids.contains(((SemaphoreImpl) semaphore).getId());
    }

    /**
     * Build a Java object through reflection when invoked by a k.Actors constructor
     * 
     * @param constructor
     * @param scope
     * @param identity
     * @return
     */
    public Object createJavaObject(KActorsValue.Constructor constructor, Scope scope, IActorIdentity<?> identity) {

        Class<?> cls = null;
        String className = constructor.getClassname();
        Object ret = null;

        if (constructor.getClasspath() == null) {
            className = JAVA_EXTENSION_NAMESPACE + "." + className;
        } else {
            className = constructor.getClasspath() + "." + className;
            throw new KlabIllegalStateException(
                    "k.Actors: creation of Java object with explicit classpath requires a security exception, unimplemented so far.");
        }

        try {

            cls = Class.forName(className);
            if (cls != null) {
                /*
                 * arguments without a key are the constructor argument; keyed arguments will be
                 * handled by looking up setXxxx(arg) through reflection.
                 */
                List<Object> arguments = new ArrayList<>();
                Map<String, Object> settings = new HashMap<>();

                for (Object arg : constructor.getArguments().getUnnamedArguments()) {
                    if (arg instanceof KActorsValue) {
                        arguments.add(scope == null
                                ? ((KActorsValue) arg).getValue()
                                : KlabActor.evaluateInScope((KActorsValue) arg, scope, identity));
                    } else {
                        arguments.add(arg);
                    }
                }

                for (String key : constructor.getArguments().keySet()) {
                    Object arg = constructor.getArguments().get(key);
                    settings.put(key,
                            arg instanceof KActorsValue
                                    ? (scope == null
                                            ? ((KActorsValue) arg).getValue()
                                            : KlabActor.evaluateInScope((KActorsValue) arg, scope, identity))
                                    : arg);
                }

                Constructor<?> constr = null;

                if (arguments.size() == 0) {
                    constr = cls.getConstructor();
                } else {
                    Class<?>[] cclasses = new Class<?>[arguments.size()];
                    int i = 0;
                    for (Object o : arguments) {
                        cclasses[i++] = o == null ? Object.class : o.getClass();
                    }
                    constr = cls.getConstructor(cclasses);
                }

                if (constr == null) {
                    throw new KlabValidationException(
                            "k.Actors: cannot find a constructor for the arguments specified for " + className);
                }

                ret = constr.newInstance(arguments.toArray());

                // shouldn't happen w/o exception
                if (ret != null) {
                    for (String setting : settings.keySet()) {
                        String methodName = setting.startsWith("set") ? setting : ("set" + StringUtil.capitalize(setting));
                        Object argument = settings.get(setting);
                        Method method = null;
                        try {
                            method = cls.getMethod(methodName, argument == null ? Object.class : argument.getClass());
                        } catch (NoSuchMethodException e) {
                            // ok, we dont'have it.
                        }
                        if (method == null) {
                            methodName = "setProperty";
                            try {
                                method = cls.getMethod(methodName, String.class, Object.class);
                            } catch (NoSuchMethodException e) {
                                // not this one, either.
                            }
                        }

                        if (method != null) {
                            if ("setProperty".equals(methodName)) {
                                method.invoke(ret, setting, argument);
                            } else {
                                method.invoke(ret, argument);
                            }
                        } else {
                            if (scope != null) {
                                scope.getMonitor().warn(
                                        "k.Actors: cannot find a " + methodName + " method to invoke on constructed object");
                            } else {
                                Logging.INSTANCE.warn(
                                        "k.Actors: cannot find a " + methodName + " method to invoke on constructed object");
                            }
                        }
                    }
                }

            }

        } catch (Throwable e) {
            if (scope != null) {
                scope.getMonitor().error("error creating k.Actors object of class " + className + ": " + e.getMessage());
            } else {
                Logging.INSTANCE.error("error creating k.Actors object of class " + className + ": " + e.getMessage());
            }
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    public Iterable<Object> getIterable(IKActorsValue iterable, Scope scope, IActorIdentity<?> identity) {
        switch(iterable.getType()) {
        case ANYTHING:
            break;
        case ANYTRUE:
            break;
        case ANYVALUE:
            break;
        case EMPTY:
            break;
        case CONSTANT:
        case DATE:
        case CLASS:
        case BOOLEAN:
        case ERROR:
        case EXPRESSION:
        case NUMBER:
        case OBSERVABLE:
        case STRING:
            if (Urns.INSTANCE.isUrn(iterable.getValue().toString())) {
                return iterateResource(iterable.getValue().toString(), scope.getMonitor());
            }
            return Collections.singletonList(KlabActor.evaluateInScope((KActorsValue) iterable, scope, identity));
        case OBJECT:
        case IDENTIFIER:
        case LIST:
        case SET:
            Object o = KlabActor.evaluateInScope((KActorsValue) iterable, scope, identity);
            if (o instanceof Iterable) {
                return (Iterable<Object>) o;
            } else {
                return Collections.singletonList(o);
            }
        case MAP:
            break;
        case NODATA:
            return Collections.singletonList(null);
        case NUMBERED_PATTERN:
            break;
        case OBSERVATION:
            return (Iterable<Object>) KlabActor.evaluateInScope((KActorsValue) iterable, scope, identity);
        case QUANTITY:
            break;
        case RANGE:
            // TODO iterate the range
            break;
        case TABLE:
            break;
        case TREE:
            break;
        case TYPE:
            break;
        case URN:
            return iterateResource(iterable.getValue().toString(), scope.getMonitor());
        default:
            break;
        }
        return new ArrayList<>();
    }

    private Iterable<Object> iterateResource(String urn, IMonitor monitor) {

        VisitingDataBuilder builder = new VisitingDataBuilder(1);
        IKlabData data = Resources.INSTANCE.getResourceData(urn, builder, monitor);
        return data.getObjectCount() == 0 ? new ArrayList<>() : new Iterable<Object>(){

            @Override
            public Iterator<Object> iterator() {

                return new Iterator<Object>(){

                    int n = 0;

                    @Override
                    public boolean hasNext() {
                        return n < data.getObjectCount();
                    }

                    @Override
                    public Object next() {
                        // wrap into an Artifact wrapper for reference inside k.Actors
                        Object ret = new Artifact(
                                new ObjectArtifact(data.getObjectName(n), data.getObjectScale(n), data.getObjectMetadata(n)));
                        n++;
                        return ret;
                    }
                };
            }

        };
    }

    /**
     * Invoke a method based on parameters from a call to a Java reactor inside the k.Actors code.
     * 
     * @param reactor
     * @param arguments
     * @param scope
     */
    public void invokeReactorMethod(Object reactor, String methodName, IParameters<String> arguments, Scope scope,
            IActorIdentity<?> identity) {

        List<Object> jargs = new ArrayList<>();
        Map<String, Object> kargs = null;
        for (Object v : arguments.getUnnamedArguments()) {
            jargs.add(v instanceof KActorsValue ? KlabActor.evaluateInScope((KActorsValue) v, scope, identity) : v);
        }
        for (String k : arguments.getNamedKeys()) {
            if (kargs == null) {
                kargs = new HashMap<>();
            }
            Object v = arguments.get(k);
            kargs.put(k, v instanceof KActorsValue ? KlabActor.evaluateInScope((KActorsValue) v, scope, identity) : v);
        }
        if (kargs != null) {
            jargs.add(kargs);
        }

        Class<?>[] clss = new Class[jargs.size()];

        int i = 0;
        for (Object jarg : jargs) {
            clss[i++] = jarg == null ? Object.class : jarg.getClass();
        }

        Method method = MethodUtils.getMatchingMethod(reactor.getClass(), methodName, clss);
 
        if (method != null) {
            try {
                method.invoke(reactor, jargs.toArray());
            } catch (Throwable e) {
                if (scope != null) {
                    scope.getMonitor().error(e);
                } else {
                    Logging.INSTANCE.error(e);
                }
            }
        } else {
            if (scope != null) {
                scope.getMonitor().warn("k.Actors: cannot find a '" + methodName + "' method to invoke on object");
            } else {
                Logging.INSTANCE.warn("k.Actors: cannot find a '" + methodName + "' method to invoke on object");
            }
        }

    }
}
