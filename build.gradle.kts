buildscript {
    
    repositories {
        mavenCentral()
    }
    
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.61"))
    }
    
}

allprojects {
    
    group = "com.marshalldbrain.pulsar"
    version = "0.1"
    
    repositories {
        mavenCentral()
    }
    
}