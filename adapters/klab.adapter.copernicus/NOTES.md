# Copernicus data services

https://www.copernicus.eu/en/accessing-data-where-and-how/conventional-data-access-hubs

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
* *At least the first individual "download" must be preceded by a manual login or POST request to accept the terms of use, or nothing will work :(*

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

The NetCDFs can be combined into a multi-temporal NcML file using 
---
<?xml version="1.0" encoding="UTF-8"?>
<netcdf xmlns="http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2">
  <aggregation dimName="time" type="joinExisting">
    <scan location="C:/Users/Ferd/Dropbox/Data/AgERA5/dataset-sis-agrometeorological-indicators-5a0fb25d-193a-416b-82e1-69200c300954/" suffix=".nc" subdirs="false"/>
  </aggregation>
</netcdf>

can also name individual netcdf inside <aggregation/> using <netcdf location="file:url"/> nodes

---
which is ingested by GS and enables the EO extensions as long as WCS has the EO enabled, the layer has the time dimension configured, and the 
layer uses EO extensions. With those, the request can contain the subset needed and the bands correspond to the timepoints.

In that case, describeCoverage will produce granules for each time instant that can be used in subsetting or as specific layer IDs:
---
<?xml version="1.0" encoding="UTF-8"?><wcs:CoverageDescriptions xmlns:wcs="http://www.opengis.net/wcs/2.0" xmlns:ows="http://www.opengis.net/ows/2.0" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:gmlcov="http://www.opengis.net/gmlcov/1.0" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:swe="http://www.opengis.net/swe/2.0" xmlns:wcsgs="http://www.geoserver.org/wcsgs/2.0" xmlns:wcseo="http://www.opengis.net/wcseo/1.0" xmlns:eop="http://www.opengis.net/eop/2.0" xmlns:om="http://www.opengis.net/om/2.0" xsi:schemaLocation=" http://www.opengis.net/wcs/2.0 http://schemas.opengis.net/wcs/2.0/wcsDescribeCoverage.xsd http://www.geoserver.org/wcsgs/2.0 http://127.0.0.1:8080/geoserver/schemas/wcs/2.0/wcsgs.xsd http://www.opengis.net/wcseo/1.0 http://127.0.0.1:8080/geoserver/schemas/wcseo/1.0/wcsEOCoverage.xsd">
  <wcs:CoverageDescription gml:id="Precipitation_Flux_Porcodio">
    <gml:description>Generated from NetCDF</gml:description>
    <gml:name>Precipitation_Flux_Porcodio</gml:name>
    <gml:boundedBy>
      <gml:EnvelopeWithTimePeriod srsName="http://www.opengis.net/def/crs/EPSG/0/4326" axisLabels="Lat Long time" uomLabels="Deg Deg s" srsDimension="2">
        <gml:lowerCorner>-90.04999999998977 -180.04999999999998</gml:lowerCorner>
        <gml:upperCorner>90.05 179.9499999999795</gml:upperCorner>
        <gml:beginPosition>2010-01-01T00:00:00.000Z</gml:beginPosition>
        <gml:endPosition>2010-03-31T00:00:00.000Z</gml:endPosition>
      </gml:EnvelopeWithTimePeriod>
    </gml:boundedBy>
    <wcs:CoverageId>Precipitation_Flux_Porcodio</wcs:CoverageId>
    <gml:coverageFunction>
      <gml:GridFunction>
        <gml:sequenceRule axisOrder="+2 +1">Linear</gml:sequenceRule>
        <gml:startPoint>0 0</gml:startPoint>
      </gml:GridFunction>
    </gml:coverageFunction>
    <gmlcov:metadata>
      <gmlcov:Extension>
        <ows:Keywords>
          <ows:Keyword>Precipitation_Flux</ows:Keyword>
          <ows:Keyword>WCS</ows:Keyword>
          <ows:Keyword>NetCDF</ows:Keyword>
        </ows:Keywords>
        <wcsgs:TimeDomain default="2010-03-31T00:00:00.000Z">
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_0">
            <gml:timePosition>2010-01-01T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_1">
            <gml:timePosition>2010-01-02T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_2">
            <gml:timePosition>2010-01-03T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_3">
            <gml:timePosition>2010-01-04T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_4">
            <gml:timePosition>2010-01-05T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_5">
            <gml:timePosition>2010-01-06T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_6">
            <gml:timePosition>2010-01-07T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_7">
            <gml:timePosition>2010-01-08T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_8">
            <gml:timePosition>2010-01-09T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_9">
            <gml:timePosition>2010-01-10T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_10">
            <gml:timePosition>2010-01-11T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_11">
            <gml:timePosition>2010-01-12T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_12">
            <gml:timePosition>2010-01-13T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_13">
            <gml:timePosition>2010-01-14T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_14">
            <gml:timePosition>2010-01-15T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_15">
            <gml:timePosition>2010-01-16T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_16">
            <gml:timePosition>2010-01-17T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_17">
            <gml:timePosition>2010-01-18T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_18">
            <gml:timePosition>2010-01-19T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_19">
            <gml:timePosition>2010-01-20T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_20">
            <gml:timePosition>2010-01-21T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_21">
            <gml:timePosition>2010-01-22T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_22">
            <gml:timePosition>2010-01-23T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_23">
            <gml:timePosition>2010-01-24T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_24">
            <gml:timePosition>2010-01-25T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_25">
            <gml:timePosition>2010-01-26T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_26">
            <gml:timePosition>2010-01-27T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_27">
            <gml:timePosition>2010-01-28T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_28">
            <gml:timePosition>2010-01-29T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_29">
            <gml:timePosition>2010-01-30T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_30">
            <gml:timePosition>2010-01-31T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_31">
            <gml:timePosition>2010-02-01T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_32">
            <gml:timePosition>2010-02-02T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_33">
            <gml:timePosition>2010-02-03T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_34">
            <gml:timePosition>2010-02-04T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_35">
            <gml:timePosition>2010-02-05T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_36">
            <gml:timePosition>2010-02-06T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_37">
            <gml:timePosition>2010-02-07T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_38">
            <gml:timePosition>2010-02-08T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_39">
            <gml:timePosition>2010-02-09T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_40">
            <gml:timePosition>2010-02-10T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_41">
            <gml:timePosition>2010-02-11T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_42">
            <gml:timePosition>2010-02-12T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_43">
            <gml:timePosition>2010-02-13T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_44">
            <gml:timePosition>2010-02-14T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_45">
            <gml:timePosition>2010-02-15T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_46">
            <gml:timePosition>2010-02-16T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_47">
            <gml:timePosition>2010-02-17T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_48">
            <gml:timePosition>2010-02-18T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_49">
            <gml:timePosition>2010-02-19T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_50">
            <gml:timePosition>2010-02-20T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_51">
            <gml:timePosition>2010-02-21T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_52">
            <gml:timePosition>2010-02-22T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_53">
            <gml:timePosition>2010-02-23T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_54">
            <gml:timePosition>2010-02-24T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_55">
            <gml:timePosition>2010-02-25T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_56">
            <gml:timePosition>2010-02-26T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_57">
            <gml:timePosition>2010-02-27T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_58">
            <gml:timePosition>2010-02-28T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_59">
            <gml:timePosition>2010-03-01T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_60">
            <gml:timePosition>2010-03-02T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_61">
            <gml:timePosition>2010-03-03T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_62">
            <gml:timePosition>2010-03-04T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_63">
            <gml:timePosition>2010-03-05T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_64">
            <gml:timePosition>2010-03-06T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_65">
            <gml:timePosition>2010-03-07T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_66">
            <gml:timePosition>2010-03-08T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_67">
            <gml:timePosition>2010-03-09T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_68">
            <gml:timePosition>2010-03-10T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_69">
            <gml:timePosition>2010-03-11T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_70">
            <gml:timePosition>2010-03-12T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_71">
            <gml:timePosition>2010-03-13T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_72">
            <gml:timePosition>2010-03-14T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_73">
            <gml:timePosition>2010-03-15T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_74">
            <gml:timePosition>2010-03-16T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_75">
            <gml:timePosition>2010-03-17T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_76">
            <gml:timePosition>2010-03-18T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_77">
            <gml:timePosition>2010-03-19T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_78">
            <gml:timePosition>2010-03-20T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_79">
            <gml:timePosition>2010-03-21T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_80">
            <gml:timePosition>2010-03-22T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_81">
            <gml:timePosition>2010-03-23T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_82">
            <gml:timePosition>2010-03-24T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_83">
            <gml:timePosition>2010-03-25T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_84">
            <gml:timePosition>2010-03-26T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_85">
            <gml:timePosition>2010-03-27T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_86">
            <gml:timePosition>2010-03-28T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_87">
            <gml:timePosition>2010-03-29T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_88">
            <gml:timePosition>2010-03-30T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
          <gml:TimeInstant gml:id="Precipitation_Flux_Porcodio_td_89">
            <gml:timePosition>2010-03-31T00:00:00.000Z</gml:timePosition>
          </gml:TimeInstant>
        </wcsgs:TimeDomain>
        <wcseo:EOMetadata>
          <eop:EarthObservation gml:id="cite__Precipitation_Flux_Porcodio_metadata">
            <om:phenomenonTime>
              <gml:TimePeriod gml:id="cite__Precipitation_Flux_Porcodio_tp">
                <gml:beginPosition>2010-01-01T00:00:00.000Z</gml:beginPosition>
                <gml:endPosition>2010-03-31T00:00:00.000Z</gml:endPosition>
              </gml:TimePeriod>
            </om:phenomenonTime>
            <om:resultTime>
              <gml:TimeInstant gml:id="cite__Precipitation_Flux_Porcodio_rt">
                <gml:timePosition>2010-03-31T00:00:00.000Z</gml:timePosition>
              </gml:TimeInstant>
            </om:resultTime>
            <om:procedure/>
            <om:observedProperty/>
            <om:featureOfInterest>
              <eop:Footprint gml:id="cite__Precipitation_Flux_Porcodio_fp">
                <eop:multiExtentOf>
                  <gml:MultiSurface gml:id="cite__Precipitation_Flux_Porcodio_ms" srsName="http://www.opengis.net/def/crs/EPSG/0/4326">
                    <gml:surfaceMembers>
                      <gml:Polygon gml:id="cite__Precipitation_Flux_Porcodio_msp">
                        <gml:exterior>
                          <gml:LinearRing>
                            <gml:posList>-90.04999999998977 -180.04999999999998 -90.04999999998977 179.9499999999795 90.05 179.9499999999795 90.05 -180.04999999999998 -90.04999999998977 -180.04999999999998</gml:posList>
                          </gml:LinearRing>
                        </gml:exterior>
                      </gml:Polygon>
                    </gml:surfaceMembers>
                  </gml:MultiSurface>
                </eop:multiExtentOf>
                <eop:centerOf>
                  <gml:Point gml:id="cite__Precipitation_Flux_Porcodio_co" srsName="http://www.opengis.net/def/crs/EPSG/0/4326">
                    <gml:pos>5.115907697472721E-12 -0.050000000010243184</gml:pos>
                  </gml:Point>
                </eop:centerOf>
              </eop:Footprint>
            </om:featureOfInterest>
            <eop:metaDataProperty>
              <eop:EarthObservationMetaData>
                <eop:identifier>cite__Precipitation_Flux_Porcodio</eop:identifier>
                <eop:acquisitionType>NOMINAL</eop:acquisitionType>
                <eop:status>ARCHIVED</eop:status>
              </eop:EarthObservationMetaData>
            </eop:metaDataProperty>
          </eop:EarthObservation>
        </wcseo:EOMetadata>
      </gmlcov:Extension>
    </gmlcov:metadata>
    <gml:domainSet>
      <gml:RectifiedGrid gml:id="grid00__Precipitation_Flux_Porcodio" dimension="2">
        <gml:limits>
          <gml:GridEnvelope>
            <gml:low>0 0</gml:low>
            <gml:high>3599 1800</gml:high>
          </gml:GridEnvelope>
        </gml:limits>
        <gml:axisLabels>i j</gml:axisLabels>
        <gml:origin>
          <gml:Point gml:id="p00_Precipitation_Flux_Porcodio" srsName="http://www.opengis.net/def/crs/EPSG/0/4326">
            <gml:pos>90.0 -180.0</gml:pos>
          </gml:Point>
        </gml:origin>
        <gml:offsetVector srsName="http://www.opengis.net/def/crs/EPSG/0/4326">0.0 0.0999999999999943</gml:offsetVector>
        <gml:offsetVector srsName="http://www.opengis.net/def/crs/EPSG/0/4326">-0.09999999999999432 0.0</gml:offsetVector>
      </gml:RectifiedGrid>
    </gml:domainSet>
    <gmlcov:rangeType>
      <swe:DataRecord>
        <swe:field name="Total precipitation (00-00LT)">
          <swe:Quantity>
            <swe:description>Total precipitation (00-00LT)</swe:description>
            <swe:nilValues>
              <swe:NilValues>
                <swe:nilValue reason="http://www.opengis.net/def/nil/OGC/0/unknown">-9999.0</swe:nilValue>
              </swe:NilValues>
            </swe:nilValues>
            <swe:uom code="mm/day"/>
            <swe:constraint>
              <swe:AllowedValues>
                <swe:interval>-3.4028235E38 3.4028235E38</swe:interval>
              </swe:AllowedValues>
            </swe:constraint>
          </swe:Quantity>
        </swe:field>
      </swe:DataRecord>
    </gmlcov:rangeType>
    <wcs:ServiceParameters>
      <wcs:CoverageSubtype>RectifiedGridCoverage</wcs:CoverageSubtype>
      <wcs:nativeFormat>image/tiff</wcs:nativeFormat>
    </wcs:ServiceParameters>
  </wcs:CoverageDescription>
</wcs:CoverageDescriptions>
---
