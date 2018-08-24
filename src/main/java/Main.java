import ch.scheitlin.alex.maven.LocalMavenRepositoryReader;

public class Main {
    public static void main(String[] args) {
        String groupId = "org.apache.maven.plugins";
        String artifactId = "maven-clean-plugin";
        String version = "2.6.1";

        // search existing group
        System.out.println("Searching group: " + groupId);
        if (LocalMavenRepositoryReader.doesGroupExist(groupId)) {
            System.out.println("\tgroup found");
        } else {
            System.out.println("\tgroup not found");
        }

        // search non-existing group
        System.out.println("Searching group: " + groupId + ".p");
        if (LocalMavenRepositoryReader.doesGroupExist(groupId + ".p")) {
            System.out.println("\tgroup found");
        } else {
            System.out.println("\tgroup not found");
            System.out.println("\tsub grop not found: " + LocalMavenRepositoryReader.getNonExistingSubGroup(groupId + ".p"));
        }

        // search artifact
        System.out.println("Searching artifact: " + artifactId);
        if (LocalMavenRepositoryReader.doesArtifactExist(groupId, artifactId)) {
            System.out.println("\tartifact found");
        } else {
            System.out.println("\tartifact not found");
        }

        // list artifacts
        System.out.println("Artifacts of: " + groupId);
        for (String artifact : LocalMavenRepositoryReader.getArtifactsOfGroup(groupId)) {
            System.out.println("\t" + artifact);
        }

        // search version
        System.out.println("Searching version: " + version);
        if (LocalMavenRepositoryReader.doesVersionExist(groupId, artifactId, version)) {
            System.out.println("\tversion found");
        } else {
            System.out.println("\tversion not found");
        }

        // list versions
        System.out.println("Versions of: " + groupId + "." + artifactId);
        for (String v : LocalMavenRepositoryReader.getArtifactVersions(groupId, artifactId)) {
            System.out.println("\t" + v);
        }
    }
}
