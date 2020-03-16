# k.LAB: a software stack for semantic modeling

This project contains version 0.10.x of k.LAB. The k.LAB software stack implements 
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

While k.LAB has been in use for several years, k.LAB v 0.10.0 should be considered a 
preview release and all APIs and code structure are subject to change at this time. 
It includes the following software components:

- Semantic modeling engine (klab.engine) providing:
    - support for the basic semantic modeling workflow: k.IM language support with 
      reasoning, resolver, dataflow compiler and runtime;
    - support for creating and using URN-based non-semantic resources and for their 
      semantic annotation in k.IM;
    - support for instrumenting "live" sessions, users and observations with behaviors 
      using the k.Actors language, enabling complex individual-based models and specialized 
      applications;
    - support for 2D regular and irregular spatial extents (GIS functionalities) 
      through Geotools, including OGC services;
    - support for regular and irregular temporal extents;
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
- Node server software that can be installed on a network server to create a node of 
  the k.LAB semantic web, with the option of spawning networked knowledge servers 
  and modelling engines as seen fit by the node administrators.
- Command-line tooling, including:
    - Command line engine interface for debugging and expert operations;
    - OWL processor to save k.IM worldviews as OWL ontologies and perform inquiries 
      and alignments
    - Bulk import, validation, publishing and CRUD operations on resources
- Eclipse IDE for modelers, providing:
- Component development tools:
    - Maven component archetype
- Web explorer application
- User and developer documentation (Asciidoc, in early stages of development)

A working distribution of k.LAB is not easily obtainable by merely downloading and compiling 
the code, as using the software requires registration to access the k.LAB semantic 
web. Prospective users should consult https://www.integratedmodelling.org/statics/pages/gettingstarted.html 
to access binary distributions, licensing information and registration instructions.

The development of k.LAB has been or is being supported by the US National Science Foundation, the 
European Union, UNEP-WCMC, the UK NERC, the Basque Government, the Interamerican Development Bank, 
the United Nations and other actors, starting in 2007.

The main active developers are:

* Ferdinando Villa (lead developer and designer)
* Enrico Girotto (Web development, release engineering)
* Steven Wohl (authentication, server infrastructure)

Any inquiries should be directed to info@integratedmodelling.org.


