pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "BankingCalculator"
include(":androidApp")
include(":root:api")
include(":root:impl")
include(":feature:deposit:api")
include(":feature:deposit:impl")
include(":feature:deposit:ui")
include(":core")
include(":core:ui")
include(":field:api")
include(":field:impl")
include(":field:ui")
include(":shared")
