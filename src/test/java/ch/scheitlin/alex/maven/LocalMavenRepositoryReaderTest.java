package ch.scheitlin.alex.maven;

import org.junit.Assert;
import org.junit.Test;

public class LocalMavenRepositoryReaderTest {
    @Test
    public void doesGroupExist_shouldExist() {
        // assign variables with test data
        String groupID = "org.apache.maven.plugins";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesGroupExist(groupID);

        // assert result
        Assert.assertTrue(result);
    }

    @Test
    public void doesGroupExist_shouldNotExist() {
        // assign variables with test data
        String groupID = "org.apache.maven.p";

        // execute methods to be tested
        boolean result = LocalMavenRepositoryReader.doesGroupExist(groupID);

        // assert result
        Assert.assertFalse(result);
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