# Changelog

All notable changes to this project are documented in this file. The number after 
the minor release is the build number. All changes made in the develop branch appear 
as unreleased until merged to master.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
[comment]: <>   (Next build: [0.10.0.151] -- ISO Date)

## [Unreleased]
### Added
### Changed
### Fixed

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

