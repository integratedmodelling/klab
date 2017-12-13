Using an embedded k.LAB engine
==============================

A programmer can use the engine API to embed observation functionalities in Java programs. A basic example of using the Java API to access the network and make observations through referencing concept and observation URNs could look like this:

.. code-block:: java

    IModelingEngine engine = new ModelingEngine().start();
    
    try (ISession session = engine.createSession()) {
       IContext context = 
          session
             .observe("im:ariesteam:example.locations:tanzania-grr").finish()
             .observe("geography:Elevation").finish();
        // visualize in k.Explorer (opens a browser window)
        context.explore();
     } finally {
         engine.stop();
     }

A major part of many independent developers’ interests will likely include development of components – independent packages that provide functionalities for k.LAB, such as new model integrations. A Maven archetype is provided that installs a sample component to use as a template. The KLAB project in Bitbucket contains many of the components that are currently available.

Little documentation and support is currently available for working with the k.LAB API, but the IM partnership plans to produce those and maintain active Gitter channels to support open-source developers interested in collaborating with the core team. Question should be directed to info@integratedmodelling.org and we encourage interested open-source contributors to inquire.