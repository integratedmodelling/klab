package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.ecology.biomass.lpjguess.common.Output;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;
import org.integratedmodelling.procsim.api.IModelObject;

///////////////////////////////////////////////////////////////////////////////////////
//STAND
//Stores data for a stand, corresponding to a modelled locality or grid cell.
//Member variables include an object of type Climate (holding climate, insolation and
//CO2 data), a object of type Soiltype (holding soil static parameters) and a list
//array of Patch objects. Soil objects (holding soil state variables) are associated
//with patches, not stands. A separate Stand object must be declared for each modelled
//locality or grid cell.

public class Stand implements IModelObject {

    /// A number identifying this Stand within the grid cell
    public int id;

    /// stand type id
    public int stid;

    /// pft id of main crop, updated during rotation
    public int pftid;

    /// current crop rotation item
    public int current_rot;

    public int ndays_inrotation;

    public Boolean infallow;

    /// whether crop rotation item is to be updated today
    public Boolean isrotationday;

    // true if current crop management hydrology == irrigated, updated during rotation
    public Boolean isirrigated;

    /// true if the stand's main crop pft intercrop==naturalgrass and a pft with isintercrop==true is in the
    /// pftlist.
    public Boolean hasgrassintercrop;

    /// gdd5-value at first intercrop grass growth
    public double gdd0_intercrop;

    /// old fraction of this stand relative to the gridcell before update
    public double frac_old;

    /// used during land cover change involving several calls to reveiving_stand_change()
    /** Set to frac_old in reduce_stands(), then modified in donor_stand_change() and receiving_stand_change().
     */
    public double frac_temp;

    public double protected_frac;

    /// net stand fraction change
    public double frac_change;

    public double gross_frac_increase;
    public double gross_frac_decrease;

    public double cloned_fraction;

    public Boolean cloned;

    public double[]      transfer_area_st;
    public LandcoverType origin;

    /// counter used for output from separate stands
    public double anpp;
    public double cmass;

    public List<Patch> patches = new ArrayList<>();

    /// Seed for generating random numbers within this Stand
    /** The reason why Stand has its own seed, rather than using for instance
     *  a single global seed is to make it easier to compare results when using
     *  different land cover types.
     *
     *  Randomness not associated with a specific stand, but rather a whole
     *  grid cell should instead use the seed in the Gridcell class.
     *
     *  \see randfrac()
     */
    public long seed;

    /// type of landcover
    /** \see landcovertype
     *  initialised in constructor
     */
    public LandcoverType landcover;

    /// The year when this stand was created.
    /** Will typically be year zero unless running with dynamic
     *  land cover.
     *
     *  Needed to set patchpft.anetps_ff_est_initial
     */
    public int first_year;

    public int clone_year;

    /// scaling factor for stands that have grown in area this year (old fraction/new fraction)
    public double scale_LC_change;

    public List<StandPFT> pft = new ArrayList<StandPFT>();

    public Output output;

    public IConfiguration _configuration;

    private Gridcell _gridcell;

    public int npatch;

    double frac;

    private Soiltype soiltype;

    /*
     * FIXME all this makes no sense here - this is a configuration thing.
     */
    public Stand(Gridcell gridcell, IConfiguration _configuration, List<PFT> pftlist, Soiltype soiltype,
            LandcoverType lc, int no_patch) {
        // Constructor: initialises reference member of climate and
        // builds list array of Standpft objects

        this.output = new Output(this, _configuration);
        this._gridcell = gridcell;
        this._configuration = _configuration;
        this.soiltype = soiltype;

        int ip = 0;
        for (PFT ppft : pftlist) {
            pft.add(new StandPFT(ip++, ppft));
        }

        // int p;
        int npatchL = 1;

        if (landcover == LandcoverType.CROP || landcover == LandcoverType.URBAN
                || landcover == LandcoverType.PEATLAND || landcover == LandcoverType.BARREN) {
            npatchL = 1;
        } else /* only natural and forest? wouldn't it be easier to check for that? */ {
            npatchL = _configuration.getNpatch();
        }

        if (no_patch > 0) {
            npatchL = no_patch;
        }

        // Create patches
        for (int i = 0; i < npatchL; i++) {
        	// FIXME use the PFTs from the gridcell (those actually used in it)
            patches.add(new Patch(this, this._configuration.getPFTs(), getGridcell().soiltype));
        }

        first_year = 0;
        clone_year = -1;
        transfer_area_st = new double[_configuration.getNStandTypes()];
        seed = 12345678;

        stid = -1;
        pftid = -1;
        current_rot = 0;
        ndays_inrotation = 0;
        infallow = false;
        isrotationday = false;
        isirrigated = false;
        hasgrassintercrop = false;
        gdd0_intercrop = 0.0;
        frac = 1.0;
        frac_old = 0.0;
        frac_temp = 0.0;
        protected_frac = 0.0;
        frac_change = 0.0;
        gross_frac_increase = 0.0;
        gross_frac_decrease = 0.0;
        cloned_fraction = 0.0;
        cloned = false;
        anpp = 0.0;
        cmass = 0.0;
        scale_LC_change = 1.0;
    }

    public Gridcell getGridcell() {
        return _gridcell;
    }

    public Climate getClimate() {
        return getGridcell().climate;
    }

    public int npatch() {
        return patches.size();
    }

    public List<Patch> getPatches() {
        return patches;
    }

    public Boolean is_true_crop_stand() {
        return landcover == LandcoverType.CROP && pft.get(pftid).pFT.phenology == Phenology.CROPGREEN;
    }

    public Boolean ifnlim_stand() {
        // return ifnlim && ifnlim_lc[landcover];
        // TODO: Sort this out!
        return true;
    }

    @Override
    public String toString() {
        // TODO finish
        return "STAND/" + patches.size() + "/";
    }

    public double get_gridcell_fraction() {
        return frac;
    }

    public void set_gridcell_fraction(double fraction) {
        frac = fraction;
    }

    @Override
    public IConfiguration getConfiguration() {
        return _configuration;
    }

    public StandPFT getPFT(int id) {
        return pft.get(id);
    }

    public void init_stand_lu(StandType st, double fraction) {

        // int error = 0;
        LandcoverType lc = st.landcover;
        landcover = lc;

        stid = st.id;
        set_gridcell_fraction(fraction);
        frac_old = 0.0;
        frac_change = fraction;
        gross_frac_increase = fraction;

        List<PFT> pftlist = _configuration.getPFTs();

        for (PFT pftx : pftlist) {

            if (!st.restrictpfts && pftx.landcover == lc
                    || st.naturalveg == NaturalVeg.ALL && pftx.landcover == LandcoverType.NATURAL
                    || st.naturalgrass && pftx.landcover == LandcoverType.NATURAL
                            && pftx.lifeform == LifeForm.GRASS) {

                pft.get(pftx.id).active = true;
            } else {
                pft.get(pftx.id).active = false;
            }
        }

        /*
         * FIXME this should just be an exception, but let's allow it for now, reverting to natural.
         */
        if (lc == LandcoverType.CROP && !st.isValid()) {
            Logging.INSTANCE.warn("configuration of crop type is invalid: reverting crop stand " + id
                    + " to natural land cover type");
            lc = LandcoverType.NATURAL;
            st.landcover = LandcoverType.NATURAL;
        }

        if (lc == LandcoverType.CROP) {

            pftid = _configuration.getPFTByName(st.management.get(0).pftname).id; // First main crop, will
                                                                                  // change
            // during crop rotation
            current_rot = 0;

            if (st.intercrop == IntercropType.NATURALGRASS && _configuration.ifintercropgrass) {
                hasgrassintercrop = true;

                for (PFT p : pftlist) {
                    if (p.isintercropgrass)
                        pft.get(p.id).active = true;
                }
            }

            // Set standpft- and patchpft-variables for all active crops in all rotations
            for (int rot = 0; rot < st.rotation.ncrops; rot++) {

                int id = _configuration.getPFTByName(st.management.get(0).pftname).id;

                if (id >= 0) {
                    pft.get(id).active = true;

                    if (rot == 0) {
                        for (Patch p : patches) {
                            GridcellPFT gcpft = _gridcell.pft.get(id);
                            PatchPFT ppft = p.pft.get(id);

                            ppft.get_cropphen().sdate = gcpft.sdate_default;
                            ppft.get_cropphen().hlimitdate = gcpft.hlimitdate_default;

                            if (pftlist.get(id).phenology == Phenology.ANY)
                                ppft.get_cropphen().growingseason = true;
                            else if (pftlist.get(id).phenology == Phenology.CROPGREEN) {
                                ppft.get_cropphen().eicdate = Utils
                                        .stepfromdate(ppft.get_cropphen().sdate, -15);
                            }
                        }

                    }
                }
            }
        }
    }

    public Soiltype getSoiltype() {
        return soiltype;
    }

    public void setSoiltype(Soiltype soiltype) {
        this.soiltype = soiltype;
    }

    void rotate() {

        if (pftid >= 0 && stid >= 0) {

            ndays_inrotation = 0;

            current_rot = (current_rot + 1) % _configuration.getSTs().get(stid).rotation.ncrops;
            pftid = _configuration
                    .getPFTByName(_configuration.getSTs().get(stid).management.get(current_rot).pftname).id;

            StandPFT standpft = pft.get(pftid);

            if (_configuration.getSTs()
                    .get(stid).management.get(current_rot).hydrology == HydrologyType.IRRIGATED) {
                isirrigated = true;
                standpft.irrigated = true;
            } else {
                isirrigated = false;
                standpft.irrigated = false;
            }

            // TODO: Make configurable
            boolean readsowingdates = true;
            boolean readharvestdates = true;

            if (!readsowingdates)
                standpft.sdate_force = _configuration.getSTs().get(stid).management.get(current_rot).sdate;
            if (!readharvestdates)
                standpft.hdate_force = _configuration.getSTs().get(stid).management.get(current_rot).hdate;
        }
    }

    public void doCropRotation() {
        int year = _configuration.getSchedule().year();
        int day = _configuration.getSchedule().julianDay();

        if (landcover == LandcoverType.CROP) {

            CropRotation rotation = _configuration.getSTs().get(stid).rotation;
            boolean postpone_rotation = false;

            ndays_inrotation++;

            if (rotation.ncrops > 1 && isrotationday) {

                // int firstrotyear = rotation.firstrotyear + nyear_spinup - firsthistyear;
                int firstrotyear = rotation.firstrotyear;

                // Alternative uses of firstrotyear:
                /*			// 1. Before firstrotyear, grow only crop1:
                			if(date.year < firstrotyear)
                				postpone_rotation = true;
                */
                // 2. Synchronise rotation with firstrotyear:

                // A. At the creation of the stand:
                if (year < first_year + 3)
                // B. At firstrotyear
                // if(date.year == firstrotyear - 1)
                // C. Continuously:
                {
                    if ((Math.abs(firstrotyear - year) % rotation.ncrops) != current_rot)
                        postpone_rotation = true;
                }

                if (!postpone_rotation) {

                    if (infallow) {
                        infallow = false;
                        getGridcell().pft.get(pftid).sowing_restriction = false;
                    }

                    int old_pftid = pftid;

                    rotate();

                    for (int p = 0; p < patches.size(); p++) {

                        CropPhen previous = patches.get(p).pft.get(old_pftid).get_cropphen();
                        CropPhen current = patches.get(p).pft.get(pftid).get_cropphen();

                        previous.bicdate = -1;
                        if (!previous.intercropseason)
                            current.bicdate = Utils.stepfromdate(day, 15);
                        previous.eicdate = -1;
                        current.eicdate = -1;
                        previous.hdate = -1;
                        current.intercropseason = previous.intercropseason;
                    }

                    // Adds sowing and harvest dates for the second crop in a double cropping system
                    // if((rotation.multicrop && stand.get_gridcell().pft[stand.pftid].multicrop) &&
                    // rotation.ncrops == 2 && stand.current_rot == 1) {
                    if (rotation.multicrop && rotation.ncrops == 2 && current_rot == 1) {
                        if (pft.get(pftid).sdate_force < 0)
                            pft.get(pftid).sdate_force = Utils.stepfromdate(day, 10);
                        if (pft.get(pftid).hdate_force < 0) {
                            pft.get(pftid).hdate_force = Utils
                                    .stepfromdate(pft.get(old_pftid).sdate_force, -10);
                        }
                    }

                    if (_configuration.getSTs().get(stid).management.get(current_rot).fallow) {
                        infallow = true;
                        getGridcell().pft.get(pftid).sowing_restriction = true;
                    }
                }

                isrotationday = false;
            }
        }
    }
}
