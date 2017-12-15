Using an embedded k.LAB engine
==============================

A programmer can use the engine API to embed observation functionalities in Java programs. A basic example of using the Java API to access the network and make observations through referencing concept and observation URNs could look like this:

.. code-block:: java
    :linenos:

    IModelingEngine engine = new ModelingEngine().start();

    try (ISession session = engine.createSession()) {
      IContext context = 
        session
          .observe("im:ariesteam:example.locations:tanzania-grr").get()
          .observe("geography:Elevation").get();
       // visualize in k.Explorer (opens a browser window)
       context.explore();
    } finally {
        engine.stop();
    }
The code is simple, but a lot is going on behind the scenes. Let us analyze the example line by line. The first call

.. code-block:: java

    IModelingEngine engine = new ModelingEngine().start();

will create a new ModelingEngine using the default configuration, which will look for a file named `im.cert` containing a valid k.LAB certificate. This is equivalent to saying

.. code-block:: java

    IModelingEngine engine = new ModelingEngine(new KlabCertificate()).start();

which may be useful if a user has more than one certificate (for example for testing, or to use different worldviews) by specifying
the certificate file:

.. code-block:: java

    IModelingEngine engine = 
       new ModelingEngine(new KlabCertificate(new File("alt.cert"))).start();

In all cases, the start() method called on the engine (which returns the engine itself to enable more compact idioms like the above) will perform all the actions related to synchronizing the worldview, loading it, connecting to the k.LAB network and initializing all network
resources according to the user's permissions. When that exits without error, our engine is ready to make observations. When launched in this mode, the engine will not be available to serve network requests (you can do that by passing the appropriate KlabStartupOptions to start().


.....

A major part of many independent developers’ interests will likely include development of components – independent packages that provide functionalities for k.LAB, such as new model integrations. A Maven archetype is provided that installs a sample component to use as a template. The KLAB project in Bitbucket contains many of the components that are currently available.

Little documentation and support is currently available for working with the k.LAB API, but the IM partnership plans to produce those and maintain active Gitter channels to support open-source developers interested in collaborating with the core team. Question should be directed to info@integratedmodelling.org and we encourage interested open-source contributors to inquire.