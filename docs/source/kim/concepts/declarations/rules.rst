==========================
Semantic composition rules
==========================

The less obvious consequences of combining concepts
---------------------------------------------------

...

Compatibility in models
-----------------------


Propagation of abstract status
---------------------------------

Concept definitions specifically state the abstract (or, with no other specification, *concrete*) nature of each concept. If declarations
combine different concepts, as many do, and some of them are abstract, it is less obvious to define whether the result is abstract or 
concrete. The distinction has important consequences in semantic modeling. The following rules apply:

- a concept that inherits from an abstract observable becomes concrete if 
	- it is identified by an identity or realm, or
	- it is made inherent to a non-abstract subject.
- the concept goes back to abstract if:
	- it is combined with a `base trait`_ (one that is both abstract and defined at the root level)

As explained [before], it is illegal to declare a non-abstract quality that does not define a context, either directly or through 
its inheritance chain.