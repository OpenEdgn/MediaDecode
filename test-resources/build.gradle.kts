
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm")
    maven
}
java.sourceCompatibility = JavaVersion.VERSION_1_8


dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

