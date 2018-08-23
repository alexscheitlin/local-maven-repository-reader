package ch.scheitlin.alex.maven;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class LocalMavenRepositoryReader {
    private static final String userHome = System.getProperty("user.home");
    private static final String localMavenRepository = userHome + "\\.m2\\repository";

    /**
     * Checks whether a specific group exists in the local maven repository (located at the '.m2/repository' folder
     * within the 'user.home' directory).
     *
     * @param groupId the id of the group as specified in the {@code <groupId></groupId>} element of the respective
     *                pom.xml file
     * @return {@code true} if the specified group exists in the local maven repository, {@code false} if not
     */
    public static boolean doesGroupExist(String groupId) {
        String groupPath = LocalMavenRepositoryReader.localMavenRepository;
        for (String subGroup : groupId.split("\\.")) {
            if (doesDirectoryContainSubDirectory(groupPath, subGroup)) {
                groupPath += "\\" + subGroup;
            } else {
                return false;
            }
        }

        return true;
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
