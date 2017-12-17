What's in a URN?
================

An *informational artifact* is, in k.LAB, any resource that can be used to produce an observation of an observable concept. In k.LAB this encompasses both "data" or computations (we will also say "models" for the latter in the following). Different types of resources can produce observation of different classes of observables. Some examples:

	*. Zio
	*. Pongo

Concept identifiers in k.IM (such as `infrastructure:Road`) are, themselves, informational resources - they specify "pure" knowledge, but a concrete concept can be seen as the result of observing its abstract (base) type. For example, .... In general, though, an informational artifact contains *raw* information, i.e. information with no semantics, that needs a k.LAB *model* to specify to be turned into observations of some observable. In this category we can see count data files, tables, local or networked databases, web services, `OGC`_ data services or `R`_ scripts - indeed, most of the resources that scientists and managers deal with every day. 

In k.LAB, the general way of pointing to an informational resource is through an identifier that follows the syntax of a Uniform Resource Name (URN):

....

However, in order to maintain user friendliness and a smooth coding experience, we provide many shorthand notations so that you will normally never have to write 'klab:urn:....' in a k.IM file. The `namespace:ConceptId` notation you have seen for concepts is one of those. It is internally expanded to

	urn:klab:<authoritative-node>:<worldview>:<namespace>:<conceptId>

For example, the concept `geography:Elevation` will internally be known as `urn:klab:im:im:geography:Elevation`. You can inquire about a URN by sending a GET call to any k.LAB node on a network using the /resolve endpoint (and you can always count on the IM partnership's nodes - try clicking on the URN above).