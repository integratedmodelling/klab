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
//import java.io.IOException;
//import org.apache.commons.io.FileUtils;
//import org.eclipse.jgit.api.CreateBranchCommand;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.LsRemoteCommand;
//import org.eclipse.jgit.api.PullCommand;
//import org.eclipse.jgit.api.errors.GitAPIException;
//import org.eclipse.jgit.internal.storage.file.FileRepository;
//import org.eclipse.jgit.lib.Repository;
//import org.integratedmodelling.klab.Logging;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//
//// TODO: Auto-generated Javadoc
///**
// * The Class GitUtils.
// *
// * @author ferdinando.villa
// * @version $Id: $Id
// */
//public class GitUtils {
//    
//    public static final String MAIN_BRANCH = "master"; 
//
//	/**
//	 * Clone.
//	 *
//	 * @param gitUrl
//	 *            the git url
//	 * @param directory
//	 *            the directory
//	 * @param removeIfExisting
//	 *            the remove if existing
//	 * @return the string
//	 * @throws KlabException
//	 *             the klab exception
//	 */
//	public static String clone(String gitUrl, File directory, boolean removeIfExisting) throws KlabException {
//
//		String dirname = MiscUtilities.getURLBaseName(gitUrl);
//
//		File pdir = new File(directory + File.separator + dirname);
//		if (pdir.exists()) {
//			if (removeIfExisting) {
//				try {
//					FileUtils.deleteDirectory(pdir);
//				} catch (Throwable e) {
//					throw new KlabIOException(e);
//				}
//			} else {
//				throw new KlabIOException("git clone: directory " + pdir + " already exists");
//			}
//		}
//
//		String[] pdefs = gitUrl.split("#");
//		String branch;
//		if (pdefs.length < 2) {
//		    branch = MAIN_BRANCH;
//		} else {
//		    branch = GitUtils.branchExists(pdefs[0], pdefs[1]) ? pdefs[1] : MAIN_BRANCH;
//		}
//		String url = pdefs[0];
//
//		Logging.INSTANCE.info("cloning Git repository " + url + " branch " + branch + " ...");
//
//		try (Git result = Git.cloneRepository().setURI(url).setBranch(branch).setDirectory(pdir).call()) {
//
//			Logging.INSTANCE.info("cloned Git repository: " + result.getRepository());
//
//			if (!branch.equals(MAIN_BRANCH)) {
//				result.checkout().setName(branch).setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
//						.setStartPoint("origin/" + branch).call();
//
//				Logging.INSTANCE.info("switched repository: " + result.getRepository() + " to branch " + branch);
//			}
//
//		} catch (Throwable e) {
//			throw new KlabIOException(e);
//		}
//
//		return dirname;
//	}
//
//	/**
//	 * Pull local repository in passed directory.
//	 *
//	 * @param localRepository
//	 *            main directory (containing .git/)
//	 * @throws KlabException
//	 *             the klab exception
//	 */
//	public static void pull(File localRepository) throws KlabException {
//
//		try (Repository localRepo = new FileRepository(localRepository + File.separator + ".git")) {
//			try (Git git = new Git(localRepo)) {
//
//				Logging.INSTANCE.info("fetch/merge changes in repository: " + git.getRepository());
//
//				PullCommand pullCmd = git.pull();
//				pullCmd.call();
//
//			} catch (Throwable e) {
//				throw new KlabIOException(
//						"error pulling repository " + localRepository + ": " + e.getLocalizedMessage());
//			}
//		} catch (IOException e) {
//			throw new KlabIOException(e);
//		}
//	}
//
//	/**
//	 * If a Git repository with the repository name corresponding to the URL exists
//	 * in gitDirectory, pull it from origin; otherwise clone it from the passed Git
//	 * URL.
//	 * 
//	 * TODO: Assumes branch is already set correctly if repo is pulled. Should check
//	 * branch and checkout if necessary.
//	 *
//	 * @param gitUrl
//	 *            the git url
//	 * @param gitDirectory
//	 *            the git directory
//	 * @return the string
//	 * @throws KlabException
//	 *             the klab exception
//	 */
//	public static String requireUpdatedRepository(String gitUrl, File gitDirectory) throws KlabException {
//
//		String repositoryName = MiscUtilities.getURLBaseName(gitUrl);
//
//		File repoDir = new File(gitDirectory + File.separator + repositoryName);
//		File gitDir = new File(repoDir + File.separator + ".git");
//
//		if (gitDir.exists() && gitDir.isDirectory() && gitDir.canRead() && repoDir.exists()) {
//
//			pull(repoDir);
//			/*
//			 * TODO check branch and switch/pull if necessary
//			 */
//		} else {
//			if (gitDir.exists()) {
//				FileUtils.deleteQuietly(gitDir);
//			}
//			clone(gitUrl, gitDirectory, true);
//		}
//
//		return repositoryName;
//	}
//
//	/**
//	 * Checks if is remote git URL.
//	 *
//	 * @param string
//	 *            the string
//	 * @return a boolean.
//	 */
//	public static boolean isRemoteGitURL(String string) {
//		return string.startsWith("http:") || string.startsWith("git:") || string.startsWith("https:")
//				|| string.startsWith("git@");
//	}
//	
//	/**
//	 * Check if remote branch exists
//	 * @param gitUrl the remote repository
//	 * @param branch the branch (without refs/heads/)
//	 * @return true if branch exists
//	 */
//	public static boolean branchExists(String gitUrl, String branch) {
//	    final LsRemoteCommand lsCmd = new LsRemoteCommand(null);
//	    lsCmd.setRemote(gitUrl);
//	    try {
//            return lsCmd.call().stream().filter(ref -> ref.getName().equals("refs/heads/"+branch)).count() == 1;
//        } catch (GitAPIException e) {
//            e.printStackTrace();
//            return false;
//        }
//	}
//
//	/**
//	 * The main method.
//	 *
//	 * @param args
//	 *            the arguments
//	 * @throws Exception
//	 *             the exception
//	 */
//	public static void main(String[] args) throws Exception {
//	    String u = requireUpdatedRepository("git@bitbucket.org:ariesteam/im.data.git#bfo",
//				new File(System.getProperty("user.home")));
//		System.out.println("Got repo " + u);
//	}
//
//}
