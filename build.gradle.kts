plugins {
	id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

buildscript {
	extra.set("project.name", "Godot Launcher Utilities")
	extra.set("project.version", "0.1.0")
	// JDK Configuration
	extra.set("sourceCompatibility", JavaVersion.VERSION_11)
	// Maven configuration
	extra.set("maven.groupId", "com.github.ushiosan23")
	extra.set("maven.artifactId", "gdl_utils")
	extra.set("maven.artifactDescription", "Godot Launcher Utilities")
	extra.set("maven.artifactUrl", "https://github.com/Ushiosan23/gdl_utils")

	extra.set("maven.licenseType", "MIT")
	extra.set("maven.licenseUrl", "")

	extra.set(
		"maven.developers", listOf(
			mapOf(
				"id" to "Ushiosan23",
				"name" to "Brian Alvarez",
				"email" to "haloleyendee@outlook.com"
			)
		)
	)
	// Git configuration
	extra.set("scm.connection", "scm:git:github.com/Ushiosan23/gdl_utils.git")
	extra.set("scm.connection.ssh", "scm:git:ssh:github.com/Ushiosan23/gdl_utils.git")
	extra.set("scm.url", "${extra["maven.artifactUrl"]}/tree/main")

	// Repos
	repositories {
		mavenCentral()
	}
	// Dependencies
	dependencies {}
}

// Project configurations
allprojects {
	repositories {
		mavenCentral()
	}
}

// Nexus configuration
nexusPublishing {
	repositories {
		sonatype {
			apply("local.gradle.kts")
			// Configure sonatype account
			stagingProfileId.set(rootProject.extra["sonatype.profileId"] as String)
			username.set(rootProject.extra["sonatype.username"] as String)
			password.set(rootProject.extra["sonatype.password"] as String)
		}
	}
}
