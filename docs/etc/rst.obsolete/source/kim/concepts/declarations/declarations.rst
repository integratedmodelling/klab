Semantic expressions
============================

In user projects, semantic expressions are used to specify semantics for resources and possibly the observations they depend on. This is normally done in :keyword:`model` statements. For example, the following statement

...

contains X semantic expressions. Another place where declarations can be used is to define aliases using the  :keyword:`equals` keyword:

...

This does not create a new concept, but rather defines an *alias* to a complex declaration. This allows models to be written in a more understandable and fluent way. Aliasing should be done in the `knowledge space`_ of a project as explained `here`_, allowing the alias tobe used without namespace throughout the project.

Declarations are also used in definitions_, for example when establishing parent concepts:

... example

The declaration above states that the concept XXX is a subclass (child) of its parent YYY, and further defines how it differs from it by specifying more properties below. This kind of use of declarations is usually only seen in worldviews; in fact, defining new concepts in a user project (one that is not part of the `worldview workspace`_) is allowed, but will prevent the project from being published to a k.LAB `network node`_. The rationale is that the knowledge in the project will, at that point, only be of interest to that project alone. In the `k.LAB IDE`_, you will see that a project loses its `mark of approval`_ decoration the moment any of its namespaces contains a `concept definition`_.

... figures


Simple declarations
-------------------


Establishing context
--------------------