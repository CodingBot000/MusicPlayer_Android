buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}