plugins {
	id("kotlin-platform-jvm")
}

dependencies {
	
	implementation(kotlin("stdlib"))
	expectedBy(project(":pulsar.core"))
}