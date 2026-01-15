
plugins {
	id("fabric-loom") version "1.14-SNAPSHOT"
}


version = property("mod_version")!! as String + "+mc" + property("minecraft_version")!!
group = property("maven_group")!!

base {
	archivesName.set(property("archives_base_name") as String)
}

repositories {
	mavenCentral()
	repositories {
		exclusiveContent {
			forRepository {
				maven {
					name = "Modrinth"
					url =  uri("https://api.modrinth.com/maven")
				}
			}
			filter {
				includeGroup("maven.modrinth")
			}
		}
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
	modImplementation ("net.fabricmc:fabric-loader:${property("loader_version")}")

	// Fabric API is not an end user dep -- it's required by Controlling hence why it's marked as runtimeOnly
	modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

	modImplementation("maven.modrinth:controlling:${property("controlling_version")}")
	modImplementation("maven.modrinth:searchables:${property("searchables_version")}")
	include(implementation("com.moulberry:mixinconstraints:${(property("mcon_version"))}")!!)

	
}

tasks.processResources {
	inputs.property("version", project.version)

	filesMatching("fabric.mod.json") {
		expand(mapOf("version" to project.version, "support_range" to project.property("support_range")))
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release.set(21)
}

java {
	withSourcesJar()
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}


tasks.named<Jar>("jar") {

	from("LICENSE") {
		rename { "${it}_${project.base.archivesName.get()}" }
	}
}

