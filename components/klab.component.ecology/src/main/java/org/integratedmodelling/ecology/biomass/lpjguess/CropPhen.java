package org.integratedmodelling.ecology.biomass.lpjguess;

public class CropPhen {

	public CropPhen() {
		sdate=-1;
		sdate_harv=-1;
		nsow=0;
		sownlastyear=false;	
		sendate=-1;		
		hdate=-1;				
		hlimitdate=-1;
		hucountend=-1;
		nharv=0;
		tb=0.0;
		pvd=0;
		vdsum=0;
		vrf=1.0;
		phu=0.0;
		phu_old=0.0;
		husum_max=0.0;
		husum_sampled=0.0;
		husum_max_10=0.0;
		nyears_hu_sample = 0;
		prf=1.0;
		husum=0.0;
		fphu=0.0;
		fphu_harv=0.0;
		hu_samplingdays=0;
		hu_samplingperiod=false;

		hi=0.0;
		hi_ystd=0.0;
		fhi=0.0;
		fhi_phen=0.0;
		fhi_water=1.0;
		fhi_harv=0.0;
		demandsum_crop=0.0;
		supplysum_crop=0.0;

		growingseason=false;	//Initialized to true for normal grass growth (CC3G & CC4G) in establishment
		growingseason_ystd=false;
		senescence=false;
		senescence_ystd=false;
		intercropseason=false;
		bicdate=-1;
		eicdate=-1;
		growingdays=0;
		growingdays_y=0;
		lgp=0;

		for(int j=0;j<2;j++)			
		{
			sdate_harvest[j]=-1;	
			hdate_harvest[j]=-1;
			sdate_thisyear[j]=-1;		
//			fhi_harvest[j]=-1.0;		
//			fphu_harvest[j]=-1.0;		
		}

		vdsum_alloc=0.0;
		vd = 0.0;
		f_alloc_root=0.0;
		f_alloc_leaf=0.0;
		f_alloc_horg=0.0;
		f_alloc_stem=0.0;
		dev_stage = 0.0;

		sen_day=0.0;
		sen_day_old=0.0;
		sen_nr_days=0.0;
		sen_start=0.0;
		fertilised[0] = false;
		fertilised[1] = false;
		fertilised[2] = false;
	}
	
	/// latest sowing date
	public int sdate;
	/// sowing date of growing period ending in latest harvest this year
	public int sdate_harv;
	/// sowing dates of growing periods ending in the two latest harvests this year
	public int sdate_harvest[] = new int [2];
	/// sowing dates of growing periods starting this year
	public int sdate_thisyear[] = new int[2];
	/// number of sowings this year
	public int nsow;
	/// latest harvest date
	public int hdate;
	/// two latest harvest dates this year
	public int hdate_harvest[] = new int[2];
	/// last date for harvest
	public int hlimitdate;
	/// last day of heat unit sampling period, set in Crop_sowing_date_new()
	public int hucountend;
	/// number of harvests this year
	public int nharv;
	/// whether sdate_harvest[0] happened last year
	public Boolean sownlastyear;
	/// latest senescence start date this year
	public int sendate;
	/// latest beginning of intercropseason (2 weeks after the harvest date)
	public int bicdate;
	/// latest end of intercropseason (2 weeks before the sowing date)
	public int eicdate;
	/// number of growing days this growing period
	public int growingdays;
	/// number of growing days this year (used for wscal_mean calculation)
	public int growingdays_y;
	/// length of growingseason ending in last harvest
	public int lgp;
	/// base temp for heat unit calculation (°C)
	public double tb;
	/// number of vernalising days required
	public int pvd;
	/// number of accumulated vernalizing days
	public int vdsum;
	/// heat unit reduction factor due to vernalization [0-1]
	public double vrf;
	/// heat unit reduction factor due to photoperiodism [0-1]
	public double prf;
	/// potential heat units required for crop maturity (°Cd)
	public double phu;
	/// potential heat units that would have been used without dynamic phu calculation
	public double phu_old;
	/// heat unit sum aquired during last growing period (°Cd)
	public double husum;
	/// heat unit sum aquired durin sampling period, starting with sdate
	public double husum_sampled;
	/// this year's heat unit sum aquired from sdate to hucountend
	public double husum_max;
	/// running mean of recent past's husum_max
	public double husum_max_10;
	/// number of heat units sampling years
	public int nyears_hu_sample;
	/// fraction of growing season [0-1] (husum/phu)
	public double fphu;
	/// fraction of growing season at latest harvest
	public double fphu_harv;
	/// fraction of growing season at the two latest harvests this year
//		double fphu_harvest[2];	
	public Boolean hu_samplingperiod;
	public int hu_samplingdays;
	/// harvest index today [0-1, >1 if below-ground ho], harvestable organ/above-ground C for above-ground harvestable organs, dependent on fphu, reduced by water stress 
	public double hi;
	/// harvest index yesterday
	public double hi_ystd;
	/// fraction of harvest index today
	public double fhi;
	/// phenology (fphu) contribution of fraction of harvest index today
	public double fhi_phen;	//Phenology (fPHU) compoment of fhi
	/// water stress contribution of fraction of harvest index today
	public double fhi_water;
	/// fraction of harvest index at latest harvest
	public double fhi_harv;
	/// acheived fraction of harvest index at the two latest harvests this year
//		double fhi_harvest[2];
	/// sum of crop patch demand (patch.wdemand) during crop growing period, reset on harvest day
	public double demandsum_crop;
	/// sum of crop supply (patchpft.wsupply) during crop growing period, reset on harvest day
	public double supplysum_crop;

	/// whether inside crop/intercrop grass growing period
	public Boolean growingseason;
	/// whether yesterday was inside crop/intercrop grass growing period
	public Boolean growingseason_ystd;
	/// whether inside crop senescence
	public Boolean senescence;
	/// whether yesterday was inside crop senescence
	public Boolean senescence_ystd;
	/// whether inside intercrop crass growing period (main crop pft variable)
	public Boolean intercropseason;

	public double vdsum_alloc;
	public double vd;

	public double f_alloc_root;
	public double f_alloc_leaf;
	public double f_alloc_horg;
	public double f_alloc_stem;
	public double dev_stage; //development stage, w&e

	public double sen_day;
	public double sen_nr_days;
	public double sen_start;
	public double sen_day_old;

	public Boolean fertilised[] = new Boolean[3];

}
