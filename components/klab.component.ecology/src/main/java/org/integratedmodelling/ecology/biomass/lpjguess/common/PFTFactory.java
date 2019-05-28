package org.integratedmodelling.ecology.biomass.lpjguess.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.integratedmodelling.ecology.biomass.lpjguess.ForceAutumnSowing;
import org.integratedmodelling.ecology.biomass.lpjguess.LandcoverType;
import org.integratedmodelling.ecology.biomass.lpjguess.LeafPhysiognomyType;
import org.integratedmodelling.ecology.biomass.lpjguess.PFT;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;
import org.integratedmodelling.procsim.api.IConfiguration.PhotosynthesisPathway;

/**
 * TODO this needs to read the whole list only once and behave as a singleton.
 * 
 * @author Ferd
 *
 */
public class PFTFactory {

    public static PFT getPFT(String pft_name, int id) {

        PFT result = new PFT(pft_name);
        result.id = id;

        PFTDef def = _types.get(pft_name);
        for (String key : def.keySet()) {

            Object val = def.get(key);

            switch (key) {
            case "lifeform":
                result.lifeform = LifeForm.get(val.toString());
                break;
            case "phenology":
                result.phenology = Phenology.get(val.toString());
                break;
            case "phengdd5ramp":
                result.phengdd5ramp = ((Number) val).doubleValue();
                break;
            case "wscal_min":
                result.wscal_min = ((Number) val).doubleValue();
                break;
            case "pathway":
                result.pathway = PhotosynthesisPathway.get(val.toString());
                break;
            case "pstemp_min":
                result.pstemp_min = ((Number) val).doubleValue();
                break;
            case "pstemp_low":
                result.pstemp_low = ((Number) val).doubleValue();
                break;
            case "pstemp_high":
                result.pstemp_high = ((Number) val).doubleValue();
                break;
            case "pstemp_max":
                result.pstemp_max = ((Number) val).doubleValue();
                break;
            case "lambda_max":
                result.lambda_max = ((Number) val).doubleValue();
                break;
            case "gmin":
                result.gmin = ((Number) val).doubleValue();
                break;
            case "emax":
                result.emax = ((Number) val).doubleValue();
                break;
            case "respcoeff":
                result.respcoeff = ((Number) val).doubleValue();
                break;
            case "cton_leaf":
                result.cton_leaf = ((Number) val).doubleValue();
                break;
            case "cton_leaf_min":
                result.cton_leaf_min = ((Number) val).doubleValue();
                break;
            case "cton_leaf_max":
                result.cton_leaf_max = ((Number) val).doubleValue();
                break;
            case "cton_leaf_avr":
                result.cton_leaf_avr = ((Number) val).doubleValue();
                break;
            case "cton_root_avr":
                result.cton_root_avr = ((Number) val).doubleValue();
                break;
            case "cton_root_max":
                result.cton_root_max = ((Number) val).doubleValue();
                break;
            case "cton_sap_avr":
                result.cton_sap_avr = ((Number) val).doubleValue();
                break;
            case "cton_sap_max":
                result.cton_sap_max = ((Number) val).doubleValue();
                break;
            case "cton_root":
                result.cton_root = ((Number) val).doubleValue();
                break;
            case "cton_sap":
                result.cton_sap = ((Number) val).doubleValue();
                break;
            case "nuptoroot":
                result.nuptoroot = ((Number) val).doubleValue();
                break;
            case "nupscoeff":
                result.nupscoeff = ((Number) val).doubleValue();
                break;
            case "fnstorage":
                result.fnstorage = ((Number) val).doubleValue();
                break;
            case "km_volume":
                result.km_volume = ((Number) val).doubleValue();
                break;
            case "reprfrac":
                result.reprfrac = ((Number) val).doubleValue();
                break;
            case "turnover_leaf":
                result.turnover_leaf = ((Number) val).doubleValue();
                break;
            case "turnover_root":
                result.turnover_root = ((Number) val).doubleValue();
                break;
            case "turnover_sap":
                result.turnover_sap = ((Number) val).doubleValue();
                break;
            case "wooddens":
                result.wooddens = ((Number) val).doubleValue();
                break;
            case "crownarea_max":
                result.crownarea_max = ((Number) val).doubleValue();
                break;
            case "k_allom1":
                result.k_allom1 = ((Number) val).doubleValue();
                break;
            case "k_allom2":
                result.k_allom2 = ((Number) val).doubleValue();
                break;
            case "k_allom3":
                result.k_allom3 = ((Number) val).doubleValue();
                break;
            case "k_rp":
                result.k_rp = ((Number) val).doubleValue();
                break;
            case "k_latosa":
                result.k_latosa = ((Number) val).doubleValue();
                break;
            case "sla":
                result.sla = ((Number) val).doubleValue();
                break;
            case "leaflong":
                result.leaflong = ((Number) val).doubleValue();
                break;
            case "ltor_max":
                result.ltor_max = ((Number) val).doubleValue();
                break;
            case "litterme":
                result.litterme = ((Number) val).doubleValue();
                break;
            case "fireresist":
                result.fireresist = ((Number) val).doubleValue();
                break;
            case "parff_min":
                result.parff_min = ((Number) val).doubleValue();
                break;
            case "alphar":
                result.alphar = ((Number) val).doubleValue();
                break;
            case "est_max":
                result.est_max = ((Number) val).doubleValue();
                break;
            case "kest_repr":
                result.kest_repr = ((Number) val).doubleValue();
                break;
            case "kest_bg":
                result.kest_bg = ((Number) val).doubleValue();
                break;
            case "kest_pres":
                result.kest_pres = ((Number) val).doubleValue();
                break;
            case "longevity":
                result.longevity = ((Number) val).doubleValue();
                break;
            case "greff_min":
                result.greff_min = ((Number) val).doubleValue();
                break;
            case "tcmin_surv":
                result.tcmin_surv = ((Number) val).doubleValue();
                break;
            case "tcmax_est":
                result.tcmax_est = ((Number) val).doubleValue();
                break;
            case "gdd5min_est":
                result.gdd5min_est = ((Number) val).doubleValue();
                break;
            case "tcmin_est":
                result.tcmin_est = ((Number) val).doubleValue();
                break;
            case "twmin_est":
                result.twmin_est = ((Number) val).doubleValue();
                break;
            case "twminusc":
                result.twminusc = ((Number) val).doubleValue();
                break;
            case "k_chilla":
                result.k_chilla = ((Number) val).doubleValue();
                break;
            case "k_chillb":
                result.k_chillb = ((Number) val).doubleValue();
                break;
            case "k_chillk":
                result.k_chillk = ((Number) val).doubleValue();
                break;
            case "intc":
                result.intc = ((Number) val).doubleValue();
                break;
            case "N_appfert":
                result.N_appfert = ((Number) val).doubleValue();
                break;
            case "dev_stage":
                result.dev_stage = ((Number) val).doubleValue();
                break;
            case "T_vn_min":
                result.T_vn_min = ((Number) val).doubleValue();
                break;
            case "T_vn_opt":
                result.T_vn_opt = ((Number) val).doubleValue();
                break;
            case "T_vn_max":
                result.T_vn_max = ((Number) val).doubleValue();
                break;
            case "T_veg_min":
                result.T_veg_min = ((Number) val).doubleValue();
                break;
            case "T_veg_opt":
                result.T_veg_opt = ((Number) val).doubleValue();
                break;
            case "T_veg_max":
                result.T_veg_max = ((Number) val).doubleValue();
                break;
            case "T_rep_min":
                result.T_rep_min = ((Number) val).doubleValue();
                break;
            case "T_rep_opt":
                result.T_veg_opt = ((Number) val).doubleValue();
                break;
            case "T_rep_max":
                result.T_rep_max = ((Number) val).doubleValue();
                break;
            case "dev_rate_veg":
                result.dev_rate_veg = ((Number) val).doubleValue();
                break;
            case "dev_rate_rep":
                result.dev_rate_rep = ((Number) val).doubleValue();
                break;
            case "cton_stem_avr":
                result.cton_stem_avr = ((Number) val).doubleValue();
                break;
            case "cton_stem_max":
                result.cton_stem_max = ((Number) val).doubleValue();
                break;
            case "landcover":
                result.landcover = LandcoverType.get(val.toString());
                break;
            case "res_outtake":
                result.res_outtake = ((Number) val).doubleValue();
                break;
            case "harv_eff":
                result.harv_eff = ((Number) val).doubleValue();
                break;
            case "harv_eff_ic":
                result.harv_eff_ic = ((Number) val).doubleValue();
                break;
            case "harvest_slow_frac":
                result.harvest_slow_frac = ((Number) val).doubleValue();
                break;
            case "turnover_harv_prod":
                result.turnover_harv_prod = ((Number) val).doubleValue();
                break;
            case "isintercropgrass":
                result.isintercropgrass = ((Number) val).intValue() != 0;
                break;
            case "ifsdcalc":
                result.ifsdcalc = ((Number) val).intValue() != 0;
                break;
            case "ifsdtemp":
                result.ifsdtemp = ((Number) val).intValue() != 0;
                break;
            case "ifsdautumn":
                result.ifsdautumn = ((Number) val).intValue() != 0;
                break;
            case "ifsdspring":
                result.ifsdspring = ((Number) val).intValue() != 0;
                break;
            case "ifsdprec":
                result.ifsdprec = ((Number) val).intValue() != 0;
                break;
            case "tempautumn":
                result.tempautumn = ((Number) val).doubleValue();
                break;
            case "tempspring":
                result.tempspring = ((Number) val).doubleValue();
                break;
            case "sdatenh":
                result.sdatenh = ((Number) val).intValue();
                break;
            case "sdatesh":
                result.sdatesh = ((Number) val).intValue();
                break;
            case "hlimitdatenh":
                result.hlimitdatenh = ((Number) val).intValue();
                break;
            case "hlimitdatesh":
                result.hlimitdatesh = ((Number) val).intValue();
                break;
            case "tb":
                result.tb = ((Number) val).doubleValue();
                break;
            case "firstsowdatenh_prec":
                result.firstsowdatenh_prec = ((Number) val).intValue();
                break;
            case "firstsowdatesh_prec":
                result.firstsowdatesh_prec = ((Number) val).intValue();
                break;
            case "trg":
                result.trg = ((Number) val).doubleValue();
                break;
            case "pvd":
                result.pvd = ((Number) val).intValue();
                break;
            case "psens":
                result.psens = ((Number) val).doubleValue();
                break;
            case "pb":
                result.pb = ((Number) val).doubleValue();
                break;
            case "ps":
                result.ps = ((Number) val).doubleValue();
                break;
            case "phu":
                result.phu = ((Number) val).doubleValue();
                break;
            case "fphusen":
                result.fphusen = ((Number) val).doubleValue();
                break;
            case "shapesenescencenorm":
                result.shapesenescencenorm = ((Number) val).intValue() != 0;
                break;
            case "flaimaxharvest":
                result.flaimaxharvest = ((Number) val).doubleValue();
                break;
            case "laimax":
                result.laimax = ((Number) val).doubleValue();
                break;
            case "aboveground_ho":
                result.aboveground_ho = ((Number) val).intValue() != 0;
                break;
            case "hiopt":
                result.hiopt = ((Number) val).doubleValue();
                break;
            case "himin":
                result.himin = ((Number) val).doubleValue();
                break;
            case "frootstart":
                result.frootstart = ((Number) val).doubleValue();
                break;
            case "frootend":
                result.frootend = ((Number) val).doubleValue();
                break;
            case "readsowingdate":
                result.readsowingdate = ((Number) val).intValue() != 0;
                break;
            case "readharvestdate":
                result.readharvestdate = ((Number) val).intValue() != 0;
                break;
            case "forceautumnsowing":
                result.forceautumnsowing = ForceAutumnSowing.get(val.toString());
                break;
            case "readNfert":
                result.readNfert = ((Number) val).intValue() != 0;
                break;
            case "nlim":
                result.nlim = ((Number) val).intValue() != 0;
                break;
            case "leafphysiognomy":
                result.leafphysiognomy = LeafPhysiognomyType.get(val.toString());
                break;
            case "drought_tolerance":
                result.drought_tolerance = ((Number) val).doubleValue();
                break;
            case "rootdist":
                result.rootdist = (double[]) val;
                break;
            case "ga":
                result.ga = ((Number) val).doubleValue();
                break;
            case "eps_iso":
                result.eps_iso = ((Number) val).doubleValue();
                break;
            case "seas_iso":
                result.seas_iso = ((Number) val).intValue() != 0;
                break;
            case "eps_mon":
                result.eps_mon = ((Number) val).doubleValue();
                break;
            case "storfrac_mon":
                result.storfrac_mon = ((Number) val).doubleValue();
                break;
            case "a1":
                result.a1 = ((Number) val).doubleValue();
                break;
            case "a2":
                result.a2 = ((Number) val).doubleValue();
                break;
            case "a3":
                result.a3 = ((Number) val).doubleValue();
                break;
            case "b1":
                result.b1 = ((Number) val).doubleValue();
                break;
            case "b2":
                result.b2 = ((Number) val).doubleValue();
                break;
            case "b3":
                result.b3 = ((Number) val).doubleValue();
                break;
            case "c1":
                result.c1 = ((Number) val).doubleValue();
                break;
            case "c2":
                result.c2 = ((Number) val).doubleValue();
                break;
            case "c3":
                result.c3 = ((Number) val).doubleValue();
                break;
            case "d1":
                result.d1 = ((Number) val).doubleValue();
                break;
            case "d2":
                result.d2 = ((Number) val).doubleValue();
                break;
            case "d3":
                result.d3 = ((Number) val).doubleValue();
                break;
            case "fertrate":
                result.fertrate = (double[]) val;
                break;
            case "photo":
                result.photo = (double[]) val;
                break;
            case "fertdates":
                result.fertdates = toInt((double[]) val);
                break;
            default:
                Logging.INSTANCE.error("cannot match PFT key to field: " + key);
                break;
            }
        }

        result.init();

        return result;
    }

    private static int[] toInt(double[] val) {
        int[] ret = new int[val.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (int) val[i];
        }
        return ret;
    }

    public static List<PFT> getAllPFTs() {
        List<PFT> result = new ArrayList<PFT>();

        int current_id = 0;

        for (String pft_name : _types.keySet()) {
            result.add(getPFT(pft_name, current_id));
            current_id++;
        }

        return result;
    }

    /*
     * FIXME remove, useless. 
     */
    static class PFTDef extends HashMap<String, Object> {

        private static final long serialVersionUID = 1861532826609987988L;

    }

    // container for partially or fully specified PFT descriptions.
    static HashMap<String, PFTDef> _types = new HashMap<String, PFTDef>();

    static {
        add("common", "reprfrac", 0.1, "lambda_max", 0.8, "emax", 5, "drought_tolerance", 0.0001, "wscal_min", 0.35, "res_outtake", 0.75, "landcover", "natural", "harv_eff", 0.7);
        add("tree", "common", "rootdist", new double[] {
                0.6,
                0.4 }, "respcoeff", 1.0, "kest_bg", 0.1, "nuptoroot", 0.0028, "harv_eff", 0.95, "turnover_root", 0.7, "wooddens", 200, "k_allom3", 0.67, "pathway", PhotosynthesisPathway.C3, "kest_pres", 1, "ltor_max", 1, "k_rp", 1.6, "km_volume", 0.000001477, "kest_repr", 200, "litterme", 0.3, "k_allom2", 60, "cton_root", 29, "lifeform", LifeForm.TREE, "harvest_slow_frac", 0.33, "cton_sap", 330, "k_chilla", 0, "k_chillb", 100, "turnover_harv_prod", 0.04, "k_chillk", 0.05, "crownarea_max", 50);
        add("shrub", "common", "rootdist", new double[] {
                0.6,
                0.4 }, "leafphysiognomy", "broadleaf", "kest_bg", 0.1, "nuptoroot", 0.0028, "longevity", 100, "kest_repr", 20, "wooddens", 250, "k_allom3", 0.67, "pathway", PhotosynthesisPathway.C3, "kest_pres", 1, "ltor_max", 1, "k_rp", 1.6, "km_volume", 0.000001477, "k_allom1", 100, "litterme", 0.3, "k_allom2", 5, "cton_root", 29, "lifeform", LifeForm.TREE, "fnstorage", 0.3, "harvest_slow_frac", 0, "cton_sap", 330, "turnover_harv_prod", 1, "crownarea_max", 10);
        add("grass", "common", "rootdist", new double[] {
                0.9,
                0.1 }, "intc", 0.01, "leafphysiognomy", "broadleaf", "nuptoroot", 0.00551, "ga", 0.030, "parff_min", 1000000, "turnover_root", 0.7, "res_outtake", 0, "turnover_harv_prod", 1, "ltor_max", 0.5, "leaflong", 0.5, "gmin", 0.5, "km_volume", 0.000001876, "phengdd5ramp", 100, "litterme", 0.2, "cton_root", 29, "lifeform", LifeForm.GRASS, "fnstorage", 0.3, "harvest_slow_frac", 0, "turnover_leaf", 1, "fireresist", 0.5, "phenology", Phenology.ANY);
        add("crop", "rootdist", new double[] {
                0.9,
                0.1 }, "twmin_est", -1000, "intc", 0.01, "respcoeff", 1, "ifsdautumn", 0, "parff_min", 0, "readharvestdate", 0, "ifsdprec", 0, "nlim", 0, "readNfert", 1, "turnover_root", 0.7, "readsowingdate", 0, "frootend", 0.20, "harv_eff", 0.9, "turnover_harv_prod", 1, "tcmax_est", 1000, "tempspring", 12, "leaflong", 1, "tempautumn", 12, "gmin", 0.5, "tcmin_surv", -1000, "fireresist", 0.5, "ifsdtemp", 0, "landcover", "cropland", "gdd5min_est", 0, "harvest_slow_frac", 0, "isintercropgrass", 0, "turnover_leaf", 1, "aboveground_ho", 1, "res_outtake", 0.75, "frootstart", 0.40, "tcmin_est", -1000, "ifsdspring", 0, "phenology", Phenology.CROPGREEN, "ifsdcalc", 0);
        add("broadleaved", "intc", 0.02, "leafphysiognomy", "broadleaf", "gmin", 0.5, "ga", 0.040, "k_latosa", 6000, "k_allom1", 250);
        add("needleleaved", "intc", 0.06, "leafphysiognomy", "needleleaf", "gmin", 0.3, "ga", 0.140, "k_latosa", 5000, "k_allom1", 150);
        add("evergreen", "fnstorage", 0.05, "phenology", Phenology.EVERGREEN, "phengdd5ramp", 0);
        add("summergreen", "leaflong", 0.5, "turnover_leaf", 1, "fnstorage", 0.15, "phenology", Phenology.SUMMERGREEN, "phengdd5ramp", 200);
        add("boreal", "pstemp_low", 10, "respcoeff", 1.0, "pstemp_min", -4, "pstemp_high", 25, "pstemp_max", 38);
        add("temperate", "pstemp_low", 15, "respcoeff", 1.0, "pstemp_min", -2, "pstemp_high", 25, "pstemp_max", 38);
        add("tropical", "pstemp_min", 2, "twmin_est", -1000, "pstemp_low", 25, "pstemp_max", 55, "tcmin_surv", 15.5, "respcoeff", 0.15, "pstemp_high", 30, "tcmax_est", 1000, "tcmin_est", 15.5, "gdd5min_est", 0);
        add("tropical", "pstemp_min", 2, "twmin_est", -1000, "pstemp_low", 25, "pstemp_max", 55, "tcmin_surv", 15.5, "respcoeff", 0.15, "pstemp_high", 30, "tcmax_est", 1000, "tcmin_est", 15.5, "gdd5min_est", 0);
        add("shade_tolerant", "alphar", 3.0, "parff_min", 350000, "greff_min", 0.04, "est_max", 0.05, "turnover_sap", 0.05);
        add("intermediate_shade_tolerant", "alphar", 7.0, "parff_min", 2000000, "greff_min", 0.06, "est_max", 0.15, "turnover_sap", 0.075);
        add("shade_intolerant", "alphar", 10.0, "parff_min", 2500000, "greff_min", 0.08, "est_max", 0.2, "turnover_sap", 0.1);
        add("BNE", "tree", "needleleaved", "shade_tolerant", "evergreen", "boreal", "leaflong", 3, "twmin_est", 5, "storfrac_mon", 0.5, "eps_mon", 4.8, "tcmin_surv", -31, "longevity", 500, "turnover_leaf", 0.33, "seas_iso", 0, "tcmax_est", -1, "fireresist", 0.3, "tcmin_est", -30, "gdd5min_est", 500, "eps_iso", 8.0);
        add("BNE", "BNE");
        add("BINE", "tree", "needleleaved", "shade_intolerant", "evergreen", "boreal", "leaflong", 3, "twmin_est", 5, "storfrac_mon", 0.5, "eps_mon", 4.8, "tcmin_surv", -31, "longevity", 500, "turnover_leaf", 0.33, "seas_iso", 0, "tcmax_est", -1, "fireresist", 0.3, "tcmin_est", -30, "gdd5min_est", 500, "eps_iso", 8.0);
        add("BINE", "BINE");
        add("BNS", "tree", "needleleaved", "shade_intolerant", "summergreen", "boreal", "twminusc", 43, "eps_mon", 4.8, "twmin_est", -1000, "storfrac_mon", 0.5, "tcmin_surv", -1000, "longevity", 300, "phengdd5ramp", 100, "seas_iso", 1, "tcmax_est", -2, "fireresist", 0.3, "tcmin_est", -1000, "gdd5min_est", 350, "eps_iso", 8.0);
        add("BNS", "BNS");
        add("TeNE", "tree", "needleleaved", "shade_intolerant", "evergreen", "temperate", "leaflong", 3, "twmin_est", 5, "tcmin_surv", -2, "longevity", 300, "turnover_leaf", 0.33, "tcmax_est", 10, "fireresist", 0.3, "tcmin_est", -2, "gdd5min_est", 2000, "eps_iso", 8.0);
        add("TeNE", "TeNE");
        add("TeBS", "tree", "broadleaved", "shade_tolerant", "summergreen", "temperate", "eps_mon", 1.6, "twmin_est", 5, "storfrac_mon", 0., "tcmin_surv", -14, "longevity", 400, "seas_iso", 1, "tcmax_est", 6, "fireresist", 0.1, "tcmin_est", -13, "gdd5min_est", 1100, "eps_iso", 45.0);
        add("TeBS", "TeBS");
        add("IBS", "tree", "broadleaved", "shade_intolerant", "summergreen", "boreal", "eps_mon", 1.6, "twmin_est", -1000, "storfrac_mon", 0., "tcmin_surv", -30, "longevity", 300, "seas_iso", 1, "tcmax_est", 7, "fireresist", 0.1, "tcmin_est", -30, "gdd5min_est", 350, "eps_iso", 45.0);
        add("IBS", "IBS");
        add("TeBE", "tree", "broadleaved", "shade_tolerant", "evergreen", "temperate", "leaflong", 3, "twmin_est", 5, "storfrac_mon", 0., "eps_mon", 1.6, "tcmin_surv", -1, "longevity", 300, "turnover_leaf", 0.33, "seas_iso", 0, "tcmax_est", 10, "fireresist", 0.3, "tcmin_est", 0, "gdd5min_est", 2000, "eps_iso", 24.0);
        add("TeBE", "TeBE");
        add("TrBE", "tree", "broadleaved", "shade_tolerant", "evergreen", "tropical", "leaflong", 2, "storfrac_mon", 0., "eps_mon", 0.8, "longevity", 500, "turnover_leaf", 0.5, "seas_iso", 0, "fireresist", 0.1, "eps_iso", 24.0);
        add("TrBE", "TrBE");
        add("TrIBE", "tree", "broadleaved", "shade_intolerant", "evergreen", "tropical", "leaflong", 2, "storfrac_mon", 0., "eps_mon", 0.8, "longevity", 200, "turnover_leaf", 0.5, "seas_iso", 0, "fireresist", 0.1, "eps_iso", 24.0);
        add("TrIBE", "TrIBE");
        add("TrBR", "tree", "broadleaved", "shade_intolerant", "tropical", "leaflong", 0.5, "fnstorage", 0.15, "storfrac_mon", 0., "eps_mon", 2.4, "longevity", 400, "turnover_leaf", 1, "seas_iso", 0, "fireresist", 0.3, "eps_iso", 45.0, "phenology", Phenology.RAINGREEN);
        add("TrBR", "TrBR");
        add("C3G", "grass", "pstemp_min", -5, "twmin_est", -1000, "respcoeff", 1.0, "storfrac_mon", 0.5, "pstemp_max", 45, "eps_mon", 1.6, "tcmin_surv", -1000, "pstemp_low", 10, "pstemp_high", 30, "seas_iso", 1, "tcmax_est", 1000, "tcmin_est", -1000, "gdd5min_est", 0, "eps_iso", 16.0, "pathway", PhotosynthesisPathway.C3);
        add("C3G", "C3G");
        add("C4G", "grass", "pstemp_min", 6, "twmin_est", -1000, "respcoeff", 0.15, "storfrac_mon", 0.5, "pstemp_max", 55, "eps_mon", 2.4, "tcmin_surv", 15.5, "pstemp_low", 20, "pstemp_high", 45, "seas_iso", 0, "tcmax_est", 1000, "tcmin_est", 15.5, "gdd5min_est", 0, "eps_iso", 8.0, "pathway", PhotosynthesisPathway.C4);
        add("C4G", "C4G");
        add("forest");
        add("BNE_F", "BNE", "forest", "landcover", "forest");
        add("BINE_F", "BINE", "forest", "landcover", "forest");
        add("BNS_F", "BNS", "forest", "landcover", "forest");
        add("TeNE_F", "TeNE", "forest", "landcover", "forest");
        add("TeBS_F", "TeBS", "forest", "landcover", "forest");
        add("IBS_F", "IBS", "forest", "landcover", "forest");
        add("TeBE_F", "TeBE", "forest", "landcover", "forest");
        add("TrBE_F", "TrBE", "forest", "landcover", "forest");
        add("TrIBE_F", "TrIBE", "forest", "landcover", "forest");
        add("TrBR_F", "TrBR", "forest", "landcover", "forest");
        add("C3G_F", "C3G", "forest", "landcover", "forest", "harv_eff", 0);
        add("C4G_F", "C4G", "forest", "landcover", "forest", "harv_eff", 0);
        add("C3G_past", "C3G", "landcover", "pasture", "harv_eff", 0.5, "res_outtake", 0.0);
        add("C4G_past", "C4G", "landcover", "pasture", "harv_eff", 0.5, "res_outtake", 0.0);
        add("C3G_urban", "C3G", "landcover", "urban", "harv_eff", 0);
        add("C3G_peat", "C3G", "landcover", "peatland", "harv_eff", 0);

        add("CC3G", "C3G", "res_outtake", 0.0, "laimax", 4, "isintercropgrass", 0, "harv_eff_ic", 0.0, "landcover", "cropland", "harv_eff", 0.5);
        add("CC4G", "C4G", "res_outtake", 0.0, "laimax", 4, "isintercropgrass", 0, "harv_eff_ic", 0.0, "landcover", "cropland", "harv_eff", 0.5);
        add("TeWW", "grass", "crop", "fphusen", 0.7, "flaimaxharvest", 0, "ifsdautumn", 1, "pvd", 0, "readharvestdate", 1, "sdatesh", 150, "sdatenh", 330, "ps", 20, "pstemp_min", -5, "readsowingdate", 1, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 1, "pb", 8, "forceautumnsowing", 0, "pstemp_high", 30, "tempspring", 5, "tb", 0, "pathway", PhotosynthesisPathway.C3, "tempautumn", 12, "ifsdtemp", 1, "phengdd5ramp", 500, "hiopt", 0.5, "phu", 2900, "hlimitdatenh", 26, "psens", 0.5, "trg", 12, "hlimitdatesh", 89, "himin", 0.2, "ifsdspring", 1, "ifsdcalc", 1);
        add("TrRi", "grass", "crop", "fphusen", 0.80, "flaimaxharvest", 0, "pvd", 0, "readharvestdate", 1, "sdatesh", 300, "sdatenh", 120, "ps", 0, "pstemp_min", -5, "readsowingdate", 1, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 1, "pb", 24, "pstemp_high", 30, "tempspring", 18, "tb", 10, "pathway", PhotosynthesisPathway.C3, "hiopt", 0.5, "phu", 1800, "hlimitdatenh", 60, "psens", 1, "trg", 10, "hlimitdatesh", 240, "himin", 0.25, "ifsdcalc", 1);
        add("TrRi", "grass", "crop", "fphusen", 0.80, "flaimaxharvest", 0, "pvd", 0, "readharvestdate", 1, "sdatesh", 300, "sdatenh", 120, "ps", 0, "pstemp_min", -5, "readsowingdate", 1, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 1, "pb", 24, "pstemp_high", 30, "tempspring", 18, "tb", 10, "pathway", PhotosynthesisPathway.C3, "hiopt", 0.5, "phu", 1800, "hlimitdatenh", 60, "psens", 1, "trg", 10, "hlimitdatesh", 240, "himin", 0.25, "ifsdcalc", 1);
        add("TeCo", "grass", "crop", "fphusen", 0.75, "respcoeff", 0.15, "flaimaxharvest", 0, "pvd", 0, "firstsowdatenh_prec", -1, "readharvestdate", 1, "sdatesh", 320, "sdatenh", 140, "ps", 24, "pstemp_min", 6, "readsowingdate", 1, "pstemp_low", 20, "pstemp_max", 55, "himin", 0.3, "pb", 0, "pstemp_high", 45, "firstsowdatesh_prec", -1, "tempspring", 14, "tb", 8, "pathway", PhotosynthesisPathway.C4, "ifsdtemp", 1, "phengdd5ramp", 500, "ifsdprec", 1, "hiopt", 0.5, "phu", 1400, "hlimitdatenh", 360, "psens", 1, "trg", 8, "hlimitdatesh", 176, "shapesenescencenorm", 1, "ifsdspring", 1, "ifsdcalc", 1);
        add("TeCo", "grass", "crop", "fphusen", 0.75, "respcoeff", 0.15, "flaimaxharvest", 0, "pvd", 0, "firstsowdatenh_prec", -1, "readharvestdate", 1, "sdatesh", 320, "sdatenh", 140, "ps", 24, "pstemp_min", 6, "readsowingdate", 1, "pstemp_low", 20, "pstemp_max", 55, "himin", 0.3, "pb", 0, "pstemp_high", 45, "firstsowdatesh_prec", -1, "tempspring", 14, "tb", 8, "pathway", PhotosynthesisPathway.C4, "ifsdtemp", 1, "phengdd5ramp", 500, "ifsdprec", 1, "hiopt", 0.5, "phu", 1400, "hlimitdatenh", 360, "psens", 1, "trg", 8, "hlimitdatesh", 176, "shapesenescencenorm", 1, "ifsdspring", 1, "ifsdcalc", 1);
        add("TrMi", "grass", "crop", "fphusen", 0.85, "respcoeff", 0.15, "flaimaxharvest", 0, "pvd", 0, "firstsowdatenh_prec", 120, "sdatesh", 330, "sdatenh", 150, "ps", 24, "pstemp_min", 6, "pstemp_low", 20, "pstemp_max", 55, "himin", 0.1, "pb", 0, "pstemp_high", 45, "firstsowdatesh_prec", 300, "tempspring", 12, "tb", 10, "pathway", PhotosynthesisPathway.C4, "phengdd5ramp", 500, "ifsdprec", 1, "hiopt", 0.25, "phu", 2000, "hlimitdatenh", 50, "psens", 1, "trg", 10, "hlimitdatesh", 230, "shapesenescencenorm", 1, "ifsdcalc", 1);
        add("TrMi", "grass", "crop", "fphusen", 0.85, "respcoeff", 0.15, "flaimaxharvest", 0, "pvd", 0, "firstsowdatenh_prec", 120, "sdatesh", 330, "sdatenh", 150, "ps", 24, "pstemp_min", 6, "pstemp_low", 20, "pstemp_max", 55, "himin", 0.1, "pb", 0, "pstemp_high", 45, "firstsowdatesh_prec", 300, "tempspring", 12, "tb", 10, "pathway", PhotosynthesisPathway.C4, "phengdd5ramp", 500, "ifsdprec", 1, "hiopt", 0.25, "phu", 2000, "hlimitdatenh", 50, "psens", 1, "trg", 10, "hlimitdatesh", 230, "shapesenescencenorm", 1, "ifsdcalc", 1);
        add("TrMi", "grass", "crop", "fphusen", 0.85, "respcoeff", 0.15, "flaimaxharvest", 0, "pvd", 0, "firstsowdatenh_prec", 120, "sdatesh", 330, "sdatenh", 150, "ps", 24, "pstemp_min", 6, "pstemp_low", 20, "pstemp_max", 55, "himin", 0.1, "pb", 0, "pstemp_high", 45, "firstsowdatesh_prec", 300, "tempspring", 12, "tb", 10, "pathway", PhotosynthesisPathway.C4, "phengdd5ramp", 500, "ifsdprec", 1, "hiopt", 0.25, "phu", 2000, "hlimitdatenh", 50, "psens", 1, "trg", 10, "hlimitdatesh", 230, "shapesenescencenorm", 1, "ifsdcalc", 1);
        add("TePu", "grass", "crop", "fphusen", 0.90, "flaimaxharvest", 0, "pvd", 0, "readharvestdate", 1, "sdatesh", 280, "sdatenh", 100, "ps", 24, "pstemp_min", -5, "readsowingdate", 1, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 2, "pb", 0, "pstemp_high", 30, "tempspring", 10, "tb", 3, "pathway", PhotosynthesisPathway.C3, "phengdd5ramp", 500, "hiopt", 0.6, "phu", 2000, "hlimitdatenh", 364, "psens", 1, "trg", 3, "hlimitdatesh", 18, "himin", 0.01, "ifsdcalc", 0);
        add("TePu", "grass", "crop", "fphusen", 0.90, "flaimaxharvest", 0, "pvd", 0, "readharvestdate", 1, "sdatesh", 280, "sdatenh", 100, "ps", 24, "pstemp_min", -5, "readsowingdate", 1, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 2, "pb", 0, "pstemp_high", 30, "tempspring", 10, "tb", 3, "pathway", PhotosynthesisPathway.C3, "phengdd5ramp", 500, "hiopt", 0.6, "phu", 2000, "hlimitdatenh", 364, "psens", 1, "trg", 3, "hlimitdatesh", 18, "himin", 0.01, "ifsdcalc", 0);
        add("TeSb", "grass", "crop", "fphusen", 0.75, "flaimaxharvest", 0.75, "pvd", 0, "sdatesh", 270, "sdatenh", 90, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 0, "pb", 0, "pstemp_high", 30, "tempspring", 8, "tb", 3, "pathway", PhotosynthesisPathway.C3, "phengdd5ramp", 500, "hiopt", 2.0, "phu", 2700, "hlimitdatenh", 330, "psens", 1, "trg", 3, "hlimitdatesh", 146, "himin", 1.1, "aboveground_ho", 0, "ifsdcalc", 0);
        add("TeSb", "grass", "crop", "fphusen", 0.75, "flaimaxharvest", 0.75, "pvd", 0, "sdatesh", 270, "sdatenh", 90, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 0, "pb", 0, "pstemp_high", 30, "tempspring", 8, "tb", 3, "pathway", PhotosynthesisPathway.C3, "phengdd5ramp", 500, "hiopt", 2.0, "phu", 2700, "hlimitdatenh", 330, "psens", 1, "trg", 3, "hlimitdatesh", 146, "himin", 1.1, "aboveground_ho", 0, "ifsdcalc", 0);
        add("TrMa", "grass", "crop", "fphusen", 0.75, "flaimaxharvest", 0.75, "pvd", 0, "firstsowdatenh_prec", 80, "sdatesh", 280, "sdatenh", 100, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "himin", 1.1, "pb", 0, "pstemp_high", 30, "firstsowdatesh_prec", 260, "tempspring", 22, "tb", 15, "pathway", PhotosynthesisPathway.C3, "phengdd5ramp", 500, "ifsdprec", 1, "hiopt", 2.0, "phu", 2000, "hlimitdatenh", 33, "psens", 1, "trg", 15, "hlimitdatesh", 14, "shapesenescencenorm", 0, "aboveground_ho", 0, "ifsdcalc", 1);
        add("TrMa", "grass", "crop", "fphusen", 0.75, "flaimaxharvest", 0.75, "pvd", 0, "firstsowdatenh_prec", 80, "sdatesh", 280, "sdatenh", 100, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "himin", 1.1, "pb", 0, "pstemp_high", 30, "firstsowdatesh_prec", 260, "tempspring", 22, "tb", 15, "pathway", PhotosynthesisPathway.C3, "phengdd5ramp", 500, "ifsdprec", 1, "hiopt", 2.0, "phu", 2000, "hlimitdatenh", 33, "psens", 1, "trg", 15, "hlimitdatesh", 14, "shapesenescencenorm", 0, "aboveground_ho", 0, "ifsdcalc", 1);
        add("TeSf", "grass", "crop", "fphusen", 0.7, "flaimaxharvest", 0, "ifsdautumn", 0, "pvd", 0, "sdatesh", 300, "sdatenh", 120, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 1, "pb", 0, "pstemp_high", 30, "tempspring", 15, "tb", 6, "pathway", PhotosynthesisPathway.C3, "ifsdtemp", 1, "phengdd5ramp", 500, "ifsdprec", 0, "hiopt", 0.3, "phu", 1500, "hlimitdatenh", 300, "psens", 1, "trg", 6, "hlimitdatesh", 116, "himin", 0.2, "ifsdspring", 1, "ifsdcalc", 1);
        add("TeSf", "grass", "crop", "fphusen", 0.7, "flaimaxharvest", 0, "ifsdautumn", 0, "pvd", 0, "sdatesh", 300, "sdatenh", 120, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 1, "pb", 0, "pstemp_high", 30, "tempspring", 15, "tb", 6, "pathway", PhotosynthesisPathway.C3, "ifsdtemp", 1, "phengdd5ramp", 500, "ifsdprec", 0, "hiopt", 0.3, "phu", 1500, "hlimitdatenh", 300, "psens", 1, "trg", 6, "hlimitdatesh", 116, "himin", 0.2, "ifsdspring", 1, "ifsdcalc", 1);
        add("TeSo", "grass", "crop", "fphusen", 0.6, "flaimaxharvest", 0, "pvd", 0, "sdatesh", 320, "sdatenh", 140, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 0, "pb", 0, "pstemp_high", 30, "tempspring", 13, "tb", 10, "pathway", PhotosynthesisPathway.C3, "phengdd5ramp", 500, "hiopt", 0.3, "phu", 1500, "hlimitdatenh", 330, "psens", 1, "trg", 10, "hlimitdatesh", 146, "himin", 0.01, "ifsdcalc", 0);
        add("TeSo", "grass", "crop", "fphusen", 0.6, "flaimaxharvest", 0, "pvd", 0, "sdatesh", 320, "sdatenh", 140, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 0, "pb", 0, "pstemp_high", 30, "tempspring", 13, "tb", 10, "pathway", PhotosynthesisPathway.C3, "phengdd5ramp", 500, "hiopt", 0.3, "phu", 1500, "hlimitdatenh", 330, "psens", 1, "trg", 10, "hlimitdatesh", 146, "himin", 0.01, "ifsdcalc", 0);
        add("TrPe", "grass", "crop", "fphusen", 0.75, "flaimaxharvest", 0, "pvd", 0, "firstsowdatenh_prec", 100, "sdatesh", 320, "sdatenh", 140, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "himin", 0.3, "pb", 0, "pstemp_high", 30, "firstsowdatesh_prec", 280, "tempspring", 15, "tb", 14, "pathway", PhotosynthesisPathway.C3, "ifsdprec", 1, "hiopt", 0.4, "phu", 1500, "hlimitdatenh", 330, "psens", 1, "trg", 14, "hlimitdatesh", 146, "shapesenescencenorm", 0, "ifsdcalc", 1);
        add("TrPe", "grass", "crop", "fphusen", 0.75, "flaimaxharvest", 0, "pvd", 0, "firstsowdatenh_prec", 100, "sdatesh", 320, "sdatenh", 140, "ps", 24, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "himin", 0.3, "pb", 0, "pstemp_high", 30, "firstsowdatesh_prec", 280, "tempspring", 15, "tb", 14, "pathway", PhotosynthesisPathway.C3, "ifsdprec", 1, "hiopt", 0.4, "phu", 1500, "hlimitdatenh", 330, "psens", 1, "trg", 14, "hlimitdatesh", 146, "shapesenescencenorm", 0, "ifsdcalc", 1);
        add("TeRa", "grass", "crop", "fphusen", 0.85, "flaimaxharvest", 0, "ifsdautumn", 1, "pvd", 0, "sdatesh", 120, "sdatenh", 300, "ps", 20, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 1, "pb", 8, "forceautumnsowing", 0, "pstemp_high", 30, "tempspring", 5, "tb", 0, "pathway", PhotosynthesisPathway.C3, "tempautumn", 17, "ifsdtemp", 1, "phengdd5ramp", 500, "ifsdprec", 0, "hiopt", 0.3, "phu", 2500, "hlimitdatenh", 24, "psens", 1, "trg", 12, "hlimitdatesh", 60, "himin", 0.15, "ifsdspring", 1, "ifsdcalc", 1);
        add("TeRa", "grass", "crop", "fphusen", 0.85, "flaimaxharvest", 0, "ifsdautumn", 1, "pvd", 0, "sdatesh", 120, "sdatenh", 300, "ps", 20, "pstemp_min", -5, "pstemp_low", 10, "pstemp_max", 45, "shapesenescencenorm", 1, "pb", 8, "forceautumnsowing", 0, "pstemp_high", 30, "tempspring", 5, "tb", 0, "pathway", PhotosynthesisPathway.C3, "tempautumn", 17, "ifsdtemp", 1, "phengdd5ramp", 500, "ifsdprec", 0, "hiopt", 0.3, "phu", 2500, "hlimitdatenh", 24, "psens", 1, "trg", 12, "hlimitdatesh", 60, "himin", 0.15, "ifsdspring", 1, "ifsdcalc", 1);
        add("CC3G", "CC3G");
        add("CC4G", "CC4G");
        add("TeWW", "TeWW");
        add("TeWW_Nlim", "TeWW", "photo", new double[] {
                9.5,
                0.34,
                0.0 }, "T_rep_max", 40.0, "b1", 0.00, "b2", 0.09, "b3", 1.00, "T_vn_min", -1.3, "d2", 0.65, "d3", 1.15, "d1", 0.55, "T_veg_min", 0.0, "T_veg_max", 35.0, "cton_leaf_min", 7.0, "N_appfert", 0.01, "a1", 0.53, "dev_rate_veg", 0.03, "a3", 0.00, "a2", 0.88, "c3", 8.32, "c2", 13.99, "c1", 7.63, "fertrate", new double[] {
                        0.4,
                        0.5 }, "T_rep_opt", 29.0, "T_vn_max", 15., "T_vn_opt", 4.9, "T_veg_opt", 24.0, "fertdates", new double[] {
                                0,
                                30 }, "T_rep_min", 8.0, "nlim", 1, "dev_rate_rep", 0.042, "sla", 45);
        add("TrRi", "TrRi");
        add("TeCo", "TeCo");
        add("TeCo_Nlim", "TeCo", "photo", new double[] {
                12.0,
                0.02619,
                1.0 }, "T_rep_max", 47.0, "b1", 1.22, "b2", -0.06, "b3", 1.00, "T_vn_min", 0, "d2", 0.81, "d3", 1.03, "d1", 1.12, "T_veg_min", 10.0, "T_veg_max", 47.0, "cton_leaf_min", 10.0, "N_appfert", 0.005, "a1", 0.24, "dev_rate_veg", 0.0265, "a3", 0.00, "a2", 0.68, "c3", 28.52, "c2", 12.45, "c1", 18.10, "fertrate", new double[] {
                        0.4,
                        0.5 }, "T_rep_opt", 28.0, "T_vn_max", 0, "T_vn_opt", 0, "T_veg_opt", 28.0, "fertdates", new double[] {
                                0,
                                30 }, "T_rep_min", 10.0, "nlim", 1, "dev_rate_rep", 0.017, "sla", 43.2);
        add("TrMi", "TrMi");
        add("TePu", "TePu");
        add("TeSb", "TeSb");
        add("TrMa", "TrMa");
        add("TeSf", "TeSf");
        add("TeSo", "TeSo");
        add("TrPe", "TrPe");
        add("TeRa", "TeRa");
        add("CC3Girr", "CC3G");
        add("CC4Girr", "CC4G");
        add("TeWWirr", "TeWW");
        add("TrRiirr", "TrRi");
        add("TrRiirr2", "TrRi");
        add("TeCoirr", "TeCo");
        add("TrMiirr", "TrMi");
        add("TePuirr", "TePu");
        add("TeSbirr", "TeSb");
        add("TrMairr", "TrMa");
        add("TeSfirr", "TeSf");
        add("TeSoirr", "TeSo");
        add("TrPeirr", "TrPe");
        add("TeRairr", "TeRa");
        add("CC3G_ic", "C3G", "leaflong", 1, "res_outtake", 0.0, "laimax", 4, "isintercropgrass", 1, "harv_eff_ic", 0.0, "landcover", "cropland", "harv_eff", 0.0);
        add("CC4G_ic", "C4G", "leaflong", 1, "res_outtake", 0.0, "laimax", 4, "isintercropgrass", 1, "harv_eff_ic", 0.0, "landcover", "cropland", "harv_eff", 0.0);
    }

    /**
     * Add a PFT by providing key/value pairs. Data are expected to be pairs
     * unless the object is a PFT name, which in that case is merged with the
     * definition before continuing.
     * 
     * @param name
     * @param data
     */
    public static void add(String name, Object... data) {

        PFTDef pft = new PFTDef();

        for (int i = 0; i < data.length; i++) {

            Object o = data[i];

            if (o instanceof String && _types.containsKey(o.toString())) {
                pft.putAll(_types.get(o.toString()));
            } else {
                pft.put(o.toString(), data[++i]);
            }
        }

        _types.put(name, pft);

    }

}
