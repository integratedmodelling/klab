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
[comment]: <>   (Next build: [0.10.0.150] -- ISO Date)

## [Unreleased]

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

