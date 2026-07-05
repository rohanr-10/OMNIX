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

rootProject.name = "OMNIX"

include(
    ":app",
    ":core-model",
    ":core-ui",
    ":core-data",
    ":feature-home",
    ":feature-chat",
    ":feature-council",
    ":feature-modelmanager"
)
