pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // বা PREFER_SETTINGS
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Baustbus"
include(":app")