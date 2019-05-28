// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

// C file for Marxan with Zones

//   Marine Reserve Design with Zoning and Annealing

//   Marxan with Zones coded by Matthew Watts
//   Written by Ian Ball, Hugh Possingham and Matthew Watts

//   Based on Marxan coded by Ian Ball, modified by Matthew Watts
//   Written by Ian Ball and Hugh Possingham

//   ian.ball@aad.gov.au
//   hpossingham@zen.uq.edu.au
//   m.watts@uq.edu.au

#include <ctype.h>
#include <math.h>
#include <setjmp.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MARZONE

#undef MEMDEBUG

#define DEBUGTRACEFILE
#undef ANNEALINGDETAILDEBUGTRACE
#undef EXTRADEBUGTRACE
#undef ANNEALING_TEST
#undef DEBUG_CM  // this definition for debugging run time error in CM>0
#undef DEBUGFPERROR
#undef DEBUGCHECKCHANGE
#undef DEBUGCALCPENALTIES
#undef DEBUGNEWPENALTY
#undef DEBUGSHOWTIMEPASSED
#undef DEBUG_ZONECONNECTIONCALCS
#undef DEBUG_CONNECTIONCOST2

#undef DEBUG_COUNT_MISSING
#undef DEBUG_CONNECTIONCOST2_LINEAR
#undef TRACE_ZONE_TARGETS

#undef DEBUGINITIALISERESERVE
#undef DEBUG_PuNotInAllowedZone
#undef DEBUG_CALC_PENALTIES
#undef DEBUG_CHANGE_PEN

#undef DEBUG_PEW_CHANGE_PEN
#undef ASYMCON
#undef PENX_MOD

#undef DEBUG_ZONATION_COST
#undef DEBUG_CONNOLLYINIT

#define DEBUG_PENALTY_NEGATIVE

#include "marxan.h"

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

jmp_buf jmpbuf;
int iMemoryUsed=0;
int iCostCount = 0;
int iZoneCount = 0;
int fSpecPROPLoaded = 0;
int marxanisslave = 0;
double **CostValues;
long int RandSeed1;
char sVersionString[50] = "Marxan with Zones v 2.03";
// version 2.0 introduces these features;
//   enhanced flexibility in objectives
//   probabilistic treatment of threats (1D prob)
//   probabilistic treatment of species (2D prob)
//   asymmetric connectivity
//   multiple connectivity files
char sIanBallEmail[50] = "ian.ball@aad.gov.au";
char sHughPossinghamEmail[50] = "h.possingham@uq.edu.au";
char sMattWattsEmail[50] = "m.watts@uq.edu.au";
char sMarxanWebSite[50] = "http://www.uq.edu.au/marxan";
char sDebugTraceFileName[50] = "DebugTraceFile_Marxan_with_Zones.txt";
int iAvailableZoneInput;
int iVerbosity;
int savelog;
char* savelogname;
FILE* fsavelog;
int iOptimisationIterativeImprovement = 1, iPuZoneCount = 0;
int iOptimisationCalcPenalties = 1;
double rClocksPerSec;
double *TotalAreas;
int asymmetricconnectivity = 0;
int iZoneContrib3On = 0;
int iZonationCost = 0;

int MarZone(char sInputFileName[],int style)
{
    int iSparseMatrixFileLength = 0, iSparseMatrixFileLength_sporder = 0;
    int iZoneContribCount = 0, iZoneTargetCount = 0, iZoneCostCount = 0, iPuLockCount = 0;
    int iZoneContrib2Count = 0, iZoneTarget2Count = 0, iRelConnectionCostCount = 0;
    int iZoneContrib3Count = 0;
    int iMessageCounter = 0, iZoneSumSolnIndex, puno,spno,gspno;
    int runopts,heurotype,verbose,clumptype,itimptype;
    int repeats, iseed,seedinit, aggexist=0,sepexist=0;
    int *R, *R_CalcPenalties; //,*bestrun,
    int *sumsoln, *ZoneSumSoln, itemp,irun, ipu, iBestRun = 1;
    int i;
    struct sgenspec *gspec;
    double prop, misslevel, costthresh,tpf1,tpf2, rBestScore;
    char savename[1000],tempname2[1000];
    #ifdef DEBUGTRACEFILE
    char debugbuffer[1000];
    #endif

    rClocksPerSec = CLOCKS_PER_SEC;

    // Handle Error driven termination
    if (setjmp(jmpbuf))
       return 1;

    ShowStartupScreen();

    SetOptions(&prop,&anneal,
               &iseed,&repeats,savename,&fnames,sInputFileName,
               &runopts,&misslevel,&heurotype,&verbose,&clumptype,&itimptype,
               &costthresh,&tpf1,&tpf2);

    SetVerbosity(verbose);
    SetRunOptions(runopts,&runoptions);

    #ifdef DEBUGTRACEFILE
    StartDebugTraceFile();
    sprintf(debugbuffer,"%s begin execution\n\n",sVersionString);
    AppendDebugTraceFile(debugbuffer);
    AppendDebugTraceFile("LoadOptions\n");
    if (iVerbosity > 3)
       DumpFileNames(fnames);
    #endif

    #ifdef DEBUGCHECKCHANGE
    StartDebugFile("debug_MarZone_CheckChange.csv","ipu,puid,R,total,cost,connection,penalty,threshpen\n",fnames);
    #endif

    #ifdef DEBUG_CHANGE_PEN
    StartDebugFile("debug_MarZone_ChangePen.csv","ipu,puid,isp,spid,cost,newamount,famount\n",fnames);
    #endif

    #ifdef DEBUG_PEW_CHANGE_PEN
    StartDebugFile("debug_MarZone_PewChangePen.csv","iteration,ipu,isp,puid,spid,Zone,newZone,ZoneTarget,PUAmount,Amount,newAmount,Shortfall,newShortfall,rSF,rNSF,iCSF,iNSF,zone\n",fnames);
    #endif

    #ifdef DEBUGCALCPENALTIES
    StartDebugFile("debug_MarZone_CalcPenalties.csv","\n",fnames);
    #endif

    if (fnames.savelog)
    {
       sprintf(tempname2,"%s_log.dat",savename);
       SetLogFile(fnames.savelog,tempname2);
    }

    delta = 1e-14;  // This would more elegantly be done as a constant

    // Dynamically Create the Big Arrays

    InitRandSeed(iseed);
    #ifdef DEBUG
    SaveSeed(iseed);
    #endif
    seedinit = iseed;

    #ifdef DEBUGTRACEFILE
    sprintf(debugbuffer,"RandSeed iseed %i RandSeed1 %li\n",iseed,RandSeed1);
    AppendDebugTraceFile(debugbuffer);
    sprintf(debugbuffer,"style is %i\n",style);
    AppendDebugTraceFile(debugbuffer);
    #endif

    //  ****     Data File Entry    * * * * * * *
    ShowGenProg("\nEntering in the data files \n");

    // read in the MarZone files
    if (strcmp("NULL",fnames.zonesname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadZones\n");
       #endif

       LoadZones(&iZoneCount,&Zones,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadZones\n");
       #endif
    }
    else
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before DefaultZones\n");
       #endif

       DefaultZones(&iZoneCount,&Zones);

       iAvailableZoneInput = 1;

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after DefaultZones\n");
       #endif
    }

    if (strcmp("NULL",fnames.costsname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadCostNames\n");
       #endif

       LoadCostNames(&iCostCount,&CostNames,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadCostNames\n");
       #endif
    }
    else
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before DefaultCostNames\n");
       #endif

       DefaultCostNames(&iCostCount,&CostNames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after DefaultCostNames\n");
       #endif
    }

    if (strcmp("NULL",fnames.zonecontribname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadZoneContrib\n");
       #endif

       LoadZoneContrib(&iZoneContribCount,&ZoneContrib,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadZoneContrib\n");
       #endif
    }
    if (strcmp("NULL",fnames.zonecontrib2name) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadZoneContrib2\n");
       #endif

       LoadZoneContrib2(&iZoneContrib2Count,&ZoneContrib2,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadZoneContrib2\n");
       #endif
    }
    if (strcmp("NULL",fnames.zonecontrib3name) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadZoneContrib3\n");
       #endif

       LoadZoneContrib3(&iZoneContrib3Count,&ZoneContrib3,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadZoneContrib3\n");
       #endif
    }
    if (strcmp("NULL",fnames.zonetargetname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadZoneTarget\n");
       #endif

       LoadZoneTarget(&iZoneTargetCount,&ZoneTarget,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadZoneTarget\n");
       #endif
    }
    if (strcmp("NULL",fnames.zonetarget2name) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadZoneTarget2\n");
       #endif

       LoadZoneTarget2(&iZoneTarget2Count,&ZoneTarget2,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadZoneTarget2\n");
       #endif
    }
    if (strcmp("NULL",fnames.zonecostname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadZoneCost\n");
       #endif

       LoadZoneCost(&iZoneCostCount,&ZoneCost,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadZoneCost\n");
       #endif
    }
    else
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before DefaultZoneCost\n");
       #endif

       DefaultZoneCost(&iZoneCostCount,&ZoneCost);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after DefaultZoneCost\n");
       #endif
    }

    if (strcmp("NULL",fnames.pulockname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadPuLock\n");
       #endif

       LoadPuLock(&iPuLockCount,&PuLock,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadPuLock\n");
       #endif
    }
    if (strcmp("NULL",fnames.puzonename) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadPuZone\n");
       #endif

       LoadPuZone(&iPuZoneCount,&PuZone,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadPuZone\n");
       #endif
    }
    if (strcmp("NULL",fnames.relconnectioncostname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadZoneConnectionCost\n");
       #endif

       LoadRelConnectionCost(&iRelConnectionCostCount,&RelConnectionCost,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadZoneConnectionCost\n");
       #endif
    }

    #ifdef DEBUGTRACEFILE
    if (iVerbosity > 3)
    {
       DumpZoneNames(iZoneCount,Zones,fnames);
       DumpCostNames(iCostCount,CostNames,fnames);
       DumpZoneContrib(iZoneContribCount,ZoneContrib,fnames);
       DumpZoneContrib2(iZoneContrib2Count,ZoneContrib2,fnames);
       DumpZoneContrib3(iZoneContrib3Count,ZoneContrib3,fnames);
       DumpZoneTarget(iZoneTargetCount,ZoneTarget,fnames);
       DumpZoneTarget2(iZoneTarget2Count,ZoneTarget2,fnames);
       DumpZoneCost(iZoneCostCount,ZoneCost,fnames);
       if (strcmp("NULL",fnames.pulockname) != 0)
          DumpPuLock(iPuLockCount,PuLock,fnames);
       if (strcmp("NULL",fnames.puzonename) != 0)
          DumpPuZone(iPuZoneCount,PuZone,fnames);
       if (strcmp("NULL",fnames.relconnectioncostname) != 0)
          DumpRelConnectionCost(iRelConnectionCostCount,RelConnectionCost,fnames);
    }
    #endif

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("before LoadPuData\n");
    #endif

    ShowDetProg("    Reading in the Planning Unit names \n");
    if (style == 1)
       itemp = ReadNameList(&puno,&pu,fnames.inputdir);
    else
        itemp = ReadPUData(&puno,&pu,iCostCount,CostNames,fnames);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("after LoadPuData\n");
    #endif

    ShowGenProg("   There are %i Planning units.\n  %i Planning Unit names read in \n",puno,itemp);

    if (iCostCount)
    {
       // create the costs array
       CostValues = (double **) calloc(puno,sizeof(double *));
       for (itemp = 0;itemp<puno;itemp++)
           CostValues[itemp] = (double *) calloc(iCostCount,sizeof(double));

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadCostValues\n");
       #endif

       // read the costs into the array
       LoadCostValues(iCostCount,CostValues,CostNames,fnames,puno);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadCostValues\n");
       #endif

       #ifdef DEBUGTRACEFILE
       if (iVerbosity > 3)
          DumpCostValues(iCostCount,puno,CostValues,fnames);
       #endif
    }

    ShowDetProg("    Reading in the species file \n");

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("before LoadSpeciesData\n");
    #endif

    if (style == 1)
       itemp = ReadSpeciesData(&spno,&spec,&aggexist,&sepexist,fnames.inputdir);
    else
        itemp = ReadSpeciesData2(&spno,&spec,fnames);

    #ifdef DEBUGTRACEFILE
    // *************************************************************************************
    AppendDebugTraceFile("after LoadSpeciesData\n");
    #endif

    ShowGenProg("  %i species read in \n",itemp);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("before build search arrays\n");
    #endif

    // create the fast lookup tables for planning units and species names
    PrepareBinarySearchArrays(puno,spno,pu,spec,&PULookup,&SPLookup);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("after build search arrays\n");
    #endif

    if (strcmp("NULL",fnames.zonecontribname) != 0)
    {
       if (strcmp("NULL",fnames.zonecontrib2name) != 0)
       {
          // error condition exists, both zonecontrib & zonecontrib2 are defined
          WriteStopErrorFile("both zonecontrib & zonecontrib2 are defined\n");
          #ifdef DEBUGTRACEFILE
          AppendDebugTraceFile("both zonecontrib & zonecontrib2 are defined\n");
          #endif
          ShowGenProg("both zonecontrib & zonecontrib2 are defined\n");
          ShowGenProgInfo("both zonecontrib & zonecontrib2 are defined\n");
          ShowShutdownScreen();
          return 0;
       }
       else
       {
           if (strcmp("NULL",fnames.zonecontrib3name) != 0)
           {
              // error condition exists, both zonecontrib & zonecontrib3 are defined
              WriteStopErrorFile("both zonecontrib & zonecontrib3 are defined\n");
              #ifdef DEBUGTRACEFILE
              AppendDebugTraceFile("both zonecontrib & zonecontrib3 are defined\n");
              #endif
              ShowGenProg("both zonecontrib & zonecontrib3 are defined\n");
              ShowGenProgInfo("both zonecontrib & zonecontrib3 are defined\n");
              ShowShutdownScreen();
              return 0;
           }
           else
           {
               #ifdef DEBUGTRACEFILE
               AppendDebugTraceFile("before Build_ZoneContrib\n");
               #endif

               Build_ZoneContrib(spno,iZoneCount,iZoneContribCount,ZoneContrib,&_ZoneContrib);
               free(ZoneContrib);

               #ifdef DEBUGTRACEFILE
               AppendDebugTraceFile("after Build_ZoneContrib\n");
               #endif
           }
       }
    }
    if (strcmp("NULL",fnames.zonecontrib2name) != 0)  // if zonecontrib2name is not null
    {
       if (strcmp("NULL",fnames.zonecontribname) != 0)
       {
          // error condition exists, both zonecontrib & zonecontrib2 are defined
          WriteStopErrorFile("both zonecontrib & zonecontrib2 are defined\n");
          #ifdef DEBUGTRACEFILE
          AppendDebugTraceFile("both zonecontrib & zonecontrib2 are defined\n");
          #endif
          ShowGenProg("both zonecontrib & zonecontrib2 are defined\n");
          ShowGenProgInfo("both zonecontrib & zonecontrib2 are defined\n");
          ShowShutdownScreen();
          return 0;

          //#ifdef DEBUGTRACEFILE
          //AppendDebugTraceFile("before Update_ZoneContrib2\n");
          //#endif

          //Update_ZoneContrib2(spno,iZoneCount,iZoneContrib2Count,ZoneContrib2,_ZoneContrib);  // zonecontribname is not null

          //#ifdef DEBUGTRACEFILE
          //AppendDebugTraceFile("after Update_ZoneContrib2\n");
          //#endif
       }
       else
       {
           if (strcmp("NULL",fnames.zonecontrib3name) != 0)
           {
              // error condition exists, both zonecontrib2 & zonecontrib3 are defined
              WriteStopErrorFile("both zonecontrib2 & zonecontrib3 are defined\n");
              #ifdef DEBUGTRACEFILE
              AppendDebugTraceFile("both zonecontrib2 & zonecontrib3 are defined\n");
              #endif
              ShowGenProg("both zonecontrib2 & zonecontrib3 are defined\n");
              ShowGenProgInfo("both zonecontrib2 & zonecontrib3 are defined\n");
              ShowShutdownScreen();
              return 0;
           }
           else
           {
               #ifdef DEBUGTRACEFILE
               AppendDebugTraceFile("before Build_ZoneContrib2\n");
               #endif

               Build_ZoneContrib2(spno,iZoneCount,iZoneContrib2Count,ZoneContrib2,&_ZoneContrib);  // zonecontribname is null

               #ifdef DEBUGTRACEFILE
               AppendDebugTraceFile("after Build_ZoneContrib2\n");
               #endif
           }
       }
       free(ZoneContrib);
    }
    if (strcmp("NULL",fnames.zonecontrib3name) != 0)
    {
       if (strcmp("NULL",fnames.zonecontribname) != 0)
       {
          // error condition exists, both zonecontrib & zonecontrib3 are defined
       }
       else
       {
           if (strcmp("NULL",fnames.zonecontrib2name) != 0)
           {
              // error condition exists, both zonecontrib2 & zonecontrib3 are defined
              WriteStopErrorFile("both zonecontrib2 & zonecontrib3 are defined\n");
              #ifdef DEBUGTRACEFILE
              AppendDebugTraceFile("both zonecontrib2 & zonecontrib3 are defined\n");
              #endif
              ShowGenProg("both zonecontrib2 & zonecontrib3 are defined\n");
              ShowGenProgInfo("both zonecontrib2 & zonecontrib3 are defined\n");
              ShowShutdownScreen();
              return 0;
           }
           else
           {
               #ifdef DEBUGTRACEFILE
               AppendDebugTraceFile("before Build_ZoneContrib3\n");
               #endif

               Build_ZoneContrib3(puno,spno,iZoneCount,iZoneContrib3Count,ZoneContrib3,&_ZoneContrib);
               free(ZoneContrib3);
               iZoneContrib3On = 1;

               #ifdef DEBUGTRACEFILE
               AppendDebugTraceFile("after Build_ZoneContrib3\n");
               #endif
           }
       }
    }
    if (strcmp("NULL",fnames.zonecontribname) == 0)
       if (strcmp("NULL",fnames.zonecontrib2name) == 0)
          if (strcmp("NULL",fnames.zonecontrib3name) == 0)
          {
             #ifdef DEBUGTRACEFILE
             AppendDebugTraceFile("before Default_ZoneContrib\n");
             #endif

             Default_ZoneContrib(spno,iZoneCount,&_ZoneContrib,iAvailableZoneInput);

             #ifdef DEBUGTRACEFILE
             AppendDebugTraceFile("after Default_ZoneContrib\n");
             #endif
          }

    if (strcmp("NULL",fnames.zonetargetname) == 0)
       if (strcmp("NULL",fnames.zonetarget2name) == 0)
       {
          ShowGenProg("Warning: No targets specified for zones.\n");

          #ifdef DEBUGTRACEFILE
          AppendDebugTraceFile("Warning: No targets specified for zones.\n");
          AppendDebugTraceFile("before Default_ZoneTarget\n");
          #endif

          Default_ZoneTarget(spno,iZoneCount,&_ZoneTarget);

          #ifdef DEBUGTRACEFILE
          AppendDebugTraceFile("after Default_ZoneTarget\n");
          #endif
       }

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("before Build_ZoneCost\n");
    #endif

    Build_ZoneCost(iCostCount,iZoneCount,iZoneCostCount,ZoneCost,&_ZoneCost);
    free(ZoneCost);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("after Build_ZoneCost\n");
    #endif

    if (strcmp("NULL",fnames.relconnectioncostname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before Build_RelConnectionCost\n");
       #endif

       Build_RelConnectionCost(iZoneCount,iRelConnectionCostCount,RelConnectionCost,&_RelConnectionCost);
       free(RelConnectionCost);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after Build_RelConnectionCost\n");
       #endif
    }
    else
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before Default_RelConnectionCost\n");
       #endif

       Default_RelConnectionCost(iZoneCount,&_RelConnectionCost);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after Default_RelConnectionCost\n");
       #endif
    }

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("before BuildZoneSpec\n");
    #endif

    BuildZoneSpec(spno,iZoneCount,&ZoneSpec);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("after BuildZoneSpec\n");
    AppendDebugTraceFile("before InitZoneSpec\n");
    #endif

    InitZoneSpec(spno,iZoneCount,ZoneSpec);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("after InitZoneSpec\n");
    #endif

    // parse PuLock and PuZone
    if (strcmp("NULL",fnames.pulockname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before ParsePuLock\n");
       #endif

       ParsePuLock(puno,pu,iPuLockCount,PuLock,PULookup);
       free(PuLock);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after ParsePuLock\n");
       #endif
    }
    if (strcmp("NULL",fnames.puzonename) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before ParsePuZone\n");
       #endif

       ParsePuZone(puno,pu,iPuZoneCount,PuZone,PULookup);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after ParsePuZone\n");
       AppendDebugTraceFile("before CheckPuZone\n");
       #endif

       CheckPuZone(puno,pu);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after CheckPuZone\n");
       #endif
    }

    #ifdef DEBUGTRACEFILE
    if (iVerbosity > 3)
    {
       Dump_ZoneContrib(puno,spno,spec,iZoneCount,_ZoneContrib,fnames);
       Dump_ZoneCost(iCostCount,iZoneCount,_ZoneCost,fnames);
       Dump_RelConnectionCost(iZoneCount,_RelConnectionCost,fnames);
       DumpZoneSpec(iMessageCounter,spno,iZoneCount,ZoneSpec,spec,fnames);
       DumpPuLockZone(puno,pu);
    }
    //TestFastPUIDtoPUINDEX(puno,PULookup,pu,fnames);
    //TestFastSPIDtoSPINDEX(spno,SPLookup,spec,fnames);
    #endif

    bestyet = (int *) calloc(puno,sizeof(int));

    R = (int *) calloc(puno,sizeof(int));

    // *******************************************************************************
    if (fnames.savesumsoln)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before InitSumSoln\n");
       #endif

       sumsoln = (int *) calloc(puno,sizeof(int));
       ZoneSumSoln = (int *) calloc(puno * iZoneCount,sizeof(int));
       InitSumSoln(puno,iZoneCount,sumsoln,ZoneSumSoln);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after InitSumSoln\n");
       #endif
    }

    connections = (typeconnection *) calloc(puno,sizeof(typeconnection));

    ShowDetProg("    Reading in the Connection file :\n");
    itemp = 0;
    if (strcmp("NULL",fnames.connectionname))
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before ReadConnections\n");
       #endif

       itemp = ReadConnections(puno,connections,verbose,pu,PULookup,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after ReadConnections\n");
       if (asymmetricconnectivity)
       {
          AppendDebugTraceFile("Asymmetric connectivity is on.\n");
          DumpAsymmetricConnectionFile(puno,connections,pu,fnames);
       }
       #endif
    }

    ShowGenProg("  %i connections entered \n",itemp);
    #ifdef ASYMCON
    if (asymmetricconnectivity)
       ShowGenProg("  Asymmetric connectivity is on.\n");
    #endif

    ShowDetProg("    Reading in the Planning Unit versus Species File \n");

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("before LoadSparseMatrix\n");
    #endif

    LoadSparseMatrix(&iSparseMatrixFileLength,&SM,puno,spno,pu,PULookup,SPLookup,fnames);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("after LoadSparseMatrix\n");
    #endif

    if (strcmp("NULL",fnames.matrixspordername) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before LoadSparseMatrix_sporder\n");
       #endif

       LoadSparseMatrix_sporder(&iSparseMatrixFileLength_sporder,&SMsporder,puno,spno,PULookup,SPLookup,fnames);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after LoadSparseMatrix_sporder\n");
       //Dump_specrichoff(spno,spec,fnames);
       //Dump_SparseMatrix_sporder(iSparseMatrixFileLength_sporder,SMsporder,pu,fnames);
       #endif

       #ifdef MEMDEBUG
       ShowGenProg("after LoadSparseMatrix_sporder\n");
       #endif
    }


    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("before CalcTotalAreas\n");
    CalcTotalAreas(puno,spno,pu,spec,SM);
    AppendDebugTraceFile("after CalcTotalAreas\n");
    #endif

    if (fnames.savetotalareas)
    {
       if (fnames.savetotalareas==3)
          sprintf(tempname2,"%s_totalareas.csv",savename);
       else
       if (fnames.savetotalareas==2)
          sprintf(tempname2,"%s_totalareas.txt",savename);
       else
           sprintf(tempname2,"%s_totalareas.dat",savename);

       OutputTotalAreas(puno,spno,pu,spec,SM,tempname2,fnames.savepenalty);
    }

    // finalise zone and non-zone targets now that matrix has been loaded
    if (fSpecPROPLoaded > 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before ApplySpecProp\n");
       #endif

       // species have prop value specified
       ApplySpecProp(spno,spec,puno,pu,SM);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after ApplySpecProp\n");
       #endif
    }
    if (strcmp("NULL",fnames.zonetargetname) != 0)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before Build_ZoneTarget\n");
       #endif

       Build_ZoneTarget(spno,iZoneCount,iZoneTargetCount,ZoneTarget,&_ZoneTarget,puno,pu,SM);
       free(ZoneTarget);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after Build_ZoneTarget\n");
       #endif
    }
    if (strcmp("NULL",fnames.zonetarget2name) != 0)
    {
       if (strcmp("NULL",fnames.zonetargetname) != 0)
       {
          #ifdef DEBUGTRACEFILE
          AppendDebugTraceFile("before Build_ZoneTarget2\n");
          #endif

          Build_ZoneTarget2(spno,iZoneCount,iZoneTarget2Count,ZoneTarget2,&_ZoneTarget,puno,pu,SM);

          #ifdef DEBUGTRACEFILE
          AppendDebugTraceFile("after Build_ZoneTarget2\n");
          #endif
       }
       else
       {
           #ifdef DEBUGTRACEFILE
           AppendDebugTraceFile("before Update_ZoneTarget2\n");
           #endif

           Update_ZoneTarget2(spno,iZoneCount,iZoneTarget2Count,ZoneTarget2,_ZoneTarget,puno,pu,SM);

           #ifdef DEBUGTRACEFILE
           AppendDebugTraceFile("after Update_ZoneTarget2\n");
           #endif
       }
       free(ZoneTarget2);
    }

    #ifdef DEBUGTRACEFILE
    if (iVerbosity > 3)
       Dump_ZoneTarget(spno,iZoneCount,_ZoneTarget,fnames);
    #endif

    if (style != 1)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before process block definitions\n");
       #endif

       if (strcmp("NULL",fnames.blockdefname) != 0)
       {
          ShowDetProg("    Reading in the Block Definition File \n");
          ReadGenSpeciesData(&gspno,&gspec,fnames);
          SetBlockDefs(gspno,spno,puno,gspec,spec,pu,SM);
       }

       SetDefs(spno,spec);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after process block definitions\n");
       #endif
    } // Read and process species block definitions

    ShowGenProgInfo("Checking to see if there are aggregating or separating species.\n");
    for (ipu=0;ipu<spno;ipu++)
    {
        if (spec[ipu].target2>0)
           aggexist = 1;
        if (spec[ipu].sepdistance > 0)
           sepexist = 1;
    }

    if (fnames.savesen)
    {
       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("before OutputScenario\n");
       #endif

       sprintf(tempname2,"%s_sen.dat",savename);
       OutputScenario(puno,spno,prop,anneal,seedinit,repeats,clumptype,
                     runopts,heurotype,costthresh,tpf1,tpf2,tempname2);

       #ifdef DEBUGTRACEFILE
       AppendDebugTraceFile("after OutputScenario\n");
       #endif
    }

    if (verbose > 1)
       ShowTimePassed();

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("before InitialiseReserve\n");
    #endif

    InitialiseReserve(puno,pu,R,iZoneCount,PuZone);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("after InitialiseReserve\n");
    #endif

    // * * *  Pre-processing    * * * * ***
    ShowGenProg("\nPre-processing Section. \n");
    ShowGenProgInfo("    Calculating all the penalties \n");

    if (strcmp("NULL",fnames.penaltyname) == 0)
    {
       if (strcmp("NULL",fnames.matrixspordername) == 0)
       {
          #ifdef DEBUGTRACEFILE
          AppendDebugTraceFile("before CalcPenalties\n");
          #endif

          // we don't have sporder matrix available, so use slow CalcPenalties method
          itemp = CalcPenalties(puno,spno,pu,spec,connections,SM,aggexist,clumptype,iZoneCount,R,_ZoneTarget);

          #ifdef DEBUGTRACEFILE
          AppendDebugTraceFile("after CalcPenalties\n");
          #endif
       }
       else
       {
           // we have sporder matrix available, so use optimised CalcPenalties method
           if (iOptimisationCalcPenalties == 1)
           {
              #ifdef DEBUGTRACEFILE
              AppendDebugTraceFile("before CalcPenaltiesOptimise\n");
              #endif

              itemp = CalcPenaltiesOptimise(puno,spno,pu,spec,connections,SM,SMsporder,R,aggexist,clumptype);

              #ifdef DEBUGTRACEFILE
              AppendDebugTraceFile("after CalcPenaltiesOptimise\n");
              #endif
           }
           else
           {
               #ifdef DEBUGTRACEFILE
               AppendDebugTraceFile("before CalcPenalties\n");
               #endif

               itemp = CalcPenalties(puno,spno,pu,spec,connections,SM,aggexist,clumptype,iZoneCount,R,_ZoneTarget);

               #ifdef DEBUGTRACEFILE
               AppendDebugTraceFile("after CalcPenalties\n");
               #endif
           }
       }
       if (itemp>0)
          ShowProg("%d species cannot meet target%c.\n",itemp,itemp==1? ' ':'s');
    }
    else
    {
        #ifdef DEBUGTRACEFILE
        AppendDebugTraceFile("before LoadPenalties\n");
        #endif

        LoadPenalty(spno,spec,fnames);

        #ifdef DEBUGTRACEFILE
        AppendDebugTraceFile("after LoadPenalties\n");
        #endif
    }

    if (runoptions.AnnealingOn)
    {
       ShowGenProgInfo("    Calculating temperatures.\n");
       if (!anneal.Titns)
          ShowErrorMessage("Initial Temperature is set to zero. Fatal Error \n");

       anneal.Tlen = anneal.iterations/anneal.Titns;
       ShowGenProgInfo("  Temperature length %d \n",anneal.Tlen);
       ShowGenProgInfo("  iterations %i, repeats %i \n",anneal.iterations,repeats);
    } // Annealing Preprocessing. Should be moved to SetAnnealingOptions

    if (fnames.savepenalty)
    {
       if (fnames.savepenalty==3)
          sprintf(tempname2,"%s_penalty.csv",savename);
       else
       if (fnames.savepenalty==2)
          sprintf(tempname2,"%s_penalty.txt",savename);
       else
           sprintf(tempname2,"%s_penalty.dat",savename);

       OutputPenalty(spno,spec,tempname2,fnames.savepenalty);
    }

    if (fnames.savespeciesdata)
    {
       sprintf(tempname2,"%s_spec.csv",savename);
       OutputSpeciesData(spno,spec,tempname2);
    }

    if (fnames.savesolutionsmatrix)
    {
       if (fnames.savesolutionsmatrix==3)
          sprintf(tempname2,"%s_solutionsmatrix.csv",savename);
       else
       if (fnames.savesolutionsmatrix==2)
          sprintf(tempname2,"%s_solutionsmatrix.txt",savename);
       else
           sprintf(tempname2,"%s_solutionsmatrix.dat",savename);

       InitSolutionsMatrix(puno,pu,tempname2,fnames.savesolutionsmatrix,fnames.solutionsmatrixheaders);

       for (i=1;i<=iZoneCount;i++)
       {
           // init solutions matrix for each zone separately
           if (fnames.savesolutionsmatrix==3)
              sprintf(tempname2,"%s_solutionsmatrix_zone%i.csv",savename,i);
           else
           if (fnames.savesolutionsmatrix==2)
              sprintf(tempname2,"%s_solutionsmatrix_zone%i.txt",savename,i);
           else
               sprintf(tempname2,"%s_solutionsmatrix_zone%i.dat",savename,i);

           InitSolutionsMatrix(puno,pu,tempname2,fnames.savesolutionsmatrix,fnames.solutionsmatrixheaders);
       }
    }

    //   The larger repetition loop
    for (irun = 1;irun <= repeats;irun++)
    {
        #ifdef DEBUGTRACEFILE
        sprintf(debugbuffer,"annealing start run %i\n",irun);
        AppendDebugTraceFile(debugbuffer);
        #endif

        ShowGenProg("\n");
        ShowProg("Run %i ",irun);

        if (runoptions.AnnealingOn)
        {
           if (anneal.type >= 2)
           {
              if (anneal.type == 2)
              {
                 #ifdef DEBUGTRACEFILE
                 sprintf(debugbuffer,"before ConnollyInit run %i\n",irun);
                 AppendDebugTraceFile(debugbuffer);
                 #endif

                 ConnollyInit(irun,puno,spno,pu,connections,spec,SM,&anneal,aggexist,R,prop,clumptype,iZoneCount,verbose);

                 #ifdef DEBUGTRACEFILE
                 sprintf(debugbuffer,"after ConnollyInit run %i\n",irun);
                 AppendDebugTraceFile(debugbuffer);
                 #endif
              }

              if (anneal.type == 3)
              {
                 #ifdef DEBUGTRACEFILE
                 sprintf(debugbuffer,"before AdaptiveInit run %i\n",irun);
                 AppendDebugTraceFile(debugbuffer);
                 #endif

                 AdaptiveInit(irun,puno,spno,prop,R,pu,connections,SM,spec,aggexist,&anneal,clumptype,iZoneCount);

                 #ifdef DEBUGTRACEFILE
                 sprintf(debugbuffer,"after AdaptiveInit run %i\n",irun);
                 AppendDebugTraceFile(debugbuffer);
                 #endif
              }

              ShowGenProg("  Using Calculated Tinit = %.4f Tcool = %.8f \n",
                          anneal.Tinit,anneal.Tcool);
           }  // Using Precalced Temperature Settings

           if (anneal.type == 3)
           {
              // Call annealing init here
           }  // using adaptive annealing type 2 here

           anneal.temp = anneal.Tinit;
        }  // Annealing Settup


        ShowGenProg("  creating the initial reserve \n");

        if (aggexist)
           ClearClumps(spno,spec,pu,SM);

        #ifdef DEBUGTRACEFILE
        sprintf(debugbuffer,"before ZonationCost run %i\n",irun);
        AppendDebugTraceFile(debugbuffer);
        #endif

        ZonationCost(irun,puno,spno,R,pu,connections,SM,spec,aggexist,&reserve,clumptype,prop,1);

        #ifdef DEBUGTRACEFILE
        sprintf(debugbuffer,"after ZonationCost run %i\n",irun);
        AppendDebugTraceFile(debugbuffer);
        #endif

        if (verbose > 1)
        {
           ShowGenProg("\n  Init:");
           PrintResVal(puno,spno,R,reserve,spec,misslevel);
        }

        if (verbose > 5)
        {
           ShowTimePassed();
        }

        // * * * * * * * * * * * * * * * * * * * ***
        // * * *  main annealing algorithm * * * * *
        // * * * * * * * * * * * * * * * * * * * ***

        if (runoptions.AnnealingOn)  // This should be moved to Annealing.c
        {
           #ifdef DEBUGTRACEFILE
           sprintf(debugbuffer,"before Annealing run %i\n",irun);
           AppendDebugTraceFile(debugbuffer);
           #endif

           Annealing(spno,puno,connections,R,spec,pu,SM,&change,&reserve,
                     repeats,irun,savename,verbose,misslevel,
                     aggexist,costthresh,tpf1,tpf2,clumptype,prop);

           #ifdef DEBUGTRACEFILE
           sprintf(debugbuffer,"after Annealing run %i\n",irun);
           AppendDebugTraceFile(debugbuffer);
           #endif
        }  // End of Annealing On

        if (runoptions.HeuristicOn)
        {
           #ifdef DEBUGTRACEFILE
           sprintf(debugbuffer,"before Heuristics run %i\n",irun);
           AppendDebugTraceFile(debugbuffer);
           #endif

           Heuristics(spno,puno,pu,connections,R,spec,SM,&reserve,
                      costthresh,tpf1,tpf2,heurotype,clumptype);

           if (verbose > 1 && (runopts == 2 || runopts == 5))
           {
              ShowGenProg("\n  Heuristic:");
              PrintResVal(puno,spno,R,reserve,spec,misslevel);
           }

           #ifdef DEBUGTRACEFILE
           sprintf(debugbuffer,"after Heuristics run %i\n",irun);
           AppendDebugTraceFile(debugbuffer);
           #endif
        }    // Activate Greedy

        if (runoptions.ItImpOn)
        {
           if (iOptimisationIterativeImprovement == 1)
           {
              #ifdef DEBUGTRACEFILE
              sprintf(debugbuffer,"before IterativeImprovementOptimise run %i\n",irun);
              AppendDebugTraceFile(debugbuffer);
              #endif

              IterativeImprovementOptimise(puno,pu,connections,spec,SM,R,
                                            &reserve,&change,costthresh,tpf1,tpf2,clumptype,irun,savename);

              if (itimptype == 3)
                 IterativeImprovementOptimise(puno,pu,connections,spec,SM,R,
                                               &reserve,&change,costthresh,tpf1,tpf2,clumptype,irun,savename);

              #ifdef DEBUGTRACEFILE
              sprintf(debugbuffer,"after IterativeImprovementOptimise run %i\n",irun);
              AppendDebugTraceFile(debugbuffer);
              #endif
           }
           else
           {
               #ifdef DEBUGTRACEFILE
               sprintf(debugbuffer,"before IterativeImprovement run %i\n",irun);
               AppendDebugTraceFile(debugbuffer);
               #endif

               IterativeImprovement(puno,pu,connections,spec,SM,R,
                                     &reserve,&change,costthresh,tpf1,tpf2,clumptype,itimptype);
               if (itimptype == 3)
                  IterativeImprovement(puno,pu,connections,spec,SM,R,
                                    &reserve,&change,costthresh,tpf1,tpf2,clumptype,1);

               #ifdef DEBUGTRACEFILE
               sprintf(debugbuffer,"after IterativeImprovement run %i\n",irun);
               AppendDebugTraceFile(debugbuffer);
               #endif
           }

           if (aggexist)
              ClearClumps(spno,spec,pu,SM);

           if (verbose > 1)
           {
              ShowGenProg("  Iterative Improvement:");
              PrintResVal(puno,spno,R,reserve,spec,misslevel);
           }
        } // Activate Iterative Improvement

        #ifdef DEBUGTRACEFILE
        sprintf(debugbuffer,"before file output run %i\n",irun);
        AppendDebugTraceFile(debugbuffer);
        #endif

        if (fnames.saverun)
        {
           if (fnames.saverun == 3)
              sprintf(tempname2,"%s_r%05i.csv",savename,irun%10000);
           else
           if (fnames.saverun == 2)
              sprintf(tempname2,"%s_r%05i.txt",savename,irun%10000);
           else
               sprintf(tempname2,"%s_r%05i.dat",savename,irun%10000);

           OutputSolution(puno,R,pu,tempname2,fnames.saverun);
        }

        #ifdef DEBUGFPERROR
        sprintf(debugbuffer,"OutputSolution ran\n");
        AppendDebugTraceFile(debugbuffer);
        #endif

        if (fnames.savespecies && fnames.saverun)
        {
           if (fnames.savespecies == 3)
              sprintf(tempname2,"%s_mv%05i.csv",savename,irun%10000);
           else
           if (fnames.savespecies == 2)
              sprintf(tempname2,"%s_mv%05i.txt",savename,irun%10000);
           else
               sprintf(tempname2,"%s_mv%05i.dat",savename,irun%10000);

           OutputFeatures(spno,spec,tempname2,fnames.savespecies,misslevel);
        }
        #ifdef DEBUGFPERROR
        sprintf(debugbuffer,"OutputFeatures ran\n");
        AppendDebugTraceFile(debugbuffer);
        #endif

        if (fnames.savesum)
        {
           if (fnames.savesum==3)
              sprintf(tempname2,"%s_sum.csv",savename);
           else
           if (fnames.savesum==2)
              sprintf(tempname2,"%s_sum.txt",savename);
           else
               sprintf(tempname2,"%s_sum.dat",savename);

           if (irun == 1)
              OutputSummary(puno,spno,R,spec,reserve,irun,tempname2,misslevel,fnames.savesum);
           else
               OutputSummary(puno,spno,R,spec,reserve,irun,tempname2,misslevel,fnames.savesum);
        }
        #ifdef DEBUGFPERROR
        sprintf(debugbuffer,"OutputSummary ran\n");
        AppendDebugTraceFile(debugbuffer);
        #endif

        // Saving the best from all the runs
        if (fnames.savebest)
        {
           ZonationCost(irun,puno,spno,R,pu,connections,SM,spec,aggexist,&change,clumptype,prop,0);

           if (irun == 1)
           {
              rBestScore = change.total;
              memcpy(bestyet,R,sizeof(int)* puno);
              iBestRun = irun;

              if (verbose >1)
              {
                 ShowGenProg("  Best:");
                 PrintResVal(puno,spno,bestyet,change,spec,misslevel);
              }
           }
           else
           {
               if (change.total <= rBestScore)
               {
                  rBestScore = change.total;
                  memcpy(bestyet,R,sizeof(int)* puno);
                  iBestRun = irun;

                  if (verbose >1)
                  {
                     ShowGenProg("  Best:");
                     PrintResVal(puno,spno,bestyet,change,spec,misslevel);
                  }
               }
           }
        }
        #ifdef DEBUGFPERROR
        sprintf(debugbuffer,"ZonationCost ran\n");
        AppendDebugTraceFile(debugbuffer);
        #endif

        if (fnames.savesumsoln) // Add current run to my summed solution
           for (ipu=0;ipu<puno;ipu++)
               if (R[ipu] > 0)
               {
                  sumsoln[ipu] += 1;
                  iZoneSumSolnIndex = (puno * (R[ipu]-1)) + ipu;
                  ZoneSumSoln[iZoneSumSolnIndex] += 1;
               }

        if (fnames.savesolutionsmatrix)
        {
           if (fnames.savesolutionsmatrix==3)
              sprintf(tempname2,"%s_solutionsmatrix.csv",savename);
           else
           if (fnames.savesolutionsmatrix==2)
              sprintf(tempname2,"%s_solutionsmatrix.txt",savename);
           else
               sprintf(tempname2,"%s_solutionsmatrix.dat",savename);

           AppendSolutionsMatrix(irun,puno,R,tempname2,fnames.savesolutionsmatrix,fnames.solutionsmatrixheaders);

           for (i=1;i<=iZoneCount;i++)
           {
               // append solutions matrix for each zone separately
               if (fnames.savesolutionsmatrix==3)
                  sprintf(tempname2,"%s_solutionsmatrix_zone%i.csv",savename,i);
               else
               if (fnames.savesolutionsmatrix==2)
                  sprintf(tempname2,"%s_solutionsmatrix_zone%i.txt",savename,i);
               else
                   sprintf(tempname2,"%s_solutionsmatrix_zone%i.dat",savename,i);

               AppendSolutionsMatrixZone(i,irun,puno,R,tempname2,fnames.savesolutionsmatrix,fnames.solutionsmatrixheaders);
           }
        }

        if (fnames.savezoneconnectivitysum)
        {
           if (fnames.savezoneconnectivitysum == 3)
              sprintf(tempname2,"%s_zoneconnectivitysum%05i.csv",savename,irun%10000);
           else
           if (fnames.savezoneconnectivitysum == 2)
              sprintf(tempname2,"%s_zoneconnectivitysum%05i.txt",savename,irun%10000);
           else
               sprintf(tempname2,"%s_zoneconnectivitysum%05i.dat",savename,irun%10000);

           OutputZoneConnectivitySum(puno,R,tempname2,fnames.savezoneconnectivitysum);
        }

        if (aggexist)
           ClearClumps(spno,spec,pu,SM);

        #ifdef DEBUGTRACEFILE
        sprintf(debugbuffer,"after file output run %i\n",irun);
        AppendDebugTraceFile(debugbuffer);
        sprintf(debugbuffer,"annealing end run %i\n",irun);
        AppendDebugTraceFile(debugbuffer);
        #endif

        if (marxanisslave == 1)
           WriteSlaveSyncFileRun(irun);

        if (verbose > 1)
           ShowTimePassed();

    } // ** the repeats  **

    #ifdef DEBUGTRACEFILE
    sprintf(debugbuffer,"before final file output\n");
    AppendDebugTraceFile(debugbuffer);
    #endif

    if (fnames.savebest)
    {
       if (fnames.savebest == 3)
          sprintf(tempname2,"%s_best.csv",savename);
       else
       if (fnames.savebest == 2)
          sprintf(tempname2,"%s_best.txt",savename);
       else
           sprintf(tempname2,"%s_best.dat",savename);
       OutputSolution(puno,bestyet,pu,tempname2,fnames.savebest);

       #ifdef DEBUGTRACEFILE
       sprintf(debugbuffer,"Best solution is run %i\n",iBestRun);
       AppendDebugTraceFile(debugbuffer);
       #endif

       ShowGenProg("\nBest solution is run %i\n",iBestRun);

       if (fnames.savezoneconnectivitysum)
       {
          if (fnames.savezoneconnectivitysum == 3)
             sprintf(tempname2,"%s_zoneconnectivitysumbest.csv",savename);
          else
              if (fnames.savezoneconnectivitysum == 2)
                 sprintf(tempname2,"%s_zoneconnectivitysumbest.txt",savename);
              else
                  sprintf(tempname2,"%s_zoneconnectivitysumbest.dat",savename);

          OutputZoneConnectivitySum(puno,bestyet,tempname2,fnames.savezoneconnectivitysum);
       }

       if (fnames.savespecies)
       {
          if (fnames.savespecies ==3)
             sprintf(tempname2,"%s_mvbest.csv",savename);
          else
          if (fnames.savespecies ==2)
             sprintf(tempname2,"%s_mvbest.txt",savename);
          else
              sprintf(tempname2,"%s_mvbest.dat",savename);

          OutputFeatures(spno,spec,tempname2,fnames.savespecies,misslevel);
       }
    }

    if (fnames.savesumsoln)
    {
       if (fnames.savesumsoln == 3)
          sprintf(tempname2,"%s_ssoln.csv",savename);
       else
       if (fnames.savesumsoln == 2)
          sprintf(tempname2,"%s_ssoln.txt",savename);
       else
           sprintf(tempname2,"%s_ssoln.dat",savename);

       OutputSumSoln(puno,sumsoln,ZoneSumSoln,R,pu,tempname2,fnames.savesumsoln);
    }

    if (aggexist)
       ClearClumps(spno,spec,pu,SM);  // ** Remove these pointers for cleanliness sake **

    // free datastructures used
    free(_ZoneTarget);
    free(_ZoneContrib);
    free(TotalAreas);
    //free(PuZone);

    ShowShutdownScreen();

    if (fnames.savelog)
       SetLogFile(0,NULL);  /* tidy up files */

    #ifdef DEBUGTRACEFILE
    sprintf(debugbuffer,"end final file output\n");
    AppendDebugTraceFile(debugbuffer);
    AppendDebugTraceFile("\nMarxan with Zones end execution\n");
    #endif

    return 0;

}   // * * * * Main     * * * *

// * * * * * * * * * * * * * * * * * * * *****
// * * * * PreProcessing Section * * * * *****
// * * * * * * * * * * * * * * * * * * * *****

// * * *  Block Definitions * * * * * * * * **
// ** Sets the block definitions for various types **

void LoadZones(int *iZoneCount,struct stringname *Zones[],struct sfname fnames)
{
     FILE *fp;
     char *readname, *writename, sLine[5000], *sVarVal;
     int i = 0, iLineCount = 0;
     char debugbuffer[1000];

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.zonesname) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.zonesname);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open Zones file %s\n",readname);

     // count the number of records
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the zones array
     *iZoneCount = iLineCount;
     *Zones = (struct stringname *) calloc(iLineCount,sizeof(struct stringname));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           // read the integer id from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*Zones)[i].id);

           // read the string name from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           strcpy((*Zones)[i].name,sVarVal);

           i++;
     }
     fclose(fp);

     free(readname);
}

void DefaultZones(int *iZoneCount,struct stringname *Zones[])
{
     // create the zones array
     *iZoneCount = 2;
     *Zones = (struct stringname *) calloc(2,sizeof(struct stringname));

     (*Zones)[0].id = 1;
     (*Zones)[1].id = 2;

     strcpy((*Zones)[0].name,"available");
     strcpy((*Zones)[1].name,"reserved");
}

void DumpZoneNames(int iZoneCount,struct stringname Zones[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneNames.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneNames.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneNames file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneid,zonename\n");

     for (i=0;i<iZoneCount;i++)
     {
         fprintf(fp,"%d,%s\n",Zones[i].id,Zones[i].name);
     }

     fclose(fp);
}

void LoadCostNames(int *iCostCount,struct stringname *CostNames[],struct sfname fnames)
{

     FILE *fp;
     char *readname, *writename, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.costsname) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.costsname);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open CostNames file %s\n",readname);

     // count the number of records
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the CostNames array
     *iCostCount = iLineCount;
     *CostNames = (struct stringname *) calloc(iLineCount,sizeof(struct stringname));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*CostNames)[i].id);

           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           strcpy((*CostNames)[i].name,sVarVal);
           i++;
     }
     fclose(fp);

     free(readname);
}

void DefaultCostNames(int *iCostCount,struct stringname *CostNames[])
{
     *iCostCount = 1;
     *CostNames = (struct stringname *) calloc(1,sizeof(struct stringname));

     (*CostNames)[0].id = 1;

     strcpy((*CostNames)[0].name,"cost");
}

void DumpCostNames(int iCostCount,struct stringname CostNames[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugCostNames.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugCostNames.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpCostNames file %s\n",writename);
     free(writename);

     fprintf(fp,"costid,costname\n");

     for (i=0;i<iCostCount;i++)
     {
         fprintf(fp,"%d,%s\n",CostNames[i].id,CostNames[i].name);
     }

     fclose(fp);
}

int rtnCostIndex(int iCostCount,struct stringname CostNames[],char *sFieldName)
{
    // returns -1 if sFieldName is not a cost in CostNames array, else returns zero-based index of matching cost name
    int i, iReturn = -1;

    for (i=0;i<iCostCount;i++)
    {
        if (strcmp(sFieldName,CostNames[i].name) == 0)
           iReturn = i;
    }

    return iReturn;
}

void LoadCostValues(int iCostCount,double **CostValues,struct stringname CostNames[],struct sfname fnames,int puno)
{

     FILE *fp;
     char *readname, *writename, sLine[1000], sFields[1000], *sVarVal, sField[1000];
     int i, iLine = 0, /*iLineCount = 0,*/ iFieldCount = 0, *CostFieldNumber, iCostIndex;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.puname) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.puname);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open Cost Values file %s\n",readname);

     fgets(sLine,999,fp);
     strcpy(sFields,sLine);
     // count the number of fields
     strtok(sFields," ,;:^*\"/|\t\'\\\n");
     iFieldCount++;
     while ((sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n")) != NULL)
           iFieldCount++;
     // determine the field number for each of our costs
     i = 0;
     strcpy(sFields,sLine);
     CostFieldNumber = (int *) calloc(iFieldCount,sizeof(int));
     sVarVal = strtok(sFields," ,;:^*\"/|\t\'\\\n");
     CostFieldNumber[i] = rtnCostIndex(iCostCount,CostNames,sVarVal);
     while ((sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n")) != NULL)
     {
           i++;
           CostFieldNumber[i] = rtnCostIndex(iCostCount,CostNames,sVarVal);
     }
     #ifdef DEBUGTRACEFILE
     if (iVerbosity > 3)
        DumpCostFieldNumber(iFieldCount,CostFieldNumber,sLine,fnames);
     #endif

     // load the cost value data to an array from the pu.dat file
     while (fgets(sLine,999,fp))
     {
           i = 0;
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           if (CostFieldNumber[i] != -1)
              sscanf(sVarVal, "%lf", &CostValues[puno-iLine-1][CostFieldNumber[i]]);
           while ((sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n")) != NULL)
           {
                 i++;
                 if (CostFieldNumber[i] != -1)
                    sscanf(sVarVal,"%lf",&CostValues[puno-iLine-1][CostFieldNumber[i]]);
           }
           iLine++;
     }
     fclose(fp);

     free(readname);
     free(CostFieldNumber);
}

void DumpCostFieldNumber(int iFieldCount,int CostFieldNumber[],char *sFields,struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugCostFieldNumber.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugCostFieldNumber.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpCostFieldNumber file %s\n",writename);
     free(writename);

     fprintf(fp,"%s",sFields);
     for (i=0;i<iFieldCount;i++)
     {
         fprintf(fp,"%d",CostFieldNumber[i]);
         if (i != (iFieldCount-1))
            fprintf(fp,",");
     }
     fprintf(fp,"\n");

     fclose(fp);
}

void DumpCostValues(int iCostCount, int puno,double **CostValues,struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i,j;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugCostValues.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugCostValues.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpCostValues file %s\n",writename);
     free(writename);

     fprintf(fp,"puindex");
     for (j=0;j<iCostCount;j++)
         fprintf(fp,",%d",j);
     fprintf(fp,"\n");

     for (i=0;i<puno;i++)
     {
         fprintf(fp,"%d,",i);

         for (j=0;j<iCostCount;j++)
         {
             fprintf(fp,"%lf",CostValues[i][j]);
             if (j != (iCostCount-1))
                fprintf(fp,",");
         }
         fprintf(fp,"\n");
     }
     fclose(fp);
}

void LoadZoneContrib(int *iZoneContribCount,struct zonecontribstruct *ZoneContrib[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.zonecontribname) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.zonecontribname);
     fp = fopen(readname,"r");
     if (fp==NULL)
         ShowErrorMessage("cannot open ZoneContrib file %s\n",readname);

     // count the number of record
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the ZoneContrib array
     *iZoneContribCount = iLineCount;
     *ZoneContrib = (struct zonecontribstruct *) calloc(iLineCount,sizeof(struct zonecontribstruct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           // read the integer zoneid from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneContrib)[i].zoneid);

           // read the integer speciesid from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneContrib)[i].speciesid);

           // read the double fraction from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%lf", &(*ZoneContrib)[i].fraction);

           i++;
     }
     fclose(fp);

     free(readname);
}

void LoadZoneContrib2(int *iZoneContrib2Count,struct zonecontrib2struct *ZoneContrib2[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.zonecontrib2name) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.zonecontrib2name);
     fp = fopen(readname,"r");
     if (fp==NULL)
         ShowErrorMessage("cannot open ZoneContrib2 file %s\n",readname);

     // count the number of record
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the ZoneContrib array
     *iZoneContrib2Count = iLineCount;
     *ZoneContrib2 = (struct zonecontrib2struct *) calloc(iLineCount,sizeof(struct zonecontrib2struct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           // read the integer zoneid from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneContrib2)[i].zoneid);

           // read the double fraction from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%lf", &(*ZoneContrib2)[i].fraction);

           i++;
     }
     fclose(fp);

     free(readname);
}

void LoadZoneContrib3(int *iZoneContrib3Count,struct zonecontrib3struct *ZoneContrib3[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.zonecontrib3name) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.zonecontrib3name);
     fp = fopen(readname,"r");
     if (fp==NULL)
         ShowErrorMessage("cannot open ZoneContrib3 file %s\n",readname);

     // count the number of record
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the ZoneContrib array
     *iZoneContrib3Count = iLineCount;
     *ZoneContrib3 = (struct zonecontrib3struct *) calloc(iLineCount,sizeof(struct zonecontrib3struct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           // read the integer zoneid from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneContrib3)[i].zoneid);

           // read the integer puid from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneContrib3)[i].puid);

           // read the integer speciesid from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneContrib3)[i].speciesid);

           // read the double fraction from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%lf", &(*ZoneContrib3)[i].fraction);

           i++;
     }
     fclose(fp);

     free(readname);
}

void Build_ZoneContrib(int spno,int iZoneCount,int iZoneContribCount,struct zonecontribstruct ZoneContrib[],double *_ZoneContrib[])
{
     int i,j,iArraySize,iSpeciesIndex;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneContrib start\n");
     #endif

     // create and initialise _ZoneContrib
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Build_ZoneContrib spno %i iZoneCount %i iArraySize %i iZoneContribCount %i\n",spno,iZoneCount,iArraySize,iZoneContribCount);
     AppendDebugTraceFile(debugbuffer);
     #endif

     *_ZoneContrib = (double *) calloc(iArraySize,sizeof(double));
     for (j=0;j<spno;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             (*_ZoneContrib)[(j*iZoneCount)+i] = 0;
         }
     }

     // populate _ZoneContrib from ZoneContrib
     for (i=0;i<iZoneContribCount;i++)
     {
         // .zoneid .speciesid .fraction

         iSpeciesIndex = FastSPIDtoSPINDEX(spno,ZoneContrib[i].speciesid,SPLookup);

         (*_ZoneContrib)[(iSpeciesIndex*iZoneCount)+(ZoneContrib[i].zoneid-1)] = ZoneContrib[i].fraction;
     }

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneContrib end\n");
     #endif
}

void Build_ZoneContrib2(int spno,int iZoneCount,int iZoneContrib2Count,struct zonecontrib2struct ZoneContrib2[],double *_ZoneContrib[])
{
     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneContrib2 start\n");
     #endif

     // create and initialise _ZoneContrib
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Build_ZoneContrib2 spno %i iZoneCount %i iArraySize %i iZoneContrib2Count %i\n",spno,iZoneCount,iArraySize,iZoneContrib2Count);
     AppendDebugTraceFile(debugbuffer);
     #endif

     *_ZoneContrib = (double *) calloc(iArraySize,sizeof(double));
     for (j=0;j<spno;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             (*_ZoneContrib)[(j*iZoneCount)+i] = 0;
         }
     }

     // populate _ZoneContrib from ZoneContrib
     for (i=0;i<iZoneContrib2Count;i++)
         for (j=0;j<spno;j++)
         {
             // .zoneid .speciesid .fraction
             (*_ZoneContrib)[(j*iZoneCount)+(ZoneContrib2[i].zoneid-1)] = ZoneContrib2[i].fraction;
         }

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneContrib2 end\n");
     #endif
}

void Update_ZoneContrib2(int spno,int iZoneCount,int iZoneContrib2Count,struct zonecontrib2struct ZoneContrib2[],double _ZoneContrib[])
{
     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Update_ZoneContrib2 start\n");
     #endif

     // create and initialise _ZoneContrib
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Update_ZoneContrib2 spno %i iZoneCount %i iArraySize %i iZoneContrib2Count %i\n",spno,iZoneCount,iArraySize,iZoneContrib2Count);
     AppendDebugTraceFile(debugbuffer);
     #endif

     // populate _ZoneContrib from ZoneContrib
     for (i=0;i<iZoneContrib2Count;i++)
         for (j=0;j<spno;j++)
         {
             #ifdef DEBUGTRACEFILE
             sprintf(debugbuffer,"Update_ZoneContrib2 i %i j %i fraction %f _ZCidx %i\n",i,j,ZoneContrib2[i].fraction,(j*iZoneCount)+(ZoneContrib2[i].zoneid-1));
             AppendDebugTraceFile(debugbuffer);
             #endif
             // .zoneid .speciesid .fraction
             _ZoneContrib[(j*iZoneCount)+(ZoneContrib2[i].zoneid-1)] = ZoneContrib2[i].fraction;
         }

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Update_ZoneContrib2 end\n");
     #endif
}

void Build_ZoneContrib3(int puno,int spno,int iZoneCount,int iZoneContrib3Count,struct zonecontrib3struct ZoneContrib3[],double *_ZoneContrib[])
{
     int i,j,k,iArraySize,iSpeciesIndex,iPUIndex;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneContrib3 start\n");
     #endif

     // create and initialise _ZoneContrib
     rArraySize = puno * spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Build_ZoneContrib3 puno %i spno %i iZoneCount %i iArraySize %i iZoneContrib3Count %i\n",puno,spno,iZoneCount,iArraySize,iZoneContrib3Count);
     AppendDebugTraceFile(debugbuffer);
     #endif

     *_ZoneContrib = (double *) calloc(iArraySize,sizeof(double));
     for (i=0;i<iArraySize;i++)
         (*_ZoneContrib)[i] = 0;

     // populate _ZoneContrib from ZoneContrib
     for (i=0;i<iZoneContrib3Count;i++)
     {
         iSpeciesIndex = FastSPIDtoSPINDEX(spno,ZoneContrib3[i].speciesid,SPLookup);
         iPUIndex = FastPUIDtoPUINDEX(puno,ZoneContrib3[i].puid,PULookup);
         // .zoneid .speciesid .fraction
         (*_ZoneContrib)[(iSpeciesIndex*puno*iZoneCount)+(iPUIndex*iZoneCount)+(ZoneContrib3[i].zoneid-1)] = ZoneContrib3[i].fraction;

         //_ZoneContrib index = (zero_base_feature_index * number_of_planning_units * number_of_zones) +
         //                     (zero_base_planning_unit_index * number_of_zones) +
         //                     zero_base_zone_index
     }

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneContrib3 end\n");
     #endif
}

void Default_ZoneContrib(int spno,int iZoneCount,double *_ZoneContrib[],int iAvailableZoneFromInput)
{
     // neither zonecontrib.dat or zonecontrib2.dat exist so we are using defaults of 1 for each zone and species

     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Default_ZoneContrib start\n");
     #endif

     // create and initialise _ZoneContrib
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"Default_ZoneContrib spno %i iZoneCount %i iArraySize %i\n",spno,iZoneCount,iArraySize);
     //AppendDebugTraceFile(debugbuffer);
     #endif

     *_ZoneContrib = (double *) calloc(iArraySize,sizeof(double));
     for (j=0;j<spno;j++)
     {
         for (i=0;i<iZoneCount;i++)
             if (iAvailableZoneFromInput == (i+1))
             {
                (*_ZoneContrib)[(j*iZoneCount)+i] = 0;
             }
             else
             {
                (*_ZoneContrib)[(j*iZoneCount)+i] = 1;
             }
     }

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Default_ZoneContrib end\n");
     #endif
}

void Dump_ZoneContrib(int puno,int spno,typesp spec[],int iZoneCount,double _ZoneContrib[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i,j,k;
     char messagebuffer[1000];

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Dump_ZoneContrib start\n");
     #endif

     if (strcmp("NULL",fnames.zonecontrib3name) != 0)
     {
        for (i=0;i<iZoneCount;i++)
        {
            // create one debug file for each zone. Each file is a 2d matrix of puXfeature
            sprintf(messagebuffer,"%i",i+1);
            writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug_Zone_Contrib.csv") + 5, sizeof(char));
            strcpy(writename,fnames.outputdir);
            strcat(writename,"debug_Zone");
            strcat(writename,messagebuffer);
            strcat(writename,"_Contrib.csv");
            fp = fopen(writename,"w");
            if (fp==NULL)
               ShowErrorMessage("cannot create Dump_ZoneContrib file %s\n",writename);
            free(writename);

            // header row
            fprintf(fp,"pu/feature");
            for (j=0;j<spno;j++)
                fprintf(fp,",%i",(j+1));
            fprintf(fp,"\n");

            for (k=0;k<puno;k++)
            {
                fprintf(fp,"%i",k+1);

                for (j=0;j<spno;j++)
                {
                    fprintf(fp,",%lf",_ZoneContrib[(j*puno*iZoneCount)+(k*iZoneCount)+i]);

                    //_ZoneContrib index = (zero_base_feature_index * number_of_planning_units * number_of_zones) +
                    //                     (zero_base_planning_unit_index * number_of_zones) +
                    //                     zero_base_zone_index
                }

                fprintf(fp,"\n");
            }

            fclose(fp);
        }
     }
     else
     {
         writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug_ZoneContrib.csv") + 2, sizeof(char));
         strcpy(writename,fnames.outputdir);
         strcat(writename,"debug_ZoneContrib.csv");
         fp = fopen(writename,"w");
         if (fp==NULL)
            ShowErrorMessage("cannot create Dump_ZoneContrib file %s\n",writename);
         free(writename);

         // header row
         fprintf(fp,"spname,spindex");
         for (i=0;i<iZoneCount;i++)
             fprintf(fp,",contrib%i",(i+1));
         fprintf(fp,"\n");

         for (j=0;j<spno;j++)
         {
             fprintf(fp,"%i,%i",spec[j].name,j);

             for (i=0;i<iZoneCount;i++)
                 fprintf(fp,",%lf",_ZoneContrib[(j*iZoneCount)+i]);

             fprintf(fp,"\n");
         }

         fclose(fp);
     }

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Dump_ZoneContrib end\n");
     #endif
}

void DumpZoneContrib(int iZoneContribCount,struct zonecontribstruct ZoneContrib[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneContrib.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneContrib.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneContrib file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneid,speciesid,fraction\n");

     for (i=0;i<iZoneContribCount;i++)
     {
         fprintf(fp,"%d,%d,%lf\n",ZoneContrib[i].zoneid,ZoneContrib[i].speciesid,ZoneContrib[i].fraction);
     }

     fclose(fp);
}

void DumpZoneContrib2(int iZoneContrib2Count,struct zonecontrib2struct ZoneContrib2[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneContrib2.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneContrib2.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneContrib2 file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneid,fraction\n");

     for (i=0;i<iZoneContrib2Count;i++)
     {
         fprintf(fp,"%d,%lf\n",ZoneContrib2[i].zoneid,ZoneContrib2[i].fraction);
     }

     fclose(fp);
}

void DumpZoneContrib3(int iZoneContrib3Count,struct zonecontrib3struct ZoneContrib3[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneContrib3.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneContrib3.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneContrib3 file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneid,puid,speciesid,fraction\n");

     for (i=0;i<iZoneContrib3Count;i++)
     {
         fprintf(fp,"%d,%d,%d,%lf\n",ZoneContrib3[i].zoneid,ZoneContrib3[i].puid,ZoneContrib3[i].speciesid,ZoneContrib3[i].fraction);
     }

     fclose(fp);
}

void LoadZoneTarget(int *iZoneTargetCount,struct zonetargetstruct *ZoneTarget[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.zonetargetname) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.zonetargetname);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open ZoneTarget file %s\n",readname);

     // count the number of record
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the ZoneTarget array
     *iZoneTargetCount = iLineCount;
     *ZoneTarget = (struct zonetargetstruct *) calloc(iLineCount,sizeof(struct zonetargetstruct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     // does the table have a 4th column called prop?

     while (fgets(sLine,999,fp))
     {
           // read the integer zoneid from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneTarget)[i].zoneid);

           // read the integer speciesid from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneTarget)[i].speciesid);

           // read the double fraction from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%lf", &(*ZoneTarget)[i].target);

           // read the integer targettype from this line if it exists
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           if (sVarVal == NULL)
              (*ZoneTarget)[i].targettype = 0;
           else
               sscanf(sVarVal, "%d", &(*ZoneTarget)[i].targettype);

           i++;
     }
     fclose(fp);

     free(readname);
}

void LoadZoneTarget2(int *iZoneTarget2Count,struct zonetarget2struct *ZoneTarget2[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.zonetarget2name) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.zonetarget2name);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open ZoneTarget2 file %s\n",readname);

     // count the number of record
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the ZoneTarget array
     *iZoneTarget2Count = iLineCount;
     *ZoneTarget2 = (struct zonetarget2struct *) calloc(iLineCount,sizeof(struct zonetarget2struct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     // does the table have a 4th column called prop?

     while (fgets(sLine,999,fp))
     {
           // read the integer zoneid from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneTarget2)[i].zoneid);

           // read the double fraction from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%lf", &(*ZoneTarget2)[i].target);

           // read the integer targettype from this line if it exists
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           if (sVarVal == NULL)
              (*ZoneTarget2)[i].targettype = 0;
           else
               sscanf(sVarVal, "%i", &(*ZoneTarget2)[i].targettype);

           i++;
     }
     fclose(fp);

     free(readname);
}

void Build_ZoneTarget(int spno, int iZoneCount,int iZoneTargetCount,struct zonetargetstruct ZoneTarget[],
                      struct _zonetargetstruct *_ZoneTarget[],int puno,struct spustuff pu[],struct spu SM[])
{
     int i,j,iArraySize,iSpeciesIndex;
     double rArraySize;
     char debugbuffer[1000];
     type_zonetarget _ZT;
     double *SpecArea;
     int *SpecOcc;

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneTarget start\n");
     #endif

     // create and initialise _ZoneTarget
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Build_ZoneTarget spno %i iZoneCount %i iArraySize %i iZoneTargetCount %i\n",spno,iZoneCount,iArraySize,iZoneTargetCount);
     AppendDebugTraceFile(debugbuffer);
     #endif

     // init arrays of species area and occurrence totals
     SpecArea = (double *) calloc(spno,sizeof(double));
     SpecOcc = (int *) calloc(spno,sizeof(int));
     for (i=0;i<spno;i++)
     {
         SpecArea[i] = 0;
         SpecOcc[i] = 0;
     }

     // find species totals from the matrix
     for (i=0;i<puno;i++)
         if (pu[i].richness > 0)
            for (j=0;j<pu[i].richness;j++)
            {
                SpecArea[SM[pu[i].offset + j].spindex] += SM[pu[i].offset + j].amount;
                SpecOcc[SM[pu[i].offset + j].spindex]++;
            }

     *_ZoneTarget = (struct _zonetargetstruct *) calloc(iArraySize,sizeof(struct _zonetargetstruct));

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("_ZoneTarget created\n");
     #endif

     for (j=0;j<spno;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             #ifdef DEBUGTRACEFILE
             //sprintf(debugbuffer,"Build_ZoneTarget init _ZoneTarget i %i j %i\n",i,j);
             //AppendDebugTraceFile(debugbuffer);
             #endif

             (*_ZoneTarget)[(j*iZoneCount)+i].target = 0;
             (*_ZoneTarget)[(j*iZoneCount)+i].occurrence = 0;
         }
     }

     // populate _ZoneTarget from ZoneTarget
     for (i=0;i<iZoneTargetCount;i++)
     {
         #ifdef DEBUGTRACEFILE
         //sprintf(debugbuffer,"Build_ZoneTarget i %i\n",i);
         //AppendDebugTraceFile(debugbuffer);
         //sprintf(debugbuffer,"Build_ZoneTarget i %i ZoneTarget[i].speciesid %i ZoneTarget[i].zoneid %i ZoneTarget[i].target %f ZoneTarget[i].targettype \n",
         //                    i,ZoneTarget[i].speciesid,ZoneTarget[i].zoneid,ZoneTarget[i].target);//,       ZoneTarget[i].targettype);
         //AppendDebugTraceFile(debugbuffer);
         #endif

         iSpeciesIndex = FastSPIDtoSPINDEX(spno,ZoneTarget[i].speciesid,SPLookup);

         // .zoneid .speciesid .target
         if (ZoneTarget[i].targettype == 0)  // area target as hectare
            (*_ZoneTarget)[(iSpeciesIndex*iZoneCount)+(ZoneTarget[i].zoneid-1)].target = ZoneTarget[i].target;
         if (ZoneTarget[i].targettype == 1)  // area target as proportion
            (*_ZoneTarget)[(iSpeciesIndex*iZoneCount)+(ZoneTarget[i].zoneid-1)].target = ZoneTarget[i].target * SpecArea[iSpeciesIndex];
         if (ZoneTarget[i].targettype == 2)  // occurrence target as occurrences
            (*_ZoneTarget)[(iSpeciesIndex*iZoneCount)+(ZoneTarget[i].zoneid-1)].occurrence = ceil(ZoneTarget[i].target);
         if (ZoneTarget[i].targettype == 3)  // occurrence target as proportion
            (*_ZoneTarget)[(iSpeciesIndex*iZoneCount)+(ZoneTarget[i].zoneid-1)].occurrence = ceil(ZoneTarget[i].target * SpecOcc[iSpeciesIndex]);

         //if ((*_ZoneTarget)[((ZoneTarget[i].speciesid-1)*iZoneCount)+(ZoneTarget[i].zoneid-1)].target > SpecArea[ZoneTarget[i].speciesid-1])
         //   ShowGenProgInfo("Species %d (%s) cannot reach target %.2f in zone %i there is only %.2f available.\n",
         //            spec[i].name,spec[i].sname,spec[i].target,ZoneTarget[i].zoneid,ftarget);

         #ifdef DEBUGTRACEFILE
         //sprintf(debugbuffer,"Build_ZoneTarget i %i .speciesid %i .zoneid %i .fraction %f\n",i,ZoneTarget[i].speciesid,ZoneTarget[i].zoneid,ZoneTarget[i].target);
         //AppendDebugTraceFile(debugbuffer);
         #endif
     }

     // destroy arrays of species area and occurrence totals
     free(SpecArea);
     free(SpecOcc);

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneTarget end\n");
     #endif
}

void Build_ZoneTarget2(int spno, int iZoneCount,int iZoneTarget2Count,struct zonetarget2struct ZoneTarget2[],
                      struct _zonetargetstruct *_ZoneTarget[],int puno,struct spustuff pu[],struct spu SM[])
{
     // this function is called when zonetarget2.dat exists but zonetarget.dat does not exist
     int i,j,iArraySize;//,iSpeciesIndex;
     double rArraySize;
     char debugbuffer[1000];
     type_zonetarget _ZT;
     double *SpecArea;
     int *SpecOcc;

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneTarget2 start\n");
     #endif

     // create and initialise _ZoneTarget
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Build_ZoneTarget spno %i iZoneCount %i iArraySize %i iZoneTarget2Count %i\n",spno,iZoneCount,iArraySize,iZoneTarget2Count);
     AppendDebugTraceFile(debugbuffer);
     #endif

     // init arrays of species area and occurrence totals
     SpecArea = (double *) calloc(spno,sizeof(double));
     SpecOcc = (int *) calloc(spno,sizeof(int));
     for (i=0;i<spno;i++)
     {
         SpecArea[i] = 0;
         SpecOcc[i] = 0;
     }

     // find species totals from the matrix
     for (i=0;i<puno;i++)
         if (pu[i].richness > 0)
            for (j=0;j<pu[i].richness;j++)
            {
                SpecArea[SM[pu[i].offset + j].spindex] += SM[pu[i].offset + j].amount;
                SpecOcc[SM[pu[i].offset + j].spindex]++;
            }

     *_ZoneTarget = (struct _zonetargetstruct *) calloc(iArraySize,sizeof(struct _zonetargetstruct));

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("_ZoneTarget created\n");
     #endif

     for (j=0;j<spno;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             #ifdef DEBUGTRACEFILE
             //sprintf(debugbuffer,"Build_ZoneTarget init _ZoneTarget i %i j %i\n",i,j);
             //AppendDebugTraceFile(debugbuffer);
             #endif

             (*_ZoneTarget)[(j*iZoneCount)+i].target = 0;
             (*_ZoneTarget)[(j*iZoneCount)+i].occurrence = 0;
         }
     }

     // populate _ZoneTarget from ZoneTarget
     for (i=0;i<iZoneTarget2Count;i++)
         for (j=0;j<spno;j++)
         {
             #ifdef DEBUGTRACEFILE
             //sprintf(debugbuffer,"Build_ZoneTarget i %i\n",i);
             //AppendDebugTraceFile(debugbuffer);
             //sprintf(debugbuffer,"Build_ZoneTarget i %i ZoneTarget[i].speciesid %i ZoneTarget[i].zoneid %i ZoneTarget[i].target %f\n",i,ZoneTarget[i].speciesid,ZoneTarget[i].zoneid,ZoneTarget[i].target);
             //AppendDebugTraceFile(debugbuffer);
             #endif

             //iSpeciesIndex = FastSPIDtoSPINDEX(spno,ZoneTarget[i].speciesid,SPLookup);

             // .zoneid .speciesid .target
             if (ZoneTarget2[i].targettype == 0)  // area target as hectare
                (*_ZoneTarget)[(j*iZoneCount)+(ZoneTarget2[i].zoneid-1)].target = ZoneTarget2[i].target;
             if (ZoneTarget2[i].targettype == 1)  // area target as proportion
                (*_ZoneTarget)[(j*iZoneCount)+(ZoneTarget2[i].zoneid-1)].target = ZoneTarget2[i].target * SpecArea[j];
             if (ZoneTarget2[i].targettype == 2)  // occurrence target as occurrences
                (*_ZoneTarget)[(j*iZoneCount)+(ZoneTarget2[i].zoneid-1)].occurrence = ceil(ZoneTarget2[i].target);
             if (ZoneTarget2[i].targettype == 3)  // occurrence target as proportion
                (*_ZoneTarget)[(j*iZoneCount)+(ZoneTarget2[i].zoneid-1)].occurrence = ceil(ZoneTarget2[i].target * SpecOcc[j]);

             #ifdef DEBUGTRACEFILE
             //sprintf(debugbuffer,"Build_ZoneTarget i %i .speciesid %i .zoneid %i .fraction %f\n",i,ZoneTarget[i].speciesid,ZoneTarget[i].zoneid,ZoneTarget[i].target);
             //AppendDebugTraceFile(debugbuffer);
             #endif
         }

     // destroy arrays of species area and occurrence totals
     free(SpecArea);
     free(SpecOcc);

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneTarget2 end\n");
     #endif
}

void Update_ZoneTarget2(int spno, int iZoneCount,int iZoneTarget2Count,struct zonetarget2struct ZoneTarget2[],
                      struct _zonetargetstruct _ZoneTarget[],int puno,struct spustuff pu[],struct spu SM[])
{
     // this function is called when zonetarget2.dat exists but zonetarget.dat does not exist
     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];
     type_zonetarget _ZT;
     double *SpecArea;
     int *SpecOcc;

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Update_ZoneTarget2 start\n");
     #endif

     // create and initialise _ZoneTarget
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Update_ZoneTarget2 spno %i iZoneCount %i iArraySize %i iZoneTarget2Count %i\n",spno,iZoneCount,iArraySize,iZoneTarget2Count);
     AppendDebugTraceFile(debugbuffer);
     #endif

     // init arrays of species area and occurrence totals
     SpecArea = (double *) calloc(spno,sizeof(double));
     SpecOcc = (int *) calloc(spno,sizeof(int));
     for (i=0;i<spno;i++)
     {
         SpecArea[i] = 0;
         SpecOcc[i] = 0;
     }

     // find species totals from the matrix
     for (i=0;i<puno;i++)
         if (pu[i].richness > 0)
            for (j=0;j<pu[i].richness;j++)
            {
                SpecArea[SM[pu[i].offset + j].spindex] += SM[pu[i].offset + j].amount;
                SpecOcc[SM[pu[i].offset + j].spindex]++;
            }

     // populate _ZoneTarget from ZoneTarget
     for (i=0;i<iZoneTarget2Count;i++)
         for (j=0;j<spno;j++)
         {
             #ifdef DEBUGTRACEFILE
             //sprintf(debugbuffer,"Build_ZoneTarget i %i\n",i);
             //AppendDebugTraceFile(debugbuffer);
             //sprintf(debugbuffer,"Build_ZoneTarget i %i ZoneTarget[i].speciesid %i ZoneTarget[i].zoneid %i ZoneTarget[i].target %f\n",i,ZoneTarget[i].speciesid,ZoneTarget[i].zoneid,ZoneTarget[i].target);
             //AppendDebugTraceFile(debugbuffer);
             #endif

             // .zoneid .speciesid .target
             if (ZoneTarget2[i].targettype == 0)  // area target as hectare
                _ZoneTarget[(j*iZoneCount)+(ZoneTarget2[i].zoneid-1)].target = ZoneTarget2[i].target;
             if (ZoneTarget2[i].targettype == 1)  // area target as proportion
                _ZoneTarget[(j*iZoneCount)+(ZoneTarget2[i].zoneid-1)].target = ZoneTarget2[i].target * SpecArea[j];
             if (ZoneTarget2[i].targettype == 2)  // occurrence target as occurrences
                _ZoneTarget[(j*iZoneCount)+(ZoneTarget2[i].zoneid-1)].occurrence = ceil(ZoneTarget2[i].target);
             if (ZoneTarget2[i].targettype == 3)  // occurrence target as proportion
                _ZoneTarget[(j*iZoneCount)+(ZoneTarget2[i].zoneid-1)].occurrence = ceil(ZoneTarget2[i].target * SpecOcc[j]);

             #ifdef DEBUGTRACEFILE
             //sprintf(debugbuffer,"Build_ZoneTarget i %i .speciesid %i .zoneid %i .fraction %f\n",i,ZoneTarget[i].speciesid,ZoneTarget[i].zoneid,ZoneTarget[i].target);
             //AppendDebugTraceFile(debugbuffer);
             #endif
         }

     // destroy arrays of species area and occurrence totals
     free(SpecArea);
     free(SpecOcc);

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Update_ZoneTarget2 end\n");
     #endif
}

void Default_ZoneTarget(int spno, int iZoneCount,struct _zonetargetstruct *_ZoneTarget[])
{
     // neither the zonetarget.dat or zonetarget2.dat files exist so set default values to zero for zone targets

     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];
     type_zonetarget _ZT;

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Default_ZoneTarget start\n");
     #endif

     // create and initialise _ZoneTarget
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"Default_ZoneTarget spno %i iZoneCount %i iArraySize %i\n",spno,iZoneCount,iArraySize);
     //AppendDebugTraceFile(debugbuffer);
     #endif

     *_ZoneTarget = (struct _zonetargetstruct *) calloc(iArraySize,sizeof(struct _zonetargetstruct));

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("_ZoneTarget created\n");
     #endif

     for (j=0;j<spno;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             #ifdef DEBUGTRACEFILE
             //sprintf(debugbuffer,"Build_ZoneTarget init _ZoneTarget i %i j %i\n",i,j);
             //AppendDebugTraceFile(debugbuffer);
             #endif

             (*_ZoneTarget)[(j*iZoneCount)+i].target = 0;
             (*_ZoneTarget)[(j*iZoneCount)+i].occurrence = 0;
         }
     }

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Default_ZoneTarget end\n");
     #endif
}

void Dump_ZoneTarget(int spno,int iZoneCount,struct _zonetargetstruct _ZoneTarget[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i,j;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"Dump_ZoneTarget start iZoneCount %i\n",iZoneCount);
     //AppendDebugTraceFile(debugbuffer);
     #endif

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug_ZoneTarget.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debug_ZoneTarget.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create Dump_ZoneTarget file %s\n",writename);
     free(writename);

     // write header row
     fprintf(fp,"spname,spindex,");
     for (i=0;i<iZoneCount;i++)
     {
         fprintf(fp,"zone%dtarget,zone%doccurrence",i+1,i+1);
         if (i != (iZoneCount-1))
            fprintf(fp,",");
     }
     fprintf(fp,"\n");

     for (j=0;j<spno;j++)
     {
         fprintf(fp,"%i,%i,",spec[j].name,j);
         for (i=0;i<iZoneCount;i++)
         {
             #ifdef DEBUGTRACEFILE
             //sprintf(debugbuffer,"Dump_ZoneTarget j %i i %i index %i\n",j,i,(j*iZoneCount)+i);
             //AppendDebugTraceFile(debugbuffer);
             #endif

             fprintf(fp,"%lf,%i",_ZoneTarget[(j*iZoneCount)+i].target,_ZoneTarget[(j*iZoneCount)+i].occurrence);
             if (i != (iZoneCount-1))
                fprintf(fp,",");
         }
         fprintf(fp,"\n");
     }

     fclose(fp);

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Dump_ZoneTarget end\n");
     #endif
}

void DumpZoneTarget(int iZoneTargetCount,struct zonetargetstruct ZoneTarget[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneTarget.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneTarget.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneTarget file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneid,speciesid,target,targettype\n");

     for (i=0;i<iZoneTargetCount;i++)
     {
         fprintf(fp,"%d,%d,%lf,%d\n",ZoneTarget[i].zoneid,ZoneTarget[i].speciesid,ZoneTarget[i].target,ZoneTarget[i].targettype);
     }

     fclose(fp);
}

void DumpZoneTarget2(int iZoneTarget2Count,struct zonetarget2struct ZoneTarget2[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneTarget2.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneTarget2.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneTarget2 file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneid,target,targettype\n");

     for (i=0;i<iZoneTarget2Count;i++)
     {
         fprintf(fp,"%d,%lf,%d\n",ZoneTarget2[i].zoneid,ZoneTarget2[i].target,ZoneTarget2[i].targettype);
     }

     fclose(fp);
}

void LoadZoneCost(int *iZoneCostCount,struct zonecoststruct *ZoneCost[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.zonecostname) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.zonecostname);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open ZoneCost file %s\n",readname);

     // count the number of records
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the ZoneCost array
     *iZoneCostCount = iLineCount;
     *ZoneCost = (struct zonecoststruct *) calloc(iLineCount,sizeof(struct zonecoststruct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           // read the integer zoneid from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneCost)[i].zoneid);

           // read the integer costid from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*ZoneCost)[i].costid);

           // read the double fraction from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%lf", &(*ZoneCost)[i].fraction);

           i++;
     }
     fclose(fp);

     free(readname);
}

void DefaultZoneCost(int *iZoneCostCount,struct zonecoststruct *ZoneCost[])
{
     // create the ZoneCost array
     *iZoneCostCount = 1;
     *ZoneCost = (struct zonecoststruct *) calloc(1,sizeof(struct zonecoststruct));

     (*ZoneCost)[0].zoneid = 2;

     (*ZoneCost)[0].costid = 1;

     (*ZoneCost)[0].fraction = 1;
}

void Build_ZoneCost(int iCostCount,int iZoneCount,int iZoneCostCount,struct zonecoststruct ZoneCost[],double *_ZoneCost[])
{
     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Build_ZoneCost start\n");
     #endif

     // create and initialise _ZoneCost
     rArraySize = iCostCount * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"Build_ZoneCost iCostCount %i iZoneCount %i iArraySize %i iZoneCostCount %i\n",iCostCount,iZoneCount,iArraySize,iZoneCostCount);
     //AppendDebugTraceFile(debugbuffer);
     #endif

     *_ZoneCost = (double *) calloc(iArraySize,sizeof(double));
     for (j=0;j<iCostCount;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             (*_ZoneCost)[(j*iZoneCount)+i] = 0;
         }
     }

     // populate _ZoneCost from ZoneCost
     for (i=0;i<iZoneCostCount;i++)
     {
         // .zoneid .costid .fraction
         (*_ZoneCost)[((ZoneCost[i].costid-1)*iZoneCount)+(ZoneCost[i].zoneid-1)] = ZoneCost[i].fraction;

         #ifdef DEBUGTRACEFILE
         //sprintf(debugbuffer,"Build_ZoneCost i %i\n",i);
         //AppendDebugTraceFile(debugbuffer);
         //sprintf(debugbuffer,"Build_ZoneCost i %i .costid %i .zoneid %i .fraction %f\n",i,ZoneCost[i].costid,ZoneCost[i].zoneid,ZoneCost[i].fraction);
         //AppendDebugTraceFile(debugbuffer);
         #endif
     }
     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Build_ZoneCost end\n");
     #endif
}

void Dump_ZoneCost(int iCostCount,int iZoneCount,double _ZoneCost[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i,j;

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Dump_ZoneCost start\n");
     #endif

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug_ZoneCost.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debug_ZoneCost.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create Dump_ZoneCost file %s\n",writename);
     free(writename);

     fprintf(fp,"costindex");
     for (i=0;i<iZoneCount;i++)
         fprintf(fp,",%d",i);
     fprintf(fp,"\n");

     for (j=0;j<iCostCount;j++)
     {
         fprintf(fp,"%d",j);

         for (i=0;i<iZoneCount;i++)
         {
             fprintf(fp,",%lf",_ZoneCost[(j*iZoneCount)+i]);
         }
         fprintf(fp,"\n");
     }

     fclose(fp);

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("Dump_ZoneCost end\n");
     #endif
}

void DumpZoneCost(int iZoneCostCount,struct zonecoststruct ZoneCost[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneCost.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneCost.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneCost file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneid,costid,fraction\n");

     for (i=0;i<iZoneCostCount;i++)
     {
         fprintf(fp,"%d,%d,%lf\n",ZoneCost[i].zoneid,ZoneCost[i].costid,ZoneCost[i].fraction);
     }

     fclose(fp);
}

void LoadPuLock(int *iPuLockCount,struct pulockstruct *PuLock[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.pulockname) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.pulockname);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open PuLock file %s\n",readname);

     // count the number of records
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the PuLock array
     *iPuLockCount = iLineCount;
     *PuLock = (struct pulockstruct *) calloc(iLineCount,sizeof(struct pulockstruct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           // read the integer puid from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*PuLock)[i].puid);

           // read the integer zoneid from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*PuLock)[i].zoneid);

           i++;
     }
     fclose(fp);

     free(readname);
}

void DumpPuLock(int iPuLockCount,struct pulockstruct PuLock[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugPuLock.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugPuLock.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpPuLock file %s\n",writename);
     free(writename);

     fprintf(fp,"puid,zoneid\n");

     for (i=0;i<iPuLockCount;i++)
     {
         fprintf(fp,"%d,%d\n",PuLock[i].puid,PuLock[i].zoneid);
     }

     fclose(fp);
}

void LoadPuZone(int *iPuZoneCount,struct puzonestruct *PuZone[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.puzonename) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.puzonename);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open PuZone file %s\n",readname);

     // count the number of records
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the PuLock array
     *iPuZoneCount = iLineCount;
     *PuZone = (struct puzonestruct *) calloc(iLineCount,sizeof(struct puzonestruct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           // read the integer puid from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*PuZone)[i].puid);

           // read the integer zoneid from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*PuZone)[i].zoneid);

           i++;
     }
     fclose(fp);

     free(readname);
}

void DumpPuZone(int iPuZoneCount,struct puzonestruct PuZone[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugPuZone.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugPuZone.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpPuZone file %s\n",writename);
     free(writename);

     fprintf(fp,"puid,zoneid\n");

     for (i=0;i<iPuZoneCount;i++)
     {
         fprintf(fp,"%d,%d\n",PuZone[i].puid,PuZone[i].zoneid);
     }

     fclose(fp);
}

void DumpPuZone_Debug(int iPuZoneCount,struct puzonestruct PuZone[],struct sfname fnames,int iMessage)
{
     FILE *fp;
     char *writename;
     char messagebuffer[1000];
     int i;

     sprintf(messagebuffer,"%i",iMessage);

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugPuZone_.csv") + strlen(messagebuffer) + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugPuZone_");
     strcat(writename,messagebuffer);
     strcat(writename,".csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpPuZone file %s\n",writename);
     free(writename);

     fprintf(fp,"puid,zoneid\n");

     for (i=0;i<iPuZoneCount;i++)
     {
         fprintf(fp,"%d,%d\n",PuZone[i].puid,PuZone[i].zoneid);
     }

     fclose(fp);
}

void LoadRelConnectionCost(int *iRelConnectionCostCount,struct relconnectioncoststruct *RelConnectionCost[],struct sfname fnames)
{

     FILE *fp;
     char *readname, sLine[1000], *sVarVal;
     int i = 0, iLineCount = 0, id;

     readname = (char *) calloc(strlen(fnames.inputdir) + strlen(fnames.relconnectioncostname) + 2, sizeof(char));
     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.relconnectioncostname);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("cannot open RelConnectionCost file %s\n",readname);

     // count the number of records
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
           iLineCount++;
     fclose(fp);

     // create the RelConnectionCost array
     *iRelConnectionCostCount = iLineCount;
     *RelConnectionCost = (struct relconnectioncoststruct *) calloc(iLineCount,sizeof(struct relconnectioncoststruct));

     // load the data to an array
     fp = fopen(readname,"r");
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           // read the integer zoneid1 from this line
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*RelConnectionCost)[i].zoneid1);

           // read the integer zoneid2 from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%d", &(*RelConnectionCost)[i].zoneid2);

           // read the double fraction from this line
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal, "%lf", &(*RelConnectionCost)[i].fraction);

           i++;
     }
     fclose(fp);

     free(readname);
}

void DumpRelConnectionCost(int iRelConnectionCostCount,struct relconnectioncoststruct RelConnectionCost[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneConnectionCost.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneConnectionCost.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneConnectionCost file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneid1,zoneid2,fraction\n");

     for (i=0;i<iRelConnectionCostCount;i++)
     {
         fprintf(fp,"%d,%d,%lf\n",RelConnectionCost[i].zoneid1,RelConnectionCost[i].zoneid2,RelConnectionCost[i].fraction);
     }

     fclose(fp);
}

void Build_RelConnectionCost(int iZoneCount,int iRelConnectionCostCount,struct relconnectioncoststruct RelConnectionCost[],double *_RelConnectionCost[])
{
     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneConnectionCost start\n");
     #endif

     // create and initialise _RelConnectionCost
     rArraySize = iZoneCount * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Build_ZoneConnectionCost iZoneCount %i iArraySize %i iZoneConnectionCostCount %i\n",iZoneCount,iArraySize,iRelConnectionCostCount);
     AppendDebugTraceFile(debugbuffer);
     #endif

     // set all values to the default value of 0 before applying user options
     *_RelConnectionCost = (double *) calloc(iArraySize,sizeof(double));
     for (j=0;j<iZoneCount;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             (*_RelConnectionCost)[(j*iZoneCount)+i] = 0;
         }
     }

     // populate _RelConnectionCost from RelConnectionCost
     for (i=0;i<iRelConnectionCostCount;i++)
     {
         // .zoneid .costid .fraction
         (*_RelConnectionCost)[((RelConnectionCost[i].zoneid1-1)*iZoneCount)+(RelConnectionCost[i].zoneid2-1)] = RelConnectionCost[i].fraction;
         (*_RelConnectionCost)[((RelConnectionCost[i].zoneid2-1)*iZoneCount)+(RelConnectionCost[i].zoneid1-1)] = RelConnectionCost[i].fraction;

         #ifdef DEBUGTRACEFILE
         //sprintf(debugbuffer,"Build_RelConnectionCost i %i\n",i);
         //AppendDebugTraceFile(debugbuffer);
         //sprintf(debugbuffer,"Build_RelConnectionCost i %i .zoneid1 %i .zoneid2 %i .fraction %f\n",i,RelConnectionCost[i].zoneid1,RelConnectionCost[i].zoneid2,RelConnectionCost[i].fraction);
         //AppendDebugTraceFile(debugbuffer);
         #endif
     }
     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Build_ZoneConnectionCost end\n");
     #endif
}

void Default_RelConnectionCost(int iZoneCount,double *_RelConnectionCost[])
{
     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Default_RelConnectionCost start\n");
     #endif

     // create and initialise _RelConnectionCost
     rArraySize = iZoneCount * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Default_RelConnectionCost iZoneCount %i iArraySize %i\n",iZoneCount,iArraySize);
     AppendDebugTraceFile(debugbuffer);
     #endif

     // set all values to the default value of 1
     *_RelConnectionCost = (double *) calloc(iArraySize,sizeof(double));
     for (j=0;j<iZoneCount;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             (*_RelConnectionCost)[(j*iZoneCount)+i] = 0;
             if (j == 0)//((j == 0) && (i != 0))
                (*_RelConnectionCost)[(j*iZoneCount)+i] = 1;
             if (i == 0)//((i == 0) && (j != 0))
                (*_RelConnectionCost)[(j*iZoneCount)+i] = 1;
         }
     }

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Default_RelConnectionCost end\n");
     #endif
}

void Dump_RelConnectionCost(int iZoneCount,double _RelConnectionCost[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i,j;

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Dump_ZoneConnectionCost start\n");
     #endif

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug_ZoneConnectionCost.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debug_ZoneConnectionCost.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create Dump_ZoneConnectionCost file %s\n",writename);
     free(writename);

     fprintf(fp,"zoneindex");
     for (j=0;j<iZoneCount;j++)
         fprintf(fp,",%d",j);
     fprintf(fp,"\n");

     for (j=0;j<iZoneCount;j++)
     {
         fprintf(fp,"%d",j);

         for (i=0;i<iZoneCount;i++)
         {
             fprintf(fp,",%lf",_RelConnectionCost[(j*iZoneCount)+i]);
         }
         fprintf(fp,"\n");
     }

     fclose(fp);

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("Dump_ZoneConnectionCost end\n");
     #endif
}

void BuildZoneSpec(int spno,int iZoneCount,struct zonespecstruct *ZoneSpec[])
{
     int i,j,iArraySize;
     double rArraySize;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("BuildZoneSpec start\n");
     #endif

     // create ZoneSpec
     rArraySize = spno * iZoneCount;
     iArraySize = floor(rArraySize);

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"BuildZoneSpec spno %i iZoneCount %i iArraySize %i\n",spno,iZoneCount,iArraySize);
     //AppendDebugTraceFile(debugbuffer);
     #endif

     *ZoneSpec = (struct zonespecstruct *) calloc(iArraySize,sizeof(struct zonespecstruct));

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("BuildZoneSpec end\n");
     #endif
}

void InitZoneSpec(int spno,int iZoneCount,struct zonespecstruct ZoneSpec[])
{
     int i,j;
     char debugbuffer[1000];

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("InitZoneSpec start\n");
     #endif

     for (j=0;j<spno;j++)
     {
         for (i=0;i<iZoneCount;i++)
         {
             ZoneSpec[(j*iZoneCount)+i].amount = 0;
             ZoneSpec[(j*iZoneCount)+i].occurrence = 0;
         }
     }

     #ifdef DEBUGTRACEFILE
     //AppendDebugTraceFile("InitZoneSpec end\n");
     #endif
}

void DumpZoneSpec(int iMessage,int spno,int iZoneCount,struct zonespecstruct ZoneSpec[],struct sspecies spec[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i,j;
     char messagebuffer[1000];
     char debugbuffer[1000];

     sprintf(messagebuffer,"%i",iMessage);

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"DumpZoneSpec %i start\n",iMessage);
     //AppendDebugTraceFile(debugbuffer);
     #endif

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugZoneSpec_.csv") + strlen(messagebuffer) + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugZoneSpec_");
     strcat(writename,messagebuffer);
     strcat(writename,".csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpZoneSpec file %s\n",writename);
     free(writename);

     // write header row, species identifier is spec.name integer
     fprintf(fp,"spname,spindex,amount,occurrence,");
     for (i=0;i<iZoneCount;i++)
     {
         fprintf(fp,"amount%i,occurrence%i",(i+1),(i+1));
         if (i != (iZoneCount-1))
            fprintf(fp,",");
     }
     fprintf(fp,"\n");

     for (j=0;j<spno;j++)
     {
         fprintf(fp,"%i,%i,%lf,%i,",spec[j].name,j,spec[j].amount,spec[j].occurrence);

         for (i=0;i<iZoneCount;i++)
         {
             fprintf(fp,"%lf,%i",ZoneSpec[(j*iZoneCount)+i].amount,ZoneSpec[(j*iZoneCount)+i].occurrence);
             if (i != (iZoneCount-1))
                fprintf(fp,",");
         }
         fprintf(fp,"\n");
     }

     fclose(fp);

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"DumpZoneSpec %i end\n",iMessage);
     //AppendDebugTraceFile(debugbuffer);
     #endif
}

#ifdef DEBUGTRACEFILE
void DumpR(int iMessage,char sMessage[],int puno,int reservedarray[],struct spustuff pu[],struct sfname fnames)
{
     FILE *fp;
     char *writename;
     int i;
     char messagebuffer[1000];
     char debugbuffer[1000];

     sprintf(messagebuffer,"%s%i",sMessage,iMessage);

     sprintf(debugbuffer,"DumpR %s %i\n",sMessage,iMessage);
     AppendDebugTraceFile(debugbuffer);

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugR_.csv") + strlen(messagebuffer) + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugR_");
     strcat(writename,messagebuffer);
     strcat(writename,".csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpR file %s\n",writename);
     free(writename);

     // write header row
     fprintf(fp,"puid,reservedarray\n");

     for (i=0;i<puno;i++)
     {
         fprintf(fp,"%i,%i\n",pu[i].id,reservedarray[i]);
     }

     fclose(fp);

     sprintf(debugbuffer,"DumpR %s %i end\n",sMessage,iMessage);
     AppendDebugTraceFile(debugbuffer);
}
#endif

void InitSumSoln(int puno,int iZoneCount,int sumsoln[],int ZoneSumSoln[])
{
     int i,j;

     for (i=0;i<puno;i++)
     {
         sumsoln[i] = 0;

         for (j=0;j<iZoneCount;j++)
             ZoneSumSoln[(puno*j) + i] = 0;
     }
}

void DumpFileNames(struct sfname fnames)
{
     FILE *fp;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugFileNames.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugFileNames.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpFileNames file %s\n",writename);
     free(writename);

     fprintf(fp,"input name,file name\n");

     fprintf(fp,"zonesname,%s\n",fnames.zonesname);
     fprintf(fp,"costsname,%s\n",fnames.costsname);
     fprintf(fp,"zonecontribname,%s\n",fnames.zonecontribname);
     fprintf(fp,"zonecontrib2name,%s\n",fnames.zonecontrib2name);
     fprintf(fp,"zonecontrib3name,%s\n",fnames.zonecontrib3name);
     fprintf(fp,"zonetargetname,%s\n",fnames.zonetargetname);
     fprintf(fp,"zonetarget2name,%s\n",fnames.zonetarget2name);
     fprintf(fp,"zonecostname,%s\n",fnames.zonecostname);
     fprintf(fp,"pulockname,%s\n",fnames.pulockname);
     fprintf(fp,"puzonename,%s\n",fnames.puzonename);
     fprintf(fp,"zoneconnectioncostname,%s\n",fnames.relconnectioncostname);

     fclose(fp);
}

// test the function rtnAmountSpecAtPu
void TestrtnAmountSpecAtPu(int puno, int spno, struct spustuff pu[], struct sspecies spec[], struct spu SM[],
                           struct sfname fnames)
{
     FILE *fp;
     int i,j;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugTestrtnAmountSpecAtPu.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugTestrtnAmountSpecAtPu.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create TestrtnAmountSpecAtPu file %s\n",writename);
     free(writename);
     fputs("puindex,specindex,puname,specname,amount\n",fp);
     for (i=0;i<puno;i++)
         for (j=0;j<spno;j++)
             fprintf(fp,"%d,%d,%d,%d,%g\n",i,j,pu[i].id,spec[j].name,rtnAmountSpecAtPu(pu,SM,i,j));

     fclose(fp);
}

// test the function rtnAmountSpecAtPuZone
//void TestrtnAmountSpecAtPuZone(int puno, int spno, struct spustuff pu[], struct sspecies spec[], struct spu SM[],
//                               struct sfname fnames)
//{
//     FILE *fp;
//     int i,j,k;
//     char *writename;
//     double rAmount;

//     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugTestrtnAmountSpecAtPuZone.csv") + 2, sizeof(char));
//     strcpy(writename,fnames.outputdir);
//     strcat(writename,"debugTestrtnAmountSpecAtPuZone.csv");
//     fp = fopen(writename,"w");
//     if (fp==NULL)
//        ShowErrorMessage("cannot create TestrtnAmountSpecAtPuZone file %s\n",writename);
//     free(writename);
//     fputs("zoneindex,puindex,specindex,zoneid,puid,spid,amount\n",fp);
//     for (i=0;i<puno;i++)
//         for (j=0;j<spno;j++)
//             for (k=0;k<iZoneCount;k++)
//             {
//               rAmount = rtnAmountSpecAtPuZone(k,pu,SM,i,j);
//               if (rAmount > 0)
//                    fprintf(fp,"%d,%d,%d,%d,%d,%d,%g\n",k,i,j,Zones[k].id,pu[i].id,spec[j].name,rAmount);
//           }

//     fclose(fp);
//}

// returns the 0-base index of a species at a planning unit, if the species doesn't occur here, returns -1
int rtnIdxSpecAtPu(struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex)
{
    int i;
    //int iTop, iBottom, iCentre, iCount;

    if (PU[iPUIndex].richness > 0)
       //if (PU[iPUIndex].richness < 100)
       {
          for (i=0;i<PU[iPUIndex].richness;i++)
              if (SM[PU[iPUIndex].offset + i].spindex == iSpecIndex)
                 return (PU[iPUIndex].offset + i);
       }
       /*
       else
       {
       // use a binary search to find the index of the species at the planning unit
       // the species index are sorted from highest to lowest

          iTop = 0;
          iBottom = PU[iPUIndex].richness - 1;
          iCentre = iTop + floor(PU[iPUIndex].richness / 2);
          iCount = PU[iPUIndex].richness;

          while ((iTop <= iBottom) && (SM[PU[iPUIndex].offset + iCentre].spindex != iSpecIndex))
          {
                if (iSpecIndex > SM[PU[iPUIndex].offset + iCentre].spindex)
                {
                    iBottom = iCentre - 1;
                   iCount = iBottom - iTop + 1;
                   iCentre = iTop + floor(iCount / 2);
                }
                else
                {
                   iTop = iCentre + 1;
                   iCount = iBottom - iTop + 1;
                   iCentre = iTop + floor(iCount / 2);
                }
          }
          if (SM[PU[iPUIndex].offset + iCentre].spindex == iSpecIndex)
             return (PU[iPUIndex].offset + iCentre);
       }
       */

    return -1;
}

// returns the clump number of a species at a planning unit, if the species doesn't occur here, returns 0
int rtnClumpSpecAtPu(struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex)
{
    int i;

    if (PU[iPUIndex].richness > 0)
       for (i=0;i<PU[iPUIndex].richness;i++)
           if (SM[PU[iPUIndex].offset + i].spindex == iSpecIndex)
              return SM[PU[iPUIndex].offset + i].clump;

    /*
    i = rtnIdxSpecAtPu(PU,SM,iPUIndex,iSpecIndex);
    if (i != -1)
       return SM[i].clump;
    */

    return 0;
}

// sets the clump number of a species at a planning unit
void setClumpSpecAtPu(struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex, int iSetClump)
{
     int i;

     if (PU[iPUIndex].richness > 0)
        for (i=0;i<PU[iPUIndex].richness;i++)
            if (SM[PU[iPUIndex].offset + i].spindex == iSpecIndex)
               SM[PU[iPUIndex].offset + i].clump = iSetClump;
     /*
     i = rtnIdxSpecAtPu(PU,SM,iPUIndex,iSpecIndex);
     if (i != -1)
        SM[i].clump = iSetClump;
     */
}

// returns the amount of a species at a planning unit, if the species doesn't occur here, returns 0
double rtnAmountSpecAtPu(struct spustuff PU[], struct spu SM[], int iPUIndex, int iSpecIndex)
{
       int i;

       if (PU[iPUIndex].richness > 0)
          for (i=0;i<PU[iPUIndex].richness;i++)
              if (SM[PU[iPUIndex].offset + i].spindex == iSpecIndex)
                 return SM[PU[iPUIndex].offset + i].amount;

       return 0;
}


//void TestrtnConvertZoneAmount(int puno, int spno, struct spustuff pu[], struct sspecies spec[], struct spu SM[],
//                               struct sfname fnames)
//{
//     FILE *fp;
//     int i,j,k;
//     char *writename;
//     double rAmount;
//
//     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugTestrtnAmountSpecAtPuZone.csv") + 2, sizeof(char));
//     strcpy(writename,fnames.outputdir);
//     strcat(writename,"debugTestrtnAmountSpecAtPuZone.csv");
//     fp = fopen(writename,"w");
//     if (fp==NULL)
//        ShowErrorMessage("cannot create TestrtnAmountSpecAtPuZone file %s\n",writename);
//     free(writename);
//     fputs("zoneindex,puindex,specindex,zoneid,puid,spid,amount\n",fp);
//     for (i=0;i<puno;i++)
//         for (j=0;j<spno;j++)
//             for (k=0;k<iZoneCount;k++)
//             {
//               rAmount = rtnAmountSpecAtPuZone(k,pu,SM,i,j);
//               if (rAmount > 0)
//                    fprintf(fp,"%d,%d,%d,%d,%d,%d,%g\n",k,i,j,Zones[k].id,pu[i].id,spec[j].name,rAmount);
//           }
//
//     fclose(fp);
//}

// apply weighting for a species amount within a particular zone
double rtnConvertZoneAmount(int iZone, int iSpecIndex,int iPUIndex,int puno, double rAmount)
       // iZone and iSpecIndex are zero based
{
       if (iZoneContrib3On == 1)
          return _ZoneContrib[(iSpecIndex * puno * iZoneCount) + (iPUIndex * iZoneCount) + iZone] * rAmount;
       else
           return _ZoneContrib[(iSpecIndex * iZoneCount) + iZone] * rAmount;
}

void ParsePuLock(int puno,struct spustuff pu[],int iPuLockCount,struct pulockstruct PuLock[],struct binsearch PULookup[])
{
     int i, iPUIndex;

     for (i=0;i<iPuLockCount;i++)
     {
         iPUIndex = FastPUIDtoPUINDEX(puno,PuLock[i].puid,PULookup);

         // .puid .zoneid
         pu[iPUIndex].fPULock = 1;
         pu[iPUIndex].iPULock = PuLock[i].zoneid;
     }
}

void ParsePuZone(int puno,struct spustuff pu[],int iPuZoneCount,struct puzonestruct PuZone[],struct binsearch PULookup[])
{
     int i, iPUIndex;

     for (i=0;i<iPuZoneCount;i++)
     {
         iPUIndex = FastPUIDtoPUINDEX(puno,PuZone[i].puid,PULookup);

         // .puid .zoneid
         if (pu[iPUIndex].fPUZone == 0)
             pu[iPUIndex].iPUZone = i;
         pu[iPUIndex].fPUZone = 1;
         pu[iPUIndex].iPUZones++;
     }
}

void CheckPuZone(int puno,struct spustuff pu[])
{
     int i;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[500];
     #endif

     for (i=0;i<puno;i++)
     {
		 if (pu[i].iPUZones == 1)
		 {
		    // error condition exists
            #ifdef DEBUGTRACEFILE
            sprintf(debugbuffer,"Error: planning unit %i is locked to a single zone in %s.\nDo not use this file to lock planning units to a single zone; use pulock.dat for this purpose.\nAborting Program.\n",pu[i].id,fnames.puzonename);
            AppendDebugTraceFile(debugbuffer);
            #endif

	        ShowErrorMessage("Error: planning unit %i is locked to a single zone in %s.\nDo not use this file to lock planning units to a single zone; use pulock.dat for this purpose.\nAborting Program.\n",pu[i].id,fnames.puzonename);
		 }
     }
}

void DumpPuLockZone(int puno,struct spustuff pu[])
{
     FILE *fp;
     char *writename;
     int i;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debugPuLockZone.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debugPuLockZone.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create DumpPuLockZone file %s\n",writename);
     free(writename);

     fprintf(fp,"id,fPULock,iPULock,fPUZone,iPUZone,iPUZones,iPreviousStatus\n");
     for (i=0;i<puno;i++)
     {
         fprintf(fp,"%d,%d,%d,%d,%d,%d,%d\n",pu[i].id,pu[i].fPULock,pu[i].iPULock,pu[i].fPUZone,pu[i].iPUZone,pu[i].iPUZones,pu[i].iPreviousStatus);
     }

     fclose(fp);
}

void SetBlockDefs(int gspno,int spno,int puno,struct sgenspec gspec[], struct sspecies spec[],
                  struct spustuff PU[], struct spu SM[])
{
     int igsp,isp,ipu;
     double totalamount;

     for (igsp=0;igsp<gspno;igsp++)
     {
         if(gspec[igsp].prop > 0) // deal with percentage in a different way
           for (isp=0;isp<spno;isp++)
               if(spec[isp].type == gspec[igsp].type && spec[isp].target < 0)
               {
                 for (ipu=0,totalamount =0;ipu<puno;ipu++)
                     totalamount += rtnAmountSpecAtPu(PU,SM,ipu,isp);
                 spec[isp].target = totalamount * gspec[igsp].prop;
               } // Setting target with percentage
         if (gspec[igsp].target > 0)
            for (isp=0;isp<spno;isp++)
                if (spec[isp].type == gspec[igsp].type && spec[isp].target < 0)
                   spec[isp].target = gspec[igsp].target;
         if (gspec[igsp].target2 > 0)
            for (isp=0;isp<spno;isp++)
            {
                if (spec[isp].type == gspec[igsp].type && spec[isp].target2 < 0)
                {
                   spec[isp].target2 = gspec[igsp].target2;
                }
            }
         if (gspec[igsp].targetocc > 0)
            for (isp=0;isp<spno;isp++)
                if (spec[isp].type == gspec[igsp].type && spec[isp].targetocc < 0)
                   spec[isp].targetocc = gspec[igsp].targetocc;
         if (gspec[igsp].sepnum > 0)
            for (isp=0;isp<spno;isp++)
                if (spec[isp].type == gspec[igsp].type && spec[isp].sepnum < 0)
                   spec[isp].sepnum = gspec[igsp].sepnum;
         if (gspec[igsp].spf > 0)
            for (isp=0;isp<spno;isp++)
                if (spec[isp].type == gspec[igsp].type && spec[isp].spf < 0)
                   spec[isp].spf = gspec[igsp].spf;
         if (gspec[igsp].sepdistance > 0)
            for (isp=0;isp<spno;isp++)
                if (spec[isp].type == gspec[igsp].type && spec[isp].sepdistance < 0)
                   spec[isp].sepdistance = gspec[igsp].sepdistance;
       // Percentage is not dealt with here yet. To do this identify
       // target species then determine their total abundance then set target
       // according to percentage
     } // Loop through each setBlockDef

} // Set Block Defs

// * * *  Set Defaults * * *
// If '-1' values haven't been set yet then this one will do it
void SetDefs(int spno, struct sspecies spec[])
{
     int isp;

     for (isp=0;isp<spno;isp++)
     {
         if (spec[isp].target <0)
            spec[isp].target = 0;
         if (spec[isp].target2 < 0)
            spec[isp].target2 = 0;
         if (spec[isp].targetocc < 0)
            spec[isp].targetocc = 0;
         if (spec[isp].sepnum < 0)
            spec[isp].sepnum = 0;
         if (spec[isp].sepdistance < 0)
            spec[isp].sepdistance = 0;
         if (spec[isp].spf < 0)
            spec[isp].spf = 1;
     }
} // Set Defs

// *** Set run options. Takes an integer runopts value and returns flags ***
void SetRunOptions(int runopts, struct srunoptions *runoptions)
{
     if (runopts < 0)
        return; // runopts < 0 indicates that these are set in some other way
     switch (runopts)
     {
            case 0: (*runoptions).AnnealingOn = 1;
                    (*runoptions).HeuristicOn = 1;
                    (*runoptions).ItImpOn = 0;
                    break;
            case 1: (*runoptions).AnnealingOn = 1;
                    (*runoptions).HeuristicOn = 0;
                    (*runoptions).ItImpOn = 1;
                    break;
            case 2: (*runoptions).AnnealingOn = 1;
                    (*runoptions).HeuristicOn = 1;
                    (*runoptions).ItImpOn = 1;
                    break;
            case 3: (*runoptions).AnnealingOn = 0;
                    (*runoptions).HeuristicOn = 1;
                    (*runoptions).ItImpOn = 0;
                    break;
            case 4: (*runoptions).AnnealingOn = 0;
                    (*runoptions).HeuristicOn = 0;
                    (*runoptions).ItImpOn = 1;
                    break;
            case 5: (*runoptions).AnnealingOn = 0;
                    (*runoptions).HeuristicOn = 1;
                    (*runoptions).ItImpOn = 1;
                    break;
            case 6: (*runoptions).AnnealingOn = 1;
                    (*runoptions).HeuristicOn = 0;
                    (*runoptions).ItImpOn = 0;
                    break;
            default: (*runoptions).AnnealingOn = 0;
                     (*runoptions).HeuristicOn = 0;
                     (*runoptions).ItImpOn = 0;
                     break;
     }

} // Set Run Options

int PuNotInAllowedZone(struct spustuff GivenPu,int iStatus,struct puzonestruct PuZone[],int iLoopCounter,char cCall)
    // returns 1 if Pu is not in allowed zone
    // returns 0 if Pu is in allowed zone
{
    int i, iReturn = 1;

    #ifdef DEBUG_PuNotInAllowedZone
    char debugbuffer[1000];
    if (GivenPu.id == 2776)
    {
       sprintf(debugbuffer,"PuNotInAllowedZone start puid %i proposed status %i puzones %i loopcounter %i call %c\n",
                           GivenPu.id,iStatus,GivenPu.iPUZones,iLoopCounter,cCall);
       AppendDebugTraceFile(debugbuffer);
    }
    #endif

    for (i=0;i<GivenPu.iPUZones;i++)
    {
        #ifdef DEBUG_PuNotInAllowedZone
        if (GivenPu.id == 2776)
        {
           sprintf(debugbuffer,"arrayindex %i arraysize %i zoneid %i\n",
                               (GivenPu.iPUZone + i),iPuZoneCount,PuZone[GivenPu.iPUZone + i].zoneid);
           AppendDebugTraceFile(debugbuffer);
        }
        #endif

        if (PuZone[GivenPu.iPUZone + i].zoneid == iStatus)
           iReturn = 0;
    }

    #ifdef DEBUG_PuNotInAllowedZone
    if (GivenPu.id == 2776)
    {
       sprintf(debugbuffer,"PuNotInAllowedZone return %i end\n",iReturn);
       AppendDebugTraceFile(debugbuffer);
    }
    #endif

    return iReturn;
}

// create the initial reserve system for CalcPenalties
void InitialiseReserve(int puno,struct spustuff pu[],
                       int R[],int iZoneCount,struct puzonestruct PuZone[])
{
     int i, iRandNum, iLoopCounter;

     #ifdef DEBUGTRACEFILE
     char debugbuffer[1000];
     #endif

     #ifdef DEBUGINITIALISERESERVE
     AppendDebugTraceFile("InitialiseReserve start\n");
     //DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,8);
     #endif

     for (i=0;i<puno;i++)
     {
         R[i] = 1;

         #ifdef DEBUGINITIALISERESERVE
         sprintf(debugbuffer,"InitialiseReserve puid %i status %i\n",pu[i].id,pu[i].status);
         AppendDebugTraceFile(debugbuffer);
         #endif

         if (pu[i].status > 0)
            R[i] = pu[i].status;

         iLoopCounter = 0;

         if (pu[i].fPUZone == 1)  // enforce PuZone, PuZone overrides status
            while (PuNotInAllowedZone(pu[i],R[i],PuZone,iLoopCounter,'i'))
            {
                  R[i] = RandNum(iZoneCount) + 1;  // roll dice for a new zone
                  iLoopCounter++;

                  if (iLoopCounter > 5000)
                  {
                     #ifdef DEBUGTRACEFILE
                     DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,999);
                     AppendDebugTraceFile("PuZone endless loop in InitialiseReserve detected\n");
                     sprintf(debugbuffer,"puid %i R %i\n",pu[i].id,R[i]);
                     AppendDebugTraceFile(debugbuffer);
                     #endif
                     ShowGenProg("\nPuZone endless loop in InitialiseReserve detected\n");
                     ShowGenProg("Internal error detected.  Please inform the Marxan with Zones developers.\n\n");
                     ShowPauseExit();
                     exit(1);
                  }
            }
         if (pu[i].fPULock == 1)  // enforce PuLock, PuLock overrides status & PuZone
            R[i] = pu[i].iPULock;
     }

     #ifdef DEBUGINITIALISERESERVE
     AppendDebugTraceFile("InitialiseReserve end\n");
     #endif
}    // * * * * Add Reserve * * * *


// * * * *  Add Reserve to current system * * * *
void AddReserve(int puno,struct spustuff pu[],int R[],int iZoneCount,struct puzonestruct PuZone[])
{
     int i, iLoopCounter;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[1000];
     #endif

     for (i=0;i<puno;i++)
     {
         iLoopCounter = 0;

         if (pu[i].fPUZone == 1)  // enforce PuZone, PuZone overrides status
         {
            while (PuNotInAllowedZone(pu[i],R[i],PuZone,0,'r'))
            {
                  R[i] = RandNum(iZoneCount) + 1;  // roll dice for a new zone
                  iLoopCounter++;

                  if (iLoopCounter > 5000)
                  {
                     #ifdef DEBUGTRACEFILE
                     DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,999);
                     AppendDebugTraceFile("PuZone endless loop in AddReserve detected\n");
                     sprintf(debugbuffer,"puid %i R %i\n",pu[i].id,R[i]);
                     AppendDebugTraceFile(debugbuffer);
                     #endif
                     ShowGenProg("\nPuZone endless loop in AddReserve detected\n");
                     ShowGenProg("Internal error detected.  Please inform the Marxan with Zones developers.\n\n");
                     ShowPauseExit();
                     exit(1);
                  }
            }
         }
         if (pu[i].fPULock == 1)  // enforce PuLock, PuLock overrides status & PuZone
            R[i] = pu[i].iPULock;
     }
}    // * * * * Add Reserve * * * *

// * * * * Calculate Initial Penalties * * * *
// This routine calculates the initial penalties or the penalty if you had no representation
int CalcPenalties(int puno,int spno,struct spustuff pu[],struct sspecies spec[],
                  struct sconnections connections[],struct spu SM[],int aggexist,int clumptype,
                  int iZoneCount,int R[],struct _zonetargetstruct _ZoneTarget[])
{
    int i,j,ibest,imaxtarget,itargetocc,iArrayIndex;
    int *PUtemp;
    double ftarget,fbest,fbestrat,fcost,ftemp, rAmount, rZoneSumTarg;
    int badspecies = 0,goodspecies = 0, iZone, iZoneSumOcc;
    #ifdef DEBUG_CALC_PENALTIES
    char debugbuffer[1000];
    FILE *fp;
    char *writename;
    #endif

    PUtemp = (int *) calloc(puno,sizeof(int));

    for (i=0;i<spno;i++)
    {
        #ifdef DEBUG_CALC_PENALTIES
        sprintf(debugbuffer,"CalcPenalties species %i\n",spec[i].name);
        AppendDebugTraceFile(debugbuffer);
        #endif

        // compute zonesumtarg and zonesumocc for this species
        rZoneSumTarg = 0;
        iZoneSumOcc = 0;
        for (j=0;j<iZoneCount;j++)
            //if ((j+1) != iAvailableEquivalentZone) // we ignore zone targets for available zone for simplicity
            {
               iArrayIndex = (i*iZoneCount) + j;

               rZoneSumTarg = rZoneSumTarg + _ZoneTarget[iArrayIndex].target;
               iZoneSumOcc = iZoneSumOcc + _ZoneTarget[iArrayIndex].occurrence;
            }

        if (spec[i].target > rZoneSumTarg)
           rZoneSumTarg = spec[i].target;
        if (spec[i].occurrence > iZoneSumOcc)
           iZoneSumOcc = spec[i].occurrence;

        #ifdef DEBUG_CALC_PENALTIES
        sprintf(debugbuffer,"CalcPenalties spid %i rZoneSumTarg %f\n",spec[i].name,rZoneSumTarg);
        AppendDebugTraceFile(debugbuffer);
        #endif

        if (spec[i].target2 || spec[i].sepnum)
        {
           j = CalcPenaltyType4(i,puno,SM,connections,spec,pu,clumptype,R);
           badspecies += (j>0);
           goodspecies += (j<0);

           #ifdef DEBUG_CALC_PENALTIES
           sprintf(debugbuffer,"CalcPenalties species %i penalty %g\n",spec[i].name,spec[i].penalty);
           AppendDebugTraceFile(debugbuffer);
           #endif

           continue;
        } // Species has aggregation requirements

        ftarget = 0;
        itargetocc = 0;
        spec[i].penalty = 0;

        for (j=0;j<puno;j++)
        {
            PUtemp[j] = R[j];
            iZone = PUtemp[j];

            //if ((iZone != iAvailableEquivalentZone) && (iZone != 0)) // pu is not available or ignored
            {
               rAmount = rtnAmountSpecAtPu(pu,SM,j,i);
               ftarget += rAmount;
               itargetocc++;
               spec[i].penalty += rtnMaxNonAvailableCost(j,connections);
               #ifdef DEBUG_CALC_PENALTIES
               //sprintf(debugbuffer,"CalcPenalties puid %i pen %f\n",pu[j].id,spec[i].penalty);
               //AppendDebugTraceFile(debugbuffer);
               #endif
            }
        } // reset PUtemp and also target

        // Already adequately represented on type 2 planning unit
        if (ftarget >= rZoneSumTarg && itargetocc >= iZoneSumOcc)
        {
           goodspecies++;
           ShowGenProgInfo("Species %i (%s) has already met target %.2f\n",
                            spec[i].name,spec[i].sname,rZoneSumTarg);

           #ifdef DEBUG_CALC_PENALTIES
           sprintf(debugbuffer,"CalcPenalties species %i penalty %g\n",spec[i].name,spec[i].penalty);
           AppendDebugTraceFile(debugbuffer);
           #endif

           continue;
        } // Target met in unremovable reserve

        #ifdef DEBUG_CALC_PENALTIES
        sprintf(debugbuffer,"CalcPenalties species %i penalty %g\n",spec[i].name,spec[i].penalty);
        AppendDebugTraceFile(debugbuffer);
        //sprintf(debugbuffer,"puid,ftarget,fbest\n");
        //AppendDebugTraceFile(debugbuffer);
        #endif

        do
        {
          fbest =0; imaxtarget = 0; fbestrat = 0;
          for (j=0;j<puno;j++)
          {
              rAmount = rtnAmountSpecAtPu(pu,SM,j,i);
              if (rAmount>0)
              {
                 fcost = rtnMaxNonAvailableCost(j,connections);
                 if (fcost == 0)
                    fcost = delta;
                 if (rAmount >= rZoneSumTarg - ftarget && (imaxtarget == 0 ||
                             (imaxtarget == 1 && fcost < fbest)))
                 {
                    imaxtarget = 1;
                    ibest = j;
                    fbest = fcost;
                 } // can I meet the target cheaply?
                 else if (fbestrat < rAmount/fcost)
                      {
                         fbest = fcost;
                         fbestrat = rAmount/fbest;
                         ibest = j;
                      }  // finding the cheapest planning unit

                 #ifdef DEBUG_CALC_PENALTIES
                 //sprintf(debugbuffer,"CalcPenalties species %i puid %i cost %g\n",spec[i].name,pu[j].id,fcost);
                 //AppendDebugTraceFile(debugbuffer);
                 #endif
              }  // Making sure only checking planning units not already used
          }  // trying to find best pu

          if (fbest > 0)
          {
             PUtemp[ibest] = iZoneCount;
             ftarget += rtnAmountSpecAtPu(pu,SM,ibest,i);
             itargetocc++;
             spec[i].penalty += fbest;

             #ifdef DEBUG_CALC_PENALTIES
             //sprintf(debugbuffer,"%i,%g,%g\n",pu[ibest].id,ftarget,fbest);
             //AppendDebugTraceFile(debugbuffer);
             #endif
          } // Add pu to target
        } while ((ftarget < rZoneSumTarg || itargetocc < iZoneSumOcc) && fbest > 0); // or no more pu left

        if (fbest == 0) // Could not meet target using all available PUs
        {
           ShowGenProgInfo("Species %d (%s) cannot reach target %.2f there is only %.2f available.\n",
                           spec[i].name,spec[i].sname,rZoneSumTarg,ftarget);

           if (ftarget==0)
              ftarget=delta;  // Protect against divide by zero
           ftemp = 0;
           if (ftarget<rZoneSumTarg)
              ftemp = rZoneSumTarg/ftarget;
           if (itargetocc < iZoneSumOcc && itargetocc)  // If ! itargetocc then also !ftarget
              ftemp += (double) iZoneSumOcc/(double) itargetocc;
           spec[i].penalty = spec[i].penalty * ftemp; // Scale it up
           // This value will be ~ 1/delta when there are no occ's of target species in system
           badspecies++;
        }  // If not met target with all available PUs

        #ifdef DEBUG_CALC_PENALTIES
        sprintf(debugbuffer,"CalcPenalties species %i penalty %g target %g\n",spec[i].name,spec[i].penalty,rZoneSumTarg);
        AppendDebugTraceFile(debugbuffer);
        #endif

        #ifdef DEBUG_CALC_PENALTIES
        //AppendDebugFile("debug_MarZone_CalcPenalties.csv","\n",fnames);
        // isp,spid,target,penalty
        #endif
    }  // Penalty for each individual Species

    if (aggexist)
        ClearClumps(spno,spec,pu,SM);

    free(PUtemp);
    DebugFree(puno*sizeof(int));

    if (goodspecies)
         ShowGenProg("%i species are already adequately represented.\n",goodspecies);

    #ifdef DEBUG_CALC_PENALTIES
    sprintf(debugbuffer,"CalcPenalties %i species are already represented. %i species cannot reach target.\n",goodspecies,badspecies);
    AppendDebugTraceFile(debugbuffer);
    #endif

    return(badspecies);
}

void AddReserve_CPO(int puno,struct spustuff pu[],int R[],int iZoneCount,struct puzonestruct PuZone[])
{
     int i, iLoopCounter;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[1000];
     #endif

     for (i=0;i<puno;i++)
     {
         R[i] = 0;

         iLoopCounter = 0;

         if (pu[i].fPUZone == 1)  // enforce PuZone, PuZone overrides status
         {
            while (PuNotInAllowedZone(pu[i],R[i],PuZone,0,'r'))
            {
                  R[i] = RandNum(iZoneCount) + 1;  // roll dice for a new zone
                  iLoopCounter++;

                  if (iLoopCounter > 5000)
                  {
                     #ifdef DEBUGTRACEFILE
                     DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,999);
                     AppendDebugTraceFile("PuZone endless loop in AddReserve detected\n");
                     sprintf(debugbuffer,"puid %i R %i\n",pu[i].id,R[i]);
                     AppendDebugTraceFile(debugbuffer);
                     #endif
                     ShowGenProg("\nPuZone endless loop in AddReserve detected\n");
                     ShowGenProg("Internal error detected.  Please inform the Marxan with Zones developers.\n\n");
                     ShowPauseExit();
                     exit(1);
                  }
            }
         }
         if (pu[i].fPULock == 1)  // enforce PuLock, PuLock overrides status & PuZone
            R[i] = pu[i].iPULock;
     }
}    // * * * * Add Reserve * * * *

int CalcPenaltiesOptimise(int puno,int spno,struct spustuff pu[],struct sspecies spec[],
                          struct sconnections connections[],struct spu SM[],struct spusporder SMsp[],int R[],int aggexist,int clumptype)
{
    int i,j,ibest,imaxtarget,itargetocc,ism,ipu, iPUsToTest, iArrayIndex, iZone;
    int *PUtemp;
    double ftarget,fbest,fbestrat,fcost,ftemp, rAmount, r_ibest_amount, rZoneSumTarg;
    int badspecies = 0,goodspecies = 0, iZoneSumOcc;
    char debugbuffer[80];

    PUtemp = (int *) calloc(puno,sizeof(int));

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("CalcPenaltiesOptimise start\n");
    #endif

    for (i=0;i<spno;i++)
    {
        #ifdef DEBUGTRACEFILE
        sprintf(debugbuffer,"CalcPenaltiesOptimise spname %i\n",spec[i].name);
        AppendDebugTraceFile(debugbuffer);
        #endif

        // compute zonesumtarg and zonesumocc for this species
        rZoneSumTarg = 0;
        iZoneSumOcc = 0;
        for (j=0;j<iZoneCount;j++)
            //if ((j+1) != iAvailableEquivalentZone) // we ignore zone targets for available zone for simplicity
            {
               iArrayIndex = (i*iZoneCount) + j;

               rZoneSumTarg = rZoneSumTarg + _ZoneTarget[iArrayIndex].target;
               iZoneSumOcc = iZoneSumOcc + _ZoneTarget[iArrayIndex].occurrence;
            }

        if (spec[i].target > rZoneSumTarg)
           rZoneSumTarg = spec[i].target;
        if (spec[i].occurrence > iZoneSumOcc)
           iZoneSumOcc = spec[i].occurrence;

        #ifdef DEBUG_CALC_PENALTIES
        sprintf(debugbuffer,"CalcPenaltiesOptimise spid %i rZoneSumTarg %f\n",spec[i].name,rZoneSumTarg);
        AppendDebugTraceFile(debugbuffer);
        #endif

        if (spec[i].target2 || spec[i].sepnum)
        {
           j = CalcPenaltyType4(i,puno,SM,connections,spec,pu,clumptype,PUtemp);
           badspecies += (j>0);
           goodspecies += (j<0);
           continue;
        } // Species has aggregation requirements

        ftarget = 0;
        itargetocc = 0;
        spec[i].penalty = 0;

        if (spec[i].richness > 0)
           for (j=0;j<spec[i].richness;j++)  // traverse pu's containing this sp
           {
               ism = spec[i].offset + j;
               ipu = SMsp[ism].puindex;

               // ****************************************
               PUtemp[ipu] = R[ipu];
               iZone = PUtemp[ipu];

               //if ((iZone != iAvailableEquivalentZone) && (iZone != 0)) // pu is not available or ignored
               {
                  ftarget += SMsp[ism].amount;
                  itargetocc++;
                  spec[i].penalty += rtnMaxNonAvailableCost(ipu,connections);
               }
           } // reset PUtemp and also target

        // Already adequately represented on type 2 planning unit
        if (ftarget >= rZoneSumTarg && itargetocc >= iZoneSumOcc)
        {
           goodspecies++;
           ShowGenProgInfo("Species %i (%s) has already met target %.2f\n",
                            spec[i].name,spec[i].sname,rZoneSumTarg);
           continue;
        } // Target met in unremovable reserve

        #ifdef DEBUG_CALC_PENALTIES
        sprintf(debugbuffer,"CalcPenalties species %i penalty %g\n",spec[i].name,spec[i].penalty);
        AppendDebugTraceFile(debugbuffer);
        #endif

        do
        {
          fbest =0; imaxtarget = 0; fbestrat = 0;
          if (spec[i].richness > 0)
             for (j=0;j<spec[i].richness;j++)  // traverse pu's containing this sp
             {
                 ism = spec[i].offset + j;
                 ipu = SMsp[ism].puindex;

                 rAmount = SMsp[ism].amount;
                 if (rAmount>0)
                 {
                    fcost = rtnMaxNonAvailableCost(ipu,connections);
                    if (fcost == 0)
                       fcost = delta;
                    if (rAmount >= rZoneSumTarg - ftarget && (imaxtarget == 0 ||
                             (imaxtarget == 1 && fcost < fbest)))
                    {
                       imaxtarget = 1;
                       ibest = ipu;
                       r_ibest_amount = rAmount;
                       fbest = fcost;
                    } // can I meet the target cheaply?
                    else if (fbestrat < rAmount/fcost)
                         {
                            fbest = fcost;
                            fbestrat = rAmount/fbest;
                            ibest = ipu;
                            r_ibest_amount = rAmount;
                         }  // finding the cheapest planning unit
                 }  // Making sure only checking planning units not already used
             }  // trying to find best pu

          if (fbest > 0)
          {
             PUtemp[ibest] = iZoneCount;
             ftarget += r_ibest_amount;
             itargetocc++;
             spec[i].penalty += fbest;
          } // Add pu to target

        } while ((ftarget < rZoneSumTarg || itargetocc < iZoneSumOcc) && fbest > 0); // or no more pu left
        // while there is some pu's with this species to test AND a best available pu was found AND targets are not met yet

        if (fbest == 0) // Could not meet target using all available PUs
        {
           ShowGenProgInfo("Species %d (%s) cannot reach target %.2f there is only %.2f available.\n",
                           spec[i].name,spec[i].sname,spec[i].target,ftarget);
           if (ftarget==0)
              ftarget=delta;  // Protect against divide by zero
           ftemp = 0;
           if (ftarget<rZoneSumTarg)
              ftemp = rZoneSumTarg/ftarget;
           if (itargetocc < iZoneSumOcc && itargetocc)  // If ! itargetocc then also !ftarget
              ftemp += (double) iZoneSumOcc/(double) itargetocc;
           spec[i].penalty = spec[i].penalty * ftemp; // Scale it up
           // This value will be ~ 1/delta when there are no occ's of target species in system
           badspecies++;
        }  // If not met target with all available PUs

        #ifdef DEBUGTRACEFILE
        sprintf(debugbuffer,"CalcPenaltiesOptimise spname %i penalty %g target %g\n",spec[i].name,spec[i].penalty,rZoneSumTarg);
        AppendDebugTraceFile(debugbuffer);
        #endif

    }  // Penalty for each individual Species
    // Clear clumps in case I needed them for target4 species

    if (aggexist)
        ClearClumps(spno,spec,pu,SM);

    free(PUtemp);

    if (goodspecies)
         ShowGenProg("%i species are already adequately represented.\n",goodspecies);

    #ifdef DEBUGTRACEFILE
    AppendDebugTraceFile("CalcPenaltiesOptimise end\n");
    #endif

    return(badspecies);

}// *** Calculate Initial Penalties *****
// ******* Calculate Initial Penalties *************

// * * * * * Connection Cost Type 1 * * * * *
// Total cost of all connections for PU independant of neighbour status * * * * *
double ConnectionCost1(int ipu,struct sconnections connections[])
{
       double fcost;
       struct sneighbour *p;

       fcost = connections[ipu].fixedcost;
       for (p = connections[ipu].first;p;p=p->next)
           #ifdef ASYMCON
           if (asymmetricconnectivity)
           {
              if (p->connectionorigon)
                 fcost += p->cost;
           }
           else
           #endif
               fcost += p->cost;
       return(fcost);
} // * * * * Connection Cost Type 1 * * * *


// * * * * *** Cost of Planning Unit * * * * ******
// ***** Used only when calculating penalties *****
double cost(int ipu,struct sconnections connections[],int iZone)
       // iZone is one base
{
       double fcost;
       fcost = ReturnPuZoneCost(ipu,iZone);

       fcost += ConnectionCost1(ipu,connections);
       return(fcost);
} // Cost of Planning Unit

double rtnMaxNonAvailableCost(int ipu,struct sconnections connections[])
       // iZone is one base
{
       double fcost = 0, rMaxCost = 0;
       int iZone;

       for (iZone=1;iZone<=iZoneCount;iZone++)
           //if (iZone != iAvailableEquivalentZone)
           {
              fcost = ReturnPuZoneCost(ipu,iZone);
              if (fcost > rMaxCost)
                 rMaxCost = fcost;
           }

       rMaxCost += ConnectionCost1(ipu,connections);
       return(rMaxCost);
} // Cost of Planning Unit


// * * * * ** Connection Cost Type 2 * * * * ******
// **  Requires R[]. imode2 = 0 there is no negative cost for removing connection, we are calling from ReserveCost
//                         or 1 there is a negative cost for removing connection, we are calling from Annealing
//                   imode = -1 we are removing the planning unit from a reserve, calling from Annealing
//                        or 1  we are adding the planning unit to a reserve, or it is already in reserve
double ConnectionCost2(int ipu,int iCurrentZone,struct sconnections connections[],int R[],int imode,int iDebugMode)
{
       double fcost, rResult, rZoneConnectionCost, rDelta;
       struct sneighbour *p;
       #ifdef DEBUG_CONNECTIONCOST2
       char debugbuffer[1000];
       #endif

       #ifdef DEBUG_CONNECTIONCOST2
       if (iDebugMode)
       {
          sprintf(debugbuffer,"ConnectionCost2 start ipu %i iCurrentZone %i Ripu %i  fixedcost %g imode %i\n"
                             ,ipu,iCurrentZone,R[ipu],connections[ipu].fixedcost,imode);
          AppendDebugTraceFile(debugbuffer);
       }
       #endif

       fcost = connections[ipu].fixedcost*imode;
       p = connections[ipu].first;

       #ifdef ASYMCON
       if (asymmetricconnectivity)
       {
          while (p) // treatment for asymmetric connectivity
          {
                if (imode2) // calling from Annealing
                {
                   if (imode == 1)
                      R_pu1 = 0;
                   else
                       R_pu1 = 1;

                   if (p->connectionorigon)
                   {
                      if (R[p->nbr] == 0)
                      {
                         if (R_pu1 == 1)
                         {
                            rDelta = -1*p->cost;
                            fcost += rDelta;
                         }
                         else
                         {
                            rDelta = p->cost;
                            fcost += rDelta;
                         }
                      }
                   }
                   else
                   {
                      if (R[p->nbr] == 1 || R[p->nbr] == 2)
                      {
                         if (R_pu1 == 1)
                         {
                            rDelta = p->cost;
                            fcost += rDelta;
                         }
                         else
                         {
                            rDelta = -1*p->cost;
                            fcost += rDelta;
                         }
                      }
                   }
                }
                else // calling from ReserveCost
                {
                    if (R[p->nbr] == 0)
                       if (p->connectionorigon)
                       {
                          rDelta = p->cost;
                          fcost += rDelta;
                       }
                }

                p = p->next;
          }
       }
       else
       #endif
       {
           while (p)
           {
                 rZoneConnectionCost = _RelConnectionCost[((iCurrentZone-1) * iZoneCount) + (R[p->nbr] - 1)];
                 fcost += imode*p->cost*rZoneConnectionCost;

                 p = p->next;
           }
       }

       rResult = fcost;

       #ifdef DEBUG_CONNECTIONCOST2
       if (iDebugMode)
       {
       sprintf(debugbuffer,"ConnectionCost2 end result %g fcost %g\n",rResult,fcost);
       AppendDebugTraceFile(debugbuffer);
       }
       #endif

       return(rResult);
}// Connection Cost Type 2

// * * * * **** Change in penalty for moving single PU between zones ******
double ChangePen(int iIteration, int ipu,int puno,struct sspecies spec[],struct spustuff pu[],struct spu SM[],
                 int R[],struct sconnections connections[],int imode,int clumptype,int iZone,double *rShortfall)
                 // iZone is one based
{
       int i, k, ism, isp, iArrayIndex, iNewOccurrence, iCurrentShortfall, iNewShortfall;
       double rShortFraction, rNewShortFraction, rOldShortfall, rNewShortfall, rNewAmount, rSumDeltaPenalty = 0, rDeltaPenalty,
              rCurrentContribAmount, rNewContribAmount;
       #ifdef DEBUG_CHANGE_PEN
       char debugline[1000];
       #endif
       #ifdef DEBUG_PEW_CHANGE_PEN
       char debugline[1000];
       #endif

       #ifdef DEBUG_CHANGE_PEN
       sprintf(debugline,"ChangePen start puid %i existing zone %i proposed zone %i\n",pu[ipu].id,R[ipu],iZone);
       AppendDebugTraceFile(debugline);
       #endif

       // we need to know the penalty with ipu in its existing zone and the penalty with ipu in its proposed zone.
       // change in penalty = penalty in proposed configuration - penalty in existing configuration

       #ifdef DEBUG_CHANGE_PEN
       sprintf(debugline,"spid,targ,current ztarg,new ztarg,current shortfall,new shortfall,delta penalty\n");//,proposed amount,proposed occurrence\n");
       AppendDebugTraceFile(debugline);
       #endif

       *rShortfall = 0;

       if (pu[ipu].richness)
          for (i=0;i<pu[ipu].richness;i++)
          {
              ism = pu[ipu].offset + i;
              isp = SM[ism].spindex;
              if (SM[ism].amount)
              {
                 // R[ipu] is existing zone
                 // iZone is proposed zone
                 // init variables tracking shortfall in existing zone
                 rOldShortfall = 0;
                 rShortFraction = 0;
                 iCurrentShortfall = 0;
                 // init variables tracking shortfall in proposed zone
                 rNewShortfall = 0;
                 rNewShortFraction = 0;
                 iNewShortfall = 0;

                 // shortfall with respect to overall targets
                 rCurrentContribAmount = rtnConvertZoneAmount(R[ipu]-1,isp,ipu,puno,SM[ism].amount);
                 rNewContribAmount = rtnConvertZoneAmount(iZone-1,isp,ipu,puno,SM[ism].amount);

                 rNewAmount = spec[isp].amount + rNewContribAmount - rCurrentContribAmount;
                 iNewOccurrence = spec[isp].occurrence + (rNewContribAmount > 0) - (rCurrentContribAmount > 0);

                 if (spec[isp].target > 0)
                 {
                    if (spec[isp].target > spec[isp].amount)
                    {
                       rOldShortfall += spec[isp].target - spec[isp].amount;
                       rShortFraction += (spec[isp].target - spec[isp].amount)/spec[isp].target;
                       iCurrentShortfall++;
                    }

                    if (spec[isp].target > rNewAmount)
                    {
                       rNewShortfall += spec[isp].target - rNewAmount;
                       rNewShortFraction += (spec[isp].target - rNewAmount)/spec[isp].target;
                       iNewShortfall++;
                    }

                    #ifdef DEBUG_PEW_CHANGE_PEN
                    sprintf(debugline,"%i,%i,%i,%i,%i,%i,%i,%g,%g,%g,%g,%g,%g,%g,%g,%i,%i,0\n"
                                     ,iIteration,ipu,isp,pu[ipu].id,spec[isp].name,R[ipu],iZone,spec[isp].target,SM[ism].amount,spec[isp].amount
                                     ,rNewAmount,rShortfall,rNewShortfall,rShortFraction,rNewShortFraction,iCurrentShortfall,iNewShortfall);
                    AppendDebugFile("debug_MarZone_PewChangePen.csv",debugline,fnames);
                    AppendDebugTraceFile("iteration,ipu,isp,puid,spid,Zone,newZone,Target,PUAmount,Amount,newAmount,Shortfall,newShortfall,rSF,rNSF,iCSF,iNSF,zone\n");
                    AppendDebugTraceFile(debugline);
                    #endif
                 }

                 if (spec[isp].targetocc > 0)
                 {
                    if (spec[isp].targetocc > spec[isp].occurrence)
                    {
                       rOldShortfall += spec[isp].targetocc - spec[isp].occurrence;
                       rShortFraction += (spec[isp].targetocc - spec[isp].occurrence)/spec[isp].targetocc;
                       iCurrentShortfall++;
                    }

                    if (spec[isp].targetocc > iNewOccurrence)
                    {
                       rNewShortfall += spec[isp].targetocc - iNewOccurrence;
                       rNewShortFraction += (spec[isp].targetocc - iNewOccurrence)/spec[isp].targetocc;
                       iNewShortfall++;
                    }
                 }

                 // compute existing & proposed shortfall for this feature across any relevant zone targets
                 for (k=0;k<iZoneCount;k++)
                 {
                     // is k our existing zone or our new zone?
                     // (ie. will the amount held for this zone change as a result of this proposed status change)
                     if ((k == (R[ipu] - 1)) || (k == (iZone - 1)))
                     {

                        iArrayIndex = (isp * iZoneCount) + k;
                        // compute amount of feature if change is made
                        if (k == (R[ipu] - 1)) // zone is existing zone, reduce zone amount by amount at site
                        {
                           rNewAmount = ZoneSpec[iArrayIndex].amount - SM[ism].amount;
                           iNewOccurrence = ZoneSpec[iArrayIndex].occurrence - 1;
                        }
                        else
                            //if (k == (iZone - 1)) // zone is proposed zone, increase zone amount by amount at site
                            {
                               rNewAmount = ZoneSpec[iArrayIndex].amount + SM[ism].amount;
                               iNewOccurrence = ZoneSpec[iArrayIndex].occurrence + 1;
                            }

                        // do we have areal zone target?
                        if (_ZoneTarget[iArrayIndex].target > 0)
                        {
                           // compute existing shortfall
                           if (_ZoneTarget[iArrayIndex].target > ZoneSpec[iArrayIndex].amount)
                           {
                              rOldShortfall += _ZoneTarget[iArrayIndex].target - ZoneSpec[iArrayIndex].amount;
                              rShortFraction += (_ZoneTarget[iArrayIndex].target - ZoneSpec[iArrayIndex].amount) / _ZoneTarget[iArrayIndex].target;
                              iCurrentShortfall++;
                           }

                           // compute proposed shortfall
                           if (_ZoneTarget[iArrayIndex].target > rNewAmount)
                           {
                              rNewShortfall += _ZoneTarget[iArrayIndex].target - rNewAmount;
                              rNewShortFraction += (_ZoneTarget[iArrayIndex].target - rNewAmount) / _ZoneTarget[iArrayIndex].target;
                              iNewShortfall++;
                           }

                           #ifdef DEBUG_PEW_CHANGE_PEN
                           sprintf(debugline,"%i,%i,%i,%i,%i,%i,%i,%g,%g,%g,%g,%g,%g,%g,%g,%i,%i,1\n"
                                            ,iIteration,ipu,isp,pu[ipu].id,spec[isp].name,R[ipu],iZone,_ZoneTarget[iArrayIndex].target
                                            ,SM[ism].amount,ZoneSpec[iArrayIndex].amount,rNewAmount,rShortfall,rNewShortfall
                                            ,rShortFraction,rNewShortFraction,iCurrentShortfall,iNewShortfall);
                           AppendDebugFile("debug_MarZone_PewChangePen.csv",debugline,fnames);
                           AppendDebugTraceFile("iteration,ipu,isp,puid,spid,Zone,newZone,ZoneTarget,PUAmount,Amount,newAmount,Shortfall,newShortfall,rSF,rNSF,iCSF,iNSF,zone\n");
                           AppendDebugTraceFile(debugline);
                           #endif
                        }

                        // do we have occurrence zone target?
                        if (_ZoneTarget[iArrayIndex].occurrence > 0)
                        {
                           // compute existing shortfall
                           if (_ZoneTarget[iArrayIndex].occurrence > ZoneSpec[iArrayIndex].occurrence)
                           {
                              rOldShortfall += _ZoneTarget[iArrayIndex].occurrence - ZoneSpec[iArrayIndex].occurrence;
                              rShortFraction += (_ZoneTarget[iArrayIndex].occurrence - ZoneSpec[iArrayIndex].occurrence) / _ZoneTarget[iArrayIndex].occurrence;
                              iCurrentShortfall++;
                           }

                           // compute proposed shortfall
                           if (_ZoneTarget[iArrayIndex].occurrence > iNewOccurrence)
                           {
                              rNewShortfall += _ZoneTarget[iArrayIndex].occurrence - iNewOccurrence;
                              rNewShortFraction += (_ZoneTarget[iArrayIndex].occurrence - iNewOccurrence) / _ZoneTarget[iArrayIndex].occurrence;
                              iNewShortfall++;
                           }
                        }
                     }
                 }

                 #ifdef PENX_MOD
                 //rDeltaPenalty = spec[isp].penalty*spec[isp].spf*(rNewShortfall - rShortfall);
                 rDeltaPenalty = spec[isp].penalty*spec[isp].spf*(rNewShortFraction - rShortFraction);
                 #else
                 /*if (iCurrentShortfall > 1)
                    rShortFraction /= iCurrentShortfall;

                 if (iNewShortfall > 1)
                    rNewShortFraction /= iNewShortfall;*/

                 rDeltaPenalty = spec[isp].penalty*spec[isp].spf*(rNewShortFraction - rShortFraction);
                 #endif

                 #ifdef DEBUG_PEW_CHANGE_PEN
                 // rDeltaPenalty,spec[isp].penalty,spec[isp].spf,rNewShortFraction,rShortFraction
                 AppendDebugTraceFile("rDeltaPenalty,spec.penalty,spec.spf,rNewShortFraction,rShortFraction\n");
                 sprintf(debugline,"%g,%g,%g,%g,%g\n",rDeltaPenalty,spec[isp].penalty,spec[isp].spf,rNewShortFraction,rShortFraction);
                 AppendDebugTraceFile(debugline);
                 #endif

                 rSumDeltaPenalty += rDeltaPenalty;
                 *rShortfall += rNewShortfall - rOldShortfall;

              }  // Only worry about PUs where species occurs

              #ifdef DEBUG_PEW_CHANGE_PEN
              sprintf(debugline,"rSumDeltaPenalty %g\n",rSumDeltaPenalty);
              AppendDebugTraceFile(debugline);
              #endif

              #ifdef DEBUG_CHANGE_PEN
              sprintf(debugline,"%i,%g,%g,%g,%g,%g,%g\n",
                        spec[isp].name,spec[isp].target,_ZoneTarget[(isp * iZoneCount) + R[ipu]-1].target,
                        _ZoneTarget[(isp * iZoneCount) + iZone-1].target,rOldShortfall,rNewShortfall,rDeltaPenalty);
              AppendDebugTraceFile(debugline);
              #endif
          }

       #ifdef DEBUG_CHANGE_PEN
       sprintf(debugline,"ChangePen end rSumDeltaPenalty %g\n",rSumDeltaPenalty);
       AppendDebugTraceFile(debugline);
       #endif

       return (rSumDeltaPenalty);
}  // Change in penalty for adding or deleting one PU

double ConnectionCost2Linear(int ipu,int iCurrentZone, struct spustuff pu[],struct sconnections connections[],int R[],int imode,int iDebugMode)
{
       // avoid double counting connections by only adding connections where puindex > current puindex
       double fcost, rResult, rZoneConnectionCost;
       struct sneighbour *p;
       #ifdef DEBUG_CONNECTIONCOST2_LINEAR
       char debugbuffer[1000];
       #endif

       #ifdef DEBUG_CONNECTIONCOST2_LINEAR
       if (iDebugMode)
       {
          sprintf(debugbuffer,"ConnectionCost2Linear start puid %i iCurrentZone %i Ripu %i  fixedcost %g imode %i\n"
                             ,pu[ipu].id,iCurrentZone,R[ipu],connections[ipu].fixedcost,imode);
          AppendDebugTraceFile(debugbuffer);
       }
       #endif

       fcost = connections[ipu].fixedcost*imode;
       p = connections[ipu].first;
       while (p)
       {
             if (p->nbr > ipu)
             {
                rZoneConnectionCost = _RelConnectionCost[((iCurrentZone-1) * iZoneCount) + (R[p->nbr] - 1)];
                fcost += imode*p->cost*rZoneConnectionCost;

                #ifdef DEBUG_CONNECTIONCOST2_LINEAR
                if (iDebugMode)
                {
                   sprintf(debugbuffer,"ConnectionCost2Linear puidnbr %i rnbr %i costnbr %g zoneconnectioncost %lf\n"
                                      ,pu[p->nbr].id,R[p->nbr],p->cost,rZoneConnectionCost);
                   AppendDebugTraceFile(debugbuffer);
                }
                #endif
             }

             p = p->next;
       }

       rResult = fcost;

       #ifdef DEBUG_CONNECTIONCOST2_LINEAR
       if (iDebugMode)
       {
       sprintf(debugbuffer,"ConnectionCost2Linear end result %g fcost %g\n",rResult,fcost);
       AppendDebugTraceFile(debugbuffer);
       }
       #endif

       return(rResult);
}// Connection Cost Type 2 Linear

void OutputZonationCostDebugTable(int spno,struct sspecies spec[],char savename[])
{
     FILE *fp;
     int i, k, iExistingArrayIndex;
     double rShortfall, rTotalShortfall;

     fp = fopen(savename,"w");

     fprintf(fp,"SPID,target,amount,shortfall");
     for (k=1;k<=iZoneCount;k++)
         fprintf(fp,",Z%itarget,Z%iamount,Z%ishortfall",k,k,k);
     fprintf(fp,",total shortfall\n");

     for (i=spno-1;i>=0;i--)
     {
         rShortfall = 0;
         rTotalShortfall = 0;

         if (spec[i].target > 0)
            if (spec[i].target > spec[i].amount)
            {
               rShortfall = spec[i].target - spec[i].amount;
            }

         fprintf(fp,"%i,%f,%f,%f",spec[i].name,spec[i].target,spec[i].amount,rShortfall);

         rTotalShortfall += rShortfall;
         rShortfall = 0;

         for (k=0;k<iZoneCount;k++)
         {
             iExistingArrayIndex = (i * iZoneCount) + k;

             if (_ZoneTarget[iExistingArrayIndex].target > 0)
                if (_ZoneTarget[iExistingArrayIndex].target > ZoneSpec[iExistingArrayIndex].amount)
                {
                   rShortfall = _ZoneTarget[iExistingArrayIndex].target - ZoneSpec[iExistingArrayIndex].amount;
                }

             fprintf(fp,",%f,%f,%f",_ZoneTarget[iExistingArrayIndex].target,ZoneSpec[iExistingArrayIndex].amount,rShortfall);

             rTotalShortfall += rShortfall;
             rShortfall = 0;
         }

         fprintf(fp,",%f\n",rTotalShortfall);
     }

     fclose(fp);
}

// * * * * ****** Value of a Zonation System * * * *
void ZonationCost(int irun,int puno,int spno,int R[],struct spustuff pu[],struct sconnections connections[],
                 struct spu SM[], struct sspecies spec[],int aggexist,
                 struct scost *reserve,int clumptype,double prop,int iApplyReserveInit)
{
     // compute the objective function score for zonation system R

     int i,j,k,iArrayIndex, iExistingArrayIndex, iShortfall, iMissingFeatures = 0, fDebugPenaltyNegative = 0;
     double rShortfall, rShortFraction, rCurrentShortfall, rTotalShortfall = 0;
     #ifdef DEBUG_ZONATION_COST
     char debugbuffer[1000], sDebugFileName[500], sDebugCost[100];
     #endif

     #ifdef DEBUG_PENALTY_NEGATIVE
     char sDebugFile[1000], sDebugMessage[1000], sDebugIndex[100];
     FILE* DebugFile;
     fDebugPenaltyNegative = 1;
     #endif

     if (fDebugPenaltyNegative)
     {
        iZonationCost++;
        // prepare output file
        sprintf(sDebugIndex,"%i",iZonationCost);
		strcpy(sDebugFile,fnames.outputdir);
		strcat(sDebugFile,"output_penalty_detail_");
		strcat(sDebugFile,sDebugIndex);
		strcat(sDebugFile,".csv");

        DebugFile = fopen(sDebugFile,"w");
        fprintf(DebugFile,"i,SPID,shortfall,spec_penalty,spf,reserve_penalty\n");

        sprintf(sDebugMessage,"ZonationCost file >%i< was created.\n",iZonationCost);
        AppendDebugTraceFile(sDebugMessage);
     }

     if (iApplyReserveInit > 0)
     {
        InitReserve(puno,prop,R,pu,iZoneCount);  // create initial reserve
        AddReserve(puno,pu,R,iZoneCount,PuZone);
     }

     #ifdef DEBUG_ZONATION_COST
     AppendDebugTraceFile("ZonationCost start\n");
     #endif

     reserve->cost = 0;
     reserve->penalty = 0;
     reserve->connection = 0;
     reserve->shortfall = 0;
     if (aggexist)
        SetSpeciesClumps(puno,R,spec,pu,SM,connections,clumptype);

     // initialise species amounts in each zone prior to computing them for this run
     InitZoneSpec(spno,iZoneCount,ZoneSpec);
     SpeciesAmounts(spno,puno,spec,pu,SM,R,clumptype,ZoneSpec);

     for (i=0;i<spno;i++)
     {
         // R[i] is existing zone
         rShortfall = 0;
         iShortfall = 0;
         rShortFraction = 0;

         // shortfall with respect to overall targets
         if (spec[i].target > 0)
            if (spec[i].target > spec[i].amount)
            {
               rShortfall += spec[i].target - spec[i].amount;
               rShortFraction += (spec[i].target - spec[i].amount)/spec[i].target;
               iShortfall++;

               reserve->shortfall += spec[i].target - spec[i].amount;
            }

         if (spec[i].targetocc > 0)
            if (spec[i].targetocc > spec[i].occurrence)
            {
               rShortfall += spec[i].targetocc - spec[i].occurrence;
               rShortFraction += (spec[i].targetocc - spec[i].occurrence)/spec[i].targetocc;
               iShortfall++;

               reserve->shortfall += spec[i].targetocc - spec[i].occurrence;
            }

         // shortfall with respect to zone targets (removing pu from existing zone)
         // loop through all zones to compute target achievement
         for (k=0;k<iZoneCount;k++)
         {
             iExistingArrayIndex = (i * iZoneCount) + k;

             if (_ZoneTarget[iExistingArrayIndex].target > 0)
                if (_ZoneTarget[iExistingArrayIndex].target > ZoneSpec[iExistingArrayIndex].amount)
                {
                   rShortfall += _ZoneTarget[iExistingArrayIndex].target - ZoneSpec[iExistingArrayIndex].amount;
                   rShortFraction += (_ZoneTarget[iExistingArrayIndex].target - ZoneSpec[iExistingArrayIndex].amount) / _ZoneTarget[iExistingArrayIndex].target;
                   iShortfall++;

                   reserve->shortfall += _ZoneTarget[iExistingArrayIndex].target - ZoneSpec[iExistingArrayIndex].amount;
                }

             if (_ZoneTarget[iExistingArrayIndex].occurrence > 0)
                if (_ZoneTarget[iExistingArrayIndex].occurrence > ZoneSpec[iExistingArrayIndex].occurrence)
                {
                   rShortfall += _ZoneTarget[iExistingArrayIndex].occurrence - ZoneSpec[iExistingArrayIndex].occurrence;
                   rShortFraction += (_ZoneTarget[iExistingArrayIndex].occurrence - ZoneSpec[iExistingArrayIndex].occurrence) / _ZoneTarget[iExistingArrayIndex].occurrence;
                   iShortfall++;

                   reserve->shortfall += _ZoneTarget[iExistingArrayIndex].occurrence - ZoneSpec[iExistingArrayIndex].occurrence;
                }
             #ifdef DEBUG_ZONATION_COST
             sprintf(debugbuffer,"ZonationCost zone %i ztarg %g zamount %g\n"
                                ,Zones[k].id,_ZoneTarget[iExistingArrayIndex].target,ZoneSpec[iExistingArrayIndex].amount);
             AppendDebugTraceFile(debugbuffer);
             #endif
         }

         #ifdef PENX_MOD
         rCurrentShortfall = rShortFraction;
         #else
         rCurrentShortfall = rShortFraction;
         /*if (iShortfall > 1)
            rCurrentShortfall /= iShortfall;*/
         #endif
         rTotalShortfall += rShortfall;
         iMissingFeatures += iShortfall;


         reserve->penalty += rCurrentShortfall * spec[i].penalty * spec[i].spf;

         if (fDebugPenaltyNegative)
         {
            fprintf(DebugFile,"%i,%i,%g,%g,%g,%g\n",i,spec[i].name,rCurrentShortfall,spec[i].penalty,spec[i].spf,reserve->penalty);
            //"i,SPID,shortfall,spec_penalty,spf,reserve_penalty"
	     }

         #ifdef DEBUG_ZONATION_COST
         sprintf(debugbuffer,"ZonationCost spid %i targ %g reserved %g Shortfall %g rShort %g missing features %i spec.pen %g spec.spf %g\n"
                            ,spec[i].name,spec[i].target,spec[i].amount,rShortfall,rCurrentShortfall,iShortfall,spec[i].penalty,spec[i].spf);
         AppendDebugTraceFile(debugbuffer);
         #endif
     }

     for (j=0;j<puno;j++)
     {
         reserve->cost += ReturnPuZoneCost(j,R[j]);

         reserve->connection += ConnectionCost2Linear(j,R[j],pu,connections,R,1,1);
     }

     reserve->total = reserve->cost + reserve->connection + reserve->penalty;

     if (fDebugPenaltyNegative)
     {
		// finalise output file
		fclose(DebugFile);
	 }

     #ifdef DEBUG_ZONATION_COST
     sprintf(sDebugCost,"%f",reserve->cost);
     strcpy(sDebugFileName,fnames.outputdir);
     strcat(sDebugFileName,"output_ZonationCostDebug_");
     strcat(sDebugFileName,sDebugCost);
     strcat(sDebugFileName,".csv");
     OutputZonationCostDebugTable(spno,spec,sDebugFileName);

     sprintf(debugbuffer,"ZonationCost total %g cost %g connection %g penalty %g shortfall %g missing features %i \n"
                        ,reserve->total,reserve->cost,reserve->connection,reserve->penalty,rTotalShortfall,iMissingFeatures);
     AppendDebugTraceFile(debugbuffer);
     AppendDebugTraceFile("ZonationCost end\n");
     #endif

}  // score of a zonation system

// * * * * *** Set the Initial Reserve System * * * * ***
void InitReserve(int puno,double prop, int R[], struct spustuff pu[], int iZoneCount)
{
     int i;

     for (i=0;i<puno;i++)
     {
         pu[i].iPreviousStatus = R[i];

         // put the planning units into a random zone
         R[i] = RandNum(iZoneCount) + 1;
     }
}// Set Initial Reserve System

// * * * *  Species Amounts * * * * * * * *
// * * * *  puts in the effective amount for each species in reserve R
void SpeciesAmounts(int spno,int puno,struct sspecies spec[],struct spustuff pu[],struct spu SM[],
                    int R[],int clumptype,struct zonespecstruct ZoneSpec[])
{
     int i, ism, isp,ipu, iZoneSpecIndex;
     double rContribAmount;
     #ifdef ANNEALING_TEST
     char debugbuffer[1000];
     #endif

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("SpeciesAmounts start\n");
     #endif

     for (isp=0;isp<spno;isp++)
     {
         spec[isp].amount = 0;
         spec[isp].occurrence = 0;
         if (spec[isp].target2)
            SpeciesAmounts4(isp,spec,clumptype);
     }

     for (ipu=0;ipu<puno;ipu++)
         if (pu[ipu].richness)
            if (R[ipu] != 0)
               for (i=0;i<pu[ipu].richness;i++)
               {
                   ism = pu[ipu].offset + i;
                   isp = SM[ism].spindex;
                   if (spec[isp].target2 == 0)
                   {
                      //if (R[ipu] != iAvailableEquivalentZone)
                      {
                         rContribAmount = rtnConvertZoneAmount(R[ipu]-1,isp,ipu,puno,SM[ism].amount);

                         spec[isp].amount += rContribAmount;
                         spec[isp].occurrence += (rContribAmount > 0);
                      }

                      // store amount in each zone
                      iZoneSpecIndex = (isp * iZoneCount) + (R[ipu]-1);
                      ZoneSpec[iZoneSpecIndex].amount += SM[ism].amount;
                      ZoneSpec[iZoneSpecIndex].occurrence++;
                   }
               }

     #ifdef ANNEALING_TEST
     for (isp=0;isp<spno;isp++)
     {
         sprintf(debugbuffer,"SpeciesAmounts isp %i spec.amount %g\n"
                            ,isp,spec[isp].amount);
         AppendDebugTraceFile(debugbuffer);
     }
     #endif

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("SpeciesAmounts end\n");
     #endif
} // Species Amounts


// * * * * * * * * * * * * * * * * * * * * * * * *
// *****    Central Processing Loop   * * * * ****
// * * * * * * * * * * * * * * * * * * * * * * * *

// ** Threshold penalty used for Check Change ***
// ** only used when there is a cost threshold **
double ThresholdPenalty(double tpf1,double tpf2,double timeprop)
{
       if (tpf2 < 0)
          return(tpf1);

       return(tpf1*exp(tpf2*timeprop));
} // Threshold Penalty

// *** Check Change * * * * * * * * * * * * ******
void CheckChange(int iIteration, int ipu,int puno,struct spustuff pu[],struct sconnections connections[],
                 struct sspecies spec[],struct spu SM[],int R[],int imode,int iZone,
                 struct scost *change, struct scost *reserve,double costthresh,double tpf1, double tpf2,
                 double timeprop,int clumptype,int iDebugMode)
                // imode = 1 (add PU), imode = -1 (remove PU), imode = 0 (free zone swapping)
                // iZone is one based
{
     double threshpen = 0;
     int threshtype = 1;  // Debugging line. This should be input parameter not hardwired
     double tchangeconnection,tresconnection;
     #ifdef DEBUGCHECKCHANGE
     char debugline[1000];
     #endif

     #ifdef DEBUGCHECKCHANGE
     if (iDebugMode)
       AppendDebugTraceFile("CheckChange start\n");
     #endif

     // change in cost = cost of new configuration - cost of old configuration
     change->cost = ReturnPuZoneCost(ipu,iZone) - ReturnPuZoneCost(ipu,R[ipu]);

     #ifdef DEBUGCHECKCHANGE
     if (iDebugMode)
        AppendDebugTraceFile("CheckChange after ReturnPuZoneCost\n");
     #endif

     // change in connection = connection of new configuration - connection of old configuration
     change->connection = ConnectionCost2(ipu,iZone,connections,R,1,iDebugMode) -
                        ConnectionCost2(ipu,R[ipu],connections,R,1,iDebugMode);

     #ifdef DEBUGCHECKCHANGE
     if (iDebugMode)
     {
        sprintf(debugline,"CheckChange after ConnectionCost2 penalty %g\n",change->penalty);
        AppendDebugTraceFile(debugline);
     }
     #endif

     if (threshtype ==1)
     {
        tchangeconnection = change->connection;
        tresconnection = reserve->connection;
        change->connection = 0;
        reserve->connection = 0;
     }

     change->penalty = ChangePen(iIteration,ipu,puno,spec,pu,SM,R,connections,imode,clumptype,iZone,&change->shortfall);

     #ifdef DEBUGCHECKCHANGE
     if (iDebugMode)
     {
        sprintf(debugline,"CheckChange after ChangePen penalty %g\n",change->penalty);
        AppendDebugTraceFile("CheckChange after ChangePen\n");
     }
     #endif

     if (costthresh)
     {  // Threshold Penalty for costs
        if (reserve->cost + reserve->connection <= costthresh)
        {
           if (change->cost + change->connection + reserve->cost + reserve->connection <= costthresh)
              threshpen = 0;
           else
               threshpen = (change->cost + change->connection +
                           reserve->cost + reserve->connection - costthresh) *
                           ThresholdPenalty(tpf1,tpf2,timeprop);
        }
        else
        {
            if (change->cost + change->connection + reserve->cost + reserve->connection <= costthresh)
               threshpen = (reserve->cost + reserve->connection - costthresh) * ThresholdPenalty(tpf1,tpf2,timeprop);
            else
                threshpen = (change->cost + change->connection) * ThresholdPenalty(tpf1,tpf2,timeprop);
        }
     }

     change->threshpen = threshpen;

     if (threshtype ==1)
     {
        change->connection = tchangeconnection;
        reserve->connection = tresconnection;
     }

     change->total = change->cost + change->connection + change->penalty + change->threshpen;

     #ifdef DEBUGCHECKCHANGE
     if (iDebugMode)
     {
        sprintf(debugline,"%i,%i,%i,%g,%g,%g,%g,%g\n",ipu,pu[ipu].id,R[ipu],change->total,change->cost,change->connection,change->penalty,change->threshpen);
        AppendDebugFile("debug_MarZone_CheckChange.csv",debugline,fnames);
     }
     #endif

     #ifdef DEBUGCHECKCHANGE
     if (iDebugMode)
        AppendDebugTraceFile("CheckChange end\n");
     #endif
}  // Check Change

//  new species Penalty
double NewPenalty(int ipu,int isp,struct sspecies spec[],struct spustuff pu[],struct spu SM[],int imode)
{
       double newpen;
       #ifdef DEBUGNEWPENALTY
       char debugbuffer[1000];
       #endif

       newpen = spec[isp].target - spec[isp].amount - rtnAmountSpecAtPu(pu,SM,ipu,isp)*imode;

       if (newpen < 0)
          newpen = 0;

       #ifdef DEBUGNEWPENALTY
       sprintf(debugbuffer,"NewPenalty imode %i ipu %i isp %i newpen %g target %g amount %g deltaamount %g\n"
                          ,imode,ipu,isp,newpen,spec[isp].target,spec[isp].amount,rtnAmountSpecAtPu(pu,SM,ipu,isp)*imode);
       AppendDebugTraceFile(debugbuffer);
       #endif

       return(newpen);
}  // species Penalty

// * * * * Good Change * * * *
int GoodChange(struct scost change,double temp)
{
    return (exp(-change.total/temp)> rand1()) ? 1 : 0;

} // Good Change

// * * * * Do Change * * * *
void DoChange(int ipu,int puno,int *R,struct scost *reserve,struct scost change,
              struct spustuff pu[],struct spu SM[],struct sspecies spec[],struct sconnections connections[],
              int imode,int iZone,int clumptype)
{    int i,ism,isp, iArrayIndexOrigon, iArrayIndexDestination;
     double rAmount, rCurrentContribAmount, rNewContribAmount;
     #ifdef ANNEALING_TEST
     char debugbuffer[1000];
     #endif

     #ifdef EXTRADEBUGTRACE
     #endif

     reserve->cost += change.cost;
     reserve->connection += change.connection;
     reserve->penalty += change.penalty;
     reserve->shortfall += change.shortfall;

     if (pu[ipu].richness)
        for (i=0;i<pu[ipu].richness;i++)
        {
            ism = pu[ipu].offset + i;
            isp = SM[ism].spindex;

            rAmount = SM[ism].amount;

            if (spec[isp].target2 && rAmount > 0)
            {
               if (imode == 1)
               {
                  AddNewPU(ipu,isp,connections,spec,pu,SM,clumptype);
               }
               else
               {
                   RemPu(ipu,isp,connections,spec,pu,SM,clumptype);
               }
               if (spec[isp].occurrence < 0)
               {
                  printf("Warning Warning ! isp %i occ %i \n",isp,spec[isp].occurrence);
                  ShowPauseProg();
               }
            } // Type 4 species and this will impact them
            else
            {
                iArrayIndexOrigon = (isp * iZoneCount) + (R[ipu] - 1);
                iArrayIndexDestination = (isp * iZoneCount) + (iZone - 1);

                // remove amount at current zone R[ipu]
                // add amount at new zone iZone
                rCurrentContribAmount = rtnConvertZoneAmount(R[ipu]-1,isp,ipu,puno,rAmount);
                rNewContribAmount = rtnConvertZoneAmount(iZone-1,isp,ipu,puno,rAmount);

                spec[isp].occurrence += (rNewContribAmount > 0) - (rCurrentContribAmount > 0);
                spec[isp].amount += rNewContribAmount - rCurrentContribAmount;

                // remove areas from origon Zone
                ZoneSpec[iArrayIndexOrigon].occurrence -= (rAmount > 0);
                ZoneSpec[iArrayIndexOrigon].amount -= rAmount;

                // add areas to destination Zone
                ZoneSpec[iArrayIndexDestination].occurrence += (rAmount > 0);
                ZoneSpec[iArrayIndexDestination].amount += rAmount;

                #ifdef ANNEALING_TEST
                sprintf(debugbuffer,"DoChange ipu %i isp %i spec.amount %g imode %i\n"
                                   ,ipu,isp,spec[isp].amount,imode);
                AppendDebugTraceFile(debugbuffer);
                #endif
            }  // None clumping species

            if (spec[isp].sepnum>0) // Count separation but only if it is possible that it has changed
               if ((imode ==1 && spec[isp].separation < spec[isp].sepnum) || (imode == -1 && spec[isp].separation >1))
                  spec[isp].separation = CountSeparation2(isp,0,NULL,puno,R,pu,SM,spec,0);
        }

     R[ipu] = iZone;
     reserve->total = reserve->cost + reserve->connection + reserve->penalty;

} // Do Change


// * * * * * * * * * * * * * * * * * * * * * * * * *****
// * * * * *** Post Processing * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * *****

// ***  Counts the number of species missing from the reserve ***
int CountMissing(int spno,struct sspecies spec[],double misslevel,double *shortfall,double *rMinimumProportionMet)
{
    int i,j,isp = 0,iArrayIndex;
    double rFeatureShortfall, rProportionMet;
    #ifdef DEBUG_COUNT_MISSING
    char debugbuffer[1000];
    #endif

    #ifdef DEBUG_COUNT_MISSING
    sprintf(debugbuffer,"CountMissing start\n");
    AppendDebugTraceFile(debugbuffer);
    #endif

    *shortfall = 0;
    *rMinimumProportionMet = 1;
    for (i=0;i<spno;i++)
    {
        rFeatureShortfall = 0;
        rProportionMet = 1;

        if (spec[i].target > 0)
           if (spec[i].amount < spec[i].target)
           {
              rFeatureShortfall += spec[i].target - spec[i].amount;
              rProportionMet = spec[i].amount / spec[i].target;

              if (rProportionMet < *rMinimumProportionMet)
                 *rMinimumProportionMet = rProportionMet;
           }
        if (spec[i].targetocc > 0)
           if (spec[i].occurrence < spec[i].targetocc)
           {
              rFeatureShortfall += spec[i].targetocc - spec[i].occurrence;
              rProportionMet = spec[i].occurrence / spec[i].targetocc;

              if (rProportionMet < *rMinimumProportionMet)
                 *rMinimumProportionMet = rProportionMet;
           }
        if (spec[i].target > 0)
           if ( spec[i].amount/spec[i].target < misslevel)
           {
              isp++;
              //continue;
           }

        for (j=0;j<iZoneCount;j++)
        {
            iArrayIndex = (i*iZoneCount)+j;
            if (_ZoneTarget[iArrayIndex].target > 0)
            {
               if (ZoneSpec[iArrayIndex].amount < _ZoneTarget[iArrayIndex].target)
               {
                  rFeatureShortfall += _ZoneTarget[iArrayIndex].target - ZoneSpec[iArrayIndex].amount;

                  rProportionMet = ZoneSpec[iArrayIndex].amount / _ZoneTarget[iArrayIndex].target;

                  if (rProportionMet < *rMinimumProportionMet)
                     *rMinimumProportionMet = rProportionMet;
               }
               if (ZoneSpec[iArrayIndex].amount/_ZoneTarget[iArrayIndex].target < misslevel)
                  isp++;
            }
            if (_ZoneTarget[iArrayIndex].occurrence > 0)
            {
               if (ZoneSpec[iArrayIndex].occurrence < _ZoneTarget[iArrayIndex].occurrence)
               {
                  rFeatureShortfall += _ZoneTarget[iArrayIndex].occurrence - ZoneSpec[iArrayIndex].occurrence;

                  rProportionMet = ZoneSpec[iArrayIndex].occurrence / _ZoneTarget[iArrayIndex].occurrence;

                  if (rProportionMet < *rMinimumProportionMet)
                     *rMinimumProportionMet = rProportionMet;
               }
               if (ZoneSpec[iArrayIndex].occurrence/_ZoneTarget[iArrayIndex].occurrence < misslevel)
                  isp++;
            }
        }

        if (spec[i].targetocc)
           if ((double)spec[i].occurrence/(double)spec[i].targetocc < misslevel)
           {
              isp++;
              /* occshort++; */
              //continue;
           }
        if (spec[i].sepdistance && spec[i].separation < 3)
        {
           isp++;  /* count species if not met separation and not already counted */
           /* sepshort++; */
        }

        *shortfall += rFeatureShortfall;

        #ifdef DEBUG_COUNT_MISSING
        sprintf(debugbuffer,"CountMissing spid %i shortfall %g sum %g\n",spec[i].name,rFeatureShortfall,*shortfall);
        AppendDebugTraceFile(debugbuffer);
        #endif
    }

    #ifdef DEBUG_COUNT_MISSING
    sprintf(debugbuffer,"CountMissing end shortfall %g\n",*shortfall);
    AppendDebugTraceFile(debugbuffer);
    #endif

    return(isp);
}  // CountMissing

void CountPuZones(char *sLine,int puno,int R[])
{
     int i,*ZoneCount;
     char sCount[20];
     ZoneCount = (int *) calloc(iZoneCount,sizeof(int));
     for (i=0;i<iZoneCount;i++)
         ZoneCount[i] = 0;

     for (i=0;i<puno;i++)
         if (R[i] > 0)
            ZoneCount[R[i]-1] += 1;

     strcpy(sLine,"");
     for (i=0;i<iZoneCount;i++)
         {
            strcat(sLine," ");
            strcat(sLine,Zones[i].name);
            sprintf(sCount,"%i",ZoneCount[i]);
            strcat(sLine," ");
            strcat(sLine,sCount);
         }

     free(ZoneCount);
}

// * * * * Reporting Value of a Reserve * * * *
void PrintResVal (int puno, int spno,int R[],struct scost reserve,
                  struct sspecies spec[],double misslevel)
{    int i, iMissing;
     double connectiontemp = 0;
     double shortfall, rMPM;
     char sPuZones[1000];


     for (i=0;i<puno;i++)
         //if ((R[i]!=iAvailableEquivalentZone) && (R[i] != 0))
         {
            connectiontemp += ConnectionCost2Linear(i,R[i],pu,connections,R,1,0);
         }

     iMissing = CountMissing(spno,spec,misslevel,&shortfall,&rMPM);
     CountPuZones(sPuZones,puno,R);
     //strcpy(sPuZones," __");

     ShowProg("Value %.1f Cost %.1f %s Connection %.1f Missing %i Shortfall %.2f Penalty %.1f MPM %.1f\n",
              reserve.total,reserve.cost,sPuZones,connectiontemp,iMissing,shortfall,reserve.penalty,rMPM);

} /* * * * Print Reserve Value * * * * */

/****** Change the Cost *****/
/* This routine changes the values in the cost structure (such as making them negative ) */
/* Useful for altering the 'change' variable  */
void ChangeCost(struct scost *cost,double changemult)
{

     cost->total *= changemult;
     cost->connection *= changemult;
     cost->penalty *= changemult;
     cost->cost *= changemult;

}  /* ChangeCost */

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/* ANNEALING.C BEGIN */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***/
/* * * * ****** Annealing Specific Functions * * * * * * * * * * * * * * * */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***/

void ConnollyInit(int irun,int puno,int spno,struct spustuff pu[],typeconnection connections[],typesp spec[],
                  struct spu SM[], struct sanneal *anneal,int aggexist,
                  int R[],double prop,int clumptype, int iZoneCount,int verbose)
{
     int i,ipu,imode, iZone, iOldR, iPreviousR,j;
     double deltamin = 0,deltamax = 0, iLoopCounter;
     double localdelta;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[1000], sRun[20];
     FILE *fp;
     char *writename;
     #endif

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"ConnollyInit start\n");
     AppendDebugTraceFile(debugbuffer);
     #endif

     localdelta = 1E-10;

     if (aggexist)
        ClearClumps(spno,spec,pu,SM);

     #ifdef DEBUG_CONNOLLYINIT
     AppendDebugTraceFile("ConnollyInit before ZonationCost\n");
     sprintf(sRun,"%i",irun);
     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug_marzone_ConnollyInit_.csv") + strlen(sRun) + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debug_marzone_ConnollyInit_");
     strcat(writename,sRun);
     strcat(writename,".csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create debug_marzone_ConnollyInit file %s\n",writename);

     sprintf(debugbuffer,"creating file >%s<\n",writename);
     AppendDebugTraceFile(debugbuffer);

     free(writename);
     fprintf(fp,"i,ipu,puid,R,imode,iZone,total,max,min\n");
     fflush(fp);
     #endif

     ZonationCost(irun,puno,spno,R,pu,connections,SM,spec,aggexist,&reserve,clumptype,prop,1);

     #ifdef DEBUG_CONNOLLYINIT
     AppendDebugTraceFile("ConnollyInit after ZonationCost\n");
     #endif

     for (i=1;i<= (*anneal).iterations/100; i++)
     {
         #ifdef DEBUG_CONNOLLYINIT
         sprintf(debugbuffer,"ConnollyInit i %i\n",i);
         AppendDebugTraceFile(debugbuffer);
         #endif

         // toggle a random planning unit to a random zone
         do
           ipu = RandNum(puno);

         while ((pu[ipu].status > 1) || (pu[ipu].fPULock == 1));

         iPreviousR = R[ipu];

         iLoopCounter = 0;

         if (pu[ipu].fPUZone == 1)
         {
            // enforce locked into range of zones
            do
            {
              iZone = RandNum(iZoneCount) + 1;

              iLoopCounter++;

              if (iLoopCounter > 5000)
              {
                 #ifdef DEBUGTRACEFILE
                 DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,999);
                 AppendDebugTraceFile("PuZone endless loop in Annealing detected\n");
                 sprintf(debugbuffer,"puid %i iZone %i\n",pu[ipu].id,iZone);
                 AppendDebugTraceFile(debugbuffer);
                 for (j=0;j<pu[ipu].iPUZones;j++)
                 {
                     sprintf(debugbuffer,"j %i zone %i\n",j,PuZone[pu[ipu].iPUZone + j].zoneid);
                     AppendDebugTraceFile(debugbuffer);
                 }
                 #endif
                 ShowGenProg("\nPuZone endless loop in Annealing detected\n");
                 ShowGenProg("Internal error detected.  Please inform the Marxan with Zones developers.\n\n");
                 ShowPauseExit();
                 exit(1);
              }
            }
            while ((iZone == iPreviousR) || (PuNotInAllowedZone(pu[ipu],iZone,PuZone,0,'c')));
         }
         else
         {
             // allowed in any zone
             do
               iZone = RandNum(iZoneCount) + 1;

             while (iZone == iPreviousR);
         }

         //if (iZone == iAvailableEquivalentZone)
         //   imode = -1;
         //else
             imode = 1;

         CheckChange(i,ipu,puno,pu,connections,spec,SM,R,imode,iZone,&change,&reserve,0,0,0,0,clumptype,1);

         #ifdef DEBUG_CONNOLLYINIT
         AppendDebugTraceFile("ConnollyInit after CheckChange\n");
         #endif

         DoChange(ipu,puno,R,&reserve,change,pu,SM,spec,connections,imode,iZone,clumptype);

         #ifdef DEBUG_CONNOLLYINIT
         AppendDebugTraceFile("ConnollyInit after DoChange\n");
         #endif

         if (change.total > deltamax)
            deltamax = change.total;
         if (change.total >localdelta && (deltamin < localdelta || change.total < deltamin))
            deltamin = change.total;

         #ifdef DEBUG_CONNOLLYINIT
         sprintf(debugbuffer,"ConnollyInit i %i puid %i R %i imode %i total %g max %g min %g\n"
                            ,i,pu[ipu].id,R[ipu],imode,change.total,deltamax,deltamin);
         AppendDebugTraceFile(debugbuffer);
         fprintf(fp,"%i,%i,%i,%i,%i,%i,%g,%g,%g\n",i,ipu,pu[ipu].id,iOldR,imode,iZone,change.total,deltamax,deltamin);
         fflush(fp);
         #endif
     }  /** Run through this bit for iterations/100 times */

     (*anneal).Tinit = deltamax;
     deltamin *= 0.1;

     if ((*anneal).Tinit)
     if ((*anneal).Titns)
        (*anneal).Tcool = exp(log(deltamin/ (*anneal).Tinit)/(double)(*anneal).Titns);
     else
         (*anneal).Tcool = 1;

     #ifdef DEBUG_CONNOLLYINIT
     fclose(fp);
     #endif

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Tinit %g Titns %i Tcool %g\n",(*anneal).Tinit,(*anneal).Titns,(*anneal).Tcool);
     AppendDebugTraceFile(debugbuffer);
     AppendDebugTraceFile("ConnollyInit end\n");
     #endif

} /** Init Annealing Schedule According to Connolly Scheme **/

/* * * * Adaptive Annealing 2 * * * * * * * * *****/
/**** Initial Trial Runs. Run for some small time to establish sigma. ****/
void AdaptiveInit(int irun,int puno,int spno,double prop,int R[],struct spustuff pu[],
                  struct sconnections connections[],struct spu SM[],struct sspecies spec[],
                  int aggexist,struct sanneal *anneal,int clumptype,int iZoneCount)
{
     int i,isamples;
     double sum = 0,sum2 = 0;
     double sigma;
     struct scost cost;
     double c = 10;  /* An initial temperature acceptance number */
     #ifdef DEBUGTRACEFILE
     char debugbuffer[1000];
     #endif

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("AdaptiveInit start\n");
     #endif

     isamples = 1000; /* Hardwired number of samples to take */

     for (i=0;i<isamples;i++)
     {   /* Generate Random Reserve */
         //InitReserve(puno,prop,R,pu,iZoneCount);
         //AddReserve(puno,iAvailableEquivalentZone,pu,R,iZoneCount,PuZone);
         /* Score Random reserve */
         ZonationCost(irun,puno,spno,R,pu,connections,SM,spec,aggexist,&cost,clumptype,prop,1);
         /* Add Score to Sum */
         sum += cost.total;
         sum2 += cost.total*cost.total;
     } /* Sample space iterations/100 times */

     sigma = sqrt(sum2 - pow(sum/isamples,2))/(isamples-1);

     (*anneal).Tinit = c * sigma;
     (*anneal).sigma = sigma;
     (*anneal).temp = (*anneal).Tinit;
     (*anneal).tempold = (*anneal).temp;
     (*anneal).sum = 0;
     (*anneal).sum2 = 0;

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Tinit %g Titns %i Tcool %g\n",(*anneal).Tinit,(*anneal).Titns,(*anneal).Tcool);
     AppendDebugTraceFile(debugbuffer);
     #endif

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("AdaptiveInit end\n");
     #endif

} /**** Adaptive Annealing Initialisation *****/

/**** Set TInitial from this as well ****/

/**** Function to decrement T and decide if it is time to stop? *****/
void AdaptiveDec(struct sanneal *anneal)
{
     double omega = 0.7; /* Control parameter */
     double sigmanew,sigmamod;
     double lambda = 0.7; /* control parameter*/


     sigmanew = ((*anneal).sum2 - pow(((*anneal).sum/(*anneal).Tlen),2))/((*anneal).Tlen-1);
     sigmamod = (1-omega)*sigmanew + omega * (*anneal).sigma *((*anneal).temp/(*anneal).tempold);
     (*anneal).tempold = (*anneal).temp;
     (*anneal).temp = exp(-lambda*(*anneal).temp/sigmamod);
     (*anneal).sigma = sigmamod;
     (*anneal).sum = 0;
     (*anneal).sum2 = 0;

} /* Adaptive Decrement. Sets the new temperature based on old values */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ****/
/*                                                                                  */
/*        Main Annealing Engine                                                     */
/*                                                                                  */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ****/

void Annealing(int spno, int puno, struct sconnections connections[],int R[],
               typesp *spec, struct spustuff pu[], struct spu SM[], struct scost *change, struct scost *reserve,
               int repeats,int irun,char *savename,int verbose,double misslevel,
               int aggexist,
               double costthresh, double tpf1, double tpf2,int clumptype,double prop)
{
     int itime = 0,iPreviousR,iZone,iGoodChange, iLoopCounter;
     int ipu = -1, i, itemp, iRowCounter, iRowLimit;
     char tempname1[1000],tempname2[1000], sRun[20];
     int snapcount;
     int ichanges = 0;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[1000];
     FILE *fp;
     #endif
     FILE *ttfp,*zonefp;
     char *writename;

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Annealing start iterations %i\n",anneal.iterations);
     AppendDebugTraceFile(debugbuffer);
     if (verbose > 4)
     {
        sprintf(sRun,"%i",irun);
        writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug_marzone_annealing_.csv") + strlen(sRun) + 2, sizeof(char));
        strcpy(writename,fnames.outputdir);
        strcat(writename,"debug_marzone_annealing_");
        strcat(writename,sRun);
        strcat(writename,".csv");
        fp = fopen(writename,"w");
        if (fp==NULL)
           ShowErrorMessage("cannot create annealing file %s\n",writename);
        free(writename);
        fprintf(fp,"itime,ipu,puid,R,itemp,iZone,iGoodChange,changetotal,changecost,changeconnection,changepen,temp\n");
     }
     #endif

     if (fnames.saveannealingtrace)
     {
        if (fnames.saveannealingtrace==3)
           sprintf(tempname2,"%s_anneal_objective%05i.csv",savename,irun%10000);
        else
        if (fnames.saveannealingtrace==2)
           sprintf(tempname2,"%s_anneal_objective%05i.txt",savename,irun%10000);
        else
            sprintf(tempname2,"%s_anneal_objective%05i.dat",savename,irun%10000);

        writename = (char *) calloc(strlen(fnames.outputdir) + strlen(tempname2) + 2, sizeof(char));
        strcat(writename,tempname2);
        if ((ttfp = fopen(writename,"w"))==NULL)
           ShowErrorMessage("cannot create threshold trace file %s\n",writename);
        free(writename);
        if (fnames.saveannealingtrace > 1)
        {
           fprintf(ttfp,"iteration,threshold,dochange,total,cost,connection,penalty,shortfall,puindex,anneal.temp\n");

           // write iteration zero
           fprintf(ttfp,"%i,%f,%i,%f,%f,%f,%f,%f,%i,%f\n"
                       ,itime,costthresh,iGoodChange,reserve->total
                       ,reserve->cost,reserve->connection,reserve->penalty,reserve->shortfall
                       ,ipu,anneal.temp);
        }
        else
        {
            fprintf(ttfp,"iteration threshold dochange total cost connection penalty shortfall puindex\n");

           // write iteration zero
           fprintf(ttfp,"%i %f %i %f %f %f %f %f %i %f\n"
                       ,itime,costthresh,iGoodChange,reserve->total
                       ,reserve->cost,reserve->connection,reserve->penalty,reserve->shortfall
                       ,ipu,anneal.temp);
        }

        if (fnames.suppressannealzones==0)
        {
           if (fnames.saveannealingtrace==3)
              sprintf(tempname2,"%s_anneal_zones%05i.csv",savename,irun%10000);
           else
           if (fnames.saveannealingtrace==2)
              sprintf(tempname2,"%s_anneal_zones%05i.txt",savename,irun%10000);
           else
               sprintf(tempname2,"%s_anneal_zones%05i.dat",savename,irun%10000);

           //sprintf(tempname2,"%s_anneal_zones%05i.csv",savename,irun%10000);
           writename = (char *) calloc(strlen(fnames.outputdir) + strlen(tempname2) + 2, sizeof(char));
           //strcpy(writename,fnames.outputdir);
           strcat(writename,tempname2);
           if ((zonefp = fopen(writename,"w"))==NULL)
              ShowErrorMessage("cannot create threshold trace file %s\n",writename);
           free(writename);
           fprintf(zonefp,"configuration");
           if (fnames.saveannealingtrace > 1)
           {
              for (i = 0;i<puno;i++)
                  fprintf(zonefp,",%i",pu[i].id);
              fprintf(zonefp,"\n0");

              for (i = 0;i<puno;i++)
                  fprintf(zonefp,",%i",R[i]);
           }
           else
           {
               for (i = 0;i<puno;i++)
                   fprintf(zonefp," %i",pu[i].id);
               fprintf(zonefp,"\n0");

               for (i = 0;i<puno;i++)
                   fprintf(zonefp," %i",R[i]);
           }
           fprintf(zonefp,"\n");
        }

        iRowCounter = 0;
        if (fnames.annealingtracerows == 0)
           iRowLimit = 0;
        else
            iRowLimit = floor(anneal.iterations / fnames.annealingtracerows);
     }

     ShowGenProgInfo("  Main Annealing Section.\n");
     for (itime = 1;itime<=anneal.iterations;itime++)
     {
         // toggle a random planning unit between reserved and available
         // (where reserved is one of the non-available zones)
         do
           ipu = RandNum(puno);

         while ((pu[ipu].status > 1) || (pu[ipu].fPULock == 1));

         iPreviousR = R[ipu];
         // swap pu to any zone at random
         //itemp = 0;

         iLoopCounter = 0;

         if (pu[ipu].fPUZone == 1)
         {
            // enforce locked into range of zones
            do
            {
              iZone = RandNum(iZoneCount) + 1;

              iLoopCounter++;

              if (iLoopCounter > 5000)
              {
                 #ifdef DEBUGTRACEFILE
                 DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,999);
                 AppendDebugTraceFile("PuZone endless loop in Annealing detected\n");
                 sprintf(debugbuffer,"puid %i iZone %i\n",pu[ipu].id,iZone);
                 AppendDebugTraceFile(debugbuffer);
                 #endif
                 ShowGenProg("\nPuZone endless loop in Annealing detected\n");
                 ShowGenProg("Internal error detected.  Please inform the Marxan with Zones developers.\n\n");
                 ShowPauseExit();
                 exit(1);
              }
            }
            while ((iZone == iPreviousR) || (PuNotInAllowedZone(pu[ipu],iZone,PuZone,0,'b')));
         }
         else
         {
             // allowed in any zone
             do
               iZone = RandNum(iZoneCount) + 1;

             while (iZone == iPreviousR);
         }

         //if (iZone == iAvailableEquivalentZone)
         //   itemp = -1;
         //else
             itemp = 1;

         #ifdef TRACE_ZONE_TARGETS
         sprintf(debugbuffer,"annealing time %i of %i\n",itime,anneal.iterations);
         AppendDebugTraceFile(debugbuffer);
         #endif

         CheckChange(itime,ipu,puno,pu,connections,spec,SM,R,itemp,iZone,change,reserve,
                     costthresh,tpf1,tpf2,(double) itime/ (double) anneal.iterations,clumptype,1);

         #ifdef TRACE_ZONE_TARGETS
         sprintf(debugbuffer,"annealing after CheckChange\n");
         AppendDebugTraceFile(debugbuffer);
         #endif

         /* Need to calculate Appropriate temperature in GoodChange or another function */
         /* Upgrade temperature */
         if (itime%anneal.Tlen == 0)
         {
            if (anneal.type == 3)
               AdaptiveDec(&anneal);
            else
                anneal.temp = anneal.temp*anneal.Tcool;

            ShowDetProg("time %i temp %f Complete %i%% currval %.4f\n",
                        itime,anneal.temp,(int)itime*100/anneal.iterations,reserve->total);
         } /* reduce temperature */
         if (fnames.savesnapsteps && !(itime % fnames.savesnapfrequency))
         {
            if (repeats > 1)
               sprintf(tempname1,"_r%05i",irun);
            else
                tempname1[0] = 0;
            if (fnames.savesnapchanges == 3)
               sprintf(tempname2,"%s_snap%st%05i.csv",savename,tempname1,++snapcount%10000);
            else
            if (fnames.savesnapchanges == 2)
               sprintf(tempname2,"%s_snap%st%05i.txt",savename,tempname1,++snapcount%10000);
            else
                sprintf(tempname2,"%s_snap%st%05i.dat",savename,tempname1,++snapcount%10000);

            OutputSolution(puno,R,pu,tempname2,fnames.savesnapsteps);
         } /* Save snapshot every savesnapfreq timesteps */

         if (GoodChange(*change,anneal.temp)==1)
         {
            #ifdef TRACE_ZONE_TARGETS
            sprintf(debugbuffer,"annealing after GoodChange\n");
            AppendDebugTraceFile(debugbuffer);
            #endif

            iGoodChange = 1;

            ++ichanges;
            DoChange(ipu,puno,R,reserve,*change,pu,SM,spec,connections,itemp,iZone,clumptype);
            if (fnames.savesnapchanges && !(ichanges % fnames.savesnapfrequency))
            {
               if (repeats > 1)
                  sprintf(tempname1,"_r%05i",irun);
               else
                   tempname1[0] = 0;
               if (fnames.savesnapchanges == 3)
                  sprintf(tempname2,"%s_snap%sc%05i.csv",savename,tempname1,++snapcount%10000);
               else
               if (fnames.savesnapchanges == 2)
                  sprintf(tempname2,"%s_snap%sc%05i.txt",savename,tempname1,++snapcount%10000);
               else
                   sprintf(tempname2,"%s_snap%sc%05i.dat",savename,tempname1,++snapcount%10000);

               OutputSolution(puno,R,pu,tempname2,fnames.savesnapchanges);
            } /* Save snapshot every savesnapfreq changes */

         } /* Good change has been made */
         else
             iGoodChange = 0;

         #ifdef TRACE_ZONE_TARGETS
         sprintf(debugbuffer,"annealing after DoChange\n");
         AppendDebugTraceFile(debugbuffer);
         #endif

         if (anneal.type == 3)
         {
            anneal.sum += reserve->total;
            anneal.sum2 += reserve->total*reserve->total;
         } /* Keep track of scores for averaging stuff */

         #ifdef DEBUGTRACEFILE
         if (verbose > 4)
            fprintf(fp,"%i,%i,%i,%i,%i,%i,%i,%f,%f,%f,%f,%f\n"
                      ,itime,ipu,pu[ipu].id,iPreviousR,itemp,iZone,iGoodChange,change->total,change->cost,change->connection,change->penalty,anneal.temp);
         #endif

         if (fnames.saveannealingtrace)
         {
            iRowCounter++;
            if (iRowCounter > iRowLimit)
               iRowCounter = 1;

            if (iRowCounter == 1)
            {
               if (fnames.suppressannealzones==0)
                  fprintf(zonefp,"%i",itime);

               if (fnames.saveannealingtrace > 1)
               {
                  fprintf(ttfp,"%i,%f,%i,%f,%f,%f,%f,%f,%i,%f\n"
                          ,itime,costthresh,iGoodChange,reserve->total
                          ,reserve->cost,reserve->connection,reserve->penalty,reserve->shortfall
                          ,ipu,anneal.temp); // itime,costthresh,cost,connection,penalty

                  #ifdef DEBUG_PEW_CHANGE_PEN
                  AppendDebugTraceFile("iteration,threshold,dochange,total,cost,connection,penalty,puindex\n");
                  sprintf(debugbuffer,"%i,%f,%i,%f,%f,%f,%f,%i\n"
                          ,itime,costthresh,iGoodChange,reserve->total
                          ,reserve->cost,reserve->connection,reserve->penalty
                          ,ipu);
                  AppendDebugTraceFile(debugbuffer);
                  #endif

                  if (fnames.suppressannealzones==0)
                     for (i = 0;i<puno;i++)
                         fprintf(zonefp,",%i",R[i]);
               }
               else
               {
                   fprintf(ttfp,"%i %f %i %f %f %f %f %f %i %f\n"
                           ,itime,costthresh,iGoodChange,reserve->total
                           ,reserve->cost,reserve->connection,reserve->penalty,reserve->shortfall
                           ,ipu,anneal.temp);

                   if (fnames.suppressannealzones==0)
                      for (i = 0;i<puno;i++)
                          fprintf(zonefp," %i",R[i]);
               }

               if (fnames.suppressannealzones==0)
                  fprintf(zonefp,"\n");
            }
         }

     } /* Run Through Annealing */

     /** Post Processing  * * * * **/
     if (verbose >1)
     {
        ShowGenProg("  Annealing:");
        PrintResVal(puno,spno,R,*reserve,spec,misslevel);
     }

     if (aggexist)
        ClearClumps(spno,spec,pu,SM);

     #ifdef DEBUGTRACEFILE
     if (verbose > 4)
        AppendDebugTraceFile("Annealing before ResevedCost\n");
     #endif

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"Annealing end changes %i\n",ichanges);
     AppendDebugTraceFile(debugbuffer);
     if (verbose > 4)
        fclose(fp);
     #endif

     if (fnames.saveannealingtrace)
     {
        fclose(ttfp);
        if (fnames.suppressannealzones==0)
           fclose(zonefp);
     }

}  /* Main Annealing Function */

/* ANNEALING.C END */
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

/* optimisation functions written by Matt Watts */

void DumpBinarySearchArrays(char *sName,struct sfname fnames, int puno, int spno, struct binsearch PULookup[],
                            struct binsearch SPLookup[])
{
     FILE *pufp, *specfp;
     int i;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug") + strlen(sName) + strlen("pu.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debug");
     strcat(writename,sName);
     strcat(writename,"pu.csv");
     pufp = fopen(writename,"w");
     if (pufp==NULL)
        ShowErrorMessage("cannot create BinarySearchArrays pu file %s\n",writename);
     free(writename);
     fputs("name,index\n",pufp);
     for (i=0;i<puno;i++)
     {
         fprintf(pufp,"%d,%d\n",PULookup[i].name,PULookup[i].index);
     }
     fclose(pufp);

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("debug") + strlen(sName) + strlen("spec.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"debug");
     strcat(writename,sName);
     strcat(writename,"spec.csv");
     specfp = fopen(writename,"w");
     if (specfp==NULL)
        ShowErrorMessage("cannot create BinarySearchArrays spec file %s\n",writename);
     free(writename);
     fputs("name,index\n",specfp);
     for (i=0;i<spno;i++)
     {
         fprintf(specfp,"%d,%d\n",SPLookup[i].name,SPLookup[i].index);
     }
     fclose(specfp);
}

void siftDown(struct binsearch numbers[], int root, int bottom, int array_size)
{
     int done, maxChild;
     typebinsearch temp;

     done = 0;
     while ((root*2 <= bottom) && (!done))
     {
           if (root*2 < array_size)
           {
              if (root*2 == bottom)
                 maxChild = root * 2;
              else if (numbers[root * 2].name > numbers[root * 2 + 1].name)
                      maxChild = root * 2;
                   else
                       maxChild = root * 2 + 1;

              if (numbers[root].name < numbers[maxChild].name)
              {
                 temp = numbers[root];
                 numbers[root] = numbers[maxChild];
                 numbers[maxChild] = temp;
                 root = maxChild;
              }
              else
                  done = 1;
           }
           else
               done = 1;
     }
}

void heapSort(struct binsearch numbers[], int array_size)
{
     int i;
     typebinsearch temp;

     for (i = (array_size / 2)-1; i >= 0; i--)
         siftDown(numbers, i, array_size, array_size);

     for (i = array_size-1; i >= 1; i--)
     {
         temp = numbers[0];
         numbers[0] = numbers[i];
         numbers[i] = temp;
         siftDown(numbers, 0, i-1, array_size);
     }
}

void heapSortBinSearch(struct binsearch numbers[], int array_size)
{
     heapSort(numbers,array_size);
}

void PrepareBinarySearchArrays(int puno, int spno, struct spustuff PU[], typesp spec[],
                               struct binsearch *PULookup[], struct binsearch *SPLookup[])
{
     int i;

     /* create the lookup arrays for planning unit and species names */
     *PULookup = (struct binsearch *) calloc(puno,sizeof(struct binsearch));
     *SPLookup = (struct binsearch *) calloc(spno,sizeof(struct binsearch));

     /* populate the lookup arrays with planning unit and species names*/
     for (i=0;i<puno;i++)
     {
         (* PULookup)[i].name = PU[i].id;
         (* PULookup)[i].index = i;
     }
     for (i=0;i<spno;i++)
     {
         (* SPLookup)[i].name = spec[i].name;
         (* SPLookup)[i].index = i;
     }

     #ifdef DEBUGTRACEFILE
     if (iVerbosity > 3)
        DumpBinarySearchArrays("before",fnames,puno,spno,(* PULookup),(* SPLookup));
     #endif

     /* sort the lookup arrays by name */
     heapSortBinSearch((* PULookup),puno);
     heapSortBinSearch((* SPLookup),spno);

     #ifdef DEBUGTRACEFILE
     if (iVerbosity > 3)
        DumpBinarySearchArrays("after",fnames,puno,spno,(* PULookup),(* SPLookup));
     //ShowGenProg("PrepareBinarySearchArrays has been executed\n");
     #endif
}

void TestFastPUIDtoPUINDEX(int puno, struct binsearch PULookup[], struct spustuff PU[], struct sfname fnames)
{
     FILE *fp;
     int i;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("TestFastPUIDtoPUINDEX.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"TestFastPUIDtoPUINDEX.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create TestFastPUIDtoPUINDEX file %s\n",writename);
     free(writename);
     fputs("name,index,bin search index\n",fp);
     for (i=0;i<puno;i++)
     {
         fprintf(fp,"%d,%d,%d\n",PU[i].id,i,FastPUIDtoPUINDEX(puno,PU[i].id,PULookup));
     }
     fclose(fp);
}


int FastPUIDtoPUINDEX(int puno,int name, struct binsearch PULookup[])
{
    /* use a binary search to find the index of planning unit "name" */
    int iTop, iBottom, iCentre, iCount;

    iTop = 0;
    iBottom = puno-1;
    iCentre = iTop + floor(puno / 2);

    while ((iTop <= iBottom) && (PULookup[iCentre].name != name))
    {
        if (name < PULookup[iCentre].name)
        {
            iBottom = iCentre - 1;
            iCount = iBottom - iTop + 1;
            iCentre = iTop + floor(iCount / 2);
        }
        else
        {
            iTop = iCentre + 1;
            iCount = iBottom - iTop + 1;
            iCentre = iTop + floor(iCount / 2);
        }
    }
    return(PULookup[iCentre].index);
}

void TestFastSPIDtoSPINDEX(int spno, struct binsearch SPLookup[], typesp spec[], struct sfname fnames)
{
     FILE *fp;
     int i;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("TestFastSPIDtoSPINDEX.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"TestFastSPIDtoSPINDEX.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create TestFastSPIDtoSPINDEX file %s\n",writename);
     free(writename);
     fputs("name,index,bin search index\n",fp);
     for (i=0;i<spno;i++)
     {
         fprintf(fp,"%d,%d,%d\n",spec[i].name,i,FastSPIDtoSPINDEX(spno,spec[i].name,SPLookup));
     }
     fclose(fp);
}


int FastSPIDtoSPINDEX(int spno,int name, struct binsearch SPLookup[])
{
    /* use a binary search to find the index of planning unit "name" */
    int iTop, iBottom, iCentre, iCount;

    iTop = 0;
    iBottom = spno-1;
    iCentre = iTop + floor(spno / 2);

    while ((iTop <= iBottom) && (SPLookup[iCentre].name != name))
    {
          if (name < SPLookup[iCentre].name)
          {
             iBottom = iCentre - 1;
             iCount = iBottom - iTop + 1;
             iCentre = iTop + floor(iCount / 2);
          }
          else
          {
              iTop = iCentre + 1;
              iCount = iBottom - iTop + 1;
              iCentre = iTop + floor(iCount / 2);
          }
    }
    return(SPLookup[iCentre].index);
}


// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/* CLUMPING.C BEGIN */

/* * * * * * * * * * * * * * * * * * * * *****/
/* * * * *  Clump Utilities * * * * * * * * **/
/* * * * * * * * * * * * * * * * * * * * ****/

// Clear a single Clump
void ClearClump(int isp,struct sclumps *target,struct spustuff pu[],
                struct spu SM[])
{
     struct sclumppu *ppu;

      // Remove all links from this clump
     while (target->head)
     {
           ppu = target->head;
           if (rtnClumpSpecAtPu(pu,SM,ppu->puid,isp) == target->clumpid) // in case pu is in new clump
              setClumpSpecAtPu(pu,SM,ppu->puid,isp,0);
           target->head = ppu->next;
           free(ppu);
           DebugFree(sizeof(struct sclumppu));
     } // Remove all links from this clump

} // Clear Clump (single clump removal)

/**** Function does this cut clump? * * * */
/**** Returns the value of the fragmented clumps if the given PU were removed ***/
/**** If imode == 1 then it will also do a separation count ***/

int ClumpCut(int isp,struct spustuff pu[],
             struct sspecies spec[],struct sclumps *clump,
             struct sclumppu *clumppu,struct sconnections connections[],struct spu SM[],
             double *totalamount,int *totalocc,
             int *iseparation, int imode,int clumptype)
{
    int ineighbour = 0,iclumps = 0;
    struct slink{int id; struct slink *next;} *head = NULL, *newhead,*thead, *clumplist, *clumpcurr;
    struct sneighbour *pnbr;
    struct sclumps *spclump = NULL, *newpclump;
    struct sclumppu *pclumppu;
    double clumpamount, rAmount;
    int iocc;

    *totalamount = 0;
    *totalocc = 0;

    /* Set up spclump for counting Separation */
    if (imode)
    {
        newpclump = (struct sclumps *) malloc(sizeof(struct sclumps));
        newpclump->clumpid = clumppu->puid;
        newpclump->amount = 0;
        newpclump->next = spclump;
        spclump = newpclump;
    }

    /** Generate list of all neighbours and count them **/
    /*First check if there are no neighbours then exit. **/
      /* return null for no clump cut done and need to do separation count */
    if (connections[clumppu->puid].nbrno == 0)
    {
        if (imode)
        {
            *iseparation = CountSeparation(isp,spclump,pu,SM,spec,0);
            free(spclump);
            DebugFree(sizeof(struct sclumps));
        }
        return(0);
    }

    for (pnbr = connections[clumppu->puid].first; pnbr;pnbr = pnbr->next)
    {
        if (rtnClumpSpecAtPu(pu,SM,pnbr->nbr,isp) == clump->clumpid)
        {
            ineighbour++;
            newhead = (struct slink *) malloc(sizeof(struct slink));
            newhead->id = pnbr->nbr;
            newhead->next = head;
            head = newhead;
        } /** If neighbour is part of the same clump **/
    } /** For cycling through all neighbours **/

    if (ineighbour <= 1) { /* One or fewer neighbours */
        if (imode)
        { /* separation distance called */
            for(pclumppu=clump->head;pclumppu;pclumppu=pclumppu->next)
             if (pclumppu != clumppu)
             {
                newpclump = (struct sclumps *) malloc(sizeof(struct sclumps));
                newpclump->clumpid = pclumppu->puid;
                newpclump->amount = clump->amount - rtnAmountSpecAtPu(pu,SM,clumppu->puid,isp);
                newpclump->next = spclump;
                spclump = newpclump;

             } /* found someone in the clump who is not being removed */

              *iseparation = CountSeparation(isp,spclump,pu,SM,spec,0);
        }
             else (*iseparation = spec[isp].sepnum);
        if (head)
        {
            free(head);
            DebugFree(sizeof(struct slink));
        }
        while (spclump)
        {
            newpclump = spclump;
            spclump = spclump->next;
            free(newpclump);
            DebugFree(sizeof(struct sclumps));
        }  /* clearing up spclump */
        rAmount = rtnAmountSpecAtPu(pu,SM,clumppu->puid,isp);
        *totalamount = PartialPen4(isp,clump->amount-rAmount,spec,clumptype);
        *totalocc = (clump->occs - (rAmount > 0))*(*totalamount > 0); /* count only if still valid size */
    return(0);
    } /** Only one neighbour **/

    /** More than one neighbour. Can they form their own clump? **/
    /* Put first neighbour at head of new list */
    while (head)
    {
    clumpamount = 0;
    iclumps++;
    clumplist = (struct slink *) malloc(sizeof(struct slink));
    clumplist->next = NULL;
    clumplist->id = head->id;
    clumpcurr = clumplist;
    newhead = head;
    head = head->next;
    free(newhead);  /* move first site from head to clumplist */
    DebugFree(sizeof(struct slink));
    ineighbour--;
    do
    {
      for (pnbr = connections[clumpcurr->id].first;pnbr;pnbr = pnbr->next)
      {
          if (rtnClumpSpecAtPu(pu,SM,pnbr->nbr,isp) == clump->clumpid && pnbr->nbr != clumppu->puid) /* if neighbour in clump but not cut out one */
          {
             for (newhead = clumplist;newhead && newhead->id != pnbr->nbr;newhead= newhead->next)
                 ; /* Cycle through clumplist looking to see if this fellow is already in it */
             if (!newhead)
             {
                newhead = (struct slink *) malloc(sizeof(struct slink));
                newhead->id = pnbr->nbr;
                newhead->next = clumpcurr->next;
                clumpcurr->next = newhead;  /* put this item in my clumplist */
                /* go through neighbour list and see if this one is there */
                for (newhead=head;newhead && newhead->id != pnbr->nbr;newhead = newhead->next)
                    ; /* find this item on the neighbour list */
                if (newhead && newhead->id == pnbr->nbr)
                {
                   ineighbour--;
                   if (newhead == head)
                      head = newhead->next;
                   else
                   {
                       for (thead=head;thead->next != newhead; thead = thead->next)
                           ; /* find link before the one to be removed */
                       thead->next = newhead->next;
                   } /* remove link that is not head */
                   free(newhead);
                   DebugFree(sizeof(struct slink));
                } /* A new neighbour is taken into account*/
             } /* Adding a novel neighbour to list */
          } /* found a neighbour in clump which isn't the one being cut */
      } /* cycling through every neighbour on this clump */

      /* point to next one on list but keep clump head where it is */
      clumpcurr = clumpcurr->next;

    } while (clumpcurr); /* if youv'e run out of new list then...*/

    iocc = 0;
    for(newhead=clumplist;newhead;newhead=newhead->next) {
        rAmount = rtnAmountSpecAtPu(pu,SM,newhead->id,isp);
        clumpamount += rAmount; /* find total amount */
        iocc += (rAmount > 0);
    }
    *totalamount += PartialPen4(isp,clumpamount,spec,clumptype);
    if (PartialPen4(isp,clumpamount,spec,clumptype))
        *totalocc += iocc;

    if (imode)
        for(newhead=clumplist;newhead;newhead=newhead->next) {
            newpclump = (struct sclumps *)malloc(sizeof(struct sclumps));
            newpclump->clumpid = newhead->id;
            newpclump->amount = clumpamount;
            newpclump->next = spclump;
            spclump = newpclump;
    } /* stick this clump into my clump list for separation purposes */

    /* clean up all lists */
    while (clumplist) {
        clumpcurr = clumplist;
        clumplist = clumplist->next;
        free(clumpcurr);
        DebugFree(sizeof(struct slink));
    } /** clean up clumplist **/
    } /*** Continue clump formation whilst there are members in the list*/

    if (imode)
    {
        *iseparation = CountSeparation(isp,spclump,pu,SM,spec,0);
      while (spclump)
      {
        newpclump = spclump;
        spclump = spclump ->next;
        free(newpclump);
        DebugFree(sizeof(struct sclumps));
      } /* clean up separation clump list */
    }
    else
        *iseparation = spec[isp].sepnum;

    while (head) {
        newhead = head;
        head = head->next;
        free(newhead);
        DebugFree(sizeof(struct slink));
    } /** clean up neighbour list **/
    return(iclumps);
} /*** Function Clump Cut.. Do I cut ? ****/


/* * * * * * * * Clear Clumps * * * * * * * */
/*** This is for clean up purposes * * * * */
void ClearClumps(int spno,struct sspecies spec[],struct spustuff pu[],
                 struct spu SM[])
{
     int i;
     struct sclumps *pclump;

     for (i=0;i<spno;i++)
     {
         while (spec[i].head)
         {
               ClearClump(i,spec[i].head,pu,SM);
               pclump = spec[i].head;
               spec[i].head = spec[i].head->next;
               free(pclump);

         }  // Remove each clump
         spec[i].clumps = 0;

     } /** Clear clump for each species **/
} /* * * * Clear\n Clumps ******/


/***** Add New Clump ******/
struct sclumps *AddNewClump(int isp,int ipu,struct sspecies spec[],struct spustuff pu[],struct spu SM[])
{
       int iclumpno = 0;
       struct sclumps *pclump,*pnewclump;
       struct sclumppu *pnewclumppu;
       double rAmount;

       /** find good clump number **/
       pclump = spec[isp].head;
       if (!pclump)
          iclumpno = 1;
       while (!iclumpno)
       {
             if (!pclump->next)
             {
                iclumpno = pclump->clumpid+1;
                break;
             } /* I've found the end of the list */
             if (pclump->next->clumpid - pclump->clumpid > 1)
             {
                iclumpno = pclump->clumpid+1;
                continue;
             } /* Looking for good number */
             pclump = pclump->next;
       } /*  Find first available clump number */

       setClumpSpecAtPu(pu,SM,ipu,isp,iclumpno);
       pnewclump = (struct sclumps *) malloc(sizeof(struct sclumps));
       pnewclump->clumpid = iclumpno;
       if (spec[isp].head)
       {
          pnewclump->next = pclump->next;
          pclump->next = pnewclump;
       } /* Stick clump into correct location */
       else
       {
           spec[isp].head = pnewclump;
           pnewclump->next = NULL;
       } /* First clump on the block */
       /** Add first clumppu to this new clump **/
       pnewclumppu = (struct sclumppu *) malloc(sizeof(struct sclumppu));
       pnewclumppu->puid = ipu;
       pnewclumppu->next = NULL;
       pnewclump->head = pnewclumppu;
       rAmount = rtnAmountSpecAtPu(pu,SM,ipu,isp);
       pnewclump->amount = rAmount;
       pnewclump->occs = (rAmount > 0);

       spec[isp].clumps++;

       return(pnewclump);

}  /*(* * * * *** Add New Clump * * * * ******/

/* * * * * * * * * ADD NEW PU * * * * * * * */
/* * * * **** Add New Planning Unit for a given Species * * * */
/* * * * * * * * * * * * * * * * * * * * ****/
void AddNewPU(int ipu,int isp,struct sconnections connections[],struct sspecies spec[],struct spustuff pu[],
              struct spu SM[],int clumptype)
{
     int ineighbours = 0;
     int iclumpno, iClump;
     struct sneighbour *pnbr;
     struct sclumps *pclump, *pnewclump, *ptempclump;
     struct sclumppu *pnewclumppu;
     double ftemp, rAmount;

     pnbr = connections[ipu].first;
     while (pnbr)
     {
           // Check all the neighbours to see if any are already in clumps
           iClump = rtnClumpSpecAtPu(pu,SM,pnbr->nbr,isp);
           if (iClump > 0)
           {  // Neighbour that is part of clump
              ineighbours++;
              if (ineighbours == 1)
              {  // Join to the first clump that is also a neighbour
                 iclumpno = iClump;
                 for (pclump = spec[isp].head; pclump->clumpid != iclumpno;pclump = pclump->next)
                     ;
                 pnewclumppu = (struct sclumppu *) malloc(sizeof(struct sclumppu));
                 pnewclumppu->puid = ipu;
                 pnewclumppu->next = pclump->head;
                 setClumpSpecAtPu(pu,SM,pnewclumppu->puid,isp,iclumpno);
                 pclump->head = pnewclumppu;

                 /* Remove old value for this clump */
                 ftemp = PartialPen4(isp,pclump->amount,spec,clumptype);
                 spec[isp].amount -= ftemp;
                 spec[isp].occurrence -= pclump->occs *(ftemp > 0);
                 rAmount = rtnAmountSpecAtPu(pu,SM,ipu,isp);
                 pclump->occs += (rAmount > 0);
                 pclump->amount += rAmount;
              }  // Adding the pu to the clump
              else
              {   // pclump points to the good clump
                  if (pclump->clumpid != iClump)
                  {  // Check if this is a different clump
                     // Join this new clump to the old one
                     for (pnewclump= spec[isp].head; pnewclump->clumpid != iClump;pnewclump = pnewclump->next)
                         ;  // point pnewclump to the joining clump
                     // Run through joining clump and tell all pu's their new number
                     for (pnewclumppu = pnewclump->head;pnewclumppu->next;pnewclumppu=pnewclumppu->next)
                         setClumpSpecAtPu(pu,SM,pnewclumppu->puid,isp,pclump->clumpid);
                     setClumpSpecAtPu(pu,SM,pnewclumppu->puid,isp,pclump->clumpid);
                     /** cut out this clump and join it to pclump **/
                     pnewclumppu->next = pclump->head;
                     pclump->head = pnewclump->head;
                     pclump->amount += pnewclump->amount;
                     pclump->occs += pnewclump->occs;
                     ftemp = PartialPen4(isp,pnewclump->amount,spec,clumptype);
                     spec[isp].amount -= ftemp;
                     spec[isp].occurrence -= pnewclump->occs * (ftemp > 0);

                     /** Remove clump head and free memory **/
                     if (pnewclump == spec[isp].head)
                        spec[isp].head = pnewclump->next;
                     else
                     {
                         for (ptempclump = spec[isp].head;ptempclump->next != pnewclump;ptempclump = ptempclump->next)
                             ; /** Find clump just before redundant clump **/
                         ptempclump->next = pnewclump->next;
                     }

                     free(pnewclump);
                     DebugFree(sizeof(struct sclumps));

                  } /** Join the two clumps together **/
              } /** Found another neighbour **/
           }
           pnbr = pnbr->next;
     } /** cycling through all the neighbours **/

     /*** Adding a New clump ***/
     if (!ineighbours)
     {
        AddNewClump(isp,ipu,spec,pu,SM);
        ftemp = PartialPen4(isp,rAmount,spec,clumptype);
        spec[isp].amount += ftemp;
        spec[isp].occurrence += (ftemp>0);
     } /** Adding a new clump **/

     /*** Correcting Amount if new clump not added ***/
     if (ineighbours)
     {
        ftemp = PartialPen4(isp,pclump->amount,spec,clumptype);
        spec[isp].amount += ftemp;
        spec[isp].occurrence += pclump->occs * (ftemp > 0);
     }
} /* * * * * * * * Add New Pu * * * * * * * * */

/* * * * ****** REM PU * * * * * * * * * * * * * * * * * * * */
/* * * * *** Remove a planning unit. Note it is similar to CutClump but actually does action */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
void RemPu(int ipu, int isp,struct sconnections connections[], struct sspecies spec[],struct spustuff pu[],
           struct spu SM[],int clumptype)
{
    int ineighbours = 0;
    struct slink{int id;struct slink *next;} *head = NULL, *newhead, *thead;
    struct sclumps *oldclump,*pclump;
    struct sclumppu *cppu,*ppu, *clumpcurr, *tppu;
    struct sneighbour *pnbr;
    double oldamount,newamount = 0.0, rAmount;
    int newoccs;

    for (oldclump = spec[isp].head;oldclump && oldclump->clumpid != rtnClumpSpecAtPu(pu,SM,ipu,isp); oldclump= oldclump->next)
    ; /* Find the correct clump to remove */
    if (!oldclump)
       ShowErrorMessage("Serious error in Remove Type 4 species routine\n");

    for (cppu = oldclump->head;cppu->puid != ipu; cppu = cppu->next)
        ; /* Locate the correct clumppu */
    setClumpSpecAtPu(pu,SM,cppu->puid,isp,0);

    oldamount = PartialPen4(isp,oldclump->amount,spec,clumptype);
    spec[isp].amount -= oldamount;
    spec[isp].occurrence -= oldclump->occs * (oldamount > 0);

    for (pnbr = connections[cppu->puid].first;pnbr;pnbr = pnbr->next)
        if (rtnClumpSpecAtPu(pu,SM,pnbr->nbr,isp) == oldclump->clumpid)
        {
            ineighbours++;
            newhead = (struct slink *)malloc(sizeof(struct slink));
            newhead->id = pnbr->nbr;
            newhead->next = head;
            head = newhead;
        } /* Building the neighbour list */

    if (ineighbours <= 1)
    {
        rAmount = rtnAmountSpecAtPu(pu,SM,ipu,isp);
        oldclump->amount -= rAmount;
        oldclump->occs -= (rAmount > 0);
        newamount = PartialPen4(isp,oldclump->amount,spec,clumptype);
        newoccs = oldclump->occs * (newamount > 0);
        /* remove clumppu */
        if (cppu == oldclump->head)
        {
            oldclump->head = cppu->next;
        }
        else
        {
            for (ppu= oldclump->head;ppu->next != cppu; ppu = ppu->next)
                ; /* find preceding clumppu;*/
            ppu->next = cppu->next;
        }
        free(cppu);
        DebugFree(sizeof(struct sclumppu));
        if (ineighbours < 1)
        {
            if (oldclump == spec[isp].head)
                spec[isp].head = oldclump->next;
            else {
                for (pclump = spec[isp].head;pclump->next != oldclump;pclump = pclump->next)
                ;
                pclump->next = oldclump->next;
            } /* find preceeding clump */
            free(oldclump);
            DebugFree(sizeof(struct sclumps));
            spec[isp].clumps--;
        } /* Removing redundant clump */
        spec[isp].amount += newamount;
        spec[isp].occurrence += newoccs;
        if (head)
        {
            free(head);
            DebugFree(sizeof(struct slink));
        } /* Only need to free head if ineighbours ==1. Then only 1 item in list */
        return;
    } /* I'm not cutting a clump */

    /* Else create new clumps */
    while (head){
        /* take first element as seed for new clump number */
        pclump = AddNewClump(isp,head->id,spec,pu,SM);
        clumpcurr = pclump->head;
        do {
            for (pnbr=connections[clumpcurr->puid].first;pnbr;pnbr=pnbr->next) {
              if (rtnClumpSpecAtPu(pu,SM,pnbr->nbr,isp) == oldclump->clumpid)
              {
                  if (oldclump->head->puid == pnbr->nbr)
                  {
                     ppu = oldclump->head;
                     oldclump->head = ppu->next;
                  } /* cut out old clump of head */
                  else
                    {
                        for (tppu= oldclump->head;tppu->next->puid != pnbr->nbr;tppu= tppu->next)
                        ; /* find preceeding pu in clump */
                        ppu = tppu->next;
                        tppu->next = ppu->next;
                    } /* cut from middle of old clump */
                     ppu->next = clumpcurr->next;
                     clumpcurr->next = ppu;
                     setClumpSpecAtPu(pu,SM,ppu->puid,isp,pclump->clumpid);
                     rAmount = rtnAmountSpecAtPu(pu,SM,ppu->puid,isp);
                     pclump->amount += rAmount;
                     pclump->occs += (rAmount>0);
                     /* Check if it is on neighbours list and if so then remove it from that list*/
                    if (head->id == ppu->puid)
                    {
                        newhead = head;
                        head = newhead->next;
                        free(newhead);
                        DebugFree(sizeof(struct slink));
                    }
                    else {
                        for (newhead= head;newhead->next && newhead->next->id != ppu->puid;newhead= newhead->next)
                            ; /* check if next one on list is same person */
                        if (newhead->next && newhead->next->id == ppu->puid)
                        {
                            thead = newhead->next;
                            newhead->next = thead->next;
                            free(thead);
                            DebugFree(sizeof(struct slink));
                        } /* cut out none head element */
                    }
                } /* This one is worth removing */
            } /* Cycling through all neighbours */
            clumpcurr = clumpcurr->next;
        } while (clumpcurr); /* Continue until you've added every conceivable neighbour */
        spec[isp].amount += PartialPen4(isp,pclump->amount,spec,clumptype);
        spec[isp].occurrence += pclump->occs * (PartialPen4(isp,pclump->amount,spec,clumptype)>0);
        newhead = head;
        head = newhead->next;
        free(newhead);
        DebugFree(sizeof(struct slink));
    } /** Account for every neighbour in my list **/

    /* Every neighbour in local list has been used and every clump formed*/
    /* Remove old clump */
    /* Worry about change in amount and hence score */
    if (oldclump == spec[isp].head)
    {
        spec[isp].head = oldclump->next;
    }
    else {
        for(pclump=spec[isp].head;pclump->next != oldclump;pclump=pclump->next)
        ; /* find neighbouring clump */
        pclump->next = oldclump->next;
    } /* removing old clump */
    ClearClump(isp,oldclump,pu,SM);
    free(oldclump);
    DebugFree(sizeof(struct sclumps));

} /* Remove a Planning Unit ***/

/* * * * * * * * * * * * * * * * * * * * ****/
/* * * *   Main functions * * * * * * * * ***/
/* * * * * * * * * * * * * * * * * * * * ****/

/* * * * * * * * Setting Clumps for Species Aggregation Rule* * * * **/
void SetSpeciesClumps(int puno,int R[],struct sspecies spec[],struct spustuff pu[],
                      struct spu SM[],struct sconnections connections[],int clumptype)
{
     int i, ipu, isp, ism;

     for (ipu=0;ipu<puno;ipu++)
         if (pu[ipu].richness)
            for (i=0;i<pu[ipu].richness;i++)
            {
                ism = pu[ipu].offset + i;
                isp = SM[ism].spindex;
                if (spec[isp].target2)
                {
                   spec[isp].clumps = 0;
                   //if ((R[ipu]!=iAvailableEquivalentZone) && (R[ipu] != 0) && (SM[ism].amount > 0) && (SM[ism].clump  == 0))
                   if ((SM[ism].amount > 0) && (SM[ism].clump  == 0))
                   {
                      AddNewPU(ipu,isp,connections,spec,pu,SM,clumptype);
                   }// Add a New planning unit
                }// For each type 4 species
            }

} /* * * *  Set Species clumps * * * * *****/

/* * * * **** Species Amounts Type 4 * * * * ******/
/** Assumes Set Species Clumps has been called **/
void SpeciesAmounts4(int isp,struct sspecies spec[],int clumptype)
{
     double ftemp;
     struct sclumps *pclump;

     for (pclump = spec[isp].head;pclump;pclump= pclump->next)
     {
         ftemp = PartialPen4(isp,pclump->amount,spec,clumptype);
         spec[isp].amount += ftemp;
         spec[isp].occurrence += pclump->occs*(ftemp>0);
     }

} /*** Species Amounts 4 **/

/*** Remove Clump Check ***/
/** returns 0 if any member of clump is non-removable, Ie status == 2 **/
int RemClumpCheck(struct sclumps *pclump,struct spustuff pu[])
{
    struct sclumppu *pcpu;

    for (pcpu = pclump->head;pcpu;pcpu = pcpu->next)
        if (pu[pcpu->puid].status == 2)
            return(0);

    return(1);
}

/* * * * * Set Penalties for a given Type 4 Species ***/
/* Returns 1 if the species is a 'bad species' and -1 if it is a 'good species' */
/* Also sticks the penalty into spec[isp].penalty */
int CalcPenaltyType4(int isp,int puno, struct spu SM[],struct sconnections connections[],
                     struct sspecies spec[],struct spustuff pu[],int clumptype,int inputR[])
{
    int i,j,ipu,iputotal = 0,iZone;
    int /*ineighbours = 0,*/iclumpno,badspecies = 0;
    int *R;
    double totalamount,dummy = 0;
    int idummy;
    double cost = 0.0, connection = 0.0, rAmount, rZoneConnectionCost;
    struct slink {int id; struct slink *next;} *plist,*plisthead = NULL,*pdiscard;
    struct sneighbour *pnbr;
    struct sclumps *pclump, *pnewclump;
    struct sclumppu *pnewclumppu, *pcpu;


    R = (int *) calloc(puno,sizeof(int)); /* needed for separation */
    for (i=0;i<puno;i++)
        R[i] = inputR[i];
    /*memcpy(R,pustat,sizeof(struct spustuff)*puno);*/

    /*** Step 1. Make a link list of all the possible PUs to be included ****/
    /*** This might change if I change the species v site into link lists ****/
    plisthead = NULL;
    for (i=0;i<puno;i++)
      if (rtnAmountSpecAtPu(pu,SM,i,isp) > 0)
      {
         if (R[i] == 0)
            continue; // not allowed to consider excluded planning unit
         //if (R[i] != iAvailableEquivalentZone)
         {  /* add to clumps and remove from list */
            AddNewPU(i,isp,connections,spec,pu,SM,clumptype);
            continue;
         } /* checking if PU forced into reserve */
         iputotal++;
         plist = (struct slink *) malloc(sizeof(struct slink));
         plist->id = i;
         plist->next = plisthead;  /* Insert on list */
         plisthead = plist;  /* point head to new number */
      } /** Made link list of all sites with this species **/

    /* Check first to see if I've already satisfied targets for this species */
    SpeciesAmounts4(isp,spec,clumptype);
    if (spec[isp].sepnum>0)
       spec[isp].separation = CountSeparation2(isp,0,0,puno,R,pu,SM,spec,0);
    if ((spec[isp].amount >= spec[isp].target) && (spec[isp].occurrence >= spec[isp].targetocc) && (spec[isp].separation >= spec[isp].sepnum))
    {
       spec[isp].amount = 0;
       spec[isp].occurrence = 0;
       spec[isp].separation = 0;
       /** Clean out all the clump numbers for this species.*/
       while (spec[isp].head)
       {
             ClearClump(isp,spec[isp].head,pu,SM);
             pclump = spec[isp].head;
             spec[isp].head = spec[isp].head->next;
             free(pclump);
             DebugFree(sizeof(struct sclumps));
             spec[isp].clumps = 0;
       }  /** Remove each clump ***/
       free(R); /* dummy array for separation */
       DebugFree(puno * sizeof(int));
       return(-1);
    }  /* return when all targets already met. */

    if (iputotal)
       do
       { /*** take all pu's at random until satisfied or I've run out **/
         /* Pluck a PU out at random */
         ipu = RandNum(iputotal);
         plist = plisthead;
         for (;ipu>0;ipu--)
         {
               plist = plist->next;
         }
         iputotal--;

         /** Add this PU to our system **/
         //do
           iZone = RandNum(iZoneCount) + 1;

         //while (iZone != iAvailableEquivalentZone);

         R[plist->id] = iZone;
         AddNewPU(plist->id,isp,connections,spec,pu,SM,clumptype);

         /** Remove the chosen site from my site list **/
         if (plisthead == plist)
         {
            plisthead = plist->next;
         } /* special case for head of list */
         else
         {
             for (pdiscard = plisthead; pdiscard->next != plist; pdiscard = pdiscard->next)
             {
             }; /*** Find link before plist ***/
             pdiscard->next = plist->next;
         } /* remove plist from the list */
         free(plist);
         DebugFree(sizeof(struct slink));

         /*** Check to see if I should continue by calculating current holdings **/
         SpeciesAmounts4(isp,spec,clumptype);
         if (spec[isp].sepnum>0)
              spec[isp].separation = CountSeparation2(isp,0,0,puno,R,pu,SM,spec,0);

       } while ((spec[isp].amount < spec[isp].target ||
                    spec[isp].separation < spec[isp].sepnum ||
                 spec[isp].occurrence < spec[isp].targetocc)
                && iputotal >= 1  );

    if (spec[isp].amount < spec[isp].target || spec[isp].occurrence < spec[isp].targetocc)
    {
       badspecies = 1;
       ShowGenProg("Species %i cannot be fully represented!\n",spec[isp].name);
    } /*** Record the fact that the species is unrepresentable ***/
    if (spec[isp].separation < spec[isp].sepnum && spec[isp].amount >= spec[isp].target && spec[isp].occurrence >= spec[isp].targetocc)
    {
       badspecies = 1;
       ShowGenProg("Species %i can only get %i separate valid clumps where %i are wanted!\n",
                   spec[isp].name,spec[isp].separation,spec[isp].sepnum);
    } /*** Record the fact that the species is unrepresentable ***/


    /* Search through the clumps looking for any which can be removed */
    /* But only do this if occurrence target met. Otherwise every single pu is neccessary*/
    if (spec[isp].occurrence >= spec[isp].targetocc)
      {
       pclump = spec[isp].head;
       while (pclump)
       {
             i = 0; /* if i becomes and stays 1 then this clump is removable */
             if (RemClumpCheck(pclump,pu))
                i = 1;
             if (i)
             {
                if (spec[isp].occurrence - pclump->occs >= spec[isp].targetocc)
                   i = 1;  /* if pclump-amount < target2 is caught in next step */
                else
                    i = 0;
             } /* Check is occurrence decrease ok? */
             if (i)
             {
                if ((spec[isp].amount - pclump->amount >= spec[isp].target) || (pclump->amount < spec[isp].target2))
                   i = 1;
                else
                    i = 0;
             } /* Check is amount decrease OK? */
             if (i && spec[isp].sepnum)
             {
                j = CountSeparation2(isp,0,pclump,puno,R,pu,SM,spec,-1);
                if ((j < spec[isp].separation) && (j < spec[isp].sepnum))
                   i = 0;
                else
                    i = 1;
                if (!spec[isp].target2)
                   i = 0; /* cannot elegantly remove clumps if species is listed as non-clumping */
             }
             if (i)   /* This is a clump which can be safely removed */
             {  /* cut clump if uneccessary or it is too small */
                if (spec[isp].head == pclump)
                {
                   spec[isp].head = pclump->next;
                }
                else
                {
                    for (pnewclump = spec[isp].head;pnewclump->next != pclump;pnewclump = pnewclump->next)
                        ; /** find clump before pclump **/
                    pnewclump->next = pclump->next;
                }
                while (pclump->head)
                {
                      pnewclumppu = pclump->head;
                      pclump->head = pnewclumppu->next;
                      setClumpSpecAtPu(pu,SM,pnewclumppu->puid,isp,0);
                      free(pnewclumppu);
                      DebugFree(sizeof(struct sclumppu));
                }
                totalamount -= pclump->amount;
                /* cut out clump and progress pclump*/
                pnewclump = pclump;
                pclump = pclump->next;
                free(pnewclump);
                DebugFree(sizeof(struct sclumps));
                spec[isp].clumps--;
             } /** removing unneccessary pclump **/
             else
                 pclump = pclump->next;

       }
    } /*** Remove unneccesary clumps and links****/


    /** Test all PU's to see if any one of them are superfluous **/
    /* But only do this if occurrence target met. Otherwise every single pu is neccessary*/
    if (spec[isp].occurrence >= spec[isp].targetocc)
    {
       pclump = spec[isp].head;
       while (pclump)
       {
             pcpu = pclump->head;
             while (pcpu)
             {     /** Test to see if this pcpu is necessary **/
                   i = 0;
                   //if (R[pcpu->puid] != 2)
                   if ((R[ipu] > 0) && (pu[ipu].status < 2) && (pu[ipu].fPULock != 1) && (pu[ipu].fPUZone != 1))
                      i = 1;
                   if (i)
                   {
                      rAmount = rtnAmountSpecAtPu(pu,SM,pcpu->puid,isp);
                      if ((pclump->amount - rAmount > spec[isp].target2) && (spec[isp].amount - rAmount > spec[isp].target))
                         i = 1;
                      else
                          i = 0;
                   }  /* doesn't drop amount below min clump size or target */
                   if (i)
                   {
                      if (spec[isp].occurrence > spec[isp].targetocc)
                         i = 1;
                      else
                          i = 0;
                   } /* Does it drop occurrences too low? */
                   if (i)
                   {
                      pnewclump = (struct sclumps *)malloc(sizeof(struct sclumps));
                      pnewclump->clumpid = pcpu->puid;  /* sclump used to store clumpPU info */
                      pnewclump->amount = 0;
                      pnewclump->next = NULL;
                      j = CountSeparation2(isp,pcpu->puid,pnewclump,puno,R,pu,SM,spec,-1);
                      free(pnewclump);
                      if ((j < spec[isp].separation) && (j < spec[isp].sepnum))
                         i = 0;
                      else
                          i = 1;
                   } /* How does count sep fare? */
                   if (i)
                   {
                      if (ClumpCut(isp,pu,spec,pclump,pcpu,connections,SM,&dummy,&idummy,&j,0,clumptype))
                         i = 0;
                      else
                          i = 1;
                   } /* Does it cut the clump? these are not allowed to remove */
                   /* Theoretically they could possible be removed */
                   if (i)  /* Is this removable? */
                   {  /* remove pcpu */
                      setClumpSpecAtPu(pu,SM,pcpu->puid,isp,0);
                      totalamount -= rAmount;
                      pclump->amount -= rAmount;
                      if (pcpu == pclump->head)
                      {
                         pclump->head = pcpu->next;
                         free(pcpu);
                         DebugFree(sizeof(struct sclumppu));
                         pcpu = pclump->head;
                      } /* removing first clump */
                      else
                      {
                          for (pnewclumppu = pclump->head;pnewclumppu->next != pcpu;pnewclumppu = pnewclumppu->next)
                              ; /* find previous pcpu */
                          pnewclumppu->next = pcpu->next;
                          free(pcpu);
                          DebugFree(sizeof(struct sclumppu));
                          pcpu = pnewclumppu->next;
                      } /* removing pcpu when it is not the head */
                   }  /** remove unneccessary clumppu **/
                   else
                       pcpu = pcpu->next; /* moving pointer when it is not removable */
             } /* Checking each pcpu in clump */
             pclump = pclump->next;
       }
    } /** Cycle over each pclump **/

    while (plisthead)
    {
          plist = plisthead;
          plisthead = plisthead->next;
          free(plist);
          DebugFree(sizeof(struct slink));
    } /* Cleaing link list */


    /*** Now count the cost of this particular reserve ****/
    /*** For each clump figure out connection cost ***/
    pclump = spec[isp].head;
    while (pclump)
    {
          iclumpno = pclump->clumpid;
          pcpu = pclump->head;
          while (pcpu)
          {
                if (pu[pcpu->puid].status != 2)
                {
                   cost += pu[pcpu->puid].cost;
                   connection += connections[pcpu->puid].fixedcost;
                } /* only count fixed costs if PU not forced into reserve */
                if (connections[pcpu->puid].nbrno)
                {
                   pnbr = connections[pcpu->puid].first;
                   while (pnbr)
                   {
                         rZoneConnectionCost = _RelConnectionCost[((R[pcpu->puid]-1) * iZoneCount) + (R[pnbr->nbr] - 1)];

                         if (rtnClumpSpecAtPu(pu,SM,pnbr->nbr,isp) != iclumpno)
                            connection += (pnbr->cost * rZoneConnectionCost);
                         pnbr = pnbr->next;
                   } /** Counting each individual connection **/
                } /** Counting connection strength if neccessary **/
                pcpu = pcpu->next;
          } /** Checking each PU in clump **/
          pclump = pclump->next;
    } /*** Count cost for each clump ***/

    /* Finally. Calculate penalty from all of this.*/
    spec[isp].penalty = cost + connection;

    /* Consider case where targets cannot be met */
    totalamount = 0;
    if (spec[isp].amount < spec[isp].target)
       totalamount = spec[isp].target / spec[isp].amount;
    if (spec[isp].occurrence < spec[isp].targetocc)
       totalamount += (double) spec[isp].targetocc/(double) spec[isp].occurrence;
    if (totalamount)
       spec[isp].penalty *= totalamount;  /* Scale it up */

    if (spec[isp].sepdistance)
       spec[isp].separation = 1;
    spec[isp].amount = 0; /* because my routines add it in */
    spec[isp].occurrence = 0;
    /** Clean out all the clump numbers for this species.*/
    while (spec[isp].head)
    {
          ClearClump(isp,spec[isp].head,pu,SM);
          pclump = spec[isp].head;
          spec[isp].head = spec[isp].head->next;
          free(pclump);
          DebugFree(sizeof(struct sclumps));
          spec[isp].clumps = 0;
    }  /** Remove each clump ***/

    free(R); /* dummy array for separation */
    DebugFree(puno * sizeof(int));
    return(badspecies);

} /*** Calculate Penalty for a Type 4 Species ***/

/**** Partial Penalty for type 4 species ***/
double PartialPen4(int isp, double amount,struct sspecies spec[],int clumptype)
{
       if (amount >= spec[isp].target2)
          return (amount);    /* I'm not a partial penalty */
       else
           switch (clumptype)
           {
                  case 0: return(0.0); /* default step function */
                  case 1: return(amount/ 2.0); /* nicer step function */
                  case 2: if (spec[isp].target2)
                             return (amount/spec[isp].target2 * amount);
                  default: return(0.0);
           }
}  /* Partial Penalty for type 4 species */

/*** Value for Adding a Planning Unit ****/
double ValueAdd(int isp,int ipu,int puno, int R[],struct sconnections connections[],struct spustuff pu[],
                struct spu SM[],struct sspecies spec[],int clumptype)
{
       int iclumpid,iseparation,oldoccs = 0,occs, iClump, i, iArrayIndex;
       struct sneighbour *pnbr;
       struct slink {int clumpid;double amount;
                     int occs; struct slink *next;} *head = NULL,*plink;
       struct sclumps *pclump,*sepclump=NULL,*psclump;
       struct sclumppu *ppu;
       double amount,oldamount = 0.0,shortfall,zshortfall;

       /* Count neighbours */
       if (connections[ipu].nbrno > 0)
       {
          pnbr = connections[ipu].first;
          while (pnbr)
          {
                iClump = rtnClumpSpecAtPu(pu,SM,pnbr->nbr,isp);
                if (iClump)
                {
                   iclumpid = 1;
                   /* Is nbr on my list ?*/
                   for (plink = head;plink;plink=plink->next)
                       if (plink->clumpid == iClump)
                          iclumpid = 0;

                   if (iclumpid)
                   {
                      //ineighbours++;
                      plink = (struct slink *) malloc(sizeof(struct slink));
                      plink->clumpid = iClump;
                      /* find amount for this clump */
                      for (pclump = spec[isp].head;plink->clumpid != pclump->clumpid;pclump = pclump->next)
                          ; /* find the right clump */
                      plink->amount = pclump->amount;
                      plink->occs = pclump->occs;
                      plink->next = head;
                      head = plink;
                      if (spec[isp].sepnum)
                      for (ppu = pclump->head;ppu;ppu=ppu->next)
                      {
                          psclump = (struct sclumps *) malloc(sizeof(struct sclumps));
                          psclump->clumpid = ppu->puid;
                          psclump->next = sepclump;
                          sepclump = psclump;  /* glue to sep list. Still need amount */
                      } /* stick whole clump onto separation clump for later */
                   } /* new neighbour found */
                } /* neighbour of clump */
                pnbr = pnbr->next;
          } /** count all neighbours if they have a clump **/
       }  /* If There are neighbours */

       if (spec[isp].sepnum)
       {
          psclump = (struct sclumps *) malloc(sizeof(struct sclumps));
          psclump->clumpid = ipu;
          psclump->next = sepclump;
          sepclump = psclump;
       } /* Add ipu to my sepclump list */

       /* now I know number and names of neighbouring clumps */
       amount = rtnAmountSpecAtPu(pu,SM,ipu,isp);
       occs = (amount > 0);
       for (plink = head;plink;plink = plink->next)
       {
           amount += plink->amount;
           occs += plink->occs;
           oldamount += PartialPen4(isp,plink->amount,spec,clumptype);
           oldoccs += plink->occs * (PartialPen4(isp,plink->amount,spec,clumptype)>0);
       }

       /* set the sepclump amounts to this new amount */
       if (spec[isp].sepnum)
          for (psclump = sepclump;psclump;psclump = psclump->next)
              psclump->amount = amount;

       amount = PartialPen4(isp,amount,spec,clumptype);
       occs = occs * (amount > 0);

       amount = amount - oldamount; /* amount is change in amount for this species */
       occs = occs - oldoccs;

       if (spec[isp].sepnum)
       {
          iseparation = CountSeparation2(isp,0,sepclump,puno,R,pu,SM,spec,1);  /* imode = 1 doesn't do anything*/
          while (sepclump)
          {
                psclump = sepclump;
                sepclump = sepclump->next;
                free(psclump);
                DebugFree(sizeof(struct sclumps));
          }
       } /* clean up sepcount link list */

       while (head)
       {
             plink = head;
             head = head->next;
             free(plink);
             DebugFree(sizeof(struct slink));
       }  /* Clean up link list */

       /* Return the effective amount for this species */
       /* Old amount + change in amount + separation penalty for changed structure */

       amount = spec[isp].amount + amount;
       shortfall = 0;
       if (spec[isp].target)
          shortfall = amount >= spec[isp].target ? 0 : (spec[isp].target - amount)/spec[isp].target;
       if (spec[isp].targetocc)
       {
          occs = occs + spec[isp].occurrence;
          amount = occs >= spec[isp].targetocc ? 0: ((double)spec[isp].targetocc - (double) occs)/(double)spec[isp].targetocc;
          shortfall += amount;
       }
       /*if (spec[isp].target && spec[isp].targetocc)
          shortfall /= 2;*/
       for (i=0;i<iZoneCount;i++)
       {
           iArrayIndex = (isp * iZoneCount) + i;
           zshortfall = 0;
           if (_ZoneTarget[iArrayIndex].target)
              if (ZoneSpec[iArrayIndex].amount < _ZoneTarget[iArrayIndex].target)
                 zshortfall += (_ZoneTarget[iArrayIndex].target - ZoneSpec[iArrayIndex].amount) / _ZoneTarget[iArrayIndex].target;
           if (_ZoneTarget[iArrayIndex].occurrence)
              if (ZoneSpec[iArrayIndex].occurrence < _ZoneTarget[iArrayIndex].occurrence)
                 zshortfall += (_ZoneTarget[iArrayIndex].occurrence - ZoneSpec[iArrayIndex].occurrence) / _ZoneTarget[iArrayIndex].occurrence;
           /*if (_ZoneTarget[iArrayIndex].target && _ZoneTarget[iArrayIndex].occurrence)
              zshortfall /= 2;*/
           shortfall += zshortfall;
       }
       return(shortfall + SepPenalty2(iseparation,spec[isp].sepnum));
} /*** Value for Adding a Planning Unit ****/


/** Value Remove. The amount of species loss for removing a single pu */
double ValueRem(int ipu,int isp,struct sspecies spec[],struct sconnections connections[],
                struct spustuff pu[],struct spu SM[],int clumptype)
{
       double newamount = 0,amount,shortfall=0,zshortfall;
       struct sclumps *pclump;
       struct sclumppu *ppu;
       int iseparation,newocc = 0,i,iArrayIndex;

       /* locate the clump and clumppu of the target site ipu */
       for (pclump = spec[isp].head; pclump && pclump->clumpid != rtnClumpSpecAtPu(pu,SM,ipu,isp); pclump = pclump->next)
           ; /* locate correct clump list */

       for (ppu = pclump->head;ppu->puid != ipu; ppu = ppu->next)
           ; /* locate the correct pclump pu */

       if (spec[isp].sepnum)
          ClumpCut(isp,pu,spec,pclump,ppu,connections,SM,&newamount,&newocc,&iseparation,1,clumptype);
       else
           ClumpCut(isp,pu,spec,pclump,ppu,connections,SM,&newamount,&newocc,&iseparation,0,clumptype);

       if (spec[isp].target)
       {
          amount = spec[isp].amount + newamount -PartialPen4(isp,pclump->amount,spec,clumptype) ;
          shortfall = amount > spec[isp].target ? 0 : (spec[isp].target - amount)/spec[isp].target;
       }  /* there is an abundance amount */

       if (spec[isp].targetocc)
       {  /* Handle the case where there is a targetocc */
          amount = spec[isp].occurrence +newocc - pclump->occs * (PartialPen4(isp,pclump->amount,spec,clumptype)>0);
          if (amount < spec[isp].targetocc)
             shortfall += ((double) spec[isp].targetocc - amount)/(double) spec[isp].targetocc;
          /*if (spec[isp].target)
             shortfall /= 2;*/
       }
       for (i=0;i<iZoneCount;i++)
       {
           iArrayIndex = (isp * iZoneCount) + i;
           zshortfall = 0;
           if (_ZoneTarget[iArrayIndex].target)
              if (ZoneSpec[iArrayIndex].amount < _ZoneTarget[iArrayIndex].target)
                 zshortfall += (_ZoneTarget[iArrayIndex].target - ZoneSpec[iArrayIndex].amount) / _ZoneTarget[iArrayIndex].target;
           if (_ZoneTarget[iArrayIndex].occurrence)
              if (ZoneSpec[iArrayIndex].occurrence < _ZoneTarget[iArrayIndex].occurrence)
                 zshortfall += (_ZoneTarget[iArrayIndex].occurrence - ZoneSpec[iArrayIndex].occurrence) / _ZoneTarget[iArrayIndex].occurrence;
           /*if (_ZoneTarget[iArrayIndex].target && _ZoneTarget[iArrayIndex].occurrence)
              zshortfall /= 2;*/
           shortfall += zshortfall;
       }

       return(shortfall + SepPenalty2(iseparation,spec[isp].sepnum));
} /** Value for removing a planning unit ****/


/* * * * * * * *   NewPenalty4   * * * * * * * * *****/
/* Calculates the new penalty for adding or removing a PU for species which have
    clumping requirements */


double NewPenalty4(int ipu,int isp,int puno,struct sspecies spec[],struct spustuff pu[],struct spu SM[],
                    int R[],struct sconnections connections[],int imode,int clumptype)
{
       double amount;

       if (imode == 1)
       {
          if (spec[isp].penalty == 0)
             return (0);  // Targets have all already been met
          amount = ValueAdd(isp,ipu,puno,R,connections,pu,SM,spec,clumptype);
       }
       else
       {
           // determine change in this amount
           amount = ValueRem(ipu,isp,spec,connections,pu,SM,clumptype);
       } // removing a planning unit
       return(amount);

}  /*** The new penalty for type 4 species ***/

/* CLUMPING.C END */
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/* DOSOUTPUT.C BEGIN */

/* The following function shows the startup screen information.
   The program title and authors */

void ShowStartupScreen(void)
{
     printf("        %s \n\n   Marine Reserve Design with Zoning and Annealing\n\n",sVersionString);
     printf("   Marxan with Zones coded by Matthew Watts\n");
     printf("   Written by Ian Ball, Hugh Possingham and Matthew Watts\n\n");
     printf("   Based on Marxan coded by Ian Ball, modified by Matthew Watts\n");
     printf("   Written by Ian Ball and Hugh Possingham\n\n");
     printf("%s\n%s\n%s\n\n",sIanBallEmail,sHughPossinghamEmail,sMattWattsEmail);
     printf("   Marxan website\n\n");
     printf("%s\n\n",sMarxanWebSite);

}  /* Show Startup Screen */


/* Show ShutDown Screen displays all the end of program information. It only displays when
    the iVerbosity has been set to 1 or higher */

void ShowShutdownScreen(void)
{
     if (iVerbosity > 0)
     {
        printf("\n");
        ShowTimePassed();
        printf("\n              The End \n");
        if (savelog)
           fprintf(fsavelog,"\n              The End \n");
     }
} /* ShowShutdownScreen */

/* ShowPauseExit delivers a message prior to exiting */

void WriteStopErrorFile(char sMess[])
{
     //
     FILE* fsync;

     fsync = fopen("stop_error.txt","w");
     fprintf(fsync,sMess);
     fclose(fsync);
}

void ShowPauseExit(void)
{
     printf("Press return to exit.\n");
     getchar();
}  /* Show Pause Exit  */

void WriteSlaveSyncFileRun(int iSyncRun)
{
     FILE* fsync;
     char sSyncFileName[80];

     sprintf(sSyncFileName,"sync%i",iSyncRun);

     fsync = fopen(sSyncFileName,"w");
     fprintf(fsync,sSyncFileName);
     fclose(fsync);
}

void WriteSlaveSyncFile(void)
{
     FILE* fsync;

     fsync = fopen("sync","w");
     fprintf(fsync,"sync");
     fclose(fsync);
}

/* SlaveExit does not deliver a message prior to exiting, but creates a file so C-Plan knows marxan has exited */

void SlaveExit(void)
{
     WriteSlaveSyncFile();
}

void ShowPauseProg(void)
{
     printf("Press return to continue.\n");
     getchar();
} /** Pause **/


/* Set Verbosity sets the module variable iVerbosity to decide how to handle different
    user specified verbosity options */

void SetVerbosity(int verb)
{
     iVerbosity = verb;

} /* Set Verbosity */


/* ShowErrorMessage displays an error message. No matter what verbosity these are
    always displayed. The program is terminated following a ShowPauseExit*/

void ShowErrorMessage(char sMess[],...)
{
     extern jmp_buf jmpbuf;
     va_list args;

     va_start(args,sMess);
     vprintf(sMess,args);
     if (savelog)
        vfprintf(fsavelog,sMess,args);
     va_end(args);
     longjmp(jmpbuf,1);
} /* Show Error Message */

/* ShowWarningMessage displays a warning message no matter what verbosity level */

void ShowWarningMessage(char sMess[],...)
{
     va_list args;

     if (iVerbosity > 0)
     {
        va_start(args,sMess);
        vprintf(sMess,args);
        if (savelog)
           vfprintf(fsavelog,sMess,args);
        va_end(args);
     }
} /* Show Warning Message */


/* ShowProg displays fundamental progress information. Basic run summary */

void ShowProg(char sMess[],...)
{
     va_list args;

     if (iVerbosity > 0)
     {
        va_start(args,sMess);
        vprintf(sMess,args);
        if (savelog)
           vfprintf(fsavelog,sMess,args);
        va_end(args);
     }
} /* Show Progress Message */

#ifdef DEBUGTRACEFILE
void StartDebugTraceFile(void)
{
     FILE* fdebugtrace;


     if (iVerbosity > 2)
     {
        fdebugtrace = fopen(sDebugTraceFileName,"w");
        fflush(fdebugtrace);
        fclose(fdebugtrace);
     }
}
void AppendDebugTraceFile(char sMess[],...)
{
     FILE* fdebugtrace;

     if (iVerbosity > 2)
     {
        fdebugtrace = fopen(sDebugTraceFileName,"a");
        fprintf(fdebugtrace,sMess);
        fflush(fdebugtrace);
        fclose(fdebugtrace);
     }
}
#endif

void StartDebugFile(char sFileName[],char sHeader[],struct sfname fnames)
{
     FILE* fdebugtrace;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen(sFileName) + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,sFileName);
     fdebugtrace = fopen(writename,"w");
     free(writename);

     fprintf(fdebugtrace,sHeader);
     fflush(fdebugtrace);
     fclose(fdebugtrace);
}

void AppendDebugFile(char sFileName[],char sLine[],struct sfname fnames)
{
     FILE* fdebugtrace;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen(sFileName) + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,sFileName);
     fdebugtrace = fopen(writename,"a");
     free(writename);

     fprintf(fdebugtrace,sLine);
     fclose(fdebugtrace);
}

/* ShowGenProg displays a general progress message when verbosity > 1 */

void ShowGenProg(char sMess[],...)
{
     va_list args;

     if (iVerbosity > 1)
     {
        va_start(args,sMess);
        vprintf(sMess,args);
        if (savelog)
           vfprintf(fsavelog,sMess,args);
        va_end(args);
     }

}  /* Show General Progress Message */


/* ShowGenProgInfo displays a general progress with information
    message when verbosity > 2 */

void ShowGenProgInfo(char sMess[],...)
{
     va_list args;

     if (iVerbosity > 5)
     {
        va_start(args,sMess);
        vprintf(sMess,args);
        if (savelog)
           vfprintf(fsavelog,sMess,args);
        va_end(args);
     }
} /*Show General Progress Information Message */


/* ShowDetailedProgress shows detailed progress information
    message when verbosity > 3 */

void ShowDetProg(char sMess[],...)
{
     va_list args;

     if (iVerbosity > 5)
     {
        va_start(args,sMess);
        vprintf(sMess,args);
        if (savelog)
           vfprintf(fsavelog,sMess,args);
        va_end(args);
     }
} /* Show Detailed Progess Message */


/* * * *  ShowTimePassed displays the time passed so far * * * * */

void ShowTimePassed(void)
{
     int itemp, iClock;
     double rTemp;
     #ifdef DEBUGSHOWTIMEPASSED
     char debugbuffer[1000];
     #endif

     #ifdef DEBUGSHOWTIMEPASSED
     AppendDebugTraceFile("ShowTimePassed start\n");
     #endif

     iClock = clock();

     #ifdef DEBUGSHOWTIMEPASSED
     sprintf(debugbuffer,"ShowTimePassed iClock %i\n",iClock);
     AppendDebugTraceFile(debugbuffer);
     sprintf(debugbuffer,"ShowTimePassed rClocksPerSec %g\n",rClocksPerSec);
     AppendDebugTraceFile(debugbuffer);
     #endif

     rTemp = iClock/rClocksPerSec;

     #ifdef DEBUGSHOWTIMEPASSED
     sprintf(debugbuffer,"ShowTimePassed rTemp %g\n",rTemp);
     AppendDebugTraceFile(debugbuffer);
     #endif

     itemp = floor(rTemp);

     #ifdef DEBUGSHOWTIMEPASSED
     sprintf(debugbuffer,"ShowTimePassed itemp %i\n",itemp);
     AppendDebugTraceFile(debugbuffer);
     #endif

     printf("Time passed so far is ");
     if (itemp >= 60*60)
        printf(" %i hour%c,%i min%c and %i secs \n",
               itemp/3600,((itemp/3600==1)?' ':'s'),
               (itemp/60)%60,((itemp/60==1)?' ':'s'),itemp%60);
     else
     {
         if (itemp >=60 )
            printf(" %i min%c and %i secs \n",itemp/60,((itemp/60==1)?' ':'s'),itemp%60);
         else
             printf("%i secs \n",itemp);
     }

     if (savelog)
     {
        fprintf(fsavelog,"Time passed so far is ");
        if (itemp >= 60*60)
           fprintf(fsavelog," %i hour%c,%i min%c and %i secs \n",
                   itemp/3600,((itemp/3600==1)?' ':'s'),
                   (itemp/60)%60,((itemp/60==1)?' ':'s'),itemp%60);
        else
        {
            if (itemp >=60 )
               fprintf(fsavelog," %i min%c and %i secs \n",itemp/60,((itemp/60==1)?' ':'s'),itemp%60);
            else
                fprintf(fsavelog,"%i secs \n",itemp);
        }
     }

     #ifdef DEBUGSHOWTIMEPASSED
     AppendDebugTraceFile("ShowTimePassed end\n");
     #endif
} /* Show Time Passed */


/* * * *  Set logged file. Also resets log file ****/

void SetLogFile(int my_savelog, char* my_savelogname)
{
     if (savelog)
     {
        fclose(fsavelog);
        free(savelogname);
     } /* close and delete old savelog info */

     savelog = my_savelog;

     if (savelog)
     {
        savelogname = calloc(strlen(my_savelogname)+1,sizeof(char));
        strcpy(savelogname,my_savelogname);
        /* Try to open file and complain if it don't work */
        if ((fsavelog = fopen(savelogname,"w"))==NULL)
        {
           free(savelogname);
           savelog = 0;
           ShowErrorMessage("Error: Cannot save to log file %s \n",savelogname);
        }  /* open failed */

        fprintf(fsavelog,"        %s \n\n   Marine Reserve Design with Zoning and Annealing\n\n",sVersionString);
        fprintf(fsavelog,"   Marxan with Zones coded by Matthew Watts\n");
        fprintf(fsavelog,"   Written by Ian Ball, Hugh Possingham and Matthew Watts\n\n");
        fprintf(fsavelog,"   Based on Marxan coded by Ian Ball, modified by Matthew Watts\n");
        fprintf(fsavelog,"   Written by Ian Ball and Hugh Possingham\n\n");
        fprintf(fsavelog,"%s\n%s\n%s\n\n",sIanBallEmail,sHughPossinghamEmail,sMattWattsEmail);
        fprintf(fsavelog,"   Marxan website\n\n");
        fprintf(fsavelog,"%s\n\n",sMarxanWebSite);

     } /* save log has just been turned on */
}  /* Set Log File */

/* DOSOUTPUT.C END */
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/* FILEIN.C BEGIN */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/***            General Functions ***/
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

struct snlink *GetVarNamePU(char **varlist,int numvars,struct stringname CostNames[],int iCostCount,char *sVarName,
                            struct snlink *head,char *fname)
                            // allows field names for multiple costs based on costs.dat
{
       int i,foundit = 0;
       struct snlink *temp,*newlink=NULL;

       for (i=0;(i<numvars && foundit==0);i++)
       {
           if (strcmp(varlist[i],sVarName) == 0)
              foundit++;
       }
       for (i=0;(i<iCostCount && foundit==0);i++)
       {
           if (strcmp(CostNames[i].name,sVarName) == 0)
              foundit++;
       }
       //if (!foundit)
       //   ShowErrorMessage("ERROR: variable name %s, not valid. Check data file %s.\n",sVarName,fname);

       if (head)
          for (temp = head;temp;temp = temp->next)
          {
              if (strcmp(temp->name,sVarName) == 0)
                 ShowErrorMessage("ERROR: variable %s has been defined twice in data file %s.\n",sVarName,fname);
          }

       newlink = (struct snlink *) malloc(sizeof(struct snlink));
       newlink->next = NULL;
       newlink->name = (char *) calloc(strlen(sVarName)+1,sizeof(char));
       strcpy(newlink->name,sVarName);
       return(newlink);
} /* Get Var Name */

struct snlink *GetVarName(char **varlist,int numvars,char *sVarName,
                          struct snlink *head,char *fname)
{
       int i,foundit = 0;
       struct snlink *temp,*newlink=NULL;

       for (i=0;(i<numvars && foundit==0);i++)
       {
           if (strcmp(varlist[i],sVarName) == 0)
              foundit++;
       }
       //if (!foundit)
       //   ShowErrorMessage("ERROR: variable name %s, not valid. Check data file %s.\n",sVarName,fname);

       if (head)
          for (temp = head;temp;temp = temp->next)
          {
              if (strcmp(temp->name,sVarName) == 0)
                 ShowErrorMessage("ERROR: variable %s has been defined twice in data file %s.\n",sVarName,fname);
          }

       newlink = (struct snlink *) malloc(sizeof(struct snlink));
       newlink->next = NULL;
       newlink->name = (char *) calloc(strlen(sVarName)+1,sizeof(char));
       strcpy(newlink->name,sVarName);
       return(newlink);
} /* Get Var Name */

int CheckVarName(char **varlist, int numvars, char *sVarName)
{   /* This routine checks if the variable name occurs in the list. It is similar to GetVarName but does not create list */
    int i,foundit = 0;

    for (i=0;i<numvars;++i)
        if (strcmp(varlist[i],sVarName) == 0)
           foundit++;

    return(foundit);
} /* Check Var Name */

/* * * * ***** Read Name List File * * * * * * * * ******/
/****** This file reads in the name list file * * * * ***/
int ReadNameList(int *puno,struct spustuff *pu[],char indir[])
{
    FILE *fp;
    int i = 0,itemp;
    char readname[1000];
    struct slink{int id;struct slink *next;} *head=NULL,*newlink;

    if (indir[0] != '0')
       strcpy(readname,indir);
    strcat(readname,"name.dat");
    fp = fopen(readname,"r");
    if (fp==NULL)
       ShowErrorMessage("file %s not found. Terminating Program\n",readname);

    while (fscanf(fp,"%d",&itemp)==1)
    {
          newlink = (struct slink *)malloc(sizeof(struct slink));
          newlink->id = itemp;
          newlink->next = head;
          head = newlink;
          i++;
    } /* Scanning Through list of file names */
    fclose(fp);

    /* create namelist now */
    *puno = i;
    *pu = (struct spustuff *) calloc(*puno,sizeof(struct spustuff));
    /* put each link into namelist and free it */
    i = 0;
    while (head)
    {
          (* pu)[i].id = head->id;
          (* pu)[i].richness = 0;
          (* pu)[i].offset = 0;
          i++;
          newlink = head;
          head = head->next;
          free(newlink);
    }
    return(i);
}

/* * * * **** read single variable * * * * * * * * * * * * * * * * ******/
/*  This function is a modified form of Drew Tyres rdsvar function      */
/*  It reads a variable of parmtype from infile and checks that the
    variable is not listed more than once in infile
    crit is 0 if failure to find the variable does not result in a termination
    with error message*/

void rdsvar(FILE *infile, char varname[], void *address, int parmtype, int crit, int *present)
     /* reads a variable of parmtype in from infile. Assumes that the next
        line is the one that has the variable in question but will wrap once to
        find the variable */
{
     int foundit, namelen, check1, check2, gotit;
     char buffer[1000] = "\0";    /* for storing the line found in the file */

     namelen = strlen(varname);    /* figure out how long the string is */
     foundit = 0;

     rewind(infile); /* Always search from top of infile */
     fgets(buffer,999,infile); /* read first line. I'm in trouble if file is empty*/
     do
     {   /* loop through file looking for varname */
       check1 = 0;
       check2 = 0;

       while (buffer[check1++] == varname[check2++]);
       if (check1 > (namelen))
       {  /* varname matches upto namelen */
          foundit++;
          switch (parmtype)
          {
                 case REAL :
                    gotit = sscanf(&buffer[check1]," %lf", (double *) address);
                    break;
                 case DOUBLE :
                    gotit = sscanf(&buffer[check1]," %lf", (double *) address);
                    break;
                 case INTEGER :
                    gotit = sscanf(&buffer[check1]," %d", (int *) address);
                    break;
                 case LONGINT :
                    gotit = sscanf(&buffer[check1]," %ld", (long int *) address);
                    break;
                 case STRING :
                    /* Copy buffer[check1] onto address */
                    /* trim leading and trailing blanks*/
                    /* this to allow spaces, which are important for new directory
                        names */
                        check1 += strspn(&buffer[check1]," ,");
                        for (check2 = strlen(&buffer[check1])-1;isspace(buffer[check1+check2]) != 0
                        ;check2--) ; /* Find last non space character */
                        if (strlen(&buffer[check1]) <2)
                           buffer[check1] = '\0';
                        buffer[check1 + check2+1] = '\0';
                        strcpy((char *) address,&buffer[check1]);
                        gotit = 1; /* So that var check works. This needs further consideration*/
                      break;
                 default :
                     ShowErrorMessage("Invalid parameter type request %d: \n",parmtype);

          }    /* end of switch(parmtype) */

          if (!gotit)
          {
             ShowWarningMessage("WARNING: found bad value for variable %s. Value ignored\n",varname);
             foundit--;
          }

       }
       fgets(buffer,999,infile);  /* Read next line. I quit here if need to */
     } while (!(feof(infile))); /* end of do-while */

     if (!foundit)
     {
        if (crit)
           ShowErrorMessage("Unable to find %s in input file.\n",varname);
     }

     if (foundit > 1)
     {
        ShowWarningMessage("WARNING variable: %s appears more than once in the input file. Final value taken\n",varname);
     }

     *present = foundit;

     return;
}   /* rdsvar */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***/
/*        Set Options    */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***/

void SetOptions(double *prop,struct sanneal *anneal,
                int *iseed,
                int *repeats,char savename[],struct sfname *fnames,char filename[],
                int *runopts,double *misslevel,int *heurotype,int *verbose,int *clumptype,
                int *itimptype,
                double *costthresh,double *tpf1,double *tpf2)
{    FILE *fp;
     double version;
     char stemp[1000];
     int present, iFEATNAMEpresent, iSPECNAMEpresent;

     SetVerbosity(1); /* This enables local warning messages */
     /* Setup all of the default parameter variables */
     version = 0.1;
     *prop = 0;
     (*anneal).type = 1;
     (*anneal).iterations = 0;
     (*anneal).Tinit = 1;
     (*anneal).Tcool = 0;
     (*anneal).Titns = 1;
     *iseed = -1;
     *costthresh = 0;
     *tpf1 = 0;
     *tpf2 = 0;
     *repeats = 0;
     (*fnames).saverun = 0;
     (*fnames).savebest = 0;
     (*fnames).savesum = 0;
     (*fnames).savesen = 0;
     (*fnames).savespecies = 0;
     (*fnames).savesumsoln = 0;
     (*fnames).savepenalty = 0;
     (*fnames).savetotalareas = 0;
     (*fnames).savesolutionsmatrix = 0;
     (*fnames).solutionsmatrixheaders = 1;
     (*fnames).saveannealingtrace = 0;
     (*fnames).annealingtracerows = 0;
     (*fnames).suppressannealzones = 0;
     (*fnames).itimptracerows = 0;
     (*fnames).saveitimptrace = 0;
     (*fnames).savespeciesdata = 0;
     (*fnames).savezoneconnectivitysum = 0;
     (*fnames).savelog = 0;
     strcpy(savename,"temp");
     *misslevel = 1;
     *heurotype = 1;
     *clumptype = 0;
     *verbose = 1;

     /* Open file and then feed in each variable type */
     fp = fopen(filename,"r");
     if (fp==NULL)
        ShowErrorMessage("input file %s not found\nAborting Program.\n\n",filename);

     rdsvar(fp,"VERSION",&version,DOUBLE,0,&present);
     rdsvar(fp,"PROP",prop,DOUBLE,0,&present);
     rdsvar(fp,"RANDSEED",iseed,INTEGER,0,&present);

     /* Annealing Controls */
     rdsvar(fp,"NUMITNS",&(*anneal).iterations,INTEGER,0,&present);
     rdsvar(fp,"STARTTEMP",&(*anneal).Tinit,DOUBLE,0,&present);
     rdsvar(fp,"COOLFAC",&(*anneal).Tcool,DOUBLE,0,&present);
     rdsvar(fp,"NUMTEMP",&(*anneal).Titns,INTEGER,0,&present);

     (*anneal).type = 1;
     if ((*anneal).iterations < 1 )
        (*anneal).type = 0;
     if ((*anneal).Tinit < 0)
        (*anneal).type = (int) (-(*anneal).Tinit) + 1;  /* type is negative of Tinit */
     fscanf(fp,"%i",iseed); /* The random seed. -1 to set by clock */

     /* Various controls */
     rdsvar(fp,"NUMREPS",repeats,INTEGER,0,&present);
     rdsvar(fp,"COSTTHRESH",costthresh,DOUBLE,0,&present);
     rdsvar(fp,"THRESHPEN1",tpf1,DOUBLE,0,&present);
     rdsvar(fp,"THRESHPEN2",tpf2,DOUBLE,0,&present);

     /* SaveFiles */
     rdsvar(fp,"SCENNAME",savename,STRING,0,&present);
     /* SaveFiles New Method */
     rdsvar(fp,"SAVERUN",&(*fnames).saverun,INTEGER,0,&present);
     rdsvar(fp,"SAVEBEST",&(*fnames).savebest,INTEGER,0,&present);
     rdsvar(fp,"SAVESUMMARY",&(*fnames).savesum,INTEGER,0,&present);
     rdsvar(fp,"SAVESCEN",&(*fnames).savesen,INTEGER,0,&present);
     rdsvar(fp,"SAVETARGMET",&(*fnames).savespecies,INTEGER,0,&present);
     rdsvar(fp,"SAVESUMSOLN",&(*fnames).savesumsoln,INTEGER,0,&present);
     rdsvar(fp,"SAVESPECIESDATA",&(*fnames).savespeciesdata,INTEGER,0,&present);
     rdsvar(fp,"SAVEPENALTY",&(*fnames).savepenalty,INTEGER,0,&present);
     rdsvar(fp,"SAVETOTALAREAS",&(*fnames).savetotalareas,INTEGER,0,&present);
     rdsvar(fp,"SAVESOLUTIONSMATRIX",&(*fnames).savesolutionsmatrix,INTEGER,0,&present);
     rdsvar(fp,"SOLUTIONSMATRIXHEADERS",&(*fnames).solutionsmatrixheaders,INTEGER,0,&present);
     rdsvar(fp,"SAVELOG",&(*fnames).savelog,INTEGER,0,&present);
     rdsvar(fp,"SAVESNAPSTEPS",&(*fnames).savesnapsteps,INTEGER,0,&present);
     rdsvar(fp,"SAVESNAPCHANGES",&(*fnames).savesnapchanges,INTEGER,0,&present);
     rdsvar(fp,"SAVESNAPFREQUENCY",&(*fnames).savesnapfrequency,INTEGER,0,&present);
     rdsvar(fp,"SAVEANNEALINGTRACE",&(*fnames).saveannealingtrace,INTEGER,0,&present);
     rdsvar(fp,"ANNEALINGTRACEROWS",&(*fnames).annealingtracerows,INTEGER,0,&present);
     rdsvar(fp,"SUPPRESSANNEALZONES",&(*fnames).suppressannealzones,INTEGER,0,&present);
     rdsvar(fp,"SAVEITIMPTRACE",&(*fnames).saveitimptrace,INTEGER,0,&present);
     rdsvar(fp,"SAVEZONECONNECTIVITYSUM",&(*fnames).savezoneconnectivitysum,INTEGER,0,&present);
     rdsvar(fp,"ITIMPTRACEROWS",&(*fnames).itimptracerows,INTEGER,0,&present);
     if (!(*fnames).savesnapfrequency)
        (*fnames).savesnapfrequency = 1;

     /* Filenames */
     rdsvar(fp,"INPUTDIR",stemp,STRING,1,&present);
     if (stemp[strlen(stemp)-1] != '/' && stemp[strlen(stemp)-1] != '\\')
        strcat(stemp,"/");
     (*fnames).inputdir = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).inputdir,stemp);

     rdsvar(fp,"OUTPUTDIR",stemp,STRING,1,&present);
     if (stemp[strlen(stemp)-1] != '/' && stemp[strlen(stemp)-1] != '\\')
        strcat(stemp,"/");
     (*fnames).outputdir = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).outputdir,stemp);

     strcpy(stemp,"PU.dat");
     rdsvar(fp,"PUNAME",stemp,STRING,1,&present);
     (*fnames).puname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).puname,stemp);

     strcpy(stemp,"spec.dat");
     rdsvar(fp,"SPECNAME",stemp,STRING,0,&iSPECNAMEpresent);
     if (iSPECNAMEpresent == 0)
     {
        rdsvar(fp,"FEATNAME",stemp,STRING,0,&iFEATNAMEpresent);
        //if (iFEATNAMEpresent == 0)
        //   ShowErrorMessage("Unable to find SPECNAME or FEATNAME in input file.\n");
     }
     (*fnames).specname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).specname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"PENALTYNAME",stemp,STRING,0,&present);
     (*fnames).penaltyname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).penaltyname,stemp);

     strcpy(stemp,"puvspr2.dat");
     rdsvar(fp,"PUVSPRNAME",stemp,STRING,1,&present);
     (*fnames).puvsprname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).puvsprname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"MATRIXSPORDERNAME",stemp,STRING,0,&present);
     (*fnames).matrixspordername = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).matrixspordername,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"BOUNDNAME",stemp,STRING,0,&present);
     if (present == 0)
        rdsvar(fp,"CONNECTIONNAME",stemp,STRING,0,&present);
     (*fnames).connectionname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).connectionname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"BLOCKDEFNAME",stemp,STRING,0,&present);
     (*fnames).blockdefname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).blockdefname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"ZONESNAME",stemp,STRING,0,&present);
     (*fnames).zonesname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).zonesname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"COSTSNAME",stemp,STRING,0,&present);
     (*fnames).costsname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).costsname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"ZONECONTRIBNAME",stemp,STRING,0,&present);
     (*fnames).zonecontribname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).zonecontribname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"ZONECONTRIB2NAME",stemp,STRING,0,&present);
     (*fnames).zonecontrib2name = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).zonecontrib2name,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"ZONECONTRIB3NAME",stemp,STRING,0,&present);
     (*fnames).zonecontrib3name = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).zonecontrib3name,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"ZONETARGETNAME",stemp,STRING,0,&present);
     (*fnames).zonetargetname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).zonetargetname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"ZONETARGET2NAME",stemp,STRING,0,&present);
     (*fnames).zonetarget2name = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).zonetarget2name,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"ZONECOSTNAME",stemp,STRING,0,&present);
     (*fnames).zonecostname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).zonecostname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"PULOCKNAME",stemp,STRING,0,&present);
     (*fnames).pulockname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).pulockname,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"PUZONENAME",stemp,STRING,0,&present);
     (*fnames).puzonename = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).puzonename,stemp);

     strcpy(stemp,"NULL");
     rdsvar(fp,"ZONEBOUNDCOSTNAME",stemp,STRING,0,&present);
     if (present == 0)
        rdsvar(fp,"ZONECONNECTIONCOSTNAME",stemp,STRING,0,&present);
     (*fnames).relconnectioncostname = (char *) calloc(strlen(stemp)+1,sizeof(char));
     strcpy((*fnames).relconnectioncostname,stemp);

     /* various other controls */
     rdsvar(fp,"RUNMODE",runopts,INTEGER,1,&present);
     rdsvar(fp,"MISSLEVEL",misslevel,DOUBLE,0,&present);
     rdsvar(fp,"HEURTYPE",heurotype,INTEGER,0,&present);
     rdsvar(fp,"CLUMPTYPE",clumptype,INTEGER,0,&present);
     rdsvar(fp,"ITIMPTYPE",itimptype,INTEGER,0,&present);
     rdsvar(fp,"VERBOSITY",verbose,INTEGER,0,&present);
     if ((*fnames).outputdir[0] != '0')
     {
        strcpy(stemp,(*fnames).outputdir);
        strcat(stemp,savename);
        strcpy(savename,stemp);
     }
     fclose(fp);
}  /***** Set Options 2* * * */

/* * * * ** Read Planning Unit Costs * * * * ******/
int ReadPUCosts(int puno,struct spustuff pu[],struct binsearch PULookup[],int verbose,char indir[])
{
    FILE *fp;
    int i,n;
    double ftemp;
    char readname[1000];

    if (indir[0] != '0')
       strcpy(readname,indir);
    strcat(readname,"cost.dat");

    fp = fopen(readname,"r");
    if (fp==NULL)
    {
       if (verbose > 1)
          ShowWarningMessage("File %s not found. All P.U.s set to cost of 1\n",readname);
       for (i=0;i<puno;i++)
           pu[i].cost = 1;
       return(0);
    } /* no PUcost file */

    /** Clear the cost structure **/
    i = 0;
    while (fscanf(fp,"%d %lf",&n,&ftemp)==2)
    {
          n = FastPUIDtoPUINDEX(puno,n,PULookup);
          if (n<0 || n>=puno)
             ShowErrorMessage("Invalid planning unit number %d \n",n);
          pu[n].cost += ftemp;
          if (ftemp == 0)
             pu[n].cost = delta; /* Don't like zero cost. This is temporary line */
          i++;
    } /** Found another valid looking line **/
    fclose(fp);
    return(i);
}  /*** Read PU Costs ****/

/****** Read Species Information Data  ****/
int ReadSpeciesData(int *spno,struct sspecies *spec[],int *aggexist, int *sepexist,char indir[])
{
    FILE *fp;
    int i,n=0;
    struct slink{struct sspecies stemp;struct slink *next;} *head = NULL,*newlink;
    struct sspecies spectemp;
    char readname[1000];
    char speciesname[1000];

    if (indir[0] != '0')
       strcpy(readname,indir);
    strcat(readname,"species.dat");

    fp = fopen(readname,"r");
    if (fp==NULL)
        ShowErrorMessage("Species file %s has not been found.\nAborting Program.",readname);

    /** Clear important species stats **/

    while (fscanf(fp,"%d %d %lf %lf %lf %lf %s",&spectemp.name,&i,&spectemp.target,&spectemp.spf,&spectemp.target2,&spectemp.sepdistance,speciesname)==7)
    {
          newlink = (struct slink *) malloc(sizeof(struct slink));
          newlink->stemp.name = spectemp.name;
          newlink->stemp.target = spectemp.target;
          newlink->stemp.spf = spectemp.spf;
          newlink->stemp.target2 = spectemp.target2;
          newlink->stemp.sepdistance = spectemp.sepdistance;
          if (spectemp.target2)
             *aggexist = 1;
          if (spectemp.sepdistance)
          {
             *sepexist = 1;
             newlink->stemp.separation = 1;
          }
          newlink->stemp.sname = (char *) calloc(strlen(speciesname)+1,sizeof(char));
          strcpy(newlink->stemp.sname,speciesname);
          n++;
          newlink->next = head;
          head = newlink;
    }
    fclose(fp);

    /* Now do as Name.dat in forming species array */
    *spno = n;
    *spec = (struct sspecies *) calloc(*spno,sizeof(struct sspecies));
    /* put each link into namelist and free it */
    n = 0;
    while (head)
    {
          (* spec)[n].name = head->stemp.name;
          (* spec)[n].target = head->stemp.target;
          (* spec)[n].spf = head->stemp.spf;
          (* spec)[n].target2 = head->stemp.target2;
          (* spec)[n].sepdistance = head->stemp.sepdistance;
          (* spec)[n].separation = head->stemp.separation;
          (* spec)[n].sname = (char *) calloc(strlen(head->stemp.sname)+1,sizeof(char));
          (* spec)[n].richness = 0;
          strcpy((* spec)[n].sname,head->stemp.sname);
          (* spec)[n].targetocc = 0;
          n++;
          newlink = head;
          head = head->next;
          free(newlink->stemp.sname);
          free(newlink);
    }

    return(n);
}  /*** Read Species Information Data  ****/


/***** Planning Unit Information File * * * * ******/
/** Note status = 0     Not in Reserve
        Status = 1        In Reserve
        Status = 2        In Reserve, non-removable
        Status = 3        Not in Reserve, can not be added **/
int ReadPUFile(int puno,struct spustuff pu[],struct binsearch PULookup[],int verbose,char indir[])
{
    FILE *fp;
    int i=0,n,ireserved =0,iproscribed = 0,iinit= 0,idup=0;
    int itemp;
    char readname[1000];

    if (indir[0] != '0')
       strcpy(readname,indir);
    strcat(readname,"pustat.dat");

    fp = fopen(readname,"r");
    if (fp==NULL)
    {
       ShowGenProg("No PU Status file \n");
       return(0);
    }

    while (fscanf(fp,"%d %d",&n,&itemp)==2)
    {
          i++;
          n = FastPUIDtoPUINDEX(puno,n,PULookup);
          if (n<0 || n>=puno)
             ShowErrorMessage("planning unit id out of bounds %d \n",n);
          if (pu[n].status)
            idup++;
          pu[n].status = itemp;
          if (itemp == 1)
             iinit++;
          if (itemp == 2)
             ireserved++;
          if (itemp == 3)
             iproscribed++;
    }
    fclose(fp);

    if (verbose > 1)
    {
       if (iinit || ireserved || iproscribed)
          ShowGenProg("Reserve Status:");
       if (iinit)
          ShowGenProg(" initial reserve %i.",iinit);
       if (ireserved)
          ShowGenProg(" iremovable  %i.",ireserved);
       if (iproscribed)
          ShowGenProg(" Not available %i.",iproscribed);
       if (idup)
          ShowGenProg("There were %i planning units duplicated",idup);
       ShowGenProg("\n");
    }
    return(i);
} /* Planning Unit Information File. Reserve Status of each planning unit. */

int ReadPUXYfile(int puno,struct spustuff pu[],struct binsearch PULookup[],char indir[])
{
    FILE *fp;
    int i = 0,n;
    double x,y;
    char readname[1000];

    if (indir[0] != '0')
       strcpy(readname,indir);
    strcat(readname,"puxy.dat");

    fp = fopen(readname,"r");
    if (fp==NULL)
       ShowErrorMessage("PU x-y data file %s not available but required.",readname);


    while (fscanf(fp,"%i%lf%lf",&n,&x,&y)==3)
    {
          n = FastPUIDtoPUINDEX(puno,n,PULookup);
          if (n<0 || n >= puno)
             ShowErrorMessage("planning unit id out of bounds %d \n",n);
          pu[i].xloc = x;
          pu[i].yloc = y;
          i++;
    }  /* Found another valid looking line */

    return(i);
} /* Read Planning Unit x-y data file */

/* Read Planning Unit Data */
/* This file reads in the data relating to each planning unit. Namely, the ID, the cost,
    The status, x and y coordinates, where appropriate. */

int ReadPUData(int *puno,struct spustuff *pu[],int iCostCount,struct stringname CostNames[],struct sfname fnames)
{
    FILE *fp;

    struct pustruct{int id;double cost; int status; double xloc; double yloc;};
    char *readname;
    char sLine[1000];
    char *varlist[5] = {"id","cost","status","xloc","yloc"};
    int numvars = 5,i=0;
    char *sVarName,*sVarVal;
    struct snlink *head = NULL, *temp = NULL;
    struct spustuff putemp;
    struct spulink{struct spustuff stemp;struct spulink *next;} *spuhead = NULL, *newspulink;

    readname = (char *) calloc(strlen(fnames.puname) + strlen(fnames.inputdir)+2, sizeof(char));

    #ifdef MEMDEBUG
    iMemoryUsed += (strlen(fnames.puname) + strlen(fnames.inputdir)+2) * sizeof(char);
    ShowGenProg("memory used %i\n",iMemoryUsed);
    #endif

    strcpy(readname,fnames.inputdir);
    strcat(readname,fnames.puname);
    fp = fopen(readname,"r");
    if (fp==NULL)
       ShowErrorMessage("Planning Unit file %s has not been found.\nAborting Program.",readname);
    free(readname);

    /* Scan header */
    fgets(sLine,999,fp);

    sVarName = strtok(sLine," ,;:^*\"/|\t\'\\\n");
    head = GetVarNamePU(varlist,numvars,CostNames,iCostCount,sVarName,head,fnames.puname);

    //ivars = 1;
    temp = head;
    while ((sVarName = strtok(NULL," ,;:^*\"/|\t\'\\\n")) != NULL)
    {
          temp->next = GetVarNamePU(varlist,numvars,CostNames,iCostCount,sVarName,head,fnames.puname);
          temp = temp->next;
    }  /* tokking out all the variable names from the header line. There are numVars of them*/

    /* While there are still lines left feed information into temporary link list */
    while (fgets(sLine,999,fp))
    {
        i++;
        putemp.id = -1; /* Set defaults for any missing values */
        putemp.cost = 1;
        putemp.status = 0;
        putemp.xloc = -1;
        putemp.yloc = -1;
        for (temp = head;temp;temp = temp->next)
        {
            if (temp == head)
               sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
            else
                sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
            if (strcmp("id",temp->name)==0)
            {
               sscanf(sVarVal,"%d",&putemp.id);
            }
            else if (strcmp("status",temp->name)==0)
            {
                sscanf(sVarVal,"%d",&putemp.status);
            }
            else if (strcmp("xloc",temp->name)==0)
            {
                sscanf(sVarVal,"%lf",&putemp.xloc);
            }
            else if (strcmp("yloc",temp->name)==0)
            {
                sscanf(sVarVal,"%lf",&putemp.yloc);
            }
            else if (strcmp("cost",temp->name)==0)
            {
                sscanf(sVarVal,"%lf",&putemp.cost);
            }
        } /* looking for ivar different input variables */

        if (putemp.id == -1)
           ShowErrorMessage("ERROR: Missing planning unit id for line %d. \n",i);
        /* Stick everything from putemp into link list */
        newspulink = (struct spulink *) malloc(sizeof(struct spulink));
        newspulink->stemp.id = putemp.id;
        newspulink->stemp.status = putemp.status;
        newspulink->stemp.cost = putemp.cost;
        newspulink->stemp.xloc = putemp.xloc;
        newspulink->stemp.yloc = putemp.yloc;
        newspulink->next = spuhead;
        spuhead = newspulink;

    } /* while still lines in data file */

    fclose(fp);

    /* Create array to store the information */
    *puno = i;
    *pu = (struct spustuff *) calloc(*puno,sizeof(struct spustuff));

    #ifdef MEMDEBUG
    iMemoryUsed += (*puno) * sizeof(struct spustuff);
    ShowGenProg("memory used %i\n",iMemoryUsed);
    #endif

    for (i=0;i<*puno;i++)
    {
        (* pu)[i].id = spuhead->stemp.id;
        (* pu)[i].cost = spuhead->stemp.cost;
        (* pu)[i].status = spuhead->stemp.status;
        (* pu)[i].xloc = spuhead->stemp.xloc;
        (* pu)[i].yloc = spuhead->stemp.yloc;
        (* pu)[i].richness = 0;
        (* pu)[i].offset = 0;
        (* pu)[i].fPULock = 0;
        (* pu)[i].iPULock = 0;
        (* pu)[i].fPUZone = 0;
        (* pu)[i].iPUZone = 0;
        (* pu)[i].iPUZones = 0;
        (* pu)[i].iPreviousStatus = 0;
        newspulink = spuhead;
        spuhead = spuhead->next;
        free(newspulink);
    }
    return(i);
} /* readpudata */

/****** Read Species Information Data  ****/
int ReadSpeciesData2(int *spno,struct sspecies *spec[],struct sfname fnames)
{
    FILE *fp;
    int n=0;
    struct snlink *snhead= NULL,*temp;
    struct slink{struct sspecies stemp;struct slink *next;} *head = NULL,*newlink;
    struct sspecies spectemp;
    char *readname;
    char speciesname[255];
    char sLine[1000];
    char *varlist[10] = {"id","type","target","spf","target2","sepdistance","sepnum","name","targetocc","prop"};
    int numvars = 10,namespecial = 0;//,ivars
    char *sVarName,*sVarVal;

    #ifdef DEBUGTRACEFILE
    //DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,66);
    #endif

    readname = (char *) calloc(strlen(fnames.specname) + strlen(fnames.inputdir)+2, sizeof(char));
    strcpy(readname,fnames.inputdir);
    strcat(readname,fnames.specname);
    fp = fopen(readname,"r");
    if (fp==NULL)
       ShowErrorMessage("Species file %s has not been found.\nAborting Program.",readname);
    free(readname);

    /* Scan header */
    fgets(sLine,999,fp);

    sVarName = strtok(sLine," ,;:^*\"/|\t\'\\\n");
    snhead = GetVarName(varlist,numvars,sVarName,snhead,fnames.specname);
    //ivars = 1;
    temp = snhead;
    while ((sVarName = strtok(NULL," ,;:^*\"/|\t\'\\\n")) != NULL)
    {
          temp->next = GetVarName(varlist,numvars,sVarName,snhead,fnames.specname);
          temp = temp->next;
    }  /* tokking out all the variable names from the header line. There are numVars of them*/

    /* While there are still lines left feed information into temporary link list */
    while (fgets(sLine,999,fp))
    {
        n++;
        /** Clear important species stats **/
        spectemp.name = -1;
        spectemp.target = -1;
        spectemp.type = -1;
        spectemp.spf = -1;
        spectemp.target2 = -1;
        spectemp.targetocc = -1;
        spectemp.sepdistance = -1;
        spectemp.sepnum = -1;
        spectemp.prop = -1;
        speciesname[0] = '\0';
        for (temp = snhead;temp;temp = temp->next)
         {  /* Tok out the next Variable */

            if (namespecial)
            {  /* used for special name handling function */
                namespecial = 0;
            }
            else {
                if (temp == snhead)
                {
                       sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
                }
                else
                    sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
                }
            if (strcmp("id",temp->name)==0)
            {
                sscanf(sVarVal,"%d",&spectemp.name);
            }
            else if (strcmp("name",temp->name)==0)
            {
                /* Cpy first part of name into this */
                strcpy(speciesname,sVarVal);
                /* get next part of name */
                do
                {
                    sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
                    if (!sVarVal)
                       namespecial = 2;
                    else
                    {
                        if(isalpha(sVarVal[0]))
                        {
                            strcat(speciesname," ");
                            strcat(speciesname,sVarVal);
                        }
                        else
                            namespecial = 1;
                    }
                } while (!namespecial);
                if (namespecial == 2)
                   namespecial = 0; /* Handles end of line case */
                /* namespecial == 1 means not at end of line and next variable should be processed*/
            } /* Special name handling routine */
            else if (strcmp("type",temp->name)==0)
            {
                sscanf(sVarVal,"%d",&spectemp.type);
            }
            else if (strcmp("target",temp->name)==0)
            {
                sscanf(sVarVal,"%lf",&spectemp.target);
            }
            else if (strcmp("prop",temp->name)==0)
            {
                sscanf(sVarVal,"%lf",&spectemp.prop);
                if (spectemp.prop > 0)
                   fSpecPROPLoaded = 1;
            }
            else if (strcmp("spf",temp->name)==0)
            {
                sscanf(sVarVal,"%lf",&spectemp.spf);
            }
            else if (strcmp("sepdistance",temp->name)==0)
            {
                sscanf(sVarVal,"%lf",&spectemp.sepdistance);
            }
            else if (strcmp("sepnum",temp->name)==0)
            {
                sscanf(sVarVal,"%d",&spectemp.sepnum);
            }
            else if (strcmp("target2",temp->name)==0)
            {
                sscanf(sVarVal,"%lf",&spectemp.target2);
            }
            else if (strcmp("targetocc",temp->name)==0)
            {
                sscanf(sVarVal,"%d",&spectemp.targetocc);
            }
            else
            {
                //ShowWarningMessage("Cannot find |%s| \n",temp->name);
                //ShowErrorMessage("Serious error in species data reading function.\n");
            }

        } /* looking for ivar different input variables */
        newlink = (struct slink *) malloc(sizeof(struct slink));
        newlink->stemp.name = spectemp.name;
        newlink->stemp.target = spectemp.target;
        newlink->stemp.prop = spectemp.prop;
        newlink->stemp.spf = spectemp.spf;
        newlink->stemp.type = spectemp.type;
        newlink->stemp.targetocc = spectemp.targetocc;
        newlink->stemp.target2 = spectemp.target2;
        newlink->stemp.sepdistance = spectemp.sepdistance;
        newlink->stemp.sepnum = spectemp.sepnum;
        newlink->stemp.sname = (char *) calloc(strlen(speciesname)+1,sizeof(char));
        strcpy(newlink->stemp.sname,speciesname);
        newlink->next = head;
        head = newlink;
    } /* Scanning through each line of file */

    fclose(fp);

    /* Now do as Name.dat in forming species array */
    *spno = n;
    *spec = (struct sspecies *) calloc(*spno,sizeof(struct sspecies));
    /* put each link into namelist and free it */
    n = 0;
    while (head) {
        (* spec)[n].name = head->stemp.name;
        (* spec)[n].type = head->stemp.type;
        (* spec)[n].target = head->stemp.target;
        (* spec)[n].prop = head->stemp.prop;
        (* spec)[n].spf = head->stemp.spf;
        (* spec)[n].target2 = head->stemp.target2;
        (* spec)[n].targetocc = head->stemp.targetocc;
        (* spec)[n].sepdistance = head->stemp.sepdistance;
        (* spec)[n].sepnum = head->stemp.sepnum;
        (* spec)[n].richness = 0;
        (* spec)[n].sname = (char *) calloc(strlen(head->stemp.sname)+1,sizeof(char));
        strcpy((* spec)[n].sname,head->stemp.sname);
        n++;
        newlink = head;
        head = head->next;
        free(newlink->stemp.sname);
        free(newlink);
    }

    #ifdef DEBUGTRACEFILE
    //DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,77);
    #endif

    return(n);
}  /*** Read Species Information Data  ****/


/* Read General Species Data */
/* This function reads in a fixed file named file the general species info. It requires that
    species are typed and changes the value of various characteristics of species
    of that type. This is done in a separate function */

int ReadGenSpeciesData(int *gspno,struct sgenspec *gspec[],struct sfname fnames)
{
    FILE *fp;
    char *readname;
    char sLine[1000];
    char *varlist[8] = {"type","target","target2","targetocc","sepnum","sepdistance","prop","spf"};
    int numvars = 8,i=0;
    char *sVarName,*sVarVal;
    struct snlink *head = NULL, *temp = NULL;
    struct sgenspec gstemp;
    struct sgslink{struct sgenspec stemp;struct sgslink *next;} *gshead = NULL, *newgslink;


    /* Find and Open File */
    readname = (char *) calloc(strlen(fnames.blockdefname) + strlen(fnames.inputdir)+2, sizeof(char));
    strcpy(readname,fnames.inputdir);
    strcat(readname,fnames.blockdefname);
    fp = fopen(readname,"r");
    if (fp==NULL)
    {
       ShowWarningMessage("Warning: Block Definition File %s not found.\n",fnames.blockdefname);
       free(readname);
       return(0);
    }
    free(readname);

    /* Scan header */
    fgets(sLine,999,fp);
    sVarName = strtok(sLine," ,;:^*\"/|\t\'\\\n");
    head = GetVarName(varlist,numvars,sVarName,head,fnames.blockdefname);
    //ivars = 1;
    temp = head;
    while ((sVarName = strtok(NULL," ,;:^*\"/|\t\'\\\n")) != NULL)
    {
          temp->next = GetVarName(varlist,numvars,sVarName,head,fnames.blockdefname);
          temp = temp->next;
    }  /* tokking out all the variable names from the header line. There are numVars of them*/
    /* While there are still lines left feed information into temporary link list */
    while (fgets(sLine,999,fp))
    {
          i++;
          gstemp.type = -1; /* Set defaults for any missing values */
          gstemp.targetocc = -1;
          gstemp.target = -1;
          gstemp.target2 = -1;
          gstemp.sepnum = -1;
          gstemp.sepdistance = -1;
          gstemp.prop = -1;
          for (temp = head;temp->next;temp = temp->next)
          {
              if (temp == head)
                 sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
              else
                  sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");

              if (strcmp("type",temp->name)==0)
              {
                 sscanf(sVarVal,"%d",&gstemp.type);
              }
              else if (strcmp("targetocc",temp->name)==0)
              {
                  sscanf(sVarVal,"%d",&gstemp.targetocc);
              }
              else if (strcmp("target",temp->name)==0)
              {
                  sscanf(sVarVal,"%lf",&gstemp.target);
              }
              else if (strcmp("target2",temp->name)==0)
              {
                   sscanf(sVarVal,"%lf",&gstemp.target2);
              }
              else if (strcmp("sepnum",temp->name)==0)
              {
                   sscanf(sVarVal,"%d",&gstemp.sepnum);
              }
              else if (strcmp("sepdistance",temp->name)==0)
              {
                   sscanf(sVarVal,"%lf",&gstemp.sepdistance);
              }
              else if (strcmp("prop",temp->name)==0)
              {
                   sscanf(sVarVal,"%lf",&gstemp.prop);
              }
              else if (strcmp("spf",temp->name)==0)
              {
                   sscanf(sVarVal,"%lf",&gstemp.spf);
              }
              else
              {
                  ShowWarningMessage("Cannot find |%s| \n",temp->name);
                  ShowErrorMessage("Serious error in GenSpecies data reading function.\n");
              }

          } /* looking for ivar different input variables */

          if (gstemp.type== -1)
             ShowErrorMessage("ERROR: Missing Gen Species type for line %d. \n",i);
          /* Stick everything from gstemp into link list */
          newgslink = (struct sgslink *) malloc(sizeof(struct sgslink));
          newgslink->stemp.type = gstemp.type;
          newgslink->stemp.targetocc = gstemp.targetocc;
          newgslink->stemp.target = gstemp.target;
          newgslink->stemp.target2 = gstemp.target2;
          newgslink->stemp.sepnum = gstemp.sepnum;
          newgslink->stemp.sepdistance = gstemp.sepdistance;
          newgslink->stemp.prop = gstemp.prop;
          newgslink->next = gshead;
          gshead = newgslink;
    } /* while still lines in data file */

    fclose(fp);

    /* Now do as Name.dat in forming species array */
    *gspno = i;
    *gspec = (struct sgenspec *) calloc(*gspno,sizeof(struct sgenspec));
    /* put each link into namelist and free it */
    i = 0;
    while (gshead)
    {
          (* gspec)[i].type = gshead->stemp.type;
          (* gspec)[i].targetocc = gshead->stemp.targetocc;
          (* gspec)[i].target = gshead->stemp.target;
          (* gspec)[i].target2 = gshead->stemp.target2;
          (* gspec)[i].sepnum = gshead->stemp.sepnum;
          (* gspec)[i].sepdistance = gshead->stemp.sepdistance;
          (* gspec)[i].prop = gshead->stemp.prop;
          i++;
          newgslink = gshead;
          gshead = gshead->next;
          free(newgslink);
    }

    return(i);
} /* read Gen Species Data */

int ReadConnections(int puno,struct sconnections connections[],int verbose,
                    struct spustuff pu[],struct binsearch PULookup[],struct sfname fnames)
{
    FILE *fp;
    int id1,id2;
    double fcost;
    int icount = 0,idup = 0;
    int bad;
    struct sneighbour *p;
    char *readname;
    int numvars = 3,ivars;
    char *sVarName,*sVarVal;
    char sLine[1000];

    readname = (char *) calloc(strlen(fnames.connectionname) + strlen(fnames.inputdir)+2, sizeof(char));
    strcpy(readname,fnames.inputdir);
    strcat(readname,fnames.connectionname);
    fp = fopen(readname,"r");
    if (fp==NULL)
    {
       ShowGenProg("Warning: Connection File %s not found ",fnames.connectionname);
       free(readname);
       return(0);
    }
    free(readname);

    fgets(sLine,999,fp); /* Scan header line */

    while (fgets(sLine,999,fp))
    {
          icount++;

          sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
          sscanf(sVarVal,"%d",&id1);
          sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
          sscanf(sVarVal,"%d",&id2);
          sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
          sscanf(sVarVal,"%lf",&fcost);

          id1 = FastPUIDtoPUINDEX(puno,id1,PULookup);
          id2 = FastPUIDtoPUINDEX(puno,id2,PULookup);

          if (id1==id2)
          {
             #ifdef ASYMCON
             if (asymmetricconnectivity)
                connections[id1].fixedcost = 0;
             else
             #endif
                 connections[id1].fixedcost += fcost;

             continue;
          } /* My code for an irremovable connection */
          if (id1>=0 && id1<puno)
          {  /* Is I a sensible number ?*/
             p = connections[id1].first;
             bad = 0;
             while (p)
             {
                   if (p->nbr == id2)
                      bad = 1;

                   p = p->next;
             }

             #ifdef ASYMCON
             if (asymmetricconnectivity)
                bad = 0;
             #endif

             if (bad)
                ShowDetProg("Double connection definition %i %i \n",pu[id1].id,pu[id2].id);
             else
             {
                 connections[id1].nbrno++;
                 p = (struct sneighbour *) malloc (sizeof(struct sneighbour));

                 #ifdef MEMDEBUG
                 iMemoryUsed += sizeof(struct sneighbour);
                 #endif

                 p->cost = fcost;
                 p->nbr = id2;
                 p->next = connections[id1].first;

                 #ifdef ASYMCON
                 if (asymmetricconnectivity)
                 {
                    p->connectionorigon = 1;
                 }
                 else
                     p->connectionorigon = 1;
                 #endif

                 connections[id1].first = p;
             }
          }
          else
              ShowErrorMessage("A connection is out of range %f %i %i \n",fcost,id1,id2);

          if (id2>=0 && id2<puno)
          {  /* Is I a sensible number ?*/
             p = connections[id2].first;
             bad = 0;
             while (p)
             {
                   if (p->nbr == id1)
                      bad = 1;
                   p = p->next;
             }

             #ifdef ASYMCON
             if (asymmetricconnectivity)
                bad = 0;
             #endif

             if (bad && verbose > 4)
                ShowDetProg("Double connection definition %i %i \n",id1,id2);
              if (bad)
                 idup++;
              else
              {
                  connections[id2].nbrno++;
                  p = (struct sneighbour *) malloc (sizeof(struct sneighbour));
                  #ifdef MEMDEBUG
                  iMemoryUsed += sizeof(struct sneighbour);
                  #endif
                  p->cost = fcost;
                  p->nbr = id1;
                  p->next = connections[id2].first;

                  #ifdef ASYMCON
                  p->connectionorigon = 1;
                  if (asymmetricconnectivity)
                     p->connectionorigon = 0;
                  else
                      p->connectionorigon = 1;
                  #endif

                  connections[id2].first = p;
              }
          }
          else
              ShowErrorMessage("A connection is out of range %f %i %i \n",fcost,id1,id2);
    }

    fclose(fp);

    if (idup)
       ShowGenProg("There were %i duplicate connection definitions.\n",idup);

    return(icount);

} /*** Read Connections ***/


/* * * *  Reading in the Planning Unit versus Species Data Type 2. Relational data *****/
/* * * *  into a Sparse Matrix data structure *****/
void LoadSparseMatrix(int *iSMSize, struct spu *SM[], int puno, int spno, struct spustuff PU[],
                      struct binsearch PULookup[],struct binsearch SPLookup[],
                      struct sfname fnames)
{    FILE *fp;
     char *readname;
     char sLine[1000];
     char sValVal;
     int ivars, iLastPUID;
     char *sVarName,*sVarVal;
     int i, _spid, _puid, iInternalSMSize = 0, iBigMatrixSize;
     double amount, rDensity, rInternalSMSize, rBigMatrixSize;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[300];
     #endif

     readname = (char *) calloc(strlen(fnames.puvsprname) + strlen(fnames.inputdir)+2, sizeof(char));

     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.puvsprname);
     fp = fopen(readname,"r");
     if (fp==NULL)
        ShowErrorMessage("PU v Species file %s not found\nAborting Program.",readname);
     free(readname);

     /* read through the file first to see how many lines */
     fgets(sLine,999,fp);
     while (fgets(sLine,999,fp))
     {
           iInternalSMSize++;
     }

     rewind(fp);
     fgets(sLine,999,fp);

     *iSMSize = iInternalSMSize;

     /* create the sparse matrix */
     *SM = (struct spu *) calloc(iInternalSMSize,sizeof(struct spu));

     /* planning unit richness and offset are already set to zero */

     iLastPUID = -1;

     /* init with zero values */
     for (i=0;i<iInternalSMSize;i++)
     {
         fgets(sLine,999,fp);

         sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
         sscanf(sVarVal,"%d",&_spid);
         sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
         sscanf(sVarVal,"%d",&_puid);
         sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
         sscanf(sVarVal,"%lf",&amount);

         if (_puid < iLastPUID)
         {
	        // error condition exists, file is not in ascending order for PUID

            #ifdef DEBUGTRACEFILE
            sprintf(debugbuffer,"Error: PU v Species file %s is not in ascending order for PUID at record %i.\nAborting Program.",fnames.puvsprname,i+1);
            AppendDebugTraceFile(debugbuffer);
            #endif

	        ShowErrorMessage("Error: PU v Species file %s is not in ascending order for PUID at record %i.\nAborting Program.",fnames.puvsprname,i+1);
		 }

         iLastPUID = _puid;

         _puid = FastPUIDtoPUINDEX(puno,_puid,PULookup);
         _spid = FastSPIDtoSPINDEX(spno,_spid,SPLookup);

         /* increment richness for planning unit containing this feature */
         PU[_puid].richness += 1;
         /* if planning units richness is one, set its offset */
         if (PU[_puid].richness == 1)
            PU[_puid].offset = i;

         (* SM)[i].amount = amount;
         (* SM)[i].clump = 0;
         (* SM)[i].spindex = _spid;
     }

     fclose(fp);


     iBigMatrixSize = puno * spno;
     rInternalSMSize = iInternalSMSize;
     rBigMatrixSize = iBigMatrixSize;
     rDensity = rInternalSMSize / rBigMatrixSize * 100;

     ShowGenProg("%i conservation values counted, %i big matrix size, %g%% density of matrix \n",
                 iInternalSMSize,iBigMatrixSize,rDensity);

} /* * * *  Reading in the Planning Unit versus Species Data Type 2. Relational data *****/
  /* * * *  into a Sparse Matrix data structure *****/

void LoadSparseMatrix_sporder(int *iSMSize, struct spusporder *SM[], int puno, int spno,
                              struct binsearch PULookup[],struct binsearch SPLookup[],// typesp spec[],
                              struct sfname fnames)
{
     FILE *fp;
     char *readname,sLine[500],*sVarName,*sVarVal;
     int i, _spid,spid, _puid, iInternalSMSize = 0, iBigMatrixSize, iLastSPID;
     double amount, rDensity, rInternalSMSize, rBigMatrixSize;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[300];
     #endif

     readname = (char *) calloc(strlen(fnames.matrixspordername) + strlen(fnames.inputdir)+2, sizeof(char));

     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.matrixspordername);
     if((fp = fopen(readname,"r"))==NULL)
         ShowErrorMessage("PU v Species file %s not found\nAborting Program.",readname);
     free(readname);

     /* read through the file first to see how many lines */
     fgets(sLine,500-1,fp);
     while (fgets(sLine,500-1,fp))
        iInternalSMSize++;

     rewind(fp);
     fgets(sLine,500-1,fp);

     *iSMSize = iInternalSMSize;

     /* create the sparse matrix */
     *SM = (struct spusporder *) calloc(iInternalSMSize,sizeof(struct spusporder));

     iLastSPID = -1;
     /* planning unit richness and offset are already set to zero */

     /* init with zero values */
     for (i=0;i<iInternalSMSize;i++)
     {

         fgets(sLine,500-1,fp);

            sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
         sscanf(sVarVal,"%d",&_spid);
            sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
         sscanf(sVarVal,"%d",&_puid);
            sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
         sscanf(sVarVal,"%lf",&amount);

         if (_spid < iLastSPID)
         {
	        // error condition exists, file is not in ascending order for SPID

            #ifdef DEBUGTRACEFILE
            sprintf(debugbuffer,"Error: PU v Species file %s is not in ascending order for SPID at record %i.\nAborting Program.",fnames.puvsprname,i+1);
            AppendDebugTraceFile(debugbuffer);
            #endif

	        ShowErrorMessage("Error: PU v Species file %s is not in ascending order for SPID at record %i.\nAborting Program.",fnames.puvsprname,i+1);
		 }

         iLastSPID = _spid;

         _puid = FastPUIDtoPUINDEX(puno,_puid,PULookup);
         spid = FastSPIDtoSPINDEX(spno,_spid,SPLookup);

         /* increment richness for planning unit containing this feature */
         spec[spid].richness += 1;
         /* if planning units richness is one, set its offset */
         if (spec[spid].richness == 1)
            spec[spid].offset = i;

         (* SM)[i].amount = amount;
         (* SM)[i].puindex = _puid;
     }

     fclose(fp);


     iBigMatrixSize = puno * spno;
     rInternalSMSize = iInternalSMSize;
     rBigMatrixSize = iBigMatrixSize;
     rDensity = rInternalSMSize / rBigMatrixSize * 100;

     ShowGenProg("%i conservation values counted, %i big matrix size, %g%% density of matrix \n",
                 iInternalSMSize,iBigMatrixSize,rDensity);

}/******** Reading in the Planning Unit versus Species Data Type 2. Relational data *****/
  /******** into a Sparse Matrix data structure *****/

/* creates an output file showing the planning unit richness and offset */
void DumpPU_richness_offset(int puno, struct spustuff PU[],struct sfname fnames)
{
     FILE *fp;
     int i;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("pu_richness_offset.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"pu_richness_offset.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create PU_richness_offset file %s\n",writename);
     free(writename);

     fputs("puindex,richness,offset\n",fp);
     for (i=0;i<puno;i++)
     {
         fprintf(fp,"%i,%i,%i\n",i,PU[i].richness,PU[i].offset);
     }

     fclose(fp);
}
/* creates an output file showing the planning unit richness and offset */

/* creates an output file from the loaded Sparse Matrix */
void DumpSparseMatrix(int iSMno, struct spu SM[],struct sfname fnames)
{
     FILE *fp;
     int i;
     char *writename;

     writename = (char *) calloc(strlen(fnames.outputdir) + strlen("sm.csv") + 2, sizeof(char));
     strcpy(writename,fnames.outputdir);
     strcat(writename,"sm.csv");
     fp = fopen(writename,"w");
     if (fp==NULL)
        ShowErrorMessage("cannot create PUvSpecies file %s\n",writename);
     free(writename);

     fputs("amount,clump,spid\n",fp);
     for (i=0;i<iSMno;i++)
     {
         fprintf(fp,"%g,%i,%i\n",SM[i].amount,SM[i].clump,SM[i].spindex);
     }

     fclose(fp);
}
/* creates an output file from the loaded Sparse Matrix */

/* FILEIN.C END */
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/* FILEOUT.C BEGIN */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * */
/* * * * ***** Output File * * * * * * * * * * * * *****/
/* * * * * * * * * * * * * * * * * * * * * * * * * * * */

void SaveSeed(int iseed)
{
     FILE *fp;
     fp = fopen("debug.out","w");
     fprintf(fp,"Debugging Output! \n");
     fprintf(fp,"iseed is %i \n",iseed);
     fclose(fp);
} /** Debugging Utility ***/


void CountPuZones2(char *sNames,char *sCounts,int imode,int puno,int R[])
{
     int i,*ZoneCount;
     char sCount[20];
     char sDelimiter[20];

     if (imode > 1)
        strcpy(sDelimiter,",");
     else
         strcpy(sDelimiter,"   ");

     ZoneCount = (int *) calloc(iZoneCount,sizeof(int));
     for (i=0;i<iZoneCount;i++)
         ZoneCount[i] = 0;

     for (i=0;i<puno;i++)
         if (R[i] > 0)
            ZoneCount[R[i]-1] += + 1;

     strcpy(sNames,"");
     strcpy(sCounts,"");
     for (i=0;i<iZoneCount;i++)
     {
         strcat(sNames,sDelimiter);
         if (imode == 2)
            strcat(sNames,"\"");
         strcat(sNames,Zones[i].name);
         strcat(sNames," PuCount");
         if (imode == 2)
            strcat(sNames,"\"");

         strcat(sCounts,sDelimiter);
         sprintf(sCount,"%i",ZoneCount[i]);
         strcat(sCounts,sCount);
     }

     free(ZoneCount);
}

void CostPuZones(char *sNames,char *sCounts,int imode,int puno,int R[])
{
     int i;
     double *ZoneCosts;
     char sCount[1000];
     char sDelimiter[20];
     double rZoneCost;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[1000];
     #endif

     if (imode > 1)
        strcpy(sDelimiter,",");
     else
         strcpy(sDelimiter,"   ");

     ZoneCosts = (double *) calloc(iZoneCount,sizeof(double));
     for (i=0;i<iZoneCount;i++)
         ZoneCosts[i] = 0;

     for (i=0;i<puno;i++)
     {
		 rZoneCost = ReturnPuZoneCost(i,R[i]);

         #ifdef DEBUGTRACEFILE
         //sprintf(debugbuffer,"CostPuZones ipu %i R %i zonecost %f\n",i,R[i],rZoneCost);
         //AppendDebugTraceFile(debugbuffer);
         #endif

         ZoneCosts[R[i]-1] += rZoneCost;
     }

     strcpy(sNames,"");
     strcpy(sCounts,"");
     for (i=0;i<iZoneCount;i++)
     {
         #ifdef DEBUGTRACEFILE
         sprintf(debugbuffer,"CostPuZones zone %i zonecost %f\n",i,ZoneCosts[i]);
         AppendDebugTraceFile(debugbuffer);
         #endif

         strcat(sNames,sDelimiter);
         if (imode == 2)
            strcat(sNames,"\"");
         strcat(sNames,Zones[i].name);
         strcat(sNames," Cost");
         if (imode == 2)
            strcat(sNames,"\"");

         strcat(sCounts,sDelimiter);
         sprintf(sCount,"%f",ZoneCosts[i]);
         strcat(sCounts,sCount);
     }

     free(ZoneCosts);
}

/* * * * ***** Output Solutions * * * * * * * */
/** imode = 1   Output Summary Stats only ******/
/** imode = 2    Output Everything * * * * *****/
void OutputSummary(int puno,int spno,int R[],struct sspecies spec[],struct scost reserve,
                   int itn,char savename[],double misslevel,int imode)
{
     FILE *fp;  /* Imode = 1, REST output, Imode = 2, Arcview output */
     int i,ino=0,isp;
     double shortfall,connectiontemp,rMPM;
     char sZoneNames[1000],sZonePuCount[1000],sZoneCostNames[1000],sZoneCost[1000];

     CountPuZones2(sZoneNames,sZonePuCount,imode,puno,R);
     CostPuZones(sZoneCostNames,sZoneCost,imode,puno,R);

     if (itn==1)
         fp = fopen(savename,"w");
     else
         fp = fopen(savename,"a");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     /*** Ouput the Summary Statistics *****/
     for (i=0;i<=puno-1;i++)
         if (R[i] != 0)
            ino ++;
     isp = CountMissing(spno,spec,misslevel,&shortfall,&rMPM);
     for (i=0,connectiontemp = 0;i<puno;i++)
         {
            connectiontemp += ConnectionCost2Linear(i,R[i],pu,connections,R,1,0);
         } /* Find True (non modified) connection */

     if (itn==1)
     {
        if (imode > 1)
        {
           fprintf(fp,"\"Run Number\",\"Score\",\"Cost\",\"Planning Units\"%s%s",sZoneNames,sZoneCostNames);
           fprintf(fp,",\"Connection Strength\",\"Penalty\",\"Shortfall\",\"Missing_Values\",\"MPM\"\n");
        }
        else
        {
            fprintf(fp,"Run no.    Score      Cost   Planning Units  %s%s",sZoneNames,sZoneCostNames);
            fprintf(fp,"  Connection_Strength   Penalty  Shortfall Missing_Values MPM\n");
        }
     }
     if (imode > 1)
        fprintf(fp,"%i,%f,%f,%i%s%s,%f,%f,%f,%i,%f\n",
                  itn,reserve.total,reserve.cost,ino,sZonePuCount,sZoneCost,connectiontemp,reserve.penalty,shortfall,isp,rMPM);
     else
         fprintf(fp,"%-4i    %8.2f  %8.2f  %8i%s%s     %8.2f         %8.2f   %8.2f       %i   %8.2f\n",
                 itn,reserve.total,reserve.cost,ino,sZonePuCount,sZoneCost,connectiontemp,reserve.penalty,shortfall,isp,rMPM);
     fclose(fp);
     return;

} /** Output Summary ***/

void OutputSpeciesData(int spno,struct sspecies spec[],char savename[])
{
     FILE *fp;
     int i;

     fp = fopen(savename,"w");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     fprintf(fp,"i,name,type,sname,target,prop,targetocc,spf,penalty,amount,occurrence,sepdistance,sepnum,separation,clumps,target2,richness,offset\n");

     for (i=0;i<spno;i++)
        fprintf(fp,"%i,%i,%i,%s,%f,%f,%i,%f,%f,%f,%i,%f,%i,%i,%i,%f,%i,%i"
                  ,i,spec[i].name,spec[i].type,spec[i].sname,spec[i].target,spec[i].prop,spec[i].targetocc
                  ,spec[i].spf,spec[i].penalty,spec[i].amount,spec[i].occurrence,spec[i].sepdistance
                  ,spec[i].sepnum,spec[i].separation,spec[i].clumps,spec[i].target2,spec[i].richness,spec[i].offset);

     fclose(fp);
} // Output Species Data

void OutputPenalty(int spno,struct sspecies spec[],char savename[],int iOutputType)
{
     FILE *fp;  // Imode = 1, REST output, Imode = 2, Arcview output
     int i;
     char sDelimiter[20];

     fp = fopen(savename,"w");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     if (iOutputType == 3)
        strcpy(sDelimiter,",");
     else
         strcpy(sDelimiter,"    ");

     fprintf(fp,"spid%spenalty\n",sDelimiter);

     // Ouput the Summary Statistics
     for (i=0;i<spno;i++)
         fprintf(fp,"%i%s%g\n",spec[i].name,sDelimiter,spec[i].penalty);

     fclose(fp);
} // Output Penalty

void LoadPenalty(int spno,struct sspecies spec[],struct sfname fnames)
{
     FILE *fp;
     int i, _spid;
     char *readname,sLine[500],*sVarName,*sVarVal;

     readname = (char *) calloc(strlen(fnames.penaltyname) + strlen(fnames.inputdir)+2, sizeof(char));

     strcpy(readname,fnames.inputdir);
     strcat(readname,fnames.penaltyname);
     if((fp = fopen(readname,"r"))==NULL)
         ShowErrorMessage("penalty file %s not found\nAborting Program.",readname);
     free(readname);

     // read header row
     fgets(sLine,500-1,fp);

     i=0;
     // read data rows
     while (fgets(sLine,500-1,fp))
     {
           sVarVal = strtok(sLine," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal,"%d",&_spid);
           sVarVal = strtok(NULL," ,;:^*\"/|\t\'\\\n");
           sscanf(sVarVal,"%lf",&spec[i].penalty);

           i++;
     }

     fclose(fp);

} // Load Penalty

void InitSolutionsMatrix(int puno,struct spustuff pu[],char savename[],int iOutputType,int iIncludeHeaders)
{
     FILE *fp;
     int i;
     char sDelimiter[20];

     fp = fopen(savename,"w");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     if (iIncludeHeaders == 1)
     {
        if (iOutputType == 3)
           strcpy(sDelimiter,",");
        else
            strcpy(sDelimiter,"    ");

        fprintf(fp,"SolutionsMatrix");

        for (i=0;i<puno;i++)
            fprintf(fp,"%s%i",sDelimiter,pu[i].id);

        fprintf(fp,"\n");
     }

     fclose(fp);
}

void AppendSolutionsMatrix(int iRun,int puno,int R[],char savename[],int iOutputType,int iIncludeHeaders)
{
     FILE *fp;
     int i,j;
     char sDelimiter[20];

     fp = fopen(savename,"a");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     if (iOutputType == 3)
        strcpy(sDelimiter,",");
     else
         strcpy(sDelimiter,"    ");

     for (i=1;i<=iZoneCount;i++)
     {
         if (iIncludeHeaders == 1)
         {
            fprintf(fp,"Z%iS%i%s",i,iRun,sDelimiter);
         }

         for (j=0;j<puno;j++)
         {
             if (j > 0)
                fprintf(fp,"%s",sDelimiter);

             if (R[j] == i)
                fprintf(fp,"1");
             else
                 fprintf(fp,"0");
         }

         fprintf(fp,"\n");
     }

     fclose(fp);
}

void AppendSolutionsMatrixZone(int iZone,int iRun,int puno,int R[],char savename[],int iOutputType,int iIncludeHeaders)
{
     FILE *fp;
     int j;
     char sDelimiter[20];

     fp = fopen(savename,"a");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     if (iOutputType == 3)
        strcpy(sDelimiter,",");
     else
         strcpy(sDelimiter,"    ");

     if (iIncludeHeaders == 1)
     {
        fprintf(fp,"S%i%s",iRun,sDelimiter);
     }

     for (j=0;j<puno;j++)
     {
         if (j > 0)
            fprintf(fp,"%s",sDelimiter);

         if (R[j] == iZone)
            fprintf(fp,"1");
         else
             fprintf(fp,"0");
     }

     fprintf(fp,"\n");

     fclose(fp);
}
/*
void AppendSolutionsMatrixRun(int iRun,int puno,int R[],char savename[],int iOutputType,int iIncludeHeaders)
{
     FILE *fp;
     int i,j;
     char sDelimiter[20];

     fp = fopen(savename,"a");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     if (iOutputType == 3)
        strcpy(sDelimiter,",");
     else
         strcpy(sDelimiter,"    ");

     for (i=1;i<=iZoneCount;i++)
     {
         if (iIncludeHeaders == 1)
         {
            fprintf(fp,"Z%i%s",i,sDelimiter);
         }

         for (j=0;j<puno;j++)
         {
             if (j > 0)
                fprintf(fp,"%s",sDelimiter);

             if (R[j] == i)
                fprintf(fp,"1");
             else
                 fprintf(fp,"0");
         }

         fprintf(fp,"\n");
     }

     fclose(fp);
}
*/
/*** Output A Solution ***/
void OutputSolution(int puno,int R[],struct spustuff pu[],char savename[],int imode)
{
     FILE *fp;  /* Imode = 1, REST output, Imode = 2, Arcview output */
     int i;
     fp = fopen(savename,"w");
     if (!fp)  ShowErrorMessage("Cannot save output to %s \n",savename);

     if (imode > 1)
        fprintf(fp,"planning_unit,zone\n");
     //for (i=0;i<puno;i++)
     for (i=puno-1;i>-1;i--)
         if (R[i] != 0)
         {
            fprintf(fp,"%i",pu[i].id);
            if (imode > 1)
               fprintf(fp,",%i",R[i]);
            fprintf(fp,"\n");
         }
     fclose(fp);
} /* Output Solution */

/* * * * ***** Scenario Output File * * * * * * * */
/*** OutputScenario ****/
void OutputScenario(int puno,int spno,double prop,
                    struct sanneal anneal,int seedinit,int repeats,int clumptype,
                    int runopts,int heurotype,double costthresh, double tpf1, double tpf2,
                    char savename[])
{
     FILE *fp;
     char temp[100];

     fp = fopen(savename,"w");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     fprintf(fp,"Number of Planning Units %i\n",puno);
     fprintf(fp,"Number of Conservation Values %i\n",spno);
     fprintf(fp,"Number of Zones %i\n",iZoneCount);
     fprintf(fp,"Number of Costs %i\n",iCostCount);
     fprintf(fp,"Starting proportion %.2f\n",prop);
     switch (clumptype)
     {
            case 0:strcpy(temp,"Clumping - default step function");break;
            case 1: strcpy(temp,"Clumping - two level step function.");break;
            case 2: strcpy(temp,"Clumping - rising benefit function");break;
     }
     fprintf(fp,"%s\n",temp);

     /* Use character array here and set up the name of the algorithm used */
     switch (runopts)
     {
            case 0: strcpy(temp,"Annealing and Heuristic");break;
            case 1: strcpy(temp,"Annealing and Iterative Improvement");break;
            case 2: strcpy(temp,"Annealing and Both");break;
            case 3: strcpy(temp,"Heuristic only");break;
            case 4: strcpy(temp,"Iterative Improvement only");break;
            case 5: strcpy(temp,"Heuristic and Iterative Improvement");
     }
     fprintf(fp,"Algorithm Used :%s\n",temp);
     if (runopts == 0 || runopts == 3 || runopts == 5)
     {
        switch (heurotype)
        {
               case 0: strcpy(temp,"Richness");break;
               case 1: strcpy(temp,"Greedy");break;
               case 2: strcpy(temp,"Maximum Rarity");break;
               case 3: strcpy(temp,"Best Rarity");break;
               case 4: strcpy(temp,"Average Rarity");break;
               case 5: strcpy(temp,"Summation Rarity");break;
               case 6: strcpy(temp,"Product Irreplaceability");break;
               case 7: strcpy(temp,"Summation Irreplaceability");break;
               default: strcpy(temp,"Unkown Heuristic Type");
        }
        fprintf(fp,"Heuristic type : %s\n",temp);
     }
     else
         fprintf(fp,"No Heuristic used \n");

     if (runopts <= 2)
     {
        fprintf(fp,"Number of iterations %i\n",anneal.iterations);
        if (anneal.Tinit >= 0)
        {
           fprintf(fp,"Initial temperature %.2f\n",anneal.Tinit);
           fprintf(fp,"Cooling factor %.6f\n",anneal.Tcool);
        }
        else
        {
            fprintf(fp,"Initial temperature set adaptively\n");
            fprintf(fp,"Cooling factor set adaptively\n");
        }
        fprintf(fp,"Number of temperature decreases %i\n\n",anneal.Titns);
     }
     else
     {
         fprintf(fp,"Number of iterations N/A\nInitial temperature N/A\nCooling Factor N/A\n");
         fprintf(fp,"Number of temperature decreases N/A\n\n");
     }

     if (costthresh)
     {
        fprintf(fp,"Cost Threshold Enabled: %lf\n",costthresh);
        fprintf(fp,"Threshold penalty factor A %.2f\n",tpf1);
        fprintf(fp,"Threshold penalty factor B %.2f\n\n",tpf2);
     }
     else
     {
         fprintf(fp,"Cost Threshold Disabled\nThreshold penalty factor A N/A\n");
         fprintf(fp,"Threshold penalty factor B N/A\n\n");
     }

     fprintf(fp,"Random Seed %i\n",seedinit);
     fprintf(fp,"Number of runs %i\n",repeats);
     fclose(fp);
}  /*** Output Scenario ****/

// Output Feature report (missing values report)
void OutputFeatures(int spno,struct sspecies spec[],char savename[],int imode,double misslevel)
{
     FILE *fp; // Imode = 1, Tab Delimitted Text output, Imode = 2, Arcview output, Imode = 3, Arcview output with CSV file extension
     int i,isp, iZoneArrayIndex;
     char temp[4];
     double rTarget, rAmountHeld, rOccurrenceTarget, rOccurrencesHeld, rMPM, rTestMPM;

     fp = fopen(savename,"w");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     if (imode > 1)
     {
        fprintf(fp,"\"Feature\",\"Feature Name\",\"Target\"");
        fprintf(fp,",\"Total Amount\",\"Contributing Amount Held\",\"Occurrence Target \",\"Occurrences Held\"");
        fprintf(fp,",\"Target Met\"");
        for (i=0;i<iZoneCount;i++)
        {
            fprintf(fp,",\"Target %s\",\"Amount Held %s\",\"Contributing Amount Held %s\",\"Occurrence Target %s\"",
                         Zones[i].name,Zones[i].name,Zones[i].name,Zones[i].name);
            fprintf(fp,",\"Occurrences Held %s\",\"Target Met %s\"",Zones[i].name,Zones[i].name);
        }
        fprintf(fp,",MPM\n");
     }
     else
     {
         fprintf(fp,"Feature\tFeature Name\tTarget");
         fprintf(fp,"\tAmount Held\tContributing Amount Held\tOccurrence Target \tOccurrences Held");
         fprintf(fp,"\tTarget Met");
         for (i=0;i<iZoneCount;i++)
         {
             fprintf(fp,"\tTarget %s\tAmount Held %s\tContributing Amount Held %s\tOccurrence Target %s",
                        Zones[i].name,Zones[i].name,Zones[i].name,Zones[i].name);
             fprintf(fp,"\tOccurrences Held %s\tTarget Met %s",Zones[i].name,Zones[i].name);
         }
         fprintf(fp,"\tMPM\n");
     }

     for (isp=0;isp<spno;isp++)
     {
         rMPM = 1;

         if (imode > 1)
         {
            fprintf(fp,"%i,%s,%lf",spec[isp].name,
                                   spec[isp].sname,
                                   spec[isp].target);
            fprintf(fp,",%lf,%lf,%i,%i",TotalAreas[isp],
                                        spec[isp].amount,
                                        spec[isp].targetocc,
                                        spec[isp].occurrence);
            strcpy(temp,"");  // use MISSLEVEL when computing target met
            if (spec[isp].target)
            {
               strcpy(temp,"yes");
               if (spec[isp].amount/spec[isp].target < misslevel)
                  strcpy(temp,"no");

               rTestMPM = spec[isp].amount/spec[isp].target;
               if (rTestMPM < rMPM)
                  rMPM = rTestMPM;
            }
            if (spec[isp].targetocc)
            {
               strcpy(temp,"yes");
               if (spec[isp].occurrence/spec[isp].targetocc < misslevel)
                  strcpy(temp,"no");

               rTestMPM = spec[isp].occurrence/spec[isp].targetocc;
               if (rTestMPM < rMPM)
                  rMPM = rTestMPM;
            }
            if (spec[isp].sepnum)
            {
               strcpy(temp,"yes");
               if (spec[isp].separation/spec[isp].sepnum < misslevel)
                  strcpy(temp,"no");
            }
            fprintf(fp,",%s",temp);
            for (i=0;i<iZoneCount;i++)
            {
                iZoneArrayIndex = (isp * iZoneCount) + i;
                rTarget = _ZoneTarget[iZoneArrayIndex].target;
                rAmountHeld = ZoneSpec[iZoneArrayIndex].amount;
                rOccurrenceTarget = _ZoneTarget[iZoneArrayIndex].occurrence;
                rOccurrencesHeld = ZoneSpec[iZoneArrayIndex].occurrence;
                fprintf(fp,",%lf,%lf,%lf,%lf,%lf",rTarget,
                                                  rAmountHeld,
                                                  rAmountHeld * _ZoneContrib[(isp * iZoneCount) + i],
                                                  rOccurrenceTarget,
                                                  rOccurrencesHeld);
                strcpy(temp,"");  // use MISSLEVEL when computing target met
                if (rTarget)
                {
                   strcpy(temp,"yes");  // use MISSLEVEL when computing target met
                   if (rAmountHeld/rTarget < misslevel)
                      strcpy(temp,"no");

                   rTestMPM = rAmountHeld/rTarget;
                   if (rTestMPM < rMPM)
                      rMPM = rTestMPM;
                }
                if (rOccurrenceTarget)
                {
                   strcpy(temp,"yes");  // use MISSLEVEL when computing target met
                   if (rOccurrencesHeld/rOccurrenceTarget < misslevel)
                      strcpy(temp,"no");

                   rTestMPM = rOccurrencesHeld/rOccurrenceTarget;
                   if (rTestMPM < rMPM)
                      rMPM = rTestMPM;
                }
                fprintf(fp,",%s",temp);
            }
            fprintf(fp,",%lf\n",rMPM);
         }
         else
         {
             fprintf(fp,"%i\t%s\t",spec[isp].name,
                                   spec[isp].sname);
             fprintf(fp,"%lf\t%lf\t%lf\t%i\t%i\t",spec[isp].target,
                                                  spec[isp].amount,
                                                  spec[isp].amount,
                                                  spec[isp].targetocc,
                                                  spec[isp].occurrence);
             strcpy(temp,"");  // use MISSLEVEL when computing target met
             if (spec[isp].target)
             {
                strcpy(temp,"yes");
                if (spec[isp].amount/spec[isp].target < misslevel)
                   strcpy(temp,"no");

                rTestMPM = spec[isp].amount/spec[isp].target;
                if (rTestMPM < rMPM)
                   rMPM = rTestMPM;
             }
             if (spec[isp].targetocc)
             {
                strcpy(temp,"yes");
                if (spec[isp].occurrence/spec[isp].targetocc < misslevel)
                   strcpy(temp,"no");

                rTestMPM = spec[isp].occurrence/spec[isp].targetocc;
                if (rTestMPM < rMPM)
                   rMPM = rTestMPM;
             }
             fprintf(fp,"\t%s",temp);
             for (i=0;i<iZoneCount;i++)
             {
                 iZoneArrayIndex = (isp * iZoneCount) + i;
                 rTarget = _ZoneTarget[iZoneArrayIndex].target;
                 rAmountHeld = ZoneSpec[iZoneArrayIndex].amount;
                 rOccurrenceTarget = _ZoneTarget[iZoneArrayIndex].occurrence;
                 rOccurrencesHeld = ZoneSpec[iZoneArrayIndex].occurrence;
                 fprintf(fp,"\t%lf\t%lf\t%lf\t%lf\t%lf",rTarget,
                                                        rAmountHeld,
                                                        rAmountHeld,
                                                        rOccurrenceTarget,
                                                        rOccurrencesHeld);
                 strcpy(temp,"");  // use MISSLEVEL when computing target met
                 if (rTarget)
                 {
                    strcpy(temp,"yes");
                    if (rAmountHeld/rTarget < misslevel)
                       strcpy(temp,"no");

                    rTestMPM = rAmountHeld/rTarget;
                    if (rTestMPM < rMPM)
                       rMPM = rTestMPM;
                 }
                 if (rOccurrenceTarget)
                 {
                    strcpy(temp,"yes");
                    if (rOccurrencesHeld/rOccurrenceTarget < misslevel)
                       strcpy(temp,"no");

                    rTestMPM = rOccurrencesHeld/rOccurrenceTarget;
                    if (rTestMPM < rMPM)
                       rMPM = rTestMPM;
                 }
                 fprintf(fp,"\t%s",temp);
             }
             fprintf(fp,"\t%lf\n",rMPM);
         }
     }

     fclose(fp);
} // Output Feature report (missing values report)


/*** Output A Solution ***/
void OutputSumSoln(int puno,int sumsoln[],int ZoneSumSoln[],int R[],struct spustuff pu[],char savename[],int imode)
{
     FILE *fp;  /* Imode = 1, REST output, Imode = 2, Arcview output */
     int i,j;

     fp = fopen(savename,"w");
     if (!fp)
        ShowErrorMessage("Cannot save output to %s \n",savename);

     if (imode > 1)
     {
        fprintf(fp,"\"planning unit\",\"number\"");
        for (j=0;j<iZoneCount;j++)
            fprintf(fp,",\"%s\"",Zones[j].name);
        fprintf(fp,"\n");
     }

     for (i=0;i<puno;i++)
         if (imode > 1)
         {
            fprintf(fp,"%i,%i",pu[i].id,sumsoln[i]);
            for (j=0;j<iZoneCount;j++)
                fprintf(fp,",%i",ZoneSumSoln[(puno * j) + i]);
            fprintf(fp,"\n");
         }
         else
         {
             fprintf(fp,"%i %i",pu[i].id,sumsoln[i]);
             for (j=0;j<iZoneCount;j++)
                 fprintf(fp," %i",ZoneSumSoln[(puno * j) + i]);
             fprintf(fp,"\n");
         }
     fclose(fp);
} /* Output Solution */

/* FILEOUT.C END */
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/* HEURISTIC.C BEGIN */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * */
/* * * * ***** Greedy Add On to  *  * * * * * * * * ****/
/* * * * * * * * * * * * * * * * * * * * * * * * * * * */

/* * * * * Greedy Species Penalty * * * * * * * * * * * */
double GreedyPen(int ipu,int puno, int spno, typesp spec[],int R[],struct spustuff pu[],struct spu SM[],
                 int clumptype)
{
       int i;
       double famount = 0.0, fold,newamount;

       for (i = 0;i<spno;i++)
       {
           fold = (spec[i].target - spec[i].amount);
           if (fold > 0)
           {
              if (spec[i].target2)
                 newamount = NewPenalty4(ipu,i,puno,spec,pu,SM,R,connections,1,clumptype);
              else
                  newamount = NewPenalty(ipu,i,spec,pu,SM,1);
              famount += (newamount - fold)*spec[i].spf;
           } /* Add new penalty if species isn't already in the system */
       }
       return(famount);  /* Negative means decrease in amount missing */
} /** Greedy Species Penalty **/

/* * * * * Greedy Score an alternative to the normal objective function *****/
double GreedyScore(int ipu,int puno, int spno, typesp *spec,struct spu SM[],
                   struct sconnections connections[],int R[],struct spustuff pu[],int clumptype)
{
       double currpen,currcost,currscore;

       currpen = GreedyPen(ipu,puno,spno,spec,R,pu,SM,clumptype);
       currcost = pu[ipu].cost + ConnectionCost2(ipu,R[ipu],connections,R,1,0);
       if (currcost <= 0)
       {
          currscore = -1.0/delta;
       } /* otherwise this 'free pu' will have +score */
       else
       {
           currscore = currpen/currcost;
       }

       return(currscore);
} /* Score for a planning unit based upon greedy algorithm */

/* * * * *** Rarity Settup. Sets up rare score for each species ******/
/**** score is total abundance / smallest species abundance * * * * */
void SetRareness(int puno, int spno, double Rare[],struct spustuff pu[],struct spu SM[],int R[])
{
     double smallest = 0;
     double *fcount;
     int i, ism, isp,ipu;

     fcount = (double *) calloc(spno,sizeof(double));

     for (isp=0;isp<spno;isp++)
         fcount[isp] = 0;

     for (ipu=0;ipu<puno;ipu++)
         if (pu[ipu].richness)
            for (i=0;i<pu[ipu].richness;i++)
            {
                ism = pu[ipu].richness + i;
                isp = SM[ism].spindex;
                //if (R[ipu] < 2)
                if ((R[ipu] > 0) && (pu[ipu].status < 2) && (pu[ipu].fPULock != 1) && (pu[ipu].fPUZone != 1))
                   fcount[isp] += rtnAmountSpecAtPu(pu,SM,ipu,isp);
            }

     for (isp=0;isp<spno;isp++)
     {
         if (smallest == 0 || (fcount[isp] < smallest && fcount[isp] > 0))
            smallest = fcount[isp];
         Rare[isp] = fcount[isp];
     }

     if (smallest == 0)
        ShowErrorMessage("Serious Error in calculating Rarenesses. No species detected.\n");

     for (isp=0;isp<spno;isp++)
         Rare[isp] /= smallest;

     free(fcount);
}  /* SetRareness */

/**** RareScore The score for a particular conservation value on a particular PU */
double RareScore(int isp,int ipu,int puno,typesp spec[],struct spu SM[], int R[],
                 struct sconnections connections[],struct spustuff pu[],int clumptype)
{
       double currpen,currcost,currscore;
       double fold, newamount;

       fold = (spec[isp].target - spec[isp].amount);
       if (fold > 0)
       {
          if (spec[isp].target2)
             newamount = NewPenalty4(ipu,isp,puno,spec,pu,SM,R,connections,1,clumptype);
          else
              newamount = NewPenalty(ipu,isp,spec,pu,SM,1);
          currpen = newamount - fold;
       } /* Add new penalty if species isn't already in the system */

       currcost = pu[ipu].cost + ConnectionCost2(ipu,R[ipu],connections,R,1,0);
       if (currcost <= 0)
       {
          currscore = -1.0/delta;
       } /* otherwise this 'free pu' will have +score */
       else
       {
           currscore = currpen/currcost;
       }

    return(currscore);
} /* RareScore */

/* * * * **** Max Rare Score Heuristic. PU scores based on rarest beast on PU */
double MaxRareScore(int ipu,int puno,struct sspecies spec[],struct spu SM[],
                    int R[],struct sconnections connections[],struct spustuff pu[],double Rare[],int clumptype)
{
       int i, ism, isp,rareno = -1;
       double rarest,rarescore;

       for (i=0;i<pu[ipu].richness;i++)
       {
           ism = pu[ipu].offset + i;
           isp = SM[ism].spindex;
           if (SM[ism].amount && (spec[isp].target > spec[isp].amount || (spec[isp].sepdistance && spec[isp].separation < 3)))
              if (1.0/Rare[isp] < rarest || rareno < 0)
              {
                 rareno = isp;
                 rarest = Rare[isp];
              }  /* Determine which is the rarest species */
       }

       if (rareno > -1)
          rarescore = RareScore(rareno,ipu,puno,spec,SM,R,connections,pu,clumptype)/rarest;
       else
           rarescore = 1.0 / delta;

       return(rarescore);
} /* Max Rare Score */

/* * * * * * * * * Best Rarity Score. Determines each species rare score * * * * */
double BestRareScore(int ipu,int puno,struct sspecies spec[],struct spu SM[],
                     int R[],struct sconnections connections[],struct spustuff pu[],double Rare[],int clumptype)
{
       int i, ism, isp,rareno = -1;
       double rarest = 0,rarescore;

       if (pu[ipu].richness)
       for (i=0;i<pu[ipu].richness;i++)
       {
           ism = pu[ipu].offset + i;
           isp = SM[ism].spindex;
           if (SM[ism].amount && (spec[isp].target > spec[isp].amount || (spec[isp].sepdistance && spec[isp].separation < 3)))
           {
              rarescore = RareScore(isp,ipu,puno,spec,SM,R,connections,pu,clumptype)/Rare[isp];
              if (rarescore > rarest || rareno < 0)
              {
                 rarest = rarescore;
                 rareno = isp;
              }
           }
       }

       return(rarescore);
} /* Best Rare Score */

/***** Average Rare Score. Rare Score for each scoring species/number scoring species **/
double AveRareScore(int ipu,int puno,struct sspecies spec[],struct spu SM[],
                    int R[],struct sconnections connections[],struct spustuff pu[],double Rare[],int clumptype)
{
       int i, ism, isp, rareno = 0;
       double rarescore = 0;

       if (pu[ipu].richness)
       for (i=0;i<pu[ipu].richness;i++)
       {
           ism = pu[ipu].offset + i;
           isp = SM[ism].spindex;
           if (SM[ism].amount && (spec[isp].target > spec[isp].amount || (spec[isp].sepdistance && spec[isp].separation < 3)))
           {
              rarescore += RareScore(isp,ipu,puno,spec,SM,R,connections,pu,clumptype)/Rare[isp];
              rareno++;
           }
       }

       return(rarescore/rareno);
} /* Ave Rare Score */

/***** Sum of Rare Score for each scoring species **/
double SumRareScore(int ipu,int puno,struct sspecies spec[],struct spu SM[],
                    int R[],struct sconnections connections[],struct spustuff pu[],double Rare[],int clumptype)
{
       int i, ism, isp;
       double rarescore = 0;

       if (pu[ipu].richness)
       for (i=0;i<pu[ipu].richness;i++)
       {
           ism = pu[ipu].offset + i;
           isp = SM[ism].spindex;
           if (SM[ism].amount && (spec[isp].target > spec[isp].amount || (spec[isp].sepdistance && spec[isp].separation < 3)))
              rarescore += RareScore(isp,ipu,puno,spec,SM,R,connections,pu,clumptype)/Rare[isp];
       }

       return(rarescore);
} /* Sum Rare Score */

/****** Set Abundances ******/
void SetAbundance(int puno,double Rare[],struct spustuff pu[],struct spu SM[])
{
     int i,j, ism, isp;

     for (i=0;i<puno;i++)
         if (pu[i].richness)
            for (j=0;j<pu[i].richness;j++)
            {
                ism = pu[i].offset + j;
                isp = SM[ism].spindex;
                Rare[isp] += SM[ism].amount;
            }
} /* Set Abundance */

/***** Irreplaceability For site for species *****/
double Irreplaceability(int ipu,int isp, double Rare[],struct spustuff pu[],struct spu SM[],typesp *spec)
{
       double buffer,effamount;

       buffer = Rare[isp] < spec[isp].target ? 0 : Rare[isp] - spec[isp].target;
       if (spec[isp].amount > spec[isp].target)
          return(0);
       effamount = rtnAmountSpecAtPu(pu,SM,ipu,isp);
       return(buffer<effamount ? 1 : effamount/buffer);
}

/***** Product Irreplaceability for a single site ****/
double ProdIrr(int ipu,double Rare[],struct spustuff pu[],struct spu SM[],typesp *spec)
{
       int i, ism, isp;
       double product = 1;

       if (pu[ipu].richness)
       for (i=0;i<pu[ipu].richness;i++)
       {
           ism = pu[ipu].offset + i;
           isp = SM[ism].spindex;
           if (SM[ism].amount && (spec[isp].target - spec[isp].amount)> 0)
              product *= (1-Irreplaceability(ipu,isp,Rare,pu,SM,spec));
       }

       return(1-product);
} /* Product Irreplaceability */

/***** Sum Irreplaceability for a single site *****/
double SumIrr(int ipu,double Rare[],struct spustuff pu[],struct spu SM[],typesp *spec)
{
       int i, ism, isp;
       double sum = 0;

       if (pu[ipu].richness)
          for (i=0;i<pu[ipu].richness;i++)
          {
              ism = pu[ipu].offset + i;
              isp = SM[ism].spindex;
              if (SM[ism].amount && (spec[isp].target - spec[isp].amount)> 0)
                 sum += (Irreplaceability(ipu,isp,Rare,pu,SM,spec));
          }

       return(sum);
} /* Sum Irreplaceability */

/* * * * ***** Main Heuristic Engine * * * * * * * * ****/
void Heuristics(int spno,int puno,struct spustuff pu[],struct sconnections connections[],
                int R[],typesp *spec,struct spu SM[], struct scost *reserve,
                double costthresh, double tpf1,double tpf2, int imode,int clumptype)
     /** imode = 1: 2: 3: 4: */
     /** imode = 5: 6: Prod Irreplaceability, 7: Sum Irreplaceability */
{
     int i,j,bestpu,iZone=0,iArrayIndex;
     double bestscore,currscore;
     struct scost change;
     double *Rare;


     /**** Irreplacability ****/

     if (imode >= 6 && imode <= 7)
     {
        Rare = (double *) calloc(spno,sizeof(double));
        SetAbundance(puno,Rare,pu,SM);
     }

     if (imode >= 2 && imode <= 5) /* Rareness Setups */
     {
        Rare = (double *) calloc(spno,sizeof(double));
        SetRareness(puno,spno,Rare,pu,SM,R);
     }

     do
     {
       bestpu = 0;
       bestscore = 0;

       for (i=0;i<puno;i++)
           //if ((R[i] != iAvailableEquivalentZone) && (R[i] != 0)) /* Only look for new PUS */
           {
              // choose a non-available zone to score this available site for
              // we are changing from the available zone to the non-available zone
              //do
                iZone = RandNum(iZoneCount) + 1;

              //while (iZone != iAvailableEquivalentZone);

              /* Set the score for the given Planning Unit */
              currscore = 1; /* null if no other mode set */
              if (imode == 0)
                 currscore = GreedyScore(i,puno,spno,spec,SM,connections,R,pu,clumptype);
              if (imode == 1)
              {
                 CheckChange(i,i,puno,pu,connections,spec,SM,R,1,iZone,&change,reserve,
                             costthresh,tpf1,tpf2,1, clumptype,0);
                 currscore = change.total;
              }
              if (imode == 2)
                 currscore = MaxRareScore(i,puno,spec,SM,R,connections,pu,Rare, clumptype);
              if (imode == 3)
                 currscore = BestRareScore(i,puno,spec,SM,R,connections,pu,Rare,clumptype);
              if (imode == 4)
                 currscore = AveRareScore(i,puno,spec,SM,R,connections,pu,Rare,clumptype);
              if (imode == 5)
                 currscore = SumRareScore(i,puno,spec,SM,R,connections,pu,Rare,clumptype);
              if (imode == 6)
                 currscore = -ProdIrr(i,Rare,pu,SM,spec);
              if (imode == 7)
                 currscore = -SumIrr(i,Rare,pu,SM,spec);

              currscore *=(double) rand1()*0.001 + 1.0;
              if (!costthresh || pu[i].cost + reserve->cost <= costthresh)
                 if (currscore < bestscore)
                 {
                    bestpu = i;
                    bestscore = currscore;
                 } /** is this better (ie negative) than bestscore? **/

           } /** I've looked through each pu to find best **/

           if (bestscore)
           {
              CheckChange(i,bestpu,puno,pu,connections,spec,SM,R,1,iZone,&change,reserve,
                          costthresh,tpf1,tpf2,1,clumptype,0);
              DoChange(bestpu,puno,R,reserve,change,pu,SM,spec,connections,1,iZone,clumptype);

              /* Different Heuristics might have different penalty effects */

              /* Old fashioned penalty and missing counting */
              reserve->missing = 0;
              for (i=0;i<spno;i++)
              {
                  for (j=0;j<iZoneCount;j++)
                  {
                      iArrayIndex = (i*iZoneCount)+j;
                      if (ZoneSpec[iArrayIndex].amount < _ZoneTarget[iArrayIndex].target)
                         reserve->missing++;
                  }
                  if (spec[i].amount < spec[i].target)
                     reserve->missing++;
                  else
                      if (spec[i].sepdistance && spec[i].separation < 3)
                         reserve->missing++;
                  /** Species missing **/
              } /** checking to see who I am missing **/
           } /** Add Pu as long as I've found one **/

           if (bestscore)
              ShowGenProgInfo("P.U. %i score %.6f Cost %.1f Connection %.1f Missing %i Amount %.1f \n",
                              bestpu,bestscore,reserve->cost,reserve->connection,reserve->missing,
                              reserve->penalty);

     } while(bestscore);
     /** Repeat until all good PUs have been added **/

     reserve->total = reserve->cost + reserve->connection + reserve->penalty;

}  /**** Heuristics * * * * ****/

/* HEURISTIC.C END */
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/* ITIMP.C BEGIN */

/*** ItImpDiscard * * * * * * * * ******/
/* move a given id from the list to the discard */
struct slink* ItImpDiscard(int ichoice, struct slink *list, struct slink **discard)
{
       struct slink *tempp;
       struct slink *lp;

       if (list->id == ichoice)
       {
          tempp = list->next;
          list->next = *discard;
          *discard = list;
          list = tempp;
       } /* discard is at head of the list */
       else
       {
           for (lp = list;lp->next && lp->next->id != ichoice; lp = lp->next)
               ;
           tempp = lp->next->next;
           lp->next->next = *discard;
           *discard = lp->next;
           lp->next = tempp;
       }  /* discard from lower on list */

  return(list);
} /* ItImpDiscard */

/* * * * **** It Imp Undiscard * * * * * * * * */
/* glue discards back on to list. Return list and set discard to NULL locally */
struct slink* ItImpUndiscard(struct slink *list, struct slink **discard)
{
       struct slink *tempp;

       if (!(*discard))
          return(list); /* no discards to glue back */
       for (tempp = (*discard); tempp->next; tempp = tempp->next)
           ;
       tempp->next = list;
       list = (*discard);
       (*discard) = NULL;
       return(list);

} /* ItImpUndiscard */


/* * * * ****** Find Swap * * * * ******/
/*** Find swap is used by the new iterative improvement to find a change which passes a threshold test ****/
/* Returns either 0 for no swap or 1 for good swap */
int FindSwap(struct slink **list,double targetval,int itestchoice,int puuntried,
             int puno,struct spustuff pu[], struct sconnections connections[],
             struct sspecies spec[],struct spu SM[],
             int R[], struct scost *reserve, struct scost *change,
             double costthresh, double tpf1, double tpf2, int clumptype)
{
    #ifdef DEBUGTRACEFILE
    char debugbuffer[1000];
    #endif
    struct slink *discard = NULL;
    struct slink *lp;
    int imode,iOriginalZone,iPreviousR,ichoice,ipu,iZone, iLoopCounter;
    struct scost swapchange;
    /* use list to cycle through the sites in random order */
    /* Start by making change (which might be later reversed) */
    if (R[itestchoice] == 0)
       return(0);  // return no swap if planning unit is excluded

    iOriginalZone = R[itestchoice];
    iLoopCounter = 0;
    iPreviousR = R[itestchoice];

    if (pu[itestchoice].fPUZone == 1)
    {
       // enforce locked into range of zones
       do
       {
         iZone = RandNum(iZoneCount) + 1;

         iLoopCounter++;

         if (iLoopCounter > 5000)
         {
            #ifdef DEBUGTRACEFILE
            DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,999);
            AppendDebugTraceFile("PuZone endless loop in FindSwap detected\n");
            sprintf(debugbuffer,"puid %i iZone %i\n",pu[itestchoice].id,iZone);
            AppendDebugTraceFile(debugbuffer);
            #endif
            ShowGenProg("\nPuZone endless loop in FindSwap detected\n");
            ShowGenProg("Internal error detected.  Please inform the Marxan with Zones developers.\n\n");
            ShowPauseExit();
            exit(1);
         }
       }
       while ((iZone == iPreviousR) || (PuNotInAllowedZone(pu[itestchoice],iZone,PuZone,0,'f')));
    }
    else
    {
        // allowed in any zone
        do
          iZone = RandNum(iZoneCount) + 1;

        while (iZone == iPreviousR);
    }

    //if (iZone == iAvailableEquivalentZone)
    //   imode = -1;
    //else
        imode = 1;

    DoChange(itestchoice,puno,R,reserve,*change,pu,SM,spec,connections,imode,iZone,clumptype);
    *list = ItImpDiscard(itestchoice,*list,&discard);

    puuntried--;

    while (puuntried > 0)
    {
          ipu = RandNum(puuntried);
          lp = *list;
          if (ipu == 0)
          {
             ichoice = (*list)->id;
             //ispecial = 1;
          }
          else
          {
              while (lp->next && --ipu > 0)
                    lp = lp->next;
              ichoice = lp->next->id;
          }

          /*imode = (R[ichoice]!=iAvailableEquivalentZone)?-1:1;

          if (imode == -1)
          {
             // we are changing from a non-available zone to the available zone
             iZone = iAvailableEquivalentZone;
          }
          else
          {
              // we are changing from the available zone to the non-available zone
              do
                iZone = RandNum(iZoneCount) + 1;

              while (iZone != iAvailableEquivalentZone);
          }*/
          imode = 1;
          iZone = RandNum(iZoneCount) + 1;

          CheckChange(puuntried,ichoice,puno,pu,connections,spec,SM,R,imode,iZone,&swapchange,reserve,
                      costthresh,tpf1,tpf2,1,clumptype,0);

          if (swapchange.total + targetval < 0) /* I have found a good swap */
          {
             DoChange(ichoice,puno,R,reserve,swapchange,pu,SM,spec,connections,imode,iZone,clumptype);
             *list = ItImpUndiscard(*list,&discard);
             ShowDetProg("It Imp has changed %i and %i with total value %lf \n",
                         itestchoice,ichoice,change->total+targetval);
             return(1); /* return negates need for else statement */
          } /* exit loop */

          /* Change is not good enough */
          puuntried--;
          *list = ItImpDiscard(ichoice,*list,&discard); /* remove choice from list */
    } /* cycle until I find swap or finish list */
    /* No change is good enough. Reverse changes and leave */
    if (iZone != iOriginalZone) // we have changed a zone
    {
       //if (iOriginalZone == iAvailableEquivalentZone)
       //   imode = -1; // we changed to a reserved zone and now are changing back
       //else
           imode = 1; // we changed to available zone and now are changing back
    }
    /* multiply all change values by -1 */
    ChangeCost(change,-1);
    DoChange(itestchoice,puno,R,reserve,*change,pu,SM,spec,connections,imode,iOriginalZone,clumptype);

     *list = ItImpUndiscard(*list,&discard);
    return(0);   /* return empty handed */
}  /* Find Swap */


/* * * * **** Iterative Improvement * * * * *****/
/*** Iterative improvement using dynamic memory allocation * * * * */
void IterativeImprovement(int puno,struct spustuff pu[], struct sconnections connections[],
                           struct sspecies spec[],struct spu SM[],int R[],
                           struct scost *reserve,struct scost *change,double costthresh,double tpf1, double tpf2,
                           int clumptype,int itimptype)
{
     struct slink *list, *discard, *lp, *newp, *tempp;
     int puuntried ,puvalid = 0, i,j,ipu,imode,ichoice,iZone, iLoopCounter;
     #ifdef DEBUGTRACEFILE
     char debugbuffer[1000];
     #endif

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"IterativeImprovement begin puvalid %d\n",puvalid);
     //AppendDebugTraceFile(debugbuffer);
     #endif

     list = NULL;
     discard = NULL;
     for (ipu=0;ipu<puno;ipu++)
     {
         #ifdef DEBUGTRACEFILE
         //sprintf(debugbuffer,"IterativeImprovement ipu %i puno %i R[ipu] %i pu[ipu].status %i pu[ipu].fPULock %i pu[ipu].fPUZone %i puvalid %d\n"
         //                   ,ipu,puno,R[ipu],pu[ipu].status,pu[ipu].fPULock,pu[ipu].fPUZone,puvalid);
         //AppendDebugTraceFile(debugbuffer);
         #endif

         //if (R[i] < 2)
         if ((R[ipu] > 0) && (pu[ipu].status < 2) && (pu[ipu].fPULock != 1) && (pu[ipu].fPUZone != 1))
            for (j=0;j<(iZoneCount*2);j++)  // add each planning unit iZoneCount*2 times to allow adequate sampling of zones
            {   // creating original link list
                #ifdef DEBUGTRACEFILE
                //sprintf(debugbuffer,"IterativeImprovement puvalid %d\n",puvalid);
                //AppendDebugTraceFile(debugbuffer);
                #endif

                newp = (struct slink *) malloc(sizeof(struct slink));
                newp->id = ipu;
                newp->next = list;
                list = newp;
                puvalid++;
            }   // creating original link list
     }

     puuntried = puvalid;

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"IterativeImprovement puuntried %d puvalid %d\n",puuntried,puvalid);
     //AppendDebugTraceFile(debugbuffer);
     #endif

     /***** Doing the improvements ****/
     while (puuntried > 0)
     {
           ipu = RandNum(puuntried);
           lp = list;
           if (ipu == 0)
           {
              ichoice = list->id;
           }
           else
           {
               while (lp->next && --ipu > 0)
                      lp = lp->next;
               ichoice = lp->next->id;
           }

           //if (R[ichoice]==iAvailableEquivalentZone)
           {
              // we are changing from the available zone to the non-available zone
              imode = 1;

              if (pu[ichoice].fPUZone == 1)
              {
                 // enforce locked into range of zones

                 iLoopCounter = 0;

                 do
                 {
                   iZone = RandNum(iZoneCount) + 1;

                   iLoopCounter++;

                   if (iLoopCounter > 5000)
                   {
                      #ifdef DEBUGINITIALISERESERVE
                      DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,999);
                      AppendDebugTraceFile("PuZone endless loop in IterativeImprovement detected\n");
                      sprintf(debugbuffer,"puid %i iZone %i\n",pu[ichoice].id,iZone);
                      AppendDebugTraceFile(debugbuffer);
                      #endif
                      ShowGenProg("\nPuZone endless loop in IterativeImprovement detected\n");
                      ShowGenProg("Internal error detected.  Please inform the Marxan with Zones developers.\n\n");
                      ShowPauseExit();
                      exit(1);
                   }
                 }
                 //while ((iZone == iAvailableEquivalentZone) || (PuNotInAllowedZone(pu[ichoice],iZone,PuZone,0,'I')));
                 while (PuNotInAllowedZone(pu[ichoice],iZone,PuZone,0,'I'));
              }
              else
              {
                 // allowed in any zone
                 //do
                   iZone = RandNum(iZoneCount) + 1;

                 //while (iZone == iAvailableEquivalentZone);
              }
           }
           //else
           //{
           //    // we are changing from a non-available zone to the available zone
           //    imode = -1;
           //    iZone = iAvailableEquivalentZone;
           //}

           CheckChange(puuntried,ichoice,puno,pu,connections,spec,SM,R,imode,iZone,change,reserve,
                       costthresh,tpf1,tpf2,1,clumptype,1);
           if (change->total < 0)
           {
              #ifdef DEBUGTRACEFILE
              sprintf(debugbuffer,"IterativeImprovement good change imode %i puid %i\n",imode,ichoice);
              AppendDebugTraceFile(debugbuffer);
              #endif

              ShowGenProgInfo("It Imp has changed %i with change value %lf \n",ichoice,change->total);
              DoChange(ichoice,puno,R,reserve,*change,pu,SM,spec,connections,imode,iZone,clumptype);
              puuntried = puvalid-1;
              list = ItImpUndiscard(list,&discard);

           }   /* I've just made a good change */
           else
           {
               puuntried--; /* it was another bad choice */
           }
           list = ItImpDiscard(ichoice,list,&discard);  /* Remove ichoice from list whether good or bad */

     }/* no untested PUs left */

     /*** Clean Up & post processing */
     //tempp= list;
     while (list)
     {
           tempp = list;
           list = list->next;
           free(tempp);
           DebugFree(sizeof(struct slink));
     }  /* clear list */
     while (discard)
     {
           tempp = discard;
           discard = discard->next;
           free(tempp);
           DebugFree(sizeof(struct slink));
     } /* Clear discard */

     #ifdef DEBUGTRACEFILE
     //sprintf(debugbuffer,"IterativeImprovement end\n");
     //AppendDebugTraceFile(debugbuffer);
     #endif
}  /*** Iterative Improvement 2 ***/

void siftDown_ii(struct iimp numbers[], int root, int bottom, int array_size)
{
     int done, maxChild;
     typeiimp temp;

     done = 0;
     while ((root*2 <= bottom) && (!done))
     {
           if (root*2 < array_size)
           {
              if (root*2 == bottom)
                 maxChild = root * 2;
              else
                  if (numbers[root * 2].randomfloat > numbers[root * 2 + 1].randomfloat)
                     maxChild = root * 2;
                  else
                      maxChild = root * 2 + 1;

              //if (numbers[root].randomindex < numbers[maxChild].randomindex)
              if (numbers[root].randomfloat < numbers[maxChild].randomfloat)
              {
                 temp = numbers[root];
                 numbers[root] = numbers[maxChild];
                 numbers[maxChild] = temp;
                 root = maxChild;
              }
              else
                  done = 1;
           }
           else
               done = 1;
     }
}

void heapSort_ii(struct iimp numbers[], int array_size)
{
     int i;
     typeiimp temp;
     #ifdef DEBUGTRACEFILE
     //char debugbuffer[1000];
     #endif

     for (i = (array_size / 2)-1; i >= 0; i--)
     {
         #ifdef DEBUGTRACEFILE
         //sprintf(debugbuffer,"heapSort_ii i %i\n",i);
         //AppendDebugTraceFile(debugbuffer);
         #endif

         siftDown_ii(numbers, i, array_size, array_size);
     }

     for (i = array_size-1; i >= 1; i--)
     {
         #ifdef DEBUGTRACEFILE
         //sprintf(debugbuffer,"heapSort_ii i %i\n",i);
         //AppendDebugTraceFile(debugbuffer);
         #endif

         temp = numbers[0];
         numbers[0] = numbers[i];
         numbers[i] = temp;
         siftDown_ii(numbers, 0, i-1, array_size);
     }
}

void IterativeImprovementOptimise(int puno,struct spustuff pu[], struct sconnections connections[],
                                   struct sspecies spec[],struct spu SM[],int R[],
                                   struct scost *reserve,struct scost *change,double costthresh,double tpf1, double tpf2,
                                   int clumptype,int irun,char *savename)
{
     int puvalid =0,i,j,ipu=0,imode,ichoice,iZone,iSamplesForEachPu, iRowCounter, iRowLimit, iLoopCounter, iPreviousR, ichanges = 0;
     struct iimp *iimparray;
     double debugfloat;
     char debugbuffer[1000],tempname2[100];
     FILE *ttfp,*zonefp;
     char *writename;

     #ifdef DEBUGTRACEFILE
     AppendDebugTraceFile("IterativeImprovementOptimise start\n");
     #endif

     iSamplesForEachPu = (iZoneCount-1)*2; // allow sampling to each zone and back to available for each non available  zone

     // counting pu's we need to test
     for (i=0;i<puno;i++)
         if ((R[ipu] > 0) && (pu[ipu].status < 2) && (pu[ipu].fPULock != 1) && (pu[ipu].fPUZone != 1))
            puvalid += iSamplesForEachPu;

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"IterativeImprovementOptimise puvalid %i\n",puvalid);
     AppendDebugTraceFile(debugbuffer);
     #endif

     if (fnames.saveitimptrace)
     {
        if (fnames.saveitimptrace==3)
           sprintf(tempname2,"%s_itimp_objective%05i.csv",savename,irun%10000);
        else
        if (fnames.saveitimptrace==2)
           sprintf(tempname2,"%s_itimp_objective%05i.txt",savename,irun%10000);
        else
            sprintf(tempname2,"%s_itimp_objective%05i.dat",savename,irun%10000);

        writename = (char *) calloc(strlen(fnames.outputdir) + strlen(tempname2) + 2, sizeof(char));
        //strcpy(writename,fnames.outputdir);
        strcpy(writename,tempname2);
        if ((ttfp = fopen(writename,"w"))==NULL)
           ShowErrorMessage("cannot create threshold trace file %s\n",writename);
        free(writename);
        if (fnames.saveitimptrace > 1)
           fprintf(ttfp,"improvement,total,cost,connection,penalty\n");
        else
            fprintf(ttfp,"improvement total cost connection penalty\n");

        if (fnames.saveitimptrace==3)
           sprintf(tempname2,"%s_itimp_zones%05i.csv",savename,irun%10000);
        else
        if (fnames.saveitimptrace==2)
           sprintf(tempname2,"%s_itimp_zones%05i.txt",savename,irun%10000);
        else
            sprintf(tempname2,"%s_itimp_zones%05i.dat",savename,irun%10000);

        //sprintf(tempname2,"%s_anneal_zones%05i.csv",savename,irun%10000);
        writename = (char *) calloc(strlen(fnames.outputdir) + strlen(tempname2) + 2, sizeof(char));
        //strcpy(writename,fnames.outputdir);
        strcat(writename,tempname2);
        if ((zonefp = fopen(writename,"w"))==NULL)
           ShowErrorMessage("cannot create threshold trace file %s\n",writename);
        free(writename);
        fprintf(zonefp,"configuration");
        if (fnames.saveitimptrace > 1)
        {
           for (i = 0;i<puno;i++)
               fprintf(zonefp,",%i",pu[i].id);
           fprintf(zonefp,"\n0");

           for (i = 0;i<puno;i++)
               fprintf(zonefp,",%i",R[i]);
        }
        else
        {
            for (i = 0;i<puno;i++)
                fprintf(zonefp," %i",pu[i].id);
            fprintf(zonefp,"\n0");

            for (i = 0;i<puno;i++)
                fprintf(zonefp," %i",R[i]);
        }
        fprintf(zonefp,"\n");

        iRowCounter = 0;
        if (fnames.itimptracerows == 0)
           iRowLimit = 0;
        else
            iRowLimit = floor(puvalid / fnames.itimptracerows);
     }

     if (puvalid > 0)
     {
        iimparray = (struct iimp *) calloc(puvalid,sizeof(struct iimp));

        for (i=0;i<puno;i++)
            if ((R[i] > 0) && (pu[i].status < 2) && (pu[i].fPULock != 1) && (pu[i].fPUZone != 1))
               for (j=0;j<iSamplesForEachPu;j++)  // add each planning unit iZoneCount*2 times to allow adequate sampling of zones
               {
                   iimparray[ipu].puindex = i;
                   iimparray[ipu].randomfloat = rand1();
                   ipu++;
               }

        #ifdef DEBUGTRACEFILE
        sprintf(debugbuffer,"IterativeImprovementOptimise after array init file %s\n",tempname2);
        AppendDebugTraceFile(debugbuffer);
        #endif

        // sort the iimp array by the randomindex field
        heapSort_ii(iimparray,puvalid);

        #ifdef DEBUGTRACEFILE
        AppendDebugTraceFile("IterativeImprovementOptimise after heapSort_ii\n");
        #endif

        /***** Doing the improvements ****/
        for (i=0;i<puvalid;i++)
        {
              ichoice = iimparray[i].puindex;

              if ((R[ichoice] > 0) && (pu[ichoice].status < 2) && (pu[ichoice].fPULock != 1) && (pu[ichoice].fPUZone != 1))
              {

                 iPreviousR = R[ichoice];

                 if (pu[ichoice].fPUZone == 1)
                 {
                    // enforce locked into range of zones
                    iLoopCounter = 0;

                    do
                    {
                      iZone = RandNum(iZoneCount) + 1;
                      iLoopCounter++;

                      if (iLoopCounter > 5000)
                      {
                         #ifdef DEBUGTRACEFILE
                         DumpPuZone_Debug(iPuZoneCount,PuZone,fnames,999);
                         AppendDebugTraceFile("PuZone endless loop in IterativeImprovementOptimise detected\n");
                         sprintf(debugbuffer,"puid %i iZone %i\n",pu[ichoice].id,iZone);
                         AppendDebugTraceFile(debugbuffer);
                         #endif
                         ShowGenProg("\nPuZone endless loop in IterativeImprovementOptimise detected\n");
                         ShowGenProg("Internal error detected.  Please inform the Marxan with Zones developers.\n\n");
                         ShowPauseExit();
                         exit(1);
                      }
                    }
                    while ((iZone == iPreviousR) || (PuNotInAllowedZone(pu[ichoice],iZone,PuZone,0,'I')));
                 }
                 else
                 {
                     // allowed in any zone
                     do
                       iZone = RandNum(iZoneCount) + 1;

                     while (iZone == iPreviousR);
                 }

                 //if (iZone == iAvailableEquivalentZone)
                 //   imode = -1;
                 //else
                     imode = 1;

                 CheckChange(i,ichoice,puno,pu,connections,spec,SM,R,imode,iZone,change,reserve,
                             costthresh,tpf1,tpf2,1,clumptype,1);
                 if (change->total < 0)
                 {
                    ichanges++;
                    ShowGenProgInfo("It Imp has changed %i with change value %lf \n",ichoice,change->total);
                    DoChange(ichoice,puno,R,reserve,*change,pu,SM,spec,connections,imode,iZone,clumptype);
                 }   /* I've just made a good change */
              }

              if (fnames.saveitimptrace)
              {
                 iRowCounter++;
                 if (iRowCounter > iRowLimit)
                    iRowCounter = 1;

                 if (iRowCounter == 1)
                 {
                    fprintf(zonefp,"%i",i);

                    if (fnames.saveitimptrace > 1)
                    {
                       fprintf(ttfp,"%i,%f,%f,%f,%f\n"
                                   ,i,reserve->total
                                   ,reserve->cost,reserve->connection,reserve->penalty); // i,costthresh,cost,connection,penalty

                       for (j = 0;j<puno;j++)
                           fprintf(zonefp,",%i",R[j]);
                    }
                    else
                    {
                        fprintf(ttfp,"%i %f %f %f %f\n"
                                    ,i,reserve->total,reserve->cost,reserve->connection,reserve->penalty);

                        for (j = 0;j<puno;j++)
                            fprintf(zonefp," %i",R[j]);
                    }

                    fprintf(zonefp,"\n");
                 }
              }
        }/* no untested PUs left */

        free(iimparray);
     }

     if (fnames.saveitimptrace)
     {
        fclose(ttfp);
        fclose(zonefp);
     }

     #ifdef DEBUGTRACEFILE
     sprintf(debugbuffer,"IterativeImprovementOptimise end changes %i\n",ichanges);
     AppendDebugTraceFile(debugbuffer);
     #endif

}  /*** Time Optimised Iterative Improvement ***/

/* ITIMP.C END */
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/* RANDOM.C BEGIN */

/* * * * * * * * *****
 *  ran1() from numerical recipes
     produces a random number between 0 and 1
 */

#define IA 16807
#define IM 2147483647
#define AM (1.0 / IM)
#define IQ 127773
#define IR 2836
#define NTAB 32
#define NDIV (1 + (IM - 1)/NTAB)
#define EPS 1.2e-7
#define RNMX (1.0-EPS)

long    RandomIY;
long    RandomIV[NTAB];

double rand1(void)
{
       int j;
       long k;
       double temp;

       if (RandSeed1 <= 0 || !RandomIY)    // Initialize
       {
          RandSeed1 = -RandSeed1;
          for (j = NTAB+7; j >= 0; j--)
          {
              k = RandSeed1/IQ;
              RandSeed1 = IA * (RandSeed1 - k * IQ) - IR * k;
              if (RandSeed1 < 0)
                 RandSeed1 += IM;
              if (j < NTAB)
                 RandomIV[j] = RandSeed1;
          }
          RandomIY=RandomIV[0];
       }
       k=RandSeed1/IQ;        /* The stuff we do on calls after the first */
       RandSeed1 = IA * (RandSeed1 - k * IQ) - IR * k;
       if (RandSeed1 < 0)
          RandSeed1 += IM;
       j = RandomIY/NDIV;
       RandomIY=RandomIV[j];
       RandomIV[j] = RandSeed1;
       temp=AM*RandomIY;
       if (temp > RNMX)
          return(RNMX);
       else
           return(temp);
}

void InitRandSeed(int iSeed)
{
     if (iSeed>0)
        RandSeed1 = iSeed;
     else
         RandSeed1 = (long int)time(NULL);
     if (RandSeed1 > 0)
        RandSeed1 = -RandSeed1;
}

/* Returns a random number between 0 and num - 1, where num is an int */
int RandNum (int num)
{
    int temp;

    if(num == 0)
      return(0);
    temp = (int)(rand1() * num);
    if (temp == num)
       return(0);
    else
        return((int)temp);
}

// RANDOM.C END
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// SEPARATION.C BEGIN

// * * * * * * * * * * * * * * * * * * * * * * * ****
// * * * * Separation Measure Routines * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * ****

double SepPenalty(int ival)
{
       // here ival = 1, 2 or 3. being number of separate locations for speceis

       switch (ival)
       {
              case 1: return(0.5);
              case 2: return(0.2);
              case 3: return (0.0);
       }

       return(0); // This line should never be reached

} // penalty associated with separation

// * * * **** Sep Penalty 2 * * * * * * * *
// This returns the penalty for not meeting separation requirments. Feed in sepnum and current
//    separation and returns a value from 0 to 1 which is an artificial shortfall.

double SepPenalty2(int ival,int itarget)
{
       double fval;

       if (!itarget)
          return (0); // no penalty if no separation requirement
       fval = (double) ival / (double) itarget;
       if (!ival)
          fval = 1.0 /(double) itarget;

       return(1/(7*fval+0.2)-(1/7.2)); // Gives a nice hyperbole with fval = 1 return 0 and
                                       // fval = 0 or 0.1 returning almost 1

} // SepPenalty2

int ValidPU(int ipu,int isp,struct sclumps *newno,struct sspecies spec[],struct spustuff pu[],
            struct spu SM[],int imode)
{
    // Returns true if ipu is acceptable as a planning unit
    int i = rtnIdxSpecAtPu(pu,SM,ipu,isp);
    struct sclumps *pclump, *ppu;
    if (newno)
    {
       if (imode == -2)
          if (SM[i].clump == newno->clumpid)
             return(0); // This whole clump is to be removed
       for (ppu=newno;ppu;ppu=ppu->next)
       {
           if (ipu == ppu->clumpid)
           {
              if (ppu->amount < spec[isp].target2)
                 return(0);
              else
                  return(1);
           }// debugging braces
       } // ipu is on list of changed pus
    }  // Above used only when newno is not NULL
    // Find clump
    for (pclump = spec[isp].head;pclump && (SM[i].clump != pclump->clumpid);pclump= pclump->next)
        ; // scan through to find clump
    if (pclump)
    {
       if (pclump->amount <spec[isp].target2)
          return(0);
       else
           return(1);
    }
    else
    {
        if (SM[i].amount < spec[isp].target2)
           return(0);
        else
            return(1);
    }

}  // Valid PU

int CheckDistance(int i, int j,struct spustuff pu[],double squaretarget)
{
    // compare x1*x2+y1*y2 with squaretarget
    if ((pu[i].xloc-pu[j].xloc)*(pu[i].xloc-pu[j].xloc) + (pu[i].yloc-pu[j].yloc)* (pu[i].yloc-pu[j].yloc) >= squaretarget)
       return(1);
    else
        return(0);
} // Is Distant returns true if PU's are big enough distance apart

int CountSeparation(int isp,struct sclumps *newno,
                    struct spustuff pu[],struct spu SM[],typesp spec[],int imode)
{
    // imode 0 = count separation on current
    // imode 1 = count separation if ipu were included
    // imode -1 = count separation if ipu were excluded
    // The following assumes imode = 0 for starters

    struct slink{int id; struct slink *next;} *first = NULL, *second = NULL,*ptemp,*ptest;
    struct sclumps *pclump;
    struct sclumppu *ppu;
    int sepcount = 1,test;
    double targetdist;
    targetdist = spec[isp].sepdistance * spec[isp].sepdistance;

    if (targetdist == 0)
       return(3); // Shortcut if sep not apply to this species
                    // This assumes that 3 is highest sep count

    // Set up the first list
    if (imode == 1)
       if (ValidPU(newno->clumpid,isp,newno,spec,pu,SM,imode))
       {
          ptemp = (struct slink *) malloc(sizeof(struct slink));
          ptemp->id = newno->clumpid;
          ptemp->next = first;
          first = ptemp;
       }
    for (pclump = spec[isp].head;pclump;pclump = pclump->next)
        for (ppu = pclump->head;ppu;ppu= ppu->next)
            if (ValidPU(ppu->puid,isp,newno,spec,pu,SM,imode))
            {
               ptemp = (struct slink *) malloc(sizeof(struct slink));
               ptemp->id = ppu->puid;
               ptemp->next = first;
               first = ptemp;
            }  // Add all valid species bearing PU's to list
    // need to worry about added pu which is not on spec[isp].head list

    // cycle through this list
    while (first)
    {
          test = first->id;
          ptemp = first;
          first = first->next;
          free(ptemp);
          DebugFree(sizeof(struct slink));

          for (ptemp = first;ptemp;ptemp = ptemp->next)
              if (CheckDistance(ptemp->id,test,pu,targetdist))
              {
                 for (ptest=second;ptest;ptest = ptest->next)
                     if (CheckDistance(ptemp->id,ptest->id,pu,targetdist))
                     {
                        // Clear all lists
                        while (first)
                        {
                              ptemp = first;
                              first = ptemp->next;
                              free(ptemp);
                              DebugFree(sizeof(struct slink));
                        }
                        while (second)
                        {
                              ptemp = second;
                              second = ptemp->next;
                              free(ptemp);
                              DebugFree(sizeof(struct slink));
                        }
                        return(3);
                     }  // I have succeeded in finding what I'm looking for

                 ptest = (struct slink *) malloc(sizeof(struct slink));
                 ptest->id = ptemp->id;
                 ptest->next = second;
                  second = ptest;
                 sepcount = 2; // there is a separation of at least2.
                               // This should be used to cut down calls to this function

              } // I am sufficient distance from test location

          while (second)
          {
                ptemp = second;
                second = ptemp->next;
                free(ptemp);
                DebugFree(sizeof(struct slink));
          } // clear second between tests
    } // finished scanning through list. first is neccessarily empty now

    while (second)
    {
          ptemp = second;
          second = ptemp->next;
          free(ptemp);
          DebugFree(sizeof(struct slink));
    }

    return(sepcount);

} // CountSeparation


// * * * ******Make List * * * * * * * * *
// This makes a list of all the valid PUs which occur on the reserve and on which species
//    isp is present (or NULL), in the form of a slink link list

struct slink *makelist(int isp,int ipu,int puno,int R[],
                       struct sclumps *newno,struct sspecies spec[],struct spustuff pu[],struct spu SM[],int imode)
// imode: 0 : as is. -1 ipu being removed, +1 ipu being added
{
    struct sclumps *pclump;
    struct sclumppu *ppu;
    struct slink *ptemp,*head=NULL;
    int i;

    double rAmount = rtnAmountSpecAtPu(pu,SM,ipu,isp);

    if (spec[isp].target2)
    {  /* deal with clumping species differently from non-clumping*/
       if ((imode == 1) && (ValidPU(newno->clumpid,isp,newno,spec,pu,SM,imode)))
       {
          ptemp = (struct slink *) malloc(sizeof(struct slink));
          ptemp->id = newno->clumpid;
          ptemp->next = head;
          head = ptemp;
       }
       for (pclump = spec[isp].head;pclump;pclump = pclump->next)
             for (ppu = pclump->head;ppu;ppu= ppu->next)
               if (ValidPU(ppu->puid,isp,newno,spec,pu,SM,imode))
               {
                  ptemp = (struct slink *) malloc(sizeof(struct slink));
                  ptemp->id = ppu->puid;
                  ptemp->next = head;
                  head = ptemp;
               }  /* Add all valid species bearing PU's to list */
    }  /* if target2 */
    else
    {   /* non clumping species */
        if ((imode ==1) && rAmount)
        {
           ptemp = (struct slink *)malloc(sizeof(struct slink));
           ptemp->id = ipu;
           ptemp->next = head;
           head = ptemp;
        } /* deal with imode == 1 case */

        for (i=0;i<puno;i++)
            //if (((R[i] != iAvailableEquivalentZone) && (R[i] != 0) && rAmount) && !(imode == -1 && ipu == i))
            if (rAmount && !(imode == -1 && ipu == i))
            {
               ptemp = (struct slink *)malloc(sizeof(struct slink));
               ptemp->id = i;
               ptemp->next = head;
               head = ptemp;
            }
    } /* non clumping species */

  return(head);
} /* Makelist */

/* * * * ***** Sep Deal List * * * * * * * * *****/
/* This funciton is called by count separation2. It takes a link list of sites and 'deals' them
    out on to the seplist */

int SepDealList(struct slink *head, typeseplist *Dist,struct spustuff *pu,
                struct sspecies spec[],int first,int sepnum,double targetdist,
                int isp)
    /* Currsep is the current separation maximum it is 0 up to sepnum */
    /* first is only needed if maximum is at 0, sepnum is the target separation */
{
    int placefound,currtarget,bestsep=0;
    int currsep;
    struct slink *temp;

    while (head)
    {
          placefound = 0;
          currtarget = first;
          currsep = sepnum;
          do
          {
            if (CheckDistance(head->id,currtarget,pu,targetdist))
            {
               currsep++;
               if (currsep == spec[isp].sepnum-1)
               {
                  while (head)
                  {
                        temp = head->next;
                        head->next = Dist[currsep].head;
                        Dist[currsep].head = head;
                        head = temp;
                  }  /* glue remaining list on to bottom of Dist. ignoring size and tail as useless */
                  return(currsep);
               } /* Just found valid separation */
               if (Dist[currsep].head)
                  currtarget = Dist[currsep].head->id;
               else
               {
                   placefound = 1;
                   Dist[currsep].head = head;
                   Dist[currsep].tail = head;
                   Dist[currsep].size++;
                   head = head->next;
                   Dist[currsep].tail->next = NULL;
               } /* I'm at the end of the line */
            } /* Good distance */
            else
            {
                placefound = 1;
                Dist[currsep].tail->next = head;
                Dist[currsep].tail = head;
                Dist[currsep].size++;
                head = head->next;
                Dist[currsep].tail->next = NULL;
            } // bad distance
          } while (!placefound); // Doing each individual
          if (currsep > bestsep)
             bestsep = currsep;
    }

    return(bestsep);
} // Sep: Deal List



// This is a modified form of count separation where the user can specify any
// maximum separation distance rather than just assuming a sep distance of three
// ipu and newno used when imode <> 0. When counting as if ipu were added or removed
// ipu used for non-clumping and newno for clumping species

int CountSeparation2(int isp,int ipu,struct sclumps *newno,int puno,int R[],
                     struct spustuff pu[],struct spu SM[],typesp spec[],int imode)
{
    typeseplist *Dist;
    struct slink *head = NULL,*temp;
    int sepcount,bestsep = 0,i,currcol;
    double targetdist;

    targetdist = spec[isp].sepdistance * spec[isp].sepdistance;

    if (targetdist == 0)
       return(spec[isp].sepnum); /*Shortcut if sep not apply to this species */

    /* Set up array for counting separation */
    Dist = (typeseplist *) calloc(spec[isp].sepnum,sizeof(typeseplist));
    /*First scan through sites. Grab first valid and place rest in lists */
    head = makelist(isp,ipu,puno,R,newno,spec,pu,SM,imode);

    if (!head)
    {
       free(Dist);
       return(0);
    } /* There was nothing to put in the list */


    Dist[0].head = head;
    Dist[0].size = 1;
    Dist[0].tail = head;
    head = head->next;
    Dist[0].tail->next = NULL;
    if (!head)
    {
      free(Dist[0].head);
      free(Dist);
      return(1);
    }  /* There was only one item in the list */


    /* Deal out link list */
    sepcount = SepDealList(head,Dist,pu,spec,Dist[0].head->id,0,targetdist,isp);
    if (sepcount >= spec[isp].sepnum-1)
    {
        /* clean up arrays */
    /*  CheckDist(Dist,spec[isp].sepnum);*/
        for (i=0;i<spec[isp].sepnum;i++)
          while(Dist[i].head)
          {
              temp = Dist[i].head;
              Dist[i].head = Dist[i].head->next;
              free(temp);
          }
        free(Dist);
        return(spec[isp].sepnum);
    }  /* I'm at maximum separation */
    bestsep = sepcount;


    do
    {  /* The main Loop */
      for (currcol=0;Dist[currcol+1].head && currcol < spec[isp].sepnum-2;currcol++)
          ;
      if (currcol == 0)
      {
         if (Dist[0].size < spec[isp].sepnum)
         {
            while (Dist[0].head)
            {
                  temp = Dist[0].head;
                  Dist[0].head = Dist[0].head->next;
                  free(temp);
            }
            free(Dist);
            return(bestsep + 1);
         } /* cannot increase separation terminate function */
         else
         {
             temp = Dist[0].head;
             Dist[0].head = Dist[0].head->next;
             head = Dist[0].head->next;
             Dist[0].head->next = NULL;
             Dist[0].size = 1;
             Dist[0].tail = Dist[0].head;
             free(temp);
             sepcount = SepDealList(head,Dist,pu,spec,Dist[0].head->id,0,targetdist,isp);
         }
      } /* Deal with first column */
      else
      {
          if (Dist[currcol].size + currcol  < spec[isp].sepnum)
          {
             Dist[currcol-1].tail->next = Dist[currcol].head;
             Dist[currcol-1].tail = Dist[currcol].tail;
             Dist[currcol-1].tail->next = NULL;
             Dist[currcol-1].size += Dist[currcol].size;
             Dist[currcol].head = NULL;
             Dist[currcol].size = 0;
             Dist[currcol].tail = NULL;
             sepcount = 0;
          } /* list is not long enough to increase sepcount */
          else
          {
              Dist[currcol-1].tail->next = Dist[currcol].head;
              Dist[currcol-1].tail = Dist[currcol].head;
              Dist[currcol-1].size++;
              Dist[currcol].head = Dist[currcol].head->next;
              head = Dist[currcol].head->next;
              Dist[currcol].head->next = NULL;
              Dist[currcol-1].tail->next = NULL;
              Dist[currcol].tail = Dist[currcol].head;
              Dist[currcol].size = 1;
              sepcount = SepDealList(head,Dist,pu,spec,Dist[currcol].head->id,currcol,targetdist,isp);
          } /* else this column might be long enough */
      } /* Deal with columns other than the first */
      if (sepcount > bestsep)
         bestsep = sepcount;
    } while (bestsep < spec[isp].sepnum-1); /* Main loop. */

    for (i=0;i<spec[isp].sepnum;i++)
        while (Dist[i].head)
        {
              temp = Dist[i].head;
              Dist[i].head = Dist[i].head->next;
              free(temp);
        }
        free(Dist);
    return(bestsep+1);

} // CountSeparation 2

// SEPARATION.C END
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// MARXAN.C BEGIN

// This function produces a usage statement for when the command line is
// not correct. Usually when there is the wrong number (ie > 1) arguments
// passed to the program

void Usage(char *programName)
{
     fprintf(stderr,"%s usage: %s -[o] -[c] [input file name]\n",programName,programName);
}

// returns the index of the first argument that is not an option; i.e.
// does not start with a dash or a slash
void HandleOptions(int argc,char *argv[],char sInputFileName[],int *oldstyle)
{
     int i;

     if (argc>4)
     {  // if more than one commandline argument then exit
        Usage(argv[0]);
        ShowPauseExit();   // This to go into output routine
        exit(1);
     }

     for (i=1;i<argc;i++)
     {
         if (argv[i][0] == '/' || argv[i][0] == '-')
         {
            switch(argv[i][1])
            {
            case '0':
            case 'o':
            case 'O':
                *oldstyle = 1;
                break;
                        case 'C':
                        case 'c':
                        case 'S':
                        case 's':
                                marxanisslave = 1;
                                break;
            default:
                fprintf(stderr,"unknown option %s\n",argv[i]);
                break;
            }
         }
         else
             strcpy(sInputFileName,argv[i]); // If not a -option then must be input.dat name
     }  // Deal with all arguments
}

int main(int argc,char *argv[])
{
    char sInputFileName[100];
    int oldstyle;

    oldstyle = 0;

    // set default input name in case where we are using c parameter
    strcpy(sInputFileName,"input.dat");

    if (argc == 1)
       strcpy(sInputFileName,"input.dat");  // If no arguments then assume the default file name of 'input.dat'
    else
        HandleOptions(argc,argv,sInputFileName,&oldstyle);  // handle the program options

    if (MarZone(sInputFileName,oldstyle))        // Calls the main annealing unit
    {
       if (marxanisslave == 1)
          SlaveExit();
       else
           ShowPauseExit();
       return 1;
    }  // Abnormal Exit
    if (marxanisslave == 1)
       SlaveExit();
    else
        ShowPauseExit();

    return 0;
}

// MARXAN.C END

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

// handles result overflow in exp function
//int _matherr (struct _exception *a)
//{
//    a->retval = MAXINT-1;
//}

// use the prop value from the conservation feature file to set a proportion target for species
void ApplySpecProp(int spno,typesp spec[],int puno,struct spustuff pu[],struct spu SM[])
{
     // compute and set target for species with a prop value
     double totalamount;
     int isp, ipu;

     for (isp=0;isp<spno;isp++)
         if (spec[isp].prop > 0)
         {
            for (ipu = 0,totalamount = 0;ipu<puno;ipu++)
                totalamount += rtnAmountSpecAtPu(pu,SM,ipu,isp);
            spec[isp].target = totalamount * spec[isp].prop;
         }
}

// return cost of planning unit in given zone
double ReturnPuZoneCost(int ipu,int iZone)
       // parameter iZone is one base
{
       int i;
       double rCost = 0, rAddCost;

       for (i=0;i<iCostCount;i++)
       {
           rAddCost = CostValues[ipu][i] * _ZoneCost[(i*iZoneCount)+(iZone-1)];

           rCost += rAddCost;
       }

       return rCost;
}

void CalcTotalAreas(int puno,int spno,struct spustuff pu[],struct sspecies spec[],struct spu SM[])
{
     int ipu, i, ism, isp, *TotalOccurrences, *TO_2, *TO_3;
     double *TA_2, *TA_3;
     FILE* TotalAreasFile;

     TotalAreas = (double *) calloc(spno,sizeof(double));

     TotalOccurrences = (int *) calloc(spno,sizeof(int));
     TO_2 = (int *) calloc(spno,sizeof(int));
     TO_3 = (int *) calloc(spno,sizeof(int));
     TA_2 = (double *) calloc(spno,sizeof(double));
     TA_3 = (double *) calloc(spno,sizeof(double));

     for (i=0;i<spno;i++)
     {
         TotalAreas[i] = 0;

         TO_2[i] = 0;
         TO_3[i] = 0;
         TA_2[i] = 0;
         TA_3[i] = 0;
     }

     for (ipu=0;ipu<puno;ipu++)
         if (pu[ipu].richness)
            for (i=0;i<pu[ipu].richness;i++)
            {
                ism = pu[ipu].offset + i;
                isp = SM[ism].spindex;

                TotalAreas[isp] += SM[ism].amount;

                if (iVerbosity > 1)
                {
                   TotalOccurrences[isp]++;

                   if (pu[ipu].status == 2)
                   {
                      TO_2[isp]++;
                      TA_2[isp] += SM[ism].amount;
                   }

                   if (pu[ipu].status == 3)
                   {
                      TO_3[isp]++;
                      TA_3[isp] += SM[ism].amount;
                   }
                }
            }

     if (iVerbosity > 3)
     {
        TotalAreasFile = fopen("MarZoneTotalAreas.csv","w");
        fprintf(TotalAreasFile,"spname,spindex,totalarea,reservedarea,excludedarea,targetarea,totalocc,reservedocc,excludedocc,targetocc\n");
        for (i=0;i<spno;i++)
            fprintf(TotalAreasFile,"%i,%i,%g,%g,%g,%g,%i,%i,%i,%i\n"
                                  ,spec[i].name,i,TotalAreas[i],TA_2[i],TA_3[i],spec[i].target
                                  ,TotalOccurrences[i],TO_2[i],TO_3[i],spec[i].targetocc);
        fclose(TotalAreasFile);
     }

     free(TotalOccurrences);
     free(TO_2);
     free(TO_3);
     free(TA_2);
     free(TA_3);
}

void ComputeZoneConnectivitySum(double **ZCS,int puno,int R[])
{
     int i;
     double fcost, rResult, rZoneConnectionCost;
     struct sneighbour *p;

     for (i=0;i<puno;i++)
     {
       // fixed cost for ipu is between this zone and itself
       ZCS[R[i]-1][R[i]-1] += connections[i].fixedcost;

       // traverse connections for this ipu
       p = connections[i].first;
       while (p)
       {
             if (p->nbr > i) // avoid double counting connnections
                if (R[i] != R[p->nbr]) // ignore internal connections within a zone
                {
                   // connections are symmetric
                   ZCS[R[i]-1][R[p->nbr]-1] += p->cost;
                   ZCS[R[p->nbr]-1][R[i]-1] += p->cost;
                }

             p = p->next;
       }
     }
}

void OutputZoneConnectivitySum(int puno,int R[],char savename[],int imode)
{
     FILE *fp;
     int i,j;
     char sDelimiter[20];
     double **ZCS;

     ZCS = (double **) calloc(iZoneCount,sizeof(double *));
     for (i=0;i<iZoneCount;i++)
         ZCS[i] = (double *) calloc(iZoneCount,sizeof(double));
     for (i=0;i<iZoneCount;i++)
         for (j=0;j<iZoneCount;j++)
             ZCS[i][j] = 0;

     ComputeZoneConnectivitySum(ZCS,puno,R);

     if (imode > 1)
        strcpy(sDelimiter,",");
     else
         strcpy(sDelimiter,"   ");

     fp = fopen(savename,"w");
     if (!fp)  ShowErrorMessage("Cannot save output to %s \n",savename);

     // write header row
     if (imode > 1)
        fprintf(fp,"\"Zone_Connectivity_Sum\"");
     for (i=0;i<iZoneCount;i++)
        fprintf(fp,",\"%s\"",Zones[i].name);
     fprintf(fp,"\n");

     // write a data row for each zone
     for (i=0;i<iZoneCount;i++)
     {
         fprintf(fp,"\"%s\"",Zones[i].name);
         for (j=0;j<iZoneCount;j++)
             fprintf(fp,",%f",ZCS[i][j]);
         fprintf(fp,"\n");
     }

     fclose(fp);

     for (i=0;i<iZoneCount;i++)
         free(ZCS[i]);
     free(ZCS);
}

int DumpAsymmetricConnectionFile(int puno,struct sconnections connections[],struct spustuff pu[],struct sfname fnames)
{
    #ifdef ASYMCON
    int i;
    FILE *fp;
    char *writename;
    struct sneighbour *p;

    writename = (char *) calloc(22 + strlen(fnames.outputdir)+2, sizeof(char));
    strcpy(writename,fnames.outputdir);
    strcat(writename,"debug_asymmetric_connection.csv");
    if ((fp = fopen(writename,"w"))==NULL)
    {
       ShowGenProg("Warning: Cannot create file %s",writename);
       free(writename);
       return(0);
    }
    free(writename);

    fprintf(fp,"idA,idB,connectionorigon\n");
    for (i=0;i<puno;i++)
    {
        for (p = connections[i].first;p;p=p->next)
            fprintf(fp,"%i,%i,%i,%lf\n",pu[i].id,pu[p->nbr].id,p->connectionorigon,p->cost);
    }

    fclose(fp);
    #endif
}

void OutputTotalAreas(int puno,int spno,struct spustuff pu[],struct sspecies spec[],struct spu SM[],char savename[],int iOutputType)
{
     int ipu, i, ism, isp, *TotalOccurrences, *TO_2, *TO_3;
     double *TotalAreas, *TA_2, *TA_3;
     FILE* TotalAreasFile;
     char sDelimiter[20];

     TotalOccurrences = (int *) calloc(spno,sizeof(int));
     TO_2 = (int *) calloc(spno,sizeof(int));
     TO_3 = (int *) calloc(spno,sizeof(int));
     TotalAreas = (double *) calloc(spno,sizeof(double));
     TA_2 = (double *) calloc(spno,sizeof(double));
     TA_3 = (double *) calloc(spno,sizeof(double));

     for (i=0;i<spno;i++)
     {
         TotalAreas[i] = 0;
         TA_2[i] = 0;
         TA_3[i] = 0;
     }

     for (ipu=0;ipu<puno;ipu++)
         if (pu[ipu].richness)
            for (i=0;i<pu[ipu].richness;i++)
            {
                ism = pu[ipu].offset + i;
                isp = SM[ism].spindex;

                TotalOccurrences[isp]++;
                TotalAreas[isp] += SM[ism].amount;

                if (pu[ipu].status == 2)
                {
                   TO_2[isp]++;
                   TA_2[isp] += SM[ism].amount;
                }

                if (pu[ipu].status == 3)
                {
                   TO_3[isp]++;
                   TA_3[isp] += SM[ism].amount;
                }
            }

     if (iOutputType > 1)
        strcpy(sDelimiter,",");
     else
         strcpy(sDelimiter,"    ");

     TotalAreasFile = fopen(savename,"w");
     fprintf(TotalAreasFile,"spname%stotalarea%sreservedarea%sexcludedarea%stargetarea%stotalocc%sreservedocc%sexcludedocc%stargetocc\n"
                           ,sDelimiter,sDelimiter,sDelimiter,sDelimiter,sDelimiter,sDelimiter,sDelimiter,sDelimiter);
     for (i=0;i<spno;i++)
         fprintf(TotalAreasFile,"%i%s%g%s%g%s%g%s%g%s%i%s%i%s%i%s%i\n"
                               ,spec[i].name,sDelimiter,TotalAreas[i],sDelimiter,TA_2[i],sDelimiter,TA_3[i],sDelimiter
                               ,spec[i].target,sDelimiter,TotalOccurrences[i],sDelimiter,TO_2[i],sDelimiter,TO_3[i],sDelimiter,spec[i].targetocc);
     fclose(TotalAreasFile);

     free(TotalOccurrences);
     free(TO_2);
     free(TO_3);
     free(TotalAreas);
     free(TA_2);
     free(TA_3);
}


