/*****************************************************************
 //
 //      	        The Dyna_CLUE model
 //
 //
 //                    -calculation modules-
 //
 //
 //                        v Dyna-CLUE
 //
 //	                  Development:
 //                        Peter Verburg
 //     														 				    The CLUE group				 		 *
 //			    27-2-2011
 //		       CLUE_logist2dneigh.CPP
 //
 //
 //                     all rights: Peter Verburg
 //
 //                      peter.verburg@ivm.vu.nl
 //
 /********************************************************************/

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
#include "finish.h"
#include "error.h"
void all_init(void) {
	int i, dimensionerror;

	dimensionerror = 0;
	gettime (&ti);
	fprintf(flog, "-- Start of simulation at: %2d:%02d:%02d.%02d -- \n\n\n",
			ti.ti_hour, ti.ti_min, ti.ti_sec, ti.ti_hund);

	fprintf(flog, " demand file used: %s\n region file used: %s\n", demd, park);

	ifstream f1;
	f1.open(F_INIT);
	if (!f1) {
		errorbox->ShowModal();
		exit(0);
	}
	f1 >> rncov >> rnreg >> rnfact >> rnatt;
	fprintf(flog,
			" number of cover types: %d\n number of regions: %d\n number of factors in 1 equation: %d\n total number of factors: %d\n",
			rncov, rnreg, rnfact, rnatt);
	f1 >> rnr >> rnc;
	fprintf(flog, " grid specification (rows, columns): %d, %d\n", rnr, rnc);
	if ((rncov > NCOV) || (rnreg > NREG) || (rnfact > NFACT) || (rnatt > NATT)
			|| (rnr > NR) || (rnc > NC))
		dimensionerror = 1;
	f1 >> cellsize;
	fprintf(flog, " cellsize: %15.10lf\n", cellsize);
	f1 >> xllcorner >> yllcorner;
	fprintf(flog, " xll and yll coordinates: %f, %f", xllcorner, yllcorner);
	fprintf(flog, "\n land use/demand use types: ");
	maxcovdem = 0;
	for (i = 0; i < rncov; i++) {
		f1 >> covdem[i];
		fprintf(flog, "%d ", covdem[i]);
		if (covdem[i] > maxcovdem)
			maxcovdem = covdem[i];
	}
	fprintf(flog, "\n number of demand types: %d", (maxcovdem + 1));
	fprintf(flog, "\n elasticity of land use types: ");
	for (i = 0; i < rncov; i++) {
		f1 >> stat[i];
		fprintf(flog, "%3.2f ", stat[i]);
	}
	f1 >> itermode >> demdiff >> demdiffmax;
	fprintf(flog, "\n iteration mode: ");
	if (itermode == 0)
		fprintf(flog, "percentage");
	else
		fprintf(flog, "absolute");
	fprintf(flog, "\n maximum average difference in demand allowed: %f",
			demdiff);
	fprintf(flog, "\n maximum individual difference in demand allowed: %f",
			demdiffmax);
	f1 >> start >> end;
	fprintf(flog, "\n start: %d end: %d \n", start, end);
	f1 >> nochange;
	fprintf(flog, " changing driver numbers: ");
	for (i = 0; i < nochange; i++) {
		f1 >> grchange[i];
		fprintf(flog, "%d ", grchange[i]);
	}
	f1 >> arcview;
	fprintf(flog, "\n output switch: %d\n", arcview);
	f1 >> diffregregi;
	fprintf(flog, " region specific regression switch: %d\n", diffregregi);
	f1 >> agemode;
	fprintf(flog, " random calculation of land use history: ");
	if (agemode > 0) {
		f1 >> startage;
		if (agemode == 2) {
			randomize();
			fprintf(flog, "randomizer ");
		}
		fprintf(flog, "on   - maximum age: %d\n", startage);
	} else
		fprintf(flog, "off\n");
	f1 >> influence;
	fprintf(flog, " neighborhood functions: ");
	if (influence > 0) {
		fprintf(flog, "on\n");
		if ((rnatt + rncov) > NATT)
			dimensionerror = 1;
	} else
		fprintf(flog, "off\n");
	fprintf(flog, " location specific addition: ");
	f1 >> locationfactor;
	if (locationfactor > 0) {
		fprintf(flog, "on, weights: ");
		for (i = 0; i < rncov; i++) {
			f1 >> locationweight[i];
			fprintf(flog, "%3.2f ", locationweight[i]);
			if ((rnatt + rncov) > NATT)
				dimensionerror = 1;
		}
	} else
		fprintf(flog, "off");
	f1 >> seed;
	if ((seed < 0.001) || (seed > 0.9))
		seed = 0.05;
	fprintf(flog, "\nseed for iteration: %f ", seed);

	if ((influence > 0) && (locationfactor > 0)
			&& ((rnatt + rncov + rncov) > NATT))
		dimensionerror = 1;
	if (dimensionerror > 0) {
		fprintf(flog,
				"\n MAXIMUM DIMENSIONS of attribute file EXCEEDED -- Exit");
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
	f1.close();
	gridsize = ((sqrt(cellsize)) * 100);
	fprintf(flog, "\n gridsize: %f \n\n", gridsize);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void set_oldco(void) {
	FILE *f7;
	char f_oldco[20];
	int i, j, k, nl, dum;
	float totalno, cum[NCOV];

	for (i = 0; i < rncov; i++)
		cum[i] = 0;
	totalno = 0;
	if (arcview == 3)
		sprintf(f_oldco, "cov_all.%d.asc", (year - 1));
	else
		sprintf(f_oldco, "cov_all.%d", (year - 1));
	if ((f7 = fopen(f_oldco, "r")) == NULL) {
		fprintf(flog, "\n %s not found \n", f_oldco);
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
	rewind(f7);
	if (arcview > 0) {
		nl = 0;
		while (nl < 6) {
			dum = fgetc(f7);
			if (dum == '\n')
				nl++;
		}
	}
	for (j = 0; j < rnr; j++) {
		for (k = 0; k < rnc; k++) {
			fscanf(f7, "%d", &mat_oldco[j][k]);
			for (i = 0; i < rncov; i++) {
				if (i == mat_oldco[j][k]) {
					cum[i]++;
					totalno++;
				}
			}
		}
	}
	fclose(f7);
	fprintf(flog, "\n");
	for (i = 0; i < rncov; i++) {
		ratio[i] = cum[i] / totalno;
		fprintf(flog, "fraction occupied by land use type %d: %f\n", i,
				ratio[i]);
		if (ratio[i] == 0)
			ratio[i] = 9;
	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void load_reg(void) {

	FILE *f2;
	char F_ALREG[11];
	int i, j, k, l, tempcov;

	fprintf(flog, "\n regression parameters: \n");
	sprintf(F_ALREG, "alloc1.reg");
	if ((f2 = fopen(F_ALREG, "r")) == NULL) {
		fprintf(flog, "no file: alloc1.reg");
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
	if (diffregregi >= 1)
		l = rnreg;
	else
		l = 1;
	for (i = 0; i < l; i++) {
		for (j = 0; j < rncov; j++) {
			fscanf(f2, "%d", &tempcov);
			fscanf(f2, "%lf", &constant[i][tempcov]);
			fscanf(f2, "%d", &no_fact[i][tempcov]);
			for (k = 0; k < no_fact[i][tempcov]; k++)
				fscanf(f2, "%f%d", &fact[i][tempcov][k], &attr[i][tempcov][k]);
			fprintf(flog, "loc:  %d  %lf  %d \n", tempcov, constant[i][tempcov],
					no_fact[i][tempcov]);
		}
	}
	fclose(f2);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void load_reg2(void) {

	FILE *f2;
	int i, j, k, l, tempcov;

	fprintf(flog, "\n regression parameters neighborhood functions: \n");
	if ((f2 = fopen("alloc2.reg", "r")) == NULL) {
		fprintf(flog, "no file: alloc2.reg");
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
	if (diffregregi >= 1)
		l = rnreg;
	else
		l = 1;
	for (i = 0; i < l; i++) {
		for (j = 0; j < rncov; j++) {
			fscanf(f2, "%d", &tempcov);
			fscanf(f2, "%lf", &constant2[i][tempcov]);
			fscanf(f2, "%d", &no_fact2[i][tempcov]);
			for (k = 0; k < no_fact2[i][tempcov]; k++) {
				fscanf(f2, "%f%d", &fact2[i][tempcov][k],
						&attr2[i][tempcov][k]);
				if (attr2[i][tempcov][k] >= rncov) {
					fprintf(flog,
							"/n/n ERROR: attribute value in neighborhood regression (alloc2.reg) exceeds number of land covers");
					fclose (flog);
					errorbox->ShowModal();
					exit(0);
				}
				attr2[i][tempcov][k] += rnatt;
			}
			fprintf(flog, "nbh:  %d  %lf  %d  \n", tempcov,
					constant2[i][tempcov], no_fact2[i][tempcov]);
		}
	}
	fclose(f2);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void load_locationfactor(void) {
	FILE *f3;
	int i, j, k, nl, dum;
	float l;
	char filename[20];

	for (i = 0; i < rncov; i++) {
		if (arcview == 3)
			sprintf(filename, "locspec%d.fil.asc", i);
		else
			sprintf(filename, "locspec%d.fil", i);
		if ((f3 = fopen(filename, "r")) == NULL) {
			fprintf(flog, " locspec* file missing \n");
			fclose (flog);
			errorbox->ShowModal();
			exit(0);
		}
		if (arcview > 0) {
			nl = 0;
			while (nl < 6) {
				dum = fgetc(f3);
				if (dum == '\n')
					nl++;
			}
		}
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++) {
				fscanf(f3, "%f ", &l);
				if (influence == 0)
					mat_at[rnatt + i][j][k] = l;
				else
					mat_at[rnatt + rncov + i][j][k] = l;
			}
		}
		fclose(f3);
	}
}
/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void read_allowed(void) {
	int i, j, countbottom;
	FILE *f9;

	bottomup = 0;
	if ((f9 = fopen("allow.txt", "r")) == NULL) {
		fprintf(flog, "no file: allow.txt");
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
	fprintf(flog, "\n allowed changes matrix\n");
	for (i = 0; i < rncov; i++) {
		countbottom = 0;
		for (j = 0; j < rncov; j++) {
			fscanf(f9, "%d", &allowed[i][j]);
			if (allowed[i][j] > 1000) {
				bottomup = 1;
				countbottom++;
				if (countbottom > 1) {
					fprintf(flog,
							"\n\n error reading allow.txt: double specification of autonomous change");
					fclose (flog);
					errorbox->ShowModal();
					exit(0);
				}
			}
			fprintf(flog, "%d ", allowed[i][j]);
		}
		fprintf(flog, "\n");
	}
	if (bottomup == 1)
		fprintf(flog,
				"\n - demand may be modified by bottom up interactions as specified in the change matrix - \n");
	fclose(f9);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void make_mat(void) {
	int i, j, result, totnatt;
	result = 1;

	totnatt = rnatt - allowmin;
	if ((locationfactor > 0) && (metamod == 0))
		totnatt += rncov;
	if (influence > 0)
		totnatt += rncov;
	for (j = 0; j < totnatt; j++) {
		mat_at[j] = (fvector*) calloc(rnr, sizeof(fvector));
		if (mat_at == NULL)
			result = 0;
		for (i = 0; i < rnr; i++) {
			mat_at[j][i] = (fvector) calloc(rnc, sizeof(MATFLOAT));
			if (mat_at[j][i] == NULL)
				result = 0;
		}
	}
	for (j = 0; j < rncov; j++) {
		mat_co[j] = (fvector*) calloc(rnr, sizeof(fvector));
		if (mat_co == NULL)
			result = 0;
		for (i = 0; i < rnr; i++) {
			mat_co[j][i] = (fvector) calloc(rnc, sizeof(MATFLOAT));
			if (mat_co[j][i] == NULL)
				result = 0;
		}
	}

	mat_newco = (vector*) calloc(rnr, sizeof(vector));
	if (mat_newco == NULL)
		result = 0;
	for (i = 0; i < rnr; i++) {
		mat_newco[i] = (vector) calloc(rnc, sizeof(MATELEMS));
		if (mat_newco[i] == NULL)
			result = 0;
	}

	region = (vector*) calloc(rnr, sizeof(vector));
	if (region == NULL)
		result = 0;
	for (i = 0; i < rnr; i++) {
		region[i] = (vector) calloc(rnc, sizeof(MATELEMS));
		if (region[i] == NULL)
			result = 0;

	}
	mat_oldco = (vector*) calloc(rnr, sizeof(vector));
	if (mat_oldco == NULL)
		result = 0;
	for (i = 0; i < rnr; i++) {
		mat_oldco[i] = (vector) calloc(rnc, sizeof(MATELEMS));
		if (mat_oldco[i] == NULL)
			result = 0;

	}

	mat_age = (vector*) calloc(rnr, sizeof(vector));
	if (mat_age == NULL)
		result = 0;
	for (i = 0; i < rnr; i++) {
		mat_age[i] = (vector) calloc(rnc, sizeof(MATELEMS));
		if (mat_age[i] == NULL)
			result = 0;

	}

	if (result == 0) {
		fprintf(flog, "Make_mat out of memory\n");
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void scgr_change(void) {
	FILE *pf1, *pf1a;
	int i, j, k, nl, dum;
	float c, l;
	char inname[20], outname[20];
	fprintf(flog, "\n update of driving factor files for drivers: ");
	for (i = 0; i < nochange; i++) {
		if ((grchange[i] >= allowmin) || (metamod == 0)) {
			if (arcview == 3) {
				sprintf(inname, "sc1gr%d.%d.asc", grchange[i], year);
				sprintf(outname, "sc1gr%d.fil.asc", grchange[i]);
			} else {
				sprintf(inname, "sc1gr%d.%d", grchange[i], year);
				sprintf(outname, "sc1gr%d.fil", grchange[i]);
			}
			fprintf(flog, "%d ", grchange[i]);
			if ((pf1 = fopen(inname, "r")) == NULL) {
				fprintf(flog, "\n\n\n\n NO NEW FILE %s \n\n", inname);
				fclose (flog);
				errorbox->ShowModal();
				exit(0);
			}
			if ((pf1a = fopen(outname, "w")) == NULL) {
				fprintf(flog, "\n\n\n\n\n ERROR: %s \n\n\n\n\n", outname);
				fclose (flog);
				errorbox->ShowModal();
				exit(0);
			}
			while ((c = getc(pf1)) != EOF)
				putc(c, pf1a);
			fclose(pf1);
			fclose(pf1a);
			if ((pf1a = fopen(outname, "r")) == NULL) {
				fprintf(flog, "\n\n\n\n\n ERROR: %s \n\n\n\n\n", outname);
				fclose (flog);
				errorbox->ShowModal();
				exit(0);
			}
			if (arcview > 0) {
				nl = 0;
				while (nl < 6) {
					dum = fgetc(pf1a);
					if (dum == '\n')
						nl++;
				}
			}
			for (j = 0; j < rnr; j++) {
				for (k = 0; k < rnc; k++) {
					fscanf(pf1a, "%f ", &l);
					mat_at[(grchange[i] - allowmin)][j][k] = l;
				}
			}
			fclose(pf1a);
		}
	}
	fprintf(flog, "\n");
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void load_grid(void) {
	FILE *f3;
	int i, j, k, nl, dum;
	float l;
	char filename[20];

	for (i = (allowmin); i < rnatt; i++) {
		if (arcview == 3)
			sprintf(filename, "sc1gr%d.fil.asc", i);
		else
			sprintf(filename, "sc1gr%d.fil", i);
		if ((f3 = fopen(filename, "r")) == NULL) {
			fprintf(flog, " sc1gr* file missing \n");
			fclose (flog);
			errorbox->ShowModal();
			exit(0);
		}
		if (arcview > 0) {
			nl = 0;
			while (nl < 6) {
				dum = fgetc(f3);
				if (dum == '\n')
					nl++;
			}
		}
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++) {
				fscanf(f3, "%f ", &l);
				mat_at[(i - allowmin)][j][k] = l;
			}
		}
		fclose(f3);
	}
}
/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void load_region(void) {
	char F_REGION[20];
	FILE *f4;
	int i, j, m, tempregion, nl, dum;

	for (m = 0; m < rnreg; m++)
		nocells[m] = 0;

	sprintf(F_REGION, "%s", park);

	if ((f4 = fopen(F_REGION, "r")) == NULL) {
		fprintf(flog, "no file: %s", F_REGION);
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
	if (arcview > 0) {
		nl = 0;
		while (nl < 6) {
			dum = fgetc(f4);
			if (dum == '\n')
				nl++;
		}
	}
	for (i = 0; i < rnr; i++) {
		for (j = 0; j < rnc; j++) {
			fscanf(f4, "%d", &region[i][j]);
			if (region[i][j] < -9900)
				tempregion = region[i][j] + 9998;
			else
				tempregion = region[i][j];
			if (tempregion > -1)
				nocells[tempregion]++;
		}
	}
	for (m = 0; m < rnreg; m++)
		fprintf(flog, "\n number of cells in region %d: %d\n", m, nocells[m]);
	fclose(f4);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void demand_read(void) {
	int i, j, l, nyear, tempreg;
	FILE *in;

	if (diffregregi < 2)
		tempreg = rnreg;
	else
		tempreg = 1;
	if ((in = fopen(demd, "r")) == NULL) {
		errorbox->ShowModal();
		fprintf(flog, "/n no demand file");
		fclose (flog);
		exit(0);
	}
	fscanf(in, "%d", &nyear);
	for (l = 0; l < tempreg; l++) {
		for (i = 0; i < nyear; i++) {
			for (j = 0; j < rncov; j++)
				fscanf(in, "%lf", &demand[l][j][i]);
		}
	}
	fclose(in);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void demand_dir(void) {
	int i, j, tempreg;

	/* determine if demand increases or decreases */
	if (diffregregi < 2)
		tempreg = rnreg;
	else
		tempreg = 1;
	for (j = 0; j < tempreg; j++)
		nostable[j] = 0;
	for (i = 0; i < rncov; i++) {
		fprintf(flog, "cover type: %d\ ", i);
		for (j = 0; j < tempreg; j++) {
			if (demand[j][i][year - 1] < demand[j][i][year])
				dem_dir[j][i] = 1;
			if (demand[j][i][year - 1] == demand[j][i][year]) {
				dem_dir[j][i] = 0;
				nostable[j]++;
			}
			if (demand[j][i][year - 1] > demand[j][i][year])
				dem_dir[j][i] = -1;
			fprintf(flog,
					" demand direction for region %d is %d; demand:\t%8.1lf\n",
					j, dem_dir[j][i], demand[j][i][year]);
			if ((demand[j][i][year] / (nocells[j] * cellsize)) < 0.001)
				speed[j][covdem[i]] = 0.01;
			else
				speed[j][covdem[i]] = seed;
		}
		fprintf(flog, "\n");
	}
	fprintf(flog, "\n");
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void init_iter(void) {
	int i, j, tempreg;

	if (diffregregi < 2)
		tempreg = rnreg;
	else
		tempreg = 1;
	for (i = 0; i < rncov; i++) {
		for (j = 0; j < tempreg; j++)
			elas[j][i] = 0;
	}
	loop = 0;
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void calc_reg(void) {
	int i, j, k, m, reg, nl, dum, temploc;
	float temp, temp2;
	FILE *fa7;
	char f_prob[20];

	if (influence > 0)
		temploc = rncov;
	else
		temploc = 0;

	if (metamod == 1) {
		for (i = 0; i < rncov; i++) {
			if (arcview == 3)
				sprintf(f_prob, "prob1_%d.%d.asc", i, year);
			else
				sprintf(f_prob, "prob1_%d.%d", i, year);
			if ((fa7 = fopen(f_prob, "r")) == NULL) {
				fprintf(flog, "\n %s not found \n", f_prob);
				fclose (flog);
				errorbox->ShowModal();
				exit(0);
			}
			rewind(fa7);
			if (arcview > 0) {
				nl = 0;
				while (nl < 6) {
					dum = fgetc(fa7);
					if (dum == '\n')
						nl++;
				}
			}
			for (j = 0; j < rnr; j++) {
				for (k = 0; k < rnc; k++)
					fscanf(fa7, "%f", &mat_co[i][j][k]);
			}
			fclose(fa7);
		}
	}

	/* just calculate the probability as determined by the regression equations, put in mat_co */

	if ((metamod == 0) || (influence > 0)) {
		for (i = 0; i < rncov; i++) {
			for (j = 0; j < rnr; j++) {
				for (k = 0; k < rnc; k++) {
					if (region[j][k] < 0)
						mat_co[i][j][k] = -9999;
					else {
						if (diffregregi >= 1)
							reg = region[j][k];
						else
							reg = 0;
						if (metamod == 0) {
							temp = constant[reg][i];
							for (m = 0; m < no_fact[reg][i]; m++)
								temp += (fact[reg][i][m]
										* mat_at[attr[reg][i][m]][j][k]);
							if (temp > 700) {
								fprintf(flog,
										"\n\n error: regression cannot be calculated due to large value in cell %d,%d for land cover %d \n",
										j, k, i);
								fclose (flog);
								errorbox->ShowModal();
								exit(0);
							}
							mat_co[i][j][k] = (exp(temp) / (1 + exp(temp)));
						}

						if ((influence == 1) && (allprob == 0)) // neighborhood calculation on //
								{
							temp2 = constant2[reg][i];
							for (m = 0; m < no_fact2[reg][i]; m++)
								temp2 +=
										(fact2[reg][i][m]
												* mat_at[(attr2[reg][i][m]
														- allowmin)][j][k]);
							if (temp2 > 700) {
								fprintf(flog,
										"\n\n error: nbh regression cannot be calculated due to large value in cell %d,%d for land cover %d \n",
										j, k, i);
								fclose (flog);
								errorbox->ShowModal();
								exit(0);
							}

							mat_co[i][j][k] =
									(((1 - neighweight[i]) * mat_co[i][j][k])
											+ (neighweight[i]
													* ((exp(temp2)
															/ (1 + exp(temp2))))));
						}

						if (metamod == 0) // location specific addition //
								{

							if (locationfactor > 0) {
								mat_co[i][j][k] +=
										(mat_at[(rnatt + temploc + i)][j][k]
												* locationweight[i]);
								if (mat_co[i][j][k] > 1.2)
									mat_co[i][j][k] = 1.2;
							}
						}

					}
				}
			}
		}
	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void free_mat(void) {
	int i, j, totnatt;

	totnatt = rnatt - allowmin;
	if ((locationfactor > 0) && (metamod == 0))
		totnatt += rncov;
	if (influence > 0)
		totnatt += rncov;
	for (i = 0; i < totnatt; i++) {
		for (j = 0; j < rnr; j++)
			free (mat_at[i][j]);
		free (mat_at[i]);
	}
	for (i = 0; i < rncov; i++) {
		for (j = 0; j < rnr; j++)
			free (mat_co[i][j]);
		free (mat_co[i]);
	}
	for (j = 0; j < rnr; j++) {
		free (mat_newco[j]);
		free (region[j]);
		free (mat_age[j]);
		free (mat_oldco[j]);
	}
	free (mat_newco);
	free (region);
	free (mat_age);
	free (mat_oldco);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void calc_change_ch(void) {
	int i, j, k, maxcov, stable, oldcov, tempreg, tempallow;
	float max, tempmax;

	for (j = 0; j < rnr; j++) {
		for (k = 0; k < rnc; k++) {

			if ((region[j][k] > -9999) && (region[j][k] < -9900))
				mat_newco[j][k] = mat_oldco[j][k];
			if (region[j][k] > -9900) {
				oldcov = mat_oldco[j][k];
				max = -3;
				stable = 0;
				if (diffregregi > 1)
					tempreg = 0;
				else
					tempreg = region[j][k];
				for (i = 0; i < rncov; i++) {
					if ((oldcov == i) && (stat[i] < 1))
						tempmax =
								(mat_co[i][j][k] + elas[tempreg][i] + stat[i]);
					else
						tempmax = (mat_co[i][j][k] + elas[tempreg][i]);
					if ((dem_dir[tempreg][i] == -1) && (oldcov != i)
							&& (stat[i] == 1))
						tempmax = -3;
					if (allowed[oldcov][i] != 1) {
						tempallow = 1;
						if (allowed[oldcov][i] == 0)
							tempmax = -3;
						if ((allowed[oldcov][i] > 1)
								&& (allowed[oldcov][i] < 100))
							tempallow =
									mat_at[(allowed[oldcov][i] - allowmin)][j][k];
						if (tempallow == 0)
							tempmax = -3;
						if (abs(allowed[oldcov][i]) > 100)
							tempallow = allowed[oldcov][i];

						/* in case a certain conversion is restricted to the age of the land use at the location */

						if ((tempallow > 100) && (tempallow < 1000)
								&& (mat_age[j][k] < (tempallow - 100)))
							tempmax = -3;
						if ((tempallow < -100)
								&& (mat_age[j][k] >= ((abs(tempallow)) - 100)))
							tempmax = -3;
						if ((tempallow > 1000)
								&& (mat_age[j][k] < (tempallow - 1000)))
							tempmax = -3;
						if (tempallow > 1000)
							bottomup = 1;
					}
					if (tempmax > max) {
						max = tempmax;
						maxcov = i;
					}
					if ((dem_dir[tempreg][i] > -1) && (oldcov == i)
							&& (stat[i] == 1))
						stable = 1;
				}
				if ((max == -3) && (stable == 0)) {
					fprintf(flog, "\n too many constraints defined  \n");
					fclose (flog);
					errorbox->ShowModal();
					exit(0);
				}
				if (nostable[tempreg] == rncov)
					stable = 1;
				if (stable == 0)
					mat_newco[j][k] = maxcov;
				if (stable == 1)
					mat_newco[j][k] = oldcov;
			}
			if (region[j][k] <= -9999)
				mat_newco[j][k] = -9999;
		}

	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void alloc_sort(void) {
	float param[NR][NC];
	int i, j, k, m;

	int number_of_values = sizeof(param) / sizeof(float);

	float *ptrL1;    // Level 1 pointer (regular indirection)
	float **ptrL2;   // Level 2 pointer (double indirection)

	// First level of indirection. Allows us to pretend the array is flat.

	ptrL1 = &param[0][0];

	// For demonstration purposes, I will populate the array with all zeros, then
	// drop in values 1 through 5, so those will be the 'highest' value in the array.
	// Since we know which places we seeded with which value, this way we can easily
	// verify if the outcome is what we expected.

	for (i = 0; i < rncov; i++) {

		for (m = 0; m < number_of_values; m++)
			*(ptrL1 + m) = 0;

		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++) {
				if (region[j][k] > -9900) {
					param[j][k] = mat_co[i][j][k];
					mat_newco[j][k] = mat_oldco[j][k];
				}
			}
		}

		// Double indirection is in order, because you are not interested only in finding the
		// top values, but in the position of those values in the array. Only by adding an
		// extra indirection and doing sort on addresses instead of the values, can we
		// keep track of how the sorting moved things around.

		// Second level of indirection.

		ptrL2 = (float**) malloc(number_of_values * sizeof(float*));
		if (ptrL2 == 0) {
			fprintf(flog, "ERROR: Insufficient memory");
			fclose (flog);
			errorbox->ShowModal();
			exit(0);
		}

		// Populate the second-level array with pointers to the values in the original array.

		for (m = 0; m < number_of_values; m++)
			*(ptrL2 + m) = ptrL1 + m;

		// Now we call qsort to reorder the pointers in the second-level
		// array. Original param array is in no way reordered.

		qsort(ptrL2, number_of_values, sizeof(int**), sorting_rule);

		// Show that you have found the 50 highest values by printing them to standard output.

		for (m = 0; m < 50; m++) {
			int array_index = (int) (*(ptrL2 + m) - ptrL1);

			// Use the stride of the array to translate flat sequence
			// numbers to their respective indices for  x and y

			int x = array_index / (rnc);
			int y = (array_index - (x * rnc));
			mat_newco[x][y] = i;
			fprintf(flog,
					"Value %d at memory position %p has array position %d and maps to param[%d][%d]\n",
					**(ptrL2 + m), *(ptrL2 + m), array_index, x, y);
		}

		free(ptrL2);
		ptrL2 = NULL;
	}

}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void autonomous_change(void) {
	int i, j, k, oldcov, tempallow, countbottom;
//        int tempyear,tempreg;
	for (j = 0; j < rnr; j++) {
		for (k = 0; k < rnc; k++) {
			if (region[j][k] > -9999) {
				oldcov = mat_oldco[j][k];
				countbottom = 0;
//                            if (diffregregi != 1)
//                                tempreg = 0;
//                            else
//                            {
//                                tempreg = region[j][k];
//                                if ((region[j][k] > -9999) && (region[j][k] < -9900))
//                                   tempreg = (region[j][k] + 9998);
//                            }

				for (i = 0; i < rncov; i++) {
					if (allowed[oldcov][i] != 1) {
						tempallow = 1;
						if ((allowed[oldcov][i] > 1)
								&& (allowed[oldcov][i] < 100))
							tempallow =
									mat_at[(allowed[oldcov][i] - allowmin)][j][k];
						if (allowed[oldcov][i] > 1000)
							tempallow = allowed[oldcov][i];
						if (tempallow > 1000) {
							countbottom++;
							if (countbottom > 1) {
								fprintf(flog,
										"\n error: same land cover cannot change autonomous in two different new land covers");
								fclose (flog);
								errorbox->ShowModal();
								exit(0);
							}
						}
						if (mat_newco[j][k] == oldcov) {
							if ((tempallow > 1000)
									&& (mat_age[j][k] >= (tempallow - 1000))) {
								//                       for (tempyear = year;tempyear<=(end-start);tempyear++)
								//                       {
								//                           if (demand[tempreg][oldcov][tempyear] > cellsize)
								//                           {
								//                             demand[tempreg][i][tempyear] += cellsize;
								//                              demand[tempreg][oldcov][tempyear] -= cellsize;
								//                          }
								//                       }
								mat_newco[j][k] = i;
							}
						}
					}
				}
			}
		}
	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void comp_demand(void) {

	int i, j, k, l, m, tempreg;
	float area[NREG][NCOV], ran[NCOV];

	/* calculate allocated cover */
	if (abs(arcview) != 2)
		fprintf(flog, "\n%d\t", loop);
	tempreg = 1;
	if (diffregregi < 2)
		tempreg = rnreg;
	for (i = 0; i <= maxcovdem; i++) {
		for (l = 0; l < tempreg; l++) {
			area[l][i] = 0;
			speed[l][i] += 0.00005;
		}
		ran[i] = random(101) + 1;
	}
	for (i = 0; i < rncov; i++) {
		for (l = 0; l < tempreg; l++)
			areacov[year][l][i] = 0;
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++) {
				if (diffregregi > 1)
					m = 0;
				else {
					if (region[j][k] < -9900)
						m = region[j][k] + 9998;
					else
						m = region[j][k];
				}
				if (i == mat_newco[j][k]) {
					area[m][covdem[i]] += cellsize;
					areacov[year][m][i] += cellsize;
				}
			}
		}
	}
	if (abs(arcview) != 2) {
		for (i = 0; i < rncov; i++) {
			for (l = 0; l < tempreg; l++)
				fprintf(flog, "%8.1f\t", areacov[year][l][i]);
			fprintf(flog, "-\t");
		}
		fprintf(flog, "*\t");
	}

	/* adapt elasticity */
	maxdiff = 0;
	totdiff = 0;

	for (i = 0; i < rncov; i++) {
		for (l = 0; l < tempreg; l++) {
			elas[l][i] -=
					(((area[l][covdem[i]] - demand[l][i][year]) / cellsize)
							/ (nocells[l] * speed[l][covdem[i]] * ran[covdem[i]]));
			if (fabs(elas[l][i]) > 1.5)
				elas[l][i] = 0;
			if (abs(arcview) != 2)
				fprintf(flog, "%4.3f\t", elas[l][i]);
			if (itermode == 0) {
				if (demand[l][i][year] > 0) {
					if (fabs(
							((area[l][covdem[i]] - demand[l][i][year])
									/ demand[l][i][year] * 100)) > maxdiff)
						maxdiff = fabs(
								((area[l][covdem[i]] - demand[l][i][year])
										/ demand[l][i][year] * 100));
					totdiff += (fabs(
							((area[l][covdem[i]] - demand[l][i][year])
									/ demand[l][i][year] * 100)));
				} else {
					if (fabs(
							((area[l][covdem[i]] - demand[l][i][year])
									/ cellsize * 100)) > maxdiff)
						maxdiff = fabs(
								((area[l][covdem[i]] - demand[l][i][year])
										/ cellsize * 100));
					totdiff += (fabs(
							((area[l][covdem[i]] - demand[l][i][year])
									/ cellsize * 100)));
				}
			}
			if (itermode == 1) {
				if (fabs(area[l][covdem[i]] - demand[l][i][year]) > maxdiff)
					maxdiff = fabs(area[l][covdem[i]] - demand[l][i][year]);
				totdiff += fabs(area[l][covdem[i]] - demand[l][i][year]);
			}
		}

		if (abs(arcview) != 2)
			fprintf(flog, "*\t%4.2f\t%4.2f\t", maxdiff, (totdiff / (rncov)));
	}
	if (itermode == 0)
		Barpos = ((10 * (log((totdiff * 100) / (rncov)))) - 10);
	if (itermode == 1) {
		i = (totdiff / (rncov)) - demdiff;
		if (i <= 0)
			i = 1;
		Barpos = (5 * log(i));
	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void write_grid(void) {
	FILE *f7, *f8, *f9, *f10;
	int i, j, k, l, tempreg;
	char filenam4[20], filenam5[20], filenam6[20];
	double tempdemand;

	/* write adapted land area file if bottomup function is active */

	if (bottomup == 1) {
		if ((f10 = fopen("landarea.txt", "w")) == NULL) {
			fprintf(flog, "\n not able to make file landarea.txt");
			fclose (flog);
			errorbox->ShowModal();
			exit(0);
		}
		if (diffregregi < 2)
			tempreg = rnreg;
		else
			tempreg = 1;
		for (i = 0; i <= (end - start); i++) {
			for (j = 0; j < rncov; j++) {
				tempdemand = 0;
				for (l = 0; l < tempreg; l++) {
					if (i == 0)
						tempdemand += demand[l][j][i];
					else
						tempdemand += areacov[i][l][j];
				}
				fprintf(f10, "%12.4lf\t", tempdemand);
			}
			fprintf(f10, "\n");
		}
		fclose(f10);
	}

	/* export output land cover maps, age file and influence*/

	if ((probmaps == 0) && (allprob == 0)) {
		/* land cover */
		if (arcview == 3)
			sprintf(filenam5, "cov_all.%d.asc", year);
		else
			sprintf(filenam5, "cov_all.%d", year);
		if ((f8 = fopen(filenam5, "w")) == NULL) {
			fprintf(flog, "\n not able to write file \n");
			fclose (flog);
			errorbox->ShowModal();
			exit(0);
		}
		if (arcview > 0) {
			fprintf(f8, "ncols    %d\n", rnc);
			fprintf(f8, "nrows    %d\n", rnr);
			fprintf(f8, "xllcorner  %f\n", xllcorner);
			fprintf(f8, "yllcorner  %f\n", yllcorner);
			fprintf(f8, "cellsize   %f\n", gridsize);
			fprintf(f8, "NODATA_value   -9999\n");
		}
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++)
				fprintf(f8, "%d\n", mat_newco[j][k]);
		}
		fclose(f8);

		/* age */
		if (arcview == 3)
			sprintf(filenam6, "age.%d.asc", year);
		else
			sprintf(filenam6, "age.%d", year);
		if ((f9 = fopen(filenam6, "w")) == NULL) {
			fprintf(flog, "\n not able to write file %s\n", filenam6);
			fclose (flog);
			errorbox->ShowModal();
			exit(0);
		}
		if (arcview > 0) {
			fprintf(f9, "ncols    %d\n", rnc);
			fprintf(f9, "nrows    %d\n", rnr);
			fprintf(f9, "xllcorner  %f\n", xllcorner);
			fprintf(f9, "yllcorner  %f\n", yllcorner);
			fprintf(f9, "cellsize   %f\n", gridsize);
			fprintf(f9, "NODATA_value   -9999\n");
		}
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++)
				fprintf(f9, "%d\n ", mat_age[j][k]);
		}
		fclose(f9);

		/* influence */
		if (influence > 1) {
			for (i = 0; i < rncov; i++) {
				if (arcview == 3)
					sprintf(filenam4, "infl_%d.%d.asc", i, (year - 1));
				else
					sprintf(filenam4, "infl_%d.%d", i, (year - 1));
				if ((f7 = fopen(filenam4, "w")) == NULL) {
					fprintf(flog,
							"\n not able to write file influence file \n");
					fclose (flog);
					errorbox->ShowModal();
					exit(0);
				}
				if (arcview > 0) {
					fprintf(f7, "ncols    %d\n", rnc);
					fprintf(f7, "nrows    %d\n", rnr);
					fprintf(f7, "xllcorner  %f\n", xllcorner);
					fprintf(f7, "yllcorner  %f\n", yllcorner);
					fprintf(f7, "cellsize   %f\n", gridsize);
					fprintf(f7, "NODATA_value   -9999\n");
				}
				for (j = 0; j < rnr; j++) {
					for (k = 0; k < rnc; k++) {
						fprintf(f7, "%6.2f\n ",
								(mat_at[(rnatt + i - allowmin)][j][k]));
					}
				}
				fclose(f7);
			}
		}
	}
	/* probability maps */
	if ((probmaps == 1) || (allprob == 1)) {
		for (i = 0; i < rncov; i++) {
			if (arcview == 3)
				sprintf(filenam4, "prob1_%d.%d.asc", i, year);
			else
				sprintf(filenam4, "prob1_%d.%d", i, year);
			if ((f7 = fopen(filenam4, "w")) == NULL) {
				fprintf(flog, "\n not able to write file \n");
				fclose (flog);
				errorbox->ShowModal();
				exit(0);
			}
			if (arcview > 0) {
				fprintf(f7, "ncols    %d\n", rnc);
				fprintf(f7, "nrows    %d\n", rnr);
				fprintf(f7, "xllcorner  %f\n", xllcorner);
				fprintf(f7, "yllcorner  %f\n", yllcorner);
				fprintf(f7, "cellsize   %f\n", gridsize);
				fprintf(f7, "NODATA_value   -9999\n");
			}
			for (j = 0; j < rnr; j++) {
				for (k = 0; k < rnc; k++)
					fprintf(f7, "%9.7f\n ", (mat_co[i][j][k]));
			}
			fclose(f7);
		}
	}

}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void calc_age(void) {
	int i, j, k, nl, dum;
	FILE *f11, *f19;
	bool same;
	char filenam6[20];

	if ((year == 0) && (agemode > 0)) {
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++)
				mat_age[j][k] = (rand() % (startage)) + 1;
		}

		if (arcview == 3)
			sprintf(filenam6, "age.%d.asc", year);
		else
			sprintf(filenam6, "age.%d", year);
		if ((f19 = fopen(filenam6, "w")) == NULL) {
			fprintf(flog, "\n not able to write file %s\n", filenam6);
			fclose (flog);
			errorbox->ShowModal();
			exit(0);
		}
		if (arcview > 0) {
			fprintf(f19, "ncols    %d\n", rnc);
			fprintf(f19, "nrows    %d\n", rnr);
			fprintf(f19, "xllcorner  %f\n", xllcorner);
			fprintf(f19, "yllcorner  %f\n", yllcorner);
			fprintf(f19, "cellsize   %f\n", gridsize);
			fprintf(f19, "NODATA_value   -9999\n");
		}
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++)
				fprintf(f19, "%d\n ", mat_age[j][k]);
		}
		fclose(f19);

	}
	if ((year == 0) && (agemode == 0)) {
		if (arcview == 3) {
			if ((f11 = fopen("age.0.asc", "r")) == NULL) {
				fprintf(flog, "no file: age.0");
				fclose (flog);
				errorbox->ShowModal();
				exit(0);
			}
		} else {
			if ((f11 = fopen("age.0", "r")) == NULL) {
				fprintf(flog, "no file: age.0");
				fclose (flog);
				errorbox->ShowModal();
				exit(0);
			}
		}
		if (arcview > 0) {
			nl = 0;
			while (nl < 6) {
				dum = fgetc(f11);
				if (dum == '\n')
					nl++;
			}
		}
		for (i = 0; i < rnr; i++) {
			for (j = 0; j < rnc; j++)
				fscanf(f11, "%d", &mat_age[i][j]);
		}
		fclose(f11);
	}

	if (year > 0) {
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++) {
				if (region[j][k] > -9900) {
					same = false;
					if (mat_newco[j][k] == mat_oldco[j][k]) {
						mat_age[j][k] += 1;
						same = true;
					}
					if (same == false)
						mat_age[j][k] = 1;
				} else
					mat_age[j][k] = -9999;
			}
		}
	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void calc_neigh(void) {
	int i, j, k, m, n, s[NCOV], t[NCOV], u, radius[NCOV];
	float cumvalue[NCOV], novalue, neighmat[NCOV][50][50];
	FILE *f13;

//read kernel definition matrix//

	if ((f13 = fopen("neighmat.txt", "r")) == NULL) {
		fprintf(flog, "no file: neighmat.txt");
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
	fprintf(flog, "\n NEIGHBORHOOD CONFIGURATION\n");
	fprintf(flog, "\n weights for neighborhood function: ");
	for (i = 0; i < rncov; i++) {
		fscanf(f13, "%f", &neighweight[i]);
		fprintf(flog, " %3.2f ", neighweight[i]);
	}
	for (u = 0; u < rncov; u++) {
		fscanf(f13, "%d", &radius[u]);
		fprintf(flog, "\n land use: %d\n kernel size: %d\n", u, radius[u]);
		s[u] = -1 * radius[u];
		t[u] = radius[u] + 1;
		for (i = s[u]; i < t[u]; i++) {
			for (j = s[u]; j < t[u]; j++) {
				fscanf(f13, "%f", &neighmat[u][radius[u] + i][radius[u] + j]);
				fprintf(flog, "%3.2f ",
						neighmat[u][radius[u] + i][radius[u] + j]);
			}
			fprintf(flog, "\n");
		}
	}
	fclose(f13);

//process all grid cells for all covers //
	for (u = 0; u < rncov; u++) {
		for (j = 0; j < rnr; j++) {
			for (k = 0; k < rnc; k++) {
				if (region[j][k] == -9999)  // exclude no-data region //
					mat_at[rnatt + u - allowmin][j][k] = -9999;
				else      // process all cells with a value //
				{

					novalue = 0;    // initialize neighborhood counters //
					cumvalue[u] = 0;
					for (m = s[u]; m < t[u]; m++) {
						for (n = s[u]; n < t[u]; n++) {
							if (((j + m) >= 0) && ((j + m) < rnr)
									&& ((k + n) >= 0) && ((k + n) < rnc)
									&& (region[j + m][k + n] > -9999)) {
								// kernel inside grid with value//
								novalue += neighmat[u][radius[u] + m][radius[u]
										+ n];
								if (mat_oldco[j + m][k + n] == u)
									cumvalue[u] +=
											neighmat[u][radius[u] + m][radius[u]
													+ n];
							}
						}
					}
					if (novalue > 0)
						mat_at[(rnatt + u - allowmin)][j][k] = ((cumvalue[u]
								/ novalue) / ratio[u]);
					else
						mat_at[(rnatt + u - allowmin)][j][k] = 0;
				}
			}
		}
	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void check_file(void) {
	int i, j, k, ercount;

	fprintf(flog, "\n\n-- Results of file check --");
	ercount = 0;
	checkfile = 0;
	for (j = 0; j < rnr; j++) {
		for (k = 0; k < rnc; k++) {
			if (region[j][k] > -9999) {
				ercount = 0;
				for (i = 0; i < rncov; i++) {

					if (mat_co[i][j][k] == -9999)
						ercount += 1;
				}
				if (ercount > 0) {
					fprintf(flog, "=>  %d errors in file cov_all.0\n", ercount);
					checkfile = 1;
				}
				for (i = 0; i < rnatt; i++) {
					if (mat_at[i][j][k] == -9999) {
						fprintf(flog, "  error in file sc1gr%d.fil\n", i);
						checkfile = 1;
					}
				}

			}
		}
	}

	if (checkfile == 0)
		fprintf(flog, " => no errors found in input files -\n\n");
	if (checkfile == 1) {
		fprintf(flog, "\n\n application terminated because of file errors");
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void init_allow(void) {
	int i, j, k;
	FILE *fa9;

	/* this module determines the lowest number of the sc1gr grids used in the conversion matrix */

	allowmin = rnatt;
	if ((fa9 = fopen("allow.txt", "r")) == NULL) {
		fprintf(flog, "no file: allow.txt");
		fclose (flog);
		errorbox->ShowModal();
		exit(0);
	}
	for (i = 0; i < rncov; i++) {
		for (j = 0; j < rncov; j++) {
			fscanf(fa9, "%d", &k);
			if ((k > 1) && (k < 100)) {
				if (k < allowmin)
					allowmin = k;
			}
		}
	}
	fprintf(flog, "\n - lowest sc1gr number in conversion matrix is: %d\n",
			allowmin);
	fclose(fa9);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

void unfinished(void) {
	FILE *ferror;
	char ername[29];

	fprintf(flog, "\nERROR: no solution; program terminated");
	year = (end - start);
	fclose (flog);
	if (ParamCount() < 1)
		errorbox->ShowModal();

	else {
		gettime (&ti);
		sprintf(ername, "error%2d%02d.txt", ti.ti_hour, ti.ti_min);
		if ((ferror = fopen(ername, "w")) == NULL) {
			errorbox->ShowModal();
			exit(0);
		}
		fprintf(ferror, "ERROR REPORT\n\n");
		fprintf(ferror, "error at: %2d:%02d\n", ti.ti_hour, ti.ti_min);
		strcpy(ername, ParamStr(0).c_str());
		fprintf(ferror, "directory of error: %s", ername);
		fclose(ferror);
		Beep(1800, 2800);

	}
	_exit(0);
}

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

