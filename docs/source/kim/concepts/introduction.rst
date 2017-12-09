Modeling with knowledge: an introduction
========================================


Observables
-----------

The context
~~~~~~~~~~~

Every observable, except subjects, can only be observed in a context. Rules to establish context:

- if the observable is a quality, the context is the one specified in a within clause, either in the
  same declaration or in an ancestor's. If the ancestor has a context, the one specified in any derived
  declaration must be [compatible].
- if the observable is a configuration or a type associated with an attribute or realm, any [describes] 
  clause may specify a quality which has a context; if so, the context of the described quality must be 
  compatible, and serves as its context if that is not specified otherwise. In case of multiple descriptions, 
  the most specific common concept between their contexts is used.
- identities and processes can be contextualized to the type they [apply to] lacking an explicit context
- any observable that does not have a within clause, the inherent type, if any, serves as the context;

Universals
----------

Configurations
--------------

* automated recognition
* models can attach behaviors and scale to the recognition of the configuration