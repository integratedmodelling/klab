//package org.integratedmodelling.klab.utils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.zip.GZIPInputStream;
//
//import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
//import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
//import org.apache.commons.compress.utils.IOUtils;
//
///**
// * Partially useful utils for tar.gz files. 
// * 
// * @author Ferd
// *
// */
//public class UntarUtils {
//
//	/**
//	 * Uncompress a tar.gz file, unpack the resulting tar file and put the contents
//	 * in a folder.
//	 * <p>
//	 * Taken from
//	 * https://netjs.blogspot.com/2017/05/how-to-untar-file-java-program.html
//	 * <p>
//	 * 
//	 * @param inputTarGz
//	 * @param existingDestinationFolderForTarFile
//	 * @param destinationFolderForUnpackedFiles
//	 * @throws IOException 
//	 */
//	public static void unpack(File inputFile, File existingDestinationFolderForTarFile,
//			File destFile) throws IOException {
//
//		UntarUtils unTarDemo = new UntarUtils();
//		String outputFile = getFileName(inputFile, existingDestinationFolderForTarFile.toString());
//		File tarFile = new File(outputFile);
//		// Calling method to decompress file
//		tarFile = unTarDemo.deCompressGZipFile(inputFile, tarFile);
//		if (!destFile.exists()) {
//			destFile.mkdir();
//		}
//		unTarDemo.unTarFile(tarFile, destFile);
//	}
//
//	/**
//	 * 
//	 * @param tarFile
//	 * @param destFile
//	 * @throws IOException
//	 */
//	private void unTarFile(File tarFile, File destFile) throws IOException {
//		FileInputStream fis = new FileInputStream(tarFile);
//		TarArchiveInputStream tis = new TarArchiveInputStream(fis);
//		TarArchiveEntry tarEntry = null;
//
//		// tarIn is a TarArchiveInputStream
//		while ((tarEntry = tis.getNextTarEntry()) != null) {
//			File outputFile = new File(destFile + File.separator + tarEntry.getName());
//			if (tarEntry.isDirectory()) {
//				if (!outputFile.exists()) {
//					outputFile.mkdirs();
//				}
//			} else {
//				outputFile.getParentFile().mkdirs();
//				FileOutputStream fos = new FileOutputStream(outputFile);
//				IOUtils.copy(tis, fos);
//				fos.close();
//			}
//		}
//		tis.close();
//	}
//
//	/**
//	 * Method to decompress a gzip file
//	 * 
//	 * @param gZippedFile
//	 * @param newFile
//	 * @throws IOException
//	 */
//	private File deCompressGZipFile(File gZippedFile, File tarFile) throws IOException {
//		
//		FileInputStream fis = new FileInputStream(gZippedFile);
//		GZIPInputStream gZIPInputStream = new GZIPInputStream(fis);
//
//		FileOutputStream fos = new FileOutputStream(tarFile);
//		byte[] buffer = new byte[1024];
//		int len;
//		while ((len = gZIPInputStream.read(buffer)) > 0) {
//			fos.write(buffer, 0, len);
//		}
//
//		fos.close();
//		gZIPInputStream.close();
//		return tarFile;
//
//	}
//
//	/**
//	 * This method is used to get the tar file name from the gz file by removing the
//	 * .gz part from the input file
//	 * 
//	 * @param inputFile
//	 * @param outputFolder
//	 * @return
//	 */
//	private static String getFileName(File inputFile, String outputFolder) {
//		return outputFolder + File.separator + inputFile.getName().substring(0, inputFile.getName().lastIndexOf('.'));
//	}
//}