plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("maven-publish")
}

android {
    namespace = "com.android_boilerplate"
    compileSdk = 35

    defaultConfig {
        minSdk = 30

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf(
            "--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
            "--add-exports", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
            "--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
            "--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"
        ))
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.navigation.fragment)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.hilt.android)
    implementation(libs.androidx.swiperefreshlayout)
    kapt(libs.hilt.android.compiler)
    implementation(libs.retrofit2.gson)
    implementation(libs.sdp)
    implementation(libs.ssp)
    implementation(libs.security)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.retrofit)
}
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from (components["release"])
                groupId = "com.github.sarkar-jayanta"
                artifactId = "android-boilerplate"
                version = "1.0.0"
            }
        }
    }
}