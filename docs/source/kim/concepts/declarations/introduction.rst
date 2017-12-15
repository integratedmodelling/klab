Referring to concepts from the worldview
========================================

If worldviews specify the *words* we can use to categorize our observations, we use declarations to *compose* the meanings we need as *sentences* built from those words. For example, we may be combining `im:Potential` with the semantic operator `presence of` applied to the domain concepts `infrastructure:House` and `infrastructure:City`:

.. code-block:: kim
    
    model im:ariesteam:im.data:global.housingpresence
		as im:Potential presence of infrastructure:House within infrastructure:City
    ;

This *declaration* is a semantic "molecule" built out of the semantic "atoms" provided by the worldview. k.LAB is founded on the ability of creating complex meaning *at the moment of usage*: the complex concept of 'potential presence of houses within a city' does not need to be declared (as a `concept statement <>`_ before it is used in a model. This way, the worldview does not need to grow unnecessarily large. We have built syntax to express many variations of concepts, optimizing it for the needs of scientific investigation. This *declaration syntax* is the topic of this section.