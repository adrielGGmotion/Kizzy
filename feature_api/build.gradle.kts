plugins {
    id ("kizzy.android.library")
    id ("kizzy.android.library.compose")
    id ("kizzy.android.feature")
    id ("kizzy.android.hilt")
}

android {
    namespace = "com.my.kizzy.feature_api"
}

dependencies {
    implementation (libs.bundles.network.ktor.server)
    implementation(project(":common:preference"))
    implementation(project(path = ":common:components"))
    implementation(libs.timber)
    implementation(libs.material.icons.extended)
    implementation(libs.activity.compose)
    implementation(libs.hilt.navigation.compose)
}
