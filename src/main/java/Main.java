import ch.scheitlin.alex.maven.LocalMavenRepositoryReader;

public class Main {
    public static void main(String[] args) {
        String groupId = "org.apache.maven.plugins";
        String artifactId = "maven-clean-plugin";

        // search group
        System.out.println("Searching group: " + groupId);
        if (LocalMavenRepositoryReader.doesGroupExist(groupId)) {
            System.out.println("\tgroup found");
        } else {
            System.out.println("\tgroup not found");
            return;
        }

        // search artifact
        System.out.println("Searching artifact: " + artifactId);
        if (LocalMavenRepositoryReader.doesArtifactExist(groupId, artifactId)) {
            System.out.println("\tartifact found");
        } else {
            System.out.println("\tartifact not found");
        }
    }
}
