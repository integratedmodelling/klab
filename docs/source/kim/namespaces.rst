Introduction: project organization and namespaces
=================================================

Semantic content specified in k.IM is organized into *namespaces*, which are in turn collected into *projects*. Projects are normally part of *workspaces*. For example, the worldview that k.LAB loads after the certificate is read is a workspace composed of several projects, each providing semantics for some domains of knowledge. 

All these are created automatically; if you are using the k.LAB IDE to develop your own models, the user workspace coincides with the Eclipse workspace.

The basic unit of specification for k.IM is the namespace, which corresponds to one resource (usually a file on disk). Within a namespace, you write statements that specify concepts, models, or observations.

Only in special situations namespaces are allowed to exist outside of projects. Such situations are defined in .... We assume in this section that namespaces are always within a project, and the project is within a workspace. The k.LAB IDE is the suggested environment 
to develop in k.IM, and it enforces this organization by providing a default workspace (equivalent with the Eclipse workspace) and wizards
to create k.IM projects and to create and edit namespaces within them.


Workspaces
----------

During a regular k.LAB session, the engine creates four workspaces, only one of which is directly under control the user:

#. The *core ontology* workspace, containing upper ontologies required by the worldview;
#. The *worldview* workspace, containing all the domain ontologies and their mapping to the core ontology;
#. The *component* workspace, containing all sources of knowledge and computable artifacts harvested from the network as observations are made;
#. The *user* workspace, where projects written by the user or synchronized from network sources are found.

All the projects in a workspace are loaded at the same time, and the namespaces in it must properly reference each other as discussed `below <imports_>`_. The engine monitors changes in the user workspace (and only there), automatically reloading a namespace and all those that import it when the file that defines it changes.


Projects
--------

A project collects namespaces within a common


Namespaces
----------


.. _imports:

Importing namespaces
^^^^^^^^^^^^^^^^^^^^

All the projects in a workspace are loaded at the same time. If a namespace uses anything from another namespace in the same workspace, it must *import* the namespace to guarantee that all knowledge is defined in the right order. This is done using the :keyword:`using` keyword:

.. code-block:: kim

     namespace earth
       "Defines fundamental natural concepts pertaining to planet Earth. Also contains
        knowledge regarding the physical processes that act at the planetary level, such
        as climate."
      using im, physical, chemistry
      in domain im:Nature;

Circular references (direct or indirect) must be avoided when defining imports. The k.LAB IDE editor will raise an error if a concept from an external namespace (in the same workspace) is used without importing the namespace.
 
.. note::

	Namespaces from previously loaded workspaces (for example from the worldview) become part of the established "knowledge environment" for the workspace, and do not need to be referenced. In fact k.LAB will raise an error if they are imported. This means, for example, that concepts from the worldview (e.g. `geography:Elevation`) can be freely referenced in any user namespace without having to import their namespace (`geography`).

Statements
----------

Annotations
-----------
