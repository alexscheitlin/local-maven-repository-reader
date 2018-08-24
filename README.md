# Local Maven Repository Reader

> _Provides functions to get information about locally available maven artifacts and their versions from the .m2 directory._

[![Build Status](https://travis-ci.org/alexscheitlin/local-maven-repository-reader.svg?branch=master)](https://travis-ci.org/alexscheitlin/local-maven-repository-reader)
[![JitPack Version](https://jitpack.io/v/alexscheitlin/local-maven-repository-reader.svg)](https://jitpack.io/#alexscheitlin/local-maven-repository-reader/v1.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## How to Use

### Add Dependency

#### Maven

- Add the JitPack repository to your `pom.xml` file:
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

- Add the dependency:
```
	<dependency>
	    <groupId>com.github.alexscheitlin</groupId>
	    <artifactId>local-maven-repository-reader</artifactId>
	    <version>v1.1.0</version>
	</dependency>
```

#### Gradle

- Add the JitPack repository to your `build.gradle` file:
```
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
```

- Add the dependency:
```
	dependencies {
	        implementation 'com.github.alexscheitlin:local-maven-repository-reader:v1.1.0'
	}
```

### Code Example

_The example below can be found [here](src/main/java/Main.java)._

[Check whether a specific group exists or not](#check-whether-a-specific-group-exists-or-not)\
[Get the part of a specific groupId that does not exist](#get-the-part-of-a-specific-groupid-that-does-not-exist)\
[Check whether a specific artifact (of a group) exists or not](#check-whether-a-specific-artifact-of-a-group-exists-or-not)\
[Get a list of all available artifacts of a specific group](#get-a-list-of-all-available-artifacts-of-a-specific-group)\
[Check whether a specific version (of a artifact of a group) exists or not](#check-whether-a-specific-version-of-a-artifact-of-a-group-exists-or-not)\
[Get a list of all available versions of a specific artifact](#get-a-list-of-all-available-versions-of-a-specific-artifact)

#### Check whether a specific group exists or not

```java
String groupId = "org.apache.maven.plugins";

System.out.println("Searching group: " + groupId);
if (LocalMavenRepositoryReader.doesGroupExist(groupId)) {
    System.out.println("\tgroup found");
} else {
    System.out.println("\tgroup not found");
}
```

Example output:
```
Searching group: org.apache.maven.plugins
	group found
```

#### Get the part of a specific groupId that does not exist

```java
String groupId = "org.apache.maven.plugins";

System.out.println("Searching group: " + groupId + ".p");
if (LocalMavenRepositoryReader.doesGroupExist(groupId + ".p")) {
    System.out.println("\tgroup found");
} else {
    System.out.println("\tgroup not found");
    System.out.println("\tsub grop not found: " + LocalMavenRepositoryReader.getNonExistingSubGroup(groupId + ".p"));
}
```

Example output:
```
Searching group: org.apache.maven.plugins.p
	group not found
	sub grop not found: p
```

#### Check whether a specific artifact (of a group) exists or not

```java
String groupId = "org.apache.maven.plugins";
String artifactId = "maven-clean-plugin";

System.out.println("Searching artifact: " + artifactId);
if (LocalMavenRepositoryReader.doesArtifactExist(groupId, artifactId)) {
    System.out.println("\tartifact found");
} else {
    System.out.println("\tartifact not found");
}
```

Example output:
```
Searching artifact: maven-clean-plugin
	artifact found
```

#### Get a list of all available artifacts of a specific group

```java
String groupId = "org.apache.maven.plugins";

System.out.println("Artifacts of: " + groupId);
for (String artifact : LocalMavenRepositoryReader.getArtifactsOfGroup(groupId)) {
    System.out.println("\t" + artifact);
}
```

Example output:
```
Artifacts of: org.apache.maven.plugins
	maven-antrun-plugin
	maven-assembly-plugin
	maven-clean-plugin
	maven-compiler-plugin
	maven-dependency-plugin
	maven-deploy-plugin
	...
```

#### Check whether a specific version (of a artifact of a group) exists or not

```java
System.out.println("Searching version: " + version);
if (LocalMavenRepositoryReader.doesVersionExist(groupId, artifactId, version)) {
    System.out.println("\tversion found");
} else {
    System.out.println("\tversion not found");
}
```

Example output:
```
Searching version: 2.6.1
	version found
```

#### Get a list of all available versions of a specific artifact

```java
String groupId = "org.apache.maven.plugins";
String artifactId = "maven-clean-plugin";

System.out.println("Versions of: " + groupId + "." + artifactId);
for (String v : LocalMavenRepositoryReader.getArtifactVersions(groupId, artifactId)) {
    System.out.println("\t" + v);
}
```

Example output:
```
Versions of: org.apache.maven.plugins.maven-clean-plugin
	2.5
	2.6
	2.6.1
```

## Authors

- **Alex Scheitlin** - *Initial work* - [alexscheitlin](https://github.com/alexscheitlin)

## License

This project is licensed under the [MIT License](LICENSE).
