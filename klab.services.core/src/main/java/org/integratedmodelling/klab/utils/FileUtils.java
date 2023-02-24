///*
// * This file is part of k.LAB.
// * 
// * k.LAB is free software: you can redistribute it and/or modify
// * it under the terms of the Affero GNU General Public License as published
// * by the Free Software Foundation, either version 3 of the License,
// * or (at your option) any later version.
// *
// * A copy of the GNU Affero General Public License is distributed in the root
// * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
// * see <http://www.gnu.org/licenses/>.
// * 
// * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
// * in author tags. All rights reserved.
// */
//package org.integratedmodelling.klab.utils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.io.input.ReversedLinesFileReader;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//
///**
// * The Class FileUtils.
// *
// * @author ferdinando.villa
// * @version $Id: $Id
// */
//public class FileUtils extends org.apache.commons.io.FileUtils {
//
//	/**
//	 * Create a md5sum file digest for a directory in a directory.
//	 *
//	 * @param directory the directory
//	 * @param fileName  the file name
//	 * @throws KlabException the klab exception
//	 */
//	public static void createMD5Digest(File directory, String fileName) throws KlabException {
//
//		if (!directory.exists() || !directory.canWrite() || !directory.isDirectory()) {
//			return;
//		}
//
//		List<String> digest = new ArrayList<>();
//		addFilesToMD5Digest(directory, digest, ".");
//
//		try {
//			writeLines(new File(directory + File.separator + fileName), digest);
//		} catch (IOException e) {
//			throw new KlabIOException(e);
//		}
//	}
//
//	private static void addFilesToMD5Digest(File directory, List<String> digest, String pathPrefix)
//			throws KlabException {
//
//		for (File f : directory.listFiles()) {
//
//			if (f.isDirectory()) {
//				addFilesToMD5Digest(f, digest, pathPrefix + "/" + MiscUtilities.getFileName(f.toString()));
//			} else {
//				try (FileInputStream fis = new FileInputStream(f)) {
//					String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
//					digest.add(md5 + "  " + pathPrefix + "/" + MiscUtilities.getFileName(f.toString()));
//				} catch (IOException e) {
//					throw new KlabIOException(e);
//				}
//			}
//		}
//	}
//	
//	public static String getFileHash(File file) {
//	    String ret = "";
//	    try (FileInputStream fis = new FileInputStream(file)) {
//            ret = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
//	    } catch (IOException e) {
//            throw new KlabIOException(e);
//        }
//	    return ret;
//	}
//	
//
//	/**
//	 * Assume file contains a path and defines a file in it, and ensure that all
//	 * directories up to last file name exist.
//	 *
//	 * @param file the file
//	 */
//	public static void makeDirsUpToFile(File file) {
//		String path = MiscUtilities.getFilePath(file.toString());
//		if (!path.isEmpty()) {
//			new File(path).mkdirs();
//		}
//	}
//
//	/**
//	 * Given a file and a set of extensions, return a collection containing the
//	 * original file plus any other file having any of the passed extensions and the
//	 * same name as the original, after ensuring that it exists alongside the
//	 * original.
//	 * 
//	 * @param original
//	 * @param extensions
//	 * @return the original file and all sidecar files
//	 */
//	public static Collection<File> getSidecarFiles(File original, Collection<String> extensions) {
//		Set<File> ret = new HashSet<>();
//		ret.add(original);
//		String base = MiscUtilities.getFilePath(original.toString()) + File.separator
//				+ MiscUtilities.getFileBaseName(original);
//		for (String extension : extensions) {
//			File sidecar = new File(base + "." + extension);
//			if (sidecar.exists() && sidecar.isFile()) {
//				ret.add(sidecar);
//			}
//		}
//		return ret;
//	}
//
//	public static void main(String[] args) {
//		for (String dio : tailFile(new File("C:\\setup.log"), 5)) {
//			System.out.println(dio);
//		}
//	}
//
//	public static final List<String> tailFile(File file, final int noOfLines) {
//		List<String> ret = new ArrayList<>(noOfLines);
//		try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file)) {
//			for (int i = 0; i < noOfLines; i++) {
//				String line = reader.readLine();
//				if (line == null) {
//					break;
//				}
//				ret.add(0, line);
//			}
//		} catch (IOException e) {
//			throw new KlabIOException(e);
//		}
//		return ret;
//	}
//
//}
