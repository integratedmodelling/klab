TiledVS
======
Calculate an approximation to the viewshed of a specified observer in a 
rectangular elevation matrix. This algorithm has been specially designed 
to minimize I/O operations.


Compilation
-----------
To compile, use:

    g++ -O3 -o TiledVS tiledVS.cpp lz4.c

Running
-------
To run it, you first need to create a "tiles" folder in the current directory. Then:

    ./TiledVS NROWS NCOLS OBSERVER[0] OBSERVER[1] OBSERVER_HEIGHT RADIUS INPUT_FILE.hgt MEM [BLOCKSIZE_ROWS, BLOCKSIZE_COLS] > OUTPUT_FILE.vs
	
	where:
		* NROWS, NCOLS: Number of rows and columns in the input terrain file;
		* OBSERVER[0] and [1]: observer coordinates, in number of cells;
		* OBSERVER_HEIGHT: observer elevation above the terrain, in the same unit as the terrain file;
		* RADIUS: Observer's radius of interest, in number of cells;
		* INPUT_FILE.hgt: Input terrain file. Depending on whether you use 2 or 4 bytes per elevation value, it may be
			necessary to change the "typedef elev_t" (lines 47 and 48 on tiledVS.cpp) and recompile the code;
		* MEM: Maximum RAM memory size the algorithm should use, in Megabytes;
		* [BLOCKSIZE_ROWS, BLOCKSIZE_COLS]: Dimensions of each TiledMatrix block. This is optional. If not set, this will
			by automatically determined;
		* OUTPUT_FILE.vs: Output viewshed file, a ROWS x COLS bit matrix where 1 indicates a visible a cell and 0 a non-visible one.


Bibliography
------------
This program is described in the publications:

1. Ferreira, C.R., et al., 2012. More efficient terrain viewshed 
 computation on massive datasets using external memory. In: Proceedings
 of the 20th International Conference on Advances in Geographic 
 Information Systems, SIGSPATIAL ’12, Redondo Beach, California, 
 USA: ACM, 494–497.
 
2. Ferreira, C.R., et al. 2016, An efficient external memory algorithm 
 for terrain viewshed computation. In: ACM Transactions on Spatial
 Algorithms and Systems 2.2 (2016): 6.
