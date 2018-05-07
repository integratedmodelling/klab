# TODO path to 1.0

## Engine


### Basic modeling

- Functional adapters for classifications and k.IM syntax for map literals
- Functional adapters for lookup tables and k.IM syntax for table literals
- Websockets Messaging API: streamline, complete (JSON RPC inspired - no need to comply)

### Resolution

- Rescaling mediators; get test4 working
- Relationship instantiator and resolver: test cases
- Smart resolution of sub-object qualities: use ranking system with scale constraints (i.e. 
  produce Model metadata from states, search there first, if something > x% don't use the network, configure 
  X)

### Temporal contextualization

- TEMPORAL transitions and agents communication.

### Resource API and URN handling

- Local storage and publishing
- Implement and test basic adapters (WCS, WFS, Raster, Vector, Table, JDBC)
- Design the publish/review/accept/reject model and API; explore DOI attribution

### REST interface

- API: work in progress to use RAML as source of truth
    Maven generation -> API constants etc
- Basic workflow to enable observations:
    - Observe context and in context
    - Context structure
    - Get data and maps for obs

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