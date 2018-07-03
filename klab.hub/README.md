# k.LAB Hub server

This package contains the implementation of the k.LAB authentication hub, which authenticates 
engines and coordinates nodes in a hub. Since 0.10.0, the node and hub functionalities 
are provided by separate servers. Both engines and nodes authenticate through a hub, 
and the hub collects the authorized nodes (including those of connected hubs) when 
returning the network to each authenticated engine.

This initial implementation, meant to support demos and courses while development 
takes place, has the following limitations:
- proxies engine authentication (through certificate file) through the old collaboration 
  server
- uses no certificate for its own authentication;
- offers no user management facilities
- does not connect to other hubs.

Users still obtain certificates and manage their identity on the collaboration server; 
the engine automatically upgrades the certificates to the 0.10.0 format. 

The network returned by this initial implementation is simply configured in through 
a JSON file, and can be filtered based on the groups each user belongs to.

The final implementation will handle one or more configurable user directories and 
use JWT authentication to collect the allowed nodes for a user and propagate the 
authentication to them.