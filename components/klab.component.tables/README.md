# Table adapter - preliminary documentation

The table adapter is meant to annotate data that come from tabular data sources. 
As the latter can represent correspondences that are completely scalar from a geometry 
standpoint, the adapter produces a generic value that may be a scalar POD, a list, 
a map or a table, potentially distributed along a geometry using either internal 
structure or joins with a different resource that provides the geometry distribution. 
For this reason, the table adapter has a relatively complex, but consistent parameterization 
to handle selection, filtering and joining of values from the original sources.

Table data sources currently supported or in development are SDMX, SPARQL, SQL/JDBC, 
XLS/CSV and NetCDF.

Data type could be any scalar type (in case the table is used to match as a regular datasource 
fitting the geometry) OR TABLE, MAP, LIST, potentially replicated according to geometry. 
Even a single complex correspondence table with multiple columns and keys would be a 
scalar; a specific geometry may create a complex table per state.

As a special case, a table resource can be used to specify a single scalar value, although 
using the value literally is obviously the best choice in any model.

## URN parameters

When the original data have multiple columns, the parameters key/keys and value/values 
can be used to select one or more of them and to specify which ones should be indexable 
for fast lookup. They can be followed by one or more column names; if columns have no 
name, progressive numbers starting at 1 can be used.

Each of the column names is a declared output in the resource.

## Resource parameters



## Joins


