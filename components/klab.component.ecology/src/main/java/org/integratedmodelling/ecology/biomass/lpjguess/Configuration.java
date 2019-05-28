package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.ecology.biomass.lpjguess.common.PFTFactory;
import org.integratedmodelling.ecology.biomass.lpjguess.common.STFactory;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.ISchedule;

/**
 * Options reflect global.ins in the original archive. Generates some stand and
 * climates and runs a minimal simulation - only used to check for obvious bugs.
 *
 * @author Ferd
 *
 */
public class Configuration implements IConfiguration {

	static Map<String, LandcoverType> globCoverIndex = new HashMap<>();
	
	static {
		/*
		 * TODO check all this. Seems like a no-brainer but flooded agriculture and
		 * stuff like that may hide subtle caveats.
		 * 
		 * Some GC classes are left out - if the translation returns null, it's where
		 * LPJ shouldn't run.
		 * 
		 * Peatlands are completely absent - we should use an independent presence
		 * observation and check that first.
		 */
        globCoverIndex.put("IrrigatedOrFloodedCropland", LandcoverType.CROP);
        globCoverIndex.put("RainfedCropland", LandcoverType.CROP);
        globCoverIndex.put("MosaicCropland", LandcoverType.CROP);
        globCoverIndex.put("MosaicVegetation", LandcoverType.NATURAL);
        globCoverIndex.put("ClosedEvergreenOrSemiDeciduousForest", LandcoverType.NATURAL);
        globCoverIndex.put("ClosedBroadleavedDeciduousForest", LandcoverType.NATURAL);
        globCoverIndex.put("OpenBroadleavedDeciduousForest", LandcoverType.NATURAL);
        globCoverIndex.put("ClosedNeedleleavedEvergreenForest", LandcoverType.NATURAL);
        globCoverIndex.put("OpenNeedleleavedDeciduousOrEvergreenForest", LandcoverType.NATURAL);
        globCoverIndex.put("MixedBroadleavedAndNeedleleavedForest", LandcoverType.NATURAL);
        globCoverIndex.put("MosaicForestShrublandWithGrassland", LandcoverType.NATURAL);
        globCoverIndex.put("MosaicGrasslandWithForestShrubland", LandcoverType.NATURAL);
        globCoverIndex.put("Shrubland", LandcoverType.NATURAL);
        globCoverIndex.put("Grassland", LandcoverType.NATURAL);
        globCoverIndex.put("SparseVegetation", LandcoverType.NATURAL);
        globCoverIndex.put("ClosedFloodedForestFreshwater", LandcoverType.NATURAL);
        globCoverIndex.put("ClosedFloodedForestSalineWater", LandcoverType.NATURAL);
        globCoverIndex.put("ClosedFloodedVegetation", LandcoverType.NATURAL);
        globCoverIndex.put("ArtificialSurfaces", LandcoverType.BARREN);
        globCoverIndex.put("BareAreas", LandcoverType.BARREN);
        globCoverIndex.put("PermanentSnowAndIce", LandcoverType.BARREN);
	}
	
//    ISchedule schedule = new Schedule();

    List<PFT> PFTs = new ArrayList<PFT>();

    List<StandType> STs = new ArrayList<StandType>();

    // Vegetation 'mode', i.e. what each Individual (see below) object
    // represents;
    // either: (1) the average characteristics of all individuals comprising a
    // PFT
    // population over the modelled area (standard LPJ mode); (2) a cohort of
    // individuals of a PFT that are roughly the same age; (3) an individual
    // plant.
    VegetationMode vegmode = VegetationMode.POPULATION;

    boolean isnlim_lc[] = { true, true, true, true, true, true, true };

    private int npatch        = 1;     // number of patches in each stand (should always be 1 in
    // population mode)
    // double patcharea = 1000.0; // patch area (m2) (individual and cohort mode
    // only)
    boolean     ifdailynpp    = false; // whether NPP calculations performed daily
    // (alt: monthly)
    boolean     ifdailydecomp = false;
    // whether soil decomposition calculations performed daily (alt: monthly)
    boolean     ifbgestab     = true;  // whether background establishment enabled
    // (individual, cohort mode)
    boolean     ifsme         = true;
    // whether spatial mass effect enabled for establishment (individual, cohort
    // mode)
    boolean     ifstochestab  = true;  // whether establishment stochastic
    // (individual, cohort mode)
    boolean     ifstochmort   = true;  // whether mortality stochastic (individual,
    // cohort mode)
    boolean     iffire        = true;  // whether fire enabled
    boolean     ifdisturb     = true;
    // whether "generic" patch-destroying disturbance enabled (individual,
    // cohort mode)
    boolean     ifcalcsla     = true;  // whether SLA calculated from leaf longevity
    // (alt: prescribed)
    int         estinterval   = 5;     // establishment interval in cohort mode (years)
    double      distinterval  = 100;
    // generic patch-destroying disturbance interval (individual, cohort mode)
    int         npft;                  // number of possible PFTs
    boolean     iffast        = false; // (??)
    boolean     ifcdebt       = true;

    // guess2008 - new inputs from the .ins file
    boolean ifsmoothgreffmort            = true;  // smooth growth efficiency mortality
    boolean ifdroughtlimitedestab        = false; // whether establishment affected by
    // growing season drought
    boolean ifrainonwetdaysonly          = true;  // rain on wet days only (1, true), or a
    // little every day (0, false);
    boolean ifspeciesspecificwateruptake = false; // water uptake is species

    int nst_lc[] = new int[LandcoverType.values().length];

    // specific

    public Configuration(VegetationMode vegMode) {

        this.vegmode = vegMode;

        switch (vegMode) {
        case COHORT:
            npatch = 5;
            break;
        case INDIVIDUAL:
            break;
        case NOVEGMODE:
            break;
        case POPULATION:
            npatch = 1;
            break;
        default:
            break;
        }

        PFTs = PFTFactory.getAllPFTs();
        STs = STFactory.getAllSTs();

        int nst = 0;
        for (StandType st : STs) {
            st.id = nst++;
            if (st.landcover != null) {
                nst_lc[st.landcover.ordinal()]++;
            }
        }
    }

    @Override
    public VegetationMode getVegmode() {
        return vegmode;
    }

    @Override
    public boolean isNPPDaily() {
        return ifdailynpp;
    }

    @Override
    public boolean isDecompositionDaily() {
        return ifdailydecomp;
    }

    @Override
    public boolean isBackgroundEstablishmentEnabled() {
        return ifbgestab;
    }

    @Override
    public boolean isSpatialMassEffectEnabled() {
        return ifsme;
    }

    @Override
    public boolean isEstablishmentStochastic() {
        return ifstochestab;
    }

    @Override
    public boolean isMortalityStochastic() {
        return ifstochmort;
    }

    @Override
    public boolean isFireEnabled() {
        return iffire;
    }

    @Override
    public boolean isDisturbanceEnabled() {
        return ifdisturb;
    }

    @Override
    public boolean isSLAComputed() {
        return ifcalcsla;
    }

    @Override
    public int getEstablishmentInterval() {
        return estinterval;
    }

    @Override
    public double getDisturbanceInterval() {
        return distinterval;
    }

    @Override
    public boolean isIffast() {
        return iffast;
    }

    @Override
    public boolean isIfcdebt() {
        return ifcdebt;
    }

    @Override
    public boolean isGrowthEfficiencySmoothed() {
        return ifsmoothgreffmort;
    }

    @Override
    public boolean isEstablishmentDroughtLimited() {
        return ifdroughtlimitedestab;
    }

    @Override
    public boolean isIfrainonwetdaysonly() {
        return ifrainonwetdaysonly;
    }

    @Override
    public boolean isIfspeciesspecificwateruptake() {
        return ifspeciesspecificwateruptake;
    }

    @Override
    public ISchedule getSchedule() {
        return null; // schedule;
    }

    @Override
    public List<PFT> getPFTs() {
        return PFTs;
    }

    @Override
    public List<StandType> getSTs() {
        return STs;
    }

    @Override
    public DemandPatchType getDemandPatchType() {
        return DemandPatchType.DEMAND_PATCH;
    }

    @Override
    public AETMonteithType getAETMonteithType() {
        return AETMonteithType.HYPERBOLIC;
    }

    @Override
    public WaterUptakeType getWaterUptakeType() {
        return WaterUptakeType.ROOTDIST;
    }

    @Override
    public int getNpatch() {
        return npatch;
    }

    @Override
    public void setNpatch(int npatch) {
        this.npatch = npatch;
    }

    @Override
    public PFT getPFTByName(String name) {
        List<PFT> pfts = this.getPFTs();
        PFT result = null;
        for (PFT p : pfts) {
            if (p.name == name) {
                result = p;
                break;
            }
        }

        return result;
    }

    @Override
    public int getNStandTypes() {
        return this.STs.size();
    }

    @Override
    public boolean isNLimitedLC(LandcoverType lc) {
        return isnlim_lc[lc.ordinal()];
    }

    @Override
    public double getNRelocFrac() {
        return 0.5;
    }
    
    /**
     * Return the LPJ land cover type correspondent to the passed GlobCover 
     * concept. If the return type is null, LPJ shouldn't run there. Peatlands
     * are not addressed as GlobCover (like all other LC global datasets) does
     * not cover them - they should be "burned in" before checking this.
     * 
     * @param globcoverType
     * @return
     */
    public static LandcoverType getLPJLandcover(IConcept globcoverType) {
    	return null; // globcoverType == null ? null : globCoverIndex.get(globcoverType.getLocalName());
    }
    
}
