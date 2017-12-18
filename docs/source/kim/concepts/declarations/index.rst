.. klab documentation master file, created by
   sphinx-quickstart on Sun Nov 12 13:45:34 2017.
   
Using concepts: declarations and authorities
============================================

If the :ref:`worldview <worldview>` specifies the *words* we can use to categorize our observations, we use declarations to *compose* the meanings we need, as if they were *sentences* built from those words, possibly using other keywords (such as *of*, *with*, *by* etc.) to specify their roles. For example, we may be combining the attribute_ `im:Potential` with the semantic operator :keyword:`presence of` applied to the domain concepts `infrastructure:House`, and set `infrastructure:City` as the context_ for the presence like so:

.. code-block:: kim
    
    model im:ariesteam:im.data:global.housingpresence
		as im:Potential presence of infrastructure:House within infrastructure:City
    ;

The *declaration* after :keyword:`as` can be seen a "molecule" of meaning, built out of the semantic "atoms" provided by the worldview. k.LAB is founded on the ability of creating complex meaning *at the moment of usage*: the complex concept of 'potential presence of houses within a city' does not need to be explicitly declared using a `concept statement <>`_ before it is used in a model, as if it would if you were using straight OWL. This way, the worldview only needs to contain the "atoms" and does not need to grow unnecessarily large: it's akin to a *periodic table* of meaning, complex but still small enough to learn. We have built syntax to express many variations of concepts, optimizing it for the needs of scientific investigation. This *declaration syntax* is the topic of this section.

.. toctree::
   :maxdepth: 2
   :caption: Contents:

	Declaring concepts <declarations>
	Observables <observables>
	Authorities: bridging to vocabularies <authorities>
	Recipes <rules>
