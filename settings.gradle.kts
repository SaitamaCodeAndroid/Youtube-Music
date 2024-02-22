pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Spotify"
include(":app")
include(":core")
include(":core:network")
include(":core:data")
include(":core:model")
include(":core:domain")
include(":core:common")
