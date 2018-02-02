# Ontology of Descriptions and Observations for Integrated Modelling (ODO-IM)


[![ODO-IM](https://img.shields.io/badge/ODO-IM-0.10.0-blue.svg?style=plastic)](http://github.com/integratedmodelling/odo)

- **Authors**: [Integrated Modelling Partnership](http://www.integratedmodelling.org); Ferdinando Villa, Ph.D.
- **License**: [CC-BY](http://creativecommons.org/licenses/by/3.0/)
- [**Bug reports and feature requests**](https://github.com/integratedmodelling/odo/issues)

The ODO-IM ontology is a core product developed and maintained by the [Integrated Modelling Partnership](http://www.integratedmodelling.org) (IMP). It specifies a view of the scientific process that can be implemented in software to support the IMP's goals of modular, distributed, semantically explicit and integrated scientific computing and modeling, according to the FAIR principles. ODO-IM provides five main core concepts:

- **Description**s describe the activities that produce scientific artifacts that describe a concept. Subclasses of Description can be used to describe scientific assertions, models and computational workflows (Definitions, see below).
- **Observation**s describe the scientific artifacts themselves, resulting from instantiating a Description within the context of an acknowledged root observation. Each concrete Description is restricted to produce a specific type of Observation.
- The **Observable** hierarchy describes the different concepts that serve as the object of a Description and the subject of the resulting Observation. Observables can be combined with **Predicate**s (such as attributes, roles etc) with constraints specified as restrictions on ODO-IM properties.
- **Definition**s are logical structures that can be used by an intelligent agent to carry out the Description of a specified Observable. Definitions specify observations either by acknowledgement (through an extensional statement) or by producing a computable dataflow (intensional statements listing zero or more Observables as dependencies). In ODO-IM applications, this class includes the main products of the activity of semantic modelling, i.e. what is commonly referred to as "data annotations" and "models". Properties and restrictions constrain specific classes of Definitions to specific classes of Observables.

What ODO-IM does not include:

- It does not provide semantics for units of measurement, like other observation ontologies do. The applications where ODO-IM is used do not need units as stated instances and do not use reasoning to validate units. To support applications, ODO-IM simply lists the SI base unit textually in annotation properties linked to all PhysicalProperty observables, using SI conventions. Applications can use a unit parser to translate these into data structures for validation and conversion.
- It does not provide any detail on space, time and any other topologies implied in the definition of observational scale beyond their statement as extentual observables (Extent), as their specific interpretation is left to worldviews derived from ODO-IM.
- It remains as agnostic as possible about the remaining details of the phenomenology underlying Observables, in an effort to preserve maximum orthogonality with an arbitrary upper ontology stated in a derived worldview. The characterization of Observables is intentionally shallow, as observables are meant to specialize types in an externally provided upper ontology to form the basis of a worldview. Such links are made in the root domain of the worldview. See the full documentation for minimum expressive requirements and caveats.

## Dependencies

ODO-IM only depends on the [PROV-O](https://www.w3.org/TR/prov-o/) provenance ontology, as Descriptions and Observations are, respectively, Activities (prov:Activity) and artifacts (prov:Entity).

## Rationale and Usage

The ODO-IM ontology is released under CC-BY license for any purpose. Its main rationale for existence is to serve as the semantic backbone of the *semantic meta-modeling* paradigm maintained by the IMP and operationalized through the k.LAB software stack. As part of this strategy, the k.IM language incarnates ODO-IM axioms in the syntax of a user-friendly language,  for which k.LAB provides intelligent editor support. The k.IM language allows its users to: 

- Define *worldviews* by specifying and composing Observables and Predicates, through efficient, flexible and readable statements, optimized for multi-domain, multi-scale definitions and with a built-in mechanisms for safe linking to existing controlled vocabularies. 
- Specify interoperable data annotations and computations (Definitions). The k.LAB software stack provides the infrastructure to publish such definitions, along with the resources they depend on, on networked repositories managed by specialized servers, and to peruse a federation of such servers to assemble context-specific computations that are run to generate Observations based on a user query for the corresponding Observable. 
	
Links to an upper ontology of choice are made in k.IM, in the root domain of each worldview. The k.LAB stack also includes a processor to rewrite k.IM namespaces into OWL ontologies if desired.

This repository is the official distribution of the ODO-IM ontology, whose latest master release is available as http://www.integratedmodelling.org/odo.owl. The docs/ directory contains source documentation that must be processed with Sphinx. Pre-built documentation is available at http://www.integratedmodelling.org/docs/odo.