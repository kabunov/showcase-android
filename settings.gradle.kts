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

rootProject.name = "Android Showcase"
include(":app")
include(":feature:list")
include(":feature:details")
include(":data:repository")
include(":domain")
include(":model")
include(":data:db")
