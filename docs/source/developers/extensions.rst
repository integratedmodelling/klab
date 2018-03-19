Developing k.LAB extensions
===========================

Components
----------

Storage providers
*****************

Runtime providers
*****************

Expressions used in k.IM
------------------------

Any code written in k.IM between square brackets is an expression that is evaluated and executed according to the semantics of the :ref:`model actions` it belongs to. The expressions use, by default, the native k.IM expression language (a derivative of Groovy), but the k.IM language allows using the _in_ keyword after an expression to specify an alternative language. Support for new languages can be added to k.LAB through a component that registers a new language processor.