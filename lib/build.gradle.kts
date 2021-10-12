plugins {
	signing
	`java-library`
	`maven-publish`
}

// Apply scripts
apply("../local.gradle.kts")
apply("../env.gradle.kts")
// Extra properties
var pExtra = rootProject.extra

// Java configuration
java {
	sourceCompatibility = pExtra["sourceCompatibility"] as JavaVersion
	targetCompatibility = pExtra["sourceCompatibility"] as JavaVersion
}

// Javadoc configuration
tasks.named<Javadoc>("javadoc") {
	options {
		val opts = this as StandardJavadocDocletOptions

		opts.docTitle = pExtra["project.name"] as String
		opts.windowTitle = "${pExtra["project.name"]} - (${pExtra["project.version"]})"
		opts.links = listOf(
			"https://docs.oracle.com/en/java/javase/11/docs/api/",
			"https://javadoc.io/doc/org.jetbrains/annotations/latest/"
		)
	}
}

// Project dependencies
dependencies {
	compileOnly("org.jetbrains:annotations:22.0.0")
	// Use JUnit test framework.
	testImplementation("junit:junit:4.13.2")
}

/* Extra tasks */
tasks.create("javaSourcesJar", Jar::class) {
	archiveClassifier.set("sources")
	from(sourceSets["main"].java.srcDirs)
}

tasks.create("javadocJar", Jar::class) {
	archiveClassifier.set("javadoc")
	from(project.tasks["javadoc"])
	dependsOn(project.tasks["javadoc"])
}

// Maven Central configuration
afterEvaluate {
	publishing {
		publications {
			// Create new publication
			create<MavenPublication>("release") {
				val ext = rootProject.extra

				groupId = ext["maven.groupId"] as String
				artifactId = ext["maven.artifactId"] as String
				version = ext["project.version"] as String
				// Artifact
				from(components["java"])
				artifact(project.tasks["javaSourcesJar"])
				artifact(project.tasks["javadocJar"])

				// POM File
				pom {
					name.set(ext["maven.artifactId"] as String)
					description.set(ext["maven.artifactDescription"] as String)
					url.set(ext["maven.artifactUrl"] as String)

					// License
					licenses {
						license {
							name.set(ext["maven.licenseType"] as String)
							url.set(ext["maven.licenseUrl"] as String)
						}
					}
					// Developers
					developers {
						@Suppress("UNCHECKED_CAST")
						(ext["maven.developers"] as List<Map<String, String>>).forEach { item ->
							developer {
								if (item.containsKey("id"))
									id.set(item["id"])
								if (item.containsKey("name"))
									name.set(item["name"])
								if (item.containsKey("email"))
									email.set(item["email"])
							}
						}
					}
					// Connection
					scm {
						connection.set(ext["scm.connection"] as String)
						developerConnection.set(ext["scm.connection.ssh"] as String)
						url.set(ext["scm.url"] as String)
					}
				}
			}
		}
	}
}

// Signing configuration
afterEvaluate {
	signing {
		useInMemoryPgpKeys(
			pExtra["SIGNING_KEY_ID"] as String,
			pExtra["SIGNING_KEY_X64"] as String,
			pExtra["SIGNING_PASSWORD"] as String
		)
		sign(publishing.publications)
	}
}
