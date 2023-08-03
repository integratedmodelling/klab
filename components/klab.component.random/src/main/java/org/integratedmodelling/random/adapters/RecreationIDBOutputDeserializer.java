package org.integratedmodelling.random.adapters;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecreationIDBOutputDeserializer {

    public String json;

    public RecreationIDBOutputDeserializer(String json){
        this.json = json;
    }

    public RecreationIDBOutputDeserializer(){
        this.json = "";
    }

    public void setJson(String json){
        this.json = json;
    }

    public RecreationAreas deserializeRecAreasData(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(this.json, RecreationAreas.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class RecreationAreas{

        // TODO: there's more potentially interesting information that is not gathered for the time being. Only gathering coordinates, id and name.
        public Collection<AreaData> areas;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public RecreationAreas(
                @JsonProperty("RECDATA") Collection<AreaData> areas){
            this.areas = areas;
        }

        public List<Map<String,Object>> getData(){
            return this.areas.stream().map(AreaData::exportAsMap).collect(Collectors.toList());
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AreaData{

            public double lon;
            public double lat;
            public String id;
            public String name;

            @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
            public AreaData(
                    @JsonProperty("RecAreaLatitude") double lat,
                    @JsonProperty("RecAreaLongitude") double lon,
                    @JsonProperty("RecAreaID") String id,
                    @JsonProperty("RecAreaName") String name){
                this.lat = lat;
                this.lon = lon;
                this.id = id;
                this.name = name;
            }

            @JsonProperty("lat")
            public double lat() {
                return lat;
            }
            @JsonProperty("lon")
            public double lon() {
                return lon;
            }
            @JsonProperty("id")
            public String id() {
                return id;
            }
            @JsonProperty("name")
            public String name() {
                return name;
            }

            public Map<String,Object> exportAsMap(){
                return Map.of("lat",lat,"lon",lon,"id",id,"name",name);
            }

        }

    }

}