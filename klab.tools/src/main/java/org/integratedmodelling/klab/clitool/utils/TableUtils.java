package org.integratedmodelling.klab.clitool.utils;

import org.integratedmodelling.klab.utils.StringUtils;

public class TableUtils {

    /**
     * Nicely format a string containing <strong>correct</strong> k.IM table data.
     * 
     * @param table
     * @param lead
     * @param spacing
     * @return the formatted table
     */
    public static String formatTable(String table, int lead, int spacing) {

        String ret = null;
        String[] rows = table.split("\\,");
        String[] items = rows[0].split("\\|");
        int[] maxSizes = new int[items.length];

        for (String row : rows) {
            String[] its = row.split("\\|");
            int i = 0;
            for (String it : its) {
                if (it.trim().length() > maxSizes[i]) {
                    maxSizes[i] = it.trim().length();
                }
                i++;
            }
        }

        for (int row = 0; row < rows.length; row++) {
            ret += StringUtils.spaces(lead);
            String[] its = rows[row].split("\\|");
            for (int i = 0; i < its.length; i++) {
                String item = its[i].trim();
                int missing = maxSizes[i] - item.length() + spacing;
                ret += StringUtils.spaces(i == 0 ? lead : spacing) + item + StringUtils.spaces(missing)
                        + (i < its.length - 1 ? "|" : (row < rows.length - 1 ? ",\n" : "\n"));
            }
        }

        return ret;

    }

    static public void main(String[] args) {
    	System.out.println(formatTable("	landcover:ArableLand                 |						*|	hydrology:SoilGroupA|	67,\r\n" + 
    			"	landcover:ArableLand                 |						*|	hydrology:SoilGroupB|	78,\r\n" + 
    			"	landcover:ArableLand|						*|	hydrology:SoilGroupC|	85,\r\n" + 
    			"	landcover:ArableLand|						*|	hydrology:SoilGroupD|	89,\r\n" + 
    			"	landcover:PermanentCropland|				*|	hydrology:SoilGroupA|	67,\r\n" + 
    			"	landcover:PermanentCropland|				*|	hydrology:SoilGroupB|	78,\r\n" + 
    			"	landcover:PermanentCropland|				*|	hydrology:SoilGroupC|	85,\r\n" + 
    			"	landcover:PermanentCropland|				*|	hydrology:SoilGroupD|	89,\r\n" + 
    			"	landcover:HeterogeneousAgriculturalLand|	*|	hydrology:SoilGroupA|	52,\r\n" + 
    			"	landcover:HeterogeneousAgriculturalLand|	*|	hydrology:SoilGroupB|	69,\r\n" + 
    			"	landcover:HeterogeneousAgriculturalLand|	*|	hydrology:SoilGroupC|	79,\r\n" + 
    			"	landcover:HeterogeneousAgriculturalLand|	*|	hydrology:SoilGroupD|	84,\r\n" + 
    			"	landcover:EvergreenBroadleafForest|			*|	hydrology:SoilGroupA|	30,\r\n" + 
    			"	landcover:EvergreenBroadleafForest|			*|	hydrology:SoilGroupB|	58,\r\n" + 
    			"	landcover:EvergreenBroadleafForest|			*|	hydrology:SoilGroupC|	71,\r\n" + 
    			"	landcover:EvergreenBroadleafForest|			*|	hydrology:SoilGroupD|	77,\r\n" + 
    			"	landcover:DeciduousBroadleafForest|			*|	hydrology:SoilGroupA|	42,\r\n" + 
    			"	landcover:DeciduousBroadleafForest|			*|	hydrology:SoilGroupB|	66,\r\n" + 
    			"	landcover:DeciduousBroadleafForest|			*|	hydrology:SoilGroupC|	79,\r\n" + 
    			"	landcover:DeciduousBroadleafForest|			*|	hydrology:SoilGroupD|	52,\r\n" + 
    			"	landcover:EvergreenConiferousForest|		*|	hydrology:SoilGroupA|	34,\r\n" + 
    			"	landcover:EvergreenConiferousForest|		*|	hydrology:SoilGroupB|	60,\r\n" + 
    			"	landcover:EvergreenConiferousForest|		*|	hydrology:SoilGroupC|	73,\r\n" + 
    			"	landcover:EvergreenConiferousForest|		*|	hydrology:SoilGroupD|	79,\r\n" + 
    			"	landcover:DeciduousConiferousForest|		*|	hydrology:SoilGroupA|	40,\r\n" + 
    			"	landcover:DeciduousConiferousForest|		*|	hydrology:SoilGroupB|	64,\r\n" + 
    			"	landcover:DeciduousConiferousForest|		*|	hydrology:SoilGroupC|	77,\r\n" + 
    			"	landcover:DeciduousConiferousForest|		*|	hydrology:SoilGroupD|	83,\r\n" + 
    			"	landcover:MixedForest|						*|	hydrology:SoilGroupA|	38,\r\n" + 
    			"	landcover:MixedForest|						*|	hydrology:SoilGroupB|	62,\r\n" + 
    			"	landcover:MixedForest|						*|	hydrology:SoilGroupC|	75,\r\n" + 
    			"	landcover:MixedForest|						*|	hydrology:SoilGroupD|	81,\r\n" + 
    			"	landcover:ClosedSavanna|					*|	hydrology:SoilGroupA|	61,\r\n" + 
    			"	landcover:ClosedSavanna|					*|	hydrology:SoilGroupB|	71,\r\n" + 
    			"	landcover:ClosedSavanna|					*|	hydrology:SoilGroupC|	81,\r\n" + 
    			"	landcover:ClosedSavanna|					*|	hydrology:SoilGroupD|	89,\r\n" + 
    			"	landcover:OpenSavanna|						*|	hydrology:SoilGroupA|	82,\r\n" + 
    			"	landcover:OpenSavanna|						*|	hydrology:SoilGroupB|	80,\r\n" + 
    			"	landcover:OpenSavanna|						*|	hydrology:SoilGroupC|	87,\r\n" + 
    			"	landcover:OpenSavanna|						*|	hydrology:SoilGroupD|	93,\r\n" + 
    			"	landcover:MoorAndHeathland|					*|	hydrology:SoilGroupA|	45,\r\n" + 
    			"	landcover:MoorAndHeathland|					*|	hydrology:SoilGroupB|	65,\r\n" + 
    			"	landcover:MoorAndHeathland|					*|	hydrology:SoilGroupC|	75,\r\n" + 
    			"	landcover:MoorAndHeathland|					*|	hydrology:SoilGroupD|	80,\r\n" + 
    			"	landcover:NaturalGrassland|					*|	hydrology:SoilGroupA|	49,\r\n" + 
    			"	landcover:NaturalGrassland|					*|	hydrology:SoilGroupB|	69,\r\n" + 
    			"	landcover:NaturalGrassland|					*|	hydrology:SoilGroupC|	79,\r\n" + 
    			"	landcover:NaturalGrassland|					*|	hydrology:SoilGroupD|	84,\r\n" + 
    			"	landcover:Wetland|							*|	hydrology:SoilGroupA|	30,\r\n" + 
    			"	landcover:Wetland|							*|	hydrology:SoilGroupB|	58,\r\n" + 
    			"	landcover:Wetland|							*|	hydrology:SoilGroupC|	71,\r\n" + 
    			"	landcover:Wetland|							*|	hydrology:SoilGroupD|	78,\r\n" + 
    			"	landcover:ArtificialSurface|				*|	hydrology:SoilGroupA|	80,\r\n" + 
    			"	landcover:ArtificialSurface|				*|	hydrology:SoilGroupB|	85,\r\n" + 
    			"	landcover:ArtificialSurface|				*|	hydrology:SoilGroupC|	90,\r\n" + 
    			"	landcover:ArtificialSurface|				*|	hydrology:SoilGroupD|	95,\r\n" + 
    			"	landcover:WaterBody|						*|	#|						98,\r\n" + 
    			"	landcover:GlacierAndPerpetualSnow|			*|	#|						40,\r\n" + 
    			"	landcover:BareArea|							*|	hydrology:SoilGroupA|	72,\r\n" + 
    			"	landcover:BareArea|							*|	hydrology:SoilGroupB|	82,\r\n" + 
    			"	landcover:BareArea|							*|	hydrology:SoilGroupC|	83,\r\n" + 
    			"	landcover:BareArea|							*|	hydrology:SoilGroupD|	87,\r\n" + 
    			"	landcover:LichenMoss|						*|	hydrology:SoilGroupA|	72,\r\n" + 
    			"	landcover:LichenMoss|						*|	hydrology:SoilGroupB|	82,\r\n" + 
    			"	landcover:LichenMoss|						*|	hydrology:SoilGroupC|	83,\r\n" + 
    			"	landcover:LichenMoss|						*|	hydrology:SoilGroupD|	87,\r\n" + 
    			"	landcover:SparseVegetation|					*|	hydrology:SoilGroupA|	72,\r\n" + 
    			"	landcover:SparseVegetation|					*|	hydrology:SoilGroupB|	82,\r\n" + 
    			"	landcover:SparseVegetation|					*|	hydrology:SoilGroupC|	83,\r\n" + 
    			"	landcover:SparseVegetation|					*|	hydrology:SoilGroupD|	87", 3, 3));
    }
    
}
