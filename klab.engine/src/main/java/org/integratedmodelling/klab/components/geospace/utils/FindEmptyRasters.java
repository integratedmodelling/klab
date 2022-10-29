package org.integratedmodelling.klab.components.geospace.utils;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.util.factory.Hints;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.utils.MiscUtilities;

/**
 * Utility to run on directories to find any rasters with no useful data. Prints the directory path
 * followed by the list of the matching file names.
 * 
 * Built to find tiles without data in large global file loops build with k.Actors. Other
 * assumptions may require restructuring.
 * 
 * Meant to run from Eclipse, so put the directory path you want in the arguments to scanner.scan()
 * before running.
 * 
 * @author Ferd
 *
 */
public class FindEmptyRasters {

    private void findEmptyRasters(File directory, Map<File, String> result) {

        StringBuffer files = new StringBuffer(1024);

        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                findEmptyRasters(f, result);
            } else if (f.toString().endsWith(".tif") || f.toString().endsWith(".tiff")) {
                
                boolean ok = false;
                try {
                    GridCoverage2D tiff = readCoverage(f);
                    if (GeotoolsUtils.INSTANCE.hasData(tiff)) {
                        ok = true;
                    }
                        
                    tiff.dispose(true);
                    
                } catch (IOException e) {
                    // leave OK to false
                }
                
                if (!ok) {
                    files.append((files.length() == 0 ? "" : ",") + MiscUtilities.getFileBaseName(f));
                }
            }
        }

        if (!files.isEmpty()) {
            result.put(directory, files.toString());
        }

    }

    public Map<File, String> scan(File directory) {
        Map<File, String> ret = new LinkedHashMap<>();
        findEmptyRasters(directory, ret);
        return ret;
    }

    public static void main(String[] args) {
        FindEmptyRasters scanner = new FindEmptyRasters();
        Map<File, String> result = scanner.scan(Configuration.INSTANCE.getDefaultExportDirectory());
        for (File dir : result.keySet()) {
            System.out.println(dir + ":\t" + result.get(dir));
        }
    }

    public GridCoverage2D readCoverage(File mainFile) throws IOException {

        GridCoverage2D ret = null;
        AbstractGridFormat format = GridFormatFinder.findFormat(mainFile);
        // this is a bit hackey but does make more geotiffs work
        Hints hints = new Hints();
        if (format instanceof GeoTiffFormat) {
            hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
        }
        GridCoverage2DReader reader = format.getReader(mainFile, hints);
        return reader.read(null);
    }

}
