.. _primer:

Semantic modeling with k.LAB
============================

k.LAB aims to address the task of "integrated modeling", which reconciles strong semantics with modeling practice, helping achieve advantages (such as modularity, interoperability, reusability, and integration of multiple paradigms and scales) that have remained largely unrealized to this day. To achieve this goal, k.LAB keeps the logical representation of the modeled world distinct from the algorithmic knowledge that allows it to be simulated, and uses artificial intelligence to assemble computations that produce *observations* of such knowledge.

The idea of *observation* is the unifying theme to define a general way to model physical objects and phenomena (Fox and Hendler 2009). A model is a *strategy to produce observations* of a concept that comes from a shared :ref:`worldview <worldview>`. EXAMPLES and USE CASES RIGHT HERE: a researcher may write this and publish it on his server. A user may just ask to observe the concept in a context using one of several interfaces (Java, Web, IDE) - example. The same applies to any other :ref:`observables`: for example a process (ideally: show a R model)... Show picture of water model in IDE. The user does not even know what a model is - all they share is a pointer to the worldview.



In order for models to be compatible and be capable of being used as component of the same computation, it is sufficient that the abstract knowledge they represent is compatible. Modern artificial intelligence provides algorithms and tools to automatically validate the consistency of an abstract knowledge base. This way, the approach enables the integration of many modeling paradigms that are often applied separately, for example spatially-explicit to process- and agent-based models, or probabilistic and deterministic models. This conceptualization builds a natural path to reach goals in modeling that have frequently been discussed, but not demonstrated to their full potential, including modular modeling, multiple-paradigm modeling, multiple-scale modeling and structurally variable modeling.

The logical representation is modeled using concepts and relationships that comprise k.LAB's abstract knowledge base, built out of ontologies (more on this later). In the abstract knowledge base, concepts such as "watershed," "elevation," or "temperature" are defined, along with information on how they may relate to each other. No attempt is made to indicate how their models may be computed.

The algorithmic knowledge base is where you can provide models so that simulated "observations" can be computed. At the user's request, the artificial intelligence in k.LAB will choose algorithms from this knowledge base and build an integrated algorithm by assembling them, driven by the abstract semantics. The result of calibrating and running the integrated model is the production of observations of the concepts contained in the abstract knowledge base.

A model in k.LAB represents a strategy to observe a concept. Models can consist of entire complex simulations, simpler functions, look-up tables or classifications, datasets, or even single numbers; from the k.LAB point of view, all these just represent different ways to observe a concept. In striking difference from almost all mainstream modeling approaches, numbers, data or equations have absolutely no meaning by themselves, even with descriptive names or associated with formal metadata: even the simplest number can only be used in k.LAB if it has a concept associated with it. k.LAB forces you to use concepts so that "metadata" in your models only document auxiliary information as they should; the conceptual part of the knowledge base serves automatically as the documentation of the models, while at the same time providing a layer of "meaning" on which collaboration and model integration are based.


LOOK OUT FOR:

	Differences with other frameworks we may be used to
		Models are global by default and semantics is not scoped! Risks 
		of using same concepts and "playing around" with models.
		 