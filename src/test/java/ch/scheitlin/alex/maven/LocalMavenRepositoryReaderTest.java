package ch.scheitlin.alex.maven;

import org.junit.Assert;
import org.junit.Test;

public class LocalMavenRepositoryReaderTest {
    /*
    // This tests won't succeed in every environment because either different artifacts and versions are available or
    // the .m2 directory is not located at the same path.
    @Test
    public void doesGroupExist_shouldExist() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesGroupExist(groupId);

        // assert result
        Assert.assertTrue(result);
    }

    @Test
    public void doesGroupExist_shouldNotExist() {
        // assign variables with test data
        String groupId = "org.apache.maven.p";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesGroupExist(groupId);

        // assert result
        Assert.assertFalse(result);
    }

    @Test
    public void getNonExistingSubGroup_shouldGetSubGroup() {
        // assign variables with test data
        String groupId = "org.apache.maven.p";
        String expectedSubGroup = "p";

        // execute methods to be tested
        String actualSubGroup = LocalMavenRepositoryReader.getNonExistingSubGroup(groupId);

        // assert result
        Assert.assertEquals(expectedSubGroup, actualSubGroup);
    }

    @Test
    public void getNonExistingSubGroup_shouldNotGetSubGroup() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";

        // execute methods to be tested
        String actualSubGroup = LocalMavenRepositoryReader.getNonExistingSubGroup(groupId);

        // assert result
        Assert.assertNull(actualSubGroup);
    }

    @Test
    public void doesArtifactExist_shouldExist() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";
        String artifactId = "maven-clean-plugin";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesArtifactExist(groupId, artifactId);

        // assert result
        Assert.assertTrue(result);
    }

    @Test
    public void doesArtifactExist_shouldNotExist() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";
        String artifactId = "m";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesArtifactExist(groupId, artifactId);

        // assert result
        Assert.assertFalse(result);
    }

    @Test
    public void doesVersionExist_shouldExist() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";
        String artifactId = "maven-clean-plugin";
        String version = "2.6.1";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesVersionExist(groupId, artifactId, version);

        // assert result
        Assert.assertTrue(result);
    }

    @Test
    public void doesVersionExist_shouldNotExist() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";
        String artifactId = "m";
        String version = "-1";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesVersionExist(groupId, artifactId, version);

        // assert result
        Assert.assertFalse(result);
    }
    */

    @Test
    public void getExpectedGroupPath() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";
        String expectedGroupPath = LocalMavenRepositoryReader.LOCAL_MAVEN_REPOSITORY + "\\org\\apache\\maven\\plugins";

        // execute methods to be tested
        String actualGroupPath = LocalMavenRepositoryReader.getExpectedGroupPath(groupId);

        // assert result
        Assert.assertEquals(expectedGroupPath, actualGroupPath);

    }

    @Test
    public void getExpectedArtifactPath() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";
        String artifactId = "maven-clean-plugin";
        String expectedArtifactPath = LocalMavenRepositoryReader.LOCAL_MAVEN_REPOSITORY + "\\org\\apache\\maven\\plugins\\maven-clean-plugin";

        // execute methods to be tested
        String actualArtifactPath = LocalMavenRepositoryReader.getExpectedArtifactPath(groupId, artifactId);

        // assert result
        Assert.assertEquals(expectedArtifactPath, actualArtifactPath);
    }

    @Test
    public void getExpectedVersionPath() {
        // assign variables with test data
        String groupId = "org.apache.maven.plugins";
        String artifactId = "maven-clean-plugin";
        String version = "2.6.1";
        String expectedVersionPath = LocalMavenRepositoryReader.LOCAL_MAVEN_REPOSITORY + "\\org\\apache\\maven\\plugins\\maven-clean-plugin\\2.6.1";

        // execute methods to be tested
        String actualVersionPath = LocalMavenRepositoryReader.getExpectedVersionPath(groupId, artifactId, version);

        // assert result
        Assert.assertEquals(expectedVersionPath, actualVersionPath);
    }

    @Test
    public void getStringsWithEnding_containsOne() {
        // assign variables with test data
        String[] strings = {"file.a", "file.b", "file.c"};
        String ending = "a";
        String[] expectedStrings = { "file.a" };    // in same order as they occur

        // execute methods to be tested
        String[] actualStrings = LocalMavenRepositoryReader.getStringsWithEnding(strings, ending);

        // assert result
        Assert.assertEquals(expectedStrings.length, actualStrings.length);
        for (int i = 0 ; i < expectedStrings.length; i++) {
            Assert.assertEquals(expectedStrings[i], actualStrings[i]);
        }
    }

    @Test
    public void getStringsWithEnding_containsNone() {
        // assign variables with test data
        String[] strings = {"file.a", "file.b", "file.c"};
        String ending = "d";
        String[] expectedStrings = {};

        // execute methods to be tested
        String[] actualStrings = LocalMavenRepositoryReader.getStringsWithEnding(strings, ending);

        // assert result
        Assert.assertEquals(expectedStrings.length, actualStrings.length);
    }

    @Test
    public void getStringsWithDifferentEnding_containsOne() {
        // assign variables with test data
        String[] strings = {"file.a", "file.a", "file.b"};
        String ending = "a";
        String[] expectedStrings = { "file.b" };    // in same order as they occur

        // execute methods to be tested
        String[] actualStrings = LocalMavenRepositoryReader.getStringsWithDifferentEnding(strings, ending);

        // assert result
        Assert.assertEquals(expectedStrings.length, actualStrings.length);
        for (int i = 0 ; i < expectedStrings.length; i++) {
            Assert.assertEquals(expectedStrings[i], actualStrings[i]);
        }
    }

    @Test
    public void getStringsWithDifferentEnding_containsNone() {
        // assign variables with test data
        String[] strings = {"file.a", "file.a", "file.a"};
        String ending = "a";
        String[] expectedStrings = {};

        // execute methods to be tested
        String[] actualStrings = LocalMavenRepositoryReader.getStringsWithDifferentEnding(strings, ending);

        // assert result
        Assert.assertEquals(expectedStrings.length, actualStrings.length);
    }

    @Test
    public void doesArrayContain_shouldContain() {
        // assign variables with test data
        String[] array = {"value1", "value2", "value3"};
        String value = "value3";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesArrayContain(array, value);

        // assert result
        Assert.assertTrue(result);
    }

    @Test
    public void doesArrayContain_shouldNotContain() {
        // assign variables with test data
        String[] array = {"value1", "value2", "value3"};
        String value = "value4";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesArrayContain(array, value);

        // assert result
        Assert.assertFalse(result);
    }

    @Test
    public void doesArrayContain_emptyArray() {
        // assign variables with test data
        String[] array = {};
        String value = "value";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesArrayContain(array, value);

        // assert result
        Assert.assertFalse(result);
    }
}