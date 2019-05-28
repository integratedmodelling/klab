package org.integratedmodelling.ecology.biomass.lpjguess;

public enum PerPatchFluxType {
	/// Carbon flux to atmosphere from burnt vegetation and litter (kgC/m2)
	FIREC,
	/// Carbon flux to atmosphere from soil respiration (kgC/m2)
	SOILC,
	/// Flux from atmosphere to vegetation associated with establishment (kgC/m2)
	ESTC,
	/// Flux to atmosphere from consumed harvested products (kgC/m2)
	HARVESTC,
	/// Flux from atmosphere to vegetation associated with sowing (kgC/m2)
	SEEDC,
	/// Nitrogen flux to atmosphere from consumed harvested products (kgN/m2)
	HARVESTN,
	/// Nitrogen flux from atmosphere to vegetation associated with sowing (kgC/m2)
	SEEDN,
	/// NH3 flux to atmosphere from fire
	NH3_FIRE,
	/// NO flux to atmosphere from fire	
	NO_FIRE,
	/// NO2 flux to atmosphere from fire
	NO2_FIRE,
	/// N2O flux to atmosphere from fire	
	N2O_FIRE,
	/// N2 flux to atmosphere from fire	
	N2_FIRE,
	/// N flux from soil
	N_SOIL,
	/// Reproduction costs
	REPRC,
	/// Number of types, must be last
	NPERPATCHFLUXTYPES
}
