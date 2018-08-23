import ch.scheitlin.alex.maven.LocalMavenRepositoryReader;

public class Main {
    public static void main(String[] args) {
        String groupID = "org.apache.maven.plugins";

        // search group
        System.out.println("Searching group: " + groupID);
        if (LocalMavenRepositoryReader.doesGroupExist(groupID)) {
            System.out.println("\tgroup found");
        } else {
            System.out.println("\tgroup not found");
        }
    }
}
