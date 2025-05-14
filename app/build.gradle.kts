import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    namespace = "com.proteam.aiskincareadvisor"
    compileSdk = 35

    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) {
        throw GradleException("File local.properties not found in project root")
    }

    val localProperties = Properties().apply {
        load(FileInputStream(localPropertiesFile))
    }
    val apiToken: String = localProperties.getProperty("apiAIToken") ?: ""
    val azureAIEndpoint: String = localProperties.getProperty("azureAIEndpoint") ?: ""

    defaultConfig {
        applicationId = "com.proteam.aiskincareadvisor"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"


        buildConfigField("String", "API_AI_TOKEN", "\"$apiToken\"")
        buildConfigField("String", "AZURE_AI_ENDPOINT", "\"$azureAIEndpoint\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    packaging {
        resources {
            excludes += setOf(
                "META-INF/INDEX.LIST",
                "META-INF/io.netty.versions.properties",
                "META-INF/LICENSE-notice.md",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE",
                "META-INF/NOTICE.md",
                "META-INF/NOTICE",
                "META-INF/DEPENDENCIES",
                "META-INF/*.kotlin_module",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.crashlytics)
    
    // DataStore Preferences for theme settings
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase Authentication
    implementation(libs.firebase.bom)
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    
    // Play Integrity API for reCAPTCHA verification
    implementation("com.google.android.recaptcha:recaptcha:18.4.0")
    
    // Google Identity Services for Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    
    implementation("com.google.android.material:material:1.12.0")
    implementation ("androidx.compose.material:material-icons-extended:1.5.0")

    implementation (libs.androidx.navigation.compose.v275)

    // Retrofit for API calls
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    // ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.azure.ai.inference)
    implementation (libs.coil.compose)

    implementation (libs.firebase.firestore.ktx)

}
