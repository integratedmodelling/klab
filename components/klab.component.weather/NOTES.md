# Copernicus Climate Data Service (CDS) pointers

* API docs are at https://cds.climate.copernicus.eu/modules/custom/cds_apikeys/app/apidocs.html, basically impossible to find
* Knowledge base at https://confluence.ecmwf.int/display/CUSF/REST+API+for+CDS+and+ADS
* apparently all requests use basic HTTP authentication (numeric user ID + API key), which doesn't seem to be true using swagger 
* They limit the request to "100 item" which seems to be the product of the number of variables, years, days, months requested. That seems to limit the request to 1 var, all days, 3 months of 1 year, which is a 250MB zip file containing one fucking NC file per day (probably each an "item"), which would be the easy thing to download with a URL and a name pattern instead of going through ridiculous hoops just to get the same stuff. In addition, the request returns a "task" which packages the zip and must be waited for and re-asked.
* They expect the world to use Python for one download at a time; the REST API is "unsupported" and can change at their pleasure to fit the Python client.
* What they call "the API" everywhere is actually the Python client.
* Each individual "download" must be preceded by a POST request that accepts the terms of use :(