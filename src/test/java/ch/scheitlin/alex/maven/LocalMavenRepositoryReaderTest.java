package ch.scheitlin.alex.maven;

import org.junit.Assert;
import org.junit.Test;

public class LocalMavenRepositoryReaderTest {
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

    /*
    // This test won't succeed in every environment because of the different versions available.
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
    */

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