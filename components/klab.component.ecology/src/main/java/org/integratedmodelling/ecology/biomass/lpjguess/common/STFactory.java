package org.integratedmodelling.ecology.biomass.lpjguess.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.integratedmodelling.ecology.biomass.lpjguess.CropRotation;
import org.integratedmodelling.ecology.biomass.lpjguess.HydrologyType;
import org.integratedmodelling.ecology.biomass.lpjguess.IntercropType;
import org.integratedmodelling.ecology.biomass.lpjguess.LandcoverType;
import org.integratedmodelling.ecology.biomass.lpjguess.Management;
import org.integratedmodelling.ecology.biomass.lpjguess.NaturalVeg;
import org.integratedmodelling.ecology.biomass.lpjguess.StandType;
import org.integratedmodelling.klab.Logging;

public class STFactory {

    // private static Field getFieldRecursively(Class<?> c, String fieldname)
    // throws NoSuchFieldException, SecurityException {
    // Field f = null;
    //
    // if (!fieldname.contains(".")) {
    // // Simple individual field
    // f = c.getDeclaredField(fieldname);
    // return f;
    // } else {
    // String[] parts = fieldname.split("\\.");
    //
    // // Split on .'s to break it up into individual bits
    // for (int i = 0; i < parts.length - 1; i++) {
    // f = c.getDeclaredField(parts[i]);
    // c = f.getClass();
    // }
    //
    // f = c.getDeclaredField(parts[parts.length - 1]);
    // return f;
    // }
    // }

    public static StandType getST(String st_name, int id) {

        StandType result = new StandType(st_name);
        result.id = id;

        STDef def = _types.get(st_name);

        /**
         * REFERENCE DECLARATIONS are in framework/parameters.cpp
         */
        for (Entry<String, Object> entry : def.entrySet()) {

            String key = entry.getKey();
            Object val = entry.getValue();

            if (key.startsWith("management[")) {
                defineManagement(result, key, val);
            } else if (key.startsWith("rotation.")) {
                defineRotation(result, key, val);
            } else {

                switch (key) {
                case "landcover":
                    result.landcover = LandcoverType.get(val.toString());
                    break;
                case "intercrop":
                    result.intercrop = IntercropType.get(val.toString());
                    break;
                case "naturalveg":
                    result.naturalveg = NaturalVeg.get(val.toString());
                    break;
                case "naturalgrass":
                    result.naturalgrass = ((Number) val).intValue() != 0;
                    break;
                case "restrictpfts":
                    result.restrictpfts = ((Number) val).intValue() != 0;
                    break;
                case "frac":
                    result.frac = ((Number) val).doubleValue();
                    break;
                case "frac_old":
                    result.frac_old = ((Number) val).doubleValue();
                    break;
                case "protected_frac":
                    result.protected_frac = ((Number) val).doubleValue();
                    break;
                case "frac_change":
                    result.frac_change = ((Number) val).doubleValue();
                    break;
                case "gross_frac_increase":
                    result.gross_frac_increase = ((Number) val).doubleValue();
                    break;
                case "gross_frac_decrease":
                    result.gross_frac_decrease = ((Number) val).doubleValue();
                    break;
                default:
                	Logging.INSTANCE.error("unknow stand type specifier: " + key);
                }
            }

        }

        result.validate();

        return result;
    }

    private static void defineRotation(StandType result, String key, Object val) {

        if (result.rotation == null) {
            result.rotation = new CropRotation();
        }

        String field = org.integratedmodelling.klab.utils.Path.getLast(key, '.');

        switch (field) {
        case "ncrops":
            result.rotation.ncrops = ((Number) val).intValue();
            break;
        case "firstrotyear":
            result.rotation.firstrotyear = ((Number) val).intValue();
            break;
        case "nyears":
            result.rotation.nyears = ((Number) val).intValue();
            break;
        case "multicrop":
            result.rotation.multicrop = ((Number) val).intValue() != 0;
            break;
        default:
        	Logging.INSTANCE.error("unknow rotation specifier: " + field);
        }

    }

    private static void defineManagement(StandType result, String key, Object val) {

        int index = org.integratedmodelling.klab.utils.MiscUtilities.extractIndex(key);
        String field = org.integratedmodelling.klab.utils.Path.getLast(key, '.');

        if (result.management.size() <= index) {
            for (int i = 0; i <= index; i++) {
                if (result.management.size() <= i) {
                    result.management.add(new Management());
                }
            }
        }

        switch (field) {
        case "pftname":
            result.management.get(index).pftname = val.toString();
            break;
        case "hydrology":
            result.management.get(index).hydrology = HydrologyType.get(val.toString());
            break;
        case "nfert":
            result.management.get(index).nfert = ((Number) val).doubleValue();
            break;
        case "fallow":
            result.management.get(index).fallow = ((Number) val).intValue() != 0;
            break;
        default:
        	Logging.INSTANCE.error("unknow management specifier: " + field);
        }
    }

    public static List<StandType> getAllSTs() {
        List<StandType> result = new ArrayList<StandType>();

        int current_id = 0;

        for (String st_name : _types.keySet()) {
            result.add(getST(st_name, current_id));
            current_id++;
        }

        return result;
    }

    static class STDef extends HashMap<String, Object> {

        private static final long serialVersionUID = 1861532826609987988L;

    }

    // container for partially or fully specified Stand Type descriptions.
    static HashMap<String, STDef> _types = new HashMap<String, STDef>();

    static {
        add("common_stand", "rotation.ncrops", 0, "restrictpfts", 0, "intercrop", IntercropType.NOINTERCROP, "naturalveg", NaturalVeg.NONE);
        add("crop_stand", "rotation.ncrops", 1, "landcover", LandcoverType.CROP, "restrictpfts", 1, "intercrop", IntercropType.NATURALGRASS, "management[0].hydrology", HydrologyType.RAINFED);
        add("Peatland", "common_stand", "landcover", LandcoverType.PEATLAND);
        add("Natural", "common_stand", "landcover", LandcoverType.NATURAL);
        add("Pasture", "common_stand", "landcover", LandcoverType.PASTURE);
        add("Forest", "common_stand", "landcover", LandcoverType.FOREST, "naturalveg", NaturalVeg.ALL);
        add("Barren", "common_stand", "landcover", LandcoverType.BARREN, "naturalveg", NaturalVeg.NONE);
        add("CC3G", "crop_stand", "intercrop", IntercropType.NOINTERCROP, "management[0].pftname", "CC3G");
        add("CC4G", "crop_stand", "intercrop", IntercropType.NOINTERCROP, "management[0].pftname", "CC4G");
        add("TeWW", "crop_stand", "rotation.ncrops", 1, "management[0].hydrology", HydrologyType.RAINFED, "management[1].pftname", "TeCo", "rotation.nyears", 1, "management[2].fallow", 1, "management[1].hydrology", HydrologyType.IRRIGATED, "management[2].pftname", "TeCo", "management[0].pftname", "TeWW", "management[0].nfert", 50, "management[1].nfert", 0, "rotation.firstrotyear", 1901);
        add("TeWW_Nlim", "crop_stand", "management[0].pftname", "TeWW_Nlim");
        add("TrRi", "crop_stand", "management[0].pftname", "TrRi");
        add("TeCo", "crop_stand", "management[0].pftname", "TeCo");
        add("TeCo_Nlim", "crop_stand", "management[0].pftname", "TeCo_Nlim");
        add("TrMi", "crop_stand", "management[0].pftname", "TrMi");
        add("TePu", "crop_stand", "management[0].pftname", "TePu");
        add("TeSb", "crop_stand", "management[0].pftname", "TeSb");
        add("TrMa", "crop_stand", "management[0].pftname", "TrMa");
        add("TeSf", "crop_stand", "management[0].pftname", "TeSf");
        add("TeSo", "crop_stand", "management[0].pftname", "TeSo");
        add("TrPe", "crop_stand", "management[0].pftname", "TrPe");
        add("TeRa", "crop_stand", "management[0].pftname", "TeRa");
        add("CC3Girr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "intercrop", IntercropType.NOINTERCROP, "management[0].pftname", "CC3Girr");
        add("CC4Girr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "intercrop", IntercropType.NOINTERCROP, "management[0].pftname", "CC4Girr");
        add("TeWWirr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TeWWirr");
        add("TrRiirr", "crop_stand", "rotation.ncrops", 1, "management[0].hydrology", HydrologyType.IRRIGATED, "management[1].pftname", "TrRiirr2", "rotation.firstrotyear", 1901, "management[1].hydrology", HydrologyType.IRRIGATED, "rotation.multicrop", 0, "management[0].pftname", "TrRiirr", "management[0].nfert", 50, "management[1].nfert", 0, "rotation.nyears", 1);
        add("TeCoirr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TeCoirr");
        add("TrMiirr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TrMiirr");
        add("TePuirr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TePuirr");
        add("TeSbirr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TeSbirr");
        add("TrMairr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TrMairr");
        add("TeSfirr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TeSfirr");
        add("TeSoirr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TeSoirr");
        add("TrPeirr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TrPeirr");
        add("TeRairr", "crop_stand", "management[0].hydrology", HydrologyType.IRRIGATED, "management[0].pftname", "TeRairr");

    }

    /**
     * Add a Stand Type by providing key/value pairs. Data are expected to be
     * pairs unless the object is a Stand Type name, which in that case is
     * merged with the definition before continuing.
     * 
     * FIXME - this assumes reflections works on arrays and members. 
     * 
     * @param name
     * @param data
     */
    public static void add(String name, Object... data) {

        STDef st = new STDef();

        for (int i = 0; i < data.length; i++) {

            Object o = data[i];

            if (o instanceof String && _types.containsKey(o.toString())) {
                st.putAll(_types.get(o.toString()));
            } else {
                st.put(o.toString(), data[++i]);
            }
        }

        _types.put(name, st);

    }

}
