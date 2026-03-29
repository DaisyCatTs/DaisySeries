import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    id("org.jetbrains.kotlin.jvm") version "2.3.20" apply false
}

allprojects {
    group = "cat.daisy"
    version = providers.gradleProperty("version").get()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    extensions.configure<KotlinJvmProjectExtension> {
        jvmToolchain(21)
    }

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.add("-Xjsr305=strict")
        }
    }

    dependencies {
        add("implementation", "org.jetbrains.kotlin:kotlin-stdlib:2.3.20")
        add("testImplementation", "org.jetbrains.kotlin:kotlin-test:2.3.20")
        add("testImplementation", "org.junit.jupiter:junit-jupiter:5.14.3")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        maxParallelForks = 1
        maxHeapSize = "256m"
    }
}

project(":series-material") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-sound") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-itemflag") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-enchantment") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-potion") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-biome") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-entity") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-game-mode") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-particle") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-statistic") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-villager-profession") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-attribute") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-difficulty") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-blockface") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-damage-cause") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-operation") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-pattern-type") {
    dependencies {
        add("api", project(":series-base"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
        add("testImplementation", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

project(":series-all") {
    dependencies {
        add("api", project(":series-material"))
        add("api", project(":series-sound"))
        add("api", project(":series-itemflag"))
        add("api", project(":series-enchantment"))
        add("api", project(":series-potion"))
        add("api", project(":series-biome"))
        add("api", project(":series-entity"))
        add("api", project(":series-game-mode"))
        add("api", project(":series-particle"))
        add("api", project(":series-statistic"))
        add("api", project(":series-villager-profession"))
        add("api", project(":series-attribute"))
        add("api", project(":series-difficulty"))
        add("api", project(":series-blockface"))
        add("api", project(":series-damage-cause"))
        add("api", project(":series-operation"))
        add("api", project(":series-pattern-type"))
    }
}

project(":example-plugin") {
    dependencies {
        add("implementation", project(":series-all"))
        add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    }
}

tasks.register("quality") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Runs the DaisySeries verification suite."
    dependsOn(subprojects.map { "${it.path}:check" })
}
