/* combined headers for Marxan */

/*    Marxan and this unit was coded by Ian Ball
    and written by Ian Ball and Hugh Possingham

    ian_bal@antdiv.gov.au
    hpossing@zen.uq.edu.au

      Modified by Matthew Watts  4 Nov 2005

        m.watts@uq.edu.au
    */


/* SPEX.H start */
#define DebugFree(x) /*Blank)*/

    int *bestyet;
    double delta;

    // type definitions for Marxan with Zones data structures
    typedef struct stringname
    {
        int id;
        char name[250];
    } typestringname;

    typestringname *Zones;
    typestringname *CostNames;

    typedef struct zonecontribstruct
    {
        int zoneid;
        int speciesid;
        double fraction;
    } typezonecontrib;

    typezonecontrib *ZoneContrib;

    typedef struct zonecontrib2struct
    {
        int zoneid;
        double fraction;
    } typezonecontrib2;

    typezonecontrib2 *ZoneContrib2;

    double *_ZoneContrib;

    typedef struct zonecontrib3struct
    {
        int zoneid;
        int puid;
        int speciesid;
        double fraction;
    } typezonecontrib3;

    typezonecontrib3 *ZoneContrib3;

    double *_ZoneContrib;

    typedef struct zonetargetstruct
    {
        int zoneid;
        int speciesid;
        double target;
        int targettype;
    } typezonetarget;

    typezonetarget *ZoneTarget;

    typedef struct zonetarget2struct
    {
        int zoneid;
        double target;
        int targettype;
    } typezonetarget2;

    typezonetarget2 *ZoneTarget2;

    typedef struct _zonetargetstruct
    {
        double target;
        int occurrence;
    } type_zonetarget;

    type_zonetarget *_ZoneTarget;

    typedef struct zonecoststruct
    {
        int zoneid;
        int costid;
        double fraction;
    } typezonecost;

    typezonecost *ZoneCost;
    double *_ZoneCost;

    typedef struct pulockstruct
    {
        int puid;
        int zoneid;
    } typepulock;

    typepulock *PuLock;

    typedef struct puzonestruct
    {
        int puid;
        int zoneid;
    } typepuzone;

    typepuzone *PuZone;

    typedef struct relconnectioncoststruct
    {
        int zoneid1;
        int zoneid2;
        double fraction;
    } typerelconnectioncost;

    typerelconnectioncost *RelConnectionCost;
    double *_RelConnectionCost;

    typedef struct zonespecstruct
    {
        double amount;
        int occurrence;
    } typezonespec;

    typezonespec *ZoneSpec;

    // type definitions for Marxan sparse matrix optimisations data structures
    typedef struct binsearch
    {
        int name;
        int index;
    } typebinsearch;

    typebinsearch *PULookup;
    typebinsearch *SPLookup;

    typedef struct spu
    {
        double amount;
        int clump;
        int spindex;
    } typepu;

    typepu *SM;

    typedef struct spusporder
    {
        double amount;
        int puindex;
    } typepusporder;

    typepusporder *SMsporder;

    // type definitions for original Ian Ball Marxan data structures

    typedef struct spustuff
    {
        int id;
        int status;
        double xloc,yloc;
        double cost;
        int richness,offset;
        int fPULock;
        int iPULock;
        int fPUZone;
        int iPUZone;
        int iPUZones;
        int iPreviousStatus;

    } typespu;

    typespu *pu;

    typedef struct scost
    {
        double total;
        double connection;
        int missing;
        double penalty;
        double cost;
        double threshpen;
        double shortfall;
    } typecost;

    typecost reserve,change,debugcost;

    typedef struct sspecies
    {
        int name;
        int type;
        char *sname;
        double target;
        double prop;
        int targetocc;
        double spf;
        double penalty;
        double amount;
        int occurrence;
        double sepdistance;
        int sepnum;
        int separation;
        int clumps;
        double target2;  /* Only some species need this (clumping species) */
        struct sclumps *head;  /* needed for clumping species */
        int richness,offset;
    }typesp;

    typesp *spec;

    /* Connection Structure. Fixed connection number. Should replace with link list! */
    struct sneighbour
    {
        int nbr;
        double cost;
        struct sneighbour *next;
        #ifdef ASYMCON
        int connectionorigon;
        #endif
    } *debugnbr;

    typedef struct sconnections
    {
        struct sneighbour *first;
        double fixedcost;
        int nbrno;
    }typeconnection;

    typeconnection *connections;

    struct sclumppu
    {
      int puid;
      struct sclumppu *next;
    }; /* PU in clump node for clump structure */

    struct sclumps
    {
      int clumpid;
      double amount;
      int occs;
      struct sclumppu *head;
      struct sclumps *next;
    }; /* Clump nodes for species Clump Structure */


/*** Annealing Control ****/

    typedef struct sanneal
    {
        int Titns;
        int iterations;
        int Tlen;
        double Tinit;    /* Initial Temperature */
        double Tcool;    /* Cooling Factor */
        double temp; /* Current Temperature */
        double tempold;
        int type;    /* Type of annealing. 0 = none, 1 = fixed, 2 = adaptive */
        double sigma; /*Used in adaptive annealing */
        double sum; /* Used in adaptive annealing */
        double sum2; /* used in adaptive annealing */
    } typeanneal; /* Annealing Control handler */

    typeanneal anneal;

    /* Useful general link list structure */

    /* General Species information structure */
    typedef struct sgenspec
    {
        int type;
        int targetocc;
        double target;
        double target2;
        int sepnum;
        double sepdistance;
        double prop;
        double spf;
    } typegenspec;

    /* Input File Name Structure */
    typedef struct sfname
    {
        char *inputdir;
        char *outputdir;
        char *specname;
        char *puname;
        char *puvsprname;
        char *matrixspordername;
        char *connectionname;
        char *blockdefname;
        char *zonesname;
        char *costsname;
        char *zonecontribname;
        char *zonecontrib2name;
        char *zonecontrib3name;
        char *zonetargetname;
        char *zonetarget2name;
        char *zonecostname;
        char *pulockname;
        char *puzonename;
        char *relconnectioncostname;
        char *penaltyname;
        char *connectivityfilesname;
        int savebest;
        int saverun;
        int savesum;
        int savesen;
        int savespecies;
        int savesumsoln;
        int savelog;
        int savesnapsteps;
        int savesnapchanges;
        int savesnapfrequency;
        int savepenalty;
        int savetotalareas;
        int savesolutionsmatrix;
        int solutionsmatrixheaders;
        int saveannealingtrace;
        int annealingtracerows;
        int suppressannealzones;
        int saveitimptrace;
        int itimptracerows;
        int savespeciesdata;
        int savezoneconnectivitysum;
    } typefname;

    typefname fnames;

    typedef struct srunoptions
    {
        int AnnealingOn;
        int HeuristicOn;
        int ItImpOn;
    } typerunopts;

    typerunopts runoptions;

/* Useful link definition */

    struct slink
    {
        int id;
        struct slink *next;
    }; /* slink struct */
    typedef struct iimp
    {
        double randomfloat;
        int puindex;
    } typeiimp;




/* Protytpe function Headers */
int spex(char sInputFileName[],int style);
void AddReserve(int puno,struct spustuff pu[],int R[],int iZoneCount,struct puzonestruct PuZone[]);
void SetBlockDefs(int gspno,int spno,int puno,struct sgenspec gspec[], struct sspecies spec[],struct spustuff PU[], struct spu SM[]);
void SetDefs(int spno, struct sspecies spec[]);
void SetRunOptions(int runopts, struct srunoptions *runoptions);
int CalcPenalties(int puno,int spno,struct spustuff pu[],struct sspecies spec[],
                  struct sconnections connections[],struct spu SM[],int aggexist,int clumptype,
                  int iZoneCount,int R[],struct _zonetargetstruct _ZoneTarget[]);
int CalcPenaltiesOptimise(int puno,int spno,struct spustuff pu[],struct sspecies spec[],
                          struct sconnections connections[],struct spu SM[],struct spusporder SMsp[],
                          int PUtemp[],int aggexist,int clumptype);


/**** Valuing a reserve ******/

double ConnectionCost1(int ipu,struct sconnections connections[]);
double cost(int ipu,struct sconnections connections[],int iZone);
double ConnectionCost2(int ipu,int iCurrentZone,struct sconnections connections[],int R[],int imode,int iDebugMode);
double ChangePen(int iIteration, int ipu,int puno,struct sspecies spec[], struct spustuff pu[],struct spu SM[],
                 int R[], struct sconnections connections[],int imode,int clumptype,int iZone,double *rShortfall);
void ZonationCost(int irun,int puno,int spno,int *R,struct spustuff pu[],struct sconnections connections[],
                  struct spu SM[], struct sspecies spec[],int aggexist,
                  struct scost *reserve,int clumptype,double prop,int iApplyReserveInit);
void InitReserve(int puno,double prop, int R[], struct spustuff pu[], int iZoneCount);
void SpeciesAmounts(int spno,int puno,struct sspecies spec[],struct spustuff pu[],
                    struct spu SM[],int R[],int clumptype,struct zonespecstruct ZoneSpec[]);

/*** Checking and instigating Changes ****/

double ThresholdPenalty(double tpf1,double tpf2,double timeprop);
void CheckChange(int iIteration, int ipu,int puno,struct spustuff pu[],struct sconnections connections[],
                 struct sspecies spec[],struct spu SM[],int R[],int imode,int iZone,
                 struct scost *change, struct scost *reserve,double costthresh,double tpf1,
                 double tpf2,double timeprop,int clumptype,int iDebugMode);
double NewPenalty(int ipu,int isp,struct sspecies spec[],struct spustuff pu[], struct spu SM[],int imode);
int GoodChange(struct scost change,double temp);
void DoChange(int ipu,int puno,int *R,struct scost *reserve,struct scost change,struct spustuff pu[],
              struct spu SM[],struct sspecies spec[],struct sconnections connections[],int imode,int iZone,int clumptype);

/**** Post Processing Headers ********/

int CountMissing(int spno,struct sspecies spec[],double misslevel,double *shortfall,double *rMinimumProportionMet);
void PrintResVal (int puno, int spno,int R[],struct scost reserve,
                  struct sspecies spec[],double misslevel);
void ChangeCost(struct scost *cost,double changemult);

void TimePassed(void);
void PauseProg(void);
void PauseExit(void);


/********* Prototype Headers For Clumping Utilities ********/
/* moved to clumping.h */

/********** Prototype Headers for Heuristics ***********/
/* moved to heuristic.h */

/************ Prototype Headers for Annealing specific functions ***/
/* moved to annealing.h */

/************* Prototype Headers for Separation specific functions ****/

/* Now moved to separation.h */


/************** Debugging Routines ****************/
/* moved to debug.h */

/* SPEX.H END */
/* ************************************************************************** */
/* ANNEALING.H START */


void ConnollyInit(int irun,int puno,int spno,struct spustuff pu[],typeconnection connections[],typesp spec[],
                  struct spu SM[], struct sanneal *anneal,int aggexist,
                  int R[],double prop,int clumptype, int iZoneCount,int verbose);
void AdaptiveInit(int irun,int puno,int spno,double prop,int R[],struct spustuff pu[],struct sconnections connections[],
                  struct spu SM[],struct sspecies spec[],int aggexist,struct sanneal *anneal,int clumptype,int iZoneCount);
void AdaptiveDec(struct sanneal *anneal);
void Annealing(int spno, int puno, struct sconnections connections[],int R[],
               typesp *spec, struct spustuff pu[], struct spu SM[], struct scost *change, struct scost *reserve,
               int repeats,int irun,char *savename,int verbose,double misslevel,
               int aggexist,double costthresh, double tpf1, double tpf2,int clumptype,double prop);

/* ANNEALING.H END */
/* ************************************************************************** */
/* CLUMPING.H START */



void ClearClump(int isp,struct sclumps *target,struct spustuff pu[],
                struct spu SM[]);
int ClumpCut(int isp,struct spustuff pu[],
             struct sspecies spec[],struct sclumps *clump,
             struct sclumppu *clumppu,struct sconnections connections[],struct spu SM[],
             double *totalamount,int *totalocc,
             int *iseparation, int imode,int clumptype);
void SetSpeciesClumps(int puno,int R[],struct sspecies spec[],struct spustuff pu[],
                      struct spu SM[],struct sconnections connections[],int clumptype);
void SpeciesAmounts4(int isp,struct sspecies spec[],int clumptype);
void ClearClumps(int spno,struct sspecies spec[],struct spustuff pu[], struct spu SM[]);
struct sclumps *AddNewClump(int isp,int ipu,struct sspecies spec[],struct spustuff pu[], struct spu SM[]);
void AddNewPU(int ipu,int isp,struct sconnections connections[],struct sspecies spec[],struct spustuff pu[],
              struct spu SM[], int clumptype);
void RemPu(int ipu, int isp,struct sconnections connections[], struct sspecies spec[],struct spustuff pu[],
           struct spu SM[],int clumptype);
int RemClumpCheck(struct sclumps *pclump,struct spustuff pu[]);
int CalcPenaltyType4(int isp,int puno, struct spu SM[],struct sconnections connections[],
                     struct sspecies spec[],struct spustuff pu[],int clumptype,int inputR[]);
double PartialPen4(int isp,double amount, struct sspecies spec[],int clumptype);
double ValueAdd(int isp,int ipu,int puno, int R[],struct sconnections connections[],struct spustuff pu[],
                struct spu SM[],struct sspecies spec[],int clumptype);
double ValueRem(int ipu,int isp,struct sspecies spec[],struct sconnections connections[],
                struct spustuff pu[],struct spu SM[],int clumptype);
double NewPenalty4(int ipu,int isp,int puno,struct sspecies spec[],struct spustuff pu[],struct spu SM[],
                   int R[],struct sconnections connections[],int imode,int clumptype);



/* CLUMPING.H END */
/* ************************************************************************** */
/* FILEIN.H START */



struct snlink{char *name; struct snlink *next;};


/* Parmtypes */
#define NOTYPE 0
#define INTEGER 1
#define LONGINT 2
#define REAL 3
#define DOUBLE 4
#define STRING 5

/* Prototype headers */

struct snlink *GetVarName(char **varlist,int numvars,char *sVarName,
                          struct snlink *head,char *fname);
int CheckVarName(char **varlist, int numvars, char *sVarName);
struct snlink *GetVarNamePU(char **varlist,int numvars,struct stringname CostNames[],int iCostCount,char *sVarName,
                            struct snlink *head,char *fname);

int ReadNameList(int *puno,struct spustuff *pu[],char indir[]);
int NameToPUID(int puno,int name, struct spustuff pu[]);
int NameToSPID(int spno,int name,typesp spec[]);
void rdsvar(FILE *infile, char varname[], void *address, int parmtype,int crit,int *present);

void SetOptions(double *prop,struct sanneal *anneal,
                int *iseed,
                int *repeats,char savename[],struct sfname *fname,char filename[],
                int *runopts,double *misslevel,int *heurotype,int *verbose,int *clumptype,
                int *itimptype,
                double *costthresh,double *tpf1,double *tpf2);

int ReadPUCosts(int puno,struct spustuff pu[],struct binsearch PULookup[],int verbose,char indir[]);
int ReadSpeciesData(int *spno,struct sspecies *spec[],int *aggexist, int *sepexist,char indir[]);
int ReadPUFile(int puno,struct spustuff pu[],struct binsearch PULookup[],int verbose,char indir[]);
int ReadPUXYfile(int puno,struct spustuff pu[],struct binsearch PULookup[],char indir[]);
int ReadPUData(int *puno,struct spustuff *pu[],int iCostCount,struct stringname CostNames[],struct sfname fnames);
int ReadSpeciesData2(int *spno,struct sspecies *spec[],struct sfname fnames);
int ReadGenSpeciesData(int *gspno,struct sgenspec *gspec[],struct sfname fnames);
int ReadConnections(int puno,struct sconnections connections[],int verbose,
                    struct spustuff pu[],struct binsearch PULookup[],struct sfname fnames);
int PrepareWeightedConnectivityFile(struct sfname fnames);
void ReadPUVSPFile22(int puno,int spno,struct spu SM[],int verbose,struct spustuff pu[],
                     typesp spec[],struct sfname fnames);
void ReadPUVSPFileTable(FILE *infile, int puno,int spno,struct spu SM[],struct spustuff pu[],
                        typesp spec[]);

/* new functions added by Matt for Marxan optimization */
void LoadSparseMatrix(int *iSMSize, struct spu *SM[], int puno, int spno, struct spustuff PU[],
                      struct binsearch PULookup[],struct binsearch SPLookup[],
                      struct sfname fnames);
void LoadSparseMatrix_sporder(int *iSMSize, struct spusporder *SM[], int puno, int spno,
                              struct binsearch PULookup[],struct binsearch SPLookup[],// typesp spec[],
                              struct sfname fnames);
void DumpSparseMatrix(int iSMno, struct spu SM[],struct sfname fnames);
void DumpPU_richness_offset(int puno, struct spustuff PU[],struct sfname fnames);
void DumpBinarySearchArrays(char *sName,struct sfname fnames, int puno, int spno, struct binsearch PULookup[],
                            struct binsearch SPLookup[]);

void PrepareBinarySearchArrays(int puno, int spno, struct spustuff PU[], typesp spec[],
                               struct binsearch *PULookup[], struct binsearch *SPLookup[]);
void TestFastPUIDtoPUINDEX(int puno, struct binsearch PULookup[], struct spustuff PU[], struct sfname fnames);
int FastPUIDtoPUINDEX(int puno,int name, struct binsearch PULookup[]);
void TestFastSPIDtoSPINDEX(int spno, struct binsearch SPLookup[], typesp spec[], struct sfname fnames);
int FastSPIDtoSPINDEX(int spno,int name, struct binsearch SPLookup[]);
int rtnIdxSpecAtPu(struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex);
double rtnAmountSpecAtPu(struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex);
int rtnClumpSpecAtPu(struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex);
void setClumpSpecAtPu(struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex, int iSetClump);
void TestrtnAmountSpecAtPu(int puno, int spno, struct spustuff pu[], struct sspecies spec[], struct spu SM[],
                           struct sfname fnames);
void TestrtnAmountSpecAtPuZone(int puno, int spno, struct spustuff pu[], struct sspecies spec[], struct spu SM[],
                               struct sfname fnames);

#ifdef DEBUGTRACEFILE
void StartDebugTraceFile(void);
void AppendDebugTraceFile(char sMess[],...);
#endif

// functions to read in Marxan with Zones data structures
void LoadZones(int *iZoneCount,struct stringname *Zones[],struct sfname fnames);
void LoadCostNames(int *iCostCount,struct stringname *CostNames[],struct sfname fnames);
void LoadZoneContrib(int *iZoneContribCount,struct zonecontribstruct *ZoneContrib[],struct sfname fnames);
void LoadZoneContrib2(int *iZoneContrib2Count,struct zonecontrib2struct *ZoneContrib2[],struct sfname fnames);
void LoadZoneContrib3(int *iZoneContrib3Count,struct zonecontrib3struct *ZoneContrib3[],struct sfname fnames);
void LoadZoneTarget(int *iZoneTargetCount,struct zonetargetstruct *ZoneTarget[],struct sfname fnames);
void LoadZoneTarget2(int *iZoneTarget2Count,struct zonetarget2struct *ZoneTarget2[],struct sfname fnames);
void LoadZoneCost(int *iZoneCostCount,struct zonecoststruct *ZoneCost[],struct sfname fnames);
void LoadPuLock(int *iPuLockCount,struct pulockstruct *PuLock[],struct sfname fnames);
void LoadPuZone(int *iPuZoneCount,struct puzonestruct *PuZone[],struct sfname fnames);
void LoadRelConnectionCost(int *iRelConnectionCostCount,struct relconnectioncoststruct *RelConnectionCost[],struct sfname fnames);
void LoadCostValues(int iCostCount,double **CostValues,struct stringname CostNames[],struct sfname fnames,int puno);

// Marxan with Zones debugging functions
void DumpFileNames(struct sfname fnames);
void DumpZoneNames(int iZoneCount,struct stringname Zones[],struct sfname fnames);
void DumpCostNames(int iCostCount,struct stringname CostNames[],struct sfname fnames);
void DumpZoneContrib(int iZoneContribCount,struct zonecontribstruct ZoneContrib[],struct sfname fnames);
void DumpZoneContrib2(int iZoneContrib2Count,struct zonecontrib2struct ZoneContrib2[],struct sfname fnames);
void DumpZoneContrib3(int iZoneContrib3Count,struct zonecontrib3struct ZoneContrib3[],struct sfname fnames);
void DumpZoneTarget(int iZoneTargetCount,struct zonetargetstruct ZoneTarget[],struct sfname fnames);
void DumpZoneTarget2(int iZoneTarget2Count,struct zonetarget2struct ZoneTarget2[],struct sfname fnames);
void DumpZoneCost(int iZoneCostCount,struct zonecoststruct ZoneCost[],struct sfname fnames);
void DumpPuLock(int iPuLockCount,struct pulockstruct PuLock[],struct sfname fnames);
void DumpPuZone(int iPuZoneCount,struct puzonestruct PuZone[],struct sfname fnames);
void DumpPuZone_Debug(int iPuZoneCount,struct puzonestruct PuZone[],struct sfname fnames,int iMessage);
void DumpRelConnectionCost(int iRelConnectionCostCount,struct relconnectioncoststruct RelConnectionCost[],struct sfname fnames);
void DumpCostValues(int iCostCount,int puno,double **CostValues,struct sfname fnames);
void DumpCostFieldNumber(int iFieldCount,int CostFieldNumber[],char *sFields,struct sfname fnames);
void Dump_ZoneContrib(int puno,int spno,typesp spec[],int iZoneCount,double _ZoneContrib[],struct sfname fnames);
void Dump_ZoneTarget(int spno,int iZoneCount,struct _zonetargetstruct _ZoneTarget[],struct sfname fnames);
void Dump_ZoneCost(int iCostCount,int iZoneCount,double _ZoneCost[],struct sfname fnames);
void DumpPuLockZone(int puno,struct spustuff pu[]);
void Dump_RelConnectionCost(int iZoneCount,double _RelConnectionCost[],struct sfname fnames);
void DumpZoneSpec(int iMessage,int spno,int iZoneCount,struct zonespecstruct ZoneSpec[],struct sspecies spec[],struct sfname fnames);
#ifdef DEBUGTRACEFILE
void DumpR(int iMessage,char sMessage[],int puno,int reservedarray[],struct spustuff pu[],struct sfname fnames);
#endif

// MarZone functions
void DefaultZones(int *iZoneCount,struct stringname *Zones[]);
void DefaultCostNames(int *iCostCount,struct stringname *CostNames[]);
int rtnCostIndex(int iCostCount,struct stringname CostNames[],char *sFieldName);
int PuNotInAllowedZone(struct spustuff GivenPu,int iStatus,struct puzonestruct PuZone[],int iLoopCounter,char cCall);
void Default_ZoneContrib(int spno,int iZoneCount,double *_ZoneContrib[],int iAvailableZoneFromInput);
void Build_ZoneContrib(int spno, int iZoneCount,int iZoneContribCount,struct zonecontribstruct ZoneContrib[],double *_ZoneContrib[]);
void Build_ZoneContrib2(int spno,int iZoneCount,int iZoneContrib2Count,struct zonecontrib2struct ZoneContrib2[],double *_ZoneContrib[]);
//void Update_ZoneContrib2(int spno,int iZoneCount,int iZoneContrib2Count,struct zonecontrib2struct ZoneContrib2[],double _ZoneContrib[]);
void Build_ZoneContrib3(int puno,int spno,int iZoneCount,int iZoneContrib3Count,struct zonecontrib3struct ZoneContrib3[],double *_ZoneContrib[]);
void Default_ZoneTarget(int spno, int iZoneCount,struct _zonetargetstruct *_ZoneTarget[]);
void Build_ZoneTarget(int spno, int iZoneCount,int iZoneTargetCount,struct zonetargetstruct ZoneTarget[],
                      struct _zonetargetstruct *_ZoneTarget[],int puno,struct spustuff pu[],struct spu SM[]);
void Build_ZoneTarget2(int spno, int iZoneCount,int iZoneTarget2Count,struct zonetarget2struct ZoneTarget2[],
                       struct _zonetargetstruct *_ZoneTarget[],int puno,struct spustuff pu[],struct spu SM[]);
void Update_ZoneTarget2(int spno, int iZoneCount,int iZoneTarget2Count,struct zonetarget2struct ZoneTarget2[],
                        struct _zonetargetstruct _ZoneTarget[],int puno,struct spustuff pu[],struct spu SM[]);
void DefaultZoneCost(int *iZoneCostCount,struct zonecoststruct *ZoneCost[]);
void Build_ZoneCost(int iCostCount,int iZoneCount,int iZoneCostCount,struct zonecoststruct ZoneCost[],double *_ZoneCost[]);
void ParsePuLock(int puno,struct spustuff pu[],int iPuLockCount,struct pulockstruct PuLock[],struct binsearch PULookup[]);
void ParsePuZone(int puno,struct spustuff pu[],int iPuZoneCount,struct puzonestruct PuZone[],struct binsearch PULookup[]);
void CheckPuZone(int puno,struct spustuff pu[]);
void Build_RelConnectionCost(int iZoneCount,int iRelConnectionCostCount,struct relconnectioncoststruct RelConnectionCost[],double *_RelConnectionCost[]);
void Default_RelConnectionCost(int iZoneCount,double *_RelConnectionCost[]);
void BuildZoneSpec(int spno,int iZoneCount,struct zonespecstruct *ZoneSpec[]);
void InitZoneSpec(int spno,int iZoneCount,struct zonespecstruct ZoneSpec[]);
void InitSumSoln(int puno,int iZoneCount,int sumsoln[],int ZoneSumSoln[]);
/* FILEIN.H END */
/* ************************************************************************** */
/* FILEOUT.H START */

void SaveSeed(int iseed);
void OutputSummary(int puno,int spno,int R[],struct sspecies spec[],struct scost reserve,
                   int itn,char savename[],double misslevel,int imode);
void OutputScenario(int puno,int spno,double prop,
                    struct sanneal anneal, int seedinit,int repeats,int clumptype,
                    int runopts,int heurotype,double costthresh, double tpf1, double tpf2,
                    char savename[]);
void OutputSolution(int puno,int R[],struct spustuff pu[],char savename[],int imode);
void OutputFeatures(int spno,struct sspecies spec[],char savename[],int imode,double misslevel);
void OutputSumSoln(int puno,int sumsoln[],int ZoneSumSoln[],int R[],struct spustuff pu[],char savename[],int imode);
void OutputSpeciesData(int spno,struct sspecies spec[],char savename[]);

/* FILEOUT.H END */
/* ************************************************************************** */
/* HEURISTIC.H START */

double GreedyPen(int ipu, int puno, int spno, typesp spec[],int R[],struct spustuff pu[],
                 struct spu SM[],int clumptype);
double GreedyScore(int ipu,int puno,int spno, typesp *spec,struct spu SM[],struct sconnections connections[],
                   int R[],struct spustuff pu[],int clumptype);
void SetRareness(int puno, int spno, double Rare[],struct spustuff pu[],struct spu SM[],int R[]);
double RareScore(int isp,int ipu,int puno,typesp spec[],struct spu SM[], int R[],
                 struct sconnections connections[], struct spustuff pu[],int clumptype);
double MaxRareScore(int ipu,int puno,struct sspecies spec[],struct spu SM[],
                    int R[],struct sconnections connections[],struct spustuff pu[],double Rare[],int clumptype);
double BestRareScore(int ipu,int puno,struct sspecies spec[],struct spu SM[],
                     int R[],struct sconnections connections[],struct spustuff pu[],double Rare[],int clumptype);
double AveRareScore(int ipu,int puno,struct sspecies spec[],struct spu SM[],
                    int R[],struct sconnections connections[],struct spustuff pu[],double Rare[],int clumptype);
double SumRareScore(int ipu,int puno,struct sspecies spec[],struct spu SM[],
                    int R[],struct sconnections connections[],struct spustuff pu[],double Rare[],int clumptype);
void SetAbundance(int puno,double Rare[],struct spustuff pu[],struct spu SM[]);
double Irreplaceability(int ipu,int isp, double Rare[],struct spustuff pu[],struct spu SM[],typesp *spec);
double ProdIrr(int ipu,double Rare[],struct spustuff pu[],struct spu SM[],typesp *spec);
double SumIrr(int ipu,double Rare[],struct spustuff pu[],struct spu SM[],typesp *spec);
void Heuristics(int spno,int puno,struct spustuff pu[],struct sconnections connections[],
                int R[],typesp *spec,struct spu SM[], struct scost *reserve,
                double costthresh, double tpf1,double tpf2, int imode,int clumptype);

/* HEURISTIC.H END */
/* ************************************************************************** */
/* ITIMP.H START */

struct slink* ItImpDiscard(int ichoice, struct slink *list, struct slink **discard);
struct slink* ItImpUndiscard(struct slink *list, struct slink **discard);
int FindSwap(struct slink **list,double targetval,int itestchoice,int puuntried,
             int puno,struct spustuff pu[], struct sconnections connections[],
             struct sspecies spec[],struct spu SM[],
             int R[], struct scost *reserve, struct scost *change,
             double costthresh, double tpf1, double tpf2, int clumptype);
void IterativeImprovement(int puno,struct spustuff pu[], struct sconnections connections[],
                           struct sspecies spec[],struct spu SM[],int R[],
                           struct scost *reserve,struct scost *change,double costthresh,double tpf1, double tpf2,
                           int clumptype,int itimptype);
void IterativeImprovementOptimise(int puno,struct spustuff pu[], struct sconnections connections[],
                                   struct sspecies spec[],struct spu SM[],int R[],
                                   struct scost *reserve,struct scost *change,double costthresh,double tpf1, double tpf2,
                                   int clumptype,int irun,char *savename);

/* ITIMP.H END */
/* ************************************************************************** */
/* RANDOM.H START */

double rand1(void);
void InitRandSeed(int iSeed);
int RandNum (int num);

/* RANDOM.H END */
/* ************************************************************************** */
/* SCREENOUT.H START */

/* Prototype Headers */

void ShowStartupScreen(void);        /* Displays splash screen info */
void ShowShutdownScreen(void);        /* displays program termination info */
void ShowPauseExit(void);            /* Press any key to terminate */
void ShowPauseProg(void);            /* Press any key to continue */

void SetVerbosity(int verb);

void ShowErrorMessage(char sMess[],...);  /* Displays message then terminates */
void ShowWarningMessage(char sMess[],...);/* Displays message without terminating */

void ShowProg(char sMess[],...);
void ShowGenProg(char sMess[],...);
void ShowGenProgInfo(char sMess[],...);
void ShowDetProg(char sMess[],...);

void ShowTimePassed(void);

void SetLogFile(int my_savelog, char* my_savelogname);

/* SCREENOUT.H END */
/* ************************************************************************** */
/* SEPERATION.H START */

/* Structure Definition */

typedef struct sseplist
{
    int size;
    struct slink *head,*tail;
} typeseplist;

/* Function Prototypes */

double SepPenalty(int ival);
double SepPenalty2(int ival, int itarget);
int ValidPU(int ipu,int isp,struct sclumps *newno,struct sspecies spec[],struct spustuff pu[],
            struct spu SM[],int imode);
int CheckDistance(int i, int j,struct spustuff pu[],double squaretarget);
int CountSeparation(int isp,struct sclumps *newno,
                    struct spustuff pu[],struct spu SM[],typesp spec[],int imode);
struct slink *makelist(int isp,int inpu, int puno,int R[],struct sclumps *newno,struct sspecies spec[],
                       struct spustuff pu[],struct spu SM[],int imode);
int SepDealList(struct slink *head, typeseplist *Dist,struct spustuff *pu,
                struct sspecies spec[],int first,int sepnum,double targetdist,int isp);
int CountSeparation2(int isp,int ipu,struct sclumps *newno,int puno,int R[],
                     struct spustuff pu[],struct spu SM[],typesp spec[],int imode);

    /* Debug files */

void CheckDist(struct sseplist *Dist,int sepnum);

/* SEPERATION.H END */
/* ************************************************************************** */

// miscellaneous MarZone functions
void ApplySpecProp(int spno,typesp spec[],int puno,struct spustuff pu[],struct spu SM[]);
void CalcTotalAreas(int puno,int spno,struct spustuff pu[],struct sspecies spec[],struct spu SM[]);
void InitialiseReserve(int puno,struct spustuff pu[],int R[],int iZoneCount,struct puzonestruct PuZone[]);
double NewPenaltyZone(int iZone,int ipu,int isp,struct sspecies spec[],struct spustuff pu[],struct spu SM[],int imode);
double ReturnPuZoneCost(int ipu,int iZone);
double rtnConvertZoneAmount(int iZone, int iSpecIndex,int iPUIndex,int puno, double rAmount);
double rtnAmountSpecAtPuZone(int iZone,struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex);
double rtnMaxNonAvailableCost(int ipu,struct sconnections connections[]);
void StartDebugFile(char sFileName[],char sHeader[],struct sfname fnames);
void AppendDebugFile(char sFileName[],char sLine[],struct sfname fnames);
void OutputPenalty(int spno,struct sspecies spec[],char savename[],int iOutputType);
void LoadPenalty(int spno,struct sspecies spec[],struct sfname fnames);
void InitSolutionsMatrix(int puno,struct spustuff pu[],char savename[],int iOutputType,int iIncludeHeaders);
void AppendSolutionsMatrix(int iRun,int puno,int R[],char savename[],int iOutputType,int iIncludeHeaders);
void AppendSolutionsMatrixZone(int iZone,int iRun,int puno,int R[],char savename[],int iOutputType,int iIncludeHeaders);
//void AppendSolutionsMatrixRun(int iRun,int puno,int R[],char savename[],int iOutputType,int iIncludeHeaders);
void WriteSlaveSyncFileRun(int iSyncRun);
void OutputZoneConnectivitySum(int puno,int R[],char savename[],int imode);

int DumpAsymmetricConnectionFile(int puno,struct sconnections connections[],struct spustuff pu[],struct sfname fnames);

void OutputTotalAreas(int puno,int spno,struct spustuff pu[],struct sspecies spec[],struct spu SM[],char savename[],int iOutputType);
void WriteStopErrorFile(char sMess[]);

