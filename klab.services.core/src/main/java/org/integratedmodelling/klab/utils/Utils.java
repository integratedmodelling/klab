package org.integratedmodelling.klab.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.integratedmodelling.klab.api.exceptions.KIOException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.logging.Logging;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Utils extends org.integratedmodelling.klab.api.utils.Utils {

    public static class YAML {
        
        static ObjectMapper defaultMapper;
        
        static {
            defaultMapper = new ObjectMapper(new YAMLFactory());
        }
        
        /**
         * Load an object from an input stream.
         * 
         * @param is the input stream
         * @param cls the class
         * @return the object
         * @throws KlabIOException
         */
        public static <T> T load(InputStream url, Class<T> cls) throws KlabIOException {
            try {
                return defaultMapper.readValue(url, cls);
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        /**
         * Load an object from a file.
         * 
         * @param file
         * @param cls
         * @return the object
         * @throws KlabIOException
         */
        public static <T> T load(File file, Class<T> cls) throws KlabIOException {
            try {
                return defaultMapper.readValue(file, cls);
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        /**
         * Load an object from a URL.
         * 
         * @param url
         * @param cls
         * @return the object
         * @throws KlabIOException
         */
        public static <T> T load(URL url, Class<T> cls) throws KlabIOException {
            try {
                return defaultMapper.readValue(url, cls);
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        /**
         * Serialize an object to a file.
         * 
         * @param object
         * @param outFile
         * @throws KlabIOException
         */
        public static void save(Object object, File outFile) throws KlabIOException {
            try {
                defaultMapper.writeValue(outFile, object);
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        public static String asString(Object object) {
            try {
                return defaultMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("serialization failed: " + e.getMessage());
            }
        }
        
    }
    
    public static class Json {

        static ObjectMapper defaultMapper;

        static {
            defaultMapper = new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                    .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).enable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            defaultMapper.getSerializerProvider().setNullKeySerializer(new NullKeySerializer());
        }

        static class NullKeySerializer extends StdSerializer<Object> {

            private static final long serialVersionUID = 7120301608140961908L;

            public NullKeySerializer() {
                this(null);
            }

            public NullKeySerializer(Class<Object> t) {
                super(t);
            }

            @Override
            public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeFieldName("");
            }
        }

        /**
         * Default conversion for a map object.
         *
         * @param node the node
         * @return the map
         */
        @SuppressWarnings("unchecked")
        public static Map<String, Object> asMap(JsonNode node) {
            return defaultMapper.convertValue(node, Map.class);
        }

        /**
         * Default conversion, use within custom deserializers to "normally" deserialize an object.
         *
         * @param <T> the generic type
         * @param node the node
         * @param cls the cls
         * @return the t
         */
        public static <T> T as(JsonNode node, Class<T> cls) {
            return defaultMapper.convertValue(node, cls);
        }

        /**
         * Convert node to list of type T.
         *
         * @param <T> the generic type
         * @param node the node
         * @param cls the cls
         * @return the list
         */
        public static <T> List<T> asList(JsonNode node, Class<T> cls) {
            return defaultMapper.convertValue(node, new TypeReference<List<T>>(){
            });
        }

        public static <T> List<T> asList(JsonNode node, Class<T> cls, ObjectMapper mapper) {
            return mapper.convertValue(node, new TypeReference<List<T>>(){
            });
        }

        @SuppressWarnings("unchecked")
        public static <T> T parseObject(String text, Class<T> cls) {
            try {
                return (T) defaultMapper.readerFor(cls).readValue(text);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        /**
         * Convert node to list of type T.
         *
         * @param <T> the generic type
         * @param node the node
         * @param cls the cls
         * @return the sets the
         */
        public static <T> Set<T> asSet(JsonNode node, Class<T> cls) {
            return defaultMapper.convertValue(node, new TypeReference<Set<T>>(){
            });
        }

        @SuppressWarnings("unchecked")
        public static <T> T cloneObject(T object) {
            return (T) parseObject(printAsJson(object), object.getClass());
        }
        
        /**
         * Load an object from an input stream.
         * 
         * @param is the input stream
         * @param cls the class
         * @return the object
         * @throws KlabIOException
         */
        public static <T> T load(InputStream url, Class<T> cls) throws KlabIOException {
            try {
                return defaultMapper.readValue(url, cls);
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        /**
         * Load an object from a file.
         * 
         * @param file
         * @param cls
         * @return the object
         * @throws KlabIOException
         */
        public static <T> T load(File file, Class<T> cls) throws KlabIOException {
            try {
                return defaultMapper.readValue(file, cls);
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        /**
         * Load an object from a URL.
         * 
         * @param url
         * @param cls
         * @return the object
         * @throws KlabIOException
         */
        public static <T> T load(URL url, Class<T> cls) throws KlabIOException {
            try {
                return defaultMapper.readValue(url, cls);
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        /**
         * Serialize an object to a file.
         * 
         * @param object
         * @param outFile
         * @throws KlabIOException
         */
        public static void save(Object object, File outFile) throws KlabIOException {
            try {
                defaultMapper.writeValue(outFile, object);
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        public static String asString(Object object) {
            try {
                return defaultMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("serialization failed: " + e.getMessage());
            }
        }

        /**
         * Serialize the passed object as JSON and pretty-print the resulting code.
         *
         * @param object the object
         * @return the string
         */
        public static String printAsJson(Object object) {

            ObjectMapper om = new ObjectMapper();
            om.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
            om.enable(SerializationFeature.WRITE_NULL_MAP_VALUES); // pretty print
            om.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED); // pretty print
            om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

            try {
                return om.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("serialization failed: " + e.getMessage());
            }
        }
        
        /**
         * Serialize the passed object as JSON and pretty-print the resulting code.
         *
         * @param object the object
         * @param file 
         * @return the string
         */
        public static void printAsJson(Object object, File file) {

            ObjectMapper om = new ObjectMapper();
            om.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
            om.enable(SerializationFeature.WRITE_NULL_MAP_VALUES); // pretty print
            om.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED); // pretty print
            om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

            try {
                om.writeValue(file, object);
            } catch (Exception e) {
                throw new IllegalArgumentException("serialization failed: " + e.getMessage());
            }
        }

        /**
         * Convert a map resulting from parsing generic JSON (or any other source) to the passed type.
         * 
         * @param payload
         * @param cls
         * @return the converted object
         */
        public static <T> T convertMap(Map<?, ?> payload, Class<T> cls) {
            return defaultMapper.convertValue(payload, cls);
        }

    }
    
    public static class Files extends org.integratedmodelling.klab.api.utils.Utils.Files {

        public static void deleteDirectory(File pdir) {
            try {
                FileUtils.deleteDirectory(pdir);
            } catch (IOException e) {
                throw new KIOException(e);
            }
        }
        
        public static boolean deleteQuietly(File pdir) {
            return FileUtils.deleteQuietly(pdir);
        }
        
    }
    
    public static class Markdown {
        
    }
    
    public static class Git {

        public static final String MAIN_BRANCH = "master"; 

        /**
         * Clone.
         *
         * @param gitUrl
         *            the git url
         * @param directory
         *            the directory
         * @param removeIfExisting
         *            the remove if existing
         * @return the string
         * @throws KlabException
         *             the klab exception
         */
        public static String clone(String gitUrl, File directory, boolean removeIfExisting) throws KlabException {

            String dirname = MiscUtilities.getURLBaseName(gitUrl);

            File pdir = new File(directory + File.separator + dirname);
            if (pdir.exists()) {
                if (removeIfExisting) {
                    try {
                        Files.deleteDirectory(pdir);
                    } catch (Throwable e) {
                        throw new KlabIOException(e);
                    }
                } else {
                    throw new KlabIOException("git clone: directory " + pdir + " already exists");
                }
            }

            String[] pdefs = gitUrl.split("#");
            String branch;
            if (pdefs.length < 2) {
                branch = MAIN_BRANCH;
            } else {
                branch = branchExists(pdefs[0], pdefs[1]) ? pdefs[1] : MAIN_BRANCH;
            }
            String url = pdefs[0];

            Logging.INSTANCE.info("cloning Git repository " + url + " branch " + branch + " ...");

            try (org.eclipse.jgit.api.Git result = org.eclipse.jgit.api.Git.cloneRepository().setURI(url).setBranch(branch).setDirectory(pdir).call()) {

                Logging.INSTANCE.info("cloned Git repository: " + result.getRepository());

                if (!branch.equals(MAIN_BRANCH)) {
                    result.checkout().setName(branch).setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                            .setStartPoint("origin/" + branch).call();

                    Logging.INSTANCE.info("switched repository: " + result.getRepository() + " to branch " + branch);
                }

            } catch (Throwable e) {
                throw new KlabIOException(e);
            }

            return dirname;
        }

        /**
         * Pull local repository in passed directory.
         *
         * @param localRepository
         *            main directory (containing .git/)
         * @throws KlabException
         *             the klab exception
         */
        public static void pull(File localRepository) throws KlabException {

            try (Repository localRepo = new FileRepository(localRepository + File.separator + ".git")) {
                try (org.eclipse.jgit.api.Git git = new org.eclipse.jgit.api.Git(localRepo)) {

                    Logging.INSTANCE.info("fetch/merge changes in repository: " + git.getRepository());

                    PullCommand pullCmd = git.pull();
                    pullCmd.call();

                } catch (Throwable e) {
                    throw new KlabIOException(
                            "error pulling repository " + localRepository + ": " + e.getLocalizedMessage());
                }
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
        }

        /**
         * If a Git repository with the repository name corresponding to the URL exists
         * in gitDirectory, pull it from origin; otherwise clone it from the passed Git
         * URL.
         * 
         * TODO: Assumes branch is already set correctly if repo is pulled. Should check
         * branch and checkout if necessary.
         *
         * @param gitUrl
         *            the git url
         * @param gitDirectory
         *            the git directory
         * @return the string
         * @throws KlabException
         *             the klab exception
         */
        public static String requireUpdatedRepository(String gitUrl, File gitDirectory) throws KlabException {

            String repositoryName = MiscUtilities.getURLBaseName(gitUrl);

            File repoDir = new File(gitDirectory + File.separator + repositoryName);
            File gitDir = new File(repoDir + File.separator + ".git");

            if (gitDir.exists() && gitDir.isDirectory() && gitDir.canRead() && repoDir.exists()) {

                pull(repoDir);
                /*
                 * TODO check branch and switch/pull if necessary
                 */
            } else {
                if (gitDir.exists()) {
                    Files.deleteQuietly(gitDir);
                }
                clone(gitUrl, gitDirectory, true);
            }

            return repositoryName;
        }

        /**
         * Checks if is remote git URL.
         *
         * @param string
         *            the string
         * @return a boolean.
         */
        public static boolean isRemoteGitURL(String string) {
            return string.startsWith("http:") || string.startsWith("git:") || string.startsWith("https:")
                    || string.startsWith("git@");
        }
        
        /**
         * Check if remote branch exists
         * @param gitUrl the remote repository
         * @param branch the branch (without refs/heads/)
         * @return true if branch exists
         */
        public static boolean branchExists(String gitUrl, String branch) {
            final LsRemoteCommand lsCmd = new LsRemoteCommand(null);
            lsCmd.setRemote(gitUrl);
            try {
                return lsCmd.call().stream().filter(ref -> ref.getName().equals("refs/heads/"+branch)).count() == 1;
            } catch (GitAPIException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    
    public static class Maps {
                
    }
    
    public static class Templates {
        
    }
    
    public static class Wildcards {
        
    }
    
    public static class Network {
        
    }
    
}
