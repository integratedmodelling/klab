=======================
Defining concepts
=======================

...

The main concept statement
--------------------------

Abstract status
~~~~~~~~~~~~~~~


Base concepts
~~~~~~~~~~~~~

A base concept is one that has been declared abstract and is at the "root level" in a namespace (that is, it is not declared as a child of another). This distinction is important in [traits], as it is not possible for an observable
to adopt two traits that have the same base trait (see [declarations]). For this reason, declaring a concept as a child to another is not the same as declaring it at the root level.


...

Parents ('is') vs. aliases ('equals')
-------------------------------------

...

Core concepts (worldview only)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



Children
--------
...

Concept clauses
---------------

In the following, applies to denotes the concept being defined. Targets denotes the concept that is mentioned in the clause, which must be defined.

Summary table of clauses. More may be added in future versions,  although the goal is the smallest useful language.

Description clauses
~~~~~~~~~~~~~~~~~~~

Very important in k.LAB. Used in inferences, validations and to establish context.

:describes: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:increases with: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:decreases with: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:marks: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:classifies: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:discretizes: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

Trait and role inheritance
~~~~~~~~~~~~~~~~~~~~~~~~~~

:inherits: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:has role: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:confers: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

Semantic links
~~~~~~~~~~~~~~

:requires: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:exposes: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:defines: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc


Mereologic relationships
~~~~~~~~~~~~~~~~~~~~~~~~

:part of: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:constituent of: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

Causal relationships
~~~~~~~~~~~~~~~~~~~~

:affects: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:creates: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

For relationships
~~~~~~~~~~~~~~~~~

:links: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

:inverse of: 

	The simplest and least descriptive link between a quality or attribute and a quality. 

	Applies to:
		xxsss

	Targets:
		xxxccc

Restrictions
------------

.. literalinclude:: snippets/ses.kim
	:language: kim

Annotations recognized in concept definitions
---------------------------------------------