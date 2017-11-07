/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * Create a md5sum file digest for a directory in a directory.
     * @param directory
     * @param fileName
     * @throws KlabException 
     */
    public static void createMD5Digest(File directory, String fileName) throws KlabException {

        if (!directory.exists() || !directory.canWrite() || !directory.isDirectory()) {
            return;
        }

        List<String> digest = new ArrayList<>();
        addFilesToMD5Digest(directory, digest, ".");

        try {
            writeLines(new File(directory + File.separator + fileName), digest);
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    private static void addFilesToMD5Digest(File directory, List<String> digest, String pathPrefix)
            throws KlabException {

        for (File f : directory.listFiles()) {

            if (f.isDirectory()) {
                addFilesToMD5Digest(f, digest, pathPrefix + "/" + MiscUtilities.getFileName(f.toString()));
            } else {
                try (FileInputStream fis = new FileInputStream(f)) {
                    String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
                    digest.add(md5 + "  " + pathPrefix + "/" + MiscUtilities.getFileName(f.toString()));
                } catch (IOException e) {
                    throw new KlabIOException(e);
                }
            }
        }
    }

    /**
     * Assume file contains a path and defines a file in it, and ensure that all directories up to
     * last file name exist.
     * 
     * @param file
     */
    public static void makeDirsUpToFile(File file) {
        String path = MiscUtilities.getFilePath(file.toString());
        if (!path.isEmpty()) {
            new File(path).mkdirs();
        }
    }

    public static void main(String[] args) throws Exception {
        createMD5Digest(new File("C:/Users/ferdinando.villa/.tl/server"), "zioporco.txt");
    }
}
