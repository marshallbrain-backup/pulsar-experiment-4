plugins {
	id("kotlin-platform-jvm")
}

dependencies {
	
	implementation(kotlin("stdlib-jdk8"))
	implementation("com.marshalldbrain.ion:src.jvm")
	expectedBy(project(":pulsar.core"))
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
	testCompile("org.junit.jupiter:junit-jupiter-engine:5.5.2")
	testCompile("org.junit.jupiter:junit-jupiter-params:5.5.2")
	testCompile("org.assertj:assertj-core:3.11.1")
	testImplementation("io.mockk:mockk:1.9.3")
	
}

tasks {
	test {
		useJUnitPlatform()
	}
	compileKotlin {
		kotlinOptions.jvmTarget = "1.8"
	}
	compileTestKotlin {
		kotlinOptions.jvmTarget = "1.8"
	}
}