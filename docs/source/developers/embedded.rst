Using an embedded k.LAB engine
==============================

A programmer can use the engine API to embed observation functionalities in Java programs. A basic example of using the Java API to access the network and make observations through referencing concept and observation URNs could look like this:

.. code-block:: java

    Engine engine = Engine.start();

    try (ISession session = engine.createSession()) {
      
      IObservation observation = 
        session
          // ISession.observe() returns a future ISubject
          .observe("im:ariesteam:example.locations:tanzania-grr").get()
          /* ISubject.observe() will return a future IObservation, in this case 
             a IState (observation of a quality within the subject) */
          .observe("geography:Elevation").get();

        /* visualize in k.Explorer (opens a browser window). This
           displays the entire observation tree, focused on the caller. */
        observation.explore();

    } finally {
        engine.stop();
    }

A lot happens behind these few lines of code. Let us analyze the example line by line. The first call creates and boots a modeling engine, of which you will only need one:

.. code-block:: java

    Engine engine = Engine.start();

This uses the default configuration, looking for a file named `im.cert` in the default k.LAB work directory (`~/.klab`), containing a valid k.LAB certificate. You can use a specific certificate by calling:

.. code-block:: java

    Engine engine = new Engine(new KlabCertificate("/home/john/alt.cert"));
    if (engine.boot()) {
        // error, exit
    }
    // use the engine


In all cases, the initialization method called on the engine will authorize the user, synchronize the worldview from the network, load it, connect to the k.LAB network and initialize all network resources according to the user's permissions. When that exits (after a short wait) without error, the engine is ready to make observations. The engine makes observations within a *session*, which we must request. Operations within sessions are assumed synchronous, while you may have several concurrent sessions without problems.

 A :java:type:`ISession <org.integratedmodelling.api.runtime.ISession>` is a :java:type:`Closeable <java.io.Closeable>` so it can be opened and closed automatically within a try-with-resource block:

.. code-block:: java

    try (ISession session = engine.createSession()) {
       // ...make observations within the session
    } finally {
      // you can call engine.stop() here if you're done. The session is closed automatically.
      // Alternatively, you can call session.close() yourself.
    }

The rationale of having sessions is that an engine can serve multiple simultaneous sessions, owned by different users. In fact, createSession() can be optionally passed a previously authenticated IUser, which the web interface of the k.LAB engine allows to configure and authenticate. In embedded mode, we can simply use the shorthand method createSession() with no arguments, which will create a session owned by the same user who owns the engine and the certificate. You can retrieve this user from the engine using :java:meth:`engine.getUser() <org.integratedmodelling.api.engine.IModelingEngine#getUser>`.

.....

A major part of many independent developers’ interests will likely include development of components – independent packages that provide functionalities for k.LAB, such as new model integrations. A Maven archetype is provided that installs a sample component to use as a template. The KLAB project in Bitbucket contains many of the components that are currently available.

Little documentation and support is currently available for working with the k.LAB API, but the IM partnership plans to produce those and maintain active Gitter channels to support open-source developers interested in collaborating with the core team. Question should be directed to info@integratedmodelling.org and we encourage interested open-source contributors to inquire.
