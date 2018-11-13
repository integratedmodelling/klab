What's in a URN? The resource lifecycle
========================================

A *resource* is, in k.LAB, any informational artifact that can be used to produce an observation of an observable concept. A resource contains or produces *raw* information, i.e. information with no semantics associated. In this category we include data files, tables, local or networked databases, web services, `OGC`_ data services or `R`_ scripts - indeed, most of the resources that scientists and managers deal with every day.  A k.LAB *model* is used to annotate the URN, which allows it to turn resource information into finished, semantically consistent *observations*. 

 Thus, the concept of resource in k.LAB encompasses both "data" and computations. Resources have a unique URN, which can be *resolved* to a data structure containing all information associated with them:

- The name of the *adapter* handling the resource;
- The native scale (extent or resolution) for the space and time represented in the resource, if any;
- Any metadata, including provenance information, access restrictions and authorship information needed to make correct use of the resource;
- The full history of modification of the resource or its associated data, including LINKversion numbers for each version
- Information about what is needed in order to extract information from the resource



The resource lifecycle
----------------------

In k.LAB, resources can be *local* or *public*. The former reside within a k.LAB project, which must be physically available in a workspace in order for the resource to be used. The latter reside on the network and are available to all users that are authorized to use it. Local resources have a URN that starts with "local:". 

Other URNs
----------

Concept identifiers in k.IM (such as `infrastructure:Road`) are, themselves, informational resources - they specify "pure" knowledge, but a concrete concept can be seen as the result of observing its abstract (base) type. For example, .... 

In k.LAB, the general way of pointing to an informational resource is through an identifier that follows the syntax of a Uniform Resource Name (URN):

....

However, in order to maintain user friendliness and a smooth coding experience, we provide shorthand notations so that you will normally never have to write 'klab:urn:....' in a k.IM file. The `namespace:ConceptId` notation you have seen for concepts is one of those. It is internally expanded to

    urn:klab:<authoritative-node>:<worldview>:<namespace>:<conceptId>

For example, the concept `geography:Elevation` will internally be known as `urn:klab:im:im:geography:Elevation`. You can inquire about the contents of a URN by sending a GET call to any k.LAB node on a network using the /resolve endpoint (and you can always count on the IM partnership's nodes - try clicking on the URN above). If your user has access rights to that URN (and public URNs like the above do not need authentication), this will return a data structure that describes its contents, which you can later retrieve using the /get endpoint. 

To better understand the information in a URN descriptor, let's break down the results of resolving typical data URN such as XXX. As you can confirm by clicking on the link, this will produce the following JSON response:

.. code-block:: json
    :linenos:

    {
        "id": "aries.aesthetics.providers",
        "description": "",
        "arguments": [],
        "returnTypes": [
            "klab:SubjectInstantiator"
        ],
        "componentId": "aries.core",
        "distributed": false,
        "published": false,
        "extentParameters": []
    }

The important bits of information in this response are:

- **geometry** which...
- **options** ...

The k.LAB IDE resolves all URNs mentioned in your project, caching the results for speed, to check that their use within models is appropriate, and deactivates any models that reference an unresponsive or unauthorized URN.

Geometry vs. Scale
------------------