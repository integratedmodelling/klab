# Changelog

All notable changes to this project are documented in this file. The number after 
the minor release is the build number. All changes made in the develop branch appear 
as unreleased until merged to master.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this 
project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

[comment]: <>   (Guiding Principles:)
[comment]: <>   (  Changelogs are for humans, not machines.)
[comment]: <>   (  There should be an entry for every single version.)
[comment]: <>   (  The same types of changes should be grouped.)
[comment]: <>   (  Versions and sections should be linkable.)
[comment]: <>   (  The latest version comes first.)
[comment]: <>   (  The release date of each version is displayed.)
[comment]: <>   (  Mention whether you follow Semantic Versioning.)
[comment]: <>   (Types of changes: )
[comment]: <>   (  Added for new features.)
[comment]: <>   (  Changed for changes in existing functionality.)
[comment]: <>   (  Deprecated for soon-to-be removed features.)
[comment]: <>   (  Removed for now removed features.)
[comment]: <>   (  Fixed for any bug fixes.)
[comment]: <>   (  Security in case of vulnerabilities.)
[comment]: <>   ()
[comment]: <>   (Next build: [0.10.0.xxx] -- ISO Date)


## Unreleased
### Added
- Logics for instantiation and subsequent resolution of attributes in direct observations
  wired in. Many fixes to resolution and kbox search to support the implicit inherencies
  required, including to the Description activity API.
- Support in k.IM for quantities with units, such as 10.year, to be used in 
  function and annotation parameters.
- k.IM support for date literals.
- Added a highly configurable flow accumulation resolver that can walk the flow directions
  from the outlet and perform all sorts of neighborhood calculations to compute an arbitrary
  quality along the drainage directions.
- Clarify semantics for generic time extents (see comment in ITime.java)
### Changed
- Overhauled the location mechanism for subsetting and scanning geometries and scales. Now
  consistently using IGeometry.at(...) to flexibly obtain a locator and ILocator.as(Class) 
  to adapt a locator to one with the desired API.
- Revised k.IM syntax for restrictions and allowed arbitrary properties to be defined
  through it, which is discouraged but should remain possible. Implementation still
  ignores all that. Put stubs in the docs to document why using attributes, roles and
  the like is a better strategy that does not introduce opaque semantics.
- Queries for concrete attributes applied to objects produce observation groups that
  are **views** of the main group containing all observations of the same base type, regardless
  of attributes.
- Predicates (and only predicates) will resolve more specific predicates, even if the
  resolving concept is abstract.
- The default storage provider now uses a single storage back-end that only creates storage
  when needed, avoids temporal repetition of timeslices, and uses a contiguous memory-mapped
  file to hold the active slices. Degrades gracefully to almost nothing when storing all
  nodata or a single value, independent of geometry size, and only uses off-heap memory
  from the OS as needed. Flexible interface can be reimplemented on top of Spark or other
  distributed storage.
### Fixed
- Fix declaration of presence dereifier (which caused a number->boolean conversion being
  inserted, and false being produced overall).
- Fixed new Eclipse lack of sync upon creation of scripts and test cases.
- Fix silly parsing order bug that had deactivated partition resolution.
### Removed

## [0.10.0.176] -- 2019/07/30
### Fixed
- Abstract concepts declared as children of another concept are now made abstract as
  requested.
- Fixed rare situation in which namespace IDs were wrongly reported as illegal (when
  they were secondary namespaces and the root namespace had the same ID as the 
  project).

## [0.10.0.175] -- 2019/07/27
### Changed
- Switch the documentation templates to Asciidoc and set the docs/ folder up
  as a maven project. Obsolete Sphinx-based files are in etc.
### Fixed
- Correct some lack of sync between IDE and k.IM project structure (in part due to
  adopting an upgraded Eclipse).

## [0.10.0.174] -- 2019/07/23
### Added
- IDE now shows input and output observables when expanding a model, with the
  abstract observables in italics and a color-coded icon. The label
  is the formal, normalized and fully parenthesized declaration, so that 
  modelers can be sure of the logical structure of each statement.
- IDE now adds a black 'lock' marker to namespace-private models and namespaces.
  Project-private gets a gold lock.
- In observable declaration, 'by' and 'down to' are now merged with all the other
  value operators and treated consistently.
- Overhauled the ObservableReasoner strategy builder into a new ObservationStrategy
  class and inference engine; add 'model compute' CLI command to test the inferences
  made for any observable.
- Finally made it illegal to have lone traits as observables or dependencies, in 
  preparation for attribute instantiators and resolvers. Error messages suggest
  use of 'type of' to contextualize traits.
- k.IM expressions can be forced to evaluate in scalar context by prefixing them with 
  a pound sign. This can be used e.g. in an area evaluator that wants the area of each
  subdivision for further aggregation, so that 'space' refers to the subdivision instead
  of the context.
- Value operators are recognized and handled both in dependencies and in the Explorer.
- Semantics for attribute and role instantiation and resolution is defined and legal
  in k.IM.
- Attribute resolvers/instantiators have new observation types CHARACTERIZATION and
  CLASSIFICATION respectively. The previous CLASSIFICATION (dedicated to contextual
  observation of class qualities) is now more appropriately CATEGORIZATION.
- Actuators that have CHARACTERIZATION or CLASSIFICATION for their observable now
  require the contextualizers to be filters. Those are merged with the computation
  of the containing actuator instead of leaving the actuator in the dataflow.
- Attributes qualified with 'rescaling' modify the observation semantics of the
  quality they apply to, and cause units to be removed and to become illegal in
  k.IM. They are also the only ones to be handled through attribute resolution when
  applied to qualities.
- Models check the units agains the geometry and allow aggregating units, rescaling
  automatically at mediation and requiring a @extensive/@intensive annotation to remove
  the warning in case a model produces output that is legitimate but requires to
  recontextualize the values. This is a pretty big change as k.LAB is now dead serious 
  about units and their proper use, while modelers are usually half-dead sloppy.
- The computational typechain, contextualizer output and geometry are now checked for 
  coherency within models, both across each other and vs. the observable semantics.
- Monetary currencies now admit distributed spatial and temporal unit terms and can
  produce the corresponding unit (with 'unitless' as the base unit). Observables with
  MONEY or NUMEROSITY type now carry their actual unit, either the unit corresponding
  to the currency or the unitless distribution of the 'per' unit for counts.
- Enabled unspecified units in observable that need units to make it possible to
  apply units flexibly but ensuring that the same base types have the same unit
  within a set of dependencies. Otherwise the unit does not default to the base
  SI unit because the geometry of the context cannot be predicted; instead, any
  model that annotates resources is required to specify the units.
- Wire in the LogMap2 aligner with a 'reason align' CLI command. Treatment of mappings
  is non-existent for now.
- Add a 'unit info' and 'unit contextualize' CLI command to check and analyze units.
- Add a 'model info' CLI command for detailed info about a model.
- Switched from 'private'/public status for models and namespaces to 'private', 
  'project private' and public. Now models and namespaces carry scope with them instead
  of a private flag, and models cannot increase the visibility of their namespaces.
- Add tentative API for characterizers and classifiers (attribute resolvers and
  instantiators), still not wired in.
### Changed
- k.IM highlighter uniformly shows abstract concepts in italics and concrete concepts
  in regular type. This becomes important as attribute models work differently in
  either situation.
- Abstract status of an observable is now dependent on whether the main observable
  is concrete; if not, it changes to concrete by adopting concrete realms or identities
  (all realms or identities must be concrete) and, if any, concrete subsetters (such
  as inherents, context, etc). This will be eventually complemented by checking that
  these are of the 'require' persuasion.
- Annotations and function calls now accept a list of unnamed parameters as well as a 
  single one. Anything declared as a list will be a list in the API, even if only one
  value is passed.
- Changed 'kbox' command namespace to 'model'.
- Changed 'process' to 'filter' in k.DL and corresponding 'isProcessor' to 'isFilter' 
  in actuator API.
- Removed previous handling of 'rescaling attributes' in favor of new generalized 
  handling of attributes and roles.
### Fixed
- Overhaul logic for communicating errors detected by the engine to the IDE; now they
  are stored for the standard validator to find. The process is supported for models
  and observables within models only.
- Notification markers should finally be reliably added to the IDE both for syntax and
  reasoning errors. There are still issues for syntax errors not visible upstream at 
  project load.
- Fix implementation of 'equals' and allow it to use core concepts in the root domain,
  so that k.IM proxies for all core classes actually use the core ontology instead of
  branching from it. This way core concepts can be correctly used in models that use
  generic scope in context, inherents and the like.
### Removed
- Remove obsolete projects klab.core (merged with klab.engine), products/explorer and
  products/klab.explorer.

## [0.10.0.163] -- 2019/06/23
### Added
- Added value operators to observable syntax, allowing expressions as observables and as dependencies.
  Quantitative aggregation using 'by' is already working.
- Template-based system documents all nodes in the dataflow, accessible through single-clicking each
  node. Contextualizer can now add documentation items by implementing IDocumentationProvider.
- Reporting engine collects tags for contextualizer-provided docs and inserts those not used in templates
  in the appendix.
- Enable trailing 'l' (lowercase only) to specify long integers in k.IM. Used in time specs to discriminate
  millisecond parameters.
- Add 'expression' modifier to tag inputs that accept expressions in k.DL.
- Add 'process' modifier to specify filter services in k.DL, used to validate role contextualizers. Expose
  as isProcessor() in both actuator and prototype API. Produce tag interface for contextualizers.
- Better behavior and proper loading of imported BIF files in WEKA resources.
- Enable much more functionality in search bar completion, including modifiers in preparation for
  the full set of value operators.
### Changed
- Interpret 'type of <trait>' as the quality that incarnates that trait in a context. This makes it
  possible to stop using traits as observables (e.g. LandCoverType is type of LandCover).
- Overhaul the dataflow visualization, with better and complete representation of all links within and 
  across components and computations. System now carries types for computations, enabling forthcoming
  customization of graph, and discovers 'hidden' links from expressions in parameters and tables.
- Overhaul the reporting of observations during contextualization to use the physical structure of the
  artifacts instead of the logical structure of the observation tree.
- Additional caching in lookup tables greatly speeds up computations using them.
- Observables are no longer concepts, cleaning up a lot of code and finally preventing internal names
  from raising to the surface in display labels.
- Aggregators in observables ('by') no longer create a different concept but preserve the semantics
  of the aggregated observable, only affecting observation semantics.
### Fixed 
- Clean up resolution of derived observables using a convenience model, enabling fully recursive 
  resolution and removing previous error with transformed + non-transformed dependencies in the same
  model.
- General cleanup of dataflow compiler due to above improvement.
- Transformed observables no longer carry the units of the non-transformed equivalents. This may require
  revision on an ad-hoc basis (should probably check the transformation declaration to see if it preserves
  observation semantics).
- Resource metadata are now saved properly.

## [0.10.0.162] -- 2019/05/29
### Added
- Basic support for currencies, without conversion.
### Changed
- Same-workspace criterion added to lexical scoping: models in local workspace are always 
  prioritized. All else being equal, projects from the same workspace as the namespace of
  resolution will be prioritized.
- Concepts are compared by matching definitions if the string value comparison fails.
### Fixed
- Correct grid size reported after context setting.
- Completed logics for attribution of specific colors for categories in colormaps.
- Observable names are now disambiguated before resolving them vs. artifacts already
  present from previous observations, preventing conflicts when different observables
  have the same name.
- Colormaps for rasters that have the same value overall but also nodata values are now
  generated correctly.
- Fix messaging exception when notifying a resource import in IDE.
- Fix deletion of resource just after importing when saving the resource data.
- Fix workspace attribution logic for imported projects not in IDE workspace.

## [0.10.0.157] -- 2019/05/14
### Added
- Stubs for table component and alignment component.
- Prototypes from KDL now expose imports and exports as well as parameters.
- Resource can be now "dropped" INTO existing resources, for adapters to react
  to.
- Resource editor now has a table for "outputs" besides the main observable, used
  by computations that provide additional observations.
- Put a bit of color in the KDL editor, just for fun.
- 'monetary value' now produces money instead of mere values, so that currencies
  are accepted.
### Changed
- KDL actuator syntax cleaned up, now using a single set of rules. 
- Use indented JSON output for references and documentation to ease understanding and
  merging in git.
### Fixed
- Partial context coverages and boundary-invariant partitions now handled correctly, with
  partitions being generated only if >1 models with different coverage boundaries exist.
- Incremental coverages do not affect resolution until max coverage is reached.
- Support collection of annotations from the observable of merged states (not from
  the individual partition models).
- Attribution of links in observation hierarchy is back to sanity. 

## [0.10.0.155] -- 2019/05/09
### Added
- Implemented WEKA "save to resource" behavior and encoder for WEKA resource.
- Add a StandaloneResourceBuilder for easy programmatic construction of resources.
- Model docstring is used to create default section descriptions with interactive model 
  parameters provided through @parameter annotation. Also now available as rdfs:comment
  in model metadata.
- New annotation @NonReentrant can be used to tag a state resolver that we don't want
  the runtime system to parallelize.
### Changed
- Resources now can have dependencies as well as attributes.
- Geometry admits 'generic' extents using Greek letters and has a GeometryBuilder to
  ease creation.
- Scale and extent have uniform merge() methods and isGeneric() to check for generic
  definitions, which will be compounded to potentially redefine scales.
- Local resource catalog is now a simple in-memory hashmap.
- Models are tested for online availability of the resources they use before they
  are chosen by the resolver. If offline models prevent resolution a warning is
  emitted, otherwise just an info message. Debug messages detail which models
  were chosen but were offline in any case.
- Contextualization logic handles partitions and masks differently, never touching
  extents that aren't covered.
- Topological sorting of actuators now uses custom logics to enable special treatment
  of partitions and other situations.
- Logics to compound and merge partially specified subscales now in place, still needing 
  treatment of various situations at extent level.
### Fixed
- Resource deletion, addition and update now refresh the Eclipse environment correctly.
- Improved resource editor (better time widget, support for dependencies, hierarchical
  editing of properties).
- All examples with complex, intermingling partitions now working at least on spatial
  extents.

## [0.10.0.154] -- 2019/04/23
### Added
- Add and support 'monetary' qualifier to 'value of' semantic operator also to enable validation 
  of currencies.
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
- Various state contextualization use cases (using @ after the identifier ) are now supported.
### Changed
- Easter egg surprise: new Groovy wrapping and caching strategy makes expressions evaluate much 
  faster. Most raster-based repeated calculations that took many minutes now compute in under a second.
### Fixed
- Abstract child classes in concept definition weren't abstract and now they are.

## [0.10.0.153] -- 2019/04/12
### Added
- Info messages in k.Modeler are double-clickable for detail pop-up again.
- Project deletion is now implemented (engine does physical deletion - use with caution).
### Changed
- Allow complex declarations after classifier 'by' to eventually enable conceptual or contextual 
  redistribution of models.
- Self-generated model names are now fully descriptive of their observables.
- Completely remove knowledge.kim project-wide namespace as it conflicts with k.LAB 
  best practices.
### Fixed
- Improve camelcase translation in observable names to avoid a_b_c_d weirdness.
- KLAB-127 Exception when creating new projects due to incomplete removal of knowledge.kim 
  from logics.
- Only 'within' concept can be null in contextual resolution (caused bad resolution bug, 
  visible in soil retention model).
- Project creation refreshes navigator properly.
    
## [0.10.0.152] -- 2019/04/04
### Added
### Changed
- All concepts "dropped" are now resolved contextually, i.e. modified before resolution 
  to the same concept 'within' the subject concept that they are observed into. This enables  
  selective resolution (i.e. within watershed can select different model than within region) 
  and enables much more sophisticated validation, but also tests the ontologies to new 
  levels, so extra care is now needed in attributing context to concepts. The IM ontologies 
  are being revised to ensure correct operation everywhere, so far all main models should 
  be OK.
- Overhaul resolution using IConcept.resolves() rather than isCompatible and removing 
  use of inheritance in kbox search strategy, as derived concepts can now be redundant.
- Derived concepts are created in the topmost ontology in the dependency tree, including all 
  component namespaces, or in the reasoner's top ontology if additional ontology dependencies 
  would occur doing so. Redundancy for the same declaration is now possible, with a 
  linear ontology import closure as the advantage. Option to redirect the "common ontology" 
  externally is now disabled.
- Importing of referenced namespaces within the same workspace is now mandatory, and import of 
  namespaces outside of the workspace is an error.
- Circular dependencies in import lists are now detected and flagged as errors as you 
  type.
- Metadata of instantiated objects are available to the dataflow to resolve attribute 
  observers if needed. Resolution uses metadata first, names of states next and types
  of states last.
- Full round-trip path between k.IM text declarations, syntactic concept declarations 
  (IKimConcept) and concepts in ontologies (IConcept). ObservableBuilder should now
  correctly enable any modification of existing concepts.
### Fixed
- WFS and vector attribute fixes.
- More contextualization exceptions are now reported to clients (no need to look in 
  engine UI window).
- Number of instances correctly reported in WEKA training.

## [0.10.0.151] -- 2019/03/26
### Added
- Begin supporting recontextualization of distributed states in expressions through @-modified identifiers 
  (e.g. elevation@nw). These are recognized and transpiled but not used yet. Use of recontextualization 
  is disabled for expressions used in documentation to avoid conflicting with @-directives.
- Add CLI 'kbox' command namespace for kbox inquiries and enable 'reason info' for ontologies.
- Support 'equals' instead of 'is' in concept declarations to declare aliases instead
  of new concepts. All implications still to be tested.
- Begin reintegrating authorities, starting from GBIF.
- Add syntax (only) to refer to a pure authority identity as a concept (identity ID by AUTH.ID).
### Changed
- Derived concepts are created within the ontology hosting the main concept once again. 
  This is needed otherwise the import structure becomes circular; should probably eliminate 
  the configurable option.
### Fixed
- Better handling of Groovy expressions using and returning concepts.
- Fixed some NPE situations when parsing models with unknown semantics.
- Fixed situations where observables would not be compatible with themselves due to 
  inherency chain issues.
- Fixes in attributes exported with shapefiles.

## [0.10.0.150] -- 2019/03/11
### Added
- Begin keeping this changelog.
- Prototype of configuration detector.
- ML/Weka component is functional (Weka bridge through functions, prediction of distributed qualities and their uncertainties); 
  generation of models and resources with import/export still unimplemented.
- Function and annotation documentation appears on hover in k.Modeler.
- Service workspace for scratch resources and user-level explorer uploads and scenarios is 
  created and managed by the engine.
- Beginning of resource add by drop functionalities (imports resource in scratch project; 
  sets context if resource is object and none is defined).
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

