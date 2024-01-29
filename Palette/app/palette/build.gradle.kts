import io.netty.util.ReferenceCountUtil.release

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "ru.mobilestingray.mylibrary"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }



    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    publishing {
        singleVariant("release-1") {
            // if you don't want sources/javadoc, remove these lines
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.palette:palette-ktx:1.0.0")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("palette") {
                from(components["release"])
                groupId = "ru.mobilestingray"
                artifactId = "palette"
                version = "1.0.0"

                pom {
                    name.set("pomPalette")
                    description.set("pomDescription")
                }
            }
        }
        repositories {
            mavenLocal()
        }
    }
}
