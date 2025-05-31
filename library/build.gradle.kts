import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.vanniktech.maven.publish") version "0.32.0"
}

android {
    namespace = "io.github.aungthiha.snackbar"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(
        groupId = "io.github.aungthiha",
        artifactId = "android-snackbar-channel",
        version = "1.0.1"
    )

    pom {
        name.set("AndroidSnackbarChannel")
        description.set("A lightweight, lifecycle-safe snackbar event dispatcher for Jetpack Compose that addresses common pitfalls of using SharedFlow and StateFlow.")
        url.set("https://github.com/AungThiha/AndroidSnackbarChannel")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/AungThiha/AndroidSnackbarChannel/blob/main/LICENSE")
            }
        }

        developers {
            developer {
                id.set("AungThiha")
                name.set("Aung Thiha")
                email.set("mr.aungthiha@gmail.com")
            }
        }

        scm {
            connection.set("scm:git:github.com/AungThiha/AndroidSnackbarChannel.git")
            developerConnection.set("scm:git:ssh://github.com/AungThiha/AndroidSnackbarChannel.git")
            url.set("https://github.com/AungThiha/AndroidSnackbarChannel")
        }
    }
}
