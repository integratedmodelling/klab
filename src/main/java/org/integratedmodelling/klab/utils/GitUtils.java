package org.integratedmodelling.klab.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;

public class GitUtils {

    public static String clone(String gitUrl, File directory, boolean removeIfExisting) throws KlabException {

        String dirname = MiscUtilities.getURLBaseName(gitUrl);

        File pdir = new File(directory + File.separator + dirname);
        if (pdir.exists()) {
            if (removeIfExisting) {
                try {
                    FileUtils.deleteDirectory(pdir);
                } catch (Throwable e) {
                    throw new KlabRuntimeException(e);
                }
            } else {
                throw new KlabIOException("git clone: directory " + pdir + " already exists");
            }
        }

        String[] pdefs = gitUrl.split("#");
        String branch = pdefs.length < 2 ? "master" : pdefs[1];
        String url = pdefs[0];

        Klab.INSTANCE.info("cloning Git repository " + url + " branch " + branch + " ...");

        try (Git result = Git.cloneRepository()
                .setURI(url)
                .setBranch(branch)
                .setDirectory(pdir)
                .call()) {

            Klab.INSTANCE.info("cloned Git repository: " + result.getRepository());

            if (!branch.equals("master")) {
                result.checkout().setName(branch)
                        .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                        .setStartPoint("origin/" + branch).call();

                Klab.INSTANCE.info("switched repository: " + result.getRepository() + " to branch " + branch);
            }

        } catch (Throwable e) {
            throw new KlabRuntimeException(e);
        }

        return dirname;
    }

    /**
     * Pull local repository in passed directory.
     * 
     * @param localRepository main directory (containing .git/)
     * @throws KlabException
     */
    public static void pull(File localRepository) throws KlabException {

        try (Repository localRepo = new FileRepository(localRepository + File.separator + ".git")) {
            try (Git git = new Git(localRepo)) {

                Klab.INSTANCE.info("fetch/merge changes in repository: " + git.getRepository());

                PullCommand pullCmd = git.pull();
                pullCmd.call();

            } catch (Throwable e) {
                throw new KlabIOException("error pulling repository " + localRepository + ": " + e.getLocalizedMessage());
            }
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    /**
     * If a Git repository with the repository name corresponding to the URL exists in
     * gitDirectory, pull it from origin; otherwise clone it from the passed Git URL.
     * 
     * TODO: Assumes branch is already set correctly if repo is pulled. Should check
     * branch and checkout if necessary.
     * 
     * @param gitUrl
     * @param repositoryName
     * @param gitDirectory
     * @throws KlabException
     */
    public static String requireUpdatedRepository(String gitUrl, File gitDirectory) throws KlabException {

        String repositoryName = MiscUtilities.getURLBaseName(gitUrl);

        File repoDir = new File(gitDirectory + File.separator + repositoryName);
        File gitDir = new File(repoDir + File.separator + ".git");

        if (gitDir.exists() && gitDir.isDirectory() && gitDir.canRead()) {
            pull(repoDir);
            /*
             * TODO check branch and switch/pull if necessary
             */
        } else {
            clone(gitUrl, gitDirectory, true);
        }

        return repositoryName;
    }

    public static boolean isRemoteGitURL(String string) {
        return string.startsWith("http:") || string.startsWith("git:") || string.startsWith("https:")
                || string.startsWith("git@");
    }
    
    public static void main(String[] args) throws Exception {
        String u = requireUpdatedRepository("git@bitbucket.org:ariesteam/im.data.git#bfo", new File(System.getProperty("user.home")));
        System.out.println("Got repo " + u);
    }

}
