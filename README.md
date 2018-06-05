# k.LAB: a software stack for semantic modeling

This project contains version 0.10.0 of k.LAB. The k.LAB software stack implements 
a semantic web platform for integrated, distributed semantic modeling. See http://www.integratedmodelling.org 
for details on k.LAB and the mission behind it.

k.LAB aims to address the activity of _integrated modeling_, which reconciles strong 
semantics with modeling practice, helping achieve advantages such as modularity, 
interoperability, reusability, and integration of multiple paradigms and scales. 
To achieve this goal, k.LAB keeps the logical representation of the modeled world 
distinct from the algorithmic knowledge that allows it to be simulated, and uses 
artificial intelligence to assemble computations that produce *observations* of such 
knowledge.

While k.LAB has been in use for several years, this repository is a pre-alpha release 
of a fully rewritten codebase, intended as the first production-level code to fully 
implement the semantic meta-modeling paradigm. 

This repository does not provide useful software yet. The first operational release 
of k.LAB v 0.10.0 is expected in Fall or Winter 2018. It will include the following 
software components:

- engine providing
    - base semantic modeling workflow: reasoner, resolver, dataflow compiler and 
      runtime.
    - support for 2D spatial extents (GIS functionalities)
    - support for temporal extents
    - configurable runtime for local, enhanced local (GPU and virtual memory) and 
      distributed computation;
    - support for machine learning through WEKA integration
    - support for calibration and data assimilation through OpenDA integration
    - REST API and UI for web-based modeling and administration
- node
- tools
- IDE
- Component development tools
- Web UI
- Web explorer
- docs


Users of the current released version (0.9.11) will not find anything useful in this 
repository until the date of release. This repository contains a complete rewrite 
of k.LAB which is entirely incompatible with previous versions, and is released only 
for documentation at this time. Actual development takes place on Bitbucket; only 
major releases are pushed to this repository.

Inquiries should be directed to info@integratedmodelling.org.

## Authors

