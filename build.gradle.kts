buildscript {
    extra.apply {
        set("room_version", "2.6.1")
    }

}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}