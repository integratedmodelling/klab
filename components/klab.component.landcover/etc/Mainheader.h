/*****************************************************************
//
//      	  The CLUE Modelling Framework
//
//                       Dyna_CLUE version
//                        -header file-
//
//
//                            vDyna_CLUE
//
//	                  Development:
//                        Peter Verburg
//     														 				    The CLUE group				 		 *
//			    15 nov 2009
//		           mainheader.h
//
//                           all rights:
//                          peter verburg
//
********************************************************************/


#include <windows.h>
#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <dir.h>
#include <conio.h>
#include <dos.h>
#include <fstream.h>

#define F_INIT "main.1"		/* name of file containing main input parameters */
#define F_LOG "log.fil"	       	/* name of log-file */

#define NCOV 17  	        /* number of land use/cover types */
#define NREG 50  		/* number of regions; default: 1 */
#define NFACT 40		/* maximum number of explanatory factors in one regression equation */
#define NATT 40 	        /* total number of grids with explanatory factors */
#define NR 6000		        /* number of rows in grid */
#define NC 6000		        /* number of columns in grid */
#define NYEAR 101               /* maximum number of years to be simulated */

/* typedefenitions for memory allocation */
typedef int MATELEMS;
typedef float MATFLOAT;
typedef MATELEMS *vector;
typedef MATFLOAT *fvector;

fvector *mat_at[NATT];	        /* matrix for grids with explanatory factors */
fvector *mat_co[NCOV];	        /* matrix for regression results */
vector *mat_newco;	        /* matrix with land use results */
vector *mat_oldco;		/* matrix for old land use type grid */
vector *region;		 	/* matrix for region subdivision */
vector *mat_age;                /* matrix keeping track of time without change */

int   start,			        /* start year of simulation */
      end,				/* end year of simulation */
      year,				/* year counter */
      attr[NREG][NCOV][NFACT],          /* explanatory factor numbers for region, land use type and explanatory factor number */
      attr2[NREG][NCOV][NFACT],
      no_fact[NREG][NCOV],	        /* number of explanatory factors in regression equation for region and land use type */
      no_fact2[NREG][NCOV],
      loop,				/* counter for iteration loop in allocation module */
      dem_dir[NREG][NCOV],		/* direction of demand: 1 = increase; -1 = decrease; 0 = stable; for land use types */
      scale,				/* control parameter: default value = 1 */
      arcview,				/* arcview output control parameter: 1 = arcviewheaders 0 = no arcviewheaders (idrisi etc.) */
      grchange[NFACT],		        /* array containing numbers of changing scgr (explanatory factor) files */
      nochange,				/* number of scgr files that is changing */
      nostable[NREG],			/* number of cover types of which demand does not change */
      Yearprog,
      Barpos,
      diffregregi,                      /* 1 different regions have different regressions; 0 one regression for all */
      nocells[NREG],
      allowed[NCOV][NCOV],
      probmaps,
      rncov,
      rnreg,
      rnfact,
      rnatt,
      rnr,
      rnc,
      bottomup,                         /* indicator if bottom-up modification of demand is to be accounted for */
      startage,                         /* maximum age of land use types at start of simulation (randomized) */
      agemode,                          /* binary switch: 1: use random calculation of initial age; 0: read initial age from age.0 */
      itermode,                         /* switch for iteration mode: 0: percentage difference between alloc and demand; 1: absolute difference */
      influence,                        /* switch to turn on neighborhood calculations */
      locationfactor,                   /* switch to turn on location specific additions to regression equation */
      checkfile,                        /* switch to turn on module to check driving factor files on missing data; indicator for error in these files */
      metamod,                          /* switch to turn the meta-model mode on or of */
      allowmin,                         /* provides minimum number of conversion matrix that reverse to a grid */
      allprob,                         /* swithc to turn on the option to create all probability maps in advance */
      covdem[NCOV],                     /* stores the number of the demand array to which this land use type contributes */
      maxcovdem;                        /* highest number of unique land cover type in demand specification */

double	demand[NREG][NCOV][NYEAR],    	/* demand for land use type */
        constant[NREG][NCOV],
	constant2[NREG][NCOV],	        /* constant in regression equation for scale level, region and land use type */
        cellsize,                       /* surface area of one grid cell (same units as demand) */
        areacov[NYEAR][NREG][NCOV];

float   maxdiff,		      	/* maximum difference between demand and allocation allowed at end of iteration */
	totdiff,                        /* total difference between demand and allocated land use (all cover types) */
	demdiff,			/* actual difference between demand and allocation */
	elas[NREG][NCOV],		/* iteration parameter, based upon break-off value of logistic regression */
        stat[NCOV],			/* indicator for behaviour allowed for land use type: 0 = increases and decreases always allowed, 1 = only decraese if decreasing demand, -1 = no increase if decreasing demand, 0-1 = relative staticity for competition among increasing demands; larger: more on decreasing covers*/
	fact[NREG][NCOV][NFACT],        /* beta for region, land use type and explanatory factor number */
        fact2[NREG][NCOV][NFACT],
	gridsize,			/* size of grid as used by arcview */
        speed[NREG][NCOV],
        demdiffmax,
        xllcorner,                      /* x-coordinate of lower left corner (only needed for georeferenced arcview simulations) */
        yllcorner,                      /* y-coordinate of lower left corner (only needed for georeferenced arcview simulations) */
        ratio[NCOV],                    /* ratio of land use type to total land area * */
	neighweight[NCOV],
        locationweight[NCOV],           /* fraction of location specific addition to be added to probability */
        seed;                           /* iteration variable */
FILE	*flog;				/* log-file */
char    editfile[20],
        park[25],
        demd[25];
struct  time ti;
int sorting_rule( const void* a, const void* b ){
    return **(const int**)b - **(const int**)a;}         /* sorting rule for qsort command used in void alloc_sort */

        void alloc_sort(void);
	void all_init(void);
	void load_reg(void);
	void make_mat(void);
	void load_grid(void);
	void load_region(void);
	void calc_reg(void);
	void calc_change_ch(void);
	void correct_100(void);
	void comp_demand(void);
	void write_grid(void);
	void free_mat(void);
	void scgr_change(void);
	void demand_dir(void);
	void read_cov(void);
	void set_elas(void);
	void set_oldco(void);
        void init_iter(void);
        void read_allowed(void);
        void calc_age(void);
        void calc_neigh(void);
        void load_reg2(void);
        void load_locationfactor(void);
        void check_file(void);
        void demand_read(void);
        void autonomous_change(void);
        void init_allow(void);
        void unfinished(void);

