plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("io.qameta.allure") version "2.11.2"
}
apply(from = "../ktlint.gradle.kts")

android {
    compileSdk = 33
    buildToolsVersion = "33.0.0"
    defaultConfig {
        applicationId = "dev.marcosfarias.pokedex"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        viewBinding = true
    }
    namespace = "dev.marcosfarias.pokedex"
}

val allureAdapterVersion = "2.27.0"

allure {
    version.set(allureAdapterVersion)
    adapter {
        allureJavaVersion.set(allureAdapterVersion)
        autoconfigure.set(true)
        frameworks {
            junit4 {
                adapterVersion.set(allureAdapterVersion)
            }
        }
    }
    report {
        dependsOnTests.set(true)
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Architecture
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

    // Material
    implementation("com.google.android.material:material:1.8.0")

    // Third Party
    implementation("com.leinardi.android:speed-dial:3.3.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Persistence
    implementation("androidx.room:room-runtime:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")

    // Glide
    kapt("com.github.bumptech.glide:compiler:4.13.2")
    implementation("com.github.bumptech.glide:glide:4.13.2")

    // Koin
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-core:3.2.0")

    // Test
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.qameta.allure:allure-junit4:$allureAdapterVersion")
    testImplementation("io.qameta.allure:allure-java-commons:$allureAdapterVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("com.android.support.test.espresso:espresso-contrib:3.0.2")
    androidTestImplementation("androidx.navigation:navigation-testing:2.5.3")
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    debugImplementation("androidx.fragment:fragment-testing:1.5.5")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("io.mockk:mockk-android:1.14.9") {
        exclude(group = "io.mockk", module = "mockk-agent-jvm")
    }
    testImplementation("io.mockk:mockk:1.14.9")
    testImplementation("io.mockk:mockk-agent-jvm:1.14.9")

    // Appium
    androidTestImplementation("io.appium:java-client:7.6.0")
}
