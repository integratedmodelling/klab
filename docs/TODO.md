# TODO path to 1.0

## Engine

### Validation

- [IN PROGRESS] Filter and validate action types and URN geometry/type
- Semantic macros and missing operators
- Connection to upper ontologies from within worldview
- OWL processor testing and docs

### Basic modeling

- [IN PROGRESS] Allow URN chains for resolving nodata and multiple instance sources
- [IN PROGRESS] Functional adapters for classifications and k.IM syntax for map literals
- [IN PROGRESS] Functional adapters for lookup tables and k.IM syntax for table literals
- [DONE] Improve lookup table k.IM declaration (mapping to table collection)
- [IN PROGRESS] Websockets Messaging API: streamline, complete
- [DONE] Build test cases for non-semantic models
- Build test cases for non-semantic model libraries
- [IN PROGRESS] Build test cases for semantic multiple contextualizers with internal references
- Build test cases for non-semantic multiple contextualizers with internal references
- Recover and test "define" functionalities and imports, including in function arguments 
    
### Resolution

- [DONE] Rescaling mediators; get test5 working
- [IN PROGRESS] Relationship instantiator and resolver: test cases
- [DONE] Smart resolution of sub-scale observations: Cache resolvers at instantiation
- [IN PROGRESS] automatically assign quality values for compatible sub-scale instances
- Interactive resolution workflow (implement and test in both CLI and Eclipse)
- Distributing observations ('by' <subject>) and aggregation by trait

### Temporal contextualization

- Build agent system in EventBus
- TEMPORAL transitions and agents communication.

### Resource API and URN handling

- [DONE] Local storage lifecycle
- Publishing and encoding
- Implement and test basic adapters:
  - [DONE] WCS
  - [DONE] WFS
  - [DONE] Raster
  - [DONE] Vector
  - Table
  - JDBC
- Design the publish/review/accept/reject model and API; explore DOI attribution

### Modeling functionalities

- Missing GIS stuff: 
  - [DONE] rasterizer
  - distance/cost surfaces
  - viewshed
  - spatial indexing
  - routing through features
  - routing through cost terrains
  - spatial network configurations 
- Model artifacts
- Attributes in instantiation (including proper provenance)
- Event subscription
- Configuration detection (may only need networks - others can be created at first use)
- Reintegrate WEKA 
- Reintegrate MCA

### Action language

- Groovy funcs for documentation generator
- Syntax for preprocessing complex concept declarations
- Recover all Groovy functionalities

### Provenance

- Flesh out model and provide adapters for docs, display and export
- Connect with resource metadata and history
- Use in documentation generator

### REST interface

- [IN PROGRESS] API: use RAML as sole source of truth
    - Maven generation -> API constants etc
- Basic workflow to enable observations:
    - Observe context and in context
    - [IN PROGRESS] Context structure
    - [IN PROGRESS] Get data and maps for obs

### Debugger

- Design

### Calibration

- Calibrate with/to a scenario
	- Build template scenario for calibration after baseline run
	- Template has all function parameters and context/observables so far
	- Add calibration data in it and run calibrate which modifies
	  the parameters in the scenario.

## Visualization (Engine)

- Define colormap, shading, bit depth and other visualization options through overriddable 
  annotations on models and concepts. Could/should also apply to individual input/output
  observables and carry on to dataflows.
- Use Geotools style features but make it simple to do in k.IM and support ColorBrewer IDs
  as modifiable starting points.
- Dataflow graphical representation (now very hard to do)
- Provenance graph (?) and browser

## UI (IDE)

- Navigator
  - Developer, Bookmark, Script modes (possibly other filters)
  - Better tree display
  - Linking with editor and other views
  - Coordinate Git and project open/close/delete with core actions
    
- Runtime view
  - Status bar
  - Multi-function log and context switcher
  - Runtime area (dataflow, interactive parameters, debugger pane)
  - Engine status and control
  - Provenance visualizer
    
- Context view
  - Similar to now, improve S/T setters
  
- Palette
  - Simpler, substitutes bookmarks, self-organizing
  - Enables context persistence
  - Configurations sent to engine and saved to nodes so they can be retrieved from the 
    web too
    
- Knowledge browser
  - Intelligent (compose concepts)
  - Interacts with palette

## UI (Web)

- Display is always tuned to a session. Two "modes" of operation:
  1. No context active: ROI setting (move map), browse previous (palette)
  2. Tuned in on a context: drag/drop to 

## Documentation

### API documentation

### Javadocs

### k.IM documentation

### Model documentation

### Developer documentation