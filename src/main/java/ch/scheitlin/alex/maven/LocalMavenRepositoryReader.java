package ch.scheitlin.alex.maven;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * Provides functions to access check whether groups exists in the local maven repository (located at the
 * '.m2/repository' folder within the 'user.home' directory).
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
        String nonExistingSubGroup = getNonExistingSubGroup(groupId);

        return nonExistingSubGroup == null;
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
        if (!doesGroupExist(groupId)) {
            return false;
        }

        return doesDirectoryContainSubDirectory(getExpectedGroupPath(groupId), artifactId);
    }

    /**
     * Checks whether a specific version of specific artifact of a specific group exists in the local maven repository.
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
        if (!doesArtifactExist(groupId, artifactId)) {
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
     * Gets all directories located at a specified path.
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
