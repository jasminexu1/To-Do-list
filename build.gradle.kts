import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
    id("dev.hydraulic.conveyor") version "1.0.1"
}

group = "org.example"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("aws.sdk.kotlin:cognitoidentityprovider:0.17.7-beta")
    implementation("aws.sdk.kotlin:cognitoidentity:0.17.7-beta")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0-RC")
    implementation("com.russhwolf:multiplatform-settings:1.0.0-RC")
    implementation("com.github.tkuenneth:nativeparameterstoreaccess:0.1.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.7.20")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.20")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    implementation("org.slf4j:slf4j-simple:1.7.25")


    implementation(compose.desktop.macos_x64)
    implementation(compose.desktop.macos_arm64)
    implementation(compose.desktop.windows_x64)
    val v = "1.7.10"
    for (m in setOf("linuxAmd64", "macAmd64", "macAarch64", "windowsAmd64")) {
        m("org.jetbrains.kotlin:kotlin-stdlib:$v")
        m("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$v")
        m("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$v")
    }

    val exposedVersion: String by project
    dependencies {
        implementation("org.jetbrains.exposed:exposed-core:0.40.1")
        implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
        implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    }
    implementation("com.h2database:h2:1.4.191")


    // Test rules and transitive dependencies:
    //testImplementation("androidx.compose.ui:ui-test-junit4:1.1.0")
// Needed for createComposeRule, but not createAndroidComposeRule:
    //debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.1")


    //implementation("androidx.compose.ui:ui:1.2.1")
    //implementation("androidx.compose.ui:ui-tooling:1.2.1")
    // implementation("androidx.compose.material:material:1.2.1")
    //implementation("androidx.compose.material:material-ripple:1.2.1")

}

/*
configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}*/

// KotlinCompile

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            includeAllModules = true
            targetFormats(TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Dmg, TargetFormat.Exe)
            packageName = "Tisk-4.1"
            packageVersion = "4.1.0"
        }
    }
}


