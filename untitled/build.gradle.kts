plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    implementation("org.seleniumhq.selenium:selenium-java:4.13.0")
    implementation("io.appium:java-client:8.4.0")
}

tasks.test {
}
