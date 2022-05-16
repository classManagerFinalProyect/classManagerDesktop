import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
}

group = "me.saine"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {

    //AutoImports
    implementation(compose.desktop.currentOs)
    implementation("com.artemzin.assert-parcelable:assert-parcelable:1.0.1")
    implementation("io.taig.android:parcelable_2.11:2.4.1")
    implementation("dev.icerock.moko:parcelize:0.8.0")
    implementation("com.google.firebase:firebase-admin:8.1.0")
    implementation("org.webjars.npm:bootstrap-datepicker:1.9.0")

    //Decompose
    val decomposeVersion = "0.6.0"
    implementation("com.arkivanov.decompose:decompose-jvm:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains-jvm:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")

    //API
    implementation("com.squareup.okhttp3:okhttp:3.8.1")
    implementation("com.squareup.retrofit2:retrofit:2.7.1")
    implementation("com.squareup.retrofit2:converter-gson:2.7.1")

    //Items


    //Others
   // implementation("io.coil-kt:coil-compose:2.0.0-rc03")
   // implementation("com.github.tfaki:ComposableSweetToast:1.0.1")

    //Firebase
    implementation("io.github.navidjalali:firebaseauth_2.13:0.0.4")
    implementation("com.google.firebase:firebase-auth-ktx:20.0.4")
    implementation("com.google.firebase:firebase-firestore-ktx:22.1.2")
    implementation("com.google.firebase:firebase-analytics:17.5.0")
    implementation("com.google.firebase:firebase-firestore:21.6.0")
    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))
    implementation("com.google.firebase:firebase-database-ktx:20.0.3")
    implementation("com.google.android.gms:play-services-auth:20.1.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.0.0")
    implementation("com.google.firebase:firebase-database-ktx:20.0.3")
    implementation("com.google.firebase:firebase-database:16.0.4")
    implementation("com.google.firebase:firebase-database-ktx:16.0.1")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "classManagerDesktop"
            packageVersion = "1.0.0"
        }
    }
}