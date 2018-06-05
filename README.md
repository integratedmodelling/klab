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
knowledge. The resulting user experience enables workflows that only use the semantics 
of the information in order to obtain results, making the need for modeling skills 
a choice rather than a necessity for most users.

While k.LAB has been in use for several years, this repository is a pre-alpha release 
of a fully rewritten codebase, intended as the first production-level code to fully 
implement the semantic meta-modeling paradigm. This repository does not provide useful 
software yet. The first operational release of k.LAB v 0.10.0 is expected in Fall 
or Winter 2018. It will include the following software components, most of which 
have working prototypes in the distribution:

- Semantic modeling engine (klab.engine) providing:
    - support for the basic semantic modeling workflow: k.IM language support with 
      reasoning, resolver, dataflow compiler and runtime.
    - support for creating and using URN-based non-semantic resources and for their 
      semantic annotation in k.IM
    - support for 2D regular and irregular spatial extents (GIS functionalities) 
      through Geotools, including OGC services
    - support for regular and irregular temporal extents
    - support for tabular resources with interfaces for common (XLS, Access, JDBC, 
      text) and specialized (weather stations) formats;
    - configurable runtime for local, enhanced local (using GPU and virtual memory) 
      and distributed computation;
    - Ability to connect to remote computations and build interactive, distributed 
      agent systems (based on Akka);
    - Groovy-based agent modeling language bindings;
    - support for machine learning through WEKA integration;
    - support for calibration and data assimilation through OpenDA integration;
    - REST API and UI for web-based modeling and administration
- node
- Command-line tooling, including:
    - Command line engine interface for debugging and expert operations;
    - OWL processor to save k.IM worldviews as OWL ontologies and perform inquiries
    - Bulk import, validation, publishing and CRUD operations on resources
- Eclipse IDE for modelers, providing:
- Component development tools:
    - Maven component archetype
- Web UI
- Web explorer application
- User and developer documentation (based on Sphinx)

Users of the current released version (0.9.11) will not find anything useful in this 
repository until the date of release. This repository contains a complete rewrite 
of k.LAB which is entirely incompatible with previous versions, and is only released 
for documentation at this time. Development takes place on Bitbucket; major releases 
are also pushed to Github, at the discretion of the developers, when milestones are 
reached.

Inquiries should be directed to info@integratedmodelling.org.

## Authors

