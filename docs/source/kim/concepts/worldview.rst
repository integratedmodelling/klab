The worldview
=============

EXPLAIN THE WORLDVIEW simply

Observable are linked to COMPUTATIONS - i.e. the specific scientific process that will produce an informational artifact describing fundamental type. If we know what it is, we have a way to compute an observation of it, and with the minimum amount of effort. The fund observation ontology exposes types that are linked to APIs but behave well with upper ontologies, so that they can be linked unambiguously. 

Also endorses and provides a mechanism to easily link terms from vocabularies without risk of ambiguity.
. 

It is possible to define a shallow worldview that uses only the observation ontology; however, a deep worldview uses an upper ontology for the key observables will engender consistency checks that are much deeper and safe when information is linked.


Domains
-------

Domains are explicit concepts in k.LAB, and can only be used in worldviews. In a worldview, each namespace must specify the domain it belongs to. A special domain, the *root domain*, marks the namespaces where connections with upper ontologies can be made. Such namespaces must define all key observables so that they can be used in other namespaces and user declarations.

The following are advanced topics meant for knowledge engineers and users who plan to develop their own worldviews or on using k.LAB as an OWL processor to obtain ontologies to use externally.
k.IM.


Linking with upper ontologies
-----------------------------

k.IM vs. OWL
------------

User does not touch OWL properties; relationships are observables and are specified by concepts. Only the root domain 
points to relationships in upper ontologies, used to model the fundamental concept predicates.

Generalities on the underlying OWL model

Example of k.IM and the correspondent OWL (in Manchester notation?)