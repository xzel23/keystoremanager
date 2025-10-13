import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

rootProject.name = "keystoremanager"

// This file is part of Keystore Manager.
//
// Keystore Manager is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License version 3 as published
// by the Free Software Foundation.
//
// Keystore Manager is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Keystore Manager. If not, see <https://www.gnu.org/licenses/>.
//
// SPDX-License-Identifier: GPL-3.0-only
// Copyright (c) 2025 Axel Howind (axel@dua3.com)

val projectVersion = "0.0.1-SNAPSHOT"

dependencyResolutionManagement {

    val isSnapshot = projectVersion.toDefaultLowerCase().contains("-snapshot")
    val isReleaseCandidate = projectVersion.toDefaultLowerCase().contains("-rc")

    versionCatalogs {
        create("libs") {
            version("projectVersion", projectVersion)

            plugin("jlink", "org.beryx.jlink").version("3.1.3")
            plugin("versions", "com.github.ben-manes.versions").version("0.53.0")
            plugin("test-logger", "com.adarshr.test-logger").version("4.0.0")
            plugin("spotbugs", "com.github.spotbugs").version("6.4.7")
            plugin("cabe", "com.dua3.cabe").version("3.3.0")
            plugin("forbiddenapis", "de.thetaphi.forbiddenapis").version("3.10")

            version("bouncycastle", "1.83")
            version("dua3-utility", "20.0.3")
            version("ikonli", "12.4.0")
            version("jackson", "2.20.1")
            version("jspecify", "1.0.0")
            version("junit-bom", "6.0.1")
            version("log4j-bom", "2.25.2")
            version("meja", "9.0.0-rc")
            version("miglayout", "11.4.2")
            version("poi", "5.5.0")
            version("slf4j", "2.0.17")
            version("record-builder", "51")
            version("commons-compress", "1.28.0")
            version("commons-logging", "1.3.1")
            version("atlantafx", "2.1.0")
            version("spotbugs", "4.9.8")

            library("atlantafx", "io.github.mkpaz", "atlantafx-base").versionRef("atlantafx")

            library("bouncycastle-provider", "org.bouncycastle", "bcprov-jdk18on").versionRef("bouncycastle")
            library("bouncycastle-pkix", "org.bouncycastle", "bcpkix-jdk18on").versionRef("bouncycastle")

            library(
                "record-builder-processor",
                "io.soabase.record-builder",
                "record-builder-processor"
            ).versionRef("record-builder")
            library(
                "record-builder-core",
                "io.soabase.record-builder",
                "record-builder-processor"
            ).versionRef("record-builder")
            library("dua3-utility-bom", "com.dua3.utility", "utility-bom").versionRef("dua3-utility")
            library("dua3-utility", "com.dua3.utility", "utility").withoutVersion()
            library("dua3-utility-db", "com.dua3.utility", "utility-db").withoutVersion()
            library("dua3-utility-logging", "com.dua3.utility", "utility-logging").withoutVersion()
            library("dua3-utility-logging-log4j", "com.dua3.utility", "utility-logging-log4j").withoutVersion()
            library("dua3-utility-swing", "com.dua3.utility", "utility-swing").withoutVersion()
            library("dua3-utility-fx", "com.dua3.utility", "utility-fx").withoutVersion()
            library("dua3-utility-fx-controls", "com.dua3.utility", "utility-fx-controls").withoutVersion()
            library("dua3-utility-fx-db", "com.dua3.utility", "utility-fx-db").withoutVersion()
            library("dua3-utility-fx-icons", "com.dua3.utility", "utility-fx-icons").withoutVersion()
            library("dua3-utility-fx-icons-ikonli", "com.dua3.utility", "utility-fx-icons-ikonli").withoutVersion()
            library("dua3-utility-fx-web", "com.dua3.utility", "utility-fx-web").withoutVersion()
            library("meja", "com.dua3.meja", "meja-core").versionRef("meja")
            library("meja-generic", "com.dua3.meja", "meja-generic").versionRef("meja")
            library("meja-poi", "com.dua3.meja", "meja-poi").versionRef("meja")
            library("meja-swing", "com.dua3.meja", "meja-swing").versionRef("meja")
            library("meja-fx", "com.dua3.meja", "meja-fx").versionRef("meja")
            library("meja-db", "com.dua3.meja", "meja-db").versionRef("meja")
            library("miglayout-fx", "com.miglayout", "miglayout-javafx").versionRef("miglayout")
            library("ikonli-fontawesome", "org.kordamp.ikonli", "ikonli-fontawesome-pack").versionRef("ikonli")
            library("ikonli-feather", "org.kordamp.ikonli", "ikonli-feather-pack").versionRef("ikonli")
            library("ikonli-javafx", "org.kordamp.ikonli", "ikonli-javafx").versionRef("ikonli")
            library("jackson-bom", "com.fasterxml.jackson", "jackson-bom").versionRef("jackson")
            library("jackson-annotations", "com.fasterxml.jackson.core", "jackson-annotations").withoutVersion()
            library("jackson-core", "com.fasterxml.jackson.core", "jackson-core").withoutVersion()
            library("jackson-databind", "com.fasterxml.jackson.core", "jackson-databind").withoutVersion()
            library("jspecify", "org.jspecify", "jspecify").versionRef("jspecify")
            library("slf4j-api", "org.slf4j", "slf4j-api").versionRef("slf4j")
            library("log4j-bom", "org.apache.logging.log4j", "log4j-bom").versionRef("log4j-bom")
            library("log4j-api", "org.apache.logging.log4j", "log4j-api").withoutVersion()
            library("log4j-core", "org.apache.logging.log4j", "log4j-core").withoutVersion()
            library("log4j-jul", "org.apache.logging.log4j", "log4j-jul").withoutVersion()
            library("log4j-jcl", "org.apache.logging.log4j", "log4j-jcl").withoutVersion()
            library("log4j-slf4j2", "org.apache.logging.log4j", "log4j-slf4j2-impl").withoutVersion()
            library("log4j-to-slf4j", "org.apache.logging.log4j", "log4j-to-slf4j").withoutVersion()
            library("junit-bom", "org.junit", "junit-bom").versionRef("junit-bom")
            library("junit-platform-launcher", "org.junit.platform", "junit-platform-launcher").withoutVersion()
            library("junit-jupiter-api", "org.junit.jupiter", "junit-jupiter-api").withoutVersion()
            library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").withoutVersion()
            library("poi", "org.apache.poi", "poi").versionRef("poi")
            library("poi-ooxml", "org.apache.poi", "poi-ooxml").versionRef("poi")
            library("commons-compress", "org.apache.commons", "commons-compress").versionRef("commons-compress")
            library("commons-logging", "commons-logging", "commons-logging").versionRef("commons-logging")
        }
    }

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {

        mavenLocal()

        // Repsy releases (for private dependencies)
        maven {
            val repsy_user: String by settings
            val repsy_password: String by settings

            credentials {
                username = repsy_user
                password = repsy_password
            }

            name = "dua3"
            url = uri("https://repo.repsy.io/mvn/dua3/dua3")
        }

        // Maven Central Repository
        mavenCentral()

        // Sonatype Releases
        maven {
            name = "central.sonatype.com-releases"
            url = java.net.URI("https://central.sonatype.com/content/repositories/releases/")
            mavenContent {
                releasesOnly()
            }
        }

        // Apache releases
        maven {
            name = "apache-releases"
            url = java.net.URI("https://repository.apache.org/content/repositories/releases/")
            mavenContent {
                releasesOnly()
            }
        }

        if (isSnapshot) {
            println("snapshot version detected, adding Maven snapshot repositories")

            mavenLocal()

            // Repsy snapshots (for private dependencies)
            maven {
                val repsy_user: String by settings
                val repsy_password: String by settings

                credentials {
                    username = repsy_user
                    password = repsy_password
                }

                name = "dua3"
                url = uri("https://repo.repsy.io/mvn/dua3/dua3-snapshot")
            }

            // Sonatype Snapshots
            maven {
                name = "Central Portal Snapshots"
                url = java.net.URI("https://central.sonatype.com/repository/maven-snapshots/")
                mavenContent {
                    snapshotsOnly()
                }
            }

            // Apache snapshots
            maven {
                name = "apache-snapshots"
                url = java.net.URI("https://repository.apache.org/content/repositories/snapshots/")
                mavenContent {
                    snapshotsOnly()
                }
            }
        }

        if (isReleaseCandidate) {
            println("release candidate version detected, adding Maven staging repositories")

            // Apache staging
            maven {
                name = "apache-staging"
                url = java.net.URI("https://repository.apache.org/content/repositories/staging/")
                mavenContent {
                    releasesOnly()
                }
            }
        }
    }

}
