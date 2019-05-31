/** tiledMatrix 1.0. Date of release: July 25th, 2013. 
 *
 * 
 * Copyright (C) 2013 Jaqueline Silveira, Marcus Andrade and Salles Magalhaes.
 *
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * You should have received a copy of the GNU General Public License along 
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 /**
 * TiledMatrix - TiledMatrix was designed to support the development
 * of applications that require the manipulation of huge matrices in 
 * external memory. And, as is known, any application requiring external 
 * memory processing should be designed focusing the I/O operations reduction.
 * 
 * For further information, consider the following reference:
 * 
 * SILVEIRA, Jaqueline A.; Magalhães, Salles V. G.; ANDRADE, Marcus Vinícius Alvim; CONCEICAO, Vinicius S.
 * A library to support the development of applications that process huge matrices in external memory. 
 * In: 15th International Conference on Enterprise Information Systems, 2013, Angers Loire Valley, 2013. 
 * 
 */

#include <iostream>
#include <fstream>
#include <algorithm>
#include <sstream>
#include <vector>
#include <cassert>
#include <sys/time.h>
#include <stdio.h>
#include <unistd.h>
#include "lz4.h"
using namespace std;

//#define DBG(X) X
#define DBG(X) 


const char STATUS_INICIALIZADO = 1;
const char STATUS_NAO_INICIALIZADO = 2;
const char STATUS_INICIALIZADO_VALOR_CONSTANTE = 3;

template <class T>
class tiledMatrix {
	public:
		void gravaStatVezes(string arquivo);

		T get(int i, int j);
		void set(int i,int j, T valor);
		void set(T valor);

		T& operator() (unsigned int i, unsigned int j);        
  		T  operator() (unsigned int i, unsigned int j) const; 

		tiledMatrix(int nrows,int ncolumns,int linhasTiles,int colunasTiles,int nTilesMemoria,string arquivoBase,string arquivoMatriz="");

		~tiledMatrix();


	private:


		tiledMatrix();
		tiledMatrix(const tiledMatrix &x);		
	
		void loadTile2(int i,int j);
		void loadTile(int i,int j);
		void removeTileDaMemoria(int i,int j);

		T **matrizes;
		int colunasTiles, linhasTiles; //numero de colunas e linhas em cada tile...
		int nrowsTiles,ncolumnsTiles;
		int numTilesMemoria;
		int nrows,ncolumns;

		unsigned long long timeStamp;

		unsigned long long *timeStamps;
		pair<int,int> *tilesCarregados;
		int **posTiles;
		fstream arquivoAssociado;

		int **numVezesTileCarregadoDisco;

		unsigned long tempoTotalCarregando;

		int tilesAbertos;
		int tilesCarregadosMemoria;
		int totalFalhasTiles;
		int tilesGravadosMemoria;

		

		bool **sujo;
		char **statusBloco;

		int **bytesCompressaoBloco;
		char *bufferTempCompressao;
		long unsigned tamanhoMaximoBlocoComprimido;

		T valorSetadoParaTodosBlocos;

};

template <class T>
tiledMatrix<T>::tiledMatrix() {

}

template <class T>
tiledMatrix<T>::tiledMatrix(const tiledMatrix &x) {

}

template <class T>
tiledMatrix<T>::~tiledMatrix() {
	cerr << "------------------------------------------------------------" <<endl;
	cerr << "Sizeof(T): " << sizeof(T) << endl;
	cerr << "Tamanho da matriz (MB): " << (nrows*((long long)ncolumns)*sizeof(T))/(1024*1024) << endl;
	cerr << "Total falhas: " << totalFalhasTiles << endl;
	cerr << "Total Tiles carregados: "<< tilesCarregadosMemoria << endl;
	cerr << "Tempo total carregando tiles (s): " << tempoTotalCarregando/1000000 << endl;
	cerr << "Tempo total carregando tiles (u): " << tempoTotalCarregando << endl;
	cerr << "Tempo de carga para cada 1M celulas: " << (tempoTotalCarregando)/( ((double)tilesCarregadosMemoria+totalFalhasTiles)*linhasTiles*colunasTiles) << endl;
	cerr << "Espaco ocupado por cada celula: " << sizeof(T) << '\n';
	cerr << "Quantos MB de celula carregados por segundo: " <<  sizeof(T)/((tempoTotalCarregando)/( ((double)tilesCarregadosMemoria+totalFalhasTiles)*linhasTiles*colunasTiles))   << " MB/s" << endl;
	cerr << "Numero de vezes o terreno foi recarregado: " << tilesCarregadosMemoria*1.0/(nrowsTiles*ncolumnsTiles) << endl ;
	cerr << "------------------------------------------------------------" <<endl;

	delete timeStamps;
	delete tilesCarregados;
	for(int i=0;i<nrowsTiles;i++) {
		delete []posTiles[i];
	}
	assert(arquivoAssociado.is_open());
	arquivoAssociado.close();
	for(int i=0;i<numTilesMemoria;i++) {
		delete []matrizes[i];
	}

 
	cerr << "Deletando ja inicializado " <<endl;
	for(int i=0;i<nrowsTiles;i++) {
		delete []statusBloco[i];
	}
	cerr << "Deletando sujo" <<endl;
	for(int i=0;i<nrowsTiles;i++) {
		delete []sujo[i];
	}
	cerr << "fim " <<endl;

	delete bufferTempCompressao;
	for(int i=0;i<nrowsTiles;i++) {
		delete []bytesCompressaoBloco[i];
	}
	

	/*delete numVezesTileCarregadoDispco;
	for(int i=0;i<nrowsTiles;i++) {
		delete []numVezesTileCarregadoDisco[i];
	}*/
}





template <class T>
tiledMatrix<T>::tiledMatrix(int nrows,int ncolumns,int linhasTiles,int colunasTiles,int nTilesMemoria,string arquivoBase,string arquivoMatriz) {


	tempoTotalCarregando=0;

	tilesAbertos = 0;
	tilesCarregadosMemoria =0;
	totalFalhasTiles =0;
	tilesGravadosMemoria = 0;

	assert(linhasTiles>0);
	assert(colunasTiles>0);
	assert(nrows > 0 && ncolumns >0);
	assert(nrows%linhasTiles == 0);
	assert(ncolumns%colunasTiles == 0);

	nrowsTiles = nrows/linhasTiles;
	ncolumnsTiles = ncolumns/colunasTiles;
	this->linhasTiles = linhasTiles;
	this->colunasTiles = colunasTiles;
	this->numTilesMemoria = nTilesMemoria;
	this->nrows = nrows;
	this->ncolumns = ncolumns;

	posTiles =  new int *[nrowsTiles];
	for(int i =0;i<nrowsTiles;i++) {
		posTiles[i] = new int[ncolumnsTiles];
		for(int j=0;j<ncolumnsTiles;j++)
			posTiles[i][j] = -1;
	}




	sujo =  new bool *[nrowsTiles];
	for(int i =0;i<nrowsTiles;i++) {
		sujo[i] = new bool[ncolumnsTiles];
		for(int j=0;j<ncolumnsTiles;j++)
			sujo[i][j] = false;
	}

	statusBloco =  new char *[nrowsTiles];
	for(int i =0;i<nrowsTiles;i++) {
		statusBloco[i] = new char[ncolumnsTiles];
		for(int j=0;j<ncolumnsTiles;j++)
			statusBloco[i][j] = STATUS_NAO_INICIALIZADO;
	}

	bytesCompressaoBloco =  new int *[nrowsTiles];
	for(int i =0;i<nrowsTiles;i++) {
		bytesCompressaoBloco[i] = new int[ncolumnsTiles];
		for(int j=0;j<ncolumnsTiles;j++)
			bytesCompressaoBloco[i][j] = 0;
	}

	tamanhoMaximoBlocoComprimido = LZ4_compressBound( linhasTiles*colunasTiles*sizeof(T) );
	bufferTempCompressao = new char[ max(tamanhoMaximoBlocoComprimido,(long unsigned int)linhasTiles*colunasTiles*sizeof(T)) ];
	
	
	/*numVezesTileCarregadoDisco =  new int *[nrowsTiles];
	for(int i =0;i<nrowsTiles;i++) {
		numVezesTileCarregadoDisco[i] = new int[ncolumnsTiles];
		for(int j=0;j<ncolumnsTiles;j++)
			numVezesTileCarregadoDisco[i][j] = 0;
	}*/

	
	matrizes = new T *[numTilesMemoria];
	for(int k=0;k<numTilesMemoria;k++) {
		matrizes[k] = new T[linhasTiles*colunasTiles];
	}

	

	timeStamp =1;
	tilesCarregados = new pair<int,int>[numTilesMemoria];
	timeStamps = new unsigned long long [numTilesMemoria];
	for(int i=0;i<numTilesMemoria;i++) {
		timeStamps[i]= 0;
	}

	//cerr <<" usando arquivo " <<arquivoBase.c_str() <<endl;
	arquivoAssociado.open(arquivoBase.c_str(),ios::out | ios::binary);
	
	

	unsigned long long tamanhoArquivo = ((unsigned long long)nrowsTiles)*ncolumnsTiles*tamanhoMaximoBlocoComprimido;

	if (arquivoMatriz.size()==0) {
		char c=0;
		arquivoAssociado.seekp(tamanhoArquivo-1,ios::beg);
		arquivoAssociado.write( reinterpret_cast<char *>( &c ),1);

		arquivoAssociado.close();
		arquivoAssociado.open(arquivoBase.c_str(),ios::in | ios::out | ios::binary);
		assert(arquivoAssociado.is_open());
			
		return;
	} else {
		exit(0);
	}

	
	/*ifstream fin(arquivoMatriz.c_str(),ios::binary);
	assert(fin.good());


	arquivoAssociado.seekp(0,ios::beg);
	//Suponha que tilesWidth*ncolumns*sizeof(T) caibam na memoria....
	T *matrizTemp = new T [linhasTiles*ncolumns];
	for(int i=0;i<nrowsTiles;i++) { //Le uma "linha" e grava os arquivos em disco..
		fin.read(reinterpret_cast<char *>(matrizTemp),linhasTiles*ncolumns*sizeof(T));
	

		//Para cada tile (i,t) (linha i, coluna t)
		for(int t=0;t<ncolumnsTiles;t++) {
			for(int y=0;y<linhasTiles;y++) {
				for(int x=0;x<colunasTiles;x++) {
					arquivoAssociado.write( reinterpret_cast<char *>( matrizTemp[y*ncolumns + x] ),sizeof(T));
				}
			}
		}		
	}
	delete matrizTemp;

	arquivoAssociado.close();
	arquivoAssociado.open(arquivoBase.c_str(),ios::in | ios::out | ios::binary);
	assert(arquivoAssociado.is_open());*/
}


template <class T>
void tiledMatrix<T>::set(T valor) {
	for(int i =0;i<nrowsTiles;i++) 
		for(int j=0;j<ncolumnsTiles;j++) {
			statusBloco[i][j] = STATUS_INICIALIZADO_VALOR_CONSTANTE;
			sujo[i][j] = true;
		}
	valorSetadoParaTodosBlocos = valor;


	/*arquivoAssociado.seekp(0,ios::beg);
	unsigned long long numElementos = nrows*((unsigned long long )ncolumns);
	for(unsigned long long  i=0;i<numElementos;i++)
			arquivoAssociado.write(reinterpret_cast<char *>(&valor),sizeof(T));	



	for(int i =0;i<nrowsTiles;i++) {
		for(int j=0;j<ncolumnsTiles;j++)
			sujo[i][j] = false;
	}

	for(int i =0;i<nrowsTiles;i++) {
		for(int j=0;j<ncolumnsTiles;j++)
			jaInicializado[i][j] = true;
	}*/
}

template <class T>
T tiledMatrix<T>::get(int i, int j) {
	int posTile = posTiles[i/linhasTiles][j/colunasTiles];
	if (posTile == -1) {
		loadTile(i/linhasTiles,j/colunasTiles);
		posTile = posTiles[i/linhasTiles][j/colunasTiles];
	}
	timeStamps[posTile] = timeStamp++; //Marca o timestamp dele como um novo timestamp
	return matrizes[posTile][ (i%linhasTiles)*colunasTiles + (j%colunasTiles)   ];
}

template <class T>
void tiledMatrix<T>::set(int i,int j, T valor) {

	int posTile = posTiles[i/linhasTiles][j/colunasTiles];
	if (posTile == -1) {
		DBG(cerr << "carregando " <<endl;)
		loadTile(i/linhasTiles,j/colunasTiles);
		posTile = posTiles[i/linhasTiles][j/colunasTiles];
		DBG(cerr << "pos " << posTile << endl;)
	}
	sujo[i/linhasTiles][j/colunasTiles] = true;
	

	timeStamps[posTile] = timeStamp++; //Marca o timestamp dele como um novo timestamp
	matrizes[posTile][ (i%linhasTiles)*colunasTiles + (j%colunasTiles)      ] = valor;
}


template <class T>
 inline T& tiledMatrix<T>::operator() (unsigned int i, unsigned int j)
 {
	exit(1);
	/*int posTile = posTiles[i/tilesWidth][j/tilesWidth];
	if (posTile == -1) {
		DBG(cerr << "carregando " <<endl;)
		loadTile(i/tilesWidth,j/tilesWidth);
		posTile = posTiles[i/tilesWidth][j/tilesWidth];
		DBG(cerr << "pos " << posTile << endl;)
	}
	timeStamps[posTile] = timeStamp++; //Marca o timestamp dele como um novo timestamp
	return matrizes[posTile][ (i%linhasTiles)*colunasTiles + (j%colunasTiles)   ];*/
 }
 




template <class T>
void tiledMatrix<T>::gravaStatVezes(string arquivo) {
	/*ofstream saida(arquivo.c_str(),ios::out);
	saida<< "P2" <<endl;
	saida<< "# feep.pgm" <<endl;
	saida << nrowsTiles << " " << nrowsTiles << endl;
	int mx = -1;
	for(int i=0;i<nrowsTiles;i++)
		for(int j=0;j<ncolumnsTiles;j++)
			mx = max( numVezesTileCarregadoDisco[i][j],mx);
	saida << mx <<endl;
	for(int i=0;i<nrowsTiles;i++) {
		for(int j=0;j<ncolumnsTiles;j++)
			saida <<  numVezesTileCarregadoDisco[i][j] << ' ';
		saida << '\n';
	}
	saida <<flush;	
	saida.close();*/
}


template <class T>
void tiledMatrix<T>::loadTile(int i,int j) {
    struct timeval start, end;

    long mtime, seconds, useconds;    

	//cerr << "aqui " <<endl;
    gettimeofday(&start, NULL);
	//cerr << "ali " <<endl;
   
	loadTile2(i,j);

    gettimeofday(&end, NULL);

    seconds  = end.tv_sec  - start.tv_sec;
    useconds = end.tv_usec - start.tv_usec;

    mtime = ((seconds) * 1000000 + useconds) ;

	//cerr << mtime << endl;

    tempoTotalCarregando += mtime;
}

//Private
template <class T>
void tiledMatrix<T>::loadTile2(int i,int j) {
	//numVezesTileCarregadoDisco[i][j]++;



	int id = 0;
	//Pegue o tile carregado (ou vago) menos recentemente utilizado...
	for(int k=0;k<numTilesMemoria;k++) {		
		DBG(cerr << k << " ---> " << tilesCarregados[k].first << " , " <<  tilesCarregados[k].second << endl;)
		if (timeStamps[k] < timeStamps[id]) 
			id = k;
	}

	DBG(cerr << "Carregando tile " << i << " , " << j << " no id " << id << endl;)
	//Se há algo carregado na posicao id... precisamos grava-lo!
	if (timeStamps[id]!=0) {
		DBG(cerr << "Removendo da memoria " <<endl;)
		DBG(cerr << tilesCarregados[id].first << " , " << tilesCarregados[id].second << endl;)
		removeTileDaMemoria(tilesCarregados[id].first,tilesCarregados[id].second); //Grava			
	} 
	
	timeStamps[id] = timeStamp++; //Marca o timestamp dele como um novo timestamp
	DBG(cerr << id << " :: " << i << endl;)
	tilesCarregados[id].first = i; //Agora o tile (i,j) estara carregado na posicao id
	tilesCarregados[id].second = j;
	posTiles[i][j] = id;
	
	//Carrega o tile (i,j) na posicao id...

	unsigned long long tilePos = ((unsigned long long )i)*ncolumnsTiles+j;
	DBG(cerr << "lendo do disco.. para a matriz com id: " << id << endl;)

	
	//unsigned long long baseLinhaTile = ((unsigned long long )tilePos)*linhasTiles*colunasTiles;


	if (statusBloco[i][j] == STATUS_INICIALIZADO) {
		tilesCarregadosMemoria++;
		tilesAbertos++;
		//arquivoAssociado.seekg(baseLinhaTile*sizeof(T),ios::beg);
		


    /*long tempoLer, tempoDescomprimir, seconds, useconds;    

    struct timeval start, end;
    gettimeofday(&start, NULL);*/
		arquivoAssociado.seekg(tilePos*tamanhoMaximoBlocoComprimido  ,ios::beg);
		arquivoAssociado.read(bufferTempCompressao ,bytesCompressaoBloco[i][j]);
   /* gettimeofday(&end, NULL);

    seconds  = end.tv_sec  - start.tv_sec;
    useconds = end.tv_usec - start.tv_usec;

    tempoLer = ((seconds) * 1000000 + useconds) ;			
			
    gettimeofday(&start, NULL);		*/
		//cerr << "Gravando com compressao: " << bytesCompressaoBloco[i][j]*1.0/(tilesWidth*tilesWidth*sizeof(T)) << endl;
		LZ4_uncompress (bufferTempCompressao, reinterpret_cast<char *>(matrizes[id]) , linhasTiles*colunasTiles*sizeof(T) );	

    /*gettimeofday(&end, NULL);

    seconds  = end.tv_sec  - start.tv_sec;
    useconds = end.tv_usec - start.tv_usec;

    tempoDescomprimir = ((seconds) * 1000000 + useconds) ;	
    cerr << "Lendo com compressao: " << bytesCompressaoBloco[i][j]*1.0/(tilesWidth*tilesWidth*sizeof(T)) << endl;
    cerr << "MB: " << bytesCompressaoBloco[i][j]*1.0/(1024*1024) << endl;
    cerr << "Tempo descomprimir: " << tempoDescomprimir << endl;
    cerr << "Tempo ler: " << tempoLer << endl << endl;*/
		
		
		sujo[i][j] = false;	
		//arquivoAssociado.read(reinterpret_cast<char *>(matrizes[id]) ,tilesWidth*tilesWidth*sizeof(T));		
	} else if (statusBloco[i][j] == STATUS_NAO_INICIALIZADO) {

	} else if (statusBloco[i][j] == STATUS_INICIALIZADO_VALOR_CONSTANTE) { 
		for(int i=0;i<linhasTiles;i++)
			for(int j=0;j<colunasTiles;j++)			
				matrizes[id][i*colunasTiles + j]  = valorSetadoParaTodosBlocos;
		statusBloco[i][j] = STATUS_INICIALIZADO;
		sujo[i][j] = true;
	} else {
		cerr << "ERRO!!!" << endl;
		exit(1);
	}
	

	DBG(cerr << "fim " <<endl;)

	//cerr << "Total falhas: " << totalFalhasTiles << endl;
	//cerr << "Total Tiles carregados: "<< tilesCarregadosMemoria << endl;
	//cerr << "Tile " << i << " ,  " <<  j << " carregado"  << endl << endl;

}

template <class T>
void tiledMatrix<T>::removeTileDaMemoria(int i,int j) {
	tilesGravadosMemoria++;
	totalFalhasTiles++;
	
	//cerr << "Tile " << i << " ,  " <<  j << " removido da memoria"  << endl << endl;

	int id = posTiles[i][j];	
	if (id==-1) {
		cerr << "Erro inesperado: tentando armazenar em disco um tile que nao foi carregado em memoria!" <<endl;	
		exit(1);	
	}
	DBG(cout << "Removendo da memoria para arquivo: " << arquivosAssociados[i][j] << endl;)

	unsigned long long tilePos = ( (unsigned long long) i)*ncolumnsTiles+j;
	//unsigned long long baseLinhaTile = ( (unsigned long long) tilePos)*linhasTiles*colunasTiles;

	if (statusBloco[i][j] == STATUS_INICIALIZADO_VALOR_CONSTANTE && sujo[i][j]) { 
		for(int i=0;i<linhasTiles;i++)
			for(int j=0;j<colunasTiles;j++)			
				matrizes[id][i*colunasTiles + j]  = valorSetadoParaTodosBlocos;
	}

	if (sujo[i][j]) {
		//arquivoAssociado.seekp(baseLinhaTile*sizeof(T),ios::beg);
		arquivoAssociado.seekp(tilePos*tamanhoMaximoBlocoComprimido,ios::beg);



  /*  long tempoComprimir, tempoGravar, seconds, useconds;    

    struct timeval start, end;
    gettimeofday(&start, NULL);*/
		bytesCompressaoBloco[i][j] = LZ4_compress(reinterpret_cast<char *>(matrizes[id]), bufferTempCompressao, linhasTiles*colunasTiles*sizeof(T) );
 /*   gettimeofday(&end, NULL);

    seconds  = end.tv_sec  - start.tv_sec;
    useconds = end.tv_usec - start.tv_usec;

    tempoComprimir = ((seconds) * 1000000 + useconds) ;			
			
    gettimeofday(&start, NULL);		*/
		//cerr << "Gravando com compressao: " << bytesCompressaoBloco[i][j]*1.0/(tilesWidth*tilesWidth*sizeof(T)) << endl;
		arquivoAssociado.write( bufferTempCompressao ,bytesCompressaoBloco[i][j] );	

  /* gettimeofday(&end, NULL);

    seconds  = end.tv_sec  - start.tv_sec;
    useconds = end.tv_usec - start.tv_usec;

    tempoGravar = ((seconds) * 1000000 + useconds) ;	
    cerr << "Gravando com compressao: " << bytesCompressaoBloco[i][j]*1.0/(tilesWidth*tilesWidth*sizeof(T)) << endl;
    cerr << "MB: " << bytesCompressaoBloco[i][j]*1.0/(1024*1024) << endl;
    cerr << "Tempo comprimir: " << tempoComprimir << endl;    
    cerr << "Tempo gravar: " << tempoGravar << endl;

    cerr << "MB/s comprimir: " << (tilesWidth*tilesWidth*sizeof(T)/(1024*1024.0))/(tempoComprimir/1000000.0) << endl;
    cerr << "MB/s gravar: " << (tilesWidth*tilesWidth*sizeof(T)/(1024*1024.0))/(tempoGravar/1000000.0) << endl << endl;*/
    
	
		sujo[i][j] = false;
		statusBloco[i][j] = STATUS_INICIALIZADO;
	}
	
	posTiles[tilesCarregados[id].first][tilesCarregados[id].second] =  -1; //Fala que ele nao estah mais carregado...

}
