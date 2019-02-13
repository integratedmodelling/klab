package org.integratedmodelling.ml.legacy.riskwiz.learning.bndata;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class GraphDataSourceAAIGrid extends GraphDataAdapter implements
        IGraphDataSource {
	
    Vector<String> scheme;
    String[] aaiGridFiles;
    BufferedReader[] bufFileReaders;
    String[] bufferStrings;
	   
    IDiscretizer discretizer;
    int arrSize;

    /**
     * @throws IOException 
     * 
     */
    public GraphDataSourceAAIGrid(String[] aaiGridFiles, String[] varNames, IDiscretizer discr) throws IOException {
        super();
        arrSize = aaiGridFiles.length;
        this.aaiGridFiles = aaiGridFiles;
        this.discretizer = discr;
        scheme = new Vector<String>();
        for (int i = 0; i < varNames.length; i++) {
            scheme.add(varNames[i]);
        }
		
        readHeders();
		 
    }

    @Override
    public Vector<String> getScheme() {
        return scheme;
    }

    @Override
	public void close() throws IOException {
		
        for (int i = 0; i < arrSize; i++) {
            if (bufFileReaders != null) {
                bufFileReaders[i].close();
            }
        }
		 
    }

    @Override
	public void readNextValues() throws IOException {
		 
        for (int i = 0; i < aaiGridFiles.length; i++) {			 
            bufferStrings[i] = bufFileReaders[i].readLine();
				
            if (bufferStrings[i] != null) {
                bufferStrings[i] = bufferStrings[i].trim();
            }
        }
		 
    }

    @Override
	public boolean hasNextValues() {
        boolean out = true;

        for (int i = 0; i < aaiGridFiles.length; i++) {
            if (bufferStrings[i] == null) {
                out = false;
            }
        }
        return out;
    }
	
    @Override
	public Vector<Vector<String>> getNextValues() {
        String[][] rasterValMatrix = new String[arrSize ][];
		 
        for (int i = 0; i < arrSize; i++) {
            rasterValMatrix[i] = discretizer.discretizeAll(scheme.elementAt(i),
                    bufferStrings[i].split(" "));        	 
        }
         
        int aagrid_row_width = rasterValMatrix[0].length;
         
        Vector<Vector<String>> table = new  Vector<Vector<String>>();
         
        for (int j = 0; j < aagrid_row_width; j++) {
            Vector<String>  tuple = new  Vector<String>();

            for (int i = 0; i < arrSize; i++) {
                tuple.add(rasterValMatrix[i][j]);
            }
        	 
            table.add(tuple);
        }
         
        return table;
    }
	
    public void readHeders() throws IOException {
		 
        bufFileReaders = new BufferedReader[arrSize];
        bufferStrings = new String[arrSize];
        for (int i = 0; i < aaiGridFiles.length; i++) {
            bufFileReaders[i] = new BufferedReader(
                    new FileReader(aaiGridFiles[i]));
					 
            // do
            // {
            // bufferStrings[i] = bufFileReaders[i].readLine();
            // } while  (bufferStrings[i]!=null &&!bufferStrings[i].startsWith("NODATA_value")  );
            //
            bufferStrings[i] = bufFileReaders[i].readLine();
				    
            while (bufferStrings[i].equalsIgnoreCase("")) {
                bufferStrings[i] = bufFileReaders[i].readLine();
            }
				    
            if (bufferStrings[i] != null) {
                bufferStrings[i] = bufferStrings[i].trim();
            }
        }
			 
    }
	
}
