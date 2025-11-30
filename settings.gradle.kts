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

rootProject.name = "MovieApp"

// App
include(":app")

// Core
include(":core:common")
include(":core:model")
include(":core:data")
include(":core:database")
include(":core:network")
include(":core:domain")
include(":core:ui")
include(":core:designsystem")

// Legacy modules (to be migrated)
include(":data")
include(":domain")

// Feature
include(":feature-home")
include(":feature-detail")
include(":feature-favorite")
include(":feature-common-ui")
