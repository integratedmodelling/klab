# Copernicus Climate Data Service (CDS) pointers

* Description page at https://cds.climate.copernicus.eu/cdsapp#!/dataset/sis-agrometeorological-indicators
* API docs are at https://cds.climate.copernicus.eu/modules/custom/cds_apikeys/app/apidocs.html, basically impossible to find
* API endpoint seems to be https://cds.climate.copernicus.eu/api/v2 
* Dataset key is `sis-agrometeorological-indicators`. Sample request is a POST to the toolbox API endpoint (`resources/dataset/{resourceName}`) :

```
 {
	"variable": "2m_temperature",
	"statistic": "24_hour_maximum",
	"year": "1990",
	"month": [
        "01", "02", "03"
    ],
    "day": [
        "01", "02", "03",
        "04", "05", "06",
        "07", "08", "09",
        "10", "11", "12",
        "13", "14", "15",
        "16", "17", "18",
        "19", "20", "21",
        "22", "23", "24",
        "25", "26", "27",
        "28", "29", "30",
    	"31"
    ]
 }
```

POST with Accept=application/json to https://cds.climate.copernicus.eu/api/v2/resources/datasets/sis-agrometeorological-indicators; returns 202 Accepted even 
if wrong parameters (sent "dataset" instead of "datasets", returns this):

---
{
    "state": "queued",
    "request_id": "c6520f3e-bb78-4d06-b31e-180ee1fa6015",
    "specific_metadata_json": {
        "top_request_origin": "api"
    }
}

which sends back with /api/v2/tasks/c6520f3e-bb78-4d06-b31e-180ee1fa6015:

{
    "message": "Bad resource type specified (valid: datasets,tools,applications)",
    "url": "http://copernicus-climate.eu/exc/bad-resource-type",
    "reason": "Bad resource type specified",
    "permanent": true,
    "who": "client"
}

so: error if "state" isn't there

---
Resend correctly with "datasets": returns 200 (not 202) and

--- 
{
    "state": "completed",
    "request_id": "16b4c644-2189-4549-abc6-93b75b417d83",
    "location": "https://download-0012.copernicus-climate.eu/cache-compute-0012/cache/data9/dataset-sis-agrometeorological-indicators-c6520f3e-bb78-4d06-b31e-180ee1fa6015.zip",
    "content_length": 514209897,
    "content_type": "application/zip",
    "result_provided_by": "c6520f3e-bb78-4d06-b31e-180ee1fa6015",
    "specific_metadata_json": {
        "top_request_origin": "api"
    }
}

--- 

if not 'completed', must poll until completed. The zip URL works w/o authentication.



* Knowledge base at https://confluence.ecmwf.int/display/CUSF/REST+API+for+CDS+and+ADS
* apparently all requests use basic HTTP authentication (numeric user ID + API key), which doesn't seem to be true using swagger 
* They limit the request to "100 item" which seems to be the product of the number of variables, years, days, months requested. That seems to limit the request to 1 var, all days, 3 months of 1 year, which is a 250MB zip file containing one fucking NC file per day (probably each an "item"), which would be the easy thing to download with a URL and a name pattern instead of going through ridiculous hoops just to get the same stuff. In addition, the request returns a "task" which packages the zip and must be waited for and re-asked.
* They expect the world to use Python for one download at a time; the REST API is "unsupported" and can change at their pleasure to fit the Python client.
* What they call "the API" everywhere is actually the Python client.
* Each individual "download" must be preceded by a POST request that accepts the terms of use :(

From the API description page:

```
Some basic concepts:

A 'resource' is a CDS catalogue entry describing a dataset or tool. When you ask for data you ask for it from a particular resource
A 'task' is a request for data. Tasks run asynchronously - you must submit one, wait for it to complete, then fetch the data from the result URL. Result URLs have a limited life.
A typical flow:

PUT a request using /api/v2/tasks/resources/dataset/my-dataset/clientid-my-random-id, or POST one to /api/v2/resources/dataset/my-dataset. Using the PUT endpoint allows you to retry without creating duplicates.
Using the request ID in the response, poll /api/v2/tasks/my-task-id (or /api/v2/tasks/clientid-my-random-id).
For as long as the state is 'queued' or 'running' continue to poll. Your result is ready when the state changes to 'completed'.
```