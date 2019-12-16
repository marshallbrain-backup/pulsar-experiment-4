plugins {
	id("kotlin-platform-jvm")
}

dependencies {
	
	implementation(kotlin("stdlib-jdk8"))
	expectedBy(project(":pulsar.core"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
	
}