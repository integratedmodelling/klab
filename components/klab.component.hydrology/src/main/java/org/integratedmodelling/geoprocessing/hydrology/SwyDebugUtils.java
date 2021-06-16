package org.integratedmodelling.geoprocessing.hydrology;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.gears.io.rasterwriter.OmsRasterWriter;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;

public class SwyDebugUtils {

    public static final boolean DUMP_MAPS = true;

    private static SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss");

    private static File getDumpFolder() {
        File klabFolder = Configuration.INSTANCE.getDataPath();

        File swyFolder = new File(klabFolder, "swy_tmp_dump_folder");
        if (!swyFolder.exists()) {
            swyFolder.mkdirs();
        }
        return swyFolder;
    }

    public static void dumpToRaster(long timestamp, ILocator locator, String producingModel, IMonitor monitor, IState... states) {
        if (DUMP_MAPS) {
            for(IState state : states) {
                if (state != null) {
                    GridCoverage2D coverage = GeotoolsUtils.INSTANCE.stateToCoverage(state, locator, false);
                    String name = state.getObservable().getName();

                    String dateStr = f.format(new Date(timestamp));
                    String fileName = name + "_" + producingModel + ".asc";

                    File outFolder = new File(getDumpFolder(), dateStr);
                    if (!outFolder.exists()) {
                        outFolder.mkdir();
                    }
                    File outfile = new File(outFolder, fileName);

                    if (monitor != null) {
                        monitor.debug("Dumping state of ts " + dateStr + " to file " + fileName);
                    }

                    try {
                        OmsRasterWriter.writeRaster(outfile.getAbsolutePath(), coverage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        monitor.error(e.getMessage());
                    }
                }
            }
        }
    }

}
