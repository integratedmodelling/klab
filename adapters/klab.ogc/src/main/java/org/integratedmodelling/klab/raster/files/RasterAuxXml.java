package org.integratedmodelling.klab.raster.files;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PAMDataset")
public class RasterAuxXml {

    @XmlElement(name = "PAMRasterBand")
    public PAMRasterBand rasterBand;

}

class PAMRasterBand {
    @XmlAttribute
    public int band;

    @XmlElement(name = "GDALRasterAttributeTable")
    public GDALRasterAttributeTable attributeTable;
}

class GDALRasterAttributeTable {

    @XmlElement(name = "FieldDefn")
    public List<FieldDefn> fieldDefnList = new ArrayList<>();
    
    @XmlElement(name = "Row")
    public List<Row> rowList = new ArrayList<>();

}

class FieldDefn {
    @XmlAttribute
    public int index;

    @XmlElement(name = "Name")
    public String name;

    @XmlElement(name = "Type")
    public int type;

    @XmlElement(name = "Usage")
    public int usage;

}

class Row {
    @XmlAttribute
    public int index;
    
    @XmlElement(name = "F")
    public List<String> fList = new ArrayList<>();
}