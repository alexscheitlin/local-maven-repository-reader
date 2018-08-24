package ch.scheitlin.alex.maven;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Provides functions to access data from the local maven repository located at the '.m2/repository' folder within the 'user.home' directory.</p>
 * <p>The following is possible:</p>
 * <ul>
 * <li>check whether a specific group exists or not</li>
 * <li>get the part of a specific groupId that does not exist</li>
 * <li>check whether a specific artifact (of a group) exists or not</li>
 * <li>get a list of all available artifacts of a specific group</li>
 * <li>check whether a specific version (of a artifact of a group) exists or not</li>
 * <li>get a list of all available versions of a specific artifact</li>
 * </ul>
 */
public class LocalMavenRepositoryReader {
    static final String USER_HOME = System.getProperty("user.home");
    static final String LOCAL_MAVEN_REPOSITORY = USER_HOME + "\\.m2\\repository";

    /**
     * Checks whether a specific group exists in the local maven repository.
     *
     * @param groupId the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                pom.xml file of the project
     * @return {@code true} if the specified group exists in the local maven repository, {@code false} if not
     */
    public static boolean doesGroupExist(String groupId) {
        // check whether the folder for the group exists
        if (!doesGroupFolderExist(groupId)) {
            return false;
        }

        // check whether an artifact exists
        return getArtifactsOfGroup(groupId).length != 0;
    }

    /**
     * Gets the part of a specific group id that does not exist in the local maven repository.
     *
     * @param groupId the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                pom.xml file of the project
     * @return the part of the specified group id that does not exist or {@code null} if the group exists
     */
    public static String getNonExistingSubGroup(String groupId) {
        String groupPath = LocalMavenRepositoryReader.LOCAL_MAVEN_REPOSITORY;
        for (String subGroup : groupId.split("\\.")) {
            if (doesDirectoryContainSubDirectory(groupPath, subGroup)) {
                groupPath += "\\" + subGroup;
            } else {
                return subGroup;
            }
        }

        return null;
    }

    /**
     * Checks whether a specific artifact of a specific group exists in the local maven repository.
     *
     * @param groupId    the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                   pom.xml file of the project
     * @param artifactId the id of the artifact as specified in the {@code <artifactId></artifactId>} element of the
     *                   respective pom.xml file of the project
     * @return {@code true} if the specified artifact of the specified group exists in the local maven repository,
     * {@code false} if not
     */
    public static boolean doesArtifactExist(String groupId, String artifactId) {
        // check whether the folder for the artifact exists
        if (!doesArtifactFolderExist(groupId, artifactId)) {
            return false;
        }

        // check whether a version exists
        return getArtifactVersions(groupId, artifactId).length != 0;
    }

    /**
     * Gets a list of all in the local maven repository existing artifacts of a specific group.
     *
     * @param groupId the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                pom.xml file of the project
     * @return {@code String Array} with all artifacts of the specified group or an empty {@code String Array} if no
     * artifacts are available
     */
    public static String[] getArtifactsOfGroup(String groupId) {
        String[] expectedArtifacts = getDirectories(getExpectedGroupPath(groupId));
        List<String> artifacts = new ArrayList<String>();
        for (String expectedArtifact : expectedArtifacts) {
            if (doesArtifactExist(groupId, expectedArtifact)) {
                artifacts.add(expectedArtifact);
            }
        }
        return artifacts.size() == 0 ? new String[]{} : artifacts.toArray(new String[0]);
    }

    /**
     * <p>
     * Checks whether a specific version of specific artifact of a specific group exists in the local maven
     * repository.
     * </p>
     * <p>
     * This function not only checks whether there exists a folder for the version but also checks whether there are
     * only files with the ending '.lastUpdated'. If so the version is not considered as existing in the local maven
     * repository.
     * </p>
     * <p>
     * The version is only considered existing in the local maven repository if for every file with the ending
     * '.lastUpdated' there is a file with the same name (considered as source file, e.g. jar or pom).
     * </p>
     *
     * @param groupId    the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                   pom.xml file of the project
     * @param artifactId the id of the artifact as specified in the {@code <artifactId></artifactId>} element of the
     *                   respective pom.xml file of the project
     * @param version    the version of the artifact as specified in the {@code <version></version>} element of the
     *                   respective pom.xml file of the project
     * @return {@code true} if the specified version of the specified artifact of the specified group exists in the
     * local maven repository, {@code false} if not
     */
    public static boolean doesVersionExist(String groupId, String artifactId, String version) {
        // check whether the folder for the version exists
        if (!doesVersionFolderExist(groupId, artifactId, version)) {
            return false;
        }

        // -> folder exists

        // check whether there are some files in the version folder
        String versionPath = getExpectedVersionPath(groupId, artifactId, version);
        String[] files = getFiles(versionPath);
        if (files.length == 0) {
            return false;
        }

        // -> folder exists and has at least one file

        // check whether '.lastUpdated' files are available
        String fileEnding = ".lastUpdated";
        String[] lastUpdatedFiles = getStringsWithEnding(files, fileEnding);

        if (lastUpdatedFiles.length == 0) {
            return true;
        }

        // -> folder exists and has at least one file with ending '.lastUpdated'

        // check whether all files are '.lastUpdated' files
        if (files.length == lastUpdatedFiles.length) {
            return false;
        }

        // -> folder exists and has at least one file with ending '.lastUpdated' and at least on file with a different
        //    ending

        // check whether for each '.lastUpdated' file there is also a corresponding source file
        String[] notLastUpdatedFiles = getStringsWithDifferentEnding(files, fileEnding);
        for (String lastUpdatedFile : lastUpdatedFiles) {
            boolean foundSourceFile = false;
            for (String notLastUpdatedFile : notLastUpdatedFiles) {
                if (notLastUpdatedFile.equals(lastUpdatedFile.replaceAll("\\.lastUpdated", ""))) {
                    foundSourceFile = true;
                    break;
                }
            }

            if (!foundSourceFile) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets a list of all in the local maven repository existing versions of a specific artifact of a specific group.
     *
     * @param groupId    the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                   pom.xml file of the project
     * @param artifactId the id of the artifact as specified in the {@code <artifactId></artifactId>} element of the
     *                   respective pom.xml file of the project
     * @return {@code String Array} with all versions of the specified artifact of the specified group or an empty
     * {@code String Array} if no versions are available
     */
    public static String[] getArtifactVersions(String groupId, String artifactId) {
        String[] expectedVersions = getDirectories(getExpectedArtifactPath(groupId, artifactId));
        List<String> versions = new ArrayList<String>();
        for (String expectedVersion : expectedVersions) {
            if (doesVersionExist(groupId, artifactId, expectedVersion)) {
                versions.add(expectedVersion);
            }
        }
        return versions.size() == 0 ? new String[]{} : versions.toArray(new String[0]);
    }

    /**
     * Checks whether the folder of a specific group exists.
     *
     * @param groupId the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                pom.xml file of the project
     * @return {@code true} if the folder exists, {@code false} if not
     */
    static boolean doesGroupFolderExist(String groupId) {
        String nonExistingSubGroup = getNonExistingSubGroup(groupId);

        return nonExistingSubGroup == null;
    }

    /**
     * Checks whether the folder of a specific artifact exists.
     *
     * @param groupId    the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                   pom.xml file of the project
     * @param artifactId the id of the artifact as specified in the {@code <artifactId></artifactId>} element of the
     *                   respective pom.xml file of the project
     * @return {@code true} if the folder exists, {@code false} if not
     */
    static boolean doesArtifactFolderExist(String groupId, String artifactId) {
        if (!doesGroupFolderExist(groupId)) {
            return false;
        }

        return doesDirectoryContainSubDirectory(getExpectedGroupPath(groupId), artifactId);
    }

    /**
     * Checks whether the folder of a specific version of a specific artifact exists.
     *
     * @param groupId    the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                   pom.xml file of the project
     * @param artifactId the id of the artifact as specified in the {@code <artifactId></artifactId>} element of the
     *                   respective pom.xml file of the project
     * @param version    the version of the artifact as specified in the {@code <version></version>} element of the
     *                   respective pom.xml file of the project
     * @return {@code true} if the folder exists, {@code false} if not
     */
    static boolean doesVersionFolderExist(String groupId, String artifactId, String version) {
        if (!doesArtifactFolderExist(groupId, artifactId)) {
            return false;
        }

        return doesDirectoryContainSubDirectory(getExpectedArtifactPath(groupId, artifactId), version);
    }

    /**
     * Gets the expected path of a specific group in the local maven repository. This does not mean that the directory
     * exists. The group id just gets converted to the expected path of this group.
     *
     * @param groupId the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                pom.xml file of the project
     * @return the expected path to the specified group
     */
    static String getExpectedGroupPath(String groupId) {
        return LocalMavenRepositoryReader.LOCAL_MAVEN_REPOSITORY + "\\" + groupId.replaceAll("\\.", "\\\\");
    }

    /**
     * Gets the expected path of a specific artifact in the local maven repository. This does not mean that the
     * directory exists. The artifact id just gets converted to the expected path of this artifact.
     *
     * @param groupId    the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                   pom.xml file of the project
     * @param artifactId the id of the artifact as specified in the {@code <artifactId></artifactId>} element of the
     *                   respective pom.xml file of the project
     * @return the expected path to the specified artifact
     */
    static String getExpectedArtifactPath(String groupId, String artifactId) {
        return getExpectedGroupPath(groupId) + "\\" + artifactId;
    }

    /**
     * Gets the expected path of a specific version of a specific artifact in the local maven repository. This does not
     * mean that the directory exists. The version just gets converted to the expected path of this version.
     *
     * @param groupId    the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                   pom.xml file of the project
     * @param artifactId the id of the artifact as specified in the {@code <artifactId></artifactId>} element of the
     *                   respective pom.xml file of the project
     * @param version    the version of the artifact as specified in the {@code <version></version>} element of the
     *                   respective pom.xml file of the project
     * @return the expected path to the specified version of the specified artifact
     */
    static String getExpectedVersionPath(String groupId, String artifactId, String version) {
        return getExpectedArtifactPath(groupId, artifactId) + "\\" + version;
    }

    /**
     * Checks whether a directory contains a specific sub directory.
     *
     * @param pathToDirectory the path to the directory to search for a specific sub directory
     * @param subDirectory    the sub directory to search within the given directory
     * @return {@code true} if the given directory contains the specified sub directory, {@code false} if not
     */
    private static boolean doesDirectoryContainSubDirectory(String pathToDirectory, String subDirectory) {
        return doesArrayContain(getDirectories(pathToDirectory), subDirectory);
    }

    /**
     * Gets all directories located at a specific path.
     *
     * @param path the path to get all directories from
     * @return a {@code String Array} with all directories located at the specified path
     */
    private static String[] getDirectories(String path) {
        return new File(path).list(
                new FilenameFilter() {
                    public boolean accept(File current, String name) {
                        return new File(current, name).isDirectory();
                    }
                }
        );
    }

    /**
     * Gets all files located at a specific path.
     *
     * @param path the path to get all files from
     * @return a {@code String Array} with all files located at the specified path
     */
    private static String[] getFiles(String path) {
        return new File(path).list(
                new FilenameFilter() {
                    public boolean accept(File current, String name) {
                        return new File(current, name).isFile();
                    }
                }
        );
    }

    /**
     * Gets all {@code String}s of a {@code String Array} having a specific ending.
     *
     * @param strings the {@code String}s to search through
     * @param ending  the ending a {@code String} needs to have
     * @return {@code String Array} with all {@code String}s having the specified ending or an empty
     * {@code String Array} if no {@code String} has the specified ending
     */
    static String[] getStringsWithEnding(String[] strings, String ending) {
        List<String> stringsWithEnding = new ArrayList<String>();
        for (String string : strings) {
            if (string.endsWith(ending)) {
                stringsWithEnding.add(string);
            }
        }

        return stringsWithEnding.toArray(new String[0]);
    }

    /**
     * Gets all {@code String}s of a {@code String Array} having not a specific ending.
     *
     * @param strings the {@code String}s to search through
     * @param ending  the ending a {@code String} does not need to have
     * @return {@code String Array} with all {@code String}s not having the specified ending or an empty
     * {@code String Array} if all {@code String}s have the specified ending
     */
    static String[] getStringsWithDifferentEnding(String[] strings, String ending) {
        List<String> stringsWithDifferentEnding = new ArrayList<String>();
        for (String string : strings) {
            if (!string.endsWith(ending)) {
                stringsWithDifferentEnding.add(string);
            }
        }

        return stringsWithDifferentEnding.toArray(new String[0]);
    }

    /**
     * Checks whether a {@code String Array} contains a specific {@code String}.
     *
     * @param array the array to check for containing a specific value
     * @param value the value to search within the given array
     * @return {@code true} if the given array contains the specified value, {@code false} if not
     */
    static boolean doesArrayContain(String[] array, String value) {
        return Arrays.asList(array).contains(value);
    }
}
