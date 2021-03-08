# Changelog

All notable changes to this project are documented in this file. The number after 
the minor release is the build number. All changes made in the develop branch appear 
as unreleased until merged to master.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), 
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased
### Added
- Improve concurrency logics for k.Actors: init/main code is run in a separate thread to
  avoid hogging the actor's mailbox; a semaphore (handled by the engine) can be passed
  in the scope; synchronization is now handled (must test more thoroughly) and a new
  behavior type ('script') is dedicated to sequential scripts and forces sequential
  execution throughout (same for 'test'). 
- k.Actors now handles 'for' loops and construction of/interaction with Java objects by calling
  their constructors at "set" statements and their methods as messages directed to them,
  using reflection. Groovy-like property getting (and setting at construction with keyed
  arguments) is also available on them. Only Java objects from a particular package can
  be created now, because of the security risk of allowing arbitrary Java classes to be
  used (this could be relaxed on local engines).
- New klab.data.aggregate contextualizer to merge states after they have been contextualized
  from an abstract dependency. Syntactic support for klab.data.merge to be used for 
  subjects and other countables, with the option of removing the original observations from
  the context.
- Table compiler now aggregates from multiple states when the classifier uses an abstract
  predicate, expanding and matching each concrete incarnation of it represented in the
  context. 
- Abstract roles and (some) identities in observables are resolved to concrete during
  resolution. While roles can be pre-set in the session by explicit user action, both
  roles and identities that are not already resolved will now lookup a characterizing
  model that outputs the union of concrete predicates for their abstract observable.
  The dynamics of resolution only looks at abstract roles and those abstract identities
  that are required for the observable (defined through "requires identity" in the
  semantics). Identities that are not required for the observable are left in the
  observable unmodified. IRuntimeContext::findArtifact now can find multiple states
  and match predicates from their abstract incarnations, returning observation groups
  also for states (not added to the context).
- Initial, partial implementation of multiple table adapters with (so far) CSV support
  and conventions for time/space contextualization and filtering.
### Fixed
- Engine honors 'klab.prerequisites' property in project options (still no IDE 
  support for defining it).
- Pre-load local resources before any namespace is created in engine
- Review naming logics to ensure all local names are seen in actuators
- Bug in resolving processes that would cause some changing states to not be found
- Bug in resolving states with value operators from pre-existing observations that
  did not have the operators.
- Resolution of attributes in instantiator from compatible context states. Needs
  overhaul to incorporate with dataflows - current implementation won't change in
  time when states are dynamic and won't replicate when re-running dataflow.

## [0.10.0.235] -- 2020/12/8
### Added
- Observables that contain abstract roles are expanded into the correspondent concrete
  observables after substituting the role's incarnation(s) in the context of resolution before
  resolving. The expansion is limited to (concrete) predicates for now, as part of
  observables.
### Fixed
- Monetary value declarations are now handled properly.
- Three-way communication of resolutions in time and space now more reliable.
- Temporal prioritization with disjoint extents now disregards specificity and
  takes temporal distance into consideration.

## [0.10.0.234] -- 2020/12/4
### Fixed
- Incorporate various fixes in apps UI behavior
- Temporal contextualization now behaves correctly when changing states from 
  previous resolutions have dependencies in the current one.

## [0.10.0.233] -- 2020/12/1

### Added
- Add the first raw implementation of a context debugger to inspect and watch
  values and value expressions. Can be started from the CLI or the Modeler.
### Changed
- Add column/row grouping and group-based requirements to tables to remove
  entire groups when some columns aren't represented.
- Context setting from either manual drawing or OSM retrieval is now uniform with
  the rest and makes "proposals" that become the context at the first observation,
  rather than setting the context right away.
### Fixed
- Various issues in temporal resolution and contextualization

## [0.10.0.232] -- 2020/11/21
### Added
- Streamline and modularize the geocoding services so that more can be
  added easily. Implement the standard bounding box naming and add 
  administrative and watershed selection based on FSCAN node-provided
  services.
- Parameter "invert" for normalizing and standardizing contextualizers.
- FSCAN adapter indexes multiple vector sources and provides fast reaction to
  queries for the smallest polygon that fits the bounding box, plus the ability
  of extracting features based on the level implied by the current scale.  
- Stubs for SDMX component.
- Extensive testing and improvements in k.Actors and the handling of UI components
  through it, with full-duplex interaction with the new session state.
- Handling of roles is now understood and implemented. Roles used in 
  observable declarations are handled like regular attributes, while roles
  implied by observables or stated by users as part of session state are used
  in the relevant branch of resolution. Dependencies for "any <role>" will be
  expanded to imply all observables currently incarnating that role. Implications
  are unimplemented for now, but session-defined roles are working. 
- Session state now elevated to API and managing all details of interaction with
  clients, including submission of observables that are queued for contextualization
  so that the level of concurrency within sessions can be controlled. Session 
  state is set up to collect history beans and for saving/restoring info, including
  the current state of application views.
- Annotation syntax now admits unnamed parameters in coexistence with named 
  ones.
- Implemented "knowledge views" as resolvable void artifacts declared with a
  configurable, typed define statement. Table view implemented and functional,
  with extensive testing needed due to extensive complexity.
- Values in k.IM now admit entire observables and relational operators on numbers, 
  resolved to ranges.
- Change models are automatically resolved for all qualities that may be affected by
  processes or events whenever the context is temporal and there is a condition for
  occurrence (either occurrents are resolved or the context has temporal distribution). 
  If there is no explicit change statement in the network scope, the scheduler will
  insert a check at each effective timestep for change of any observations from which 
  others depend, and re-run the actuator for those where the check is positive. This 
  essentially makes any model track change even if it's not made explicitly dynamic.
- Implement syntactic typing for change rates ('change rate of') and change events 
  ('changed <quality>'). Support in engine (unit/context validation and model inference)
  still missing.
- Authority concepts should now be identified using the uppercase authority name as a 
  namespace, which is now syntactically supported. The ID can be a lowercase or
  uppercase ID, an integer, or a string. This enables a much more natural mapping
  of identities to concepts and can be supplemented with editing tools so that
  intermediate aliases can be reduced or eliminated. Syntax like IUPAC:water is
  fully interoperable with IUPAC:H2O or any other definition; checking and testing
  will be integrated with the editor and the 'identified as' syntax is now 
  deprecated.
- Brought back the IUPAC, GBIF and WRB authorities with modernized API and both
  engine and node versions. WRB due for update to 2014 vocabulary with 
  constraint validation; versions will be selectable using syntax like 
  WRB.2014:"Thionic Vertisols", with 2014 as the default vocabulary.
- Syntax highlighting in k.Actors now shows different classes of actions with
  matching colors. In addition, observables are now parsed and decorated using
  same colors as k.IM. Any k.Actors keyword in namespace IDs can be quoted using a 
  backtick in front of it.
- One-way aggregation working properly both by category and object, using the
  spatial nature of the context or of the aggregating artifact to define
  unit collapse. N-way aggregation supported only syntactically for now.
- Blacklist (synchronized) projects by adding their (comma-separated) names to
  property klab.project.blacklist. For ARIES users, advisable for im.data.usa 
  until USGS gets its act together. Projects aren't removed from the deploy
  directory, so they should be removed manually after adding to the blacklist.
- Spatial (Areal, Lineal, Puntal, Volumetric) and commonsense temporal (Yearly, 
  Monthly, Weekly, Daily, Hourly) identities are now core identities so they can
  be used when assessing default units for aggregation by scaled countables (continuants
  for space and events for time).
- The 'by', 'without' and other value operators that take concepts as arguments can
  now use a comma-separated list of concepts (implementation for now is only
  syntactical).
- The random URN adapter accessible through klab:random URNs now creates objects
  and events, with optional attributes that can have fixed values or take them
  from all sorts of distributions. 
- Syntactic support for "new <behavior> [(args)]" verb in k.Actors that creates
  new actors from a behavior. A #tag in the arguments references the new actor for
  later use.
- Distinction between "library" (behavior for importing of actions) and "component"
  which produces a peer UI layout within a parent layout when loaded with "new". 
- Groups of events can be located in time (using the at() method) so that
  they only produce the events that are happening when iterated. The unlocated
  group still produces all events.
- Syntactic support for 'during each' operator, which does not affect the 
  semantics but schedules processes so that they only happen during events.
- In models, long URNs can be specified in multiple consecutive strings, which are 
  merged before use.
- Each behavior is loaded into a separate actor and external messages to affect 
  that behavior must include the resulting appId. The main actor receives the messages
  and dispatches them if an appId is included. Bookkeeping and logics to start/stop/pause
  each behavior being added. Concurrent applications now fully supported.
- UI layout communicated to view from actors with view components. Marshalling
  mechanism for inclusion of UI-driven actions in place and working. Begin support
  for UI in the IDE.
- k.Actors improvements include parsing of keys (introduced by : or ! for naked
  negative keys that encode false) for group and value metadata and addition of
  tags (#xxx) to any statement for identification wherever needed. Some syntax 
  highlighting and parser fixes.
- Validate units in ratios of physical properties, skipping aggregation.
- Units in physical properties annotating resource attributes are mandatory.
- Using 'within' is only allowed in the first observable of a model.
- Improve model commands to resolve models and visualize resolution strategies, adding 
  the resolution context and optional contextualization to geocoded names and years 
  in model query.
- In observables, getContext() of an observable O whose semantic context is X and
  was declared as "O of X" will strip the context and return null, allowing indirect
  inherency to match data in dependencies when the contexts are incompatible. This 
  does not affect the getContextType() applied to the semantics, which will remain
  X.
- Setting temporal extent and resolution from explorer for subsequent observation
  is now supported.
- Engine events can be generated and notified to clients that subscribe to them,
  using an identifier to allow asynchronous events of the same type to set remote 
  state coherently. Only one event type exists for now, sent to notify of ongoing 
  resource validation when it's likely than any observation will leave the workspace 
  in an inconsistent state.
- Fix an overzealous referencing that prevented a second filtered dependency to 
  be computed correctly (e.g. two "normalized" observations in the same dependency
  list).
- Notification logics overhauled to enable a subscription model for any observations
  that the view wants notified.
- Resolution 'within' observables now completely supported. Explicit 'within' observables
  are only allowed as the primary outputs of models. They produce observations within
  a specialized context observation and are used to resolve "X of Y" observables in
  contexts that are not Y. Each observation made within Y will first instantiate
  Y, then observe X in each instance. If the observation is triggered in a context
  that is not Y, the merged observation X of Y will be produced to satisfy the
  query. This does not apply to learning models when they have an archetype
  that is Y: in that case the semantics "learn within Y" is interpreted as "use the
  distributed archetype to learn a quality" which is then applied to the context if
  the predictors match it.
- Clarify the computation of semantic distance when observables or their
  inherent types are abstract (necessary condition for transitive resolution). 
  Deprecate Observables.isCompatible in favor of IConcept.resolves(). Could be
  repurposed after refactoring, calling resolves() and then proceeding to assess
  unit compatibility according to extent and value operator consistency.
- Actors can schedule actions (needs synchronization to ensure no temporal overlap
  can happen), bind behaviors and use the monitor to send notifications.
- Deferred resolution for observables whose context is a subject incompatible with
  the context of observation, after successful instantiation of the subjects
  themselves.
- New scaling instantiator for spatial objects uses a set of resources to assess the
  most appropriate scale for the results. Flexible options to ensure coverage and 
  define granularity preferences. Watershed instantiator being rewritten to
  use that with global HydroBASINS data. 
- Vector resources return clipped geometries as before unless a new intersect=false parameter 
  is passed in the URN.
- Direct observations with suitable dimensionality inherit the context's 
  scale by default, inheriting grid specifications. Implementation is still incomplete
  but the @space and @time annotation can be used on the instantiators to change
  the defaults.
- There is now just one dataflow per context, with hierarchical structure, and each
  new resolution below the root level creates a new (void) resolver at the level
  it pertains to. Distributed resolution is now done by reasoning on the contexts
  of the observation, creating the context as an observation of it if not the same
  as the root context and distributing the resolution over all the observations
  instantiated.
- All IIdentities now have the option of becoming actors by loading a IBehavior. 
  If behavior isn't loaded but actors exist in the session, they automatically become 
  "dumb" actors that do nothing but do take messages. Declaration statement in k.Actor 
  files differentiated to reflect the various identity destinations.
- Actor language design and support (k.Actors) and stubs for integration in engine. 
  This is the component that will enable full-scale IBM and allow building palettes 
  and applications by interacting with system, context and view actors.
- Rename model's IBehavior to IContextualization to free up IBehavior for the product 
  of parsing an actor specification. Change all the implementations and accessors 
  too.
- Implement API for modification logging as part of provenance model with provenance 
  nodes having getActions() -> List<IActivity>. This will be used to ensure recomputation 
  of artifacts whose dependencies have changed during the latest transition.
- Change models that use resources that provide different temporal states for a changing 
  quality are enabled. They automatically export the quality as output unless it's 
  explicitly mentioned as either an output or an input.
- Improve logics in notifying partitioned observations so that small slivers of uncovered 
  space don't create false partitions that result in nonfunctional models.
- Add UI in IDE to specify or select a categorization in resource editor.
- Add syntactic support for "change rate of" operator to refer to the rate of change 
  for a quality (dQ/dt). Also support 'changed' (quality->event). Observation ontology 
  has correspondent concepts, still missing the observable constructors.
- Allow concept declarations using 'equals' in test and script namespaces.
- Enable support for auxiliary variables (https://integratedmodelling.org/jira/browse/KEN-16)
- Extend IResourcePublisher with an IResourceEnhancer interface that can postprocess 
  resources (in asynchronous, arbitrarily long operations) and modify them for better 
  accessibility, performance or content.
- Implement publishing of vector and raster files to node-connected Geoserver and 
  enhancement of vector/raster public resources to WFS/WCS ones.
### Fixed
- Several fixes in unit validation.
- Testing and fixes in use of "where" value operator.

## [0.10.0.222] -- 2020/02/01
### Added
- First tests with remote resources using test UrnAdapter working.
- Add ITicket and ITicketManager for basic, file-based ticket management in both 
  engine and client packages.
- IDE reads status of network periodically and enables publish action if there are 
  nodes that allow publishing for the adapter of the resource.
- New Configuration::getProperty method uses overriding system properties.
- Node permissions (query, publish and use of each adapter) configured from properties.
- Node has its own engine and publishes basic permissions and adapters in capabilities.
- Full CLUE-inspired (and much extended) landcover change module working and documented.
- Fixed logics in contextualization of sub-countables removes bugs in relationship 
  contextualization.
- New class LocatedExpression to (eventually) substitute all custom expression handling 
  in contextualizers, accessing neighborhoods and the like automatically.
- IResourceCalculator class to build an easy to use API wrapper for a resource and 
  evaluate it as a scalar and/or in a located context.
- Learning models without an archetype will use the main observable as a dependency 
  and change the main observable to its "predicted" version using the worldview core 
  concept for predicted, now in the observation ontology. This allows faster and 
  easier specification of simple learners. Distributed learned qualities now offers 
  a sample (fraction) and a select (points for sampling) option in the Weka contextualizers.
- Archetype and predictor annotations are now part of the main engine instead of 
  the ML component.
- Indexing of states based on temporal geometries working in visualization API.
- New 'level of' operator to allow easy modeling of orderings (on SB's suggestion).
- New 'change in' operator creating the process that changes a quality. The process 
  is inherent to the quality, automatically "affects" it, and a dependency is added 
  to models to set the initial value.
- New 'summed' and 'averaged' modifiers in value operators to force aggregations 
  to produce sums or averages independent of semantics. "Total" should work the same 
  way.
- API to support 'universal URNs' with 'klab' as node name, resolved entirely by 
  the engine using IUrnResolver.
- Implementation of 'klab:osm:' open street maps URNs and search service using OSMNames 
  linked to free-text search.
- Stubs for event instantiators in space/time.
- Observations with occurrents promote the temporal context from generic/specific 
  to grid. Temporal logics straightened out and better understood.
- Lots of additional subtlety in the contextualization of dependencies and secondary 
  observables. In general, contextualization is done as soon as possible, i.e. in 
  model declarations according to the model type. Instantiators are the only ones 
  where dependencies are contextualized to the context at resolution time, as the 
  context cannot be known in advance. Context is always validated but only assigned 
  to dependencies when the primary observable has *explicit* ('within') contextualization.
- Learning models now learn contextual qualities properly and create an "archetype" 
  state that does not end up in the context or in the notifications, so that the 
  API of the contextualizer does not change.
- Reset the build count from 200 and align the codebase to the new hub and node architecture.
- Now understanding 'set xxx to []' with an unknown xxx (not an output and not the 
  implied inherent for change processes) as "variables" that will be computed and 
  become available in subsequent actions. Using new IContextualizer#isVariable API.
- Syntactic support for 'merging <model|resource>....' instead of 'using' ... in 
  models, reserved to change models that can string together compatible temporal 
  resources or models.
- Straighten out and clean up k.DL for targeting computations: xxx <- contextualizer() 
  for variables, xxx >> mediator() for mediators, and contextualizer() >> xxx for 
  targeting alternative outputs.
- Any stray storage remained on disk after previous contextualizations is deleted 
  at engine boot.
### Changed
- Resources can be "granular", i.e. composed of multiple resources with individual 
  geometry that give the full resource geometry when merged. Mostly unimplemented.
### Fixed
- Learning models and void models are no longer indexed.
- Catch exceptions from botched URN used for model coverage at indexing so that boot 
  continues instead of aborting without notice.
- Full testing and support for learning qualities from a sampled distributed archetype, 
  both categorical or numeric.
### Removed

## [0.10.0.179] -- 2019/09/05
### Added
- Logics for instantiation and subsequent resolution of attributes in direct observations 
  wired in. Many fixes to resolution and kbox search to support the implicit inherencies 
  required, including to the Description activity API.
- Support in k.IM for quantities with units, such as 10.year, to be used in function 
  and annotation parameters.
- k.IM support for date literals.
- Added a highly configurable flow accumulation resolver that can walk the flow directions 
  from the outlet and perform all sorts of neighborhood calculations to compute an 
  arbitrary quality along the drainage directions.
- Add 'distribute' upstream expression to flow accumulation resolver for upstream 
  calculations.
- Clarify semantics for generic time extents (see comment in ITime.java)
### Changed
- Overhauled the location mechanism for subsetting and scanning geometries and scales. 
  Now consistently using IGeometry.at(...) to flexibly obtain a locator and ILocator.as(Class) 
  to adapt a locator to one with the desired API.
- Contexts built by default at k.Explorer map zooming now include the generic time 
  focused on the current year.
- Revised k.IM syntax for restrictions and allowed arbitrary properties to be defined 
  through it, which is discouraged but should remain possible. Implementation still 
  ignores all that. Put stubs in the docs to document why using attributes, roles 
  and the like is a better strategy that does not introduce opaque semantics.
- Queries for concrete attributes applied to objects produce observation groups that 
  are **views** of the main group containing all observations of the same base type, 
  regardless of attributes.
- Predicates (and only predicates) will resolve more specific predicates, even if 
  the resolving concept is abstract.
- The default storage provider now uses a single storage back-end that only creates 
  storage when needed, avoids temporal repetition of timeslices, and uses a contiguous 
  memory-mapped file to hold the active slices. Degrades gracefully to almost nothing 
  when storing all nodata or a single value, independent of geometry size, and only 
  uses off-heap memory from the OS as needed. Flexible interface can be reimplemented 
  on top of Spark or other distributed storage.
### Fixed
- Fix declaration of presence dereifier (which caused a number->boolean conversion 
  being inserted, and false being produced overall).
- Fixed new Eclipse lack of sync upon creation of scripts and test cases.
- Fix silly parsing order bug that had deactivated partition resolution.
- Bring back (and fix) mediation logics for multi-source observations with scale 
  partitions.

## [0.10.0.176] -- 2019/07/30
### Fixed
- Abstract concepts declared as children of another concept are now made abstract 
  as requested.
- Fixed rare situation in which namespace IDs were wrongly reported as illegal (when 
  they were secondary namespaces and the root namespace had the same ID as the project).

## [0.10.0.175] -- 2019/07/27
### Changed
- Switch the documentation templates to Asciidoc and set the docs/ folder up as a 
  maven project. Obsolete Sphinx-based files are in etc.
### Fixed
- Correct some lack of sync between IDE and k.IM project structure (in part due to 
  adopting an upgraded Eclipse).

## [0.10.0.174] -- 2019/07/23
### Added
- IDE now shows input and output observables when expanding a model, with the abstract 
  observables in italics and a color-coded icon. The label is the formal, normalized 
  and fully parenthesized declaration, so that modelers can be sure of the logical 
  structure of each statement.
- IDE now adds a black 'lock' marker to namespace-private models and namespaces. 
  Project-private gets a gold lock.
- In observable declaration, 'by' and 'down to' are now merged with all the other 
  value operators and treated consistently.
- Overhauled the ObservableReasoner strategy builder into a new ObservationStrategy 
  class and inference engine; add 'model compute' CLI command to test the inferences 
  made for any observable.
- Finally made it illegal to have lone traits as observables or dependencies, in 
  preparation for attribute instantiators and resolvers. Error messages suggest use 
  of 'type of' to contextualize traits.
- k.IM expressions can be forced to evaluate in scalar context by prefixing them 
  with a pound sign. This can be used e.g. in an area evaluator that wants the area 
  of each subdivision for further aggregation, so that 'space' refers to the subdivision 
  instead of the context.
- Value operators are recognized and handled both in dependencies and in the Explorer.
- Semantics for attribute and role instantiation and resolution is defined and legal 
  in k.IM.
- Attribute resolvers/instantiators have new observation types CHARACTERIZATION and 
  CLASSIFICATION respectively. The previous CLASSIFICATION (dedicated to contextual 
  observation of class qualities) is now more appropriately CATEGORIZATION.
- Actuators that have CHARACTERIZATION or CLASSIFICATION for their observable now 
  require the contextualizers to be filters. Those are merged with the computation 
  of the containing actuator instead of leaving the actuator in the dataflow.
- Attributes qualified with 'rescaling' modify the observation semantics of the quality 
  they apply to, and cause units to be removed and to become illegal in k.IM. They 
  are also the only ones to be handled through attribute resolution when applied 
  to qualities.
- Models check the units agains the geometry and allow aggregating units, rescaling 
  automatically at mediation and requiring a @extensive/@intensive annotation to 
  remove the warning in case a model produces output that is legitimate but requires 
  to recontextualize the values. This is a pretty big change as k.LAB is now dead 
  serious about units and their proper use, while modelers are usually half-dead 
  sloppy.
- The computational typechain, contextualizer output and geometry are now checked 
  for coherency within models, both across each other and vs. the observable semantics.
- Monetary currencies now admit distributed spatial and temporal unit terms and can 
  produce the corresponding unit (with 'unitless' as the base unit). Observables 
  with MONEY or NUMEROSITY type now carry their actual unit, either the unit corresponding 
  to the currency or the unitless distribution of the 'per' unit for counts.
- Enabled unspecified units in observable that need units to make it possible to 
  apply units flexibly but ensuring that the same base types have the same unit within 
  a set of dependencies. Otherwise the unit does not default to the base SI unit 
  because the geometry of the context cannot be predicted; instead, any model that 
  annotates resources is required to specify the units.
- Wire in the LogMap2 aligner with a 'reason align' CLI command. Treatment of mappings 
  is non-existent for now.
- Add a 'unit info' and 'unit contextualize' CLI command to check and analyze units.
- Add a 'model info' CLI command for detailed info about a model.
- Switched from 'private'/public status for models and namespaces to 'private', 'project 
  private' and public. Now models and namespaces carry scope with them instead of 
  a private flag, and models cannot increase the visibility of their namespaces.
- Add tentative API for characterizers and classifiers (attribute resolvers and instantiators), 
  still not wired in.
### Changed
- k.IM highlighter uniformly shows abstract concepts in italics and concrete concepts 
  in regular type. This becomes important as attribute models work differently in 
  either situation.
- Abstract status of an observable is now dependent on whether the main observable 
  is concrete; if not, it changes to concrete by adopting concrete realms or identities 
  (all realms or identities must be concrete) and, if any, concrete subsetters (such 
  as inherents, context, etc). This will be eventually complemented by checking that 
  these are of the 'require' persuasion.
- Annotations and function calls now accept a list of unnamed parameters as well 
  as a single one. Anything declared as a list will be a list in the API, even if 
  only one value is passed.
- Changed 'kbox' command namespace to 'model'.
- Changed 'process' to 'filter' in k.DL and corresponding 'isProcessor' to 'isFilter' 
  in actuator API.
- Removed previous handling of 'rescaling attributes' in favor of new generalized 
  handling of attributes and roles.
### Fixed
- Overhaul logic for communicating errors detected by the engine to the IDE; now 
  they are stored for the standard validator to find. The process is supported for 
  models and observables within models only.
- Notification markers should finally be reliably added to the IDE both for syntax 
  and reasoning errors. There are still issues for syntax errors not visible upstream 
  at project load.
- Fix implementation of 'equals' and allow it to use core concepts in the root domain, 
  so that k.IM proxies for all core classes actually use the core ontology instead 
  of branching from it. This way core concepts can be correctly used in models that 
  use generic scope in context, inherents and the like.
### Removed
- Remove obsolete projects klab.core (merged with klab.engine), products/explorer 
  and products/klab.explorer.

## [0.10.0.163] -- 2019/06/23
### Added
- Added value operators to observable syntax, allowing expressions as observables 
  and as dependencies. Quantitative aggregation using 'by' is already working.
- Template-based system documents all nodes in the dataflow, accessible through single-clicking 
  each node. Contextualizer can now add documentation items by implementing IDocumentationProvider.
- Reporting engine collects tags for contextualizer-provided docs and inserts those 
  not used in templates in the appendix.
- Enable trailing 'l' (lowercase only) to specify long integers in k.IM. Used in 
  time specs to discriminate millisecond parameters.
- Add 'expression' modifier to tag inputs that accept expressions in k.DL.
- Add 'process' modifier to specify filter services in k.DL, used to validate role 
  contextualizers. Expose as isProcessor() in both actuator and prototype API. Produce 
  tag interface for contextualizers.
- Better behavior and proper loading of imported BIF files in WEKA resources.
- Enable much more functionality in search bar completion, including modifiers in 
  preparation for the full set of value operators.
### Changed
- Interpret 'type of <trait>' as the quality that incarnates that trait in a context. 
  This makes it possible to stop using traits as observables (e.g. LandCoverType 
  is type of LandCover).
- Overhaul the dataflow visualization, with better and complete representation of 
  all links within and across components and computations. System now carries types 
  for computations, enabling forthcoming customization of graph, and discovers 'hidden' 
  links from expressions in parameters and tables.
- Overhaul the reporting of observations during contextualization to use the physical 
  structure of the artifacts instead of the logical structure of the observation 
  tree.
- Additional caching in lookup tables greatly speeds up computations using them.
- Observables are no longer concepts, cleaning up a lot of code and finally preventing 
  internal names from raising to the surface in display labels.
- Aggregators in observables ('by') no longer create a different concept but preserve 
  the semantics of the aggregated observable, only affecting observation semantics.
### Fixed 
- Clean up resolution of derived observables using a convenience model, enabling 
  fully recursive resolution and removing previous error with transformed + non-transformed 
  dependencies in the same model.
- General cleanup of dataflow compiler due to above improvement.
- Transformed observables no longer carry the units of the non-transformed equivalents. 
  This may require revision on an ad-hoc basis (should probably check the transformation 
  declaration to see if it preserves observation semantics).
- Resource metadata are now saved properly.

## [0.10.0.162] -- 2019/05/29
### Added
- Basic support for currencies, without conversion.
### Changed
- Same-workspace criterion added to lexical scoping: models in local workspace are 
  always prioritized. All else being equal, projects from the same workspace as the 
  namespace of resolution will be prioritized.
- Concepts are compared by matching definitions if the string value comparison fails.
### Fixed
- Correct grid size reported after context setting.
- Completed logics for attribution of specific colors for categories in colormaps.
- Observable names are now disambiguated before resolving them vs. artifacts already 
  present from previous observations, preventing conflicts when different observables 
  have the same name.
- Colormaps for rasters that have the same value overall but also nodata values are 
  now generated correctly.
- Fix messaging exception when notifying a resource import in IDE.
- Fix deletion of resource just after importing when saving the resource data.
- Fix workspace attribution logic for imported projects not in IDE workspace.

## [0.10.0.157] -- 2019/05/14
### Added
- Stubs for table component and alignment component.
- Prototypes from KDL now expose imports and exports as well as parameters.
- Resource can be now "dropped" INTO existing resources, for adapters to react to.
- Resource editor now has a table for "outputs" besides the main observable, used 
  by computations that provide additional observations.
- Put a bit of color in the KDL editor, just for fun.
- 'monetary value' now produces money instead of mere values, so that currencies 
  are accepted.
### Changed
- KDL actuator syntax cleaned up, now using a single set of rules.
- Use indented JSON output for references and documentation to ease understanding 
  and merging in git.
### Fixed
- Partial context coverages and boundary-invariant partitions now handled correctly, 
  with partitions being generated only if >1 models with different coverage boundaries 
  exist.
- Incremental coverages do not affect resolution until max coverage is reached.
- Support collection of annotations from the observable of merged states (not from 
  the individual partition models).
- Attribution of links in observation hierarchy is back to sanity.

## [0.10.0.155] -- 2019/05/09
### Added
- Implemented WEKA "save to resource" behavior and encoder for WEKA resource.
- Add a StandaloneResourceBuilder for easy programmatic construction of resources.
- Model docstring is used to create default section descriptions with interactive 
  model parameters provided through @parameter annotation. Also now available as 
  rdfs:comment in model metadata.
- New annotation @NonReentrant can be used to tag a state resolver that we don't 
  want the runtime system to parallelize.
### Changed
- Resources now can have dependencies as well as attributes.
- Geometry admits 'generic' extents using Greek letters and has a GeometryBuilder 
  to ease creation.
- Scale and extent have uniform merge() methods and isGeneric() to check for generic 
  definitions, which will be compounded to potentially redefine scales.
- Local resource catalog is now a simple in-memory hashmap.
- Models are tested for online availability of the resources they use before they 
  are chosen by the resolver. If offline models prevent resolution a warning is emitted, 
  otherwise just an info message. Debug messages detail which models were chosen 
  but were offline in any case.
- Contextualization logic handles partitions and masks differently, never touching 
  extents that aren't covered.
- Topological sorting of actuators now uses custom logics to enable special treatment 
  of partitions and other situations.
- Logics to compound and merge partially specified subscales now in place, still 
  needing treatment of various situations at extent level.
### Fixed
- Resource deletion, addition and update now refresh the Eclipse environment correctly.
- Improved resource editor (better time widget, support for dependencies, hierarchical 
  editing of properties).
- All examples with complex, intermingling partitions now working at least on spatial 
  extents.

## [0.10.0.154] -- 2019/04/23
### Added
- Add and support 'monetary' qualifier to 'value of' semantic operator also to enable 
  validation of currencies.
- Relationships are instantiated and resolved.
- Configurations are detected, created and resolved.
- Relationship instantiator klab.networks.connect functional (some methods missing).
- k.Explorer interactive mode supported and working.
- Enable parameterization of models with Groovy code through @parameter annotation, 
  which also admits interactive mode editing.
- Enable interactive parameters in observable annotations with special label handling.
- Basic GEXF and GraphML export for network configurations, including spatial attributes 
  for GEXF.
- Basic JSON network configuration export with format 'json', untested.
- Add and support syntax to further specialize relationship source and target types 
  in declarations (... linking ... to ...).
- Various state contextualization use cases (using @ after the identifier ) are now 
  supported.
### Changed
- Easter egg surprise: new Groovy wrapping and caching strategy makes expressions 
  evaluate much faster. Most raster-based repeated calculations that took many minutes 
  now compute in under a second.
### Fixed
- Abstract child classes in concept definition weren't abstract and now they are.

## [0.10.0.153] -- 2019/04/12
### Added
- Info messages in k.Modeler are double-clickable for detail pop-up again.
- Project deletion is now implemented (engine does physical deletion - use with caution).
### Changed
- Allow complex declarations after classifier 'by' to eventually enable conceptual 
  or contextual redistribution of models.
- Self-generated model names are now fully descriptive of their observables.
- Completely remove knowledge.kim project-wide namespace as it conflicts with k.LAB 
  best practices.
### Fixed
- Improve camelcase translation in observable names to avoid a_b_c_d weirdness.
- KLAB-127 Exception when creating new projects due to incomplete removal of knowledge.kim 
  from logics.
- Only 'within' concept can be null in contextual resolution (caused bad resolution 
  bug, visible in soil retention model).
- Project creation refreshes navigator properly.

## [0.10.0.152] -- 2019/04/04
### Added
### Changed
- All concepts "dropped" are now resolved contextually, i.e. modified before resolution 
  to the same concept 'within' the subject concept that they are observed into. This 
  enables selective resolution (i.e. within watershed can select different model 
  than within region) and enables much more sophisticated validation, but also tests 
  the ontologies to new levels, so extra care is now needed in attributing context 
  to concepts. The IM ontologies are being revised to ensure correct operation everywhere, 
  so far all main models should be OK.
- Overhaul resolution using IConcept.resolves() rather than isCompatible and removing 
  use of inheritance in kbox search strategy, as derived concepts can now be redundant.
- Derived concepts are created in the topmost ontology in the dependency tree, including 
  all component namespaces, or in the reasoner's top ontology if additional ontology 
  dependencies would occur doing so. Redundancy for the same declaration is now possible, 
  with a linear ontology import closure as the advantage. Option to redirect the 
  "common ontology" externally is now disabled.
- Importing of referenced namespaces within the same workspace is now mandatory, 
  and import of namespaces outside of the workspace is an error.
- Circular dependencies in import lists are now detected and flagged as errors as 
  you type.
- Metadata of instantiated objects are available to the dataflow to resolve attribute 
  observers if needed. Resolution uses metadata first, names of states next and types 
  of states last.
- Full round-trip path between k.IM text declarations, syntactic concept declarations 
  (IKimConcept) and concepts in ontologies (IConcept). ObservableBuilder should now 
  correctly enable any modification of existing concepts.
### Fixed
- WFS and vector attribute fixes.
- More contextualization exceptions are now reported to clients (no need to look 
  in engine UI window).
- Number of instances correctly reported in WEKA training.

## [0.10.0.151] -- 2019/03/26
### Added
- Begin supporting recontextualization of distributed states in expressions through 
  @-modified identifiers (e.g. elevation@nw). These are recognized and transpiled 
  but not used yet. Use of recontextualization is disabled for expressions used in 
  documentation to avoid conflicting with @-directives.
- Add CLI 'kbox' command namespace for kbox inquiries and enable 'reason info' for 
  ontologies.
- Support 'equals' instead of 'is' in concept declarations to declare aliases instead 
  of new concepts. All implications still to be tested.
- Begin reintegrating authorities, starting from GBIF.
- Add syntax (only) to refer to a pure authority identity as a concept (identity 
  ID by AUTH.ID).
### Changed
- Derived concepts are created within the ontology hosting the main concept once 
  again. This is needed otherwise the import structure becomes circular; should probably 
  eliminate the configurable option.
### Fixed
- Better handling of Groovy expressions using and returning concepts.
- Fixed some NPE situations when parsing models with unknown semantics.
- Fixed situations where observables would not be compatible with themselves due 
  to inherency chain issues.
- Fixes in attributes exported with shapefiles.

## [0.10.0.150] -- 2019/03/11
### Added
- Begin keeping this changelog.
- Prototype of configuration detector.
- ML/Weka component is functional (Weka bridge through functions, prediction of distributed 
  qualities and their uncertainties); generation of models and resources with import/export 
  still unimplemented.
- Function and annotation documentation appears on hover in k.Modeler.
- Service workspace for scratch resources and user-level explorer uploads and scenarios 
  is created and managed by the engine.
- Beginning of resource add by drop functionalities (imports resource in scratch 
  project; sets context if resource is object and none is defined).
- Improved and generalized observation export features using adapters.
- Export to shapefile working for both individual observations and folders.
- Hyperlink resource names to resource editor in modeler when ctrl-clicked.
### Changed
- Make local resource database in-memory for now.
- Improve docs and handle concept count in GIS neighborhood resolver.
### Fixed
- Additional output states created and reported.
- New context notification includes scale.
- Various bug fixes.

