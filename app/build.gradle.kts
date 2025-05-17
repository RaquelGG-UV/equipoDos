plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.univalle.dogappnew"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.univalle.dogappnew"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures{
        dataBinding = true
    }


}

dependencies {

    val navversion = "2.9.0"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.biometric.ktx)
    implementation(libs.androidx.databinding.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.lottie.compose)

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navversion")
    implementation("androidx.navigation:navigation-ui-ktx:$navversion")
    implementation("androidx.navigation:navigation-common-ktx:$navversion")


    //cardview
    implementation("androidx.cardview:cardview:1.0.0")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.5.7")

    //livedata
    implementation("androidx.lifecycle:lifecycly-livedata-ktx:2.6.1")

    



}