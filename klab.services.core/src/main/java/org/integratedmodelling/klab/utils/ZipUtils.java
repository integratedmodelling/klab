//package org.integratedmodelling.klab.utils;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Collection;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipInputStream;
//
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//
//import net.lingala.zip4j.core.ZipFile;
//import net.lingala.zip4j.exception.ZipException;
//import net.lingala.zip4j.model.ZipParameters;
//
//public class ZipUtils  {
//
//    /**
//     * Create a zip with the passed directory's contents in it. The directory will be the top entry
//     * in the file if storeDirectory is true.
//     * 
//     * @param zipFile
//     * @param directory
//     * @throws KlabException
//     */
//    public static void zip(File zipFile, File directory, boolean storeDirectory, boolean readHiddenFiles) {
//
//        // dest = buildDestinationZipFilePath(srcFile, dest);
//        ZipParameters parameters = new ZipParameters();
//        // parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); //
//        // parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); //
//        parameters.setIncludeRootFolder(storeDirectory);
//        // parameters.setReadHiddenFiles(readHiddenFiles);
//        try {
//            ZipFile zipF = new ZipFile(zipFile);
//            zipF.addFolder(directory, parameters);
//        } catch (ZipException e) {
//            throw new KlabIOException(e);
//        }
//    }
//
//    /**
//     * Unzip the contents of the zip file in the passed destination directory. If the directory
//     * does not exist, create it.
//     * 
//     * @param zipFile
//     * @param destDir
//     * @throws KlabException
//     */
//    public static void unzip(File zipFile, File destDir) {
//
//        ZipFile zFile;
//        try {
//            zFile = new ZipFile(zipFile);
//            if (!zFile.isValidZipFile()) {
//                throw new KlabIOException("file " + zipFile + " is not a valid archive");
//            }
//
//            if (!destDir.exists()) {
//                destDir.mkdirs();
//            }
//            // if (zFile.isEncrypted()) {
//            // zFile.setPassword(passwd.toCharArray());
//            // }
//            zFile.extractAll(destDir.toString());
//        } catch (ZipException e) {
//            throw new KlabIOException(e);
//        }
//    }
//
//    public static void extractDirectories(File zipFilePath, File destinationPath, Collection<String> pathsWanted)
//            throws IOException {
//        extractDirectories(zipFilePath, destinationPath, pathsWanted.toArray(new String[pathsWanted.size()]));
//    }
//
//    public static void extractDirectories(File zipFilePath, File destinationPath, String... pathsWanted)
//            throws IOException {
//
//        destinationPath.mkdirs();
//
//        ZipInputStream zis = null;
//        try {
//
//            zis = new ZipInputStream(new FileInputStream(zipFilePath));
//            ZipEntry entry;
//
//            while ((entry = zis.getNextEntry()) != null) {
//
//                boolean ok = false;
//
//                for (String ss : pathsWanted) {
//                    if (entry.getName().startsWith(ss)) {
//                        ok = true;
//                        break;
//                    }
//                }
//
//                if (!ok) {
//                    continue;
//                }
//
//                File entryFile = new File(destinationPath, entry.getName());
//                if (entry.isDirectory()) {
//
//                    if (!entryFile.exists()) {
//                        entryFile.mkdirs();
//                    }
//
//                } else {
//                    copy(zis, entryFile);
//                }
//            }
//        } finally {
//            closeQuietly(zis);
//        }
//    }
//
//    private static void closeQuietly(ZipInputStream zis) {
//        try {
//            zis.close();
//        } catch (IOException e) {
//        }
//    }
//
//    /*
//     * copy istream to file; do not close the istream
//     */
//    private static int copy(InputStream iStream, File entryFile) throws IOException {
//
//        if (entryFile.getParentFile() != null && !entryFile.getParentFile().exists()) {
//            entryFile.getParentFile().mkdirs();
//        }
//
//        if (!entryFile.exists()) {
//            entryFile.createNewFile();
//        }
//
//        BufferedOutputStream fOut = null;
//        int bytes = 0;
//        try {
//            try {
//                fOut = new BufferedOutputStream(new FileOutputStream(entryFile));
//                byte[] buffer = new byte[32 * 1024];
//                int bytesRead = 0;
//                if (iStream != null) {
//                    while ((bytesRead = iStream.read(buffer)) != -1) {
//                        fOut.write(buffer, 0, bytesRead);
//                        bytes += bytesRead;
//                    }
//                }
//            } catch (Exception e) {
//                throw new IOException("writeToFile failed, got: " + e.toString());
//            } finally {
//                fOut.close();
//            }
//        } catch (Exception e) {
//            throw new IOException(e);
//        }
//
//        return bytes;
//    }
//
//    public static int copyAndClose(InputStream iStream, File entryFile) throws IOException {
//
//        if (entryFile.getParentFile() != null && !entryFile.getParentFile().exists()) {
//            entryFile.getParentFile().mkdirs();
//        }
//
//        if (!entryFile.exists()) {
//            entryFile.createNewFile();
//        }
//
//        BufferedOutputStream fOut = null;
//        int bytes = 0;
//        try {
//            try {
//                fOut = new BufferedOutputStream(new FileOutputStream(entryFile));
//                byte[] buffer = new byte[32 * 1024];
//                int bytesRead = 0;
//                if (iStream != null) {
//                    while ((bytesRead = iStream.read(buffer)) != -1) {
//                        fOut.write(buffer, 0, bytesRead);
//                        bytes += bytesRead;
//                    }
//                }
//            } catch (Exception e) {
//                throw new IOException("writeToFile failed: " + e.toString());
//            } finally {
//                if (iStream != null)
//                    iStream.close();
//                fOut.close();
//            }
//        } catch (Exception e) {
//            throw new IOException(e);
//        }
//
//        return bytes;
//    }
//
//}
