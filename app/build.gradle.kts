plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.mapsplatform.secrets)
    alias(libs.plugins.google.services)
}

android {
    compileSdk = libs.versions.compile.sdk.get().toInt()
    namespace = "com.gotasoft.mosojkausay_mobile"

    defaultConfig {
        applicationId = "com.gotasoft.mosojkausay_mobile"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = 8
        versionName = "1.0.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.datastore.preferences)
    kapt(libs.moshi.kotlin.codegen)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.lottie)
    implementation(libs.maps.utils)
    implementation(libs.maps)
    implementation(libs.play.services.location)
    implementation(libs.permissions)

    implementation(libs.logging.interceptor)
    implementation(libs.coil)

    implementation(libs.imagepicker)

    implementation(libs.swiperefresh)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.messaging.ktx)

    implementation(libs.coroutines.play.services)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit.ktx)
    androidTestImplementation(libs.espresso.core)
}