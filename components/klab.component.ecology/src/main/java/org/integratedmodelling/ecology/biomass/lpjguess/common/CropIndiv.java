package org.integratedmodelling.ecology.biomass.lpjguess.common;

public class CropIndiv {

	//Plant carbon biomass variables are all on patch area basis (kgC/m2)

	/// year's harvestable organ C biomass (= ycmass_plant)
	public double cmass_ho;
	/// above-ground pool C biomass (when calculating daily cmass_leaf from lai_crop) (= ycmass_agpool)
	public double cmass_agpool;
	public double cmass_stem;

	/// year's maximum value of leaf C biomass
	public double cmass_leaf_max;
	/// grs_cmass_leaf value saved at day before senescence (for LAI-calculation in allometry)
	public double cmass_leaf_sen;

	/// today's increase of whole plant C biomass
	public double dcmass_plant;
	/// today's increase of leaf C biomass
	public double dcmass_leaf;
	/// today's increase of root C biomass
	public double dcmass_root;
	/// today's increase of harvestable organ C biomass
	public double dcmass_ho;
	/// today's increase of above-ground pool C biomass
	public double dcmass_agpool;
	public double dcmass_stem;

	/// today's increase of leaf N biomass
	public double dnmass_leaf;
	/// today's increase of root N biomass
	public double dnmass_root;
	/// today's increase of harvestable organ N biomass
	public double dnmass_ho;
	/// today's increase of above-ground pool N biomass
	public double dnmass_agpool;

	///CARBON
	/// daily updated whole plant C biomass, reset at harvest day
	public double grs_cmass_plant;
	/// daily updated leaf C biomass, reset at harvest day
	public double grs_cmass_leaf;
	/// daily updated root C biomass, reset at harvest day
	public double grs_cmass_root;
	/// daily updated harvestable organ C biomass, reset at harvest day
	public double grs_cmass_ho;
	/// daily updated above-ground pool C biomass, reset at harvest day
	public double grs_cmass_agpool;
	public double grs_cmass_dead_leaf;
	public double grs_cmass_stem;

	/// carbon content of harvestable organs saved on first day of land use change year
	public double grs_cmass_leaf_luc;
	/// carbon content of harvestable organs saved on first day of land use change year
	public double grs_cmass_root_luc;
	/// carbon content of harvestable organs saved on first day of land use change year
	public double grs_cmass_ho_luc;
	/// carbon content of above-ground pool saved on first day of land use change year
	public double grs_cmass_agpool_luc;
	public double grs_cmass_dead_leaf_luc;
	public double grs_cmass_stem_luc;

	/// daily updated whole plant C biomass, reset at day 0
	public double ycmass_plant;
	/// daily updated leaf C biomass, reset at day 0
	public double ycmass_leaf;
	/// daily updated root C biomass, reset at day 0
	public double ycmass_root;
	/// daily updated harvestable organ C biomass, reset at day 0
	public double ycmass_ho;
	/// daily updated above-ground pool C biomass, reset at day 0
	public double ycmass_agpool;
	public double ycmass_dead_leaf;
	public double ycmass_stem;

	/// year's whole plant C biomass at time of harvest (cumulative if several harvest events)
	public double harv_cmass_plant;
	/// year's leaf C biomass at time of harvest (cumulative if several harvest events)
	public double harv_cmass_leaf;
	/// year's root C biomass at time of harvest (cumulative if several harvest events)
	public double harv_cmass_root;
	/// year's harvestable organ C biomass at time of harvest (cumulative if several harvest events)
	public double harv_cmass_ho;
	/// year's above-ground pool C biomass at time of harvest (cumulative if several harvest events)
	public double harv_cmass_agpool;
	public double harv_cmass_stem;

	///NITROGEN
	/// nitrogen content of harvestable organs
	public double nmass_ho;
	/// nitrogen content of above-ground pool
	public double nmass_agpool;
	public double nmass_dead_leaf;

	/// nitrogen content of harvestable organs saved on first day of land use change year
	public double nmass_ho_luc;
	/// nitrogen content of above-ground pool saved on first day of land use change year
	public double nmass_agpool_luc;
	public double nmass_dead_leaf_luc;

	/// daily updated leaf N biomass, reset at day 0		// These are not used
	public double ynmass_leaf;
	/// daily updated root N biomass, reset at day 0
	public double ynmass_root;
	/// daily updated harvestable organ N biomass, reset at day 0
	public double ynmass_ho;
	/// daily updated above-ground pool N biomass, reset at day 0
	public double ynmass_agpool;

	public double ynmass_dead_leaf;

	/// year's leaf N biomass at time of harvest (cumulative if several harvest events)
	public double harv_nmass_leaf;
	/// year's root N biomass at time of harvest (cumulative if several harvest events)
	public double harv_nmass_root;
	/// year's harvestable organ N biomass at time of harvest (cumulative if several harvest events)
	public double harv_nmass_ho;
	/// year's above-ground pool N biomass at time of harvest (cumulative if several harvest events)
	public double harv_nmass_agpool;

	/// dry weight crop yield harvested this year (cumulative if several harvest events), based on harv_cmass_xx
	public double harv_yield;

	/// harvestable organ C biomass at the last two harvest events this year
	public double cmass_ho_harvest[] = new double[2];
	/// harvestable organ N biomass at the last two harvest events this year
	public double nmass_ho_harvest[] = new double[2];
	/// dry weight crop yield at the last two harvest events this year
	public double yield_harvest[] = new double[2];	

	/// dry weight crop yield grown this year (cumulative if several harvest events), based on ycmass_xx
	public double yield;				

	/// whether this pft is the main crop in the stand (pft.id==stand.pftid)
	public boolean isprimarycrop;
	/// whether this pft is allowed to compete with the main crop during the same growing period (for future use)
	public boolean isprimarycovegetation;
	/// whether this pft is grown during a second growing period, different from the primary (main) crop (for future use)
//	public boolean issecondarycrop;

	/// set to true if pft.isintercropgrass is true and the stand's main crop pft.intercrop is "naturalgrass"
	public boolean isintercropgrass;

	public CropIndiv() {
		cmass_ho=0.0;
		cmass_agpool=0.0;
		cmass_stem = 0.0;
		cmass_leaf_max=0.0;
		cmass_leaf_sen=0.0;
		yield=0.0;
		yield_harvest[0]=0.0;
		yield_harvest[1]=0.0;
		dcmass_leaf=0.0;
		dcmass_root=0.0;
		dcmass_plant=0.0;
		dcmass_ho=0.0;
		dcmass_agpool=0.0;
		grs_cmass_leaf=0.0;
		grs_cmass_root=0.0;
		grs_cmass_plant=0.0;
		grs_cmass_ho=0.0;
		grs_cmass_agpool=0.0;
		grs_cmass_stem = 0.0;
		grs_cmass_dead_leaf = 0.0;
		grs_cmass_leaf_luc=0.0;
		grs_cmass_root_luc=0.0;
		grs_cmass_ho_luc=0.0;
		grs_cmass_agpool_luc=0.0;
		grs_cmass_dead_leaf_luc = 0.0;
		grs_cmass_stem_luc = 0.0;
		nmass_ho=0.0;
		nmass_agpool=0.0;
		nmass_dead_leaf = 0.0;
		ycmass_leaf=0.0;
		ycmass_root=0.0;
		ycmass_plant=0.0;
		ycmass_ho=0.0;
		ycmass_agpool=0.0;
		ycmass_stem = 0.0;
		ycmass_dead_leaf = 0.0;
		harv_cmass_leaf=0.0;
		harv_cmass_root=0.0;
		harv_cmass_root=0.0;
		harv_cmass_ho=0.0;
		harv_yield=0.0;
		harv_cmass_agpool=0.0;
		harv_cmass_stem = 0.0;
		cmass_ho_harvest[0]=0.0;
		cmass_ho_harvest[1]=0.0;

		//Nitrogen
		dnmass_leaf=0.0;
		dnmass_root=0.0;
		dnmass_ho=0.0;
		dnmass_agpool=0.0;
		ynmass_leaf=0.0;
		ynmass_root=0.0;
		ynmass_ho=0.0;
		ynmass_agpool=0.0;
		ynmass_dead_leaf = 0.0;
		harv_nmass_leaf=0.0;
		harv_nmass_root=0.0;
		harv_nmass_root=0.0;
		harv_nmass_ho=0.0;
		harv_nmass_agpool=0.0;
		nmass_dead_leaf_luc = 0.0;
		nmass_ho_harvest[0]=0.0;
		nmass_ho_harvest[1]=0.0;

		isprimarycrop=false;
		isprimarycovegetation=false;
//		issecondarycrop=false;
		isintercropgrass=false;
	}
}
