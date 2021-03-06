import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	idea
	kotlin("jvm") version "1.3.21"
	jacoco
	maven
	signing
	id("io.codearte.nexus-staging") version "0.20.0"
	id("org.jetbrains.dokka") version "0.9.18"
}

group = "com.apandey"
version = "0.1.2"

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation(kotlin("stdlib"))

	testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
	testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.14")
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}

val sourcesJar by tasks.registering(Jar::class) {
	archiveClassifier.set("sources")
	from(sourceSets["main"].allSource)
}

val dokka by tasks.getting
val javadocJar by tasks.registering(Jar::class) {
	archiveClassifier.set("javadoc")
	from(dokka)
}

artifacts {
	archives(sourcesJar)
	archives(javadocJar)
}

signing {
	sign(configurations["archives"])
}

val uploadArchives by tasks.getting(Upload::class) {
	val ossrhUsername: String? by project
	val ossrhPassword: String? by project

	fun GroovyBuilderScope.repo(type: String, mavenUrl: String) {
		type("url" to mavenUrl) {
			"authentication"(
					"userName" to ossrhUsername,
					"password" to ossrhPassword
			)
		}
	}

	repositories {
		withConvention(MavenRepositoryHandlerConvention::class) {
			mavenDeployer {
				beforeDeployment( object: Action<MavenDeployment> {
					override fun execute(deployment: MavenDeployment) { signing.signPom(deployment) }
				})

				withGroovyBuilder {
					repo("repository", "https://oss.sonatype.org/service/local/staging/deploy/maven2/")
					repo("snapshotRepository", "https://oss.sonatype.org/content/repositories/snapshots/")
				}

				pom.project {
					withGroovyBuilder {
						"name"("clinical")
						"packaging"("jar")
						"description"("a health check framework for java applications")
						"url"("https://github.com/ashishpandey/clinical")

						"scm" {
							"connection"("scm:git:git@github.com:ashishpandey/clinical.git")
							"developerConnection"("scm:git:git@github.com:ashishpandey/clinical.git")
							"url"("https://github.com/ashishpandey/clinical")
						}

						"licenses" {
							"license" {
								"name"("MIT License")
								"url"("https://raw.githubusercontent.com/ashishpandey/clinical/master/LICENSE")
							}
						}

						"developers" {
							"developer" {
								"id"("ashishpandey")
								"name"("Ashish Pandey")
								"email"("ashish@apandey.com")
							}
						}

					}
				}
			}
		}
	}
}

val jacocoTestReport by tasks.getting(JacocoReport::class) {
	reports {
		xml.isEnabled = true
		html.isEnabled = false
	}
}

val check by tasks.getting {
	dependsOn(jacocoTestReport)
}
