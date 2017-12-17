Introduction: project organization and namespaces
=================================================

Semantic content specified in k.IM is organized into *namespaces*, which are in turn collected into *projects*. Projects are normally part of *workspaces*. For example, the worldview that k.LAB loads after the certificate is read is a workspace composed of several projects, each providing semantics for some domains of knowledge. 

All these are created automatically; if you are using the k.LAB IDE to develop your own models, the user workspace coincides with the Eclipse workspace.

The basic unit of specification for k.IM is the namespace, which corresponds to one resource (usually a file on disk). Within a namespace, you write statements that specify concepts, models, or observations.

Only in special situations namespaces are allowed to exist outside of projects. Such situations are defined in .... We assume in this section that namespaces are always within a project, and the project is within a workspace. The k.LAB IDE is the suggested environment 
to develop in k.IM, and it enforces this organization by providing a default workspace (equivalent with the Eclipse workspace) and wizards
to create k.IM projects and to create and edit namespaces within them.

Projects
--------

A project is 

Namespaces
----------


Namespace imports
^^^^^^^^^^^^^^^^^^

All the projects in a workspace are loaded at the same time, and the namespaces in it must properly reference each other to guarantee that all knowledge is defined in the right order. This is done using the :keyword:`using` keyword:

.. code-block:: kim

     namespace earth
       "Defines fundamental natural concepts pertaining to planet Earth. Also contains
        knowledge regarding the physical processes that act at the planetary level, such
        as climate."
      using im, physical, chemistry
      in domain im:Nature;

Circular references (direct or indirect) must be avoided when defining imports. 

Namespaces from previously loaded workspaces are part of the "knowledge environment" for the workspace and do not need to be referenced (in fact k.LAB will raise an error if they are mentioned). The concepts from the worldview can be freely referenced in any user namespace.

Statements
----------

Annotations
-----------

Workspaces
----------

During a regular k.LAB session, the engine creates four workspaces, only one of which is directly under control the user:

#. The *core ontology* workspace, containing upper ontologies required by the worldview;
#. The *worldview* workspace, containing all the domain ontologies and their mapping to the core ontology;
#. The *component* workspace, containing all sources of knowledge and computable artifacts harvested from the network as observations are made;
#. The *user* workspace, where projects written by the user or synchronized from network sources are found.

All the projects in a workspace are loaded at the same time, and the namespaces in it must properly reference each other as discussed before. The engine monitors changes in the user workspace (and only there), automatically reloading a namespace and all those that import it when the file that defines it changes.