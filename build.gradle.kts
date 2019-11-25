import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

dependencies {

}

allprojects {
    
    apply {
        plugin("kotlin")
    }
    
    version = 0.1
    
    repositories {
        mavenCentral()
    }
    
    dependencies {
        
        implementation(kotlin("stdlib-jdk8"))
        
    }
    
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    
}